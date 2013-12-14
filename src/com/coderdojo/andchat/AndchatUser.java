package com.coderdojo.andchat;

public class AndchatUser {

	private long id;
	private String name;
	private Profile profile;
	
	public AndchatUser(long id2, String name2, Profile pro){
		id = id2;
		name = name2;
		profile = pro;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Profile getProfile() {
		return profile;
	}
}
