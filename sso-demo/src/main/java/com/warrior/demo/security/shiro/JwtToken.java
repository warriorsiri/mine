package com.warrior.demo.security.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWTToken
 * 
 * @author warrior
 * 2018年3月26日
 */
public class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = 4334767559636709599L;

	private String token;

	public JwtToken(String token) {
		super();
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

}
