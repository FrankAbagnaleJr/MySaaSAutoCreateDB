package com.kyrie;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
public class MySaasAutoCreateDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySaasAutoCreateDbApplication.class, args);
    }

}
