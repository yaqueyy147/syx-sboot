//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

import javax.sql.DataSource;
import java.sql.SQLException;

public class TxRunnerHolder {
    private static final ThreadLocal<TxRunner> container = new ThreadLocal();

    public TxRunnerHolder() {
    }

    public static TxRunnerStatus getTxRunnerStatus(DataSource dataSource) throws SQLException {
        TxRunner runner = (TxRunner)container.get();
        if(runner != null) {
            return new TxRunnerStatus(runner, false);
        } else {
            TxSqlRunner runner1 = new TxSqlRunner(dataSource);
            container.set(runner1);
            return new TxRunnerStatus(runner1, true);
        }
    }

    public static void commit(TxRunnerStatus txRunnerStatus) throws SQLException {
        if(txRunnerStatus != null) {
            if(txRunnerStatus.isNewTxRunner()) {
                TxRunner runner = txRunnerStatus.getTxRunner();

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

    public static void rollback(TxRunnerStatus txRunnerStatus) throws SQLException {
        if(txRunnerStatus != null) {
            TxRunner runner = txRunnerStatus.getTxRunner();
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
