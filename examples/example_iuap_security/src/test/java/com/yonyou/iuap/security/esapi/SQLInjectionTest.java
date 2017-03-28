package com.yonyou.iuap.security.esapi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yonyou.iuap.security.esapi.IUAPEncoder.DatabaseCodec;

/**
 * 
 * SQL注入问题是比较常见的安全漏洞，在OWASP TOP 10 2013中名列第一，需要引起大家重视。
 * SQL注入问题本质是将SQL脚本和数据混在一起，把数据当成代码执行了，在编程时采用字符串拼接SQL时会出现这种情况。
 * 
 * 解决SQL注入问题有以下三个方案，请大家依据不同的应用场景选择合适的方案。
 * 
 * 方案1：采用JDBC java.sql.PreparedStatement方法，这种情况一般适合简单的场景，使用也比较简单。 示例：
 * PreparedStatement pstmt =
 * con.prepareStatement("UPDATE EMPLOYEES SET SALARY = ? WHERE ID = ?");
 * pstmt.setBigDecimal(1, 10000.00); pstmt.setInt(2, 110592)
 * 
 * 方案2：使用简单转码方式。在解决遗留代码问题，如果SQL的拼接是通过很多复杂函数完成的，使用方案1比较困难，可以考虑本方案。
 * 示例：见testSolution2()。
 * 
 * 方案3：使用复杂转码方式。对于复杂拼接字符串的场景，可以采用此方案。 示例：见testSolution3()。
 * 
 * 方案4：使用输入验证，例如数字输入的项，需要禁止非法字符输入。对于复杂输入场景，该方案部不能彻底解决问题。需要结合 上述三个方案一起解决注入问题。
 * 示例：略。
 * 
 * 
 */
public class SQLInjectionTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * SQL注入方案2示例 假设user_name1和user_pwd1是不安全的用户手工输入。
	 */
	@Test
	public void testSolution2() {
		String selectUser = "select * from user where user_name='";

		// case 1: 正常输入
		String user_name1 = "zhangsan";
		String user_pwd1 = "123456";
		String user_name2 = IUAPESAPI.encoder().sqlEncode(user_name1,
				DatabaseCodec.ORACLE);
		String user_pwd2 = IUAPESAPI.encoder().sqlEncode(user_pwd1,
				DatabaseCodec.ORACLE);
		String query1 = selectUser + user_name1 + "' and user_pwd = '"
				+ user_pwd1 + "'";
		String query2 = selectUser + user_name2 + "' and user_pwd = '"
				+ user_pwd2 + "'";
		System.out.println();
		System.err
				.println("testSolution2-->case 1-->: **************************   正常输入   **************************");
		System.err.println("testSolution2-->case 1-->转码前: " + query1);
		System.err.println("testSolution2-->case 1-->转码后: " + query2);

		// case 2: 异常输入
		user_name1 = "zhangsan";
		user_pwd1 = "' or '1' = '1";
		user_name2 = IUAPESAPI.encoder().sqlEncode(user_name1, DatabaseCodec.ORACLE);
		user_pwd2 = IUAPESAPI.encoder().sqlEncode(user_pwd1, DatabaseCodec.ORACLE);
		query1 = selectUser + user_name1 + "' and user_pwd = '" + user_pwd1
				+ "'";
		query2 = selectUser + user_name2 + "' and user_pwd = '" + user_pwd2
				+ "'";
		System.out.println("");
		System.err
				.println("testSolution2-->case 2-->: **************************   异常输入   **************************");
		System.err.println("testSolution2-->case 2-->转码前: " + query1);
		System.err.println("testSolution2-->case 2-->转码后: " + query2);

		// case 3: 异常输入（危害攻击）
		user_name1 = "zhangsan";
		user_pwd1 = "'; delete from user where '1' = '1";
		user_name2 = IUAPESAPI.encoder().sqlEncode(user_name1, DatabaseCodec.ORACLE);
		user_pwd2 = IUAPESAPI.encoder().sqlEncode(user_pwd1, DatabaseCodec.ORACLE);
		query1 = selectUser + user_name1 + "' and user_pwd = '" + user_pwd1
				+ "'";
		query2 = selectUser + user_name2 + "' and user_pwd = '" + user_pwd2
				+ "'";
		System.out.println();
		System.err
				.println("testSolution2-->case 3-->: **************************   异常输入（危害攻击）   **************************");
		System.err.println("testSolution2-->case 3-->转码前（悲剧发生了！）: " + query1);
		System.err.println("testSolution2-->case 3-->转码后: " + query2);

	}

	/**
	 * SQL注入方案3示例 假设user_name1和user_pwd1是不安全的用户手工输入。
	 */
	@Test
	public void testSolution3() {
		// 字符串参数的"?"两次需要加单引号
		String conditionStr = "where user_name='?' and user_pwd = '?' and age > ?";

		// case 1: 正常输入
		String user_name1 = "zhangsan";
		String user_pwd1 = "123456";
		String age = "30";
		String[] paras = new String[3];
		paras[0] = user_name1;
		paras[1] = user_pwd1;
		paras[2] = age;

		String queryCondition = IUAPESAPI.encoder().sqlPreparedString(conditionStr,
				paras, DatabaseCodec.ORACLE);
		System.out.println("");
		System.err
				.println("testSolution3-->case 1-->: **************************   正常输入   **************************");
		System.err.println("testSolution3-->case 1-->转码前: where user_name='"
				+ user_name1 + "' and user_pwd = '" + user_pwd1
				+ "' and age > " + age);
		System.err.println("testSolution3-->case 1-->转码后: " + queryCondition);

		// case 2: 异常输入
		user_name1 = "zhangsan";
		user_pwd1 = "' or '1' = '1' --";
		age = "30";
		paras[0] = user_name1;
		paras[1] = user_pwd1;
		paras[2] = age;

		queryCondition = IUAPESAPI.encoder().sqlPreparedString(conditionStr, paras,
				DatabaseCodec.ORACLE);
		System.out.println();
		System.err
				.println("testSolution3-->case 2-->: **************************   异常输入   **************************");
		System.err.println("testSolution3-->case 1-->转码前: where user_name='"
				+ user_name1 + "' and user_pwd = '" + user_pwd1
				+ "' and age > " + age);
		System.err.println("testSolution3-->case 2-->转码后(攻击失败）: "
				+ queryCondition);

	}
}
