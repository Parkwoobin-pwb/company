package com.spring.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.NoticeDAO;
import com.spring.board.dto.NoticeDTO;


@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDAO noticedao;

	@Override
	public void insertNotice(NoticeDTO ndto) throws Exception {
		
		noticedao.insertNotice(ndto);
	}

	@Override
	public boolean noticeDuplicate(NoticeDTO ndto) throws Exception {
		boolean isResult = false;
		
		isResult = noticedao.duplicate(ndto);
		
		return isResult;
	}

	@Override
	public List<NoticeDTO> selectNoticeList(NoticeDTO ndto) throws Exception {
		
		return noticedao.selectList(ndto);
	}

	@Override
	public int selectListTotal(NoticeDTO ndto) throws Exception {
		
		return noticedao.selectListTotal(ndto);
	}

	@Override
	public NoticeDTO selectNoticeDetail(NoticeDTO ndto) throws Exception {
		
		return noticedao.selectNoticeDetail(ndto);
	}

}
