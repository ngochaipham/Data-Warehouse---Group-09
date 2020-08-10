/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100406
 Source Host           : localhost:3306
 Source Schema         : control

 Target Server Type    : MySQL
 Target Server Version : 100406
 File Encoding         : 65001

 Date: 10/08/2020 00:03:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id_config` int(11) NOT NULL AUTO_INCREMENT,
  `nameConfig` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `tableName` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `userName` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `host` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `port` int(11) NULL DEFAULT NULL,
  `user` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `pw` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `file_format` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `remotePath` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `localPath` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `mode` int(10) NULL DEFAULT NULL,
  `nameDbControl` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `nameTbConfig` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `nameTbLog` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `nameDbStaging` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `nameTbStaging` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `name_dbWarehouse` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `name_tbWarehouse` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_config`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, 'Load_students', 'student', 'root', NULL, 'drive.ecepvn.org', 2227, 'guest_access', '123456', 'sinhvien_sang_*.xlsx, sinhvien_chieu_*.xlsx', '/volume1/ECEP/song.nguyen/DW_2020/data', 'D:\\DW_LAB\\sinhvien', NULL, 'control', 'config', 'log', 'staging', 'student', 'warehouse', 'student');
INSERT INTO `config` VALUES (2, 'Load_subjects', 'monhoc', 'root', NULL, 'drive.ecepvn.org', 2227, 'guest_access', '123456', 'monhoc_*.xlsx', '/volume1/ECEP/song.nguyen/DW_2020/data', 'D:\\DW_LAB\\monhoc', NULL, 'control', 'config', 'log', 'staging', 'monhoc', 'warehouse', 'monhoc');
INSERT INTO `config` VALUES (3, 'Load_classess', 'lophoc', 'root', NULL, 'drive.ecepvn.org', 2227, 'guest_access', '123456', 'lophoc_sang_*.xlsx, lophoc_chieu_*.xlsx', '/volume1/ECEP/song.nguyen/DW_2020/data', 'D:\\DW_LAB\\lophoc', NULL, 'control', 'config', 'log', 'staging', 'lophoc', 'warehouse', 'lophoc');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id_log` int(10) NOT NULL AUTO_INCREMENT,
  `name_log` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `urlLocal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data_file_config_id` int(10) NULL DEFAULT NULL,
  `time_stamp_download` timestamp(0) NULL DEFAULT NULL,
  `staging_load_count` int(55) NULL DEFAULT NULL,
  `time_stamp_insert_staging` timestamp(0) NULL DEFAULT NULL,
  `timestamp_insert_datawarehouse` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id_log`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, 'Load_subjects', 'Uploading Staging', 'D:\\DW_LAB\\monhoc\\monhoc_2013.xlsx', 2, '2020-08-09 23:35:52', NULL, NULL, NULL);
INSERT INTO `log` VALUES (2, 'Load_subjects', 'Uploading Staging', 'D:\\DW_LAB\\monhoc\\monhoc_2014.xlsx', 2, '2020-08-09 23:35:52', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
