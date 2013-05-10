package cn.buaa.myweixin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ConstantUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;
import cn.edu.shiep.view.TeacherViewAdapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TeacherListActivity extends Activity {

	private Context context = null;
	private ProgressBar progressBar = null;
	private ListView listView = null;
	private JSONObject mJsonObject = null;
	private int count = 0;
	private TextView textView;
	private EditText et_searchmessage;
	String searchName = "";
	private Button btn_search;

	private TeacherViewAdapter adapter = null;

	private MyHander myHander = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = this;
		setContentView(R.layout.main_tab_friends);
		listView = (ListView) findViewById(R.id.class_list);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		textView = (TextView) findViewById(R.id.none_data);
		btn_search = (Button) findViewById(R.id.btn_search);
		et_searchmessage = (EditText)findViewById(R.id.et_searchmessage);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchName = et_searchmessage.getText().toString().trim();
				ThreadPoolUtils.execute(new TeacherThread());
			}
		});

		adapter = new TeacherViewAdapter(context, mJsonObject);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView teacherId = (TextView) arg1
						.findViewById(R.id.teacher_id);
				TextView teacherName = (TextView) arg1
						.findViewById(R.id.teacher_name_new);
				Intent intent = new Intent();
				intent.putExtra("uuid", teacherId.getText().toString().trim());
				intent.putExtra("teacherName", teacherName.getText().toString()
						.trim());
				TeacherListActivity.this.setResult(1, intent);
				TeacherListActivity.this.finish();
			}

		});
		myHander = new MyHander();
		ThreadPoolUtils.execute(new TeacherThread());

	}

	private class MyHander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (mJsonObject != null) {
				adapter.notifyDataSetChanged();
			}
			if (count == 0) {
				textView.setVisibility(View.VISIBLE);
			} else {
				textView.setVisibility(View.GONE);
			}
			progressBar.setVisibility(View.GONE);
		}

	}

	private class TeacherThread implements Runnable {

		@Override
		public void run() {
			try {
				JSONObject params = new JSONObject();
				params.put("teacherName", searchName);
				mJsonObject = DataUtil.http(params, ConstantUtil.TEACHER_LIST);
				// mJsonObject = result();
				try {
					count = Integer.parseInt(mJsonObject
							.getString("recordamount"));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				adapter.setmJsonObject(mJsonObject);
				Message msg = myHander.obtainMessage();
				myHander.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("uuid", "");
		intent.putExtra("classId", "");
		TeacherListActivity.this.setResult(1, intent);
		TeacherListActivity.this.finish();
	}
	
	public static JSONObject result() {
		JSONObject result = new JSONObject();

		try {

			result.put("recordamount", "10");
			JSONArray rclist = new JSONArray();
			for (int i = 0; i < 10; i++) {
				JSONObject a = new JSONObject();
				a.put("teacherName", "cxy" + i);
				a.put("teacherId", i+"00");
				rclist.put(i, a);
			}
			System.out.println("--------------" + rclist.toString());
			result.put("rclist", rclist);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	

}
