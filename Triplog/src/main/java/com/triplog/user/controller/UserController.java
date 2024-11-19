package com.triplog.user.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplog.user.service.UserService;
import com.triplog.user.model.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "User Controller", description = "유저 정보를 담당하는 컨트롤러입니다.")
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService UserService;

	public UserController(UserService UserService) {
		super();
		this.UserService = UserService;
	}

	// 아이디 중복 체크
	@GetMapping("/{userid}")
	@Operation(summary = "아이디 중복 체크", description = "회원가입 중 ID의 중복 여부를 체크")
	public ResponseEntity<Map<String, Integer>> idCheck(@PathVariable("userid") String userId) throws Exception {
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
			@Parameter(description = "회원가입 정보", required = true) @RequestBody UserDto UserDto) {
		try {
			UserService.joinUser(UserDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 문제 발생");
		}
	}

	// 로그인
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "로그인을 수행")
	public ResponseEntity<?> login(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "로그인 정보", required = true)
			@RequestBody Map<String, String> loginInfo, HttpSession session) {
		try {
			UserDto UserDto = UserService.loginUser(loginInfo);
			if (UserDto != null) {
				session.setAttribute("userinfo", UserDto);
				return ResponseEntity.ok(UserDto); // 로그인 성공 시 사용자 정보 반환
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 문제 발생");
		}
	}

	// 로그아웃
	@GetMapping("/logout")
	@Operation(summary = "로그아웃", description = "로그아웃을 수행")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("로그아웃 성공");
	}

	// 마이 페이지 조회
	@GetMapping("/mypage")
	@Operation(summary = "마이페이지 이동", description = "마이 페이지로 이동")
	public ResponseEntity<?> getMypage(HttpSession session) {
		UserDto temp = (UserDto) session.getAttribute("userinfo");
		UserDto UserInfo;
		try {
			UserInfo = (UserDto) UserService.getUser(temp.getUserNo());
			if (UserInfo != null) {
				return ResponseEntity.ok(UserInfo); // 로그인된 사용자 상세 정보 반환
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 정보 불러오는 중 문제 발생");
		}
	}

	// 회원 정보 수정 페이지 조회 (REST에서는 주로 사용하지 않으므로 제거하거나 정보 조회로 처리)
	@GetMapping("/modify")
	@Operation(summary = "회원 정보 수정 페이지 이동", description = "회원 정보 수정 페이지로 이동")
	public ResponseEntity<?> getModifyPage(HttpSession session) {
		UserDto temp = (UserDto) session.getAttribute("userinfo");
		UserDto UserInfo;
		try {
			UserInfo = (UserDto) UserService.getUser(temp.getUserNo());
			if (UserInfo != null) {
				return ResponseEntity.ok(UserInfo); // 로그인된 사용자 상세 정보 반환
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 정보 불러오는 중 문제 발생");
		}
	}

	// 회원 정보 수정
	@PutMapping("/modify")
	@Operation(summary = "회원 정보 수정", description = "회원 정보를 수정하는 URL")
	public ResponseEntity<String> modify(@RequestBody UserDto UserDto, @RequestBody int admin_check, HttpSession session) {
		try {
			UserService.updateUser(UserDto, admin_check);
			UserDto userInfo = UserService.getUser(UserDto.getUserNo());
			return ResponseEntity.ok("회원 정보 수정 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정 중 문제 발생");
		}
	}

	@DeleteMapping("{userNo}")
	@Operation(summary = "계정 삭제", description = "userNo에 해당하는 계정 정보를 삭제")
	public ResponseEntity<String> delete(
			@Parameter(description = "유저 ID", required = true) @PathVariable("userNo") int userNo) {
		try {
			UserService.deleteUser(userNo);
			return ResponseEntity.ok("계정 삭제 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 문제 발생");
		}
	}
}
