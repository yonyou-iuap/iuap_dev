package com.yonyou.iuap.crm.common.exception;

import com.yonyou.iuap.persistence.vo.pub.BusinessException;

public class AppBusinessException extends BusinessException {
	private static final long serialVersionUID = -1226029198803081798L;

	public AppBusinessException(String msg) {
		super(msg);
	}

	public AppBusinessException(String msg, Throwable th) {
		super(msg, th);
	}
}
