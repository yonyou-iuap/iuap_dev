package com.yonyou.iuap.example.disconf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.disconf.client.common.annotations.DisconfFile;
import com.yonyou.iuap.disconf.client.common.annotations.DisconfFileItem;

//copy2TargetDirPath可以下载到指定目录
@DisconfFile(filename="simple.properties",copy2TargetDirPath="/etc/conf/download")
public class DisConfBean {
	
	private static final Logger LOG = LoggerFactory.getLogger(DisConfBean.class);

	private String userName;
	
	private String userCode;
	
	private String memo;

	@DisconfFileItem(name = "userName", associateField = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		LOG.info("method setUserName has been called！");
	}

	@DisconfFileItem(name = "userCode", associateField = "userCode")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
		LOG.info("method setUserCode has been called！");
	}

	@DisconfFileItem(name = "memo", associateField = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
		LOG.info("method setMemo has been called！");
	}
	
}
