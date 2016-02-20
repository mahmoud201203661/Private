package com.models;

public class Message {
	String sender;
	String conversationID;
	String content;
	
	public Message(String sender, String conversationID, String content) {
		this.sender = sender;
		this.conversationID = conversationID;
		this.content = content;
	}

	public Message(String sender, String content) {
		this.sender = sender;
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getConversationID() {
		return conversationID;
	}

	public void setConversationID(String conversationID) {
		this.conversationID = conversationID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
