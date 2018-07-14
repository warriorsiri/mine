package com.warrior.crawler.target.tjcn.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.warrior.crawler.config.AppConfig;
import com.warrior.crawler.target.FieldFilter;

/**
 * 利润总额同比
 * 
 * @author warrior
 * 2018年7月13日
 */
public class LrzeTbFilter implements FieldFilter {

	public final static String REGEX = "\\S*?\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*?(\\u5229\\u6da6){1}\\S*?("
			+ AppConfig.YOY_MODIFIER + "){1}(\\d+\\.?\\d*)";
	public final static Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public String getUnit() {
		return "%";
	}

	@Override
	public String getColName() {
		return "利润总额_同比";
	}

	@Override
	public String keyName() {
		return "LRZE_TB";
	}

	@Override
	public String extractValue(String content) {
		Matcher matcher = PATTERN.matcher(content);
		String value = null;
		String ud = null;
		if (matcher.find()) {
			ud = matcher.group(2);
			value = matcher.group(3);
			if (AppConfig.isNegative(ud)) {
				return "-" + value;
			}
			return value;
		}
		return null;
	}

}
