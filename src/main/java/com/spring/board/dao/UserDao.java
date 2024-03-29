package com.spring.board.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.board.dto.UserDTO;



public interface UserDao {
	
	
	public UserDTO login(Map<String, String> loginMap)throws DataAccessException;
	public void insert(UserDTO udto) throws Exception;
	public int chekckId(String id) throws Exception;
	public List<UserDTO> userHistory(Map<String, Object> condMap) throws DataAccessException;
	public UserDTO userDetail(String userId) throws Exception;
	public List<UserDTO> userExcell(Map<String,String> map)throws Exception;
	public void updateMyInfo(Map<String, String> map) throws Exception;
	
}
