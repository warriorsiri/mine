package com.warrior.crawler.time;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.warrior.crawler.common.HttpUtils;
import com.warrior.crawler.common.MenuUtils;
import com.warrior.crawler.poi.ExcelData;

import cn.hutool.core.date.DateUtil;

/**
 * 月度，季度和年度的数据抓取
 * 
 * @author warrior
 * 2018年8月17日
 */
public class TimeCrawler {

	private static final Logger log = LoggerFactory.getLogger(TimeCrawler.class);

	private static final String TIME_URL = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=hgyd&rowcode=zb&colcode=sj&wds=%5B%5D&dfwds=%5B%7B%22wdcode%22%3A%22sj%22%2C%22valuecode%22%3A%22${param}%22%7D%5D";
	private static final String MONTH = "LAST36";
	private static final String SEASON = "LAST18";
	private static final String YEAR = "LAST20";
	private static final String DATA_URL = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=hgyd&rowcode=zb&colcode=sj&wds=%5B%5D&dfwds=%5B%7B%22wdcode%22%3A%22zb%22%2C%22valuecode%22%3A%22${param}%22%7D%5D";

	private static final Pattern PATTERN = Pattern.compile("\\((\\d+)-(\\S+)\\)$");

	/**
	 * 抓取数据
	 * 
	 * @param menuId
	 * 菜单ID
	 * @param name
	 * 菜单名称
	 * @param timeType
	 * month season year
	 * @return
	 */
	public ExcelData crawler(String menuId, String name, String timeType) {
		HttpUtils.getCookie();
		log.debug("开始解释菜单名={}，menuId={}的子菜单……", name, menuId);
		Map<String, String> result = new HashMap<>();
		MenuUtils.getChildrenMenu(menuId, name, result);
		log.debug("解释菜单名={}，menuId={}的子菜单完成！", name, menuId);

		// 先初始化时间
		HttpUtils.get(getTimeUrl(getTime(timeType)));
		boolean isCustom = false;
		for (Entry<String, String> kv : result.entrySet()) {
			String ct = getCustomTime(kv.getKey());
			if (ct != null) {
				HttpUtils.get(getTimeUrl(ct));
				isCustom = true;
			} else if (isCustom) { // 如果前一次自定义过时间，这次需要先重置时间
				HttpUtils.get(getTimeUrl(getTime(timeType)));
				isCustom = false;
			}
			// 获取数据
			String json = HttpUtils.get(getDataUrl(kv.getValue()));
			if (StringUtils.isBlank(json)) {
				log.warn("菜单名={}，menuId={}，获取不到数据。", kv.getKey(), kv.getValue());
				continue;
			}
			System.out.println(json);
		}
		return null;
	}

	/**
	 * 获取数据url
	 * 
	 * @param menuId
	 * @return
	 */
	private String getDataUrl(String menuId) {
		return DATA_URL.replace("${param}", menuId);
	}

	private String getTime(String timeType) {
		switch (timeType) {
		case "month":
			return MONTH;
		case "season":
			return SEASON;
		case "year":
			return YEAR;
		default:
			break;
		}
		return null;
	}

	/**
	 * 获取时间url
	 * 
	 * @param ct
	 * @return
	 */
	private String getTimeUrl(String ct) {
		return TIME_URL.replace("${param}", ct);

	}

	/**
	 * 获取自定义的时间
	 * 例如 工业分大类行业出口交货值(2003-2011)
	 * 
	 * @param menuName
	 * @return
	 */
	private String getCustomTime(String menuName) {
		Matcher matcher = PATTERN.matcher(menuName);
		if (matcher.find()) {
			if (!NumberUtils.isNumber(matcher.group(2))) {
				return matcher.group(1) + "-" + DateUtil.year(new Date());
			} else {
				return matcher.group(1) + "-" + matcher.group(2);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		TimeCrawler crawler = new TimeCrawler();
		// System.out.println(crawler.getCustomTime("国内贸易_限额以上住宿和餐饮业经营情况(-2009)"));
		// System.out.println(crawler.getCustomTime("国内贸易_限额以上住宿和餐饮业经营情况(2010-2017)"));
		// System.out.println(crawler.getCustomTime("国内贸易_限额以上住宿和餐饮业经营情况(2010-至今)"));
		// System.out.println(crawler.getCustomTime("国内贸易_限额以上住宿和餐饮业经营情况"));
		// System.out.println(crawler.getCustomTime("国内贸易_限额以上住宿和餐饮业经营情况2010"));
		System.out.println(crawler.getTimeUrl("2012-2017"));
		System.out.println(crawler.getTimeUrl("LAST36"));
	}
}
