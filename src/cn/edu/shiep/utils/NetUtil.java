package cn.edu.shiep.utils;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**    
 * ��Ŀ���ƣ�PFWX_PAD   
 * �����ƣ�NetUtil   
 * ��������Ч����������״̬
 * �����ˣ�zsg  
 * ����ʱ�䣺2012-8-11
 */
public class NetUtil {
	 private static final String TAG = "NetUtil";  
	   /** 
	    * ���������Ƿ���� 
	    */  
	   public static boolean isConnnected(Context context) {  
	       ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	       if (null != connectivityManager) {  
	           NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();  
	           if (null != networkInfo) {  
	               for (NetworkInfo info : networkInfo) {  
	                   if (info.getState() == NetworkInfo.State.CONNECTED) {  
	                       Log.e(TAG, "the net is ok");  
	                       return true;  
	                   } 
	               }  
	           }  
	       }  
    	   try{
		       Looper.prepare(); 
		       Toast.makeText(context, Utils.NETWORK_TIP, Toast.LENGTH_SHORT).show();  
		       Looper.loop();
    	   }catch(Exception e){
    		   Log.e("--ideal--", e.getMessage(),e);
    		   Toast.makeText(context, Utils.NETWORK_TIP, Toast.LENGTH_SHORT).show();  
    	   }
	       return false;  
	   }  
}
