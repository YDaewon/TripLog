package com.triplog.plan.service;

import java.util.List;
import java.util.Map;

import com.triplog.plan.model.DestinationDto;
import com.triplog.plan.model.PlanDto;
import com.triplog.plan.model.mapper.PlanMapper;

public class PlanServiceImpl implements PlanService {

	private final PlanMapper planMapper;

	public PlanServiceImpl(PlanMapper planMapper) {
		super();
		this.planMapper = planMapper;
	}

	@Override
	public void createPlan(PlanDto planDto) {
		planMapper.createPlan(planDto);
	}

	@Override
	public PlanDto getPlan(Map<String, String> map) {
		return planMapper.getPlan(map);
	}

	@Override
	public List<PlanDto> getPlans(String userId) {
		return planMapper.getPlans(userId);
	}

	@Override
	public List<DestinationDto> getDestinations(String planNo) {
		return planMapper.getDestinations(planNo);
	}

	@Override
	public void updatePlan(PlanDto planDto) {
		planMapper.updatePlan(planDto);
	}

	@Override
	public void deletePlan(String planNo) {
		planMapper.deletePlan(planNo);
	}
}
