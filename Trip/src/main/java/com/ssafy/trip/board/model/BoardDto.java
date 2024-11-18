package com.ssafy.trip.board.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "BoardDto (게시글정보)", description = "게시글의 정보를 정의한 Domain Class")
public class BoardDto {

	@Schema(description = "게시글번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	private int articleNo;
	@Schema(description = "제목", example = "글 제목입니다")
	private String title;
	@Schema(description = "내용", example = "글 내용 입니다.")
	private String content;
	@Schema(description = "생성일", defaultValue = "현재시간")
	private String created_At;
	@Schema(description = "공지사항", defaultValue = "0")
	private int notice;
	@Schema(description = "작성자id", example = "ssafy")
	private String userNo;
	public BoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardDto(int articleNo, String userNo, String title, String content, String created_At, int notice) {
		super();
		this.articleNo = articleNo;
		this.userNo = userNo;
		this.title = title;
		this.content = content;
		this.created_At = created_At;
		this.notice = notice;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated_At() {
		return created_At;
	}
	public void setCreated_At(String created_At) {
		this.created_At = created_At;
	}
	public int getNotice() {
		return notice;
	}
	public void setNotice(int notice) {
		this.notice = notice;
	}
	@Override
	public String toString() {
		return "BoardDto [articleNo=" + articleNo + ", userNo=" + userNo + ", title=" + title + ", content=" + content
				+ ", created_At=" + created_At + ", notice=" + notice + "]";
	}
	
	
}
