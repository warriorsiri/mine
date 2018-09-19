package com.warrior.demo.common;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * 根据事务配置切换数据源
 * 
 * @author warrior
 * 2018年9月19日
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

	private static final long serialVersionUID = -8259986469326635231L;

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		boolean readOnly = definition.isReadOnly();
		// 前面根据方法名称可能已经设置了数据库
		if (DataSourceContextHolder.getDataSource() == null) {
			// 只读模式
			if (readOnly) {
				DataSourceContextHolder.setSlave();
			} else {// 读写模式
				DataSourceContextHolder.setMaster();
			}
		}
		super.doBegin(transaction, definition);
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		DataSourceContextHolder.cleanDataSource();
	}

}
