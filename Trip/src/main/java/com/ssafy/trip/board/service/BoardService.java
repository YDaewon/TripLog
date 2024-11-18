package com.ssafy.trip.board.service;

import java.util.List;

import com.ssafy.trip.board.model.BoardDto;

public interface BoardService {
	void createBoard(BoardDto boardDto);
	List<BoardDto> listAll();
	BoardDto getBoard(int articleNo);
	void modifyBoard(BoardDto boardDto);
	void deleteBoard(int articleNo);
	public List<BoardDto> listNotice();
	
	//ADMIN
	public void createNotice(BoardDto boardDto);

}
