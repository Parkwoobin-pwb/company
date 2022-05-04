package com.spring.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.LogDAO;
import com.spring.board.dto.LogDTO;


@Service
public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogDAO logdao;

	@Override
	public void insertLog(LogDTO logDto) throws Exception {
		
		logdao.insertLog(logDto);
		System.out.println("service");
		
	}

}
