package com.syx.sboot.webback.utils;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileSend {
     List<String> uploadFile(MultipartFile[] files);
     List<String> uploadFile(List<MultipartFile> files);
}
