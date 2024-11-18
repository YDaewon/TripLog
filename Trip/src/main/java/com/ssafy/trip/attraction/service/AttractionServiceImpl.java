package com.ssafy.trip.attraction.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.attraction.model.AttractionDto;
import com.ssafy.trip.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService{

	private final AttractionMapper attractionMapper;
	
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
	public List<AttractionDto> getAttractions(Map<String, String> map) {
		return attractionMapper.getAttractions(map);
	}

}
