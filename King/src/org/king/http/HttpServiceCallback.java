package org.king.http;

/**
 * 回调接口
 * 
 * @author jenly
 * @date 2013-3-25
 */

public interface HttpServiceCallback {
	
	
	public void onConnect(final String message);
	
	public void onRead(final String message);
	
	public void onExecute(final String message,final String response);
	
	public void onFinish();
	
	public void onError(final String error);

}
