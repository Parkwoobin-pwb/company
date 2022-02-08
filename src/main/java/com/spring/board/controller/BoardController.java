package com.spring.board.controller;

import java.lang.ProcessBuilder.Redirect;
import java.text.SimpleDateFormat;
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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.Session;
import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.UserDTO;
import com.spring.board.service.BoardService;
import com.spring.board.service.UserService;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.core.io.FileSystemResource;

@Controller
public class BoardController {
	
	@Autowired
	private JavaMailSenderImpl mailsender;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDTO userDTO;

	@RequestMapping(value = "/", method = RequestMethod.GET)
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

		System.out.println("====================================");
		System.out.println("startPage : " + startPage);
		System.out.println("endPage : " + endPage);
		System.out.println("totalBoardCount : " + totalBoardCount);
		System.out.println("onePageViewCount : " + onePageViewCount);
		System.out.println("currentPageNumber : " + currentPageNumber);
		System.out.println("searchKeyword : " + searchKeyword);
		System.out.println("searchWord : " + searchWord);
		System.out.println("====================================\n");

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
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String joinForm(UserDTO udto) throws Exception{
		userService.insertUser(udto);
		System.out.println(udto.getMemberName());
		return "redirect:/login";
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
		userDTO = userService.login(loginMap);
		
		HttpSession session = request.getSession();
		if(userDTO != null) {
			session.setAttribute("isLogin", true);
			session.setAttribute("memberInfo", userDTO);
			System.out.println("login");
			mv.setViewName("redirect:/boardList");
		}else {
			mv.addObject("message","로그인실패");
			mv.setViewName("redirect:/login");
			System.out.println("loginFail");
			
		}
		return mv;
		
		
		
	}
	

}
