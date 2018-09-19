package com.warrior.demo.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.warrior.demo.common.DataSourceContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源切换AOP
 * @author warrior
 * 2018年9月19日
 */
@Aspect
@Component
@Slf4j
@Order(1)  
public class DataSourceAopConfig {


	@Pointcut("execution(* com.baomidou.mybatisplus.extension.service.IService.list*(..))  or execution(* com.baomidou.mybatisplus.extension.service.IService.get*(..))"
			+ " or execution(* com.baomidou.mybatisplus.extension.service.IService.page*(..)) "
			+ " or execution(* com.warrior.demo.service.*.select*(..)) or execution(* com.warrior.demo.service.*.find*(..))"
			+ " or execution(* com.warrior.demo.service.*.list*(..)) or execution(* com.warrior.demo.service.*.get*(..))")
    private void pointCutMethod() {  
    }  
	
	 @Before("execution(* com.baomidou.mybatisplus.extension.service.IService.list*(..))  or execution(* com.baomidou.mybatisplus.extension.service.IService.get*(..))"
				+ " or execution(* com.baomidou.mybatisplus.extension.service.IService.page*(..)) "
				+ " or execution(* com.warrior.demo.service.*.select*(..)) or execution(* com.warrior.demo.service.*.find*(..))"
				+ " or execution(* com.warrior.demo.service.*.list*(..)) or execution(* com.warrior.demo.service.*.get*(..))")  
    public void setReadDataSourceType() {
        DataSourceContextHolder.setSlave();
        log.debug("dataSource切换到：Read");
    }
	 
	 //声明最终通知  
    @After("pointCutMethod()")  
    public void doAfter() {  
    	DataSourceContextHolder.cleanDataSource();
        log.info("MessageQueueAopAspect1:doAfter");  
    }  
}
