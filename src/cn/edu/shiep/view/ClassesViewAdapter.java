package cn.edu.shiep.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.buaa.myweixin.AppSystem;
import cn.buaa.myweixin.R;
import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;
import cn.edu.shiep.utils.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClassesViewAdapter extends BaseAdapter {

	private Context mContext = null;
	private JSONObject mJsonObject = null;
	private JSONArray mJsonArray = null;
	private int callCode;
	private AppSystem appSystem;

	private static class ItemViewCache {

		public TextView teacher_class_id;
		public TextView day_of_week_id;
		public Button is_add_delete;
		public TextView teacher_and_class;
		public TextView time_and_address;

	}

	public ClassesViewAdapter(Context mContext, JSONObject mJsonObject,
			int callCode, AppSystem appSystem) {
		this.appSystem = appSystem;
		this.callCode = callCode;
		this.mContext = mContext;
		this.mJsonObject = mJsonObject;
		if (mJsonObject != null) {
			jsonObject2JsonArray(mJsonObject);
		}

	}

	public void setmJsonObject(JSONObject mJsonObject) {
		this.mJsonObject = mJsonObject;
		jsonObject2JsonArray(mJsonObject);
	}

	private void jsonObject2JsonArray(JSONObject mJsonObject2) {
		try {
			if (mJsonObject != null)
				mJsonArray = mJsonObject.getJSONArray("rclist");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		int count = 0;
		if (mJsonObject != null) {
			try {
				count = Integer.parseInt(mJsonObject.getString("recordamount"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		Object obj = null;
		try {
			if (mJsonArray != null)
				obj = mJsonArray.get(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewCache viewCache = null;
		try {
			if (mJsonArray != null) {
				JSONObject data = mJsonArray.getJSONObject(position);
				System.out.println("data====" + data);
				if (convertView == null) {
					convertView = LayoutInflater.from(mContext).inflate(
							R.layout.main_tab_setting_item, null);
					viewCache = new ItemViewCache();
					viewCache.teacher_class_id = (TextView) convertView
							.findViewById(R.id.teacher_class_id);
					System.out.println("viewCache.teacher_class_id====="+(viewCache.teacher_class_id==null));
					viewCache.day_of_week_id = (TextView) convertView.findViewById(R.id.day_of_week_id);
					viewCache.is_add_delete = (Button) convertView.findViewById(R.id.is_add_delete_setting);
					viewCache.teacher_and_class = (TextView) convertView.findViewById(R.id.teacher_and_class);
					viewCache.time_and_address = (TextView) convertView.findViewById(R.id.time_and_address);
					convertView.setTag(viewCache);
				} else {
					viewCache = (ItemViewCache) convertView.getTag();
				}
				viewCache.teacher_class_id.setText(data.getString("idOfClass"));
				viewCache.day_of_week_id.setText(data.getString("dayOfWeek"));
				viewCache.teacher_and_class.setText("老师:"+data.getString("teacherOfClass")+"  教室:"+data.getString("classAddress"));
				viewCache.time_and_address.setText("课程:"+data.getString("className")+"  时间:"+data.getString("classTimeNo"));
				/*viewCache.teacher_class_content.setText("星期:"+data.getString("dayOfWeek")+"  "+data.getString("classTimeNo")+"节课在："+data.getString("classAddress")
						+data.getString("teacherOfClass")+"老师上："+ data.getString("className")+"课");*/
				String isAddDelete = data.getString("isAddDelete");

				if (callCode == 1) {
					viewCache.is_add_delete.setVisibility(View.INVISIBLE);
				} else {
					if ("1".equals(isAddDelete)) {
						viewCache.is_add_delete.setText("删除");
					} else if ("0".equals(isAddDelete)) {
						viewCache.is_add_delete.setText("增加");
					}
					viewCache.is_add_delete.setVisibility(View.VISIBLE);
					viewCache.is_add_delete.setTag(data.getString("idOfClass"));
					viewCache.is_add_delete
							.setOnClickListener(new MyOnClickListener(
									viewCache.is_add_delete));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	private class MyOnClickListener implements OnClickListener {

		Button add_delete;
		String userId;

		public MyOnClickListener(Button add_delete) {
			this.add_delete = add_delete;
		}

		private Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if ("增加".equals(add_delete.getText().toString().trim())) {
						add_delete.setText("删除");
					} else if ("删除".equals(add_delete.getText().toString()
							.trim())) {
						add_delete.setText("增加");
					}
					Toast.makeText(mContext, "操作成功！", Toast.LENGTH_SHORT)
							.show();
					Utils.cancelProgress();
					break;
				case 0:
					Toast.makeText(mContext, "操作失败！", Toast.LENGTH_SHORT)
							.show();
					Utils.cancelProgress();
					break;
				default:
					break;
				}
			}

		};

		@Override
		public void onClick(View v) {

			// Utils.showProgress(mContext, "正在进行操作，请稍后...");
			if ("增加".equals(add_delete.getText().toString().trim())) {
				userId = appSystem.getUserId();
				Toast.makeText(mContext,
						add_delete.getTag().toString() + "==" + userId,
						Toast.LENGTH_LONG).show();
				Utils.showProgress(mContext, "操作中...请稍后");
				ThreadPoolUtils.execute(new AddThread());
			} else if ("删除".equals(add_delete.getText().toString().trim())) {
				userId = appSystem.getUserId();
				Utils.showProgress(mContext, "操作中...请稍后");
				Toast.makeText(mContext,
						add_delete.getTag().toString() + "==" + userId,
						Toast.LENGTH_LONG).show();
				ThreadPoolUtils.execute(new DeleteThread());
			}

		}

		private class DeleteThread implements Runnable {

			@Override
			public void run() {

				JSONObject params = new JSONObject();
				try {
					params.put("teacherClassId", add_delete.getTag().toString()
							.trim());
					params.put("userId", userId);
					System.out.println("xueke params=" + params.toString());
					JSONObject resultData = DataUtil.http(params, "tuike");
					if ("1".equals(resultData.getString("head"))) {
						Message message = new Message();
						message.what = 1;
						mHandler.sendMessage(message);
					} else {
						Message message = new Message();
						message.what = 0;
						mHandler.sendMessage(message);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message message = new Message();
					message.what = 0;
					mHandler.sendMessage(message);
				}

			}

		}

		private class AddThread implements Runnable {

			@Override
			public void run() {

				JSONObject params = new JSONObject();
				try {
					params.put("userId", userId);
					params.put("teacherClassId", add_delete.getTag().toString()
							.trim());
					System.out.println("xueke params=" + params.toString());
					JSONObject resultData = DataUtil.http(params, "xuanke");
					if ("1".equals(resultData.getString("head"))) {
						Message message = new Message();
						message.what = 1;
						mHandler.sendMessage(message);
					} else {
						Message message = new Message();
						message.what = 0;
						mHandler.sendMessage(message);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message message = new Message();
					message.what = 0;
					mHandler.sendMessage(message);
				}

			}

		}

	}

}
