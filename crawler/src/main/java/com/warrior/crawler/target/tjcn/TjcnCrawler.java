package com.warrior.crawler.target.tjcn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.warrior.crawler.config.AppConfig;
import com.warrior.crawler.target.FieldFilter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.util.ReUtil;

/**
 * 中国统计信息网 网页处理类
 * http://www.tjcn.org/tjgb/
 * 
 * @author warrior
 * 2018年7月11日
 */
public class TjcnCrawler {

	public static final String MAIN_URL = "http://www.tjcn.org/tjgb";
	public static final String MAIN = "http://www.tjcn.org";

	/**
	 * 爬取信息
	 * 
	 * @param addr
	 * 地点
	 * @param year
	 * 年份
	 * @param path
	 * excel保存路径
	 * @param dealPro
	 * 是否抓取省数据
	 * @throws Exception
	 */
	public void crawler(String addrs, String year, String path, boolean dealPro) throws Exception {
		System.out.println("人工智能赵帅2.1开始抓取数据……");
		Document doc = Jsoup.connect(MAIN_URL).get();
		Elements atags = doc.getElementsByClass("zlm").select("a");

		JSONArray addrArray = JSONArray.parseArray(addrs);
		String[] yearArray = year.split("-");
		Integer minYear = NumberUtils.createInteger(yearArray[0]);
		Integer maxYear = NumberUtils.createInteger(yearArray[1]);

		List<Tuple> targets = new ArrayList<>();
		for (int i = 0; i < addrArray.size(); i++) {
			JSONObject pObj = addrArray.getJSONObject(i);
			String province = pObj.getString("porvince");
			String provinceUrl = findProvinceUrl(atags, province);
			// 找省的目标地址
			if (dealPro) {
				findProTargetUrl(targets, provinceUrl, province, minYear, maxYear);
			}

			// 找市的目标地址
			List<String> addStr = getCityAddrStr(pObj);
			findCityTargetUrl(targets, provinceUrl, province, addStr, minYear, maxYear);
		}
		// System.out.println(targets);
		List<Map<String, String>> results = new ArrayList<>();
		for (Tuple item : targets) {
			Map<String, String> result = new HashMap<>();
			String target = item.get(0);
			String itemyear = item.get(1);
			String url = item.get(2);
			filterValue(target, itemyear, result, url);
			if (result.size() > 0) {
				results.add(result);
			}
		}
		System.out.println("数据抓取完毕，开始生成excel……");
		// System.out.println(results);
		String filePath = createExcel(results, path);
		System.out.println("Excel生成成功，文件地址：" + filePath);
	}

	private void findProTargetUrl(List<Tuple> targets, String provinceUrl, String province, Integer minYear,
			Integer maxYear) throws IOException {
		Document doc = Jsoup.connect(provinceUrl).get();
		Elements alinks = doc.getElementsByClass("box").get(0).select("li > a");

		for (Element a : alinks) {
			String targetName = getTargetName(a.attr("title"));
			if (province.equals(targetName)) {
				Integer year = getYear(a.attr("title"));
				if (year < minYear) {
					return;
				}
				if (year != null && year >= minYear && year <= maxYear) {
					Tuple target = new Tuple(province, year + "年", MAIN + a.attr("href"));
					targets.add(target);
				}
			}
		}

		String nextPageUrl = getNextPage(doc.getElementsByClass("epages").select("a"));
		if (nextPageUrl != null) {
			findProTargetUrl(targets, nextPageUrl, province, minYear, maxYear);
		}

	}

	private String createExcel(List<Map<String, String>> results, String path) {
		// 第一步创建workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步创建sheet
		HSSFSheet sheet = wb.createSheet("城市公报");

		// 第一行
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0); // 第一个单元格
		cell.setCellValue("head");
		cell = row1.createCell(1); // 第二个单元格
		cell.setCellValue("名字=城市公报");

		// 第二行 单位
		HSSFRow row2 = sheet.createRow(1);
		int fieldSize = AppConfig.getFilters().size();
		for (int i = 0; i < 3 + fieldSize; i++) {
			HSSFCell tc = row2.createCell(i);
			if (i < 3) {
				tc.setCellValue("d");
			} else {
				tc.setCellValue(AppConfig.getFilters().get(i - 3).getUnit());
			}
		}

		// 第三行 列名
		HSSFRow row3 = sheet.createRow(2);
		row3.createCell(0).setCellValue("城报省市");
		row3.createCell(1).setCellValue("城报年月");
		row3.createCell(2).setCellValue("城报企业规模");
		for (int i = 0; i < fieldSize; i++) {
			row3.createCell(i + 3).setCellValue(AppConfig.getFilters().get(i).getColName());
		}

		// 数值
		for (int i = 0; i < results.size(); i++) {
			Map<String, String> item = results.get(i);
			HSSFRow row = sheet.createRow(i + 3);
			row.createCell(0).setCellValue(item.get("TARGET"));
			row.createCell(1).setCellValue(item.get("YEAR"));
			row.createCell(2).setCellValue(item.get("CBGM"));
			int j = 0;
			for (FieldFilter filter : AppConfig.getFilters()) {
				row.createCell(j + 3).setCellValue(item.getOrDefault(filter.keyName(), ""));
				j++;
			}
		}
		// 第六步将生成excel文件保存到指定路径下
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String filePath = path + "/" + createFileName() + ".xls";
		try {
			FileOutputStream fout = new FileOutputStream(filePath);
			wb.write(fout);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePath;

	}

	private String createFileName() {
		return "重点联系城市_v" + DateUtil.format(new Date(), "yyMMdd");
	}

	/**
	 * 提取值
	 * 
	 * @param target
	 * @param year
	 * @param result
	 * @param url
	 * @throws IOException
	 */
	private void filterValue(String target, String year, Map<String, String> result, String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements divs = doc.getElementsByClass("xwnr").select("div");

		for (Element div : divs) {
			for (FieldFilter fileter : AppConfig.getFilters()) {
				if (!result.containsKey(fileter.keyName())) { // 前面已经找到，后面就不再匹配
					String value = fileter.extractValue(div.html());
					if (value != null) {
						result.put("TARGET", target);
						result.put("YEAR", year);
						result.put("CBGM", "规模以上");
						result.put(fileter.keyName(), value);
						if (result.size() == (AppConfig.getFilters().size() + 3)) { // 所有值已经填充，不需要往下找
							return;
						}
					}
				}
			}
		}
		String nextUrl = getNextPage(doc.getElementsByClass("pageLink").select("a"));
		if (nextUrl != null) {
			filterValue(target, year, result, nextUrl);
		}
	}

	private List<String> getCityAddrStr(JSONObject pObj) {
		List<String> strs = new ArrayList<>();
		JSONArray citys = pObj.getJSONArray("city");
		strs.addAll(citys.toJavaList(String.class));
		return strs;
	}

	/**
	 * 查找目标url
	 * 
	 * @param targets
	 * <>
	 * @param provinceUrl
	 * @param addStr
	 * @param addStr2
	 * @param minYear
	 * @param maxYear
	 * @throws IOException
	 */
	private void findCityTargetUrl(List<Tuple> targets, String provinceUrl, String province, List<String> addStr,
			Integer minYear, Integer maxYear) throws IOException {
		Document doc = Jsoup.connect(provinceUrl).get();
		Elements alinks = doc.getElementsByClass("box").get(0).select("li > a");
		String title = alinks.first().attr("title");
		Integer firstYear = getYear(title);
		Integer lastYear = getYear(alinks.last().attr("title"));
		if (firstYear < minYear) { // 第一条数据的年份已经不在范围内，
			return;
		}
		if (lastYear > maxYear) { // 最后一条数据的年份已经不在范围内，下一页
			String nextPageUrl = getNextPage(doc.getElementsByClass("epages").select("a"));
			if (nextPageUrl != null) {
				findCityTargetUrl(targets, nextPageUrl, province, addStr, minYear, maxYear);
			}
			return;
		}
		for (Element a : alinks) {
			Integer year = getYear(a.attr("title"));
			String targetName = getTargetName(a.attr("title"));
			if (year != null && year >= minYear && year <= maxYear) {
				String keyName = checkName(province, addStr, targetName);
				if (keyName != null) {
					Tuple target = new Tuple(keyName, year + "年", MAIN + a.attr("href"));
					targets.add(target);
				}
			}
		}
		String nextPageUrl = getNextPage(doc.getElementsByClass("epages").select("a"));
		if (nextPageUrl != null) {
			findCityTargetUrl(targets, nextPageUrl, province, addStr, minYear, maxYear);
		}
	}

	private String checkName(String province, List<String> addStr, String targetName) {
		for (String name : addStr) {
			if (name.indexOf(targetName) >= 0) {
				return province + "." + name;
			}
		}
		return null;
	}

	private String getTargetName(String title) {
		return ReUtil.getGroup0("\\W+\\D", title);
	}

	private String getNextPage(Elements pages) {
		if (CollectionUtil.isEmpty(pages)) {
			return null;
		}
		for (Element page : pages) {
			if ("下一页".equals(page.html())) {
				return MAIN + page.attr("href");
			}
		}
		return null;
	}

	private Integer getYear(String title) {
		return NumberUtils.createInteger(ReUtil.getGroup0("\\d{4}", title));
	}

	/**
	 * 找到目标页
	 * 
	 * @param doc
	 * @param addr
	 * @return
	 */
	private String findProvinceUrl(Elements atags, String addr) {
		for (Element a : atags) {
			if (addr.indexOf(a.html()) >= 0) {
				return MAIN + a.attr("href");
			}
		}
		return null;
	}
}
