package org.king.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;

/**
 * 图片工具类
 * 
 * @author Jenly
 * @date 2013-6-25
 */
public class ImageUtils {
	
	/**
	 * 图片压缩
	 * @param savePath
	 * @return
	 */
	public static File ImageCompress(Bitmap bitmap,String savePath,int newWidth,int newHeight) {
		
		// 通过缩放得到新的图片
		Bitmap resizedBitmap = zoomImage(bitmap, newWidth, newHeight);


		// 将图片 保存到传入的路径
		File file = new File(savePath);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new File(savePath);
		} finally {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
			if (resizedBitmap != null && !resizedBitmap.isRecycled()) {
				resizedBitmap.recycle();
			}
		}
		return file;
	}
	/**
	 * 图片压缩
	 * @param picPath
	 * @return
	 */
	public static File ImageCompress(String picPath,int newWidth,int newHeight) {
		
		Bitmap bitmap = BitmapFactory.decodeFile(picPath);
		// 通过缩放得到新的图片
		Bitmap resizedBitmap = zoomImage(bitmap, newWidth, newHeight);
		
		
		// 将图片 保存到传入的路径
		File file = new File(picPath);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new File(picPath);
		} finally {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
			if (resizedBitmap != null && !resizedBitmap.isRecycled()) {
				resizedBitmap.recycle();
			}
		}
		return file;
	}
	
	
	/**
	 * 图片压缩
	 * @param savePath
	 * @return
	 */
	public static File imageCompress(Bitmap bitmap,String savePath,float scale)throws Exception {
		
		// 通过缩放得到新的图片
		Bitmap resizedBitmap = zoomImageFromScale(bitmap, scale);

		
		// 将图片 保存到传入的路径
		File file = new File(savePath);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			return new File(savePath);
		} finally {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
			if (resizedBitmap != null && !resizedBitmap.isRecycled()) {
				resizedBitmap.recycle();
			}
		}
		return file;
	}
	/**
	 * 图片压缩
	 * @param picPath
	 * @return
	 */
	public static File imageCompress(String picPath,float scale)throws Exception {
		
		Bitmap bitmap = BitmapFactory.decodeFile(picPath);
		// 通过缩放得到新的图片
		Bitmap resizedBitmap = zoomImageFromScale(bitmap, scale);
		
		
		// 将图片 保存到传入的路径
		File file = new File(picPath);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			return new File(picPath);
		} finally {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
			if (resizedBitmap != null && !resizedBitmap.isRecycled()) {
				resizedBitmap.recycle();
			}
		}
		return file;
	}
	
	
	
	
	
	/**
	 * 缩放照片（宽高缩放）
	 * @param bitmap
	 * @param newWidth
	 * @param newHeight
	 * @return 缩放后的照片
	 */
	public static Bitmap zoomImage(Bitmap bitmap,float newWidth,float newHeight){
		
		//图片的原宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		//缩放比例
		float scaleWidth = newWidth/width;
		float scaleHeight = newHeight/height;
		
		Matrix matrix = new Matrix();
		//比率缩放
		matrix.postScale(scaleWidth, scaleHeight);
		
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
		
		return bmp;
	}
	
	/**
	 * 缩放照片（比率缩放）
	 * @param bitmap
	 * @param scaleWidth
	 * @param scaleHeight
	 * @return 缩放后的照片
	 */
	public static Bitmap zoomImageFromScale(Bitmap bitmap,float scale){
		
		//图片的原宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		// 缩放图片动作
		Matrix matrix = new Matrix();
		//比率缩放
		matrix.postScale(scale, scale);
		
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
		
		return bmp;
	}
	
	/**
	 * 倒影图片效果
	 * @param originalImage
	 * @return
	 */
    public static Bitmap createMirrorImage(Bitmap originalImage)  {  
    	
        int width = originalImage.getWidth();  
        int height = originalImage.getHeight();
        //反射倒影图片的高度
        int mirrorHeight = height>>1;
        
        Matrix matrix = new Matrix();  
        // 实现图片翻转90度  
        matrix.preScale(1, -1);  
        // 创建倒影图片（是原始图片的一半大小）  
        Bitmap mirrorImage = Bitmap.createBitmap(originalImage, 0,mirrorHeight , width, mirrorHeight, matrix, false);  
        // 创建总图片（原图片 + 倒影图片+中间间隔1）  
        Bitmap finalBitmap = Bitmap.createBitmap(width, height + mirrorHeight + 1, Config.ARGB_8888);  
        // 创建画布  
        Canvas canvas = new Canvas(finalBitmap);  
        canvas.drawBitmap(originalImage, 0, 0, null);  
        //把倒影图片画到画布上  （中间间隔1感觉更真实）
        canvas.drawBitmap(mirrorImage, 0, height + 1, null);  
        Paint shaderPaint = new Paint();  
        //创建线性渐变LinearGradient对象  
        LinearGradient shader = new LinearGradient(0, height + 1, 0, finalBitmap.getHeight(), 0x7fffffff,  
                0x00ffffff, TileMode.MIRROR);  

        shaderPaint.setShader(shader);  
        
        shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));  
        //画布画出反转图片大小区域、然后把渐变效果加到其中、就出现了图片的倒影效果、  
        canvas.drawRect(0, height + 1, width, finalBitmap.getHeight(), shaderPaint);
        
        return finalBitmap;  
    }
    
    
    /**
     * 获取网络图片
     * @param url
     * @return
     */
	public static Bitmap getBitmap(String url){
		
		Bitmap bitmap = null;
		
		try {
			URL ur = new URL(url);
			
			HttpURLConnection conn = (HttpURLConnection)ur.openConnection();
			
			InputStream is = conn.getInputStream();
			
			bitmap = BitmapFactory.decodeStream(is);
		} catch (MalformedURLException e) {
			LogUtils.logE("异常："+e.getMessage());
		} catch (IOException e) {
			LogUtils.logE("异常："+e.getMessage());
		}
		
		return bitmap;
	}
	
	
	
}
