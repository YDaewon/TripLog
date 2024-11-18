package com.ssafy.trip.board.model.mapper;

import java.util.List;

import com.ssafy.trip.board.model.BoardDto;

public interface BoardMapper {
	void createBoard(BoardDto boardDto);
	List<BoardDto> listAll();
	BoardDto getBoard(int articleNo);
	void modifyBoard(BoardDto boardDto);
	void deleteBoard(int articleNo);
	void createNotice(BoardDto boardDto);
	List<BoardDto> listNotice();
}
