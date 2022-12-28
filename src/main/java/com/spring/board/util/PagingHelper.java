package com.spring.board.util;

import com.spring.board.dto.BaseVO;

public class PagingHelper {
	public static String paging(int totalRowSize, int targetPage) {
		BaseVO baseVO = new BaseVO();
		return paging(totalRowSize, baseVO.getRecordCountPerPage(), targetPage, baseVO.getPageUnit(), "fn_paging");
	}
	
	public static String paging(int totalRowSize, int targetPage, String sortIdx) {
		BaseVO baseVO = new BaseVO();
		return paging(totalRowSize, baseVO.getRecordCountPerPage(), targetPage, baseVO.getPageUnit(), "fn_paging", sortIdx);
	}
	
	public static String koreaPaging(int page){
		StringBuffer returnValue = new StringBuffer();
		
		if(page > 1){
			returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:fn_searchList('"+(page-1)+"');return false;\" class=\"btFirst2\">이전</a></span>");
		}else{
			returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:alert('첫페이지 입니다.');return false;\" class=\"btFirst2\">처음</a></span>");
		}
		returnValue.append("<a href=\"#\" onclick=\"javascript:fn_searchList('1');return false;\">처음</a>");
		returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:fn_searchList('" + (page+1) + "');return false;\" class=\"btLast2\">마지막</a></span>");
		
		return returnValue.toString();
	}
	
	private static String paging(int totalRowSize, int rowSize, int targetPage, int pageAdminSize, String fn) {
		
		if(totalRowSize==0)
			return "";
		 
        StringBuffer returnValue = new StringBuffer();

        int lastIndex = 0;
		if(totalRowSize%rowSize == 0){
			lastIndex = totalRowSize/rowSize;
		}else{
			lastIndex = totalRowSize/rowSize + 1;
		}
		if(lastIndex == 0){
			lastIndex = 1;
		}
		
        int totalPageSize = (totalRowSize / rowSize);
        if ((totalRowSize % rowSize) > 0)
               totalPageSize++;

        int startPage = ((targetPage - 1) / pageAdminSize) * pageAdminSize + 1;
        int endPage = startPage + pageAdminSize - 1;
        if (endPage > totalPageSize)
               endPage = totalPageSize;

        if (totalPageSize == 0)
               targetPage = 0;

        if (targetPage > pageAdminSize) {
               int prePage = ((targetPage / pageAdminSize) - 1) * pageAdminSize + 1;
               returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:fn_searchList('1');return false;\" class=\"btFirst\">처음</a><a href=\"#\" onclick=\"javascript:fn_searchList('" + prePage + "');return false;\" class=\"btPrev\">이전</a></span>");
        } else {
               returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:fn_searchList('1');return false;\" class=\"btFirst\">처음</a><a href=\"#\" class=\"btPrev\">이전</a></span>");
        }

        for (int i = startPage; i <= endPage; i++) {
               if (i == targetPage) {
                      returnValue.append("<strong>"+i+"</strong>");
               } else {
                      returnValue.append("<a href=\"#\" onclick=\"javascript:fn_searchList('"+i+"');return false;\">"+i+"</a>");
               }
        }

        if (endPage < totalPageSize) {
               int nextPage = endPage + 1;
               returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:fn_searchList('" + nextPage + "');return false;\" class=\"btNext\">다음</a><a href=\"#\" onclick=\"javascript:fn_searchList('" + lastIndex + "');return false;\" class=\"btLast\">마지막</a></span>");
        } else {
               returnValue.append("<span class=\"btgp\"><a href=\"#\" class=\"btNext\">다음</a><a href=\"#\" onclick=\"javascript:fn_searchList('" + lastIndex + "');return false;\" class=\"btLast\">마지막</a></span>");
        }

        return returnValue.toString();
	}
	
	private static String paging(int totalRowSize, int rowSize, int targetPage, int pageAdminSize, String fn, String sortIdx) {
		
		if(totalRowSize==0)
			return "";
		 
        StringBuffer returnValue = new StringBuffer();

        int lastIndex = 0;
		if(totalRowSize%rowSize == 0){
			lastIndex = totalRowSize/rowSize;
		}else{
			lastIndex = totalRowSize/rowSize + 1;
		}
		if(lastIndex == 0){
			lastIndex = 1;
		}
		
        int totalPageSize = (totalRowSize / rowSize);
        if ((totalRowSize % rowSize) > 0)
               totalPageSize++;

        int startPage = ((targetPage - 1) / pageAdminSize) * pageAdminSize + 1;
        int endPage = startPage + pageAdminSize - 1;
        if (endPage > totalPageSize)
               endPage = totalPageSize;

        if (totalPageSize == 0)
               targetPage = 0;

        if (targetPage > pageAdminSize) {
               int prePage = ((targetPage / pageAdminSize) - 1) * pageAdminSize + 1;
               returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:fn_searchList('1', '"+ sortIdx +"');return false;\" class=\"btFirst\">처음</a><a href=\"#\" onclick=\"javascript:fn_searchList('" + prePage + "');return false;\" class=\"btPrev\">이전</a></span>");
        } else {
               returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:fn_searchList('1', '"+ sortIdx +"');return false;\" class=\"btFirst\">처음</a><a href=\"#\" class=\"btPrev\">이전</a></span>");
        }

        for (int i = startPage; i <= endPage; i++) {
               if (i == targetPage) {
                      returnValue.append("<strong>"+i+"</strong>");
               } else {
                      returnValue.append("<a href=\"#\" onclick=\"javascript:fn_searchList('"+i+"', '"+ sortIdx +"');return false;\">"+i+"</a>");
               }
        }

        if (endPage < totalPageSize) {
               int nextPage = endPage + 1;
               returnValue.append("<span class=\"btgp\"><a href=\"#\" onclick=\"javascript:fn_searchList('" + nextPage + "', '"+ sortIdx +"');return false;\" class=\"btNext\">다음</a><a href=\"#\" onclick=\"javascript:fn_searchList('" + lastIndex + "', '"+ sortIdx +"');return false;\" class=\"btLast\">마지막</a></span>");
        } else {
               returnValue.append("<span class=\"btgp\"><a href=\"#\" class=\"btNext\">다음</a><a href=\"#\" onclick=\"javascript:fn_searchList('" + lastIndex + "', '"+ sortIdx +"');return false;\" class=\"btLast\">마지막</a></span>");
        }

        return returnValue.toString();
  	}
	
	public static int getLastIndex(int total){
		BaseVO baseVO = new BaseVO();
		int lastIndex = 0;
		if(total%baseVO.getRecordCountPerPage() == 0){
			lastIndex = total/baseVO.getRecordCountPerPage();
		}else{
			lastIndex = total/baseVO.getRecordCountPerPage() + 1;
		}
		if(lastIndex == 0){
			lastIndex = 1;
		}
		
		return lastIndex;
	}
}
