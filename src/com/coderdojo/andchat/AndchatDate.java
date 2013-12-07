package com.coderdojo.andchat;

public class AndchatDate {
	
	private String year;
	private String month;
	private String day;
	
	private String minute;
	private String hour;
	private String second;
	
	public AndchatDate(String second2, String minute2, String hour2, String year2, String month2, String day2) {
		year = year2;
		month = month2;
		day = day2;
		minute = minute2;
		hour = hour2;
		second = second2;
	}
	
	public String getDateTime() {
		return getYear() + "/" + getMonth() + "/" + getDay() + " " + getHours() + ":" + getMinutes();
	}
	
	public String getYear() {
		return year;
	}
	
	public String getMonth() {
		return month;
	}
	
	public String getDay() {
		return day;
	}
	
	public String getMinutes() {
		return minute;
	}
	
	public String getHours() {
		return hour;
	}
	
	public String getSeconds() {
		return second;
	}
	
}
