package cn.edu.shiep.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.buaa.myweixin.AppSystem;
import cn.buaa.myweixin.ClassDetailActivity;
import cn.buaa.myweixin.R;
import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AllClassView {

	private Context context = null;
	private View view = null;
	private ProgressBar progressBar = null;
	private ListView listView = null;

	private AllClassViewAdapter adapter = null;
	private JSONObject mJsonObject = null;
	private int count = 0;
	private TextView textView;
	private EditText et_searchmessage;
	String searchName = "";
	private Button btn_search;
	
	private AppSystem appSystem;
	
	private Button add_class_btn;

	public AllClassView(Context context,AppSystem appSystem) {
		this.appSystem = appSystem;
		
		this.context = context;
	}

	public View getView() {
		view = View.inflate(context, R.layout.main_tab_address, null);
		et_searchmessage = (EditText) view.findViewById(R.id.et_searchmessage);
		listView = (ListView) view.findViewById(R.id.class_list);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		textView = (TextView) view.findViewById(R.id.none_data);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				searchName = et_searchmessage.getText().toString().trim();
				ThreadPoolUtils.execute(new MyThread());
			}
		});
		adapter = new AllClassViewAdapter(context, mJsonObject,0,appSystem);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyOnItemClickListener());
		ThreadPoolUtils.execute(new MyThread());
		
		add_class_btn = (Button) view.findViewById(R.id.add_class_btn);
		if("admin".equals(appSystem.getUsername())){
			add_class_btn.setVisibility(View.VISIBLE);
		}else {
			add_class_btn.setVisibility(View.GONE);
		}
		return view;
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
			TextView textView = (TextView) view.findViewById(R.id.name_class_tv_new);
			String name_class_tv = textView.getText().toString().trim();
			Toast.makeText(context, name_class_tv, Toast.LENGTH_LONG).show();
//			System.out.println("-------MyOnItemClickListener------------");
//			Intent intent = new Intent(context, ClassDetailActivity.class);
//			context.startActivity(intent);
		}
		
	}
	private class MyThread implements Runnable{

		@Override
		public void run() {
			try {
				JSONObject params = new JSONObject();
				params.put("searchName", searchName);
				mJsonObject = DataUtil.http(params, "allClass");
				System.out.println("mJsonObject==="+mJsonObject);
//				if("222".equals(searchName)){
//					mJsonObject = result1();
//				}else {
//					mJsonObject = result();
//				}
				
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
				a.put("className", "¿Î³Ì£º¸ßÊý"+i);
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
	public static JSONObject result1(){
		JSONObject result = new JSONObject();
		
		try {
			
			result.put("recordamount", "10");
			JSONArray rclist = new JSONArray();
			for(int i=0;i<10;i++){
				JSONObject a = new JSONObject();
				a.put("name_class_tv", "222£ºC++"+i);
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

}
