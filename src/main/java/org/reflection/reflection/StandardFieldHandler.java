/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 */
package org.reflection.reflection;

import java.lang.reflect.Field;

/**
 * @author CaiBo
 * @version $Id: StandardFieldHandler.java 15573 2016-05-31 03:10:25Z CaiBo $
 * @since 2016年5月31日 上午9:16:33
 */
final class StandardFieldHandler implements FieldHandler {

	private Object currentHost;
	private Field currentField;

	public StandardFieldHandler(Object currentHost, Field currentField) {
		this.currentHost = currentHost;
		this.currentField = currentField;
	}

	@Override
	public void setValue(Object value) {
		setValue(currentHost, currentField, value);
	}

	static void setValue(Object currentHost, Field currentField, Object value) {
		if (currentField != null) {
			if (!currentField.isAccessible()) {
				currentField.setAccessible(true);
			}
			try {
				currentField.set(currentHost, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				StringBuilder sb = new StringBuilder(64);
				sb.append("Can not set value [").append(value);
				sb.append("] with field [").append(currentField);
				sb.append("] on instance [").append(currentHost);
				sb.append(']');
				throw new RuntimeException(sb.toString(), e);
			}
		} else {
			StringBuilder sb = new StringBuilder(64);
			sb.append("Can not set the root Object [").append(currentHost);
			sb.append("], current field is null");
			throw new RuntimeException(sb.toString());
		}
	}
}
