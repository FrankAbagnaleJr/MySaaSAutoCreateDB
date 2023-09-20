package com.kyrie.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyrie.pojo.CreateDbInfo;
import com.kyrie.service.CreateDBService;
import com.kyrie.utils.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 这个类是用来项目启动初始化多数据源的，从数据库中查出用户的数据源，循环添加到项目中
 */
@Component
@Slf4j
public class DynamicDataSourceInitConfig {

    @Autowired
    CreateDBService createDBService;

    @Resource
    DynamicRoutingDataSource dynamicRoutingDataSource;

    @PostConstruct
    public void addAllDataSourceToCache(){
        //条件，只查状态为1的数据，就是已经建库完成的。
        LambdaQueryWrapper<CreateDbInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CreateDbInfo::getStatus,1);
        //查出数据库所有的 用户数据库连接信息
        List<CreateDbInfo> list = createDBService.list(lqw);

        //循环添加到数据源中
        for (CreateDbInfo createDbInfo : list) {
            String s = createDBService.addDataSourceToCache(createDbInfo);
            log.info(s);
        }
    }
}
