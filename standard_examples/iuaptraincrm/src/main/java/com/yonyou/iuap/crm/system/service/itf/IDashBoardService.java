package com.yonyou.iuap.crm.system.service.itf;

import java.util.Map;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.system.entity.DashBoardVO;

public interface IDashBoardService {
	public Map<String,Object> queryDashBoard() throws AppBusinessException;
}
