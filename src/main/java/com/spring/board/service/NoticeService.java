package com.spring.board.service;

import java.util.List;

import com.spring.board.dto.NoticeDTO;

public interface NoticeService {
	public void insertNotice(NoticeDTO ndto) throws Exception;
	public boolean noticeDuplicate(NoticeDTO ndto) throws Exception;
	public List<NoticeDTO> selectNoticeList(NoticeDTO ndto)throws Exception;
	public int selectListTotal(NoticeDTO ndto) throws Exception;
	public NoticeDTO selectNoticeDetail(NoticeDTO ndto) throws Exception;
}
