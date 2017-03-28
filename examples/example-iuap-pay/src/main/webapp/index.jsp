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
	var pay = function() {
		return location.href = "pay/jump";
	};
	var refund = function() {
		return location.href = "<%=basePath%>/alipay_refund_index.jsp";
	};
</script>

</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span8">
				<h3 class="text-center">这是iUAP-Pay组件的简单示例系统</h3>
				<p class="text-info text-center"></p>
				<p class="text-info text-center">此系统只是对iuap-pay组件做了基本功能展示。</p>
				<p class="text-info text-center"></p>
				<p class="text-info text-center">
					<em>想了解更多，请点击这里：<a href="http://iuap.yonyou.com">http://iuap.yonyou.com</a></em>
				</p>
				<hr />
				<dl class="dl-horizontal">
					<dt>
						<strong>支付</strong>
					</dt>
					<dd>
						<a href="javascript:pay()">支持用户使用支付宝、微信支付、财付通等主流支付工具进行在线支付</a>
					</dd>
					<dd>
						<a href="javascript:refund()">支持用户使用支付宝、微信支付进行在线退款（此处演示支付宝退款）</a>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>