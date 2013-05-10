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

public class TeacherViewAdapter extends BaseAdapter{
	private Context mContext = null;
	private JSONObject mJsonObject = null;
	private JSONArray mJsonArray = null;
	
	private static class ItemViewCache{
		public TextView teacherId;
		public TextView teacherName;
		public TextView teacherJob;

	}
	

	public TeacherViewAdapter(Context mContext, JSONObject mJsonObject) {
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewCache viewCache = null;
		try {
			if(mJsonArray!=null){
				JSONObject data = (JSONObject) mJsonArray.get(position);
				if(convertView==null){
					convertView = LayoutInflater.from(mContext).inflate(
							R.layout.main_tab_friends_item, null);
					viewCache = new ItemViewCache();
					viewCache.teacherName = (TextView) convertView.findViewById(R.id.teacher_name_new);
					viewCache.teacherId = (TextView) convertView.findViewById(R.id.teacher_id);
					viewCache.teacherJob = (TextView) convertView.findViewById(R.id.teacher_job);
					convertView.setTag(viewCache);
				}else {
					viewCache = (ItemViewCache) convertView.getTag();
				}
				String teacherName = data.getString("teacherName");
				String teacherId = data.getString("teacherId");
				String teacherJob = data.getString("teacherJob");
				viewCache.teacherName.setText(teacherName);		
				viewCache.teacherId.setText(teacherId);
				viewCache.teacherJob.setText(teacherJob);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return convertView;
	}

	

}
