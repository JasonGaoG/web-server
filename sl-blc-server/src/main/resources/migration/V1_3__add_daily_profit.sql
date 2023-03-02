DROP TABLE IF EXISTS `blc_daily_profit`;
CREATE TABLE `blc_daily_profit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `delstatus` int(4) NOT NULL,
  `account_xvg_btc` double NOT NULL,
  `account_dcr_btc` double NOT NULL,
  `total_btc` double NOT NULL,
  `btc_price` double NOT NULL,
  `day` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '日期',
  `remarks` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;