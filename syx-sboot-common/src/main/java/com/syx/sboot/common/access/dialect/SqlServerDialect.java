//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access.dialect;

import com.syx.sboot.common.access.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlServerDialect implements Dialect {
    private static final Logger log = LoggerFactory.getLogger(SqlServerDialect.class);

    public SqlServerDialect() {
    }

    public static Dialect getInstance() {
        return SqlServerDialect.DialectHolder.instance;
    }

    public String getPageQuerySql(PageQuery pageQuery) {
        if(!pageQuery.isEnded()) {
            pageQuery.end();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select * from (select top ");
        sb.append(pageQuery.getStart() + pageQuery.getLimit());
        sb.append(" row_number() over (");
        sb.append(" ORDER BY CURRENT_TIMESTAMP ");
        sb.append(") as _mssql_row_number_, ");
        sb.append(pageQuery.getSqlSelect());
        sb.append(" from ");
        sb.append(pageQuery.getSqlFrom());
        if(pageQuery.hasWhereCondition()) {
            sb.append(" where ");
            sb.append(pageQuery.getSqlWhere());
        }

        if(pageQuery.hasGroupbyCondition()) {
            sb.append(" group by ");
            sb.append(pageQuery.getSqlGroupby());
        }

        if(pageQuery.hasHavingCondition()) {
            sb.append(" having ");
            sb.append(pageQuery.getSqlHaving());
        }

        sb.append(") _sub_ where _mssql_row_number_ > ");
        sb.append(pageQuery.getStart());
        log.debug("SqlServer分页语句：{}", sb.toString());
        return sb.toString();
    }

    private static class DialectHolder {
        static Dialect instance = new SqlServerDialect();

        private DialectHolder() {
        }
    }
}
