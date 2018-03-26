package com.warrior.demo.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.warrior.demo.domain.User;

/**
 * token生成器
 * 
 * @author Warrior 2018年3月25日
 */
@Component
public class TokenProvider {

	private String secretKey;

	private long tokenValidityInseconds;

	// private final JHipsterProperties jHipsterProperties;

	@PostConstruct
	public void init() {
		this.secretKey = "123456asdggr";

		this.tokenValidityInseconds = 24 * 60 * 60;
	}

	/**
	 * 创建token
	 * 
	 * @param loginName
	 * @return
	 */
	public String createToken(User user) {
		try {
			Date date = new Date(System.currentTimeMillis() + tokenValidityInseconds * 1000);
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			// 附带loginName信息
			return JWT.create().withClaim("id", user.getId()).withClaim("loginName", user.getLogin())
					.withClaim("roles", user.getRoles()).withClaim("permissions", user.getPermissions())
					.withExpiresAt(date).sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 获取token里面的用户信息（只是部分信息）
	 * 
	 * @param token
	 * @return
	 */
	public User getUser(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			Long id = jwt.getClaim("id").asLong();
			String login = jwt.getClaim("loginName").asString();
			String roles = jwt.getClaim("roles").asString();
			String permissions = jwt.getClaim("permissions").asString();
			return new User(id, login, null, roles, permissions, null, null);
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 验证token
	 * 
	 * @param authToken
	 * @return
	 */
	public boolean validateToken(String authToken) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(authToken);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}
}
