package com.triplog.file.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileUploadServiceImpl implements FileUploadService{
		@Value("${file.path}")
	    private String uploadPath;

	    public String upload(String No, MultipartFile uploadFile) {
	        String origName = uploadFile.getOriginalFilename();
	        String imgurl = "";
	        try {
	            File directory = new File(uploadPath);
	                if (!directory.exists()) {
	                    directory.mkdirs();  // 디렉토리 생성
	                }
	    	        if (!uploadFile.isEmpty()) {
	    	            // 파일 이름 생성 및 저장
	    	            String fileName = getUuid() + No + "_" + uploadFile.getOriginalFilename();
	    	            File saveFile = new File(uploadPath, fileName);
	    	            uploadFile.transferTo(saveFile);
	    	            imgurl = "http://localhost:80/file/" + fileName;
	    	        }
	        } catch (StringIndexOutOfBoundsException | IllegalStateException | java.io.IOException e) {
	            imgurl = null;
	        }
	        return imgurl;
	    }

	    private static String getUuid() {
	        return UUID.randomUUID().toString().replaceAll("-", "");
	    }
}
