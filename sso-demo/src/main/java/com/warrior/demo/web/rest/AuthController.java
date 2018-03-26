package com.warrior.demo.web.rest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrior.demo.domain.RespData;
import com.warrior.demo.domain.User;
import com.warrior.demo.security.TokenProvider;
import com.warrior.demo.service.UserService;

/**
 * 验证接口
 * 
 * @author Warrior 2018年3月25日
 */

@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;

	@GetMapping("/hello")
	public RespData hello(String name) {
		return new RespData("hello " + name);
	}

	/**
	 * 登陆认证
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	public RespData authenticate(String login, String password) {
		if (StringUtils.isBlank(login) || StringUtils.isBlank(password)) {
			throw new UnknownAccountException();
		}
		User user = userService.findUser(login);
		if (user == null) {
			throw new UnknownAccountException();
		}
		if (!user.getPassword().equals(password)) {
			throw new IncorrectCredentialsException();
		}
		// 1. 执行登录
		// 把用户名和密码封装为UsernamePasswordToken对象
		// UsernamePasswordToken uptoken = new UsernamePasswordToken(login,
		// password);
		// SecurityUtils.getSubject().login(uptoken);

		// 2.登陆成功，生成tonken返回
		String token = tokenProvider.createToken(user);
		return new RespData(token);
	}

}
