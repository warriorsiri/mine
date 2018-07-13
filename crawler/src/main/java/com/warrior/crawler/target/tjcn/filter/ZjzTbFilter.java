package com.warrior.crawler.target.tjcn.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.warrior.crawler.target.FieldFilter;

/**
 * 工业增加值同比
 * 
 * @author warrior
 * 2018年7月13日
 */
public class ZjzTbFilter implements FieldFilter {

	public final static String REGEX = "\\S*\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*?(\\u589e\\u52a0\\u503c){1}\\S*?(\\u589e\\u957f|\\u4e0a\\u5347|\\u4e0b\\u964d){1}(\\d+\\.?\\d*)";
	public final static Pattern PATTERN = Pattern.compile(REGEX);

	public final static String NEGATIVE_CONTENT = "下降";

	@Override
	public String getUnit() {
		return "%";
	}

	@Override
	public String getColName() {
		return "工业增加值_同比";
	}

	@Override
	public String keyName() {
		return "ZJZ_TB";
	}

	@Override
	public String extractValue(String content) {
		Matcher matcher = PATTERN.matcher(content);
		String value = null;
		String ud = null;
		if (matcher.find()) {
			ud = matcher.group(2);
			value = matcher.group(3);
			if (NEGATIVE_CONTENT.indexOf(ud) >= 0) {
				return "-" + value;
			}
			return value;
		}
		return null;
	}

}
