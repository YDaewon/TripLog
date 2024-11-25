package com.triplog.comment.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.triplog.comment.model.commentDto;
import com.triplog.comment.model.mapper.commentMapper;


@Service
public class commentServiceImpl implements commentService {

	private final commentMapper commentMapper;
	
	public commentServiceImpl(commentMapper commentMapper) {
		super();
		this.commentMapper = commentMapper;
	}

	@Override
	public List<commentDto> getComments(int articleNo) {
		return commentMapper.getComments(articleNo);
	}

	@Override
	public void createComment(commentDto commentDto) {
		commentMapper.createComment(commentDto);
	}

	@Override
	public void modifyComment(Long commentNo, String comment) {
		commentMapper.modifyComment(commentNo, comment);
	}

	@Override
	public void deleteComment(int commentNo) {
		commentMapper.deleteComment(commentNo);
	}

}
