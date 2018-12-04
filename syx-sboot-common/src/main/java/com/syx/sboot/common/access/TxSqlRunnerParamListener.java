package com.syx.sboot.common.access;

import java.util.Map;

public interface TxSqlRunnerParamListener {
	public void onTxSqlRunnerReady(TxSqlRunner runner, Map<String, Object> params) throws Exception;
}
