window.errors={};
window.errors.error = function(XMLHttpRequest) {
	var status = XMLHttpRequest.status;
	var msg = XMLHttpRequest.responseText;
	msg = msg.replace("\"","").replace("\"","");
	try{
		msg = eval(msg);				
	}catch(e){
		msg = status+",操作出现异常!";
	}
	u.showMessage({msg:msg});
	var tipmsg = msg;
	if(msg == "NoPermission!"){
		tipmsg = "您没有权限进行操作";
	}else if(msg == "auth check error!"){
		tipmsg = "请先登录再进行操作，可能您的会话信息已超时！";
	}
	u.confirmDialog({
		 msg : tipmsg,
		 title : "提示",
		 onOk : function() {
			if(status != undefined && status == '306'){
				if(msg == "NoPermission!"){
					
				}else{
					window.location.href="login.html";
					//return;							
				}
			}
		 }
	 });
};