package com.spring.board.dao;

import com.spring.board.dto.LogDTO;

public interface LogDAO {
	public void insertLog(LogDTO logDTO) throws Exception; 
}
