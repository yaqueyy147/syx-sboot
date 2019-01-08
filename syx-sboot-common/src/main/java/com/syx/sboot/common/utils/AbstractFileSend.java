package com.syx.sboot.common.utils;

import com.syx.sboot.common.entity.Qzstoreset;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;

/**
 * Created by Intellij idea
 * User: liu.y
 * Date: 2018/3/21
 * Description:
 * To change this template use File | Setting | File and Code Templates
 */
@SuppressWarnings("all")
public abstract class AbstractFileSend implements FileSend{
    protected Map<String,Object> fieldsMap;
    protected HttpServletRequest request;
    protected Qzstoreset qzstoreset;

    protected static final Logger log = Logger.getLogger(AbstractFileSend.class);
    protected static final String basePath = "upload"+ File.separator+"adpact"+File.separator;

    public AbstractFileSend(HttpServletRequest request) throws Exception {
        this.request = request;
        qzstoreset = (Qzstoreset) request.getAttribute("qzstoreset");
        fieldsMap = (Map<String,Object>)request.getAttribute("fileFiled");
    }

    /**
     * 删除指定目录下的子文件
     * @param delpath
     */
    protected void deleteChlidFile(String delpath){
        File dirFile = new File(delpath);
        String[] filelist = dirFile.list();
        for (int i = 0; i < filelist.length; i++) {
            File delfile = new File(delpath + File.separator + filelist[i]);
            if (!delfile.isDirectory()) {
                delfile.delete();
            } else if (delfile.isDirectory()) {
                deletefile(delpath + File.separator + filelist[i]);
            }
        }
    }

    /**
     * 对临时生成的文件夹和文件夹下的文件进行删除
     */
    protected void deletefile(String delpath) {
        try {
            File file = new File(delpath);
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + File.separator + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + File.separator + filelist[i]);
                    }
                }
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件校验合法性
     * @param authcode
     * @param
     * @return
     */
    protected boolean validlegality(String path,String authcode){
        boolean tag = false;
        File zipFile = new File(path);

        if(Md5Util.Md5Of32(zipFile).equals(authcode.toLowerCase())){
            tag = true;
        }
        return tag;
    }

    /**
     * 获取zip包文件中的json read字符流
     * @param zipFile
     * @return
     */
    protected static InputStreamReader getInfoJsonRead(org.apache.tools.zip.ZipFile zipFile, String str){
        InputStreamReader read=null;
        try {
            read=new InputStreamReader(zipFile.getInputStream(zipFile.getEntry(str+"/info.json")));
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (java.util.zip.ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read;
    }

    /**
     * 给定一个fileheader 返回文件流
     * @param zipFile
     * @param fileHeader
     * @return
     */
    protected InputStream getInputStream(org.apache.tools.zip.ZipFile zipFile, String fileHeader)  {
        InputStream inputStream = null;
        try {
            ZipEntry zipEntry = zipFile.getEntry(fileHeader);
            if(zipEntry != null){
                inputStream = zipFile.getInputStream(zipEntry);
            }
        } catch (IOException e) {
            ;
        }
        return inputStream;
    }

    private static StringBuffer buildStringBuffer(InputStreamReader reader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        BufferedReader bufferedReader  = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        reader.close();

        return stringBuffer;
    }

    /**
     * 字节流写入指定路径文件
     * @param filePath
     * @param inputStream
     */
    protected Boolean BufferStreamWriteFile(String dirPath ,String filePath,InputStream inputStream){
        Boolean isWriteSuccess = false;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {

            File dirFile = new File(dirPath);
            if(!dirFile.exists()){
                dirFile.mkdir();
            }
            fos = new FileOutputStream(new File(filePath));
            bos = new BufferedOutputStream(fos,100000);
            byte [] input = new byte[1024];//大型的文件对应的数组大一点，比较小的文件数组小一点。
            int len = -1;
            while ((len = inputStream.read(input)) != -1) {
                bos.write(input,0,len);
            }
            isWriteSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bos!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isWriteSuccess;
    }
}
