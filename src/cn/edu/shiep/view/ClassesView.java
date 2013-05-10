package cn.edu.shiep.view;

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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ClassesView {

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
	private AppSystem appSystem;
	private ClassesViewAdapter adapter;
	private Button add_teacher_class_btn;

	public ClassesView(Context context, AppSystem appSystem) {
		this.appSystem = appSystem;
		this.context = context;
	}

	public View getView() {
		view = View.inflate(context, R.layout.main_tab_settings, null);
		listView = (ListView) view.findViewById(R.id.class_list_ok_class);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		textView = (TextView) view.findViewById(R.id.none_data);
		et_searchmessage = (EditText) view.findViewById(R.id.et_searchmessage);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchName = et_searchmessage.getText().toString().trim();
				ThreadPoolUtils.execute(new MyThread());
			}
		});
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("文件操作");
			    // add context menu item
			    menu.add(0, 1, Menu.NONE, "发送");
			    menu.add(0, 2, Menu.NONE, "标记为重要");
			    menu.add(0, 3, Menu.NONE, "重命名");
			    menu.add(0, 4, Menu.NONE, "删除");
				
			}
		});
		
		
	   
		
		
		if ("admin".equals(appSystem.getUsername())) {
			adapter = new ClassesViewAdapter(context, mJsonObject, 1, appSystem);
		} else {
			adapter = new ClassesViewAdapter(context, mJsonObject, 0, appSystem);
		}

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyOnItemClickListener());

		ThreadPoolUtils.execute(new MyThread());

		add_teacher_class_btn = (Button) view
				.findViewById(R.id.add_teacher_class_btn);
		if ("admin".equals(appSystem.getUsername())) {
			add_teacher_class_btn.setVisibility(View.VISIBLE);
		} else {
			add_teacher_class_btn.setVisibility(View.GONE);
		}
		return view;
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			TextView teacher_class_id = (TextView) view
					.findViewById(R.id.teacher_class_id);
			String id = teacher_class_id.getText().toString().trim();
			Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(context, ClassDetailActivity.class);
			intent.putExtra("classId", id);
			context.startActivity(intent);
		}

	}

	private Handler mHandler = new Handler() {

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

	};

	private class MyThread implements Runnable {

		@Override
		public void run() {
			try {
				JSONObject params = new JSONObject();
				params.put("searchName", searchName);
				params.put("userId", appSystem.getUserId());
				System.out.println("mJsonObject===" + mJsonObject);
				mJsonObject = DataUtil.http(params, "allClasses");

				try {
					count = Integer.parseInt(mJsonObject
							.getString("recordamount"));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (JSONException e) {
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

}
