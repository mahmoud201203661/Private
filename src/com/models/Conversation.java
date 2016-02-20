package com.models;

public class Conversation {
	String ID;
	String title;
	String users;
	
	public Conversation(String iD, String title, String users) {
		ID = iD;
		this.title = title;
		this.users = users;
	}

	public Conversation(String title, String users) {
		this.users = users;
		this.title = title;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}
	
}
