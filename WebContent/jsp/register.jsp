<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
	<title>Welcome!</title>
	<link rel="stylesheet" href = "/MasterFace/CSS/register.css">
	<style type = "text/css" >	
	</style>
	<script type="text/javascript" src = "/MasterFace/JS/register.js"></script>
	<script src="/MasterFace/JS/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body onload="prepareDate(),validate();">
	<form action="/MasterFace/social/response" method="post">
		<h1>MasterFace registration</h1>
		<input type="text" name="firstName" placeholder = "First Name"/>
		<input type="text" name="lastName" placeholder = "Last Name"/><br>
  		<input type="text" name="userName" placeholder = "User Name" /> <br>
  		<input id = "email" type="text" name="email" placeholder = "email@example.com"/>
  		<div id = "notify">
  			<p >Hello</p>
  		</div><br>
		<input type="text" name="confirmEmail" placeholder = "confirmation email"/> <br>
		<input type="password" name="password" placeholder = "************************"/><br>
		<select name = "year" id = "year">
			<option>”‰…</option>
		</select>
		<select name = "month" id = "month">
			<option>‘Â—</option>
		</select>
		<select name = "day" month = "day">
			<option>ÌÊ„</option>
		</select>
		<p>Gender:
		male: <input type = "radio" name = "gender" value = "male"></input>
		female: <input type = "radio" name = "gender" value = "female"></input></p><br>
		<input type="submit" name = "register" value="">
	</form>
</body>
</html>
