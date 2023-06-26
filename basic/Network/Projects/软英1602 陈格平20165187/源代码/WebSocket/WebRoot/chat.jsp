<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body {
	background-color: lavenderblush;
}

.find {
	margin: 0px;
	margin-left: 120px; background-color : #8AC007;
	border: none;
	border-radius: 8px;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 15px;
	background-color: #8AC007
}

input#chat {
	width: 600px;
	text-align: center;
	margin: 20px;
	margin-left: 120px;
}

input#choose {
	width: 100px;
	text-align: center;
	margin: 20px;
	margin-left: 120px;
}

p {
	margin: 10px auto;
}

#list-container {
	width: 180px;
}

#console-container {
	margin: 0px auto;
	width: 400px;
}

#online-list {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 400px;
	overflow-y: scroll;
	padding: 5px;
	width: 100%;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 400px;
	overflow-y: scroll;
	padding: 5px;
	width: 100%;
}

#console p {
	padding: 0;
	margin: 0;
}
</style>
<script type="text/javascript" src="jquery-1.4.4.min.js"></script>
<script type="application/javascript">
	
	
        var Chat = {};
        //获得username和password
        var URL = "ws://localhost:8080/WebSocket/websocket/chat?username="+'${sessionScope.username}'+"&password="+'${sessionScope.password}'+"&idradio="+'${sessionScope.idradio}';
    	
        Chat.socket = null;
        Chat.connect = (function(URL) {
            if ('WebSocket' in window) {
                Chat.socket = new WebSocket(URL);
            } else if ('MozWebSocket' in window) {
                Chat.socket = new MozWebSocket(URL);
            } else {
                Console.log('Error: WebSocket is not supported by this browser.');
                return;
            }

            Chat.socket.onopen = function () {
            	
                Console.log('Info: WebSocket connection opened.');
                document.getElementById('chat').onkeydown = function(event) {
                    if (event.keyCode == 13) {
                        Chat.sendMessage();
                    }
                };
            };

            Chat.socket.onclose = function () {
                document.getElementById('chat').onkeydown = null;
                Console.log('Info: WebSocket closed.');
            };

            Chat.socket.onmessage = function (message) {
            	eval("var msg="+message.data+";");
                Console.log(msg.date+":"+msg.message);
                
                //此为在线列表
       //         if(undefined!=msg.message && username == msg.name){
      //          	
      //          }
                if (undefined != msg.customerList) {
        			$("#online-list").html("online-customerlist");
        			$(msg.customerList)
        					.each(
        							function() {
        								$("#online-list")
        										.append(
        												"<ul><li class = 'namelist'>"
        														+ this + "</li></ul>");

        							});
        		}
            };
        });

        Chat.initialize = function() {
            if (window.location.protocol == 'http:') {
                Chat.connect(URL);
            } else {
                Chat.connect('wss://' + window.location.host + '/WebSocket/websocket/chat');
            }
        };

        Chat.sendMessage = (function() {
            var message = document.getElementById('chat').value;
            var person=document.getElementById('choose').value;
        //   Console.log(document.getElementById('choose').value);
            var obj = {
            		destination: person,
        			message : $("#chat").val()
        		}
            
        		var str = JSON.stringify(obj);
        		Chat.socket.send(str);
        		$("#chat").val("");
        });

        var Console = {};

        Console.log = (function(message) {
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.innerHTML = message;
            console.appendChild(p);
            while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            }
            console.scrollTop = console.scrollHeight;
        });

        Chat.initialize();


        document.addEventListener("DOMContentLoaded", function() {
            // Remove elements with "noscript" class - <noscript> is not allowed in XHTML
            var noscripts = document.getElementsByClassName("noscript");
            for (var i = 0; i < noscripts.length; i++) {
                noscripts[i].parentNode.removeChild(noscripts[i]);
            }
        }, false);

        function service(){
        	
        }
    

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="noscript">
		<h2 style="color: #ff0000">Seems your browser doesn't support
			Javascript! Websockets rely on Javascript being enabled. Please
			enable Javascript and reload this page!</h2>
	</div>
	<div>
		<div id="welcome"
			style="align-content: center; margin: 10px auto; width: 600px;">
			<div
				style="margin: 10px; font-family: verdana; padding: 20px; border-radius: 10px; border: 5px solid lightcoral;">
				<div style="color: #40B3DF; text-align: center;">

					<span style="background-color: lightpink; color: #ffffff;">
						Welcome ${sessionScope.username} to chatroom!</span>
				</div>
			</div>
		</div>
		<div id="box" style="width: 600px; margin: auto;">
			<div id="list-container" style="float: left">
				<div id="online-list"></div>
			</div>
			<div id="console-container" style="float: right">
				<div id="console"></div>
			</div>
		</div>
		<br />
		<p>
			<input type="text" placeholder="type and press enter to chat"
				id="chat" />
		</p>
		<p>
			<!--选择我要联系的人,原始值为all，判断，如果仍然为all，则不变，向所有人发送，否则，向对应的人发送-->
			<input type="text" placeholder="person(default:all)" id="choose"
				value="all" />
		</p>
		<button class="find" type="submit"
			onclick="service()" style="font-size: 15px;">service</button>
	</div>

</body>
</html>