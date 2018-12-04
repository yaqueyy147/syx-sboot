//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access.dialect;

import com.syx.sboot.common.access.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySqlDialect implements Dialect {
    private static final Logger log = LoggerFactory.getLogger(MySqlDialect.class);

    public MySqlDialect() {
    }

    public static Dialect getInstance() {
        return MySqlDialect.DialectHolder.instance;
    }

    public String getPageQuerySql(PageQuery pageQuery) {
        if(!pageQuery.isEnded()) {
            pageQuery.end();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
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

        sb.append(" limit ");
        sb.append(pageQuery.getStart());
        sb.append(",");
        sb.append(pageQuery.getLimit());
        log.debug("MySql分页语句：{}", sb.toString());
        return sb.toString();
    }

    private static class DialectHolder {
        static Dialect instance = new MySqlDialect();

        private DialectHolder() {
        }
    }
}
