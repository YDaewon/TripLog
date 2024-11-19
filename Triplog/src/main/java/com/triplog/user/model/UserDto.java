package com.triplog.user.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "UserDto (회원정보)", description = "회원 정보를 가진 Domain Class")
public class UserDto {
	@Schema(description = "회원번호", requiredMode = Schema.RequiredMode.REQUIRED)
	private int userNo;
	@Schema(description = "회원아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "ssafy")
	private String userId;
	@Schema(description = "회원비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "ssafy")
	private String userPwd;
	@Schema(description = "회원이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "김싸피")
	private String userName;
	@Schema(description = "프로필사진", example = "img/defaultImage.png")
	private String userImage;
	@Schema(description = "닉네임(미기입시 회원이름 사용)", example = "대복이")
	private String nickname;
	@Schema(description = "관리자", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
	private int admin;
	@Schema(description = "이메일 ID", example = "ssafy")
	private String emailId;
	@Schema(description = "이메일 Domain", example = "gmail.com")
	private String emailDomain;
	@Schema(description = "생성 시간", example = "2024-11-19 16:30:48")
	private String createdAt;
	@Schema(description = "삭제 시간", example = "2024-11-19 16:30:48")
	private String deletedAt;
	@Schema(description = "JWT 엑세스 토큰")
	private String Token;
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDto(int userNo, String userId, String userPwd, String userName, String userImage, String nickname,
			int admin, String emailId, String emailDomain, String createdAt, String deletedAt, String token) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.userImage = userImage;
		this.nickname = nickname;
		this.admin = admin;
		this.emailId = emailId;
		this.emailDomain = emailDomain;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
		Token = token;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmailDomain() {
		return emailDomain;
	}
	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
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
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	@Override
	public String toString() {
		return "UserDto [userNo=" + userNo + ", userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName
				+ ", userImage=" + userImage + ", nickname=" + nickname + ", admin=" + admin + ", emailId=" + emailId
				+ ", emailDomain=" + emailDomain + ", createdAt=" + createdAt + ", deletedAt=" + deletedAt + ", Token="
				+ Token + "]";
	}
	
	
}
