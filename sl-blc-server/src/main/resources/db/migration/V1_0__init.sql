

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blc_config
-- ----------------------------
DROP TABLE IF EXISTS `blc_config`;
CREATE TABLE `blc_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delstatus` int NOT NULL DEFAULT '0',
  `key_item` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `value` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `remarks` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for blc_cost
-- ----------------------------
DROP TABLE IF EXISTS `blc_cost`;
CREATE TABLE `blc_cost` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delstatus` int NOT NULL DEFAULT '0',
  `amount` double NOT NULL COMMENT '花费数量',
  `datatime` datetime NOT NULL COMMENT '时间',
  `balance` double NOT NULL,
  `details` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '花费详情',
  `remarks` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for blc_daily_currency
-- ----------------------------
DROP TABLE IF EXISTS `blc_daily_currency`;
CREATE TABLE `blc_daily_currency` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delstatus` int NOT NULL,
  `currency` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '货币类型',
  `amount` double NOT NULL DEFAULT '0' COMMENT '数量',
  `daily_profit_id` int NOT NULL COMMENT '统计每天的收益id',
  `remarks` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for blc_daily_profit
-- ----------------------------
DROP TABLE IF EXISTS `blc_daily_profit`;
CREATE TABLE `blc_daily_profit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delstatus` int NOT NULL,
  `account_xvg_btc` double NOT NULL,
  `account_dcr_btc` double NOT NULL,
  `total_btc` double NOT NULL,
  `btc_price` double NOT NULL,
  `day` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '日期',
  `remarks` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1023 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for blc_deposit
-- ----------------------------
DROP TABLE IF EXISTS `blc_deposit`;
CREATE TABLE `blc_deposit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delstatus` int NOT NULL DEFAULT '0',
  `account` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ref_id` bigint NOT NULL,
  `status` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `symbol` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `amount` double NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `remarks` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `createtime` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9793 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for blc_exchange
-- ----------------------------
DROP TABLE IF EXISTS `blc_exchange`;
CREATE TABLE `blc_exchange` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delstatus` int NOT NULL DEFAULT '0',
  `account` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户 -- 电话号码',
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `finished_time` datetime NOT NULL,
  `amount` double NOT NULL COMMENT '交易数量',
  `fees` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '手续费',
  `filled_btc_amount` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'btc 数量',
  `remarks` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `symbol` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'xvgbtc dcrbtc 等',
  PRIMARY KEY (`id`),
  KEY `creattime` (`create_time`) USING BTREE COMMENT 'shijian',
  KEY `endtime` (`finished_time`) USING BTREE COMMENT 'shijian'
) ENGINE=InnoDB AUTO_INCREMENT=5743 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
