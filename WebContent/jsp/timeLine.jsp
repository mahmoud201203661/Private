<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.models.User"%>
<html>
<head>
</head>
<body>
${it.userName}
<%
	String current = User.getCurrentActiveUser().getID();
%>
	<form action="/MasterFace/social/home/sendRequest" method="post">
		<input type="hidden" name="reciever" value=${it.ID}  readonly /> 
		<input type="hidden" name="sender" value=current readonly /> 
		<input type="submit" value="add friend" />
	</form>
	<form action="/MasterFace/social/home/sendMessage" method="post">
		<input type="hidden" name="ID" value=${it.ID}  readonly /> 
		
		<input type="submit" value="send message" />
	</form>
</body>
</html>