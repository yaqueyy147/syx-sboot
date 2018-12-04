package com.syx.sboot.common.utils.extend.lang.enums;

import java.util.ArrayList;
import java.util.List;

public enum ServiceInvocationCodeEnum {
	INVOCATE_SUCCESS("INVOCATE_SUCCESS", "调用成功"),

	INVOCATE_FAIL("INVOCATE_FAIL", "调用失败"),

	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"),

	EXECUTE_FAIL("EXECUTE_FAIL", "执行失败"),

	INVOCATE_EXCEPTION("INVOCATE_EXCEPTION", "调用异常"),

	EXECUTE_EXCEPTION("EXECUTE_EXCEPTION", "执行异常"),

	UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", "未知异常");

	private final String code;
	private final String message;

	private ServiceInvocationCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public String code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

	public static ServiceInvocationCodeEnum getByCode(String code) {
		for (ServiceInvocationCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	public static List<ServiceInvocationCodeEnum> getAllEnum() {
		List list = new ArrayList();
		for (ServiceInvocationCodeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	public static List<String> getAllEnumCode() {
		List list = new ArrayList();
		for (ServiceInvocationCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}