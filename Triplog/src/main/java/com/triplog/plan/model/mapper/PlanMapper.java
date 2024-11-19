package com.triplog.plan.model.mapper;

import java.util.List;
import java.util.Map;

import com.triplog.plan.model.DestinationDto;
import com.triplog.plan.model.PlanDto;

public interface PlanMapper {
	void createPlan(PlanDto planDto);
	PlanDto getPlan(Map<String, String> map);
	List<PlanDto> getPlans(String userId);
	List<DestinationDto> getDestinations(String planNo);
	void updatePlan(PlanDto planDto);
	void deletePlan(String planNo);
}
