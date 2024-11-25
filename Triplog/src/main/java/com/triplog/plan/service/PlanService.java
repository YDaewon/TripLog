package com.triplog.plan.service;

import java.util.List;
import java.util.Map;

import com.triplog.plan.model.DestinationDto;
import com.triplog.plan.model.PlanDto;

public interface PlanService {
	int createPlan(PlanDto planDto);
	PlanDto getPlan(int planNo);
	List<PlanDto> getPlans(int userId);
	int updatePlan(PlanDto planDto);
	int deletePlan(int planNo);
	int deletePlanAdmin(Map<String, Integer> map);
	
	List<DestinationDto> getDestinations(int planNo);
	DestinationDto getDestinationInfo(int attractionNo);
	void addDestinations(DestinationDto destinationDto);
	void deleteDestination(int destinationNo);
	void updateDestination(DestinationDto destinationDto);
}
