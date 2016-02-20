
var user = "";


function trigger(){
	$(document).ready(function(){
		$('#user').click(function(){
			
			user = $("input[name = user]").val();
			notify();
			alert(user);
		});
	});
}
function notify(){
	var pusher = new Pusher("abf991c2374533ca183c");
	var notificationsChannel = pusher.subscribe('notifications');
	//do something with our new information
	notificationsChannel.bind(user, function(notification){
		// assign the notification's message to a <div></div>
		
	    var message = notification.message;
		//toastr.success(message);
	    $('div.notification').html($('div.notification').html()+"<br>"+"-"+message);
	});
}
function send(){
	$(document).ready(function(){
		$('button.submit-notification').click(function(){
		    // get the contents of the input
		    var text = $('input.create-notification').val();
		    // POST to our server
		    $.post("http://127.0.0.1/notification/index.php", "message=" + text + "&user=" + user, function(data, status){
				if(data.substring(data.lastIndexOf("=>")+3, data.length-3)!="200"){
					alert("failed"+data.substring(data.indexOf("=>")+3, data.length-3));
				}
			});
		});
	});
}
	function showFriends(){
		$(document).ready(function(){
			var user = $("#user").val();
			$.ajax({
				type : "POST",
	    	    url: "http://localhost:8888/M/social/home/friends",
	    	    data: "user="+user,
	    	    cache: false,
	    	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
	    	    success:  function(friendString){
	    			friendString = friendString.substring(0, friendString.length-1);
	    			var friends = friendString.split("-");
	    			var friend = "", text = "";
	    			
	    			for(i = 0; i< friends.length; i++){
	    				friend = friends[i];
	    				var data = friend.split(" ")
	    				text += "<tr><td>"+
	    				data[0]+"</td><td>"+
	    				data[1]+"</td><td>"+
	    				data[2]+"</td>"+"</tr>";
	    			}
	    			$("table").html(text);
	    	        
	    	    },
	    	    error: function(data){
	    	    	alert(data);
	    	    }
			});
			
		});
	}