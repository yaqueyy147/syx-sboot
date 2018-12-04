//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

public class TxRunnerStatus {
    private TxRunner txRunner;
    private boolean newTxRunner;

    public TxRunnerStatus(TxRunner txRunner, boolean newTxRunner) {
        this.txRunner = txRunner;
        this.newTxRunner = newTxRunner;
    }

    public TxRunner getTxRunner() {
        return this.txRunner;
    }

    public void setTxRunner(TxRunner txRunner) {
        this.txRunner = txRunner;
    }

    public boolean isNewTxRunner() {
        return this.newTxRunner;
    }
}
