package com.triplog.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
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
	@Schema(description = "이메일 ID", example = "ssafy")
	private String emailId;
	@Schema(description = "이메일 Domain", example = "gmail.com")
	private String emailDomain;
	@Schema(description = "생성 시간", example = "2024-11-19 16:30:48")
	private String createdAt;
	@Schema(description = "삭제 시간", example = "2024-11-19 16:30:48")
	private String deletedAt;
	private String token;
	
}
