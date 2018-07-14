package com.warrior.crawler;

import java.io.IOException;

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
		System.out.println(
				ReUtil.get("\\S*?\\u89c4\\u6a21\\u4ee5\\u4e0a\\u5de5\\u4e1a\\S*?(\\u5229\\u6da6){1}\\S*?(\\d+\\.?\\d*)",
						"全年规模以上工业企业实现利润总额511.97亿元，比上年增长17.6%。规模以上工业企业资产负债率55.88%，每百元主营业务收入中的成本为87.75元，下降0.06元，主营业务收入利润率为5.9%。",
						0));

	}

}
