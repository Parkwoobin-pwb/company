package com.spring.board.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spring.board.dao.BoardDAO;
import com.spring.board.dto.BoardDTO;
@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO boardDAO;

	@Override
	public void insertBoard(BoardDTO bdto) throws Exception {
		boardDAO.insert(bdto);
		
	}

	@Override
	public List<BoardDTO> getSearchBoard(Map<String, Object> searchBoard) throws Exception {
		
		return boardDAO.getSearchBoard(searchBoard);
	}

	@Override
	public BoardDTO selectOne(int num) throws Exception {
		boardDAO.increaseReadCount(num);
		return boardDAO.selectOne(num);
	}

	@Override
	public boolean update(BoardDTO bdto) throws Exception {
		boolean isSuccess = false;
		if(boardDAO.validateUserCheck(bdto)!=null) {
			boardDAO.update(bdto);
			isSuccess = true;
		}
		System.out.println("service result : "+isSuccess);
		return isSuccess;
	}

	@Override
	public boolean delete(BoardDTO bdto) throws Exception {
		boolean isSuccess = false;
		if(boardDAO.validateUserCheck(bdto)!=null) {
			boardDAO.delete(bdto.getNum());
			isSuccess = true;
		}
		return isSuccess;
	}

	@Override
	public int getAllBoardCount(Map<String, String> searchCountInfo) throws Exception {
		
		
		return boardDAO.getAllBoardCount(searchCountInfo);
	}

	@Override
	public void makeDummyData() throws Exception {
	
		Random ran = new Random();//난수함수생성
		
		List<BoardDTO> dummyDataList = new ArrayList<BoardDTO>();//더미데이터 담을 리스트 생성
		
		String[] array = {"가","나","다","라","마","바","사","아","자","차","카","파","하"};//더미재료 생성
		
		String writer;
		String email;
		String password;
		String subject;
		String address;
		String content;
		
		BoardDTO temp = null;
		
		//데이터 생성 for문
		for(int i = 100; i< 300; i++) {
			
			writer = "";
			password ="1234";
			subject = "";
			email = "";
			address = "";
			content ="";
			
			for(int j = 0; j < 7; j++) {
				writer  += array[ran.nextInt(array.length)];
				subject += array[ran.nextInt(array.length)];
				content += array[ran.nextInt(array.length)];
				address += array[ran.nextInt(array.length)];
				if(j < 4) {
					email += array[ran.nextInt(array.length)];
				}
			}
			email +="@naver.com";
			
			temp = new BoardDTO();
			
			temp.setNum(i);
			temp.setWriter(writer);
			temp.setEmail(email);
			temp.setSubject(subject);
			temp.setPassword(password);
			temp.setRef(i);
			temp.setReLevel(1);
			temp.setReStep(1);
			temp.setReadCount(0);
			temp.setAddress(address);
			temp.setContent(content);
			
			dummyDataList.add(temp);
		}
		
		boardDAO.makeDummyData(dummyDataList);
		
	}

	@Override
	public int checkWriter(String writer) throws Exception {
		int checkCount = 0;
		try {
			checkCount = boardDAO.checkWriter(writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkCount;
	}

	@Override
	public List<BoardDTO> selectBoardList(Map<String, Object> condMap) throws Exception {
		
		return boardDAO.selectBoardList(condMap);
	}

	@Override
	public void insertReplyBoard(BoardDTO bdto) throws Exception {
		
		   
		  boardDAO.updateBoardReplyStep(bdto);
		  
		  
		  boardDAO.insertReplyBoard(bdto);
		 
		
	}

	@Override
	public List<BoardDTO> selectBoarListMain() throws Exception {
		
		return boardDAO.selectBoardListMain();
	}




}
