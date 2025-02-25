package com.triplog.attraction.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.triplog.attraction.model.AttractionDto;
import com.triplog.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService{

	private final AttractionMapper attractionMapper;
	
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
	public List<AttractionDto> getAttractions(Map<String, String> map) {
		System.out.println(attractionMapper.getAttractions(map));
		return attractionMapper.getAttractions(map);
	}

	// @Override
	// public List<AttractionDto> getAttractions(int planNo) {
	// 	return attractionMapper.getAttractions(planNo);
	// }

	@Override
	public void UpdateHitCount(int attractionNo) {
		attractionMapper.UpdateHitCount(attractionNo);
	}

	@Override
	public void UpdateHitLog(int attractionNo, String tokenId) {
		attractionMapper.UpdateHitLog(attractionNo, tokenId);
	}

	@Override
	public AttractionDto getDetailAttraction(int attractionNo) {
		
		return attractionMapper.getDetailAttraction(attractionNo);
	}

	@Override
	public List<Map<String, String>> getSidos() {
		return attractionMapper.getSidos();
	}

	@Override
	public List<Map<String, String>> getGuguns(int sidoCode) {
		return attractionMapper.getGuguns(sidoCode);
	}

	@Override
	public List<Map<String, String>> getContentTypes() {
		return attractionMapper.getContentTypes();
	}
	
	@Override
	public List<Map<String, Object>> getSidoCounts() {
	    return attractionMapper.getSidoCounts();
	}

}
