package com.warrior.demo.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.warrior.demo.common.DataSourceContextHolder.DYNAMIC_DATA_SOURCE;

/**
 * 多数据源切换，可以扩展数据库选取策略
 * 
 * @author warrior
 * 2018年9月18日
 */
public class MultipleDataSources extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		DYNAMIC_DATA_SOURCE dataSource = DataSourceContextHolder.getDataSource();
		if (dataSource == null) {
			dataSource = DataSourceContextHolder.DYNAMIC_DATA_SOURCE.MASTER;
		}
		return dataSource.name();
	}

}
