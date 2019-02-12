package com.hzb.exception;

import java.io.Serializable;

/**
 * @author:
 * @date：2019年1月30日 上午9:06:01
 * @descripe:自定义异常
 */
public class MyException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -6153410129781911322L;

	private String code;
	private String msg;

	public MyException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
