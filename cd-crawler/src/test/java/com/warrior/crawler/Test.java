package com.warrior.crawler;

import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test {

	private static final String Index = "http://data.stats.gov.cn/easyquery.htm";
	private static final String GET_URL = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=hgyd&rowcode=zb&colcode=sj&wds=%5B%5D&dfwds=%5B%7B%22wdcode%22%3A%22sj%22%2C%22valuecode%22%3A%22LAST36%22%7D%5D";
	private static final String GET_URL2 = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=hgyd&rowcode=zb&colcode=sj&wds=%5B%5D&dfwds=%5B%7B%22wdcode%22%3A%22zb%22%2C%22valuecode%22%3A%22A1B01%22%7D%5D";

	public static void main(String[] args) {
		// 全局请求设置
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		// 创建cookie store的本地实例
		CookieStore cookieStore = new BasicCookieStore();
		// 创建HttpClient上下文
		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(cookieStore);

		// 创建一个HttpClient
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
				.setDefaultCookieStore(cookieStore).build();
		try {

			HttpGet httpGet = new HttpGet(Index);
			httpClient.execute(httpGet, context);//
			for (Cookie c : cookieStore.getCookies()) {
				System.out.println(c.getName() + ": " + c.getValue());
			}
			HttpGet httpGet1 = new HttpGet(GET_URL);
			HttpResponse res = httpClient.execute(httpGet1, context);//
			EntityUtils.toString(res.getEntity());
			HttpGet httpGet2 = new HttpGet(GET_URL2);
			HttpResponse res2 = httpClient.execute(httpGet2, context);//
			System.out.println(EntityUtils.toString(res2.getEntity(), Charset.forName("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

}
