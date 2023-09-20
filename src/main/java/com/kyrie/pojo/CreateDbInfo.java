package com.kyrie.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_db_info")
public class CreateDbInfo {
    //TODO 所有值都不能为空，并且进行校验
    @TableId
    private Integer id;
    @TableField("db_schema_name")
    private String schemaName;
    @TableField("db_ip")
    private String ip;
    @TableField("db_port")
    private String port;
    private String url =  "jdbc:mysql://#{dbIp}:#{dbPort}/#{schemaName}?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true";
    @TableField("db_username")
    private String username;
    @TableField("db_password")
    private String password;

    @TableField("db_driver_class")
    private String driverClass = "com.mysql.cj.jdbc.Driver";
}
