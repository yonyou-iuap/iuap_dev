package com.toolkit.drools;

import com.toolkit.drools.bean.Order;
import com.toolkit.drools.bean.Task;
import com.toolkit.drools.bean.User;

public class UserRuleImpl implements UserRule {
	private DroolsEngine droolsEngine;

	public UserRuleImpl() {
		this.droolsEngine = new DroolsEngine(
				new String[] { "credit-rule.drl", "express-rule.drl", "rank-rule.drl", "discount-rule.drl" });
	}

	public int getTaskCredit(int taskType) {
		Task task = new Task(taskType);
		this.droolsEngine.fireAllRules(task);
		return task.getCredit();
	}

	public double getExpressAmount(int userRank, int expressType, double amount) {
		Order order = new Order(userRank, expressType, amount);
		this.droolsEngine.fireAllRules(order);
		return order.getExpressFee();
	}

	public int getUserRank(long credit) {
		User user = new User(credit);
		this.droolsEngine.fireAllRules(user);
		return user.getRank();
	}

	public double getDiscountPrice(int userRank, double amount) {
		Order order = new Order(userRank, -2147483648, amount);
		this.droolsEngine.fireAllRules(order);
		return order.getAmount();
	}
}