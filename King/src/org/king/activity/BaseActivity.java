package org.king.activity;


import org.king.R;
import org.king.utils.DialogUtils;
import org.king.utils.LogUtils;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * 基本活动类
 * @author Jenly
 *
 */
public class BaseActivity extends Activity{
	
	/**
	 * 上下文
	 */
	protected Context context = this;
	
	/**
	 * 处理者
	 */
	protected Handler handler = new Handler();
	
	/**
	 * 进度条提示框
	 */
	protected ProgressDialog progressDialog = null;
	
	/**
	 * 是否结束
	 */
	protected boolean isFinish = false;
	
	/**
	 * 是否注册退出广播
	 */
	protected boolean isRegister = false;
	
	/**
	 * 是否显示标题
	 */
	protected static boolean isShowTitle = false;
	
	/**
	 * 是否显示过度动画 
	 */
	protected static boolean isTransition = false;
	/**
	 * 退出隐式意图行为
	 */
	private static final String ACTION_EXIT = "jenly_action_exit_";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!isShowTitle){
			//去除标题栏
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		
	}
	
	/**
	 * 返回按钮监听接口实例
	 */
	protected OnClickListener onBackListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	//-------------------------------------------------
	/**
	 * 弹出框
	 * @param message
	 */
	protected void showToast(String message){
		DialogUtils.showToast(context, message,handler);
	}
	
	protected void showToast(String message,int duration){
		DialogUtils.showToast(context, message,duration,handler);
	}
	//-------------------------------------------------
	/**
	 * 进度提示框
	 */
	//-------------------
	protected void showProgressDialog(){
		progressDialog = DialogUtils.showProgressDialog(context);
	}
	//-------------------
	protected void showProgressDialog(boolean isCancelable){
		progressDialog = DialogUtils.showProgressDialog(context,isCancelable);
	}
	//-------------------
	protected void showProgressDialog(String message){
		progressDialog = DialogUtils.showProgressDialog(context, message,false);
	}
	//-------------------
	protected void showProgressDialog(String message,boolean isCancelable){
		progressDialog = DialogUtils.showProgressDialog(context, message,isCancelable);
	}
	//-------------------
	protected void dismissProgressDialog(){
		DialogUtils.dismissProgressDialog(progressDialog, handler);
	}
	
	//-------------------------------------------------
	/**
	 * 日志
	 * @param message
	 */
	//-------------------
	protected void logI(String message){
		LogUtils.logI(message);
	}
	//-------------------
	protected void logV(String message){
		LogUtils.logV(message);
	}
	//-------------------
	protected void logD(String message){
		LogUtils.logD(message);
	}
	//-------------------
	protected void logW(String message){
		LogUtils.logW(message);
	}
	//-------------------
	protected void logE(String message){
		LogUtils.logE(message);
	}
	//-------------------
	protected void print(String message){
		LogUtils.print(message);
	}
	//-------------------
	protected void println(String message){
		LogUtils.println(message);
	}
	
	//-------------------------------------------------
	/**
	 * 启动活动类
	 * @param clazz
	 */
	//-------------------
	protected void startActivity(Class<?> clazz){
		startActivity(new Intent(context,clazz));
	}
	//-------------------
	protected void startActivity(Class<?> clazz,int flags){
		Intent intent = new Intent(context,clazz);
		intent.setFlags(flags);
		startActivity(intent);
	}
	
	//-------------------
	protected void startActivityFinish(Class<?> clazz){
		startActivity(new Intent(context,clazz));
		super.finish();
		
	}
	//-------------------
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		if(isTransition)
		overridePendingTransition(R.anim.from_right_in,R.anim.to_left_out);
	}
	@Override
	public void finish() {
		super.finish();
		if(isTransition)
		overridePendingTransition(R.anim.from_left_in,R.anim.to_right_out);
	}
	
	//-------------------------------------------------
	/**
	 * 发送退出广播
	 */
	protected void exit(){
		Intent intent = new Intent(ACTION_EXIT);
		sendBroadcast(intent);
	}
	
	/**
	 * 退出对话框
	 */
	protected void exitDialog(){
		DialogUtils.showAlertDialog(context, "确定退出吗？", "确定","取消", handler,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					exit();
					break;

				default:
					break;
				}
				
			}
		});
	}
	
	/**
	 * 退出广播
	 */
	private BroadcastReceiver exitReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			isFinish = true;
			//接到广播退出
			finish();
		}
	};
	
	
	protected void onResume() {
		super.onResume();
		try{
			if(!isRegister){
				IntentFilter filter = new IntentFilter();
				filter.addAction(ACTION_EXIT);
				//注册广播
				registerReceiver(exitReceiver, filter);
				isRegister = true;
			}
		}catch(Exception e){
			logE("异常："+e.getMessage());
		}
		
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(isFinish && isRegister && exitReceiver!=null){
			//注销广播
			unregisterReceiver(exitReceiver);
			isRegister = false;
		}
			
	}
	
	
	

}
