package com.warrior.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.warrior.demo.entity.User;

public interface UserService extends IService<User> {

	public void testTranscation();
}
