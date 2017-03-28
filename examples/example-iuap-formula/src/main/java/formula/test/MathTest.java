package formula.test;

import java.text.DecimalFormat;

import com.yonyou.iuap.formula.formulaset.FormulaParseFather;
import com.yonyou.iuap.formula.formulaset.coretype.Complex;
import com.yonyou.iuap.formula.service.formulaparse.FormulaParse;

import junit.framework.TestCase;

public class MathTest extends TestCase {

	private FormulaParse _parser;

	private FormulaParse getFormulaParser() {
		if (_parser == null)
			_parser = new FormulaParse("test");
		return _parser;
	}

	/**
	 * abs(num)求数num的绝对值
	 * 
	 * @throws Exception
	 */
	public void testAbs() throws Exception {
		String formula = "a->abs(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("data", new Object[] { 1, -2, 0 });
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", 1, result[0][0]);
		assertEquals("应该相等", 2, result[0][1]);
		assertEquals("应该相等", 0, result[0][2]);
	}

	/**
	 * acos(num) 弧度x的反余弦(arccos)
	 * 
	 * @throws Exception
	 */
	public void testAcos() throws Exception {
		String formula = "a->acos(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("data", new Object[] { 0.5, 1, 2 });
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", 1.0471975511965979, result[0][0]);
		assertEquals("应该相等", 0.0, result[0][1]);
		assertEquals("应该相等", Double.NaN, result[0][2]);
	}

	/**
	 * add(num1,num2)用于高精度加法运算
	 * 
	 * @throws Exception
	 */
	public void testAdd() throws Exception {
		// String formula = "a->add(b, c)";
		String[] formula = new String[] { "a->b+c" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Double[] paramsB = new Double[] { 0.82545, 578478.0, 0.45645 };
		Double[] paramsC = new Double[] { 500.24564, -45648.0, 21345.0 };
		f.addVariable("b", paramsB);
		f.addVariable("c", paramsC);
		Object[][] result = f.getValueOArray();
		assertEquals((Double) result[0][0], 501.07109);
		assertEquals((Double) result[0][1], 532830.0);
		assertEquals((Double) result[0][2], 21345.45645);
	}

	public void testMath() throws Exception {
		// 超过了Integer的取值范围。
		// String formula = "a->add(b, c)";
		String[] formula = new String[] { "a->-1*-3936540618530" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		// assertEquals("应该相等", 3936540618530, result[0][0]);
		assertEquals("返回值数字长度应该相等", 0, result.length);
		// System.out.println(result);
	}

	/**
	 * asin(x)返回一个弧度x的反正弦,弧度值在-Pi/2到Pi/2之间
	 * 
	 * @throws Exception
	 */
	public void testAsin() throws Exception {
		String formula = "a->asin(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, -1.0, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(Math.asin((Double) params[i])));
		}
	}

	/**
	 * atan(x)返回一个弧度x的反正切值,弧度值在-Pi/2到Pi/2之间
	 * 
	 * @throws Exception
	 */
	public void testAtan() throws Exception {
		String formula = "a->atan(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, -1.0, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(Math.atan((Double) params[i])));
		}
	}

	/**
	 * cos(x)返回给定角度x的余弦值
	 * 
	 * @throws Exception
	 */
	public void testCos() throws Exception {
		String formula = "a->cos(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, -1.0, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(Math.cos((Double) params[i])));
		}
	}

	/**
	 * div(num1,num2)用于高精度除法运算
	 * 
	 * @throws Exception
	 */
	public void testDiv() throws Exception {
		String formula = "a->div(b, c)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] paramsB = new Double[] { 0.82545, 578478.0, 0.45645 };
		Double[] paramsC = new Double[] { 500.24564, -45648.0, 21345.0 };
		f.addVariable("b", paramsB);
		f.addVariable("c", paramsC);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < paramsB.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(paramsB[i] / paramsC[i]));
		}
	}

	/**
	 * exp(x)e的x次方
	 * 
	 * @throws Exception
	 */
	public void testExp() throws Exception {
		String formula = "a->exp(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, -1.0, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(Math.exp((Double) params[i])));
		}
	}

	/**
	 * int(数字或者字符串) 将变量转换为int类型
	 * 
	 * @throws Exception
	 */
	public void testInt() throws Exception {
		String formula = "a->int(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Object[] params = new Object[] { 0.66, -1.0, 0.0, "120" };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();

		assertEquals("应该相等", 1, result[0][0]);
		assertEquals("应该相等", -1, result[0][1]);
		assertEquals("应该相等", 0, result[0][2]);
		assertEquals("应该相等", 120, result[0][3]);

	}

	/**
	 * ln(x)返回给定数值x的自然对数
	 * 
	 * @throws Exception
	 */
	public void testLn() throws Exception {
		String formula = "a->ln(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, 1.0, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			assertEquals(((Complex) result[0][i]).re(), Math.log(params[i]));
		}
	}

	/**
	 * log(x)返回给定数n的以十为底的对数
	 * 
	 * @throws Exception
	 */
	public void testLog() throws Exception {
		String formula = "a->log(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, 1.0, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", -0.301029996, convertToDouble((Complex) result[0][0]));
		assertEquals("应该相等", 0.0, convertToDouble((Complex) result[0][1]));
		assertEquals("应该相等", Double.NEGATIVE_INFINITY, ((Complex) result[0][2]).re());
	}

	private double convertToDouble(Complex complex) {
		double expected = complex.re();
		DecimalFormat df = new DecimalFormat("#.#########");
		return Double.parseDouble(df.format(expected));
	}

	/**
	 * max(x, y) 求数字x,y两者中的最大值
	 * 
	 * @throws Exception
	 */
	public void testMax() throws Exception {
		String formula = "a->max(b, c)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] paramsB = new Double[] { 0.82545, 578478.0, 0.45645 };
		Double[] paramsC = new Double[] { 500.24564, -45648.0, 21345.0 };
		f.addVariable("b", paramsB);
		f.addVariable("c", paramsC);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < paramsB.length; i++) {
			Double expected = (paramsB[i] > paramsC[i] ? paramsB[i] : paramsC[i]);
			double actual = ((Double) result[0][i]).doubleValue();
			assertTrue(actual == expected);
			// assertEquals(true, actual == expected);
		}
	}

	/**
	 * min(x, y) 求x,y两者中的最小值
	 * 
	 * @throws Exception
	 */
	public void testMin() throws Exception {
		String formula = "a->min(b, c)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] paramsB = new Double[] { 0.82545, 578478.0, 0.45645 };
		Double[] paramsC = new Double[] { 500.24564, -45648.0, 21345.0 };
		f.addVariable("b", paramsB);
		f.addVariable("c", paramsC);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < paramsB.length; i++) {
			Double expected = (paramsB[i] < paramsC[i] ? paramsB[i] : paramsC[i]);
			double actual = ((Double) result[0][i]).doubleValue();
			assertTrue(actual == expected);
		}
	}

	/**
	 * mul(num1,num2)用于高精度乘法运算
	 * 
	 * @throws Exception
	 */
	public void testMul() throws Exception {
		String formula = "a->mul(b, c)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] paramsB = new Double[] { 0.82545, 578478.0, 0.45645 };
		Double[] paramsC = new Double[] { 500.24564, -45648.0, 21345.0 };
		f.addVariable("b", paramsB);
		f.addVariable("c", paramsC);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < paramsB.length; i++) {
			double expected = new Double(paramsB[i].doubleValue() * paramsC[i].doubleValue()).doubleValue();
			double actual = ((Double) result[0][i]).doubleValue();
			assertEquals(actual,expected, 0.0001);
		}
	}

	/**
	 * round(double num, int index) 对num保留index位小数(四舍五入)
	 * 
	 * @throws Exception
	 */
	public void testRound() throws Exception {
		String formula = "a->round(b, c)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] paramsB = new Double[] { new Double("0.82545"), new Double("578478.0"), new Double("0.45645") };
		Number[] paramsC = new Number[] { 1, 2, 4 };
		f.addVariable("b", paramsB);
		f.addVariable("c", paramsC);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", 0.8, result[0][0]);
		assertEquals("应该相等", 578478.00, result[0][1]);
		assertEquals("应该相等", 0.4565, result[0][2]);
	}

	/**
	 * sgn(num) 当数num大于0时,返回1,等于0时,返回0,小于0时返回-1
	 * 
	 * @throws Exception
	 */
	public void testSgn() throws Exception {
		String formula = "a->sgn(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { -0.82545, 578478.0, 0.45645 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			int expected = (params[i] > 0 ? 1 : (params[i] == 0 ? 0 : -1));
			int actual = ((Double) result[0][i]).intValue();
			assertTrue(actual == expected);
		}
	}

	/**
	 * sin(x)返回给定角度x的正弦值
	 * 
	 * @throws Exception
	 */
	public void testSin() throws Exception {
		String formula = "a->sin(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, -1.0, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(Math.sin((Double) params[i])));
		}
	}

	/**
	 * sqrt(x)返回数值x的平方根
	 * 
	 * @throws Exception
	 */
	public void testSqrt() throws Exception {
		String formula = "a->sqrt(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, 1.23, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(Math.sqrt((Double) params[i])));
		}
	}

	/**
	 * sub(num1,num2)用于高精度减法运算
	 * 
	 * @throws Exception
	 */
	public void testSub() throws Exception {
		String formula = "a->sub(b, c)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] paramsB = new Double[] { 0.82545, 578478.0, 0.45645 };
		Double[] paramsC = new Double[] { 500.24564, -45648.0, 21345.0 };
		f.addVariable("b", paramsB);
		f.addVariable("c", paramsC);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", -499.42019, result[0][0]);
		assertEquals("应该相等", 624126.0, result[0][1]);
		assertEquals("应该相等", -21344.54355, result[0][2]);
	}

	/**
	 * tan(x)返回给定角度x的正切值
	 * 
	 * @throws Exception
	 */
	public void testTan() throws Exception {
		String formula = "a->tan(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Double[] params = new Double[] { 0.5, 1.23, 0.0 };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(Math.tan((Double) params[i])));
		}
	}

	/**
	 * toNumber(String st) 将字符串st转换为本解析器可识别的数字,
	 * 比如toNumber("45.0")将返回一个数字型45.0,经过转化后可参与各种数值计算.
	 * 
	 * @throws Exception
	 */
	public void testToNumber() throws Exception {
		String formula = "a->toNumber(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		String[] params = new String[] { "0.5", "1.23", "0.0" };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Double paramf = (Double) result[0][i];
			assertEquals(paramf, new Double(Double.parseDouble(params[i])));
		}
	}

	/**
	 * zeroifnull(var)表示如果var为空将返回0
	 * 
	 * @throws Exception
	 */
	public void testZeroIfNull() throws Exception {
		String formula = "a->zeroIfNull(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Object[] params = new Object[] { "0.5", 1.23, 22, null };
		f.addVariable("data", params);
		Object[][] result = f.getValueOArray();
		for (int i = 0; i < params.length; i++) {
			Object expectedValue;
			if (params[i] == null) {
				expectedValue = new Double(0);
			} else if (params[i] instanceof Double) {
				expectedValue = new Double((Double) params[i]);
			} else {
				expectedValue = params[i];
			}
			assertEquals(expectedValue, result[0][i]);
		}
	}

	/**
	 * acosh(num) 反双曲余弦
	 * 
	 * @throws Exception
	 */
	public void testAcosh() throws Exception {
		String formula = "a->acosh(data)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("data", new Object[] { 0.5, 1, 2 });
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", 0.0, ((Complex) result[0][0]).re());
		assertEquals("应该相等", 0.0, convertToDouble((Complex) result[0][1]));
		assertEquals("应该相等", 1.316957897, convertToDouble((Complex) result[0][2]));
	}

	/**
	 * angle(num) 以弧度为单位计算并返回点y/x的角度。
	 * 
	 * @throws Exception
	 */
	public void testAngle() throws Exception {
		String formula = "a->angle(c,b)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("c", new Object[] { 0.5 });
		f.addVariable("b", new Object[] { 1 });
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", 0.4636476090008061, result[0][0]);
	}

	/**
	 * mod(a,b) 返回两数相除的余数.
	 * 
	 * @throws Exception
	 */
	public void testMod() throws Exception {
		String formula = "a->mod(c,b)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("c", new Object[] { 7 });
		f.addVariable("b", new Object[] { 2 });
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", 1, result[0][0]);
	}

	/**
	 * rand()生成随机数
	 * 
	 * @throws Exception
	 */
	public void testRand() throws Exception {
		String formula = "a->rand()";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Object[][] result = f.getValueOArray();
		assertNotNull(result[0][0]);
	}

	/**
	 * sum(a,b)计算两个数的和
	 * 
	 * @throws Exception
	 */
	public void testSum() throws Exception {
		String formula = "a->sum(c,b)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("c", new Object[] { 7.1 });
		f.addVariable("b", new Object[] { 2.5 });
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", 9.6, result[0][0]);
	}
}
