CREATE TABLE `books` (
  `isbn` varchar(13) NOT NULL,
  `title` varchar(80) NOT NULL,
  `publisher` varchar(60) NOT NULL,
  `genre` varchar(40) NOT NULL,
  `year` int NOT NULL,
  `price` float NOT NULL,
  `description` text,
  `stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`isbn`),
  UNIQUE KEY `books_UN` (`title`),
  CONSTRAINT `stock_CHECK` CHECK ((`stock` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE `authors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lastname` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `firstname` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;


CREATE TABLE `authors_books` (
  `authors_id` int NOT NULL,
  `books_isbn` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `author_index` int NOT NULL,
  PRIMARY KEY (`authors_id`,`books_isbn`),
  KEY `Authors_Books_FK` (`books_isbn`),
  CONSTRAINT `Authors_Books_FK_1` FOREIGN KEY (`authors_id`) REFERENCES `authors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
