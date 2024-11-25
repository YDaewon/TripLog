package com.triplog.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class commentDto {
	private int commentNo;
	private int articleNo;
	private int userNo;
	private String author;
	private String authorImage;
	private String content;
	private int parent;
	private String createAt;
	private String deleteAt;
}
