package org.king.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;

/**
 * 系统工具类
 * 
 * @author Jenly
 * @date 2013-3-25
 */
public class SystemUtils {
	
	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 时间格式化
	 * @param time
	 * @return
	 */
	public static String DateFormat(long time){
		return  sdf.format(time);
	}
	
	public static String DateFormat(Calendar cal){
		return  sdf.format(cal.getTime());
	}
	
	public static String DateFormat(Date date){
		return  sdf.format(date.getTime());
	}
	
	/**
	 * 拍照
	 * @param activity
	 * @param requestCode
	 * @param fileFullPath
	 * 
	 * <uses-permission android:name="android.permission.CAMERA" />
	 */
	public static void imageCapture(Activity activity,int requestCode,String fileFullPath){
		if(StringUtils.getString(fileFullPath)==null){
			return;
		}
		String path = fileFullPath.substring(0, fileFullPath.lastIndexOf('/')+1);
		String fileName = fileFullPath.substring(fileFullPath.lastIndexOf('/')+1);
		imageCapture(activity, requestCode, path, fileName);
		
	}
	
	public static void imageCapture(Activity activity,int requestCode,String path,String fileName){
		
		LogUtils.logI("路径："+path+"文件名："+fileName);
		
		if(path.indexOf('/')==-1){
			path+='/';
		}
		File dirFile = new File(path);
		if(!dirFile.exists()){
			FileUtils.createNewDir(path);
			LogUtils.logI("创建路径："+path);
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(path+fileName);
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(intent,requestCode);
	}
	
	/**
	 * 录视频
	 * @param activity
	 * @param requestCode
	 * @param fileFullPath
	 * 
	 * <uses-permission android:name="android.permission.CAMERA" />
	 */
	public static void videoCapture(Activity activity,int requestCode,String fileFullPath){
		if(StringUtils.getString(fileFullPath)==null){
			return;
		}
		String path = fileFullPath.substring(0, fileFullPath.lastIndexOf('/')+1);
		String fileName = fileFullPath.substring(fileFullPath.lastIndexOf('/')+1);
		videoCapture(activity, requestCode, path, fileName);
		
	}
	
	public static void videoCapture(Activity activity,int requestCode,String path,String fileName){
		
		if(path.indexOf('/')==-1){
			path+='/';
		}
		File dirFile = new File(path);
		if(!dirFile.exists()){
			FileUtils.createNewDir(path);
		}
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		File file = new File(path+fileName);
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(intent,requestCode);
	}
	
	/**
	 * 打电话
	 * @param context
	 * @param phoneNumber
	 * 
	 * <uses-permission android:name="android.permission.CALL_PHONE"/>
	 */
	public static void call(Context context,String phoneNumber){

		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));
		
		context.startActivity(intent);
		
	}
	
	/**
	 * 调用发短信界面
	 * @param context
	 * @param phoneNumber
	 */
	public static void sendSMS(Context context,String phoneNumber){
		
		Intent intent = new Intent();
		
		intent.setAction(Intent.ACTION_SENDTO);
		
		intent.setData(Uri.parse("smsto:"+phoneNumber));
		
		context.startActivity(intent);
	}
	
	/**
	 * 发短信
	 * @param context
	 * @param phoneNumber
	 * @param msg
	 * 
	 * <uses-permission android:name="android.permission.SEND_SMS"/>
	 */
	public static void sendSMS(String phoneNumber,String msg){
		
		SmsManager sm = SmsManager.getDefault();
		
		List<String> msgs = sm.divideMessage(msg);
		
		for(String text:msgs){
			sm.sendTextMessage(phoneNumber, null, text,null, null);
		}
		
	}
	
	/**
	 * 判断是否有存储卡
	 * @return
	 */
	public static boolean hasSDCard(){
		
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}
	
	/**
	 * 判断是否是wifi网络
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context){
		
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		if(networkInfo!=null&&ConnectivityManager.TYPE_WIFI==networkInfo.getType()){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否启用wifi网络
	 * @param context
	 * @return
	 * 
	 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
	 */
	public static boolean isWifiEnabled(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}
	
	/**
	 * 设置wifi的状态
	 * @param context
	 * @param isEnabled
	 */
	public static void setWifiEnabled(Context context,boolean isEnabled){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(isEnabled);
	}
	
	/**
	 * 获取包的信息
	 * @param context
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context){
		
		PackageManager manager = context.getPackageManager();
		
		try {
			return manager.getPackageInfo(context.getPackageName(),0);
		} catch (NameNotFoundException e) {
			LogUtils.logE("获取包信息出错："+e.getMessage());
		}
		
		return null;
	}
	
	
	
	/**
	 * 安装apk
	 * @param context
	 * @param file
	 */
	public static void installApk(Context context,File file){
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		
		Uri uriData = Uri.fromFile(file);
		String type = "application/vnd.android.package-archive";
		
		intent.setDataAndType(uriData, type);
		context.startActivity(intent);
	}
	
	/**
	 * 卸载apk
	 * @param context
	 * @param packageName
	 */
	public static void uninstallApk(Context context,String packageName){
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		
		Uri uriData = Uri.parse("package:" + packageName);
		intent.setData(uriData);
		context.startActivity(intent);
	}
	
	
	public static void uninstallApk(Context context){
		uninstallApk(context,context.getPackageName());
	}
	
	
	/**
	 * 执行命令（最好开启一个线程）
	 * @param cmd
	 * @return
	 */
	public static String execCommand(String cmd){
		
		StringBuilder sb = null;
		BufferedReader br = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			sb = new StringBuilder();
			String temp=null;
			while((temp = br.readLine())!=null){
				sb.append(temp+"\n");
			}
			process.waitFor();
		} catch (IOException e) {
			LogUtils.logE("异常："+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(br!=null){
					br.close();
				}
				if(process!=null)
				process.destroy();
			} catch (IOException e) {
				LogUtils.logE("IO异常!");
			}
		}
		
		return sb.toString(); 
	}
	/**
	 * 请求ROOT权限后执行命令（最好开启一个线程）
	 * @param cmd	(pm install -r *.apk)
	 * @return
	 */
	public static boolean runRootCommand(String cmd) { 
        Process process = null; 
        DataOutputStream os = null; 
		BufferedReader br = null;
		StringBuilder sb = null;
            try { 
            process = Runtime.getRuntime().exec("su"); 
            os = new DataOutputStream(process.getOutputStream()); 
            os.writeBytes(cmd+"\n"); 
            os.writeBytes("exit\n"); 
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			sb = new StringBuilder();
			String temp=null;
			while((temp = br.readLine())!=null){
				sb.append(temp+"\n");
				if("Success".equalsIgnoreCase(temp)){
					LogUtils.logE("----------"+sb.toString());
					return true; 
				}
			}
            process.waitFor(); 
            } catch (Exception e) { 
                    LogUtils.logE("异常: "+e.getMessage()); 
            }finally { 
                try { 
                    if (os != null) { 
                    	os.flush(); 
                        os.close(); 
                    } 
                    if(br!=null){
                    	br.close();
                    }
                    process.destroy(); 
                } catch (Exception e) { 
                       
                	return false; 
                } 
            } 
            return false; 
    } 
	
	
} 
	

