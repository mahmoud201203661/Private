package com.entities;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.models.Message;

public class MessageEntity {
	Message message;
	Entity entity;
	JSONObject json;
	public MessageEntity() {
		
	}
	public MessageEntity(Message message) {
		this.message = message;
		
	}
	public MessageEntity(String jsonString) {
		JSONObject json = null;
		try {
			json = new JSONObject(jsonString);
		} catch (JSONException e) {
			
		}
		this.json = json;
	}
	public Entity prepareEntity(){
		Date date = new Date();
		int ID = (date.toString()+message.toString()).hashCode();
		Entity entity = new Entity("Message", ID);
		entity.setProperty("Sender", message.getSender());
		entity.setProperty("ConversationID", message.getConversationID());
		entity.setProperty("content", message.getContent());
		date = new Date();
		entity.setProperty("Date", date);
		return entity;
	}
	public Message prepareMessage(){
		try {
			message = new Message(json.getString("sender"), json.getString("conversationID"),
					json.getString("content"));
		} catch (JSONException e) {
			System.out.println("failed to retrieve Message");
		}
		return message;
	}
}
