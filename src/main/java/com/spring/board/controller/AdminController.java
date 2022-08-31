package com.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView goodsInsert()throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("goods/AddGoods");
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
}
