package cn.buaa.myweixin;

import org.json.JSONException;
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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClassDetailActivity extends Activity{
	private Button login_reback_btn;
	private Context context;

	private String classId;
	
	private JSONObject mJsonObject;
	
	private TextView id_of_class;
	private TextView class_address_show;
	private TextView class_time_show;
	private TextView teacher_name_show;
	private TextView class_name_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.class_detail);
		Intent intent = this.getIntent();
		classId = intent.getStringExtra("classId");
		login_reback_btn = (Button) findViewById(R.id.login_reback_btn);
		login_reback_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ClassDetailActivity.this.finish();
				
			}
		});
		id_of_class = (TextView) findViewById(R.id.id_of_class);
		class_address_show = (TextView) findViewById(R.id.class_address_show);
		class_time_show = (TextView) findViewById(R.id.class_time_show);
		teacher_name_show = (TextView) findViewById(R.id.teacher_name_show);
		class_name_show = (TextView) findViewById(R.id.class_name_show);
		context = this;
		Utils.showProgress(context, "加载数据中.....");
		ThreadPoolUtils.execute(new DetailClassThread());
	}
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Utils.cancelProgress();
				copyData();
				break;
			case 0:
				Utils.cancelProgress();
				Toast.makeText(context, "网络出现故障", Toast.LENGTH_SHORT).show();
				ClassDetailActivity.this.finish();
				break;
			default:
				break;
			}
		}
		
	};
	private class DetailClassThread implements Runnable{
		@Override
		public void run() {
			try {
				JSONObject params = new JSONObject();
				params.put("classId", classId);
				mJsonObject = DataUtil.http(params, "detailOkClass");
				if("1".equals(mJsonObject.getString("head"))){
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
				}else {
					Message message = new Message();
					message.what = 0;
					mHandler.sendMessage(message);
				}
			} catch (Exception e) {
				Message message = new Message();
				message.what = 0;
				mHandler.sendMessage(message);
				e.printStackTrace();
			}
			
		}
		
	}
	protected void copyData() {
		if(mJsonObject!=null){
			try {
				JSONObject data = mJsonObject.getJSONObject("data");
				String idOfClass = data.getString("idOfClass");
				String teacherOfClass = data.getString("teacherOfClass");
				String classAddress =  data.getString("classAddress");
				String className = data.getString("className");
				String classTimeNo = data.getString("classTimeNo");
				 id_of_class.setText(idOfClass);
				 class_address_show.setText(classAddress);
				 class_time_show.setText(classTimeNo);
				 teacher_name_show.setText(teacherOfClass);
				 class_name_show.setText(className);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	

}
