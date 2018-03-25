package com.warrior.demo.domain;

import java.io.Serializable;

import com.warrior.demo.constant.ServiceCode;

/**
 * Rest返回格式定义
 * 
 * @author Warrior 2018年3月25日
 */
public class RespData implements Serializable {

	private static final long serialVersionUID = 2497073534245296444L;

	// 状态code
	private int code = ServiceCode.SUCCESS;

	// 错误信息
	private String msg;

	// 数据
	private Object data;

	public RespData(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public RespData(Object data) {
		super();
		this.data = data;
	}

	public RespData(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RespData [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
