<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%
	request.setCharacterEncoding("utf-8"); 
	response.setCharacterEncoding("utf-8"); 
	String path = request.getContextPath();
	String basePath = request.getScheme() 
			+ "://" + request.getServerName() 
			+ ":" + request.getServerPort() 
			+ path + "/";
%>
<html>
<head>
<title>iUAP支付示例</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/pay_main.css" />
<script type="text/javascript" src="<%=path%>/resources/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/bank_selected.js"></script>
<%

	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String dateTime = format.format(date);

%>
</head>
<body>
	<div id="main">
		<div class="cashier-nav"><span class="title">iUAP支付-模拟订单</span></div>
		<form name="payment_form" action="<%=path%>/pay/bill" method="post" target="_self">
			<!-- 畅捷支付需要传递的一些参数  开始 -->
			<input type="hidden" name="is_anonymous" value="Y" />
			<input type="hidden" name="pay_method" value="2" />		
			<!-- 畅捷支付需要传递的一些参数  结束-->
			<div id="form_body" >
				<dl class="content">
					<div>
						<dt nowrap><label class="red-star">*</label>支付金额（元）：</dt>
						<dd nowrap><strong><input type="text" name="total_fee" value="0.01" /></strong></dd>
					</div>
					
					<div>
						<dt nowrap>订单标题：</dt>
						<dd nowrap><strong><input type="text" name="order_name" value="配备Retina显示屏的新一代MacBook" /></strong></dd>
					</div>
					
					<div>
						<dt nowrap>订单号：</dt>
						<dd nowrap><strong><input type="text" name="out_trade_no" value="2016-01-26-002" /></strong></dd>
					</div>
					
					<div>
						<dt nowrap>商品描述：</dt>
						<dd nowrap><strong><input type="text" name="goods_des" value="配备Retina显示屏的新一代MacBook" /></strong></dd>
					</div>
					
					<div>
						<dt nowrap>商品数量：</dt>
						<dd nowrap><strong><input type="text" name="trade_quantity" value="1" /></strong></dd>
					</div>
					
					<div>
						<dt nowrap>return_url：</dt>
						<dd nowrap><strong><input type="text" name="pay_return" value="http://iuap.yonyou.com/pay_return" /></strong></dd>
					</div>
					
					<div>
						<dt nowrap>noyify_url：</dt>
						<dd nowrap><strong><input type="text" name="pay_notify" value="http://iuap.yonyou.com/pay_notify" /></strong></dd>
					</div>
					
					<div>
						<dt nowrap>订单创建时间：</dt>
						<dd nowrap><strong><input type="text" name="orderTime" value="<%=dateTime%>" readonly="readonly" /></strong></dd>
					</div>
					
					<div>
						<dt nowrap><label class="red-star"> * </label>付款方式：</dt>
						<dd nowrap>
							<div class="pay_channel_list">
								<div><input type="radio" name="pay_type" style="width:15px;" value="ALI_APY" onclick="notShowBankList()" checked/><label>支付宝账户支付/扫码支付</label><br></div>
								<div><input type="radio" name="pay_type" style="width:15px;" value="ALI_BANK" onclick="showBankList()"/><label>支付宝网银支付</label><br></div>
								<div><input type="radio" name="pay_type" style="width:15px;" value="ALI_GU" onclick="notShowBankList()" /><label>支付宝担保交易</label><br></div>
								<div><input type="radio" name="pay_type" style="width:15px;" value="WX_SCAN" onclick="notShowBankList()" /><label>微信扫码支付</label><br></div>
								<div><input type="radio" name="pay_type" style="width:15px;" value="CHANJETPAY" onclick="notShowBankList()" /><label>畅捷支付</label><br></div>
							</div>
						</dd>
					</div>
					
					<div class="bank-list">
						<dt>付款银行：</dt>
						<dd class="payment-list type-check">
							<ul class="pl-wrap">
								<li class="pl-item selected">
									<span class="bank-logo" id="bank-icbc">中国工商银行</span>
									<i class="pl-i-selected"></i>
									<input type="radio" class="radio_bank_code" name="pay_bank" value="ICBC" checked="checked">
								</li>
								<li class="pl-item">
									<span class="bank-logo" id="bank-ccb">中国建设银行</span>
									<i class="pl-i-selected"></i>
									<input type="radio" class="radio_bank_code" name="pay_bank" value="CCB">
								</li>
								<li class="pl-item"><span class="bank-logo" id="bank-cmb">招商银行</span>
									<i class="pl-i-selected"></i>
									<input type="radio" class="radio_bank_code" name="pay_bank" value="CMB">
								</li>
								<li class="pl-item"><span class="bank-logo" id="bank-abc">中国农业银行</span>
									<i class="pl-i-selected"></i>
									<input type="radio" class="radio_bank_code" name="pay_bank" value="ABC">
								</li>
							</ul>
						</dd>
					</div>
					
					<dd>
						<span class="new-btn-login-sp">
							<button class="new-btn-login" type="submit" style="text-align: center;">确 认</button>
						</span>
					</dd>
					
				</dl>
			</div>
		</form>
	</div>
</body>
</html>