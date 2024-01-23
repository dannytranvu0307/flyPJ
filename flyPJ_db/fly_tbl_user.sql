DROP TABLE IF EXISTS `tbl_user`;

CREATE TABLE `tbl_user` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(256) NOT NULL,
  `PASSWORD` varchar(256) NOT NULL,
  `FIRSTNAME` varchar(128) NOT NULL,
  `LASTNAME` varchar(128) NOT NULL,
  `AGE` int,
  `AVATAR_LINK` varchar(256),
  `CREATED_AT` datetime DEFAULT CURRENT_TIMESTAMP,
  `DELETED_AT` datetime,
  `STAGE` tinyint CHECK (`STAGE` IN (0, 1, 2)),
  `ACTIVE_CODE` varchar(128),
  `TOKEN` varchar(256),
  PRIMARY KEY (`ID`),
  UNIQUE KEY `EMAIL` (`EMAIL`),
  CONSTRAINT `tbl_user_chk_1` CHECK (((length(`FIRSTNAME`) >= 6) and (length(`FIRSTNAME`) <= 64))),
  CONSTRAINT `tbl_user_chk_2` CHECK (((length(`EMAIL`) >= 6) and (length(`EMAIL`) <= 256)))
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `tbl_user` WRITE;
INSERT INTO `tbl_user` (`EMAIL`, `FIRSTNAME`, `LASTNAME`, `AGE`, `AVATAR_LINK`, `STAGE`, `ACTIVE_CODE`, `TOKEN`)
VALUES
  ('test@example.com', 'anhlacho12','Test', 'User', 25, 'https://example.com/avatar.jpg', 2, '123456', 'abc123def456');
UNLOCK TABLES;