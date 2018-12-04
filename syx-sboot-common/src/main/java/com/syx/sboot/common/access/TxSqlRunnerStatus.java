//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

public class TxSqlRunnerStatus {
    private TxSqlRunner txSqlRunner;
    private boolean newTxSqlRunner;

    public TxSqlRunnerStatus(TxSqlRunner txSqlRunner, boolean newTxSqlRunner) {
        this.txSqlRunner = txSqlRunner;
        this.newTxSqlRunner = newTxSqlRunner;
    }

    public TxSqlRunner getTxSqlRunner() {
        return this.txSqlRunner;
    }

    public void setTxSqlRunner(TxSqlRunner txSqlRunner) {
        this.txSqlRunner = txSqlRunner;
    }

    public boolean isNewTxSqlRunner() {
        return this.newTxSqlRunner;
    }
}
