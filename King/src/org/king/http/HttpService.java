package org.king.http;

import java.util.Map;

/**
 * Http·þÎñ
 * 
 * @author jenly
 * @date 2013-3-25	
 */
public interface HttpService {
	
   public enum HttpMethod {GET,POST}
	
	public final String GBK = "GBK";
	public final String UTF_8 = "UTF-8";
	public final String DEFAULT_ENCODING = "UTF-8";
	
	
	public void syncConnect(final String url,final HttpMethod method,final HttpServiceCallback callback);

	public void asyncConnect(final String url,final HttpMethod method,final HttpServiceCallback callback);
	
	public void syncConnect(final String url,final Map<String,String> params,final HttpMethod method,final HttpServiceCallback callback);
	
	public void asyncConnect(final String url,final Map<String, String> params,final HttpMethod method,final HttpServiceCallback callback);
	
}
