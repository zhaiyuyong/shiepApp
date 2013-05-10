package cn.edu.shiep.api;


/**
 * 项目: 上海理想android客户端MVC框架
 * 文件名: HttpDataAccessOperation.java
 * 文件ID:
 * 功能描述:定义通过Http方式来获取数据的初始类
 * 作者:陆帅
 * 创建日期：2011-01-04
 * 修改原因：
 * 修改日期：
 * 修改人：
 * 版本号：V1.0
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
 * 类名：HttpDataAccessOperation 
 * 功能描述：建立Http连接，获得服务端响应,访问服务器数据的父类
 * 模块ID:
 * 模块名称：数据访问模块
 * 作者：zsg
 * 创建日期：2011-01-11
 */
public abstract class HttpDataAccess {
	protected Context context;
	DefaultHttpClient client;// 客户端对象
	String httpMethodStr;// 连接方法
    HttpResponse response;// http响应结果
    HttpParams httpParams;// 连接参数
    HttpEntity entity;// http实体
    String urlStr;// 建立http连接的URL
    HttpGet httpGet;// get连接
    HttpPost httpPost;// post连接
    List<NameValuePair> postParams;// post连接参数
    HttpHost proxy;// http连接代理
    String connectionTypeStr;// 网络接入方式,net或者wap
	private boolean isHtttps=false;//是否https接入
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
	 * 方法名：HttpDataAccessOperation 
	 * 功能描述：构造HttpDataAccessOperation类
	 * 输入参数：urlStr,建立Http连接的URL 
	 * 输入参数：httpMethodStr,http连接方式 
	 * 输入参数：connectionTypeStr,网络接入方式
	 * 异常： 
	 * 返回值： 
	 * 作者：陆帅 
	 * 创建日期：2011-01-04
	 */
	public HttpDataAccess(String urlStr, String httpMethodStr,
			String connectionTypeStr,Context context) {
		this.connectionTypeStr = connectionTypeStr;
		this.context=context;
		//
		if(this.httpParams==null){
			this.httpParams = new BasicHttpParams();
//			// 增加最大连接到200
//			ConnManagerParams.setMaxTotalConnections(this.httpParams, 200);
//			// 增加每个路由的默认最大连接到20
//			ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
		}
		if(this.isHtttps){
			SchemeRegistry schemeRegistry = new SchemeRegistry();  
			schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));  
			schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 8443));  
			ClientConnectionManager connManager = new ThreadSafeClientConnManager(this.httpParams, schemeRegistry);  
			client = new DefaultHttpClient(connManager,this.httpParams);// 初始化客户端连接对象 https接入方式
//			new IdleConnectionMonitorThread(connManager).start();// 连接收回策略
		}else{
			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(this.httpParams, schReg);
			client = new DefaultHttpClient(conMgr,this.httpParams);// 初始化客户端连接对象
//			new IdleConnectionMonitorThread(conMgr).start();// 连接收回策略
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
//		client.execute(httpGet, handler);//处理响应
		// 请求重发
//		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
//			@Override
//			public boolean retryRequest(IOException exception,
//					int executionCount, HttpContext context) {
//				// TODO Auto-generated method stub
//				// 重发不超过3次
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
//		client.setHttpRequestRetryHandler(retryHandler);// 设置请求重发
		httpParams = client.getParams();// 连接参数
		// 如果需要访问wap网络必须采用wap接入方式并设置代理
		if (connectionTypeStr.equals("wap")) {
			proxy = new HttpHost("10.0.0.200", 80);
			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		// 连接参数
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8196);
		HttpClientParams.setRedirecting(httpParams, true);
		this.httpMethodStr = httpMethodStr;
		this.urlStr = urlStr;
		if (httpMethodStr.equals("get")) {
			httpGet = new HttpGet(urlStr);// 初始化get连接
		}
		if (httpMethodStr.equals("post")) {
			httpPost = new HttpPost(urlStr);// 初始化post连接
		}
	}
	/**
	 * 方法名：getResponse
	 * 功能描述：获得服务端响应
	 * 输入参数：connectionTypeStr,接入网络的方式
	 * 输入参数：httpMethod,http连接方式
	 * 异常：ClientProtocolException 
	 * 异常：IOException，连接异常，它会导致ANR
	 * 异常：NullPointerException，空异常，它会导致ANR
	 * 返回值：服务端响应对象
	 * 作者：陆帅 
	 * 创建日期：2011-01-04
	 */
	HttpResponse getResponse(String connectionTypeStr,
			HttpUriRequest httpMethod) throws ClientProtocolException,
			NullPointerException, IOException {
		HttpResponse response = null;
		if(NetUtil.isConnnected(context)){
			//wap连接
			if (connectionTypeStr.equals("wap")) {
				response = client.execute(proxy, httpMethod);
			} else {
				//net连接
				response = client.execute(httpMethod);
			}
		}else{
			throw new IOException();
		}
		return response;
	}
	
	/**
	 * 方法名：getDataByGetMethod
	 * 功能描述：建立Get数据连接，并获得服务端数据以字符串的形式返回数据
	 * 异常：ClientProtocolException 
	 * 异常：IOException，连接异常，它会导致ANR
	 * 异常：NullPointerException，空异常，它会导致ANR
	 * 返回值：JSONArray或者JSONObject形式的字符串，主要是一些List数据或者某个对象数据 
	 * 作者：陆帅 
	 * 创建日期：2011-01-04
	 */
	public String getDataByGetMethod() throws ClientProtocolException, IOException,TimeoutException,
			NullPointerException {
		StringBuilder result = new StringBuilder();//保存服务端数据的字符串
		if(httpPost!=null){
			response = getResponse(connectionTypeStr, httpPost);// 服务端响应对象
		}else{
			response = getResponse(connectionTypeStr, httpGet);// 服务端响应对象
		}
		//正常响应
		if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
			//将输入流转换成字符串
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
	 * 方法名：getDataByPostMethod
	 * 功能描述：建立Post连接，并获得服务端数据以字符串的形式返回数据
	 * 异常：ClientProtocolException 
	 * 异常：IOException，连接异常，它会导致ANR
	 * 异常：NullPointerException，空异常，它会导致ANR
	 * 返回值：JSONArray或者JSONObject形式的字符串，主要是一些List数据或者某个对象数据 
	 * 作者：陆帅 
	 * 创建日期：2011-01-04
	 */
	public String getDataByPostMethod() throws  ClientProtocolException, IOException,TimeoutException,
	NullPointerException  {
		StringBuilder result = new StringBuilder();//保存服务端数据的字符串
		HttpEntity httpEntity = new UrlEncodedFormEntity(postParams,
				HTTP.UTF_8);//构造连接实体
		httpPost.setEntity(httpEntity);//设置连接实体
		if(httpPost!=null){
			response = getResponse(connectionTypeStr, httpPost);// 服务端响应对象
		}else{
			response = getResponse(connectionTypeStr, httpGet);// 服务端响应对象
		}
		//正常连接
		if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
			//将输入流转换成字符串
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
	 * 方法名：getDataByPostSendString
	 * 功能描述：建立Post连接,以字符串的格式向服务端进行数据发送，并获得服务端数据以字符串的形式返回数据
	 * 异常：ClientProtocolException 
	 * 异常：IOException，连接异常，它会导致ANR
	 * 异常：NullPointerException，空异常，它会导致ANR
	 * 返回值：JSONArray或者JSONObject形式的字符串，主要是一些List数据或者某个对象数据 
	 * 作者：zsg 
	 * 创建日期：2011-01-04
	 */
	public String getDataByPostSendString(String msg) throws  ClientProtocolException, IOException,TimeoutException,
	NullPointerException  {
		StringBuilder result = new StringBuilder();//保存服务端数据的字符串
		HttpEntity httpEntity = new StringEntity(msg,HTTP.UTF_8);//构造连接实体
		httpPost.setEntity(httpEntity);//设置连接实体
		if(httpPost!=null){
			response = getResponse(connectionTypeStr, httpPost);// 服务端响应对象
		}else{
			response = getResponse(connectionTypeStr, httpGet);// 服务端响应对象
		}
		//正常连接
		if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
			//将输入流转换成字符串
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
	 * 文件上传
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
				response = getResponse(connectionTypeStr, httpPost);// 服务端响应对象
			}else{
				response = getResponse(connectionTypeStr, httpGet);// 服务端响应对象
			}
			// 正常响应
			if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity resEntity  = response.getEntity();
				   if (resEntity != null) {
				      result= EntityUtils.toString(resEntity);
				      resEntity.consumeContent();
				    }
			}else{
				result="fileFailure";//文件上传失败
			}
			return result;
	}
	
	
	/**
	 * 方法名：getFileStream
	 * 功能描述：从服务端下载文件
	 * 输入参数：fileNameStr,文件名
	 * 输入参数：fileExtensionsStr,文件扩展名
	 * 异常：ClientProtocolException 客户端连接异常
	 * 异常：IOException，输入输出异常，它会导致ANR
	 * 异常：NullPointerException，空异常，它会导致ANR
	 * 返回值：文件，根据文件流在存储卡上所创建的文件
	 * 作者：zsg
	 * 创建日期：2011-01-04
	 */
	public File getFileStream(String fileLoacation,String fileNameStr) throws ClientProtocolException, NullPointerException, IOException,TimeoutException{
		if(httpPost!=null){
			response = getResponse(connectionTypeStr, httpPost);// 服务端响应对象
		}else{
			response = getResponse(connectionTypeStr, httpGet);// 服务端响应对象
		}
		File tempFile = new File(fileLoacation, fileNameStr);//创建文件
		//正常响应
		if (response!=null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream is = response.getEntity().getContent();
			// 创建文件流
			FileOutputStream fileStream = new FileOutputStream(tempFile);
			byte[] buf = new byte[HttpConnectionParams
					.getSocketBufferSize(httpParams)];
			int size = 0;
			// 将文件流转换成字节流
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
	* 项目名称：PFWX_PAD   
	* 类名称：IdleConnectionMonitorThread   
	* 类描述：ThreadSafeClientConnManager将会分配基于它的配置的连接收回策略
	* 创建人：zsg  
	* 创建时间：2012-8-14
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
						// 关闭过期连接
						connMgr.closeExpiredConnections();
						// 可选地，关闭空闲超过30秒的连接
//						connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
					}
				}
			} catch (InterruptedException ex) {
				// 终止
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
