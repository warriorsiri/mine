package com.warrior.demo.domain;

import java.io.Serializable;

/**
 * 用户
 * 
 * @author Warrior 2018年3月25日
 */
public class User implements Serializable {

	private static final long serialVersionUID = -554039673018187754L;

	private Long id;

	private String login;

	private String password;

	private String roles;

	private String permissions;

	private String name;

	private String addr;

	public User() {
		super();
	}

	public User(Long id, String login, String password, String roles, String permissions, String name, String addr) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.roles = roles;
		this.permissions = permissions;
		this.name = name;
		this.addr = addr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", name=" + name + ", addr=" + addr + "]";
	}

}
