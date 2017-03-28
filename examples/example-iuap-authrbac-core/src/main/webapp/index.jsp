<%@ page contentType="text/html;charset=UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8" />
<title>IEOP 示例首页</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/bootstrap.css" />
<script type="text/javascript" src="resources/js/jquery-1.11.2.js"></script>
<script type="text/javascript">
	var login = function() {
		return location.href = "login/jump";
	};
	var pay = function() {
		return location.href = "pay/jump";
	};
	var print = function() {
		return location.href = "<%=basePath%>print.jsp";
	};
	var ecadapter = function() {
		return location.href = "<%=basePath%>/ecadapter_servlet";
	};
</script>

</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span8">
				<h3 class="text-center">这是iUAP应用组件简单示例系统</h3>
				<p class="text-info text-center"></p>
				<p class="text-info text-center">此系统只是对iUAP某几个应用支撑组件做了基本功能展示。</p>
				<p class="text-info text-center"></p>
				<p class="text-info text-center">
					<em>想了解更多，请点击这里：<a href="http://iuap.yonyou.com">http://iuap.yonyou.com</a></em>
				</p>
				<hr />
				<dl class="dl-horizontal">
					<dt>
						<strong>登录、权限管理</strong>
					</dt>
					<dd>
						<a href="javascript:login()">鉴别登录系统的用户是合法的使用者。控制用户在应用系统中对受保护资源是否拥有相应的访问权限和相应的操作权限</a>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>