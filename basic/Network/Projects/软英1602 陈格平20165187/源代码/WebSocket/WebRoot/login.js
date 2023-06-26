function login(){

				var pwd=document.getElementById("password").value;
				//进行判断
					if(pwd=="123"){
						return true;
				}else{
					alert("password incorrect!");
					return false;
				}
			}
