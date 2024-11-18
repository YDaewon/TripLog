package com.ssafy.trip.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	public void createBoard(BoardDto boardDto) {
		boardMapper.createBoard(boardDto);
		
	}

	@Override
	public List<BoardDto> listAll() {
		return boardMapper.listAll();
	}

	@Override
	public BoardDto getBoard(int articleNo) {
		return boardMapper.getBoard(articleNo);
	}

	@Override
	public void modifyBoard(BoardDto boardDto) {
		boardMapper.modifyBoard(boardDto);
	}

	@Override
	public void deleteBoard(int articleNo) {
		boardMapper.deleteBoard(articleNo);
	}

	@Override
	public void createNotice(BoardDto boardDto) {
		boardMapper.createNotice(boardDto);
	}
	
	@Override
	public List<BoardDto> listNotice() {
		return boardMapper.listNotice();
	}
}
