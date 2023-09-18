package com.kyrie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDbDTO {
    private String schemaName;
    private String dbIp;
    private String dbPort;
    private String dbUrl =  "jdbc:mysql://#{dbIp}:#{dbPort}/#{schemaName}?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true";;
    private String dbUsername;
    private String dbPassword;

    private String driverClass = "com.mysql.cj.jdbc.Driver";
}
