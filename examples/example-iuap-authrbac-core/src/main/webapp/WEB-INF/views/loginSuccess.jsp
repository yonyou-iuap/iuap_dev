<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String path = request.getContextPath();
%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>登录成功</title>
<link href="<%=path%>/resources/css/bootstrap.css" rel="stylesheet" />
<link href="<%=path%>/resources/css/login.css" rel="stylesheet"/>  
<script type="text/javascript">
	var addUser = function() {
		return location.href = "<%=path%>/user/manage";
	};
	var loginOut = function(userid) {
		return location.href = "<%=path%>/login/logout/" + userid;
	};
</script>
</head>
<body>
	<div class="row common-center">
		<div class="container-fluid main">
			<div class="row-fluid">
				<div class="span2"></div>
				<div class="span6">
					<p class="text-center text-success"></p>
					<p class="text-center">
						<label>Welcome ,</label>
						<label class=" text-primary">
							<shiro:authenticated>
								<a href="<%=path %>/user/view/${user.id}">${user.name}</a>
							</shiro:authenticated>
						</label> 
					</p>
					<p class="text-center">
						<label>你的角色是：</label>
					 	<label class=" text-primary">${user.roles}</label>
					 </p>
					<p class="text-center">
						<!--角色为admin的用户登录后会显示添加按钮，否则不显示-->
						<shiro:hasRole name="admin">
							<button type="button" class="btn btn-info btn-entry" onclick="addUser();" style="width:10%;">新增用户</button>
						</shiro:hasRole>
					</p>
					<p class="text-center text-success"></p>
					<p class="text-center ">
						<button type="button" class="btn btn-danger" onclick="loginOut(${user.id});" style="width:20%;">登出</button>
					</p>
				</div>
				<div class="span4"></div>
			</div>
		</div>
	</div>
</body>
</html>