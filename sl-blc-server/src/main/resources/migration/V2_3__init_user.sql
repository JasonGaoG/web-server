DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '密码',
    `user_role` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '角色',
  `delstatus` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
