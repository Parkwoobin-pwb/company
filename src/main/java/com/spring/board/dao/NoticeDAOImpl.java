package com.spring.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.dto.NoticeDTO;


@Repository
public class NoticeDAOImpl implements NoticeDAO{
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public void insertNotice(NoticeDTO ndto) throws Exception {
		sqlSession.insert("com.spring.mapper.noticeMapper.insertNotice", ndto);
		
	}

	@Override
	public boolean duplicate(NoticeDTO ndto) throws Exception {
		
		boolean isResult = false;
		int result = 0;
		result = sqlSession.selectOne("com.spring.mapper.noticeMapper.noticeDuplicate", ndto);
		
		if(result == 0) {
			isResult = true;
		}else {
			isResult = false;
		}
		
		return isResult;
	}

	@Override
	public List<NoticeDTO> selectList(NoticeDTO ndto) throws Exception {
		
		return sqlSession.selectList("com.spring.mapper.noticeMapper.selectNoticeList", ndto);
	}

	@Override
	public int selectListTotal(NoticeDTO ndto) throws Exception {
		int totalCnt = 0;
		totalCnt = sqlSession.selectOne("com.spring.mapper.noticeMapper.selectNoticeListTotal", ndto);
		return totalCnt;
	}

	@Override
	public NoticeDTO selectNoticeDetail(NoticeDTO ndto) throws Exception {
		
		return sqlSession.selectOne("com.spring.mapper.noticeMapper.selectNoticeDetail", ndto);
	}

}
