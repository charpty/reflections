/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 */
package org.reflection.reflection;

/**
 * @since 2016年5月31日 上午8:55:10
 * @version $Id: Predicate.java 15563 2016-05-31 01:03:00Z CaiBo $
 * @author CaiBo
 *
 */
@FunctionalInterface
public interface Predicate<Self extends Object, Context extends ScanContext, Ac extends Action> {

	/**
	 * @param self
	 * @param context
	 */
	Ac apply(Self self, Context context);
}