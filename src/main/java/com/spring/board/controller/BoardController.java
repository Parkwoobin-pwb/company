package com.spring.board.controller;

import java.lang.ProcessBuilder.Redirect;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.mysql.cj.Session;
import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.CommonUtil;
import com.spring.board.dto.LogDTO;
import com.spring.board.dto.UserDTO;
import com.spring.board.service.BoardService;
import com.spring.board.service.LogService;
import com.spring.board.service.UserService;
import com.sprong.board.util.SHA256Util;
import com.sprong.board.util.StringUtil;

import javax.el.PropertyNotFoundException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class BoardController {
	
	@Autowired
	private JavaMailSenderImpl mailsender;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserDTO userDTO;

	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String main(Model model) throws Exception {
		
		//날씨 크롤링
		String url = "https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&sq=&o=&q=%EC%98%A4%EB%8A%98%EB%82%A0%EC%94%A8";
		Document doc = Jsoup.connect(url).get();		
		
		
		
		Elements e5 = doc.getElementsByAttributeValue("class","txt_temp");
		Elements yesterday = doc.getElementsByAttributeValue("class", "txt_desc");
		Elements nongdo = doc.getElementsByAttributeValue("class", "dl_weather");
		Elements img = doc.getElementsByTag("img");

		Element today = e5.get(2);
		String todayWeather = today.text();//태그에 감싸져있는 값들을 가져오는.text함수
		
		
		
		String yesterDay = yesterday.text();
		System.out.println(yesterday);//어제날씨
		
		
		String misemeonji = nongdo.text();
		System.out.println(misemeonji);//풍속,습도,미세먼지 농도
		
		
		String url2 = "http://www.dangjin.go.kr/xml_clone/BBSMSTR_000000000013/BBSMSTR_000000000013.xml";
		Document doc2 = Jsoup.connect(url2).get();
		
		Elements title = doc2.getElementsByAttributeValue("class", "title");
		System.out.println("dfdfdfdfdfd"+title);
		
		LogDTO logDto = new LogDTO();
		try {
				
			    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			    String ip = req.getHeader("X-FORWARDED-FOR");
			    if (ip == null) {
			    	ip = req.getRemoteAddr();
			    	
			    	
			    }
			    
			    logDto.setLogIp(ip);
			    
			    
			    	
			    logService.insertLog(logDto);
			    
			    System.out.println("iptest : "+ip);
			   
			    UrlPathHelper urlPathHelper = new UrlPathHelper();
			    String originalURL = urlPathHelper.getOriginatingRequestUri(req);
			    
			    String params = req.getQueryString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		
		Element img2 = img.get(0);
		
		List<BoardDTO> boardList = boardService.selectBoarListMain();
		
		
		
		
		model.addAttribute("img", img2);
		model.addAttribute("today", todayWeather);
		model.addAttribute("yesterDay", yesterDay);
		model.addAttribute("misemeonji", misemeonji);
		model.addAttribute("resultList", boardList);
		return "board/main";
	}

	/*
	 * @RequestMapping(value="/") public ModelAndView main()throws Exception{ return
	 * new ModelAndView("redirect:/board/main"); }
	 */

	@RequestMapping(value = "/boardWrite", method = RequestMethod.GET)
	public String boardWrite() throws Exception {

		return "board/bWrite";
	}

	@RequestMapping(value = "/boardWrite", method = RequestMethod.POST)
	public String boardWrite(BoardDTO bdto) throws Exception {
		boardService.insertBoard(bdto);
		return "redirect:/boardList";
	}
	
	
	@RequestMapping(value = "/boardReplyWrite" , method = RequestMethod.GET)
	public String boardReplyWrite(@RequestParam("num") int num , Model model) throws Exception{
		model.addAttribute("bdto", boardService.selectOne(num));
		return "board/bReply";
	}
	
	
	
	@RequestMapping(value = "/boardReplyWrite" , method = RequestMethod.POST)
	public String boardReplyWrite(BoardDTO bdto) throws Exception{
	
		boardService.insertReplyBoard(bdto);
		return "redirect:/boardList";	
	}
	

	@RequestMapping(value = "/boardList")
	public String boardList(@RequestParam(name = "onePageViewCount", defaultValue = "10") int onePageViewCount,
			@RequestParam(name = "currentPageNumber", defaultValue = "1") int currentPageNumber,
			@RequestParam(name = "searchKeyword", defaultValue = "total") String searchKeyword,
			@RequestParam(name = "searchWord", defaultValue = "") String searchWord, Model model) throws Exception {
		
		
		try {
			
			
			int startBoardIdx = (currentPageNumber - 1) * onePageViewCount;
	
			// 관련 정보 MAP생성 (한페이지에 보여줄 게시글 숫자, 시작페이지의 인덱스, 검색키워드, 검색어)
			Map<String, Object> searchInfo = new HashMap<String, Object>();
			searchInfo.put("onePageViewCount", onePageViewCount);
			searchInfo.put("startBoardIdx", startBoardIdx);
			searchInfo.put("searchKeyword", searchKeyword);
			searchInfo.put("searchWord", searchWord);
	
			System.out.println("searchInfo" + searchInfo);
	
			List<BoardDTO> boardList = boardService.getSearchBoard(searchInfo);
	
			// 게시글의 전체 개수를 반환하는 관련 정보 MAP생성(검색키워드, 검색어)
	
			Map<String, String> searchCountInfo = new HashMap<String, String>();
			searchCountInfo.put("searchKeyword", searchKeyword);
			searchCountInfo.put("searchWord", searchWord);
	
			// 전체페이개수 = 전체게시글수 / 한페이지에서 보여지는 글 수
			int totalBoardCount = boardService.getAllBoardCount(searchCountInfo);
			int addPage = totalBoardCount % onePageViewCount == 0 ? 0 : 1; // 나머지가 0이면 추가x, 나머지가 0이 아니면 +1 페이지 처리
			int totalPageCount = totalBoardCount / onePageViewCount + addPage;
	
			// 시작 페이지
			int startPage = 1;
			if (currentPageNumber % 10 == 0)
				startPage = (currentPageNumber / 10 - 1) * 10 + 1;
			else
				startPage = (currentPageNumber / 10) * 10 + 1;
	
			// 끝페이지
			int endPage = startPage + 9;
	
	//		// 끝페이지가 전체 페이지 개수보다 크다면 
			if (endPage > totalPageCount) {
				endPage = totalPageCount;
			}
	//		
	//		// 게시물이 한페이지에 보여지는 것보다 작다면
			if (onePageViewCount > totalBoardCount) {
				startPage = 1;
				endPage = 0;
			}
			
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("totalBoardCount", totalBoardCount);
			model.addAttribute("onePageViewCount", onePageViewCount);
			model.addAttribute("currentPageNumber", currentPageNumber);
			model.addAttribute("searchKeyword", searchKeyword);
			model.addAttribute("searchWord", searchWord);
			model.addAttribute("boardList", boardList);
			
			
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}
	

		return "board/bList";

	}

	@RequestMapping(value = "/boardInfo")
	public String boardInfo(@RequestParam("num") int num, Model model) throws Exception {
		BoardDTO bdto = boardService.selectOne(num);
		model.addAttribute("bdto", bdto);
		return "board/bInfo";
	}

	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdate(@RequestParam("num") int num, Model model) throws Exception {
		BoardDTO bdto = boardService.selectOne(num);
		model.addAttribute("bdto", bdto);
		return "board/bUpdate";
	}

	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdate(BoardDTO bdto, Model model) throws Exception {
		boolean isSuccess = boardService.update(bdto);
		if (isSuccess)
			model.addAttribute("success", true);
		else
			model.addAttribute("success", false);
		return "board/bUpdatePro";
	}

	@RequestMapping(value = "/boardDelete", method = RequestMethod.GET)
	public String boardDelete(@RequestParam("num") int num, Model model) throws Exception {
		BoardDTO bdto = boardService.selectOne(num);
		model.addAttribute("bdto", bdto);
		return "board/bDelete";

	}

	@RequestMapping(value = "/boardDelete", method = RequestMethod.POST)
	public String boardDelete(BoardDTO bdto, Model model) throws Exception {

		boolean isSuccess = boardService.delete(bdto);
		if (isSuccess)
			model.addAttribute("success", true);
		else
			model.addAttribute("success", false);
		return "board/bDeletePro";
	}

	@RequestMapping(value = "/makeDummyData")
	public String makeDummyData() throws Exception {
		boardService.makeDummyData();
		return "redirect:/";
	}

	/*
	 * 박우빈 답글기능 구현 중 실패....
	 * 
	 * @RequestMapping(value="/boardReplyWrite", method = RequestMethod.GET) public
	 * String boardReplyWrite(@RequestParam("num")int num, Model model) throws
	 * Exception{
	 * 
	 * BoardDTO bdto = boardService.selectOne(num); model.addAttribute("bdto",
	 * bdto); return "board/bReply"; }
	 */

	/*
	 * @RequestMapping(value="/checkWriter.json",method={RequestMethod.POST,
	 * RequestMethod.GET})
	 * 
	 * @ResponseBody public ModelAndView checkWriter(@ModelAttribute("bdto")BoardDTO
	 * bdto)throws Exception{
	 * 
	 * ModelAndView modelAndview = new ModelAndView("jsonView");
	 * 
	 * try { int checkcount = 0; checkcount = boardService.checkWriter(bdto);
	 * 
	 * System.out.println("checkcount : "+checkcount);
	 * 
	 * modelAndview.addObject("result","success");
	 * modelAndview.addObject("checkcount", checkcount);
	 * modelAndview.setViewName("board/bWrite");
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * modelAndview.setViewName("board/bWrite"); System.out.println("catch : ");
	 * modelAndview.addObject("result", "fail");
	 * modelAndview.addObject("message","�슂泥� �떎�뙣�뻽�뒿�땲�떎. �떞�떦�옄�뿉寃� 臾몄쓽�빐二쇱꽭�슂."
	 * );
	 * 
	 * 
	 * }
	 * 
	 * return modelAndview; }
	 */
	
	@RequestMapping(value="/checkWriter", method={RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf-8")
	@ResponseBody
	public String writerCheck(HttpServletRequest request) throws Exception{
		String writer = request.getParameter("writer");
		System.out.println(writer);
		int result = boardService.checkWriter(writer);
		System.out.println("controller  : " + result);
		
		
		return Integer.toString(result);
	}
	
	@RequestMapping(value="excelPort")
	public void BoardListExport(HttpServletResponse response, @RequestParam Map<String, Object> dateMap)throws Exception{
		SimpleDateFormat fileSdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy_MM_dd");
		String makeFileTime = fileSdf.format(new Date());
		String makeFileName = makeFileTime + "_BoardList.xls";
		
		//워크북 생성
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = null;
		Cell cell = null;
		
		int rowNo = 0;
		
		// 테이블 헤더용 스타일
		CellStyle headStyle = wb.createCellStyle();
		//가는 경계선
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		
		//노란색배경
		headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		//가운데 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		
		//데이터용 경계 스타일 테두리만 지정
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setAlignment(HorizontalAlignment.CENTER);
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
			
		
		//헤더 생성
		row = sheet.createRow(rowNo++); 
		cell = row.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("넘버");
		cell = row.createCell(1);
		cell.setCellStyle(headStyle);
		cell.setCellValue("작성자");
		cell = row.createCell(2);
		cell.setCellStyle(headStyle);
		cell.setCellValue("이메일");
		cell = row.createCell(3);
		cell.setCellStyle(headStyle);
		cell.setCellValue("제목");
		cell = row.createCell(4);
		cell.setCellStyle(headStyle);
		cell.setCellValue("날짜");
		cell = row.createCell(5);
		cell.setCellStyle(headStyle);
		cell.setCellValue("내용");
		
		Map<String, Object> condMap = new HashMap<String, Object>();
		
		
		for(BoardDTO boardDTO : boardService.selectBoardList(condMap)) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(boardDTO.getNum());
			cell = row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(boardDTO.getWriter());
			cell = row.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(boardDTO.getEmail());
			cell = row.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(boardDTO.getSubject());
			cell = row.createCell(4);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(dateSdf.format(boardDTO.getRegDate()));
			cell = row.createCell(5);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(boardDTO.getContent());
			
		}
		
		for(int i =0; i < boardService.selectBoardList(condMap).size(); i++) {
			sheet.autoSizeColumn(i);
		}
		
		
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+makeFileName);
		//엑셀출력
		wb.write(response.getOutputStream());
		wb.close();
		
	}
	
	@RequestMapping(value = "/sendEmail")
	public String sendEmail(BoardDTO bdto)throws Exception{
		System.out.println("불렸는지 확인");
		try {
			MimeMessage message = mailsender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setFrom("dnqlsvkxld@gmai.com");
			messageHelper.setTo(bdto.getEmail());
			messageHelper.setTo(bdto.getSubject());
			messageHelper.setText(bdto.getContent());
			
			mailsender.send(message);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "redirect:/board/bInfo";
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView loginForm() throws Exception{
		return new ModelAndView("board/bLogin");
		
	}
	
	
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public ModelAndView joinForm() throws Exception{
		return new ModelAndView("board/bJoin");
		
	}
	
	@RequestMapping(value="/join2", method = RequestMethod.POST)
	public String joinForm(UserDTO udto) throws Exception{
		//암복호화 진행
		
		String password = udto.getMemberPw();
		password = StringUtil.encryptPassword(password);
		udto.setMemberPw(password);
		
		userService.insertUser(udto);
		System.out.println(udto.getMemberName());
		System.out.println(udto.getMemberPw());
		return "redirect:/login";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public ResponseEntity<String> joinForm2(@ModelAttribute UserDTO udto, 
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		//암복호화 진행
		
		String password = udto.getMemberPw();
		password = StringUtil.encryptPassword(password);
		udto.setMemberPw(password);
		
		//response.setContentType("text/html; charset=UTF-8");
		//request.setCharacterEncoding("utf-8");
		String message = "";
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		try {
			
			userService.insertUser(udto);
			message  = "<script>";
			message +=" alert('회원가입되었습니다.');";
			message += " location.href='"+request.getContextPath()+"/login';";
			message += " </script>";
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/checkId", method={RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf-8")
	@ResponseBody
	public String writerId(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		int result = userService.checkId(id);
		System.out.println("controller  : " + result);
		
		
		return Integer.toString(result);
	}
	
	@RequestMapping(value="/login.do", method= RequestMethod.POST)
	public ModelAndView login(@RequestParam Map<String, String>loginMap, HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		UserDTO userDTO = new UserDTO();
		//먼저 loginMap 수정해야함
		String password = loginMap.get("memberPw");
		
		
		try {
			password = StringUtil.encryptPassword(password);
			loginMap.put("memberPw", password);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			
		}
		
		userDTO = userService.login(loginMap);
		if(userDTO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("isLogin",true);
			session.setAttribute("userDTO",userDTO);
			mv.setViewName("redirect:/boardList");
			
		}else {
			mv.addObject("message","false");
			mv.setViewName("redirect:/login");
			
		}
		
		
		
		return mv;
		
	}
	
	@RequestMapping(value="/logout.do" , method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView(); //보여질화면 세팅
		HttpSession session = request.getSession(); //세션에 현재로그인정보 입력
		
		session.setAttribute("isLogin", false); //현재로그인 정보를 폴스로반환
		session.removeAttribute("userDTO"); //세션정보를 삭제
		
		mv.setViewName("redirect:/boardList"); //보여질화면 정의
		
		return mv; //화면 리턴
	
	}
	
	
	
	@RequestMapping(value="/header.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String header(Model model, HttpServletRequest request) {
		
		return "/common/header";
		
	}
	
	
	
	@RequestMapping(value="/userHistory", method = RequestMethod.GET)
	public ModelAndView userHistory(@RequestParam Map<String, String> dataMap) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/user/history");
		
		String fixedSearchPeriod = dataMap.get("fixedSearchPeriod");
		String search_type = dataMap.get("search_type");
		String search_word = dataMap.get("search_word");
		String beginDate = "";
		String endDate = "";
		String[] tempDate;
		
		if(dataMap.get("beginDate") == null && dataMap.get("endDate")==null) {
			
			tempDate = commonUtil.calcSearchPeriod(fixedSearchPeriod).split(",");
			beginDate = tempDate[0];
			endDate = tempDate[1];
		}else {
			beginDate = dataMap.get("beginDate");
			endDate = dataMap.get("endDate");
			
		}
		/*
		 * if(search_word == "" ) { search_word = null; }
		 * 
		 * if(search_type != "") { search_type = null; }
		 */
		
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("beginDate", beginDate);
		condMap.put("endDate", endDate);
		condMap.put("search_type", search_type);
		condMap.put("search_word", search_word);
		
		
		
		List<UserDTO> userList = userService.userHistory(condMap);
		
		String beginDate1[] = beginDate.split("-");
		String endDate1[] = endDate.split("-");
		
		mv.addObject("beginYear", beginDate1[0]);
		mv.addObject("beginMonth", beginDate1[1]);
		mv.addObject("beginDay", beginDate1[2]);
		mv.addObject("endYear", endDate1[0]);
		mv.addObject("endMonth", endDate1[1]);
		mv.addObject("endDay", endDate1[2]);
		mv.addObject("userList", userList);
		
		return mv;

		
	}
	/*
	@RequestMapping(value = "/boardInfo")
	public String boardInfo2(@RequestParam("num") int num, Model model) throws Exception {
		BoardDTO bdto = boardService.selectOne(num);
		model.addAttribute("bdto", bdto);
		return "board/bInfo";
	}*/
	
	@RequestMapping("/myPageMain.do")
	public ModelAndView userDetail(@RequestParam(required = false, value="message")String message, HttpServletRequest request) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		
		session = request.getSession();
		UserDTO userDTO = new UserDTO();
		userDTO = (UserDTO)session.getAttribute("userDTO");
		String userId = null;
		if(userDTO!=null) userId = userDTO.getMemberId();
		mv.addObject("memberInfo", userDTO);
		mv.setViewName("/user/detail");
		
		
		
		return mv;
		
		
	}
	
	
	@RequestMapping(value="modifyMyInfo.do", method = RequestMethod.POST)
	public ResponseEntity<String> modiftInfo(@RequestParam() String attribute,
			@RequestParam() String value,
			HttpServletRequest request) throws Exception{
				
		Map<String,String> memberMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		UserDTO userDTO = new UserDTO();
		userDTO = (UserDTO) session.getAttribute("userDTO");
		
		String val[] = null;
		String userId= userDTO.getMemberId();
		
		if(attribute.equals("memberPw")) {
			memberMap.put("memberPw", StringUtil.encryptPassword(value));
		}
		else if(attribute.equals("memberBirth")){
			
			val = value.split(",");
			memberMap.put("memberBirthY", val[0]);
			memberMap.put("memberBirthM", val[1]);
			memberMap.put("memberBirthD", val[2]);
			

		}
		else if(attribute.equals("tel")) {
			val = value.split(",");
			memberMap.put("tel1", val[0]);
			memberMap.put("tel2", val[1]);
			memberMap.put("tel3", val[3]);
		}
		
		else if(attribute.equals("hp")) {
			val = value.split(",");
			memberMap.put("hp1", val[0]);
			memberMap.put("hp2", val[1]);
			memberMap.put("hp3", val[3]);
		}
		else if(attribute.equals("email")) {
			val = value.split(",");
			memberMap.put("email1", val[0]);
			memberMap.put("email2", val[1]);
			memberMap.put("email3", val[2]);
		}
		else if(attribute.equals("address")) {
			val = value.split(",");
			memberMap.put("roadAddress", val[0]);
			memberMap.put("jibunAddress", val[1]);
			memberMap.put("namujiAddress", val[2]);
		}
		else {
			memberMap.put(attribute, value);
		}
		
		memberMap.put("memberId", userId);
		
		userDTO = (UserDTO)userService.updateMyInfo(memberMap);
		session.removeAttribute("memberInfo");//기존 세션 삭제
		session.setAttribute("memberInfo", userDTO);
		
		return new ResponseEntity<String>("modSuccess", new HttpHeaders(), HttpStatus.OK);
		
		
		
	}
			
	

}
