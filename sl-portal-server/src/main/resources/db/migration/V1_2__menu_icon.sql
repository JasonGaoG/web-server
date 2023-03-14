ALTER TABLE `sl_portal`.`sl_menu`
CHANGE COLUMN `top` `icon` varchar(64) NULL COMMENT '菜单图标' AFTER `parent_code`;