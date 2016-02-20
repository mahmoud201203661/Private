package com.service.sql;


import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.controller.Notification;
import com.controller.SqlPreparedStatement;
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
		Notification.sendMailToLateUsers("mahmoudelprins22@gmail.com", "1");
		System.out.println("\ntest");
		return null;
	}
	
	@POST
	@Path("/signUp")
	@Produces({MediaType.TEXT_HTML})
	public String signUp(@FormParam("userName")String userName, 
			@FormParam("password")String password, @FormParam("email")String email,
			@FormParam("firstName")String firstName, @FormParam("lastName")String lastName){
		ResultSet result = SqlPreparedStatement.select("select Email, UserName from User where Email='"+email+"' OR UserName='"+userName+"'");
		try {
			if(result.next()){
				if(result.getString("Email").equals(email)){
					return "email arleady exist";
				}else{
					return "name arleady exist";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "can't sign up";
		}
		Date date = new Date();
		String d = String.format("%s-%s-%s", date.getYear()+1900, date.getMonth(), date.getDate());
		String ID = String.format("%s", SqlPreparedStatement.increment("Users", 1));
		SqlPreparedStatement.insert("insert into User(User_ID, UserName, FirstName, LastName, Email, Password, Date)values(?, ?, ?, ?, ?, ?, ?)",
				ID, userName, firstName, lastName, email, password, d);
		SqlPreparedStatement.insert("INSERT INTO `userrelation`(`User_ID`, `Friends`, `Sent`, `Recieved`, `MyPage`, `Group`, `Pages`) VALUES (?, ?, ?, ?, ?, ?, ?)",
				ID, "(", "(", "(", "(", "(", "(");
		return ID;
	}
	@POST
	@Path("/home")
	@Produces({MediaType.TEXT_HTML})
	public String login( @FormParam("password")String password, @FormParam("userName")String userName ){
		ResultSet result = SqlPreparedStatement.select("select * from User where (Email='"+userName+"' OR UserName='"+userName+"') AND Password='"+password+"'");
		JSONObject json = new JSONObject();
		try {
			if(result.next()){
				try {
					json.put("userName", result.getString("UserName"));
					json.put("email", result.getString("Email"));
					json.put("password", result.getString("Password"));
					json.put("firstName", result.getString("FirstName"));
					json.put("lastName", result.getString("LastName"));
					json.put("ID", result.getString("User_ID"));
					return json.toString();
				} catch (JSONException e) {
					return null;
				}
			}else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	@POST
	@Path("/searchUser")
	@Produces(MediaType.TEXT_HTML)
	public String searchUser(@FormParam("userName")String userName){
		ResultSet users = null;
		if(userName.equals("") || userName == null){
			users = SqlPreparedStatement.select("select * from User");
		}else{
			if(userName.contains(" ")){
				String[] names = userName.split(" ");
				users = SqlPreparedStatement.select("select * from User where FirstName LIKE '"+userName+"' OR "
						+ "(FirstName LIKE '"+names[0]+"' AND LastName LIKE '"+names[1]+"')");
			}else{
				users = SqlPreparedStatement.select("select * from User where FirstName LIKE '"+userName+"'");
			}
		}
		
		JSONObject json = new JSONObject();
		org.json.simple.JSONArray array = new org.json.simple.JSONArray();
		try {
			while(users.next()){
				json = new JSONObject();
				try {
					json.put("userName", users.getString("UserName"));
					json.put("ID", users.getString("User_ID"));
					json.put("firstName", users.getString("FirstName"));
					json.put("lastName", users.getString("LastName"));
					json.put("email", users.getString("Email"));
					json.put("password", users.getString("Password"));
					array.add(json);
				} catch (JSONException e) {
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array.toJSONString();
	}
	@POST
	@Path("/sendRequest")
	@Produces({MediaType.TEXT_HTML})
	public String sendRequest(@FormParam("reciever")String reciever,@FormParam("sender")String sender){
		boolean r1 = SqlPreparedStatement.append("UserRelation", "User_ID", sender, "Sent", reciever+",");
		boolean r2 = SqlPreparedStatement.append("UserRelation", "User_ID", reciever, "Recieved", sender+",");
		
		return (r1 && r2)? "done" : "faild";
	}
	@POST
	@Path("/acceptRequest")
	@Produces({MediaType.TEXT_HTML})
	public String acceptRequest(@FormParam("reciever")String reciever,@FormParam("sender")String sender){
		boolean r1 = SqlPreparedStatement.append("UserRelation", "User_ID", sender, "Friends", reciever+",");
		boolean r2 = SqlPreparedStatement.append("UserRelation", "User_ID", reciever, "Friends", sender+",");
		if(r1 && r2){
			r1 = SqlPreparedStatement.remove("UserRelation", "User_ID", sender, "Sent", reciever+",");
			r2 = SqlPreparedStatement.remove("UserRelation", "User_ID", reciever, "Recieved", sender+",");
			return (r1 && r2)? "done" : "faild34";
		}
		return "faild12";
	}
	@POST
	@Path("/friends")
	@Produces({MediaType.TEXT_HTML})
	public String loadFriends(@FormParam("user")String user){
		ResultSet IDs = SqlPreparedStatement.select("select Friends from UserRelation where User_ID='"+user+"'");
		String r = "";
		try {
			if(IDs.next()){
				r = IDs.getString("Friends");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		r = r.substring(0, r.length()-1);
		ResultSet friends = SqlPreparedStatement.select("select User_ID, FirstName, LastName from User where User_ID in "+r+")");
		try {
			String result = "";
			while (friends.next()) {
				result += friends.getString("User_ID")+" "+friends.getString("FirstName")+" "+friends.getString("LastName")+"-";
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
