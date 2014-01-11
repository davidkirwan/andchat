package com.coderdojo.andchat;


public class AndchatUserProfile {
	
	private long idOfUser;
	private String name;
	private AndchatDate joinDate;
	private String metaData;
	private String bio;
	
	public AndchatUserProfile(long idOfUser2, String name2, AndchatDate joinDate2, String bio2) {
		idOfUser = idOfUser2;
		name = name2;
		joinDate = joinDate2;
		bio = bio2;
	}
	
	public long getUserID() {
		return idOfUser;
	}
	
	public void setUserName(String name)
	{
		this.name = name;
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

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}
	
}
