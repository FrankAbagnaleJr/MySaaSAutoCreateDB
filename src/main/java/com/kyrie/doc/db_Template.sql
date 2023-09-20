CREATE DATABASE IF NOT EXISTS `test_saas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `test_saas`;


-- 这张表在自己的库中，用来存用户的数据库连接信息
CREATE TABLE IF NOT EXISTS `test_db_info` (
    `id` bigint(20) unsigned NOT NULL,
    `db_schema_name` char(100) DEFAULT NULL COMMENT ' ',
    `db_ip` char(100) DEFAULT NULL COMMENT ' ',
    `db_port` char(100) DEFAULT NULL COMMENT ' ',
    `db_url` char(100) DEFAULT NULL COMMENT ' ',
    `db_username` char(100) DEFAULT NULL COMMENT ' ',
    `db_password` char(100) DEFAULT NULL COMMENT ' ',
    `db_driver_class` char(100) DEFAULT NULL COMMENT ' ',
    `status` bigint(10) DEFAULT '0',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB ;

-- 这张表是用户数据库创建的，用来测试
CREATE TABLE IF NOT EXISTS `test_user` (
    `id` bigint(20) unsigned NOT NULL,
    `name` char(100) DEFAULT NULL,
    `company` char(100) DEFAULT NULL,
    `addr` char(100) DEFAULT NULL,
    `phone` char(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB ;

INSERT IGNORE INTO `test_user` (`id`, `name`, `company`, `addr`, `iphone`) VALUES
	(1, '雷军', '小米科技公司','深圳龙华区', '188254541111'),
	(2, '马化腾', '腾讯科技公司','深圳南山区', '18822222222'),
	(3, '冀金梁', '光良科技公司','郭家报区', '18833333333'),
	(4, '小白', '小白科技公司','深圳龙华区', '18844444444'),
	(5, '小黑', '小黑科技公司','深圳龙华区', '18855555555'),
	(6, '小蓝', '小蓝科技公司','深圳龙华区', '18866666666');
