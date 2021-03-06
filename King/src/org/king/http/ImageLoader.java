package org.king.http;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.king.utils.LogUtils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;


/**
 * 图片异步加载
 * @author Jenly
 * @date 2013-6-5
 *
 */
public class ImageLoader {
	
	private Map<String, SoftReference<Drawable>> imageCache;//使用软引用，可以由系统在恰当的时候更容易回收
	
	public ImageLoader(){
		imageCache = Collections.synchronizedMap(new HashMap<String,SoftReference<Drawable>>());
	}
	
	
	/**
	 * 通过url获取图片
	 * @param url
	 * @return
	 */
	public Drawable getDrawable(String url){
		
		Drawable drawable = null;
		
		try{
			drawable = imageCache.get(url).get();
		}catch(NullPointerException e){
			 LogUtils.logE("异常："+e.getMessage());
		}
		if(null!=drawable){
			return drawable;
		}
		 URL request; 
		 InputStream is;
		 try{
			 request = new URL(url);
			 is = (InputStream)request.getContent();
			 drawable = Drawable.createFromStream(is, "src");
			 
		 }catch(Exception e){
			 drawable = null;
			 LogUtils.logE("异常："+e.getMessage());
		 }catch(OutOfMemoryError e){
			 LogUtils.logE("错误："+e.getMessage());
			 drawable = null;
			 System.gc();
		 }
		  
		return drawable;
	}
	
	/**
	 * 异步加载图片
	 * @param url
	 * @param imageCallback
	 */
	public Drawable asynLoadDrawable(final String url,final ImageCallback imageCallback){
		
		Drawable drawable = null;
		
		if(imageCache.containsKey(url)){
			drawable = imageCache.get(url).get();
			if(null!=drawable){
//				imageCallback.Image(url,drawable);
				return drawable;
			}
		}
		
		
		final Handler handler = new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				Drawable drawable = (Drawable)msg.obj;
				if(drawable!=null){
					imageCache.put(url, new SoftReference<Drawable>(drawable));
					imageCallback.Image(url,drawable);
					
				}
				
			}
		};
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				handler.obtainMessage(1, getDrawable(url)).sendToTarget();
				
			}
		}).start();
		
		return null;
	}
	
	
	/**
	 * 回调接口
	 *
	 */
	public interface ImageCallback{
		
		public void Image(String url,Drawable drawable);
		
	}

}
