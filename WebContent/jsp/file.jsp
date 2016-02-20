<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Upload and Download</title>
<script src="/MasterFace/JS/jquery-1.11.3.min.js"></script>
<script>
function showFiles(){
	$(document).ready(function(){
		$("#display").click(function(){
			var ID = $("input[name ='ID']").val();
			alert("ID===="+4);
			$("div").html("<video src='http://localhost:8888/MasterFace/download?ID="+ID+"' controls></video>");
		});
		
// 		$.ajax({
// 			type : "Get",
//     	    url: "http://localhost:8888/MasterFace/download",
//     	    data: "ID="+ID,
//     	    cache: false,
//     	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
//     	    success:  function(data){
//     			alert("success");
    			
// //     			for(i = 0; i< friends.length; i++){
// //     				friend = friends[i];
// //     				var data = friend.split(" ")
// //     				text += "<tr><td>"+
// //     				data[0]+"</td><td>"+
// //     				data[1]+"</td><td>"+
// //     				data[2]+"</td>"+"</tr>";
// //     			}
// //     			$("table").html(text);
    	        
//     	    },
//     	    error: function(data){
//     	    	alert(data);
//     	    }
// 		});
		
	});
}
</script>
</head>
<body onload = "showFiles();">
	<form action="/MasterFace/upload" method="post" enctype="multipart/form-data">
		<input type = "file" name = "myFile"/>
		<input type = "submit" value = "upload"/>
	</form>

		<input type = "text" name = "ID" place holder = "ID"/>
		<button  id = "display">download</button>
<div></div>
</body>
</html>