CREATE TABLE `session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `emp_name` varchar(45) NOT NULL,
  `token` varchar(60) NOT NULL,
  `expires_when` bigint(20) DEFAULT NULL,
  `created` bigint(20) NOT NULL,
  `updated` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_UNIQUE` (`token`),
  KEY `empl_id_idx` (`emp_id`),
  KEY `token` (`token`),
  CONSTRAINT `empl_id` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
