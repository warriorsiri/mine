package com.warrior.demo.exception;

import com.warrior.demo.constant.ServiceCode;

/**
 * 业务异常封装
 * 
 * @author Warrior 2018年3月25日
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -3610998643728740142L;

	private int code = ServiceCode.INTERNAL_ERROR;

	public ServiceException(Integer code) {
		super();
		this.code = code;
	}

	public ServiceException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ServiceException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
