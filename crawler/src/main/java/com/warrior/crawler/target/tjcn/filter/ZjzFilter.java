package com.warrior.crawler.target.tjcn.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.warrior.crawler.target.FieldFilter;

/**
 * 增加值提取过滤器
 * 
 * @author warrior
 * 2018年7月13日
 */
public class ZjzFilter implements FieldFilter {

	public final static String REGEX = "\\S*?\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*?(\\u589e\\u52a0\\u503c){1}(\\d+\\.?\\d*){1}(%)*";
	public final static Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public String getUnit() {
		return "亿元";
	}

	@Override
	public String getColName() {
		return "工业增加值";
	}

	@Override
	public String keyName() {
		return "ZJZ";
	}

	@Override
	public String extractValue(String content) {
		Matcher matcher = PATTERN.matcher(content);
		if (matcher.find()) {
			if (matcher.group(3) != null) {
				return null;
			}
			return matcher.group(2);
		}
		return null;
	}

}
