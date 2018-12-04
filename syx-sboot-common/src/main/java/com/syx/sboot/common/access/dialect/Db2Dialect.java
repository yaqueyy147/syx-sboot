//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access.dialect;

import com.syx.sboot.common.access.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Db2Dialect implements Dialect {
    private static final Logger log = LoggerFactory.getLogger(Db2Dialect.class);

    public Db2Dialect() {
    }

    public static Dialect getInstance() {
        return Db2Dialect.DialectHolder.instance;
    }

    public String getPageQuerySql(PageQuery pageQuery) {
        if(!pageQuery.isEnded()) {
            pageQuery.end();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select * from ( ");
        sb.append("select row_number() as _db2_row_number_, ");
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

        sb.append(" ) _sub_ where _sub_._db2_row_number_ > ");
        sb.append(pageQuery.getStart());
        sb.append(" and _sub_._db2_row_number_ <= ");
        sb.append(pageQuery.getStart() + pageQuery.getLimit());
        log.debug("DB2分页语句：{}", sb.toString());
        return sb.toString();
    }

    private static class DialectHolder {
        static Dialect instance = new Db2Dialect();

        private DialectHolder() {
        }
    }
}
