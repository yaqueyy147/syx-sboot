package com.syx.sboot.common.access;

import com.syx.sboot.common.datasource.DataSourceFactory;
import com.syx.sboot.common.exception.DbException;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.Map;


/**
 * 
 * @author younger
 * @date 2017年7月18日
 * @version V1.0
 */
public class TxSqlRunnerUtil {

	public static void execute(String datasourcecode, TxSqlRunnerListener mListener){
		TxSqlRunner runner = null;
		try {
			if(StringUtils.isEmpty(datasourcecode)){
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			}else{
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(datasourcecode));
			}
			if(mListener != null){
				mListener.onTxSqlRunnerReady(runner);
			}
			
			runner.commit();
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
	
	public static void execute(String datasourcecode, TxSqlRunnerParamListener txSqlRunnerParamListener, Map<String,Object> params ){
		TxSqlRunner runner = null;
		try {
			if(StringUtils.isEmpty(datasourcecode)){
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getSystemDataSource());
			}else{
				runner = new TxSqlRunner(DataSourceFactory.getInstance().getDataSourceByCode(datasourcecode));
			}
			if(txSqlRunnerParamListener != null){
				txSqlRunnerParamListener.onTxSqlRunnerReady(runner, params);
			}
			
			runner.commit();
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
	
	
	/*
	 * 不处理异常信息
	 */
	public static void executeNoHandleExp(String datasourcecode, TxSqlRunnerListener mListener) throws Exception{
		TxSqlRunner runner = null;
		try {
			runner = new TxSqlRunner(DataSourceFactory.getInstance()
					.getDataSourceByCode(datasourcecode));
			
			if(mListener != null){
				mListener.onTxSqlRunnerReady(runner);
			}
			
			runner.commit();
		} finally{
			if(runner != null){
				try {
					runner.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


