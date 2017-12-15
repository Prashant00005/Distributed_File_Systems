create database fileserver;  
use fileserver;  
drop table `fileserver`.`users`;

CREATE TABLE `fileserver`.`users` (
  `username` varchar(50) DEFAULT NULL,
  `pswd` varchar(100) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `usertype` varchar(1) DEFAULT NULL,
  `token` varchar(200) DEFAULT NULL,
  `encrypt_username` varchar(200) DEFAULT NULL,
  `key1` varchar(200) DEFAULT NULL,
  `key2` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `fileserver`.`users` (`username`,`pswd`,`fname`,`usertype`,`token`,`encrypt_username`,`key1`,`key2`) VALUES ('Prashant.Aggarwal','Prashant123!','Prashant','N','+2Rx5+QJxqbeE7pVUalBzXL5MHFXOE/6BuWYAU28P7voN1LoSC711g==','bt7mPJhQD0fjiNnBCZ+PjBpLMq7FwfPM','[B@786dd73b','[B@1468298d');
commit;
select * from `fileserver`.`users`;



