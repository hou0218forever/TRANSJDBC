package com.hzb.exception;

import java.io.Serializable;

public class BuyStockException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -5050162169296522042L;
	private String code;
	private String msg;

	public BuyStockException() {
		super();
	}

	public BuyStockException(String code, String msg) {
		super();
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
