/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.util.Map;

/**
 * @since 2016年5月31日 上午9:17:41
 * @version $Id: ComplexMapHandler.java 15564 2016-05-31 01:20:21Z CaiBo $
 * @author CaiBo
 *
 */
final class ComplexMapHandler implements FieldHandler {

	private Map<Object, Object> map;
	private Object key;

	@SuppressWarnings("unchecked")
	public ComplexMapHandler(Map<?, ?> map) {
		this.map = (Map<Object, Object>) map;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	@Override
	public void setValue(Object value) {
		map.put(getKey(), value);
	}

}