package com.syx.sboot.common.access.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum SqlBuilderEnum {
	mysql("mysql", "com.syx.sboot.common.access.sql.MySqlSqlBuilder"),
	sqlserver("sqlserver", "com.syx.sboot.common.access.sql.MySqlSqlBuilder"),
	oracle("oracle", "com.syx.sboot.common.access.sql.MySqlSqlBuilder"),
	postgresql("postgresql", "com.syx.sboot.common.access.sql.MySqlSqlBuilder"),
	db2("db2", "com.syx.sboot.common.access.sql.MySqlSqlBuilder"),
	h2("h2", "com.syx.sboot.common.access.sql.MySqlSqlBuilder");
	//sqlserver("sqlserver", "com.syx.sboot.common.access.sql.SqlServerSqlBuilder"),
	//oracle("oracle", "com.syx.sboot.common.access.sql.OracleSqlBuilder"),
	//postgresql("postgresql", "com.syx.sboot.common.access.sql.PostgresqlSqlBuilder"),
	//db2("db2", "com.syx.sboot.common.access.sql.Db2SqlBuilder"),
	//h2("h2", "com.syx.sboot.common.access.sql.H2SqlBuilder");
	
	/** 枚举值 */
	private final String code;

	/** 枚举描述 */
	private final String message;

	private SqlBuilderEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}

	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return CardTypeEnum
	 */
	public static SqlBuilderEnum getByCode(String code) {
		for (SqlBuilderEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	
	public static SqlBuilderEnum getMewwageByCode(String code) {
		for (SqlBuilderEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return CardTypeEnum
	 */
	public static SqlBuilderEnum getByMessage(String message) {
		for (SqlBuilderEnum _enum : values()) {
			if (_enum.getMessage().equals(message)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 * 
	 * @return List<CardTypeEnum>
	 */
	public static List<SqlBuilderEnum> getAllEnum() {
		List<SqlBuilderEnum> list = new ArrayList<SqlBuilderEnum>();
		for (SqlBuilderEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CardTypeEnum>
	 */
	public static HashMap<String, String> getAllEnumMap() {
		HashMap<String, String> maps = new HashMap<String,String>();
		for (SqlBuilderEnum _enum : values()) {
			maps.put(_enum.getCode(), _enum.getMessage());
		}
		return maps;
	}

	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (SqlBuilderEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
