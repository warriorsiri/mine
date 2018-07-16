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
		// String addr =
		// "[{\"porvince\":\"广东省\",\"city\":[\"广州市\",\"深圳市\"]},{\"porvince\":\"湖南省\",\"city\":[\"长沙市\"]}]";
		String addr = "[{\"porvince\":\"天津\",\"city\":[\"天津市\"]},{\"porvince\":\"上海\",\"city\":[\"上海市\"]},"
				+ "{\"porvince\":\"重庆\",\"city\":[\"重庆市\"]},{\"porvince\":\"广东省\",\"city\":[\"深圳市\",\"佛山市\"]},"
				+ "{\"porvince\":\"四川省\",\"city\":[\"成都市\"]},{\"porvince\":\"吉林省\",\"city\":[\"长春市\"]},"
				+ "{\"porvince\":\"辽宁省\",\"city\":[\"沈阳市\",\"大连市\"]},{\"porvince\":\"湖北省\",\"city\":[\"武汉市\",\"襄阳市\"]},"
				+ "{\"porvince\":\"山东省\",\"city\":[\"青岛市\",\"烟台市\"]},{\"porvince\":\"内蒙古自治区\",\"city\":[\"鄂尔多斯市\"]},"
				+ "{\"porvince\":\"浙江省\",\"city\":[\"宁波市\",\"杭州市\",\"金华市\"]},{\"porvince\":\"黑龙江省\",\"city\":[\"大庆市\"]},"
				+ "{\"porvince\":\"安徽省\",\"city\":[\"合肥市\",\"芜湖市\"]},{\"porvince\":\"河北省\",\"city\":[\"唐山市\",\"石家庄市\"]},"
				+ "{\"porvince\":\"江西省\",\"city\":[\"南昌市\",\"九江市\"]},{\"porvince\":\"江苏省\",\"city\":[\"无锡市\",\"扬州市\"]},"
				+ "{\"porvince\":\"青海省\",\"city\":[\"西宁市\"]},{\"porvince\":\"福建省\",\"city\":[\"福州市\",\"厦门市\"]},"
				+ "{\"porvince\":\"宁夏回族自治区\",\"city\":[\"银川市\"]},{\"porvince\":\"贵州省\",\"city\":[\"贵阳市\"]},"
				+ "{\"porvince\":\"广西壮族自治区\",\"city\":[\"南宁市\"]},{\"porvince\":\"湖南省\",\"city\":[\"郴州市\",\"长沙市\"]},"
				+ "{\"porvince\":\"陕西省\",\"city\":[\"西安市\"]},{\"porvince\":\"甘肃省\",\"city\":[\"兰州市\"]},"
				+ "{\"porvince\":\"云南省\",\"city\":[\"昆明市\"]},{\"porvince\":\"河南省\",\"city\":[\"洛阳市\"]},"
				+ "{\"porvince\":\"新疆维吾尔自治区\",\"city\":[\"乌鲁木齐市\"]},{\"porvince\":\"山西省\",\"city\":[\"太原市\"]}]";
		String year = "2016-2017";
		String path = "D:/data/excel";
		boolean dealPro = false;
		crawler.crawler(addr, year, path, dealPro);
		long end = System.currentTimeMillis();
		System.out.println("总耗时：" + (end - start) + "毫秒！");
	}
}
