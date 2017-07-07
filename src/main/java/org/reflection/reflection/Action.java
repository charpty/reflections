package org.reflection.reflection;

/**
 * @author CaiBo
 * @version $Id$
 * @since 2017/7/7 下午3:34
 */
public enum Action {

	/**
	 * 处理该属性
	 */
	ACTION_DO_WITH,
	/**
	 * 跳过该属性继续寻找
	 */
	ACTION_GO_NEXT,
	/**
	 * 停止整个扫描
	 */
	ACTION_STOP;
}
