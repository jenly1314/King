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
 * 图片异步加载
 * @author Jenly
 * @date 2013-6-5
 *
 */
@SuppressLint("HandlerLeak")
public class ImageLoaderByTag {
	
	private HashMap<String,SoftReference<Drawable>> imageCache;//使用软引用，可以由系统在恰当的时候更容易回收
	
	public ImageLoaderByTag(){
		imageCache = new HashMap<String,SoftReference<Drawable>>();
	}
	
	
	/**
	 * 通过url获取图片
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
	 * 回调接口
	 *
	 */
	public interface ImageCallback{
		
		public void Image(TagInfo tagInfo,Drawable drawable,boolean isFirst);
		
	}
	
	

}
