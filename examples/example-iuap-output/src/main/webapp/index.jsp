<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>订单打印</title>
		<link href="<%=path%>/css/print.css" rel="stylesheet" type="text/css" />
<!-- 		<script type="text/javascript" src="/trd/jquery/jquery-1.11.2.js"></script> -->
		<script type="text/javascript" src="<%=path%>/js/jquery-2.1.4.min.js"></script>
		<script type="text/javascript" src="<%=path%>/js/underscore.js"></script>
		<script type="text/javascript" src="<%=path%>/js/PrintModel.js"></script>
		<script type="text/javascript" src="<%=path%>/js/orderdetail.js"></script>
	</head>
	<body>
		<div align="center">
		<button onclick="printDirectCurrentPage()"   >
			打印此订单
		</button>
		 
		<button onclick="printByPDFTemplate('<%=path%>')"   >
			导出为PDF
		</button>
		<button onclick="printByHTMLTemplate()"   >
			html模板打印
		</button>
		</div>
		<div class="container" id="printarea">
<!-- 			<div class="logo"> -->
<!-- 				<img src="/css/ui-new/i/logo.png"> -->
<!-- 			</div> -->
			<div class="orderlist">
				<table class="ordertable">
					<tr>
						<td>订单编号：<span>20150920001</span></td><td>订购时间：<span>2015-09-12 12:45:45</span></td>
					</tr>
					<tr>
						<td>客户姓名：<span>客户姓名</span></td><td>联系方式：<span>13436989458</span></td>
					</tr>
					<tr>
						<td colspan="2">配送地址：<span>北京市海淀区北清路68号用友软件园</span></td>
					</tr>
				</table>
			</div>
			<div class="goodslist">
				<table class="goodstable">
					<tr>
						<th width="10%">商品编号</th>
						<th width="70%">商品名称</th>
						<th width="10%">数量</th>
						<th width="10%">商品金额</th>
					</tr>
					<tr>
						<td width="10%">000001</td>
						<td width="70%">图解CSS3：核心技术与案例实战 </td>
						<td width="10%">1</td>
						<td width="10%">￥57.30</td>
					</tr>
				</table>
			</div>
			<div class="totolAmount">
				订单支付金额：<span>￥57.30</span>
			</div>
		</div>
	</body>
</html>