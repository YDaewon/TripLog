package com.triplog.plan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplog.plan.model.PlanDto;
import com.triplog.plan.service.PlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Plan Controller", description = "여행계획 정보를 담당하는 컨트롤러입니다.")
@RestController
@RequestMapping("/plan")
public class PlanController {
	private final PlanService planService;
	
	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}
	
	@PostMapping("/")
	@Operation(summary = "여행 계획 생성", description = "새 여행계획을 추가합니다.")
	public ResponseEntity<String> createPlan(
			@Parameter(description = "여행 계획 기본정보", required = true) @RequestBody PlanDto planDto) {
		try {
			planService.createPlan(planDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("여행 계획 생성 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 생성 중 문제 발생");
		}
	}
	
	@PutMapping("/")
	@Operation(summary = "여행 계획 수정", description = "여행계획 기본정보를 수정합니다.")
	public ResponseEntity<String> updatePlan(
			@Parameter(description = "수정할 여행 계획 정보", required = true) @RequestBody PlanDto planDto) {
		try {
			planService.updatePlan(planDto);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("여행 계획 수정 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 수정 중 문제 발생");
		}
	}
	
	@DeleteMapping("/")
	@Operation(summary = "여행 계획 삭제", description = "여행계획을 삭제합니다.")
	public ResponseEntity<String> deletePlan(
			@Parameter(description = "여행 계획 기본정보", required = true) @RequestBody PlanDto planDto, HttpSession session) {
		try {
//			String userNo = (String) session.getAttribute("userNo");
//			if(userNo == null) {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
//			}
			
//			if(!planDto.getUserNo().equals(userNo)) {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
//			}
			
			int res = planService.deletePlan(planDto.getPlanNo());
			if(res==1) {				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("여행 계획 삭제 성공");
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 삭제 중 문제 발생");
		}
	}
}
