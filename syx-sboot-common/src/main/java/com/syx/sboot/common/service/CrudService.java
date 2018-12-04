/**
 * Copyright 2014-2015 <a href="http://www.hlideal.com">dingding</a> All rights reserved.
 */
package com.syx.sboot.common.service;

import com.syx.sboot.common.access.PageQuery;
import com.syx.sboot.common.access.TxSqlRunner;
import com.syx.sboot.common.access.sql.SqlBuilder;
import com.syx.sboot.common.access.sql.SqlQuery;
import com.syx.sboot.common.datasource.DataSourceFactory;
import com.syx.sboot.common.datasource.TypedDataSource;
import com.syx.sboot.common.entity.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class CrudService<T extends DataEntity<T>> extends BaseService {
	private String dbcode = "";//system
	private String dbtablename = "";
	private TypedDataSource typdatasource = null;

	public void initProperties(T entity) {
		// 获取数据表名
		if(StringUtils.isEmpty(dbtablename)){
			if (entity.getClass().isAnnotationPresent(DbTableField.class) == true) {
				DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
				if (!StringUtils.isEmpty(tfiled.value())) {
					dbtablename = tfiled.value();
				}
			}
		}
		// 获取数据源code
		if(StringUtils.isEmpty(dbcode)){
			if (entity.getClass().isAnnotationPresent(DbCodeField.class) == true) {
				DbCodeField tfiled = entity.getClass().getAnnotation(DbCodeField.class);
				if (!StringUtils.isEmpty(tfiled.value())) {
					dbcode = tfiled.value();
				}
	
			}
		}

		if(dbcode.equals("hlideal_db_log")){
			dbcode = "system";
		}

	}

	public TypedDataSource getTypdatasource() {
		if (typdatasource == null) {
			typdatasource = DataSourceFactory.getInstance().getDataSourceByCode(dbcode);
		}
		return typdatasource;
	}

	/**
	 * 获取单条数据
	 * 
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		initProperties(entity);
		SqlQuery sqlquery = new SqlQuery();
		TxSqlRunner runner;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dbcode));
			try {
				TypedDataSource typdatasource = getTypdatasource();
				if (typdatasource != null) {
					String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
					SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();
					sqlquery = sqlbuilder.buildFindByIdSql(entity);

					List<T> list = (List<T>) runner.query(sqlquery.getSql(), new BeanListHandler(entity.getClass()),
							sqlquery.getParams());
					if (list != null && list.size() > 0) {
						return list.get(0);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} finally {
				if (runner != null)
					runner.close();
			}
		} catch (SQLException e1) {
			;
		}
		return null;
	}
	
	public T get(String dscode, T entity) {
		initProperties(entity);
		SqlQuery sqlquery = new SqlQuery();
		TxSqlRunner runner;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			try {
				TypedDataSource typdatasource = getTypdatasource();
				if (typdatasource != null) {
					String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
					SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();
					sqlquery = sqlbuilder.buildFindByIdSql(entity);

					List<T> list = (List<T>) runner.query(sqlquery.getSql(), new BeanListHandler(entity.getClass()),
							sqlquery.getParams());
					if (list != null && list.size() > 0) {
						return list.get(0);
					}
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (runner != null)
					runner.close();
			}
		} catch (SQLException e1) {
			;
		}
		return null;
	}

	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		initProperties(entity);
		Page<T> temp = getDataList(entity, "");
		if (temp != null) {
			return temp.getList();
		}
		return new ArrayList<T>();
	}
	
	public List<T> findList(String dscode, T entity) {
		initProperties(entity);
		Page<T> temp = getDataList(dscode, entity, "");
		if (temp != null) {
			return temp.getList();
		}
		return new ArrayList<T>();
	}


	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity, String condition) {
		initProperties(entity);
		Page<T> temp = getDataList(entity, condition);
		if (temp != null) {
			return temp.getList();
		}
		return new ArrayList<T>();
	}
	
	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findList(String dscode, T entity, String condition) {
		initProperties(entity);
		Page<T> temp = getDataList(dscode, entity, condition);
		if (temp != null) {
			return temp.getList();
		}
		return new ArrayList<T>();
	}

	/**
	 * 查询分页数据
	 * 
	 * @param page
	 *            分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		initProperties(entity);
		entity.setPage(page);
		Page<T> temp = getDataList(entity, "");
		if (temp != null) {
			page.setCount(temp.getCount());
			page.setList(temp.getList());
		}
		return page;
	}

	/**
	 * 查询分页数据
	 * 
	 * @param page
	 *            分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPageByCondition(Page<T> page, T entity, String condition) {
		initProperties(entity);
		entity.setPage(page);
		Page<T> temp = getDataList(entity, condition);
		if (temp != null) {
			page.setCount(temp.getCount());
			page.setList(temp.getList());
		}
		return page;
	}
	
	public Page<T> findPageByCondition(String dscode, Page<T> page, T entity, String condition) {
		initProperties(entity);
		entity.setPage(page);
		Page<T> temp = getDataList(dscode, entity, condition);
		if (temp != null) {
			page.setCount(temp.getCount());
			page.setList(temp.getList());
		}
		return page;
	}

	public Page<T> getDataList(T entity, String condition) {
		if(StringUtils.isEmpty(condition) || !condition.contains("where")){
			condition = " 1=1 and deleteflag='0' " + condition;
		}
		initProperties(entity);
		Page<T> list = new Page<T>();
		try {
			TypedDataSource dataSource = DataSourceFactory.getInstance().getDataSourceByCode(dbcode);
			TxSqlRunner runner = new TxSqlRunner(dataSource);

			int start = 0;
			int pagesize = 0;
			// 分页
			if (entity.getPage() != null && entity.getPage().getPageSize() > 0) {
				start = entity.getPage().getFirstResult();
				pagesize = entity.getPage().getMaxResults();
				PageQuery pageQuery = PageQuery.begin(dataSource).select("*").from(dbtablename + "")
						.whereOriginal(condition, !StringUtils.isEmpty(condition), null)
						.start(start).limit(pagesize)
						.end();
				try {
					int totalRows = 0;
					if(entity.getPage().getCount() <= 0){
						@SuppressWarnings("unchecked")
						Object objTotalRows = runner.query(pageQuery.getPageCountSql(), new ScalarHandler(),
								pageQuery.getParamArray());
						if (objTotalRows != null) {
							totalRows = Integer.parseInt(objTotalRows.toString());
						}
					}else{
						totalRows = (int) entity.getPage().getCount();
					}

					if (totalRows > 0) {
						list.setCount(totalRows);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						List orderList = (List) runner.query(pageQuery.getPageQuerySql(),
								new BeanListHandler(entity.getClass()), pageQuery.getParamArray());

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
			} else {
				typdatasource = getTypdatasource();
				String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
				SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();

				SqlQuery query = new SqlQuery();
				query.appendSql("select * from ").appendSql(dbtablename);
				query.appendSql(" where ").appendSql(condition);
				try {
					List<T> listtemp = (List<T>) runner.query(query.getSql(), new BeanListHandler(entity.getClass()),
							query.getParams());
					if (listtemp != null && listtemp.size() > 0) {
						list.setList(listtemp);
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
	
	public Page<T> getDataList(String dscode, T entity, String condition) {
		if(StringUtils.isEmpty(condition) || !condition.contains("where")){
			condition = " 1=1 and deleteflag='0' " + condition;
		}
		initProperties(entity);
		Page<T> list = new Page<T>();
		try {
			TypedDataSource dataSource = DataSourceFactory.getInstance().getDataSourceByCode(dscode);
			TxSqlRunner runner = new TxSqlRunner(dataSource);

			int start = 0;
			int pagesize = 0;
			// 分页
			if (entity.getPage() != null && entity.getPage().getPageSize() > 0) {
				start = entity.getPage().getFirstResult();
				pagesize = entity.getPage().getMaxResults();
				PageQuery pageQuery = PageQuery.begin(dataSource).select("*").from(dbtablename + "")
						.whereOriginal(condition, !StringUtils.isEmpty(condition), null)
						.start(start).limit(pagesize)
						.end();
				try {
					int totalRows = 0;
					if(entity.getPage().getCount() <= 0){
						@SuppressWarnings("unchecked")
						Object objTotalRows = runner.query(pageQuery.getPageCountSql(), new ScalarHandler(),
								pageQuery.getParamArray());
						if (objTotalRows != null) {
							totalRows = Integer.parseInt(objTotalRows.toString());
						}
					}else{
						totalRows = (int) entity.getPage().getCount();
					}

					if (totalRows > 0) {
						list.setCount(totalRows);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						List orderList = (List) runner.query(pageQuery.getPageQuerySql(),
								new BeanListHandler(entity.getClass()), pageQuery.getParamArray());

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
			} else {
				typdatasource = getTypdatasource();
				String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
				SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();

				SqlQuery query = new SqlQuery();
				query.appendSql("select * from ").appendSql(dbtablename);
				query.appendSql(" where ").appendSql(condition);
				try {
					List<T> listtemp = (List<T>) runner.query(query.getSql(), new BeanListHandler(entity.getClass()),
							query.getParams());
					if (listtemp != null && listtemp.size() > 0) {
						list.setList(listtemp);
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
	
	
	public Page<T> getDataList(String dbcode, Page<T> page, String tablename, SqlQuery sqlquery, Class<T> clazz) {
		Page<T> list = new Page<T>();
		try {
			TypedDataSource dataSource = DataSourceFactory.getInstance().getDataSourceByCode(dbcode);
			TxSqlRunner runner = new TxSqlRunner(dataSource);

			int start = 0;
			int pagesize = 0;
			// 分页
			if (page != null && page.getPageSize() > 0) {
				start = page.getFirstResult();
				pagesize = page.getMaxResults();
				PageQuery pageQuery = PageQuery.begin(dataSource).select("*").from(tablename + "")
						.whereOriginal(sqlquery.getSql(), !StringUtils.isEmpty(sqlquery.getSql()), sqlquery.getParams())
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
								new BeanListHandler(clazz.getClass()), pageQuery.getParamArray());

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
			} else {
				SqlQuery query = new SqlQuery();
				query.appendSql("select * from ").appendSql(tablename);
				query.appendSql(" where 1=1 ").appendSql(sqlquery.getSql());
				try {
					List<T> listtemp = (List<T>) runner.query(query.getSql(), new BeanListHandler(clazz.getClass()),
							sqlquery.getParams());
					if (listtemp != null && listtemp.size() > 0) {
						list.setList(listtemp);
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

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param entity
	 */
	public boolean save(T entity) {
		initProperties(entity);
		SqlQuery sqlquery = new SqlQuery();
		TxSqlRunner runner;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dbcode));
			try {
				TypedDataSource typdatasource = getTypdatasource();
				if (typdatasource != null) {
					String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
					SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();

					if (entity.getIsNewRecord()) {
						entity.preInsert();
						sqlquery = sqlbuilder.buildSaveSql(entity);
					} else {
						entity.preUpdate();
						sqlquery = sqlbuilder.buildUpdateSql(entity);
					}
					runner.update(sqlquery.getSql(), sqlquery.getParams());
					runner.commit();
					return true;

				}				
			} catch (Exception e) {
				logger.error("报错了-->",e);
				runner.rollback();
			} finally {
				if (runner != null)
					runner.close();
			}
		} catch (SQLException e1) {
			logger.error("报错了-->",e1);
		}
		return false;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param entity
	 */
	public boolean save(String dscode, T entity) {
		initProperties(entity);
		SqlQuery sqlquery = new SqlQuery();
		TxSqlRunner runner;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			try {
				TypedDataSource typdatasource = getTypdatasource();
				if (typdatasource != null) {
					String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
					SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();

					if (entity.getIsNewRecord()) {
						entity.preInsert();
						sqlquery = sqlbuilder.buildSaveSql(entity);
					} else {
						entity.preUpdate();
						sqlquery = sqlbuilder.buildUpdateSql(entity);
					}
					runner.update(sqlquery.getSql(), sqlquery.getParams());
					runner.commit();
					return true;
				}				
			} catch (Exception e) {
				runner.rollback();
			} finally {
				if (runner != null)
					runner.close();
			}
		} catch (SQLException e1) {
			;
		}
		return false;
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	public boolean deleteByID(T entity) {
		initProperties(entity);
		SqlQuery sqlquery = new SqlQuery();
		TxSqlRunner runner;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dbcode));
			try {
				TypedDataSource typdatasource = getTypdatasource();
				if (typdatasource != null) {
					String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
					SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();

					sqlquery = sqlbuilder.buildDeleteSqlByID(entity);

					runner.update(sqlquery.getSql(), sqlquery.getParams());
					runner.commit();
					return true;
				}				
			} catch (Exception e) {
				runner.rollback();
			} finally {
				if (runner != null)
					runner.close();
			}
		} catch (SQLException e1) {
			;
		}
		return false;
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	public boolean delete(String dscode, T entity) {
		initProperties(entity);
		SqlQuery sqlquery = new SqlQuery();
		TxSqlRunner runner;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
			try {
				TypedDataSource typdatasource = getTypdatasource();
				if (typdatasource != null) {
					String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
					SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();

					sqlquery = sqlbuilder.buildDeleteSql(entity);

					runner.update(sqlquery.getSql(), sqlquery.getParams());
					runner.commit();
					return true;
				}
				
			} catch (Exception e) {
				runner.rollback();
			} finally {
				if (runner != null)
					runner.close();
			}
		} catch (SQLException e1) {
			;
		}
		return false;
	}
	
	public boolean delete(T entity) {
		initProperties(entity);
		SqlQuery sqlquery = new SqlQuery();
		TxSqlRunner runner;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dbcode));
			try {
				TypedDataSource typdatasource = getTypdatasource();
				if (typdatasource != null) {
					String sqlbuildclass = SqlBuilderEnum.getByCode(typdatasource.getType()).getMessage();
					SqlBuilder sqlbuilder = (SqlBuilder) Class.forName(sqlbuildclass).newInstance();

					sqlquery = sqlbuilder.buildDeleteSql(entity);

					runner.update(sqlquery.getSql(), sqlquery.getParams());
					runner.commit();
					return true;
				}
				
			} catch (Exception e) {
				runner.rollback();
			} finally {
				if (runner != null)
					runner.close();
			}
		} catch (SQLException e1) {
			;
		}
		return false;
	}

}
