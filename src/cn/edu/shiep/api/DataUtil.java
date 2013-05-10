package cn.edu.shiep.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.shiep.utils.ConstantUtil;



public class DataUtil {
	
	private DataUtil(){}

	public static JSONObject sendPostRequest(String path, JSONObject data, String enc) throws Exception{
		JSONObject result = new JSONObject();
		result.put("head", "0");
		result.put("body", "");
		
		byte[] entitydata = data.toString().getBytes();//得到实体的二进制数据
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entitydata);
		outStream.flush();
		outStream.close();
		InputStream in = null;
		int length = -1;
		byte[] buffer = new byte[1024];
		StringBuffer sb = new StringBuffer();
		if(conn.getResponseCode()==200){
			in = conn.getInputStream();
			while((length=in.read(buffer))!=-1){
				sb.append(new String(buffer,0,length));
			}
			//System.out.println(sb.toString());
			//result = JSONObject.fromObject(sb.toString());
			result = new JSONObject(sb.toString());
			//System.out.println(result.get("head"));
			return result;
		}
		return result;
	}
	public static JSONObject http(JSONObject data, String method) throws Exception{
		JSONObject result = new JSONObject();
		result.put("head", "0");
		result.put("body", "");
		JSONObject params = new JSONObject();
		
		params = convert(method, data);
		
		String sbs = URLEncoder.encode(params.toString(), "utf-8");
		byte[] entitydata =sbs.getBytes();//得到实体的二进制数据
		URL url = new URL(ConstantUtil.URL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setReadTimeout(10*1000);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		conn.setDoInput(true);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entitydata);
		outStream.flush();
		outStream.close();
		InputStream in = null;
		int length = -1;
		byte[] buffer = new byte[1024];
		StringBuffer sb = new StringBuffer();
		if(conn.getResponseCode()==200){
			in = conn.getInputStream();
			while((length=in.read(buffer))!=-1){
				sb.append(new String(buffer,0,length));
			}
			String sbstring =  URLDecoder.decode(sb.toString(), "UTF-8");
			result = new JSONObject(sbstring);
			return result;
		}
		return result;
	}
	
	public static JSONObject convert(String method,JSONObject data) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		JSONObject head = new JSONObject();
		head.put("method", method);
		JSONObject body = new JSONObject();
		body.put("data", data);
		jsonObject.put("head", head);
		jsonObject.put("body", body);
		return jsonObject;
	}
}
