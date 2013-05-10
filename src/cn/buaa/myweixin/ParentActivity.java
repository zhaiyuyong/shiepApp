package cn.buaa.myweixin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class ParentActivity extends Activity{

	protected AppActivityManager appActivityManager;
	protected Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppSystem application = (AppSystem) this.getApplication();
		appActivityManager = application.getAppActivityManager();
		appActivityManager.pushActivity(this);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		AppSystem application = (AppSystem) this.getApplication();
		application.getAppActivityManager().popActivity(this);
	}

}
