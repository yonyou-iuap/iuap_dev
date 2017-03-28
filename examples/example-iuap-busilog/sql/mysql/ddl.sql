-- drop table if exists busilog;

CREATE TABLE busilog(
     id VARCHAR(40) NOT NULL,
     clientip VARCHAR(18),/*IP*/
     operuser VARCHAR(40),/*用户*/
	 logcategory  VARCHAR(40),/*日志分类*/
	 logcontent  VARCHAR(500),/*日志内容*/
	 sysid VARCHAR(40),/* 应用id*/
	 tenantid VARCHAR(40),/* 租户id*/
     logdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
