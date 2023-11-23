-- 2023-11-23 --

CREATE TABLE `board` (
  `bno` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `writer` varchar(100) NOT NULL,
  `content` text,
  `regdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `moddate` datetime DEFAULT CURRENT_TIMESTAMP,
  `readCount` int DEFAULT '0',
  PRIMARY KEY (`bno`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci