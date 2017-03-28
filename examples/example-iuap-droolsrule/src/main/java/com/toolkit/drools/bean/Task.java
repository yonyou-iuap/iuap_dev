package com.toolkit.drools.bean;

/**
 * 用户获取积分的方式实体，如注册任务获取相应的积分
 * 
 * @author lihn
 *
 */
public class Task {
	/**
	 * 任务类型，包括注册、完善资料等
	 */
	private int taskType;
	/**
	 * 任务相应的积分
	 */
	private int credit;

	public Task(int taskType) {
		this.taskType = taskType;
	}

	public int getTaskType() {
		return this.taskType;
	}

	public int getCredit() {
		return this.credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
}