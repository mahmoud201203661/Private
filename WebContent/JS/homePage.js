function myRect(){
	var c = document.getElementById("myCanvas");
	var ctx = c.getContext("2d");
	ctx.fillStyle = "#FF0000";
	ctx.fillRect(0,0,150,75);
}
function play(){
	alert("play");
	document.getElementById("menu").style.WebkitAnimationPlayState = "running"; // Code for Chrome, Safari, and Opera
    document.getElementById("menu").style.mozAnimationPlayState = "running";
}
function pause(){
	alert("pause");
	document.getElementById("menu").style.WebkitAnimationPlayState = "paused"; // Code for Chrome, Safari, and Opera
    document.getElementById("menu").style.mozAnimationPlayState = "paused";
}
function myCanvas() {
	var c = document.getElementById("myCanvas");
	var ctx = c.getContext("2d");
	var img = document.getElementById("scream");
	ctx.drawImage(img,10,10);
}
function myEvent(){
	
	var anim = document.getElementById("scream");
	anim.addEventListener("animationstart", AnimationListener, false);
	anim.addEventListener("animationiteration", AnimationListener, false);
	anim.addEventListener("animationend", AnimationListener, false);
}
function AnimationListener(e){
	if (e.animationName == "scrollView"){
		var newPath = arr[j];
		if(e.type.indexOf("animationstart") >= 0) {
			document.getElementById("scream").src = newPath;
			if(j >= arr.length-1){
				j = 0;
			}else{
				j ++;
			}
		}else if (e.type.indexOf("animationiteration") >= 0) {
			newPath = arr[j];
			document.getElementById("scream").src = newPath;
			if(j >= arr.length-1){
				j = 0;
			}else{
				j ++;
			}
		}else if (e.type.indexOf("animationend") >= 0) {
			newPath = arr[j];
			document.getElementById("scream").src = newPath;
			if(j >= arr.length-1){
				j = 0;
			}else{
				j ++;
			}
		}	
	}
}
function mytest(){
}