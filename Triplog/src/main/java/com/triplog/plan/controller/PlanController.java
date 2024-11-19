package com.triplog.plan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplog.plan.model.PlanDto;
import com.triplog.plan.service.PlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	
	@PostMapping("/create")
	@Operation(summary = "여행 계획 생성", description = "plans 테이블에 계획 insert")
	public ResponseEntity<String> createPlan(
			@Parameter(description = "여행 계획 기본정보", required = true) @RequestBody PlanDto planDto) {
		try {
			planService.createPlan(planDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("여행 계획 생성 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 생성 중 문제 발생");
		}
	}
	
	
	
	

}
