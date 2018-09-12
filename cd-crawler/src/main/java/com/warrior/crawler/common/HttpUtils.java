package com.warrior.crawler.common;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import cn.hutool.http.CookiePool;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * 封装hutool的HttpUtil，给定一个sessionId
 * 
 * @author warrior
 * 2018年8月17日
 */
public class HttpUtils {

	// private static final String JSESSIONID =
	// "JSESSIONID=139871744081A7397A33C8BACE5571AA; u=1";
	private static HttpCookie[] cookies = null;

	private static final String INDEX = "http://data.stats.gov.cn/easyquery.htm";

	public static String get(String urlString) {
		// System.out.println("请求前的cookies:" + cookies);
		HttpResponse resp = HttpRequest.get(urlString).disableCache().timeout(HttpRequest.TIMEOUT_DEFAULT).execute();
		System.out.println("请求后的cookies：" + resp.getCookie());
		return resp.body();
	}

	public static String post(String urlString, Map<String, Object> paramMap) {
		return HttpRequest.post(urlString).form(paramMap).timeout(HttpRequest.TIMEOUT_DEFAULT).execute().body();
	}

	public static void getCookie() {
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

			HttpGet httpGet = new HttpGet(INDEX);
			httpClient.execute(httpGet, context);//
			cookies = new HttpCookie[cookieStore.getCookies().size()];
			for (int i = 0; i < cookieStore.getCookies().size(); i++) {
				Cookie cookie = cookieStore.getCookies().get(i);
				System.out.println(cookie.getName() + "-" + cookie.getValue());
				cookies[i] = new HttpCookie(cookie.getName(), cookie.getValue());
			}
			String cookieStr = cookieStore.getCookies().get(0).getName() + "="
					+ cookieStore.getCookies().get(0).getValue() + "; " + cookieStore.getCookies().get(1).getName()
					+ "=" + cookieStore.getCookies().get(1).getValue();
			CookiePool.put("data.stats.gov.cn", cookieStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		get("http://data.stats.gov.cn/easyquery.htm");
	}
}
