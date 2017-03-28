CREATE TABLE `pub_oid` ( `schemacode` varchar(8) NOT NULL, `oidbase` varchar(20) NOT NULL, `id` varchar(36) NOT NULL, `ts` timestamp NULL DEFAULT NULL, PRIMARY KEY (`id`), UNIQUE KEY `UQ_schemacode` (`schemacode`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pub_oid (schemacode, oidbase, id, ts) VALUES ('uapcloud', '100000000001', 'b141e37a-8d86-40bb-b484-7ed374eabf01', '2016-04-22 15:17:42');

-- 自动取数据库名和uuid的sql
INSERT INTO pub_oid (schemacode, oidbase, id, ts) VALUES (DATABASE(), '100000000001', uuid(), current_timestamp());