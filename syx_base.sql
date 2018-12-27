/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50634
Source Host           : localhost:3307
Source Database       : syx_base

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2018-12-27 10:29:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qzstoreset
-- ----------------------------
DROP TABLE IF EXISTS `qzstoreset`;
CREATE TABLE `qzstoreset` (
  `id` varchar(64) NOT NULL,
  `servername` varchar(50) DEFAULT NULL,
  `servertype` char(1) DEFAULT NULL,
  `viewpath` varchar(300) DEFAULT NULL,
  `useflag` char(1) DEFAULT NULL,
  `ftpaddress` varchar(50) DEFAULT NULL,
  `ftpport` varchar(10) DEFAULT NULL,
  `ftpusername` varchar(50) DEFAULT NULL,
  `ftppassword` varchar(100) DEFAULT NULL,
  `localaddress` varchar(500) DEFAULT NULL,
  `usetype` varchar(50) DEFAULT '0' COMMENT '存储用途，0：取证数据存储\r\n1：第三方取证信息存储（如客户端，先将打包的一起上传到指定的目录再进行取证后合并，或上传到指定目录后立即打包证据包）',
  `remark` varchar(200) DEFAULT NULL,
  `deleteflag` char(1) DEFAULT NULL,
  `createid` varchar(50) DEFAULT NULL,
  `createname` varchar(50) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT NULL,
  `var02` varchar(50) DEFAULT NULL,
  `var03` varchar(50) DEFAULT NULL,
  `var04` varchar(50) DEFAULT NULL,
  `var05` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qzstoreset
-- ----------------------------
INSERT INTO `qzstoreset` VALUES ('3d0c2194e17e4449aa0071175a6596e2', 'FTP存储节点01', '1', 'http://10.2.12.223/', '1', '10.2.12.223', '21', 'test1', '111111', '', '1', '', '0', '1', 'admin', '2018-01-09 09:57:24', null, '2018-08-07 10:28:24', null, null, null, null, null);

-- ----------------------------
-- Table structure for sysapplication
-- ----------------------------
DROP TABLE IF EXISTS `sysapplication`;
CREATE TABLE `sysapplication` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `appcode` varchar(64) DEFAULT '',
  `appname` varchar(50) DEFAULT '',
  `apphost` varchar(100) DEFAULT '',
  `appport` varchar(50) DEFAULT '',
  `appcontext` varchar(100) DEFAULT '',
  `appctx` varchar(100) DEFAULT '',
  `appurl` varchar(200) DEFAULT '',
  `appstatus` varchar(50) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysapplication
-- ----------------------------
INSERT INTO `sysapplication` VALUES ('1', '0001', '版权管理系统', null, null, null, null, null, null, null, '0', '1', 'admin', '2016-11-11 00:00:00', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for sysdatasource
-- ----------------------------
DROP TABLE IF EXISTS `sysdatasource`;
CREATE TABLE `sysdatasource` (
  `id` varchar(64) NOT NULL,
  `dscode` varchar(50) NOT NULL,
  `dsname` varchar(50) NOT NULL,
  `dsdriver` varchar(100) NOT NULL,
  `dsurl` varchar(200) NOT NULL,
  `dsusername` varchar(50) NOT NULL,
  `dspassword` varchar(50) NOT NULL,
  `dsmaxactive` int(11) NOT NULL,
  `dsmaxidle` int(11) DEFAULT NULL,
  `dscheckouttime` int(11) DEFAULT NULL,
  `dswaittime` int(11) DEFAULT NULL,
  `dspingenabled` int(11) NOT NULL,
  `dspingquery` varchar(100) DEFAULT NULL,
  `dspingtime` int(11) DEFAULT NULL,
  `dsasorg` int(11) DEFAULT NULL,
  `dsremark` varchar(100) DEFAULT NULL,
  `dataversion` int(11) DEFAULT NULL,
  `datavalid` int(11) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `deleteflag` char(1) DEFAULT NULL,
  `createid` varchar(50) DEFAULT NULL,
  `createname` varchar(50) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT NULL,
  `var02` varchar(50) DEFAULT NULL,
  `var03` varchar(50) DEFAULT NULL,
  `var04` varchar(50) DEFAULT NULL,
  `var05` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX01` (`datavalid`,`dscode`,`id`) USING BTREE,
  KEY `INDEX02` (`dscode`,`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysdatasource
-- ----------------------------
INSERT INTO `sysdatasource` VALUES ('0dbb8eee88a9442f8db4756d13fae04b', 'hl_ad_closing', '广告结案系统', 'com.mysql.jdbc.Driver', 'jdbc:mysql://10.2.12.240:3307/hlideal_ad_closing?useUnicode=true&characterEncoding=UTF-8', 'root', 'root', '500', '5', '20000', '20000', '1', 'SELECT 1', '0', '0', '', '0', '1', 'system.psn', '0', '1', 'admin', '2016-09-12 10:38:13', 'admin', '2018-11-30 16:17:26', null, null, null, null, null);

-- ----------------------------
-- Table structure for sysdept
-- ----------------------------
DROP TABLE IF EXISTS `sysdept`;
CREATE TABLE `sysdept` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `parentid` varchar(64) DEFAULT '',
  `depttype` varchar(50) DEFAULT '',
  `deptno` varchar(50) DEFAULT '',
  `deptname` varchar(50) DEFAULT '',
  `managerid` varchar(64) DEFAULT '',
  `tempmanagerids` varchar(500) DEFAULT '',
  `supermanagerid` varchar(64) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysdept
-- ----------------------------
INSERT INTO `sysdept` VALUES ('1635449d3f6b484aa8730bad90cc324f', '0', '', '0001', '测试0001', '', '', '', '', '0', '1', '系统管理员', '2018-12-03 11:03:25', '', '2018-12-03 11:03:25', '', '', '', '', '');
INSERT INTO `sysdept` VALUES ('5c629a9f68374a048667d2c7e016874e', '1635449d3f6b484aa8730bad90cc324f', '', '00011', '测试00011', '', '', '', '', '0', '1', '系统管理员', '2018-12-03 11:04:12', '', '2018-12-03 11:04:12', '', '', '', '', '');

-- ----------------------------
-- Table structure for sysdic
-- ----------------------------
DROP TABLE IF EXISTS `sysdic`;
CREATE TABLE `sysdic` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `cateid` varchar(64) DEFAULT '',
  `catekey` varchar(64) DEFAULT '',
  `parentid` varchar(64) DEFAULT '',
  `checktree` varchar(1) DEFAULT '',
  `isDefault` varchar(50) DEFAULT '',
  `dickey` varchar(50) DEFAULT '',
  `dicvalue` varchar(50) DEFAULT '',
  `minvalue` varchar(50) DEFAULT '',
  `maxvalue` varchar(50) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(300) DEFAULT NULL,
  `var02` varchar(300) DEFAULT NULL,
  `var03` varchar(300) DEFAULT '',
  `var04` varchar(300) DEFAULT NULL,
  `var05` varchar(300) DEFAULT NULL,
  `var06` varchar(50) DEFAULT '',
  `var07` varchar(50) DEFAULT '',
  `var08` varchar(50) DEFAULT '',
  `var09` varchar(50) DEFAULT '',
  `var10` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysdic
-- ----------------------------
INSERT INTO `sysdic` VALUES ('cd2deba8c85c4e65bd3be38133cdb665', '32dce52693834d5aa87ccf6525ef1670', 'articlesystemno', '', '', '', '1001', '取证系统', '', '', '', '0', '1', 'admin', '2017-12-20 11:03:09', '', '2017-12-20 11:03:09', null, null, '', null, null, '', '', '', '', '');
INSERT INTO `sysdic` VALUES ('6ca262c10a9f4cd09ce83a1f6163893d', '32dce52693834d5aa87ccf6525ef1670', 'articlesystemno', '', '', '', '1002', '版权系统', '', '', '', '0', '1', 'admin', '2017-12-20 11:03:18', '', '2017-12-20 11:03:18', null, null, '', null, null, '', '', '', '', '');
INSERT INTO `sysdic` VALUES ('80954169a40d401c8ff88048447df0ae', 'cf1159ab96f2477ab2815a6a4d6bcca5', 'tradecode', '', '', '', 'tradename1', '行业类别名称1', '', '', '', '0', '1', 'admin', '2017-08-08 17:12:45', '', '2017-08-08 17:12:52', null, null, '', null, null, '', '', '', '', '');
INSERT INTO `sysdic` VALUES ('a225d4299cea4f4c8620ac2ffba2d7fa', 'cf1159ab96f2477ab2815a6a4d6bcca5', 'tradecode', '', '', '', 'tradename2', '行业类别名称2', '', '', '', '0', '1', 'admin', '2017-08-08 17:13:04', '', '2017-08-08 17:13:04', null, null, '', null, null, '', '', '', '', '');
INSERT INTO `sysdic` VALUES ('deeefe08661a444ba756888f4f70d121', '4c7649d3a7894f6d8eff714884d9f9f9', 'qzchannel', '', '', '', '0', '本地取证', '', '', '', '0', '1', 'admin', '2018-04-19 10:05:09', '', '2018-04-19 10:05:09', null, null, '', null, null, '', '', '', '', '');
INSERT INTO `sysdic` VALUES ('0a1661d84be04ecd901d18323cb7c555', '4c7649d3a7894f6d8eff714884d9f9f9', 'qzchannel', '', '', '', '1', '百度取证', '', '', '', '0', '1', 'admin', '2018-04-19 10:05:17', '', '2018-04-19 10:05:17', null, null, '', null, null, '', '', '', '', '');

-- ----------------------------
-- Table structure for sysdiccate
-- ----------------------------
DROP TABLE IF EXISTS `sysdiccate`;
CREATE TABLE `sysdiccate` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `catekey` varchar(50) DEFAULT '',
  `catename` varchar(50) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysdiccate
-- ----------------------------
INSERT INTO `sysdiccate` VALUES ('32dce52693834d5aa87ccf6525ef1670', 'articlesystemno', '文章发布平台', '文章发布平台', '0', '1', 'admin', '2017-12-20 11:01:47', '', '2017-12-20 11:01:47', '', '', '', '', '');
INSERT INTO `sysdiccate` VALUES ('cf1159ab96f2477ab2815a6a4d6bcca5', 'tradecode', '行业类别', '行业类别', '0', '1', 'admin', '2017-08-08 17:12:28', '', '2017-08-08 17:12:28', '', '', '', '', '');
INSERT INTO `sysdiccate` VALUES ('a027fb82a328448eb4d8424579777a0e', 'weiquanstatus', '维权状态', '维权状态', '0', '1', 'admin', '2018-02-04 15:03:07', '', '2018-02-04 15:03:07', '', '', '', '', '');
INSERT INTO `sysdiccate` VALUES ('4c7649d3a7894f6d8eff714884d9f9f9', 'qzchannel', '取证方式', '取证方式', '0', '1', 'admin', '2018-04-19 10:04:43', '', '2018-04-19 10:04:43', '', '', '', '', '');

-- ----------------------------
-- Table structure for sysfunction
-- ----------------------------
DROP TABLE IF EXISTS `sysfunction`;
CREATE TABLE `sysfunction` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `appid` varchar(64) DEFAULT '',
  `parentid` varchar(64) DEFAULT '',
  `funtionkey` varchar(200) DEFAULT '',
  `functionname` varchar(50) DEFAULT '',
  `orderno` int(11) DEFAULT '0',
  `linkurl` varchar(500) DEFAULT '',
  `iconlink` varchar(200) DEFAULT '',
  `globalcheck` varchar(1) DEFAULT '',
  `checkshow` varchar(1) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysfunction
-- ----------------------------
INSERT INTO `sysfunction` VALUES ('5000', '1', '0', null, '系统管理', '5000', '', '', null, '1', '系统管理', '0', null, null, null, null, '2015-09-01 00:00:00', '', null, null, null, null);
INSERT INTO `sysfunction` VALUES ('5001', '1', '5000', null, '用户管理', '2', '/admin/backstage/sys/usermanage.htm', '', null, '1', '用户管理', '0', null, null, null, null, null, '', null, null, null, null);
INSERT INTO `sysfunction` VALUES ('5002', '1', '5000', null, '角色管理', '3', '/admin/backstage/sys/rolemanage.htm', '', null, '1', '角色管理', '0', null, null, null, null, null, '', null, null, null, null);
INSERT INTO `sysfunction` VALUES ('5006', '1', '5000', '', '参数管理', '6', '/admin/backstage/sys/parammanage.htm', '', '', '1', '参数管理', '0', '', '', null, '', null, '', '', '', '', '');
INSERT INTO `sysfunction` VALUES ('5003', '1', '5000', '', '字典管理', '4', '/admin/backstage/sys/diccatemanage.htm', '', '', '1', '字典管理', '0', '', '', null, '', null, '', '', '', '', '');
INSERT INTO `sysfunction` VALUES ('5004', '1', '5000', '', '部门管理', '5', '/admin/backstage/sys/deptmanage.htm', '', '', '1', '部门管理', '0', '', '', null, '', null, '', '', '', '', '');
INSERT INTO `sysfunction` VALUES ('5005', '1', '5000', '', '数据源管理', '1', '/admin/backstage/sys/datasourcemanage.htm', '', '', '1', '数据源管理', '0', '', '', null, '', null, '', '', '', '', '');
INSERT INTO `sysfunction` VALUES ('5007', '1', '5000', '', 'ftp设置', '11', '/admin/backstage/sys/qzstoreset.htm', '', '', '1', 'ftp设置', '0', '', '', null, '', null, '', '', '', '', '');

-- ----------------------------
-- Table structure for sysparam
-- ----------------------------
DROP TABLE IF EXISTS `sysparam`;
CREATE TABLE `sysparam` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `paramkey` varchar(50) DEFAULT '',
  `paramvalue` varchar(500) DEFAULT '',
  `description` varchar(200) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysparam
-- ----------------------------
INSERT INTO `sysparam` VALUES ('651e220e418148e7b54b71fd82406d65', 'QZ_API_SERVER', 'http://10.2.12.234:9090/api', '取证API路径', '', '0', '1', 'admin', '2017-07-26 17:30:08', '', '2018-06-08 16:13:12', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('f2fe18a387b242fa99567e7be09de1d5', 'FEEDBACK-IMAGEPATH', 'http://10.2.12.222:5566/api/bq/feedbackimage.htm', '', '', '0', '1', 'admin', '2017-10-26 17:27:00', '', '2017-10-31 09:20:33', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('8777701930cd408ebba59a4d608b365c', 'BQ-CUSTOMID', '1aae77943e38432f9e667ec2afd9b78c', '版权平台的customid', '', '0', '1', 'admin', '2017-08-15 16:39:57', '', '2017-08-22 10:29:48', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('61f402e673d34aef999dee500ad6478e', 'QZ_API_SERVER_VIEW', 'http://10.2.12.234:9090/api', '', '', '0', '1', 'admin', '2017-09-27 16:40:48', '', '2018-06-08 16:13:18', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('fac8649023ee4b8fbd172f3492bfe7c9', 'CATCH-REPRINT-DAY', '3', '设置3天内持续抓取转载', '', '0', '1', 'admin', '2017-08-16 15:55:23', '', '2017-08-16 15:55:23', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('1347d562b7bd4d369dcec3fba45a3377', 'QZ_CHANNEL', '0', '取证渠道，0：本地，1：百度', '', '0', '1', 'admin', '2018-01-08 14:44:23', '', '2018-06-08 10:07:59', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('cc27d371e8c84a02afdfcfb04f2eb6c0', 'BQ-HLDT-API', 'http://10.2.12.222:5577/hldt/api/bq/createbqJob.htm', '版权调用中间件api地址', '', '0', '1', 'admin', '2018-01-19 14:15:50', '', '2018-07-11 17:31:35', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('dca95a06656641b4a037dd32d6286c92', 'HLDT-BQ-API', 'http://10.2.12.222:5599/api/bq/hldtBackToBq.htm', '创建中间件job返回版权', '', '0', '1', 'admin', '2018-02-22 10:15:04', '', '2018-07-06 10:43:36', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('92b418ef89a54006a9ae8cd4ed3390ac', 'HLDT-API-BASE', 'http://10.2.12.127:5577', '中间件ip', '', '0', '1', 'admin', '2018-07-06 09:28:49', '', '2018-07-06 10:07:58', '', '', '', '', '');
INSERT INTO `sysparam` VALUES ('55a61fb1d71240f885780d373cc9b08f', 'BQ-PAY-PRICE', '10', '版权公众平台支付金额', '', '0', '1', 'admin', '2018-10-15 17:13:14', '', '2018-10-15 17:13:14', '', '', '', '', '');

-- ----------------------------
-- Table structure for syspost
-- ----------------------------
DROP TABLE IF EXISTS `syspost`;
CREATE TABLE `syspost` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `posttype` varchar(50) DEFAULT '',
  `postno` varchar(50) DEFAULT '',
  `postname` varchar(50) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of syspost
-- ----------------------------

-- ----------------------------
-- Table structure for syspoststaff
-- ----------------------------
DROP TABLE IF EXISTS `syspoststaff`;
CREATE TABLE `syspoststaff` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `postid` varchar(64) DEFAULT '',
  `staffid` varchar(64) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of syspoststaff
-- ----------------------------

-- ----------------------------
-- Table structure for sysrole
-- ----------------------------
DROP TABLE IF EXISTS `sysrole`;
CREATE TABLE `sysrole` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `roleno` varchar(50) DEFAULT '',
  `name` varchar(50) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysrole
-- ----------------------------
INSERT INTO `sysrole` VALUES ('1', '001', '管理员', '管理员', '0', '1', 'admin', '2015-08-28 00:00:00', 'admin', '2016-11-16 00:00:00', null, null, null, null, null);
INSERT INTO `sysrole` VALUES ('12d572d6aabd418b8c409ce5bfcd94bc', '', '测试01', '', '1', '1', 'admin', '2017-11-08 10:45:39', '', '2017-11-08 10:46:09', '', '', '', '', '');
INSERT INTO `sysrole` VALUES ('194fd0b2ba0f44e38e3f007a0687dfed', '', '测试01', '测试账号，使用版权管理和用户管理功能', '0', '1', 'admin', '2017-11-08 10:46:03', '', '2017-11-08 10:46:03', '', '', '', '', '');

-- ----------------------------
-- Table structure for sysrolefunction
-- ----------------------------
DROP TABLE IF EXISTS `sysrolefunction`;
CREATE TABLE `sysrolefunction` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `roleid` varchar(64) DEFAULT '',
  `functionid` varchar(64) DEFAULT '',
  `appid` varchar(64) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysrolefunction
-- ----------------------------
INSERT INTO `sysrolefunction` VALUES ('1d320c03482c40d2a794b0e8490e5d93', '194fd0b2ba0f44e38e3f007a0687dfed', '5006', '', '', '0', '1', '系统管理员', '2018-12-03 10:25:53', '', '2018-12-03 10:25:53', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('c4ce3630d2ee4467a37178982ceedded', '194fd0b2ba0f44e38e3f007a0687dfed', '5003', '', '', '0', '1', '系统管理员', '2018-12-03 10:25:53', '', '2018-12-03 10:25:53', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('cf8ca47e4f68457d8fb162d6d6884f66', '194fd0b2ba0f44e38e3f007a0687dfed', '5000', '', '', '0', '1', '系统管理员', '2018-12-03 10:25:53', '', '2018-12-03 10:25:53', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('ad8806b3606743d48418b7e82c464441', '1', '5007', '', '', '0', '1', 'admin', '2017-11-16 17:04:14', '', '2017-11-16 17:04:14', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('9c9cc816e0a54f81813fddddbd41ea05', '1', '5005', '', '', '0', '1', 'admin', '2017-11-16 17:04:14', '', '2017-11-16 17:04:14', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('83229531d5fd43e0943b3ff5c44a20a2', '1', '5004', '', '', '0', '1', 'admin', '2017-11-16 17:04:14', '', '2017-11-16 17:04:14', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('43204bb5c66f47b3b57f58f5116873ed', '1', '5003', '', '', '0', '1', 'admin', '2017-11-16 17:04:14', '', '2017-11-16 17:04:14', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('413ca186f35247abb37692260b8d1155', '1', '5006', '', '', '0', '1', 'admin', '2017-11-16 17:04:14', '', '2017-11-16 17:04:14', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('dbbcb453bc4f41418761e556346a4376', '1', '5002', '', '', '0', '1', 'admin', '2017-11-16 17:04:14', '', '2017-11-16 17:04:14', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('d1ffffdb623840d2bbbeea18b0326dc6', '1', '5001', '', '', '0', '1', 'admin', '2017-11-16 17:04:14', '', '2017-11-16 17:04:14', '', '', '', '', '');
INSERT INTO `sysrolefunction` VALUES ('8bbf3ab976564159aa41df99235d7b50', '1', '5000', '', '', '0', '1', 'admin', '2017-11-16 17:04:14', '', '2017-11-16 17:04:14', '', '', '', '', '');

-- ----------------------------
-- Table structure for sysstaff
-- ----------------------------
DROP TABLE IF EXISTS `sysstaff`;
CREATE TABLE `sysstaff` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `loginname` varchar(50) NOT NULL DEFAULT '',
  `loginpwd` varchar(100) DEFAULT '',
  `staffno` varchar(50) DEFAULT '',
  `name` varchar(50) DEFAULT '',
  `deptid` varchar(64) DEFAULT '',
  `deptname` varchar(50) DEFAULT '',
  `sex` varchar(50) DEFAULT '',
  `address` varchar(50) DEFAULT '',
  `email` varchar(50) DEFAULT '',
  `hostip` varchar(50) DEFAULT '',
  `mobilephone` varchar(50) DEFAULT '',
  `connectphone` varchar(50) DEFAULT '',
  `adminstatus` varchar(1) DEFAULT '',
  `loginip` varchar(50) DEFAULT '',
  `logindate` datetime DEFAULT NULL,
  `loginflag` varchar(1) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysstaff
-- ----------------------------
INSERT INTO `sysstaff` VALUES ('1', 'admin', '96e79218965eb72c92a549dd5a330112', '00111', '系统管理员', '1', '管理部', '0', null, 'younger@163.com', null, '13635419447', '023-73321451', '1', '127.0.0.1', '2016-05-17 00:00:00', '1', null, '0', '1', 'admin', '2015-08-28 00:00:00', 'admin', '2017-07-26 10:22:03', null, null, null, null, null);
INSERT INTO `sysstaff` VALUES ('1df7f384291f4c028162c562e50bf77c', 'xuyi001', '96e79218965eb72c92a549dd5a330112', '001', '徐宜', '', '', '', '', '', '', '13996222004', '', '0', '', null, '1', '', '0', '1', 'admin', '2017-11-08 10:45:15', '', '2017-11-08 10:45:15', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('005918d55765483981ec9aa239164866', 'xiaoqiang001', '96e79218965eb72c92a549dd5a330112', '002', '肖强 01', '', '', '', '', '', '', '17726602653', '', '0', '', null, '1', '', '0', '1', 'admin', '2017-11-16 16:59:46', '', '2017-11-16 16:59:46', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('16d6915f51284a8792087f912e0d2e03', 'test01', '96e79218965eb72c92a549dd5a330112', '003', '测试01', '1635449d3f6b484aa8730bad90cc324f', '测试0001', '', '', '', '', '11111111111', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-02-04 14:24:40', '', '2018-02-04 14:24:40', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '13883147541', '96e79218965eb72c92a549dd5a330112', '1', '张春霞', '', '', '', '', '', '', '13883147541', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-02-11 09:17:31', '', '2018-03-02 09:14:53', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('bb8cf317d6e04b7ba2b112064c8f80e9', '13896956987', '96e79218965eb72c92a549dd5a330112', '2', '罗亮', '', '', '', '', '', '', '13896956987', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-02-22 15:57:17', '', '2018-02-22 15:57:17', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('7d6fc3c6d6004b8bb97ba32704a63684', '18623088642', '96e79218965eb72c92a549dd5a330112', '3', '翁秀明', '', '', '', '', '', '', '18623088642', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-02-22 15:59:11', '', '2018-02-22 15:59:11', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('b30af5d58ce8411b9ea34ac84afd1ee7', '17338674686', '96e79218965eb72c92a549dd5a330112', '4', '林世豪', '', '', '', '', '', '', '17338674686', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-02-22 16:00:09', '', '2018-02-22 16:00:09', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('d7ecccf10e924cda9f356046ba58cfc8', '13883211345', '96e79218965eb72c92a549dd5a330112', '5', '江福洲', '', '', '', '', '', '', '13883211345', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-02-22 16:01:21', '', '2018-02-22 16:01:21', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('772b06bb9b814a8f90f7fd1e8f1328b7', '13594187169', '96e79218965eb72c92a549dd5a330112', '6', '陈宇生', '', '', '', '', '', '', '13594187169', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-02-22 16:06:18', '', '2018-02-22 16:06:18', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('403da187e7cd4634b8a8dba8daec022a', '18883285180', '96e79218965eb72c92a549dd5a330112', '7', '王梦龙', '', '', '', '', '', '', '18883285180', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-02-22 16:26:39', '', '2018-02-22 16:26:39', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '15683933777', '96e79218965eb72c92a549dd5a330112', '8', '彭朝庭', '', '', '', '', '', '', '15683933777', '', '0', '', null, '1', '', '0', '1', 'admin', '2018-03-02 09:02:35', '', '2018-03-02 09:02:35', '', '', '', '', '');
INSERT INTO `sysstaff` VALUES ('01f1856464994c19afcb09b968b0db1f', 'test0001', '96E79218965EB72C92A549DD5A330112', '', '测试一下', '5c629a9f68374a048667d2c7e016874e', '测试00011', '', '', '', '', '', '', '', '', '2018-11-29 17:05:34', '', '', '0', '1', 'ttt', '2018-11-29 17:05:34', '', '2018-11-29 17:05:34', '', '', '', '', '');

-- ----------------------------
-- Table structure for sysstaffdic
-- ----------------------------
DROP TABLE IF EXISTS `sysstaffdic`;
CREATE TABLE `sysstaffdic` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `staffid` varchar(64) DEFAULT '',
  `dictype` varchar(50) DEFAULT '',
  `dicvalue` varchar(2000) DEFAULT '',
  `dictext` varchar(2000) DEFAULT '',
  `remark` varchar(200) DEFAULT '',
  `deleteflag` varchar(1) DEFAULT '',
  `createid` varchar(50) DEFAULT '',
  `createname` varchar(50) DEFAULT '',
  `createdate` datetime DEFAULT NULL,
  `updatename` varchar(50) DEFAULT '',
  `updatedate` datetime DEFAULT NULL,
  `var01` varchar(50) DEFAULT '',
  `var02` varchar(50) DEFAULT '',
  `var03` varchar(50) DEFAULT '',
  `var04` varchar(50) DEFAULT '',
  `var05` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysstaffdic
-- ----------------------------

-- ----------------------------
-- Table structure for sysstafforg
-- ----------------------------
DROP TABLE IF EXISTS `sysstafforg`;
CREATE TABLE `sysstafforg` (
  `staffid` varchar(64) NOT NULL DEFAULT '' COMMENT '后台用户ID',
  `orgid` varchar(64) NOT NULL DEFAULT '' COMMENT '前台机构ID',
  PRIMARY KEY (`staffid`,`orgid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='帐号机构关联表';

-- ----------------------------
-- Records of sysstafforg
-- ----------------------------
INSERT INTO `sysstafforg` VALUES ('1', '07ae66a2affd4e6284e6708757ceb059');
INSERT INTO `sysstafforg` VALUES ('1', '2000cc0362174c37883b464d606b4866');
INSERT INTO `sysstafforg` VALUES ('1', '21cb3863aa414661917688105f5b8094');
INSERT INTO `sysstafforg` VALUES ('1', '35c64ad5d7c04cd7aee5b4b7d690fac6');
INSERT INTO `sysstafforg` VALUES ('1', '46868a1908b34b18a994c998ca62a66e');
INSERT INTO `sysstafforg` VALUES ('1', '5d246f547bfb49dab8b06f313fbda66a');
INSERT INTO `sysstafforg` VALUES ('1', '5f1fb6922d114c5bafa60919625b77a2');
INSERT INTO `sysstafforg` VALUES ('1', '74ee901b8b6540d187557b698ee84fe7');
INSERT INTO `sysstafforg` VALUES ('1', '91d633148ba044dcbdb2fbb0d720784b');
INSERT INTO `sysstafforg` VALUES ('1', '9226d101093e4495a9a1e7ccea1a589d');
INSERT INTO `sysstafforg` VALUES ('1', '96f61777346743559e294558a5136ee2');
INSERT INTO `sysstafforg` VALUES ('1', '9a109fa87e10422888920114105af254');
INSERT INTO `sysstafforg` VALUES ('1', 'a77604157e334aa8971dc4def8e050a5');
INSERT INTO `sysstafforg` VALUES ('1', 'b96b0cd845da47a9bd89a7388677d836');
INSERT INTO `sysstafforg` VALUES ('16d6915f51284a8792087f912e0d2e03', '2000cc0362174c37883b464d606b4866');
INSERT INTO `sysstafforg` VALUES ('16d6915f51284a8792087f912e0d2e03', '9a109fa87e10422888920114105af254');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '07ae66a2affd4e6284e6708757ceb059');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '0bda2b680c4b4809b64877dc3771f3e8');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '17e042d795b14ed58a16baf817e439fd');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '1bdef75f23bb46e3b7f5adfa18fb33a3');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '2000cc0362174c37883b464d606b4866');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '21cb3863aa414661917688105f5b8094');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '35c64ad5d7c04cd7aee5b4b7d690fac6');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '44de70d1ee7d41009f2bd7c4e397a785');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '46868a1908b34b18a994c998ca62a66e');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '48ef656a17914b53812952964f18037a');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '4f54e16f423b4912b18d48aa61e65449');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '5d246f547bfb49dab8b06f313fbda66a');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '5f1fb6922d114c5bafa60919625b77a2');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '6359fef7169b4e85ac62e51eb45c387e');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '6581a8b3059b42aeabb2ea5abd28f509');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '6704c56b78324f388a8ed0266ebec891');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '6b30ba3254e34123bc437b75431eea6c');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '6fa719080fbb44a594615b4e56f0b42e');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '74ee901b8b6540d187557b698ee84fe7');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '773b94b66e5944068012c38b09631d1a');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '7deef983cc14459b8d31067fdcf7c306');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '81c53575821345b1baaf32a2de2f261a');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '846ca08204ba4406bed82aea47b991dc');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '91d633148ba044dcbdb2fbb0d720784b');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '9226d101093e4495a9a1e7ccea1a589d');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '96f61777346743559e294558a5136ee2');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '9a109fa87e10422888920114105af254');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'a3d3fb7221a146e8923b59dd27344973');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'a77604157e334aa8971dc4def8e050a5');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'a97800f1c7224ec0b5d74b85883c44d8');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'aac7e6b1af0b4ac6902f3bc7cbc2f228');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'aef73f3bca394a2c8ebf91e17c19c9e1');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'b190a9b0da544102aa79b71226b1ad49');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'b394bb47499b461f8a6fa5b3fa6aea4d');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'b96b0cd845da47a9bd89a7388677d836');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'bec657668eaa46c6b92f0fa1651c362d');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'c0825b6c3a4b49a283e3285e13e1a057');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'd8555ebab2b2480d99d68e5fe5229907');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'dbcfbbd887804a7f952c92b4843a78e0');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'e94589e6b7dd43c0bd576818bf347bca');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'ea0e03bfbf7641c4a276cb185e809088');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'eb8bd08d6e36403fa07f753b84c36bd6');
INSERT INTO `sysstafforg` VALUES ('2bc744fb28ef45f094c9de7d251dba26', 'fe511a5e82604ac4ad3e11abee49dc22');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '07ae66a2affd4e6284e6708757ceb059');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '0bda2b680c4b4809b64877dc3771f3e8');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '17e042d795b14ed58a16baf817e439fd');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '1bdef75f23bb46e3b7f5adfa18fb33a3');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '2000cc0362174c37883b464d606b4866');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '21cb3863aa414661917688105f5b8094');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '35c64ad5d7c04cd7aee5b4b7d690fac6');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '44de70d1ee7d41009f2bd7c4e397a785');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '46868a1908b34b18a994c998ca62a66e');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '48ef656a17914b53812952964f18037a');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '4f54e16f423b4912b18d48aa61e65449');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '5d246f547bfb49dab8b06f313fbda66a');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '5f1fb6922d114c5bafa60919625b77a2');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '6359fef7169b4e85ac62e51eb45c387e');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '6581a8b3059b42aeabb2ea5abd28f509');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '6704c56b78324f388a8ed0266ebec891');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '6b30ba3254e34123bc437b75431eea6c');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '6fa719080fbb44a594615b4e56f0b42e');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '74ee901b8b6540d187557b698ee84fe7');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '773b94b66e5944068012c38b09631d1a');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '7deef983cc14459b8d31067fdcf7c306');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '81c53575821345b1baaf32a2de2f261a');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '846ca08204ba4406bed82aea47b991dc');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '91d633148ba044dcbdb2fbb0d720784b');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '9226d101093e4495a9a1e7ccea1a589d');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '96f61777346743559e294558a5136ee2');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', '9a109fa87e10422888920114105af254');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'a3d3fb7221a146e8923b59dd27344973');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'a77604157e334aa8971dc4def8e050a5');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'a97800f1c7224ec0b5d74b85883c44d8');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'aac7e6b1af0b4ac6902f3bc7cbc2f228');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'aef73f3bca394a2c8ebf91e17c19c9e1');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'b190a9b0da544102aa79b71226b1ad49');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'b394bb47499b461f8a6fa5b3fa6aea4d');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'b96b0cd845da47a9bd89a7388677d836');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'bec657668eaa46c6b92f0fa1651c362d');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'c0825b6c3a4b49a283e3285e13e1a057');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'd8555ebab2b2480d99d68e5fe5229907');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'dbcfbbd887804a7f952c92b4843a78e0');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'e94589e6b7dd43c0bd576818bf347bca');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'ea0e03bfbf7641c4a276cb185e809088');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'eb8bd08d6e36403fa07f753b84c36bd6');
INSERT INTO `sysstafforg` VALUES ('403da187e7cd4634b8a8dba8daec022a', 'fe511a5e82604ac4ad3e11abee49dc22');
INSERT INTO `sysstafforg` VALUES ('772b06bb9b814a8f90f7fd1e8f1328b7', '0bda2b680c4b4809b64877dc3771f3e8');
INSERT INTO `sysstafforg` VALUES ('772b06bb9b814a8f90f7fd1e8f1328b7', '17e042d795b14ed58a16baf817e439fd');
INSERT INTO `sysstafforg` VALUES ('772b06bb9b814a8f90f7fd1e8f1328b7', '1bdef75f23bb46e3b7f5adfa18fb33a3');
INSERT INTO `sysstafforg` VALUES ('772b06bb9b814a8f90f7fd1e8f1328b7', '21cb3863aa414661917688105f5b8094');
INSERT INTO `sysstafforg` VALUES ('772b06bb9b814a8f90f7fd1e8f1328b7', '44de70d1ee7d41009f2bd7c4e397a785');
INSERT INTO `sysstafforg` VALUES ('7d6fc3c6d6004b8bb97ba32704a63684', '44de70d1ee7d41009f2bd7c4e397a785');
INSERT INTO `sysstafforg` VALUES ('7d6fc3c6d6004b8bb97ba32704a63684', '4f54e16f423b4912b18d48aa61e65449');
INSERT INTO `sysstafforg` VALUES ('7d6fc3c6d6004b8bb97ba32704a63684', '6581a8b3059b42aeabb2ea5abd28f509');
INSERT INTO `sysstafforg` VALUES ('7d6fc3c6d6004b8bb97ba32704a63684', 'c0825b6c3a4b49a283e3285e13e1a057');
INSERT INTO `sysstafforg` VALUES ('7d6fc3c6d6004b8bb97ba32704a63684', 'dbcfbbd887804a7f952c92b4843a78e0');
INSERT INTO `sysstafforg` VALUES ('b30af5d58ce8411b9ea34ac84afd1ee7', '6704c56b78324f388a8ed0266ebec891');
INSERT INTO `sysstafforg` VALUES ('b30af5d58ce8411b9ea34ac84afd1ee7', '96f61777346743559e294558a5136ee2');
INSERT INTO `sysstafforg` VALUES ('b30af5d58ce8411b9ea34ac84afd1ee7', 'd8555ebab2b2480d99d68e5fe5229907');
INSERT INTO `sysstafforg` VALUES ('b30af5d58ce8411b9ea34ac84afd1ee7', 'ea0e03bfbf7641c4a276cb185e809088');
INSERT INTO `sysstafforg` VALUES ('bb8cf317d6e04b7ba2b112064c8f80e9', '17e042d795b14ed58a16baf817e439fd');
INSERT INTO `sysstafforg` VALUES ('bb8cf317d6e04b7ba2b112064c8f80e9', '21cb3863aa414661917688105f5b8094');
INSERT INTO `sysstafforg` VALUES ('bb8cf317d6e04b7ba2b112064c8f80e9', '5f1fb6922d114c5bafa60919625b77a2');
INSERT INTO `sysstafforg` VALUES ('bb8cf317d6e04b7ba2b112064c8f80e9', '773b94b66e5944068012c38b09631d1a');
INSERT INTO `sysstafforg` VALUES ('bb8cf317d6e04b7ba2b112064c8f80e9', '7deef983cc14459b8d31067fdcf7c306');
INSERT INTO `sysstafforg` VALUES ('bb8cf317d6e04b7ba2b112064c8f80e9', '81c53575821345b1baaf32a2de2f261a');
INSERT INTO `sysstafforg` VALUES ('d7ecccf10e924cda9f356046ba58cfc8', '6b30ba3254e34123bc437b75431eea6c');
INSERT INTO `sysstafforg` VALUES ('d7ecccf10e924cda9f356046ba58cfc8', '6fa719080fbb44a594615b4e56f0b42e');
INSERT INTO `sysstafforg` VALUES ('d7ecccf10e924cda9f356046ba58cfc8', 'b394bb47499b461f8a6fa5b3fa6aea4d');
INSERT INTO `sysstafforg` VALUES ('d7ecccf10e924cda9f356046ba58cfc8', 'eb8bd08d6e36403fa07f753b84c36bd6');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '07ae66a2affd4e6284e6708757ceb059');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '0bda2b680c4b4809b64877dc3771f3e8');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '17e042d795b14ed58a16baf817e439fd');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '1bdef75f23bb46e3b7f5adfa18fb33a3');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '2000cc0362174c37883b464d606b4866');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '21cb3863aa414661917688105f5b8094');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '35c64ad5d7c04cd7aee5b4b7d690fac6');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '44de70d1ee7d41009f2bd7c4e397a785');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '46868a1908b34b18a994c998ca62a66e');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '48ef656a17914b53812952964f18037a');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '4f54e16f423b4912b18d48aa61e65449');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '5d246f547bfb49dab8b06f313fbda66a');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '5f1fb6922d114c5bafa60919625b77a2');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '6359fef7169b4e85ac62e51eb45c387e');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '6581a8b3059b42aeabb2ea5abd28f509');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '6704c56b78324f388a8ed0266ebec891');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '6b30ba3254e34123bc437b75431eea6c');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '6fa719080fbb44a594615b4e56f0b42e');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '74ee901b8b6540d187557b698ee84fe7');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '773b94b66e5944068012c38b09631d1a');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '7deef983cc14459b8d31067fdcf7c306');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '81c53575821345b1baaf32a2de2f261a');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '846ca08204ba4406bed82aea47b991dc');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '91d633148ba044dcbdb2fbb0d720784b');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '9226d101093e4495a9a1e7ccea1a589d');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '96f61777346743559e294558a5136ee2');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '9a109fa87e10422888920114105af254');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'a3d3fb7221a146e8923b59dd27344973');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'a77604157e334aa8971dc4def8e050a5');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'a97800f1c7224ec0b5d74b85883c44d8');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'aac7e6b1af0b4ac6902f3bc7cbc2f228');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'aef73f3bca394a2c8ebf91e17c19c9e1');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'b190a9b0da544102aa79b71226b1ad49');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'b394bb47499b461f8a6fa5b3fa6aea4d');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'b96b0cd845da47a9bd89a7388677d836');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'bec657668eaa46c6b92f0fa1651c362d');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'c0825b6c3a4b49a283e3285e13e1a057');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'd8555ebab2b2480d99d68e5fe5229907');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'dbcfbbd887804a7f952c92b4843a78e0');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'e94589e6b7dd43c0bd576818bf347bca');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'ea0e03bfbf7641c4a276cb185e809088');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'eb8bd08d6e36403fa07f753b84c36bd6');
INSERT INTO `sysstafforg` VALUES ('e6c85ed223424772a2ccb5e4473a241d', 'fe511a5e82604ac4ad3e11abee49dc22');

-- ----------------------------
-- Table structure for sysstaffrole
-- ----------------------------
DROP TABLE IF EXISTS `sysstaffrole`;
CREATE TABLE `sysstaffrole` (
  `staffid` varchar(64) NOT NULL DEFAULT '',
  `roleid` varchar(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`staffid`,`roleid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysstaffrole
-- ----------------------------
INSERT INTO `sysstaffrole` VALUES ('005918d55765483981ec9aa239164866', '1');
INSERT INTO `sysstaffrole` VALUES ('1', '1');
INSERT INTO `sysstaffrole` VALUES ('16d6915f51284a8792087f912e0d2e03', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('1df7f384291f4c028162c562e50bf77c', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('2bc744fb28ef45f094c9de7d251dba26', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('403da187e7cd4634b8a8dba8daec022a', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('772b06bb9b814a8f90f7fd1e8f1328b7', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('7d6fc3c6d6004b8bb97ba32704a63684', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('b30af5d58ce8411b9ea34ac84afd1ee7', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('bb8cf317d6e04b7ba2b112064c8f80e9', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('d7ecccf10e924cda9f356046ba58cfc8', '194fd0b2ba0f44e38e3f007a0687dfed');
INSERT INTO `sysstaffrole` VALUES ('e6c85ed223424772a2ccb5e4473a241d', '194fd0b2ba0f44e38e3f007a0687dfed');
