package com.coderdojo.andchat;


public class AndchatUserProfile {
	
	private long idOfUser;
	private String name;
	private AndchatDate joinDate;
	private String metaData;
	private String bio;
	
	
	public AndchatUserProfile(){
		this.idOfUser = 0l;
		this.name = "";
		this.joinDate = new AndchatDate();
		this.metaData = "";
		this.bio = "";
	}
	
	public AndchatUserProfile(long newIdOfUser, String newName, long newJoinDate, String newBio) {
		this.idOfUser = newIdOfUser;
		this.name = newName;
		this.joinDate = new AndchatDate(newJoinDate);
		this.bio = newBio;
		this.metaData = "";
	}
	
	public long getUserID() {
		return idOfUser;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setJoinDate(AndchatDate newJoinDate)
	{
		this.joinDate = newJoinDate;
	}
	
	public AndchatDate getJoinDate() {
		return joinDate;
	}
	
	public String getBio() {
		return bio;
	}
	
	public void setBio(String string) {
		this.bio = string;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}
	
}
