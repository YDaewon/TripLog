package com.triplog.attraction.service;

import java.util.List;
import java.util.Map;

import com.triplog.attraction.model.AttractionDto;

public interface AttractionService {
	List<AttractionDto> getAttractions(Map<String, String> map);
	AttractionDto getDetailAttraction(int attractionNo);
	void UpdateHitLog(int attractionNo, String userId);
	void UpdateHitCount(int attractionNo);
	List<Map<String, String>> getSidos();
	List<Map<String, String>> getGuguns(int sidoCode);
	List<Map<String, String>> getContentTypes();
	List<Map<String, Object>> getSidoCounts();
	// List<AttractionDto> getAttractions(int planNo);
}
