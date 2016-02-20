package com.controller.sql;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

import com.controller.Connection;
import com.controller.SqlPreparedStatement;
import com.entities.UserEntity;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.models.User;
import com.mysql.jdbc.Blob;
@Path("/")
public class UserControllerSql {
	final static String THANKING_MESSAGE = "<form id = 'myform' action='/MasterFace/social/home' method='POST'>"+
		  "<a href ='timeLine' onclick='myform.submit(); return false;'><h3>login</h3></a>"+
		  "</form>";
	java.sql.Connection con;
	static String path = "";
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	public UserControllerSql(){
		System.out.println("access UserControllerSql Class");
	}
	private Response validate(String url) {
		URI uri = null;
		try {
			uri = new URI("http://localhost:8888/MasterFace/social/home");
		} catch (URISyntaxException e) {
			return Response.ok(new Viewable("/jsp/login")).build();
		}
		path = "http://localhost:8888/MasterFace/social/home/"+url;
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
		String jsonString = Connection.connect("http://localhost:8888/MasterFace/sql/signUp/", 
				"userName="+userName+"&password="+password+"&email="+email+
				"&firstName="+firstName+"&lastName="+lastName, "POST",
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
		String jsonString = Connection.connect("http://localhost:8888/MasterFace/sql/home/", 
				"password="+password+"&userName="+userName, "POST",
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
		map.put("ID", user.getID());
		
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
			url = new URI("http://localhost:8888/MasterFace/social/home");
			
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
		String jsonString = Connection.connect("http://localhost:8888/MasterFace/sql/searchUser/", 
				"userName="+userName, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		String code = "";
		JSONArray array;
		try {
			array = new JSONArray(jsonString);
			for(int i = 0; i<array.length(); i++){
				String value = array.get(i).toString();
				User user= new UserEntity(value).prepareUser();
				String name = user.getFirstName()+" "+user.getLastName();
				String ID = user.getID();
				user.setEmail(null);
				user.setPassword(null);
				String id = "name"+i;
				code += "<form id = "+id+" action='timeLine' method='POST'>"
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
		String jsonString = Connection.connect("http://localhost:8888/MasterFace/sql/sendRequest/",
				"sender="+User.getCurrentActiveUser().getID()+"&reciever="+reciever
				, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		return jsonString;
	}
	@POST
	@Path("/home/acceptRequest")
	@Produces({MediaType.TEXT_HTML})
	public String acceptRequest(@FormParam("sender")String sender, @FormParam("reciever")String reciever){
		String jsonString = Connection.connect("http://localhost:8888/MasterFace/sql/acceptRequest/",
				"reciever="+User.getCurrentActiveUser().getID()+"&sender="+sender
				, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		return jsonString;
	}
	@POST
	@Path("/home/friends")
	@Produces({MediaType.TEXT_PLAIN})
	public String loadFriends(@FormParam("user")String user){
		String jsonString = Connection.connect("http://localhost:8888/MasterFace/sql/friends/",
				"user="+User.getCurrentActiveUser().getID()
				, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		return jsonString;
	}
	@POST
	@Path("/home/sendMessage")
	@Produces({MediaType.TEXT_PLAIN})
	public String sendMessage(@FormParam("sender")String user, @FormParam("ID")
	String conversationID, @FormParam("content")String content){
		String jsonString = Connection.connect("http://localhost:8888/MasterFace/sql/sendMessage/",
				"sender="+User.getCurrentActiveUser().getID()+"&ID="+conversationID
				+"&content="+content
				, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		return jsonString;
	}
	@POST
	@Path("/home/createConversation")
	@Produces({MediaType.TEXT_PLAIN})
	public String createConversation(@FormParam("title")String title, @FormParam("users")String users){
		String jsonString = Connection.connect("http://localhost:8888/MasterFace/sql/createConversation/",
				"title="+title+"&users="+users
				, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		return jsonString;
	}
	@POST
	@Path("/home/upload")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public String upload(HttpServletRequest h){
    	String ID = "2";
		java.sql.Connection connection = SqlPreparedStatement.getConnection();
		Blob blob = (Blob) h.getAttribute("myFile");
		try {
			System.out.println("blob is "+blob);
			PreparedStatement st = connection.prepareStatement("insert into Upload(User_ID, Name)values('2', '"+blob+"')");
			System.out.println("SUCESS");
			st.execute();
		} catch (SQLException e) {
			System.out.println("insert error");
			e.printStackTrace();
		}
		return "";
	}
}

