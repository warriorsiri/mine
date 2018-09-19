package com.warrior.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.warrior.demo.common.DataSourceContextHolder;
import com.warrior.demo.common.MultipleDataSources;

/**
 * DataSource 的配置不能写到MybatisPlusConfig里面，和MapperScannerConfigurer冲突
 * 
 * @author warrior
 * 2018年9月19日
 */
@Configuration
public class DruidConfiguration {
	/**
	 * 配置监控服务器
	 *
	 * @return 返回监控注册的servlet对象
	 */
	@Bean
	public ServletRegistrationBean<StatViewServlet> statViewServlet() {
		ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(
				new StatViewServlet(), "/druid/*");
		// 添加IP白名单
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
		servletRegistrationBean.addInitParameter("deny", "192.168.25.123");
		// 添加控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", "druid");
		servletRegistrationBean.addInitParameter("loginPassword", "123456");
		// 是否能够重置数据 如果设置为false，页面上依然有操作，不过不会起作用
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	/**
	 * 配置服务过滤器
	 *
	 * @return 返回过滤器配置对象
	 */
	@Bean
	public FilterRegistrationBean<WebStatFilter> statFilter() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(
				new WebStatFilter());
		// 添加过滤规则
		filterRegistrationBean.addUrlPatterns("/*");
		// 忽略过滤格式
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
		return filterRegistrationBean;
	}

	/**
	 * 按理说现在druid就搭建好了可以通过http://localhost:8080/druid/index.html进行正常访问了，但是在操作中我发现sql监控并没有起到作用，
	 * 也就是并没有sql监控的记录，在多次查阅资料后，终于找到解决办法，虽然我们在配置文件application.properties中已经配置了druid数据源，
	 * 但是在这里我们需要再次将这个DataSource配置到java配置中，这里我们将这个配置直接写入到启动类中。
	 * https://blog.csdn.net/weixin_39715550/article/details/79375080
	 * 
	 * @return
	 */
	@Bean(name = "master")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource master() throws ClassNotFoundException {
		return new DruidDataSource();
	}

	@Bean(name = "slave")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slave() {
		return new DruidDataSource();
	}

	/**
	 * 动态数据源配置
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public DataSource multipleDataSource(@Qualifier("master") DataSource master, @Qualifier("slave") DataSource slave) {
		MultipleDataSources multipleDataSource = new MultipleDataSources();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceContextHolder.DYNAMIC_DATA_SOURCE.MASTER.name(), master);
		targetDataSources.put(DataSourceContextHolder.DYNAMIC_DATA_SOURCE.SLAVE.name(), slave);
		// 添加数据源
		multipleDataSource.setTargetDataSources(targetDataSources);
		// 设置默认数据源
		multipleDataSource.setDefaultTargetDataSource(master);
		return multipleDataSource;
	}

	@Bean("sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(multipleDataSource(master(), slave()));

		MybatisConfiguration configuration = new MybatisConfiguration();
		// configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setCacheEnabled(false);
		sqlSessionFactory.setConfiguration(configuration);
		// sqlSessionFactory.setPlugins(new Interceptor[] { //
		// PerformanceInterceptor(),OptimisticLockerInterceptor()
		// paginationInterceptor() // 添加分页功能
		// });
		// sqlSessionFactory.setGlobalConfig(globalConfiguration());
		return sqlSessionFactory.getObject();
	}

	// @Bean 使用方法aop来切换数据源
	// public DataSourceTransactionManager
	// dataSourceTransactionManager(DataSource dataSources) {
	// DynamicDataSourceTransactionManager manager = new
	// DynamicDataSourceTransactionManager();
	// manager.setDataSource(dataSources);
	// return manager;
	// }
}
