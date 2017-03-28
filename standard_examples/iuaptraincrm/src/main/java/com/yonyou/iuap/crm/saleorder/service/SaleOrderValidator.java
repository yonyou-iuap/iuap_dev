package com.yonyou.iuap.crm.saleorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.saleorder.dao.SaleorderDao;
import com.yonyou.iuap.crm.saleorder.entity.Orderdetail;
import com.yonyou.iuap.crm.saleorder.entity.Saleorder;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;

@Component
public class SaleOrderValidator {

	@Autowired
	private SaleorderDao dao;
	
	public void validate(Saleorder entity) {
		List<Orderdetail> list = entity.getId_orderdetail();
		StringBuffer errorbuf = new StringBuffer();

		// 子表校验
		if (list != null) {
			for (Orderdetail detail : list) {
				String materid = detail.getCmaterialid();
				if (StringUtil.isEmpty(materid)) {
					errorbuf.append("物料编码不能为空,");
				}

				// 主数量>0价格>0
				Integer num = detail.getNastnum();
				if (num != null) {
					if (num.intValue() <= 0) {
						errorbuf.append("数量应该大于0,\n");
					}
				}
				Double price = detail.getNqtorigprice();
				if (price != null && price.doubleValue() < 0) {
					errorbuf.append("价格应该大于等于0,\n");
				}
			}
		}

		if (errorbuf.length() > 0) {
			throw new AppBusinessException(errorbuf.toString());
		}
	}

}
