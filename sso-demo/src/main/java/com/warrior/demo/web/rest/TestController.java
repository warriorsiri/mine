package com.warrior.demo.web.rest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrior.demo.domain.RespData;

/**
 * 权限测试接口
 * 
 * @author Warrior 2018年3月25日
 */
@RestController
public class TestController {

	@GetMapping("/article")
	public RespData article() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return new RespData(200, "You are already logged in", null);
		} else {
			return new RespData(200, "You are guest", null);
		}
	}

	@GetMapping("/require_auth")
	@RequiresAuthentication
	public RespData requireAuth() {
		return new RespData(200, "You are authenticated", null);
	}

	@GetMapping("/require_role")
	@RequiresRoles("admin")
	public RespData requireRole() {
		return new RespData(200, "You are visiting require_role", null);
	}

	@GetMapping("/require_permission")
	@RequiresPermissions(logical = Logical.AND, value = { "view", "edit" })
	public RespData requirePermission() {
		return new RespData(200, "You are visiting permission require edit,view", null);
	}
}
