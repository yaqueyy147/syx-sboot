//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.datasource.unpooled;

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
public class UnpooledDataSourceFactory extends DataSourceFactory {

    private static final Logger log = LoggerFactory.getLogger(UnpooledDataSourceFactory.class);
    private static final Map<String, String> codeMap = new ConcurrentHashMap();
    private static final Map<String, UnpooledDataSource> dsMap = new ConcurrentHashMap();

    public UnpooledDataSourceFactory() {
    }

    public TypedDataSource getDataSourceById(String dataSourceId) {
        if(StringUtils.isBlank(dataSourceId)) {
            log.error("获取数据源出错：参数[dataSourceId]不能为空！");
            throw new IllegalArgumentException("获取数据源出错：参数[dataSourceId]不能为空！");
        } else {
            String dsId = dataSourceId.trim().toLowerCase();
            return (TypedDataSource)(dsId.equalsIgnoreCase("system")?this.doGetSysDataSource():this.doGetAppDataSourceById(dsId));
        }
    }

    public TypedDataSource getDataSourceByCode(String dataSourceCode) {
        if(StringUtils.isBlank(dataSourceCode)) {
            log.error("获取数据源出错：参数[dataSourceCode]不能为空！");
            throw new IllegalArgumentException("获取数据源出错：参数[dataSourceCode]不能为空！");
        } else {
            String dsCode = dataSourceCode.trim().toLowerCase();
            return (TypedDataSource)(dsCode.equalsIgnoreCase("system")?this.doGetSysDataSource():this.doGetAppDataSourceByCode(dsCode));
        }
    }

    private TypedDataSource doGetSysDataSource() {
        UnpooledDataSource ds = (UnpooledDataSource)dsMap.get("system");
        if(ds != null) {
            return ds;
        } else {
            synchronized(this) {
                ds = (UnpooledDataSource)dsMap.get("system");
                if(ds != null) {
                    return ds;
                } else {
                    log.debug("正在初始化数据源[{}]", "system");
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
                                    ds = new UnpooledDataSource(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
                                    ds.setDefaultAutoCommit(Boolean.valueOf(true));
                                    codeMap.put("system", "system");
                                    dsMap.put("system", ds);
                                    log.debug("成功初始化数据源[{}]", "system");
                                    return ds;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private UnpooledDataSource doGetAppDataSourceById(String dsId) {
        UnpooledDataSource ds = (UnpooledDataSource)dsMap.get(dsId);
        if(ds != null) {
            return ds;
        } else {
            synchronized(this) {
                ds = (UnpooledDataSource)dsMap.get(dsId);
                if(ds != null) {
                    return ds;
                } else {
                    log.debug("正在初始化数据源[{}]", dsId);
                    SysDataSource entDataSource = null;

                    try {
                        SqlRunner e = new SqlRunner(this.getSystemDataSource());
                        entDataSource = (SysDataSource)e.query("select * from sys_datasource where (dsId=?) and (dataValid=1)", new BeanHandler(SysDataSource.class), new Object[]{dsId});
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

    private UnpooledDataSource doGetAppDataSourceByCode(String dsCode) {
        String dsId = (String)codeMap.get(dsCode);
        if(dsId != null) {
            UnpooledDataSource ds = (UnpooledDataSource)dsMap.get(dsId);
            if(ds != null) {
                return ds;
            }
        }

        synchronized(this) {
            dsId = (String)codeMap.get(dsCode);
            UnpooledDataSource entDataSource;
            if(dsId != null) {
                entDataSource = (UnpooledDataSource)dsMap.get(dsId);
                if(entDataSource != null) {
                    return entDataSource;
                }
            }

            log.debug("正在初始化数据源[{}]", dsCode);
            entDataSource = null;

            SysDataSource entDataSource1;
            try {
                SqlRunner ds1 = new SqlRunner(this.getSystemDataSource());
                entDataSource1 = (SysDataSource)ds1.query("select * from sys_datasource where (dsCode=?) and (dataValid=1)", new BeanHandler(SysDataSource.class), new Object[]{dsCode});
                if(entDataSource1 == null) {
                    throw new Exception("数据源配置信息不存在！");
                }
            } catch (Exception var6) {
                log.error("初始化数据源[{}]失败：{}", dsCode, var6.getMessage());
                throw new DataSourceException("初始化数据源[" + dsCode + "]失败：" + var6.getMessage());
            }

            UnpooledDataSource ds2 = this.createDataSource(entDataSource1);
            codeMap.put(entDataSource1.getDscode(), entDataSource1.getId());
            dsMap.put(entDataSource1.getId(), ds2);
            log.debug("成功初始化数据源[{}]", dsId);
            return ds2;
        }
    }

    private UnpooledDataSource createDataSource(SysDataSource entDataSource) {
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
                        UnpooledDataSource ds = new UnpooledDataSource(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
                        ds.setDefaultAutoCommit(Boolean.valueOf(true));
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
                        dsMap.remove(dsId);
                        log.debug("数据源[{}]已被移除", dsId);
                    }
                }
            }

        }
    }

    public void resetDataSourceByCode(String dataSourceCode) {
        if(!StringUtils.isBlank(dataSourceCode)) {
            String dsCode = dataSourceCode.trim().toLowerCase();
            if(codeMap.containsKey(dsCode)) {
                synchronized(this) {
                    if(codeMap.containsKey(dsCode)) {
                        String dsId = (String)codeMap.get(dsCode);
                        if(dsId != null && dsMap.containsKey(dsId)) {
                            dsMap.remove(dsId);
                            log.debug("数据源[{}]已被移除", dsId);
                        }
                    }
                }
            }

        }
    }
}
