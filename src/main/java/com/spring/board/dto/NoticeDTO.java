package com.spring.board.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class NoticeDTO extends BaseVO{
	private String ntId;
	private String adminGroupId;
	private String adminId;
	private String ntTitle;
	private String ntContent;
	private Date ntRegdate;
	

	public String getNtId() {
		return ntId;
	}
	public void setNtId(String ntId) {
		this.ntId = ntId;
	}
	public String getAdminGroupId() {
		return adminGroupId;
	}
	public void setAdminGroupId(String adminGroupId) {
		this.adminGroupId = adminGroupId;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getNtTitle() {
		return ntTitle;
	}
	public void setNtTitle(String ntTitle) {
		this.ntTitle = ntTitle;
	}
	public String getNtContent() {
		return ntContent;
	}
	public void setNtContent(String ntContent) {
		this.ntContent = ntContent;
	}
	public Date getNtRegdate() {
		return ntRegdate;
	}
	public void setNtRegdate(Date ntRegdate) {
		this.ntRegdate = ntRegdate;
	}
	
	
	
}
