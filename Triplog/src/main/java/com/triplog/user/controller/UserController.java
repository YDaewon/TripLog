package com.triplog.user.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.triplog.user.service.UserService;
import com.triplog.util.JWTUtil;
import com.triplog.user.model.UserDto;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "User Controller", description = "유저 정보를 담당하는 컨트롤러입니다.")
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService UserService;
	private final JWTUtil jwtUtil;

	public UserController(UserService UserService, JWTUtil jwtUtil) {
		super();
		this.UserService = UserService;
		this.jwtUtil = jwtUtil;
	}

	// 아이디 중복 체크
	@GetMapping("/{userId}")
	@Operation(summary = "아이디 중복 체크", description = "회원가입 중 ID의 중복 여부를 체크")
	public ResponseEntity<Map<String, Integer>> idCheck(@PathVariable("userId") String userId) throws Exception {
		System.out.println(userId);
		int cnt = UserService.idCheck(userId);
		Map<String, Integer> response = new HashMap<>();
		response.put("cnt", cnt);

		return ResponseEntity.ok(response);
	}

	// 회원가입
	@PostMapping("/join")
	@Operation(summary = "회원가입", description = "유저 테이블에 데이터를 Insert")
	public ResponseEntity<String> join(
			@Parameter(description = "회원가입 정보", required = true) @RequestBody UserDto userDto) {
		try {

			// userDto, userImage 확인
	        System.out.println("Received userDto: " + userDto);
			
//	        // 이미지 저장 경로
//	        String uploadPath = "src/main/resources/static/assets/img/profile";
//	        if (!userImage.isEmpty()) {
//	            // 파일 이름 생성 및 저장
//	            String fileName = UUID.randomUUID().toString() + "_" + userImage.getOriginalFilename();
//	            File saveFile = new File(uploadPath, fileName);
//	            userImage.transferTo(saveFile);
//
//	            // 저장된 파일 이름을 DTO에 설정
//	            userDto.setUserImage(fileName);
//	        }
	        
	        
			UserService.joinUser(userDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 문제 발생");
		}
	}
	

	// 로그인
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "로그인을 수행")
	public ResponseEntity<Map<String, Object>> login(
			@Parameter(description = "로그인 정보", required = true) @RequestBody Map<String, String> loginInfo) {
		log.debug("login user : {}", loginInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto loginUser = UserService.loginUser(loginInfo);
			if (loginUser != null) {
				System.out.println(loginUser.getNickname());

				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId(), loginUser.getUserNo(), loginUser.getRole());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId(), loginUser.getUserNo(), loginUser.getRole());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);
				
				UserService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);

				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "로그인 중 문제 발생");
				status = HttpStatus.UNAUTHORIZED;

			}
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@Operation(summary = "로그아웃", description = "회원 정보를 담은 Token 을 제거한다.")
	@GetMapping("/logout/{userId}")
	@Hidden
	public ResponseEntity<?> removeToken(
			@PathVariable("userId") @Parameter(description = "로그아웃 할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}


	// 마이 페이지 조회
	@Operation(summary = "마이페이지", description = "유저의 상세정보를 반환")
	@GetMapping("/info")
	public ResponseEntity<Map<String, Object>> getInfo(
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String tokenId = (String) request.getAttribute("userId");
		if (tokenId != null) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = UserService.getUser(tokenId);
				System.out.println(userDto.toString());
				resultMap.put("userInfo", userDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}


	// 회원 정보 수정
	@PutMapping("/info/{userId}")
	@Operation(summary = "회원 정보 수정", description = "회원 정보를 수정하는 URL")
	public ResponseEntity<Map<String, Object>> modify(@RequestBody UserDto UserDto,  @PathVariable("userId") String userId,
		HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String tokenId = (String) request.getAttribute("userId");
		if (userId.equals(tokenId)) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto.setUserId(userId);
				System.out.println(UserDto.toString());
				UserService.updateUser(UserDto);
				resultMap.put("message", "회원 정보 수정 성공");
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보수정 실패 : {}", e);
				resultMap.put("message", "회원 정보 수정 중 문제 발생");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@DeleteMapping("/info/{userId}")
	@Operation(summary = "계정 삭제", description = "userNo에 해당하는 계정 정보를 삭제")
	public ResponseEntity<Map<String, Object>> delete(
			@Parameter(description = "유저 ID", required = true) @PathVariable("userId") String userId,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String tokenId = (String) request.getAttribute("userId");
		if (userId.equals(tokenId)) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserService.deleteUser(userId);
				UserService.deleRefreshToken(userId);
				resultMap.put("message", "회원 삭제 성공");
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error(" 회원 삭제 실패 : {}", e);
				resultMap.put("message", "회원 정보 삭제 중 문제 발생");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@Operation(summary = "Access Token 재발급", description = "만료된 access token 을 재발급 받는다.")
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, @RequestHeader("refreshToken") String token)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		log.debug("token : {}, memberDto : {}", token, userDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(UserService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(userDto.getUserId(), userDto.getUserNo(), userDto.getRole());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 access token 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("refresh token 도 사용 불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
