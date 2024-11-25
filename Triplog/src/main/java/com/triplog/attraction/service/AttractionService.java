package com.triplog.attraction.service;

import java.util.List;
import java.util.Map;

import com.triplog.attraction.model.AttractionDto;

public interface AttractionService {
	List<AttractionDto> getAttractions(Map<String, String> map);
	void UpdateHitCount(int attractionNo);
	void UpdateHitLog(int attractionNo, String tokenId);
	AttractionDto getDetailAttraction(int attractionNo);
	List<Map<String, String>> getSidos();
	List<Map<String, String>> getGuguns(int sidoCode);
	List<Map<String, String>> getContentTypes();
	List<Map<String, Object>> getSidoCounts();
}
