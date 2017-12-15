create database lockserver;  
use lockserver;  

DROP TABLE `lockserver`.`lookup`;


 CREATE TABLE `lookup` (
  `username` varchar(100) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `emailclient` varchar(100) DEFAULT NULL,
  `locked` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

commit;