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
	public final static String NETWORK_ERROR = "����������������źŲ��ȶ�,���Ժ�����";
	public final static String NETWORK_TIP = "û�п��õ��������ӣ�����������";
	public final static String TIP = "��������";
	public final static String EXIT = "�˳�";
	public final static String USER_EXIT = "�û��˳�";
	public final static String CONFIRM = "ȷ��";
	public final static String CANCEL = "ȡ��";
	public final static String LOCATION_TIP = "�޷����λ����Ϣ��������λ����Դ";
	public final static String CONFIRM_LOCATION = "��λ��...";
	public final static String LOG_SUCCESS_ACTION = "cn.sh.ideal.android.client.log.success.action";
	public final static String LOG_FAIL_ACTION = "cn.sh.ideal.android.client.log.fail.action";
	public final static String LOG_EXCEPTION_ACTION = "cn.sh.ideal.android.client.log.exception.action";
	//����Ƿ����°汾�ķ����Action
	public final static String CHECK_NEW_VERSION_SERVICE_ACTION = "cn.sh.ideal.android.client.check.version.service.action";
	//����Ƿ����°汾�Ľ���Ĺ㲥��Action
	public final static String CHECK_VERSION_RESULT_BROADCASTRECEIVER_ACTION = "cn.sh.ideal.android.client.check.version.result.broadcastreceiver.action";
	//����ǰ汾�����г����쳣�Ĺ㲥��Action
	public final static String CHECK_VERSION_EXCEPTION_BROADCASTRECEIVER_ACTION = "cn.sh.ideal.android.client.check.version.networkerror.broadcastreceiver.action";
	//�洢����״̬�Ĺ㲥��Action
	public final static String SDCARD_STATUS_BROADCASTRECEIVER_ACTION = "cn.sh.ideal.android.client.sdcard.broadcastreceiver.action";
	//�����°汾�ļ��ķ����Action
	public final static String DOWNLOAD_INSTALLER_PACKAGE_SERVICE_ACTION = "cn.sh.ideal.android.client.download.installer.package.service.action";
	//�����°汾�ļ���ɵĹ㲥��Action
	public final static String DOWNLOAD_COMPLETE_BROADCASTRECEIVER_ACTION = "cn.sh.ideal.android.client.download.complete.action";
	public final static String SDCARD_STATUS_NO_SDCARD="�����洢��,�����޷������ļ�";
	public final static String SDCARD_STATUS_READ_ONLY="�洢����ֻ���ģ������޷�������";
	public final static String SDCARD_STATUS_SHARE="�洢�����͵��Թ��������޷������ļ�";
	public final static String SDCARD_STATUS_CHECKING="�洢�����ڼ�⣬�����޷������ļ�";
	public final static String SDCARD_STATUS_NOT_SUPPORT="�洢�����ļ�ϵͳ��֧�֣������޷������ļ�";
	public final static String SDCARD_STATUS_NOT_MOUNT="�洢���޷����أ������޷������ļ�";
	public final static String NO_NEW_VERSION="��ǰ�汾Ϊ���°汾���������";
	public final static String HAVE_NEW_VERSION="���µİ汾����ȷ�ϸ�����";
	public final static String VERSION_UPDATE="�汾����";
	public final static String NEW_VERSION_DOWNLOADING="����������..�������ĵȺ�";
	public final static String CHECKING_VERSION="��������...�������ĵȺ�";
	public final static String CONTINUE_DOWNLOADING="��������";
	public final static String LOADING_WAIT ="���ڼ����У����Ժ�...";
	public static ProgressDialog progressDialog = null;
	public static String ERRORCODE, ERRORMSG = "";
	
	//��ȡ��ǰSDK�汾��
		public static int getSDKVerstion(Context context){
			@SuppressWarnings("unused")
			TelephonyManager phoneMgr=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			Log.i("---ML---", "--SDKVerstion=="+Build.VERSION.SDK_INT);
			return Build.VERSION.SDK_INT;
		}
		
		
		/**
		 * Android�ж�Ӧ���Ƿ����
		 * @param context
		 * @param packageName//����Ӧ�ð���
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
		 * ��ͼƬ����ΪԲ��
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
		 * ��ӵ�Ӱ��ԭ���ȷ�תͼƬ�����ϵ��·Ŵ�͸����
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
			// ָ��һ���Ƕ���0,0Ϊ���������ת
			// matrix.setRotate(120);
			// ָ������(x�᲻�䣬y���෴)
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
		 * ��ʾ�ȴ�������
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
		 * ȡ���ȴ�������
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

