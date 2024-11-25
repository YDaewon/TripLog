package com.triplog.comment.service;

import java.util.List;

import com.triplog.comment.model.commentDto;

public interface commentService {
	List<commentDto> getComments(int articleNo);
	void createComment(commentDto commentDto);
	void modifyComment(Long commentNo, String comment);
	void deleteComment(int commentNo);
}
