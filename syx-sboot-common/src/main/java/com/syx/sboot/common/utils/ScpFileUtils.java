package com.syx.sboot.common.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.syx.sboot.common.entity.CommonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class ScpFileUtils {
	private static String  DEFAULTCHART="UTF-8";  
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static ScpFileUtils instance = null;
	
	private String ipaddress = BaseConfigData.ADPACT_STORE_FTP_IP;
	private String ipport = BaseConfigData.ADPACT_STORE_FTP_PORT;
	private String username = BaseConfigData.ADPACT_STORE_FTP_USER;
	private String userpwd =  BaseConfigData.ADPACT_STORE_FTP_PWD;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getIpport() {
		return ipport;
	}

	public void setIpport(String ipport) {
		this.ipport = ipport;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	private ScpFileUtils() {
	}
	
	public ScpFileUtils(String ipaddress, String ipport, String username, String userpwd) {
		this.ipaddress = ipaddress;
		this.ipport = ipport;
		this.username = username;
		this.userpwd = userpwd;
	}

	public static ScpFileUtils getInstance() {
		if (instance == null) {
			ScpFileUtils.init();
		}
		return instance;
	}

	public static synchronized void init() {
		if (instance == null) {
			instance = new ScpFileUtils();
		}
	}
	
	public static void destory(){
		instance = null;
    }
	
	/**
	 * 将本地某文件复制到指定远程Linux服务器的指定目录
	 * 
	 * @author 
	 * 
	 * @since 2017/6/28
	 * 
	 * @param localfile
	 * 				本地文件(例：/test/test.xml)
	 * 				远程目录(例：/test)
	 * @throws Exception 
	 * 				IO异常或认证异常
	 */
	public boolean sendFile(String localfile, String targetdir){
		Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				//从本地复制文件到远程目录   
				scpClient.put(localfile, targetdir, "0755"); //常规权限  0755 0644
				return true;
			} else {
				logger.error("login exception,user or password error!");
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}
	
	public boolean sendFile(String localfile, String targetdir, boolean mkdir){
		Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				
				if(!StringUtils.isEmpty(targetdir) && mkdir==true){
					mkdir(conn, targetdir);
				}
				//从本地复制文件到远程目录   
				scpClient.put(localfile, targetdir, "0755"); //常规权限  0755 0644
				return true;
			} else {
				logger.error("login exception,user or password error!");
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}
	
	public boolean sendFile(String localfile, String targetdir, String mode){
		Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				//从本地复制文件到远程目录   
				scpClient.put(localfile, targetdir, mode); //常规权限  0755 0644
				return true;
			} else {
				logger.error("login exception,user or password error!");
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}
	
	/**
	 * 将本地某文件复制到指定远程Linux服务器的指定目录
	 * 
	 * @author 
	 * 
	 * @since 2017/6/28
	 * 
	 * 				远程目录(例：/test)
	 * @throws Exception 
	 * 				IO异常或认证异常
	 */
	public boolean sendFiles(String[] localFiles, String targetdir){
		Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				 for (String localFile : localFiles)  
                 {  
                     scpClient.put(localFile, targetdir,"0755");  
                 }
				return true;
			} else {
				logger.error("login exception,user or password error!");
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}
	
	
	public boolean sendFiles(String[] localFiles, String targetdir, String mode){
		Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				for (String localFile : localFiles)  
				{  
					scpClient.put(localFile, targetdir, mode);  
				}
				return true;
			} else {
				logger.error("login exception,user or password error!");
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}
	//common01为本地文件,common02为远程文件
	public boolean sendFiles(List<CommonVo> uploadfiles, String mode){
		Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				 for (CommonVo item : uploadfiles)
		         {  
		             scpClient.put(item.getCommon01(), item.getCommon02(), mode);  
		         }
				 return true;				
			}else{
				logger.error("scp验证失败");
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}
	
	//common01为本地文件,common02为远程文件, common03 远程目录
	public boolean sendFiles(List<CommonVo> uploadfiles, String mode, boolean mkdir){
		Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				 for (CommonVo item : uploadfiles)
		         {  
					 if(mkdir == true){
						 String commmand = "";
						 if(StringUtils.isEmpty(item.getCommon03())){
							 commmand = commmand + item.getCommon02();
						 }else{
							 commmand = commmand + item.getCommon03();
						 }
						 mkdir(conn, commmand);
					 }
		             scpClient.put(item.getCommon01(), item.getCommon02(), mode);  
		         }
				 return true;				
			}else{
				logger.error("scp验证失败");
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}
	
	//common01为本地文件,common02为远程文件, common03 远程目录
	public boolean sendFiles(List<CommonVo> uploadfiles, boolean mkdir){
		Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				 for (CommonVo item : uploadfiles)
		         {  
					 if(mkdir == true){
						 String commmand = "";
						 if(StringUtils.isEmpty(item.getCommon03())){
							 commmand = commmand + item.getCommon02();
						 }else{
							 commmand = commmand + item.getCommon03();
						 }
						 mkdir(conn, commmand);
					 }
		             scpClient.put(item.getCommon01(), item.getCommon02(), "0755");  
		         }
				 return true;				
			}else{
				logger.error("scp验证失败");
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}
	
	public boolean mkdir(Connection conn, String dirpath){
		String commmand = "mkdir -p " + dirpath;         
		Session session = null;
		try{
			session = conn.openSession();
			session.execCommand(commmand);
			processStdout(session.getStdout(),DEFAULTCHART);			
		}catch(Exception e){
			logger.error("创建远程文件夹失败：", e.getMessage());
		}finally{
			try{
				if (session != null) {
					session.close();
				}
			}catch(Exception e){
				;
			}
		}
		return false;
	}
	
	private String processStdout(InputStream in, String charset){  
        InputStream    stdout = new StreamGobbler(in);  
        StringBuffer buffer = new StringBuffer();;  
        try {  
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));  
            String line=null;  
            while((line=br.readLine()) != null){  
                buffer.append(line+"\n");  
            }  
        } catch (UnsupportedEncodingException e) {  
        	logger.error(e.getMessage());
        } catch (IOException e) {  
        	logger.error(e.getMessage());
        }  
        return buffer.toString();  
    }  
      
	
	//common01为本地文件,common02为远程文件
	public boolean sendFiles(List<CommonVo> uploadfiles){
		return sendFiles(uploadfiles, "0755");
	}
	
	
	/** 
     * 从远程服务器端下载文件到本地指定的目录中 
     * @param remoteFile 
     */
    public boolean downloadFile(String remoteFile,String localdir)  
    {  
    	Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				scpClient.get(remoteFile, localdir);
				return true;
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
    }
    
	/** 
     * 从远程服务器端下载文件到本地指定的目录中 
     */
    public boolean downloadFile(String remoteFiles[],String localdir)  
    {  
    	Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				for(String file: remoteFiles){
					scpClient.get(file, localdir);
				}
				return true;
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
    }
    
    /** 
     * 从远程服务器端下载文件到本地指定的目录中 
     */
    public boolean downloadFile(List<CommonVo> remoteFiles)
    {  
    	Connection conn = null;
		SCPClient scpClient = null;
		try {
			//连接
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				//创建SCP客户端 
				scpClient = conn.createSCPClient();
				for(CommonVo item: remoteFiles){
					scpClient.get(item.getCommon01(), item.getCommon02());
				}				
				return true;
			}
		} catch (IOException e) {
			logger.error("执行scp命令失败：", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
    }
	
    
    /**
	 * 在指定远程Linux服务器上执行命令</br>
	 * 不支持执行结果
	 * 
	 * @author 
	 * 
	 * @since 2017/6/28
	 * 
	 * 				服务器登录密码
	 * @param cmd 
	 * 				标准Linux命令
	 * @throws Exception
	 * 				IO异常或认证异常
	 */
    public boolean execCommand(String cmd){  
    	
        Connection conn = null;
        Session session = null; 
		try {
			conn = new Connection(ipaddress,Integer.parseInt(ipport));
			conn.connect();
			//认证
			boolean isAuthed = conn.authenticateWithPassword(username, userpwd);
			//认证成功
			if(isAuthed) {
				session = conn.openSession();    
                session.execCommand(cmd); 
                return true;
			} else {
				logger.error("login exception,user or password error!");
			}
		} catch (IOException e) {
			logger.error("执行命令失败：", e);
		} finally {
			if (session != null) {
				session.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return false;
          
    } 
    /**
     * 创建目录
     * @param dirPath 目录路径
     * @throws Exception
     */
    public boolean mkdir(String dirPath){
    	String cmd = "mkdir "  + dirPath;
    	return execCommand(cmd);
    }

}
