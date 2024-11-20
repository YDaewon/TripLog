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
		return planMapper.createPlan(planDto);
	}

	@Override
	public PlanDto getPlan(int planNo) {
		return planMapper.getPlan(planNo);
	}

	@Override
	public List<PlanDto> getPlans(int userId) {
		return planMapper.getPlans(userId);
	}

	@Override
	public List<DestinationDto> getDestinations(int planNo) {
		return planMapper.getDestinations(planNo);
	}

	@Override
	public int updatePlan(PlanDto planDto) {
		return planMapper.updatePlan(planDto);
	}

	@Override
	public int deletePlan(int planNo) {
		return planMapper.deletePlan(planNo);
	}
	
	@Override
	public int deletePlanAdmin(Map<String, Integer> map) {
		return planMapper.deletePlanAdmin(map);
	}

	@Override
	public void addDestinations(DestinationDto destinationDto) {
		planMapper.addDestinations(destinationDto);
	}

	@Override
	public void deleteDestination(int destinationNo) {
		planMapper.deleteDestination(destinationNo);
	}

	@Override
	public void updateDestination(DestinationDto destinationDto) {
		try {			
			planMapper.updateDestination(destinationDto);
		}catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
