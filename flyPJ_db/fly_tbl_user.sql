-- flyPJ_db/fly_tbl_user.sql

DROP TABLE IF EXISTS `tbl_user`;

CREATE TABLE `tbl_user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `FULL_NAME` varchar(128) NOT NULL,
  `EMAIL` varchar(256) NOT NULL,
  `PASSWORD` varchar(256) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `EMAIL` (`EMAIL`),
  CONSTRAINT `tbl_user_chk_1` CHECK (((length(`FULL_NAME`) >= 6) and (length(`FULL_NAME`) <= 64))),
  CONSTRAINT `tbl_user_chk_2` CHECK (((length(`EMAIL`) >= 6) and (length(`EMAIL`) <= 256)))
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
