package com.syx.sboot.webback.utils;

import com.syx.sboot.common.utils.CommonUtils;
import com.syx.sboot.common.utils.DateUtils;
import com.syx.sboot.common.utils.IdGen;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class FileLocalSend extends AbstractFileSend{
    @Value("${upload.file.path}")
    private String fileurl;
    public FileLocalSend(HttpServletRequest request) throws Exception {
        super(request);
    }

    /**
     *
     * @param files
     * @return
     */
    public List<String> uploadFile(MultipartFile[] files) {
//        String localpath = (String)request.getAttribute("localpath");
        String localpath = qzstoreset.getLocaladdress();
        List<String> filePaths = new ArrayList<>() ;
        // 上传
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                try {
                    CommonsMultipartFile cf= (CommonsMultipartFile)file;
                    DiskFileItem item = (DiskFileItem)cf.getFileItem();
                    String middleAddress = CommonUtils.getDateFrmFormat("yyyyMMdd");;
                    String fileName = item.getName();
                    String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
                    String newFileName = IdGen.uuid() + suffix ;
                    File dirFile = new File(localpath+ File.separator + middleAddress);
                    if(!dirFile.exists()){
                        dirFile.mkdir();
                    }
                    File uploadedFile = new File(dirFile, newFileName);
                    item.write(uploadedFile);
                    filePaths.add(middleAddress+File.separator + newFileName) ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return filePaths;
    }

    @Override
    public List<String> uploadFile(List<MultipartFile> files) {
//        String localpath = request.getSession().getServletContext().getRealPath("");//项目根目录
        String localpath = "";//项目根目录
        localpath = localpath + qzstoreset.getLocaladdress() + File.separator + request.getAttribute("var01");
        List<String> filePaths = new ArrayList<>() ;
        // 上传
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                try {
                    CommonsMultipartFile cf= (CommonsMultipartFile)file;
                    DiskFileItem item = (DiskFileItem)cf.getFileItem();
                    String middleAddress = DateUtils.getYear() + DateUtils.getMonth();
                    String fileName = item.getName();
                    String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
                    String newFileName = IdGen.uuid() + suffix ;
                    File dirFile = new File(localpath+ File.separator + middleAddress);
                    if(!dirFile.exists()){
                        dirFile.mkdirs();
                    }
                    File uploadedFile = new File(dirFile, newFileName);
                    item.write(uploadedFile);
                    String returnPath = request.getAttribute("var01") + File.separator + middleAddress+File.separator + newFileName ;
                    if(returnPath.contains("\\")){
                        returnPath = returnPath.replace("\\","/") ;
                    }
                    filePaths.add(returnPath) ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return filePaths;
    }
}
