/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.util.function.Function;

/**
 * @since 2016年5月31日 上午8:56:46
 * @version $Id: Callback.java 15563 2016-05-31 01:03:00Z CaiBo $
 * @author CaiBo
 *
 */
@FunctionalInterface
public interface Callback<Handler extends FieldHandler,Context extends ScanContext, Void>{

	/**
	 * @param handler
	 * @param context
	 */
	void doWith(Handler handler, Context context);
}