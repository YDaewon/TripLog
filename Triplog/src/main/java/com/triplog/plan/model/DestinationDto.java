package com.triplog.plan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "DestinationDto (목적지 기본정보)", description = "여행계획에 포함되는 목적지의 정보를 저장하는 Domain Class")
public class DestinationDto {
	@Schema(description = "목적지 고유번호", example = "1")
	private int planNo;
	private Long destinationNo;
	private int destinationOrder;
	private String visitDate;
	private String memo;
	private int attractionNo;
	private String attractionTitle;
	private String contentTypeName;
	private String firstImage1;
	private String addr1;
	private Double latitude;
	private Double longitude;
}
