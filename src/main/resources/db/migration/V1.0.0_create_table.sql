CREATE DATABASE IF NOT EXISTS `library`;

USE `library`;

CREATE TABLE IF NOT EXISTS `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT into books values(0 , 'title_1', 'author_1');
INSERT into books values(0 , 'title_2', 'author_2');
INSERT into books values(0 , 'title_3', 'author_3');
INSERT into books values(0 , 'title_4', 'author_4');
INSERT into books values(0 , 'title_5', 'author_5');