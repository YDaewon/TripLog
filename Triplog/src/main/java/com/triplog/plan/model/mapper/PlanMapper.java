package com.triplog.plan.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.triplog.plan.model.DestinationDto;
import com.triplog.plan.model.PlanDto;

@Mapper
public interface PlanMapper {
	int createPlan(PlanDto planDto);
	PlanDto getPlan(Map<String, String> map);
	List<PlanDto> getPlans(String userId);
	List<DestinationDto> getDestinations(String planNo);
	int updatePlan(PlanDto planDto);
	int deletePlan(String planNo);
}
