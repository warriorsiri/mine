package com.warrior.crawler.target.tjcn.filter;

import com.warrior.crawler.target.FieldFilter;

/**
 * 城市规模
 * 
 * @author warrior
 * 2018年7月13日
 */
public class CbgmFilter implements FieldFilter {

	public final static String REGEX = "\\S*\\u89c4\\u6a21\\u4ee5\\u4e0a\\S*";

	@Override
	public String getUnit() {
		return "d";
	}

	@Override
	public String getColName() {
		return "城报企业规模";
	}

	@Override
	public String keyName() {
		return "CBGM";
	}

	@Override
	public String extractValue(String content) {
		return "规模以上";
	}

}
