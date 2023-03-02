CREATE TABLE `selected_stock`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_name` varchar(16) NOT NULL,
  `stock_code` varchar(16) NOT NULL,
  `delstatus` int NOT NULL,
  PRIMARY KEY (`id`)
);