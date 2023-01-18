package com.spring.board.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	
	public String getDateToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar cal = Calendar.getInstance();
		String curDate = sdf.format(cal.getTime());
		
		return curDate;
	}
	
	public String getCurrentDttm() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Calendar cal = Calendar.getInstance();
		String curDttm = sdf.format(cal.getTime());
		
		return curDttm;
	}
	
	public String getYearOfDate() {		
		return getDateToday().substring(0, 4);
	}
	
	public String getMonthOfDate() {		
		return getDateToday().substring(4, 6);
	}
	
	public String getTodayOfDate() {		
		return getDateToday().substring(6, 8);
	}
	
	public String getTodayOfStrDay() {
		Calendar cal = Calendar.getInstance();
		
		String[] week = {"일", "월", "화", "수", "목", "금", "토"};
		
		return week[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	public String getStartDateOfWeek(String date) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String parseDate = removeSL(date);
		Date tempDate = sdf.parse(parseDate);
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(tempDate);
		cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK) + 1);
		
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	public String getStartDateOfWeek() {
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK) + 1);
		
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	public String getEndDateOfWeek(String date) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String parseDate = removeSL(date);
		Date tempDate = sdf.parse(parseDate);
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(tempDate);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	public String getEndDateOfWeek() {
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK) + 1);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	public String getDifferenceDay(String date, int diff) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String parseDate = removeSL(date);
		Date tempDate = sdf.parse(parseDate);
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(tempDate);
		cal.add(Calendar.DAY_OF_MONTH, diff);
		
		
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	public String getDifferenceMonth(String date, int diff) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String parseDate = removeSL(date);
		Date tempDate = sdf.parse(parseDate);
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(tempDate);
		cal.add(Calendar.MONTH, diff);
		
		
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	public String removeSL(String str) {
		String remove = str.replace("-", "").replace(":", "").replace(" ", "");
		return remove;
	}
	
	public String getYearOfDate(String date) {
		String parseDate = removeSL(date);
		return parseDate.substring(0, 4);
	}
	
	public String getMonthOfDate(String date) {	
		String parseDate = removeSL(date);
		return parseDate.substring(4, 6);
	}
	
	public String getWeekOfMonth(String date) throws ParseException {	
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String parseDate = removeSL(date);
		Date tempDate = sdf.parse(parseDate);
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(tempDate);
				
		return String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
	}
	
	public String getMaxDateOfMonth(String date) throws ParseException {	
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(getYearOfDate(date)), Integer.parseInt(getMonthOfDate(date)) - 1, 1);
		
		return getYearOfDate(date) + getMonthOfDate(date) + (String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH)).length() == 1 ? "0" + cal.getActualMaximum(Calendar.DAY_OF_MONTH) : cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	}
	
	public String getTodayOfDate(String date) {
		String parseDate = removeSL(date);
		return parseDate.substring(6, 8);
	}
	
	public String getTodayOfStrDay(String date) throws ParseException {	
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String parseDate = removeSL(date);
		Date tempDate = sdf.parse(parseDate);
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(tempDate);
		
		String[] week = {"일", "월", "화", "수", "목", "금", "토"};
		
		return week[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	public String getTimeFormat(String time) {
		String parseTime = time;
		
		if (time.length() == 4) {
			parseTime = time.substring(0, 2) + ":" + time.substring(2, 4);
		}
		
		return parseTime;
	}
	
	public ArrayList<String> getDaysOfMonth(String date) throws ParseException {
		ArrayList<String> days = new ArrayList<String>();
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String parseDate = removeSL(date);
		parseDate = parseDate.substring(0, 6) + "01";
		Date tempDate = sdf.parse(parseDate);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(tempDate);
		
		int firstDayOfMonth = 0;
		int lastDayOfMonth = 0;
		
		firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK);
		lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for (int i = 0; i < firstDayOfMonth - 1; i++) {
			days.add("");
		}
		
		for (int i = 1;  i <= lastDayOfMonth; i++) {
			days.add(String.valueOf(i));
		}
		
		int modDays = (7 - (days.size() % 7));
		
		if (days.size() % 7 != 0) {
			for (int i=0; i<modDays; i++) {
				days.add("");
			}
		}
		
		return days;
	}
	
	public String addDateToday(int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		
		String result = sdf.format(cal.getTime());
		
		return result;
	}

}
