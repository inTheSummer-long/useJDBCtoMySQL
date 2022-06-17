/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.28 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;
CREATE DATABASE IF NOT EXISTS fruitdb;

USE fruitdb;

CREATE TABLE `t_fruit` (
	`fid` INT ,
	`fname` VARCHAR(20),
	`price` INT ,
	`fcount` INT ,
	`remark` VARCHAR(20)
);

INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('37','李子','21','80','好吃，不贵');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('38','芭蕉','23','12','美滴很');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('39','水蜜桃','23','12','是水蜜桃不是杀马特');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('40','火龙果','22','80','美滴很');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('42','橘子','20','80','非常不错');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('43','柚子','23','11','美滴很');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('44','山李子','10','88','物美价廉');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('63','木瓜','23','80','美滴很');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('66','杨桃','19','20','是杨桃哦');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('69','吗啡','20','10','好吃');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('70','柠檬','20','10','好吃');
INSERT INTO `t_fruit` (`fid`, `fname`, `price`, `fcount`, `remark`) VALUES('72','番茄','23','80','非常不错');
