//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.datasource.unpooled;

import com.alibaba.druid.support.logging.Resources;
import com.syx.sboot.common.datasource.DataSourceException;
import com.syx.sboot.common.datasource.TypedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class UnpooledDataSource implements TypedDataSource {
    private static final Logger log = LoggerFactory.getLogger(UnpooledDataSource.class);
    private static Map<String, Driver> registeredDrivers = new ConcurrentHashMap();
    private String driver;
    private String url;
    private String username;
    private String password;
    private String type;
    private Boolean defaultAutoCommit;
    private Integer defaultTransactionIsolationLevel;

    static {
        Enumeration drivers = DriverManager.getDrivers();
        if(drivers != null) {
            while(drivers.hasMoreElements()) {
                Driver driver = (Driver)drivers.nextElement();
                registeredDrivers.put(driver.getClass().getName(), driver);
            }
        }

    }

    public UnpooledDataSource(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.type = this.ensureTypeFromUrl(this.url);
        this.registerDriver(this.driver);
    }

    public Connection getConnection() throws SQLException {
        return this.getConnection(this.username, this.password);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(this.url, username, password);
        this.configureConnection(connection);
        return connection;
    }

    public void setLoginTimeout(int loginTimeout) throws SQLException {
        DriverManager.setLoginTimeout(loginTimeout);
    }

    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    public void setLogWriter(PrintWriter logWriter) throws SQLException {
        DriverManager.setLogWriter(logWriter);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    public String getType() {
        return this.type;
    }

    public String getDriver() {
        return this.driver;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Boolean isDefaultAutoCommit() {
        return this.defaultAutoCommit;
    }

    public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    public Integer getDefaultTransactionIsolationLevel() {
        return this.defaultTransactionIsolationLevel;
    }

    public void setDefaultTransactionIsolationLevel(Integer defaultTransactionIsolationLevel) {
        this.defaultTransactionIsolationLevel = defaultTransactionIsolationLevel;
    }

    private void registerDriver(String driver) {
        if(!registeredDrivers.containsKey(driver)) {
            synchronized(this) {
                if(!registeredDrivers.containsKey(driver)) {
                    try {
                        Class e = Resources.classForName(driver);
                        Driver driverInstance = (Driver)e.newInstance();
                        DriverManager.registerDriver(new UnpooledDataSource.DriverProxy(driverInstance));
                        registeredDrivers.put(driver, driverInstance);
                    } catch (Exception var5) {
                        log.error("注册数据库驱动失败：{}", var5.getMessage());
                        throw new DataSourceException("注册数据库驱动失败：" + var5.getMessage());
                    }

                }
            }
        }
    }

    private String ensureTypeFromUrl(String url) {
        String jdbcUrl = url.toLowerCase();
        return jdbcUrl.indexOf(":mysql:") >= 0?"mysql":(jdbcUrl.indexOf(":sqlserver:") >= 0?"sqlserver":(jdbcUrl.indexOf(":oracle:") >= 0?"oracle":(jdbcUrl.indexOf(":postgresql:") >= 0?"postgresql":(jdbcUrl.indexOf(":db2:") >= 0?"db2":(jdbcUrl.indexOf(":h2:") >= 0?"h2":"")))));
    }

    private void configureConnection(Connection conn) throws SQLException {
        if(this.defaultAutoCommit != null && this.defaultAutoCommit.booleanValue() != conn.getAutoCommit()) {
            conn.setAutoCommit(this.defaultAutoCommit.booleanValue());
        }

        if(this.defaultTransactionIsolationLevel != null && this.defaultTransactionIsolationLevel.intValue() != conn.getTransactionIsolation()) {
            conn.setTransactionIsolation(this.defaultTransactionIsolationLevel.intValue());
        }

    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException(this.getClass().getName() + " is not a wrapper.");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public java.util.logging.Logger getParentLogger() {
        return java.util.logging.Logger.getLogger("global");
    }

    private static class DriverProxy implements Driver {
        private Driver driver;

        DriverProxy(Driver d) {
            this.driver = d;
        }

        public boolean acceptsURL(String u) throws SQLException {
            return this.driver.acceptsURL(u);
        }

        public Connection connect(String u, Properties p) throws SQLException {
            return this.driver.connect(u, p);
        }

        public int getMajorVersion() {
            return this.driver.getMajorVersion();
        }

        public int getMinorVersion() {
            return this.driver.getMinorVersion();
        }

        public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
            return this.driver.getPropertyInfo(u, p);
        }

        public boolean jdbcCompliant() {
            return this.driver.jdbcCompliant();
        }

        public java.util.logging.Logger getParentLogger() {
            return java.util.logging.Logger.getLogger("global");
        }
    }
}
