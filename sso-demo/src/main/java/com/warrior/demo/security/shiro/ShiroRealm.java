package com.warrior.demo.security.shiro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrior.demo.domain.User;
import com.warrior.demo.security.TokenProvider;

/**
 * 自定义Realm
 * 
 * @author Warrior 2018年3月25日
 */
@Service
public class ShiroRealm extends AuthorizingRealm {

	// @Autowired
	// private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public boolean supports(AuthenticationToken token) {
		// 仅支持JwtToken类型的Token
		return token instanceof JwtToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 从princals中获取主身份信息，将返回值转为真实的身份信息，填充到上面认证的身份中
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		Set<String> roles = new HashSet<>(Arrays.asList(user.getRoles().split(",")));
		simpleAuthorizationInfo.addRoles(roles);
		Set<String> permission = new HashSet<>(Arrays.asList(user.getPermissions().split(",")));
		simpleAuthorizationInfo.addStringPermissions(permission);
		return simpleAuthorizationInfo;
	}

	/**
	 * 用户名和密码验证入口
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		JwtToken jwt = (JwtToken) token;
		String jwtToken = jwt.getPrincipal().toString();
		if (!tokenProvider.validateToken(jwtToken)) {
			throw new AuthenticationException("token invalid");
		}
		User user = tokenProvider.getUser(jwtToken);
		if (user == null) {
			throw new AuthenticationException("token");
		}
		return new SimpleAuthenticationInfo(user, jwtToken, "ShiroRealm");
	}

}
