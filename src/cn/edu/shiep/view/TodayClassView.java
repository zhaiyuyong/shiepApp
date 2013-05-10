package cn.edu.shiep.view;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.buaa.myweixin.AppSystem;
import cn.buaa.myweixin.ChatActivity;
import cn.buaa.myweixin.MainWeixin;
import cn.buaa.myweixin.R;
import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TodayClassView {

	private Context context = null;
	private View view = null;
	private ProgressBar progressBar = null;
	private ListView listView = null;

	private TodayClassViewAdapter adapter = null;
	private JSONObject mJsonObject = null;
	private int count = 0;
	private TextView textView;
	


	private AppSystem appSystem;
	
	private Button show_week_class_btn;
	private Button show_day_class_btn;

	public TodayClassView(Context context,AppSystem appSystem) {
		this.context = context;
		this.appSystem = appSystem;
	}

	

	public View getView() {
		view = View.inflate(context, R.layout.main_tab_weixin, null);
		listView = (ListView) view.findViewById(R.id.today_class_list);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		textView = (TextView) view.findViewById(R.id.none_data);

		adapter = new TodayClassViewAdapter(context, mJsonObject,appSystem);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyOnItemClickListener());
		ThreadPoolUtils.execute(new TodayClassThread());
		show_day_class_btn = (Button) view.findViewById(R.id.show_day_class_btn);
		show_week_class_btn = (Button) view.findViewById(R.id.show_week_class_btn);
		if("admin".equals(appSystem.getUsername())){
			show_day_class_btn.setVisibility(View.GONE);
			show_week_class_btn.setVisibility(View.GONE);
		}else {
			show_day_class_btn.setVisibility(View.VISIBLE);
			show_week_class_btn.setVisibility(View.VISIBLE);
		}
		return view;
	}
	Handler mHandler = new Handler(){

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
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			TextView textView = (TextView) view.findViewById(R.id.textView_number);
			Intent intent = new Intent (context,ChatActivity.class);	
			intent.putExtra("id", textView.getText().toString().trim());
			context.startActivity(intent);
			
		}
		
	}

	private class TodayClassThread implements Runnable{

		@Override
		public void run() {
			try {
				JSONObject body = new JSONObject();
				body.put("userId", appSystem.getUserId());
				body.put("dayOfWeek", Calendar.DAY_OF_WEEK);
				mJsonObject = DataUtil.http(body, "todayClass");
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
				
			}
			
		}
		
		
	}
	public static JSONObject result(){
		JSONObject result = new JSONObject();
		
		try {
			
			result.put("recordamount", "6");
			JSONArray rclist = new JSONArray();
			for(int i=0;i<6;i++){
				JSONObject a = new JSONObject();
				a.put("number", (i+1)+"");
				a.put("className", "课程：高数"+i);
				a.put("teacherName", "授课老师：翟玉勇"+i);
				a.put("time", "2013-04-20");
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
