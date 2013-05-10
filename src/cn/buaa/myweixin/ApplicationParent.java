package cn.buaa.myweixin;

import android.app.Application;



public abstract class ApplicationParent extends Application {
	private AppActivityManager appActivityManager = null;
    public AppActivityManager getAppActivityManager() {
        return appActivityManager;
    }
    public void setActivityManager(AppActivityManager activityManager) {
        this.appActivityManager = activityManager;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
      //��ʼ���Զ���Activity������
        appActivityManager = AppActivityManager.getScreenManager();
    }
}
