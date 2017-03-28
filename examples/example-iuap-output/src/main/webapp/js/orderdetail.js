var ctxpath = "";

/**
 * 直接打印页面，具体打印区域由参数指定
 */
function printDirectCurrentPage() {
	Print.printByElementIds([ "printarea" ]);
}
 
/**
 * 通过html模板打印
 */
function printByHTMLTemplate() {
	Print.printByTemplate();
}

/**
 * 打印pdf文档
 */
function printByPDFTemplate(actionPath) {
	alert(actionPath);
	var form = $('<form>', {
		action : actionPath+'/PrintController',
		method : 'post'
	}).appendTo(document.body);
	$('<input>', {
		type : 'hidden',
		name : 'type',
		value : 'pdf'
	}).appendTo(form);
	form.submit();
}

///////////////////////////////////////////////
function getJsonData() {
	var obj = {
		no : "20150920001",
		buydate : "2015-09-12 12:45:45",
		customername : "刘XX",
		phoneno : "134XXXXXXXX",
		customeraddr : "北京市海淀区北清路68号用友软件园",
		totalamount : '57.30',
		goodslist : [ {
			no : "000001",
			name : "图解CSS3：核心技术与案例实战",
			count : "1",
			amount : '57.30'
		} ]
	};

	return obj;
}

function getTemplate() {
	return getTemplate1();
}

function getStyle() {
	return getStyle1();
}

////////////////////////////////////////////

function getTemplate1(){
	var template = '<div class="container">';
//	template += '<div class="logo"><img src="'+ctxpath+'/css/ui-new/i/logo.png"></div>';
	template += '<div class="orderlist">';
	template += '<table class="ordertable">';
	template += '<tr><td>订单编号：<span><%=printdata.no%></span></td><td>购买日期：<span><%=printdata.buydate%></span></td></tr>';
	template += '<tr><td>客户姓名：<span><%=printdata.customername%></span></td><td>联系方式：<span><%=printdata.phoneno%></span></td></tr>';
	template += '<tr><td colspan="2">配送地址：<span><%=printdata.customeraddr%></span></td></tr>';
	template += '</table></div>';
	template += '<div class="goodslist">';
	template += '<table class="goodstable">';
	template += '<tr><th width="10%">商品编号</th><th width="70%">商品名称</th><th width="10%">数量</th><th width="10%">金额（元）</th></tr>';
	template += '<% for(var i = 0; i < printdata.goodslist.length; i++) { %>';
	template += '<% var item = printdata.goodslist[i] %>';
	template += '<tr><td width="10%"><%=item.no%></td><td width="70%"><%=item.name%> </td><td width="10%"><%=item.count%></td><td width="10%">￥<%=item.amount%></td></tr>';
	template += '<% } %></table></div>';
	template += '<div class="totolAmount">订单总额：<span>￥<%=printdata.totalamount%></span>&nbsp; 元</div></div>';

	return template;
}

function getTemplate2(){
	var template ='<div class="container printstyle">';
//	template += '<div class="logo"><img src="/css/ui-new/i/logo.png"></div>';
	template += '<div class="orderlist">';
	template += '<div class="title">订单信息:</div><table>';
	template += '<tr><td>订单编号：<span><%=printdata.no%></span></td>';
	template += '<td>购买日期：<span><%=printdata.buydate%></span></td>';
	template += '<td>客户姓名：<span><%=printdata.customername%></span></td></tr>';
	template += '<tr><td>联系方式：<span><%=printdata.phoneno%></span></td>';
	template += '<td colspan="2">配送地址：<span><%=printdata.customeraddr%></span></td></tr></table></div>';
	template += '<div class="goodslist"><table class="goodstable">';
	template += '<tr><th width="10%">商品编号</th><th width="70%">商品名称</th><th width="10%">数量</th><th width="10%">金额（元）</th></tr>';
	template += '<% for(var i = 0; i < printdata.goodslist.length; i++) { %>';
	template += '<% var item = printdata.goodslist[i] %>';
	template += '<tr><td width="10%"><%=item.no%></td><td width="70%"><%=item.name%> </td>';
	template += '<td width="10%"><%=item.count%></td><td width="10%">￥<%=item.amount%></td></tr>';
	template += '<% } %></table></div>';
	template += '<div class="totolAmount">实付款：<span>￥<%=printdata.totalamount%></span> &nbsp;元</div></div>';
	
	return template;
}

function getStyle1(){
	var style = ' .container {border: solid 1px #aaaaaa;min-width:860px;font-size: 12px;	font-family: "宋体";text-align: left;}';
	style += ' @media print {.printstyle {width: 650px;display: inline-block;}}';
	style += ' .logo {height: 90px;margin-bottom:20px;border-bottom: solid 1px #aaaaaa;}';
	style += ' .orderlist {font-size: 12px;}';
	style += ' .orderlist table {width: 60%;}';
	style += ' .orderlist TD {font-weight: bold;padding: 3px;}';
	style += ' .orderlist TD SPAN {font-weight: normal;}';
	style += ' .goodslist {font-size: 12px;padding: 1px;}';
	style += ' .goodslist table {width: 100%;border-collapse: collapse;border: solid 1px #000000;}';
	style += ' .goodslist table th, .goodslist table td {border: solid 1px #000000;padding: 3px;}';
	style += ' .goodslist table th {text-align: center;font-weight: normal;}';
	style += ' .totolAmount {text-align: right;font-size: 14px;font-weight: bold;padding-top: 10px;padding-bottom: 10px;padding-right: 9px;}';
	style += ' .totolAmount span {color: #ff0000;}';
	return style;
}

function getStyle2(){
	var style='';
	style+=' html,body{margin:0px;padding:0px;overflow: auto;text-align: center;}';
	style+=' .container {border: solid 1px #aec7e5;display: inline-block;color: #444444;width: 860px;font-size: 12px;font-family: Tahoma, Helvetica, Arial,"宋体";text-align: left;}';
	style+=' @media print {.printstyle {width: 750px;display: inline-block;}}';
	style+=' .logo {height: 70px;margin-bottom:20px;border-bottom: solid 1px #aec7e5;}';
	style+=' .orderlist {font-size:13px;padding-left:10px;}';
	style+=' .orderlist .title{ color: #444444;font-weight: bold;height:20px;}';
	style+=' .orderlist table {width: 80%;}';
	style+=' .orderlist TD {padding: 3px;color: #444444;}';
	style+=' .goodslist {font-size: 12px;padding: 1px;margin-top:5px;}';
	style+=' .goodslist table {width: 100%;border-collapse: collapse;border: solid 1px #eeeeee;}';
	style+=' .goodslist table th {border: solid 1px #dddddd;padding: 6px;color:#444444;background: #e8f2ff;text-align: center;font-weight: normal;}';
	style+=' .goodslist table td {border: solid 1px #dddddd;padding: 9px;color:#444444;}'; 
	style+=' .totolAmount {text-align: right;font-size: 12px;padding-top: 10px;padding-bottom: 10px;padding-right: 9px;}';
	style+=' .totolAmount span {color: #f50;font-weight: bold;}';
	return style;
}