<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="com.yonyou.login.exception.IncorrectCaptchaException"%>

<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title>
<script>
	var exponent = '${exponent}';
	var modulus = '${modulus}';
</script>
<link href="<%=path%>/resources/css/bootstrap.css" rel="stylesheet" />
<%-- 	<link href="<%=path%>/resources/css/common.css" rel="stylesheet"/> --%>
<link href="<%=path%>/resources/css/login.css" rel="stylesheet" />
<script type="text/javascript"
	src="<%=path%>/resources/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/security.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/login.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/share.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/qrcode.js"></script>
</head>
<body>
	<div class="row">
		<div class="col-md-8 main">
			<div class="panel panel-default">
				<div class="panel-heading" style="height: 50px;">
					<h2 class="panel-title" style="float: left; padding-top: 5px;">登录</h2>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" action="<%=path%>/login/formLogin"
						id="formlogin" method="post" style="width: 80%">
						<div class="form-group">
							<label class="col-sm-4 control-label">邮箱</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="username" name="username" value="" placeholder="手机号/邮箱/用户名">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">密码</label>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="password" name="password" value="" placeholder="密码" autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">验证码</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="captcha" name="captcha" placeholder="请输入下图中的验证码"> 
								<img alt="验证码" src="<%=path%>/images/kaptcha.jpg" title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha('<%=path%>')"> (看不清?
								&nbsp;
								<a href="javascript:void(0)" onclick="javascript:refreshCaptcha('<%=path%>')">换一张</a>)
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8">
								<div class="checkbox">
									<label> <input type="checkbox" name="rememberMe"
										value="true">记住账号
									</label>
								</div>
							</div>
						</div>
						<%
							Object obj = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
							String msg = "";
							if (obj != null) {
								if (obj instanceof IncorrectCaptchaException) {
									msg = "验证码错误！";
								} else {
									msg = "帐号或者密码错误！";
								}
							}
							out.println("<div class='form-group'><div class='col-sm-offset-4 col-sm-8'><div class='error'>" + msg
									+ "</div></div></div>");
						%>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8">
								<button type="button" class="btn btn-info btn-entry"
									onclick="doLogin()" style="width: 50%;">登 录</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-md-4 other">
			<div class="panel panel-default">
				<div class="panel-heading" style="height: 50px;">
					<h2 class="panel-title" style="float: left; padding-top: 5px;">其他方式登录</h2>
				</div>
				<div class="panel-body other-login">
					<ul class="cf">
						<li class="i6"><a href="#" title="微信"><i></i>微信登录</a></li>
						<li class="i5"><a href="http://localhost:8080/example-iuap-authrbac-core/login_servlet?LoinType=QQ&isLogin=true" title="QQ"><i></i>QQ登录</a></li>
						<li class="i1"><a href="#" title="新浪微博"><i></i>微博登录</a></li>
						<li class="i4"><a href="#" title="支付宝"><i></i>支付宝登录</a></li>
					</ul>
				</div>
				<div class="panel-bottom" >
					<a style="font-size: 18px"
						onclick="document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">
						分享: </a>
				</div>
			</div>
		</div>
	</div>

	<!--分享层 -->
	<div id="light"
		style="display: none; position: absolute; top: 100px; left: 30%; width: 40%; height: 350px; padding: 16px; border: 5px solid gray; background-color: white; z-index: 1002; overflow: auto;">
		分享到 : <a href="javascript:void(0)"
			onclick="document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">
			<img src="../resources/images/shareimg/x.png" border="0"
			style="float: right;" alt="关闭">
		</a>
		<ul id="bsLogoList">
			<div id="bp-qqmb" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToTencent(document.title,location.href,document.title);"
					class="tmblog"><img
					src="../resources/images/shareimg/qqweibo.gif" border="0"
					alt="腾讯微博"></a>
				<div style="font-size: 10px;" title="腾讯微博">腾讯微博</div>
			</div>
			<div id="bp-sinaminiblog"
				style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToSina(document.title,location.href,document.title);"
					class="tmblog"><img
					src="../resources/images/shareimg/sinaweibo.gif" border="0"
					alt="新浪微博"></a>
				<div style="font-size: 10px;" title="新浪微博">新浪微博</div>
			</div>
			<div id="bp-qqim" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToQQIM(document.title,location.href,document.title);"
					class="tmblog"><img src="../resources/images/shareimg/qqim.gif"
					border="0" alt="QQ"></a>
				<div style="font-size: 10px;" title="QQ">QQ</div>
			</div>
			<div id="bp-qzone" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToQzone(document.title,location.href,document.title);"
					class="tmblog"><img
					src="../resources/images/shareimg/qzone.gif" border="0" alt="QQ空间"></a>
				<div style="font-size: 10px;" title="QQ空间">QQ空间</div>
			</div>

			<div id="bp-baiduhi" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToBaiduTieba(document.title,location.href,document.title);"
					class="tmblog"><img
					src="../resources/images/shareimg/baiduhi.gif" border="0" alt="百度"></a>
				<div style="font-size: 10px;" title="百度贴吧">百度贴吧</div>
			</div>
			<div id="bp-douban" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToDouban(document.title,location.href,document.title);"
					class="tmblog"><img
					src="../resources/images/shareimg/douban.gif" border="0" alt="豆瓣网"></a>
				<div style="font-size: 10px;" title="豆瓣网">豆瓣网</div>
			</div>
			<div id="bp-wechart" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)" onclick="ShareToWeChart();"
					class='tmblog'> <img
					src="../resources/images/shareimg/wechart.png" border="0"
					alt="微信分享">
				</a>
				<div style="font-size: 10px;" title="微信分享">微信</div>
			</div>
		</ul>
	</div>
	<!-- 二维码展示层 -->
	<div id="qrcode"
		style="display: none; position: absolute; top: 300px; left: 40%; padding: 16px; border: 2px solid gray; background-color: white; z-index: 1003; overflow: auto;">
		<a href="javascript:void(0)"
			onclick="document.getElementById('qrcode').style.display='none';document.getElementById('fade').style.display='none'">
			<img src="../resources/images/shareimg/x.png" border="0"
			style="float: right;" alt="关闭">
		</a>
	</div>
	<!--end分享层 -->
	<!--[if lt IE 9]>
        <script src="resources/js/html5shiv.min.js"></script>
        <script src="resources/js/respond.min.js"></script>
    <![endif]-->
	</div>
</body>
</html>