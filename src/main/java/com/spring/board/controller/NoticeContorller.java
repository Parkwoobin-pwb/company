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

import com.spring.board.util.DateUtil;
import com.spring.board.util.PagingHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.spring.board.dto.NoticeDTO;
import com.spring.board.dto.WeatherVO;
import com.spring.board.service.NoticeService;
import com.spring.board.service.WeatherService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


@Controller
public class NoticeContorller {
	
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private NoticeService noticeservice;
	
	private String whoAmi;
	//22.07.18 우빈추가
	private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
	private final static String HANAMSI = "https://www.hanam.go.kr/www/";
	
	final String W_API_KEY = "8%2BkzaNmft6P0UiUrMV3HwOP%2Fzou7LP26y3j9E4lM9ObUxc5uWS%2FDlN78BL6Ot25GmGAaprxKsn7qFjRXwxQ35w%3D%3D"; 
	
	@Scheduled(cron="0 39 14 ? * MON-FRI")
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
				ul2 = HANAMSI+ul2; //링크값 수정
				
				
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
	
	
	
	//@Scheduled(cron="* 2 * * * *")
	@Scheduled(cron="0 11 16 * * *") 
		public void updateWeather(){
			whoAmi = Thread.currentThread().getStackTrace()[1].toString();
			String api_url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"; 
			 
			WeatherVO weatherVO = new WeatherVO();
			String today = dateUtil.getDateToday();
			String result = "";
			
			try {
				 final List<WeatherVO> resultList = (List<WeatherVO>)this.weatherService.selectWeatherList(weatherVO);
	     	 		for (final WeatherVO wthr : resultList) {
	     	 			weatherVO.setAdmin_id(wthr.getAdmin_id());
	     	 			weatherVO.setAdmin_group_id(wthr.getAdmin_group_id());
	     	 			//System.out.println("adminid="+ wthr.getAdmin_group_id());
	     	 			String wthr_nx = wthr.getWthr_la(); //
	     	 			String wthr_ny = wthr.getWthr_lo(); // 
	     	 			
	     	 			StringBuilder urlBuilder = new StringBuilder(api_url);
	     	 			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + W_API_KEY);
	     	 			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	     	 			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	     	 	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
	     	 	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*당일 발표*/
	     	 	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0800", "UTF-8")); /*08시 발표(정시단위) */
	     	 	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(wthr_nx, "UTF-8")); /*예보지점의 X 좌표값*/
	     	 	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(wthr_ny, "UTF-8")); /*예보지점의 Y 좌표값*/
	     	 	        URL url = new URL(urlBuilder.toString());
	     	 	       // System.out.println("url" + urlBuilder.toString());
	     	 	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     	 	        conn.setRequestMethod("GET");
	     	 	        conn.setRequestProperty("Content-type", "application/json");
	     	 	       // System.out.println("Response code: " + conn.getResponseCode());
	     	 	        
	     	 	        BufferedReader rd;
	     	 	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	     	 	        	rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	     	 	        } else {
	     	 	        	rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	     	 	        }
	     	 	        StringBuilder sb = new StringBuilder();
	     	 	        String line;
	     	 	        while ((line = rd.readLine()) != null) {
	     	 	        	sb.append(line);
	     	 	        }
	     	 	        rd.close();
	     	 	        conn.disconnect();
	     	 	        result = sb.toString();
	     	 	        
	     	 	      //  System.out.println(result);
	     	 	        
	     	 	        // response 키를 가지고 데이터를 파싱
	     	 	        //JSONObject jsonObj_1 = new JSONObject(result);
	     	 	        JSONObject jsonObj_1 = new JSONObject();
	     	 	        String response = jsonObj_1.getString("response");

	     	 	        // response 로 부터 body 찾기
	     	 	      //  JSONObject jsonObj_2 = new JSONObject(response);
	     	 	        JSONObject jsonObj_2 = new JSONObject();
	     	 	        String body = jsonObj_2.getString("body");

	     	 	        // body 로 부터 items 찾기
	     	 	        //JSONObject jsonObj_3 = new JSONObject(body);
	     	 	        JSONObject jsonObj_3 = new JSONObject();
	     	 	        String items = jsonObj_3.getString("items");
	     	 	       
	     	 	        // items로 부터 itemlist 를 받기 
	     	 	        //JSONObject jsonObj_4 = new JSONObject(items);
	     	 	        JSONObject jsonObj_4 = new JSONObject();
	     	 	        JSONArray jsonArray = jsonObj_4.getJSONArray("item");
	     	 	        
	     	 	        for(int i=0;i < jsonArray.size();i++){
	     	 	        	jsonObj_4 = jsonArray.getJSONObject(i);
	     	 	        	String fcstValue = jsonObj_4.getString("fcstValue");
	     	 	        	String category = jsonObj_4.getString("category");
	     	 	        	
	     	 	        	String temp = "";
	     	 	        	if(category.equals("TMP")) {
	     	 	        		temp = fcstValue;
	     	 	        		weatherVO.setCr_tmp(temp);//현재기온
	     	 	        	}
//	     	 	        	String maxTemp = "";
//	     	 	        	if(category.equals("TMX")) {
//	     	 	        		maxTemp = fcstValue;
//	     	 	        		
//	     	 	        	}
//	     	 	        	String minTemp = "";
//	     	 	        	if(category.equals("TMN")) {
//	     	 	        		minTemp = fcstValue;
//	     	 	        		
//	     	 	        	}
	     	 	        	
	     	 	        	String status = ""; 
	     	 	        	if(category.equals("SKY")){
	     	 	        		if(fcstValue.equals("1")) {
	     	 	        			status = "맑음";
	     	 	        		}else if(fcstValue.equals("2")) {
	     	 	        			status = "비";
	     	 	        		}else if(fcstValue.equals("3")) {
	     	 	        			status = "구름많음 ";
	     	 	        		}else if(fcstValue.equals("4")) {
	     	 	        			status = "흐림";
	     	 	        		}
	     	 	        		weatherVO.setWthr_cd(status); //날씨상태
	     	 	        	}//if sky
	     	 	        	
	     	 	        	String dust = "좋음";
	            			weatherVO.setDst_cd(dust);
	            			String dustsize = "30";
	            			weatherVO.setDst_vl(dustsize);
	            			
	            			this.weatherService.updateWeather(weatherVO);
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

