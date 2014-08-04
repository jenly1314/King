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
 * ͼƬ������
 * 
 * @author Jenly
 * @date 2013-6-25
 */
public class ImageUtils {
	
	/**
	 * ͼƬѹ��
	 * @param savePath
	 * @return
	 */
	public static File ImageCompress(Bitmap bitmap,String savePath,int newWidth,int newHeight) {
		
		// ͨ�����ŵõ��µ�ͼƬ
		Bitmap resizedBitmap = zoomImage(bitmap, newWidth, newHeight);


		// ��ͼƬ ���浽�����·��
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
	 * ͼƬѹ��
	 * @param picPath
	 * @return
	 */
	public static File ImageCompress(String picPath,int newWidth,int newHeight) {
		
		Bitmap bitmap = BitmapFactory.decodeFile(picPath);
		// ͨ�����ŵõ��µ�ͼƬ
		Bitmap resizedBitmap = zoomImage(bitmap, newWidth, newHeight);
		
		
		// ��ͼƬ ���浽�����·��
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
	 * ͼƬѹ��
	 * @param savePath
	 * @return
	 */
	public static File imageCompress(Bitmap bitmap,String savePath,float scale)throws Exception {
		
		// ͨ�����ŵõ��µ�ͼƬ
		Bitmap resizedBitmap = zoomImageFromScale(bitmap, scale);

		
		// ��ͼƬ ���浽�����·��
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
	 * ͼƬѹ��
	 * @param picPath
	 * @return
	 */
	public static File imageCompress(String picPath,float scale)throws Exception {
		
		Bitmap bitmap = BitmapFactory.decodeFile(picPath);
		// ͨ�����ŵõ��µ�ͼƬ
		Bitmap resizedBitmap = zoomImageFromScale(bitmap, scale);
		
		
		// ��ͼƬ ���浽�����·��
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
	 * ������Ƭ��������ţ�
	 * @param bitmap
	 * @param newWidth
	 * @param newHeight
	 * @return ���ź����Ƭ
	 */
	public static Bitmap zoomImage(Bitmap bitmap,float newWidth,float newHeight){
		
		//ͼƬ��ԭ���
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		//���ű���
		float scaleWidth = newWidth/width;
		float scaleHeight = newHeight/height;
		
		Matrix matrix = new Matrix();
		//��������
		matrix.postScale(scaleWidth, scaleHeight);
		
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
		
		return bmp;
	}
	
	/**
	 * ������Ƭ���������ţ�
	 * @param bitmap
	 * @param scaleWidth
	 * @param scaleHeight
	 * @return ���ź����Ƭ
	 */
	public static Bitmap zoomImageFromScale(Bitmap bitmap,float scale){
		
		//ͼƬ��ԭ���
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		// ����ͼƬ����
		Matrix matrix = new Matrix();
		//��������
		matrix.postScale(scale, scale);
		
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
		
		return bmp;
	}
	
	/**
	 * ��ӰͼƬЧ��
	 * @param originalImage
	 * @return
	 */
    public static Bitmap createMirrorImage(Bitmap originalImage)  {  
    	
        int width = originalImage.getWidth();  
        int height = originalImage.getHeight();
        //���䵹ӰͼƬ�ĸ߶�
        int mirrorHeight = height>>1;
        
        Matrix matrix = new Matrix();  
        // ʵ��ͼƬ��ת90��  
        matrix.preScale(1, -1);  
        // ������ӰͼƬ����ԭʼͼƬ��һ���С��  
        Bitmap mirrorImage = Bitmap.createBitmap(originalImage, 0,mirrorHeight , width, mirrorHeight, matrix, false);  
        // ������ͼƬ��ԭͼƬ + ��ӰͼƬ+�м���1��  
        Bitmap finalBitmap = Bitmap.createBitmap(width, height + mirrorHeight + 1, Config.ARGB_8888);  
        // ��������  
        Canvas canvas = new Canvas(finalBitmap);  
        canvas.drawBitmap(originalImage, 0, 0, null);  
        //�ѵ�ӰͼƬ����������  ���м���1�о�����ʵ��
        canvas.drawBitmap(mirrorImage, 0, height + 1, null);  
        Paint shaderPaint = new Paint();  
        //�������Խ���LinearGradient����  
        LinearGradient shader = new LinearGradient(0, height + 1, 0, finalBitmap.getHeight(), 0x7fffffff,  
                0x00ffffff, TileMode.MIRROR);  

        shaderPaint.setShader(shader);  
        
        shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));  
        //����������תͼƬ��С����Ȼ��ѽ���Ч���ӵ����С��ͳ�����ͼƬ�ĵ�ӰЧ����  
        canvas.drawRect(0, height + 1, width, finalBitmap.getHeight(), shaderPaint);
        
        return finalBitmap;  
    }
    
    
    /**
     * ��ȡ����ͼƬ
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
			LogUtils.logE("�쳣��"+e.getMessage());
		} catch (IOException e) {
			LogUtils.logE("�쳣��"+e.getMessage());
		}
		
		return bitmap;
	}
	
	
	
}
