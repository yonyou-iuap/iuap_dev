package com.yonyou.iuap.crm.system.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.vo.BaseEntity;
@Entity
public class DashBoardVO extends BaseEntity {
	private int pocounts;
	private int vehiclecounts;
	public int getPocounts() {
		return pocounts;
	}
	public void setPocounts(int pocounts) {
		this.pocounts = pocounts;
	}
	public int getVehiclecounts() {
		return vehiclecounts;
	}
	public void setVehiclecounts(int vehiclecounts) {
		this.vehiclecounts = vehiclecounts;
	}
}
