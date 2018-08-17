package com.warrior.crawler.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MenuUtils {

	public static final String MENU_URL = "http://data.stats.gov.cn/easyquery.htm";
	public static final String target_url = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsyd&rowcode=zb&colcode=sj&wds=%5B%7B%22wdcode%22%3A%22reg%22%2C%22valuecode%22%3A%22530000%22%7D%5D&dfwds=%5B%7B%22wdcode%22%3A%22zb%22%2C%22valuecode%22%3A%22A020203%22%7D%5D&k1=1534239778433";

	/**
	 * 获取叶子菜单
	 * 
	 * @param pid
	 * @param pname
	 * @param result
	 * <菜单层级 menuid>
	 */
	public static void getChildrenMenu(String pid, String pname, Map<String, String> result) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", pid);
		param.put("dbcode", "hgyd");
		param.put("wdcode", "zb");
		param.put("m", "getTree");
		String menuJson = HttpUtils.post(MENU_URL, param);
		if (StringUtils.isBlank(menuJson)) {
			return;
		}
		JSONArray menus = JSONArray.parseArray(menuJson);
		for (int i = 0; i < menus.size(); i++) {
			JSONObject menu = menus.getJSONObject(i);
			String menuName = menu.getString("name");
			String menuId = menu.getString("id");
			if (menu.getBooleanValue("isParent")) {
				getChildrenMenu(menuId, createKey(pname, menuName), result);
			} else {
				result.put(createKey(pname, menuName), menuId);
			}
		}
	}

	private static String createKey(String pname, String menuName) {
		return pname + "_" + menuName;
	}

	public static void main(String[] args) {
		Map<String, String> result = new HashMap<>();
		getChildrenMenu("A15", "国内贸易", result);
		System.out.println(result);
	}
}
