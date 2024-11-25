package com.triplog.comment.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.triplog.comment.model.commentDto;

@Mapper
public interface commentMapper {
	List<commentDto> getComments(int articleNo);
	void createComment(commentDto commentDto);
	void modifyComment(Long commentNo, String comment);
	void deleteComment(int commentNo);
}
