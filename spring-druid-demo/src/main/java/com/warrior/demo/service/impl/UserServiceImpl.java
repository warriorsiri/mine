package com.warrior.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warrior.demo.entity.User;
import com.warrior.demo.mapper.UserMapper;
import com.warrior.demo.service.UserService;

@Component
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Override
	public void testTranscation() {
		User u1 = new User();
		u1.setAge(11);
		u1.setEmail("test222222222");
		u1.setName("哈哈22222222");
		baseMapper.insert(u1);

		User u2 = new User();
		u2.setAge(12);
		u2.setEmail("test33333333");
		u2.setName("哈哈3333333333");
		baseMapper.insert(u2);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> selectList() {
		return baseMapper.selectList(null);
	}

}
