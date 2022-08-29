package com.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.board.dto.UserDTO;



@Repository
public class UserDaoImpl implements UserDao {
	
	
	@Autowired
	private SqlSession sqlSession;
	

	@Override
	public void insert(UserDTO udto) throws Exception {
		sqlSession.insert("com.spring.mapper.userMapper.insertUser", udto);
		
	}


	@Override
	public int chekckId(String id) throws Exception {
		int count = 0;
		count = sqlSession.selectOne("com.spring.mapper.userMapper.checkId", id);			
		
		
		return count;
	}


	@Override
	public UserDTO login(Map<String, String> loginMap) throws DataAccessException {
		
		return sqlSession.selectOne("com.spring.mapper.userMapper.login", loginMap);
	}


	@Override
	public List<UserDTO> userHistory(Map<String, Object> condMap) throws DataAccessException {
		
		return sqlSession.selectList("com.spring.mapper.userMapper.selectUserJoinHistory", condMap);
	}


	@Override
	public UserDTO userDetail(String userId) throws Exception {
		
		return sqlSession.selectOne("com.spring.mapper.userMapper.selectOneUser", userId);
	}

}
