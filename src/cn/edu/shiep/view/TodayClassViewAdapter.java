package cn.edu.shiep.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.buaa.myweixin.AppSystem;
import cn.buaa.myweixin.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TodayClassViewAdapter extends BaseAdapter {

	private Context mContext = null;
	private JSONObject mJsonObject = null;
	private JSONArray mJsonArray = null;

	private static class ItemViewCache {
		public TextView imageView_number;
		public TextView textView_className;
		public TextView textView_teacherName;
		public TextView textView_time;
	}

	private AppSystem appSystem;
	public TodayClassViewAdapter(Context mContext, JSONObject mJsonObject,AppSystem appSystem) {
		this.mContext = mContext;
		this.mJsonObject = mJsonObject;
		this.appSystem = appSystem;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewCache viewCache = null;
		try {
			if (mJsonArray != null) {
				JSONObject data = (JSONObject) mJsonArray.get(position);
				if (convertView == null) {
					convertView = LayoutInflater.from(mContext).inflate(
							R.layout.today_class_item, null);
					viewCache = new ItemViewCache();
					viewCache.imageView_number = (TextView) convertView
							.findViewById(R.id.textView_number);
					viewCache.textView_className = (TextView) convertView
							.findViewById(R.id.textView_className);
					viewCache.textView_teacherName = (TextView) convertView
							.findViewById(R.id.textView_teacherName);
					viewCache.textView_time = (TextView) convertView
							.findViewById(R.id.textView_time);
					convertView.setTag(viewCache);
				} else {
					viewCache = (ItemViewCache) convertView.getTag();
				}
				String number = data.getString("classTimeNo");
				String className = data.getString("className");
				String teacherName = data.getString("teacherOfClass");
				String address = data.getString("classAddress");
				if(number!=null&&!"".equals(number)){
					viewCache.imageView_number.setText(number);
					viewCache.textView_className.setText("课程:"+className);
					viewCache.textView_teacherName.setText("老师"+teacherName);
					viewCache.textView_time.setText("地点:"+address);
				}
				
				//viewCache.textView_className.setBackgroundColor(Color.RED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	

}
