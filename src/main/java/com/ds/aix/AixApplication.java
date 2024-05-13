package com.ds.aix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
@SpringBootApplication(scanBasePackages = {"com.ds.aix"},
                       exclude = {DataSourceAutoConfiguration.class})
public class AixApplication {
    public static void main(String[] args) {
        SpringApplication.run(AixApplication.class, args);
    }
}
