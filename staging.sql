/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100406
 Source Host           : localhost:3306
 Source Schema         : staging

 Target Server Type    : MySQL
 Target Server Version : 100406
 File Encoding         : 65001

 Date: 10/08/2020 00:04:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lophoc
-- ----------------------------
DROP TABLE IF EXISTS `lophoc`;
CREATE TABLE `lophoc`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `STT` int(11) NOT NULL,
  `maLophoc` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `maMonHoc` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `namHoc` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `id_log` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for monhoc
-- ----------------------------
DROP TABLE IF EXISTS `monhoc`;
CREATE TABLE `monhoc`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `STT` int(11) NOT NULL,
  `maMH` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `tenMH` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `TC` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `khoaBoMon` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `ghiChu` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `id_log` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `STT` int(11) NOT NULL AUTO_INCREMENT,
  `MSSV` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `ho` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `ten` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `dob` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `lop` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `tenlop` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `sdt` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `email` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `quequan` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `ghichu` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `id_log` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`STT`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
