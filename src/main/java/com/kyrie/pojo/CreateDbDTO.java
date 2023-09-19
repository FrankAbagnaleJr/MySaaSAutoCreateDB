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
public class CreateDbDTO {
    //TODO 所有值都不能为空，并且进行校验
    @TableId
    private Integer id;
    @TableField("db_schema_name")
    private String schemaName;
    private String ip;
    private String port;
    private String url =  "jdbc:mysql://#{dbIp}:#{dbPort}/#{schemaName}?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true";
    private String username;
    private String password;

    @TableField("db_driver_class")
    private String driverClass = "com.mysql.cj.jdbc.Driver";
}
