package com.yonyou.iuap.drools.test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.toolkit.drools.UserRule;
import com.toolkit.drools.UserRuleImpl;
public class MainTest {
	private UserRule userRule =null;
	
	@Before
	public void start(){
		userRule = new UserRuleImpl() ;
	}
	
	@Test
	public void	getTaskCredit(){
		int taskCredit=userRule.getTaskCredit(0);
		Assert.assertEquals(5, taskCredit);
		taskCredit=userRule.getTaskCredit(1);
		Assert.assertEquals(2, taskCredit);
	}
	
	@Test
	public void	getExpressAmount(){
		double expressAmount=userRule.getExpressAmount(0, 0, 89);
		Assert.assertEquals(0, expressAmount,2);
		expressAmount=userRule.getExpressAmount(0, 99, 1);
		Assert.assertEquals(49, expressAmount,2);
		expressAmount=userRule.getExpressAmount(0, -1, 89);
		Assert.assertEquals(0, expressAmount,2);
		expressAmount=userRule.getExpressAmount(0, 0, 70);
		Assert.assertEquals(15.0, expressAmount,2);
		
	}
	
	@Test
	public void getUserRank(){
	int userRank=	userRule.getUserRank(50001);
	Assert.assertEquals(99,userRank);
	 userRank=	userRule.getUserRank(5001);
	Assert.assertEquals(1,userRank);
	 userRank=	userRule.getUserRank(501);
	Assert.assertEquals(0,userRank);		
	}
	
	@Test
	public void	getDiscountPrice(){		
		double realAmount = userRule.getDiscountPrice(99 ,100) ;//8折 促销*0.8
		Assert.assertEquals(64, realAmount, 2);
		 realAmount = userRule.getDiscountPrice(1 ,100) ;//95折 促销*0.8
		 Assert.assertEquals(76, realAmount, 2);
		 realAmount = userRule.getDiscountPrice(0 ,100) ;//原价  促销*0.8
		Assert.assertEquals(80, realAmount, 2);
		
	}
	
}
