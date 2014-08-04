package org.king.http;

import java.io.File;
import java.util.Map;

import org.king.entity.ResultInfo;

/**
 * 上传接口
 * 
 * @author jenly
 * @date 2013-3-28
 */
public interface HttpUpload {
	
	interface HttpUploadListener {
		
		void onStatus(String message);
		
		void onFinish(ResultInfo resultInfo);
		
		void onError(String error);
		
	}
	
	public void upload(String url,Map<String, String> params,Map<String,File> files,HttpUploadListener listener);

	public void setCancel(boolean isCancel);
}
