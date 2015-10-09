CREATE TABLE `time_sheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `work_date` bigint(20) NOT NULL,
  `chunk_id` int(2) NOT NULL,
  `time_in` bigint(20) DEFAULT NULL,
  `time_out` bigint(20) DEFAULT NULL,
  `created` bigint(20) DEFAULT NULL,
  `updated` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id_idx` (`emp_id`),
  KEY `date` (`work_date`),
  CONSTRAINT `employee_id` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
