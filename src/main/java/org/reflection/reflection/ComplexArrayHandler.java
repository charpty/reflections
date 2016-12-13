/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.lang.reflect.Array;

import com.ifugle.util.UtilException;

/**
 * @since 2016年5月31日 上午9:18:34
 * @version $Id: ComplexArrayHandler.java 15564 2016-05-31 01:20:21Z CaiBo $
 * @author CaiBo
 *
 */
final class ComplexArrayHandler implements FieldHandler {
	private int index = 0;
	private Object arr;
	private boolean primitive;

	public ComplexArrayHandler(Object arr) {
		this.arr = arr;
		this.primitive = arr.getClass().getComponentType().isPrimitive();
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void setValue(Object value) {
		if (primitive) {
			if (value instanceof Integer) {
				Array.setInt(arr, index, (int) value);
			} else if (value instanceof Byte) {
				Array.setByte(arr, index, (byte) value);
			} else if (value instanceof Long) {
				Array.setLong(arr, index, (long) value);
			} else if (value instanceof Double) {
				Array.setDouble(arr, index, (double) value);
			} else if (value instanceof Boolean) {
				Array.setBoolean(arr, index, (boolean) value);
			} else if (value instanceof Character) {
				Array.setChar(arr, index, (char) value);
			} else if (value instanceof Float) {
				Array.setFloat(arr, index, (float) value);
			} else if (value instanceof Short) {
				Array.setShort(arr, index, (short) value);
			} else {
				throw new UtilException("this array component type is primitive, but try to set non-primitive value: [{}]", value);
			}
		} else {
			Array.set(arr, index, value);
		}
	}
}
