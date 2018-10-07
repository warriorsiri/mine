package com.warrior.demo;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * mybatis plus 代码生成器
 * 
 * @author Warrior 2018年10月7日
 */
public class GeneratorServiceEntity {

	private static final String ENTITY_NAME = "entity";
	private static final String CONTROLLER_NAME = "rest";
	private static final String OUTPUT_DIR = "E:\\workspace\\mine-git\\spring-druid-demo\\src\\main\\java";
	private static final String PACKAGE_NAME = "com.warrior.demo";
	private static final String AUTHOR = "Warrior";

	private static final String DB_URL = "jdbc:postgresql://101.200.62.126:15432/Test123";
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_USER_NAME = "postgres";
	private static final String DB_PASSWORD = "dgchinapgsql@171026";

	@Test
	public void generateCode() {
		generateByTables(false, "sys_user");
	}

	@Test
	public void deleteCode() throws IOException {
		deleteEntity("sysUser");
	}

	/**
	 * @param serviceNameStartWithI
	 *            user -> UserService, 设置成true: user -> IUserService
	 * @param packageName
	 * @param tableNames
	 */
	private void generateByTables(boolean serviceNameStartWithI, String... tableNames) {
		GlobalConfig config = new GlobalConfig();
		config.setActiveRecord(false).setEnableCache(false) // XML 二级缓存
				.setBaseResultMap(true).setBaseColumnList(true).setAuthor(AUTHOR).setOutputDir(OUTPUT_DIR)
				.setFileOverride(true);
		if (!serviceNameStartWithI) {
			config.setServiceName("%sService");
		}

		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.POSTGRE_SQL).setUrl(DB_URL).setUsername(DB_USER_NAME).setPassword(DB_PASSWORD)
				.setDriverName(DB_DRIVER);

		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setCapitalMode(true).setEntityLombokModel(false).setNaming(NamingStrategy.underline_to_camel)
				// .setTablePrefix("sys_", "live_")// 此处可以修改为您的表前缀
				.setEntityColumnConstant(true) // 生成字段常量
				.setInclude(tableNames)// 修改替换成你需要的表名，多个表名传数组
				.setRestControllerStyle(true);

		new AutoGenerator().setTemplateEngine(new FreemarkerTemplateEngine()).setGlobalConfig(config)
				.setDataSource(dataSourceConfig).setStrategy(strategyConfig).setPackageInfo(new PackageConfig()
						.setParent(PACKAGE_NAME).setController(CONTROLLER_NAME).setEntity(ENTITY_NAME))
				.execute();
	}

	private void deleteEntity(String... entityNames) throws IOException {
		for (String entity : entityNames) {
			String dir = joinPath(OUTPUT_DIR, PACKAGE_NAME);
			// 删除controller
			FileUtils.forceDeleteOnExit(new File(dir + "\\" + CONTROLLER_NAME + "\\" + entity + "Controller.java"));
			// 删除serviceImpl
			FileUtils.forceDeleteOnExit(new File(dir + "\\service\\impl\\" + entity + "ServiceImpl.java"));
			// 删除service
			FileUtils.forceDeleteOnExit(new File(dir + "\\service\\" + entity + "Service.java"));
			// 删除mapper
			FileUtils.forceDeleteOnExit(new File(dir + "\\mapper\\" + entity + "Mapper.java"));
			// 删除mapper.xml
			FileUtils.forceDeleteOnExit(new File(dir + "\\mapper\\xml\\" + entity + "Mapper.xml"));
			// 删除entity
			FileUtils.forceDeleteOnExit(new File(dir + "\\" + ENTITY_NAME + "\\" + entity + ".java"));
			System.out.println("删除完成！");
		}
	}

	private String joinPath(String parentDir, String packageName) {
		if (StringUtils.isEmpty(parentDir)) {
			parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
		}
		if (!StringUtils.endsWith(parentDir, File.separator)) {
			parentDir += File.separator;
		}
		packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
		return parentDir + packageName;
	}
}
