-- DROP table if exists pub_eventtype;
create table pub_eventtype
(
   id                   varchar(48) not null,
   sourceid             varchar(40) not null,
   sourcename           varchar(200),
   sourcenamei18n       varchar(40),
   eventtypecode        varchar(40) not null,
   eventtypename        varchar(200),
   eventtypenamei18n    varchar(40),
   note                 varchar(1024),
   last_time            varchar(20),
   version              bigint default 1,
   primary key (id),
   key uq_eventtype (sourceid, eventtypecode)
)comment = "事件类型表";

-- DROP table if exists pub_eventlistener;
create table pub_eventlistener
(
   id                   varchar(48) not null,
   event_type_id        varchar(48) not null,
   name                 varchar(200),
   namei18n             varchar(40),
   implclassname        varchar(200) not null,
   note                 varchar(1024),
   operindex            smallint not null,
   enabled              char(1) not null comment 'Y/N',
   last_time            varchar(20),
   version              bigint default 1,
   primary key (id)
)
comment = "事件通知业务组根据需要注册插件的表";
