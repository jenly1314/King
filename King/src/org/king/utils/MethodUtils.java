package org.king.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ���������ࣨ������ƣ�
 * 
 * @author Jenly
 * @date 2013-7-4
 * 
 */
public class MethodUtils {
	
	
	
	/**
	 * ͨ����Ķ���ͷ������õ���������
	 * @param obj ��Ӧ�����������ʵ������
	 * @param methodName ������
	 * @param clazzs  ������������
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method getMethod(Object obj,String methodName,Class<?>... clazzs) throws SecurityException, NoSuchMethodException{
		
		Method method = obj.getClass().getMethod(methodName, clazzs);
		
		return method;
	}
	
	/**
	 * ����ĳ������
	 * @param obj ��Ӧ�����������ʵ������
	 * @param methodName ������
	 * @param objs ��������
	 * @param clazzs ������������
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
	 * ����ĳ������
	 * @param obj ��Ӧ�����������ʵ������
	 * @param method ����
	 * @param objs ��������
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void invoke(Object obj,Method method,Object... objs) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		method.invoke(obj,objs);
		
	}

}
