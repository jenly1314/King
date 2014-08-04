package org.king.http;

import java.io.File;

import org.king.utils.FileUtils.FileType;

/**
 * обть╫с©з
 * 
 * @author jenly
 * @date 2013-3-27
 */
public interface HttpDownload {
	
	interface HttpDownloadListener{
		
		void onStatus(int progress,String progressMsg);
		
		void onFinish(File file);
		
		void onError(String msg);
	}
	
	public void download(String url,FileType fileType,String fileName,HttpDownloadListener listener);
	
	public void setCancel(boolean isCancel);

}
