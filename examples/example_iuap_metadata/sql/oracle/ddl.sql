/* tablename: md_component */
-- drop table md_component;
create table md_component (id varchar2(50) not null 
/*组件ID*/,
versiontype smallint not null 
/*版本标识*/,
namespace varchar2(512) null 
/*名称空间*/,
ownmodule varchar2(50) null 
/*所属模块*/,
name varchar2(50) not null 
/*组件名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
creator varchar2(30) null 
/*创建人*/,
modifier varchar2(30) null 
/*修改人*/,
createtime char(19) null 
/*创建时间*/,
modifytime char(19) null 
/*修改时间*/,
version varchar2(512) null 
/*组件版本*/,
resmodule varchar2(90) null 
/*资源模块*/,
resid varchar2(90) null 
/*名称资源ID*/,
preload char(1) null 
/*是否预加载*/,
isbizmodel char(1) null 
/*是否业务模型组件*/,
fromsourcebmf char(1) null 
/*是否来源于原始bmf*/,
industry varchar2(50) not null 
/*行业*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_component primary key (id,versiontype,industry),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 数据类型及实体表 */
-- drop table md_class;
create table md_class (id varchar2(50) not null 
/*classID*/,
versiontype smallint not null 
/*版本标识*/,
parentclassid varchar2(50) null 
/*父实体ID*/,
componentid varchar2(50) null 
/*组件ID*/,
keyattribute varchar2(50) null 
/*主键属性*/,
name varchar2(50) not null 
/*名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
fullclassname varchar2(256) null 
/*类全路径名称*/,
classtype smallint null 
/*数据类型*/,
accessorclassname varchar2(256) null 
/*访问器对应类名*/,
fixedlength char(1) null 
/*是否定长*/,
precise smallint null 
/*精度*/,
refmodelname varchar2(768) null 
/*参照名称*/,
returntype varchar2(50) null 
/*枚举类型返回类型*/,
isprimary char(1) null 
/*是否主实体*/,
isactive char(1) null 
/*是否启用*/,
creator varchar2(30) null 
/*创建人*/,
modifier varchar2(30) null 
/*修改人*/,
createtime char(19) null 
/*创建时间*/,
modifytime char(19) null 
/*修改时间*/,
isauthen char(1) null 
/*是否安全授权*/,
resid varchar2(90) null 
/*名称资源ID*/,
bizitfimpclassname varchar2(256) null 
/*业务接口实现类*/,
modinfoclassname varchar2(256) null 
/*元数据动态修改类*/,
iscreatesql char(1) null 
/*是否生成建库脚本*/,
defaulttablename varchar2(50) null 
/*默认表表名*/,
isextendbean char(1) null 
/*继承标签*/,
userdefclassname varchar2(256) null 
/*自定义功能类名*/,
stereotype varchar2(256) null 
/*版型*/,
industry varchar2(50) not null 
/*行业*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_class primary key (id,versiontype,industry),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: md_association */
-- drop table md_association;
create table md_association (id varchar2(50) not null 
/*关联关系ID*/,
versiontype smallint not null 
/*版本标识*/,
name varchar2(60) not null 
/*名称*/,
componentid varchar2(50) null 
/*组件ID*/,
startbeanid varchar2(50) not null 
/*起始BeanID*/,
startelementid varchar2(50) not null 
/*起始元素ID*/,
endelementid varchar2(50) not null 
/*关联末端元素ID*/,
startcardinality varchar2(10) null 
/*起始重复度*/,
endcardinality varchar2(10) null 
/*末端重复度*/,
type smallint null 
/*关联类型*/,
cascadeupdate char(1) null 
/*是否级联更新*/,
cascadedelete char(1) null 
/*是否级联删除*/,
isactive char(1) null 
/*是否启用*/,
creator varchar2(30) null 
/*创建人*/,
modifier varchar2(30) null 
/*修改人*/,
createtime char(19) null 
/*创建时间*/,
modifytime char(19) null 
/*修改时间*/,
industry varchar2(50)   null 
/*创建行业*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_association primary key (id),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 属性表 */
-- drop table md_property;
create table md_property (id varchar2(50) not null 
/*属性ID*/,
versiontype smallint not null 
/*版本标识*/,
name varchar2(50) not null 
/*名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
classid varchar2(50) null 
/*所属类ID*/,
defaultvalue varchar2(512) null 
/*默认值*/,
attrminvalue varchar2(50) null 
/*最小值*/,
attrmaxvalue varchar2(50) null 
/*最大值*/,
fixedlength char(1) null 
/*是否定长*/,
attrlength smallint null 
/*长度*/,
precise smallint null 
/*精度*/,
datatype varchar2(50) null 
/*类型ID*/,
datatypestyle smallint null 
/*数据类型样式*/,
nullable char(1) null 
/*是否可为空*/,
customattr char(1) null 
/*是否自定义属性*/,
calculation char(1) null 
/*是否计算属性*/,
readonly char(1) null 
/*是否只读*/,
visibility smallint null 
/*可见性*/,
accessorclassname varchar2(256) null 
/*访问器对应类名*/,
attrsequence smallint null 
/*序号*/,
hided char(1) null 
/*是否隐藏*/,
refmodelname varchar2(256) null 
/*参照名称*/,
isactive char(1) null 
/*是否启用*/,
creator varchar2(30) null 
/*创建人*/,
modifier varchar2(30) null 
/*修改人*/,
createtime char(19) null 
/*创建时间*/,
modifytime char(19) null 
/*修改时间*/,
isauthen char(1) null 
/*是否安全授权*/,
resid varchar2(90) null 
/*名称资源ID*/,
dynamicattr char(1) null 
/*是否动态属性*/,
notserialize char(1) null 
/*是否不可序列化*/,
accesspower char(1) null 
/*使用权*/,
accesspowergroup varchar2(256) null 
/*使用权组*/,
industry varchar2(50) not null 
/*行业*/,
createindustry varchar2(50) null 
/*创建行业*/,
dynamictable varchar2(50) null 
/*动态表*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_property primary key (id,versiontype,industry),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 表 */
-- drop table md_table;
create table md_table (id varchar2(50) not null 
/*表ID*/,
versiontype smallint not null 
/*版本标识*/,
displayname varchar2(90) null 
/*显示名称*/,
name varchar2(50) not null 
/*名称*/,
databaseid varchar2(50) null 
/*数据库ID*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
isactive char(1) null 
/*是否启用*/,
creator varchar2(30) null 
/*创建人*/,
modifier varchar2(30) null 
/*修改人*/,
createtime char(19) null 
/*创建时间*/,
modifytime char(19) null 
/*修改时间*/,
resid varchar2(90) null 
/*名称资源ID*/,
parenttable varchar2(50) null 
/*父表id*/,
isextendtable char(1) null 
/*是否扩展表*/,
fromsourcebmf char(1) null 
/*是否源于bmf*/,
industry varchar2(50) null 
/*行业*/,
tenantid varchar2(50) null 
/*租户ID*/,
resmodule varchar2(50) null 
/*多语资源目录*/,
 constraint pk_md_table primary key (id),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 字段信息表 */
-- drop table md_column;
create table md_column (id varchar2(50) not null 
/*字段信息ID*/,
versiontype smallint not null 
/*版本标识*/,
tableid varchar2(50) null 
/*表ID*/,
name varchar2(50) not null 
/*名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
isactive char(1) null 
/*是否启用*/,
sqldatetype varchar2(50) null 
/*SQLDateType*/,
pkey char(1) null 
/*是否为主键*/,
identitied char(1) null 
/*isIdentity*/,
incrementstep smallint null 
/*incrementStep*/,
incrementseed smallint null 
/*incrementSeed*/,
nullable char(1) null 
/*是否可为空*/,
precise smallint null 
/*精度*/,
columnlength smallint null 
/*长度*/,
defaultvalue varchar2(512) null 
/*默认值*/,
columnsequence smallint null 
/*序号*/,
modifytime char(19) null 
/*修改时间*/,
createtime char(19) null 
/*创建时间*/,
modifier varchar2(30) null 
/*修改人*/,
creator varchar2(30) null 
/*创建人*/,
resid varchar2(90) null 
/*名称资源ID*/,
groupid varchar2(50) null 
/*组id*/,
columntype smallint null 
/*列类型*/,
forlocale char(1) null 
/*是否支持本地语言*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_column primary key (id),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);


/* tablename: 数据库表关系表 */
-- drop table md_db_relation;
create table md_db_relation (id varchar2(50) not null 
/*数据库表关联ID*/,
versiontype smallint not null 
/*版本标识*/,
name varchar2(90) not null 
/*名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
starttableid varchar2(50) not null 
/*起始表ID*/,
startfieldid varchar2(50) not null 
/*起始字段ID*/,
startattrid varchar2(50) null 
/*起始属性*/,
startcardinality varchar2(10) null 
/*startCardinality*/,
endtableid varchar2(50) not null 
/*被关联表ID*/,
endfieldid varchar2(50) not null 
/*被关联字段ID*/,
endcardinality varchar2(10) null 
/*endCardinality*/,
asstype smallint null 
/*对应实体间关联类型*/,
creator varchar2(30) null 
/*创建人*/,
createtime char(19) null 
/*创建时间*/,
modifier varchar2(30) null 
/*修改人*/,
modifytime char(19) null 
/*修改时间*/,
resid varchar2(90) null 
/*名称资源ID*/,
isforeignkey char(1) null 
/*是否外键*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_db_relation primary key (id,versiontype),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: OR关系映射表 */
-- drop table md_ormap;
create table md_ormap (classid varchar2(50) not null 
/*类ID*/,
attributeid varchar2(50) not null 
/*属性ID*/,
tableid varchar2(50) not null 
/*表ID*/,
columnid varchar2(50) not null 
/*列ID*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint a_pk_md_ormap primary key (attributeid),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 数据库表 */
-- drop table md_db;
create table md_db (id varchar2(50) not null 
/*数据库ID*/,
versiontype smallint not null 
/*版本标识*/,
name varchar2(50) not null 
/*名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
isactive char(1) not null 
/*是否启用*/,
modifytime char(19) null 
/*修改时间*/,
createtime char(19) null 
/*创建时间*/,
modifier varchar2(30) null 
/*修改人*/,
creator varchar2(30) null 
/*创建人*/,
resid varchar2(90) null 
/*名称资源ID*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_db primary key (id,versiontype),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 模块表 */
-- drop table md_module;
create table md_module (id varchar2(50) not null 
/*模块ID*/,
versiontype smallint not null 
/*版本标识*/,
name varchar2(50) not null 
/*名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
parentmoduleid varchar2(50) null 
/*父模块ID*/,
isactive char(1) null 
/*是否启用*/,
creator varchar2(30) null 
/*创建人*/,
modifier varchar2(30) null 
/*修改人*/,
createtime char(19) null 
/*创建时间*/,
modifytime char(19) null 
/*修改时间*/,
resid varchar2(90) null 
/*名称资源ID*/,
resmodule varchar2(90) null 
/*多语资源模块*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_module primary key (id,versiontype),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 参数表 */
-- drop table md_parameter;
create table md_parameter (id varchar2(50) not null 
/*参数ID*/,
versiontype smallint not null 
/*版本标识*/,
operationid varchar2(50) null 
/*所属方法ID*/,
name varchar2(50) not null 
/*名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
datatype varchar2(50) not null 
/*类型id*/,
datatypestyle smallint not null 
/*数据类型样式*/,
parasequence smallint not null 
/*参数序号*/,
modifier varchar2(30) null 
/*修改人*/,
creator varchar2(30) null 
/*创建人*/,
createtime char(19) null 
/*创建时间*/,
modifytime char(19) null 
/*修改时间*/,
resid varchar2(90) null 
/*名称资源ID*/,
paramdefclassname varchar2(50) null 
/*参数类型自定义类名*/,
isaggvo char(1) null 
/*是否聚合VO*/,
paramtypeforlog varchar2(10)   null 
/*paramTypeForLog*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_parameter primary key (id,versiontype),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 枚举值表 */
-- drop table md_enumvalue;
create table md_enumvalue (id varchar2(50) not null 
/*枚举类型ID*/,
versiontype smallint not null 
/*版本标识*/,
value varchar2(512) not null 
/*枚举值*/,
name varchar2(512) null 
/*枚举值对应名*/,
description varchar2(512) null 
/*枚举值对应描述*/,
resid varchar2(90) null 
/*名称资源ID*/,
enumsequence smallint null 
/*序号*/,
hidden char(1) null 
/*是否隐藏*/,
industry varchar2(50) not null 
/*行业*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_enumvalue primary key (id,versiontype,value,industry),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 业务接口映射表 */
-- drop table md_bizitfmap;
create table md_bizitfmap (bizinterfaceid varchar2(50) not null 
/*业务接口ID*/,
intattrid varchar2(50) not null 
/*接口属性ID*/,
classid varchar2(50) not null 
/*对应ClassID*/,
classattrid varchar2(50) null 
/*实体属性ID*/,
bizitfimpclassname varchar2(256) null 
/*业务接口实现类*/,
classattrpath varchar2(512) null 
/*实体属性路径*/,
versiontype smallint null 
/*开发维度*/,
industry varchar2(50) null 
/*行业*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_bizitfmap primary key (intattrid,classid),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 组件对业务模块映射表 */
-- drop table md_comp_busimodule;
create table md_comp_busimodule (componentid varchar2(50) not null 
/*componentid*/,
busimodulecode varchar2(50) null 
/*busimoduleCode*/,
tenantid varchar2(50) null 
/*租户ID*/,
 constraint pk_md_c_b_mod primary key (componentid),
 ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 系统模块数据库结构信息表 */
-- drop table md_sysmoduleconfig;
create table md_sysmoduleconfig (tablename varchar2(50) null 
/*表名*/,
codefield varchar2(50) null 
/*code列名*/,
namefield varchar2(50) null 
/*name列名*/,
resmodulefield varchar2(50) null 
/*多语模块列名*/,
resmodulevalue varchar2(50) null 
/*多语模块固定值*/,
residfield varchar2(50) null
/*多语资源id列名*/,
tenantid varchar2(50) null  
/*租户ID*/,
ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* 以下表后续考虑删除 */

/* tablename: 访问器实例参数 */
-- drop table md_accessorpara;
create table md_accessorpara (id varchar2(256) not null 
/*属性或实体ID*/,
assosequence smallint not null 
/*访问器参数序号*/,
paravalue varchar2(512) null 
/*参数值*/,
versiontype smallint null 
/*版本*/,
industry varchar2(50) null 
/*行业*/,
tenantid varchar2(50) null
/*租户ID*/, 
constraint pk_md_accessorpara primary key (id,assosequence),
ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

/* tablename: 操作接口表 */
-- drop table md_opinterface;
create table md_opinterface (id varchar2(50) not null 
/*业务操作ID*/,
versiontype smallint not null 
/*版本标识*/,
name varchar2(50) not null 
/*名称*/,
displayname varchar2(90) null 
/*显示名称*/,
description varchar2(512) null 
/*描述信息*/,
help varchar2(1024) null 
/*帮助信息*/,
parentid varchar2(50) null 
/*父操作ID*/,
componentid varchar2(50) not null 
/*所属组件ID*/,
fullclassname varchar2(256) null 
/*业务操作类名*/,
isservice char(1) null 
/*是否服务*/,
visibility smallint null 
/*可见性*/,
remote char(1) null 
/*是否远程*/,
singleton char(1) null 
/*是否单例*/,
creator varchar2(30) null 
/*创建人*/,
modifier varchar2(30) null 
/*修改人*/,
createtime char(19) null 
/*创建时间*/,
modifytime char(19) null 
/*修改时间*/,
implstrategy varchar2(256) null 
/*实现策略*/,
resid varchar2(90) null 
/*名称资源ID*/,
authorizable char(1) null 
/*是否授权*/,
ownertype varchar2(50) null 
/*所属类型*/,
defaultimplclassname varchar2(256) null 
/*默认实现类*/,
isbusioperation char(1) null 
/*是否业务操作*/,
isbusiactivity char(1) null 
/*是否业务活动*/,
industry varchar2(50)   not null 
/*行业*/,
tenantid varchar2(50) null 
/*租户ID*/,
constraint pk_md_interface primary key (id,versiontype,industry),
ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
dr number(10) default 0
);

-- drop table pub_multilang;
create table pub_multilang
(
  charsetname varchar2(50),
  dislpayname char(75),
  dr number(10) default 0,
  langcode varchar2(50),
  langdirpath varchar2(50),
  langseq number(10),
  localcountry varchar2(50),
  locallang varchar2(50),
  pk_multilang varchar2(50),
  transclassname varchar2(50),
  ts char(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT pk_pub_multilang PRIMARY KEY (pk_multilang)
);