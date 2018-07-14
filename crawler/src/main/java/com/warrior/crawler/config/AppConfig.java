package com.warrior.crawler.config;

import java.util.ArrayList;
import java.util.List;

import com.warrior.crawler.target.FieldFilter;
import com.warrior.crawler.target.tjcn.filter.ZjzFilter;
import com.warrior.crawler.target.tjcn.filter.ZjzTbFilter;

/**
 * 程序配置
 * 
 * @author warrior
 * 2018年7月13日
 */
public class AppConfig {

	private static List<FieldFilter> fliters = new ArrayList<>();

	public final static String YOY_MODIFIER = "\\u589e\\u957f|\\u4e0a\\u5347|\\u4e0b\\u964d";
	public final static String NEGATIVE_CONTENT = "下降";

	static {
		fliters.add(new ZjzFilter());
		fliters.add(new ZjzTbFilter());
	}

	public static List<FieldFilter> getFilters() {
		return fliters;
	}

	public static boolean isNegative(String str) {
		if (NEGATIVE_CONTENT.indexOf(str) >= 0) {
			return true;
		}
		return false;
	}

}
