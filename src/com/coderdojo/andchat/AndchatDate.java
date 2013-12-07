package com.coderdojo.andchat;

public class AndchatDate {
<<<<<<< HEAD
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String seconds;
	
	
	public AndchatDate(String second2, String minute2, String hour2, String year2, String month2, String day2) {
		year = year2;
		month = month2;
	}
	
	public String getDateTime() {
		return getYear() + "/" + getMonth() + "/" + getDay() + " " + getHours() + ":" + getMinutes();
=======
	
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
>>>>>>> 75c6b0e2043a820054e78cfbb5a4924c1d8e992e
	}
	
	public String getYear() {
		return year;
	}
<<<<<<< HEAD
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
=======
	
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
	
>>>>>>> 75c6b0e2043a820054e78cfbb5a4924c1d8e992e
}
