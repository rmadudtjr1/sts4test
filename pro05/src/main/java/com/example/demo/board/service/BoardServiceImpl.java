package com.example.demo.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.board.dao.BoardDAO;
import com.example.demo.board.dto.BoardDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardDAO boardDAO;
	
	@Override
	public List<BoardDTO> boardList() {
		// TODO Auto-generated method stub
		return boardDAO.boardList();
	}

	@Override
	public void insertBoard(BoardDTO dto) {
		// TODO Auto-generated method stub
		boardDAO.insertBoard(dto);
	}

	@Override
	public BoardDTO getBoard(int articleNo) {
		// TODO Auto-generated method stub
		BoardDTO board = boardDAO.getBoard(articleNo);
		boardDAO.updateCnt(board);
		return board;
	}

	@Override
	public void updateBoard(BoardDTO dto) {
		// TODO Auto-generated method stub
		boardDAO.updateBoard(dto);
	}

	@Override
	public int deleteBoard(int articleNo) {
		// TODO Auto-generated method stub
		return boardDAO.deleteBoard(articleNo);
	}

}
