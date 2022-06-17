/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.28 : Database - fruitdb
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `fruitdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `fruitdb`;

/*Table structure for table `t_fruit` */

DROP TABLE IF EXISTS `t_fruit`;

CREATE TABLE `t_fruit` (
  `fid` int NOT NULL  AUTO_INCREMENT,
  `fname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` int DEFAULT NULL,
  `fcount` int DEFAULT NULL,
  `remark` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`fid`),
  KEY `fid` (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb3;

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

/*Data for the table `t_fruit` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
