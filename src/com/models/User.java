package com.models;

public class User {
	private String userName;
	private String email;
	private String password;
	private String ID;
	private String firstName;
	private String lastName;
	private static User currentActiveUser;
	public User() {
		
	}
	
	public User(String userName, String email, String password, String firstName, String lastName) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String userName, String email, String password, String firstName, String lastName, String ID) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.ID = ID;
	}
	
	public static User getCurrentActiveUser() {
		return currentActiveUser;
	}

	public static void setCurrentActiveUser(User currentActiveUser) {
		User.currentActiveUser = currentActiveUser;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
