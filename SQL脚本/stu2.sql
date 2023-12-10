

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS stu;


-- ----------------------------
-- 课程表
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `cno` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `cname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `credit` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`cno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- 添加数据
-- ----------------------------
INSERT INTO `course` VALUES ('0001', 'Java程序设计', 1);
INSERT INTO `course` VALUES ('0002', 'C++语言程序设计', 2);
INSERT INTO `course` VALUES ('0003', 'C语言程序设计', 3);
INSERT INTO `course` VALUES ('0004', '计算机网络基础', 2);
INSERT INTO `course` VALUES ('0005', '英语', 2);
INSERT INTO `course` VALUES ('0006', '高等数学', 1);
INSERT INTO `course` VALUES ('0007', '软件工程', 2);
INSERT INTO `course` VALUES ('0008', '大数据导论', 3);
INSERT INTO `course` VALUES ('0009', 'JavaWeb', 4);

-- ----------------------------
-- SC表
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc`  (
  `sno` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cno` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `score` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- sc表
-- ----------------------------
INSERT INTO `sc` VALUES ('1001', '0008', 66);
INSERT INTO `sc` VALUES ('1002', '0002', 99);
INSERT INTO `sc` VALUES ('1003', '0003', 97);
INSERT INTO `sc` VALUES ('1005', '0006', 80);
INSERT INTO `sc` VALUES ('1006', '0007', 66);
INSERT INTO `sc` VALUES ('1007', '0011', 55);
INSERT INTO `sc` VALUES ('1008', '0005', 87);
INSERT INTO `sc` VALUES ('1009', '0006', 56);
INSERT INTO `sc` VALUES ('1010', '0005', 0);

-- ----------------------------
-- Stu表
-- ----------------------------
DROP TABLE IF EXISTS `stu`;
CREATE TABLE `stu`  (
  `sno` int(11) NOT NULL DEFAULT 0,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` int(255) NULL DEFAULT NULL,
  `sdept` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 插入数据
-- ----------------------------
INSERT INTO `stu` VALUES (1001, 'mihayo', '男', 18, '信息');
INSERT INTO `stu` VALUES (1002, 'miimi', '男', 22, '艺术');
INSERT INTO `stu` VALUES (1003, 'yuansheng', '女', 23, '传媒');
INSERT INTO `stu` VALUES (1004, 'fenxin', '男', 22, '土建');
INSERT INTO `stu` VALUES (1005, 'fasdf', '女', 21, '体育');
INSERT INTO `stu` VALUES (1006, '李志强', '男', 23, '信电');
INSERT INTO `stu` VALUES (1007, 'tensei', '男', 34, '软件');
INSERT INTO `stu` VALUES (1008, '张青平', '男', 21, '计算机');
INSERT INTO `stu` VALUES (1009, '刘东阳', '女', 23, '艺术');
INSERT INTO `stu` VALUES (1010, '马晓夏', '男', 14, '艺术');
INSERT INTO `stu` VALUES (1011, '钱忠理', '女', 36, '金融');
INSERT INTO `stu` VALUES (1012, '孙阳洋', '男', 24, '信息');
INSERT INTO `stu` VALUES (1013, '郭小斌', '女', 15, '信息');
INSERT INTO `stu` VALUES (1014, '肖月玲', '男', 24, '金融');
INSERT INTO `stu` VALUES (1015, '张玲珑', '女', 24, '信息');

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `uno` int(11) NOT NULL AUTO_INCREMENT ,
                         `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`uno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- 加上数据
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123');

SET FOREIGN_KEY_CHECKS = 1;

