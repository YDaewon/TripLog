package com.ssafy.trip.attraction.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.attraction.model.AttractionDto;

public interface AttractionService {
	List<AttractionDto> getAttractions(Map<String, String> map);
}
