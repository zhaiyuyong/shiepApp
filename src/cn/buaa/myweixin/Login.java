package cn.buaa.myweixin;

import org.json.JSONObject;

import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ConstantUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;
import cn.edu.shiep.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText mUser; // �ʺű༭��
	private EditText mPassword; // ����༭��
	private Button login_button = null;

	private String username = "";
	private String password = "";
	
	private AppSystem application;
	
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		login_button = (Button) findViewById(R.id.login_login_btn);
		login_button.setOnClickListener(onClick);
		this.context = this;
		application = (AppSystem) this.getApplication();
		

	

	}

	private void loginAccess() {
		Utils.showProgress(context, "���ڽ������У�飬���Ժ�...");
		ThreadPoolUtils.execute(new LoginThread());
	}

	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Animation shake = AnimationUtils.loadAnimation(Login.this,
					R.anim.shake);
			Animation myAlphaAction = AnimationUtils.loadAnimation(Login.this,
					R.anim.alpha_action);
			v.startAnimation(myAlphaAction);
			switch (v.getId()) {
			case R.id.login_login_btn:
				username = mUser.getText().toString().trim();
				password = mPassword.getText().toString().trim();
				if ("".equals(username)) {
					mUser.startAnimation(shake);
					mUser.requestFocus();
					return;
				} else if ("".equals(password)) {
					mPassword.startAnimation(shake);
					mPassword.requestFocus();
					return;
				}
				loginAccess();
				break;
			default:
				break;
			}

		}
	};

	public void login_mainweixin(View v) {
		if ("buaa".equals(mUser.getText().toString())
				&& "123".equals(mPassword.getText().toString())) // �ж� �ʺź�����
		{
			Intent intent = new Intent();
			intent.setClass(Login.this, LoadingActivity.class);
			startActivity(intent);
			// Toast.makeText(getApplicationContext(), "��¼�ɹ�",
			// Toast.LENGTH_SHORT).show();
		} else if ("".equals(mUser.getText().toString())
				|| "".equals(mPassword.getText().toString())) // �ж� �ʺź�����
		{
			new AlertDialog.Builder(Login.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("��¼����").setMessage("΢���ʺŻ������벻��Ϊ�գ�\n��������ٵ�¼��")
					.create().show();
		} else {

			new AlertDialog.Builder(Login.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("��¼ʧ��").setMessage("΢���ʺŻ������벻��ȷ��\n������������룡")
					.create().show();
		}

		// ��¼��ť
		/*
		 * Intent intent = new Intent();
		 * intent.setClass(Login.this,Whatsnew.class); startActivity(intent);
		 * Toast.makeText(getApplicationContext(), "��¼�ɹ�",
		 * Toast.LENGTH_SHORT).show(); this.finish();
		 */
	}

	public void login_back(View v) { // ������ ���ذ�ť
		this.finish();
	}

	public void login_pw(View v) { // �������밴ť
		Uri uri = Uri.parse("http://3g.qq.com");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
		// Intent intent = new Intent();
		// intent.setClass(Login.this,Whatsnew.class);
		// startActivity(intent);
	}

	private class LoginThread implements Runnable {

		@Override
		public void run() {
			try {
				JSONObject data = new JSONObject();
				data.put("username", username);
				data.put("password", password);
				JSONObject result = DataUtil.http(data,
						ConstantUtil.LOGIN_METHOD);
				System.out.println("result=---------" + result.toString());
				if ("1".equals(result.getString("head"))) {
					application.setUsername(username);
					application.setPassword(password);
					application.setUserId(result.getString("body"));
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
					
				} else {
					Message message = new Message();
					message.what = 0;
					handler.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
				showToastMessage(context, "����������ʧ�ܣ�");
				Utils.cancelProgress();
			}

		}

	}
	private void showToastMessage(Context context, String msg) {
		Message message = new Message();
		message.obj = msg;
		message.what = 10;
		handler.sendMessage(message);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Utils.cancelProgress();
				
				Intent intent = new Intent();
				intent.setClass(Login.this, MainWeixin.class);
				startActivity(intent);
				break;
			case 0:
				break;
			case 10:
				Toast.makeText(context, msg.obj.toString(), 8000).show();
				break;
			default:
				break;
			}
		}

	};

}
