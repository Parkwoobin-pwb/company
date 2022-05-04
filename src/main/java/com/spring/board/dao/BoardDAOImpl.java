package com.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.dto.BoardDTO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	private SqlSession sqlsession;
	
	@Override
	public List<BoardDTO> getSearchBoard(Map<String, Object> searchInfo) throws Exception {
		
		return sqlsession.selectList("com.spring.mapper.BoardMapper.getSearchBoard", searchInfo);
	}
	
	@Override
	public int getAllBoardCount(Map<String, String> searchCountInfo) throws Exception {
		
		return sqlsession.selectOne("com.spring.mapper.BoardMapper.getAllBoardCount", searchCountInfo);
	}

	@Override
	public void insert(BoardDTO bdto) throws Exception {
		sqlsession.insert("com.spring.mapper.BoardMapper.insertBoard", bdto);
		
	}


	@Override
	public BoardDTO selectOne(int num) throws Exception {
		
		return sqlsession.selectOne("com.spring.mapper.BoardMapper.getOneBoard", num);
	}

	@Override
	public BoardDTO validateUserCheck(BoardDTO bdto) throws Exception {
		return sqlsession.selectOne("com.spring.mapper.BoardMapper.validateUserCheck", bdto);
	}

	@Override
	public void increaseReadCount(int num) throws Exception {
		sqlsession.update("com.spring.mapper.BoardMapper.increaseReadCount", num);
		
	}

	@Override
	public void update(BoardDTO bdto) throws Exception {
		sqlsession.update("com.spring.mapper.BoardMapper.updateBoard", bdto);
		
	}

	@Override
	public void delete(int num) throws Exception {
		sqlsession.delete("com.spring.mapper.BoardMapper.deleteBoard", num);
		
	}


	@Override
	public void makeDummyData(List<BoardDTO> dataLists) throws Exception {
		sqlsession.insert("com.spring.mapper.BoardMapper.makeDummyData", dataLists);
		
	}

	@Override
	public int checkWriter(String writer) {
		int checkCount = 0;
		try {
			checkCount = sqlsession.selectOne("com.spring.mapper.BoardMapper.checkWriter", writer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkCount;
	}

	@Override
	public List<BoardDTO> selectBoardList(Map<String, Object> condMap) throws Exception {

		return sqlsession.selectList("com.spring.mapper.BoardMapper.selectBoardList", condMap);
	}

	@Override
	public void insertReplyBoard(BoardDTO bdto) throws Exception {
		
		
		sqlsession.insert("com.spring.mapper.BoardMapper.insertReplyBoard",bdto);
	
		
	}

	@Override
	public void updateBoardReplyStep(BoardDTO bdto) throws Exception {
		
		sqlsession.update("com.spring.mapper.BoardMapper.updateBoardReplyStep", bdto);
		
	}

	@Override
	public List<BoardDTO> selectBoardListMain() throws Exception {
		
		return sqlsession.selectList("com.spring.mapper.BoardMapper.getAllboardList");
	}


	
	
	
}
