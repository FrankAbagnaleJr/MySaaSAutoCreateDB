package com.kyrie;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
//@MapperScan("com/kyrie/mapper")
public class MySaasAutoCreateDbApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySaasAutoCreateDbApplication.class, args);
    }
}
