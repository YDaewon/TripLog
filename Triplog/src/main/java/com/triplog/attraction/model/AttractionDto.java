package com.triplog.attraction.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "AttractionDto (관광지정보)", description = "관광지 정보를 정의한 Class")
public class AttractionDto {
	
	@Schema(description = "관광지 번호", example = "3819")
	private int attractionNo;
	@Schema(description = "콘텐츠 ID", example = "2763807")
	private int contentId;
	@Schema(description = "관광지 이름", example = "간데메공원")
	private String title;
	@Schema(description = "콘텐츠 유형", example = "12")
	private int contentTypeId;
	@Schema(description = "지역 코드", example = "1")
	private int areaCode;
	@Schema(description = "시군구 코드", example = "11")
	private int siGunGuCode;
	@Schema(description = "첫 번째 이미지 URL", example = "http://example.com/image1.jpg")
	private String firstImage1;
	@Schema(description = "두 번째 이미지 URL", example = "http://example.com/image2.jpg")
	private String firstImage2;
	@Schema(description = "지도 레벨", example = "6")
	private int mapLevel;
	@Schema(description = "위도", example = "37.57285200320000000")
	private double latitude;
	@Schema(description = "경도", example = "127.04909774270000000")
	private double longitude;
	@Schema(description = "전화번호", example = "02-123-4567")
	private String tel;
	@Schema(description = "주소 1", example = "서울특별시 동대문구 서울시립대로2길 59")
	private String addr1;
	@Schema(description = "주소 2", example = "(답십리동)")
	private String addr2;
	@Schema(description = "홈페이지 URL", example = "http://example.com")
	private String homepage;
	@Schema(description = "관광지 개요", example = "이곳은 역사적인 장소입니다.")
	private String overview;
	@Schema(description = "관광지 조회수", example = "0")
	private int hitCount;
}
