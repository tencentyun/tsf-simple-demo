-- Create tsf_apm schema
DROP SCHEMA IF EXISTS `tsf_test`;
CREATE SCHEMA `tsf_test` ;

-- Create Tables of tsf_user
-- tsf_user
DROP TABLE IF EXISTS `tsf_test`.`tsf_user`;
CREATE TABLE `tsf_test`.`tsf_user` (
	`user_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'user identifier',
	`user_name` varchar(60) NOT NULL COMMENT 'user name',
	`user_token` varchar(32) NOT NULL COMMENT 'user token',
	`creation_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'user creation time',
	UNIQUE INDEX unique_name(`user_name`)
);

-- Create Tables of tsf_count
-- tsf_count
DROP TABLE IF EXISTS `tsf_test`.`tsf_count`;
CREATE TABLE `tsf_test`.`tsf_count` (
	`count_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'count identifier',
	`insertion_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'insertion time'
);