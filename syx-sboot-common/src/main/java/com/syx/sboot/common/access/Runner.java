//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;

public interface Runner {
    <T> T query(String var1, ResultSetHandler<T> var2) throws SQLException;

    <T> T query(String var1, ResultSetHandler<T> var2, Object... var3) throws SQLException;

    int update(String var1) throws SQLException;

    int update(String var1, Object... var2) throws SQLException;
}
