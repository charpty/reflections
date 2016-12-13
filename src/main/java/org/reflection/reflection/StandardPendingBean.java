/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @since 2016年5月30日 下午9:49:15
 * @version $Id: StandardPendingBean.java 15602 2016-05-31 07:49:38Z CaiBo $
 * @author CaiBo
 *
 */
final class StandardPendingBean implements PendingBean {

	private boolean complexHost;
	private final Object currentBean;
	private final Object currentHost;
	private final String fieldPath;
	private final Field currentField;
	private final Method currentMethod;
	private final StandardScanContext context;
	private FieldHandler complexHostHandler;

	public StandardPendingBean(StandardScanContext context) {
		this.complexHost = context.isComplexHost();
		this.currentBean = context.getCurrentBean();
		this.currentHost = context.getCurrentHost();
		this.fieldPath = context.getFieldPath();
		this.currentField = context.getCurrentField();
		this.context = context;
		if (complexHost) {
			this.complexHostHandler = context.getComplexHostHandler();
		}
		this.currentMethod = context.getCurrentMethod();

	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		if (currentField == null) {
			return false;
		}
		return currentField.isAnnotationPresent(annotationClass);
	}

	@Override
	public void setValue(Object value) {
		if (complexHost) {
			complexHostHandler.setValue(value);
		} else {
			StandardFieldHandler.setValue(currentHost, currentField, value);
		}
		context.setChanged(true);
	}

	@Override
	public Annotation[] getFieldAnnotation() {
		if (currentField != null) {
			return currentField.getAnnotations();
		} else {
			return new Annotation[0];
		}
	}

	@Override
	public Annotation[] getMethodAnnotation() {
		return currentMethod.getAnnotations();
	}

	@Override
	public Object getCurrentHost() {
		return currentHost;
	}

	@Override
	public Field getCurrentField() {
		return currentField;
	}

	@Override
	public Object getCurrentBean() {
		return currentBean;
	}

	@Override
	public String getFieldPath() {
		return fieldPath;
	}

}
