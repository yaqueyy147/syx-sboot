//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access.dialect;

import com.syx.sboot.common.access.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleDialect implements Dialect {
    private static final Logger log = LoggerFactory.getLogger(OracleDialect.class);

    public OracleDialect() {
    }

    public static Dialect getInstance() {
        return OracleDialect.DialectHolder.instance;
    }

    public String getPageQuerySql(PageQuery pageQuery) {
        if(!pageQuery.isEnded()) {
            pageQuery.end();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select * from (select _sub_.*, rownum as _oracle_row_number_ from (select ");
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

        sb.append(") _sub_ where rownum <= ");
        sb.append(pageQuery.getStart() + pageQuery.getLimit());
        sb.append(") where _oracle_row_number_ > ");
        sb.append(pageQuery.getStart());
        log.debug("Oracle分页语句：{}", sb.toString());
        return sb.toString();
    }

    private static class DialectHolder {
        static Dialect instance = new OracleDialect();

        private DialectHolder() {
        }
    }
}
