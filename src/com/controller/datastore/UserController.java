package com.controller.datastore;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.controller.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.ws.FaultAction;

import org.glassfish.jersey.server.mvc.Viewable;

import com.entities.UserEntity;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.models.User;

@Path("/")
public class UserController {
	final static String THANKING_MESSAGE = "<form id = 'myform' action='/social/home' method='post'>"+
			  "<a href ='timeLine' onclick='myform.submit(); return false;'><h3>login</h3></a>"+
			  "</form>";
	java.sql.Connection con;
	static String path = "";
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	public UserController(){
//		 try {
//				Class.forName("com.mysql.jdbc.Driver");
//			} catch (ClassNotFoundException e) {
//				System.out.println("error in driver");
//				e.printStackTrace();
//			}
//			java.sql.Connection con = null;
//			try {
//				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniuniversity","root","");
//				String sqlStatement = "select Name from student where SSN='101'";
//				java.sql.PreparedStatement emailStatement = con.prepareStatement(sqlStatement);
//				ResultSet passwordResult = emailStatement.executeQuery();
//				while (passwordResult.next()) {
//					System.out.println("this is password--->"+passwordResult.getString("Name"));
//					
//				}
//			} catch (SQLException e) {
//				System.out.println("database connection");
//			} 
		System.out.println("access UserController Class");
		//System.out.println("KB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024*1024));
		
	}
	private Response validate(String url) {
		URI uri = null;
		try {
			uri = new URI("http://localhost:8888/social/home");
		} catch (URISyntaxException e) {
			return Response.ok(new Viewable("/jsp/login")).build();
		}
		path = "http://localhost:8888/social/home/"+url;
		return Response.temporaryRedirect(uri).build();
	}
	@GET
	@Path("/signup")
	@Produces({MediaType.TEXT_HTML})
	public Response signUpPage(){
		return Response.ok(new Viewable("/jsp/register")).build();
	}
	@POST
	@Path("/response")
	@Produces({MediaType.TEXT_HTML})
	public String signUp(@FormParam("userName")String userName, 
			@FormParam("password")String password, @FormParam("email")String email,
			@FormParam("firstName")String firstName, @FormParam("lastName")String lastName){
		if(userName == null || userName.equals("") || email == null || email.equals("")
				|| password == null || password.equals("") || lastName == null || lastName.equals("")){
			return "all fields required";
		}
		String jsonString = Connection.connect("http://localhost:8888/datastore/signUp/", 
				"userName="+userName+"&password="+password+"&email="+email+
				"&firstName="+firstName+"&lastName="+lastName, "post",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		if(!jsonString.contains("arleady exist")){
			User.setCurrentActiveUser(new User(userName, email, password, firstName, lastName, jsonString));
			return "<h3>Thank you for registeration do you want to </h3>"+THANKING_MESSAGE;
		}else{
			return jsonString;
		}
	}
	@GET
	@Path("/home")
	@Produces({MediaType.TEXT_HTML})
	public Response login(){
		if(User.getCurrentActiveUser() != null){
			return home(User.getCurrentActiveUser().getUserName(), User.getCurrentActiveUser().getPassword());
		}
		return Response.ok(new Viewable("/jsp/login")).build();
	}	
	@POST
	@Path("/home")
	@Produces({MediaType.TEXT_HTML})
	public Response home(@FormParam("userName")String userName, @FormParam("password")String password ){
		String jsonString = Connection.connect("http://localhost:8888/datastore/home/", 
				"password="+password+"&userName="+userName, "post",
				"application/x-www-form-urlencoded;charset=UTF-8");
		User user = null;
		try {
			
			user = new UserEntity(jsonString).prepareUser();
			
		} catch (Exception e) {
			return Response.ok(new Viewable("/jsp/login")).build();
		}
		
		User.setCurrentActiveUser(user);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", user.getUserName());
		map.put("email", user.getEmail());
		map.put("password", user.getPassword());
		map.put("firstName", user.getFirstName());
		map.put("lastName", user.getLastName());
		try {
			map.put("ID", user.getID());
		} catch (Exception e) {
			
		}
		if(path != ""){
			URI url = null;
			try {
				url = new URI(path);
				return Response.temporaryRedirect(url).build();
			} catch (URISyntaxException e) {
				return Response.ok(new Viewable("/jsp/home" , map)).build();
			}finally{
				path = "";
			}
			
		}
		return Response.ok(new Viewable("/jsp/home" , map)).build();
		
	}
	@GET
	@Path("/home/logout")
	@Produces({MediaType.TEXT_HTML})
	public Response logout(){
		try{
			User.setCurrentActiveUser(null);
		}catch(Exception e){
			System.out.println("///////////");
		}
		
		path = "";
		URI url = null;
		try {
			url = new URI("http://localhost:8888/social/home");
			
			return Response.temporaryRedirect(url).build();
		} catch (URISyntaxException e) {
			return Response.ok(new Viewable("/jsp/login")).build();
		}
	}
	@GET
	@Path("/home/searchUser")
	@Produces({MediaType.TEXT_HTML})
	public Response searchPage(){
		
		if(User.getCurrentActiveUser() == null){
			return validate("searchUser");
		}
		return Response.ok(new Viewable("/jsp/search")).build();
	}
	@POST
	@Path("/home/doSearch")
	@Produces({MediaType.TEXT_HTML})
	public Response searchUser(@FormParam("userName")String userName){
		String jsonString = Connection.connect("http://localhost:8888/datastore/searchUser/", 
				"userName="+userName, "post",
				"application/x-www-form-urlencoded;charset=UTF-8");
		String code = "";
		JSONArray array;
		try {
			array = new JSONArray(jsonString);
			for(int i = 0; i<array.length(); i++){
				String value = array.get(i).toString();
				User user= new UserEntity(value).prepareUser();
				String name = user.getUserName();
				String ID = user.getID();
				user.setEmail(null);
				user.setPassword(null);
				String id = "name"+i;
				code += "<form id = "+id+" action='timeLine' method='post'>"
				+"<input type='hidden' name='userName' value="+name+"  readonly />"
				+"<input type='hidden' name='ID' value="+ID+"  readonly />"
				+"<a href ='timeLine' onclick='"+id+".submit(); return false;'> "+name+"</a>"
				+"</form><br>";
			}
		} catch (JSONException e) {
			return null;
		}
		
		return Response.ok(code).build();
	}
	@POST
	@Path("home/timeLine")
	@Produces({MediaType.TEXT_HTML})
	public Response timeLine(@FormParam("userName")String userName, @FormParam("ID")String ID){
		Map<String, String>map = new HashMap<String, String>();
		map.put("ID", ID);
		map.put("userName", userName);
		
		return Response.ok(new Viewable("/jsp/timeLine", map)).build();
	}
	@POST
	@Path("/home/sendRequest")
	@Produces({MediaType.TEXT_HTML})
	public String sendRequest(@FormParam("sender")String sender, @FormParam("reciever")String reciever){
		String jsonString = Connection.connect("http://localhost:8888/datastore/sendRequest/",
				"sender="+User.getCurrentActiveUser().getID()+"&reciever="+reciever
				, "post", "application/x-www-form-urlencoded;charset=UTF-8");
		if(jsonString == null){
			return "failed";
		}
		return "done";
	}
	@POST
	@Path("/home/acceptRequest")
	@Produces({MediaType.TEXT_HTML})
	public String acceptRequest(@FormParam("sender")String sender, @FormParam("reciever")String reciever){
		String jsonString = Connection.connect("http://localhost:8888/datastore/acceptRequest/",
				"reciever="+User.getCurrentActiveUser().getID()+"&sender="+sender
				, "post", "application/x-www-form-urlencoded;charset=UTF-8");
		if(jsonString == null){
			return "failed";
		}
		return "done";
	}
	@POST
	@Path("/home/friends")
	@Produces({MediaType.TEXT_PLAIN})
	public String loadFriends(@FormParam("user")String user){
		String jsonString = Connection.connect("http://localhost:8888/datastore/friends/",
				"user="+User.getCurrentActiveUser().getID()
				, "post", "application/x-www-form-urlencoded;charset=UTF-8");
		return jsonString;
	}
	@POST
	@Path("/home/sendMessage")
	@Produces({MediaType.TEXT_PLAIN})
	public String sendMessage(@FormParam("sender")String user, @FormParam("ID")
	String conversationID, @FormParam("content")String content){
		String jsonString = Connection.connect("http://localhost:8888/datastore/sendMessage/",
				"sender="+User.getCurrentActiveUser().getID()+"&ID="+conversationID
				+"&content="+content
				, "post", "application/x-www-form-urlencoded;charset=UTF-8");
		return jsonString;
	}
	@POST
	@Path("/home/createConversation")
	@Produces({MediaType.TEXT_PLAIN})
	public String createConversation(@FormParam("title")String title, @FormParam("users")String users){
		String jsonString = Connection.connect("http://localhost:8888/datastore/createConversation/",
				"title="+title+"&users="+users
				, "post", "application/x-www-form-urlencoded;charset=UTF-8");
		return jsonString;
	}
}
