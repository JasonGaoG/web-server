CREATE TABLE `blockchain`.`configs`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `item_key` varchar(128) NOT NULL,
  `item_value` text NOT NULL,
  `desc` varchar(255) NULL,
  `delstatus` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);