CREATE DATABASE IF NOT EXISTS campusitemdeskdb;
USE campusitemdeskdb;

CREATE TABLE IF NOT EXISTS items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    found_date DATE NOT NULL,
    contact VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'Available',
    returned_to VARCHAR(100)
);

-- Optional sample rows. Existing rows with these IDs are kept.
INSERT IGNORE INTO items (id, item_name, location, found_date, contact, description, status, returned_to) VALUES
(1, 'Water bottle', 'Library', '2026-10-27', 'Library desk', 'Blue bottle', 'Available', NULL),
(2, 'Notebook', 'Classroom 2', '2026-10-27', 'Class representative', 'Maths notebook', 'Available', NULL),
(3, 'USB drive', 'Computer lab', '2026-10-26', 'Lab assistant', 'Black USB drive', 'Returned', 'Demo Student');
