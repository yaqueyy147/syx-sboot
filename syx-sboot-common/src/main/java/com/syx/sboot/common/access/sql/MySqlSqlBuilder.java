//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access.sql;

import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.IdField;
import com.syx.sboot.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MySqlSqlBuilder extends AbstractSqlBuilder implements SqlBuilder {
    private static final Logger log = LoggerFactory.getLogger(MySqlSqlBuilder.class);

    public MySqlSqlBuilder() {
    }

    public static SqlBuilder getInstance() {
        return MySqlSqlBuilder.SqlBuildHolder.instance;
    }
   
	
	@Override
	public <T> SqlQuery buildSaveSql(T entity) throws Exception {
		Class<?> entityClass = entity.getClass();
		StringBuilder builder = new StringBuilder("insert into ");
		String tableName=camelConvertFieldName(entityClass.getSimpleName());
		builder.append(tableName).append(" ( ");
		List<Object> values = new ArrayList<Object>();
		List<Field> fields = getListField(entity);
		for (Field field : fields) {
			if (field.isAnnotationPresent(DbField.class)) {
				String key = camelConvertFieldName(field.getName());
				field.setAccessible(true);
				Object value = field.get(entity);
				if (value==null) continue;
				if(value.getClass() == String.class&& StringUtils.isEmpty((String)value)){
					continue;
				}
				builder.append(key).append(" , ");
				values.add(value);
			}
		}
		if (values.size()<1) return null;
		builder.delete(builder.lastIndexOf(" , "), builder.length());
		builder.append(" ) values ( ");
		for (int i = 0; i < values.size(); i++) {
			builder.append("? , ");
		}
		builder.delete(builder.lastIndexOf(" , "), builder.length());
		builder.append(" )");
		String sql=builder.toString();
		return new SqlQuery(sql, values.toArray());
	}
	
	/**
	 * id作为where条件
	 * 其他字段作为更新
	 */
	@Override
	public <T> SqlQuery buildUpdateSql(T entity) throws Exception{
		Class<?> entityClass = entity.getClass();
		StringBuilder builder = new StringBuilder("update ");
		String tableName=camelConvertFieldName(entityClass.getSimpleName());
		builder.append(tableName).append(" set ");
		String idFieldName=null;
		Object idFieldValue=null;
		List<Field> fields = getListField(entity);
		List<Object> values = new ArrayList<Object>();
		for (Field field : fields) {
			if (field.isAnnotationPresent(DbField.class)) {
				String key = camelConvertFieldName(field.getName());
				field.setAccessible(true);
				Object value = field.get(entity);
				if (value==null) continue;
				if (field.isAnnotationPresent(IdField.class)) {
					idFieldName=key;
					idFieldValue=value;
					continue;
				}
				builder.append("`" + key + "`").append(" = ? , ");
				values.add(value);
			}
		}
		if (values.size()<1) return null;
		builder.delete(builder.lastIndexOf(" , "), builder.length());
		if (idFieldName!=null&&idFieldValue!=null) {
			builder.append(" where ").append(camelConvertFieldName("`" + idFieldName + "`")).append(" = ? ");
		}
		values.add(idFieldValue);
		String sql=builder.toString();
		return new SqlQuery(sql, values.toArray());
	}
	
	/**
	 * 实体作为where条件
	 */
	@Override
	public <T> SqlQuery buildDeleteSql(T entity) throws Exception {
		Class<?> entityClass = entity.getClass();
		StringBuilder builder = new StringBuilder("delete from ");
		String tableName=camelConvertFieldName(entityClass.getSimpleName());
		builder.append(tableName).append(" where ");
		List<Field> fields = getListField(entity);
		List<Object> values = new ArrayList<Object>();
		for (Field field : fields) {
			if(field.isAnnotationPresent(DbField.class) == true){
				String key = camelConvertFieldName(field.getName());
				field.setAccessible(true);
				Object value = field.get(entity);
				if (value==null) continue;
				if(value.getClass() == String.class&&StringUtils.isEmpty((String)value)){
					continue;
				}
				builder.append(key).append(" = ? and ");
				values.add(value);
			}
		}
		if (values.size()<1) return null;
		builder.delete(builder.lastIndexOf(" and "), builder.length());
		String sql=builder.toString();
		return new SqlQuery(sql, values.toArray());
	}
	
	@Override
	public <T> SqlQuery buildDeleteSqlByID(T entity) throws Exception {
		Class<?> entityClass = entity.getClass();
		StringBuilder builder = new StringBuilder("delete from ");
		String tableName=camelConvertFieldName(entityClass.getSimpleName());
		builder.append(tableName).append(" where ");
		List<Field> fields = getListField(entity);
		List<Object> values = new ArrayList<Object>();
		for (Field field : fields) {
			if(field.isAnnotationPresent(IdField.class) == true){
				String key = camelConvertFieldName(field.getName());
				field.setAccessible(true);
				Object value = field.get(entity);
				if (value==null) continue;
				if(value.getClass() == String.class&&StringUtils.isEmpty((String)value)){
					continue;
				}
				builder.append(key).append(" = ? ");
				values.add(value);
			}
		}
		
		String sql=builder.toString();
		return new SqlQuery(sql, values.toArray());
	}
	
	
	
	/**
	 * 生成根据id查实体的query对象 
	 */
	@Override
	public <T> SqlQuery buildFindByIdSql(T entity, Object id) throws Exception {
		SqlQuery query=new SqlQuery();
		query.appendSql("select * from ").appendSql(camelConvertFieldName(entity.getClass().getSimpleName()));
		query.appendSql(" where ").appendSql(getIdFieldName(entity)).appendSql(" = ?");
		query.addParam(id);
		return query;
	}
	
	/**
	 * 生成根据id查实体的query对象 
	 */
	@Override
	public <T> SqlQuery buildFindByIdSql(T entity) throws Exception {
		SqlQuery query=new SqlQuery();
		query.appendSql("select * from ").appendSql(camelConvertFieldName(entity.getClass().getSimpleName()));
		query.appendSql(" where ").appendSql(getIdFieldName(entity)).appendSql(" = ?");
		
		List<Field> fields = getListField(entity);
		List<Object> values = new ArrayList<Object>();
		Object value = null;
		for (Field field : fields) {
			if(field.isAnnotationPresent(IdField.class)){
				field.setAccessible(true);
				value = field.get(entity);
				break;
			}
		}
		
		query.addParam(value);
		return query;
	}
	
	@Override
	public <T> SqlQuery buildFindByIdsSql(T entity,List<?> ids) throws Exception {
		SqlQuery query=new SqlQuery();
		query.appendSql("select * from ").appendSql(camelConvertFieldName(entity.getClass().getSimpleName()));
		query.appendSql(" where ").appendSql(getIdFieldName(entity)).appendSql(" in (");
		for (Object id : ids) {
			query.appendSql(" ?,").addParam(id);
		}
		query.getSqlData().deleteCharAt(query.getSqlData().length()-1);
		query.appendSql(")");
		return query;
	}
    

    private static class SqlBuildHolder {
        static SqlBuilder instance = new MySqlSqlBuilder();
        private SqlBuildHolder() {
        }
    }

}
