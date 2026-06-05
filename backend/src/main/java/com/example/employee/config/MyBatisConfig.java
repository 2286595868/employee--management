package com.example.employee.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.employee.mapper")
public class MyBatisConfig {
}
