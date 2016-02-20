function validate(){
	$(document).ready(function(){
	    $("#email").blur(function(){
	    	var email = document.getElementById("email");
	    	
	    	var value = email.value;
	    	//https://www.facebook.com/login.php?login_attempt=1&amp;lwv=110
	    	alert("hhhhhhhhhh");
	    	$.ajax({
	    	    type: "POST",
	    	    url: "http://masterface-989.appspot.com/social/home",
	    	    data: "email=20120366&password=123",
	    	    cache: false,
	    	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
	    	    processData: false,
	    	    success:  function(data){
	    	        //alert("---"+data);
	    	        alert("Settings has been updated successfully.");
	    	        
	    	    }
	    	});
//			$.post("http://masterface-989.appspot.com/social/home",{email:"20120366",password:"123"}, function(data, status){
//				document.getElementById("notify").style.display = "inline";
//				document.getElementById("notify").innerHTML = status;
//			});
	    });
	});
}
function prepareDate() {
    var x = document.getElementById("year");
    var date = new Date();
    var currentYear = date.getYear();
    //alert(currentYear);
    for (var int = 1998; int > 1949; int--) {
    	var option = document.createElement("option");
    	option.text = int;
    	x.add(option);
    }
}
function loadDoc() {
	$(document).ready(function(){
		$("#email").blur(function(){
	    	var xhttp = new XMLHttpRequest();
	    	xhttp.onreadystatechange = function() {
	    		if (xhttp.readyState == 4 && xhttp.status == 200) {
	    			document.getElementById("notify").style.display = "inline";
	    			document.getElementById("notify").innerHTML = xhttp.responseText;
			    }
	    	};
	    	xhttp.open("POST", "/social/home", true);
	    	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	    	xhttp.send("userName=20120366&password=123");
	    });
	});
	}