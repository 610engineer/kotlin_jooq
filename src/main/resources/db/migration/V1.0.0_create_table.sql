CREATE DATABASE IF NOT EXISTS `library`;

USE `library`;

CREATE TABLE IF NOT EXISTS `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT into books values(0 , 'test_title_a', 'test_author_a');
INSERT into books values(0 , 'test_title_b', 'test_author_b');