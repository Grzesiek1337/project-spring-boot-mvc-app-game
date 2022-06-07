-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.27-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hero`
--

DROP TABLE IF EXISTS `hero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hero` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dodge_chance` int NOT NULL,
  `experience` int NOT NULL,
  `experience_threshold` int NOT NULL,
  `gold` int NOT NULL,
  `health` int NOT NULL,
  `hp_potions` int NOT NULL,
  `level` int NOT NULL,
  `max_attack` int NOT NULL,
  `maximum_health` int NOT NULL,
  `min_attack` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh3p9ph8tclayakk7t6qj1xc2l` (`user_id`),
  CONSTRAINT `FKh3p9ph8tclayakk7t6qj1xc2l` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hero`
--

LOCK TABLES `hero` WRITE;
/*!40000 ALTER TABLE `hero` DISABLE KEYS */;
INSERT INTO `hero` VALUES (1,5,0,100,50,115,7,1,7,115,2,'Thorin',1);
/*!40000 ALTER TABLE `hero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `heroes_items`
--

DROP TABLE IF EXISTS `heroes_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `heroes_items` (
  `hero_id` bigint NOT NULL,
  `item_id` bigint NOT NULL,
  KEY `FKoabhmgdgauh95j1ykuy7rcku` (`item_id`),
  KEY `FKt6lfwopl8cldjayreys0io2ox` (`hero_id`),
  CONSTRAINT `FKoabhmgdgauh95j1ykuy7rcku` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `FKt6lfwopl8cldjayreys0io2ox` FOREIGN KEY (`hero_id`) REFERENCES `hero` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `heroes_items`
--

LOCK TABLES `heroes_items` WRITE;
/*!40000 ALTER TABLE `heroes_items` DISABLE KEYS */;
INSERT INTO `heroes_items` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `heroes_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `heroes_quests`
--

DROP TABLE IF EXISTS `heroes_quests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `heroes_quests` (
  `hero_id` bigint NOT NULL,
  `quest_id` bigint NOT NULL,
  KEY `FK6x3t5g0mm9ohp50u1rkqfat91` (`quest_id`),
  KEY `FKiif0ombrsh2dweq3428l6wp1l` (`hero_id`),
  CONSTRAINT `FK6x3t5g0mm9ohp50u1rkqfat91` FOREIGN KEY (`quest_id`) REFERENCES `quests` (`id`),
  CONSTRAINT `FKiif0ombrsh2dweq3428l6wp1l` FOREIGN KEY (`hero_id`) REFERENCES `hero` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `heroes_quests`
--

LOCK TABLES `heroes_quests` WRITE;
/*!40000 ALTER TABLE `heroes_quests` DISABLE KEYS */;
/*!40000 ALTER TABLE `heroes_quests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `increase_health` int NOT NULL,
  `increase_max_attack` int NOT NULL,
  `increase_min_attack` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,5,2,0,'Bronze Ring',50),(2,10,0,1,'Bronze Amulet',50);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobs`
--

DROP TABLE IF EXISTS `mobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `health` int NOT NULL,
  `level` int NOT NULL,
  `max_attack` int NOT NULL,
  `min_attack` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobs`
--

LOCK TABLES `mobs` WRITE;
/*!40000 ALTER TABLE `mobs` DISABLE KEYS */;
INSERT INTO `mobs` VALUES (1,100,1,4,2,'Goblin'),(2,110,2,5,2,'Spider');
/*!40000 ALTER TABLE `mobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quests`
--

DROP TABLE IF EXISTS `quests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `experience_prize` int NOT NULL,
  `gold_prize` int NOT NULL,
  `killed_mob` int NOT NULL,
  `mob_count_to_kill` int NOT NULL,
  `mob_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtcnnkmqdu4qixnii23ty9jkl1` (`mob_id`),
  CONSTRAINT `FKtcnnkmqdu4qixnii23ty9jkl1` FOREIGN KEY (`mob_id`) REFERENCES `mobs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quests`
--

LOCK TABLES `quests` WRITE;
/*!40000 ALTER TABLE `quests` DISABLE KEYS */;
/*!40000 ALTER TABLE `quests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `hero_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FK9kwnw75s86s6aixsg50e7ra8j` (`hero_id`),
  CONSTRAINT `FK9kwnw75s86s6aixsg50e7ra8j` FOREIGN KEY (`hero_id`) REFERENCES `hero` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '','$2a$10$D75EOpQAqjGwjO9yjQzm1uqtkyACutJTh9TFg2eDKvMtaQZN7ZULO','ROLE_ADMIN','aaa',1);
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

-- Dump completed on 2022-06-07 19:17:53
