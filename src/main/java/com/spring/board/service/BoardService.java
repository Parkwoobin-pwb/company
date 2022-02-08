package com.spring.board.service;

import java.util.List;
import java.util.Map;

import com.spring.board.dto.BoardDTO;

public interface BoardService {
	
	public List<BoardDTO> getSearchBoard(Map<String, Object> searchBoard) throws Exception;
	public int getAllBoardCount(Map<String, String> searchCountInfo) throws Exception;
	public BoardDTO selectOne(int num) throws Exception;
	public void insertBoard(BoardDTO bdto) throws Exception;
	public void insertReplyBoard(BoardDTO bdto) throws Exception;
	public boolean update(BoardDTO bdto) throws Exception;
	public boolean delete(BoardDTO bdto) throws Exception;
	public int checkWriter(String writer) throws Exception;
	public void makeDummyData() throws Exception;
	public List<BoardDTO> selectBoardList(Map<String, Object>condMap) throws Exception;
	public List<BoardDTO> selectBoarListMain()throws Exception;
	
}
