/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package org.reflection.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @since 2016年5月31日 上午8:57:07
 * @version $Id: ScanContext.java 15563 2016-05-31 01:03:00Z CaiBo $
 * @author CaiBo
 *
 */
public interface ScanContext {

	String getFieldPath();

	Field getCurrentField();

	List<Field> getFields();

	Object getCurrentHost();

	Object getCurrentBean();

	Method getCurrentMethod();

	boolean isChanged();

	boolean isFound();

	boolean isErrorHappened();

	void setErrorHappened(boolean errorHappened);

	String getErrorMessage();

	void setErrorMessage(String errorMessage);
}
