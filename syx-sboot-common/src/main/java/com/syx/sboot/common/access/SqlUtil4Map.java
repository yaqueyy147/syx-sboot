package com.syx.sboot.common.access;

import com.syx.sboot.common.access.sql.SqlQuery;
import com.syx.sboot.common.datasource.DataSourceFactory;
import com.syx.sboot.common.datasource.TypedDataSource;
import com.syx.sboot.common.entity.Page4Map;
import com.syx.sboot.common.exception.DbException;
import com.syx.sboot.common.service.SqlUtils;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/7/31 0031.
 */
public class SqlUtil4Map extends SqlUtil {

    public static List<Map<String,Object>> getMapByOriginSQL(Runner runner, SqlQuery query) throws Exception {
        List<Map<String,Object>> entitys = runner.query(query.getSql(),
                new MapListHandler(), query.getParams());
        return entitys;
    }

    public static List<Map<String,Object>> getMapByOriginSQL(String dscode, SqlQuery query) {

        TxSqlRunner runner = null;
        try {
            runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
            return SqlUtil4Map.getMapByOriginSQL(runner, query);
        } catch (Exception e) {
            if(runner != null){
                try {
                    runner.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }finally{
            if(runner != null){
                try {
                    runner.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static Map<String,Object> getMapBySQL(String dscode, SqlQuery query) {

        TxSqlRunner runner = null;
        Map<String,Object> map = new HashMap<>();
        try {
            runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(dscode));
            map = runner.query(query.getSql(),new MapHandler(), query.getParams());
            return map;
        } catch (Exception e) {
            if(runner != null){
                try {
                    runner.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new DbException(e.getMessage());
        }finally{
            if(runner != null){
                try {
                    runner.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Page4Map getPageDataList(String dscode, SqlQuery query, Page4Map page) {

        try {
            TypedDataSource dataSource = DataSourceFactory.getInstance().getDataSourceByCode(dscode);
            TxSqlRunner runner = new TxSqlRunner(dataSource);
            // 分页
            if (page != null && page.getPageSize() > 0) {

                try {
                    int totalRows = 0;
                    if(page.getCount() <= 0){
                        @SuppressWarnings("unchecked")
                        Object objTotalRows = runner.query(SqlUtils.getPageCountSql(query.getSql()), new ScalarHandler());
                        if (objTotalRows != null) {
                            totalRows = Integer.parseInt(objTotalRows.toString());
                        }
                    }else{
                        totalRows = (int) page.getCount();
                    }

                    if (totalRows > 0) {
                        page.setCount(totalRows);

                        @SuppressWarnings({ "rawtypes", "unchecked" })
                        List orderList = (List) runner.query(SqlUtils.getPageQuerySql(query.getSql(),page),
                                new MapListHandler());

                        if ((orderList != null) && (orderList.size() > 0)) {
                            page.setList(orderList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runner.rollback();
                } finally {
                    if (runner != null)
                        runner.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

}
