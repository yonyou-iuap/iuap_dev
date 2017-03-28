<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String path = request.getContextPath();
%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户信息</title>
<link href="<%=path%>/resources/css/bootstrap.css" rel="stylesheet" />
<link href="<%=path%>/resources/css/login.css" rel="stylesheet"/>  
</head>
<body>
	<div class="row common-center">
		<div class="container-fluid main">
			<div class="row-fluid">
				<div class="span2"></div>
				<div class="span6">
					<c:forEach items="${allUsers}" var="user" varStatus="vs">
<!-- 						<p class="text-center text-success"><s:property value="#vs.index+1"/></p> -->
						<p class="text-center text-primary">登录名：${user.loginName}</p>
						<p class="text-center text-primary">昵称：${user.name}</p>
						<p class="text-center text-primary">角色：${user.roles}</p>
						<p class="text-center text-primary">创建时间：${user.registerDate}</p>
						<hr>
					</c:forEach>
				</div>
				<div class="span4"></div>
			</div>
		</div>
	</div>
</body>
</html>