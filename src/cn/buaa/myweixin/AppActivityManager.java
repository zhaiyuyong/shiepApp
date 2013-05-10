package cn.buaa.myweixin;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class AppActivityManager {
	private static Stack<Activity> activityStack;
    private static AppActivityManager instance;
    private AppActivityManager() {
    }
    public static AppActivityManager getScreenManager() {
        if (instance == null) {
            instance = new AppActivityManager();
        }
        return instance;
    }
    //退出栈顶Activity
    public void popActivity(Activity activity) {
        if (activity != null) {
           //在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
        	if(!activity.isFinishing()){
        		activity.finish();
        	}
            activityStack.remove(activity);
            activity = null;
        }
    }
    //获得当前栈顶Activity
    public Activity currentActivity() {
        Activity activity = null;
       if(!activityStack.empty())
         activity= activityStack.lastElement();
        return activity;
    }
    //将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if(!activityStack.contains(activity)){//判断当前Activity是否已经存在，如果不存在，就其数据放入堆栈中
        	activityStack.add(activity);
        }
    }
    //退出栈中所有Activity
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }
    //退出栈中所有Activity
    public void popAllActivityExceptOne(Context context,Class cls) {
    	Intent intent=null;
    	while (true) {
    		Activity activity = currentActivity();
    		if (activity == null) {
    			break;
    		}
    		if (activity.getClass().equals(cls)) {
    			if(activity.isFinishing()){
    				intent=new Intent(context,cls);
    			}
    			break;
    		}
    		popActivity(activity);
    	}
    	if(intent!=null&&cls!=null){
    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		context.startActivity(intent);
    	}
    }
}
