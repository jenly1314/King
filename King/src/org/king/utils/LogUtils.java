package org.king.utils;

import android.util.Log;

/**
 * ÈÕÖ¾
 * 
 * @author Jenly
 * @date 2013-3-25
 * 
 *       V>D>I>W>E
 */
public class LogUtils {

	public static final String TAG = "Jenly";

	public static boolean isDEBUG = true;

	public static void logI(String message) {
		if (isDEBUG)
			Log.i(TAG, message);

	}

	public static void logV(String message) {
		if (isDEBUG)
			Log.v(TAG, message);

	}

	public static void logD(String message) {
		if (isDEBUG)
			Log.d(TAG, message);

	}

	public static void logE(String message) {
		if (isDEBUG)
			Log.e(TAG, message);

	}

	public static void logW(String message) {
		if (isDEBUG)
			Log.w(TAG, message);

	}

	public static void logI(String className, String message) {
		if (isDEBUG)
			Log.i(TAG, className + "--->" + message);

	}

	public static void logV(String className, String message) {
		if (isDEBUG)
			Log.v(TAG, className + "--->" + message);

	}

	public static void logD(String className, String message) {
		if (isDEBUG)
			Log.d(TAG, className + "--->" + message);

	}

	public static void logE(String className, String message) {
		if (isDEBUG)
			Log.e(TAG, className + "--->" + message);

	}

	public static void logW(String className, String message) {
		if (isDEBUG)
			Log.w(TAG, className + "--->" + message);

	}

	public static void print(String message) {
		if (isDEBUG)
			System.out.print(message);

	}

	public static void println(String message) {
		if (isDEBUG)
			System.out.println(message);

	}

}
