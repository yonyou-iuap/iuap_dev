package formula.test;


import org.junit.Test;

import com.yonyou.iuap.formula.formulaset.FormulaParseFather;
import com.yonyou.iuap.formula.service.formulaparse.FormulaParse;

import junit.framework.TestCase;

public class OverFloatTest extends TestCase{

	
	
	@Test
	public void testOverFloat() throws Exception {
		String[] formula = new String[] { "a->-1*-3936540618530"};
		FormulaParseFather f = new FormulaParse("test");
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertTrue(result.length == 0);
	}

}
