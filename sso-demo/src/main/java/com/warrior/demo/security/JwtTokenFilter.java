package com.warrior.demo.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 重写安全过滤器
 * 
 * @author Warrior 2018年3月25日
 */
public class JwtTokenFilter extends AccessControlFilter {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private TokenProvider tokenProvider;

	public JwtTokenFilter(TokenProvider tokenProvider) {
		super();
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object arg2) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		String token = resolveToken(req);
		if (StringUtils.isBlank(token)) {
			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		return tokenProvider.validateToken(token);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) throws Exception {
		return false;
	}

	private String resolveToken(HttpServletRequest request) {
		return request.getHeader(AUTHORIZATION_HEADER);
	}

	// 登录失败时默认返回401状态码
	private void onLoginFail(ServletResponse response, String errorMsg) throws IOException {
		// HttpServletResponse httpServletResponse = (HttpServletResponse)
		// response;
		// Map result = new HashMap();
		// result.put(Constants.RES_ACCESS_KEY, false);
		// result.put(Constants.RES_MSG_KEY, Constants.RES_MSG_NO_ACCESS_VALUE);
		// String json = JSON.toJSONString(result);
		// httpServletResponse.setHeader("Content-type",
		// "application/json;charset=UTF-8");
		// httpServletResponse.getWriter().write(json);
	}

	public void setTokenProvider(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

}
