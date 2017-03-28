<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(403);%>

<!DOCTYPE html>
<html>
<head>
	<title>403 - 请求被阻止</title>
</head>

<body>
	<h2>403 - 请求被阻止.</h2>
	<p><span style="color:red">详细信息: 无授权！</span><br></p>
</body>
</html>