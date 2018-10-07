package com.warrior.demo.rest;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warrior.demo.entity.User;
import com.warrior.demo.service.UserService;

@RestController
public class TestController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		userService.testTranscation();
		return "hello world";
	}

	@RequestMapping(value = "select", method = RequestMethod.GET)
	public List<User> select() {
		List<User> list = userService.selectList();
		System.out.println(list);
		return list;
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insert() {
		User u = new User();
		u.setAge(10);
		u.setEmail("test@qq.com");
		u.setName("test insert");
		userService.saveOrUpdate(u);
		System.out.println(u.getId());
		return "ok";
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update() {
		User u = userService.getById(1l);
		u.setAge(10);
		u.setEmail("test@qq.com");
		u.setName("Random" + RandomUtils.nextInt(0, 10));
		userService.saveOrUpdate(u);
		return "ok";
	}

	@RequestMapping(value = "page", method = RequestMethod.GET)
	public IPage<User> selectPage() {
		IPage<User> page = new Page<>(2, 5);
		IPage<User> list = userService.page(page, null);
		System.out.println(list);
		return list;
	}
}
