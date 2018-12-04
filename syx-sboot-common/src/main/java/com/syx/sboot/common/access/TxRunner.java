//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

import java.sql.SQLException;

public interface TxRunner extends Runner {
    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

    boolean isClosed();
}
