package cn.edu.shiep.utils;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Utils {
	public final static String NETWORK_ERROR = "您所在区域的网络信号不稳定,请稍后再试";
	public final static String NETWORK_TIP = "没有可用的网络连接，请设置网络";
	public final static String TIP = "友情提醒";
	public final static String EXIT = "退出";
	public final static String USER_EXIT = "用户退出";
	public final static String CONFIRM = "确定";
	public final static String CANCEL = "取消";
	public final static String LOCATION_TIP = "无法获得位置信息，请设置位置来源";
	public final static String CONFIRM_LOCATION = "定位中...";
	public final static String LOG_SUCCESS_ACTION = "cn.sh.ideal.android.client.log.success.action";
	public final static String LOG_FAIL_ACTION = "cn.sh.ideal.android.client.log.fail.action";
	public final static String LOG_EXCEPTION_ACTION = "cn.sh.ideal.android.client.log.exception.action";
	//检测是否有新版本的服务的Action
	public final static String CHECK_NEW_VERSION_SERVICE_ACTION = "cn.sh.ideal.android.client.check.version.service.action";
	//检测是否有新版本的结果的广播的Action
	public final static String CHECK_VERSION_RESULT_BROADCASTRECEIVER_ACTION = "cn.sh.ideal.android.client.check.version.result.broadcastreceiver.action";
	//检测是版本过程中出现异常的广播的Action
	public final static String CHECK_VERSION_EXCEPTION_BROADCASTRECEIVER_ACTION = "cn.sh.ideal.android.client.check.version.networkerror.broadcastreceiver.action";
	//存储卡的状态的广播的Action
	public final static String SDCARD_STATUS_BROADCASTRECEIVER_ACTION = "cn.sh.ideal.android.client.sdcard.broadcastreceiver.action";
	//下载新版本文件的服务的Action
	public final static String DOWNLOAD_INSTALLER_PACKAGE_SERVICE_ACTION = "cn.sh.ideal.android.client.download.installer.package.service.action";
	//下载新版本文件完成的广播的Action
	public final static String DOWNLOAD_COMPLETE_BROADCASTRECEIVER_ACTION = "cn.sh.ideal.android.client.download.complete.action";
	public final static String SDCARD_STATUS_NO_SDCARD="请插入存储卡,现在无法下载文件";
	public final static String SDCARD_STATUS_READ_ONLY="存储卡是只读的，现在无法下载文";
	public final static String SDCARD_STATUS_SHARE="存储卡正和电脑共享，现在无法下载文件";
	public final static String SDCARD_STATUS_CHECKING="存储卡正在检测，现在无法下载文件";
	public final static String SDCARD_STATUS_NOT_SUPPORT="存储卡的文件系统不支持，现在无法下载文件";
	public final static String SDCARD_STATUS_NOT_MOUNT="存储卡无法挂载，现在无法下载文件";
	public final static String NO_NEW_VERSION="当前版本为最新版本，无需更新";
	public final static String HAVE_NEW_VERSION="有新的版本，您确认更新吗？";
	public final static String VERSION_UPDATE="版本更新";
	public final static String NEW_VERSION_DOWNLOADING="正在下载中..，请耐心等候";
	public final static String CHECKING_VERSION="检测更新中...，请耐心等候";
	public final static String CONTINUE_DOWNLOADING="继续下载";
	public final static String LOADING_WAIT ="正在加载中，请稍后...";
	public static ProgressDialog progressDialog = null;
	public static String ERRORCODE, ERRORMSG = "";
	
	//获取当前SDK版本号
		public static int getSDKVerstion(Context context){
			@SuppressWarnings("unused")
			TelephonyManager phoneMgr=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			Log.i("---ML---", "--SDKVerstion=="+Build.VERSION.SDK_INT);
			return Build.VERSION.SDK_INT;
		}
		
		
		/**
		 * Android判断应用是否存在
		 * @param context
		 * @param packageName//完整应用包名
		 * @return
		 */
		public static boolean checkBrowser(Context context,String packageName) {
	        if (packageName == null || "".equals(packageName))
	            return false;
	        try {
	        	ApplicationInfo info= context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
	        	Log.i("---ML---", "--packageInfo=="+info.packageName);
	            return true;
	        } catch (NameNotFoundException e) {
	        	Log.e("---ML---", null,e);
	            return false;
	        }
	    }
	
		/**
		 * 将图片设置为圆角
		 * @param bitmap
		 * @param pixels
		 * @return
		 */
		public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;
		}
		
		/**
		 * 添加倒影，原理，先翻转图片，由上到下放大透明度
		 * 
		 * @param originalImage
		 * @return
		 */
		public static Bitmap createReflectedImage(Bitmap originalImage) {
			
			// The gap we want between the reflection and the original image
			final int reflectionGap = 4;

			int width = originalImage.getWidth();
			int height = originalImage.getHeight();

			// This will not scale but will flip on the Y axis
			Matrix matrix = new Matrix();
			// 指定一个角度以0,0为坐标进行旋转
			// matrix.setRotate(120);
			// 指定矩阵(x轴不变，y轴相反)
			matrix.preScale(1, -1);
			// Create a Bitmap with the flip matrix applied to it.
			// We only want the bottom half of the image
			Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
					height / 6, width, height / 6, matrix, false);

			// Create a new bitmap with same width but taller to fit reflection
			Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
					(height + height / 400), Config.ARGB_4444);//ARGB_8888

			// Create a new Canvas with the bitmap that's big enough for
			// the image plus gap plus reflection
			Canvas canvas = new Canvas(bitmapWithReflection);
			// Draw in the original image
			canvas.drawBitmap(originalImage, 0, 0, null);
			// Draw in the gap
			Paint defaultPaint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
			// Draw in the reflection
			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

			// Create a shader that is a linear gradient that covers the reflection
			Paint paint = new Paint();
			LinearGradient shader = new LinearGradient(0,
					bitmapWithReflection.getHeight(), 0,
					bitmapWithReflection.getHeight() + reflectionGap, 0xB0FFFFFF,
					0x00000000, Shader.TileMode.CLAMP);
			// Set the paint to use this shader (linear gradient)
			paint.setShader(shader);
			// Set the Transfer mode to be porter duff and destination in
			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			// Draw a rectangle using the paint with our linear gradient
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);

			return bitmapWithReflection;
		}
		
		/**
		 * 显示等待进度条
		 * 
		 * @param context
		 */
		public static void showProgress(Context context) {
			showProgress(context, LOADING_WAIT);
		}

		public static void showProgress(Context context, String message) {
			progressDialog = new ProgressDialog(context);
			// progressDialog.setTitle("Please wait.....");
			progressDialog.setMessage(message);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			if (!progressDialog.isShowing()) {
				progressDialog.show();
			}
			progressDialog.setCanceledOnTouchOutside(false);
		}

		/**
		 * 取消等待进度条
		 * 
		 */
		public static void cancelProgress() {
			if (progressDialog != null) {
				progressDialog.cancel();
			}
		}


		/**
		 * @return
		 */
		public static String getThress() {
			return "dianll";
		}
}

