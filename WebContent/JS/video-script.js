function login(){
	$(document).ready(function() {
		//var email = $("[name = email]").val();
		//var password = $("[name = password]").val();
		$.post("http://localhost:8888/MasterFace/social/home","userName=20120366&password=123", function(data, status){
			alert(data, status);
		});
		//https://www.facebook.com/login.php?login_attempt=1&amp;lwv=110
	});
}
function hov(){
	$(document).ready(function() {
		$("[id ^= 'volumeSample']").hover( function(){
			var id  = this.id.substring(12, this.id.length);
			$("#volumeBar"+id).css("display","block");
		});
		$("[id ^= 'volumeSample']").mouseleave( function(){
			var id  = this.id.substring(12, this.id.length);
			$("#volumeBar"+id).css("display","none");
		});
	});
}
function play(){
	$(document).ready(function() {
		$("[id ^= 'play']").click( function(){
			var videoID = "video"+this.id.substring(4, this.id.length);
			if($("#"+videoID)[0].paused){
				$("#"+videoID)[0].play();
				$("#"+this.id).removeClass("fa fa-play");
				$("#"+this.id).addClass("fa fa-pause");
				
			}else if(!$("#"+videoID)[0].paused){
				$("#"+videoID)[0].pause();
				$("#"+this.id).removeClass("fa fa-pause");
				$("#"+this.id).addClass("fa fa-play");
			}
			videoTime(videoID);
		});
	});
}
function mute(){
	$(document).ready(function() {
		$("[id ^= 'mute']").click( function(){
			var videoID = "video"+this.id.substring(4, this.id.length);
			if(!$("#"+videoID)[0].muted){
				$("#"+videoID)[0].muted = true;
				$("#"+this.id).removeClass("fa fa-volume-up");
				$("#"+this.id).addClass("fa fa-bell-slash");
				
			}else if($("#"+videoID)[0].muted){
				$("#"+videoID)[0].muted = false;
				$("#"+this.id).removeClass("fa fa-bell-slash");
				$("#"+this.id).addClass("fa fa-bell");
			}
		});
	});
}
function videoTime(id){
	$(document).ready(function() {
		$("#"+id)[0].addEventListener("timeupdate",function(){
			
			var timeID = "time"+this.id.substring(5, this.id.length);
			var progressID = "progress"+this.id.substring(5, this.id.length);
			var width = $("#progressBar"+this.id.substring(5, this.id.length)).width();
			var time = $("#"+this.id)[0].currentTime, minites = 0, seconds = 0, hours = 0;
			hours = time / 3600;
			time = time % 3600;
			minites = time/60;
			seconds = time % 60;
			hours = Math.floor(hours);
			minites = Math.floor(minites);
			seconds = Math.floor(seconds);
			hours = hours < 10 ? "0"+hours : hours;
			minites = minites < 10 ? "0"+minites : minites;
			seconds = seconds < 10 ? "0"+seconds : seconds;
			$("#"+timeID).html(hours + ":" + minites + ":" + seconds);
			var percentage = Math.floor((width / $("#"+this.id)[0].duration) * $("#"+this.id)[0].currentTime);
			$("#"+progressID).css('width', percentage+'px');
			$("#"+progressID).css('background-color', 'blue');
			//$("#"+progressID).width(percentage);
			$("#"+progressID).css('height', '25px');
		},false);
	});
}
function videoProgress (){
	$(document).ready(function() {
		var timeDrag = false;   /* Drag status */
		$("[id ^= progress]").mousedown(function(e) {
			timeDrag = true;
		});
		$("[id ^= progress]").mouseup(function(e) {
			if(timeDrag) {
				timeDrag = false;
				var first = this.id.indexOf("Bar");
				if(first>=0){
					updateVideoBar(this.id.substring(11, this.id.length), e.pageX);
					
				}else{
					updateVideoBar(this.id.substring(8, this.id.length), e.pageX);
				}
			}
		});
		$("[id ^= progress]").mousemove(function(e) {
			if(timeDrag) {
				
				var first = this.id.indexOf("Bar");
				if(first>=0){
					updateVideoBar(this.id.substring(11, this.id.length), e.pageX);
				}else{
					updateVideoBar(this.id.substring(8, this.id.length), e.pageX);
				}
			}
		});
	});
}
function updateVideoBar(id, x) {
	var progressBar = $("#progressBar"+id);
	var video = $("#video"+id)[0];
	var progress = $("#progress"+id);
	var maxduration = video.duration; //Video duraiton
	var position = x - progressBar.offset().left; //Click pos
	var percentage = position / progressBar.width();
	//Check within range
	if(percentage > 1) {
		percentage = 1;
	}
	if(percentage < 0) {
		percentage = 0;
	}
	 //Update progress bar and video currenttime
	progress.css('width', position+'px');
	progress.css('background-color', 'blue');
	video.currentTime = maxduration * percentage;
	videoTime("video"+id);
}
function volumeProgress (){
	$(document).ready(function() {
		var volumeDrag = false;   /* Drag status */
		$("[id ^= volume]").mousedown(function(e) {
			volumeDrag = true;
		});
		$("[id ^= volume]").mouseup(function(e) {
			if(volumeDrag) {
				volumeDrag = false;
				var first = this.id.indexOf("Bar");
				if(first>=0){
					updateVolumeBar(this.id.substring(9, this.id.length), e.pageY);
					
				}else{
					updateVolumeBar(this.id.substring(6, this.id.length), e.pageY);
				}
			}
		});
		$("[id ^= volume]").mousemove(function(e) {
			if(volumeDrag) {
				var first = this.id.indexOf("Bar");
				if(first>=0){
					updateVolumeBar(this.id.substring(9, this.id.length), e.pageY);
					
				}else{
					updateVolumeBar(this.id.substring(6, this.id.length), e.pageY);
				}
			}
		});
	});
}
function updateVolumeBar(id, y){
	var volumeBar = $("#volumeBar"+id);
	var video = $("#video"+id)[0];
	var volume = $("#volume"+id);
	var position = y - volumeBar.offset().top; //Click pos
	var percentage = position / volumeBar.height();
	//Check within range
	if(percentage >= 1) {
		percentage = 1;
	}
	if(percentage <= 0) {
		percentage = 0;
		var name = $("#volumeSample"+id).prop('class');
		$("#volumeSample"+id).removeClass(name);
		$("#volumeSample"+id).addClass("fa fa-volume-off");
	}
	if(percentage > 0.5){
		var name = $("#volumeSample"+id).prop('class');
		$("#volumeSample"+id).removeClass(name);
		$("#volumeSample"+id).addClass("fa fa-volume-up");
	}else if(percentage <0.5){
		if(percentage<0.4){percentage/2;}
		var name = $("#volumeSample"+id).prop('class');
		$("#volumeSample"+id).removeClass(name);
		$("#volumeSample"+id).addClass("fa fa-volume-down");
	}
	 //Update volume bar and video currenttime
	volume.css('height', position+'px');
	volume.css('background-color', 'blue');
	video.volume = percentage;
}