package com.coderdojo.libretalk;

public class LibretalkUser {

	private long id;
	private boolean online;
	private LibretalkUserProfile profile;
	
	public LibretalkUser(long newId, String newName, String newMeta, String newBio, long joinDate){
		id = newId;
		this.setProfile( new LibretalkUserProfile(newId, newName, joinDate, newBio) );
		this.setOnline(false);
	}
	
	public LibretalkUser(long newId, LibretalkUserProfile newProfile){
		id = newId;
		profile = newProfile;
	}
	
	public LibretalkUser(){
		this.id = 0l;
		this.setProfile( new LibretalkUserProfile() );
		this.setOnline(false);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LibretalkUserProfile getProfile() {
		return profile;
	}
	
	public void setProfile(LibretalkUserProfile profile){
		this.profile = profile;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
}
