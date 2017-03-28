package com.toolkit.drools.bean;
/**
 * 描述订单的实体
 * @author lihn
 *
 */
public class Order {
	/**
	 * 用户等级
	 */
	private int userRank;
	/**
	 * 快递方式
	 */
	private int expressType;
	/**
	 * 商品总价格
	 */
	private double amount;
	/**
	 * 快递费用
	 */
	private double expressFee;
	/**
	 * 积分
	 */
	private int credit;

	public Order(int userRank, int expressType, double amount) {
		this.userRank = userRank;
		this.expressType = expressType;
		this.amount = amount;
	}

	public int getUserRank() {
		return this.userRank;
	}

	public int getExpressType() {
		return this.expressType;
	}

	public void setExpressFee(double expressFee) {
		this.expressFee = expressFee;
	}

	public double getExpressFee() {
		return this.expressFee;
	}

	public double getAmount() {
		return this.amount;
	}

	public void doDiscount(double discount) {
		this.amount *= discount;
	}

	public int getCredit() {
		return this.credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
}