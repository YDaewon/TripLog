package com.triplog.file.Controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.triplog.file.model.FileResponse;
import com.triplog.file.service.FileUploadService;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "File Controller", description = "파일을 담당하는 컨트롤러입니다.")
@RestController
@RequestMapping("/file")
public class FIleController {
	@Value("${file.path}")
    private String uploadPath;
	
	
	private final FileUploadService fileUploadService;
	
	
    public FIleController(FileUploadService fileUploadService) {
		super();
		this.fileUploadService = fileUploadService;
	}

	
    @GetMapping("{fileName}")
	@Operation(summary = "파일 다운로드", description = "파일을 다운로드 합니다.")
	public ResponseEntity<Resource> download(
			@PathVariable("fileName") String fileName
			) throws Exception {
        try {
            // 파일 경로 설정 (업로드된 파일 경로)
            Path filePath = Paths.get(uploadPath).resolve(fileName);
            Resource resource = new FileSystemResource(filePath);
            System.out.println(filePath);
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();  // 파일이 없으면 404 반환
            }

            // 파일을 반환 (이미지 파일일 경우, MIME 타입 설정)
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)  // 이미지 MIME 타입 (필요시 다른 타입으로 수정)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // 오류 발생 시 500 반환
        }
	}

	@PostMapping("{userId}")
	@Operation(summary = "파일 업로드", description = "파일을 업로드 합니다.")
	public ResponseEntity<String> upload(
			@Parameter(description = "파일 정보", required = true) @RequestPart MultipartFile userImage,
			@PathVariable("userId") String userId) {
		String imgurl = "";
		try {
			imgurl = fileUploadService.upload(userId, userImage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ResponseEntity.status(HttpStatus.OK).body(imgurl);
	}
	
	@PostMapping("article")
	@Operation(summary = "에디터 이미지 파일 업로드", description = "에디터 이미지 파일을 업로드 합니다.")
	public ResponseEntity<FileResponse> articleupload(
			@Parameter(description = "파일 정보", required = true) MultipartRequest request
			) throws IOException, IllegalStateException, java.io.IOException {
		MultipartFile file = request.getFile("upload");
		return new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(fileUploadService.upload("article", file)).
                build(), HttpStatus.OK);
	}
	
}
