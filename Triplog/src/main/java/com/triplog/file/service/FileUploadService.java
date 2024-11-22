package com.triplog.file.service;

import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

public interface FileUploadService {
	public String upload(String articleNo, MultipartFile uploadFile) throws IOException, IllegalStateException, java.io.IOException;
}
