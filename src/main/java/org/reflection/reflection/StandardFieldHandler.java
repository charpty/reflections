/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.lang.reflect.Field;

import com.ifugle.util.UtilException;

/**
 * @since 2016年5月31日 上午9:16:33
 * @version $Id: StandardFieldHandler.java 15573 2016-05-31 03:10:25Z CaiBo $
 * @author CaiBo
 *
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
				throw new UtilException("Can not set value [{}] with field [{}] on instance [{}] ", value, currentField, currentHost, e);
			}
		} else {
			throw new UtilException("Can not set the root Object [{}], current field is null", currentHost);
		}
	}
}
