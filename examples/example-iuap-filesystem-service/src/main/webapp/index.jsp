<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ossupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/interface.file.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/interface.file.impl.js"></script>
<script type="text/javascript">
/* 以下都是例子：参考使用  */

/*cookies写入到浏览器下*/
$(function () {
    $.cookie("usercode", "usercode", {expires:7, path: "/"});
    $.cookie("sysid", "sysid", {expires:7,path: "/"});
    $.cookie("tenantid", "tenant001", {expires:7,path: "/"});
    $.cookie("userid", "userid", {expires:7,path: "/"});
})

//跨域通信接收机制
var onmessage = function (event) {
    var data = event.data;
    var origin = event.origin;
		var f = new interface_file();
	    if("upload" == data.action){
		 f.filesystem_upload(data,callback);
	    }
};

if (typeof window.addEventListener != 'undefined') {
    window.addEventListener('message', onmessage, false);
} else if (typeof window.attachEvent != 'undefined') {
    //for ie
    window.attachEvent('onmessage', onmessage);
}

/**
 * 回调函数--返回结果
 */
 var callback = function(data){
	 if(-1 == data.status){//后台校验信息状态
		 var warn = "";
		 for(var k in  data.message){
			 data.message[k].ObjectName    //校验的对象名称
			 data.message[k].Field         //校验的字段名称
			 data.message[k].RejectedValue //校验的错误原因
			 data.message[k].DefaultMessage; //校验的错误提示信息
			 warn +=data.message[k].ObjectName+"对象的"+ data.message[k].Field+"属性不能为："+data.message[k].RejectedValue +"\n"; //自己拼的方式
			 warn +=data.message[k].DefaultMessage +"\n"//后台的默认信息

			 /*这只是例子，具体验证信息想如何展示自己处理  */
		 }
		 alert(warn);

	 }else if(1 == data.status){//上传成功状态

		 alert(JSON.stringify(data))
		 /* 自己看data处理 */

	 }else if(0 == data.status){//上传失败状态
		 /* 自己看data处理 */
		   alert(JSON.stringify(data))
	 }else{//error 或者加載js錯誤
		 /* 自己看着处理 */
		  alert(JSON.stringify(data))
	 }
 };
/*上传附件-异步*/
function upload(){
	 var par = {
			 fileElementId: "uploadbatch_id",  //【必填】文件上传空间的id属性  <input type="file" id="id_file" name="file" />,可以修改，主要看你使用的 id是什么
			 filepath: "code",   //【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
			 groupname: "single",//【必填】分組名称,未来会提供树节点
			 permission: "read", //【选填】 read是可读=公有     private=私有     当这个参数不传的时候会默认private
			 url: true,          //【选填】是否返回附件的连接地址，并且会存储到数据库
			 thumbnail :  "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
			 }
	 var f = new interface_file();
	 f.filesystem_upload(par,callback);
 }

/*查询附件-异步*/
function query(){
	 var par = {
			     //建议一定要有条件否则会返回所有值
				 filepath: "code", //【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
				 groupname: "single",//【选填】[分組名称,未来会提供树节点]
			 }
	 var f = new interface_file();
	 f.filesystem_query(par,callback);
 }

/*下载附件-异步*/
function download(){
	 var par = {
        	 id:'cac6e3f2-a953-4fd9-a397-5f01644ed8fb',//【必填】表的id
			 }
	 var f = new interface_file();
	 f.filesystem_download(par,callback);
 }

/*下载附件-表单方式====> 请参照下面的【下载-表单方式--直接下載附件】这个例子*/

/*删除附件-异步*/
function deletefile(){
	 var par = {
        	 id:'a2395c64-8fec-40bb-9e86-72d04131edd0',//【必填】表的id
			 }
	 var f = new interface_file();
	 f.filesystem_delete(par,callback);
 }
/*获得附件的url-异步*/
function url(){
	 //为了使用快捷，这里直接提供字符串即可     如果 5f212796-da6a-48eb-b047-effa59fe2f1e重复  返回结果会去重
	 var par =
			     {
		    	   ids:'cac6e3f2-a953-4fd9-a397-5f01644ed8fb,a3cdc936-27d8-4183-bfc2-978ccd351f6d',//【必填】表的id 支持批量
		    	   thumbnail :  "100w", //【选填】缩略图
				 }
	 var f = new interface_file();
	 f.filesystem_geturl(par,callback);
 }

/*覆盖上传--异步*/
function replace(){
	 var par = {
        	 id:$("#oldid").val(),//【必填】需要被替换的附件id
        	 fileElementId: "input_id_replace",  //【必填】文件上传空间的id属性  <input type="file" id="id_file" name="file" />,可以修改，主要看你使用的 id是什么
			 filepath: "code", //【必填】[单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则]
			 groupname: "single",//【必填】[分組名称,未来会提供树节点]
			 url: true,//【选填】是否返回附件的连接地址
			 permission: "read", //【选填】read是可读=公有     private=私有     当这个参数不传的时候会默认private
			 thumbnail :  "100w", //【选填】缩略图
			 }
	 var f = new interface_file();
	 f.filesystem_replace(par,callback);
 }


/*附件更新--异步--我们封装的js 只支持filepath、groupname 更新*/
function update(){
	 var par = {
        	 id:'5f212796-da6a-48eb-b047-effa59fe2f1e',//【必填】需要被替换的附件id
			 filepath: "code更新了", //【选填】[单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则]
			 groupname: "single更新了",//【选填】[分組名称,未来会提供树节点]
			 }
	 var f = new interface_file();
	 f.filesystem_update(par,callback);
 }

 /*直传调用*/
	 function ossupload(){
		 var par ={
			groupname : '分组名称',//【必填】
			filepath : '单据id',//【必填】
			fileid : 'file',//【必填】input上传控件的id
			permission : 'read', //【 选填】私有private   read是可读 公有
			modular :'uap',//【选填】产品下面的细分模块
			sysid : '产品名称',//【 选填】
		 }
		 var f = new interface_file();
		 f.filesystem_ossupload(par,callback);
	 }

	/*流式上传*/
	function stream_upload() {
		var file = $("#stream_uploadbatch_id").prop('files');
		var f = new interface_file();
        for ( var k in file) {
		   var formData = new FormData();
			if (file[k] instanceof File) {
				formData.append("file", file[k])//【必填】===================获得文件数据（需要你提供，获得修改成你自己的方式）
				formData.append("filepath", "单据标示")//【必填】
				formData.append("groupname", "分组")//【必填】
				formData.append("url", true)//【选填】
				formData.append("permission", "read")//【必填】
				f.filesystem_stream_upload(formData, callback);
			}

		}

	}
</script>
</head>
<!-- <iframe id='iframe' src="http://localhost:8080/iuap-saas-fileservice-base/" onload ="onLoad()"></iframe>    -->
<body>
  <h5>------------------------------------------------------------------------------------------------------------------------</h5>
  <h5>上传文件-异步:<input type="file" name="addfile" id = "uploadbatch_id" multiple="multiple"/><input type="button" value="上传" onclick="upload()"></h5>
    <h5>------------------------------------------------------------------------------------------------------------------------</h5>
    <h5>查询功能       <input type="button" value="查询" onclick="query();"></h5>
    <h5>------------------------------------------------------------------------------------------------------------------------</h5>
    <h5>获得附件的url地址：<input type="button" value="得到url" onclick="url();"> </h5>
    <h5>------------------------------------------------------------------------------------------------------------------------</h5>
    <form name="div_download2"   action="<%=request.getContextPath()%>/file/download?id=cac6e3f2-a953-4fd9-a397-5f01644ed8fb&permission=read" method="post" enctype="multipart/form-data">
        <h5>下载-表单方式--直接下載附件： <input type="submit" value="表单下载"></h5>
    </form>
    <h5>------------------------------------------------------------------------------------------------------------------------</h5>
    <h5>下载-异步方式--直接返回字符流：<input type="button" value="异步下载" onclick="download();"></h5>
    <h5>------------------------------------------------------------------------------------------------------------------------</h5>
    <h5>删除：<input type="button" value="异步删除" name="delete" onclick="deletefile();"/></h5>
    <h5>------------------------------------------------------------------------------------------------------------------------</h5>
    <h5>更新操作<input type="button" value="更新" name="update" onclick="update();"/></h5>
    <h5>---------------------------替换上传---------------------------------------------------------------------------------------------</h5>
    <input type="file" name="addfile" id = "input_id_replace" multiple="multiple"/>
               原附件id：<input type="text" value="" id ="oldid">
    <input type="button" value="替换上传" onclick="replace()">
    <h5>------------------------------------------------------------------------------------------------------------------------</h5>
    <h5>查询功能 -跨域  <input type="button" value="跨域查询" onclick="filesystem_query_cors();"></h5>
    <h5>------------------------------------------------------------------------------------------------------------------------</h5>
    <h5>oss直传  </h5>
    <form id="formdata">
            <input type="file" id="file" multiple="multiple">
            <input type="button" id="submit" value="提交" onclick="ossupload();">
    </form>
    <h5>--------------------------------通用的formData上传方式--------------------------------------------------------------------------------------------------</h5>
    <input type="file" name="stream" id = "stream_uploadbatch_id" multiple="multiple"/><input type="button" value="流式上传" onclick="stream_upload();">

</body>
</html>
