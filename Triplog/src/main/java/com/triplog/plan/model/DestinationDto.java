package com.triplog.plan.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "DestinationDto (목적지 기본정보)", description = "여행계획에 포함되는 목적지의 정보를 저장하는 Domain Class")
public class DestinationDto {
	private Long destinationNo;
	private Integer destinationOrder;
	private String visitDate;
	private String memo;
	private Long attractionNo;
	private String attractionTitle;
	private String contentTypeName;
	private String firstImage1;
	private String addr1;
	private Double latitude;
	private Double longitude;
	
	public DestinationDto(Long destinationNo, Integer destinationOrder, String visitDate, String memo,
			Long attractionNo, String attractionTitle, String contentTypeName, String firstImage1, String addr1,
			Double latitude, Double longitude) {
		super();
		this.destinationNo = destinationNo;
		this.destinationOrder = destinationOrder;
		this.visitDate = visitDate;
		this.memo = memo;
		this.attractionNo = attractionNo;
		this.attractionTitle = attractionTitle;
		this.contentTypeName = contentTypeName;
		this.firstImage1 = firstImage1;
		this.addr1 = addr1;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getDestinationNo() {
		return destinationNo;
	}

	public void setDestinationNo(Long destinationNo) {
		this.destinationNo = destinationNo;
	}

	public Integer getDestinationOrder() {
		return destinationOrder;
	}

	public void setDestinationOrder(Integer destinationOrder) {
		this.destinationOrder = destinationOrder;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getAttractionNo() {
		return attractionNo;
	}

	public void setAttractionNo(Long attractionNo) {
		this.attractionNo = attractionNo;
	}

	public String getAttractionTitle() {
		return attractionTitle;
	}

	public void setAttractionTitle(String attractionTitle) {
		this.attractionTitle = attractionTitle;
	}

	public String getContentTypeName() {
		return contentTypeName;
	}

	public void setContentTypeName(String contentTypeName) {
		this.contentTypeName = contentTypeName;
	}

	public String getFirstImage1() {
		return firstImage1;
	}

	public void setFirstImage1(String firstImage1) {
		this.firstImage1 = firstImage1;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "DestinationDto [destinationNo=" + destinationNo + ", destinationOrder=" + destinationOrder
				+ ", visitDate=" + visitDate + ", memo=" + memo + ", attractionNo=" + attractionNo
				+ ", attractionTitle=" + attractionTitle + ", contentTypeName=" + contentTypeName + ", firstImage1="
				+ firstImage1 + ", addr1=" + addr1 + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
