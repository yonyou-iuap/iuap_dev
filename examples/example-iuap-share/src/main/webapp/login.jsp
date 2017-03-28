<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<script>
</script>
<link href="<%=request.getContextPath()%>/resources/css/bootstrap.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/resources/css/login.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/share.js"></script>
</head>
<body>
	<div class="panel-bottom" >
	<button type="button" id="myButton" data-loading-text="Loading..." class="btn btn-primary" autocomplete="off"  onclick="document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">
	  分享
	</button>
   </div>
	<!--分享层 -->
	<div id="light"
		style="display: none; position: absolute; top: 100px; left: 30%; width: 40%; height: 350px; padding: 16px; border: 5px solid gray; background-color: white; z-index: 1002; overflow: auto;">
		分享到 : <a href="javascript:void(0)"
			onclick="document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">
			<img src="<%=request.getContextPath()%>/resources/images/x.png" border="0"
			style="float: right;" alt="关闭">
		</a>
		<ul id="bsLogoList">
			<div id="bp-qqmb" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToTencent(document.title,location.href,document.title);"
					class="tmblog"><img
					src="<%=request.getContextPath()%>/resources/images/qqweibo.gif" border="0"
					alt="腾讯微博"></a>
				<div style="font-size: 10px;" title="腾讯微博">腾讯微博</div>
			</div>
			<div id="bp-sinaminiblog"
				style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToSina(document.title,location.href,document.title);"
					class="tmblog"><img
					src="<%=request.getContextPath()%>/resources/images/sinaweibo.gif" border="0"
					alt="新浪微博"></a>
				<div style="font-size: 10px;" title="新浪微博">新浪微博</div>
			</div>
			<div id="bp-qqim" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToQQIM(document.title,location.href,document.title);"
					class="tmblog"><img src="<%=request.getContextPath()%>/resources/images/qqim.gif"
					border="0" alt="QQ"></a>
				<div style="font-size: 10px;" title="QQ">QQ</div>
			</div>
			<div id="bp-qzone" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToQzone(document.title,location.href,document.title);"
					class="tmblog"><img
					src="<%=request.getContextPath()%>/resources/images/qzone.gif" border="0" alt="QQ空间"></a>
				<div style="font-size: 10px;" title="QQ空间">QQ空间</div>
			</div>

			<div id="bp-baiduhi" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToBaiduTieba(document.title,location.href,document.title);"
					class="tmblog"><img
					src="<%=request.getContextPath()%>/resources/images/baiduhi.gif" border="0" alt="百度"></a>
				<div style="font-size: 10px;" title="百度贴吧">百度贴吧</div>
			</div>
			<div id="bp-douban" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)"
					onclick="ShareToDouban(document.title,location.href,document.title);"
					class="tmblog"><img
					src="<%=request.getContextPath()%>/resources/images/douban.gif" border="0" alt="豆瓣网"></a>
				<div style="font-size: 10px;" title="豆瓣网">豆瓣网</div>
			</div>
			<div id="bp-wechart" style="float: left; width: 50px; margin: 15px;">
				<a href="javascript:void(0)" onclick="ShareToWeChart();"
					class='tmblog'> <img
					src="<%=request.getContextPath()%>/resources/images/wechart.png" border="0"
					alt="微信分享">
				</a>
				<div style="font-size: 10px;" title="微信分享">微信</div>
			</div>
		</ul>
	</div>
	<!-- 二维码展示层 -->
	<div id="qrcode"
		style="display: none; position: absolute; top: 300px; left: 40%; padding: 16px; border: 2px solid gray; background-color: white; z-index: 1003; overflow: auto;">
		<a href="javascript:void(0)"
			onclick="document.getElementById('qrcode').style.display='none';document.getElementById('fade').style.display='none'">
			<img src="<%=request.getContextPath()%>/resources/images/x.png" border="0"
			style="float: right;" alt="关闭">
		</a>
	</div>
</body>
</html>