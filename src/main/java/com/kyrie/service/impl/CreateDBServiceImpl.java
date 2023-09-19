package com.kyrie.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.kyrie.pojo.CreateDbDTO;
import com.kyrie.service.CreateDBService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Service
public class CreateDBServiceImpl implements CreateDBService {

    @Autowired
    DynamicRoutingDataSource dataSource;

    @Autowired
    HttpServletResponse response;

    @Override
    public String initDB(CreateDbDTO createDBDTO){

        //TODO 建库之前先检查数据库是否能连接成功
        boolean flag = tryConnectDB(createDBDTO);
        if (!flag) {
            return "数据库连接失败！请检查连接信息，或者联系我们工作人员。";
        }

        //TODO 建库之前先从数据库表查询是否重名 和是否已经创建
        boolean falg2 = checkDBName(createDBDTO);
        if (!falg2) {
            return "数据库已存在！";
        }

        Connection connection = null;

        try {
            String schema = createDBDTO.getSchemaName();
            String jdbcurl = createDBDTO.getUrl();
            String username = createDBDTO.getUsername();
            String password = createDBDTO.getPassword();
            String driverClass = createDBDTO.getDriverClass();

            //建库语句
            String sqlCreateSchema = "CREATE DATABASE IF NOT EXISTS `#{schema}` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;";
            //删库语句
//            String sqlDropSchema = "DROP DATABASE IF EXISTS #{schema};";

            String sql2 = "use `#{schema}`;";

            //jdbd的方式与数据库建立连接
            Class.forName(driverClass);
            DriverManager.getConnection(jdbcurl, username, password);

            //与数据库连接成功后创建statement来执行sql
            Statement statement = connection.createStatement();
            //执行的建库语句,先删再建
//            statement.execute(sqlDropSchema.replace("#{schema}", schema));
            statement.execute(sqlCreateSchema.replace("#{schema}", schema));
            //切换到刚建好的数据库
            statement.execute(sql2.replace("#{schema}", schema));

            //用mybatis提供的工具来执行sql建库
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setStopOnError(true);

            //fixme 测试能不能放在包下
//            ClassPathResource classPathResource = new ClassPathResource("db_wimoorTemplate.sql");    //建库文件在根目录下
            ClassPathResource classPathResource = new ClassPathResource("com/kyrie/doc/db_wimoorTemplate.sql");
            InputStream inputStream = classPathResource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            scriptRunner.runScript(inputStreamReader);

            //fixme 建库成功后向数据库表记录下来。id, 用户名字, 数据库名字 , 数据库账号密码, 数据库状态（0未创建，1已创建，2逻辑删除）


            //TODO 这里把数据源添 并且到请求头
            String s = addDataSource(createDBDTO);
            log.info(s);

            return "建库成功";

        } catch (Exception e) {
            log.error("自动建库出现异常",e.getMessage());
            return "建库失败";
        }finally {
            if (!Objects.isNull(connection)) {
                try {
                    connection.commit();
                    connection.close();
                } catch (SQLException e) {
                    log.error("数据库连接connection关闭失败",e.getMessage());
                }
            }
        }
    }

    /**
     * 添加到数据源，并且返回数据源名到请求头
     * @param createDbDTO
     * @return
     */
    public String addDataSource(CreateDbDTO createDbDTO) {
        DruidDataSource tempDB = new DruidDataSource();
        tempDB.setUrl(createDbDTO.getUrl());
        tempDB.setUsername(createDbDTO.getUsername());
        tempDB.setPassword(createDbDTO.getPassword());
        tempDB.setDriverClassName(createDbDTO.getDriverClass());

        //根据数据库名存入数据源，找数据源的时候根据数据库名找
        dataSource.addDataSource(createDbDTO.getSchemaName(), tempDB);

        //把数据源添加到response请求头中返回
        response.setHeader("userDs",createDbDTO.getSchemaName());
        log.info("已把数据源名添加到resp中");

        return "用户：" + createDbDTO.getUsername() + ", 数据源已添加：" + createDbDTO.getUrl();
    }

    boolean tryConnectDB(CreateDbDTO createDBDTO) {
        Connection connection = null;
        try {
            //这里尝试连接的是数据库的mysql表，因为mysql表是自带的，肯定存在
            String dbUrl = "jdbc:mysql://" + createDBDTO.getIp() + ":" + createDBDTO.getPort() + "/mysql?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true";
            connection = DriverManager.getConnection(dbUrl, createDBDTO.getUsername(), createDBDTO.getPassword());
            return true;
        } catch (SQLException e) {
            log.error("连接数据库失败", e.getMessage());
            return false;
        } finally {
            if (null != connection) {
                try {
                    connection.commit();
                    connection.close();
                } catch (SQLException e) {
                    log.error("数据库连接关闭失败", e.getMessage());
                }
            }
        }
    }

    boolean checkDBName(CreateDbDTO createDbDTO) {
        //fixme 查询数据库是否重名
        //User user = DBService.selectByName(createDbDTO.getSchameName);
        //if(！Objects.isNull(user)){ retuen false;}
        return true;
    }
}
