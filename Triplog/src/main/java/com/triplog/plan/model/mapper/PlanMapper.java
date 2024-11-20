package com.triplog.plan.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.triplog.plan.model.DestinationDto;
import com.triplog.plan.model.PlanDto;

@Mapper
public interface PlanMapper {
	int createPlan(PlanDto planDto);
	PlanDto getPlan(int planNo);
	List<PlanDto> getPlans(int userId);
	List<DestinationDto> getDestinations(int planNo);
	int updatePlan(PlanDto planDto);
	int deletePlan(int planNo);
	int deletePlanAdmin(Map<String, Integer> map);
}
