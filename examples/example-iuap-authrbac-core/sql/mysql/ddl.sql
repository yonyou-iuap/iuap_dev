/*功能注册*/
-- DROP TABLE ieop_function;
CREATE TABLE ieop_function ( id varchar(64)  NOT NULL, func_name varchar(64) NOT NULL, func_code varchar(64) NOT NULL, func_type varchar(64) NOT NULL, func_url varchar(200),iscontrol varchar(10) DEFAULT 'N' NOT NULL, isactive varchar(10) DEFAULT 'Y' NOT NULL, create_date timestamp DEFAULT '0000-00-00 00:00:00' NULL, isleaf varchar(10) DEFAULT 'N', parent_id bigint NOT NULL, sys_id VARCHAR(64)/* 应用id*/,tenant_id varchar(64)/* 租户id*/,enableaction varchar(10) DEFAULT 'N',PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*业务活动注册*/
-- DROP TABLE  ieop_function_activity;
CREATE TABLE ieop_function_activity  ( id varchar(64)  NOT NULL,activity_name VARCHAR(64) NOT NULL,  activity_code VARCHAR(64) NOT NULL,activity_url VARCHAR(200) NOT NULL, func_id VARCHAR(64) NOT NULL, isactive VARCHAR(10) DEFAULT 'Y' NOT NULL, create_date TIMESTAMP DEFAULT '0000-00-00 00:00:00' NULL, sysid VARCHAR(64) /* 应用id*/,tenant_id varchar(64)/* 租户id*/,PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 /*角色注册*/
-- DROP TABLE ieop_role;
CREATE TABLE ieop_role ( id varchar(64)  NOT NULL, role_name varchar(64) NOT NULL, role_code varchar(64) NOT NULL, role_type varchar(64) NOT NULL, isactive varchar(10) DEFAULT 'Y' NOT NULL, create_date timestamp DEFAULT '0000-00-00 00:00:00' NULL, sys_id VARCHAR(64)/* 应用id*/,tenant_id varchar(64)/* 租户id*/, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*功能角色注册*/
-- DROP TABLE ieop_role_permission;
CREATE TABLE ieop_role_permission ( id varchar(64)  NOT NULL, role_id varchar(64)  NOT NULL,  role_code varchar(64) NOT NULL,permission_id varchar(64)  NOT NULL, permission_code varchar(64) NOT NULL ,permission_type varchar(64), sys_id VARCHAR(64) /* 应用id*/,tenant_id varchar(64)/* 租户id*/, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--DROP TABLE ieop_permission_strategy;
CREATE TABLE ieop_permission_strategy ( id varchar(64)  NOT NULL, permission_type varchar(64) NOT NULL ,Permission_Strategy varchar(64) NOT NULL, sys_id VARCHAR(64)/* 应用id*/,tenant_id varchar(64)/* 租户id*/, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*用户角色注册*/
-- DROP TABLE ieop_role_user;
CREATE TABLE ieop_role_user ( id varchar(64)  NOT NULL, role_id varchar(64)  NOT NULL, role_code varchar(64) NOT NULL, user_id varchar(64) NOT NULL, user_code varchar(64) NOT NULL, sys_id VARCHAR(64)/* 应用id*/,tenant_id varchar(64)/* 租户id*/, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*用户注册*/
-- DROP TABLE ieop_user;
CREATE TABLE ieop_user ( id varchar(64)  NOT NULL , login_name varchar(64) NOT NULL, name varchar(64) NOT NULL, password varchar(255) NOT NULL, salt varchar(64) NOT NULL, roles varchar(255),  states varchar(64),register_date timestamp DEFAULT '0000-00-00 00:00:00', sys_id VARCHAR(64)/* 应用id*/,tenant_id varchar(64)/* 租户id*/, PRIMARY KEY (id), CONSTRAINT login_name UNIQUE (login_name) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*数据权限*/
-- DROP TABLE ieop_dataPermission;
CREATE TABLE ieop_datapermission ( id varchar(64)  NOT NULL, role_id varchar(64)  NOT NULL, resource_id varchar(64)  NOT NULL ,resourcetypecode varchar(64) DEFAULT 'organiztion' ,operationcode varchar(64) DEFAULT 'read', sys_id VARCHAR(64)/* 应用id*/,tenant_id varchar(64)/* 租户id*/, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* tablename: 数据权限分表记录 */
-- DROP TABLE ieop_dpprofile_reg;
CREATE TABLE ieop_dpprofile_reg ( id varchar(64)  NOT NULL, resourcetypecode varchar(64) NOT NULL, resourcetypename varchar(64) NOT NULL,reftypecode varchar(64),dptablename varchar(64),operationcode varchar(64) DEFAULT 'read', dataconverturl varchar(200),sys_id VARCHAR(64)/* 应用id*/,tenant_id varchar(64)/* 租户id*/, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
