package formula.test;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.yonyou.iuap.formula.formulaset.FormulaParseFather;
import com.yonyou.iuap.formula.service.formulaparse.FormulaParse;

import junit.framework.TestCase;

/**
 * 测试一些常用公式的正确性
 * 
 * 
 */
public class CommonUseTest extends TestCase {

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

	public void testAnd() throws Exception {
		FormulaParseFather f = new FormulaParse();
        f.setExpress("iif((sin(30)<0.5 | sin(60/180*3.1415)>0.5) & sin(30)>0.5,\"false\",\"false\")");
        assertEquals("应该相等!", "false", f.getValue());
        f.setExpress("iif(sin(30)<0.5 && sin(60/180*3.1415)>0.5,\"true\",\"false\")");
        assertEquals("应该相等!", "false", f.getValue());
        f.setExpress("iif(sin(30)<0.5 | sin(60/180*3.1415)>0.5,\"true\",\"false\")");
        assertEquals("应该相等!", "true", f.getValue());
	}

	public void testOr() throws Exception {

	}

	public void testIIf() throws Exception {
		String formula = "code->iif(a == null, b, a)";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		f.addVariable("a", "a");
		f.addVariable("b", "c");
		String result = f.getValue();
		assertEquals("a", result);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIF() {
		String formula = "pjzt->iif(pjzt=\"01\",\"已登记\",iif(pjzt=\"02\",\"已内部贴现\",iif(pjzt=\"03\",\"办托收\",iif(pjzt=\"04\",\"已承兑\",iif(pjzt=\"05\",\"已银行贴现\",iif(pjzt=\"06\",\"内部转让\",iif(pjzt=\"07\",\"对外转让\",iif(pjzt=\"08\",\"已抵押\",iif(pjzt=\"09\",\"抵押到期收回\",iif(pjzt=\"10\",\"抵押到期未收回\",iif(pjzt=\"11\",\"抵押未到期收回\",iif(pjzt=\"12\",\"退票\",iif(pjzt=\"13\",\"贴现后到期处理\",\"已登记\")))))))))))))";
		FormulaParseFather f = getFormulaParser();
		f.addVariable("val1", new Double(56));
		ArrayList val2 = new ArrayList();
		val2.add(new Double(2));
		val2.add(new Double(3));
		f.addVariable("val2", val2);
		List arrList = new ArrayList();
		String[] initStrs = new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" };
		arrList.add("01");
		f.addVariable("pjzt", initStrs);
		f.setExpress(formula);
		// f.setNullAsZero(true);
		String[][] res = f.getValueSArray();
		assertEquals("应该相等!", "已登记", res[0][0]);
		assertEquals("应该相等!", "已内部贴现", res[0][1]);
		assertEquals("应该相等!", "办托收", res[0][2]);
		assertEquals("应该相等!", "已承兑", res[0][3]);
		assertEquals("应该相等!", "已银行贴现", res[0][4]);
		assertEquals("应该相等!", "内部转让", res[0][5]);
		assertEquals("应该相等!", "对外转让", res[0][6]);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIF2() {
		String formula = "fkdw->iif(val2<>null,val2,\"dddd\")";
		FormulaParseFather f = getFormulaParser();
		f.addVariable("val1", new Double(56));
		ArrayList val2 = new ArrayList();
		val2.add(2.0);
		val2.add(3);
		f.addVariable("val2", val2);
		f.addVariable("pjzt", "01");
		f.setExpress(formula);
		// f.setNullAsZero(true);
		String[][] res = f.getValueSArray();
		assertEquals("应该相等!", "2.0", res[0][0]);
		assertEquals("应该相等!", "3", res[0][1]);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIFnew1() {
		String formula = "fkdw->iif((val2==null)==\"N\",val2,\"dddd\")";
		FormulaParseFather f = getFormulaParser();
		ArrayList val2 = new ArrayList();
		val2.add(2.0);
		val2.add(3);
		val2.add(null);
		f.addVariable("val2", val2);
		f.setExpress(formula);
		String[][] res = f.getValueSArray();
		assertEquals("应该相等!", "2.0", res[0][0]);
		assertEquals("应该相等!", "3", res[0][1]);
		assertEquals("应该相等!", "dddd", res[0][2]);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIFnew2() {
		String formula = "fkdw->iif((!(val2==null))==\"Y\",val2,\"dddd\")";
		FormulaParseFather f = getFormulaParser();
		ArrayList val2 = new ArrayList();
		val2.add(2.0);
		val2.add(3);
		val2.add(null);
		f.addVariable("val2", val2);
		f.setExpress(formula);
		String[][] res = f.getValueSArray();
		assertEquals("应该相等!", "2.0", res[0][0]);
		assertEquals("应该相等!", "3", res[0][1]);
		assertEquals("应该相等!", "dddd", res[0][2]);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIF3() {
		String formula = "iif(shzt==0,\"审批不通过\",iif(shzt==1,\"审批通过\",iif(shzt==2,\"审批进行中\",iif(shzt==3,\"提交态\",iif(shzt==4,\"作废态\",iif(shzt==5,\"冲销态\",iif(shzt==6,\"终止(结算)态\",iif(shzt==7,\"冻结态\",\"自由态\"))))))))";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Hashtable map = new Hashtable();
		String v1[] = new String[] { "2", "1", "0" };
		map.put("shzt", v1);
		f.setDataS(map);
		String[][] res = f.getValueSArray();
		assertEquals("应该相等!", "审批进行中", res[0][0]);
		assertEquals("应该相等!", "审批通过", res[0][1]);
		assertEquals("应该相等!", "审批不通过", res[0][2]);

	}

	@SuppressWarnings("unchecked")
	public void testComplexIIF4() {
		String formula = "pjzt11  ->  iif(pjzt=\"01\",\"已登记\",iif(pjzt=\"02\",\"已内部贴现\",iif(pjzt=\"03\",\"办托收\",iif(pjzt=\"04\",\"已承兑\",iif(pjzt=\"05\",\"已银行贴现\",iif(pjzt=\"06\",\"内部转让\",iif(pjzt=\"07\",\"对外转让\",iif(pjzt=\"08\",\"已抵押\",iif(pjzt=\"09\",\"抵押到期收回\",iif(pjzt=\"10\",\"抵押到期未收回\",iif(pjzt=\"11\",\"抵押未到期收回\",iif(pjzt=\"12\",\"退票\",iif(pjzt=\"13\",\"贴现后到期处理\",\"已开票\")))))))))))))";
		FormulaParseFather f = getFormulaParser();
		f.setExpress(formula);
		Hashtable map = new Hashtable();
		String v1[] = new String[] { "\"02\"", "\"01\"", "\"03\"", "\"07\"", "\"04\"" };
		map.put("pjzt", v1);
		f.setDataS(map);
		String[][] res = f.getValueSArray();
		assertEquals("应该相等!", "已内部贴现", res[0][0]);
		assertEquals("应该相等!", "已登记", res[0][1]);
		assertEquals("应该相等!", "办托收", res[0][2]);
		assertEquals("应该相等!", "对外转让", res[0][3]);
		f.setExpress(formula);
		res = f.getValueSArray();
		assertEquals("应该相等!", "已内部贴现", res[0][0]);
		assertEquals("应该相等!", "已登记", res[0][1]);
		assertEquals("应该相等!", "办托收", res[0][2]);
		assertEquals("应该相等!", "对外转让", res[0][3]);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIF5() {
		String formula = "fkdw->iif(val2==\"\",val2,\"dddd\")";
		FormulaParseFather f = getFormulaParser();
		f.addVariable("val1", new Double(56));
		ArrayList val2 = new ArrayList();
		val2.add(new Double(2));
		val2.add(null);
		f.addVariable("val2", val2);
		f.setExpress(formula);
		// f.setNullAsZero(true);
		String[][] res = f.getValueSArray();
		assertEquals("应该相等!", "dddd", res[0][0]);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIF6() {
		String formula = "fkdw->iif(val2==0,\"val2 is zero\",\"not zero\")";
		fglobal.addVariable("val1", new Double(56));
		ArrayList val2 = new ArrayList();
		val2.add(new Double(0));
		val2.add(null);
		fglobal.addVariable("val2", val2);
		fglobal.setNeedDoPostConvert(false);
		for (int i = 0; i < 1000; i++) {
			fglobal.setExpress(formula);
		}
		fglobal.setNullAsZero(true);
		String[][] res = fglobal.getValueSArray();
		assertEquals("应该相等!", "val2 is zero", res[0][0]);
		assertEquals("应该相等!", "val2 is zero", res[0][1]);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIF7() {
		String formula = "fkdw->iif(val2==null,\"val2 is zero\",val2)";
		fglobal.addVariable("val1", new Double(56));
		ArrayList val2 = new ArrayList();
		val2.add(new Double(0));
		val2.add(null);
		fglobal.addVariable("val2", val2);
		fglobal.setExpress(formula);
		fglobal.setNullAsZero(true);
		String[][] res = fglobal.getValueSArray();
		assertEquals("应该相等!", "val2 is zero", res[0][0]);
		assertEquals("应该相等!", "val2 is zero", res[0][1]);
	}

	/**
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void testComplexIIF8() {
		String formula = "fkdw->iif(iif(nnumber==NULL,0,nnumber)*iif(nprice==NULL,0,nprice)==0,\"Zero\",\"Not Zero\")";
		fglobal.addVariable("nnumber", new Double(56));
		ArrayList val2 = new ArrayList();
		val2.add(new Double(1));
		val2.add(null);
		fglobal.addVariable("nprice", val2);
		fglobal.setExpress(formula);
		String[] res = fglobal.getValueS();
		assertEquals("Not Zero", res[0]);
		assertEquals("Zero", res[1]);
	}
	public void testComplexIIF82() {
		String formula = "fkdw->1111;ddd->222";
		fglobal.setExpress(formula);
		String[] res = fglobal.getValueS();
		assertTrue(res.length ==1);
		assertEquals("1111", res[0]);
	}
	public void testComplexIIF83() {
		String[] formulas = {"fkdw->1111",
				"ddd->222"};
		fglobal.setExpressArray(formulas);
		Object[][] res = fglobal.getValueOArray();
		assertEquals(1111, res[0][0]);
		assertEquals(222, res[1][0]);
	}
	@SuppressWarnings("unchecked")
	public void testComplexIIF81() {
		String formula = "fkdw->iif(nnumber==NULL,0,nnumber)*iif(nprice==NULL,0,nprice)";
		fglobal.addVariable("nnumber", new Double(56));
		ArrayList val2 = new ArrayList();
		val2.add(1);
		val2.add(null);
		fglobal.addVariable("nprice", val2);
		fglobal.setExpress(formula);
		String[] res = fglobal.getValueS();
		assertEquals("应该相等!", "56.0", res[0]);
		assertEquals("应该相等!", "0.0", res[1]);
		String formula1 = "fkdw->nnumber*nprice";
		fglobal.setExpress(formula1);
		fglobal.setNullAsZero(false);
		res = fglobal.getValueS();
		assertEquals("应该相等!", "", res[1]);
	}

	@SuppressWarnings("unchecked")
	public void testComplexIIF9() {
		String formula = "fkdw->iif(nprice==NULL,0,nprice)";
		fglobal.addVariable("nnumber", new Double(56));
		ArrayList val2 = new ArrayList();
		val2.add(1.2);
		val2.add(null);
		fglobal.addVariable("nprice", val2);
		fglobal.setExpress(formula);
		String[] res = fglobal.getValueS();
		assertEquals("应该相等!", "1.2", res[0]);
		assertEquals("应该相等!", "0", res[1]);

	}
	

	public void testGetColValue() throws Exception {

	}

}
