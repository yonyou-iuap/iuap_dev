<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <title>登录</title>
    <link rel="stylesheet" href="${ctx}/style/login.css" />
    <!--[if lt IE 9]>
    <script src="${ctx}/vendor/html5shiv.min.js"></script>
    <script src="${ctx}/vendor/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<script>
		window.$ctx = '${ctx}';
	</script>
	<form method="post" id="formlogin" action="${ctx}/login/formLogin">
		<div id="entry" class=" w1">
			<div id="bgDiv" class="mc ">
				<div clstag="pageclick|keycount|20150112ABD|48" id="entry-bg" style="width: 511px; height: 455px; background: url(&quot;${ctx}/static/dl1.png&quot;) no-repeat scroll 0px 0px transparent; position: absolute; left: -44px; top: -44px;">
				</div>
				<!--
				此登录页面简单的示意一下登录的过程，主要为验证iuap-auth组件。
				此处应该进行输入的验证和前端js的RSA加密，请参考security.js中的方法进行加密，配合后端的RsaUtils进行解密
				-->
				<div class="form">
					<div class="item fore1">
						<span>邮箱/用户名/已验证手机</span>
						<div class="item-ifo">
							<input type="text" autocomplete="off" tabindex="1" value="" class="text" name="username" id="username">				
						</div>
					</div>
					<div class="item fore2">
						<span>密码</span>
						<div class="item-ifo">
							<input type="password" autocomplete="off" tabindex="2" class="text" name="password" id="password">
						</div>
					</div>
					<div class="item login-btn2013">
						<button type="submit" class="btn btn-danger btn-entry">登     录</button>
					</div>
					<div style="float: left; color: black;">
						<input name="rmbUser" id="rmbUser" type="checkbox" class="jpwd" style="margin-top: 2px;">记住密码
					</div>
					<div style="left: 50%;position: relative;">
						<a href="#">忘记密码</a>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script src="${ctx}/vendor/jquery/jquery-1.11.2.js" type="text/javascript"></script>
	<script src="${ctx}/vendor/security/security.js"></script>
</body>
</html>