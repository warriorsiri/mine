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
 * 
 * @author warrior 2018年9月19日
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class DataSourceAopConfig {

	/**
	 * 卧槽，写在pointCut上，或操作要用“||”不能用“or”
	 */
	@Pointcut("execution(* com.baomidou.mybatisplus.extension.service.IService.list*(..))  || execution(* com.baomidou.mybatisplus.extension.service.IService.get*(..))"
			+ " || execution(* com.baomidou.mybatisplus.extension.service.IService.page*(..)) "
			+ " || execution(* com.warrior.demo.service.*.select*(..)) || execution(* com.warrior.demo.service.*.find*(..))"
			+ " || execution(* com.warrior.demo.service.*.list*(..)) || execution(* com.warrior.demo.service.*.get*(..))")
	private void pointCutMethod() {
	}

	@Before("pointCutMethod()")
	public void setReadDataSource() {
		DataSourceContextHolder.setSlave();
		 log.debug("切换到读库！");
	}

	 //声明最终通知
	 @After("pointCutMethod()")
	 public void cleanDataSource() {
		 DataSourceContextHolder.cleanDataSource();
	  	 log.debug("清除当前线程的数据库选择");
	 }
}
