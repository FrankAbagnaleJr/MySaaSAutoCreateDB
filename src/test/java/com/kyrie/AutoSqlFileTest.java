package com.kyrie;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AutoSqlFileTest {
    public static void main(String[] args) throws Exception {

        //fixme 建库之前先从数据库表查询是否重名 和是否已经创建

        try {
        String jdbcurl = "jdbc:mysql://192.168.101.65:3306/itcast?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "mysql";
        String driverClass = "com.mysql.cj.jdbc.Driver";


        String schema = "createDBTest";


            //建库语句
            String sqlCreateSchema = "CREATE DATABASE IF NOT EXISTS `#{schema}` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;";
            String sql2 = "use `#{schema}`;";

            //jdbd的方式与数据库建立连接
            Class.forName(driverClass);
            Connection connection = DriverManager.getConnection(jdbcurl, username, password);

            //与数据库连接成功后创建statement来执行sql
            Statement statement = connection.createStatement();
            //执行的建库语句
            statement.execute(sqlCreateSchema.replace("#{schema}", schema));
            //切换到刚建好的数据库
            statement.execute(sql2.replace("#{schema}", schema));

            //用mybatis提供的工具来执行sql建库
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setStopOnError(true);

            ClassPathResource classPathResource = new ClassPathResource("db_wimoorTemplate.sql");
            InputStream inputStream = classPathResource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            scriptRunner.runScript(inputStreamReader);

            connection.commit();
            connection.close();

            //fixme 建库成功后向数据库表记录下来。id, 用户名字, 数据库名字 , 数据库账号密码, 数据库状态（0未创建，1已创建，2逻辑删除）

            System.out.printf("建库成功");

        } catch (ClassNotFoundException e) {
            System.out.printf("建库失败");
        }
    }
}
