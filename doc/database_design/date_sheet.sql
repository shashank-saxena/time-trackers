CREATE TABLE `date_sheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `work_date` bigint(20) NOT NULL,
  `work_desc` varchar(100) NOT NULL DEFAULT 'Not Specified',
  `created` bigint(20) NOT NULL,
  `updated` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `emp_id_idx` (`emp_id`),
  KEY `date` (`work_date`),
  CONSTRAINT `emp_id` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;