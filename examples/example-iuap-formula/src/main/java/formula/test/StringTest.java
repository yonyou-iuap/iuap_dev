package formula.test;


import com.yonyou.iuap.formula.formulaset.FormulaParseFather;
import com.yonyou.iuap.formula.service.formulaparse.FormulaParse;

import junit.framework.TestCase;
/**
 * 用于字符串函数的测试用例实现
 * 
 * @author mazqa
 * 
 */
public class StringTest extends TestCase {

	FormulaParseFather		fglobal	= null;
	private FormulaParse	_parser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		fglobal = getFormulaParser();
	}

	private FormulaParse getFormulaParser() {
		if (_parser == null)
			_parser = new FormulaParse("test");
		return _parser;
	}

	@SuppressWarnings("unchecked")
	public void testCharAt() {
		String formula = "a->charAt(\"cch,nihao\",4)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		String result = f.getValue();
		assertEquals("应该相等!", "n", result);
	}

	public void testEndswith() throws Exception {
		String[] formula = new String[] { "a->endswith(\"ttttsst\", \"st\")", "b->endswith(\"ttttsst\", \"ss\")",
				"c->endswith(null, \"aaa\")", "d->endswith(null, null)" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等",true, result[0][0]);
		assertEquals("应该相等", false, result[1][0]);
		assertEquals("应该相等", false, result[2][0]);
		assertEquals("应该相等", true, result[3][0]);
		

	}

	public void testEqualsIgnorecase() throws Exception {
		String[] formula = new String[] { "a->equalsIgnorecase(\"abcDef\",\"ABCDeF\")",
				"b->equalsIgnorecase(\"abcD1f\",\"ABCDeF\")", "c->equalsIgnorecase(null,\"ABCDeF\")",
				"d->equalsIgnorecase(\"abcDef\",null)", "e->equalsIgnorecase(null,null)", };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", 1, result[0][0]);
		assertEquals("应该相等", -1, result[1][0]);
		assertEquals("应该相等", -1, result[2][0]);
		assertEquals("应该相等", -1, result[3][0]);
		assertEquals("应该相等", 1, result[4][0]);
	}

	public void testIndexOf() {
		String formula1 = "a->indexOf(\"cch,nihao\",\"ni\")";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula1);
		String result1 = f.getValue();
		assertEquals("应该相等!", "4", result1);

		String formula2[] = new String[] { "a->indexOf(null,\"i\")", "b->indexOf(\"cch,nihao\",\"ni\")" };
		f.setExpressArray(formula2);
		String result2[][] = f.getValueSArray();
		assertEquals("应该相等!", "", result2[0][0]);
		assertEquals("应该相等!", "4", result2[1][0]);

		String formula3[] = new String[] { "a->indexOf(null,\"i\")", "b->indexOf(\"cch,nihao\",null)" };
		f.setExpressArray(formula3);
		Object result3[][] = f.getValueOArray();
		assertEquals("应该相等!", null, result3);
	}

	public void testIsEmpty() throws Exception {
		String[] formular = new String[] { "a->isempty(\"test\")", "b->isempty(\"\")", "c->isempty(null)" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formular);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等",false, result[0][0]);
		assertEquals("应该相等", true, result[1][0]);
		assertEquals("应该相等", true, result[2][0]);
	
	}

	public void testLastIndexOf() throws Exception {
		String formula = "iif(lastindexOf(a,b)<0,a,mid(a,lastindexOf(a,b)+1,length(a)))";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("a", "123\\345\\456");
		f.addVariable("b", "\\");
		Object result = f.getValue();
		assertEquals("应该相等!", "456", result);
	}

	/**
	 * left(st, index) 求字符串st左边前index个字符组成的字符串
	 * 
	 * @throws Exception
	 */
	public void testLeft() throws Exception {
		String[] formula = new String[] { "a->left(\"I want to fly!\", 5)", "b->left(null, 3)", "c->left(\"\", 2)",
				"d->left(\"abc\", 4)", "e->left(\"abc\", 0)" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", result[0][0], "I wan");
		assertEquals("应该相等", result[1][0], null);
		assertEquals("应该相等", result[2][0], null);
		assertEquals("应该相等", result[3][0], null);
		assertEquals("应该相等", result[4][0], "");
	}

	/**
	 * leftStr(st,len,defaultStr)
	 * 求字符串st左边前len个字符组成的字符串，如果字符串长度小于len，
	 * 则用defaultStr补齐,比如leftStr("abc",6,"@")将返回abc@@@
	 * 
	 * @throws Exception
	 */
	public void testLeftStr() throws Exception {
		String[] formula = new String[] { 
//				"a->leftstr(\"I want to fly!\", 5, \"@\")", 
				"a->leftstr(\"I want to fly!\", 5, \"#\")", 
				"b->leftstr(null, 3, \"@\")", 
				"c->leftstr(\"\", 2, \"@*\")",
				"d->leftstr(\"abc\", 6, \"@*\")", 
				"e->leftstr(\"abc\", 0, \"@*\")" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", result[0][0], "I wan");
		assertEquals("应该相等", result[1][0], null);
		assertEquals("应该相等", result[2][0], null);
		assertEquals("应该相等", result[3][0], "abc@*@");
		assertEquals("应该相等", result[4][0], "");
	}

	/**
	 * length(st) 求字符串st的长度
	 * @throws Exception
	 */
	public void testLength() throws Exception {
		String[] formula = new String[] { 
				"a->length(\"I want to fly!\")", 
				"b->length(\"\")", 
				"c->length(null)",
			};
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", result[0][0], 14);
		assertEquals("应该相等", result[1][0], 0);
		assertEquals("应该相等", result[2][0], 0);
	}

	/**
	 * mid(String st, int start, int end) 
	 * 求字符串st左边前第start个字符至第end个字符之间的字符串
	 * @throws Exception
	 */
	public void testMid() throws Exception {
		String[] formula = new String[] { 
				"a->mid(\"I want to fly!\", 0, 14)", 
				"b->mid(\"a\", 0, 0)", 
				"c->mid(null, 1, 3)",
				"d->mid(\"0123456\", 1, 5)",
				"e->mid(\"0123456\", -1, 5)",
				"f->mid(\"0123456\", 1, 9)",
				"g->mid(\"0123456\", 4, 2)"
			};
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", result[0][0], "I want to fly!");
		assertEquals("应该相等", result[1][0], "");
		assertEquals("应该相等", result[2][0], null);
		assertEquals("应该相等", result[3][0], "1234");
		assertEquals("应该相等", result[4][0], null);
		assertEquals("应该相等", result[5][0], null);
		assertEquals("应该相等", result[6][0], null);
	}

	/**
	 * right(String st, int index) 求字符串st右边前index个字符组成的字符串
	 * @throws Exception
	 */
	public void testRight() throws Exception {
		String[] formula = new String[] { 
				"a->right(\"I want to fly!\", 5)", 
				"b->right(null, 3)", 
				"c->right(\"\", 2)",
				"d->right(\"abc\", 4)", 
				"e->right(\"abc\", 0)" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", result[0][0], " fly!");
		assertEquals("应该相等", result[1][0], null);
		assertEquals("应该相等", result[2][0], null);
		assertEquals("应该相等", result[3][0], null);
		assertEquals("应该相等", result[4][0], "");
	}

	/**
	 * rightStr(st,len,defaultStr) 
	 * 求字符串st右边后len个字符组成的字符串，如果字符串长度小于len，
	 * 则用defaultStr补齐,比如rightStr("abc",6,"@")将返回abc@@@.
	 * @throws Exception
	 */
	public void testRightStr() throws Exception {
		String[] formula = new String[] { 
				"a->rightstr(\"I want to fly!\", 5, \"@\")", 
				"b->rightstr(null, 3, \"@\")", 
				"c->rightstr(\"\", 2, \"@*\")",
				"d->rightstr(\"abc\", 6, \"@*\")", 
				"e->rightstr(\"abc\", 0, \"@*\")" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", result[0][0], " fly!");
		assertEquals("应该相等", result[1][0], null);
		assertEquals("应该相等", result[2][0], null);
		assertEquals("应该相等", result[3][0], "abc@*@");
		assertEquals("应该相等", result[4][0], "");
	}

	/**
	 * startsWith(String st, String start) 判断字符串st是否以字符串start开头
	 * @throws Exception
	 */
	public void testStartsWith() throws Exception {
		String[] formula = new String[] { 
				"a->startswith(\"I want to fly!\", \"I w\")", 
				"b->startswith(null, \"@\")", 
				"c->startswith(\"\", \"@*\")",
				"d->startswith(null, null)", 
				"e->startswith(\"abcd\", \"abd\")" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();

		assertEquals("应该相等",true, result[0][0]);
		assertEquals("应该相等", false, result[1][0]);
		assertEquals("应该相等", false, result[2][0]);
		assertEquals("应该相等", true, result[3][0]);
		assertEquals("应该相等", false, result[4][0]);
	}

	/**
	 * toLowerCase(String st) 求字符串st的小写形式,比如toLowerCase("Abc")返回"abc".
	 * @throws Exception
	 */
	public void testToLowerCase() throws Exception {
		String[] formula = new String[] { 
				"a->toLowerCase(\"I WaNt to fLy!\")", 
				"b->toLowerCase(null)", 
				"c->toLowerCase(\"\")",
				"d->toLowerCase(\"AABCD\")", 
				"e->toLowerCase(\"abcd\")" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", result[0][0], "i want to fly!");
		assertEquals("应该相等", result[1][0], null);
		assertEquals("应该相等", result[2][0], "");
		assertEquals("应该相等", result[3][0], "aabcd");
		assertEquals("应该相等", result[4][0], "abcd");
	}

	/**
	 * @throws Exception
	 */
	public void testToString() throws Exception {
		//TODO:暂时未实现
		String formula = "a->toString(b)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("b", 9999999999.00);
		String result = f.getValue();
		assertEquals("9999999999.00", result);
	}

	/**
	 * toUpperCase(String st) 求字符串st的大写形式
	 * @throws Exception
	 */
	public void testToUpperCase() throws Exception {
		String[] formula = new String[] { 
				"a->toUpperCase(\"I WaNt to fLy!\")", 
				"b->toUpperCase(null)", 
				"c->toUpperCase(\"\")",
				"d->toUpperCase(\"AABCD\")", 
				"e->toUpperCase(\"abcd\")" };
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", result[0][0], "I WANT TO FLY!");
		assertEquals("应该相等", result[1][0], null);
		assertEquals("应该相等", result[2][0], "");
		assertEquals("应该相等", result[3][0], "AABCD");
		assertEquals("应该相等", result[4][0], "ABCD");
	}

	/**
	 * @throws Exception
	 */
	public void testTrimZero() throws Exception {
		String[] formula = new String[] { 
				"a->trimZero(\"8.023000\")", 
				"b->trimZero(\"8.023000a\")", 
				"c->trimZero(8.023000)",
				"d->trimZero(\"8.023000\", 2)", 
				"e->trimZero(null)", 
				"f->trimZero(\"\")" 
				};
		FormulaParseFather f = getFormulaParser();
		f.setExpressArray(formula);
		Object[][] result = f.getValueOArray();
		assertEquals("应该相等", ((Double)result[0][0]).doubleValue(), 8.023);
		assertEquals("应该相等", result[1][0], "8.023000a");
		assertEquals("应该相等", ((Double)result[2][0]).doubleValue(), 8.023);
		assertEquals("应该相等", ((Double)result[3][0]).doubleValue(), 8.02);
		assertEquals("应该相等", result[4][0], null);
		assertEquals("应该相等", ((Double)result[5][0]).doubleValue(), 0.0);
	}
}
