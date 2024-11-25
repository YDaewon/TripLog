package com.triplog.attraction.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.triplog.attraction.model.AttractionDto;

@Mapper
public interface AttractionMapper {
	List<AttractionDto> getAttractions(Map<String, String> map);
	AttractionDto getDetailAttraction(int attractionNo);
	void UpdateHitLog(int attractionNo, String userId);
	void UpdateHitCount(int attractionNo);
	List<Map<String, String>> getSidos();
	List<Map<String, String>> getGuguns(int sidoCode);
	List<Map<String, String>> getContentTypes();
	List<AttractionDto> getAttractions(int planNo);
}
