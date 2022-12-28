package com.spring.board.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//엑셀 워크시트 
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.MvcNamespaceHandler;

import com.spring.board.dto.CommonUtil;
import com.spring.board.dto.UserDTO;
import com.spring.board.service.BoardService;
import com.spring.board.service.UserService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/goodsInsert")
	public ModelAndView goodsInsert(HttpServletRequest request)throws Exception{
		UserDTO userDTO = (UserDTO) request.getSession().getAttribute("userDTO");
		ModelAndView mv = new ModelAndView();
		String type = userDTO.getUserType();
		if(type.equals("A")) {
			mv.setViewName("goods/AddGoods");
			return mv;
		}
		mv.setViewName("redirect:/boardList");
		return mv;
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
	
	@RequestMapping(value="memberExport")
	public void memberListExport(HttpServletResponse response, @RequestParam Map<String, Object> dateMap) throws Exception{
		SimpleDateFormat fileSdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy_MM_dd");
		String makeFileTime = fileSdf.format(new Date());
		String makeFileName = makeFileTime + "_MemberList.xls";
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = null;
		Cell cell = null;
		
		int rowNo = 0;
		
		//테이블 헤더용 스타일
		CellStyle headStyle = wb.createCellStyle();
		//가는 경계선
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		
		
		//노란색 배경
		headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		//가운데 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		
		//데이터용 경계 스타일 테두리만 지정
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setAlignment(HorizontalAlignment.CENTER);
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		
		//헤더 생성
		row = sheet.createRow(rowNo++);
		cell = row.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("MEMBER_ID");
		cell = row.createCell(1);
		cell.setCellStyle(headStyle);
		cell.setCellValue("MEMBER_NAME");
		cell = row.createCell(2);
		cell.setCellStyle(headStyle);
		cell.setCellValue("MEMBER_ADDRESS");
		cell = row.createCell(3);
		cell.setCellStyle(headStyle);
		cell.setCellValue("MEMBER_EMAIL");
		cell = row.createCell(4);
		cell.setCellStyle(headStyle);
		cell.setCellValue("MEMBER_PHONE");
		
		cell = row.createCell(5);
		cell.setCellStyle(headStyle);
		cell.setCellValue("MEMBER_REGDATE");
		cell = row.createCell(6);
		cell.setCellStyle(headStyle);
		cell.setCellValue("MEMBER_TYPE");
		Map<String,String> map = new HashMap<String, String>();
		
		for(UserDTO userDTO : userService.userExcell(map) ) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(userDTO.getMemberId());
			cell = row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(userDTO.getMemberName());
			cell = row.createCell(2);
			cell.setCellStyle(bodyStyle);
			String address = "";
			address = userDTO.getRoadAddress() + userDTO.getJibunAddress()+userDTO.getNamujiAddress();
			cell.setCellValue(address);
			cell = row.createCell(3);
			cell.setCellStyle(bodyStyle);
			String email = "";
			email = userDTO.getEmail1() + "@"+userDTO.getEmail2() + userDTO.getEmail3();
			cell.setCellValue(email);
			cell = row.createCell(4);
			cell.setCellStyle(bodyStyle);
			String phone ="";
			phone = userDTO.getHp1() +"-"+ userDTO.getHp2() +"-"+ userDTO.getHp3();
			cell.setCellValue(phone);
			cell = row.createCell(5);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(dateSdf.format(userDTO.getRegDate()));
			cell = row.createCell(6);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(userDTO.getUserType());

			
		}
		
		for(int i = 0; i < userService.userExcell(map).size(); i++) {
			sheet.autoSizeColumn(i);
		}
		
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+makeFileName);
		
		wb.write(response.getOutputStream());
		wb.close();
	}
}
