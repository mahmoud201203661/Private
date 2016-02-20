<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
<link rel="stylesheet" href = "/MasterFace/CSS/register.css">
<style>	
	input{
		font-size: small;
	}
	input[type = "submit"]{
		width: 100px;
		height: 30px;
		background-image: url("/MasterFace/pictures/b1.png");
	}
</style>
</head>
<body>
<form action="/MasterFace/social/home" method="post">
  <input type="text" name="userName" placeholder = "UserName or Email" /> <br>
  <input type="password" name="password" placeholder = "password"/> <br>
  <input type="submit" value="">
  <br>
</form>
Don't have account <a href="/MasterFace/social/signup/">Sign up</a> <br>
</body>
</html>