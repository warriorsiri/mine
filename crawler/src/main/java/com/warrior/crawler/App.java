package com.warrior.crawler;

import com.warrior.crawler.target.tjcn.TjcnCrawler;

/**
 * 
 * @author warrior
 * 2018年7月11日
 */
public class App {
	/**
	 * arg[0]:广东
	 * arg[1]:年份
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// if (args.length != 2) {
		// System.out.println("必须输入参数：地址 年份，空格隔开");
		// System.out.println("例如：广东 2017");
		// return;
		// }
		long start = System.currentTimeMillis();
		TjcnCrawler crawler = new TjcnCrawler();
		// [{"porvince":"广东省","city":["广州市","深圳市"]},{"porvince":"湖南省","city":["长沙市"]}]
		String addr = "[{\"porvince\":\"广东省\",\"city\":[\"广州市\",\"深圳市\"]},{\"porvince\":\"湖南省\",\"city\":[\"长沙市\"]}]";
		// String addr = "[{\"porvince\":\"广东省\",\"city\":[\"广州市\"]}]";
		String year = "2016-2017";
		String path = "D:/data/excel";
		crawler.crawler(addr, year, path);
		long end = System.currentTimeMillis();
		System.out.println("总耗时：" + (end - start) + "毫秒！");
	}
}
