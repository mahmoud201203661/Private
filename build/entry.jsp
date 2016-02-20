<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	int length = 0;
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>Welcome Page</title>
	<link rel="stylesheet" href = "CSS/myshapes and Animation.css">
	
	<style type = "text/css" >
	</style>
	<script src = "JS/homePage.js"></script>
	<script>
	var j = 0;
	var arr = ["", ""];
		function getImage(){
			<%
				List<String> paths = new ArrayList<String>();
				paths.add("pictures/1374073_10154030147063647_2480893394772682585_n.jpg");
				paths.add("pictures/FILE609.JPG");
				paths.add("pictures/FILE655.JPG");
				paths.add("pictures/FILE675.JPG");
				paths.add("pictures/FILE676.JPG");
				paths.add("pictures/10.jpg");
				paths.add("pictures/Untitled.png");
				paths.add("pictures/FILE001.BMP");
				for(int i = 0;i<paths.size();i++){
			%>
					var temp = "<%=i%>";
					arr[temp] = "<%=paths.get(i)%>";
			<%
				}
			%>
		}
	</script>
</head>
<body onload = "getImage(),myEvent(),mytest();">
	<div id = "into">
	    <a href="social/home">login</a>
	    <a href="/social/signup/">sign up</a>
	    <h1>Welcome Master Face</h1>
	</div>
	<img id = "scream" src = "pictures/FILE609.JPG"></img>
	<a href="datastore" id = "v">service</a>
	<p id  = "b">some pictures uploaded to this server</p>
	<!--<canvas id="myCanvas" width="800" height="500" style="border:1px solid #000000;"></canvas>-->
	<div id = "footer">
		<div id = "text">
	    	<p>this place is reserved for ads and site note</p>
	    </div>
	</div>
</body>
</html>