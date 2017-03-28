-- DROP TABLE IF EXISTS `org_orgs`;
CREATE TABLE `org_orgs` (
  `id` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fatherid` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tenantid` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sysid` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype1` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype2` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype3` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype4` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype5` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype6` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype7` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype8` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype9` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `orgtype10` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- DROP TABLE IF EXISTS `org_type`;
CREATE TABLE `org_type` (
  `id` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tenantid` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sysid` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fieldname` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `serviceclass` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `entityclassname` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
