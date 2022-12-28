package com.spring.board.dao;

import java.util.List;

import com.spring.board.dto.NoticeDTO;

public interface NoticeDAO {
	
	public void insertNotice(NoticeDTO ndto) throws Exception;
	public boolean duplicate(NoticeDTO ndto) throws Exception;
	public List<NoticeDTO> selectList(NoticeDTO ndto)throws Exception;
	public int selectListTotal(NoticeDTO ndto) throws Exception;
	public NoticeDTO selectNoticeDetail(NoticeDTO ndto) throws Exception;

}
