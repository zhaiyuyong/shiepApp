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
    //�˳�ջ��Activity
    public void popActivity(Activity activity) {
        if (activity != null) {
           //�ڴ��Զ��弯����ȡ����ǰActivityʱ��Ҳ������Activity�Ĺرղ���
        	if(!activity.isFinishing()){
        		activity.finish();
        	}
            activityStack.remove(activity);
            activity = null;
        }
    }
    //��õ�ǰջ��Activity
    public Activity currentActivity() {
        Activity activity = null;
       if(!activityStack.empty())
         activity= activityStack.lastElement();
        return activity;
    }
    //����ǰActivity����ջ��
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if(!activityStack.contains(activity)){//�жϵ�ǰActivity�Ƿ��Ѿ����ڣ���������ڣ��������ݷ����ջ��
        	activityStack.add(activity);
        }
    }
    //�˳�ջ������Activity
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
    //�˳�ջ������Activity
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
