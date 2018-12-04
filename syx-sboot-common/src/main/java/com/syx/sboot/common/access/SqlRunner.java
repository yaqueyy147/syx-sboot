//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.access;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

public class SqlRunner extends QueryRunner implements Runner {
    public SqlRunner() {
        super(true);
    }

    public SqlRunner(boolean pmdKnownBroken) {
        super(pmdKnownBroken);
    }

    public SqlRunner(DataSource dataSource) {
        super(dataSource, true);
    }

    public SqlRunner(DataSource dataSource, boolean pmdKnownBroken) {
        super(dataSource, pmdKnownBroken);
    }
}
