package com.triplog.plan.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triplog.plan.model.DestinationDto;
import com.triplog.plan.model.PlanDto;
import com.triplog.plan.service.PlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Plan Controller", description = "여행 계획 정보를 담당하는 컨트롤러입니다.")
@RestController
@RequestMapping("/plan")
public class PlanController {
	private final PlanService planService;
	
	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}
	
	@PostMapping("/")
	@Operation(summary = "여행 계획 생성", description = "새 여행 계획을 추가합니다.")
	public ResponseEntity<String> createPlan(
			@Parameter(description = "여행 계획 기본정보", required = true) @RequestBody PlanDto planDto) {
		try {
			planService.createPlan(planDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("여행 계획 생성 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 생성 중 문제 발생");
		}
	}
	
	@GetMapping("/")
	@Operation(summary = "여행 계획 조회", description = "여행 계획 정보를 조회합니다.")
	public ResponseEntity<?> getPlan(
			@Parameter(description = "회원 ID", required = true) @RequestParam("userId") String userId,
			@Parameter(description = "계획 번호", required = true) @RequestParam("planNo") String planNo) {
		try {
			Map<String, String> map = new HashMap<>();
			map.put("userId", userId);
			map.put("planNo", planNo);
			PlanDto result = planService.getPlan(map);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 조회 중 문제 발생");
		}
	}
	
	@GetMapping("/all")
	@Operation(summary = "여행 계획 조회", description = "여행 계획 정보를 조회합니다.")
	public ResponseEntity<?> getPlans(
			@Parameter(description = "회원 ID", required = true) @RequestParam("userId") String userId) {
		try {
			List<PlanDto> results = planService.getPlans(userId);
			return new ResponseEntity<>(results, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 조회 중 문제 발생");
		}
	}
	@GetMapping("/dest")
	@Operation(summary = "여행 목적지 조회", description = "여행 목적지 정보들을 조회합니다.")
	public ResponseEntity<?> getDestinations(
			@Parameter(description = "계획 번호", required = true) @RequestParam("planNo") String planNo) {
		try {
			List<DestinationDto> results = planService.getDestinations(planNo);
			for(DestinationDto d : results) {
				System.out.println(d.toString());
			}
			return new ResponseEntity<>(results, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 목적지 조회 중 문제 발생");
		}
	}
	
	// TODO: dest CRUD 작성
	
	
	
	@PutMapping("/")
	@Operation(summary = "여행 계획 수정", description = "여행 계획 기본정보를 수정합니다.")
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
	@Operation(summary = "여행 계획 삭제", description = "여행 계획을 삭제합니다.")
	public ResponseEntity<String> deletePlan(
			@Parameter(description = "여행 계획 번호", required = true) @RequestParam String planNo, HttpServletRequest request) {
		try {
			int res = planService.deletePlan(planNo);
			if(res==1) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("여행 계획 삭제 성공");
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 삭제 중 문제 발생");
		}
	}
	
	@DeleteMapping("/admin")
	@Operation(summary = "여행 계획 진짜 삭제", description = "여행 계획을 진짜로 삭제합니다.")
	public ResponseEntity<String> deletePlanAdmin(
			@Parameter(description = "여행 계획 번호", required = true) @RequestParam String planNo, HttpServletRequest request) {
		try {
			request.getHeader("userId");
			int res = planService.deletePlanAdmin(planNo);
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
