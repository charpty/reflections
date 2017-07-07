package org.reflection.reflection;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiFunction;

/**
 * @author CaiBo
 * @version $Id$
 * @since 2017/7/7 上午10:40
 */
public final class ClassFieldTestUtil {
	private static Object GSON;
	private static Method TO_JSON;

	static {
		try {
			Class c = Class.forName("com.google.gson.Gson");
			GSON = c.newInstance();
			TO_JSON = c.getMethod("toJson", Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String toString(Object obj) {
		if (TO_JSON == null) {
			return String.valueOf(obj);
		}
		return toJson(obj);
	}

	public static final void print(Object obj) {
		if (TO_JSON == null) {
			System.out.println(obj);
			return;
		}
		System.out.println(toJson(obj));
	}

	private static String toJson(Object obj) {
		try {
			return String.valueOf(TO_JSON.invoke(GSON, obj));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}
}
