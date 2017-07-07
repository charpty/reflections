package org.reflection.reflection;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.function.Function;

/**
 * @author CaiBo
 * @version $Id$
 * @since 2017/7/5 下午10:19
 */

public class SimpleTest extends TestCase {

	@Test
	public void testSetInteger() {
		BeanA ba = new BeanA();
		Callback f = (handler, context) -> {
			handler.setValue(1);
		};
		ReflectionUtil.doWithFieldsNested(ba, f, (self, context) -> {
			if (self instanceof Integer) {
				return Action.ACTION_DO_WITH;
			}
			return Action.ACTION_GO_NEXT;
		});
		System.out.println(ClassFieldTestUtil.toString(ba));
		assertEquals("{\"inta\":1,\"ba\":false,\"ca\":\"\\u0000\"}", ClassFieldTestUtil.toString(ba));
	}
}
