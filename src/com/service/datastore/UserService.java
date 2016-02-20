package com.service.datastore;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.controller.DatastorePreparedStatement;
import com.entities.UserEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.cloudsearch.v1.FieldValue.StringFormat;
import com.models.User;
//import com.sun.jersey.core.header.FormDataContentDisposition;

@Path("/")
public class UserService {
	
	public UserService() {
		System.out.println("access UserService Class");
		//ContentDisposition d = new ContentDisposition();
		
	}
	@GET
	@Path("/")
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	public Response test(){
		System.out.println("\ntest");
		
		
//		String jsonString = Connection.connect("https://www.facebook.com/mahmoud.elprins.1", 
//				"data-tab-key=timeline", "get",
//				"application/x-www-form-urlencoded;charset=UTF-8");
//		System.out.println("String = "+jsonString);
//		return jsonString;
		
//		Entity entity = new Entity("Statistics", "1");
//		entity.setProperty("UsersNumber", 0);
//		entity.setProperty("FriendsNumber", 0);
//		entity.setProperty("PostsNumber", 0);
//		entity.setProperty("GroupsNumber", 0);
//		entity.setProperty("PagesNumber", 0);
//		entity.setProperty("ConversationsNumber", 0);
//		PreparedStatement.insertInto(entity);
		//PreparedStatement.removeWhere("User", "ID", "1", "Friends", "2/");
		//PreparedStatement.increment("UsersNumber", -1);
		return null;
//		org.json.simple.JSONArray array = new org.json.simple.JSONArray();
//		JSONObject o = new JSONObject();
//		try {
//			o.put("1", "true");
//			o.put("2", "false");
//			array.add(o);
//			o = new JSONObject();
//			
//			o.put("5", "145");
//			o.put("6", "123");
//			array.add(o);
//			
//		} catch (Exception e1) {
//			
//		}
//		JSONArray Array = null;
//		try {
//			Array = new JSONArray(array.toJSONString());
//		} catch (JSONException e) {
//			System.out.println("wrong");
//		}
//		try {
//			return Response.ok(Array.toString()).build();
//		} catch (Exception e) {
//			return null;
//		}
	}
	
	@POST
	@Path("/signUp")
	@Produces({MediaType.TEXT_HTML})
	public String signUp(@FormParam("userName")String userName, 
			@FormParam("password")String password, @FormParam("email")String email,
			@FormParam("firstName")String firstName, @FormParam("lastName")String lastName){
		if(DatastorePreparedStatement.selectWhere("User", "Email", email) == null){
			if(DatastorePreparedStatement.selectWhere("User", "UserName", userName) == null){
				User user = new User(userName, email, password, firstName, lastName);
				Entity entity = new UserEntity(user).prepareEntity();
				DatastorePreparedStatement.insertInto(entity);
				return String.format("%s", entity.getKey().getId());
			}
			return "name arleady exist";
		}else{
			return "email arleady exist";
		}
	}
	@POST
	@Path("/home")
	@Produces({MediaType.TEXT_HTML})
	public String login( @FormParam("password")String password, @FormParam("userName")String userName ){
		Entity entity = DatastorePreparedStatement.selectWhere("User", "UserName", userName);
		if( entity == null){
			entity = DatastorePreparedStatement.selectWhere("User", "Email", userName);
		}
		if(entity == null){
			return null;
		}else{
			if(!entity.getProperty("Password").toString().equals(password)){
				return null;
			}
			JSONObject json = new JSONObject();
			try {
				json.put("userName", entity.getProperty("UserName").toString());
				json.put("email", entity.getProperty("Email").toString());
				json.put("password", entity.getProperty("Password").toString());
				json.put("firstName", entity.getProperty("FirstName").toString());
				json.put("lastName", entity.getProperty("LastName").toString());
				json.put("ID", String.format("%s", entity.getKey().getId()));
			return json.toString();
			} catch (JSONException e) {
				return null;
			}
		}
	}
	@POST
	@Path("/searchUser")
	@Produces({MediaType.TEXT_HTML})
	public String searchUser(@FormParam("userName")String userName){
		List<Entity> users = new ArrayList<Entity>();
		System.out.println(userName);
		if(userName.equals("") || userName == null){
			users = DatastorePreparedStatement.retrieveAll("User");
		}else{
			users = DatastorePreparedStatement.retrieveAllWhere("User", "FirstName", userName);
		}
		
		JSONObject json = new JSONObject();
		org.json.simple.JSONArray array = new org.json.simple.JSONArray();
		for(Entity entity : users){
			json = new JSONObject();
			try {
				json.put("userName", entity.getProperty("UserName").toString());
				json.put("ID", entity.getKey().getId());
				json.put("firstName", entity.getProperty("FirstName"));
				json.put("lastName", entity.getProperty("LastName"));
				json.put("email", entity.getProperty("Email"));
				json.put("password", entity.getProperty("Password"));
				array.add(json);
			} catch (JSONException e) {
				
			}
		}
		return array.toJSONString();
	}
	@POST
	@Path("/sendRequest")
	@Produces({MediaType.TEXT_HTML})
	public String sendRequest(@FormParam("reciever")String reciever,@FormParam("sender")String sender){
		System.out.println(sender+"\t"+reciever);
		DatastorePreparedStatement.appendWhere("User", "ID", sender, "Sent", reciever+"/");
		DatastorePreparedStatement.appendWhere("User", "ID", reciever, "Recieved", sender+"/");
		return "done";
	}
	@POST
	@Path("/acceptRequest")
	@Produces({MediaType.TEXT_HTML})
	public String acceptRequest(@FormParam("reciever")String reciever,@FormParam("sender")String sender){
		DatastorePreparedStatement.appendWhere("User", "ID", sender, "Friends", reciever+"/");
		DatastorePreparedStatement.appendWhere("User", "ID", reciever, "Friends", sender+"/");
		
		DatastorePreparedStatement.removeWhere("User", "ID", sender, "Sent", reciever+"/");
		DatastorePreparedStatement.removeWhere("User", "ID", reciever, "Recieved", sender+"/");
		return "done";
	}
	@POST
	@Path("/friends")
	@Produces({MediaType.TEXT_HTML})
	public String loadFriends(@FormParam("user")String user){
		Entity entity = DatastorePreparedStatement.selectWhere("User", "ID", user);
		return entity.getProperty("Friends").toString();
	}
}
