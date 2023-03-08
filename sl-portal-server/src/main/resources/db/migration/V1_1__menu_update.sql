ALTER TABLE `sl_portal`.`sl_menu` 
ADD COLUMN `parent_code` varchar(32) NULL COMMENT '上级菜单' AFTER `delstatus`,
ADD COLUMN `top` int(1) NOT NULL COMMENT '是否是顶部菜单栏' AFTER `parent_code`;