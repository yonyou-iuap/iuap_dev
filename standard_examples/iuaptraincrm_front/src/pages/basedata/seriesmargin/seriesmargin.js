define(['iReferComp','refComp','error','text!./seriesmargin.html','uuigrid',"utilrow",'ajaxfileupload'], function(iReferComp,refComp,errors,html) {
	'use strict';
    var refid;
	var dom;
	//参照
	function ref() {
		//合同-查询条件
		$('#contract').each(function(i,val){
		 	var $that=$(this);
		 	dom = $that;
			var options = {
					refCode:"contract",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
		});
		//车系编码-查询条件
		var pk='';
		$('#seriescode').each(function(i,val){
	     	var $that=$(this);
	     	dom = $that;
			var options = {
					refCode:"series",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
	 	});
		
		//车系名称-查询条件
		$('#seriesname').each(function(i,val){
		 	var $that=$(this);
		 	dom = $that;
			var options = {
					refCode:"series",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
		});
		//合同-卡片
		$('#contract_card').each(function(i,val){
		 	var $that=$(this);
		 	dom = $that;
			var options = {
					refCode:"contract",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
		});
		
		//车系编码-卡片
		$('#seriescode_card').each(function(i,val){
		 	var $that=$(this);
		 	dom = $that;
			var options = {
					refCode:"series",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
		});
		
		/*var $input=dom.find('input');
		$input.val(name);*/
    }
	var app, viewModel, basicDatas, events,pagesize=10;
	
	basicDatas = {
		searchData: new u.DataTable({
				meta: {
					pk_contract:{type: 'string'},//合同
					pk_series: {type: 'string'},   //车系主键
					vyear: {type: 'string'},      //年度
					vmonth:{type: 'string'}       //月度
				}
			}),
		listData: new u.DataTable({
			meta: {
				pk_seriesmargin: {type: 'string'},  //车系毛利主键
				pk_contract:{type: 'string'},//合同
				pk_series: {type: 'string'},        //车系主键
				vseriescode: {type: 'string'},      //车系编码
				vseriesname: {type: 'string'},      //车系名称
				classname: {type: 'string'},        //所属类别名称
				brandname: {type: 'string'},        //所属品牌名称
				vyear: {type: 'string'},			//年度
				vmonth:{type: 'string'},			//月度
				nmargin:{type: 'string'}			//毛利
			}
		}),
		cardData: new u.DataTable({
			meta: {
				pk_seriesmargin: {type: 'string'},                         //车系毛利主键
				pk_contract:{type: 'string',required:'true'},
				pk_series: {type: 'string',required:'true'},             //车系
				vehiclename: {type: 'string'}, 
				vseriescode: {type: 'string'}, 
				brandname: {type: 'string'}, 
				vyear: {type: 'string',required:'true'},       //年度
				vmonth:{type: 'string',required:'true'},       //季度
				nmargin:{type: 'float',required:'true'}     //毛利
			}
		}),
		//新增
		addFunShow : ko.observable(false),
		//修改
		editFunShow : ko.observable(false),
		//导入模板下载
		impDemFunShow : ko.observable(false),
		//导入
		importFunShow : ko.observable(false),
	//季度下拉框
	monthData:[{name:'1',value:'1'},{name:'2',value:'2'},{name:'3',value:'3'},{name:'4',value:'4'}]
	}
	
	events = {
	    //处理查询
			querySeries: function(){
				var queryData = '';
				var searchRow = viewModel.searchData.getCurrentRow();
				// 拼接查询参数,
				var isFirst = true;
				var queryParaHead = 'search_like_';
				var searchData = searchRow.getSimpleData();
				for(var s in searchData)
				 	if(searchData[s]) {
						 if(isFirst) {
							queryData = queryParaHead + s + '=' + searchData[s];
							isFirst = false;
						 }else {
							queryData += '&' + queryParaHead + s + '=' + searchData[s];
						 }
				 }
				$.ajax({
					type: 'POST',
					url: window.cturl + '/bd/seriesMarginCtrl/querypage?' + queryData,
					success: function(data) {
						viewModel.listData.pageSize(pagesize);//每页显示多少条
						viewModel.listData.setSimpleData(data.content, {
							unSelect: true
						});
						viewModel.listData.totalPages(data.totalPages);
						viewModel.listData.totalRow(data.totalElements); 
						viewModel.listData.setRowUnFocus();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			queryMain: function(){
				var rows = viewModel.searchData.getSelectedRows();
				var param = rows[0].getSimpleData();
				param.psize = viewModel.listData.pageSize();
				param.pindex = viewModel.listData.pageIndex();
				// param: 	查询参数 & 分页参数
				$.ajax({
					type : 'POST',
					url : window.cturl + '/bd/seriesMarginCtrl/querypage',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(param),
					success : function(result) {
						var data = result.data;
						if(data!=null){
							viewModel.listData.pageSize(pagesize);//每页显示多少条
							viewModel.listData.setSimpleData(data.content, {
								unSelect: true
							});
							viewModel.listData.totalPages(data.totalPages);
							viewModel.listData.totalRow(data.totalElements);
							viewModel.listData.setRowUnFocus();
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				});
			},
	    //点击查询按钮
		searchClick: function(model,event){
			events.queryMain();
		},
		//页码改变
		pageChangeFunc: function(pageIndex) {
			viewModel.listData.pageIndex(pageIndex);
			viewModel.searchClick();
		},
		//每页显示条数改变
		sizeChangeFunc: function(size, pageIndex) {
			viewModel.listData.pageSize(size);
			viewModel.listData.pageIndex(0);
			viewModel.searchClick();
		},
		//点击新增
		addClick: function(){//跳转到新增卡片洁面
			var md = document.querySelector('#seriesmargin-mdlayout')['u.MDLayout']
			viewModel.cardData.clear();
			viewModel.listData.clear();
			md.dGo('seriesmargin_cardpage');
			var r = viewModel.cardData.createEmptyRow();
		},
		//点击返回
		backClick: function(){
			viewModel.cardData.clear();
			var	md = document.querySelector('#seriesmargin-mdlayout')['u.MDLayout']
			md.dGo('seriesmargin_listpage');
			getInitData();
			
		},
		//点击修改
		updateClick:function(){
			var row = viewModel.listData.getSelectedRows();
				if(row.length!=1){
					u.messageDialog({msg:"请先选择一条修改数据",title:"提示", btnText:"OK"});
				   	return
				}
			var data = row[0].getSimpleData();
			console.log(data);
			viewModel.cardData.clear();
			var r = viewModel.cardData.createEmptyRow();
			var md = document.querySelector('#seriesmargin-mdlayout')['u.MDLayout']
			md.dGo('seriesmargin_cardpage');
			r.setSimpleData(data);
			console.log(r);
			
		},
		//点击保存
		saveClick:function(){
			
			//获取新增栏数据
			var data = viewModel.cardData.getCurrentRow().getSimpleData();
			//校验
			var validate = getvalidate(app,"#seriesmargin_cardpage");
			if(validate==false){
				return
			}
			var entity = {};
			entity.pk_series = data.pk_series;
			entity.pk_contract=data.pk_contract;
			entity.vyear = data.vyear;
			entity.vmonth = data.vmonth;
			entity.nmargin = data.nmargin;
			entity.pk_seriesmargin = data.pk_seriesmargin;
			entity.ts = data.ts;
//			onLoading();
			$.ajax({
				type: 'post',
				url: window.cturl+'/bd/seriesMarginCtrl/save',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(entity),
				success: function(data) {
					u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
					var md = document.querySelector('#seriesmargin-mdlayout')['u.MDLayout']
					md.dBack();
					getInitData();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
					getInitData();
				}
			})
		},
		afterAdd: function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},
		//车系毛利信息导入
		onUploadFile : function(){
			viewModel.importFlag = "seriesMarginImport";
			window.seriesmargin_md = u.dialog({id:'seriesmargin_testDialg',content:"#seriesmargin_dialog_content",hasCloseMenu:true});
			$('.sub-list1-new').css('display','inline-block');
		} ,
		//关闭上传附件界面
		onCloseFileWindow : function(){
			seriesmargin_md.close();
		},
		onUploadExcel : function(){
			onLoading();
			$.ajaxFileUpload({
				url: window.cturl+"/bd/seriesMarginCtrl/seriesMarginUpload",
				secureuri:false,
				fileElementId:'seriesmargin_fileName',
				dataType: 'json',
				success: function(data) {
					onCloseLoading();
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					getInitData();
					md.close();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					onCloseLoading();
					errors.error(XMLHttpRequest);
				}
			});
		},
		modelChange:function(obj){
			if(obj.field == "pk_series"){
				var param = {};
				param.pk_series = obj.newValue;
				$.ajax({
					type: 'POST',
					url: window.cturl + '/bd/seriesMarginCtrl/getSeriesDetail',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(param), 
					success: function success(data) {
						var old = {};
					    old = viewModel.cardData.getSimpleData();
					    data.tmmodel.pk_contract=old[0].pk_contract;
						data.tmmodel.vyear = old[0].vyear;
						data.tmmodel.vmonth = old[0].vmonth;
						data.tmmodel.nmargin = old[0].nmargin;
						data.tmmodel.pk_seriesmargin = old[0].pk_seriesmargin;
						data.tmmodel.ts = old[0].ts;
						console.log(data.tmmodel);
						viewModel.cardData.setSimpleData(data.tmmodel);
					},
					error: function error(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				});
			}
			
		},
		//车辆导入模板下载
		onDownload : function(){
			var form = $("<form>");   //定义一个form表单
			form.attr('style', 'display:none');   //在form表单中添加查询参数
			form.attr('target', '');
			form.attr('method', 'post');
			form.attr('action',window.cturl + "/bd/seriesMarginCtrl//importDemo");

			var input1 = $('<input>');
			input1.attr('type', 'hidden');
			input1.attr('name', '');
			input1.attr('value','');
            $('#seriesmargin-mdlayout').append(form);  //将表单放置在web中 
			form.append(input1);   //将查询参数控件提交到表单上
			form.submit();
		},
		//点击删除
		delClick: function() {
			
			var dats = [];
			var row = viewModel.listData.getSelectedRows();
			if(row.length==0){					
				u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
					return
				}
				u.confirmDialog({
					title: "确认",
					msg: "请确认是否删除选中的数据？",
					onOk: function () {
					   for(var i=0;i<row.length;i++){
							dats.push(row[i].getSimpleData());
						}
					onLoading();
			$.ajax({
				url: window.cturl+'/bd/seriesMarginCtrl/deletedata',
				type: 'POST',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(dats),
				success: function(data) {
					onCloseLoading();
					viewModel.listData.removeRows(viewModel.listData.getSelectedRows());
					u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
								getInitData();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					onCloseLoading();
								errors.error(XMLHttpRequest);
							}
			})
					}
				});
			}
	}
	viewModel = u.extend(basicDatas, events)
	//初始化数据
	var getInitData = function() {
		$.ajax({
			type: 'get',
			url: window.cturl+"/security/authBtn/auth?funcCode=F020204",
			success: function(data) {
				var funList = data;
				if(null != funList){
					for(var i=0;i<funList.length;i++){
						var funTemp = funList[i];
						if("新增"==funTemp){
							viewModel.addFunShow(true);
						}else if("修改"==funTemp){
							viewModel.editFunShow(true);
						}else if("导入模板下载"==funTemp){
							viewModel.impDemFunShow(true);
						}else if("导入"==funTemp){
							viewModel.importFunShow(true);
						}
					}
				}
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		});
		
		viewModel.listData.clear();
		viewModel.cardData.clear();
		viewModel.searchData.clear();
		viewModel.searchData.createEmptyRow();
		viewModel.listData.pageSize(10);
		viewModel.listData.pageIndex(0);
		events.searchClick();
	}
	return {
		init: function(content,tabid) {
			content.innerHTML = html;
			window.vm = viewModel;

			app = u.createApp({
				el: '#'+ tabid,
				model: viewModel
			})
			ref();
			getInitData();
			viewModel.listData.setEnable(false);
			viewModel.cardData.on("valuechange",events.modelChange);
		}
	}
});