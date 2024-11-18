package com.ssafy.trip.attraction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.attraction.model.AttractionDto;
import com.ssafy.trip.attraction.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	@Operation(summary = "관광지 목록 검색", description = "조건에 맞는 관광지 정보를 불러옴")
	public ResponseEntity<?> getAttractionList(
			@RequestBody(description = "검색 조건") @org.springframework.web.bind.annotation.RequestBody Map<String, String> map) {
		List<AttractionDto> list = attractionService.getAttractions(map);
		return ResponseEntity.ok(list);
	}

}
