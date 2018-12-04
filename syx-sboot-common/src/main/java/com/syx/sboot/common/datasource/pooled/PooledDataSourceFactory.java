//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.datasource.pooled;

import com.syx.sboot.common.access.SqlRunner;
import com.syx.sboot.common.datasource.DataSourceException;
import com.syx.sboot.common.datasource.DataSourceFactory;
import com.syx.sboot.common.datasource.SqlUtilDbConfigStatic;
import com.syx.sboot.common.datasource.TypedDataSource;
import com.syx.sboot.common.entity.SysDataSource;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PooledDataSourceFactory extends DataSourceFactory {

    private static final Logger log = LoggerFactory.getLogger(PooledDataSourceFactory.class);
    private static final Map<String, String> codeMap = new ConcurrentHashMap();
    private static final Map<String, PooledDataSource> dsMap = new ConcurrentHashMap();

    public PooledDataSourceFactory() {
    }

    public TypedDataSource getDataSourceById(String dataSourceId) {
        if(StringUtils.isBlank(dataSourceId)) {
            log.error("获取数据源出错：参数[dataSourceId]不能为空！");
            throw new IllegalArgumentException("获取数据源出错：参数[dataSourceId]不能为空！");
        } else {
            String dsId = dataSourceId.trim().toLowerCase();
            return dsId.equalsIgnoreCase("system")?this.doGetSysDataSource():this.doGetAppDataSourceById(dsId);
        }
    }

    public TypedDataSource getDataSourceByCode(String dataSourceCode) {
        if(StringUtils.isBlank(dataSourceCode)) {
        	return this.doGetSysDataSource();
        } else {
            String Dscode = dataSourceCode.trim().toLowerCase();
            return Dscode.equalsIgnoreCase("system")?this.doGetSysDataSource():this.doGetAppDataSourceByCode(Dscode);
        }
    }

    private TypedDataSource doGetSysDataSource() {
        PooledDataSource ds = (PooledDataSource)dsMap.get("system");
        if(ds != null) {
            return ds;
        } else {
            synchronized(this) {
                ds = (PooledDataSource)dsMap.get("system");
                if(ds != null) {
                    return ds;
                } else {
                    log.debug("开始初始化数据源[{}]", "system");
//                    String jdbcDriver = AppConfiguration.getInstance().getString("jdbc.driver");
                    String jdbcDriver = SqlUtilDbConfigStatic.driver;
                    if(StringUtils.isBlank(jdbcDriver)) {
                        log.error("初始化数据源[{}]失败：没有配置驱动类[jdbc.driver]", "system");
                        throw new DataSourceException("初始化数据源[system]失败：没有配置驱动类[jdbc.driver]");
                    } else {
//                        String jdbcUrl = AppConfiguration.getInstance().getString("jdbc.url");
                        String jdbcUrl = SqlUtilDbConfigStatic.url;
                        if(StringUtils.isBlank(jdbcUrl)) {
                            log.error("初始化数据源[{}]失败：没有配置连接地址[jdbc.url]", "system");
                            throw new DataSourceException("初始化数据源[system]失败：没有配置连接地址[jdbc.url]");
                        } else {
//                            String jdbcUsername = AppConfiguration.getInstance().getString("jdbc.username");
                            String jdbcUsername = SqlUtilDbConfigStatic.username;
                            if(StringUtils.isBlank(jdbcUsername)) {
                                log.error("初始化数据源[{}]失败：没有配置用户名[jdbc.username]", "system");
                                throw new DataSourceException("初始化数据源[system]失败：没有配置用户名[jdbc.username]");
                            } else {
//                                String jdbcPassword = AppConfiguration.getInstance().getString("jdbc.password");
                                String jdbcPassword = SqlUtilDbConfigStatic.password;
                                if(StringUtils.isBlank(jdbcPassword)) {
                                    log.error("初始化数据源[{}]失败：没有配置密码[jdbc.password]", "system");
                                    throw new DataSourceException("初始化数据源[system]失败：没有配置密码[jdbc.password]");
                                } else {
                                    ds = new PooledDataSource(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
                                    ds.setDefaultAutoCommit(true);
//                                    int poolMaxActive = AppConfiguration.getInstance().getInt("pool.maxActive", 10);
                                    int poolMaxActive = SqlUtilDbConfigStatic.poolMaxActive;
                                    ds.setPoolMaximumActiveConnections(poolMaxActive);
//                                    int poolMaxIdle = AppConfiguration.getInstance().getInt("pool.maxIdle", 5);
                                    int poolMaxIdle = SqlUtilDbConfigStatic.poolMaxIdle;
                                    ds.setPoolMaximumIdleConnections(poolMaxIdle);
//                                    int poolCheckoutTime = AppConfiguration.getInstance().getInt("pool.checkoutTime", 20000);
                                    int poolCheckoutTime = SqlUtilDbConfigStatic.poolCheckoutTime;
                                    ds.setPoolMaximumCheckoutTime(poolCheckoutTime);
//                                    int poolWaitTime = AppConfiguration.getInstance().getInt("pool.waitTime", 20000);
                                    int poolWaitTime = SqlUtilDbConfigStatic.poolWaitTime;
                                    ds.setPoolTimeToWait(poolWaitTime);
//                                    boolean poolPingEnabled = AppConfiguration.getInstance().getBoolean("pool.pingEnabled", false);
                                    boolean poolPingEnabled = SqlUtilDbConfigStatic.poolPingEnabled;
                                    ds.setPoolPingEnabled(poolPingEnabled);
//                                    String poolPingQuery = AppConfiguration.getInstance().getString("pool.pingQuery", "NO PING QUERY SET");
                                    String poolPingQuery = SqlUtilDbConfigStatic.poolPingQuery;
                                    ds.setPoolPingQuery(poolPingQuery);
//                                    int poolPingTime = AppConfiguration.getInstance().getInt("pool.pingTime", 0);
                                    int poolPingTime = SqlUtilDbConfigStatic.poolPingTime;
                                    ds.setPoolPingConnectionsNotUsedFor(poolPingTime);
                                    codeMap.put("system", "system");
                                    dsMap.put("system", ds);
                                    log.debug("已成功初始化数据源[{}]", "system");
                                    return ds;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private TypedDataSource doGetAppDataSourceById(String dsId) {
        PooledDataSource ds = (PooledDataSource)dsMap.get(dsId);
        if(ds != null) {
            return ds;
        } else {
            synchronized(this) {
                ds = (PooledDataSource)dsMap.get(dsId);
                if(ds != null) {
                    return ds;
                } else {
                    log.debug("正在初始化数据源[{}]", dsId);
                    SysDataSource entDataSource = null;

                    try {
                        SqlRunner e = new SqlRunner(this.getSystemDataSource());
                        entDataSource = (SysDataSource)e.query("select * from sysdatasource where (id=?) and (datavalid=1)", new BeanHandler(SysDataSource.class), new Object[]{dsId});
                        if(entDataSource == null) {
                            throw new Exception("数据源配置信息不存在！");
                        }
                    } catch (Exception var6) {
                        log.error("初始化数据源[{}]失败：{}", dsId, var6.getMessage());
                        throw new DataSourceException("初始化数据源[" + dsId + "]失败：" + var6.getMessage());
                    }

                    ds = this.createDataSource(entDataSource);
                    codeMap.put(entDataSource.getDscode(), entDataSource.getId());
                    dsMap.put(entDataSource.getId(), ds);
                    log.debug("成功初始化数据源[{}]", dsId);
                    return ds;
                }
            }
        }
    }

    private TypedDataSource doGetAppDataSourceByCode(String Dscode) {
        String dsId = (String)codeMap.get(Dscode);
        if(dsId != null) {
            PooledDataSource ds = (PooledDataSource)dsMap.get(dsId);
            if(ds != null) {
                return ds;
            }
        }

        synchronized(this) {
            dsId = (String)codeMap.get(Dscode);
            PooledDataSource entDataSource;
            if(dsId != null) {
                entDataSource = (PooledDataSource)dsMap.get(dsId);
                if(entDataSource != null) {
                    return entDataSource;
                }
            }

            log.debug("正在初始化数据源[{}]", Dscode);
            entDataSource = null;

            SysDataSource entDataSource1;
            try {
                SqlRunner ds1 = new SqlRunner(this.getSystemDataSource());
                entDataSource1 = (SysDataSource)ds1.query("select * from sysdatasource where (datavalid=1) and (dscode=?)", new BeanHandler(SysDataSource.class), new Object[]{Dscode});
                if(entDataSource1 == null) {
                    throw new Exception("数据源配置信息不存在！");
                }
            } catch (Exception var6) {
                log.error("初始化数据源[{}]失败：{}", Dscode, var6.getMessage());
                throw new DataSourceException("初始化数据源[" + Dscode + "]失败：" + var6.getMessage());
            }

            PooledDataSource ds2 = this.createDataSource(entDataSource1);
            codeMap.put(entDataSource1.getDscode(), entDataSource1.getId());
            dsMap.put(entDataSource1.getId(), ds2);
            log.debug("成功初始化数据源[{}]", Dscode);
            return ds2;
        }
    }

    private PooledDataSource createDataSource(SysDataSource entDataSource) {
        String jdbcDriver = entDataSource.getDsdriver();
        if(StringUtils.isBlank(jdbcDriver)) {
            log.error("初始化数据源[{}]失败：没有配置驱动类", entDataSource.getDscode());
            throw new DataSourceException("初始化数据源[" + entDataSource.getDscode() + "]失败：没有配置驱动类");
        } else {
            String jdbcUrl = entDataSource.getDsurl();
            if(StringUtils.isBlank(jdbcUrl)) {
                log.error("初始化数据源[{}]失败：没有配置连接地址", entDataSource.getDscode());
                throw new DataSourceException("初始化数据源[" + entDataSource.getDscode() + "]失败：没有配置连接地址");
            } else {
                String jdbcUsername = entDataSource.getDsusername();
                if(StringUtils.isBlank(jdbcUsername)) {
                    log.error("初始化数据源[{}]失败：没有配置用户名", entDataSource.getDscode());
                    throw new DataSourceException("初始化数据源[" + entDataSource.getDscode() + "]失败：没有配置用户名");
                } else {
                    String jdbcPassword = entDataSource.getDspassword();
                    if(StringUtils.isBlank(jdbcPassword)) {
                        log.error("初始化数据源[{}]失败：没有配置密码", entDataSource.getDscode());
                        throw new DataSourceException("初始化数据源[" + entDataSource.getDscode() + "]失败：没有配置密码");
                    } else {
                        PooledDataSource ds = new PooledDataSource(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
                        ds.setDefaultAutoCommit(true);
                        Integer poolMaxActive = entDataSource.getDsmaxactive();
                        if(poolMaxActive == null || poolMaxActive.intValue() <= 0) {
                            log.warn("数据源[{}]最大活动连接数无效，将使用默认值：10", entDataSource.getDscode());
                            poolMaxActive = Integer.valueOf(10);
                        }

                        ds.setPoolMaximumActiveConnections(poolMaxActive.intValue());
                        Integer poolMaxIdle = entDataSource.getDsmaxidle();
                        if(poolMaxIdle == null || poolMaxIdle.intValue() <= 0) {
                            log.warn("数据源[{}]最大空闲连接数无效，将使用默认值：5", entDataSource.getDscode());
                            poolMaxIdle = Integer.valueOf(5);
                        }

                        ds.setPoolMaximumIdleConnections(poolMaxIdle.intValue());
                        Integer poolCheckoutTime = entDataSource.getDscheckouttime();
                        if(poolCheckoutTime == null || poolCheckoutTime.intValue() < 0) {
                            log.warn("数据源[{}]配置的CheckoutTime超时时间无效，将使用默认值：20000", entDataSource.getDscode());
                            poolCheckoutTime = Integer.valueOf(20000);
                        }

                        ds.setPoolMaximumCheckoutTime(poolCheckoutTime.intValue());
                        Integer poolWaitTime = entDataSource.getDswaittime();
                        if(poolWaitTime == null || poolWaitTime.intValue() < 0) {
                            log.warn("数据源[{}]配置的WaitTime超时时间无效，将使用默认值：20000", entDataSource.getDscode());
                            poolWaitTime = Integer.valueOf(20000);
                        }

                        ds.setPoolTimeToWait(poolWaitTime.intValue());
                        Integer poolPingEnabled = entDataSource.getDspingenabled();
                        if(poolPingEnabled == null || poolPingEnabled.intValue() > 1 || poolPingEnabled.intValue() < 0) {
                            log.warn("数据源[{}]配置的PingEnabled属性无效，将使用默认值：0", entDataSource.getDscode());
                            poolPingEnabled = Integer.valueOf(0);
                        }

                        ds.setPoolPingEnabled(poolPingEnabled.intValue() == 1);
                        String poolPingQuery = entDataSource.getDspingquery();
                        if(StringUtils.isBlank(poolPingQuery)) {
                            log.warn("数据源[{}]配置的PingQuery属性无效，将使用默认值：NO PING QUERY SET", entDataSource.getDscode());
                            poolPingQuery = "NO PING QUERY SET";
                        }

                        ds.setPoolPingQuery(poolPingQuery.trim());
                        Integer poolPingTime = entDataSource.getDspingtime();
                        if(poolPingTime == null || poolPingTime.intValue() < 0) {
                            log.warn("数据源[{}]配置的PingTime属性无效，将使用默认值：0", entDataSource.getDscode());
                            poolPingTime = Integer.valueOf(0);
                        }

                        ds.setPoolPingConnectionsNotUsedFor(poolPingTime.intValue());
                        return ds;
                    }
                }
            }
        }
    }

    public void resetDataSourceById(String dataSourceId) {
        if(!StringUtils.isBlank(dataSourceId)) {
            String dsId = dataSourceId.trim().toLowerCase();
            if(dsMap.containsKey(dsId)) {
                synchronized(this) {
                    if(dsMap.containsKey(dsId)) {
                        PooledDataSource dataSource = (PooledDataSource)dsMap.remove(dsId);
                        dataSource.forceCloseAll();
                        log.debug("数据源[{}]已被移除", dsId);
                    }
                }
            }

        }
    }

    public void resetDataSourceByCode(String dataSourceCode) {
        if(!StringUtils.isBlank(dataSourceCode)) {
            String Dscode = dataSourceCode.trim().toLowerCase();
            if(codeMap.containsKey(Dscode)) {
                synchronized(this) {
                    if(codeMap.containsKey(Dscode)) {
                        String dsId = (String)codeMap.remove(Dscode);
                        if(dsId != null && dsMap.containsKey(dsId)) {
                            PooledDataSource dataSource = (PooledDataSource)dsMap.remove(dsId);
                            dataSource.forceCloseAll();
                            log.debug("数据源[{}]已被移除", Dscode);
                        }
                    }
                }
            }

        }
    }
}
