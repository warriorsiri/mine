package com.warrior.crawler.target;

/**
 * 字段过滤器接口
 * 
 * @author warrior
 * 2018年7月13日
 */
public interface FieldFilter {

	/**
	 * 单位
	 * 
	 * @return
	 */
	public String getUnit();

	/**
	 * 列名
	 * 
	 * @return
	 */
	public String getColName();

	/**
	 * 结果的key
	 * 
	 * @return
	 */
	public String keyName();

	/**
	 * 提取值
	 * 
	 * @param content
	 * @return 没有返回null
	 */
	public String extractValue(String content);
}
