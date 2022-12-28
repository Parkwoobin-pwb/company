package com.spring.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.spring.board.util.PagingHelper;

import com.spring.board.dto.NoticeDTO;
import com.spring.board.service.NoticeService;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


@Controller
public class NoticeContorller {
	
	@Autowired
	private NoticeService noticeservice;
	
	//22.07.18 우빈추가
	private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
	private final static String HANAMSI = "https://www.hanam.go.kr/www/";
	
	@Scheduled(cron="0 40 16 ? * MON-FRI")
	public void insertNotice() {
		
		try {
			
			String url = "https://www.hanam.go.kr/www/selectGosiList.do?key=172&not_ancmt_se_code=05&pageIndex=1";
			Connection conn =  Jsoup.connect(url).header("Content-Type", "application/json;charset=UTF-8")
					.userAgent(USER_AGENT)
					.method(Connection.Method.GET)
					.ignoreContentType(true);
			System.setProperty( "https.protocols", "TLSv1,TLSv1.1,TLSv1.2" );
			org.jsoup.nodes.Document doc = conn.get();
			org.jsoup.select.Elements hireList = doc.select(".subject a");
			
			for(int i =0; i < hireList.size(); i++) {
				
				Element ul = hireList.get(i);
				String ul2 = ul.getElementsByAttribute("href").attr("href").toString();//링크가져오기
				
				ul2 = ul2.substring(2,ul2.length());
				ul2 = ul2.replace("amp", "");
				ul2= HANAMSI+ul2; //링크값 수정
				
				
				conn =  Jsoup.connect(ul2).header("Content-Type", "application/json;charset=UTF-8")
						.userAgent(USER_AGENT)
						.method(Connection.Method.GET)
						.ignoreContentType(true);
				
				doc = conn.get();//링크오픈
				
				NoticeDTO noticedto = new NoticeDTO();
				String title = doc.select(".p-table__subject_text").text();//제목가져오기
				noticedto.setNtTitle(title);
				noticedto.setAdminGroupId("woobintest");
				noticedto.setAdminId("woobin");
				
				boolean isResult = noticeservice.noticeDuplicate(noticedto);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.getMessage();
				}
				
				if(isResult == true) {
					String admin = doc.select(".p-split").get(1).text();
					admin = admin.substring(4, admin.length());//작성자가져오기
					noticedto.setAdminId(admin);
					String content =  doc.select(".p-table__content").text();//콘텐츠가져오기
					noticedto.setNtContent(content);
					noticeservice.insertNotice(noticedto);
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		}
	
	
	@RequestMapping(value="/noticeList", method= {RequestMethod.GET, RequestMethod.POST})
	public String selectNoticeList(@ModelAttribute("parameterDTO") NoticeDTO parameterDTO,
			ModelMap model ) {
		
		parameterDTO.setAdminGroupId("woobintest");
		parameterDTO.setAdminId("''");
		List<NoticeDTO> noticeList = new ArrayList<NoticeDTO>();

		int totalCount = 0;
		try {
			
			parameterDTO.setFirstIndex((parameterDTO.getPageIndex()-1)*parameterDTO.getRecordCountPerPage());
			parameterDTO.setSearch_text(parameterDTO.getSearch_text());
			
			noticeList = noticeservice.selectNoticeList(parameterDTO);
			totalCount = noticeservice.selectListTotal(parameterDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		  model.addAttribute("resultList", noticeList);
		  model.addAttribute("totalCnt", totalCount);
		  model.addAttribute("pagingList", PagingHelper.paging(totalCount,parameterDTO.getPageIndex())); 
		  model.addAttribute("search_text",parameterDTO.getSearch_text()); 
		  model.addAttribute("parameterDTO",parameterDTO);
		 
		
		
		
		return "/notice/noticeList";
		
	}
	
	@RequestMapping(value="/noticeDetail.view")
	public String noticeDetail(String id, ModelMap model) {
		
		NoticeDTO noticeDTO = new NoticeDTO();
		try {
			noticeDTO.setNtId(id);
			noticeDTO = noticeservice.selectNoticeDetail(noticeDTO);
			
			model.addAttribute("resultVO", noticeDTO);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/notice/noticeDetail";
		
	}
	
}

