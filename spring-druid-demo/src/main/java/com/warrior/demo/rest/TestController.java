package com.warrior.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
		List<User> list = userService.list(null);
		System.out.println(list);
		return list;
	}
}
