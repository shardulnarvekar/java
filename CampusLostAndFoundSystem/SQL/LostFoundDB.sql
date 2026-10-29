CREATE DATABASE IF NOT EXISTS `lostfounddb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `lostfounddb`;

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

-- Universal Ai University, Karjat, Mumbai, Maharashtra, India.
-- Sample names, campus areas and +91 contacts are demonstration data.
INSERT IGNORE INTO `users` (`name`,`location`,`phone`,`username`,`password`,`usertype`) VALUES
('Campus Administrator','Karjat, Mumbai, Maharashtra, India','+91 90000 00100','admin','admin','ADMIN'),
('Demo Student','Karjat, Mumbai, Maharashtra, India','+91 90000 00101','user','user','USER');

-- Existing accounts and reports are kept. Samples are added only once.
SET @add_samples = (SELECT COUNT(*) = 0 FROM lost_items)
  AND NOT EXISTS (SELECT 1 FROM app_settings WHERE setting_key='sample_items_seeded');
START TRANSACTION;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Student ID Card','Identification','University Library - Reading Area','2026-05-27 11:45:00.000000','Shardul Narvekar','+91 90000 00001','Universal Ai University student ID card in a clear holder.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Nike Sneaker','Clothing & Accessories','Campus Cafeteria Entrance','2026-05-29 15:00:00.000000','Jeel Nandha','+91 90000 00002','Black left shoe, size 42; loose laces.','Claimed','Arjun Rao','+91 90000 00030','2026-05-29 16:15:00.000000','admin','admin' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Calculus Textbook','Study Supplies','Academic Block - Classroom','2026-05-26 13:15:00.000000','Shivshree Patil','+91 90000 00003','Seventh edition with visible cover creases and notes.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Pink Stanley Tumbler','Everyday Items','Campus Cafe','2026-05-28 17:50:00.000000','Krishna Sevak','+91 90000 00004','Pink tumbler with a straw lid and an engraving.','Claimed','Diya Sharma','+91 90000 00031','2026-05-28 19:35:00.000000','admin','user' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Apple Watch SE','Valuables','Academic Block - Reception','2026-05-29 11:20:00.000000','Akhil Barman','+91 90000 00005','White band; small scratch at the bottom right of the screen.','Unclaimed',NULL,NULL,NULL,'admin',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Power Bank','Electronics','University Library - Study Room','2026-05-27 21:10:00.000000','Tejas Adhiya','+91 90000 00006','White 10,000 mAh power bank with light surface wear.','Claimed','Kabir Nair','+91 90000 00032','2026-05-27 22:45:00.000000','admin','user' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'University Campus Card','Identification','Campus Dining Hall','2026-05-25 14:25:00.000000','Aarav Deshmukh','+91 90000 00007','Universal Ai University campus card with visible wear.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'White Dress','Clothing & Accessories','Seminar Hall','2026-05-28 18:40:00.000000','Ananya Joshi','+91 90000 00008','Lightweight Zara summer dress with lace trim.','Claimed','Anika Shetty','+91 90000 00033','2026-05-28 22:00:00.000000','admin','admin' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Razer Mechanical Keyboard','Electronics','Student Activity Centre','2026-05-29 22:15:00.000000','Rohan Kulkarni','+91 90000 00009','Black RGB keyboard with fingerprints on the keys.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Laptop Charger','Electronics','University Library - Study Area','2026-05-26 17:05:00.000000','Isha Shah','+91 90000 00010','Original 65 W charger with coiled cable marks.','Claimed','Akhil Barman','+91 90000 00005','2026-05-26 18:20:00.000000','admin','admin' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Black Folding Umbrella','Everyday Items','Hostel Reception','2026-05-27 19:50:00.000000','Vedant Pawar','+91 90000 00011','Automatic open and close; water spots on the canopy.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Aadhaar Card','Identification','Campus Cafe','2026-05-29 12:45:00.000000','Sakshi Jadhav','+91 90000 00012','Aadhaar card in a clear holder; identifying details are not displayed.','Claimed','Tejas Adhiya','+91 90000 00006','2026-05-29 14:10:00.000000','admin','user' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'AirPods Pro','Electronics','Computer Lab','2026-05-28 16:25:00.000000','Aditya Mehta','+91 90000 00013','White charging case; slight wear on the left earbud.','Unclaimed',NULL,NULL,NULL,'admin',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Jansport Backpack','Clothing & Accessories','Academic Block - Common Area','2026-05-25 12:00:00.000000','Priya Naik','+91 90000 00014','Light gray backpack with worn straps.','Claimed','Aarav Deshmukh','+91 90000 00007','2026-05-25 16:50:00.000000','admin','user' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Casio Digital Watch','Valuables','Sports Ground','2026-05-29 14:35:00.000000','Omkar Shinde','+91 90000 00015','Black band; display works normally.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Black Framed Glasses','Everyday Items','Lecture Hall','2026-05-27 18:10:00.000000','Sneha More','+91 90000 00016','Thin black frame; lenses are approximately -4.00 diopters.','Claimed','Ananya Joshi','+91 90000 00008','2026-05-27 20:40:00.000000','admin','admin' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Kingston USB Drive','Electronics','University Library - Entrance','2026-05-28 22:55:00.000000','Yash Chavan','+91 90000 00017','Metal casing, 128 GB.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Bear Keychain','Everyday Items','Student Services Desk','2026-05-26 21:20:00.000000','Neha Sawant','+91 90000 00018','Silver keyring with a brown bear charm.','Claimed','Aditya Mehta','+91 90000 00013','2026-05-27 00:00:00.000000','admin','user' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Black A4 Notebook','Study Supplies','Academic Block - Classroom','2026-05-29 16:40:00.000000','Atharva Kale','+91 90000 00019','Cover features calculus formulas.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Green Canvas Tote','Everyday Items','Campus Courtyard','2026-05-24 12:40:00.000000','Riya Mahajan','+91 90000 00020','Universal Ai University student club tote; scrap paper inside.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Kindle E-Reader','Electronics','Hostel Common Room','2026-05-24 21:55:00.000000','Soham Gokhale','+91 90000 00021','Black protective case; low battery.','Claimed','Shardul Narvekar','+91 90000 00001','2026-05-25 11:30:00.000000','admin','user' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Red Scarf','Clothing & Accessories','Auditorium','2026-05-23 11:15:00.000000','Tanvi Apte','+91 90000 00022','Red knitted scarf with fringe.','Unclaimed',NULL,NULL,NULL,'admin',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Lab Report Folder','Study Supplies','Science Lab','2026-05-23 19:00:00.000000','Pranav Patil','+91 90000 00023','Clear folder containing a physics lab report.','Handed Over',NULL,NULL,NULL,'admin',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Smartphone','Valuables','Academic Block - Lobby','2026-05-22 22:35:00.000000','Aditi Bhosale','+91 90000 00024','Blue case with a landscape lock screen.','Claimed','Shivshree Patil','+91 90000 00003','2026-05-23 14:40:00.000000','admin','user' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Clear Lunch Box','Everyday Items','Campus Cafeteria','2026-05-22 14:50:00.000000','Kunal Thakur','+91 90000 00025','Clear three-tier lunch box with a blue lid.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Calculator','Study Supplies','Computer Lab','2026-05-21 16:30:00.000000','Mihir Shah','+91 90000 00026','Scientific calculator with a label on the back.','Claimed','Rohan Kulkarni','+91 90000 00009','2026-05-21 19:00:00.000000','admin','admin' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'White Baseball Cap','Clothing & Accessories','Sports Ground','2026-05-20 20:15:00.000000','Sanika Nene','+91 90000 00027','Blue lettering on the brim.','Unclaimed',NULL,NULL,NULL,'user',NULL WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Sony Earbuds Case','Electronics','Seminar Hall','2026-05-19 21:05:00.000000','Harsh Vyas','+91 90000 00028','Black case without the earbuds.','Claimed','Krishna Sevak','+91 90000 00004','2026-05-20 11:55:00.000000','admin','user' WHERE @add_samples;

INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,pickup_person,contact,description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username)
SELECT 'Silver Necklace','Valuables','Campus Main Gate','2026-05-18 15:45:00.000000','Meera Kulkarni','+91 90000 00029','Thin chain with a star-shaped pendant.','Unclaimed',NULL,NULL,NULL,'admin',NULL WHERE @add_samples;

INSERT IGNORE INTO app_settings (setting_key,setting_value) VALUES ('sample_items_seeded','true');
COMMIT;
