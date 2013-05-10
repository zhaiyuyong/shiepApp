package cn.buaa.myweixin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;
import cn.edu.shiep.view.AllClassViewAdapter;


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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ClassListActivity extends Activity{

	private Context context = null;
	private ProgressBar progressBar = null;
	private ListView listView = null;

	private AllClassViewAdapter adapter = null;
	private JSONObject mJsonObject = null;
	private int count = 0;
	private TextView textView;
	private EditText et_searchmessage;
	String searchName = "";
	private Button btn_search;
	
	private Button add_class_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tab_address);
		this.context = this;
		add_class_btn = (Button) findViewById(R.id.add_class_btn);
		add_class_btn.setVisibility(View.INVISIBLE);
		et_searchmessage = (EditText) findViewById(R.id.et_searchmessage);
		listView = (ListView) findViewById(R.id.class_list);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		textView = (TextView) findViewById(R.id.none_data);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				searchName = et_searchmessage.getText().toString().trim();
				ThreadPoolUtils.execute(new MyThread());
			}
		});
		adapter = new AllClassViewAdapter(context, mJsonObject,1,(AppSystem)this.getApplication());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyOnItemClickListener());
		ThreadPoolUtils.execute(new MyThread());
		
	}
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(mJsonObject!=null){
				adapter.notifyDataSetChanged();
			}
			if (count == 0) {
				textView.setVisibility(View.VISIBLE);
			} else {
				textView.setVisibility(View.GONE);
			}
			progressBar.setVisibility(View.GONE);
		}
		
	};
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			TextView className = (TextView) view.findViewById(R.id.name_class_tv_new);
			TextView classId = (TextView) view.findViewById(R.id.class_id);
			Intent intent = new Intent();
			intent.putExtra("uuid", classId.getText().toString().trim());
			intent.putExtra("className", className.getText().toString()
					.trim());
			ClassListActivity.this.setResult(2, intent);
			ClassListActivity.this.finish();
		
		}
		
	}
	private class MyThread implements Runnable{

		@Override
		public void run() {
			try {
				JSONObject params = new JSONObject();
				params.put("searchName", searchName);
				mJsonObject = DataUtil.http(params, "allClass");
				//mJsonObject = result();
				try {
					count = Integer.parseInt(mJsonObject.getString("recordamount"));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}catch (JSONException e) {
					e.printStackTrace();
				}
				adapter.setmJsonObject(mJsonObject);
				Message msg = mHandler.obtainMessage();
				mHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static JSONObject result(){
		JSONObject result = new JSONObject();
		
		try {
			
			result.put("recordamount", "10");
			JSONArray rclist = new JSONArray();
			for(int i=0;i<10;i++){
				JSONObject a = new JSONObject();
				a.put("name_class_tv", "¿Î³Ì£º¸ßÊý"+i);
				a.put("classId", i+"00");
				rclist.put(i,a);
			}
			System.out.println("--------------"+rclist.toString());
			result.put("rclist", rclist);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("uuid", "");
		intent.putExtra("classId", "");
		ClassListActivity.this.setResult(1, intent);
		ClassListActivity.this.finish();
	}

	

}
