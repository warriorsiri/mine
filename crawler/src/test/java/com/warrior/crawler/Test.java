package com.warrior.crawler;

import java.io.IOException;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;

public class Test {

	public static void main(String[] args) throws IOException {
		// System.out.println(ReUtil.getGroup0("\\W+\\D",
		// "湛江市2017年国民经济和社会发展统计公报"));

		// Document doc =
		// Jsoup.connect("http://www.tjcn.org/tjgb/19gd/35441.html").get();
		// doc.getElementsByClass("xwnr").html();
		// System.out.println(
		// ReUtil.get("\\S*\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*(\\u589e\\u52a0\\u503c)+(\\d+\\.?\\d*)",
		// doc.getElementsByClass("xwnr").html(), 2));
		// ([^\u589e\u957f])([\u589e\u957f]){1}
		// \\S*\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a([^\\u589e\\u52a0\\u503c])*(\\u589e\\u52a0\\u503c){1}([^\\u957f])*([\\u589e\\u957f]){1}(\\d+\\.?\\d*)
		System.out.println(ReUtil.get(
				"\\S*\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*?(\\u589e\\u52a0\\u503c){1}\\S*?(\\u589e\\u957f|\\u4e0b\\u964d){1}(\\d+\\.?\\d*)",
				"年末全市共有1163家规模以上工业企业，实现增加值760.8亿元，XX增加值22亿元，下降9.4%，实现总产值3317.66亿元，增长10.3%。", 3));

		System.out.println(DateUtil.format(new Date(), "yyMMdd"));

	}

}
