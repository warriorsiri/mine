package com.warrior.crawler.config;

import java.util.ArrayList;
import java.util.List;

import com.warrior.crawler.target.FieldFilter;
import com.warrior.crawler.target.tjcn.filter.GdzcFilter;
import com.warrior.crawler.target.tjcn.filter.GdzcTbFilter;
import com.warrior.crawler.target.tjcn.filter.LrzeFilter;
import com.warrior.crawler.target.tjcn.filter.LrzeTbFilter;
import com.warrior.crawler.target.tjcn.filter.LszeFilter;
import com.warrior.crawler.target.tjcn.filter.LszeTbFilter;
import com.warrior.crawler.target.tjcn.filter.ZjzFilter;
import com.warrior.crawler.target.tjcn.filter.ZjzTbFilter;
import com.warrior.crawler.target.tjcn.filter.ZysrFilter;
import com.warrior.crawler.target.tjcn.filter.ZysrTbFilter;

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
		fliters.add(new ZysrFilter());
		fliters.add(new ZysrTbFilter());
		fliters.add(new LrzeFilter());
		fliters.add(new LrzeTbFilter());
		fliters.add(new LszeFilter());
		fliters.add(new LszeTbFilter());
		fliters.add(new GdzcFilter());
		fliters.add(new GdzcTbFilter());

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
