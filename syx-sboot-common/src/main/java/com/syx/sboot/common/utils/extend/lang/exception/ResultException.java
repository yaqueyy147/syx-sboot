package com.syx.sboot.common.utils.extend.lang.exception;


public class ResultException extends RuntimeException {
	private static final long serialVersionUID = -6142198086517154408L;
	protected String code;
	protected String description;

	public ResultException() {
	}

	public ResultException(String code) {
		this(code, null, null, null);
	}

	public ResultException(String code, String description) {
		this(code, description, null, null);
	}

	public ResultException(String code, Throwable t) {
		this(code, null, null, t);
	}

	public ResultException(String code, String message, Throwable t) {
		this(code, null, message, t);
	}

	public ResultException(String code, String description, String message, Throwable t) {
		super(message, t);
		this.code = code;
		this.description = description;
	}

	public ResultException(String code, String description, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public synchronized Throwable fillInStackTrace() {
		return super.fillInStackTrace();
	}
}