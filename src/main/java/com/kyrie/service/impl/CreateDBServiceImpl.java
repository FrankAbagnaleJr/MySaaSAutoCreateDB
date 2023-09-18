package com.kyrie.service.impl;

import com.kyrie.pojo.CreateDbDTO;
import com.kyrie.service.CreateDBService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    @Override
    public String initDB(CreateDbDTO createDBDTO) throws ClassNotFoundException, SQLException, IOException {

        //TODO 建库之前先检查数据库是否能连接成功
        boolean flag = tryConnectDB(createDBDTO);
        if (!flag) {
            return "数据库连接失败";
        }

        //TODO 建库之前先从数据库表查询是否重名 和是否已经创建
        boolean falg2 = checkDBName(createDBDTO);
        if (!falg2) {
            return "数据库重名，请更名";
        }

        Connection connection = null;

        try {
            String schema = createDBDTO.getSchemaName();
            String jdbcurl = createDBDTO.getDbUrl();
            String username = createDBDTO.getDbUsername();
            String password = createDBDTO.getDbPassword();
            String driverClass = createDBDTO.getDriverClass();

            //建库语句
            String sqlCreateSchema = "CREATE DATABASE IF NOT EXISTS `#{schema}` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;";
            String sql2 = "use `#{schema}`;";

            //jdbd的方式与数据库建立连接
            Class.forName(driverClass);
            DriverManager.getConnection(jdbcurl, username, password);

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

            return "建库成功";

        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            return "建库失败";
        }
    }

    boolean tryConnectDB(CreateDbDTO createDBDTO) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(createDBDTO.getDbUrl(), createDBDTO.getDbUsername(), createDBDTO.getDbPassword());
            return true;
        } catch (SQLException e) {
            log.error("连接数据库失败",e.getMessage());
            return false;
        }finally {
            if (null != connection) {
                try {
                    connection.commit();
                    connection.close();
                } catch (SQLException e) {
                    log.error("数据库连接关闭失败",e.getMessage());
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
