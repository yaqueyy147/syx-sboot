//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

import javax.sql.DataSource;
import java.sql.SQLException;

public class TxSqlRunnerHolder {
    private static final ThreadLocal<TxSqlRunner> container = new ThreadLocal();

    public TxSqlRunnerHolder() {
    }

    public static TxSqlRunnerStatus getTxSqlRunnerStatus(DataSource dataSource) throws SQLException {
        TxSqlRunner runner = (TxSqlRunner)container.get();
        if(runner != null) {
            return new TxSqlRunnerStatus(runner, false);
        } else {
            runner = new TxSqlRunner(dataSource);
            container.set(runner);
            return new TxSqlRunnerStatus(runner, true);
        }
    }

    public static void commit(TxSqlRunnerStatus txSqlRunnerStatus) throws SQLException {
        if(txSqlRunnerStatus != null) {
            if(txSqlRunnerStatus.isNewTxSqlRunner()) {
                TxSqlRunner runner = txSqlRunnerStatus.getTxSqlRunner();

                try {
                    runner.commit();
                } finally {
                    try {
                        runner.close();
                    } finally {
                        container.remove();
                    }
                }

            }
        }
    }

    public static void rollback(TxSqlRunnerStatus txSqlRunnerStatus) throws SQLException {
        if(txSqlRunnerStatus != null) {
            TxSqlRunner runner = txSqlRunnerStatus.getTxSqlRunner();
            if(!runner.isClosed()) {
                try {
                    runner.rollback();
                } finally {
                    try {
                        runner.close();
                    } finally {
                        container.remove();
                    }
                }

            }
        }
    }
}
