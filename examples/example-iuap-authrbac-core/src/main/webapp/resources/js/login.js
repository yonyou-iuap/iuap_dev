$(function(){
//	$('.navbar-static-top #top-nav').hide();
//	$('.navbar-static-top #top-login').show();
	
	$(document).keydown(function(e){//键盘回车执行登录
		var evt = window.event ? window.event : e ;
		if(evt.keyCode == 13){
//			$("div.panel-body form").submit();
			doLogin();
		}
	});
	
})

function refreshCaptcha(basePath){
	document.getElementById("img_captcha").src=basePath + "/images/kaptcha.jpg?t=" + Math.random();
}

function doLogin() {
	var plainPassword = $("#password").val(); 
	if(plainPassword.indexOf("_encrypted") < 0){
		var key = RSAUtils.getKeyPair(exponent, '', modulus);
		var encryptedPwd = RSAUtils.encryptedString(key, plainPassword);
		$("#password").val(encryptedPwd + "_encrypted"); 
	}
	
    $('#loginsubmit').text('正在登录...');
    
    $('#formlogin').submit();
    
//    var loginUrl = "http://localhost:8080/ieop/login/ajaxlogin";
//    var uuid = Math.random().toString(36);
//    $.ajax({
//        url: loginUrl + "?uuid=" + uuid ,
//        type: "POST",
//        dataType: "json",
//        contentType: "application/x-www-form-urlencoded; charset=utf-8",
//        data: $("#formlogin").serialize(),
//        error: function () {
//           alert("用户名密码输入错误或网络超时，请重试!");
//           $("#loginsubmit").html("登录");
//        },
//        success: function (result) {
//            if (result) {
//                if ("success"==result.flag) {
//                	//console.log(result.msg);
//                	// 正常逻辑请补齐浏览器判断
//                    var isIE = false;
//                    if (isIE) {
//                        var link = document.createElement("a");
//                        link.href = result.successUrl;
//                        link.style.display = 'none';
//                        document.body.appendChild(link);
//                        link.click();
//                    } else {
//                    	var userName = result.user.name;
//                        window.location = result.successUrl + "?userName=" + userName;
//                    }
//                    return;
//                } else {
//                	//console.error(result.msg);
//                	alert(result.msg);
//                }
//            }
//            $("#loginsubmit").html("登录");
//        }
//    });
}
