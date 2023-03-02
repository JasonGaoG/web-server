CREATE TABLE `blockchain`.`company`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '公司名称',
  `address` varchar(255) NULL COMMENT '地址',
  `remarks` varchar(255) NULL,
  `push_url` varchar(255) NULL COMMENT '消息推送地址',
  `delstatus` int NOT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `blockchain`.`user`
ADD COLUMN `company_id` int NOT NULL COMMENT '所属公司id' AFTER `delstatus`;