package com.warrior.crawler.target.tjcn.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.warrior.crawler.config.AppConfig;
import com.warrior.crawler.target.FieldFilter;

/**
 * 利税总额同比
 * 
 * @author warrior
 * 2018年7月13日
 */
public class LszeTbFilter implements FieldFilter {

	public final static String REGEX = "\\S*?\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*?(\\u5229\\u7a0e|\\u7a0e\\u91d1){1}\\S*?("
			+ AppConfig.YOY_MODIFIER + "){1}(\\d+\\.?\\d*)";

	// 全年规模以上工业实现主营业务收入6166.39亿元，比上年增长12.9%；利润总额296.33亿元，利税总额526.23亿元，分别比上年增长3.2%和下降8.3%。
	public final static String SPEC_REGEX = "\\S*?\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*?(\\u5229\\u7a0e|\\u7a0e\\u91d1){1}\\S*?("
			+ AppConfig.YOY_MODIFIER + "){1}(\\d+\\.?\\d*)\\S*?(" + AppConfig.YOY_MODIFIER + ")*?(\\d+\\.?\\d*)";

	public final static Pattern PATTERN = Pattern.compile(REGEX);
	public final static Pattern SPEC_PATTERN = Pattern.compile(SPEC_REGEX);

	@Override
	public String getUnit() {
		return "%";
	}

	@Override
	public String getColName() {
		return "利税总额_同比";
	}

	@Override
	public String keyName() {
		return "LSZE_TB";
	}

	@Override
	public String extractValue(String content) {
		Matcher matcher = PATTERN.matcher(content);
		String value = null;
		String ud = null;
		if (matcher.find()) {
			String spec = matcher.group(0);
			if (spec.indexOf("分别") >= 0) {
				Matcher specMatcher = SPEC_PATTERN.matcher(content);
				if (specMatcher.find()) {
					int lri = spec.indexOf("利润");
					int lrs = spec.indexOf("利税");
					String sud = null;
					String svalue = null;
					if (lrs > lri) {
						sud = specMatcher.group(4);
						sud = sud == null ? specMatcher.group(2) : sud;
						svalue = AppConfig.isNegative(sud) ? "-" + specMatcher.group(5) : specMatcher.group(5);
					} else {
						sud = specMatcher.group(2);
						svalue = AppConfig.isNegative(sud) ? "-" + specMatcher.group(3) : specMatcher.group(3);
					}
					return svalue;
				}
			}
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
