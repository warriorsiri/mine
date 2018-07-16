package com.warrior.crawler.target.tjcn.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.warrior.crawler.config.AppConfig;
import com.warrior.crawler.target.FieldFilter;

/**
 * 固定资产提取过滤器
 * 
 * @author warrior
 * 2018年7月13日
 */
public class GdzcTbFilter implements FieldFilter {

	public final static String REGEX = "\\S*?\u5de5\u4e1a\u6295\u8d44\\S*?(" + AppConfig.YOY_MODIFIER
			+ "){1}(\\d+\\.?\\d*)";
	public final static Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public String getUnit() {
		return "%";
	}

	@Override
	public String getColName() {
		return "固定资产投入_同比";
	}

	@Override
	public String keyName() {
		return "GDZC_TB";
	}

	@Override
	public String extractValue(String content) {
		Matcher matcher = PATTERN.matcher(content);
		String value = null;
		String ud = null;
		if (matcher.find()) {
			ud = matcher.group(1);
			value = matcher.group(2);
			if (AppConfig.isNegative(ud)) {
				return "-" + value;
			}
			return value;
		}
		return null;
	}

}
