/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.util.Collection;
import java.util.List;

import com.ifugle.util.UtilException;

/**
 * @since 2016年5月31日 上午9:17:58
 * @version $Id: ComplexCollectionHandler.java 15564 2016-05-31 01:20:21Z CaiBo $
 * @author CaiBo
 *
 */
final class ComplexCollectionHandler implements FieldHandler {

	private Collection<Object> collection;
	private int index;

	@SuppressWarnings("unchecked")
	public ComplexCollectionHandler(Collection<?> collection) {
		this.collection = (Collection<Object>) collection;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void setValue(Object value) {
		if (collection instanceof List) {
			List<Object> tmp = (List<Object>) collection;
			tmp.set(getIndex(), value);
		} else {
			throw new UtilException("unsupport complex type [{}]", collection.getClass());
		}
	}

}
