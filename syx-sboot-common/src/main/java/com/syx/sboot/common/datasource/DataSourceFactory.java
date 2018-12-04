//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.datasource;


import com.syx.sboot.common.datasource.pooled.PooledDataSourceFactory;

public abstract class DataSourceFactory {
    public static final String SYSTEM_DATASOURCE_ID = "system";

    public DataSourceFactory() {
    }

    public static DataSourceFactory getInstance() {
        return DataSourceFactory.DataSourceFactoryHolder.instance;
    }

    public TypedDataSource getSystemDataSource() {
        return this.getDataSourceById("system");
    }

    public abstract TypedDataSource getDataSourceById(String var1);

    public abstract TypedDataSource getDataSourceByCode(String var1);

    public void resetSystemDataSource() {
        this.resetDataSourceById("system");
    }

    public abstract void resetDataSourceById(String var1);

    public abstract void resetDataSourceByCode(String var1);

    private static class DataSourceFactoryHolder {
        static DataSourceFactory instance = new PooledDataSourceFactory();

        private DataSourceFactoryHolder() {
        }
    }
}
