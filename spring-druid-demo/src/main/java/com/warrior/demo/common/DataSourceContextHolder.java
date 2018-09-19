package com.warrior.demo.common;

/**
 * 数据源线程变量
 * 
 * @author warrior
 * 2018年9月18日
 */
public class DataSourceContextHolder {

	public enum DYNAMIC_DATA_SOURCE {
		MASTER, // 主库（用于写操作）
		SLAVE// 从库（用于读操作）
	}

	private static final ThreadLocal<DYNAMIC_DATA_SOURCE> contextHolder = new InheritableThreadLocal<>();

	/**
	 * 设置数据源
	 * 
	 * @param db
	 */
	public static void setDataSource(DYNAMIC_DATA_SOURCE db) {
		contextHolder.set(db);
	}

	public static void setMaster() {
		contextHolder.set(DYNAMIC_DATA_SOURCE.MASTER);
	}

	public static void setSlave() {
		contextHolder.set(DYNAMIC_DATA_SOURCE.SLAVE);
	}

	public static boolean isMaster() {
		return DYNAMIC_DATA_SOURCE.MASTER == contextHolder.get();
	}

	public static boolean isSlave() {
		return DYNAMIC_DATA_SOURCE.SLAVE == contextHolder.get();
	}

	/**
	 * 取得当前数据源
	 * 
	 * @return
	 */
	public static DYNAMIC_DATA_SOURCE getDataSource() {
		return contextHolder.get();
	}

	/**
	 * 清除上下文数据
	 */
	public static void cleanDataSource() {
		contextHolder.remove();
	}
}
