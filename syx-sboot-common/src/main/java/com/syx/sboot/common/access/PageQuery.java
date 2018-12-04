//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

import com.syx.sboot.common.access.dialect.*;
import com.syx.sboot.common.datasource.TypedDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PageQuery {
	private static final Logger log = LoggerFactory.getLogger(PageQuery.class);
	private String dialectType;
	private List<Object> paramList;
	private StringBuilder sbSelect;
	private StringBuilder sbFrom;
	private StringBuilder sbWhere;
	private StringBuilder sbGroupby;
	private StringBuilder sbHaving;
	private StringBuilder sbOrderby;
	private String sqlSelect;
	private String sqlFrom;
	private String sqlWhere;
	private String sqlGroupby;
	private String sqlHaving;
	private int start = 0;
	private int limit = 10;
	private boolean hasWhereCondition = false;
	private boolean hasGroupbyCondition = false;
	private boolean hasHavingCondition = false;
	private boolean ended = false;
	private long count = 0;

	private PageQuery(String dialectType) {
		this.dialectType = dialectType;
		this.paramList = new ArrayList();
		this.sbSelect = new StringBuilder();
		this.sbFrom = new StringBuilder();
		this.sbWhere = new StringBuilder();
		this.sbGroupby = new StringBuilder();
		this.sbHaving = new StringBuilder();
		this.sbOrderby = new StringBuilder();
	}

	public static PageQuery begin(TypedDataSource dataSource) {
		return begin(dataSource == null ? "" : dataSource.getType());
	}

	public static PageQuery begin(String dialectType) {
		return new PageQuery(dialectType == null ? "" : dialectType);
	}

	public PageQuery select(String select) {
		return this.select(select, true);
	}

	public PageQuery select(String select, boolean toAdd) {
		if (toAdd && StringUtils.isNotBlank(select)) {
			String str = select.trim();
			if (str.startsWith(",")) {
				str = str.substring(1).trim();
			}

			if (str.endsWith(",")) {
				str = str.substring(0, str.length() - 1).trim();
			}

			if (StringUtils.isNotBlank(str)) {
				if (this.sbSelect.length() > 0) {
					this.sbSelect.append(",");
				}

				this.sbSelect.append(str);
				this.ended = false;
			}
		}

		return this;
	}

	public PageQuery selectOriginal(String select) {
		return this.selectOriginal(select, true);
	}

	public PageQuery selectOriginal(String select, boolean toAdd) {
		if (toAdd && StringUtils.isNotEmpty(select)) {
			this.sbSelect.append(select);
			this.ended = false;
		}

		return this;
	}

	public PageQuery from(String from) {
		return this.from(from, true, new Object[0]);
	}

	public PageQuery from(String from, boolean toAdd, Object... params) {
		if (toAdd && StringUtils.isNotBlank(from)) {
			if (this.sbFrom.length() > 0) {
				this.sbFrom.append(" ");
			}

			this.sbFrom.append(from.trim());
			this.ended = false;
			if (params != null) {
				Object[] var7 = params;
				int var6 = params.length;

				for (int var5 = 0; var5 < var6; ++var5) {
					Object obj = var7[var5];
					this.paramList.add(obj);
				}
			}
		}

		return this;
	}

	public PageQuery fromOriginal(String from) {
		return this.fromOriginal(from, true, new Object[0]);
	}

	public PageQuery fromOriginal(String from, boolean toAdd, Object... params) {
		if (toAdd && StringUtils.isNotEmpty(from)) {
			this.sbFrom.append(from);
			this.ended = false;
			if (params != null) {
				Object[] var7 = params;
				int var6 = params.length;

				for (int var5 = 0; var5 < var6; ++var5) {
					Object obj = var7[var5];
					this.paramList.add(obj);
				}
			}
		}

		return this;
	}

	public PageQuery where(String where) {
		return this.where(where, true, new Object[0]);
	}

	public PageQuery where(String where, boolean toAdd, Object... params) {
		return this.whereAndOr(where, toAdd, true, params);
	}

	public PageQuery whereOr(String where) {
		return this.whereOr(where, true, new Object[0]);
	}

	public PageQuery whereOr(String where, boolean toAdd, Object... params) {
		return this.whereAndOr(where, toAdd, false, params);
	}

	private PageQuery whereAndOr(String where, boolean toAdd, boolean isAnd, Object... params) {
		if (toAdd && StringUtils.isNotBlank(where)) {
			String str = where.trim();
			String strlower = str.toLowerCase();
			if (strlower.startsWith("and ")) {
				str = str.substring(4).trim();
			} else if (strlower.startsWith("or ")) {
				str = str.substring(3).trim();
			}

			if (strlower.endsWith(" and")) {
				str = str.substring(0, str.length() - 4).trim();
			} else if (strlower.endsWith(" or")) {
				str = str.substring(0, str.length() - 3).trim();
			}

			if (StringUtils.isNotBlank(str)) {
				if (this.sbWhere.length() > 0 && !this.sbWhere.toString().toLowerCase().endsWith(" where")) {
					if (isAnd) {
						this.sbWhere.append(" and ");
					} else {
						this.sbWhere.append(" or ");
					}
				}

				this.sbWhere.append(str);
				this.ended = false;
				if (params != null) {
					Object[] var10 = params;
					int var9 = params.length;

					for (int var8 = 0; var8 < var9; ++var8) {
						Object obj = var10[var8];
						this.paramList.add(obj);
					}
				}
			}
		}

		return this;
	}

	public PageQuery whereOriginal(String where) {
		return this.whereOriginal(where, true, new Object[0]);
	}

	public PageQuery whereOriginal(String where, boolean toAdd, Object... params) {
		if (toAdd && StringUtils.isNotEmpty(where)) {
			this.sbWhere.append(where);
			this.ended = false;
			if (params != null) {
				Object[] var7 = params;
				int var6 = params.length;

				for (int var5 = 0; var5 < var6; ++var5) {
					Object obj = var7[var5];
					this.paramList.add(obj);
				}
			}
		}

		return this;
	}

	public PageQuery groupby(String groupby) {
		return this.groupby(groupby, true);
	}

	public PageQuery groupby(String groupby, boolean toAdd) {
		if (toAdd && StringUtils.isNotBlank(groupby)) {
			String str = groupby.trim();
			if (str.startsWith(",")) {
				str = str.substring(1).trim();
			}

			if (str.endsWith(",")) {
				str = str.substring(0, str.length() - 1).trim();
			}

			if (StringUtils.isNotBlank(str)) {
				if (this.sbGroupby.length() > 0) {
					this.sbGroupby.append(",");
				}

				this.sbGroupby.append(str);
				this.ended = false;
			}
		}

		return this;
	}

	public PageQuery groupbyOriginal(String groupby) {
		return this.groupbyOriginal(groupby, true);
	}

	public PageQuery groupbyOriginal(String groupby, boolean toAdd) {
		if (toAdd && StringUtils.isNotEmpty(groupby)) {
			this.sbGroupby.append(groupby);
			this.ended = false;
		}

		return this;
	}

	public PageQuery having(String having) {
		return this.having(having, true, new Object[0]);
	}

	public PageQuery having(String having, boolean toAdd, Object... params) {
		return this.havingAndOr(having, toAdd, true, params);
	}

	public PageQuery havingOr(String having) {
		return this.havingOr(having, true, new Object[0]);
	}

	public PageQuery havingOr(String having, boolean toAdd, Object... params) {
		return this.havingAndOr(having, toAdd, false, params);
	}

	private PageQuery havingAndOr(String having, boolean toAdd, boolean isAnd, Object... params) {
		if (toAdd && StringUtils.isNotBlank(having)) {
			String str = having.trim();
			String strlower = str.toLowerCase();
			if (strlower.startsWith("and ")) {
				str = str.substring(4).trim();
			} else if (strlower.startsWith("or ")) {
				str = str.substring(3).trim();
			}

			if (strlower.endsWith(" and")) {
				str = str.substring(0, str.length() - 4).trim();
			} else if (strlower.endsWith(" or")) {
				str = str.substring(0, str.length() - 3).trim();
			}

			if (StringUtils.isNotBlank(str)) {
				if (this.sbHaving.length() > 0 && !this.sbHaving.toString().toLowerCase().endsWith(" where")) {
					if (isAnd) {
						this.sbHaving.append(" and ");
					} else {
						this.sbHaving.append(" or ");
					}
				}

				this.sbHaving.append(str);
				this.ended = false;
				if (params != null) {
					Object[] var10 = params;
					int var9 = params.length;

					for (int var8 = 0; var8 < var9; ++var8) {
						Object obj = var10[var8];
						this.paramList.add(obj);
					}
				}
			}
		}

		return this;
	}

	public PageQuery havingOriginal(String having) {
		return this.havingOriginal(having, true, new Object[0]);
	}

	public PageQuery havingOriginal(String having, boolean toAdd, Object... params) {
		if (toAdd && StringUtils.isNotEmpty(having)) {
			this.sbHaving.append(having);
			this.ended = false;
			if (params != null) {
				Object[] var7 = params;
				int var6 = params.length;

				for (int var5 = 0; var5 < var6; ++var5) {
					Object obj = var7[var5];
					this.paramList.add(obj);
				}
			}
		}

		return this;
	}

	public PageQuery orderby(String orderbyField) {
		return this.orderby(orderbyField, "asc");
	}

	public PageQuery orderby(String orderbyField, String orderbyDirection) {
		return this.orderby(orderbyField, orderbyDirection, true);
	}

	public PageQuery orderby(String orderbyField, String orderbyDirection, boolean toAdd) {
		if (toAdd && StringUtils.isNotBlank(orderbyField)) {
			String field = orderbyField.trim();
			String direction = orderbyDirection == null ? "asc" : orderbyDirection.trim().toLowerCase();
			if (!"asc".equals(direction) && !"desc".equals(direction)) {
				direction = "asc";
			}

			if (this.sbOrderby.length() > 0) {
				this.sbOrderby.append(",");
			}

			this.sbOrderby.append(field);
			this.sbOrderby.append(" ");
			this.sbOrderby.append(direction);
			this.ended = false;
		}

		return this;
	}

	public PageQuery start(int start) {
		this.start = start;
		this.ended = false;
		return this;
	}

	public PageQuery limit(int limit) {
		this.limit = limit;
		this.ended = false;
		return this;
	}

	public PageQuery end() {
		if (this.ended) {
			return this;
		} else {
			this.sqlSelect = this.sbSelect.toString();
			if (StringUtils.isBlank(this.sqlSelect)) {
				log.error("Select语句块没有设置！");
				throw new RuntimeException("Select语句块没有设置！");
			} else {
				this.sqlFrom = this.sbFrom.toString();
				if (StringUtils.isBlank(this.sqlFrom)) {
					log.error("From语句块没有设置！");
					throw new RuntimeException("From语句块没有设置！");
				} else {
					this.sqlWhere = this.sbWhere.toString();
					if (StringUtils.isBlank(this.sqlWhere)) {
						this.hasWhereCondition = false;
					} else {
						this.hasWhereCondition = true;
					}

					this.sqlGroupby = this.sbGroupby.toString();
					if (StringUtils.isBlank(this.sqlGroupby)) {
						this.hasGroupbyCondition = false;
					} else {
						this.hasGroupbyCondition = true;
					}

					this.sqlHaving = this.sbHaving.toString();
					if (StringUtils.isBlank(this.sqlHaving)) {
						this.hasHavingCondition = false;
					} else {
						this.hasHavingCondition = true;
						if (!this.hasGroupbyCondition) {
							log.error("Groupby语句块没有设置！");
							throw new RuntimeException("Groupby语句块没有设置！");
						}
					}

					if (this.getStart() < 0) {
						log.error("起始记录数不能小于0！");
						throw new RuntimeException("起始记录数不能小于0！");
					} else if (this.getLimit() <= 0) {
						log.error("每页记录数必须大于0！");
						throw new RuntimeException("每页记录数必须大于0！");
					} else {
						this.ended = true;
						return this;
					}
				}
			}
		}
	}

	public String getSqlSelect() {
		return this.sqlSelect;
	}

	public String getSqlFrom() {
		return this.sqlFrom;
	}

	public String getSqlWhere() {
		return this.sqlWhere;
	}

	public String getSqlGroupby() {
		return this.sqlGroupby;
	}

	public String getSqlHaving() {
		return this.sqlHaving;
	}

	public int getStart() {
		return this.start;
	}

	public int getLimit() {
		return this.limit;
	}

	public String getDialectType() {
		return this.dialectType;
	}

	public boolean isEnded() {
		return this.ended;
	}

	public boolean hasWhereCondition() {
		return this.hasWhereCondition;
	}

	public boolean hasGroupbyCondition() {
		return this.hasGroupbyCondition;
	}

	public boolean hasHavingCondition() {
		return this.hasHavingCondition;
	}

	public Object[] getParamArray() {
		return this.paramList != null ? this.paramList.toArray() : new Object[0];
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getPageCountSql() {
		if (!this.ended) {
			this.end();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ");
		sb.append(this.sqlFrom);
		if (this.hasWhereCondition) {
			sb.append(" where ");
			sb.append(this.sqlWhere);
		}

		if (this.hasGroupbyCondition) {
			sb.append(" group by ");
			sb.append(this.sqlGroupby);
		}

		if (this.hasHavingCondition) {
			sb.append(" having ");
			sb.append(this.sqlHaving);
		}

		log.debug("查询总记录数的SQL语句：{}", sb.toString());
		return sb.toString();
	}

	public String getPageQuerySql() {
		if (this.dialectType.equalsIgnoreCase("mysql")) {
			return MySqlDialect.getInstance().getPageQuerySql(this);
		} else if (this.dialectType.equalsIgnoreCase("sqlserver")) {
			return SqlServerDialect.getInstance().getPageQuerySql(this);
		} else if (this.dialectType.equalsIgnoreCase("oracle")) {
			return OracleDialect.getInstance().getPageQuerySql(this);
		} else if (this.dialectType.equalsIgnoreCase("postgresql")) {
			return PostgresqlDialect.getInstance().getPageQuerySql(this);
		} else if (this.dialectType.equalsIgnoreCase("db2")) {
			return Db2Dialect.getInstance().getPageQuerySql(this);
		} else if (this.dialectType.equalsIgnoreCase("h2")) {
			return H2Dialect.getInstance().getPageQuerySql(this);
		} else if (this.dialectType.equalsIgnoreCase("")) {
			log.error("获取分页查询语句出错：数据库类型未知！");
			throw new RuntimeException("获取分页查询语句出错：数据库类型未知！");
		} else {
			log.error("获取分页查询语句出错：数据库类型[{}]暂不支持！", this.dialectType);
			throw new RuntimeException("获取分页查询语句出错：数据库类型[" + this.dialectType + "]暂不支持！");
		}
	}
}
