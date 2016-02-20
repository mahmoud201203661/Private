package com.entities;

import java.util.Date;

import com.controller.DatastorePreparedStatement;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.models.Conversation;
import com.models.User;

public class ConversationEntity {
	Conversation conversation;
	Entity entity;
	JSONObject json;
	
	public ConversationEntity(Conversation conversation) {
		this.conversation = conversation;
	}
	public ConversationEntity(String jsonString) {
		JSONObject json = null;
		try {
			json = new JSONObject(jsonString);
		} catch (JSONException e) {
			
		}
		this.json = json;
	}
	public Entity prepareEntity(){
		int number = DatastorePreparedStatement.increment("ConversationsNumber", 1);
		Entity entity = new Entity("Conversation", number);
		entity.setProperty("Title", conversation.getTitle());
		entity.setProperty("Users", conversation.getUsers()+User.getCurrentActiveUser().getID());
		Date date = new Date();
		entity.setProperty("Date", date);
		return entity;
	}
	public Conversation prepareConversation(){
		try {
			conversation = new Conversation(json.getString("ID"), json.getString("title"),
					json.getString("users"));
		} catch (JSONException e) {
			System.out.println("failed to retrieve conversation");
		}
		return conversation;
	}
}
