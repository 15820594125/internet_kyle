package com.tensquare.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import util.IdWorker;

/**
 * 基础微服务启动类
 */
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    /**
     * 初始化工作
     */
    @Bean  // 单例的
    public IdWorker idWorker(){
        return new IdWorker();
    }

}
