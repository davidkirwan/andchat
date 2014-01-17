package com.coderdojo.andchat;

public class AndchatUser {

	private long id;
	private boolean online;
	private AndchatUserProfile profile;
	
	public AndchatUser(long newId, String newName, String newMeta, String newBio, long joinDate){
		id = newId;
		this.setProfile( new AndchatUserProfile(newId, newName, joinDate, newBio) );
		this.setOnline(false);
	}
	
	public AndchatUser(long newId, AndchatUserProfile newProfile){
		id = newId;
		profile = newProfile;
	}
	
	public AndchatUser(){
		this.id = 0l;
		this.setProfile( new AndchatUserProfile() );
		this.setOnline(false);
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
