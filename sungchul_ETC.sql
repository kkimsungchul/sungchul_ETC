-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        5.7.25-log - MySQL Community Server (GPL)
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- etc 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `etc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `etc`;

-- 테이블 etc.file_table 구조 내보내기
CREATE TABLE IF NOT EXISTS `file_table` (
  `seq` int(10) NOT NULL AUTO_INCREMENT COMMENT '순서',
  `file_name` varchar(200) NOT NULL COMMENT '기존 파일명',
  `save_file_name` varchar(200) NOT NULL COMMENT '저장된 파일명',
  `user_id` varchar(20) NOT NULL COMMENT '파일 업로드 유저 ID',
  `file_upload_date` char(8) NOT NULL DEFAULT '' COMMENT '로그날짜',
  `file_upload_time` char(6) NOT NULL DEFAULT '' COMMENT '로그 시간',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='파일 업로드 테이블';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 etc.user_table 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_table` (
  `user_id` varchar(20) NOT NULL COMMENT 'ID',
  `password` varchar(200) NOT NULL COMMENT '비밀번호',
  `name` varchar(20) NOT NULL COMMENT '이름',
  `reg_date` char(8) NOT NULL COMMENT '등록일',
  `reg_time` char(6) NOT NULL DEFAULT '' COMMENT '등록시간',
  `role_id` varchar(15) NOT NULL COMMENT 'admin, manager , user',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '상태 :  1:사용, 2:삭제',
  `otp` int(1) DEFAULT '0' COMMENT 'OTP 발행 여부 0 : 미발행 , 1 : 발행',
  `otp_key` varchar(8) DEFAULT NULL COMMENT 'OTP 고유 키 값',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 테이블';

-- 내보낼 데이터가 선택되어 있지 않습니다.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
