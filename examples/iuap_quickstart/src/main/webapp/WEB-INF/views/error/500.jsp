<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%
    //设置返回码200，避免浏览器自带的错误页面
    response.setStatus(500);
    //记录日志
    Logger logger = LoggerFactory.getLogger("500.jsp");
    logger.error(exception.getMessage(), exception);
%>

<!DOCTYPE html>
<html>
<head>
    <title>500 - 系统内部错误</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
<h2>500 - 系统发生内部错误.</h2>
<div>
    <div><b>提示信息</b>：<p>${resp.message}</p></div>
    <div><b>详细信息</b>：<p>${resp.detailMsg}</p></div>
    <div><b>操作提示</b>：<p>${resp.operate}</p></div>
    <div><b>堆栈</b>：<p>${resp.stack}</p></div>
</div>
</body>
</html>
