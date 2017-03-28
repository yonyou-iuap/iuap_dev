<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page import="com.yonyou.login.exception.IncorrectCaptchaException" %>

<%
	request.setCharacterEncoding("utf-8"); 
	response.setCharacterEncoding("utf-8"); 
	String path = request.getContextPath();
	String basePath = request.getScheme() 
			+ "://" + request.getServerName() 
			+ ":" + request.getServerPort() 
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>用户管理</title>
	<link href="<%=path%>/resources/css/bootstrap.css" rel="stylesheet"/>
<%-- 	<link href="<%=path%>/resources/css/common.css" rel="stylesheet"/> --%>
	<link href="<%=path%>/resources/css/login.css" rel="stylesheet"/>  
	<script type="text/javascript" src="<%=path%>/resources/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/security.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/login.js"></script>
</head>
<body>
	<div class="row">
		<div class="col-md-8 main">
			<div class="panel panel-default">
				<div class="panel-heading" style="height: 50px;">
					<h2 class="panel-title" style="float:left;padding-top: 5px;">用户管理-新增用户</h2>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" action="save" method="post" style="width:80%" >
						<div class="form-group">
							<label class="col-sm-4 control-label">登录名</label>
							<div class="col-sm-8">
								<input type="text" class="form-control"  id="loginName" name="loginName" value="" placeholder="手机号/邮箱/用户名">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">昵称</label>
							<div class="col-sm-8">
								<input type="text" class="form-control"  id="name" name="name" value="" placeholder="昵称">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">密码</label>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="password" name="plainPassword" value="" placeholder="密码" autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">角色</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="roles" name="roles" value="" placeholder="多个角色时，使用','分割" autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8">
							<input type="submit" class="btn btn-info" style="width: 50%;" value="新增用户" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
    </div>
</body>
</html>