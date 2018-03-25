package com.warrior.demo.service;

import org.springframework.stereotype.Service;

import com.warrior.demo.domain.User;

/**
 * 用户信息服务
 * 
 * @author Warrior 2018年3月25日
 */
@Service
public class UserService {

	/**
	 * 查找用户
	 * 
	 * @param login
	 * @return
	 */
	public User findUser(String login) {
		if ("warrior".equals(login)) {
			return new User(1l, login, "123", "ADMIN,USER", "VIEW,EDIT", "WARRIOR", "深圳湾");
		} else {
			return null;
		}
	}
}
