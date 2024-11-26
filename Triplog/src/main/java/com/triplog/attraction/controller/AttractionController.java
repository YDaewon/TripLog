package com.triplog.attraction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triplog.attraction.model.AttractionDto;
import com.triplog.attraction.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Attraction Controller", description = "관광지 정보를 담당하는 컨트롤러입니다.")
@RestController
@RequestMapping("/attraction")
public class AttractionController {

	private final AttractionService attractionService;

	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}

	@GetMapping("")
	@Operation(summary = "관광지 목록 검색", description = "조건에 맞는 관광지 리스트를 불러옴")
	public ResponseEntity<?> getAttractionList(
			@Parameter(description = "검색 조건") @RequestParam Map<String, String> map) {
		System.out.println(map.toString());
		List<AttractionDto> list = attractionService.getAttractions(map);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{planNo}")
	@Operation(summary = "관광지 목록 검색", description = "플랜에 포함된 관광지 리스트를 불러옴")
	public ResponseEntity<?> getAttractionList(
			@Parameter(description = "플랜 번호") @PathVariable int planNo) {
		System.out.println(planNo);
		List<AttractionDto> list = attractionService.getAttractions(planNo);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/info/{attractionNo}")
	@Operation(summary = "관광지 상세정보", description = "attractionNo 번호의 관광지 정보를 불러옴")
	public ResponseEntity<AttractionDto> getDetailAttraction(
			@RequestBody(description = "검색 조건")@PathVariable("attractionNo") int attractionNo,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String tokenId = (String) request.getAttribute("userId");
		System.out.println(tokenId);
		attractionService.UpdateHitCount(attractionNo);
		attractionService.UpdateHitLog(attractionNo, tokenId);
		AttractionDto Dto = attractionService.getDetailAttraction(attractionNo);
		return ResponseEntity.ok(Dto);
	}
	
	@GetMapping("/sido")
	@Operation(summary = "시도 목록", description = "DB에 등록된 시/도 목록을 불러옵니다")
	public ResponseEntity<?> getSidos() {
		List<Map<String, String>> sidos = attractionService.getSidos();
		if(sidos!=null) {
			return new ResponseEntity<>(sidos, HttpStatus.OK);
		}else {
	        return new ResponseEntity<>("시/도 목록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/gugun")
	@Operation(summary = "구군 목록", description = "선택된 시/도 코드에 맞는 구/군을 불러옵니다")
	public ResponseEntity<?> getGuguns(
			@Parameter(description = "시도 코드") int sidoCode) {
		List<Map<String, String>> guguns = attractionService.getGuguns(sidoCode);
		if(guguns!=null) {
			return new ResponseEntity<>(guguns, HttpStatus.OK);
		}else {
	        return new ResponseEntity<>("구/군 목록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/content")
	@Operation(summary = "콘텐츠 유형 목록", description = "DB에 저장된 콘텐츠 유형 목록을 불러옵니다")
	public ResponseEntity<?> getContentTypes() {
		List<Map<String, String>> contenttypes = attractionService.getContentTypes();
		if(contenttypes!=null) {
			return new ResponseEntity<>(contenttypes, HttpStatus.OK);
		}else {
	        return new ResponseEntity<>("구/군 목록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
		}
	}
	
}
