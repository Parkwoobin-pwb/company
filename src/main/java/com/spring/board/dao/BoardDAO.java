package com.spring.board.dao;

import java.util.List;
import java.util.Map;

import com.spring.board.dto.BoardDTO;

public interface BoardDAO {
	
	public List<BoardDTO> getSearchBoard(Map<String, Object> searchInfo) throws Exception;
	public int getAllBoardCount(Map<String, String> searchCountInfo) throws Exception;
	public BoardDTO selectOne(int num) throws Exception;
	public void increaseReadCount(int num) throws Exception;
	public void insert(BoardDTO bdto) throws Exception;
	public void insertReplyBoard(BoardDTO bdto) throws Exception;
	public void update(BoardDTO bdto) throws Exception;
	public void delete(int num) throws Exception;
	public void updateBoardReplyStep(BoardDTO bdto) throws Exception;
	public BoardDTO validateUserCheck(BoardDTO bdto) throws Exception;
	public void makeDummyData(List<BoardDTO> dataLists)throws Exception;
	public int checkWriter(String writer) throws Exception;
	public List<BoardDTO> selectBoardList(Map<String, Object> condMap) throws Exception;
	public List<BoardDTO> selectBoardListMain()throws Exception;
	
	
}
