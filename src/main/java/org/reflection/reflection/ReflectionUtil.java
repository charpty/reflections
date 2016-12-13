/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @since 2016年4月12日 上午9:48:54
 * @version $Id: ReflectionUtil.java 15602 2016-05-31 07:49:38Z CaiBo $
 * @author CaiBo
 *
 */
public final class ReflectionUtil {

	private ReflectionUtil() {
		super();
	}

	private static final String COMPLEX_ALL = "[@]";
	private static final Predicate DEFAULT_PREDICATE = new Predicate() {
		@Override
		public int apply(Object self, ScanContext context) {
			if (isSimpleType(self)) {
				return ACTION_DO_WITH;
			}
			return ACTION_GO_NEXT;
		}
	};

	/**
	 * 循环扫描对象, 并对满足指定条件的属性进行处理<br>
	 * <br>
	 * 
	 * 扫描过程会自动处理数组、集合、表等类型, 会循环扫描对象属性中的子对象, 直到满足处理条件或者终止条件<br>
	 * 谓词(触发条件)是非常自由的, 3种不同的值可以决定扫描处理的进一步选择【0处理|1继续循环|2忽略】
	 * 
	 * @param bean
	 *            待扫描的对象
	 * @param callback
	 *            扫描到指定内容后的回调方法
	 * @param predicates
	 *            谓词, 指明调用callback的条件
	 * @return {@link ScanContext}
	 */
	public static ScanContext doWithFieldsNested(Object bean, Callback callback, Predicate... predicates) {
		return doWithFieldsNested0(bean, callback, predicates);
	}

	/**
	 * 循环扫描对象, 并将满足指定条件的属性返回<br>
	 * 
	 * @param bean
	 *            待扫描的对象
	 * @param predicates
	 *            谓词, 将扫描到的对象存为待处理对象的条件
	 * @return
	 */
	public static ScanPendingBeanContext getPendingBeans(Object bean, Predicate... predicates) {
		return doWithFieldsNested0(bean, null, predicates);
	}

	private static StandardScanContext doWithFieldsNested0(Object bean, Callback callback, Predicate... predicates) {
		StandardScanContext context = new StandardScanContext(bean);
		if (bean != null) {
			if (predicates == null) {
				predicates = new Predicate[] { DEFAULT_PREDICATE };
			}
			context.setCurrentHost(bean);
			doWith(context, bean, callback, predicates);
		}
		return context;
	}

	private static void doWithFields(StandardScanContext context, Object bean, Callback callback, Predicate... predicates) {
		String currentPath = context.getFieldPath();
		context.setComplexHost(false);
		Class<? extends Object> klass = bean.getClass();
		boolean includeSuperClass = klass.getClassLoader() != null;

		Method[] methods = (includeSuperClass) ? klass.getMethods() : klass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			context.setCurrentHost(bean);

			Method method = methods[i];
			int modifier = method.getModifiers();
			if (!Modifier.isStatic(modifier) && Modifier.isPublic(modifier)) {
				String name = method.getName();
				String key = "";
				if (name.startsWith("get")) {
					if (name.equals("getClass") || name.equals("getDeclaringClass")) {
						key = "";
					} else {
						key = name.substring(3);
					}
				} else if (name.startsWith("is")) {
					key = name.substring(2);
				}
				if (key.length() > 0 && Character.isUpperCase(key.charAt(0)) && method.getParameterTypes().length == 0) {
					if (key.length() == 1) {
						key = key.toLowerCase();
					} else if (!Character.isUpperCase(key.charAt(1))) {
						key = key.substring(0, 1).toLowerCase() + key.substring(1);
					}
					Field field = null;
					Class<? extends Object> clazz = klass;
					for (; null != clazz;) {
						try {
							field = clazz.getDeclaredField(key);
							if (!Modifier.isTransient(field.getModifiers())) {
								Object result = method.invoke(bean, (Object[]) null);
								if (currentPath == null) {
									context.setFieldPath(key);
								} else {
									context.setFieldPath(currentPath + "." + key);
								}
								context.setCurrentField(field);
								context.setCurrentMethod(method);
								doWith(context, result, callback, predicates);
							}
							break;
						} catch (NoSuchFieldException e) {
							clazz = clazz.getSuperclass();
							if (null == clazz && Character.isUpperCase(key.charAt(0))) {
								clazz = klass;
								key = key.substring(0, 1).toLowerCase() + key.substring(1);
							}
						} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ignore) {
							break;
						}
					}
				}
			}
		}
	}

	private static void doWith(StandardScanContext context, Object bean, Callback callback, Predicate... predicates) {
		context.setCurrentBean(bean);
		int c = filter(context, bean, predicates);
		if (c == Predicate.ACTION_DO_WITH) {
			if (callback != null) {
				context.setCurrentBeanValue(callback);
			} else {
				context.savePendingBean();
			}
			context.saveCurrentField();
		} else if (c == Predicate.ACTION_GO_NEXT) {
			doWith0(context, bean, callback, predicates);
		}
	}

	private static boolean isSimpleType(Object bean) {
		if (bean instanceof String || bean instanceof Boolean || bean instanceof Character || bean instanceof Number || bean instanceof Date
				|| bean instanceof Calendar) {
			return true;
		}
		return false;
	}

	private static void doWith0(StandardScanContext context, Object bean, Callback callback, Predicate... predicates) {
		if (bean == null || isSimpleType(bean)) {
			// int.class.getPackage() == null
			// classLoader !=null && classLoader.definePackage
		} else if (bean instanceof Collection) {
			doWithCollection(context, (Collection<?>) bean, callback, predicates);
		} else if (bean.getClass().isArray()) {
			doWithArray(context, bean, callback, predicates);
		} else if (bean instanceof Map) {
			doWithMap(context, (Map<?, ?>) bean, callback, predicates);
		} else {
			Package objectPackage = bean.getClass().getPackage();
			String objectPackageName = (objectPackage != null ? objectPackage.getName() : "");
			if (!objectPackageName.startsWith("java.") && !objectPackageName.startsWith("javax.") && bean.getClass().getClassLoader() != null) {
				doWithFields(context, bean, callback, predicates);
			}
		}
	}

	private static void doWithArray(StandardScanContext context, Object arr, Callback callback, Predicate... predicates) {
		context.setComplex(true);
		int i = 0;
		String currentPath = context.getFieldPath();
		boolean all = context.isComplexForAll();
		ComplexArrayHandler handler = new ComplexArrayHandler(arr);
		context.setComplexHostHandler(handler);

		int length = Array.getLength(arr);
		for (i = 0; i < length; i++) {
			Object o = Array.get(arr, i);
			handler.setIndex(i);
			if (all) {
				context.setFieldPath(currentPath + COMPLEX_ALL);
			} else {
				context.setFieldPath(currentPath + "[" + i++ + "]");
			}
			context.setComplexHost(true);
			doWith(context, o, callback, predicates);
		}
		context.setComplex(false);
		context.setComplexHost(false);
	}

	private static void doWithCollection(StandardScanContext context, Collection<?> collection, Callback callback, Predicate... predicates) {
		Iterator<?> it = collection.iterator();
		int i = 0;
		context.setComplex(true);
		String currentPath = context.getFieldPath();
		boolean all = context.isComplexForAll();
		ComplexCollectionHandler handler = new ComplexCollectionHandler(collection);
		context.setComplexHostHandler(handler);
		while (it.hasNext()) {
			handler.setIndex(i);
			if (all) {
				context.setFieldPath(currentPath + COMPLEX_ALL);
			} else {
				context.setFieldPath(currentPath + "[" + i++ + "]");
			}
			context.setComplexHost(true);
			doWith(context, it.next(), callback, predicates);
		}
		context.setComplex(false);
		context.setComplexHost(false);
	}

	private static void doWithMap(StandardScanContext context, Map<?, ?> map, Callback callback, Predicate... predicates) {
		context.setComplex(true);
		String currentPath = context.getFieldPath();
		boolean all = context.isComplexForAll();
		ComplexMapHandler handler = new ComplexMapHandler(map);
		context.setComplexHostHandler(handler);
		for (Entry<?, ?> e : map.entrySet()) {
			Object key = e.getKey();
			handler.setKey(key);
			if (all) {
				context.setFieldPath(currentPath + COMPLEX_ALL);
			} else {
				context.setFieldPath(currentPath + "[" + key + "]");
			}
			context.setComplexHost(true);
			doWith(context, e.getValue(), callback, predicates);
		}
		context.setComplex(false);
		context.setComplexHost(false);
	}

	private static <T> int filter(final StandardScanContext context, final T element, Predicate... predicates) {
		assertNotNull(predicates, "predicates");
		for (Predicate c : predicates) {
			assertNotNull(c, "predicate");
			int apply = c.apply(element, context);
			if (apply != Predicate.ACTION_DO_WITH) {
				return apply;
			}
		}
		context.setFound(true);
		return Predicate.ACTION_DO_WITH;
	}

	private static void assertNotNull(final Object argument, final String name) {
		if (argument == null) {
			throw new IllegalArgumentException(name + "may not be null");
		}
	}

}
