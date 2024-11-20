package com.triplog.plan.controller;

import java.util.Collection;
import java.util.Enumeration;
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
import com.triplog.util.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
			@Parameter(description = "여행 계획 기본정보", required = true) @RequestBody PlanDto planDto,
			HttpServletRequest request) {
		try {
			int tokenNo = (Integer) request.getAttribute("userNo");
			planDto.setUserNo(tokenNo);
			planService.createPlan(planDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("여행 계획 생성 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 생성 중 문제 발생");
		}
	}

	@GetMapping("/{planNo}")
	@Operation(summary = "여행 계획 조회", description = "여행 계획 정보를 조회합니다.")
	public ResponseEntity<?> getPlan(
			@Parameter(description = "계획 번호", required = true) @PathVariable("planNo") int planNo,
			HttpServletRequest request) {
		try {
			PlanDto result = planService.getPlan(planNo);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 조회 중 문제 발생");
		}
	}

	@GetMapping("/")
	@Operation(summary = "여행 계획 조회", description = "여행 계획 정보를 조회합니다.")
	public ResponseEntity<?> getPlans(HttpServletRequest request) {
		try {
			List<PlanDto> results = planService.getPlans((Integer)request.getAttribute("userNo"));
			return new ResponseEntity<>(results, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 조회 중 문제 발생");
		}
	}

	@PutMapping("/")
	@Operation(summary = "여행 계획 수정", description = "여행 계획 기본정보를 수정합니다.")
	public ResponseEntity<String> updatePlan(
			@Parameter(description = "수정할 여행 계획 정보", required = true) @RequestBody PlanDto planDto,
			HttpServletRequest request) {
		int tokenNo = (Integer) request.getAttribute("userNo");
		if (planDto.getUserNo() == tokenNo) {
			try {
				planService.updatePlan(planDto);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("여행 계획 수정 성공");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 수정 중 문제 발생");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
		}
	}

	@DeleteMapping("/")
	@Operation(summary = "여행 계획 삭제", description = "여행 계획을 삭제합니다.")
	public ResponseEntity<String> deletePlan(
			@Parameter(description = "여행 계획 번호", required = true) @RequestBody PlanDto planDto,
			HttpServletRequest request) {
		int tokenNo = (Integer) request.getAttribute("userNo");
		if (planDto.getUserNo() == tokenNo) {
			try {
				int res = planService.deletePlan(planDto.getPlanNo());
				if (res == 1) {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body("여행 계획 삭제 성공");
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 계획 삭제 중 문제 발생");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
		}
	}
	// 여행계획 CRUD ↑ ----------------------------------------------------
	// 목적지 CRUD ↓ ------------------------------------------------------
	
	@GetMapping("/dest")
	@Operation(summary = "여행 목적지 조회", description = "여행 목적지 정보들을 조회합니다.")
	public ResponseEntity<?> getDestinations(
			@Parameter(description = "소속된 여행계획 번호", required = true) @RequestParam("planNo") int planNo) {
		try {
			List<DestinationDto> results = planService.getDestinations(planNo);
			for(DestinationDto d : results) {
				System.out.println(d.toString());
			}
			return new ResponseEntity<>(results, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 목적지 조회 중 문제 발생");
		}
	}
	
	
	/* 목적지 추가
	{
	  "planNo": 2,
	  "destinationOrder": 3,
	  "visitDate": "2024-12-12",
	  "memo": "추가테스트",
	  "attractionNo": 3
	}
	*/
	@PostMapping("/dest")
	@Operation(summary = "여행 목적지 추가", description = "여행 목적지 정보를 추가합니다.")
	public ResponseEntity<?> addDestinations(
			@Parameter(description = "추가할 목적지 정보", required = true) @RequestBody DestinationDto destinationDto) {
		try {
			System.out.println(destinationDto.toString());
			planService.addDestinations(destinationDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("목적지 추가 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("목적지 추가 중 문제 발생");
		}
	}

	
	/* 목적지 수정
	{
		"planNo": 2,
		"destinationNo": 5,
		"destinationOrder": 3,
		"visitDate": "2024-12-12",
		"memo": "수정테스트",
		"attractionNo": 3
	}
	*/
	@PutMapping("/dest")
	@Operation(summary = "여행 목적지 수정", description = "기존의 여행 목적지를 수정합니다.")
	public ResponseEntity<?> updateDestination(
			@Parameter(description = "변경할 목적지 정보", required = true) @RequestBody DestinationDto destinationDto) {
	    try {
	        planService.updateDestination(destinationDto);
	        return new ResponseEntity<>("여행 목적지 수정 성공", HttpStatus.OK);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 목적지 수정 중 문제 발생");
	    }
	}
	
	@DeleteMapping("/dest/{destinationNo}")
	@Operation(summary = "여행 목적지 삭제", description = "여행 목적지를 삭제합니다.")
	public ResponseEntity<?> deleteDestination(
			@Parameter(description = "삭제할 목적지 번호", required = true) @PathVariable("destinationNo") int destinationNo) {
	    try {
	        planService.deleteDestination(destinationNo);
	        return new ResponseEntity<>("여행 목적지 삭제 성공", HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 목적지 삭제 중 문제 발생");
	    }
	}

	
}
