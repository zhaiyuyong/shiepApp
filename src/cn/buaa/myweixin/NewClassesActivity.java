package cn.buaa.myweixin;

import org.json.JSONObject;


import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;
import cn.edu.shiep.utils.Utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class NewClassesActivity extends Activity{

	private EditText name_of_class;
	private Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_classes_add);
		
		context = this;
		name_of_class = (EditText) findViewById(R.id.name_of_class);
	}

	public void btn_addNewClasses(View v){
		Animation myAlphaAction = AnimationUtils.loadAnimation(context,
				R.anim.alpha_action);
		v.startAnimation(myAlphaAction);
		String nameOfClass = name_of_class.getText().toString().trim();
		Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
		if("".equals(nameOfClass)){
			name_of_class.startAnimation(shake);
			name_of_class.requestFocus();
			return;
		}
		
		Utils.showProgress(context, "正在进行增加老师，请稍后...");
		ThreadPoolUtils.execute(new AddClassesThread());
		
	}
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(context, "添加成功！",Toast.LENGTH_LONG).show();
				Utils.cancelProgress();
				NewClassesActivity.this.finish();
				break;
			case 2:
				Toast.makeText(context, "该课程已经存在！",Toast.LENGTH_LONG).show();
				Utils.cancelProgress();
				name_of_class.setText("");
				break;
			case 0:
				Toast.makeText(context, "发生未知异常!添加课程失败,请稍后再试....",
						Toast.LENGTH_LONG).show();
				Utils.cancelProgress();
				break;
			default:
				break;
			}
		}
		
	};
	
	private class AddClassesThread implements Runnable{

		@Override
		public void run() {
			JSONObject params = new JSONObject();
			try {
				params.put("nameOfClass", name_of_class.getText().toString().trim());
				JSONObject resultData = DataUtil.http(params, "addClasses");
				if("1".equals(resultData.getString("head"))){
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
				}else if("0".equals(resultData.getString("head"))){
					Message message = new Message();
					message.what = 0;
					mHandler.sendMessage(message);
				}else if("2".equals(resultData.getString("head"))){
					Message message = new Message();
					message.what = 2;
					mHandler.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Message message = new Message();
				message.what = 0;
				mHandler.sendMessage(message);
			}
			
		}
		
	}
	
	public void btn_back(View v){
		this.finish();
	}
}
