package com.spring.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.spring.board.dao.UserDao;
import com.spring.board.dto.UserDTO;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	

	@Override
	public void insertUser(UserDTO udto) throws Exception {
		
		userDao.insert(udto);
		
	}


	@Override
	public int checkId(String id) throws Exception {
		int count = 0;
		
		try {			
			count=userDao.chekckId(id);
			System.out.println("service"+count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


	@Override
	public UserDTO login(Map<String, String> loginMap) throws DataAccessException {
		
		UserDTO user = userDao.login(loginMap);
		
		if(user!=null) {
			
			String inputPassword = loginMap.get("memberPw");
			String dbPassword = user.getMemberPw();
			
			if(dbPassword.equals(inputPassword)) {
				return user;
			}
			
			
		}
		
		return null;
	}


	@Override
	public List<UserDTO> userHistory(Map<String, Object> condMap) throws DataAccessException {
		System.out.println("service"+condMap.get("beginDate"));
		System.out.println("service1"+condMap.get("endDate"));
		System.out.println("service2"+condMap.get("search_word"));
		System.out.println("service2"+condMap.get("search_type"));
		return userDao.userHistory(condMap);
	}

}
