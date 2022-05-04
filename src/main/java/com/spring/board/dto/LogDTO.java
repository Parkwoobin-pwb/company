package com.spring.board.dto;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class LogDTO {
		private int logPk;
		private Date reserveDate;
		private String logIp;
		
		
		public int getLogPk() {
			return logPk;
		}
		public void setLogPk(int logPk) {
			
			this.logPk = logPk;
		}
	
		public Date getReserveDate() {
			return reserveDate;
		}
		public void setReserveDate(Date reserveDate) {
			this.reserveDate = reserveDate;
		}
		public String getLogIp() {
			return logIp;
		}
		public void setLogIp(String logIp) {
			this.logIp = logIp;
		}
		
		
}
