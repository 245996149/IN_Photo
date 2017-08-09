-- MySQL dump 10.13  Distrib 5.7.19, for Linux (x86_64)
--
-- Host: localhost    Database: IN_Photo
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_category`
--

DROP TABLE IF EXISTS `admin_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_category` (
  `admin_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`admin_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_category`
--

LOCK TABLES `admin_category` WRITE;
/*!40000 ALTER TABLE `admin_category` DISABLE KEYS */;
INSERT INTO `admin_category` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `admin_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_info`
--

DROP TABLE IF EXISTS `admin_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_info` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `admin_statu` char(2) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_info`
--

LOCK TABLES `admin_info` WRITE;
/*!40000 ALTER TABLE `admin_info` DISABLE KEYS */;
INSERT INTO `admin_info` VALUES (1,'ming123','C01C624C6E877C2782B7435EFD0290C8','18817774173','245996149@qq.com','2017-07-10 16:00:00','0'),(2,'inshow123','C01C624C6E877C2782B7435EFD0290C8','17087950984','chen.ming@in-show.com.cn','2017-07-10 16:00:00','0');
/*!40000 ALTER TABLE `admin_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_module_info`
--

DROP TABLE IF EXISTS `admin_module_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_module_info` (
  `module_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_module_info`
--

LOCK TABLES `admin_module_info` WRITE;
/*!40000 ALTER TABLE `admin_module_info` DISABLE KEYS */;
INSERT INTO `admin_module_info` VALUES (1,'客户管理'),(2,'套餐管理'),(3,'用户管理'),(4,'角色管理');
/*!40000 ALTER TABLE `admin_module_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_role`
--

DROP TABLE IF EXISTS `admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role` (
  `admin_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`admin_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role`
--

LOCK TABLES `admin_role` WRITE;
/*!40000 ALTER TABLE `admin_role` DISABLE KEYS */;
INSERT INTO `admin_role` VALUES (1,1),(1,7),(2,1),(2,7);
/*!40000 ALTER TABLE `admin_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_role_info`
--

DROP TABLE IF EXISTS `admin_role_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role_info` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role_info`
--

LOCK TABLES `admin_role_info` WRITE;
/*!40000 ALTER TABLE `admin_role_info` DISABLE KEYS */;
INSERT INTO `admin_role_info` VALUES (1,'管理员'),(7,'技师');
/*!40000 ALTER TABLE `admin_role_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_role_module`
--

DROP TABLE IF EXISTS `admin_role_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role_module` (
  `role_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role_module`
--

LOCK TABLES `admin_role_module` WRITE;
/*!40000 ALTER TABLE `admin_role_module` DISABLE KEYS */;
INSERT INTO `admin_role_module` VALUES (1,1),(1,2),(1,3),(1,4),(7,2),(7,3),(7,4);
/*!40000 ALTER TABLE `admin_role_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `category_id` int(3) NOT NULL AUTO_INCREMENT COMMENT '套餐id',
  `category_code` varchar(20) NOT NULL COMMENT '套餐简码',
  `category_name` varchar(20) NOT NULL COMMENT '套餐名称',
  `made_gif` tinyint(4) NOT NULL DEFAULT '0',
  `gif_transparency` tinyint(4) NOT NULL DEFAULT '0',
  `category_note` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'gif2','gif2.0系统',1,1,' 制作透明背景的GIF动画'),(2,'gif1','gif1.0系统',1,0,' 制作普通gif动画'),(3,'slr','单反拍照系统',0,0,'单反实拍'),(4,'slrg','单反拍照抠像系统',0,0,'单反拍照抠像');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `code_webinfo`
--

DROP TABLE IF EXISTS `code_webinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `code_webinfo` (
  `code_webinfo_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `category_id` int(3) NOT NULL,
  `page_title` varchar(20) DEFAULT NULL,
  `background` varchar(255) DEFAULT NULL,
  `input_top` float(5,2) DEFAULT NULL,
  `input_left` float(5,2) DEFAULT NULL,
  `input_right` float(5,2) DEFAULT NULL,
  `input_bottom` float(5,2) DEFAULT NULL,
  `input_bg_color` varchar(10) DEFAULT NULL,
  `input_border_color` varchar(10) DEFAULT NULL,
  `input_text_color` varchar(10) DEFAULT NULL,
  `button_top` float(5,2) DEFAULT NULL,
  `button_left` float(5,2) DEFAULT NULL,
  `button_right` float(5,2) DEFAULT NULL,
  `button_bottom` float(5,2) DEFAULT NULL,
  `button_pic` varchar(255) DEFAULT NULL,
  `code_webinfo_state` char(1) DEFAULT NULL,
  PRIMARY KEY (`code_webinfo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_webinfo`
--

LOCK TABLES `code_webinfo` WRITE;
/*!40000 ALTER TABLE `code_webinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `code_webinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginfo`
--

DROP TABLE IF EXISTS `loginfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loginfo` (
  `loginfo_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `category_id` int(11) DEFAULT NULL COMMENT '套餐系统id',
  `class` varchar(255) DEFAULT NULL COMMENT '操作的类',
  `method` varchar(255) DEFAULT NULL COMMENT '操作的方法',
  `line` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `log_level` varchar(20) DEFAULT NULL COMMENT '错误级别',
  `message` longtext COMMENT '错误信息',
  PRIMARY KEY (`loginfo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginfo`
--

LOCK TABLES `loginfo` WRITE;
/*!40000 ALTER TABLE `loginfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `loginfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_code`
--

DROP TABLE IF EXISTS `media_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media_code` (
  `media_code_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `media_code` varchar(10) NOT NULL,
  `media_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `category_id` int(3) NOT NULL,
  PRIMARY KEY (`media_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_code`
--

LOCK TABLES `media_code` WRITE;
/*!40000 ALTER TABLE `media_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `media_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_data`
--

DROP TABLE IF EXISTS `media_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media_data` (
  `media_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `media_name` varchar(50) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `category_id` int(3) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `media_state` char(1) NOT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `over_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`media_id`),
  UNIQUE KEY `media_name_UNIQUE` (`media_name`)
) ENGINE=InnoDB AUTO_INCREMENT=589 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_data`
--

LOCK TABLES `media_data` WRITE;
/*!40000 ALTER TABLE `media_data` DISABLE KEYS */;
INSERT INTO `media_data` VALUES (1,'7990194750','/data/IN_Photo/2/slr/7990194750.jpg',1,3,'2017-06-23 06:34:26','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(2,'1432295352','/data/IN_Photo/2/slr/1432295352.jpg',1,3,'2017-06-23 09:53:13','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(3,'8256209046','/data/IN_Photo/2/slr/8256209046.jpg',1,3,'2017-06-23 09:53:14','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(4,'3327884547','/data/IN_Photo/2/slr/3327884547.jpg',1,3,'2017-06-23 09:53:14','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(5,'0230330957','/data/IN_Photo/2/slr/0230330957.jpg',1,3,'2017-06-23 09:53:15','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(6,'4383005630','/data/IN_Photo/2/slr/4383005630.jpg',1,3,'2017-06-23 09:53:15','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(7,'6324221779','/data/IN_Photo/2/slr/6324221779.jpg',1,3,'2017-06-23 09:53:16','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(8,'0159168924','/data/IN_Photo/2/slr/0159168924.jpg',1,3,'2017-06-23 09:53:17','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(9,'2658645210','/data/IN_Photo/2/slr/2658645210.jpg',1,3,'2017-06-23 09:53:17','1','2017-06-27 08:42:31','2017-07-27 08:42:31'),(10,'9135038869','/data/IN_Photo/2/slr/9135038869.jpg',1,3,'2017-06-23 09:53:18','1','2017-06-27 08:42:46','2017-07-27 08:42:46'),(11,'8612323609','/data/IN_Photo/2/slr/8612323609.jpg',1,3,'2017-06-23 09:53:18','1','2017-06-27 08:42:46','2017-07-27 08:42:46'),(32,'1498455212','/data/IN_Photo/2/slr/1498455212.png',1,3,'2017-06-26 05:35:58','0',NULL,NULL),(39,'1498455557','/data/IN_Photo/2/slr/1498455557.png',1,3,'2017-06-26 05:41:46','0',NULL,NULL),(48,'1498455706','/data/IN_Photo/2/slr/1498455706.png',1,3,'2017-06-26 05:44:11','0',NULL,NULL),(52,'1498455908','/data/IN_Photo/2/slr/1498455908.png',1,3,'2017-06-26 05:47:33','0',NULL,NULL),(53,'1498456191','/data/IN_Photo/2/slr/1498456191.png',1,3,'2017-06-26 05:52:18','0',NULL,NULL),(54,'1498456359','/data/IN_Photo/2/slr/1498456359.png',1,3,'2017-06-26 05:55:05','0',NULL,NULL),(55,'1498456603','/data/IN_Photo/2/slr/1498456603.png',1,3,'2017-06-26 05:59:09','0',NULL,NULL),(56,'1498456702','/data/IN_Photo/2/slr/1498456702.png',1,3,'2017-06-26 06:00:47','0',NULL,NULL),(57,'1498456759','/data/IN_Photo/2/slr/1498456759.png',1,3,'2017-06-26 06:01:45','0',NULL,NULL),(58,'1498456811','/data/IN_Photo/2/slr/1498456811.png',1,3,'2017-06-26 06:02:37','0',NULL,NULL),(59,'1498456942','/data/IN_Photo/2/slr/1498456942.png',1,3,'2017-06-26 06:04:48','0',NULL,NULL),(60,'1498457004','/data/IN_Photo/2/slr/1498457004.png',1,3,'2017-06-26 06:05:49','0',NULL,NULL),(61,'1498457056','/data/IN_Photo/2/slr/1498457056.png',1,3,'2017-06-26 06:08:01','0',NULL,NULL),(72,'1498457193','/data/IN_Photo/2/slr/1498457193.png',1,3,'2017-06-26 06:08:58','0',NULL,NULL),(73,'1498457343','/data/IN_Photo/2/slr/1498457343.png',1,3,'2017-06-26 06:11:28','0',NULL,NULL),(74,'1498457366','/data/IN_Photo/2/slr/1498457366.png',1,3,'2017-06-26 06:11:51','0',NULL,NULL),(75,'1498457423','/data/IN_Photo/2/slr/1498457423.png',1,3,'2017-06-26 06:12:49','0',NULL,NULL),(76,'1498457445','/data/IN_Photo/2/slr/1498457445.png',1,3,'2017-06-26 06:13:09','0',NULL,NULL),(77,'1498457467','/data/IN_Photo/2/slr/1498457467.png',1,3,'2017-06-26 06:13:32','0',NULL,NULL),(78,'1498457489','/data/IN_Photo/2/slr/1498457489.png',1,3,'2017-06-26 06:14:53','0',NULL,NULL),(79,'1498458208','/data/IN_Photo/2/slr/1498458208.png',1,3,'2017-06-26 06:25:53','0',NULL,NULL),(80,'1498458995','/data/IN_Photo/2/slr/1498458995.png',1,3,'2017-06-26 06:38:50','0',NULL,NULL),(81,'1498459251','/data/IN_Photo/2/slr/1498459251.png',1,3,'2017-06-26 06:43:03','0',NULL,NULL),(82,'1498459346','/data/IN_Photo/2/slr/1498459346.png',1,3,'2017-06-26 06:44:41','0',NULL,NULL),(83,'1498459370','/data/IN_Photo/2/slr/1498459370.png',1,3,'2017-06-26 06:45:04','0',NULL,NULL),(84,'1498459396','/data/IN_Photo/2/slr/1498459396.png',1,3,'2017-06-26 06:45:29','0',NULL,NULL),(85,'1498459427','/data/IN_Photo/2/slr/1498459427.png',1,3,'2017-06-26 06:46:01','0',NULL,NULL),(86,'1498459458','/data/IN_Photo/2/slr/1498459458.png',1,3,'2017-06-26 06:46:31','0',NULL,NULL),(87,'1498459500','/data/IN_Photo/2/slr/1498459500.png',1,3,'2017-06-26 06:47:13','0',NULL,NULL),(88,'1498459537','/data/IN_Photo/2/slr/1498459537.png',1,3,'2017-06-26 06:47:49','0',NULL,NULL),(89,'1498459560','/data/IN_Photo/2/slr/1498459560.png',1,3,'2017-06-26 06:48:15','0',NULL,NULL),(90,'1498459584','/data/IN_Photo/2/slr/1498459584.png',1,3,'2017-06-26 06:48:37','0',NULL,NULL),(91,'1498459608','/data/IN_Photo/2/slr/1498459608.png',1,3,'2017-06-26 06:49:01','0',NULL,NULL),(92,'1498459630','/data/IN_Photo/2/slr/1498459630.png',1,3,'2017-06-26 06:49:24','0',NULL,NULL),(93,'1498459975','/data/IN_Photo/2/slr/1498459975.png',1,3,'2017-06-26 06:55:09','2','2017-06-26 07:10:50','2017-06-26 07:11:11'),(94,'1498460810','/data/IN_Photo/2/slr/1498460810.png',1,3,'2017-06-26 07:09:15','0',NULL,NULL),(95,'1498460890','/data/IN_Photo/2/slr/1498460890.png',1,3,'2017-06-26 07:10:27','0',NULL,NULL),(96,'1498461097','/data/IN_Photo/2/slr/1498461097.png',1,3,'2017-06-26 07:13:56','0',NULL,NULL),(97,'1498461189','/data/IN_Photo/2/slr/1498461189.png',1,3,'2017-06-26 07:15:24','0',NULL,NULL),(98,'1498461359','/data/IN_Photo/2/slr/1498461359.png',1,3,'2017-06-26 07:18:13','0',NULL,NULL),(99,'1498461386','/data/IN_Photo/2/slr/1498461386.png',1,3,'2017-06-26 07:18:41','2','2017-06-26 07:24:51','2017-06-27 04:17:07'),(100,'1498464875','/data/IN_Photo/2/slr/1498464875.png',1,3,'2017-06-26 08:16:49','2','2017-06-26 09:00:37','2017-06-27 04:17:07'),(101,'1498467292','/data/IN_Photo/2/slr/1498467292.png',1,3,'2017-06-26 08:57:10','0',NULL,NULL),(102,'1498467548','/data/IN_Photo/2/slr/1498467548.png',1,3,'2017-06-26 09:01:21','0',NULL,NULL),(103,'1498467653','/data/IN_Photo/2/slr/1498467653.png',1,3,'2017-06-26 09:03:29','0',NULL,NULL),(104,'1498467887','/data/IN_Photo/2/slr/1498467887.png',1,3,'2017-06-26 09:07:00','0',NULL,NULL),(105,'1498468164','/data/IN_Photo/2/slr/1498468164.png',1,3,'2017-06-26 09:11:40','0',NULL,NULL),(106,'1498468415','/data/IN_Photo/2/slr/1498468415.png',1,3,'2017-06-26 09:15:48','0',NULL,NULL),(107,'1498468644','/data/IN_Photo/2/slr/1498468644.png',1,3,'2017-06-26 09:19:50','0',NULL,NULL),(108,'1498469091','/data/IN_Photo/2/slr/1498469091.png',1,3,'2017-06-26 09:27:06','0',NULL,NULL),(109,'1498469176','/data/IN_Photo/2/slr/1498469176.png',1,3,'2017-06-26 09:28:31','0',NULL,NULL),(110,'1498525087','/data/IN_Photo/2/slr/1498525087.png',1,3,'2017-06-27 01:00:38','0',NULL,NULL),(111,'5400264021','/data/IN_Photo/2/slr/5400264021.jpg',1,3,'2017-06-27 02:52:57','2','2017-06-27 02:53:50','2017-06-27 04:17:07'),(112,'6473797689','/data/IN_Photo/2/slr/6473797689.jpg',1,3,'2017-06-27 02:55:50','2','2017-06-27 02:56:10','2017-06-27 04:17:07'),(113,'8483839868','/data/IN_Photo/2/slr/8483839868.jpg',1,3,'2017-06-27 02:56:19','2','2017-06-27 03:05:37','2017-06-27 04:17:07'),(114,'8005840855','/data/IN_Photo/2/slr/8005840855.jpg',1,3,'2017-06-27 03:05:45','2','2017-06-27 03:13:19','2017-06-27 04:17:07'),(115,'0892747844','/data/IN_Photo/2/slr/0892747844.jpg',1,3,'2017-06-27 03:05:56','2','2017-06-27 03:13:19','2017-06-27 04:17:07'),(116,'2547958286','/data/IN_Photo/2/slr/2547958286.jpg',1,3,'2017-06-27 03:09:19','2','2017-06-27 03:13:19','2017-06-27 04:17:07'),(117,'4456003751','/data/IN_Photo/2/slr/4456003751.jpg',1,3,'2017-06-27 03:18:14','2','2017-06-27 04:16:55','2017-06-27 04:17:07'),(118,'6573753696','/data/IN_Photo/2/gif1/6573753696.gif',1,2,'2017-06-27 04:25:17','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(119,'6813887149','/data/IN_Photo/2/gif1/6813887149.gif',1,2,'2017-06-27 04:25:18','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(120,'8089855386','/data/IN_Photo/2/gif1/8089855386.gif',1,2,'2017-06-27 04:25:18','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(121,'6637450950','/data/IN_Photo/2/gif1/6637450950.gif',1,2,'2017-06-27 04:25:19','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(122,'0490129499','/data/IN_Photo/2/gif1/0490129499.gif',1,2,'2017-06-27 04:25:19','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(123,'0810503263','/data/IN_Photo/2/gif1/0810503263.gif',1,2,'2017-06-27 04:25:20','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(124,'7922578810','/data/IN_Photo/2/gif1/7922578810.gif',1,2,'2017-06-27 04:25:20','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(125,'3228172158','/data/IN_Photo/2/gif1/3228172158.gif',1,2,'2017-06-27 04:25:20','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(126,'8401829007','/data/IN_Photo/2/gif1/8401829007.gif',1,2,'2017-06-27 04:25:21','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(127,'0348088612','/data/IN_Photo/2/gif1/0348088612.gif',1,2,'2017-06-27 04:25:21','2','2017-06-27 04:31:33','2017-06-27 04:31:45'),(128,'8153965718','/data/IN_Photo/2/slr/8153965718.jpg',1,3,'2017-06-27 07:43:30','1','2017-06-27 07:45:39','2017-07-27 07:45:39'),(129,'7252810091','/data/IN_Photo/2/slr/7252810091.jpg',1,3,'2017-06-27 07:46:18','0',NULL,NULL),(130,'1644042484','/data/IN_Photo/2/slr/1644042484.jpg',1,3,'2017-06-27 07:49:24','0',NULL,NULL),(131,'4947277867','/data/IN_Photo/2/slr/4947277867.jpg',1,3,'2017-06-27 07:54:56','0',NULL,NULL),(132,'1498550153','/data/IN_Photo/2/slr/1498550153.png',1,3,'2017-06-27 07:58:10','0',NULL,NULL),(133,'1498551037','/data/IN_Photo/2/slr/1498551037.png',1,3,'2017-06-27 08:12:52','0',NULL,NULL),(134,'1498551207','/data/IN_Photo/2/slr/1498551207.png',1,3,'2017-06-27 08:15:45','0',NULL,NULL),(135,'1498551299','/data/IN_Photo/2/slr/1498551299.png',1,3,'2017-06-27 08:17:14','0',NULL,NULL),(136,'1498552650','/data/IN_Photo/2/slr/1498552650.png',1,3,'2017-06-27 08:37:44','0',NULL,NULL),(137,'1498552694','/data/IN_Photo/2/slr/1498552694.png',1,3,'2017-06-27 08:38:31','0',NULL,NULL),(138,'1498557125','/data/IN_Photo/2/slr/1498557125.png',1,3,'2017-06-27 09:52:26','0',NULL,NULL),(139,'1498557230','/data/IN_Photo/2/slr/1498557230.png',1,3,'2017-06-27 09:54:05','0',NULL,NULL),(140,'1498557343','/data/IN_Photo/2/slr/1498557343.png',1,3,'2017-06-27 09:55:58','0',NULL,NULL),(141,'1498612490','/data/IN_Photo/2/slr/1498612490.png',1,3,'2017-06-28 01:15:16','0',NULL,NULL),(142,'1498612749','/data/IN_Photo/2/slr/1498612749.png',1,3,'2017-06-28 01:19:46','0',NULL,NULL),(143,'1498613660','/data/IN_Photo/2/slr/1498613660.png',1,3,'2017-06-28 01:35:17','0',NULL,NULL),(144,'1498613901','/data/IN_Photo/2/slr/1498613901.png',1,3,'2017-06-28 01:39:22','0',NULL,NULL),(145,'1498614119','/data/IN_Photo/2/slr/1498614119.png',1,3,'2017-06-28 01:44:15','0',NULL,NULL),(146,'1498614345','/data/IN_Photo/2/slr/1498614345.png',1,3,'2017-06-28 01:46:00','0',NULL,NULL),(147,'1498614407','/data/IN_Photo/2/slr/1498614407.png',1,3,'2017-06-28 01:47:06','0',NULL,NULL),(148,'1498614674','/data/IN_Photo/2/slr/1498614674.png',1,3,'2017-06-28 01:51:40','0',NULL,NULL),(149,'1498614749','/data/IN_Photo/2/slr/1498614749.png',1,3,'2017-06-28 01:52:45','0',NULL,NULL),(150,'1498614808','/data/IN_Photo/2/slr/1498614808.png',1,3,'2017-06-28 01:53:56','0',NULL,NULL),(151,'1498614903','/data/IN_Photo/2/slr/1498614903.png',1,3,'2017-06-28 01:55:19','0',NULL,NULL),(152,'1498614948','/data/IN_Photo/2/slr/1498614948.png',1,3,'2017-06-28 01:56:09','0',NULL,NULL),(153,'1498615574','/data/IN_Photo/2/slr/1498615574.png',1,3,'2017-06-28 02:06:34','0',NULL,NULL),(154,'1498615735','/data/IN_Photo/2/slr/1498615735.png',1,3,'2017-06-28 02:09:11','0',NULL,NULL),(155,'1498615921','/data/IN_Photo/2/slr/1498615921.png',1,3,'2017-06-28 02:12:17','0',NULL,NULL),(156,'1498615963','/data/IN_Photo/2/slr/1498615963.png',1,3,'2017-06-28 02:13:02','0',NULL,NULL),(157,'1498616216','/data/IN_Photo/2/slr/1498616216.png',1,3,'2017-06-28 02:17:12','0',NULL,NULL),(158,'1498616507','/data/IN_Photo/2/slr/1498616507.png',1,3,'2017-06-28 02:22:07','0',NULL,NULL),(159,'1498617502','/data/IN_Photo/2/slr/1498617502.png',1,3,'2017-06-28 02:38:38','0',NULL,NULL),(160,'1498617564','/data/IN_Photo/2/slr/1498617564.png',1,3,'2017-06-28 02:39:53','0',NULL,NULL),(161,'1498618216','/data/IN_Photo/2/slr/1498618216.png',1,3,'2017-06-28 02:50:34','0',NULL,NULL),(162,'1498618348','/data/IN_Photo/2/slr/1498618348.png',1,3,'2017-06-28 02:52:43','0',NULL,NULL),(163,'1498618421','/data/IN_Photo/2/slr/1498618421.png',1,3,'2017-06-28 02:53:57','0',NULL,NULL),(164,'1498618520','/data/IN_Photo/2/slr/1498618520.png',1,3,'2017-06-28 02:55:38','0',NULL,NULL),(165,'1498618929','/data/IN_Photo/2/slr/1498618929.png',1,3,'2017-06-28 03:02:29','0',NULL,NULL),(166,'1498619294','/data/IN_Photo/2/slr/1498619294.png',1,3,'2017-06-28 03:08:30','0',NULL,NULL),(167,'1498620232','/data/IN_Photo/2/slr/1498620232.png',1,3,'2017-06-28 03:24:07','0',NULL,NULL),(168,'1498620374','/data/IN_Photo/2/slr/1498620374.png',1,3,'2017-06-28 03:26:31','0',NULL,NULL),(169,'1498621646','/data/IN_Photo/2/slr/1498621646.png',1,3,'2017-06-28 03:47:43','0',NULL,NULL),(170,'1498621924','/data/IN_Photo/2/slr/1498621924.png',1,3,'2017-06-28 03:52:20','0',NULL,NULL),(171,'1498622076','/data/IN_Photo/2/slr/1498622076.png',1,3,'2017-06-28 03:54:54','0',NULL,NULL),(172,'1498622202','/data/IN_Photo/2/slr/1498622202.png',1,3,'2017-06-28 03:57:03','0',NULL,NULL),(173,'1498622259','/data/IN_Photo/2/slr/1498622259.png',1,3,'2017-06-28 03:57:57','0',NULL,NULL),(174,'1498622362','/data/IN_Photo/2/slr/1498622362.png',1,3,'2017-06-28 03:59:41','0',NULL,NULL),(175,'1498622977','/data/IN_Photo/2/slr/1498622977.png',1,3,'2017-06-28 04:09:53','0',NULL,NULL),(176,'1498623254','/data/IN_Photo/2/slr/1498623254.png',1,3,'2017-06-28 04:14:33','0',NULL,NULL),(177,'1498623474','/data/IN_Photo/2/slr/1498623474.png',1,3,'2017-06-28 04:18:09','0',NULL,NULL),(178,'1498623624','/data/IN_Photo/2/slr/1498623624.png',1,3,'2017-06-28 04:20:42','0',NULL,NULL),(179,'1498624945','/data/IN_Photo/2/slr/1498624945.png',1,3,'2017-06-28 04:42:42','0',NULL,NULL),(180,'1498627142','/data/IN_Photo/2/slr/1498627142.png',1,3,'2017-06-28 05:19:20','0',NULL,NULL),(181,'1498627263','/data/IN_Photo/2/slr/1498627263.png',1,3,'2017-06-28 05:21:19','0',NULL,NULL),(182,'1498627491','/data/IN_Photo/2/slr/1498627491.png',1,3,'2017-06-28 05:25:09','0',NULL,NULL),(183,'1498628158','/data/IN_Photo/2/slr/1498628158.png',1,3,'2017-06-28 05:36:15','0',NULL,NULL),(184,'1498628982','/data/IN_Photo/2/slr/1498628982.png',1,3,'2017-06-28 05:49:58','0',NULL,NULL),(185,'1498629078','/data/IN_Photo/2/slr/1498629078.png',1,3,'2017-06-28 05:51:43','0',NULL,NULL),(186,'1498629207','/data/IN_Photo/2/slr/1498629207.png',1,3,'2017-06-28 05:53:46','0',NULL,NULL),(187,'1498629281','/data/IN_Photo/2/slr/1498629281.png',1,3,'2017-06-28 05:54:59','0',NULL,NULL),(188,'1498629446','/data/IN_Photo/2/slr/1498629446.png',1,3,'2017-06-28 05:57:42','0',NULL,NULL),(189,'1498629547','/data/IN_Photo/2/slr/1498629547.png',1,3,'2017-06-28 05:59:25','0',NULL,NULL),(190,'1498629702','/data/IN_Photo/2/slr/1498629702.png',1,3,'2017-06-28 06:01:58','0',NULL,NULL),(191,'1498629860','/data/IN_Photo/2/slr/1498629860.png',1,3,'2017-06-28 06:04:38','0',NULL,NULL),(192,'1498631216','/data/IN_Photo/2/slr/1498631216.png',1,3,'2017-06-28 06:27:13','0',NULL,NULL),(193,'1498631260','/data/IN_Photo/2/slr/1498631260.png',1,3,'2017-06-28 06:27:56','0',NULL,NULL),(194,'1498632317','/data/IN_Photo/2/slr/1498632317.png',1,3,'2017-06-28 06:45:35','0',NULL,NULL),(195,'1498633029','/data/IN_Photo/2/slr/1498633029.png',1,3,'2017-06-28 06:57:31','0',NULL,NULL),(196,'1498637583','/data/IN_Photo/2/slr/1498637583.png',1,3,'2017-06-28 08:13:21','0',NULL,NULL),(197,'1498638242','/data/IN_Photo/2/slr/1498638242.png',1,3,'2017-06-28 08:24:19','0',NULL,NULL),(198,'1498638357','/data/IN_Photo/2/slr/1498638357.png',1,3,'2017-06-28 08:26:29','0',NULL,NULL),(199,'1498638422','/data/IN_Photo/2/slr/1498638422.png',1,3,'2017-06-28 08:27:19','0',NULL,NULL),(200,'1498638544','/data/IN_Photo/2/slr/1498638544.png',1,3,'2017-06-28 08:29:20','0',NULL,NULL),(201,'1498697548','/data/IN_Photo/2/slr/1498697548.png',1,3,'2017-06-29 00:52:48','0',NULL,NULL),(202,'1498697592','/data/IN_Photo/2/slr/1498697592.png',1,3,'2017-06-29 00:53:28','0',NULL,NULL),(203,'1498699468','/data/IN_Photo/2/slr/1498699468.png',1,3,'2017-06-29 01:24:47','0',NULL,NULL),(204,'1498699528','/data/IN_Photo/2/slr/1498699528.png',1,3,'2017-06-29 01:25:48','0',NULL,NULL),(205,'1498699744','/data/IN_Photo/2/slr/1498699744.png',1,3,'2017-06-29 01:29:17','0',NULL,NULL),(206,'1498701038','/data/IN_Photo/2/slr/1498701038.png',1,3,'2017-06-29 01:50:58','0',NULL,NULL),(207,'1498702596','/data/IN_Photo/2/slr/1498702596.png',1,3,'2017-06-29 02:16:53','0',NULL,NULL),(208,'1498703793','/data/IN_Photo/2/slr/1498703793.png',1,3,'2017-06-29 02:36:49','0',NULL,NULL),(209,'1498705830','G:\\IN_Photo\\2\\gif2\\1498705830.gif',1,1,'2017-06-29 03:13:03','1','2017-06-29 03:16:09','2017-07-29 03:16:09'),(210,'1498705940','/data/IN_Photo/2/gif2/1498705940.gif',1,1,'2017-06-29 03:14:53','0',NULL,NULL),(211,'1498707112','/data/IN_Photo/2/slr/1498707112.png',1,3,'2017-06-29 03:32:15','0',NULL,NULL),(212,'1498707285','G:\\IN_Photo/2/gif2/1498707285.gif',1,1,'2017-06-29 03:37:18','1','2017-06-29 03:43:24','2017-07-29 03:43:24'),(213,'1498707422','G:\\IN_Photo/2/gif2/1498707422.gif',1,1,'2017-06-29 03:39:34','1','2017-06-29 03:43:24','2017-07-29 03:43:24'),(214,'1186273339','G:\\IN_Photo/2/gif2/1186273339.gif',1,1,'2017-06-29 03:42:17','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(215,'6732638293','G:\\IN_Photo/2/gif2/6732638293.gif',1,1,'2017-06-29 03:42:18','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(216,'3995758475','G:\\IN_Photo/2/gif2/3995758475.gif',1,1,'2017-06-29 03:42:18','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(217,'1806697209','G:\\IN_Photo/2/gif2/1806697209.gif',1,1,'2017-06-29 03:42:18','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(218,'9008538781','G:\\IN_Photo/2/gif2/9008538781.gif',1,1,'2017-06-29 03:42:20','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(219,'4617432049','G:\\IN_Photo/2/gif2/4617432049.gif',1,1,'2017-06-29 03:42:20','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(220,'8841211921','G:\\IN_Photo/2/gif2/8841211921.gif',1,1,'2017-06-29 03:42:20','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(221,'8598578430','G:\\IN_Photo/2/gif2/8598578430.gif',1,1,'2017-06-29 03:42:21','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(222,'3217565459','G:\\IN_Photo/2/gif2/3217565459.gif',1,1,'2017-06-29 03:42:21','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(223,'3274038468','G:\\IN_Photo/2/gif2/3274038468.gif',1,1,'2017-06-29 03:42:21','1','2017-06-29 03:43:16','2017-07-29 03:43:16'),(224,'3530107484','/data/IN_Photo/2/gif2/3530107484.gif',1,1,'2017-06-29 03:44:46','1','2017-06-29 03:44:57','2017-07-29 03:44:57'),(225,'1498708017','/data/IN_Photo/2/slr/1498708017.png',1,3,'2017-06-29 03:47:43','0',NULL,NULL),(226,'1498708099','/data/IN_Photo/2/slr/1498708099.png',1,3,'2017-06-29 03:48:33','0',NULL,NULL),(227,'1498712113','/data/IN_Photo/2/gif2/1498712113.gif',1,1,'2017-06-29 04:57:54','0',NULL,NULL),(228,'1498712912','/data/IN_Photo/2/slr/1498712912.png',1,3,'2017-06-29 05:08:46','0',NULL,NULL),(229,'1498712957','/data/IN_Photo/2/gif2/1498712957.gif',1,1,'2017-06-29 05:11:44','0',NULL,NULL),(230,'1498713179','/data/IN_Photo/2/gif2/1498713179.gif',1,1,'2017-06-29 05:15:34','0',NULL,NULL),(231,'1498713769','/data/IN_Photo/2/gif2/1498713769.gif',1,1,'2017-06-29 05:25:25','0',NULL,NULL),(232,'1498714018','/data/IN_Photo/2/slr/1498714018.png',1,3,'2017-06-29 05:27:27','0',NULL,NULL),(233,'1498714322','/data/IN_Photo/2/slr/1498714322.png',1,3,'2017-06-29 05:32:40','0',NULL,NULL),(234,'1498714371','/data/IN_Photo/2/slr/1498714371.png',1,3,'2017-06-29 05:33:14','0',NULL,NULL),(235,'1498714480','/data/IN_Photo/2/slr/1498714480.png',1,3,'2017-06-29 05:35:09','0',NULL,NULL),(236,'1498714417','/data/IN_Photo/2/gif2/1498714417.gif',1,1,'2017-06-29 05:36:04','0',NULL,NULL),(237,'1498714595','/data/IN_Photo/2/gif2/1498714595.gif',1,1,'2017-06-29 05:39:11','0',NULL,NULL),(238,'1498714741','/data/IN_Photo/2/slr/1498714741.png',1,3,'2017-06-29 05:39:35','0',NULL,NULL),(239,'1498714813','/data/IN_Photo/2/slr/1498714813.png',1,3,'2017-06-29 05:40:29','0',NULL,NULL),(240,'1498718906','/data/IN_Photo/2/slr/1498718906.png',1,3,'2017-06-29 06:48:47','0',NULL,NULL),(241,'1498718952','/data/IN_Photo/2/slr/1498718952.png',1,3,'2017-06-29 06:49:28','0',NULL,NULL),(242,'1498720047','/data/IN_Photo/2/slr/1498720047.png',1,3,'2017-06-29 07:07:59','0',NULL,NULL),(243,'1498720631','/data/IN_Photo/2/slr/1498720631.png',1,3,'2017-06-29 07:17:36','0',NULL,NULL),(244,'1498720791','/data/IN_Photo/2/slr/1498720791.png',1,3,'2017-06-29 07:20:08','0',NULL,NULL),(245,'1498720916','/data/IN_Photo/2/slr/1498720916.png',1,3,'2017-06-29 07:22:20','0',NULL,NULL),(246,'1498721060','/data/IN_Photo/2/slr/1498721060.png',1,3,'2017-06-29 07:24:46','0',NULL,NULL),(247,'1498721218','/data/IN_Photo/2/slr/1498721218.png',1,3,'2017-06-29 07:27:30','0',NULL,NULL),(248,'1498721331','/data/IN_Photo/2/slr/1498721331.png',1,3,'2017-06-29 07:29:06','0',NULL,NULL),(249,'1498721428','/data/IN_Photo/2/slr/1498721428.png',1,3,'2017-06-29 07:30:45','0',NULL,NULL),(250,'1498721567','/data/IN_Photo/2/slr/1498721567.png',1,3,'2017-06-29 07:33:10','0',NULL,NULL),(251,'1498721783','/data/IN_Photo/2/slr/1498721783.png',1,3,'2017-06-29 07:36:44','0',NULL,NULL),(252,'1498721930','/data/IN_Photo/2/slr/1498721930.png',1,3,'2017-06-29 07:39:33','0',NULL,NULL),(253,'1498722019','/data/IN_Photo/2/slr/1498722019.png',1,3,'2017-06-29 07:40:42','0',NULL,NULL),(254,'1498722060','/data/IN_Photo/2/slr/1498722060.png',1,3,'2017-06-29 07:41:44','0',NULL,NULL),(255,'1498722391','/data/IN_Photo/2/slr/1498722391.png',1,3,'2017-06-29 07:47:14','0',NULL,NULL),(256,'1498722484','/data/IN_Photo/2/slr/1498722484.png',1,3,'2017-06-29 07:48:44','0',NULL,NULL),(257,'1498722542','/data/IN_Photo/2/slr/1498722542.png',1,3,'2017-06-29 07:49:26','0',NULL,NULL),(258,'1498722677','/data/IN_Photo/2/slr/1498722677.png',1,3,'2017-06-29 07:51:40','0',NULL,NULL),(259,'1498722717','/data/IN_Photo/2/slr/1498722717.png',1,3,'2017-06-29 07:52:19','0',NULL,NULL),(260,'1498722943','/data/IN_Photo/2/slr/1498722943.png',1,3,'2017-06-29 07:56:06','0',NULL,NULL),(261,'1498723263','/data/IN_Photo/2/slr/1498723263.png',1,3,'2017-06-29 08:01:21','0',NULL,NULL),(262,'1498724585','/data/IN_Photo/2/slr/1498724585.png',1,3,'2017-06-29 08:23:20','0',NULL,NULL),(263,'1498724902','/data/IN_Photo/2/slr/1498724902.png',1,3,'2017-06-29 08:28:41','0',NULL,NULL),(264,'1498724825','/data/IN_Photo/2/gif2/1498724825.gif',1,1,'2017-06-29 08:29:34','0',NULL,NULL),(265,'1498724982','/data/IN_Photo/2/slr/1498724982.png',1,3,'2017-06-29 08:29:57','0',NULL,NULL),(266,'1498724951','/data/IN_Photo/2/gif2/1498724951.gif',1,1,'2017-06-29 08:31:39','0',NULL,NULL),(267,'1498725146','/data/IN_Photo/2/slr/1498725146.png',1,3,'2017-06-29 08:32:42','0',NULL,NULL),(268,'1498725023','/data/IN_Photo/2/gif2/1498725023.gif',1,1,'2017-06-29 08:32:54','0',NULL,NULL),(269,'1498725093','/data/IN_Photo/2/gif2/1498725093.gif',1,1,'2017-06-29 08:34:03','0',NULL,NULL),(270,'1498725176','/data/IN_Photo/2/gif2/1498725176.gif',1,1,'2017-06-29 08:35:27','0',NULL,NULL),(271,'1498725359','/data/IN_Photo/2/gif2/1498725359.gif',1,1,'2017-06-29 08:38:28','0',NULL,NULL),(272,'1498728742','/data/IN_Photo/2/gif2/1498728742.gif',1,1,'2017-06-29 09:34:56','0',NULL,NULL),(273,'1498784954','/data/IN_Photo/2/slr/1498784954.png',1,3,'2017-06-30 01:09:30','0',NULL,NULL),(274,'1498787633','/data/IN_Photo/2/slr/1498787633.png',1,3,'2017-06-30 01:54:22','0',NULL,NULL),(275,'1498799299','/data/IN_Photo/2/gif2/1498799299.gif',1,1,'2017-06-30 05:11:26','0',NULL,NULL),(276,'1498801369','/data/IN_Photo/2/gif2/1498801369.gif',1,1,'2017-06-30 05:45:26','0',NULL,NULL),(277,'1498801639','/data/IN_Photo/2/gif2/1498801639.gif',1,1,'2017-06-30 05:49:52','0',NULL,NULL),(278,'4619182815','/data/IN_Photo/2/gif2/4619182815.gif',1,1,'2017-06-30 05:59:34','0',NULL,NULL),(279,'0569638770','/data/IN_Photo/2/gif2/0569638770.gif',1,1,'2017-06-30 06:18:08','1','2017-06-30 06:18:25','2017-07-30 06:18:25'),(280,'1498805446','/data/IN_Photo/2/slr/1498805446.png',1,3,'2017-06-30 06:51:05','0',NULL,NULL),(281,'1498806448','/data/IN_Photo/2/slr/1498806448.png',1,3,'2017-06-30 07:07:45','0',NULL,NULL),(282,'1499143834','/data/IN_Photo/2/gif2/1499143834.gif',1,1,'2017-07-04 04:53:17','0',NULL,NULL),(283,'1499221657','/data/IN_Photo/2/slr/1499221657.png',1,3,'2017-07-05 02:28:05','0',NULL,NULL),(284,'1499310981','/data/IN_Photo/2/gif2/1499310981.gif',1,1,'2017-07-06 03:19:07','0',NULL,NULL),(285,'1499311555','/data/IN_Photo/2/gif2/1499311555.gif',1,1,'2017-07-06 03:28:38','0',NULL,NULL),(286,'1499411954','/data/IN_Photo/2/gif2/1499411954.gif',1,1,'2017-07-07 07:21:59','0',NULL,NULL),(287,'1499412660','/data/IN_Photo/2/gif2/1499412660.gif',1,1,'2017-07-07 07:31:29','0',NULL,NULL),(288,'1499412730','/data/IN_Photo/2/gif2/1499412730.gif',1,1,'2017-07-07 07:32:39','0',NULL,NULL),(289,'1499412799','/data/IN_Photo/2/gif2/1499412799.gif',1,1,'2017-07-07 07:34:02','0',NULL,NULL),(290,'1499412883','/data/IN_Photo/2/gif2/1499412883.gif',1,1,'2017-07-07 07:35:12','0',NULL,NULL),(291,'1499412955','/data/IN_Photo/2/gif2/1499412955.gif',1,1,'2017-07-07 07:36:25','0',NULL,NULL),(292,'1499413605','/data/IN_Photo/2/gif2/1499413605.gif',1,1,'2017-07-07 07:47:13','0',NULL,NULL),(293,'1499417809','/data/IN_Photo/2/gif2/1499417809.gif',1,1,'2017-07-07 08:59:37','0',NULL,NULL),(294,'1499417876','/data/IN_Photo/2/gif2/1499417876.gif',1,1,'2017-07-07 09:00:48','0',NULL,NULL),(295,'1499827581','/data/IN_Photo/2/slr/1499827581.png',1,3,'2017-07-12 02:49:24','0',NULL,NULL),(296,'1499827662','/data/IN_Photo/2/slr/1499827662.png',1,3,'2017-07-12 02:50:43','0',NULL,NULL),(297,'1499995964','/data/IN_Photo/2/gif2/1499995964.gif',1,1,'2017-07-14 01:37:08','0',NULL,NULL),(298,'1499997724','/data/IN_Photo/2/gif2/1499997724.gif',1,1,'2017-07-14 02:03:11','0',NULL,NULL),(299,'1500016769','/data/IN_Photo/2/gif2/1500016769.gif',1,1,'2017-07-14 07:20:08','0',NULL,NULL),(300,'1500024399','/data/IN_Photo/2/slr/1500024399.png',1,3,'2017-07-14 09:30:02','0',NULL,NULL),(301,'1500024519','/data/IN_Photo/2/slr/1500024519.png',1,3,'2017-07-14 09:31:41','0',NULL,NULL),(302,'1500024582','/data/IN_Photo/2/slr/1500024582.png',1,3,'2017-07-14 09:32:44','0',NULL,NULL),(303,'1500024671','/data/IN_Photo/2/slr/1500024671.png',1,3,'2017-07-14 09:34:13','0',NULL,NULL),(304,'1500024779','/data/IN_Photo/2/slr/1500024779.png',1,3,'2017-07-14 09:36:00','0',NULL,NULL),(305,'1500024860','/data/IN_Photo/2/slr/1500024860.png',1,3,'2017-07-14 09:37:21','0',NULL,NULL),(306,'1500026362','/data/IN_Photo/2/slr/1500026362.png',1,3,'2017-07-14 10:02:24','0',NULL,NULL),(307,'1500026474','/data/IN_Photo/2/slr/1500026474.png',1,3,'2017-07-14 10:04:16','0',NULL,NULL),(308,'1500027334','/data/IN_Photo/2/slr/1500027334.png',1,3,'2017-07-14 10:18:36','0',NULL,NULL),(309,'1500285794','/data/IN_Photo/2/gif2/1500285794.gif',1,1,'2017-07-17 10:04:02','0',NULL,NULL),(310,'1500285986','/data/IN_Photo/2/gif2/1500285986.gif',1,1,'2017-07-17 10:07:17','0',NULL,NULL),(311,'1500355034','/data/IN_Photo/2/gif2/1500355034.gif',1,1,'2017-07-18 05:20:26','0',NULL,NULL),(312,'1500355424','/data/IN_Photo/2/gif1/1500355424.gif',1,2,'2017-07-18 05:26:58','0',NULL,NULL),(313,'1500356783','/data/IN_Photo/2/gif2/1500356783.gif',1,1,'2017-07-18 05:49:36','0',NULL,NULL),(314,'1500357059','/data/IN_Photo/2/gif2/1500357059.gif',1,1,'2017-07-18 05:54:13','0',NULL,NULL),(315,'1500358635','/data/IN_Photo/2/gif1/1500358635.gif',1,2,'2017-07-18 06:20:29','0',NULL,NULL),(316,'1500358732','/data/IN_Photo/2/gif1/1500358732.gif',1,2,'2017-07-18 06:22:07','0',NULL,NULL),(317,'1500358866','/data/IN_Photo/2/gif2/1500358866.gif',1,1,'2017-07-18 06:24:19','0',NULL,NULL),(318,'1500535643','/data/IN_Photo/2/slr/1500535643.png',1,3,'2017-07-20 07:30:44','0',NULL,NULL),(319,'1500552576','/data/IN_Photo/2/gif2/1500552576.gif',1,1,'2017-07-20 12:07:05','0',NULL,NULL),(320,'1500558641','/data/IN_Photo/2/slr/1500558641.png',1,3,'2017-07-20 13:51:35','0',NULL,NULL),(321,'1500558702','/data/IN_Photo/2/slr/1500558702.png',1,3,'2017-07-20 13:53:45','0',NULL,NULL),(322,'1500558800','/data/IN_Photo/2/slr/1500558800.png',1,3,'2017-07-20 13:54:19','0',NULL,NULL),(323,'1500558846','/data/IN_Photo/2/slr/1500558846.png',1,3,'2017-07-20 13:55:01','0',NULL,NULL),(324,'1500558903','/data/IN_Photo/2/slr/1500558903.png',1,3,'2017-07-20 13:55:57','0',NULL,NULL),(325,'1500603934','/data/IN_Photo/2/gif2/1500603934.gif',1,1,'2017-07-21 02:22:58','0',NULL,NULL),(326,'1500604118','/data/IN_Photo/2/gif2/1500604118.gif',1,1,'2017-07-21 02:26:03','0',NULL,NULL),(327,'1500603971','/data/IN_Photo/2/slr/1500603971.png',1,3,'2017-07-21 02:27:58','0',NULL,NULL),(328,'1500604263','/data/IN_Photo/2/slr/1500604263.png',1,3,'2017-07-21 02:32:00','0',NULL,NULL),(329,'1500605210','/data/IN_Photo/2/gif2/1500605210.gif',1,1,'2017-07-21 02:44:17','0',NULL,NULL),(330,'1500605524','/data/IN_Photo/2/gif1/1500605524.gif',1,2,'2017-07-21 02:49:28','0',NULL,NULL),(331,'1500605878','/data/IN_Photo/2/gif1/1500605878.gif',1,2,'2017-07-21 02:55:23','0',NULL,NULL),(332,'1500630376','/data/IN_Photo/2/slr/1500630376.png',1,3,'2017-07-21 09:47:11','0',NULL,NULL),(333,'1500630787','/data/IN_Photo/2/slr/1500630787.png',1,3,'2017-07-21 09:54:01','0',NULL,NULL),(334,'1500631205','/data/IN_Photo/2/slr/1500631205.png',1,3,'2017-07-21 10:01:04','0',NULL,NULL),(335,'1500631821','/data/IN_Photo/2/slr/1500631821.png',1,3,'2017-07-21 10:11:21','0',NULL,NULL),(336,'1500632529','/data/IN_Photo/2/slr/1500632529.png',1,3,'2017-07-21 10:23:06','0',NULL,NULL),(337,'1500632757','/data/IN_Photo/2/slr/1500632757.png',1,3,'2017-07-21 10:26:53','0',NULL,NULL),(338,'1500632886','/data/IN_Photo/2/slr/1500632886.png',1,3,'2017-07-21 10:29:01','0',NULL,NULL),(339,'1500632944','/data/IN_Photo/2/slr/1500632944.png',1,3,'2017-07-21 10:29:59','0',NULL,NULL),(340,'1500634106','/data/IN_Photo/2/slr/1500634106.png',1,3,'2017-07-21 10:49:24','0',NULL,NULL),(341,'1500634209','/data/IN_Photo/2/slr/1500634209.png',1,3,'2017-07-21 10:51:07','0',NULL,NULL),(342,'1500634362','/data/IN_Photo/2/slr/1500634362.png',1,3,'2017-07-21 10:53:39','0',NULL,NULL),(343,'1500634393','/data/IN_Photo/2/slr/1500634393.png',1,3,'2017-07-21 10:54:11','0',NULL,NULL),(344,'1500634768','/data/IN_Photo/2/gif1/1500634768.gif',1,2,'2017-07-21 10:56:55','0',NULL,NULL),(345,'1500634918','/data/IN_Photo/2/gif1/1500634918.gif',1,2,'2017-07-21 10:59:28','0',NULL,NULL),(346,'1500634907','/data/IN_Photo/2/slr/1500634907.png',1,3,'2017-07-21 11:02:44','0',NULL,NULL),(347,'1500635469','/data/IN_Photo/2/gif1/1500635469.gif',1,2,'2017-07-21 11:08:31','0',NULL,NULL),(348,'1500635643','/data/IN_Photo/2/slr/1500635643.png',1,3,'2017-07-21 11:15:20','0',NULL,NULL),(349,'1500635818','/data/IN_Photo/2/slr/1500635818.png',1,3,'2017-07-21 11:17:54','0',NULL,NULL),(350,'1500636128','/data/IN_Photo/2/gif1/1500636128.gif',1,2,'2017-07-21 11:19:30','0',NULL,NULL),(351,'1500636070','/data/IN_Photo/2/slr/1500636070.png',1,3,'2017-07-21 11:22:06','0',NULL,NULL),(352,'1500636592','/data/IN_Photo/2/gif1/1500636592.gif',1,2,'2017-07-21 11:27:15','0',NULL,NULL),(353,'1500636390','/data/IN_Photo/2/slr/1500636390.png',1,3,'2017-07-21 11:27:27','0',NULL,NULL),(354,'1500636519','/data/IN_Photo/2/slr/1500636519.png',1,3,'2017-07-21 11:29:37','0',NULL,NULL),(355,'1500636736','/data/IN_Photo/2/slr/1500636736.png',1,3,'2017-07-21 11:33:23','0',NULL,NULL),(356,'1500636869','/data/IN_Photo/2/slr/1500636869.png',1,3,'2017-07-21 11:35:26','0',NULL,NULL),(357,'1500637132','/data/IN_Photo/2/gif1/1500637132.gif',1,2,'2017-07-21 11:36:19','0',NULL,NULL),(358,'1500637071','/data/IN_Photo/2/slr/1500637071.png',1,3,'2017-07-21 11:38:53','0',NULL,NULL),(359,'1500637295','/data/IN_Photo/2/slr/1500637295.png',1,3,'2017-07-21 11:42:33','0',NULL,NULL),(360,'1500637519','/data/IN_Photo/2/gif1/1500637519.gif',1,2,'2017-07-21 11:42:48','0',NULL,NULL),(361,'1500637550','/data/IN_Photo/2/slr/1500637550.png',1,3,'2017-07-21 11:47:05','0',NULL,NULL),(362,'1500637666','/data/IN_Photo/2/slr/1500637666.png',1,3,'2017-07-21 11:48:48','0',NULL,NULL),(363,'1500638387','/data/IN_Photo/2/slr/1500638387.png',1,3,'2017-07-21 12:00:46','0',NULL,NULL),(364,'1500643473','/data/IN_Photo/2/slr/1500643473.png',1,3,'2017-07-21 13:25:33','0',NULL,NULL),(365,'1500643758','/data/IN_Photo/2/gif1/1500643758.gif',1,2,'2017-07-21 13:26:45','0',NULL,NULL),(366,'1500643980','/data/IN_Photo/2/gif1/1500643980.gif',1,2,'2017-07-21 13:30:22','0',NULL,NULL),(367,'1500644118','/data/IN_Photo/2/gif1/1500644118.gif',1,2,'2017-07-21 13:33:35','0',NULL,NULL),(368,'1500644458','/data/IN_Photo/2/gif1/1500644458.gif',1,2,'2017-07-21 13:38:22','0',NULL,NULL),(369,'1500644919','/data/IN_Photo/2/slr/1500644919.png',1,3,'2017-07-21 13:49:38','0',NULL,NULL),(370,'1500645075','/data/IN_Photo/2/slr/1500645075.png',1,3,'2017-07-21 13:52:21','0',NULL,NULL),(371,'1500863348','/data/IN_Photo/2/slr/1500863348.png',1,3,'2017-07-24 02:30:11','0',NULL,NULL),(372,'1501124845','/data/IN_Photo/2/slr/1501124845.png',1,3,'2017-07-27 03:06:39','0',NULL,NULL),(373,'1501125027','/data/IN_Photo/2/slr/1501125027.png',1,3,'2017-07-27 03:09:40','0',NULL,NULL),(374,'1501125164','/data/IN_Photo/2/slr/1501125164.png',1,3,'2017-07-27 03:11:57','0',NULL,NULL),(375,'1501125366','/data/IN_Photo/2/slr/1501125366.png',1,3,'2017-07-27 03:15:21','0',NULL,NULL),(376,'1501125443','/data/IN_Photo/2/slr/1501125443.png',1,3,'2017-07-27 03:16:37','0',NULL,NULL),(377,'1501125518','/data/IN_Photo/2/slr/1501125518.png',1,3,'2017-07-27 03:17:52','0',NULL,NULL),(378,'1501125584','/data/IN_Photo/2/slr/1501125584.png',1,3,'2017-07-27 03:18:59','0',NULL,NULL),(379,'1501126067','/data/IN_Photo/2/slr/1501126067.png',1,3,'2017-07-27 03:27:05','0',NULL,NULL),(380,'1501134969','/data/IN_Photo/2/slr/1501134969.png',1,3,'2017-07-27 05:55:25','0',NULL,NULL),(381,'1501135072','/data/IN_Photo/2/slr/1501135072.png',1,3,'2017-07-27 05:57:08','0',NULL,NULL),(382,'1501135856','/data/IN_Photo/2/slr/1501135856.png',1,3,'2017-07-27 06:10:11','0',NULL,NULL),(383,'1501136156','/data/IN_Photo/2/slr/1501136156.png',1,3,'2017-07-27 06:15:10','0',NULL,NULL),(384,'1501136246','/data/IN_Photo/2/slr/1501136246.png',1,3,'2017-07-27 06:16:41','0',NULL,NULL),(385,'1501136623','/data/IN_Photo/2/slr/1501136623.png',1,3,'2017-07-27 06:22:56','0',NULL,NULL),(386,'1501136894','/data/IN_Photo/2/slr/1501136894.png',1,3,'2017-07-27 06:27:31','0',NULL,NULL),(387,'1501137003','/data/IN_Photo/2/slr/1501137003.png',1,3,'2017-07-27 06:29:20','0',NULL,NULL),(388,'1501139672','/data/IN_Photo/2/slr/1501139672.png',1,3,'2017-07-27 07:14:11','0',NULL,NULL),(389,'1501139869','/data/IN_Photo/2/slr/1501139869.png',1,3,'2017-07-27 07:17:04','0',NULL,NULL),(390,'1501139984','/data/IN_Photo/2/slr/1501139984.png',1,3,'2017-07-27 07:18:57','0',NULL,NULL),(391,'1501140079','/data/IN_Photo/2/slr/1501140079.png',1,3,'2017-07-27 07:20:36','0',NULL,NULL),(392,'1501140200','/data/IN_Photo/2/slr/1501140200.png',1,3,'2017-07-27 07:22:35','0',NULL,NULL),(393,'1501140277','/data/IN_Photo/2/slr/1501140277.png',1,3,'2017-07-27 07:23:50','0',NULL,NULL),(394,'1501140492','/data/IN_Photo/2/slr/1501140492.png',1,3,'2017-07-27 07:27:27','0',NULL,NULL),(395,'1501141426','/data/IN_Photo/2/slr/1501141426.png',1,3,'2017-07-27 07:43:01','0',NULL,NULL),(396,'1501141647','/data/IN_Photo/2/slr/1501141647.png',1,3,'2017-07-27 07:46:41','0',NULL,NULL),(397,'1501143122','/data/IN_Photo/2/slr/1501143122.png',1,3,'2017-07-27 08:13:09','0',NULL,NULL),(398,'1501144831','/data/IN_Photo/2/slr/1501144831.png',1,3,'2017-07-27 08:41:56','0',NULL,NULL),(399,'1501221736','/data/IN_Photo/2/slr/1501221736.png',1,3,'2017-07-28 06:03:37','0',NULL,NULL),(400,'1501221989','/data/IN_Photo/2/slr/1501221989.png',1,3,'2017-07-28 06:08:25','0',NULL,NULL),(401,'1501222799','/data/IN_Photo/2/slr/1501222799.png',1,3,'2017-07-28 06:21:52','0',NULL,NULL),(402,'1501223189','/data/IN_Photo/2/slr/1501223189.png',1,3,'2017-07-28 06:28:27','0',NULL,NULL),(403,'1501225335','/data/IN_Photo/2/slr/1501225335.png',1,3,'2017-07-28 07:03:52','0',NULL,NULL),(404,'1501225855','/data/IN_Photo/2/slr/1501225855.png',1,3,'2017-07-28 07:12:31','0',NULL,NULL),(405,'1501225926','/data/IN_Photo/2/slr/1501225926.png',1,3,'2017-07-28 07:13:42','0',NULL,NULL),(406,'1501226029','/data/IN_Photo/2/slr/1501226029.png',1,3,'2017-07-28 07:15:24','0',NULL,NULL),(407,'1501227464','/data/IN_Photo/2/slr/1501227464.png',1,3,'2017-07-28 07:37:00','0',NULL,NULL),(408,'1501227682','/data/IN_Photo/2/slr/1501227682.png',1,3,'2017-07-28 07:40:38','0',NULL,NULL),(409,'1501228709','/data/IN_Photo/2/slr/1501228709.png',1,3,'2017-07-28 07:57:45','0',NULL,NULL),(410,'1501228972','/data/IN_Photo/2/slr/1501228972.png',1,3,'2017-07-28 08:02:06','0',NULL,NULL),(411,'1501228882','/data/IN_Photo/2/slr/1501228882.png',1,3,'2017-07-28 08:02:59','0',NULL,NULL),(412,'1501228955','/data/IN_Photo/2/slr/1501228955.png',1,3,'2017-07-28 08:04:12','0',NULL,NULL),(413,'1501229080','/data/IN_Photo/2/slr/1501229080.png',1,3,'2017-07-28 08:06:15','0',NULL,NULL),(414,'1501229185','/data/IN_Photo/2/slr/1501229185.png',1,3,'2017-07-28 08:08:08','0',NULL,NULL),(415,'1501229574','/data/IN_Photo/2/slr/1501229574.png',1,3,'2017-07-28 08:14:33','0',NULL,NULL),(416,'1501229793','/data/IN_Photo/2/slr/1501229793.png',1,3,'2017-07-28 08:18:10','0',NULL,NULL),(417,'1501253527','/data/IN_Photo/2/slr/1501253527.png',1,3,'2017-07-28 14:51:20','0',NULL,NULL),(418,'1501309038','/data/IN_Photo/2/slr/1501309038.png',1,3,'2017-07-29 06:19:24','0',NULL,NULL),(419,'1501309478','/data/IN_Photo/2/slr/1501309478.png',1,3,'2017-07-29 06:26:26','0',NULL,NULL),(420,'1501309807','/data/IN_Photo/2/slr/1501309807.png',1,3,'2017-07-29 06:31:52','0',NULL,NULL),(421,'1501312281','/data/IN_Photo/2/slr/1501312281.png',1,3,'2017-07-29 07:13:32','0',NULL,NULL),(422,'1501312415','/data/IN_Photo/2/slr/1501312415.png',1,3,'2017-07-29 07:15:56','0',NULL,NULL),(423,'1501312609','/data/IN_Photo/2/slr/1501312609.png',1,3,'2017-07-29 07:18:52','0',NULL,NULL),(424,'1501312863','/data/IN_Photo/2/slr/1501312863.png',1,3,'2017-07-29 07:22:40','0',NULL,NULL),(425,'1501313018','/data/IN_Photo/2/slr/1501313018.png',1,3,'2017-07-29 07:25:16','0',NULL,NULL),(426,'1501313089','/data/IN_Photo/2/slr/1501313089.png',1,3,'2017-07-29 07:26:27','0',NULL,NULL),(427,'1501313122','/data/IN_Photo/2/slr/1501313122.png',1,3,'2017-07-29 07:27:01','0',NULL,NULL),(428,'1501313211','/data/IN_Photo/2/slr/1501313211.png',1,3,'2017-07-29 07:28:30','0',NULL,NULL),(429,'1501313303','/data/IN_Photo/2/slr/1501313303.png',1,3,'2017-07-29 07:30:09','0',NULL,NULL),(430,'1501318358','/data/IN_Photo/2/slr/1501318358.png',1,3,'2017-07-29 08:54:21','0',NULL,NULL),(431,'1501322075','/data/IN_Photo/2/slr/1501322075.png',1,3,'2017-07-29 09:56:19','0',NULL,NULL),(432,'1501324122','/data/IN_Photo/2/slr/1501324122.png',1,3,'2017-07-29 10:30:23','0',NULL,NULL),(433,'1501324233','/data/IN_Photo/2/slr/1501324233.png',1,3,'2017-07-29 10:32:15','0',NULL,NULL),(434,'1501324448','/data/IN_Photo/2/slr/1501324448.png',1,3,'2017-07-29 10:35:48','0',NULL,NULL),(435,'1501325941','/data/IN_Photo/2/slr/1501325941.png',1,3,'2017-07-29 11:00:48','0',NULL,NULL),(436,'1501326581','/data/IN_Photo/2/slr/1501326581.png',1,3,'2017-07-29 11:11:22','0',NULL,NULL),(437,'1501326707','/data/IN_Photo/2/slr/1501326707.png',1,3,'2017-07-29 11:13:27','0',NULL,NULL),(438,'1501328330','/data/IN_Photo/2/slr/1501328330.png',1,3,'2017-07-29 11:40:36','0',NULL,NULL),(439,'1501328441','/data/IN_Photo/2/slr/1501328441.png',1,3,'2017-07-29 11:42:35','0',NULL,NULL),(440,'1501328592','/data/IN_Photo/2/slr/1501328592.png',1,3,'2017-07-29 11:44:52','0',NULL,NULL),(441,'1501328627','/data/IN_Photo/2/slr/1501328627.png',1,3,'2017-07-29 11:45:33','0',NULL,NULL),(442,'1501328712','/data/IN_Photo/2/slr/1501328712.png',1,3,'2017-07-29 11:46:56','0',NULL,NULL),(443,'1501328881','/data/IN_Photo/2/slr/1501328881.png',1,3,'2017-07-29 11:49:40','0',NULL,NULL),(444,'1501328974','/data/IN_Photo/2/slr/1501328974.png',1,3,'2017-07-29 11:51:16','0',NULL,NULL),(445,'1501329050','/data/IN_Photo/2/slr/1501329050.png',1,3,'2017-07-29 11:52:33','0',NULL,NULL),(446,'1501329126','/data/IN_Photo/2/slr/1501329126.png',1,3,'2017-07-29 11:53:48','0',NULL,NULL),(447,'1501329205','/data/IN_Photo/2/slr/1501329205.png',1,3,'2017-07-29 11:55:10','0',NULL,NULL),(448,'1501329394','/data/IN_Photo/2/slr/1501329394.png',1,3,'2017-07-29 11:58:12','0',NULL,NULL),(449,'1501329490','/data/IN_Photo/2/slr/1501329490.png',1,3,'2017-07-29 11:59:50','0',NULL,NULL),(450,'1501330377','/data/IN_Photo/2/slr/1501330377.png',1,3,'2017-07-29 12:14:43','0',NULL,NULL),(451,'1501330480','/data/IN_Photo/2/slr/1501330480.png',1,3,'2017-07-29 12:16:19','0',NULL,NULL),(452,'1501330553','/data/IN_Photo/2/slr/1501330553.png',1,3,'2017-07-29 12:17:34','0',NULL,NULL),(453,'1501330659','/data/IN_Photo/2/slr/1501330659.png',1,3,'2017-07-29 12:19:17','0',NULL,NULL),(454,'1501330738','/data/IN_Photo/2/slr/1501330738.png',1,3,'2017-07-29 12:20:35','0',NULL,NULL),(455,'1501474604','/data/IN_Photo/2/slr/1501474604.png',1,3,'2017-07-31 04:15:57','0',NULL,NULL),(456,'1501479586','/data/IN_Photo/2/slr/1501479586.png',1,3,'2017-07-31 05:40:35','0',NULL,NULL),(457,'1501482633','/data/IN_Photo/2/slr/1501482633.png',1,3,'2017-07-31 06:29:45','0',NULL,NULL),(458,'1501483205','/data/IN_Photo/2/slr/1501483205.png',1,3,'2017-07-31 06:39:19','0',NULL,NULL),(459,'1501483489','/data/IN_Photo/2/slr/1501483489.png',1,3,'2017-07-31 06:45:58','0',NULL,NULL),(460,'1501483695','/data/IN_Photo/2/slr/1501483695.png',1,3,'2017-07-31 06:47:30','0',NULL,NULL),(461,'1501483612','/data/IN_Photo/2/slr/1501483612.png',1,3,'2017-07-31 06:47:30','0',NULL,NULL),(462,'1501483825','/data/IN_Photo/2/slr/1501483825.png',1,3,'2017-07-31 06:49:35','0',NULL,NULL),(463,'1501484209','/data/IN_Photo/2/slr/1501484209.png',1,3,'2017-07-31 06:57:44','0',NULL,NULL),(464,'1501485015','/data/IN_Photo/2/slr/1501485015.png',1,3,'2017-07-31 07:09:30','0',NULL,NULL),(465,'1501485448','/data/IN_Photo/2/slr/1501485448.png',1,3,'2017-07-31 07:16:40','0',NULL,NULL),(466,'1501489955','/data/IN_Photo/2/slr/1501489955.png',1,3,'2017-07-31 08:33:07','0',NULL,NULL),(467,'1501491166','/data/IN_Photo/2/slr/1501491166.png',1,3,'2017-07-31 08:53:24','0',NULL,NULL),(468,'1501491748','/data/IN_Photo/2/slr/1501491748.png',1,3,'2017-07-31 09:01:39','0',NULL,NULL),(469,'1501491881','/data/IN_Photo/2/slr/1501491881.png',1,3,'2017-07-31 09:06:30','0',NULL,NULL),(470,'1501492034','/data/IN_Photo/2/slr/1501492034.png',1,3,'2017-07-31 09:07:58','0',NULL,NULL),(471,'1501492151','/data/IN_Photo/2/slr/1501492151.png',1,3,'2017-07-31 09:09:43','0',NULL,NULL),(472,'1501492464','/data/IN_Photo/2/slr/1501492464.png',1,3,'2017-07-31 09:13:56','0',NULL,NULL),(473,'1501492608','/data/IN_Photo/2/slr/1501492608.png',1,3,'2017-07-31 09:15:59','0',NULL,NULL),(474,'1501492784','/data/IN_Photo/2/slr/1501492784.png',1,3,'2017-07-31 09:18:56','0',NULL,NULL),(475,'1501492933','/data/IN_Photo/2/slr/1501492933.png',1,3,'2017-07-31 09:21:25','0',NULL,NULL),(476,'1501493055','/data/IN_Photo/2/slr/1501493055.png',1,3,'2017-07-31 09:23:28','0',NULL,NULL),(477,'1501493217','/data/IN_Photo/2/slr/1501493217.png',1,3,'2017-07-31 09:26:09','0',NULL,NULL),(478,'1501493309','/data/IN_Photo/2/slr/1501493309.png',1,3,'2017-07-31 09:27:41','0',NULL,NULL),(479,'1501493386','/data/IN_Photo/2/slr/1501493386.png',1,3,'2017-07-31 09:28:58','0',NULL,NULL),(480,'1501493470','/data/IN_Photo/2/slr/1501493470.png',1,3,'2017-07-31 09:30:20','0',NULL,NULL),(481,'1501494144','/data/IN_Photo/2/slr/1501494144.png',1,3,'2017-07-31 09:41:35','0',NULL,NULL),(482,'1501494378','/data/IN_Photo/2/slr/1501494378.png',1,3,'2017-07-31 09:45:32','0',NULL,NULL),(483,'1501494787','/data/IN_Photo/2/slr/1501494787.png',1,3,'2017-07-31 09:53:40','0',NULL,NULL),(484,'1501495330','/data/IN_Photo/2/slr/1501495330.png',1,3,'2017-07-31 10:02:48','0',NULL,NULL),(485,'1501495358','/data/IN_Photo/2/slr/1501495358.png',1,3,'2017-07-31 10:03:09','0',NULL,NULL),(486,'1501495376','/data/IN_Photo/2/slr/1501495376.png',1,3,'2017-07-31 10:03:28','0',NULL,NULL),(487,'1501495395','/data/IN_Photo/2/slr/1501495395.png',1,3,'2017-07-31 10:03:47','0',NULL,NULL),(488,'1501495415','/data/IN_Photo/2/slr/1501495415.png',1,3,'2017-07-31 10:04:07','0',NULL,NULL),(489,'1501549493','/data/IN_Photo/2/slr/1501549493.png',1,3,'2017-08-01 01:05:32','0',NULL,NULL),(490,'1501550645','/data/IN_Photo/2/slr/1501550645.png',1,3,'2017-08-01 01:24:42','0',NULL,NULL),(491,'1501580468','/data/IN_Photo/2/slr/1501580468.png',1,3,'2017-08-01 09:41:52','0',NULL,NULL),(492,'1501580562','/data/IN_Photo/2/slr/1501580562.png',1,3,'2017-08-01 09:43:18','0',NULL,NULL),(493,'1501580648','/data/IN_Photo/2/slr/1501580648.png',1,3,'2017-08-01 09:45:10','0',NULL,NULL),(494,'1501580706','/data/IN_Photo/2/slr/1501580706.png',1,3,'2017-08-01 09:45:41','0',NULL,NULL),(495,'1501642782','/data/IN_Photo/2/slr/1501642782.png',1,3,'2017-08-02 02:58:51','0',NULL,NULL),(496,'1501642862','/data/IN_Photo/2/slr/1501642862.png',1,3,'2017-08-02 03:00:12','0',NULL,NULL),(497,'1501642966','/data/IN_Photo/2/slr/1501642966.png',1,3,'2017-08-02 03:01:55','0',NULL,NULL),(498,'1501643040','/data/IN_Photo/2/slr/1501643040.png',1,3,'2017-08-02 03:03:10','0',NULL,NULL),(499,'1501643172','/data/IN_Photo/2/slr/1501643172.png',1,3,'2017-08-02 03:05:22','0',NULL,NULL),(500,'1501643266','/data/IN_Photo/2/slr/1501643266.png',1,3,'2017-08-02 03:06:55','0',NULL,NULL),(501,'1501643763','/data/IN_Photo/2/slr/1501643763.png',1,3,'2017-08-02 03:15:14','0',NULL,NULL),(502,'1501643871','/data/IN_Photo/2/slr/1501643871.png',1,3,'2017-08-02 03:16:59','0',NULL,NULL),(503,'1501643919','/data/IN_Photo/2/slr/1501643919.png',1,3,'2017-08-02 03:17:47','0',NULL,NULL),(504,'1501643966','/data/IN_Photo/2/slr/1501643966.png',1,3,'2017-08-02 03:18:36','0',NULL,NULL),(505,'1501644265','/data/IN_Photo/2/slr/1501644265.png',1,3,'2017-08-02 03:23:34','0',NULL,NULL),(506,'1501645173','/data/IN_Photo/2/slr/1501645173.png',1,3,'2017-08-02 03:38:42','0',NULL,NULL),(507,'1501645583','/data/IN_Photo/2/slr/1501645583.png',1,3,'2017-08-02 03:45:34','0',NULL,NULL),(508,'1501645510','/data/IN_Photo/2/slr/1501645510.png',1,3,'2017-08-02 03:45:48','0',NULL,NULL),(509,'1501645574','/data/IN_Photo/2/slr/1501645574.png',1,3,'2017-08-02 03:46:50','0',NULL,NULL),(510,'1501645804','/data/IN_Photo/2/slr/1501645804.png',1,3,'2017-08-02 03:50:44','0',NULL,NULL),(511,'1501645965','/data/IN_Photo/2/slr/1501645965.png',1,3,'2017-08-02 03:53:23','0',NULL,NULL),(512,'1501646017','/data/IN_Photo/2/slr/1501646017.png',1,3,'2017-08-02 03:54:17','0',NULL,NULL),(513,'1501646237','/data/IN_Photo/2/slr/1501646237.png',1,3,'2017-08-02 03:57:55','0',NULL,NULL),(514,'1501646667','/data/IN_Photo/2/slr/1501646667.png',1,3,'2017-08-02 04:03:36','0',NULL,NULL),(515,'1501646828','/data/IN_Photo/2/slr/1501646828.png',1,3,'2017-08-02 04:06:18','0',NULL,NULL),(516,'1501646973','/data/IN_Photo/2/slr/1501646973.png',1,3,'2017-08-02 04:08:43','0',NULL,NULL),(517,'1501647226','/data/IN_Photo/2/slr/1501647226.png',1,3,'2017-08-02 04:14:27','0',NULL,NULL),(518,'1501652451','/data/IN_Photo/2/slr/1501652451.png',1,3,'2017-08-02 05:40:01','0',NULL,NULL),(519,'1501652605','/data/IN_Photo/2/slr/1501652605.png',1,3,'2017-08-02 05:42:35','0',NULL,NULL),(520,'1501652816','/data/IN_Photo/2/slr/1501652816.png',1,3,'2017-08-02 05:47:30','0',NULL,NULL),(521,'1501652962','/data/IN_Photo/2/slr/1501652962.png',1,3,'2017-08-02 05:50:00','0',NULL,NULL),(522,'1501662930','/data/IN_Photo/2/slr/1501662930.png',1,3,'2017-08-02 08:36:04','0',NULL,NULL),(523,'1501664874','/data/IN_Photo/2/slr/1501664874.png',1,3,'2017-08-02 09:09:02','0',NULL,NULL),(524,'1501664929','/data/IN_Photo/2/slr/1501664929.png',1,3,'2017-08-02 09:09:51','0',NULL,NULL),(525,'1501665148','/data/IN_Photo/2/slr/1501665148.png',1,3,'2017-08-02 09:13:23','0',NULL,NULL),(526,'1501665199','/data/IN_Photo/2/slr/1501665199.png',1,3,'2017-08-02 09:13:56','0',NULL,NULL),(527,'1501665224','/data/IN_Photo/2/slr/1501665224.png',1,3,'2017-08-02 09:14:22','0',NULL,NULL),(528,'1501665643','/data/IN_Photo/2/slr/1501665643.png',1,3,'2017-08-02 09:21:19','0',NULL,NULL),(529,'1501666483','/data/IN_Photo/2/slr/1501666483.png',1,3,'2017-08-02 09:35:24','0',NULL,NULL),(530,'1501666740','/data/IN_Photo/2/slr/1501666740.png',1,3,'2017-08-02 09:39:40','0',NULL,NULL),(531,'1501723320','/data/IN_Photo/2/slr/1501723320.png',1,3,'2017-08-03 01:22:37','0',NULL,NULL),(532,'1501723344','/data/IN_Photo/2/slr/1501723344.png',1,3,'2017-08-03 01:23:10','0',NULL,NULL),(533,'1501723979','/data/IN_Photo/2/slr/1501723979.png',1,3,'2017-08-03 01:32:07','0',NULL,NULL),(534,'1501724019','/data/IN_Photo/2/slr/1501724019.png',1,3,'2017-08-03 01:32:51','0',NULL,NULL),(535,'1501724046','/data/IN_Photo/2/slr/1501724046.png',1,3,'2017-08-03 01:33:12','0',NULL,NULL),(536,'1501724063','/data/IN_Photo/2/slr/1501724063.png',1,3,'2017-08-03 01:33:34','0',NULL,NULL),(537,'1501724335','/data/IN_Photo/2/slr/1501724335.png',1,3,'2017-08-03 01:38:01','0',NULL,NULL),(538,'1501724712','/data/IN_Photo/2/slr/1501724712.png',1,3,'2017-08-03 01:44:18','0',NULL,NULL),(539,'1501724729','/data/IN_Photo/2/slr/1501724729.png',1,3,'2017-08-03 01:44:38','0',NULL,NULL),(540,'1501724972','/data/IN_Photo/2/slr/1501724972.png',1,3,'2017-08-03 01:48:39','0',NULL,NULL),(541,'1501725778','/data/IN_Photo/2/slr/1501725778.png',1,3,'2017-08-03 02:02:05','0',NULL,NULL),(542,'1501725850','/data/IN_Photo/2/slr/1501725850.png',1,3,'2017-08-03 02:03:17','0',NULL,NULL),(543,'1501726002','/data/IN_Photo/2/slr/1501726002.png',1,3,'2017-08-03 02:05:48','0',NULL,NULL),(544,'1501726198','/data/IN_Photo/2/slr/1501726198.png',1,3,'2017-08-03 02:09:04','0',NULL,NULL),(545,'1501726215','/data/IN_Photo/2/slr/1501726215.png',1,3,'2017-08-03 02:09:24','0',NULL,NULL),(546,'1501726310','/data/IN_Photo/2/slr/1501726310.png',1,3,'2017-08-03 02:11:05','0',NULL,NULL),(547,'1501726337','/data/IN_Photo/2/slr/1501726337.png',1,3,'2017-08-03 02:11:50','0',NULL,NULL),(548,'1501726422','/data/IN_Photo/2/slr/1501726422.png',1,3,'2017-08-03 02:12:49','0',NULL,NULL),(549,'1501726572','/data/IN_Photo/2/slr/1501726572.png',1,3,'2017-08-03 02:15:18','0',NULL,NULL),(550,'1501726633','/data/IN_Photo/2/slr/1501726633.png',1,3,'2017-08-03 02:16:18','0',NULL,NULL),(551,'1501726650','/data/IN_Photo/2/slr/1501726650.png',1,3,'2017-08-03 02:16:57','0',NULL,NULL),(552,'1501726709','/data/IN_Photo/2/slr/1501726709.png',1,3,'2017-08-03 02:17:49','0',NULL,NULL),(553,'1501726741','/data/IN_Photo/2/slr/1501726741.png',1,3,'2017-08-03 02:18:23','0',NULL,NULL),(554,'1501726775','/data/IN_Photo/2/slr/1501726775.png',1,3,'2017-08-03 02:19:23','0',NULL,NULL),(555,'1501726879','/data/IN_Photo/2/slr/1501726879.png',1,3,'2017-08-03 02:20:27','0',NULL,NULL),(556,'1501726978','/data/IN_Photo/2/slr/1501726978.png',1,3,'2017-08-03 02:22:03','0',NULL,NULL),(557,'1501727351','/data/IN_Photo/2/slr/1501727351.png',1,3,'2017-08-03 02:28:24','0',NULL,NULL),(558,'1501727383','/data/IN_Photo/2/slr/1501727383.png',1,3,'2017-08-03 02:28:54','0',NULL,NULL),(559,'1501727970','/data/IN_Photo/2/slr/1501727970.png',1,3,'2017-08-03 02:38:36','0',NULL,NULL),(560,'1501728098','/data/IN_Photo/2/slr/1501728098.png',1,3,'2017-08-03 02:40:43','0',NULL,NULL),(561,'1501728324','/data/IN_Photo/2/slr/1501728324.png',1,3,'2017-08-03 02:44:31','0',NULL,NULL),(562,'1501728443','/data/IN_Photo/2/slr/1501728443.png',1,3,'2017-08-03 02:46:29','0',NULL,NULL),(563,'1501728788','/data/IN_Photo/2/slr/1501728788.png',1,3,'2017-08-03 02:52:14','0',NULL,NULL),(564,'1501728970','/data/IN_Photo/2/slr/1501728970.png',1,3,'2017-08-03 02:55:26','0',NULL,NULL),(565,'1501729207','/data/IN_Photo/2/slr/1501729207.png',1,3,'2017-08-03 02:59:13','0',NULL,NULL),(566,'1501729483','/data/IN_Photo/2/slr/1501729483.png',1,3,'2017-08-03 03:03:51','0',NULL,NULL),(567,'1501729715','/data/IN_Photo/2/slr/1501729715.png',1,3,'2017-08-03 03:07:43','0',NULL,NULL),(568,'1501729743','/data/IN_Photo/2/slr/1501729743.png',1,3,'2017-08-03 03:08:09','0',NULL,NULL),(569,'1501729844','/data/IN_Photo/2/slr/1501729844.png',1,3,'2017-08-03 03:09:54','0',NULL,NULL),(570,'1501729928','/data/IN_Photo/2/slr/1501729928.png',1,3,'2017-08-03 03:11:13','0',NULL,NULL),(571,'1501729945','/data/IN_Photo/2/slr/1501729945.png',1,3,'2017-08-03 03:11:31','0',NULL,NULL),(572,'1501730283','/data/IN_Photo/2/slr/1501730283.png',1,3,'2017-08-03 03:17:08','0',NULL,NULL),(573,'1501730299','/data/IN_Photo/2/slr/1501730299.png',1,3,'2017-08-03 03:17:25','0',NULL,NULL),(574,'1501730364','/data/IN_Photo/2/slr/1501730364.png',1,3,'2017-08-03 03:20:02','0',NULL,NULL),(575,'1501730397','/data/IN_Photo/2/slr/1501730397.png',1,3,'2017-08-03 03:20:34','0',NULL,NULL),(576,'1501730651','/data/IN_Photo/2/slr/1501730651.png',1,3,'2017-08-03 03:24:46','0',NULL,NULL),(577,'1501730772','/data/IN_Photo/2/slr/1501730772.png',1,3,'2017-08-03 03:25:19','0',NULL,NULL),(578,'1501730943','/data/IN_Photo/2/slr/1501730943.png',1,3,'2017-08-03 03:28:10','0',NULL,NULL),(579,'1501730983','/data/IN_Photo/2/slr/1501730983.png',1,3,'2017-08-03 03:28:49','0',NULL,NULL),(580,'1501731102','/data/IN_Photo/2/slr/1501731102.png',1,3,'2017-08-03 03:30:48','0',NULL,NULL),(581,'1501731178','/data/IN_Photo/2/slr/1501731178.png',1,3,'2017-08-03 03:33:45','0',NULL,NULL),(582,'1501731301','/data/IN_Photo/2/slr/1501731301.png',1,3,'2017-08-03 03:35:36','0',NULL,NULL),(583,'1501731381','/data/IN_Photo/2/slr/1501731381.png',1,3,'2017-08-03 03:36:55','0',NULL,NULL),(584,'1501731645','/data/IN_Photo/2/slr/1501731645.png',1,3,'2017-08-03 03:41:38','0',NULL,NULL),(585,'1501731743','/data/IN_Photo/2/slr/1501731743.png',1,3,'2017-08-03 03:43:21','0',NULL,NULL),(586,'1501742260','/data/IN_Photo/2/slr/1501742260.png',1,3,'2017-08-03 06:38:15','0',NULL,NULL),(587,'1501808853','/data/IN_Photo/2/slr/1501808853.png',1,3,'2017-08-04 01:08:10','0',NULL,NULL),(588,'1501813855','/data/IN_Photo/2/slr/1501813855.png',1,3,'2017-08-04 02:31:37','3','2017-08-09 02:31:37',NULL);
/*!40000 ALTER TABLE `media_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pic_webinfo`
--

DROP TABLE IF EXISTS `pic_webinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pic_webinfo` (
  `pic_webinfo_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `category_id` int(3) NOT NULL,
  `page_title` varchar(20) DEFAULT NULL,
  `background` varchar(255) DEFAULT NULL,
  `picture_top` float(5,2) DEFAULT NULL,
  `picture_left` float(5,2) DEFAULT NULL,
  `picture_right` float(5,2) DEFAULT NULL,
  `picture_bottom` float(5,2) DEFAULT NULL,
  `pic_webinfo_state` char(1) DEFAULT NULL,
  PRIMARY KEY (`pic_webinfo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pic_webinfo`
--

LOCK TABLES `pic_webinfo` WRITE;
/*!40000 ALTER TABLE `pic_webinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `pic_webinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `share_data`
--

DROP TABLE IF EXISTS `share_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_data` (
  `share_data_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `category_id` int(3) NOT NULL,
  `media_id` bigint(20) NOT NULL,
  `share_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `share_type` char(2) NOT NULL,
  PRIMARY KEY (`share_data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `share_data`
--

LOCK TABLES `share_data` WRITE;
/*!40000 ALTER TABLE `share_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `share_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `share_info`
--

DROP TABLE IF EXISTS `share_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_info` (
  `share_info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `category_id` int(3) NOT NULL,
  `share_moments_title` varchar(45) DEFAULT NULL,
  `share_moments_icon` varchar(255) DEFAULT NULL,
  `share_chats_title` varchar(45) DEFAULT NULL,
  `share_chats_text` varchar(45) DEFAULT NULL,
  `share_chats_icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`share_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `share_info`
--

LOCK TABLES `share_info` WRITE;
/*!40000 ALTER TABLE `share_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `share_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_category`
--

DROP TABLE IF EXISTS `user_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_category` (
  `user_category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `pay_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `begin_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `media_number` int(10) DEFAULT NULL,
  `category_id` int(3) DEFAULT NULL,
  `user_category_state` char(1) DEFAULT NULL,
  PRIMARY KEY (`user_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_category`
--

LOCK TABLES `user_category` WRITE;
/*!40000 ALTER TABLE `user_category` DISABLE KEYS */;
INSERT INTO `user_category` VALUES (1,1,'2017-06-12 08:18:48','2017-06-12 08:18:48','2017-08-03 02:30:49',1000,1,'0'),(2,1,'2017-06-12 08:19:25','2017-06-12 08:18:48','2018-07-12 08:19:25',1000,2,'0'),(3,1,'2017-06-12 08:19:25','2017-06-12 08:18:48','2018-07-12 08:19:25',1000,3,'0'),(4,1,'2017-06-12 08:19:25','2017-06-12 08:19:25','2018-07-12 08:19:25',1000,4,'0'),(5,1,'2015-06-12 08:19:25','2015-06-12 08:19:25','2015-07-12 08:19:25',100,1,'1'),(6,1,'2017-07-28 04:18:50','2018-07-27 16:00:00','2018-08-31 16:00:00',10000,1,'2');
/*!40000 ALTER TABLE `user_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id号',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  `phone` varchar(20) DEFAULT NULL COMMENT '用户手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `company` varchar(255) DEFAULT NULL COMMENT '用户公司',
  `user_state` char(1) DEFAULT NULL COMMENT '用户状态',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `admin_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'ming','25D55AD283AA400AF464C76D713C07AD','2017-06-05 07:16:57','18817774173','245996149@qq.com',NULL,'0',NULL,1),(2,'inshow','C01C624C6E877C2782B7435EFD0290C8','2017-06-12 05:10:39','17087950984','chen.ming@in-show.com.cn',NULL,'0',NULL,1),(7,NULL,'7F8C0FBBB3CA46C7304272CEA147B9A5','2017-07-26 07:31:16',NULL,'1925689992@qq.com','上海','0',NULL,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-09 13:54:35
