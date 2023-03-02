DROP TABLE IF EXISTS `blc_daily_currency`;
CREATE TABLE `blc_daily_currency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `delstatus` int(4) NOT NULL,
  `currency` varchar(16) COLLATE utf8_unicode_ci NOT NULL COMMENT '货币类型',
  `amount` double NOT NULL DEFAULT '0' COMMENT '数量',
  `daily_profit_id` int(11) NOT NULL COMMENT '统计每天的收益id',
  `remarks` varchar(256) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;