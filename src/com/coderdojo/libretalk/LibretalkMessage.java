package com.coderdojo.libretalk;


public class LibretalkMessage {
	
	private long messageID;
	private long idSender;
	private long idReceiver;
	private String message;
	private LibretalkDate date;
	
	public LibretalkMessage(long messageID2, long senderID, long receiverID, String message2, LibretalkDate date2) {
		messageID = messageID2;
		idSender = senderID;
		idReceiver = receiverID;
		message = message2;
		date = date2;
	}
	
	public String getMessage() {
		return message;
	}
	
	public long getIdOfSender() {
		return idSender;
	}
	
	public long getIdOfReceiver() {
		return idReceiver;
	}
	
	public LibretalkDate getDate() {
		return date;
	}
	
	public long getID() {
		return messageID;
	}
	
}
