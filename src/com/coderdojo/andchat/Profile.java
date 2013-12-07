package com.coderdojo.andchat;


public class Profile {
	
	private int idOfUser;
	private String name;
	private AndchatDate joinDate;
	private String bio;
	
	public Profile(int idOfUser2, String name2, AndchatDate joinDate2, String bio2) {
		idOfUser = idOfUser2;
		name = name2;
		joinDate = joinDate2;
		bio = bio2;
	}
	
	public int getUserID() {
		return idOfUser;
	}
	
	public String getUserName() {
		return name;
	}
	
	public AndchatDate getJoinDate() {
		return joinDate;
	}
	
	public String getBio() {
		return bio;
	}
	
	public void setBio(String string) {
		bio = string;
	}
	
}
