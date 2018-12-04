//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access.sql;

import java.util.List;


public interface SqlBuilder {
	
	public abstract <T> SqlQuery buildSaveSql(T entity) throws Exception;
	
	public abstract <T> SqlQuery buildUpdateSql(T entity) throws Exception;
	
	public abstract <T> SqlQuery buildDeleteSql(T entity) throws Exception;
	
	public abstract <T> SqlQuery buildDeleteSqlByID(T entity) throws Exception;
	
	public abstract <T> SqlQuery buildFindByIdSql(T entity, Object id) throws Exception;
	
	public abstract <T> SqlQuery buildFindByIdsSql(T entity, List<?> ids) throws Exception;
	
	public abstract <T> SqlQuery buildFindByIdSql(T entity) throws Exception;
	
}
