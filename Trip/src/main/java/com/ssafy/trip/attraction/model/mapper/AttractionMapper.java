package com.ssafy.trip.attraction.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.attraction.model.AttractionDto;

public interface AttractionMapper {
	List<AttractionDto> getAttractions(Map<String, String> map);

}
