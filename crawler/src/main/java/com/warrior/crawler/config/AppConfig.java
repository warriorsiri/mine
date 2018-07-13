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

	public static List<FieldFilter> fliters = new ArrayList<>();

	static {
		fliters.add(new ZjzFilter());
		fliters.add(new ZjzTbFilter());
	}

}
