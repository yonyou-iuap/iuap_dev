define(['error','text!./salecutratio.html','uuigrid','utilrow'], 
		function(errors,html) { 
	'use strict';
	// 定义
	var app, viewModel, basicDatas, events, oper,enums,pageSize=10;
	basicDatas = {
			// 查询用
			searchData: new u.DataTable({
				meta:{
					search_nyear: {type: 'integer'}, // 年度
				}
			}),
		// 列表界面
		listData: new u.DataTable({
			meta: {
				pk_salecutratio: {type: 'string'},// 提成主键
				nyear: {type: 'integer'},// 提成年度
				ncutratio: {type: 'string'},// 提成系数
				ncontract: {type: 'string'},// 合同占比
				ncountry:{type: 'string'},// 国补占比
				nlocation: {type: 'string'},// 地补占比
				vinvoice: {type: 'string'},// 开票是否包含地补
				visbyd: {type: 'string'},// 是否申报地补
				vinvoice_co: {type: 'string'},// 开票是否包含国补
				visbyd_co: {type: 'string'}// 是否申报国补
			}
		}),
		// 卡片界面(新增,修改)
		cardData: new u.DataTable({
			meta: {
				nyear: {type: 'integer',
					 				  required: true,
					 				  placement: 'top'},
				ncutratio: {type: 'float',
									  required: true,
									  placement: 'top'},
				ncontract:{type: 'float',
									  required: true,
									  placement: 'top'},
				ncountry:{type: 'float',
									  required: true,
									  placement: 'top'},
				nlocation:{type: 'float',
									  required: true,
									  placement: 'top'},
			    vinvoice:{type: 'string',
									  required: true,
									  placement: 'top'},
				visbyd:{type: 'string',
									  required: true,
									  placement: 'top'},
				vinvoice_co:{type: 'string',
									  required: true,
									  placement: 'top'},
                visbyd_co:{type: 'string',
									  required: true,
									  placement: 'top'}
			}
		}),
		//新增
		addFunShow : ko.observable(false),
		//修改
		editFunShow : ko.observable(false),
		//删除
		delFunShow : ko.observable(false),
	};
	enums = {
			// 年度枚举
			years: [    {"value": "2016","name": "2016"}, 
			            {"value": "2017","name": "2017"},
			            {"value": "2018","name": "2018"},
			            {"value": "2019","name": "2019"},
			            {"value": "2020","name": "2020"}],
			// 是否枚举
			            yesORno: enumerate(1001),
	};

	events = {
		/**
		 * 列表界面方法
		 */
		// 点击查询按钮
			searchClick: function(){
				var queryData = {};
				var search_nyear = viewModel.searchData.getSimpleData()[0];
				queryData["search_nyear"] = viewModel.searchData.getSimpleData()[0].search_nyear;
				queryData["page"] = viewModel.listData.pageIndex();
				viewModel.listData.pageSize(pageSize);
		        queryData["page.size"] = viewModel.listData.pageSize();
//		        onLoading();
				$.ajax({
					type: 'get',
					url : window.cturl + '/basedata/ratio/queryratio',
					data: queryData,
					success: function(data) {
						viewModel.listData.pageSize(data.size);// 每页显示多少条
						viewModel.listData.setSimpleData(data.content);
						viewModel.listData.totalPages(data.totalPages);
						viewModel.listData.totalRow(data.totalElements); 
						viewModel.listData.setAllRowsUnSelect();
						onCloseLoading();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
						onCloseLoading();
					}
				})
			},
			// 列表页码改变
			pageChangeFunc: function(pageIndex) {
				viewModel.listData.pageIndex(pageIndex);
				viewModel.searchClick();
			},
			// 列表每页显示条数改变
			sizeChangeFunc: function(size, pageIndex) {
				pageSize = size;
				viewModel.listData.pageIndex(0);
				viewModel.searchClick();
			},
			// 点击新增按钮
			addClick: function() {
				viewModel.cardData.clear();
				viewModel.cardData.setEnable(true);
				var r = viewModel.cardData.createEmptyRow();
				var md = document.querySelector('#salecutratio-mdlayout')['u.MDLayout'];
				md.dGo('salecutratio_new');
			},
			rowClick: function() {
			},
			afterAdd: function(element, index, data) {
				if (element.nodeType === 1) {
					u.compMgr.updateComp(element);
				}
				},
			// 点击修改按钮
			editClick: function() {
				var rows = viewModel.listData.getSelectedRows();
				if(rows==null || rows.length!=1){
					u.messageDialog({msg:"请选择一条数据且只能选择一条！",title:"提示", btnText:"OK"});
					return;
				}
//				viewModel.cardData.clear();
//				var r = viewModel.cardData.createEmptyRow();
				var mainData = rows[0].data;
				viewModel.cardData.setSimpleData(mainData);
				
				var md = document.querySelector('#salecutratio-mdlayout')['u.MDLayout'];
				md.dGo('salecutratio_update');
			},
			
			// 点击删除按钮
			delClick: function() {
				// 获取选中行
				var dats = [];
				var row = viewModel.listData.getSelectedRows();
				if(row.length==0){
					u.messageDialog({msg:"*请选择要删除的数据",title:"提示", btnText:"OK"});
					return;
				}
				for(var i=0;i<row.length;i++){
						dats.push(row[i].getSimpleData());
				}
				
				// 确认删除对话框
				u.confirmDialog({
					title: "确认",
					msg: "是否删除此条信息？",
					onOk: function () {
						// 调用后台删除方法
						onLoading();
						$.ajax({
							url : window.cturl + '/basedata/ratio/deletedata',
							type: 'POST',
							dataType: 'json',
							contentType: "application/json ; charset=utf-8",
							data: JSON.stringify(dats),
							success: function(data) {
								viewModel.listData.removeRows(row);
								u.messageDialog({msg:"删除成功！",title:"提示", btnText:"OK"});
								onCloseLoading();
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
								onCloseLoading();
							}
						})
					}
				});
			},
			/**
			 * 卡片界面方法
			 */
			// 点击返回按钮
			onBackClick: function() {
//				viewModel.cardData.clear();
				var md = document.querySelector('#salecutratio-mdlayout')['u.MDLayout'];
				md.dBack();
	
			},
			// 新增页面点击保存按钮
			onSaveClick: function() {
				// 表单校验
				var validate = getvalidate(app,"#salecutratio_new");
				if(validate==false){
					return;
				};
				// 获取表单
				var row = viewModel.cardData.getCurrentRow();	
				// 校验
				var num =(parseFloat(row.getValue("ncontract"))+parseFloat(row.getValue("ncountry"))+parseFloat(row.getValue("nlocation"))).toPrecision(3);
				if(parseFloat(row.getValue("ncutratio")).toPrecision(3)<=0){
					u.messageDialog({msg:"注意:提成系数必须大于1",title:"提示", btnText:"OK"});
					return;
				}else if(num!=1.0){
					u.messageDialog({msg:"注意:合同,国补,地补占比之和必须为1",title:"提示", btnText:"OK"});
					return;
				}else if(parseFloat(row.getValue("ncontract")).toPrecision(3)<0 || parseFloat(row.getValue("ncontract")).toPrecision(3)>1){
					u.messageDialog({msg:"合同占比只能大于等于0小于等于1",title:"提示", btnText:"OK"});
					return;
				}else if(parseFloat(row.getValue("ncountry")).toPrecision(3)<0 || parseFloat(row.getValue("ncountry")).toPrecision(3)>1){
					u.messageDialog({msg:"国补占比只能大于等于0小于等于1",title:"提示", btnText:"OK"});
					return;
				}else if(parseFloat(row.getValue("nlocation")).toPrecision(3)<0 || parseFloat(row.getValue("nlocation")).toPrecision(3)>1){
					u.messageDialog({msg:"地补占比只能大于等于0小于等于1",title:"提示", btnText:"OK"});
					return;
				}
				var a = [];
				a.push(row.getSimpleData());
//				viewModel.cardData.clear();
				onLoading();
				$.ajax({
					type: 'POST',
					url : window.cturl + '/basedata/ratio/save',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(a),
					success: function(data) {
						// 提示框
						u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
						// 返回列表界面
						var md = document.querySelector('#salecutratio-mdlayout')['u.MDLayout'];
						md.dBack();
						// 重新执行页面加载方法
						getInitData();
						onCloseLoading();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
						getInitData();
						onCloseLoading();
					}
				})
			},
			// 点击修改页面保存按钮
			onUpdateClick: function() {
				// 表单校验(u.js报错:.lengthb,此处校验暂时搁置不用)
				var validate = getvalidate(app,"#salecutratio_update");
				if(validate==false){
					return
				};
				// 获取表单
				var row = viewModel.cardData.getCurrentRow();
				// 校验
				var num =(parseFloat(row.getValue("ncontract"))+parseFloat(row.getValue("ncountry"))+parseFloat(row.getValue("nlocation"))).toPrecision(3);
				if(parseFloat(row.getValue("ncutratio")).toPrecision(3)<=0){
					u.messageDialog({msg:"注意:提成系数必须大于1",title:"提示", btnText:"OK"});
					return;
				}else if(num!=1.0){
					u.messageDialog({msg:"注意:合同,国补,地补占比之和必须为1",title:"提示", btnText:"OK"});
					return;
				}else if(parseFloat(row.getValue("ncontract")).toPrecision(3)<0 || parseFloat(row.getValue("ncontract")).toPrecision(3)>1){
					u.messageDialog({msg:"合同占比只能大于等于0小于等于1",title:"提示", btnText:"OK"});
					return;
				}else if(parseFloat(row.getValue("ncountry")).toPrecision(3)<0 || parseFloat(row.getValue("ncountry")).toPrecision(3)>1){
					u.messageDialog({msg:"国补占比只能大于等于0小于等于1",title:"提示", btnText:"OK"});
					return;
				}else if(parseFloat(row.getValue("nlocation")).toPrecision(3)<0 || parseFloat(row.getValue("nlocation")).toPrecision(3)>1){
					u.messageDialog({msg:"地补占比只能大于等于0小于等于1",title:"提示", btnText:"OK"});
					return;
				}
				// 定义数组类型传参,以适应后台list参数
				var a = [];
				a.push(row.getSimpleData());
//				viewModel.cardData.clear();
				onLoading();
				$.ajax({
					type: 'POST',
					url : window.cturl + '/basedata/ratio/save',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(a),
					success: function(data) {
						// 提示框
						u.messageDialog({msg:'修改成功',title:"提示", btnText:"OK"});
						// 返回列表界面
						var md = document.querySelector('#salecutratio-mdlayout')['u.MDLayout'];
						md.dBack();
						// 重新执行页面加载方法
						getInitData();
						onCloseLoading();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
						getInitData();
						onCloseLoading();
					}
				})
			}
			// 点击行事件
			// 单选框点击事件
		}
	
		viewModel = u.extend(basicDatas,events,enums)
		// 加载数据
		var getInitData = function() {
        $.ajax({
			type: 'get',
			url: window.cturl+"/security/authBtn/auth?funcCode=F020208",
			success: function(data) {
				var funList = data;
				if(null != funList){
					for(var i=0;i<funList.length;i++){
						var funTemp = funList[i];
						if("新增"==funTemp){
							viewModel.addFunShow(true);
						}else if("修改"==funTemp){
							viewModel.editFunShow(true);
						}else if("删除"==funTemp){
							viewModel.delFunShow(true);
						}
					}
				}
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		});
			viewModel.searchData.createEmptyRow();
			viewModel.searchClick();// 调用查询方法,模拟默认查询条件,全查询
		}
	
		return {
			init: function(content,tabid) {
				content.innerHTML = html;
				window.vm = viewModel;
				app = u.createApp({
					el: '#'+tabid,
					model: viewModel
				})
				getInitData();
				viewModel.listData.setEnable(false);
			}
		}
});