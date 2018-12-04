//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TxSqlRunner implements TxRunner {
    private Connection connection;
    private boolean oldAutoCommit;
    private SqlRunner runner;
    private boolean closed;

    public TxSqlRunner(DataSource dataSource) throws SQLException {
        this(dataSource.getConnection());
    }

    public TxSqlRunner(Connection connection) throws SQLException {
        this.connection = connection;
        this.oldAutoCommit = this.connection.getAutoCommit();
        if(this.oldAutoCommit) {
            this.connection.setAutoCommit(false);
        }

        this.runner = new SqlRunner();
        this.closed = false;
    }

    public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
        return this.runner.query(this.connection, sql, rsh);
    }

    public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        return this.runner.query(this.connection, sql, rsh, params);
    }

    public int update(String sql) throws SQLException {
        return this.runner.update(this.connection, sql);
    }

    public int update(String sql, Object... params) throws SQLException {
        return this.runner.update(this.connection, sql, params);
    }

    public void commit() throws SQLException {
        this.connection.commit();
    }

    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    public void close() throws SQLException {
        try {
            if(this.oldAutoCommit != this.connection.getAutoCommit()) {
                this.connection.setAutoCommit(this.oldAutoCommit);
            }
        } finally {
            try {
                this.connection.close();
            } finally {
                this.closed = true;
            }
        }

    }

    public boolean isClosed() {
        return this.closed;
    }
}
