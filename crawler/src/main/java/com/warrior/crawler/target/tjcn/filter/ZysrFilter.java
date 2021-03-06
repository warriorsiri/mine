package com.warrior.crawler.target.tjcn.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.warrior.crawler.target.FieldFilter;

/**
 * 主营业务收入提取过滤器
 * 
 * @author warrior
 * 2018年7月13日
 */
public class ZysrFilter implements FieldFilter {

	public final static String REGEX = "\\S*?\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*?(\\u4e3b\\u8425\\u4e1a\\u52a1\\u6536\\u5165){1}\\S*?(\\d+\\.?\\d*){1}(%)*";
	public final static Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public String getUnit() {
		return "亿元";
	}

	@Override
	public String getColName() {
		return "主营业务收入";
	}

	@Override
	public String keyName() {
		return "ZYYWSR";
	}

	@Override
	public String extractValue(String content) {
		Matcher matcher = PATTERN.matcher(content);
		if (matcher.find()) {
			String ec = matcher.group(0);
			if (ec.indexOf("每百元") >= 0) {
				return null;
			}
			if (matcher.group(3) != null) {
				return null;
			}
			return matcher.group(2);
		}
		return null;
	}

}
