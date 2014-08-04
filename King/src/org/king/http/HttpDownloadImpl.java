package org.king.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.king.utils.DialogUtils;
import org.king.utils.FileUtils;
import org.king.utils.FileUtils.FileType;
import org.king.utils.LogUtils;
import org.king.utils.SystemUtils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;

/**
 * 下载的实现
 * 
 * @author jenly
 * @date 2013-3-28
 */
public class HttpDownloadImpl implements HttpDownload{
	
	private Context context;
	private Handler handler;
	
	protected int connectTimeout = 5000;
	protected int readTimeout = 5000;
	
	private boolean isCancel = false;
	private boolean isFinish = false;
	
	private final String CLAZZ = "HttpDownloadServiceImpl";
	
	//---------------------------------
	
	public HttpDownloadImpl(Context context,Handler handler){
		
		this.context = context;
		this.handler = handler;
	}
	
	public HttpDownloadImpl(Context context,Handler handler,int connectTimeout,int readTimeout){
		
		this.context = context;
		this.handler = handler;
		
		if(connectTimeout>0){
			this.connectTimeout = connectTimeout;
		}
		if(readTimeout>0){
			this.readTimeout = readTimeout;
		}
		
	}

	//---------------------------------
	
	@Override
	public void download(final String url,final FileType fileType, final String fileName,final HttpDownloadListener listener) {
		
		if(!SystemUtils.hasSDCard()){
			DialogUtils.showToast(context, "未找到存储卡！", handler);
			return;
		}
		
		
		final File file= FileUtils.newFile(fileName);
		
		if(file.exists()){
			DialogUtils.showAlertDialog(context, "文件已存在！是否重新下载？", "是", "否", handler, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						downloadFile(url,file,listener);
						break;

					default:
						listener.onFinish(file);
						break;
					}
				}
			});
			
		}else{
			File file1 = FileUtils.createNewFile(fileName);
			downloadFile(url,file1,listener);
		}
		
		
		
	}
	
	private void downloadFile(final String url, final File file,final HttpDownloadListener listener){
		LogUtils.logV("下载地址："+url);
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				InputStream is = null;
				FileOutputStream fos = null;
				
				try{
//					isCancel = false;
					LogUtils.logD(CLAZZ,"文件存储地址："+file.getAbsolutePath());
					
//					URLConnection connect = new URL(url).openConnection();
//					connect.setConnectTimeout(connectTimeout);
//					connect.setReadTimeout(readTimeout);
//					
//					is = connect.getInputStream();
					
					
					HttpGet httpRequest = new HttpGet(url);
					HttpClient httpclient = new DefaultHttpClient();
					HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
					
					int code = response.getStatusLine().getStatusCode();
					
					if(code==HttpStatus.SC_OK){
						
						HttpEntity entity = response.getEntity();
						BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
						is = bufferedHttpEntity.getContent();
						
						int sumSize = (int)bufferedHttpEntity.getContentLength();
//					int sumSize = is.available();
						int loadSize = 0;
						
						fos = new FileOutputStream(file);
						byte[] buffer = new byte[FileUtils.DEFAULT_BUFFER_SIZE];
						int len = 0;
						
						while(!isCancel&&(len = is.read(buffer))!=-1){
							fos.write(buffer,0,len);
							
							if(listener!=null){
								loadSize+=len;
								if(loadSize<is.available()){
									int progress = (sumSize/loadSize)*100;
									listener.onStatus(progress,"文件大小："+FileUtils.getFileSize(sumSize)+"已下载："+progress+"%"+"---"+loadSize+"/"+sumSize);
								}else{
									listener.onStatus(100,"下载完成！");
								}
							}
						}
						fos.flush();
						if(!isCancel){
							if(listener!=null){
								LogUtils.logI("下载成功");
								isFinish = true;
								listener.onFinish(file);
								
							}
						}else{
							LogUtils.logI("取消下载");
							listener.onError("取消下载");
						}
						
					}else{
						listener.onError("响应代码："+code);
						
					}
					
				}catch (Exception e) {
					LogUtils.logE(CLAZZ,"下载出错："+e.getMessage());
					listener.onError("下载出错："+e.getMessage());
				}finally{
						try{
							if(fos!=null){
								fos.close();
							}
							if(is!=null){
								is.close();
							}
						}catch (IOException e) {
							fos = null;
							is = null;
							LogUtils.logE(CLAZZ, "关闭流出错:"+e.getMessage());
						}
						
						if(!isFinish){
							try{
								if(file.exists()&&file.canWrite()){
									file.delete();
								}
							}catch(Exception e){
								LogUtils.logE(CLAZZ,"文件处理异常："+e.getMessage());
							}
						}
				}
				
			}
		}).start();
	}

	//---------------------------------
	
	@Override
	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
	
	
	

}
