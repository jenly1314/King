package org.king.http;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

import org.king.utils.LogUtils;

import org.king.entity.TagInfo;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;


/**
 * ͼƬ�첽����
 * @author Jenly
 * @date 2013-6-5
 *
 */
@SuppressLint("HandlerLeak")
public class ImageLoaderByTag {
	
	private HashMap<String,SoftReference<Drawable>> imageCache;//ʹ�������ã�������ϵͳ��ǡ����ʱ������׻���
	
	public ImageLoaderByTag(){
		imageCache = new HashMap<String,SoftReference<Drawable>>();
	}
	
	
	/**
	 * ͨ��url��ȡͼƬ
	 * @param url
	 * @return
	 */
	public Drawable getDrawable(String url){
		
		 Drawable drawable = null;
		 URL request; 
		 InputStream is;
		 try{
			 request = new URL(url);
			 is = (InputStream)request.getContent();
			 drawable = Drawable.createFromStream(is, "src");
			 
		 }catch(Exception e){
			 drawable = null;
			 LogUtils.logE(e.getMessage());
		 }catch(OutOfMemoryError e){
			 LogUtils.logE("����"+e.getMessage());
			 drawable = null;
			 System.gc();
		 }
		  
		return drawable;
	}
	
	/**
	 * �첽����ͼƬ
	 * @param url
	 * @param imageCallback
	 */
	public void asynLoadDrawable(final TagInfo tagInfo,final ImageCallback imageCallback){
		
		
		Drawable drawable = null;
		if(imageCache.containsKey(tagInfo.getUrl())){
			drawable = imageCache.get(tagInfo.getUrl()).get();
			if(null!=drawable){
				imageCallback.Image(tagInfo,drawable,false);
				return;
			}
		}
		
		
		final Handler handler = new Handler(){
			
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				Drawable drawable = (Drawable)msg.obj;
				if(drawable!=null){
					imageCache.put(tagInfo.getUrl(), new SoftReference<Drawable>(drawable));
					TagInfo tag = tagInfo;
					tag.setDrawable(drawable);
					imageCallback.Image(tag,drawable,true);
					
				}
				
			}
		};
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				handler.obtainMessage(1, getDrawable(tagInfo.getUrl())).sendToTarget();
				
			}
		}).start();
		
		
	}
	
	
	/**
	 * �ص��ӿ�
	 *
	 */
	public interface ImageCallback{
		
		public void Image(TagInfo tagInfo,Drawable drawable,boolean isFirst);
		
	}
	
	

}
