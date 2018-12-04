//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.syx.sboot.common.datasource.TypedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.*;

public class PooledDruidDataSource implements TypedDataSource {
    private static final Logger log = LoggerFactory.getLogger(PooledDruidDataSource.class);
    private final PooledDruidState state = new PooledDruidState(this);
    private final DruidDataSource dataSource;
    protected int poolMaximumActiveConnections = 10;
    protected int poolMaximumIdleConnections = 5;
    protected int poolMaximumCheckoutTime = 20000;
    protected int poolTimeToWait = 20000;
    protected String poolPingQuery = "NO PING QUERY SET";
    protected boolean poolPingEnabled = false;
    protected int poolPingConnectionsNotUsedFor = 0;
    private int expectedConnectionTypeCode;

    public PooledDruidDataSource(String driver, String url, String username, String password) {
        this.dataSource = this.createDatasource(driver, url, username, password);
        this.expectedConnectionTypeCode = this.assembleConnectionTypeCode(this.dataSource.getUrl(), this.dataSource.getUsername(), this.dataSource.getPassword());
    }
    
    public DruidDataSource createDatasource(String driver, String url, String username, String password){
    	DruidDataSource ds = new DruidDataSource();
    	ds.setDriverClassName(driver);
    	ds.setUsername(username); 
    	ds.setPassword(password);
    	ds.setUrl(url); 
    	ds.setInitialSize(5); 
    	ds.setMinIdle(1); 
    	ds.setMaxActive(10);
    	return ds;
    }

    public Connection getConnection() throws SQLException {
        return this.getConnection(this.dataSource.getUsername(), this.dataSource.getPassword());
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return this.popConnection(username, password).getProxyConnection();
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

    public void setDefaultAutoCommit(boolean defaultAutoCommit) {
        this.dataSource.setDefaultAutoCommit(Boolean.valueOf(defaultAutoCommit));
        this.forceCloseAll();
    }

    public void setPoolMaximumActiveConnections(int poolMaximumActiveConnections) {
        this.poolMaximumActiveConnections = poolMaximumActiveConnections;
        this.dataSource.setMaxActive(poolMaximumActiveConnections);
        this.forceCloseAll();
    }

    @SuppressWarnings("deprecation")
	public void setPoolMaximumIdleConnections(int poolMaximumIdleConnections) {
        this.poolMaximumIdleConnections = poolMaximumIdleConnections;
        this.dataSource.setMaxIdle(poolMaximumIdleConnections);
        this.forceCloseAll();
    }

    public void setPoolMaximumCheckoutTime(int poolMaximumCheckoutTime) {
        this.poolMaximumCheckoutTime = poolMaximumCheckoutTime;
        this.forceCloseAll();
    }

    public void setPoolTimeToWait(int poolTimeToWait) {
        this.poolTimeToWait = poolTimeToWait;
        this.dataSource.setMaxWait(poolTimeToWait);
        this.forceCloseAll();
    }

    public void setPoolPingQuery(String poolPingQuery) {
        this.poolPingQuery = poolPingQuery;
        this.dataSource.setValidationQuery(poolPingQuery);
        this.forceCloseAll();
    }

    public void setPoolPingEnabled(boolean poolPingEnabled) {
        this.poolPingEnabled = poolPingEnabled;
        this.dataSource.setTestWhileIdle(poolPingEnabled);
        this.forceCloseAll();
    }

    public void setPoolPingConnectionsNotUsedFor(int milliseconds) {
        this.poolPingConnectionsNotUsedFor = milliseconds;
        this.forceCloseAll();
    }

    public String getDriver() {
        return this.dataSource.getDriverClassName();
    }

    public String getUrl() {
        return this.dataSource.getUrl();
    }

    public String getUsername() {
        return this.dataSource.getUsername();
    }

    public String getPassword() {
        return this.dataSource.getPassword();
    }

    public int getPoolMaximumActiveConnections() {
        return this.poolMaximumActiveConnections;
    }

    public int getPoolMaximumIdleConnections() {
        return this.poolMaximumIdleConnections;
    }

    public int getPoolMaximumCheckoutTime() {
        return this.poolMaximumCheckoutTime;
    }

    public int getPoolTimeToWait() {
        return this.poolTimeToWait;
    }

    public String getPoolPingQuery() {
        return this.poolPingQuery;
    }

    public boolean isPoolPingEnabled() {
        return this.poolPingEnabled;
    }

    public int getPoolPingConnectionsNotUsedFor() {
        return this.poolPingConnectionsNotUsedFor;
    }

    public void forceCloseAll() {
    	PooledDruidState var1 = this.state;
        synchronized(this.state) {
            if(this.state.activeConnections.size() > 0 || this.state.idleConnections.size() > 0) {
                log.debug("连接池正在强制移除并关闭所有数据库连接……");

                int i;
                PooledDruidConnection e;
                Connection realConn;
                for(i = this.state.activeConnections.size(); i > 0; --i) {
                    try {
                        e = (PooledDruidConnection)this.state.activeConnections.remove(i - 1);
                        e.invalidate();
                        realConn = e.getRealConnection();
                        if(!realConn.getAutoCommit()) {
                            realConn.rollback();
                        }

                        realConn.close();
                    } catch (Exception var6) {
                        log.warn("关闭活动连接{}出错：{}", Integer.valueOf(i), var6.getMessage());
                    }
                }

                for(i = this.state.idleConnections.size(); i > 0; --i) {
                    try {
                        e = (PooledDruidConnection)this.state.idleConnections.remove(i - 1);
                        e.invalidate();
                        realConn = e.getRealConnection();
                        if(!realConn.getAutoCommit()) {
                            realConn.rollback();
                        }

                        realConn.close();
                    } catch (Exception var5) {
                        log.warn("关闭空闲连接{}出错：{}", Integer.valueOf(i), var5.getMessage());
                    }
                }

                log.debug("连接池已强制移除并关闭所有数据库连接。");
            }
        }
    }

    public PooledDruidState getPoolState() {
        return this.state;
    }

    private int assembleConnectionTypeCode(String url, String username, String password) {
        return ((url == null?"":url) + (username == null?"":username) + (password == null?"":password)).hashCode();
    }

    protected void pushConnection(PooledDruidConnection conn) throws SQLException {
    	PooledDruidState var2 = this.state;
        synchronized(this.state) {
            this.state.activeConnections.remove(conn);
            if(conn.isValid()) {
                Connection realConn;
                if(this.state.idleConnections.size() < this.poolMaximumIdleConnections && conn.getConnectionTypeCode() == this.expectedConnectionTypeCode) {
                    this.state.accumulatedCheckoutTime += conn.getCheckoutTime();
                    realConn = conn.getRealConnection();
                    if(!realConn.getAutoCommit()) {
                        realConn.rollback();
                    }

                    PooledDruidConnection newConn = new PooledDruidConnection(realConn, this);
                    this.state.idleConnections.add(newConn);
                    newConn.setCreatedTimestamp(conn.getCreatedTimestamp());
                    newConn.setLastUsedTimestamp(conn.getLastUsedTimestamp());
                    conn.invalidate();
                    log.debug("数据库连接[{}]已返回连接池中。", Integer.valueOf(newConn.getRealHashCode()));
                    this.state.notifyAll();
                } else {
                    this.state.accumulatedCheckoutTime += conn.getCheckoutTime();
                    realConn = conn.getRealConnection();
                    if(!realConn.getAutoCommit()) {
                        realConn.rollback();
                    }

                    realConn.close();
                    log.debug("数据库连接[{}]已被关闭。", Integer.valueOf(conn.getRealHashCode()));
                    conn.invalidate();
                }
            } else {
                log.debug("数据库连接[{}]不可用，已被丢弃。", Integer.valueOf(conn.getRealHashCode()));
                ++this.state.badConnectionCount;
            }

        }
    }

    private PooledDruidConnection popConnection(String username, String password) throws SQLException {
        boolean countedWait = false;
        PooledDruidConnection conn = null;
        long t = System.currentTimeMillis();
        int localBadConnectionCount = 0;

        while(conn == null) {
        	PooledDruidState var8 = this.state;
            synchronized(this.state) {
                if(!this.state.idleConnections.isEmpty()) {
                    conn = (PooledDruidConnection)this.state.idleConnections.remove(0);
                    log.debug("数据库连接[{}]已成功获取，因为连接池的空闲队列中还有可用的连接。", Integer.valueOf(conn.getRealHashCode()));
                } else if(this.state.activeConnections.size() < this.poolMaximumActiveConnections) {
                    conn = new PooledDruidConnection(this.dataSource.getConnection(), this);
                    log.debug("数据库连接[{}]已被新建，因为连接池的空闲队列已为空，并且活动队列未满。", Integer.valueOf(conn.getRealHashCode()));
                } else {
                    PooledDruidConnection oldestActiveConnection = (PooledDruidConnection)this.state.activeConnections.get(0);
                    long longestCheckoutTime = oldestActiveConnection.getCheckoutTime();
                    if(longestCheckoutTime > (long)this.poolMaximumCheckoutTime) {
                        ++this.state.claimedOverdueConnectionCount;
                        this.state.accumulatedCheckoutTimeOfOverdueConnections += longestCheckoutTime;
                        this.state.accumulatedCheckoutTime += longestCheckoutTime;
                        this.state.activeConnections.remove(oldestActiveConnection);
                        if(!oldestActiveConnection.getRealConnection().getAutoCommit()) {
                            oldestActiveConnection.getRealConnection().rollback();
                        }

                        conn = new PooledDruidConnection(oldestActiveConnection.getRealConnection(), this);
                        oldestActiveConnection.invalidate();
                        log.debug("数据库连接[{}]已被剥夺，因为连接池的空闲队列已为空，并且活动队列已满，并且最早的连接已经超时。", Integer.valueOf(conn.getRealHashCode()));
                    } else {
                        try {
                            if(!countedWait) {
                                ++this.state.hadToWaitCount;
                                countedWait = true;
                            }

                            log.debug("等待获取连接，因为连接池的空闲队列已为空，并且活动队列已满，并且最早的连接还未超时。");
                            long e = System.currentTimeMillis();
                            this.state.wait((long)this.poolTimeToWait);
                            this.state.accumulatedWaitTime += System.currentTimeMillis() - e;
                        } catch (InterruptedException var14) {
                            break;
                        }
                    }
                }

                if(conn != null) {
                    if(conn.isValid()) {
                        if(!conn.getRealConnection().getAutoCommit()) {
                            conn.getRealConnection().rollback();
                            conn.getRealConnection().setAutoCommit(true);
                        }

                        conn.setConnectionTypeCode(this.assembleConnectionTypeCode(this.dataSource.getUrl(), username, password));
                        conn.setCheckoutTimestamp(System.currentTimeMillis());
                        conn.setLastUsedTimestamp(System.currentTimeMillis());
                        this.state.activeConnections.add(conn);
                        ++this.state.requestCount;
                        this.state.accumulatedRequestTime += System.currentTimeMillis() - t;
                    } else {
                        log.debug("数据库连接[{}]已经失效，将尝试另外获取一个。", Integer.valueOf(conn.getRealHashCode()));
                        ++this.state.badConnectionCount;
                        ++localBadConnectionCount;
                        conn = null;
                        if(localBadConnectionCount > this.poolMaximumIdleConnections + 3) {
                            log.error("无法从连接池中获取到有效的数据库连接。");
                            throw new SQLException("无法从连接池中获取到有效的数据库连接。");
                        }
                    }
                }
            }
        }

        if(conn == null) {
            log.error("未知的严重错误：连接池返回的数据库连接为null");
            throw new SQLException("未知的严重错误：连接池返回的数据库连接为null");
        } else {
            return conn;
        }
    }

    protected boolean pingConnection(PooledDruidConnection conn) {
        boolean result = true;

        try {
            result = !conn.getRealConnection().isClosed();
        } catch (SQLException var12) {
            log.debug("数据库连接[{}]已经失效：{}", Integer.valueOf(conn.getRealHashCode()), var12.getMessage());
            result = false;
        }

        if(result && this.poolPingEnabled && this.poolPingConnectionsNotUsedFor >= 0 && conn.getTimeElapsedSinceLastUse() > (long)this.poolPingConnectionsNotUsedFor) {
            try {
                log.debug("数据库连接[{}]正在Ping测试中……", Integer.valueOf(conn.getRealHashCode()));
                Connection e = conn.getRealConnection();
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    stmt = e.createStatement();
                    rs = stmt.executeQuery(this.poolPingQuery);
                } finally {
                    if(rs != null) {
                        rs.close();
                        rs = null;
                    }

                    if(stmt != null) {
                        stmt.close();
                        stmt = null;
                    }

                }

                if(!e.getAutoCommit()) {
                    e.rollback();
                }

                result = true;
                log.debug("数据库连接[{}]Ping测试完毕，连接有效。", Integer.valueOf(conn.getRealHashCode()));
            } catch (Exception var14) {
                log.debug("数据库连接[{}]在执行Ping测试脚本时出现错误：{}", Integer.valueOf(conn.getRealHashCode()), var14.getMessage());

                try {
                    conn.getRealConnection().close();
                } catch (Exception var11) {
                    ;
                }

                result = false;
                log.debug("数据库连接[{}]已被关闭。", Integer.valueOf(conn.getRealHashCode()));
            }
        }

        return result;
    }

    public static Connection unwrapConnection(Connection conn) {
        if(Proxy.isProxyClass(conn.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(conn);
            if(handler instanceof PooledDruidConnection) {
                return ((PooledDruidConnection)handler).getRealConnection();
            }
        }

        return conn;
    }

    protected void finalize() throws Throwable {
        this.forceCloseAll();
        super.finalize();
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

    public String getType() {
        return this.dataSource.getDbType();
    }
}
