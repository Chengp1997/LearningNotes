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
    	li.style.background = "green";
    	console.info("changecolor");
    }
	
    function changecolorback(li){
    	li.style.background = "teal";
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
			$(this).css("background-color","teal");
		})
		
		to.style.background = "green";
		
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
		<div id = "headline" align = "center" style = "background:#548d80; height: 80px"><h2 id = "test" style = "color:white">Let's chat it!</h2></div>
		<table style="width: 954px; height: 237px" boader = "0">
			
			<tr>
				<td rowspan = "2" style = "background:teal; width: 242px">
					<div>
						<ul id = "userlist">
							<li>GroupChat</li>
						</ul>
					
					</div>
				
				</td>
				<td>
					<textarea id = "show" disabled = "value" style="width: 676px; height: 131px; background: white;"></textarea>
			  </td>
			</tr>
			
			
			
			<tr>
				<td>
						<input type = "text" id = "writting" style="width: 669px; "><dr/>
					
					
					
						<input type="button" value = "send it" onclick = "sendmsg()">
					
					
				</td>			
			</tr>
		</table>
	</form>








	</body>
</html>