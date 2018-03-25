package com.warrior.demo.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.warrior.demo.constant.ServiceCode;
import com.warrior.demo.domain.RespData;
import com.warrior.demo.exception.ServiceException;

/**
 * rest异常处理类
 * 
 * @author Warrior 2018年3月25日
 */
@RestControllerAdvice
public class ExceptionController {

	public final static Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

	// 捕捉shiro的异常
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ShiroException.class)
	public RespData handle401(ShiroException e) {
		e.printStackTrace();
		if (e instanceof IncorrectCredentialsException) {
			return new RespData(ServiceCode.UNAUTHORIZED, "账号或者密码不正确", null);
		}
		return new RespData(ServiceCode.UNAUTHORIZED, e.getMessage(), null);
	}

	// 捕捉UnauthorizedException
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public RespData handle401() {
		return new RespData(ServiceCode.UNAUTHORIZED, "Unauthorized", null);
	}

	// 捕捉其他所有异常
	@ExceptionHandler(Exception.class)
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	public RespData globalException(HttpServletRequest request, Throwable e) {
		LOGGER.error("request url={}", request.getRequestURI(), e);
		Integer code = null;
		if (e instanceof ServiceException) {
			ServiceException se = (ServiceException) e;
			code = se.getCode();
		}
		return new RespData(code, e.getMessage(), null);
	}

	// private HttpStatus getStatus(HttpServletRequest request) {
	// Integer statusCode = (Integer)
	// request.getAttribute("javax.servlet.error.status_code");
	// if (statusCode == null) {
	// return HttpStatus.INTERNAL_SERVER_ERROR;
	// }
	// return HttpStatus.valueOf(statusCode);
	// }
}
