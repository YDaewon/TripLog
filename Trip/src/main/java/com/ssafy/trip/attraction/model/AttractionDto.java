package com.ssafy.trip.attraction.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "AttractionDto (관광지정보)", description = "관광지 정보를 정의한 Class")
public class AttractionDto {
	
	@Schema(description = "관광지 번호", example = "3819")
	private int no;
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

	public AttractionDto() {
		super();
	}
	
	public AttractionDto(int no, int contentId, String title, int contentTypeId, int areaCode, int siGunGuCode,
			String firstImage1, String firstImage2, int mapLevel, double latitude, double longitude, String tel,
			String addr1, String addr2, String homepage, String overview) {
		super();
		this.no = no;
		this.contentId = contentId;
		this.title = title;
		this.contentTypeId = contentTypeId;
		this.areaCode = areaCode;
		this.siGunGuCode = siGunGuCode;
		this.firstImage1 = firstImage1;
		this.firstImage2 = firstImage2;
		this.mapLevel = mapLevel;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tel = tel;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.homepage = homepage;
		this.overview = overview;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public int getSiGunGuCode() {
		return siGunGuCode;
	}

	public void setSiGunGuCode(int siGunGuCode) {
		this.siGunGuCode = siGunGuCode;
	}

	public String getFirstImage1() {
		return firstImage1;
	}

	public void setFirstImage1(String firstImage1) {
		this.firstImage1 = firstImage1;
	}

	public String getFirstImage2() {
		return firstImage2;
	}

	public void setFirstImage2(String firstImage2) {
		this.firstImage2 = firstImage2;
	}

	public int getMapLevel() {
		return mapLevel;
	}

	public void setMapLevel(int mapLevel) {
		this.mapLevel = mapLevel;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "AttractionDto [no=" + no + ", contentId=" + contentId + ", title=" + title + ", contentTypeId="
				+ contentTypeId + ", areaCode=" + areaCode + ", siGunGuCode=" + siGunGuCode + ", firstImage1="
				+ firstImage1 + ", firstImage2=" + firstImage2 + ", mapLevel=" + mapLevel + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", tel=" + tel + ", addr1=" + addr1 + ", addr2=" + addr2 + ", homepage="
				+ homepage + ", overview=" + overview + "]";
	}
}
