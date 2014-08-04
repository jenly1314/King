package org.king.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.king.utils.LogUtils;

/**
 * 服务的实现
 *
 * @author jenly
 * @date 2013-3-25
 */
public class HttpServiceImpl implements HttpService {
	
	private HttpClient httpClient;
	
	public static final int CONNECTION_TIME_OUT = 10000;
	public static final int SO_TIME_OUT = 5000;
	
	//-----------------------------------------
	
	public HttpServiceImpl(){
		HttpClientInstance(CONNECTION_TIME_OUT,SO_TIME_OUT);
	}
	
	public HttpServiceImpl(int connectTimeOut,int soTimeOut){
		HttpClientInstance(connectTimeOut,soTimeOut);
	}
	
	private void HttpClientInstance(int connectionTimeOut,int soTimeOut){
		
		HttpParams httpParams = new BasicHttpParams();
		
		HttpConnectionParams.setConnectionTimeout(httpParams,connectionTimeOut);
		HttpConnectionParams.setSoTimeout(httpParams, soTimeOut);
		
		httpClient = new DefaultHttpClient(httpParams);
	}
	

	//-----------------------------------------
	
	@Override
	public void syncConnect(String url, HttpMethod method, HttpServiceCallback callback) {
		
		syncConnect(url, null,method, callback);
	}
	
	//-----------------------------------------
	@Override
	public void asyncConnect(String url, HttpMethod method, HttpServiceCallback callback) {
		
		asyncConnect(url, null,method, callback);
	}
	
	//-----------------------------------------
	@Override
	public void asyncConnect(final String url, final Map<String, String> params, final HttpMethod method,final HttpServiceCallback callback) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				syncConnect(url, params,method, callback);
			}
		}).start();
	}

	//-----------------------------------------
	@Override
	public void syncConnect(String url, Map<String, String> params, HttpMethod method, HttpServiceCallback callback) {
		LogUtils.logV(url);
		
		BufferedReader reader = null;
		
		try {
			callback.onConnect("建立连接...");
			HttpUriRequest httpUriRequest =getHttpUriRequest(url,params,method);
			HttpResponse httpResponse = httpClient.execute(httpUriRequest);
			
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				
				callback.onRead("读取数据...");
				
				reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
				
				StringBuilder builder = new StringBuilder();
				String buffer = null;
				while ((buffer = reader.readLine())!=null) {
					builder.append(buffer);
				}
				
				callback.onExecute("加载数据...", builder.toString());
				
			}else{
				callback.onError("响应代码："+httpResponse.getStatusLine().getStatusCode());
			}
			
		} catch (ClientProtocolException e) {
			
			callback.onError("客户端协议错误："+e.getMessage());
			
		} catch (IOException e) {
			if(e instanceof SocketTimeoutException){
				callback.onError("服务器连接或响应超时！");
			}else{
				callback.onError("IO异常："+e.getMessage());
			}
		}catch (Exception e) {
			callback.onError("异常："+e.getMessage());
		}finally{
			
			try {
				
				if(reader!=null){
					reader.close();
				}
				
			} catch (IOException e) {
				reader = null;
			}
			
			callback.onFinish();
		}
		
	}
	
	//-----------------------------------------
	
	private HttpUriRequest getHttpUriRequest(String url,Map<String, String> params,HttpMethod method){
		
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		if(params!=null){
			for(String key : params.keySet()){
				list.add(new BasicNameValuePair(key,params.get(key)));
			}
		}
		
		if(HttpMethod.POST.equals(method)){//POST
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,DEFAULT_ENCODING);
				HttpPost requestPost = new HttpPost(url);
				requestPost.setEntity(entity);
				return requestPost;
			} catch (UnsupportedEncodingException e) {
				
				throw new RuntimeException(e.getMessage(),e);
//				e.printStackTrace();
				
			}
		}else{//GET
			
			if(url.indexOf('?')==-1){
				url += "?";
			}
			
			url += URLEncodedUtils.format(list,DEFAULT_ENCODING);
			
			HttpGet requestGet = new HttpGet(url);
			
			return requestGet;
		}

	}
	
}
