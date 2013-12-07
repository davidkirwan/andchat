package com.coderdojo.andchat;

public class AndchatMessage {
	
	private long id;
	private long to;
	private long from;
	private AndchatDate theDate;
	private String messageContent;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTo() {
		return to;
	}
	public void setTo(long to) {
		this.to = to;
	}
	public long getFrom() {
		return from;
	}
	public void setFrom(long from) {
		this.from = from;
	}
	public AndchatDate getTheDate() {
		return theDate;
	}
	public void setTheDate(AndchatDate theDate) {
		this.theDate = theDate;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}
