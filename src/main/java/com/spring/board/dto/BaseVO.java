package com.spring.board.dto;

import java.io.Serializable;

public class BaseVO implements Serializable, Comparable<Object>{
	
	
	private static final long serialVersionUID = -1736373917026657021L;

	private int pageIndex = 1;					//현재페이지
	
	private int pageUnit = 10;					//페이지갯수
	
	private int total = 1;						//페이지사이즈
	
	private int firstIndex = 0;					//첫페이지 인덱스
	
	private int lastIndex = 1;					//마지막페이지 인덱스
	
	private int prev = 1;						//이전페이지
	
	private int next = 2;						//다음페이지
	
	private int recordCountPerPage = 10;		//페이지당 레코드갯수
	
	private int rownum;							//줄번호
	
	private String search_text;				//검색어
	
	private String start_date;				//검색시작일
	
	private String end_date;				//검색종료일
	
	private String search_type;				//검색타입
	
	private String movie_real_name;
	
	
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public String getSearch_text() {
		return search_text;
	}

	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getSearch_type() {
		return search_type;
	}

	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}

	public String getMovie_real_name() {
		return movie_real_name;
	}

	public void setMovie_real_name(String movie_real_name) {
		this.movie_real_name = movie_real_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
