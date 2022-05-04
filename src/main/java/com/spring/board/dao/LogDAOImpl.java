package com.spring.board.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.dto.LogDTO;


@Repository
public class LogDAOImpl implements LogDAO {
	
	//저번에 이거만들때 @Autowired 이거 안써서 3시간 헤맴 기억하자
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertLog(LogDTO logDTO) throws Exception {
		System.out.println("DAO");
		sqlSession.insert("com.spring.mapper.logMapper.insertLog", logDTO);
		
	}

}
