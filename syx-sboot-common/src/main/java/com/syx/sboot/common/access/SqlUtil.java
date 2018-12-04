package com.syx.sboot.common.access;

import com.syx.sboot.common.access.sql.SqlQuery;
import com.syx.sboot.common.datasource.DataSourceFactory;
import com.syx.sboot.common.datasource.TypedDataSource;
import com.syx.sboot.common.entity.BaseEntity;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.IdField;
import com.syx.sboot.common.entity.Page;
import com.syx.sboot.common.exception.DbException;
import com.syx.sboot.common.utils.StringUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author younger
 * @date 2017年7月18日
 * @version V1.0
 */
public class SqlUtil {

	private static final String SERIALVERSIONUID = "serialVersionUID";
	private static final Logger logger = LoggerFactory.getLogger(SqlUtil.class);
	
	/*
	 * 过滤非法字符
	 */
	public static String filteSQLStr(String str) {
		return StringEscapeUtils.escapeSql(str);
	}

	/*
	 * 组织插入sql
	 * 
	 */
	private static String getInsertSql(String tableName, Object obj) throws Exception {
		List<Field> fieldlst = new ArrayList<Field>();

		if(BaseEntity.class.isAssignableFrom(obj.getClass())==true){
			((BaseEntity)obj).preInsert();
		}
		PropertiesUtil.getAllFileds(fieldlst, obj.getClass());
		String sql = "";
		if (fieldlst != null && fieldlst.size() > 0) {
			sql += "INSERT INTO " + tableName + "(";
			String values = "";
			for (Field field : fieldlst) {
				if(field.isAnnotationPresent(DbField.class)){
					field.setAccessible(true);
					if (field.get(obj) != null) {
						sql += PropertiesUtil.camelToUnderline(field.getName()) + ",";
						Object value = field.get(obj);
						if (field.getType().isAssignableFrom(Date.class)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = sdf.format(value);
						}
						values += "'" + filteSQLStr(String.valueOf(value)) + "',";
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ") VALUES(";
			sql += values.substring(0, values.length() - 1) + ")";
		}
		return sql;
	}
	
	private static SqlQuery getInsertSql02(String tableName, Object obj) throws Exception {
		List<Field> fieldlst = new ArrayList<Field>();
		SqlQuery sqlquery = new SqlQuery();
		String temp = "";
		if(BaseEntity.class.isAssignableFrom(obj.getClass())==true){
			((BaseEntity)obj).preInsert();
		}
		PropertiesUtil.getAllFileds(fieldlst, obj.getClass());
		String sql = "";
		if (fieldlst != null && fieldlst.size() > 0) {
			sql += "INSERT INTO " + tableName + "(";
			for (Field field : fieldlst) {
				if(field.isAnnotationPresent(DbField.class)){
					field.setAccessible(true);
					if (field.get(obj) != null) {
						sql += PropertiesUtil.camelToUnderline(field.getName()) + ",";
						Object value = field.get(obj);
						if (field.getType().isAssignableFrom(Date.class)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = sdf.format(value);
						}
						sqlquery.addParam(value);
						temp = temp + "?,";
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ") VALUES(";
			sqlquery.appendSql(sql);
			sqlquery.appendSql(temp.substring(0, temp.length() - 1) + ")");
		}
		
		return sqlquery;
	}

	/*
	 * 组织插入sql 不将字段转成驼峰
	 * 
	 */
	private static String getInsertSqlWithCamel(String tableName, Object obj) throws Exception {
		if(BaseEntity.class.isAssignableFrom(obj.getClass())==true){
			((BaseEntity)obj).preInsert();
		}
		List<Field> fieldlst = new ArrayList<Field>();
		PropertiesUtil.getAllFileds(fieldlst, obj.getClass());
		String sql = "";
		if (fieldlst != null && fieldlst.size() > 0) {
			sql += "INSERT INTO " + tableName + "(";
			String values = "";
			for (Field field : fieldlst) {				
				if(field.isAnnotationPresent(DbField.class)){
					field.setAccessible(true);
					if (field.get(obj) != null) {
						sql += field.getName() + ",";
						Object value = field.get(obj);
						if (field.getType().isAssignableFrom(Date.class)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = sdf.format(value);
						}
						values += "'" + filteSQLStr(String.valueOf(value)) + "',";
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ") VALUES(";
			sql += values.substring(0, values.length() - 1) + ")";
		}
		return sql;
	}

	/*
	 * 插入对象
	 */
	public static int insert(Runner runner, String tableName, Object obj) throws Exception {
		String sql = getInsertSql(tableName, obj);
		return runner.update(sql);
	}

	/*
	 * 插入对象
	 */
	public static int insert(TxSqlRunner runner, String tableName, Object obj) throws Exception {
		String sql = getInsertSql(tableName, obj);
		return runner.update(sql);
	}
	
	public static int insert02(TxSqlRunner runner, String tableName, Object obj) throws Exception {
		SqlQuery sqlquery = getInsertSql02(tableName, obj);
		return runner.update(sqlquery.getSql(),sqlquery.getParams());
	}
	
	

	/*
	 * 插入对象 不转换字段
	 */
	public static int insertWithCamel(TxSqlRunner runner, String tableName, Object obj) throws Exception {
		String sql = getInsertSqlWithCamel(tableName, obj);
		return runner.update(sql);
	}

	/*
	 * 组织插入sql
	 * 
	 * 如果属性值为null,则不进行更新 建议对象的整型等不要用基本类型,而采用封装类型,如int ===> Integer
	 */
	private static String getUpdateSql(String tableName, Object obj) throws Exception {
		if(BaseEntity.class.isAssignableFrom(obj.getClass())==true){
			((BaseEntity)obj).preUpdate();
		}
		List<Field> fieldlst = new ArrayList<Field>();
		PropertiesUtil.getAllFileds(fieldlst, obj.getClass());
		String sql = "";
		if (fieldlst != null && fieldlst.size() > 0) {
			sql += "UPDATE " + tableName + " SET ";
			for (Field field : fieldlst) {
				if(field.isAnnotationPresent(DbField.class) && !field.isAnnotationPresent(IdField.class)){
					field.setAccessible(true);
					if (field.get(obj) != null) {
						Object value = field.get(obj);
						if (field.getType().isAssignableFrom(Date.class)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = sdf.format(value);
						}
						sql += PropertiesUtil.camelToUnderline(field.getName()) + " = '" + value + "',";
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += " where id = ?";
		}
		return sql;
	}

	private static String getUpdateCamelSql(String tableName, String col, Object obj) throws Exception {
		if(BaseEntity.class.isAssignableFrom(obj.getClass())==true){
			((BaseEntity)obj).preUpdate();
		}
		List<Field> fieldlst = new ArrayList<Field>();
		PropertiesUtil.getAllFileds(fieldlst, obj.getClass());
		String sql = "";
		if (fieldlst != null && fieldlst.size() > 0) {
			sql += "UPDATE " + tableName + " SET ";
			for (Field field : fieldlst) {
				if(field.isAnnotationPresent(DbField.class) && !field.isAnnotationPresent(IdField.class)){
					field.setAccessible(true);
					if (field.get(obj) != null) {
						Object value = field.get(obj);
						if (field.getType().isAssignableFrom(Date.class)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = sdf.format(value);
						}
						sql += field.getName() + " = '" + value + "',";
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += " where " + col + " = ?";
		}
		return sql;
	}

	/*
	 * 更新对象
	 */
	public static int update(Runner runner, String tableName, String id, Object obj) throws Exception {
		String sql = getUpdateSql(tableName, obj);
		return runner.update(sql, id);
	}

	/*
	 * 更新对象
	 */
	public static int update(TxSqlRunner runner, String tableName, String id, Object obj) throws Exception {
		String sql = getUpdateSql(tableName, obj);
		return runner.update(sql, id);
	}

	/*
	 * 更新对象
	 */
	public static int updateWithCamel(TxSqlRunner runner, String tableName, String field, String value, Object obj)
			throws Exception {
		String sql = getUpdateCamelSql(tableName, field, obj);
		return runner.update(sql, value);
	}

	/*
	 * 更新某个属性
	 */
	public static int update(QueryRunner runner, String tableName, String id, String[] columns, Object... objs)
			throws Exception {
		String sql = "UPDATE " + tableName + " SET ";
		for (int i = 0; i < columns.length; ++i) {
			sql += PropertiesUtil.camelToUnderline(columns[i]) + " = ?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " WHERE id = '" + id + "'";
		return runner.update(sql, objs);
	}

	/*
	 * 更新某个属性
	 */
	public static int update(TxSqlRunner runner, String tableName, String id, String[] columns, Object... objs)
			throws Exception {
		String sql = "UPDATE " + tableName + " SET ";
		for (int i = 0; i < columns.length; ++i) {
			sql += PropertiesUtil.camelToUnderline(columns[i]) + " = ?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " WHERE id = '" + id + "'";
		return runner.update(sql, objs);
	}

	/*
	 * 更新某个属性
	 */
	public static int update(QueryRunner runner, String tableName, String[] columns, String where, Object... objs)
			throws Exception {
		String sql = "UPDATE " + tableName + " SET ";
		for (int i = 0; i < columns.length; ++i) {
			sql += PropertiesUtil.camelToUnderline(columns[i]) + " = ?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " WHERE " + where;
		return runner.update(sql, objs);
	}

	/*
	 * 更新某个属性
	 */
	public static int update(TxSqlRunner runner, String tableName, String[] columns, String where, Object... objs)
			throws Exception {
		String sql = "UPDATE " + tableName + " SET ";
		for (int i = 0; i < columns.length; ++i) {
			sql += PropertiesUtil.camelToUnderline(columns[i]) + " = ?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " WHERE " + where;
		return runner.update(sql, objs);
	}
	
	public static int update(TxSqlRunner runner, SqlQuery sqlquery)
			throws Exception {
		return runner.update(sqlquery.getSql(), sqlquery.getParams());
	}
	
	public static int updateBySqlQuery(String dscode, SqlQuery sqlquery)
			throws Exception {		
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.update(runner, sqlquery);
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 删除
	 */
	public static int delete(Runner runner, String tableName, String id) throws Exception {
		String sql = "delete from " + tableName + " where (id=?)";
		return runner.update(sql, id);
	}
	
	public static int deleteById(String dscode, String tableName, String id){
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.delete(runner, tableName, id);
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 根据字段删除
	 */
	public static int delete(Runner runner, String tableName, String filed, String val) throws Exception {
		String sql = "delete from " + tableName + " where (" + filed + "=?)";
		return runner.update(sql, val);
	}

	/*
	 * 根据字段删除
	 */
	public static int delete(String dscode, String tableName, String filed, String val) throws Exception {
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.delete(runner, tableName, filed, val);
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/*
	 * 删除
	 */
	public static int deleteIds(Runner runner, String tableName, String... ids) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder("delete from " + tableName + " where id in (");
		String sql = "";
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; ++i) {
				sqlBuilder.append("?,");
			}
			sql = sqlBuilder.subSequence(0, sqlBuilder.length() - 1) + ")";
		}
		return runner.update(sql, ids);
	}
	
	public static int deleteIds(String dscode, String tableName, String... ids) throws Exception {
		
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.deleteIds(runner, tableName, ids);
		}  catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 更新某个属性
	 */
	public static int deleteByColumn(QueryRunner runner, String tableName, String column, Object obj) throws Exception {
		String sql = "delete from " + tableName + " where " + column + " = ?";
		return runner.update(sql, obj);
	}

	/*
	 * 更新某个属性
	 */
	public static int deleteByColumns(QueryRunner runner, String tableName, String[] columns, Object... objs)
			throws Exception {
		String sql = "delete from " + tableName + " where ";
		for (int i = 0; i < columns.length; ++i) {
			sql += PropertiesUtil.camelToUnderline(columns[i]) + " = ?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		return runner.update(sql, objs);
	}

	/*
	 * 更新某个属性
	 */
	public static int deleteByColumn(TxSqlRunner runner, String tableName, String column, Object obj) throws Exception {
		String sql = "delete from " + tableName + " where " + column + " = ?";
		return runner.update(sql, obj);
	}

	/*
	 * 更新某个属性
	 */
	public static int deleteByColumns(TxSqlRunner runner, String tableName, String[] columns, Object... objs)
			throws Exception {
		String sql = "delete from " + tableName + " where ";
		for (int i = 0; i < columns.length; ++i) {
			sql += PropertiesUtil.camelToUnderline(columns[i]) + " = ?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		return runner.update(sql, objs);
	}
	
	public static int deleteByColumns(String dscode, String tableName, String[] columns, Object... objs)
			throws Exception {
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.deleteByColumns(runner, tableName, columns, objs);
		}  catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static int deleteBySqlQuery(String dscode, SqlQuery sqlquery)
			throws Exception {
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return runner.update(sqlquery.getSql(), sqlquery.getParams());
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static int deleteBySqlQuery(TxSqlRunner runner, SqlQuery sqlquery) throws Exception {		
		return runner.update(sqlquery.getSql(), sqlquery.getParams());
	}

	/*
	 * 删除
	 */
	public static int delete(TxSqlRunner runner, String tableName, String id) throws Exception {
		String sql = "delete from " + tableName + " where (id=?)";
		return runner.update(sql, id);
	}
	
	/*
	 * 删除
	 */
	public static int delete(String dscode, String tableName, String id) throws Exception {
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.delete(runner, tableName, id);
		}  catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 获取
	 */
	public static <T> T get(Runner runner, String tableName, Class<T> clazz, String id) throws Exception {
		String sql = "select * from " + tableName + " where (id=?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}

	/*
	 * 获取
	 */
	public static <T> T get(TxSqlRunner runner, String tableName, Class<T> clazz, String id) throws Exception {
		String sql = "select * from " + tableName + " where (id=?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}

	/*
	 * 获取
	 */
	public static <T> List<T> getAll(QueryRunner runner, String tableName, Class<T> clazz, String id) throws Exception {
		String sql = "select * from " + tableName + " where (id=?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys;
	}

	/*
	 * 获取
	 */
	public static <T> List<T> getAll(TxSqlRunner runner, String tableName, Class<T> clazz, String id) throws Exception {
		String sql = "select * from " + tableName + " where (id=?)";
		List<T> entitys = runner.query(sql, new BeanListHandler<T>(clazz), id);
		return entitys;
	}

	/*
	 * 获取
	 */
	public static <T> T get(Runner runner, String tableName, Class<T> clazz, String column, String id)
			throws Exception {
		String sql = "select * from " + tableName + " where (" + column + "=?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}

	/*
	 * 获取
	 */
	public static <T> T get(TxSqlRunner runner, String tableName, Class<T> clazz, String column, String id)
			throws Exception {
		String sql = "select * from " + tableName + " where (" + column + "=?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}
	
	/*
	 * 获取
	 */
	public static <T> T get(String dscode, String tableName, Class<T> clazz, String column, String id)
			throws Exception {
		
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.get(runner, tableName, clazz, column, id);
			
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 获取
	 */
	public static <T> List<T> getAll(QueryRunner runner, String tableName, Class<T> clazz, String column, String id)
			throws Exception {
		String sql = "select * from " + tableName + " where (" + column + "=?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null ? entitys : new ArrayList<T>();
	}

	/*
	 * 获取
	 */
	public static <T> List<T> getAll(TxSqlRunner runner, String tableName, Class<T> clazz, String column, String id)
			throws Exception {
		String sql = "select * from " + tableName + " where (" + column + "=?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null ? entitys : new ArrayList<T>();
	}

	/*
	 * 模糊获取
	 */
	public static <T> T getLike(QueryRunner runner, String tableName, Class<T> clazz, String column, String id)
			throws Exception {
		id = "%" + id + "%";
		String sql = "select * from " + tableName + " where (" + column + " like ?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}

	/*
	 * 模糊获取
	 */
	public static <T> T getLike(TxSqlRunner runner, String tableName, Class<T> clazz, String column, String id)
			throws Exception {
		id = "%" + id + "%";
		String sql = "select * from " + tableName + " where (" + column + " like ?)";
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}

	/*
	 * 获取并排序
	 */
	public static <T> T get(Runner runner, String tableName, Class<T> clazz, String column, String id, String orderBy,
			String esc) throws Exception {

		String sql = "select * from " + tableName + " where (" + column + " = ?) order by " + orderBy + " " + esc;
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}

	/*
	 * 获取并排序
	 */
	public static <T> T get(TxSqlRunner runner, String tableName, Class<T> clazz, String column, String id,
			String orderBy, String esc) throws Exception {
		String sql = "select * from " + tableName + " where (" + column + " = ?) order by " + orderBy + " " + esc;
		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), id);
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}
	
	/*
	 * 获取并排序
	 */
	public static <T> T get(String dscode, String tableName, Class<T> clazz, String column, String id,
			String orderBy, String esc) throws Exception {		
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.get(runner, tableName, clazz, column, id, orderBy,  esc);
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	/*
	 * 获取
	 */
	public static <T> T getOnly(Runner runner, String tableName, Class<T> clazz, SqlQuery query) throws Exception {
		String sql = "select * from " + tableName + " where ";
		String where = "1 = 1 ";
		if (query != null && !StringUtils.isEmpty(query.getSql())) {
			where = where + query.getSql();
		}
		sql += where;

		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), query.getParams());
		return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
	}

	public static <T> T getOnly(String dscode, String tableName, Class<T> clazz, SqlQuery query) {
		TxSqlRunner runner = null;
		try {
			if (StringUtils.isEmpty(dscode)) {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			} else {
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			}
			return SqlUtil.getOnly(runner, tableName, clazz, query);
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 获取
	 */
	public static <T> List<T> get(Runner runner, String tableName, Class<T> clazz, String[] columns, Object... values)
			throws Exception {
		String sql = "select * from " + tableName + " where ";
		String where = "1 = 1";
		if (columns != null && columns.length > 0) {
			for (String column : columns) {
				where += " and " + PropertiesUtil.camelToUnderline(column) + "=?";
			}
		}
		sql += where;

		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), values);
		return entitys;
	}

	/*
	 * 获取
	 */
	public static <T> List<T> get(Runner runner, String tableName, Class<T> clazz, SqlQuery query) throws Exception {
		String sql = "select * from " + tableName + " where ";
		String where = "1 = 1 ";
		if (query != null && !StringUtils.isEmpty(query.getSql())) {
			where = where + query.getSql();
		}
		sql += where;

		List<T> entitys = runner.query(sql,
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), query.getParams());
		return entitys;
	}

	public static <T> List<T> get(String dscode, String tableName, Class<T> clazz, SqlQuery query){
		TxSqlRunner runner = null;
		List<T> entitys = new ArrayList<T>();
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			entitys = SqlUtil.get(runner, tableName, clazz, query);
		}catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return entitys;
	}

	public static <T> List<T> getByOriginSQL(Runner runner, Class<T> clazz, SqlQuery query) throws Exception {
		List<T> entitys = runner.query(query.getSql(),
				new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), query.getParams());
		return entitys;
	}

	public static <T> List<T> getByOriginSQL(String dscode, Class<T> clazz, SqlQuery query) {
		TxSqlRunner runner = null;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			return SqlUtil.getByOriginSQL(runner, clazz, query);
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static <T> T getOnlyByOriginSQL(String dscode, Class<T> clazz, SqlQuery query) {
		TxSqlRunner runner = null;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			List<T> entitys = SqlUtil.getByOriginSQL(runner, clazz, query);
			return entitys != null && entitys.size() > 0 ? entitys.get(0) : null;
		} catch (Exception e) {
			if(runner != null){
				try {
					runner.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new DbException(e.getMessage());
		}finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 获取
	 */
	public static <T> List<T> get(TxSqlRunner runner, String tableName, Class<T> clazz, String[] columns,
			Object... values) throws Exception {
		String sql = "select * from " + tableName + " where ";
		String where = "1 = 1";
		if (columns != null && columns.length > 0) {
			for (String column : columns) {
				where += " and " + PropertiesUtil.camelToUnderline(column) + "=?";
			}
		}
		sql += where;
		List<T> entitys = runner.query(sql, new BeanListHandler<T>(clazz), values);
		return entitys;
	}
	
	public static <T> Page<T> findPageByCondition(String dscode, String tableName, Page<T> page, Class<T> clazz, String condition) {
		if(StringUtils.isEmpty(condition) || !condition.contains("where")){
			condition = " 1=1 " + condition;
		}
		
		Page<T> list = new Page<T>();
		try {
			TypedDataSource dataSource = DataSourceFactory.getInstance().getDataSourceByCode(dscode);
			TxSqlRunner runner = new TxSqlRunner(dataSource);

			int start = 0;
			int pagesize = 0;
			// 分页
			if (page != null && page.getPageSize() > 0) {
				start = page.getFirstResult();
				pagesize = page.getMaxResults();
				PageQuery pageQuery = PageQuery.begin(dataSource).select("*").from(tableName + "")
						.whereOriginal(condition, !StringUtils.isEmpty(condition), null)
						.start(start).limit(pagesize)
						.end();
				try {
					int totalRows = 0;
					if(page.getCount() <= 0){
						@SuppressWarnings("unchecked")
						Object objTotalRows = runner.query(pageQuery.getPageCountSql(), new ScalarHandler(),
								pageQuery.getParamArray());
						if (objTotalRows != null) {
							totalRows = Integer.parseInt(objTotalRows.toString());
						}
					}else{
						totalRows = (int) page.getCount();
					}

					if (totalRows > 0) {
						list.setCount(totalRows);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						List orderList = (List) runner.query(pageQuery.getPageQuerySql(),
								new BeanListHandler(clazz), pageQuery.getParamArray());

						if ((orderList != null) && (orderList.size() > 0)) {
							list.setList(orderList);
						}
					}
				} catch (Exception e) {
					;
				} finally {
					if (runner != null)
						runner.close();
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;		
	}

}
