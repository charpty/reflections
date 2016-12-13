/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 2016年5月31日 上午9:15:03
 * @version $Id: StandardScanContext.java 15602 2016-05-31 07:49:38Z CaiBo $
 * @author CaiBo
 *
 */
final class StandardScanContext implements ScanPendingBeanContext {

	private Object root;
	private Object currentHost;
	private Object currentBean;
	private Method currentMethod;
	/** stop get fields names when found complex type */
	private boolean stopOnComplex;
	private boolean complex;
	private boolean complexForAll;
	private boolean complexHost;
	private boolean changed;
	private boolean found;
	private boolean errorHappened;
	private String errorMessage;

	private FieldHandler complexHostHandler;
	private String fieldPath = "this";
	private Map<String, FieldInfo> names = new HashMap<String, FieldInfo>();
	private Map<String, PendingBean> pendingBeans = new HashMap<>();
	private Field currentField;
	private List<Field> fields = new ArrayList<Field>();

	public void setCurrentBeanValue(Callback callback) {
		if (complexHost) {
			callback.doWith(complexHostHandler, this);
		} else {
			callback.doWith(new StandardFieldHandler(currentHost, currentField), this);
		}
		changed = true;
	}

	public List<Field> getFields() {
		return fields;
	}

	public Method getCurrentMethod() {
		return currentMethod;
	}

	public void setCurrentMethod(Method method) {
		this.currentMethod = method;
	}

	public boolean isErrorHappened() {
		return errorHappened;
	}

	public void setErrorHappened(boolean errorHappened) {
		this.errorHappened = errorHappened;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public Object getCurrentBean() {
		return currentBean;
	}

	public void setCurrentBean(Object currentBean) {
		this.currentBean = currentBean;
	}

	public StandardScanContext(Object root) {
		this.root = root;
	}

	public Object getRootObject() {
		return root;
	}

	@Override
	public Object getCurrentHost() {
		return currentHost;
	}

	public void setCurrentHost(Object host) {
		this.currentHost = host;
	}

	public FieldInfo[] getFieldsInfo() {
		if (names != null) {
			return names.values().toArray(new FieldInfo[names.size()]);
		} else {
			return null;
		}
	}

	public void savePendingBean() {
		pendingBeans.put(fieldPath, new StandardPendingBean(this));
	}

	public Map<String, PendingBean> getPendingBeans() {
		return pendingBeans;
	}

	public void saveCurrentField() {
		if (stopOnComplex) {
			names = null;
		} else {
			String hostPath = null;
			if (fieldPath != null && fieldPath.contains(".")) {
				hostPath = fieldPath.substring(0, fieldPath.lastIndexOf("."));
			}
			if (names != null) {
				names.put(fieldPath, new StandardFieldInfo(fieldPath, hostPath));
			}
		}
	}

	public FieldHandler getComplexHostHandler() {
		return complexHostHandler;
	}

	public void setComplexHostHandler(FieldHandler complexHostHandler) {
		this.complexHostHandler = complexHostHandler;
	}

	public boolean isComplexForAll() {
		return complexForAll;
	}

	public void setComplexForAll(boolean complexForAll) {
		this.complexForAll = complexForAll;
	}

	public boolean isComplexHost() {
		return complexHost;
	}

	public void setComplexHost(boolean complexHost) {
		this.complexHost = complexHost;
	}

	public boolean isComplex() {
		return complex;
	}

	public void setComplex(boolean complex) {
		this.complex = complex;
	}

	public boolean isStopOnComplex() {
		return stopOnComplex;
	}

	public void setStopOnComplex(boolean stopOnComplex) {
		this.stopOnComplex = stopOnComplex;
	}

	@Override
	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	public void appendFieldPath(String current) {
		this.fieldPath = this.fieldPath + current;
	}

	@Override
	public Field getCurrentField() {
		return currentField;
	}

	public void setCurrentField(Field currentField) {
		this.currentField = currentField;
		fields.add(currentField);
	}

	public void markComplex() {
		this.names = null;
	}

}
