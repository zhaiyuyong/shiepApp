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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ClassView {

	private Context context;
	private View view1, view2, view3, view4, view5, view6;
	private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4,
			progressBar5, progressBar6 = null;
	private ListView listView1, listView2, listView3, listView4, listView5,
			listView6 = null;
	private JSONObject mJsonObject1, mJsonObject2, mJsonObject3, mJsonObject4,
			mJsonObject5, mJsonObject6 = null;
	private int count1, count2, count3, count4, count5, count6 = 0;
	private ClassVIewAdapter adapter1, adapter2, adapter3, adapter4, adapter5,
			adapter6 = null;
	private TextView textView1, textView2, textView3, textView4, textView5,
			textView6;
	private MyOnItemClickListener itemClickListener = new MyOnItemClickListener();

	private AppSystem appSystem;
	public ClassView(Context context,AppSystem appSystem) {
		this.context = context;
		this.appSystem = appSystem;
	}

	public View getView(int dayOfWeek) {

		if (dayOfWeek == 1) {
			view1 = View.inflate(context, R.layout.whats1, null);
			listView1 = (ListView) view1.findViewById(R.id.list_class);
			adapter1 = new ClassVIewAdapter(context, mJsonObject1, dayOfWeek);

			progressBar1 = (ProgressBar) view1.findViewById(R.id.progressBar);
			textView1 = (TextView) view1.findViewById(R.id.none_data);
			new Thread(new MyThread(dayOfWeek)).start();
			listView1.setAdapter(adapter1);
			listView1.setOnItemClickListener(itemClickListener);

			return view1;
		} else if (dayOfWeek == 2) {
			view2 = View.inflate(context, R.layout.whats2, null);
			listView2 = (ListView) view2.findViewById(R.id.list_class);
			adapter2 = new ClassVIewAdapter(context, mJsonObject2, dayOfWeek);
			listView2.setAdapter(adapter2);
			progressBar2 = (ProgressBar) view2.findViewById(R.id.progressBar);
			textView2 = (TextView) view2.findViewById(R.id.none_data);
			new Thread(new MyThread(dayOfWeek)).start();
			 listView2.setOnItemClickListener(itemClickListener);
			return view2;
		} else if (dayOfWeek == 3) {
			view3 = View.inflate(context, R.layout.whats3, null);
			listView3 = (ListView) view3.findViewById(R.id.list_class);
			adapter3 = new ClassVIewAdapter(context, mJsonObject3, dayOfWeek);
			listView3.setAdapter(adapter3);
			progressBar3 = (ProgressBar) view3.findViewById(R.id.progressBar);
			textView3 = (TextView) view3.findViewById(R.id.none_data);
			 listView3.setOnItemClickListener(itemClickListener);
			new Thread(new MyThread(dayOfWeek)).start();
			return view3;
		} else if (dayOfWeek == 4) {
			view4 = View.inflate(context, R.layout.whats4, null);
			listView4 = (ListView) view4.findViewById(R.id.list_class);
			adapter4 = new ClassVIewAdapter(context, mJsonObject4, dayOfWeek);
			listView4.setAdapter(adapter4);
			progressBar4 = (ProgressBar) view4.findViewById(R.id.progressBar);
			textView4 = (TextView) view4.findViewById(R.id.none_data);
			new Thread(new MyThread(dayOfWeek)).start();
			 listView4.setOnItemClickListener(itemClickListener);
			return view4;
		} else if (dayOfWeek == 5) {
			view5 = View.inflate(context, R.layout.whats5, null);
			listView5 = (ListView) view5.findViewById(R.id.list_class);
			adapter5 = new ClassVIewAdapter(context, mJsonObject5, dayOfWeek);
			listView5.setAdapter(adapter5);
			progressBar5 = (ProgressBar) view5.findViewById(R.id.progressBar);
			textView5 = (TextView) view5.findViewById(R.id.none_data);
			 listView5.setOnItemClickListener(itemClickListener);
			new Thread(new MyThread(dayOfWeek)).start();
			return view5;
		} else if (dayOfWeek == 6) {
			view6 = View.inflate(context, R.layout.whats6, null);
			listView6 = (ListView) view6.findViewById(R.id.list_class);
			adapter6 = new ClassVIewAdapter(context, mJsonObject6, dayOfWeek);
			listView6.setAdapter(adapter6);
			progressBar6 = (ProgressBar) view6.findViewById(R.id.progressBar);
			textView6 = (TextView) view6.findViewById(R.id.none_data);
			 listView6.setOnItemClickListener(itemClickListener);
			new Thread(new MyThread(dayOfWeek)).start();
			return view6;
		} else {
			return null;
		}

	}

	private Handler mHandler1 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adapter1.notifyDataSetChanged();
			if (count1 == 0) {
				textView1.setVisibility(View.VISIBLE);
			} else {
				textView1.setVisibility(View.GONE);
			}
			progressBar1.setVisibility(View.GONE);
		}

	};
	private Handler mHandler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adapter2.notifyDataSetChanged();
			if (count2 == 0) {
				textView2.setVisibility(View.VISIBLE);
			} else {
				textView2.setVisibility(View.GONE);
			}
			progressBar2.setVisibility(View.GONE);
		}

	};
	private Handler mHandler3 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adapter3.notifyDataSetChanged();
			if (count3 == 0) {
				textView3.setVisibility(View.VISIBLE);
			} else {
				textView3.setVisibility(View.GONE);
			}
			progressBar3.setVisibility(View.GONE);
		}

	};
	private Handler mHandler4 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adapter4.notifyDataSetChanged();
			if (count4 == 0) {
				textView4.setVisibility(View.VISIBLE);
			} else {
				textView4.setVisibility(View.GONE);
			}
			progressBar4.setVisibility(View.GONE);
		}

	};
	private Handler mHandler5 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adapter5.notifyDataSetChanged();
			if (count5 == 0) {
				textView5.setVisibility(View.VISIBLE);
			} else {
				textView5.setVisibility(View.GONE);
			}
			progressBar5.setVisibility(View.GONE);
		}

	};
	private Handler mHandler6 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adapter6.notifyDataSetChanged();
			if (count6 == 0) {
				textView6.setVisibility(View.VISIBLE);
			} else {
				textView6.setVisibility(View.GONE);
			}
			progressBar6.setVisibility(View.GONE);
		}

	};

	private class MyThread implements Runnable {
		private int dayOfWeek;

		public MyThread(int dayOfWeek) {
			super();
			this.dayOfWeek = dayOfWeek;
		}

		@Override
		public void run() {
			try {
				JSONObject param = new JSONObject();
				param.put("dayOfWeek", this.dayOfWeek);
				param.put("userId",appSystem.getUserId() );
				if (dayOfWeek == 1) {
					mJsonObject1 = DataUtil.http(param, "todayClass");
					adapter1.setmJsonObject(mJsonObject1);
					try {
						count1 = Integer.parseInt(mJsonObject1
								.getString("recordamount"));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message msg = mHandler1.obtainMessage();
					mHandler1.sendMessage(msg);
				} else if (dayOfWeek == 2) {
					mJsonObject2 = DataUtil.http(param, "todayClass");
					adapter2.setmJsonObject(mJsonObject2);
					try {
						count2 = Integer.parseInt(mJsonObject2
								.getString("recordamount"));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message msg = mHandler2.obtainMessage();
					mHandler2.sendMessage(msg);
				} else if (dayOfWeek == 3) {
					mJsonObject3 = DataUtil.http(param, "todayClass");
					adapter3.setmJsonObject(mJsonObject3);
					try {
						count3 = Integer.parseInt(mJsonObject3
								.getString("recordamount"));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message msg = mHandler3.obtainMessage();
					mHandler3.sendMessage(msg);
				} else if (dayOfWeek == 4) {
					mJsonObject4 = DataUtil.http(param, "todayClass");
					adapter4.setmJsonObject(mJsonObject4);
					try {
						count4 = Integer.parseInt(mJsonObject4
								.getString("recordamount"));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message msg = mHandler4.obtainMessage();
					mHandler4.sendMessage(msg);
				} else if (dayOfWeek == 5) {
					mJsonObject5 = DataUtil.http(param, "todayClass");
					adapter5.setmJsonObject(mJsonObject5);
					try {
						count5 = Integer.parseInt(mJsonObject5
								.getString("recordamount"));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message msg = mHandler5.obtainMessage();
					mHandler5.sendMessage(msg);
				} else if (dayOfWeek == 6) {
					mJsonObject6 = DataUtil.http(param, "todayClass");
					adapter6.setmJsonObject(mJsonObject6);
					try {
						count6 = Integer.parseInt(mJsonObject6
								.getString("recordamount"));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message msg = mHandler6.obtainMessage();
					mHandler6.sendMessage(msg);
				}
				// adapter.setmJsonObject(mJsonObject);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static JSONObject result(int dayOfWeek) {
		JSONObject result = new JSONObject();

		try {

			result.put("recordamount", "6");
			JSONArray rclist = new JSONArray();
			if (dayOfWeek == 1) {
				for (int i = 0; i < 6; i++) {
					JSONObject a = new JSONObject();
					a.put("id_of_class", "class" + i);
					a.put("class_time_no", (i + 1) + "-" + (i + 2));
					a.put("tv_chatcontent", "课程：C++" + i);
					a.put("tv_username", "授课老师：cxy" + i);
					rclist.put(i, a);
				}
			} else {
				for (int i = 0; i < 6; i++) {
					JSONObject a = new JSONObject();
					a.put("id_of_class", "class" + i);
					a.put("class_time_no", (i + 1) + "-" + (i + 2));
					a.put("tv_chatcontent", "课程：高数" + i);
					a.put("tv_username", "授课老师：翟玉勇" + i);
					rclist.put(i, a);
				}
			}
			System.out.println("--------------" + rclist.toString());
			result.put("rclist", rclist);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TextView id_of_class = (TextView) view
					.findViewById(R.id.id_of_class);
			String classId = id_of_class.getText().toString().trim();
			Toast.makeText(context, classId, Toast.LENGTH_LONG).show();
			System.out.println("-----------MyOnItemClickListener----------------");
			Intent intent = new Intent(context, ClassDetailActivity.class);
			intent.putExtra("classId", classId);
			context.startActivity(intent);
		}

	}

}
