define(['iReferComp','refComp','error','text!./tmModel.html','uuigrid','utilrow','ajaxfileupload','ossupload','interfaceFile','interfaceFileImpl'], function(iReferComp,refComp,errors,html) {
	'use strict';
	var app, viewModel, basicDatas, events,fileEvents,refid,dom,enums,dialogEvents,comValueNameMap;
	var ctrlBathPath = ctx+'/basedata/tmModel';
	basicDatas = {
			
		modelListData: new u.DataTable({
			meta:{
				pk_model:{type:'string'},
				//车型编码
				vmodelcode:{type:'string'},
				//车型名称
				vmodelname:{type:'string'},
				//车辆型号
				vannouncenum:{type:'string'},
				//所属车系
				pk_series:{type:'string',},
				//电量
				nchargepower:{type:'string'},
				//新能源车辆推广目录
				vmarketcatalog:{type:'string'},
				//工信部车辆公告
				vvehicleannouncement:{type:'string'},
				//公告有效日期
				vannouncevaliddate:{type:'string'},
				//车型状态
				vstatus:{type:'string'},
				//状态
				vusestatus:{type:'string'}
			}
		}),
		modelCardData: new u.DataTable({
			meta:{
				//车型编码
				vmodelcode:{type:'string',maxLength:100},
				//车型名称
				vmodelname:{type:'string',required:true,maxLength:100},
				//车辆型号
				vannouncenum:{type:'string',maxLength:50},
				//所属车系
				pk_series:{type:'string',required:true,maxLength:50},
				//充电功率
				nchargepower:{type: 'string'},
				//电量
				nvbatterypower:{type: 'string',required:true},
				//新能源车辆推广目录
				vmarketcatalog:{type:'string',maxLength:50},
				//工信部车辆公告
				vvehicleannouncement:{type:'string',maxLength: 50},
				//公告有效日期
				vannouncevaliddate:{type:'string',maxLength: 50},
				//备注
				vremark:{type:'string',maxLength: 50},
				//长
				vlength:{type: 'integer',min:0},
				//宽
				vwidth:{type: 'integer',min:0},
				//高
				vheight:{type: 'integer',min:0},
				//轴距
				nwheelbase:{type: 'integer',min:0},
				//前悬/后悬
				nsuspensionbase:{type:'string',maxLength:120},
				//离地间隙
				ngroundclearanc:{type: 'integer',min:0},
				//接近角/离去角
				vdriveangle:{type:'string'},
				//入口高度
				ninheight:{type:'string'},
				//地板高度
				nfloorheight:{type: 'integer',min:0},
				//车内最低高度
				nminheight:{type: 'integer',min:0},
				//轮包通道宽 前/后
				vhubcapwidth:{type:'string',maxLength: 120},
				//座位数
				nseat:{type: 'string',min:0},
				//载客数
				npasenger:{type: 'string',min:0},
				//站立面积
				nstandarea:{type: 'string',precision: 4},
				//车门
				ncardoor:{type: 'string'},
				//所属车辆类别
				vehicelname:{type:'string'},
				//所属品牌
				brandname:{type:'string'},
				//质保期
				vwarranty:{type: 'integer',min:0},
				//质保里程
				vmilewarranty:{type: 'integer',min:0},
				//轮胎质保里程
				vtirewarranty:{type: 'integer',min:0},
				//最大爬坡度
				vmaxclaim:{type:'string',maxLength:200},
				//转弯半径
				vturnradii:{type:'string',maxLength:200},
				//充电枪个数
				nchargegun:{type:'string'},
				//电机额定最大功率
				vmotorpower:{type:'string',maxLength:200},
				//电机额定/最大扭矩
				vmotortorque:{type:'string',maxLength:200},
				//充电时间
				nchargetime:{type:'string'},
				//整备质量
				ncurbweight:{type:'integer',min:0},
				//最大质量
				nmaxweight:{type:'integer',min:0},
				//电池组成相型号
				vbattarycagetype:{type:'string',maxLength:100},
				//驱动电机型号
				vdrivemotortype:{type:'string',maxLength:100},
			}
		}),
		competListData: new u.DataTable({
			meta:{
				//品牌
				vcompetbrand:{type:'string'},
				//车型
				vcompetmodel:{type:'string'},
				//电量
				nvbatterypower:{type:'string'},
				//长
				clength:{type:'string'},
				//宽
				cwidth:{type:'string'},
				//高
				cheight:{type:'string'},
				//整备质量
				ncurbweight:{type:'string'},
				//最大质量
				nmaxweight:{type:'string'},
				//轴距
				nwheelbase:{type:'string'},
				//前悬/后悬
				nsuspensionbase:{type:'string'},
				//离地间隙
				ngroundclearanc:{type:'string'},
				//接近角/离去角
				vdriveangle:{type:'string'},
				//入口高度
				ninheight:{type:'string'},
				//地板高度
				nfloorheight:{type:'string'},
				//车内最低高度
				nminheight:{type:'string'},
				//轮包通道宽
				vhubcapwidth:{type:'string'},
				//座位数
				nseat:{type:'string'},
				//载客数
				npasenger:{type:'string'},
				//站立面积
				nstandarea:{type:'string'},
				//车门
				ncardoor:{type:'string'},
				//车门净宽
				ndoorwidth:{type:'string'},
				//轮胎型式
				vtyreform:{type:'string'},
				//驱动型式
				vdriveform:{type:'string'},
				//最高车速
				nmaxspeed:{type:'string'},
			}
		}),
		dialogListData: new u.DataTable({
			meta:{
				batpower:{type:'integer',required:true}
			}
		}),
		//查询用
		searchData: new u.DataTable({
			meta:{
				vmodelcode:{type:'string'},
				vmodelname:{type:'string'},
				pk_series:{type:'string'},
				pk_veicleclass:{type:'string'}
			}
		}),
		//附件信息(车型外观图)
		fileData: new u.DataTable({
			meta: {
				id: {type: 'string'},//主键
				filepath: {type: 'string'},//文件名称
				filesize: {type: 'string'},//文件大小
				filename: {type: 'string'},//文件名称
				uploadtime: {type: 'string'},//上传时间
				groupname: {type: 'string'},//
				url: {type: 'string'}//URL
			}
		}),
		//附件信息(车型内饰图)
		fileInData: new u.DataTable({
			meta: {
				id: {type: 'string'},//主键
				filepath: {type: 'string'},//文件名称
				filesize: {type: 'string'},//文件大小
				filename: {type: 'string'},//文件名称
				uploadtime: {type: 'string'},//上传时间
				groupname: {type: 'string'},//
				url: {type: 'string'}//URL
			}
		}),
		gridStatus: ko.observable("view"),
		//新增
		addFunShow : ko.observable(false),
		//修改
		editFunShow : ko.observable(false),
		//启用
		startFunShow : ko.observable(false),
		//停用
		stopFunShow : ko.observable(false),
		//导出
		exportFunShow : ko.observable(false),
	}
	
	enums = {
		//工信部车辆公告	
//		vehicleannouncement: [{"value": "10041001","name": "申报中"}, 
//		            {"value": "10041002","name": "已完成"}],
//		vehicleStatus: [{"value": "10031001","name": "设计"}, 
//		            {"value": "10031002","name": "试制"},
//		            {"value": "10031003","name": "调试"},
//		            {"value": "10031004","name": "量产"},
//		            {"value": "10031005","name": "停产"}],
//		useStatus: [{"value": "10051001","name": "启用"}, 
//		    		{"value": "10051002","name": "停用"}],		            
//		//是否枚举	
//		yesORno: [{"value": "10011001","name": "是"}, 
//		            {"value": "10011002","name": "否"}]
		vehicleannouncement:enumerate(1004),
		vehicleStatus:enumerate(1003),
		useStatus:enumerate(1005),
		yesORno:enumerate(1001)
				};
	events = {
		//查询主数据
		queryMain: function(){
			viewModel.searchData.createEmptyRow();
			var rows = viewModel.searchData.getSelectedRows();
			var queryData = rows[0].getSimpleData();
			queryData["pageIndex"] = viewModel.modelListData.pageIndex();
			queryData["pageSize"] = viewModel.modelListData.pageSize();

			$.ajax({
				type : 'POST',
				url : window.cturl+'/basedata/tmModel/querypage',
				data : queryData,
				dataType : 'json',
				success : function(result) {
					var data = result.data;
					if(data!=null){
						viewModel.modelListData.setSimpleData(data.content,{'unSelect':true});
						viewModel.modelListData.totalPages(data.totalPages);
						viewModel.modelListData.totalRow(data.totalElements);
						viewModel.modelListData.setRowUnFocus();
					}
				},
			});
		},
		//查询竞品信息
		queryTmCompet:function(row){
			//查询出竞品信息
			$.ajax({
				type : 'POST',
				url : window.cturl+'/basedata/tmModel/queryTmCompet',
				dataType : 'json',
				contentType: "application/json ; charset=utf-8",
				data : JSON.stringify(row.getSimpleData()),
				success : function(result) {
					var data = result.data;
					if(data!=null){
						viewModel.competListData.setSimpleData(data);
					}
				},
			});
		},
		//查询按钮
		searchClick:function(){
			viewModel.queryMain();
		},
		//启用按钮
		startClick:function(){
			var dats = [];
			var rows = viewModel.modelListData.getSelectedRows();
			if(null==rows||rows.length==0){
				u.messageDialog({msg:"请选择单据！",title:"提示", btnText:"OK"});
				return;
			}
			for (var i = 0; i < rows.length; i++) {
				dats.push(rows[i].getSimpleData());
			}
			onLoading();
			//调用后台启用方法
			$.ajax({
				url: window.cturl+"/basedata/tmModel/start",
				type: 'POST',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(dats),
				success: function(data) {
					viewModel.queryMain();
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
			onCloseLoading();
		},
		//停用按钮
		stopClick:function(){
			var dats = [];
			var rows = viewModel.modelListData.getSelectedRows();
			if(null==rows||rows.length==0){
				u.messageDialog({msg:"请选择单据！",title:"提示", btnText:"OK"});
				return;
			}
			for (var i = 0; i < rows.length; i++) {
				dats.push(rows[i].getSimpleData());
			}
			onLoading();
			$.ajax({
				url: window.cturl+"/basedata/tmModel/stop",
				type: 'POST',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(dats),
				success: function(data) {
					viewModel.queryMain();
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
			onCloseLoading();
		},
		onDownloadExcel: function onDownloadExcel() {
			var dats = [];
			var pks = "";
			var rows = viewModel.modelListData.getSelectedRows();
			if (rows == null || rows.length == 0) {
				u.messageDialog({ msg: "请选择要导出的数据", title: "提示", btnText: "OK" });
				return;
			}
			for (var i = 0; i < rows.length; i++) {
				var pkItem = rows[i].getValue("pk_model");
				dats.push(rows[i].getSimpleData());
				if (pks.length == 0) {
					pks = pkItem;
				} else {
					pks = pks + "," + pkItem;
				}
			}
			onLoading();
			var form = $("<form>"); //定义一个form表单
			form.attr('style', 'display:none'); //在form表单中添加查询参数
			form.attr('target', '');
			form.attr('method', 'post');
			form.attr('action', window.cturl+"/basedata/tmModel/vehicelDownload");

			var input1 = $('<input>');
			input1.attr('type', 'hidden');
			input1.attr('name', 'pkModelIds');
			input1.attr('value', pks);
			$('#tmModel-mdlayout').append(form); //将表单放置在web中 
			form.append(input1); //将查询参数控件提交到表单上
			form.submit();
			onCloseLoading();
		},
		//新增按钮
		addClick: function(){
			viewModel.gridStatus("new");
			var md = document.querySelector('#tmModel-mdlayout')['u.MDLayout'];
			viewModel.modelCardData.clear();
			viewModel.modelCardData.setEnable(true);
			var r = viewModel.modelCardData.createEmptyRow();
			md.dGo('tmModel_addPage');
		},
		//点击修改按钮
		editClick: function() {
			viewModel.gridStatus("edit");
			viewModel.modelCardData.setEnable(true);
			//获取选中行
			var rows = viewModel.modelListData.getSelectedRows();
			viewModel.modelCardData.setEnable(true);
			
			if(null==rows||rows.length==0){
				u.messageDialog({msg:"请选择单据！",title:"提示", btnText:"OK"});
				return;
			}else if(rows.length > 1){
				u.messageDialog({msg:"不能操作多行单据",title:"提示", btnText:"OK"});
				return;
			}else if("10051002"==rows[0].getSimpleData().vusestatus){
				u.messageDialog({msg:"停用的单据不能做修改操作",title:"提示", btnText:"OK"});
				return;
			}
			viewModel.modelCardData.setSimpleData(rows[0].getSimpleData());
			
			//查询竞品信息
			viewModel.queryTmCompet(rows[0]);
			viewModel.fileQuery();
			var md = document.querySelector('#tmModel-mdlayout')['u.MDLayout'];
			md.dGo('tmModel_addPage');
			
		},
		//点击查看按钮
		showClick: function(id){
			viewModel.gridStatus("view");
			//将id行设置为选中行
			viewModel.modelListData.setRowSelect(id);
			//获取选中行
			var row = viewModel.modelListData.getCurrentRow();
			viewModel.modelCardData.setSimpleData(row.getSimpleData());
			
			//查询出竞品信息
			viewModel.queryTmCompet(row);
			viewModel.fileQuery();
			var md = document.querySelector('#tmModel-mdlayout')['u.MDLayout'];
			md.dGo('tmModel_showPage');
		},
		//点击删除按钮
		delClick: function() {
			//获取选中行
			var row = viewModel.modelListData.getCurrentRow();
			if(null==row){
				u.messageDialog({msg:"请选择单据！",title:"提示", btnText:"OK"});
				return;
			}
			//确认删除对话框
			u.confirmDialog({
				title: "确认",
				msg: "是否删除选中的数据？",
				onOk: function () {
					//如有页面级逻辑在这里补充
					//todo...
					onLoading();
					//调用后台删除方法
					$.ajax({
						url: window.cturl+"/basedata/tmModel/delete",
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(row.getSimpleData()),
						success: function(data) {
							onCloseLoading();
							//删除成功后将页面选中行移除
							viewModel.modelListData.removeRow(row);
							//成功后页面提示
							u.messageDialog({msg:"删除成功！",title:"提示", btnText:"OK"});
							//也可重新调用初始化查询方法，
							//getInitData();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							onCloseLoading();
							errors.error(XMLHttpRequest);
						}
					});
				}
			});
		},
		//返回
		goBack: function(){
			viewModel.modelCardData.clear();
			var	md = document.querySelector('#tmModel-mdlayout')['u.MDLayout'];
			md.dGo('tmModel_listpage');
			
		},
		//保存
		addOrEditRow:function(){
			var self = this;
			var url = '';
			var postData =JSON.stringify(this.modelCardData.getSimpleData()[0]);;
			var validate = getvalidate(app,"#tmModel_addPage");
			if(validate==false){
				return
			};
			if ( viewModel.gridStatus()=== 'new') {
				url = window.cturl+'/basedata/tmModel/save';
			} else {
				url = window.cturl+'/basedata/tmModel/update';
			}
			onLoading();
			$.ajax({
				url:url,
				type:'POST',
				contentType: 'application/json',
				data:postData,
				success:function(res){
					if (res.flag == 'success'){
						if (viewModel.gridStatus()=== 'new'){
							self.modelListData.addSimpleData(res.data);
						}else{
							var curRow = viewModel.modelListData.getCurrentRow();
							curRow.setSimpleData(self.modelListData.getCurrentRow().getSimpleData());
						}
						viewModel.md.dBack();
						viewModel.queryMain();								
					}else{
						u.showMessageDialog(res.msg);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
			onCloseLoading();
		},
		pageChangeFunc: function(index){
			viewModel.modelListData.pageIndex(index);
			viewModel.queryMain();		
		},
		sizeChangeFunc: function(size){
			viewModel.modelListData.pageSize(size);
			viewModel.modelListData.pageIndex(0);
			viewModel.queryMain();
		},
		afterAdd: function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},
		fseriesData:function(obj,row){
			var row = viewModel.searchData.getCurrentRow();
			if(row!=null){
				var veicle = row.getValue("pk_veicleclass");
				if(veicle != null && veicle != ""){
					iReferComp.setFilterPks("pk_vehicleclassify", veicle, obj);
				}
			}
		},
	}
	
	dialogEvents={
		//模态框事件-----
		//打开模态框
		dialogClick:function(){
			var arry = new Array();
			var datas = new Array();
			var nvbatterypower = viewModel.modelCardData.getSimpleData()[0].nvbatterypower;
			if(null!=nvbatterypower){
				arry = nvbatterypower.split('/');
				for (var i=0;i<arry.length;i++){
					datas[i] = new Object();
					datas[i].batpower = arry[i]+"";
				}
			}
			window.md = u.dialog({id:'powerDialg',content:"#tmModel_dialog_content",hasCloseMenu:true});
			viewModel.dialogListData.clear();
			viewModel.dialogListData.setSimpleData(datas);
		},
		//增行
		dialog_addRow:function(){
			viewModel.dialogListData.setEnable(true);
			viewModel.dialogListData.createEmptyRow();
		},
		//删行
		dialog_delRow:function(){
			var curRow = viewModel.dialogListData.getCurrentRow();
			viewModel.dialogListData.removeRow(curRow);
		},
		//保存-模态框
		dialog_saveClick:function(){
			var dialogData = viewModel.dialogListData.getSimpleData();
			var validate = getvalidate(app,"#tmModel_dialog_content");
			if(validate==false){
				return
			};
			var powerstr = "";
			for(var i=0;i<dialogData.length;i++){
				if(i==dialogData.length-1){
					powerstr = powerstr + dialogData[i]["batpower"];
				}else{
					powerstr = powerstr + dialogData[i]["batpower"]+"/";
				}
			}
			var cardRow = viewModel.modelCardData.getCurrentRow();
				
			cardRow.setValue("nvbatterypower",powerstr);
			window.md.close();
		},	
	}
	
	fileEvents={
		//打开附件上传界面
		onOpenUploadWin : function(){
			window.md = u.dialog({id:'testDialg3',content:"#tmModel_dialog_uploadfile",hasCloseMenu:true});
			$('.sub-list1-new').css('display','inline-block');
		},
		//上传附件
		onFileUpload : function(){
			//获取表单
			var row = viewModel.modelCardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_model;
			//var position = $("#positiion").val();
			var par = { 
				fileElementId: "tmModel_uploadbatch_id",  //【必填】文件上传空间的id属性  <input type="file" id="id_file" name="file" />,可以修改，主要看你使用的 id是什么 
				filepath: pk,   //【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
				groupname: "tmmodel-out",//【必填】分組名称,未来会提供树节点
				permission: "private", //【选填】 read是可读=公有     private=私有     当这个参数不传的时候会默认private
				url: true,          //【选填】是否返回附件的连接地址，并且会存储到数据库
				//thumbnail :  "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
				cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
			}
			var f = new interface_file();
			f.filesystem_upload(par,viewModel.fileUploadCallback);	  
			onLoading();
		},
		onCloseFileWindow:function(){
			window.md.close();
		},
		//上传文件回传信息
		fileUploadCallback : function(data){
			onCloseLoading();
			if(1 == data.status){//上传成功状态
				viewModel.fileData.addSimpleData(data.data);
				u.messageDialog({msg:"上传成功！",title:"提示", btnText:"OK"});
			}else{//error 或者加載js錯誤
				u.messageDialog({msg:"上传失败！"+data.message,title:"提示", btnText:"OK"});
			}
		},
		fileQuery : function(){
			//获取表单
			var row = viewModel.modelCardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_model;
			var par = { 
				//建议一定要有条件否则会返回所有值
				filepath: pk, //【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
				groupname: "tmmodel-out",//【选填】[分組名称,未来会提供树节点]
				cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
			}
			var f = new interface_file();
			f.filesystem_query(par,viewModel.fileQueryCallBack);	 
		},
		fileQueryCallBack : function(data){
			if(1 == data.status){//上传成功状态
				viewModel.fileData.setSimpleData(data.data);
			}else{
				//没有查询到数据，可以不用提醒	
				if("没有查询到相关数据" != data.message){
					u.messageDialog({msg:"查询失败"+data.message,title:"提示", btnText:"OK"});
				}else{
					viewModel.fileData.removeAllRows();
				}
			}
		},
		//附件删除
		fileDelete : function(){
			var row = viewModel.fileData.getSelectedRows();
			if(row==null || row.length==0){
				u.messageDialog({msg:"请选择要删除的附件",title:"提示", btnText:"OK"});
				return
			}else if(row.length>1){
				u.messageDialog({msg:"每次只能删除一个附件",title:"提示", btnText:"OK"});
				return
			}
			for(var i=0;i<row.length;i++){
				var pk = row[i].getValue("id");
				var par = {
					id:pk,//【必填】表的id
					cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
				}
				var f = new interface_file();
				f.filesystem_delete(par,viewModel.fileDeleteCallBack);
			}
		},
		//附件删除回调
		fileDeleteCallBack : function(data){
			if(1 == data.status){//上传成功状态
				viewModel.fileQuery();
			}else{
				u.messageDialog({msg:"删除失败"+data.message,title:"提示", btnText:"OK"});
			}
		},
		//下载
		fileDownload : function(){
			var row = viewModel.fileData.getSelectedRows();
			if(row==null || row.length==0){
				u.messageDialog({msg:"请选择要下载的附件",title:"提示", btnText:"OK"});
				return
			}else if(row.length>1){
				u.messageDialog({msg:"每次只能下载一个附件",title:"提示", btnText:"OK"});
				return
			}
			for(var i=0;i<row.length;i++){
				var pk = row[i].getValue("id");
				var form = $("<form>");   //定义一个form表单
				form.attr('style', 'display:none');   //在form表单中添加查询参数
				form.attr('target', '');
				form.attr('enctype', 'multipart/form-data');
				form.attr('method', 'post');
				form.attr('action', window.ctxfilemng+"file/download?permission=private&id="+pk);
				$('#tmModel-mdlayout').append(form);  //将表单放置在web中 
				form.submit();
			}
		},
		//查看
		fileView : function(){
			var row = viewModel.fileData.getSelectedRows();
			if(row==null || row.length==0){
				u.messageDialog({msg:"请选择要下载的附件",title:"提示", btnText:"OK"});
				return
			}else if(row.length>1){
				u.messageDialog({msg:"每次只能查看一个附件",title:"提示", btnText:"OK"});
				return
			}
			for(var i=0;i<row.length;i++){
				var url = row[i].getValue("url");
				parent.open("http://"+url);
			}
		}
	}
	
	viewModel = u.extend(basicDatas, events,fileEvents,dialogEvents,enums,comValueNameMap);
	
	var getInitData = function() {
		viewModel.searchData.createEmptyRow();
		
		$.ajax({
			type: 'get',
			url: window.cturl+"/security/authBtn/auth?funcCode=F020205",
			success: function(data) {
				var funList = data;
				if(null != funList){
					for(var i=0;i<funList.length;i++){
						var funTemp = funList[i];
						if("新增"==funTemp){
							viewModel.addFunShow(true);
						}else if("修改"==funTemp){
							viewModel.editFunShow(true);
						}else if("启用"==funTemp){
							viewModel.startFunShow(true);
						}else if("停用"==funTemp){
							viewModel.stopFunShow(true);
						}else if("导出"==funTemp){
							viewModel.exportFunShow(true);
						}
					}
				}
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		});

		viewModel.modelListData.pageSize(10);
		
		viewModel.searchData.on("valuechange",function(obj){
			if(obj.field=="pk_veicleclass"){
				viewModel.searchData.setValue("pk_series",null);
			}
			
		});
		
		viewModel.modelCardData.on("valuechange",function(obj){
			//如果是电量的改变，不作处理
			if(obj.field!="pk_series") return;
			$.ajax({
				type: 'GET',
				url: window.cturl + "/ref/tmseriesref/getIntRefData?pks="+"'"+obj.newValue+"'",
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				success: function success(data) {
					if(data.length>0){
						viewModel.modelCardData.setValue("vehicelname", data[0].vehicle);
						viewModel.modelCardData.setValue("brandname", data[0].brand);
					}else{
						viewModel.modelCardData.setValue("vehicelname", "");
						viewModel.modelCardData.setValue("brandname", "");
					}
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		});
		
		viewModel.md = document.querySelector('#tmModel-mdlayout')['u.MDLayout'];
		viewModel.queryMain();
		
	}

	return {
		init: function(content,tabid) {
			content.innerHTML = html;
			window.vm = viewModel;
			app = u.createApp({
				el: '#'+tabid,
				model: viewModel
			})
			ref();
			getInitData();
		}
	}
	//参照
	function ref() {
		//车系-查询条件
		$('#search_tmmodel_tmseries').each(function(i,val){
	     	var $that=$(this);
	     	dom = $that;
			var options = {
					refCode:"series",
					isMultiSelectedEnabled:false,
			};
			refComp.initRefComp($that,options);
	 	});
		
		$('#new_tmmodel_tmseries').each(function(i,val){
	     	var $that=$(this);
			var options = {
					refCode:"series",
					isMultiSelectedEnabled:false,
			};
			
			refComp.initRefComp($that,options);
	 	});
		//车辆类别-查询条件
		$('#search_tmmodel_vehicleclassifyref').each(function(i,val){
			var $that=$(this);
	     	dom = $that;
			var options = {
					refCode:"vehicleclassifyref",
					isMultiSelectedEnabled:false,
			};
			refComp.initRefComp($that,options);
		});
	}
});