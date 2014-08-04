package org.king.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法工具类（反射机制）
 * 
 * @author Jenly
 * @date 2013-7-4
 * 
 */
public class MethodUtils {
	
	
	
	/**
	 * 通过类的对象和方法名得到方法对象
	 * @param obj 对应方法所在类的实例对象
	 * @param methodName 方法名
	 * @param clazzs  方法参数类型
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method getMethod(Object obj,String methodName,Class<?>... clazzs) throws SecurityException, NoSuchMethodException{
		
		Method method = obj.getClass().getMethod(methodName, clazzs);
		
		return method;
	}
	
	/**
	 * 调用某个方法
	 * @param obj 对应方法所在类的实例对象
	 * @param methodName 方法名
	 * @param objs 方法参数
	 * @param clazzs 方法参数类型
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void invoke(Object obj,String methodName,Object[] objs,Class<?>[] clazzs) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		invoke(obj,getMethod(objs, methodName, clazzs), objs);
		
	}
	
	/**
	 * 调用某个方法
	 * @param obj 对应方法所在类的实例对象
	 * @param method 方法
	 * @param objs 方法参数
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void invoke(Object obj,Method method,Object... objs) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		method.invoke(obj,objs);
		
	}

}
