package com.syx.sboot.common.utils.extend.lang.enums;

import java.util.ArrayList;
import java.util.List;

public enum DataOperationCodeEnum {
	DATA_ACCESS_EXCEPTION("DB_EXCEPTION", "数据库异常"),

	UN_KNOWN_EXCEPTION("DATA_ACCESS_EXCEPTION", "未知异常"),

	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"),

	EXECUTE_FAIL("EXECUTE_FAIL", "执行失败");

	private final String code;
	private final String message;

	private DataOperationCodeEnum(String code, String message) {
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

	public static DataOperationCodeEnum getByCode(String code) {
		for (DataOperationCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	public List<DataOperationCodeEnum> getAllEnum() {
		List list = new ArrayList();
		for (DataOperationCodeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	public List<String> getAllEnumCode() {
		List list = new ArrayList();
		for (DataOperationCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}