package com.coderdojo.andchat;

public class AndchatDate {
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String seconds;
	
	
	public String getTime()
	{   // 14:14
		return hour + ":" + minute;
	}
	
	public String getDate(){
		// 2013-12-07
		return year + "-" + month + "-"  + day;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getSeconds() {
		return seconds;
	}
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
}
