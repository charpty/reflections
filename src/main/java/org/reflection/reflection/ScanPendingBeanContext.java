/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.util.Map;

/**
 * @since 2016年5月31日 下午3:36:55
 * @version $Id: ScanPendingBeanContext.java 15602 2016-05-31 07:49:38Z CaiBo $
 * @author CaiBo
 *
 */
public interface ScanPendingBeanContext extends ScanContext {

	Map<String, PendingBean> getPendingBeans();
}
