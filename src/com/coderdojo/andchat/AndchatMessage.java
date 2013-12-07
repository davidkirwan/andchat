package com.coderdojo.andchat;


public class AndchatMessage {
	
	private long messageID;
	private int idSender;
	private int idReceiver;
	private String message;
	private AndchatDate date;
	
	public AndchatMessage(long messageID2, int senderID, int receiverID, String message2, AndchatDate date2) {
		messageID = messageID2;
		idSender = senderID;
		idReceiver = receiverID;
		message = message2;
		date = date2;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getIdOfSender() {
		return idSender;
	}
	
	public int getIdOfReceiver() {
		return idReceiver;
	}
	
	public AndchatDate getDate() {
		return date;
	}
	
	public long getID() {
		return messageID;
	}
	
}
