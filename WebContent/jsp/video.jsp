<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>videos</title>
	<link rel = "stylesheet" href = "/MasterFace/CSS/video-style.css">
	<link rel="stylesheet" href="/MasterFace/jsp/font-awesome-4.5.0/css/font-awesome.css">
	<link rel="stylesheet" href="/MasterFace/jsp/font-awesome-4.5.0/css/font-awesome.min.css">
	<script src = "/MasterFace/JS/video-script.js"></script>
	<script src = "/MasterFace/JS/jquery-1.12.0.min.js"></script>
	<style>
	</style>
	
	<script>
		function showFiles(){
			$(document).ready(function() {
				$("#display").click(function(){
					var ID = $("input[name ='ID']").val();
					var text = "<ul id = 'videoBlock1'>"+
						"<video id = 'video1' src = 'http://localhost:8888/MasterFace/download?ID=4' poster=''></video><br>"+
						"<div id = 'controls1'>"+
							"<li><i id = 'play1' class = 'fa fa-play'></i> </li>"+
							"<li><div id = 'progressBar1' ><div id='progress1'></div></div></li>"+
							"<li><i id = 'mute1' class = 'fa fa-bell'></i> </li>"+
							"<li><p id = 'time1' class = 'time'>00:00:00</p></li>"+
							"<li><i class='fa fa-volume-up w3-large' id = 'volumeSample1'><div id = 'volumeBar1' ><div id='volume1'></div></div></i></li>"+
						"</div>"+
					"</ul>";
					$("[id ^= 'videoBlock']").css("display","block");
					$("#video1").prop("src", "http://localhost:8888/MasterFace/download?ID="+ID);
				});
			});
		}
	</script>
</head>
<body onload = "play();mute();videoProgress();volumeProgress();hov(),showFiles();">
	<ul id = "videoBlock1">
		<video id = "video1" src = "" poster=""></video><br>
		<div id = "controls1">
			<li><i id = "play1" class = "fa fa-play"></i> </li>
			<li><div id = "progressBar1" ><div id='progress1'></div></div></li>
			<li><i id = "mute1" class = "fa fa-bell"></i> </li>
			<li><p id = "time1" class = "time">00:00:00</p></li>
			<li><i class="fa fa-volume-up w3-large" id = "volumeSample1"><div id = "volumeBar1" ><div id='volume1'></div></div></i></li>
		</div>
	</ul>
	<div id = "page"></div>
	<form action="/MasterFace/upload" method="post" enctype="multipart/form-data">
		<input type = "file" name = "myFile"/>
		<input type = "submit" value = "upload"/>
	</form>

	<input type = "text" name = "ID" place holder = "ID"/>
	<button  id = "display">download</button>

</body>
</html>