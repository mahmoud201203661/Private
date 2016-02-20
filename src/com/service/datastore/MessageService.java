package com.service.datastore;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.controller.DatastorePreparedStatement;
import com.entities.ConversationEntity;
import com.entities.MessageEntity;
import com.google.appengine.api.datastore.Entity;
import com.models.Conversation;
import com.models.Message;

@Path("/")
public class MessageService {

	public MessageService() {
		System.out.println("access MessageService Class");
	}
	@POST
	@Path("/sendMessage")
	@Produces({MediaType.TEXT_PLAIN})
	public String sendMessage(@FormParam("sender")String sender, @FormParam("ID")
	String conversationID, @FormParam("content")String content){
		
		Message message = new Message(sender, conversationID, content);
		MessageEntity messageEntity = new MessageEntity(message);
		Entity entity = messageEntity.prepareEntity();
		
		if(DatastorePreparedStatement.insertInto(entity)){
			return "done";
		}
		return null;
	}
	@POST
	@Path("/createConversation")
	@Produces({MediaType.TEXT_PLAIN})
	public String createConversation(@FormParam("title")String title, @FormParam("users")String users){
		Conversation conversation = new Conversation(title, users);
		ConversationEntity conversationEntity = new ConversationEntity(conversation);
		Entity entity = conversationEntity.prepareEntity();
		if(DatastorePreparedStatement.insertInto(entity)){
			return "done";
		}
		return null;
	}
}
