-- DROP TABLE IF EXISTS pub_securitylog;
CREATE TABLE pub_securitylog (
  ID varchar(200) NOT NULL,
  TIMESTAMP timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  MY_PRODUCT varchar(255) default NULL,
  CATEGORY varchar(255) default NULL,
  LEVEL varchar(255) default NULL,
  NOTICE varchar(255) default NULL,
  USER_ID varchar(255) default NULL,
  USER_CODE varchar(255) default NULL,
  USER_AUTHTYPR varchar(255) default NULL,
  USER_IDENTIFY varchar(255) default NULL,
  LESSEE varchar(255) default NULL,
  IP varchar(255) default NULL,
  SYSTEM varchar(255) default NULL,
  RESULT varchar(255) default NULL,
  CONTENTDES varchar(1024) default NULL,
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
