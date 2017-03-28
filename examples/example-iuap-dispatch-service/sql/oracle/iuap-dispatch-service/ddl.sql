-- DROP TABLE   dispatch_group;
-- DROP TABLE   dispatch_task;
-- DROP TABLE   dispatch_taskway;
-- DROP TABLE   dispatch_taskparam;
-- DROP TABLE   dispatch_tasklog;
-- DROP TABLE   dispatch_tasktime;
-- DROP TABLE   dispatch_taskuser;
-- DROP TABLE   dispatch_task_recallparam;
-- DROP TABLE   dispatch_taskwayclass;
-- DROP TABLE   ref_refinfo;
-- DROP TABLE   qrtz_calendars;
-- DROP TABLE   qrtz_fired_triggers;
-- DROP TABLE   qrtz_blob_triggers;
-- DROP TABLE   qrtz_cron_triggers;
-- DROP TABLE   qrtz_simple_triggers;
-- DROP TABLE   qrtz_simprop_triggers;
-- DROP TABLE   qrtz_triggers;
-- DROP TABLE   qrtz_job_details;
-- DROP TABLE   qrtz_paused_trigger_grps;
-- DROP TABLE   qrtz_locks;
-- DROP TABLE   qrtz_scheduler_state;



CREATE TABLE ref_refinfo (
  pk_ref varchar(200) NOT NULL,
  refname varchar(300) DEFAULT NULL,
  refcode varchar(300) DEFAULT NULL,
  refclass varchar(300) DEFAULT NULL,
  refurl varchar(300) DEFAULT NULL,
  md_entitypk varchar(300) DEFAULT NULL,
  productType varchar(300) DEFAULT NULL,
  PRIMARY KEY (pk_ref)
);

CREATE TABLE dispatch_group(
     id VARCHAR(40) NOT NULL,
    sysid VARCHAR(40),/* 应用id*/
    tenantid VARCHAR(40),/* 租户id*/
    name VARCHAR(200) NOT NULL,/*分组名称*/
     PRIMARY KEY (id)
);

CREATE TABLE dispatch_task(
     id VARCHAR(40) NOT NULL,
    sysid VARCHAR(40),/* 应用id*/
    tenantid VARCHAR(40),/* 租户id*/
    code VARCHAR(200) NOT NULL,/*任务编码*/
    name VARCHAR(200) NOT NULL,/*任务名称*/
     groupid VARCHAR(40),  /*对应分组id*/
     cronexpression VARCHAR(200),/*定时规则表达式*/
     taskwayid VARCHAR(40),/*调用方式ID*/
     description VARCHAR(250), /*描述信息*/
     flag INTEGER DEFAULT '1',/*1启动，0暂停*/
     createtime  TIMESTAMP,/*任务创建时间*/
     userid VARCHAR(64),  /*创建人ID*/
     PRIMARY KEY (id)
);


CREATE TABLE dispatch_tasktime(
     id VARCHAR(40) NOT NULL,
    starttime TIMESTAMP,/* 开始时间*/
    endtime TIMESTAMP,/* 结束时间*/
    modetype INTEGER DEFAULT '0',/*天0/周1/月2*/
    modefrequency INTEGER DEFAULT '0', /*每隔【value】多久(单位是天，周，月)*/
    modevaule VARCHAR(40), /*周1-7/月1-31,0*/
    duramode INTEGER DEFAULT '0',/*一次0/1周期*/
    duravalue VARCHAR(200),/*一次值或者周期值*/
    periodmode INTEGER DEFAULT '0',/*0小时/1分钟*/
    periodstart VARCHAR(40),/*一天中的开始时间段，时：分*/
    periodend VARCHAR(40),/*一天中的结束时间段，时：分*/
    taskid VARCHAR(40),/*任务的id*/
     PRIMARY KEY (id)
);


CREATE TABLE dispatch_taskuser(
     id VARCHAR(40) NOT NULL,
    ucode VARCHAR(200),/* 用户编码*/
    uname VARCHAR(200),/* 用户名称*/
    msg INTEGER DEFAULT '0',/*消息中心接收推送*/
    email INTEGER DEFAULT '0', /*email接收推送*/
    phone INTEGER DEFAULT '0', /*电话接收推送*/
    taskid VARCHAR(40), /*对应的任务id*/
    userid VARCHAR(64), /*对应的用户id*/
     PRIMARY KEY (id)
) ;
CREATE TABLE dispatch_taskway(
     id VARCHAR(40) NOT NULL,
     code VARCHAR(200),/* 编码*/
     name VARCHAR(200),/* 名称*/
    url VARCHAR(200),/* http的url或者class名称*/
    classname VARCHAR(200),
        taskwayclassid VARCHAR(40),
     PRIMARY KEY (id)
) ;

CREATE TABLE dispatch_taskwayclass(
     id VARCHAR(40) NOT NULL,
     sysid VARCHAR(40),
    tenantid VARCHAR(40),
    code VARCHAR(200) NOT NULL,
    name VARCHAR(200) NOT NULL,
    parentid VARCHAR(40),
     PRIMARY KEY (id)
) ;

insert into DISPATCH_TASKWAYCLASS (ID, SYSID, TENANTID, CODE, NAME, PARENTID) values ('001', null, null, 'testClass01', 'testClass01', null);


CREATE TABLE dispatch_taskparam(
     id VARCHAR(40) NOT NULL,
    code VARCHAR(200),/* 名称*/
    name VARCHAR(200),/* 参数名称*/
    paramvalue VARCHAR(200),/* 参数取值*/
    taskwayid VARCHAR(40),
     PRIMARY KEY (id)
);

insert into DISPATCH_TASKWAY (ID, CODE, NAME, URL, CLASSNAME, TASKWAYCLASSID) values ('001', 'rule01', 'rule01', 'http://localhost:8080/iuap-saas-dispatch-service/taskReturnExample/success', null, '001');
insert into DISPATCH_TASKWAY (ID, CODE, NAME, URL, CLASSNAME, TASKWAYCLASSID) values ('002002', '002', '002', 'http://localhost:8080', null, '001');


CREATE TABLE dispatch_task_recallparam(
     id VARCHAR(40) NOT NULL,
     taskwayid VARCHAR(40) NOT NULL,/*规则主键*/
     taskparamid VARCHAR(40) NOT NULL,/*规则参数主键*/
     taskid VARCHAR(40) NOT NULL,/*任务主键*/
    paramvalue VARCHAR(200), /*回调参数值*/
    PRIMARY KEY (id)
);

CREATE TABLE dispatch_tasklog(
     id VARCHAR2(40) NOT NULL,
    starttime TIMESTAMP,/* 开始时间*/
    endtime TIMESTAMP,/* 结束时间*/
    visible INTEGER DEFAULT '1',/*0不显示，1显示*/
    flag INTEGER DEFAULT '0',/*0失败，1成功*/
     content blob,  /*对应异常日志的内容*/
     taskid VARCHAR2(40),/*对应任务的id*/
     jobcode VARCHAR2(64),/*非界面添加的任务code*/
     jobgroupcode VARCHAR2(64),/*非界面添加的任务分组code*/
     PRIMARY KEY (id)
);

CREATE TABLE qrtz_job_details
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    JOB_CLASS_NAME   VARCHAR2(250) NOT NULL,
    IS_DURABLE VARCHAR2(1) NOT NULL,
    IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
    IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);
CREATE TABLE qrtz_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(200) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_TRIGGER_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
      REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);
CREATE TABLE qrtz_simple_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(10) NOT NULL,
    CONSTRAINT QRTZ_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_SIMPLE_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_cron_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    CRON_EXPRESSION VARCHAR2(120) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    CONSTRAINT QRTZ_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
      REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_simprop_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    STR_PROP_1 VARCHAR2(512) NULL,
    STR_PROP_2 VARCHAR2(512) NULL,
    STR_PROP_3 VARCHAR2(512) NULL,
    INT_PROP_1 NUMBER(10) NULL,
    INT_PROP_2 NUMBER(10) NULL,
    LONG_PROP_1 NUMBER(13) NULL,
    LONG_PROP_2 NUMBER(13) NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR2(1) NULL,
    BOOL_PROP_2 VARCHAR2(1) NULL,
    CONSTRAINT QRTZ_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_SIMPROP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
      REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_blob_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_calendars
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    CALENDAR_NAME  VARCHAR2(200) NOT NULL,
    CALENDAR BLOB NOT NULL,
    CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);
CREATE TABLE qrtz_paused_trigger_grps
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_GROUP  VARCHAR2(200) NOT NULL,
    CONSTRAINT QRTZ_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_fired_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    SCHED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(200) NULL,
    JOB_GROUP VARCHAR2(200) NULL,
    IS_NONCONCURRENT VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    CONSTRAINT QRTZ_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);
CREATE TABLE qrtz_scheduler_state
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);
CREATE TABLE qrtz_locks
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    LOCK_NAME  VARCHAR2(40) NOT NULL,
    CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);
