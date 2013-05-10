package cn.edu.shiep.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.buaa.myweixin.R;





import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassVIewAdapter extends BaseAdapter{
	private Context mContext = null;
	private JSONObject mJsonObject = null;
	private JSONArray mJsonArray = null;
	private int dayOfWeek;
	
	private static class ItemViewCache{
		public ImageView iv_userhead;
		public TextView tv_chatcontent;
		public TextView tv_username;
		public TextView class_time_no;
		public TextView id_of_class;
	}
	

	public ClassVIewAdapter(Context mContext, JSONObject mJsonObject,
			int dayOfWeek) {
		this.mContext = mContext;
		this.mJsonObject = mJsonObject;
		this.dayOfWeek = dayOfWeek;
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
							R.layout.what_new_item, null);
					viewCache = new ItemViewCache();
					viewCache.iv_userhead = (ImageView) convertView.findViewById(R.id.iv_userhead);
					viewCache.tv_chatcontent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
					viewCache.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
					viewCache.class_time_no = (TextView) convertView.findViewById(R.id.class_time_no);
					viewCache.id_of_class = (TextView) convertView.findViewById(R.id.id_of_class);
					convertView.setTag(viewCache);
				}else {
					viewCache = (ItemViewCache) convertView.getTag();
				}
				String tv_chatcontent = data.getString("className");
				String tv_username = data.getString("teacherOfClass");
				String class_time_no = data.getString("classTimeNo");
				String id_of_class = data.getString("idOfClass");
				String address = data.getString("classAddress");
				if(id_of_class!=null&&!"".equals(id_of_class)){
				viewCache.tv_chatcontent.setText("课程："+tv_chatcontent);
				viewCache.tv_username.setText("老师:"+tv_username+"   教室:"+address);
				viewCache.class_time_no.setText(class_time_no);
				viewCache.id_of_class.setText(id_of_class);
				}
			}
		} catch (Exception e) {
		}
		return convertView;
	}

	

}
