package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
//@MapperScan(basePackages = "com.imooc.mapper")
//@MapperScan(basePackages = "com.imooc.mapper")
// 扫描所有包以及相关组件包
//@ComponentScan(basePackages = {"com.imooc", "org.n3r.idworker"})
//@EnableTransactionManagement
@EnableScheduling       // 开启定时任务
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}