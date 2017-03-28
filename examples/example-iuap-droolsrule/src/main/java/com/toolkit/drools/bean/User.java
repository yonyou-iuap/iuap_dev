package com.toolkit.drools.bean;

/**
 * 用户实体
 * 
 * @author lihn
 *
 */
public class User {
	/**
	 * 用户积分
	 */
	private long credit;
	/**
	 * 用户等级
	 */
	private int rank;

	public User(long credit) {
		this.credit = credit;
	}

	public long getCredit() {
		return this.credit;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
