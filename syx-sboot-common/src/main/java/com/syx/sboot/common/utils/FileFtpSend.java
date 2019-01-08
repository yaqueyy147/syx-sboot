package com.syx.sboot.common.utils;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
public class FileFtpSend extends AbstractFileSend {

    private FTPClient ftp;

    public FileFtpSend(HttpServletRequest request) throws Exception {
        super(request);
        /*connect((String)request.getAttribute("path"), qzstoreset.getFtpaddress(),
                Integer.parseInt(qzstoreset.getFtpport()), qzstoreset.getFtpusername(), qzstoreset.getFtppassword());*/
        connect(qzstoreset.getLocaladdress(), qzstoreset.getFtpaddress(),
                Integer.parseInt(qzstoreset.getFtpport()), qzstoreset.getFtpusername(), qzstoreset.getFtppassword());
    }

    public List<String> uploadFile(MultipartFile[] files) {
        List<String> filePaths = new ArrayList<>() ;
        // 上传
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem item = (DiskFileItem)cf.getFileItem();
                boolean success = false;
                BufferedInputStream inStream = null;
                try {
                    String tmpPath = request.getSession().getServletContext().getRealPath("/") + File.separator + basePath;
                    String fileName = item.getName();
                    String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
                    String newFileName = UUID.randomUUID().toString().replaceAll("-","") + suffix ;
                    //下载到本地临时保存
//                    File uploadedFile = new File(tmpPath, fileName);
                    File uploadedFile = new File(tmpPath, newFileName);
                    item.write(uploadedFile);
//                    String adpactPath = String.valueOf(request.getAttribute("path"));
                    String adpactPath = qzstoreset.getLocaladdress() ;
                    inStream = new BufferedInputStream(new FileInputStream(uploadedFile));
                    ftp.changeWorkingDirectory("/");
                    ftp.makeDirectory(adpactPath);
                    createDirs(adpactPath);//remoteUpLoadPath
//                    success = this.ftp.storeFile(fileName, inStream);
                    success = this.ftp.storeFile(newFileName, inStream);
//                    String tempid = fileName.substring(0, fileName.lastIndexOf(".") >=0 ?fileName.lastIndexOf("."):fileName.length());
                    if (success != true) {
                    }else{
                        filePaths.add(newFileName) ;
//                        this.deletefile(tmpPath+fileName);
                        this.deletefile(tmpPath+newFileName);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return filePaths;
    }

    @Override
    public List<String> uploadFile(List<MultipartFile> files) {
        List<String> filePaths = new ArrayList<>() ;
        // 上传
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem item = (DiskFileItem)cf.getFileItem();
                boolean success = false;
                BufferedInputStream inStream = null;
                try {
                    String tmpPath = request.getSession().getServletContext().getRealPath("/") + File.separator + basePath;
                    String fileName = item.getName();
                    String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
                    String newFileName = UUID.randomUUID().toString().replaceAll("-","") + suffix ;
                    //下载到本地临时保存
//                    File uploadedFile = new File(tmpPath, fileName);
                    File uploadedFile = new File(tmpPath, newFileName);
                    item.write(uploadedFile);
//                    String adpactPath = String.valueOf(request.getAttribute("path"));
                    String adpactPath = qzstoreset.getLocaladdress() ;
                    inStream = new BufferedInputStream(new FileInputStream(uploadedFile));
                    ftp.changeWorkingDirectory("/");
                    ftp.makeDirectory(adpactPath);
                    createDirs(adpactPath);//remoteUpLoadPath
//                    success = this.ftp.storeFile(fileName, inStream);
                    success = this.ftp.storeFile(newFileName, inStream);
//                    String tempid = fileName.substring(0, fileName.lastIndexOf(".") >=0 ?fileName.lastIndexOf("."):fileName.length());
                    if (success != true) {
                    }else{
                        filePaths.add(newFileName) ;
//                        this.deletefile(tmpPath+fileName);
                        this.deletefile(tmpPath+newFileName);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return filePaths;
    }

    /**
     * ftp文件存储
     * @param file
     * @param path
     * @return
     */
    private boolean ftpUpload(File file,String path){
        Boolean success = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream inStream = new BufferedInputStream(fileInputStream);
            ftp.changeWorkingDirectory("/");

            ftp.makeDirectory(path);
            createDirs(path);
            success = this.ftp.storeFile(file.getName(), inStream);
            fileInputStream.close();
            inStream.close();
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * ftp字节流存储
     * @param path
     * @param inputStream
     * @return
     */
    private boolean ftpstoreFile(String path,String fileName,InputStream inputStream){
        Boolean isSuccess = false;
        try {

            ftp.changeWorkingDirectory("/");
            ftp.makeDirectory(path);
            createDirs(path);//remoteUpLoadPath
            isSuccess = this.ftp.storeFile(fileName, inputStream);
            inputStream.close();
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }


    public void createDirs(String remoteUpLoadPath) throws IOException {
        String[]dirs = remoteUpLoadPath.split("/");
        for(String dir : dirs){
            this.ftp.mkd(dir);
            this.ftp.changeWorkingDirectory(dir);

        }
    }

    /**
     *
     * @param path 上传到ftp服务器哪个路径下
     * @param addr 地址
     * @param port 端口号
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    private boolean connect(String path,String addr,int port,String username,String password) throws Exception {
        boolean result = false;
        ftp = new FTPClient();
        int reply;
        try {
            ftp.connect(addr,port);
        } catch (IOException e) {
            e.printStackTrace();
            this.log.debug("ftp连接失败");
        }
        ftp.login(username,password);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return result;
        }
        ftp.changeWorkingDirectory(path);
        result = true;
        return result;
    }



}
