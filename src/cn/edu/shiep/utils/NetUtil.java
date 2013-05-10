package cn.edu.shiep.utils;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**    
 * 项目名称：PFWX_PAD   
 * 类名称：NetUtil   
 * 类描述：效验网络连接状态
 * 创建人：zsg  
 * 创建时间：2012-8-11
 */
public class NetUtil {
	 private static final String TAG = "NetUtil";  
	   /** 
	    * 网络连接是否可用 
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
