package org.king.utils;


import android.view.animation.Animation;

import android.view.animation.AlphaAnimation;//透明
import android.view.animation.RotateAnimation;//旋转
import android.view.animation.ScaleAnimation;//缩放
import android.view.animation.TranslateAnimation;//位移

/**
 * 过场动画工具类
 * 
 * @author Jenly
 * @date 2012-9-1
 * 
 */
public class AnimationUtils {
	
	/**
	 * 位移
	 * @return
	 */
	public static Animation translate(int fromX,int toX,int fromY,int toY){
		Animation am = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, fromX,
				Animation.RELATIVE_TO_PARENT, toX, Animation.RELATIVE_TO_PARENT,
				fromY, Animation.RELATIVE_TO_PARENT, toY);
		
		am.setDuration(2000);
		
		return am;
	}
	
	
	/**
	 * 向上位移
	 * @return
	 */
	public static Animation translateUp(){
		Animation am = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				1, Animation.RELATIVE_TO_PARENT, 0);
		
		am.setDuration(2000);
		
		return am;
	}
	
	/**
	 * 向下位移
	 * @return
	 */
	public static Animation translateBelow(){
		Animation am = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 1);
		
		am.setDuration(2000);
		
		return am;
	}

	/**
	 * 向左位移
	 * @return
	 */
	public static Animation translateLeft(){
		Animation am = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 0);
		
		am.setDuration(2000);
		
		return am;
	}
	
	
	
	/**
	 * 向右位移
	 * @return
	 */
	public static Animation translateRight(){
		Animation am = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 0);
		
		am.setDuration(2000);
		
		return am;
	}
	
	/**
	 * 缩放
	 * @param fromX
	 * @param toX
	 * @param fromY
	 * @param toY
	 * @param time
	 * @return
	 */
	public static Animation ratio(float fromX,float toX,float fromY,float toY,int time){
		
		Animation am = new ScaleAnimation(fromX,toX,fromY,toX);
		
		am.setDuration(time);
		
		return am;
	}
	
	/**
	 * 缩放Y（缩小）
	 * @return
	 */
	public static Animation ratio0(){
		
		Animation am = new ScaleAnimation(1,1,1,0);
		
		am.setDuration(2000);
		
		return am;
	}
	
	/**
	 * 缩放Y（还原）
	 * @return
	 */
	public static Animation ratio1(){
		
		Animation am = new ScaleAnimation(1,1,0,1);
		
		am.setDuration(2000);
		
		return am;
	}
	
	
	
	/**
	 * 透明度
	 * @return
	 */
	public static Animation alpha(float from,float to, int time){
		
		Animation am = new AlphaAnimation(from,to);
		
		am.setDuration(time);
		
		return am;
	}
	
	/**
	 * 旋转
	 * @return
	 */
	public static Animation rotate(int fromRadian,int toRadian){ 
		
		Animation am = new RotateAnimation(fromRadian, toRadian, 1, 1);
		
		am.setDuration(3000);
		
		return am;
		
	}
	
	
}
