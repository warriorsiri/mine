package com.warrior.crawler;

import java.io.IOException;

import com.warrior.crawler.time.TimeCrawler;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		// 设置统一的cookie
		// String cookie = "JSESSIONID=A0AE42E8F5D1EC033CC940D13E8CAF29; u=1";
		// CookiePool.put("data.stats.gov.cn", cookie);
		TimeCrawler crawler = new TimeCrawler();
		crawler.crawler("A1B", "金融", "month");
	}
}
