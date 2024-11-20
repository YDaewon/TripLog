package com.triplog.plan.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.triplog.plan.model.DestinationDto;
import com.triplog.plan.model.PlanDto;
import com.triplog.plan.model.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {

	private final PlanMapper planMapper;

	public PlanServiceImpl(PlanMapper planMapper) {
		super();
		this.planMapper = planMapper;
	}

	@Override
	public int createPlan(PlanDto planDto) {
		System.out.println(planDto.toString());
		return planMapper.createPlan(planDto);
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
	public int updatePlan(PlanDto planDto) {
		return planMapper.updatePlan(planDto);
	}

	@Override
	public int deletePlan(String planNo) {
		return planMapper.deletePlan(planNo);
	}
	
	@Override
	public int deletePlanAdmin(String planNo) {
		return planMapper.deletePlanAdmin(planNo);
	}
}
