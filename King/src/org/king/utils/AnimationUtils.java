package org.king.utils;


import android.view.animation.Animation;

import android.view.animation.AlphaAnimation;//͸��
import android.view.animation.RotateAnimation;//��ת
import android.view.animation.ScaleAnimation;//����
import android.view.animation.TranslateAnimation;//λ��

/**
 * ��������������
 * 
 * @author Jenly
 * @date 2012-9-1
 * 
 */
public class AnimationUtils {
	
	/**
	 * λ��
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
	 * ����λ��
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
	 * ����λ��
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
	 * ����λ��
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
	 * ����λ��
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
	 * ����
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
	 * ����Y����С��
	 * @return
	 */
	public static Animation ratio0(){
		
		Animation am = new ScaleAnimation(1,1,1,0);
		
		am.setDuration(2000);
		
		return am;
	}
	
	/**
	 * ����Y����ԭ��
	 * @return
	 */
	public static Animation ratio1(){
		
		Animation am = new ScaleAnimation(1,1,0,1);
		
		am.setDuration(2000);
		
		return am;
	}
	
	
	
	/**
	 * ͸����
	 * @return
	 */
	public static Animation alpha(float from,float to, int time){
		
		Animation am = new AlphaAnimation(from,to);
		
		am.setDuration(time);
		
		return am;
	}
	
	/**
	 * ��ת
	 * @return
	 */
	public static Animation rotate(int fromRadian,int toRadian){ 
		
		Animation am = new RotateAnimation(fromRadian, toRadian, 1, 1);
		
		am.setDuration(3000);
		
		return am;
		
	}
	
	
}
