<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page session="false"%>
<%
	request.setCharacterEncoding("utf-8"); 
	response.setCharacterEncoding("utf-8"); 
	String path = request.getContextPath();
	String open_url = (String)request.getAttribute("open_url");
	
%>
<html>
<head>
<title>畅捷支付-订单确认页面</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/pay_main.css">
</head>
<body  onload="page_onload()">
<input type="hidden" id="open_url" name="open_url" value="<%=open_url%>" />
</body>
<script>
	function page_onload() {debugger;
		var open_url=document.getElementById("open_url").value;
		window.open(open_url,"_self")
	}
</script>
</html>
