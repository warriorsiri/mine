package com.warrior.crawler.target.tjcn.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.warrior.crawler.target.FieldFilter;

/**
 * 固定资产提取过滤器
 * 
 * @author warrior
 * 2018年7月13日
 */
public class GdzcFilter implements FieldFilter {

	public final static String REGEX = "\\S*?\u5de5\u4e1a\u6295\u8d44(\\d+\\.?\\d*){1}(%)*";
	public final static Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public String getUnit() {
		return "亿元";
	}

	@Override
	public String getColName() {
		return "固定资产投入";
	}

	@Override
	public String keyName() {
		return "GDZC";
	}

	@Override
	public String extractValue(String content) {
		Matcher matcher = PATTERN.matcher(content);
		if (matcher.find()) {
			if (matcher.group(2) != null) {
				return null;
			}
			return matcher.group(1);
		}
		return null;
	}

}
