package cn.edu.shiep.api;


/**
 * ��Ŀ: �Ϻ�����android�ͻ���MVC���
 * �ļ���: HttpDataAccessOperation.java
 * �ļ�ID:
 * ��������:����ͨ��Http��ʽ����ȡ���ݵĳ�ʼ��
 * ����:½˧
 * �������ڣ�2011-01-04
 * �޸�ԭ��
 * �޸����ڣ�
 * �޸��ˣ�
 * �汾�ţ�V1.0
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import cn.edu.shiep.utils.NetUtil;

import android.content.Context;


/**
 * ������HttpDataAccessOperation 
 * ��������������Http���ӣ���÷������Ӧ,���ʷ��������ݵĸ���
 * ģ��ID:
 * ģ�����ƣ����ݷ���ģ��
 * ���ߣ�zsg
 * �������ڣ�2011-01-11
 */
public abstract class HttpDataAccess {
	protected Context context;
	DefaultHttpClient client;// �ͻ��˶���
	String httpMethodStr;// ���ӷ���
    HttpResponse response;// http��Ӧ���
    HttpParams httpParams;// ���Ӳ���
    HttpEntity entity;// httpʵ��
    String urlStr;// ����http���ӵ�URL
    HttpGet httpGet;// get����
    HttpPost httpPost;// post����
    List<NameValuePair> postParams;// post���Ӳ���
    HttpHost proxy;// http���Ӵ���
    String connectionTypeStr;// ������뷽ʽ,net����wap
	private boolean isHtttps=false;//�Ƿ�https����
	protected DefaultHttpClient getClient() {
		return client;
	}
	protected void setClient(DefaultHttpClient client) {
		this.client = client;
	}
	protected String getHttpMethodStr() {
		return httpMethodStr;
	}
	protected void setHttpMethodStr(String httpMethodStr) {
		this.httpMethodStr = httpMethodStr;
	}
	protected HttpResponse getResponse() {
		return response;
	}
	protected void setResponse(HttpResponse response) {
		this.response = response;
	}
	protected HttpParams getHttpParams() {
		return httpParams;
	}
	protected void setHttpParams(HttpParams httpParams) {
		this.httpParams = httpParams;
	}
	protected HttpEntity getEntity() {
		return entity;
	}
	protected void setEntity(HttpEntity entity) {
		this.entity = entity;
	}
	protected String getUrlStr() {
		return urlStr;
	}
	protected void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	protected HttpGet getHttpGet() {
		return httpGet;
	}
	protected void setHttpGet(HttpGet httpGet) {
		this.httpGet = httpGet;
	}
	protected HttpPost getHttpPost() {
		return httpPost;
	}
	protected void setHttpPost(HttpPost httpPost) {
		this.httpPost = httpPost;
	}
	protected List<NameValuePair> getPostParams() {
		return postParams;
	}
	protected void setPostParams(List<NameValuePair> postParams) {
		this.postParams = postParams;
	}
	protected HttpHost getProxy() {
		return proxy;
	}
	protected void setProxy(HttpHost proxy) {
		this.proxy = proxy;
	}
	protected String getConnectionTypeStr() {
		return connectionTypeStr;
	}
	protected void setConnectionTypeStr(String connectionTypeStr) {
		this.connectionTypeStr = connectionTypeStr;
	}
	
	protected boolean isHtttps() {
		return isHtttps;
	}
	protected void setHtttps(boolean isHtttps) {
		this.isHtttps = isHtttps;
	}
	/**
	 * ��������HttpDataAccessOperation 
	 * ��������������HttpDataAccessOperation��
	 * ���������urlStr,����Http���ӵ�URL 
	 * ���������httpMethodStr,http���ӷ�ʽ 
	 * ���������connectionTypeStr,������뷽ʽ
	 * �쳣�� 
	 * ����ֵ�� 
	 * ���ߣ�½˧ 
	 * �������ڣ�2011-01-04
	 */
	public HttpDataAccess(String urlStr, String httpMethodStr,
			String connectionTypeStr,Context context) {
		this.connectionTypeStr = connectionTypeStr;
		this.context=context;
		//
		if(this.httpParams==null){
			this.httpParams = new BasicHttpParams();
//			// ����������ӵ�200
//			ConnManagerParams.setMaxTotalConnections(this.httpParams, 200);
//			// ����ÿ��·�ɵ�Ĭ��������ӵ�20
//			ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
		}
		if(this.isHtttps){
			SchemeRegistry schemeRegistry = new SchemeRegistry();  
			schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));  
			schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 8443));  
			ClientConnectionManager connManager = new ThreadSafeClientConnManager(this.httpParams, schemeRegistry);  
			client = new DefaultHttpClient(connManager,this.httpParams);// ��ʼ���ͻ������Ӷ��� https���뷽ʽ
//			new IdleConnectionMonitorThread(connManager).start();// �����ջز���
		}else{
			// �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			// ʹ���̰߳�ȫ�����ӹ���������HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(this.httpParams, schReg);
			client = new DefaultHttpClient(conMgr,this.httpParams);// ��ʼ���ͻ������Ӷ���
//			new IdleConnectionMonitorThread(conMgr).start();// �����ջز���
		}
//		ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>() {
//			@Override
//			public byte[] handleResponse(HttpResponse response)
//					throws ClientProtocolException, IOException {
//				HttpEntity entity = response.getEntity();
//				if (entity != null) {
//					return EntityUtils.toByteArray(entity);
//				} else {
//					return null;
//				}
//			}
//		};
//		client.execute(httpGet, handler);//������Ӧ
		// �����ط�
//		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
//			@Override
//			public boolean retryRequest(IOException exception,
//					int executionCount, HttpContext context) {
//				// TODO Auto-generated method stub
//				// �ط�������3��
//				if (executionCount > 3) {
//					return false;
//				}
//				if (exception instanceof SSLHandshakeException) {
//					return false;
//				}
//				if (exception instanceof NoHttpResponseException) {
//					return true;
//				}
//				HttpRequest request = (HttpRequest) context
//						.getAttribute(ExecutionContext.HTTP_REQUEST);
//				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
//				if (idempotent) {
//					return true;
//				} else
//					return false;
//			}
//		};
//		client.setHttpRequestRetryHandler(retryHandler);// ���������ط�
		httpParams = client.getParams();// ���Ӳ���
		// �����Ҫ����wap����������wap���뷽ʽ�����ô���
		if (connectionTypeStr.equals("wap")) {
			proxy = new HttpHost("10.0.0.200", 80);
			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		// ���Ӳ���
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8196);
		HttpClientParams.setRedirecting(httpParams, true);
		this.httpMethodStr = httpMethodStr;
		this.urlStr = urlStr;
		if (httpMethodStr.equals("get")) {
			httpGet = new HttpGet(urlStr);// ��ʼ��get����
		}
		if (httpMethodStr.equals("post")) {
			httpPost = new HttpPost(urlStr);// ��ʼ��post����
		}
	}
	/**
	 * ��������getResponse
	 * ������������÷������Ӧ
	 * ���������connectionTypeStr,��������ķ�ʽ
	 * ���������httpMethod,http���ӷ�ʽ
	 * �쳣��ClientProtocolException 
	 * �쳣��IOException�������쳣�����ᵼ��ANR
	 * �쳣��NullPointerException�����쳣�����ᵼ��ANR
	 * ����ֵ���������Ӧ����
	 * ���ߣ�½˧ 
	 * �������ڣ�2011-01-04
	 */
	HttpResponse getResponse(String connectionTypeStr,
			HttpUriRequest httpMethod) throws ClientProtocolException,
			NullPointerException, IOException {
		HttpResponse response = null;
		if(NetUtil.isConnnected(context)){
			//wap����
			if (connectionTypeStr.equals("wap")) {
				response = client.execute(proxy, httpMethod);
			} else {
				//net����
				response = client.execute(httpMethod);
			}
		}else{
			throw new IOException();
		}
		return response;
	}
	
	/**
	 * ��������getDataByGetMethod
	 * ��������������Get�������ӣ�����÷�����������ַ�������ʽ��������
	 * �쳣��ClientProtocolException 
	 * �쳣��IOException�������쳣�����ᵼ��ANR
	 * �쳣��NullPointerException�����쳣�����ᵼ��ANR
	 * ����ֵ��JSONArray����JSONObject��ʽ���ַ�������Ҫ��һЩList���ݻ���ĳ���������� 
	 * ���ߣ�½˧ 
	 * �������ڣ�2011-01-04
	 */
	public String getDataByGetMethod() throws ClientProtocolException, IOException,TimeoutException,
			NullPointerException {
		StringBuilder result = new StringBuilder();//�����������ݵ��ַ���
		if(httpPost!=null){
			response = getResponse(connectionTypeStr, httpPost);// �������Ӧ����
		}else{
			response = getResponse(connectionTypeStr, httpGet);// �������Ӧ����
		}
		//������Ӧ
		if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
			//��������ת�����ַ���
			if (entity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), HTTP.UTF_8),
						8192);
				String line = null;
				while ((line = reader.readLine()) != null) {
					result.append(line + "\n");
				}
				reader.close();
			}
			if (entity != null) {
				entity.consumeContent();
			}
		}
		return result.toString();
	}
	/**
	 * ��������getDataByPostMethod
	 * ��������������Post���ӣ�����÷�����������ַ�������ʽ��������
	 * �쳣��ClientProtocolException 
	 * �쳣��IOException�������쳣�����ᵼ��ANR
	 * �쳣��NullPointerException�����쳣�����ᵼ��ANR
	 * ����ֵ��JSONArray����JSONObject��ʽ���ַ�������Ҫ��һЩList���ݻ���ĳ���������� 
	 * ���ߣ�½˧ 
	 * �������ڣ�2011-01-04
	 */
	public String getDataByPostMethod() throws  ClientProtocolException, IOException,TimeoutException,
	NullPointerException  {
		StringBuilder result = new StringBuilder();//�����������ݵ��ַ���
		HttpEntity httpEntity = new UrlEncodedFormEntity(postParams,
				HTTP.UTF_8);//��������ʵ��
		httpPost.setEntity(httpEntity);//��������ʵ��
		if(httpPost!=null){
			response = getResponse(connectionTypeStr, httpPost);// �������Ӧ����
		}else{
			response = getResponse(connectionTypeStr, httpGet);// �������Ӧ����
		}
		//��������
		if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
			//��������ת�����ַ���
			if (entity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), HTTP.UTF_8),
						8192);
				String line = null;
				while ((line = reader.readLine()) != null) {
					result.append(line + "\n");
				}
				reader.close();
			}
			if (entity != null) {
				entity.consumeContent();
			}
		} 
		return result.toString();
	}
	
	
	/**
	 * ��������getDataByPostSendString
	 * ��������������Post����,���ַ����ĸ�ʽ�����˽������ݷ��ͣ�����÷�����������ַ�������ʽ��������
	 * �쳣��ClientProtocolException 
	 * �쳣��IOException�������쳣�����ᵼ��ANR
	 * �쳣��NullPointerException�����쳣�����ᵼ��ANR
	 * ����ֵ��JSONArray����JSONObject��ʽ���ַ�������Ҫ��һЩList���ݻ���ĳ���������� 
	 * ���ߣ�zsg 
	 * �������ڣ�2011-01-04
	 */
	public String getDataByPostSendString(String msg) throws  ClientProtocolException, IOException,TimeoutException,
	NullPointerException  {
		StringBuilder result = new StringBuilder();//�����������ݵ��ַ���
		HttpEntity httpEntity = new StringEntity(msg,HTTP.UTF_8);//��������ʵ��
		httpPost.setEntity(httpEntity);//��������ʵ��
		if(httpPost!=null){
			response = getResponse(connectionTypeStr, httpPost);// �������Ӧ����
		}else{
			response = getResponse(connectionTypeStr, httpGet);// �������Ӧ����
		}
		//��������
		if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
			//��������ת�����ַ���
			if (entity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), HTTP.UTF_8),
						8192);
				String line = null;
				while ((line = reader.readLine()) != null) {
					result.append(line + "\n");
				}
				reader.close();
			}
			if (entity != null) {
				entity.consumeContent();
			}
		} 
		return result.toString();
	}
	
	
	
	
	/**
	 * �ļ��ϴ�
	 * @param file
	 * @return
	 * @throws ClientProtocolException
	 * @throws TimeoutException
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public String UploadFile(File file)throws ClientProtocolException,TimeoutException,NullPointerException, IOException {
		   FileEntity reqEntity = new FileEntity(file, "binary/octet-stream");
		   httpPost.setEntity(reqEntity);
		   String result=null;
		    reqEntity.setContentType("binary/octet-stream");
		    System.out.println("executing request " + httpPost.getRequestLine());
		    if(httpPost!=null){
				response = getResponse(connectionTypeStr, httpPost);// �������Ӧ����
			}else{
				response = getResponse(connectionTypeStr, httpGet);// �������Ӧ����
			}
			// ������Ӧ
			if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity resEntity  = response.getEntity();
				   if (resEntity != null) {
				      result= EntityUtils.toString(resEntity);
				      resEntity.consumeContent();
				    }
			}else{
				result="fileFailure";//�ļ��ϴ�ʧ��
			}
			return result;
	}
	
	
	/**
	 * ��������getFileStream
	 * �����������ӷ���������ļ�
	 * ���������fileNameStr,�ļ���
	 * ���������fileExtensionsStr,�ļ���չ��
	 * �쳣��ClientProtocolException �ͻ��������쳣
	 * �쳣��IOException����������쳣�����ᵼ��ANR
	 * �쳣��NullPointerException�����쳣�����ᵼ��ANR
	 * ����ֵ���ļ��������ļ����ڴ洢�������������ļ�
	 * ���ߣ�zsg
	 * �������ڣ�2011-01-04
	 */
	public File getFileStream(String fileLoacation,String fileNameStr) throws ClientProtocolException, NullPointerException, IOException,TimeoutException{
		if(httpPost!=null){
			response = getResponse(connectionTypeStr, httpPost);// �������Ӧ����
		}else{
			response = getResponse(connectionTypeStr, httpGet);// �������Ӧ����
		}
		File tempFile = new File(fileLoacation, fileNameStr);//�����ļ�
		//������Ӧ
		if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream is = response.getEntity().getContent();
			// �����ļ���
			FileOutputStream fileStream = new FileOutputStream(tempFile);
			byte[] buf = new byte[HttpConnectionParams
					.getSocketBufferSize(httpParams)];
			int size = 0;
			// ���ļ���ת�����ֽ���
			while ((size = is.read(buf)) != -1) {
				fileStream.write(buf, 0, size);
			}
			fileStream.close();
			is.close();
			if (entity != null) {
				entity.consumeContent();
			}
		}
		return tempFile;
	}
	
	
	/**    
	* ��Ŀ���ƣ�PFWX_PAD   
	* �����ƣ�IdleConnectionMonitorThread   
	* ��������ThreadSafeClientConnManager�����������������õ������ջز���
	* �����ˣ�zsg  
	* ����ʱ�䣺2012-8-14
	 */
	public static class IdleConnectionMonitorThread extends Thread {
		private final ClientConnectionManager connMgr;
		private volatile boolean shutdown;
		public IdleConnectionMonitorThread(ClientConnectionManager connMgr) {
			super();
			this.connMgr = connMgr;
		}
		@Override
		public void run() {
			try {
				while (!shutdown) {
					synchronized (this) {
						wait(5000);
						// �رչ�������
						connMgr.closeExpiredConnections();
						// ��ѡ�أ��رտ��г���30�������
//						connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
					}
				}
			} catch (InterruptedException ex) {
				// ��ֹ
				shutdown();
			}
		}
		public void shutdown() {
			shutdown = true;
			synchronized (this) {
				this.notify();
			}
		}
	}

	
}
