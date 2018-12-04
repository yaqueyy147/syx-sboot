package com.syx.sboot.common.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.DefaultConfigurationBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class AppConfiguration {

	private static AppConfiguration appConf = null;
	private Configuration conf = null;

	private AppConfiguration() {
	}

	public static AppConfiguration getInstance() {
		if (appConf == null) {
			AppConfiguration.init("config/app-config.xml");
			if (appConf == null) {
				throw new NullPointerException(
						"AppConfiguration has not been inited.");
			}
		}

		return appConf;
	}

	public static synchronized void init(String configFile){
		try{
			if (appConf == null) {
				appConf = new AppConfiguration();
				DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder(
						configFile);
				appConf.conf = builder.getConfiguration();
	
				int pIndex = configFile.lastIndexOf("/");
				if (pIndex == -1)
					pIndex = configFile.lastIndexOf("\\");
			}
		}catch(Exception e){
			appConf = null;
		}
	}

	public void clear() {
		if (this.conf != null) {
			this.conf.clear();
			this.conf = null;
		}
	}

	public String getFileTempPath() {
		return this.conf.getString("app.server.filetemppath");
	}

	public String getApplicationCode() {
		return this.conf.getString("app.application.code");
	}
	
	public String getApplicationCode(String defaultvalue) {
		return this.conf.getString("app.application.code", defaultvalue);
	}


	public boolean containsKey(String arg0) {
		return this.conf.containsKey(arg0);
	}

	public BigDecimal getBigDecimal(String arg0) {
		return this.conf.getBigDecimal(arg0);
	}

	public BigDecimal getBigDecimal(String arg0, BigDecimal arg1) {
		return this.conf.getBigDecimal(arg0, arg1);
	}

	public BigInteger getBigInteger(String arg0) {
		return this.conf.getBigInteger(arg0);
	}

	public BigInteger getBigInteger(String arg0, BigInteger arg1) {
		return this.conf.getBigInteger(arg0, arg1);
	}

	public boolean getBoolean(String arg0) {
		try {
			return this.conf.getBoolean(arg0);
		} catch (Exception e) {
			;
		}
		return false;
	}

	public boolean getBoolean(String arg0, boolean arg1) {
		return this.conf.getBoolean(arg0, arg1);
	}

	public Boolean getBoolean(String arg0, Boolean arg1) {
		return this.conf.getBoolean(arg0, arg1);
	}

	public byte getByte(String arg0) {
		return this.conf.getByte(arg0);
	}

	public byte getByte(String arg0, byte arg1) {
		return this.conf.getByte(arg0, arg1);
	}

	public Byte getByte(String arg0, Byte arg1) {
		return this.conf.getByte(arg0, arg1);
	}

	public double getDouble(String arg0) {
		return this.conf.getDouble(arg0);
	}

	public double getDouble(String arg0, double arg1) {
		return this.conf.getDouble(arg0, arg1);
	}

	public Double getDouble(String arg0, Double arg1) {
		return this.conf.getDouble(arg0, arg1);
	}

	public float getFloat(String arg0) {
		return this.conf.getFloat(arg0);
	}

	public float getFloat(String arg0, float arg1) {
		return this.conf.getFloat(arg0, arg1);
	}

	public Float getFloat(String arg0, Float arg1) {
		return this.conf.getFloat(arg0, arg1);
	}

	public int getInt(String arg0) {
		return this.conf.getInt(arg0);
	}

	public int getInt(String arg0, int arg1) {
		return this.conf.getInt(arg0, arg1);
	}

	public Integer getInteger(String arg0, Integer arg1) {
		return this.conf.getInteger(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public Iterator getKeys() {
		return this.conf.getKeys();
	}

	@SuppressWarnings("rawtypes")
	public Iterator getKeys(String arg0) {
		return this.conf.getKeys(arg0);
	}

	@SuppressWarnings("rawtypes")
	public List getList(String arg0) {
		return this.conf.getList(arg0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getList(String arg0, List arg1) {
		return this.conf.getList(arg0, arg1);
	}

	public long getLong(String arg0) {
		return this.conf.getLong(arg0);
	}

	public long getLong(String arg0, long arg1) {
		return this.conf.getLong(arg0, arg1);
	}

	public Long getLong(String arg0, Long arg1) {
		return this.conf.getLong(arg0, arg1);
	}

	public Properties getProperties(String arg0) {
		return this.conf.getProperties(arg0);
	}

	public Object getProperty(String arg0) {
		return this.conf.getProperty(arg0);
	}

	public short getShort(String arg0) {
		return this.conf.getShort(arg0);
	}

	public short getShort(String arg0, short arg1) {
		return this.conf.getShort(arg0, arg1);
	}

	public Short getShort(String arg0, Short arg1) {
		return this.conf.getShort(arg0, arg1);
	}

	public String getString(String arg0) {
		return this.conf.getString(arg0);
	}

	public String getString(String arg0, String arg1) {
		return this.conf.getString(arg0, arg1);
	}

	public String[] getStringArray(String arg0) {
		return this.conf.getStringArray(arg0);
	}

	public boolean isEmpty() {
		return this.conf.isEmpty();
	}

	public Configuration subset(String arg0) {
		return this.conf.subset(arg0);
	}
}
