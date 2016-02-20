<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<html>
<head>
</head>
<body>
	<form action="/MasterFace/social/home/doSearch" method="post">
		<p>Enter user name</p>
		<input type="text" name="userName" /> 
		<input type="submit" value="Search" />
	</form>
</body>
</html>