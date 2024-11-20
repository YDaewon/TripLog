package com.triplog.plan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "PlanDto (여행계획 기본정보)", description = "여행계획의 제목, 설명을 저장하는 Domain Class")
public class PlanDto {
	@Schema(description = "여행계획 번호", example = "1")
	private int planNo;
	@Schema(description = "회원 번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	private int userNo;
	@Schema(description = "여행계획 제목", requiredMode = Schema.RequiredMode.REQUIRED, example = "겨울 제주여행")
	private String title;
	@Schema(description = "여행계획 설명", defaultValue = "", example = "겨울바다 구경하러 가자")
	private String description;
	@Schema(description = "여행계획 생성 일자", defaultValue = "CURRENT_TIMESTAMP", example = "2024-11-11 10:15:30")
	private String createdAt;
	@Schema(description = "여행계획 삭제 일자", example = "2024-11-30 10:15:30")
	private String deletedAt;
	@Schema(description = "계획 복사 횟수", defaultValue = "0",example = "15")
	private String forkCount;
	
	public PlanDto(int planNo, int userNo, String title, String description, String createdAt, String deletedAt,
			String forkCount) {
		super();
		this.planNo = planNo;
		this.userNo = userNo;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
		this.forkCount = forkCount;
	}
	
	public int getPlanNo() {
		return planNo;
	}
	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}
	public String getForkCount() {
		return forkCount;
	}
	public void setForkCount(String forkCount) {
		this.forkCount = forkCount;
	}
	@Override
	public String toString() {
		return "PlanDto [planNo=" + planNo + ", userNo=" + userNo + ", title=" + title + ", description=" + description
				+ ", createdAt=" + createdAt + ", deletedAt=" + deletedAt + ", forkCount=" + forkCount + "]";
	}
}
