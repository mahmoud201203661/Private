package com.entities;

import java.util.Date;

import com.controller.DatastorePreparedStatement;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.models.User;

public class UserEntity {
	User user;
	Entity entity;
	JSONObject json;
	public UserEntity(User user) {
		this.user = user;
		
	}
	public UserEntity(String jsonString) {
		JSONObject json = null;
		try {
			json = new JSONObject(jsonString);
		} catch (JSONException e) {
			
		}
		this.json = json;
	}
	public Entity prepareEntity(){
		int number = DatastorePreparedStatement.increment("UsersNumber", 1);
		entity = new Entity("User", number);
		entity.setProperty("UserName", user.getUserName());
		entity.setProperty("FirstName", user.getFirstName());
		entity.setProperty("LastName", user.getLastName());
		entity.setProperty("Email", user.getEmail());
		entity.setProperty("Password", user.getPassword());
		entity.setProperty("Pages", "/");
		entity.setProperty("Sent", "/");
		entity.setProperty("Recieved", "/");
		entity.setProperty("Friends", "/");
		Date date = new Date();
		entity.setProperty("Date", date);
		return entity;
	}
	public User prepareUser(){
		try {
			
			user = new User(json.getString("userName"), json.getString("email"),
					json.getString("password"), json.getString("firstName"), json.getString("lastName"));
			user.setID(json.getString("ID"));
			
		} catch (JSONException e) {
			
		}
		return user;
	}
}
