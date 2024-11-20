package com.triplog.article.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(title = "ArticleDto (게시글정보)", description = "게시글의 정보를 정의한 Domain Class")
public class ArticleDto {
	@Schema(description = "게시글번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	private int articleNo;
	@Schema(description = "링크된 플랜의 번호", example = "1")
	private int planNo;
	@Schema(description = "작성자id 번호", example = "2")
	private int userNo;
	@Schema(description = "작성자 닉네임", example = "대복이")
	private String author;
	@Schema(description = "제목", example = "글 제목입니다")
	private String title;
	@Schema(description = "내용", example = "글 내용 입니다.")
	private String content;
	@Schema(description = "생성일", defaultValue = "현재시간")
	private String createdAt;
	@Schema(description = "삭제일", defaultValue = "현재시간")
	private String deletedAt;
	@Schema(description = "즐겨찾기", defaultValue = "0")
	private int stars;
	@Schema(description = "조회수", defaultValue = "0")
	private int hitCount;

}
