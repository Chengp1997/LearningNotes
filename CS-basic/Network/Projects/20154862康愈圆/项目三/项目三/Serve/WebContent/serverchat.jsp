<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Server</title>
<script type="text/javascript"  src="jquery-1.4.4.min.js"></script>
<script type = "text/javascript">
  	var username = '${sessionScope.username}';
  	var id = '${sessionScope.id}'
  	
  	
  	var target = "ws://localhost:8080/Serve/chatSocket?username="+username+"&id="+id;
  	var ws = new WebSocket(target);
  	var des;
  	
  	ws.onmessage = function(message)
    {
  		console.info(username);
		eval("var msg = "+ message.data+";");
		
		console.info(msg);
		
		if(undefined!=msg.message && username == msg.servername)
			$("#show").append(msg.message + "<br/>");
		
		
		if(undefined!=msg.serverNames){
			$("#serverlist").html("");
			$(msg.serverNames).each(function(){
				$("#serverlist").append("<li class = 'namelist' onclick = 'privateMsg(this)' onmousemove = 'changecolor(this)' onmouseout = 'changecolorback(this)'>"+this+"</li>");
				
			});
		}
			
		if(undefined!=msg.curCustomerNames && username == msg.servername){
			$("#customerlist").html("");
			$(msg.curCustomerNames).each(function(){
				$("#customerlist").append("<li class = 'namelist' onclick = 'privateMsg(this)' onmousemove = 'changecolor(this)' onmouseout = 'changecolorback(this)'>"+this+"</li>");
				
			});
		}		
	}
  	
  	
  	
  	function sendmsg(){
  		var obj = {
  				to:des,
  				from:username,
  				msg:$("#writing").val()
  			}
  			console.info(obj);
  			var str = JSON.stringify(obj);
  			ws.send(str);
  			$("#writing").val("");
  	}
  	
  	
	function privateMsg(to){
			
			
			des = to.innerHTML;
			console.info(des);
		
	}
	
	function changecolor(li){
    	li.style.background = "#cae8f0";
    }
	
    function changecolorback(li){
    	li.style.background = "#a2d6e3";
    }
	
	
  	
 //	console.info(username);
 	

</script>

<style type = "text/css">
	.b{
		height:30px;
		width:150px;
		background:rgb(45,95,107);
		color:white;
		border:none;
		
	}
</style>
</head>
<body>
	<div align = "center" style = "margin:50px">
		<img src = "heads.jpg" style = "width:800px">
	</div>
	<div align = "center">
	<table style = "border:none">
	

		<tr>

			
			<td style = "width:150px;background:rgb(162,214,227);height:680px;color:rgb(45,95,107)">
				<div style = "width:150px;background:rgb(162,214,227);height:680px;color:rgb(45,95,107)">

				<h3>customers</h3>
					<ul id = "customerlist">
					</ul>
					
					<h3>servers</h3>
					<ul id = "serverlist">
					</ul>

				</div>
					
			</td>


			<td style = "background:rgb(154,185,192)">
			
				<div align = "center">
					<textarea id = "show" style="width: 400px; height: 300px; resize:none ;margin:50"></textarea>
				</div>

				<div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					Write your message here:
				</div>

				<div align = "center">
					<input id = "writing" type = "text" style="width: 400px; height: 200px">
				</div>

				<div align = "center" style = "margin:20px">
					<input class = "b" type="button" value = "send the message" onclick = "sendmsg()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input class = "b" type="button" value = "clear the text window">
				</div>
			</td>
			
			
		</tr>
	</table>

	</div>
	<br/>
	<br/>
	<br/>
	<br/>


</body>
</html>