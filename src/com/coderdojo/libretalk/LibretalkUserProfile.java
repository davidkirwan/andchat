package com.coderdojo.libretalk;


public class LibretalkUserProfile {
	
	private long idOfUser;
	private String name;
	private LibretalkDate joinDate;
	private String metaData;
	private String bio;
	
	
	public LibretalkUserProfile(){
		this.idOfUser = 0l;
		this.name = "";
		this.joinDate = new LibretalkDate();
		this.metaData = "";
		this.bio = "";
	}
	
	public LibretalkUserProfile(long newIdOfUser, String newName, long newJoinDate, String newBio) {
		this.idOfUser = newIdOfUser;
		this.name = newName;
		this.joinDate = new LibretalkDate(newJoinDate);
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
	
	public void setJoinDate(LibretalkDate newJoinDate)
	{
		this.joinDate = newJoinDate;
	}
	
	public LibretalkDate getJoinDate() {
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
