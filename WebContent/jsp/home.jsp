<%@page import="com.controller.sql.UserControllerSql"%>
<%@page import="com.controller.datastore.UserController"%>
<%@page import="com.models.User"%>
<%@page import="com.controller.Connection"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>MasterFace</title>
<script src = "/MasterFace/JS/home.js"></script>
<script src="/MasterFace/JS/jquery-1.11.3.min.js"></script>
<script src="/MasterFace/JS/pusher-js-master/dist/pusher.min.js"></script>

</head>
<body onload = "notify(),trigger(),send();">
<br>
<p> Welcome ${it.firstName} ${it.lastName}</p>
<p>your ID is ${it.ID}</p>
<br>
<br>
<input class="create-notification" placeholder="Send a notification :)"></input>
<button class="submit-notification">Go!</button>
<input type = "text" name = "user" placeholder = "type an event" />
<button id = "user">Trigger</button>
<br>
<br>
<div class="notification"></div>
<a href = "home/searchUser">search User</a><br>
<a href = "/MasterFace/jsp/video.jsp">Upload and Dowload</a>
<form action="/MasterFace/social/home/sendRequest" method="post">
	Send Friend Request:-
	    	   <input type="hidden" name="sender" value=${it.ID}  readonly /> <br>
  	Receiver:  <input type="text" name="reciever" />
  			   <input type="submit" value="Send"> <br><br>
  
</form>

<form action="/MasterFace/social/home/acceptRequest" method="post">
	Accept Friend Request:-
	    	   <input type="hidden" name="reciever" value=${it.ID}  readonly /> <br>
  	Request Sender :  <input type="text" name="sender" />
  			   		  <input type="submit" value="Accept"> <br><br>
 
</form>
<form action="/MasterFace/social/home/sendMessage" method="post">
	send message:-
	    	   <input type="hidden" name="sender" value=${it.ID}  readonly /> <br>
  	conversation ID :  <input type="text" name="ID" />
  	content : _________<input type="text" name="content" />
  			   		  <input type="submit" value="sendMessage"> <br><br>
</form>
<form action="/MasterFace/social/home/createConversation" method="post">
	create conversation:-
	    	   
  	users :  <input type="text" name="users" />
  	title : _________<input type="text" name="title" />
  			   		  <input type="submit" value="createConversation"> <br><br>
</form>
create post<br>
<form action="/MasterFace/social/createPost" method="post">
	<input type="hidden" name="page" value=${it.ID}  readonly /> <br>
  	who see this post :  
  	<input type="text" name="reciever" />
  	<br>
  	<pr>say something____<pr>
  	<input type="text" name="content"/>
  	<input type="submit" value="sumit"> <br><br>
 
</form>
share post<br>
<form action="/MasterFace/social/sharePost" method="post">
	<input type="hidden" name="user" value=${it.ID}  readonly /> <br>
  	who see this post :  
  	<input type="text" name="reciever" />
  	<br>
  	<pr>ID_____________<pr>
  	<input type="text" name="postID"/>
  	<input type="submit" value="sumit"> <br><br>
 
</form>
see post
<form action="/MasterFace/social/seePost" method="post">
	<input type="hidden" name="reciever" value=${it.ID}  readonly /> <br>
  	post ID_____:  
  	<input type="text" name="postID" />
  	<input type="submit" value="sumit"> <br><br>
 
</form>
like post
<form action="/MasterFace/social/likePost" method="post">
	<input type="hidden" name="user" value=${it.ID}  readonly /> <br>
  	post ID____:  
  	<input type="text" name="postID" />
  	<input type="submit" value="sumit"> <br><br>
 
</form>
create page
<form action="/MasterFace/social/createPage" method="post">
	<input type="hidden" name="user" value=${it.ID}  readonly /> <br>
  	page Name____:  
  	<input type="text" name="pageName" />
  	<br>
  	<pr>type_________<pr>
  	<input type="text" name="type"/>
  	<input type="submit" value="sumit"> <br><br>
</form>
like page
<form action="/MasterFace/social/likePage" method="post">
	<input type="hidden" name="user" value=${it.ID}  readonly /> <br>
  	page ID____ :  
  	<input type="text" name="pageName" />
  	<input type="submit" value="sumit"> <br><br>
 
</form>
<form action = "" id = "myform" >
	<input type="hidden" id = "user" name="user" value=${it.ID}  readonly /> <br>
	<a href = "" onclick = "showFriends(); return false;" id = "loadFriends" value = "Hello" name = "loadFriends"> load friends</a>	
</form>
<table border = "1">
	
</table>
<a href='/MasterFace/social/home/logout/' >logout</a>

</body>
</html>