/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50521
Source Host           : localhost:3306
Source Database       : ieop

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2016-05-09 09:41:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `pub_bcr_elem`
-- ----------------------------
-- DROP TABLE IF EXISTS `pub_bcr_elem`;
CREATE TABLE `pub_bcr_elem` (
  `pk_billcodeelem` varchar(40) NOT NULL,
  `pk_billcodebase` varchar(40) NOT NULL,
  `elemtype` smallint(6) DEFAULT NULL,
  `elemvalue` varchar(100) DEFAULT NULL,
  `elemlenth` smallint(6) DEFAULT NULL,
  `isrefer` smallint(6) DEFAULT NULL,
  `eorder` smallint(6) DEFAULT NULL,
  `fillstyle` smallint(6) DEFAULT NULL,
  `datedisplayformat` varchar(16) DEFAULT NULL,
  `fillsign` varchar(4) DEFAULT NULL,
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_billcodeelem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `pub_bcr_precode`
-- ----------------------------
-- DROP TABLE IF EXISTS `pub_bcr_precode`;
CREATE TABLE `pub_bcr_precode` (
  `pk_precode` varchar(40) NOT NULL,
  `pk_rulebase` varchar(40) NOT NULL,
  `markstr` varchar(100) DEFAULT NULL,
  `billcode` varchar(100) DEFAULT NULL,
  `lastsn` varchar(10) DEFAULT NULL,
  `markstrdesc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pk_precode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_bcr_precode
-- ----------------------------

-- ----------------------------
-- Table structure for `pub_bcr_return`
-- ----------------------------
-- DROP TABLE IF EXISTS `pub_bcr_return`;
CREATE TABLE `pub_bcr_return` (
  `pk_billcodertn` varchar(40) NOT NULL,
  `pk_billcodebase` varchar(40) NOT NULL,
  `markstr` varchar(100) DEFAULT NULL,
  `rtnsn` varchar(10) DEFAULT NULL,
  `markstrdesc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pk_billcodertn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_bcr_return
-- ----------------------------

-- ----------------------------
-- Table structure for `pub_bcr_rulebase`
-- ----------------------------
-- DROP TABLE IF EXISTS `pub_bcr_rulebase`;
CREATE TABLE `pub_bcr_rulebase` (
  `pk_billcodebase` varchar(40) NOT NULL,
  `pkbillobj` varchar(40) NOT NULL,
  `rulecode` varchar(100) DEFAULT NULL,
  `rulename` varchar(300) DEFAULT NULL,
  `codemode` varchar(10) DEFAULT NULL,
  `iseditable` char(1) DEFAULT NULL,
  `isautofill` char(1) DEFAULT NULL,
  `format` varchar(20) DEFAULT NULL,
  `isdefault` char(1) DEFAULT NULL,
  `isused` char(1) DEFAULT NULL,
  `islenvar` char(1) DEFAULT NULL,
  `isgetpk` char(1) DEFAULT NULL,
  `renterid` varchar(40) DEFAULT NULL,
  `sysid` varchar(40) DEFAULT NULL,
  `createdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_billcodebase`),
  UNIQUE KEY `rulecode` (`rulecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `pub_bcr_sn`
-- ----------------------------
-- DROP TABLE IF EXISTS `pub_bcr_sn`;
CREATE TABLE `pub_bcr_sn` (
  `pk_billcodesn` varchar(40) NOT NULL,
  `pk_billcodebase` varchar(40) NOT NULL,
  `markstr` varchar(100) DEFAULT NULL,
  `lastsn` varchar(10) DEFAULT NULL,
  `markstrdesc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pk_billcodesn`),
  KEY `idx_pub_bcr_sn` (`pk_billcodebase`,`markstr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `pub_bcr_obj`
-- ----------------------------
-- DROP TABLE IF EXISTS `pub_bcr_obj`;
CREATE TABLE `pub_bcr_obj` (
  `pk_billobj` varchar(40) NOT NULL,
  `code` varchar(40) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `pk_parent` varchar(40) DEFAULT NULL,
  `iscatalog` char(1) DEFAULT NULL,
  `assigntype` varchar(40) DEFAULT NULL,
  `createdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_billobj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `pub_bcr_assign`
-- ----------------------------
-- DROP TABLE IF EXISTS `pub_bcr_assign`;
CREATE TABLE `pub_bcr_assign` (
  `pk` varchar(40) NOT NULL,
  `pk_billcodebase` varchar(40) NOT NULL,
  `pkassign` varchar(40) NOT NULL,
  `pk_billobj` varchar(40) NOT NULL,
  PRIMARY KEY (`pk`),
  unique ( pk_billcodebase, pkassign)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `pub_bcr_assignreg`
-- ----------------------------
-- DROP TABLE IF EXISTS `pub_bcr_assignreg`;
CREATE TABLE `pub_bcr_assignreg` (
  `pk_assigntype` varchar(40) NOT NULL,
  `implclassname` varchar(300) NOT NULL,
  PRIMARY KEY (`pk_assigntype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
