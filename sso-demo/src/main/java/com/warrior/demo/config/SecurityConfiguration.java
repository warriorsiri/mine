package com.warrior.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.warrior.demo.security.JwtTokenFilter;
import com.warrior.demo.security.TokenProvider;
import com.warrior.demo.security.shiro.ShiroRealm;

/**
 * 安全配置
 * 
 * @author Warrior 2018年3月25日
 */
@Configuration
public class SecurityConfiguration {

	@Bean("securityManager")
	public DefaultWebSecurityManager getManager(ShiroRealm realm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		// 使用自己的realm
		manager.setRealm(realm);

		/*
		 * 关闭shiro自带的session，详情见文档
		 * http://shiro.apache.org/session-management.html#SessionManagement-
		 * StatelessApplications%28Sessionless%29
		 */
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		manager.setSubjectDAO(subjectDAO);

		return manager;
	}

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager, TokenProvider tokenProvider) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new HashMap<>();
		filterMap.put("jwt", new JwtTokenFilter(tokenProvider));
		factoryBean.setFilters(filterMap);

		factoryBean.setSecurityManager(securityManager);
		factoryBean.setUnauthorizedUrl("/401");

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		factoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		factoryBean.setSuccessUrl("/index");

		/*
		 * 自定义url规则 http://shiro.apache.org/web.html#urls-
		 */
		Map<String, String> filterRuleMap = new HashMap<>();
		// 配置不会被拦截的链接 顺序判断
		filterRuleMap.put("/static/**", "anon");
		filterRuleMap.put("/favicon.ico", "anon");
		filterRuleMap.put("/login", "anon");
		// 访问401和404页面不通过我们的Filter
		filterRuleMap.put("/401", "anon");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterRuleMap.put("/logout", "logout");
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// 所有请求通过我们自己的JWT Filter
		filterRuleMap.put("/**", "jwt");

		factoryBean.setFilterChainDefinitionMap(filterRuleMap);
		return factoryBean;
	}

	/**
	 * 下面的代码是添加注解支持
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		// 强制使用cglib，防止重复代理和可能引起代理出错的问题
		// https://zhuanlan.zhihu.com/p/29161098
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
