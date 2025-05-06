package com.example.demo.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.board.dto.BoardDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class BoardDAOImpl implements BoardDAO{

	@PersistenceContext
    private EntityManager em;
	
	public List<BoardDTO> boardList(){
		return em.createQuery("SELECT b FROM board b", BoardDTO.class).getResultList();
	}

	@Override
	public void insertBoard(BoardDTO dto) {
		// TODO Auto-generated method stub
		em.persist(dto);
	}

	@Override
	public BoardDTO getBoard(int articleNo) {
		// TODO Auto-generated method stub
		return em.find(BoardDTO.class, articleNo);
	}

	@Override
	public void updateCnt(BoardDTO board) {
		// TODO Auto-generated method stub
		board.setCnt(board.getCnt()+1);
		em.merge(board);
	}

	@Override
	public void updateBoard(BoardDTO dto) {
		// TODO Auto-generated method stub
		em.merge(dto);
	}

	@Override
	public int deleteBoard(int articleNo) {
		// TODO Auto-generated method stub
	    String jpql = "DELETE FROM board b WHERE b.articleNo = :articleNo";
	    return em.createQuery(jpql)
	             .setParameter("articleNo", articleNo)
	             .executeUpdate();
	}

}
