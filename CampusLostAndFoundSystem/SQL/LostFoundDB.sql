CREATE DATABASE IF NOT EXISTS `LostFoundDB` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `LostFoundDB`;

CREATE TABLE IF NOT EXISTS `lost_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `category` varchar(50) NOT NULL,
  `pickup_location` varchar(100) NOT NULL,
  `pickup_time` datetime NOT NULL,
  `pickup_person` varchar(50) NOT NULL,
  `contact` varchar(50) NOT NULL,
  `description` text,
  `status` varchar(20) NOT NULL DEFAULT 'Unclaimed',
  `claimant` varchar(50),
  `claim_contact` varchar(50),
  `claim_time` datetime,
  `submitter_username` varchar(20),
  `claimant_username` varchar(20),
  PRIMARY KEY (`id`),
  KEY `idx_item_name` (`item_name`),
  KEY `idx_category` (`category`),
  KEY `idx_pickup_location` (`pickup_location`),
  KEY `idx_status` (`status`),
  KEY `idx_submitter` (`submitter_username`),
  KEY `idx_claimant_username` (`claimant_username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `lost_items` MODIFY `status` varchar(20) NOT NULL DEFAULT 'Unclaimed';

CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(200) NOT NULL,
  `usertype` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `userlogs` (
  `username` varchar(45) NOT NULL,
  `in_time` varchar(45) NOT NULL,
  `out_time` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `app_settings` (
  `setting_key` varchar(50) NOT NULL,
  `setting_value` varchar(100) NOT NULL,
  PRIMARY KEY (`setting_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `users` (`name`,`location`,`phone`,`username`,`password`,`usertype`) VALUES
('Administrator','Campus','13300000000','admin','admin','ADMIN'),
('Standard User','Campus','18256789012','user','user','USER');

-- Sample items are inserted only into an empty table by the application.
