<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript"  src="jquery-1.4.4.min.js"></script>
<script type="text/javascript">
	var username = '${sessionScope.username}';
	var password = '${sessionScope.password}';
	
	var des = "GroupChat";
	var src;
	
	var target = "ws://localhost:8080/Chatit/chatSocket?username="+username+"&password="+password;
	var ws = new WebSocket(target);
	
	
	
	ws.onmessage = function(message)
    {
		eval("var msg = "+ message.data+";");
		
		console.info(msg);
		
		if(undefined!=msg.message)
			$("#show").append(msg.message + "<br/>");
		
		
		if(undefined!=msg.usernames){
			$("#userlist").html("");
			$("#userlist").append("<li class = 'namelist' onclick = 'privateMsg(this)' onmousemove = 'changecolor(this)' onmouseout = 'changecolorback(this)'>GroupChat</li>");
			$(msg.usernames).each(function(){
				$("#userlist").append("<li class = 'namelist' onclick = 'privateMsg(this)' onmousemove = 'changecolor(this)' onmouseout = 'changecolorback(this)'>"+this+"</li>");
				
			});
		}	
		
	}

    function changecolor(li){
    	li.style.background = "#ffbfb7";
    	console.info("changecolor");
    }
	
    function changecolorback(li){
    	li.style.background = "#d75a4a";
    	li.style.color = "white";
    	console.info("changecolorback");
    }
	
	function sendmsg()
    {
		var obj = {
			to:des,
			msg:$("#writting").val()
		}
		
		var str = JSON.stringify(obj);
		ws.send(str);
		$("#writting").val(""); 
		
    }
	
	
	function privateMsg(to){
		
		$(".namelist").each(function(){
			$(this).css("background-color","#d75a4a");
		})
		
		des = to.innerHTML;
	
	}
	
		
	 String.prototype.endWith=function(str){
		  if(str==null||str==""||this.length==0||str.length>this.length)
		     return false;
		  if(this.substring(this.length-str.length)==str)
		     return true;
		  else
		     return false;
		  
	}
		 
	 String.prototype.startWith=function(str){
		  if(str==null||str==""||this.length==0||str.length>this.length)
		   return false;
		  if(this.substr(0,str.length)==str)
		     return true;
		  else
		     return false;
	}
	 
	 function showName(){
		 $("#headline").append("<h2>"+username+"</h2>");
	 }
	

</script>

</head>


<body>

		<form>
		<div align = "center">
		<img src = "chathead.jpg" style = "width:600px ; margin-top:50px" >
		</div>
		<div align = "center">

		<table>

			<tr><!--on the very left  name list-->
				<td rowspan = "3">
					<div style = "color:white;background:rgb(215,90,74); width: 150px;height:450px; padding-top:20px">
						<ul style="list-style-type:none; margin-left:0px" id = "userlist" >
							<li>GroupChat</li>
						</ul>					
					</div>
				</td>


				<td style = "height:300px"><!--the show pannle-->
				<textarea id = "show" disabled = "value" style="width: 400px; height: 300px; background: white;resize:none" ></textarea>
				
				</td>
				

			</tr>

			<tr style = "height:100px">
				
				<td>
				<div><h4 style = "margin-top:10px">Write your message here:</h4>
				</div>
				<div style = "height:70px">
				<input type = "text" id = "writting" style="width: 400px; height:70px;padding:0px"><dr/>
				</div>
				

				</td>
				
			</tr>

			<tr>
				
				<td >
				<div align = "center">
				<input style = "height:35px;width:150px;color:white;background:rgb(56,69,79);border:none" type="button" value = "send it now" onclick = "sendmsg()">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

				<input style = "height:35px;width:150px;color:white;background:rgb(56,69,79);border:none" type = "button" value = "clear the window" >
				</div>
				</td>
			</tr>

		</table>
		</div>
	</form>








	</body>
</html>