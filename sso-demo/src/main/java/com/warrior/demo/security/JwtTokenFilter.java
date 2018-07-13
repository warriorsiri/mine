package com.warrior.demo.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.warrior.demo.constant.ServiceCode;
import com.warrior.demo.domain.RespData;
import com.warrior.demo.security.shiro.JwtToken;

/**
 * 重写安全过滤器
 * 
 * @author Warrior 2018年3月25日
 */
public class JwtTokenFilter extends AccessControlFilter {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object arg2) throws Exception {
		if (null != getSubject(request, response) && getSubject(request, response).isAuthenticated()) {
			return true;// 已经认证过直接放行
		}
		return false;// 转到拒绝访问处理逻辑
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		String token = resolveToken(req);
		if (StringUtils.isBlank(token)) {
			sendError(response, ServiceCode.UNAUTHORIZED, "用户需要验证");
			return false;
		}
		try {
			Subject subject = getSubject(request, response);
			subject.login(new JwtToken(token));// 认证
			return true;// 认证成功，过滤器链继续
		} catch (AuthenticationException e) {// 认证失败，发送401状态并附带异常信息
			sendError(response, ServiceCode.UNAUTHORIZED, "token失效");
		}
		return false;
	}

	private String resolveToken(HttpServletRequest request) {
		return request.getHeader(AUTHORIZATION_HEADER);
	}

	// 登录失败时默认返回401状态码
	private void sendError(ServletResponse response, int code, String msg) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		RespData data = new RespData(code, msg);
		httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
		httpServletResponse.getWriter().write(JSON.toJSONString(data));
	}

}
