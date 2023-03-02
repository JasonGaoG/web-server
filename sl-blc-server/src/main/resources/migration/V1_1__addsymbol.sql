ALTER TABLE `blc_exchange`
  ADD COLUMN `symbol`  varchar(9) NOT NULL COMMENT 'xvgbtc dcrbtc 等' AFTER `remarks`;

ALTER TABLE `blc_deposit`
ADD INDEX `createtime` (`create_time`) USING BTREE ;

-- ----------------------------
-- Table structure for `blc_config`
-- ----------------------------
DROP TABLE IF EXISTS `blc_config`;
CREATE TABLE `blc_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `delstatus` int(4) NOT NULL DEFAULT 0,
  `key_item` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `value` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `remarks` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;