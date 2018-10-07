package com.warrior.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Warrior 2018年10月6日
 */
// 使用动态数据源，需要把spring boot的自动数据源配置去掉
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableTransactionManagement
public class SpringDruidDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDruidDemoApplication.class, args);
	}
}
