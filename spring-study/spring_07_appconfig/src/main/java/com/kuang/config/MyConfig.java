package com.kuang.config;

import com.kuang.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@Configuration代表这是一个配置类
@Configuration
@ComponentScan("com.kuang.pojo")
@Import(MyConfig2.class)
public class MyConfig {
    //这个方法的名字相当于bean标签的id属性
    //这个方法的返回值相当于bean标签的class属性
    @Bean
    public User getUser(){
        return new User();
    }
}
