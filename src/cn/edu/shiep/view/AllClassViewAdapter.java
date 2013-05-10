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

public class AllClassViewAdapter extends BaseAdapter {

	private Context mContext = null;
	private JSONObject mJsonObject = null;
	private JSONArray mJsonArray = null;
	private int callCode;
	private AppSystem appSystem;

	private static class ItemViewCache {
		public TextView classId;
		public TextView name_class_tv;

	}

	public AllClassViewAdapter(Context mContext, JSONObject mJsonObject,
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewCache viewCache = null;
		try {
			if (mJsonArray != null) {
				JSONObject data = (JSONObject) mJsonArray.get(position);
				//System.out.println("data===" + data);
				if (convertView == null) {
					//System.out.println("-------------convertView == null");
					convertView = LayoutInflater.from(mContext).inflate(R.layout.main_tab_address_item, null);
					//System.out.println("-------------convertView == null------"+(convertView == null)+"++++"+(mContext==null));
					viewCache = new ItemViewCache();

					viewCache.name_class_tv = (TextView) convertView.findViewById(R.id.name_class_tv_new);
					//System.out.println("inner===viewCache.name_class_tv"+(viewCache.name_class_tv==null));
					viewCache.classId = (TextView) convertView
							.findViewById(R.id.class_id);
					convertView.setTag(viewCache);
				}
				viewCache = (ItemViewCache) convertView.getTag();

				String name_class_tv = data.getString("className");
				String classId = data.getString("classId");
				//System.out.println("viewCache.name_class_tv==null"+(viewCache.name_class_tv==null));
				viewCache.name_class_tv.setText(name_class_tv+"");
				viewCache.classId.setText(classId+"");
				return convertView;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

}
