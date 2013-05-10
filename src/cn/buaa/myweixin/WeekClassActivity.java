package cn.buaa.myweixin;

import org.json.JSONArray;
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
import android.widget.TextView;
import android.widget.Toast;



public class WeekClassActivity  extends Activity{

	private Context context;
	private JSONObject mJsonObject = null;
	
	
	private TextView class_0_1 ,class_0_2, class_0_3, class_0_4, class_0_5;
	private TextView class_1_1 ,class_1_2, class_1_3, class_1_4, class_1_5;
	private TextView class_2_1 ,class_2_2, class_2_3, class_2_4, class_2_5;
	private TextView class_3_1 ,class_3_2, class_3_3, class_3_4, class_3_5;
	private TextView class_4_1 ,class_4_2, class_4_3, class_4_4, class_4_5;
	private TextView class_5_1 ,class_5_2, class_5_3, class_5_4, class_5_5;
	private TextView class_6_1 ,class_6_2, class_6_3, class_6_4, class_6_5;
	
	private MyOnClickListener myOnClickListener;
	private AppSystem appSystem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.week_class);
		context = this;
		myOnClickListener = new MyOnClickListener();
		init();
		appSystem = (AppSystem) this.getApplication();
		Utils.showProgress(context, "正在进行增加老师，请稍后...");
		ThreadPoolUtils.execute(new ClassThread());
		
	}
	
	private void init(){
		class_0_1 = (TextView) findViewById(R.id.class_0_1);
		class_0_1.setOnClickListener(myOnClickListener);
		class_0_2 = (TextView) findViewById(R.id.class_0_2);
		class_0_2.setOnClickListener(myOnClickListener);
		class_0_3 = (TextView) findViewById(R.id.class_0_3);
		class_0_3.setOnClickListener(myOnClickListener);
		class_0_4 = (TextView) findViewById(R.id.class_0_4);
		class_0_4.setOnClickListener(myOnClickListener);
		class_0_5 = (TextView) findViewById(R.id.class_0_5);
		class_0_5.setOnClickListener(myOnClickListener);
		
		class_1_1 = (TextView) findViewById(R.id.class_1_1);
		class_1_1.setOnClickListener(myOnClickListener);
		class_1_2 = (TextView) findViewById(R.id.class_1_2);
		class_1_2.setOnClickListener(myOnClickListener);
		class_1_3 = (TextView) findViewById(R.id.class_1_3);
		class_1_3.setOnClickListener(myOnClickListener);
		class_1_4 = (TextView) findViewById(R.id.class_1_4);
		class_1_4.setOnClickListener(myOnClickListener);
		class_1_5 = (TextView) findViewById(R.id.class_1_5);
		class_1_5.setOnClickListener(myOnClickListener);
		
		class_2_1 = (TextView) findViewById(R.id.class_2_1);
		class_2_1.setOnClickListener(myOnClickListener);
		class_2_2 = (TextView) findViewById(R.id.class_2_2);
		class_2_2.setOnClickListener(myOnClickListener);
		class_2_3 = (TextView) findViewById(R.id.class_2_3);
		class_2_3.setOnClickListener(myOnClickListener);
		class_2_4 = (TextView) findViewById(R.id.class_2_4);
		class_2_4.setOnClickListener(myOnClickListener);
		class_2_5 = (TextView) findViewById(R.id.class_2_5);
		class_2_5.setOnClickListener(myOnClickListener);
		
		class_3_1 = (TextView) findViewById(R.id.class_3_1);
		class_3_1.setOnClickListener(myOnClickListener);
		class_3_2 = (TextView) findViewById(R.id.class_3_2);
		class_3_2.setOnClickListener(myOnClickListener);
		class_3_3 = (TextView) findViewById(R.id.class_3_3);
		class_3_3.setOnClickListener(myOnClickListener);
		class_3_4 = (TextView) findViewById(R.id.class_3_4);
		class_3_4.setOnClickListener(myOnClickListener);
		class_3_5 = (TextView) findViewById(R.id.class_3_5);
		class_3_5.setOnClickListener(myOnClickListener);
		
		class_4_1 = (TextView) findViewById(R.id.class_4_1);
		class_4_1.setOnClickListener(myOnClickListener);
		class_4_2 = (TextView) findViewById(R.id.class_4_2);
		class_4_2.setOnClickListener(myOnClickListener);
		class_4_3 = (TextView) findViewById(R.id.class_4_3);
		class_4_3.setOnClickListener(myOnClickListener);
		class_4_4 = (TextView) findViewById(R.id.class_4_4);
		class_4_4.setOnClickListener(myOnClickListener);
		class_4_5 = (TextView) findViewById(R.id.class_4_5);
		class_4_5.setOnClickListener(myOnClickListener);
		
		class_5_1 = (TextView) findViewById(R.id.class_5_1);
		class_5_1.setOnClickListener(myOnClickListener);
		class_5_2 = (TextView) findViewById(R.id.class_5_2);
		class_5_2.setOnClickListener(myOnClickListener);
		class_5_3 = (TextView) findViewById(R.id.class_5_3);
		class_5_3.setOnClickListener(myOnClickListener);
		class_5_4 = (TextView) findViewById(R.id.class_5_4);
		class_5_4.setOnClickListener(myOnClickListener);
		class_5_5 = (TextView) findViewById(R.id.class_5_5);
		class_5_5.setOnClickListener(myOnClickListener);
		
		class_6_1 = (TextView) findViewById(R.id.class_6_1);
		class_6_1.setOnClickListener(myOnClickListener);
		class_6_2 = (TextView) findViewById(R.id.class_6_2);
		class_6_2.setOnClickListener(myOnClickListener);
		class_6_3 = (TextView) findViewById(R.id.class_6_3);
		class_6_3.setOnClickListener(myOnClickListener);
		class_6_4 = (TextView) findViewById(R.id.class_6_4);
		class_6_4.setOnClickListener(myOnClickListener);
		class_6_5 = (TextView) findViewById(R.id.class_6_5);
		class_6_5.setOnClickListener(myOnClickListener);
	
		
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				try {
					copyData();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Utils.cancelProgress();
				break;
			case 0:
				Toast.makeText(context, "发生未知异常,请稍后再试....",
						Toast.LENGTH_LONG).show();
				Utils.cancelProgress();
				break;
			default:
				break;
			}
		}
		
	};
	
	private void copyData() throws JSONException{
		JSONObject day1 =new JSONObject(mJsonObject.getString("body")).getJSONObject("day7");
	
		JSONObject record0_1 = day1.getJSONObject("record1");
		if(record0_1.getString("classTimeNo")==null||"".equals(record0_1.getString("classTimeNo"))){
			class_0_1.setText("");
		}else {
			class_0_1.setText("课程:\n"+record0_1.getString("className")+"\n"+"老师:\n"+record0_1.getString("teacherOfClass"));
			class_0_1.setTag(record0_1.getString("idOfClass"));
		}
		JSONObject record0_2 = day1.getJSONObject("record2");
		if(record0_2.getString("classTimeNo")==null||"".equals(record0_2.getString("classTimeNo"))){
			class_0_2.setText("");
		}else {
			class_0_2.setText("课程:\n"+record0_2.getString("className")+"\n"+"老师:\n"+record0_2.getString("teacherOfClass"));
			class_0_2.setTag(record0_2.getString("idOfClass"));
		}
		JSONObject record0_3 = day1.getJSONObject("record3");
		if(record0_3.getString("classTimeNo")==null||"".equals(record0_3.getString("classTimeNo"))){
			class_0_3.setText("");
		}else {
			class_0_3.setText("课程:\n"+record0_3.getString("className")+"\n"+"老师:\n"+record0_3.getString("teacherOfClass"));
			class_0_3.setTag(record0_3.getString("idOfClass"));
		}
		JSONObject record0_4 = day1.getJSONObject("record4");
		if(record0_4.getString("classTimeNo")==null||"".equals(record0_4.getString("classTimeNo"))){
			class_0_4.setText("");
		}else {
			class_0_4.setText("课程:\n"+record0_4.getString("className")+"\n"+"老师:\n"+record0_4.getString("teacherOfClass"));
			class_0_4.setTag(record0_4.getString("idOfClass"));
		}
		JSONObject record0_5 = day1.getJSONObject("record5");
		if(record0_5.getString("classTimeNo")==null||"".equals(record0_5.getString("classTimeNo"))){
			class_0_5.setText("");
		}else {
			class_0_5.setText("课程:\n"+record0_5.getString("className")+"\n"+"老师:\n"+record0_5.getString("teacherOfClass"));
			class_0_5.setTag(record0_5.getString("idOfClass"));
		}
		
		
		
		
		JSONObject day2 = new JSONObject(mJsonObject.getString("body")).getJSONObject("day1");
		JSONObject record1_1 = day2.getJSONObject("record1");
		if(record1_1.getString("classTimeNo")==null||"".equals(record1_1.getString("classTimeNo"))){
			class_1_1.setText("");
		}else {
			class_1_1.setText("课程:\n"+record1_1.getString("className")+"\n"+"老师:\n"+record1_1.getString("teacherOfClass"));
			class_1_1.setTag(record1_1.getString("idOfClass"));
		}
		JSONObject record1_2 = day2.getJSONObject("record2");
		if(record1_2.getString("classTimeNo")==null||"".equals(record1_2.getString("classTimeNo"))){
			class_1_2.setText("");
		}else {
			class_1_2.setText("课程:\n"+record1_2.getString("className")+"\n"+"老师:\n"+record1_2.getString("teacherOfClass"));
			class_1_2.setTag(record1_2.getString("idOfClass"));
		}
		JSONObject record1_3 = day2.getJSONObject("record3");
		if(record1_3.getString("classTimeNo")==null||"".equals(record1_3.getString("classTimeNo"))){
			class_1_3.setText("");
		}else {
			class_1_3.setText("课程:\n"+record1_3.getString("className")+"\n"+"老师:\n"+record1_3.getString("teacherOfClass"));
			class_1_3.setTag(record1_3.getString("idOfClass"));
		}
		JSONObject record1_4 = day2.getJSONObject("record4");
		if(record1_4.getString("classTimeNo")==null||"".equals(record1_4.getString("classTimeNo"))){
			class_1_4.setText("");
		}else {
			class_1_4.setText("课程:\n"+record1_4.getString("className")+"\n"+"老师:\n"+record1_4.getString("teacherOfClass"));
			class_1_4.setTag(record1_4.getString("idOfClass"));
		}
		JSONObject record1_5 = day2.getJSONObject("record5");
		if(record1_5.getString("classTimeNo")==null||"".equals(record1_5.getString("classTimeNo"))){
			class_1_5.setText("");
		}else {
			class_1_5.setText("课程:\n"+record1_5.getString("className")+"\n"+"老师:\n"+record1_5.getString("teacherOfClass"));
			class_1_5.setTag(record1_5.getString("idOfClass"));
		}
		
		JSONObject day3 = new JSONObject(mJsonObject.getString("body")).getJSONObject("day2");
		JSONObject record2_1 = day3.getJSONObject("record1");
		if(record2_1.getString("classTimeNo")==null||"".equals(record2_1.getString("classTimeNo"))){
			class_2_1.setText("");
		}else {
			class_2_1.setText("课程:\n"+record2_1.getString("className")+"\n"+"老师:\n"+record2_1.getString("teacherOfClass"));
			class_2_1.setTag(record2_1.getString("idOfClass"));
		}
		JSONObject record2_2 = day3.getJSONObject("record2");
		if(record2_2.getString("classTimeNo")==null||"".equals(record2_2.getString("classTimeNo"))){
			class_2_2.setText("");
		}else {
			class_2_2.setText("课程:\n"+record2_2.getString("className")+"\n"+"老师:\n"+record2_2.getString("teacherOfClass"));
			class_2_2.setTag(record2_2.getString("idOfClass"));
		}
		JSONObject record2_3 = day3.getJSONObject("record3");
		if(record2_3.getString("classTimeNo")==null||"".equals(record2_3.getString("classTimeNo"))){
			class_2_3.setText("");
		}else {
			class_2_3.setText("课程:\n"+record2_3.getString("className")+"\n"+"老师:\n"+record2_3.getString("teacherOfClass"));
			class_2_3.setTag(record2_3.getString("idOfClass"));
		}
		JSONObject record2_4 = day3.getJSONObject("record4");
		if(record2_4.getString("classTimeNo")==null||"".equals(record2_4.getString("classTimeNo"))){
			class_2_4.setText("");
		}else {
			class_2_4.setText("课程:\n"+record2_4.getString("className")+"\n"+"老师:\n"+record2_4.getString("teacherOfClass"));
			class_2_4.setTag(record2_4.getString("idOfClass"));
		}
		JSONObject record2_5 = day3.getJSONObject("record5");
		if(record2_5.getString("classTimeNo")==null||"".equals(record2_5.getString("classTimeNo"))){
			class_2_5.setText("");
		}else {
			class_2_5.setText("课程:\n"+record2_5.getString("className")+"\n"+"老师:\n"+record2_5.getString("teacherOfClass"));
			class_2_5.setTag(record2_5.getString("idOfClass"));
		}
		
		
		
		JSONObject day4 = new JSONObject(mJsonObject.getString("body")).getJSONObject("day3");
		JSONObject record3_1 = day4.getJSONObject("record1");
		if(record3_1.getString("classTimeNo")==null||"".equals(record3_1.getString("classTimeNo"))){
			class_3_1.setText("");
		}else {
			class_3_1.setText("课程:\n"+record3_1.getString("className")+"\n"+"老师:\n"+record3_1.getString("teacherOfClass"));
			class_3_1.setTag(record3_1.getString("idOfClass"));
		}
		JSONObject record3_2 = day4.getJSONObject("record2");
		if(record3_2.getString("classTimeNo")==null||"".equals(record3_2.getString("classTimeNo"))){
			class_3_2.setText("");
		}else {
			class_3_2.setText("课程:\n"+record3_2.getString("className")+"\n"+"老师:\n"+record3_2.getString("teacherOfClass"));
			class_3_2.setTag(record3_2.getString("idOfClass"));
		}
		JSONObject record3_3 = day4.getJSONObject("record3");
		if(record3_3.getString("classTimeNo")==null||"".equals(record3_3.getString("classTimeNo"))){
			class_3_3.setText("");
		}else {
			class_3_3.setText("课程:\n"+record3_3.getString("className")+"\n"+"老师:\n"+record3_3.getString("teacherOfClass"));
			class_3_3.setTag(record3_3.getString("idOfClass"));
		}
		JSONObject record3_4 = day4.getJSONObject("record4");
		if(record3_4.getString("classTimeNo")==null||"".equals(record3_4.getString("classTimeNo"))){
			class_3_4.setText("");
		}else {
			class_3_4.setText("课程:\n"+record3_4.getString("className")+"\n"+"老师:\n"+record3_4.getString("teacherOfClass"));
			class_3_4.setTag(record3_4.getString("idOfClass"));
		}
		JSONObject record3_5 = day4.getJSONObject("record5");
		if(record3_5.getString("classTimeNo")==null||"".equals(record3_5.getString("classTimeNo"))){
			class_3_5.setText("");
		}else {
			class_3_5.setText("课程:\n"+record3_5.getString("className")+"\n"+"老师:\n"+record3_5.getString("teacherOfClass"));
			class_3_5.setTag(record3_5.getString("idOfClass"));
		}
		
		
		JSONObject day5 = new JSONObject(mJsonObject.getString("body")).getJSONObject("day4");
		JSONObject record4_1 = day5.getJSONObject("record1");
		if(record4_1.getString("classTimeNo")==null||"".equals(record4_1.getString("classTimeNo"))){
			class_4_1.setText("");
		}else {
			class_4_1.setText("课程:\n"+record4_1.getString("className")+"\n"+"老师:\n"+record4_1.getString("teacherOfClass"));
			class_4_1.setTag(record4_1.getString("idOfClass"));
		}
		JSONObject record4_2 = day5.getJSONObject("record2");
		if(record4_2.getString("classTimeNo")==null||"".equals(record4_2.getString("classTimeNo"))){
			class_4_2.setText("");
		}else {
			class_4_2.setText("课程:\n"+record4_2.getString("className")+"\n"+"老师:\n"+record4_2.getString("teacherOfClass"));
			class_4_2.setTag(record4_2.getString("idOfClass"));
		}
		JSONObject record4_3 = day5.getJSONObject("record3");
		if(record4_3.getString("classTimeNo")==null||"".equals(record4_3.getString("classTimeNo"))){
			class_4_3.setText("");
		}else {
			class_4_3.setText("课程:\n"+record4_3.getString("className")+"\n"+"老师:\n"+record4_3.getString("teacherOfClass"));
			class_4_3.setTag(record4_3.getString("idOfClass"));
		}
		JSONObject record4_4 = day5.getJSONObject("record4");
		if(record4_4.getString("classTimeNo")==null||"".equals(record4_4.getString("classTimeNo"))){
			class_4_4.setText("");
		}else {
			class_4_4.setText("课程:\n"+record4_4.getString("className")+"\n"+"老师:\n"+record4_4.getString("teacherOfClass"));
			class_4_4.setTag(record4_4.getString("idOfClass"));
		}
		JSONObject record4_5 = day5.getJSONObject("record5");
		if(record4_5.getString("classTimeNo")==null||"".equals(record4_5.getString("classTimeNo"))){
			class_4_5.setText("");
		}else {
			class_4_5.setText("课程:\n"+record4_5.getString("className")+"\n"+"老师:\n"+record4_5.getString("teacherOfClass"));
			class_4_5.setTag(record4_5.getString("idOfClass"));
		}
		
		
		
		JSONObject day6 = new JSONObject(mJsonObject.getString("body")).getJSONObject("day5");
		JSONObject record5_1 = day6.getJSONObject("record1");
		if(record5_1.getString("classTimeNo")==null||"".equals(record5_1.getString("classTimeNo"))){
			class_5_1.setText("");
		}else {
			class_5_1.setText("课程:\n"+record5_1.getString("className")+"\n"+"老师:\n"+record5_1.getString("teacherOfClass"));
			class_5_1.setTag(record5_1.getString("idOfClass"));
		}
		JSONObject record5_2 = day6.getJSONObject("record2");
		if(record5_2.getString("classTimeNo")==null||"".equals(record5_2.getString("classTimeNo"))){
			class_5_2.setText("");
		}else {
			class_5_2.setText("课程:\n"+record5_2.getString("className")+"\n"+"老师:\n"+record5_2.getString("teacherOfClass"));
			class_5_2.setTag(record5_2.getString("idOfClass"));
		}
		JSONObject record5_3 = day6.getJSONObject("record3");
		if(record5_3.getString("classTimeNo")==null||"".equals(record5_3.getString("classTimeNo"))){
			class_5_3.setText("");
		}else {
			class_5_3.setText("课程:\n"+record5_3.getString("className")+"\n"+"老师:\n"+record5_3.getString("teacherOfClass"));
			class_5_3.setTag(record5_3.getString("idOfClass"));
		}
		JSONObject record5_4 = day6.getJSONObject("record4");
		if(record5_4.getString("classTimeNo")==null||"".equals(record5_4.getString("classTimeNo"))){
			class_5_4.setText("");
		}else {
			class_5_4.setText("课程:\n"+record5_4.getString("className")+"\n"+"老师:\n"+record5_4.getString("teacherOfClass"));
			class_5_4.setTag(record5_4.getString("idOfClass"));
		}
		JSONObject record5_5 = day6.getJSONObject("record5");
		if(record5_5.getString("classTimeNo")==null||"".equals(record5_5.getString("classTimeNo"))){
			class_5_5.setText("");
		}else {
			class_5_5.setText("课程:\n"+record5_5.getString("className")+"\n"+"老师:\n"+record5_5.getString("teacherOfClass"));
			class_5_5.setTag(record5_5.getString("idOfClass"));
		}
		
		
		
		JSONObject day7 = new JSONObject(mJsonObject.getString("body")).getJSONObject("day6");
		JSONObject record6_1 = day7.getJSONObject("record1");
		if(record6_1.getString("classTimeNo")==null||"".equals(record6_1.getString("classTimeNo"))){
			class_6_1.setText("");
		}else {
			class_6_1.setText("课程:\n"+record6_1.getString("className")+"\n"+"老师:\n"+record6_1.getString("teacherOfClass"));
			class_6_1.setTag(record6_1.getString("idOfClass"));
		}
		JSONObject record6_2 = day7.getJSONObject("record2");
		if(record6_2.getString("classTimeNo")==null||"".equals(record6_2.getString("classTimeNo"))){
			class_6_2.setText("");
		}else {
			class_6_2.setText("课程:\n"+record6_2.getString("className")+"\n"+"老师:\n"+record6_2.getString("teacherOfClass"));
			class_6_2.setTag(record6_2.getString("idOfClass"));
		}
		JSONObject record6_3 = day7.getJSONObject("record3");
		if(record6_3.getString("classTimeNo")==null||"".equals(record6_3.getString("classTimeNo"))){
			class_6_3.setText("");
		}else {
			class_6_3.setText("课程:\n"+record6_3.getString("className")+"\n"+"老师:\n"+record6_3.getString("teacherOfClass"));
			class_6_3.setTag(record6_3.getString("idOfClass"));
		}
		JSONObject record6_4 = day7.getJSONObject("record4");
		if(record6_4.getString("classTimeNo")==null||"".equals(record6_4.getString("classTimeNo"))){
			class_6_4.setText("");
		}else {
			class_6_4.setText("课程:\n"+record6_4.getString("className")+"\n"+"老师:\n"+record6_4.getString("teacherOfClass"));
			class_6_4.setTag(record6_4.getString("idOfClass"));
		}
		JSONObject record6_5 = day7.getJSONObject("record5");
		if(record6_5.getString("classTimeNo")==null||"".equals(record0_5.getString("classTimeNo"))){
			class_6_5.setText("");
		}else {
			class_6_5.setText("课程:\n"+record6_5.getString("className")+"\n"+"老师:\n"+record6_5.getString("teacherOfClass"));
			class_6_5.setTag(record6_5.getString("idOfClass"));
		}

	}

	private class ClassThread implements Runnable{
		@Override
		public void run() {
			try {
				JSONObject params = new JSONObject();
				params.put("userId", appSystem.getUserId());
				mJsonObject = DataUtil.http(params, "weekClass");
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
	private static JSONObject defaultData() throws JSONException{
		JSONObject reObject = new JSONObject();
		JSONObject day0 = new JSONObject();
		day0.put("1-2", "高数1");
		day0.put("3-4", "C++");
		day0.put("5-6", "java");
		day0.put("7-8", "c");
		day0.put("9-11", "选修课");
		reObject.put("day0", day0);
		reObject.put("day1", day0);
		reObject.put("day2", day0);
		reObject.put("day3", day0);
		reObject.put("day4", day0);
		reObject.put("day5", day0);
		reObject.put("day6", day0);
		return reObject;
	}
	
	public void btn_back(View v){
		this.finish();
	}
	
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String classId = (String) ((TextView)v).getTag();
			String b = ((TextView)v).getText().toString().trim();
			Toast.makeText(context, classId, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(WeekClassActivity.this, ClassDetailActivity.class);
			intent.putExtra("classId", classId);
			WeekClassActivity.this.startActivity(intent);
		}
		
	}
	

	



}
