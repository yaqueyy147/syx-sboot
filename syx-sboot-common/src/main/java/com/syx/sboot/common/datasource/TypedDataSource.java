//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.datasource;

import javax.sql.DataSource;

public interface TypedDataSource extends DataSource {
    String TYPE_NAME_MYSQL = "mysql";
    String TYPE_NAME_SQLSERVER = "sqlserver";
    String TYPE_NAME_ORACLE = "oracle";
    String TYPE_NAME_POSTGRESQL = "postgresql";
    String TYPE_NAME_DB2 = "db2";
    String TYPE_NAME_H2 = "h2";
    String TYPE_NAME_UNKNOWN = "";

    String getType();
}
