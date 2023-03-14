SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for composite_index
-- ----------------------------
DROP TABLE IF EXISTS `composite_index`;
CREATE TABLE `composite_index` (
  `id` int NOT NULL AUTO_INCREMENT,
  `market` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '上证A， 深成A，创业板',
  `price` double(10,2) NOT NULL,
  `up_percent` double(10,2) NOT NULL COMMENT '上涨下跌幅度',
  `record_date` varchar(16) COLLATE utf8_bin NOT NULL,
  `delstatus` int NOT NULL DEFAULT '0',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `turn_over` double(12,2) NOT NULL COMMENT '成交量',
  `market_name` varchar(16) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for invest_user
-- ----------------------------
DROP TABLE IF EXISTS `invest_user`;
CREATE TABLE `invest_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `delstatus` int NOT NULL,
  `company_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for policy_limit
-- ----------------------------
DROP TABLE IF EXISTS `policy_limit`;
CREATE TABLE `policy_limit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `plates` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `limit_type` int NOT NULL COMMENT '涨停或者跌停',
  `days` int NOT NULL COMMENT '涨停或者跌停天数',
  `limit_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '涨跌停日期',
  `remarks` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `delstatus` int NOT NULL DEFAULT '0' COMMENT '软删除标志位',
  `current_price` double(10,2) NOT NULL COMMENT '当前价格',
  `up_percent` double(10,2) NOT NULL COMMENT '涨跌幅度',
  `highest_price` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '当天最高价格',
  `lowest_price` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '当天最低价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1660 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for policy_price
-- ----------------------------
DROP TABLE IF EXISTS `policy_price`;
CREATE TABLE `policy_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(12) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `prices` varchar(20000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '价格列表',
  `new_high_20` double(10,2) NOT NULL COMMENT '20日高价',
  `new_high_60` double(10,2) NOT NULL COMMENT '60日高价格',
  `new_high_90` double(10,2) NOT NULL COMMENT '90日高价',
  `new_high_120` double(10,2) NOT NULL COMMENT '120日高价',
  `new_high_est` double(10,2) NOT NULL COMMENT '历史最高',
  `new_low_20` double(10,2) NOT NULL COMMENT '20日高价',
  `new_low_60` double(10,2) NOT NULL COMMENT '60日低价格',
  `new_low_90` double(10,2) NOT NULL COMMENT '90日低价',
  `new_low_120` double(10,2) NOT NULL COMMENT '120日低价',
  `new_low_est` double(10,2) NOT NULL COMMENT '历史最低',
  `delstatus` int NOT NULL,
  `new_high_20_date` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '20日高价日期',
  `new_high_60_date` varchar(32) COLLATE utf8_bin NOT NULL,
  `new_high_90_date` varchar(32) COLLATE utf8_bin NOT NULL,
  `new_high_120_date` varchar(32) COLLATE utf8_bin NOT NULL,
  `new_high_est_date` varchar(32) COLLATE utf8_bin NOT NULL,
  `new_low_20_date` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '20日高价日期',
  `new_low_60_date` varchar(32) COLLATE utf8_bin NOT NULL,
  `new_low_90_date` varchar(32) COLLATE utf8_bin NOT NULL,
  `new_low_120_date` varchar(32) COLLATE utf8_bin NOT NULL,
  `new_low_est_date` varchar(32) COLLATE utf8_bin NOT NULL,
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4402 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for policy_sel_high_low_notify
-- ----------------------------
DROP TABLE IF EXISTS `policy_sel_high_low_notify`;
CREATE TABLE `policy_sel_high_low_notify` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `prices` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `notify_type` int NOT NULL COMMENT '最高还是最低的通知类型',
  `notify_price` double(10,2) NOT NULL COMMENT '通知时的价格',
  `has_buy` int NOT NULL COMMENT '是否买入',
  `buy_price` double(10,2) DEFAULT NULL COMMENT '买入价格',
  `has_sell` int DEFAULT NULL COMMENT '是否卖出',
  `sell_price` double(10,2) DEFAULT NULL COMMENT '卖出价格',
  `profit` double(10,2) DEFAULT NULL COMMENT '收益',
  `best_profit` double(10,2) DEFAULT NULL COMMENT '30天内最好收益',
  `bad_profit` double(10,2) DEFAULT NULL COMMENT '30天内最差收益',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `delstatus` int NOT NULL DEFAULT '0',
  `notify_date` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '记录日期',
  `days` int NOT NULL COMMENT '记录天数，目前记录30天记录',
  `buy_date` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '购买日期',
  `sell_date` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '卖出日期',
  `high_est_date` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '30日内最高日期',
  `low_est_date` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '30日内最低日期',
  `high_est_price` double(10,2) DEFAULT NULL,
  `low_est_price` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for profit
-- ----------------------------
DROP TABLE IF EXISTS `profit`;
CREATE TABLE `profit` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'primariy key',
  `date` datetime NOT NULL COMMENT '交易时间',
  `user_id` int NOT NULL COMMENT '交易人id',
  `stock_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '股票名称',
  `profit` double(255,0) NOT NULL COMMENT '盈利',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `settled` int NOT NULL DEFAULT '0' COMMENT '是否结算',
  `company_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sel_stock
-- ----------------------------
DROP TABLE IF EXISTS `sel_stock`;
CREATE TABLE `sel_stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `code` varchar(9) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `delstatus` int NOT NULL DEFAULT '0',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `is_third` int NOT NULL DEFAULT '0',
  `company_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for stocks
-- ----------------------------
DROP TABLE IF EXISTS `stocks`;
CREATE TABLE `stocks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `delstatus` int NOT NULL,
  `list_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `remarks` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `plates` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '股票所属板块',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`code`) USING BTREE COMMENT '股票编码唯一'
) ENGINE=InnoDB AUTO_INCREMENT=4442 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for trade_date
-- ----------------------------
DROP TABLE IF EXISTS `trade_date`;
CREATE TABLE `trade_date` (
  `id` int NOT NULL AUTO_INCREMENT,
  `t_date` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '交易日',
  `delstatus` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=486 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
