package com.spring.board.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class BoardDTO {
	
	private int num;
	private String writer;
	private String email;
	private String subject;
	private String password;
	private Date regDate;
	private int ref;
	private int reStep;
	private int reLevel;
	private int readCount;
	private String address;
	private String addressDetail;
	private String content;
	
	
	
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getReStep() {
		return reStep;
	}
	public void setReStep(int reStep) {
		this.reStep = reStep;
	}
	public int getReLevel() {
		return reLevel;
	}
	public void setReLevel(int reLevel) {
		this.reLevel = reLevel;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}