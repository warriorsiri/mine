package com.warrior.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.warrior.demo.entity.User;
import com.warrior.demo.mapper.UserMapper;
import com.warrior.demo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		List<User> userList = userMapper.selectList(null);
		// Assert.assertEquals(5, userList.size());
		userList.forEach(System.out::println);
	}

	@Test
	public void testInsert() {
		User u1 = new User();
		u1.setAge(11);
		u1.setEmail("test123");
		u1.setName("哈哈");
		userService.saveOrUpdate(u1);
		System.out.println("插入完成");
	}

}