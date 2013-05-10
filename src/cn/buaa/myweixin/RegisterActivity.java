package cn.buaa.myweixin;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;
import cn.edu.shiep.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 
 * @author zhaiyuyong@126.com
 * @version 1.0.0
 *
 */
public class RegisterActivity extends Activity{

	private Button register_btn;
	private EditText register_user_edit;
	private EditText register_passwd_edit1;
	private EditText register_passwd_edit2;
	
	private String userName;
	private String password1;
	private String password2;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_user);
		context = this;
		register_btn = (Button) findViewById(R.id.register_btn);
		register_user_edit = (EditText) findViewById(R.id.register_user_edit);
		register_passwd_edit1 = (EditText) findViewById(R.id.register_passwd_edit1);
		register_passwd_edit2 = (EditText) findViewById(R.id.register_passwd_edit2);
		register_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Animation myAlphaAction = AnimationUtils.loadAnimation(RegisterActivity.this,
						R.anim.alpha_action);
				v.startAnimation(myAlphaAction);
				if(checkForm()){
					Utils.showProgress(context, "正在进行注册，请稍后...");
					ThreadPoolUtils.execute(new RegisterThread());
				}
				
			}
		});
		
	}
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
				Utils.cancelProgress();
				RegisterActivity.this.finish();
				break;
			case 2:
				Toast.makeText(context, "该用户已经存在", Toast.LENGTH_SHORT).show();
				Utils.cancelProgress();
				register_user_edit.setText("");
				register_passwd_edit1.setText("");
				register_passwd_edit2.setText("");
				break;
			case 0:
				Toast.makeText(context, "网络发生故障、请稍后再试", Toast.LENGTH_SHORT).show();
				Utils.cancelProgress();
				break;
			default:
				break;
			}
		}
		
	};
	private class RegisterThread implements Runnable{
		@Override
		public void run() {
			try {
				JSONObject params = new JSONObject();
				params.put("userName", userName);
				params.put("password", password1);
				JSONObject result = DataUtil.http(params, "register");
				if("1".equals(result.getString("head"))){
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
				}else if("2".equals(result.getString("head"))){
					Message message = new Message();
					message.what = 2;
					mHandler.sendMessage(message);
				}else if("0".equals(result.getString("head"))){
					Message message = new Message();
					message.what = 0;
					mHandler.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	private boolean checkForm() {
		Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this,R.anim.shake);
		userName = register_user_edit.getText().toString().trim();
		password1 = register_passwd_edit1.getText().toString().trim();
		password2 = register_passwd_edit2.getText().toString().trim();
		if("".equals(userName)){
			register_user_edit.startAnimation(shake);
			register_user_edit.requestFocus();
			return false;
		}else if("".equals(password1)){
			register_passwd_edit1.startAnimation(shake);
			register_passwd_edit1.requestFocus();
			return false;
		}else if("".equals(password2)){
			register_passwd_edit2.startAnimation(shake);
			register_passwd_edit2.requestFocus();
			return false;
		}else if(!password1.equals(password2)){
			register_passwd_edit1.startAnimation(shake);
			register_passwd_edit2.startAnimation(shake);
			register_passwd_edit2.requestFocus();
			return false;
		}
		return true;
	}
	
	
	
	public void register_back(View v){
		this.finish();
	}

	
}
