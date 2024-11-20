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
import org.springframework.web.bind.annotation.RestController;

import com.triplog.attraction.model.AttractionDto;
import com.triplog.attraction.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
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

	@PostMapping("")
	@Operation(summary = "관광지 목록 검색", description = "조건에 맞는 관광지 리스트를 불러옴")
	public ResponseEntity<?> getAttractionList(
			@RequestBody(description = "검색 조건") @org.springframework.web.bind.annotation.RequestBody Map<String, String> map) {
		List<AttractionDto> list = attractionService.getAttractions(map);
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
	
}
