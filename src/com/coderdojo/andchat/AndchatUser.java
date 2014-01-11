package com.coderdojo.andchat;

public class AndchatUser {

	private long id;
	private boolean online;
	private AndchatUserProfile profile;
	
	public AndchatUser(long id2, String name2){
		id = id2;
		this.profile.setUserName(name2);
		this.setOnline(false);
	}
	
	public AndchatUser(long id2, AndchatUserProfile pro){
		id = id2;
		profile = pro;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AndchatUserProfile getProfile() {
		return profile;
	}
	
	public void setProfile(AndchatUserProfile profile){
		this.profile = profile;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
}
