/*  测试使用，用户可根据需要重写或替换默认的用户表*/
DROP TABLE IF EXISTS `mgr_user`;
CREATE TABLE `mgr_user` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `login_name` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `roles` varchar(255) NOT NULL,
  `register_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `mgr_user` VALUES ('1', 'admin', 'Admin', '691b14d79bf0fa2215f155235df5e670b64394cc', '7efbd59d9741d34f', 'admin', '2012-06-04 01:00:00');
INSERT INTO `mgr_user` VALUES ('2', 'user', 'Calvin', '2488aa0c31c624687bd9928e0a5d29e7d1ed520b', '6d65d24122c30500', 'user', '2012-06-04 02:00:00');
INSERT INTO `mgr_user` VALUES ('5', 'liujmc', '刘建民', 'e286dd520c1ac7daf2c2d6240f9f328c4d6d280c', '076b3c44bf24fee0', 'user', '2015-02-03 14:49:41');
INSERT INTO `mgr_user` VALUES ('24', 'zhukai', 'zhukai', '810592d961d665ee8f61edee655cd1488d0f2dc0', '78a29e3e2e11508e', 'admin', '2015-02-05 18:45:36');
INSERT INTO `mgr_user` VALUES ('31', 'liujmc@yonyou.com', '买家小刘', 'a0ae63c89d9d3d64f486aa52819fe2bd15381c5c', 'f1d8a7198f0dba72', 'buyers', '2015-02-12 14:59:36');
INSERT INTO `mgr_user` VALUES ('32', 'taomk', 'taomk', '392ecf9a2177e8c76e4f0c46dba2b287974383b5', 'ec759e16e26ec058', 'user', '2016-03-24 14:26:30');
INSERT INTO `mgr_user` VALUES ('33', 'yy', '用友', 'd9f4e23532da70e64c1fcd2edac25f77f95d6575', 'e7676c444a821c3e', 'admin,user', '2016-03-24 14:58:35');
INSERT INTO `mgr_user` VALUES ('34', 'abc', 'abc', 'acb3b99dad5cd54643f03add82bf449f243fa8ab', 'a87deaf08afa2a70', 'user,admin', '2016-03-28 09:51:21');
