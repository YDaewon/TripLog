package com.triplog.plan.service;

import java.util.List;
import java.util.Map;

import com.triplog.plan.model.DestinationDto;
import com.triplog.plan.model.PlanDto;

public interface PlanService {
	int createPlan(PlanDto planDto);
	PlanDto getPlan(Map<String, String> map);
	List<PlanDto> getPlans(String userId);
	List<DestinationDto> getDestinations(String planNo);
	int updatePlan(PlanDto planDto);
	int deletePlan(String planNo);
}
