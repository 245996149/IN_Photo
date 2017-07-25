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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_info`
--

LOCK TABLES `admin_info` WRITE;
/*!40000 ALTER TABLE `admin_info` DISABLE KEYS */;
INSERT INTO `admin_info` VALUES (1,'ming','chen245996149',NULL,NULL,'2017-07-10 16:00:00','0');
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
INSERT INTO `admin_role` VALUES (1,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role_info`
--

LOCK TABLES `admin_role_info` WRITE;
/*!40000 ALTER TABLE `admin_role_info` DISABLE KEYS */;
INSERT INTO `admin_role_info` VALUES (1,'管理员');
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
INSERT INTO `admin_role_module` VALUES (1,1),(1,2),(1,3),(1,4);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_data`
--

LOCK TABLES `media_data` WRITE;
/*!40000 ALTER TABLE `media_data` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_category`
--

LOCK TABLES `user_category` WRITE;
/*!40000 ALTER TABLE `user_category` DISABLE KEYS */;
INSERT INTO `user_category` VALUES (1,1,'2017-06-12 08:18:48','2017-06-12 08:18:48','2018-07-12 08:19:25',1000,1,'0'),(2,1,'2017-06-12 08:19:25','2017-06-12 08:18:48','2018-07-12 08:19:25',1000,2,'0'),(3,1,'2017-06-12 08:19:25','2017-06-12 08:18:48','2018-07-12 08:19:25',1000,3,'0'),(4,1,'2017-06-12 08:19:25','2017-06-12 08:19:25','2018-07-12 08:19:25',1000,4,'0');
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
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'ming','chen245996149','2017-06-05 07:16:57','18817774173','245996149@qq.com',NULL,'0',NULL,1),(2,'inshow','inshow123','2017-06-12 05:10:39',NULL,NULL,NULL,'0',NULL,1);
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

-- Dump completed on 2017-07-25 18:13:12
