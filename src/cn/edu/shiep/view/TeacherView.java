package cn.edu.shiep.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.buaa.myweixin.AppSystem;
import cn.buaa.myweixin.R;
import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ConstantUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherView {

	private Context context = null;
	private View view = null;
	private ProgressBar progressBar = null;
	private ListView listView = null;
	private JSONObject mJsonObject = null;
	private int count = 0;
	private TextView textView;
	private EditText et_searchmessage;
	String searchName = "";
	private Button btn_search;
	
	private Button add_teacher_btn;

	private TeacherViewAdapter adapter = null;

	private MyHander myHander = null;

	private AppSystem appSystem;
	public TeacherView(Context context,AppSystem appSystem) {
		this.context = context;
		this.appSystem = appSystem;
	}

	public View getView() {
		view = View.inflate(context, R.layout.main_tab_friends, null);
		listView = (ListView) view.findViewById(R.id.class_list);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		textView = (TextView) view.findViewById(R.id.none_data);
		et_searchmessage = (EditText)view.findViewById(R.id.et_searchmessage);
		btn_search = (Button) view.findViewById(R.id.btn_search);
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
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Toast.makeText(context, ((TextView)view.findViewById(R.id.teacher_name_new)).getText().toString().trim(), Toast.LENGTH_SHORT).show();

			}

		});

		myHander = new MyHander();
		ThreadPoolUtils.execute(new TeacherThread());
		
		add_teacher_btn = (Button) view.findViewById(R.id.add_teacher_btn);
		if("admin".equals(appSystem.getUsername())){
			add_teacher_btn.setVisibility(View.VISIBLE);
		}else {
			add_teacher_btn.setVisibility(View.GONE);
		}
		return view;
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
				//mJsonObject = result();
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

	public static JSONObject result() {
		JSONObject result = new JSONObject();

		try {

			result.put("recordamount", "10");
			JSONArray rclist = new JSONArray();
			for (int i = 0; i < 10; i++) {
				JSONObject a = new JSONObject();
				a.put("teacherName", "¿Î³Ì£º¸ßÊý" + i);
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
