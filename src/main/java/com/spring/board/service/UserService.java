package com.spring.board.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.spring.board.dto.UserDTO;



public interface UserService {
		public UserDTO login(Map<String, String> loginMap) throws DataAccessException;
		public void insertUser(UserDTO udto) throws Exception;
		public int checkId(String id)throws Exception;
}
