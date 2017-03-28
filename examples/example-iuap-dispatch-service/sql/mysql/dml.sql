insert into dispatch_taskwayclass(id,code,name,parentid) values('0000001','test','测试分类',null);
insert into dispatch_taskway(id,code,name,url,taskwayclassid) values('0000001','test','调用规则测试','http://localhost:8085/iuap-dispatch-service-example/test/test','0000001');
