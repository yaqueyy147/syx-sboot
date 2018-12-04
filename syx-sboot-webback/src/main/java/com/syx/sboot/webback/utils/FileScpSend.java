package com.syx.sboot.webback.utils;

import com.syx.sboot.common.utils.DateUtils;
import com.syx.sboot.webback.entity.Qzstoreset;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
public class FileScpSend extends AbstractFileSend{

    public FileScpSend(HttpServletRequest request) throws Exception {
        super(request);
    }

    /**
     *
     * @param files
     * @return
     */
    public List<String> uploadFile(MultipartFile[] files){
        List<String> filePaths = new ArrayList<>() ;
        //上传
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem item = (DiskFileItem)cf.getFileItem();

                String middleAddress = DateUtils.getYear() + DateUtils.getMonth();
                String tmpPath = request.getSession().getServletContext().getRealPath("/") + File.separator + basePath + File.separator;
                String fileName = item.getName();
                String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
                String newFileName = UUID.randomUUID().toString().replaceAll("-","") + suffix ;
                //下载到本地临时保存
//                File uploadedFile = new File(tmpPath, fileName);
                File uploadedFile = new File(tmpPath, newFileName);
                try {
                    item.write(uploadedFile);
                    String fileNameNoExt = uploadedFile.getName().substring(0,uploadedFile.getName().lastIndexOf("."));
                    Qzstoreset qzstoreset = (Qzstoreset) request.getAttribute("qzstoreset");
                    String uploadPath = qzstoreset.getLocaladdress()
                            + File.separator + middleAddress + File.separator;

                    ScpFileUtils scpfileutils = new ScpFileUtils(qzstoreset.getFtpaddress(),qzstoreset.getFtpport(),qzstoreset.getFtpusername(),qzstoreset.getFtppassword());
                    if(scpfileutils.sendFile(tmpPath+fileName,uploadPath,true)){
                        filePaths.add(middleAddress + File.separator + newFileName) ;
                    }
//                    this.deletefile(tmpPath+fileName);
                    this.deletefile(tmpPath+newFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return filePaths;
    }

    @Override
    public List<String> uploadFile(List<MultipartFile> files) {
        List<String> filePaths = new ArrayList<>() ;
        //上传
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem item = (DiskFileItem)cf.getFileItem();

                String middleAddress = DateUtils.getYear() + DateUtils.getMonth();
                String tmpPath = request.getSession().getServletContext().getRealPath("/") + File.separator + basePath + File.separator;
                String fileName = item.getName();
                String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
                String newFileName = UUID.randomUUID().toString().replaceAll("-","") + suffix ;
                //下载到本地临时保存
//                File uploadedFile = new File(tmpPath, fileName);
                File uploadedFile = new File(tmpPath, newFileName);
                try {
                    item.write(uploadedFile);
                    String fileNameNoExt = uploadedFile.getName().substring(0,uploadedFile.getName().lastIndexOf("."));
                    Qzstoreset qzstoreset = (Qzstoreset) request.getAttribute("qzstoreset");
                    String uploadPath = qzstoreset.getLocaladdress()
                            + File.separator + middleAddress + File.separator;

                    ScpFileUtils scpfileutils = new ScpFileUtils(qzstoreset.getFtpaddress(),qzstoreset.getFtpport(),qzstoreset.getFtpusername(),qzstoreset.getFtppassword());
                    if(scpfileutils.sendFile(tmpPath+fileName,uploadPath,true)){
                        filePaths.add(middleAddress + File.separator + newFileName) ;
                    }
//                    this.deletefile(tmpPath+fileName);
                    this.deletefile(tmpPath+newFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return filePaths;
    }

    public static void main(String[] args) throws IOException {
        org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile("/Users/liuyang/Desktop/测试8.zip");
        zipFile.getEntry("测试8/info.json");
        zipFile.getInputStream(zipFile.getEntry("测试8/info.json"));
    }
}
