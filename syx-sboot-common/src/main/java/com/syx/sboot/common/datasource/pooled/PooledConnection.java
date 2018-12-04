//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.datasource.pooled;

import com.syx.sboot.common.exception.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

class PooledConnection implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(PooledConnection.class);
    private static final String CLOSE = "close";
    private static final Class<?>[] IFACES = new Class[]{Connection.class};
    private int hashCode = 0;
    private PooledDataSource dataSource;
    private Connection realConnection;
    private Connection proxyConnection;
    private long checkoutTimestamp;
    private long createdTimestamp;
    private long lastUsedTimestamp;
    private int connectionTypeCode;
    private boolean valid;

    public PooledConnection(Connection connection, PooledDataSource dataSource) {
        this.hashCode = connection.hashCode();
        this.realConnection = connection;
        this.dataSource = dataSource;
        this.createdTimestamp = System.currentTimeMillis();
        this.lastUsedTimestamp = System.currentTimeMillis();
        this.valid = true;
        this.proxyConnection = (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), IFACES, this);
    }

    public void invalidate() {
        this.valid = false;
    }

    public boolean isValid() {
        return this.valid && this.realConnection != null && this.dataSource.pingConnection(this);
    }

    public Connection getRealConnection() {
        return this.realConnection;
    }

    public Connection getProxyConnection() {
        return this.proxyConnection;
    }

    public int getRealHashCode() {
        return this.realConnection == null?0:this.realConnection.hashCode();
    }

    public int getConnectionTypeCode() {
        return this.connectionTypeCode;
    }

    public void setConnectionTypeCode(int connectionTypeCode) {
        this.connectionTypeCode = connectionTypeCode;
    }

    public long getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public long getLastUsedTimestamp() {
        return this.lastUsedTimestamp;
    }

    public void setLastUsedTimestamp(long lastUsedTimestamp) {
        this.lastUsedTimestamp = lastUsedTimestamp;
    }

    public long getTimeElapsedSinceLastUse() {
        return System.currentTimeMillis() - this.lastUsedTimestamp;
    }

    public long getAge() {
        return System.currentTimeMillis() - this.createdTimestamp;
    }

    public long getCheckoutTimestamp() {
        return this.checkoutTimestamp;
    }

    public void setCheckoutTimestamp(long timestamp) {
        this.checkoutTimestamp = timestamp;
    }

    public long getCheckoutTime() {
        return System.currentTimeMillis() - this.checkoutTimestamp;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        return obj instanceof PooledConnection?this.realConnection.hashCode() == ((PooledConnection)obj).realConnection.hashCode():(obj instanceof Connection?this.hashCode == obj.hashCode():false);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if("close".hashCode() == methodName.hashCode() && "close".equals(methodName)) {
            this.dataSource.pushConnection(this);
            return null;
        } else {
            try {
                if(!Object.class.equals(method.getDeclaringClass())) {
                    this.checkConnection();
                }

                return method.invoke(this.realConnection, args);
            } catch (Throwable var7) {
                Throwable ut = ExceptionUtil.unwrapThrowable(var7);
                log.error("连接代理的方法调用出现错误：{}", ut.getMessage());
                throw ut;
            }
        }
    }

    private void checkConnection() throws SQLException {
        if(!this.valid) {
            log.error("连接代理的方法调用出现错误：连接已经失效。");
            throw new SQLException("连接代理的方法调用出现错误：连接已经失效。");
        }
    }
}
