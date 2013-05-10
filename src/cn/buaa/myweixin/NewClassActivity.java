package cn.buaa.myweixin;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.shiep.api.DataUtil;
import cn.edu.shiep.utils.ThreadPoolUtils;
import cn.edu.shiep.utils.Utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class NewClassActivity extends Activity {
	private Context context;
	private EditText name_of_class;
	private EditText name_of_teacher;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	


	private static final String[] m = { "1-2", "3-4", "5-6", "7-8", "9-11" };
	private static final String[] n = {"1","2","3","4","5"};
	private static final String[] address = {"北1101","北1102","北1103","北1104","北1105","北1106","北1107","南1101","南1102","南1103","南1104","南1105","南1106"};
	private ArrayAdapter<String> adapter_m;
	
	private ArrayAdapter<String> adapter_n;
	
	private ArrayAdapter<String> adapter_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_class_add);
		context = this;
		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		if("1".equals(type)){
			
		}
		name_of_class = (EditText) findViewById(R.id.name_of_class);
		name_of_class.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ClassListActivity.class);
				startActivityForResult(intent, 2);

			}
		});
		name_of_teacher = (EditText) findViewById(R.id.name_of_teacher);

		name_of_teacher.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, TeacherListActivity.class);
				startActivityForResult(intent, 1);
			}
		});

		spinner1 = (Spinner) findViewById(R.id.Spinner01);
		adapter_m = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m);
		// 设置下拉列表的风格
		adapter_m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner1.setAdapter(adapter_m);
		// 设置默认值
		spinner1.setVisibility(View.VISIBLE);
		
		spinner2= (Spinner) findViewById(R.id.Spinner02);
		adapter_n = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, n);
		// 设置下拉列表的风格
		adapter_n.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner2.setAdapter(adapter_n);
		// 设置默认值
		spinner2.setVisibility(View.VISIBLE);
		
		spinner3= (Spinner) findViewById(R.id.Spinner03);
		adapter_address = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, address);
		// 设置下拉列表的风格
		adapter_address.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner3.setAdapter(adapter_address);
		// 设置默认值
		spinner3.setVisibility(View.VISIBLE);
		

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 1) {
			name_of_teacher.setText(data.getStringExtra("teacherName"));
			name_of_teacher.setTag(data.getStringExtra("uuid"));
		} else if (requestCode == 2) {
			name_of_class.setText(data.getStringExtra("className"));
			name_of_class.setTag(data.getStringExtra("uuid"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View view, int arg2,
				long arg3) {

		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	public void btn_back(View v) {
		this.finish();
	}

	public void btn_addNewClass(View v) {
		Animation myAlphaAction = AnimationUtils.loadAnimation(context,
				R.anim.alpha_action);
		v.startAnimation(myAlphaAction);
		String nameOfClass = name_of_class.getText().toString().trim();
		String nameOfTeacher = name_of_teacher.getText().toString().trim();
		Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
		if ("".equals(nameOfClass)) {
			name_of_class.startAnimation(shake);
			name_of_class.requestFocus();
			return;
		} else if ("".equals(nameOfTeacher)) {
			name_of_teacher.startAnimation(shake);
			name_of_teacher.requestFocus();
			return;
		}
		Utils.showProgress(context, "正在进行增加课程，请稍后...");
		ThreadPoolUtils.execute(AddClassThread);

	}

	private Runnable AddClassThread = new Runnable() {

		@Override
		public void run() {
			JSONObject params = new JSONObject();
			try {
				params.put("teacherId", name_of_teacher.getTag().toString().trim());
				params.put("classId", name_of_class.getTag().toString().trim());
				params.put("classAddress", spinner3.getSelectedItem().toString().trim());
				params.put("classTime", spinner1.getSelectedItem().toString().trim());
				params.put("comments", "");
				params.put("dayOfWeek", spinner2.getSelectedItem().toString().trim());
				System.out.println("params=="+params.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				JSONObject resultData = DataUtil.http(params, "addTeacherClass");
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
				Message message = new Message();
				message.what = 0;
				mHandler.sendMessage(message);
			}

		}
	};
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(context, "添加成功！",
						Toast.LENGTH_LONG).show();
				Utils.cancelProgress();

				break;
			case 0:
				Toast.makeText(context, "发生未知异常!添加课程失败,请稍后再试....",
						Toast.LENGTH_LONG).show();
				Utils.cancelProgress();
				break;
			default:
				break;
			}
		}

	};

}
