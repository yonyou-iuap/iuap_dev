package com.yonyou.iuap.crm.basedata.entity;


import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import org.springframework.data.annotation.Transient;

/**
 * The persistent class for the tm_competbrand database table.
 * 
 */
@Entity
@Table(name = "tm_competbrand")
public class TmCompetBrandExtVO extends TmCompetBrandVO {
	private static final long serialVersionUID = -6718244035830801529L;
	@Transient
	private String showStatus;

	@Column(name = "vcbrandname")
	private String combrandname;//竞品品牌名称

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public String getCombrandname() {
		return combrandname;
	}

	public void setCombrandname(String combrandname) {
		this.combrandname = combrandname;
	}
	
	

}