/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

/**
 * @since 2016年5月31日 上午8:56:46
 * @version $Id: Callback.java 15563 2016-05-31 01:03:00Z CaiBo $
 * @author CaiBo
 *
 */
public interface Callback {

	/**
	 * @param handler
	 * @param context
	 */
	void doWith(FieldHandler handler, ScanContext context);
}