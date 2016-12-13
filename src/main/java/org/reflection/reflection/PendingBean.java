/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @since 2016年5月30日 下午9:50:11
 * @version $Id: PendingBean.java 15618 2016-05-31 08:53:42Z CaiBo $
 * @author CaiBo
 *
 */
public interface PendingBean {

	void setValue(Object value);

	Annotation[] getFieldAnnotation();

	Annotation[] getMethodAnnotation();

	Object getCurrentBean();

	Field getCurrentField();

	Object getCurrentHost();

	String getFieldPath();

	boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);

}
