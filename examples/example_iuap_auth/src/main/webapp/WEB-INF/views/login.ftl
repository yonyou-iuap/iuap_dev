<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <title>登录</title>
    <link rel="stylesheet" href="${ctx}/css/login.css" />
</head>
<body>
	<script>
		window.$ctx = '${ctx}';
	</script>
	<form  method="post" id="formlogin" action="${ctx}/login/formLogin">
		<div id="entry" class=" w1">
			<div id="bgDiv" class="mc ">
				<div clstag="pageclick|keycount|20150112ABD|48" id="entry-bg" style="width: 511px; height: 455px; background: url(&quot;${ctx}/images/dl1.png&quot;) no-repeat scroll 0px 0px transparent; position: absolute; left: -44px; top: -44px;">
				</div>
				<div class="form " >
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
				</div>
			</div>
			<div class="free-regist">
				<span><a  href="#">免费注册&gt;&gt;</a></span>
			</div>
		</div>
	</form>
</body>
</html>