/*
Navicat MySQL Data Transfer

Source Server         : 172.16.137.6
Source Server Version : 50717
Source Host           : 172.16.137.6:3306
Source Database       : blockchain

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-05-06 10:06:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `blc_cost`
-- ----------------------------
DROP TABLE IF EXISTS `blc_cost`;
CREATE TABLE `blc_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `delstatus` int(4) NOT NULL DEFAULT 0,
  `amount` double NOT NULL COMMENT '花费数量',
  `datatime` datetime NOT NULL COMMENT '时间',
  `details` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '花费详情',
  `remarks` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of blc_cost
-- ----------------------------

-- ----------------------------
-- Table structure for `blc_deposit`
-- ----------------------------
DROP TABLE IF EXISTS `blc_deposit`;
CREATE TABLE `blc_deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `delstatus` int(4) NOT NULL DEFAULT '0',
  `account` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `ref_id` bigint(20) NOT NULL,
  `status` varchar(11) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `symbol` varchar(9) COLLATE utf8_unicode_ci NOT NULL,
  `amount` double NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `remarks` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1542 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Records of blc_deposit
-- ----------------------------

-- ----------------------------
-- Table structure for `blc_exchange`
-- ----------------------------
DROP TABLE IF EXISTS `blc_exchange`;
CREATE TABLE `blc_exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `delstatus` int(4) NOT NULL DEFAULT 0,
  `account` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户 -- 电话号码',
  `order_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `finished_time` datetime NOT NULL,
  `amount` double NOT NULL COMMENT '交易数量',
  `fees` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '手续费',
  `filled_btc_amount` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT 'btc 数量',
  `remarks` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of blc_exchange
-- ----------------------------
