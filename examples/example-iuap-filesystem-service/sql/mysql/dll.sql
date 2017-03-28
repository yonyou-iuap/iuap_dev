-- DROP table if exists pub_filesystem;
CREATE TABLE pub_filesystem (
  id varchar(36) NOT NULL ,
  pkfile varchar(100) NOT NULL,
  filename varchar(100) NOT NULL,
  filepath varchar(100) NOT NULL,
  filesize varchar(100) NOT NULL,
  groupname varchar(100) NOT NULL,
  permission varchar(20) NOT NULL,
  uploader varchar(36),
  uploadtime varchar(100),
  sysid varchar(100),
  tenant varchar(100),
  modular varchar(100),
  url varchar(1000),
  secretkey varchar(1000),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
