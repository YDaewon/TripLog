package com.triplog.comment.service;

import java.util.List;

import com.triplog.comment.model.commentDto;

public interface commentService {
	List<commentDto> getComments(int articleNo);
	void createComment(commentDto commentDto);
	void modifyComment(commentDto commentDto);
	void deleteComment(int commentNo);
}
