/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

/**
 * @since 2016年5月31日 上午8:55:10
 * @version $Id: Predicate.java 15563 2016-05-31 01:03:00Z CaiBo $
 * @author CaiBo
 *
 */
public interface Predicate {
	int ACTION_DO_WITH = 0;
	int ACTION_GO_NEXT = 1;
	int ACTION_IGNORE = 2;

	/**
	 * @param self
	 * @param context
	 * @return {@link ACTION_DO_WITH}<br>
	 *         {@link ACTION_GO_NEXT}<br>
	 *         {@link ACTION_IGNORE}
	 */
	int apply(Object self, ScanContext context);
}