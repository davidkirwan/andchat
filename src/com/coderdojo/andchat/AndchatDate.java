package com.coderdojo.andchat;

import android.text.format.Time;


public class AndchatDate {

	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	
	
	public AndchatDate() {
		Time now = new Time();
		now.setToNow();
		
		this.second = Integer.toString(now.second);
		this.minute = Integer.toString(now.minute);
		this.hour = Integer.toString(now.hour);
		this.day = Integer.toString(now.weekDay);
		this.month = Integer.toString(now.month);
		this.year = Integer.toString(now.year);
	}
	
	private Time getDateTime() {
		Time theDate = new Time();
		
		theDate.set(Integer.parseInt(this.second), 
							Integer.parseInt(this.minute), 
							Integer.parseInt(this.hour), 
							Integer.parseInt(this.day), 
							Integer.parseInt(this.month), 
							Integer.parseInt(this.year));
		
		return theDate;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	public void setMonth(String month) {
		this.month = month;
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
	public String getMonth() {
		return month;
	}
	
	public String getDay() {
		return day;
	}
	
	public String getSecond() {
		return second;
	}
}
