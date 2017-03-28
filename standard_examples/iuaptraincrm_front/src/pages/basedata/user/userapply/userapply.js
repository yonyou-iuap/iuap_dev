define(['iReferComp','refComp','error','text!./userapply.html','uuigrid','utilrow'], function(iReferComp,refComp,errors,html) {
	'use strict';
	var refid;
	var dom;
	//参照
	function ref() {
		//组织-查询条件
		var pk='';
		$('#userApply_corp').each(function(i,val){
		 	var $that=$(this);
		 	dom = $that;
			var options = {
					refCode:"corp",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
		});
		//部门-查询条件
		var pk='';
		$('#userApply_dept').each(function(i,val){
		 	var $that=$(this);
		 	dom = $that;
			var options = {
					refCode:"dept",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
		});
		//组织-卡片
		var pk='';
		$('#userApply_corp_card').each(function(i,val){
		 	var $that=$(this);
		 	dom = $that;
			var options = {
					refCode:"corp",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
		});
		//部门-卡片
		var pk='';
		$('#userApply_dept_card').each(function(i,val){
		 	var $that=$(this);
		 	dom = $that;
			var options = {
					refCode:"dept",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
		});
    }

	//定义
	var app, viewModel, basicDatas, events, oper,enums,pagesize=10;
	basicDatas = {
		//查询用
		searchData: new u.DataTable({
			meta:{
				vusercode: {type: 'string'},//用户编码
				vpsncode:{type: 'string'},//人员编码
				vworkid: {type: 'string'},//工号
				pk_org: {type: 'string'},//所属组织
				pk_dept: {type: 'string'},//所属部门
				vstatus: {type: 'string'}//状态
			}
		}),
		//列表界面
		listData: new u.DataTable({
			meta: {				
				pk_userapply:{type: 'string'},
				vusercode:{type: 'string'},
				vpsncode:{type: 'string'},
				vusername:{type: 'string'},
				vid:{type: 'string'},
				vworkid:{type: 'string'},
				vphone:{type: 'string'},
				vemail:{type: 'string'},
				pk_org:{type: 'string'},
				pk_dept:{type: 'string'},
				vstatus:{type: 'string'},
				orgname:{type: 'string'},
				deptname:{type: 'string'},
				ts:{type: 'string'}
			}
		}),
		//卡片界面
		cardData: new u.DataTable({
			meta:{
				pk_userapply:{type: 'string'},
				vusercode:{type: 'string',required: true},
				vpsncode:{type: 'string',required: true},
				vusername:{type: 'string',required: true},
				vid:{type: 'string',required: true},
				vworkid:{type: 'string',required: true},
				vphone:{type: 'string',required: true},
				vemail:{type: 'string',required: true},
				pk_org:{type: 'string',required: true},
				pk_dept:{type: 'string',required: true},
				vstatus:{type: 'string'},
				ts:{type: 'string'}
			}
		}),
		//用户岗位列表
		posListData: new u.DataTable({
			meta:{
				pk_userapplypos: {
					type: 'string'
				},
				pk_userapply: {
					type: 'string'
				},
				pk_position: {
					type: 'string'
				},
				position_code: {
					type: 'string'
				},
				position_name: {
					type: 'string'
				},
				ts: {
					type: 'string'
				}
			}
		}),
		posSearchData:new u.DataTable({
			meta:{}
		}),
		roleData:new u.DataTable({
			meta:{
				id: {
					type: 'string'
				},
				position_code: {
					type: 'string'
				},
				position_name: {
					type: 'string'
				}
			}
		}),
		gridStatus: ko.observable("view")
	};
	
	
	enums = {
		usapplysts:enumerate(2029)
		};


	events = {
		/**
		 * 列表界面方法
		 */
		//点击查询按钮
		searchClick: function(){
			var rows = viewModel.searchData.getSelectedRows()
			var param = rows[0].getSimpleData();
			param.psize = pagesize;
			$.ajax({
				type: 'post',
				url: window.cturl+"/bd/userapply/queryPage",
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function(data) {
					viewModel.listData.pageSize(pagesize);//每页显示多少条
					viewModel.listData.setSimpleData(data.content);
					viewModel.listData.totalPages(data.totalPages);
					viewModel.listData.totalRow(data.totalElements); 
					viewModel.listData.setAllRowsUnSelect();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//页码改变
		pageChangeFunc: function(pageIndex) {
			var rows = viewModel.searchData.getSelectedRows()
			var param = rows[0].getSimpleData();
			param.psize = pagesize;
			param.pindex = pageIndex;
			$.ajax({
				type: 'post',
				url: window.cturl+"/bd/userapply/queryPage",
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function(data) {
					//iReferComp.setSimpleData(data, 'grid3',false);
					viewModel.listData.setSimpleData(data.content);
					viewModel.listData.totalPages(data.totalPages); //共多少页
					viewModel.listData.totalRow(data.totalElements); 
					viewModel.listData.setAllRowsUnSelect();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//每页显示条数改变
		sizeChangeFunc: function(size, pageIndex) {
			var rows = viewModel.searchData.getSelectedRows()
			var param = rows[0].getSimpleData();
			param.psize = size;
			param.pindex = pageIndex;
			$.ajax({
				type: 'post',
				url: window.cturl+"/bd/userapply/queryPage",
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function(data) {
					viewModel.listData.pageSize(size);//每页显示多少条
					viewModel.listData.setSimpleData(data.content);
					viewModel.listData.totalPages(data.totalPages);
					viewModel.listData.totalRow(data.totalElements); 
					viewModel.listData.setRowUnFocus();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//点击新增按钮
		addClick: function() {
			viewModel.gridStatus("new");
			viewModel.cardData.setEnable(true);
			viewModel.cardData.clear();
			var r = viewModel.cardData.createEmptyRow();
			viewModel.posListData.clear();
			var md = document.querySelector('#userApply-mdlayout')['u.MDLayout'];
			md.dGo('userApply_new');
		},
		//点击修改按钮
		editClick: function() {
			viewModel.gridStatus("edit");
			viewModel.cardData.setEnable(true);
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();
			if(null==rows||rows.length==0){
				u.messageDialog({msg:"请选择单据！",title:"提示", btnText:"OK"});
				return;
			}else if(rows.length>1){
				u.messageDialog({msg:"只能选择一条单据！",title:"提示", btnText:"OK"});
				return;
			}
			//操作当前行
			var row = viewModel.listData.getCurrentRow();
			var param = row.getSimpleData();
			if(param.vstatus=='20291002'){
				u.messageDialog({msg:"用户申请审核中，无法修改！",title:"提示", btnText:"OK"});
				return;
			}
			if(param.vstatus=='20291004'){
				u.messageDialog({msg:"用户申请已通过审核，无法修改！",title:"提示", btnText:"OK"});
				return;
			}
			viewModel.cardData.setSimpleData(param);
			var pk = param.pk_userapply;
			//查找用户对应岗位
			viewModel.queryUserPos(pk);
			var md = document.querySelector('#userApply-mdlayout')['u.MDLayout'];
			md.dGo('userApply_new');
		},
		queryUserPos:function(id){
			$.ajax({
				type: 'get',
				url: window.cturl+"/bd/userapply/queryUserPositionByPk/"+id,
				success: function(data) {
					viewModel.posListData.setSimpleData(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//点击查看按钮
		showClick: function(id){
			viewModel.gridStatus("view");
			viewModel.listData.setRowSelect(id);
			//获取选中行
			var row = viewModel.listData.getCurrentRow();
			var param = row.getSimpleData();
			viewModel.cardData.setSimpleData(param);
			var pk = param.pk_userapply;
			viewModel.queryUserPos(pk);
			var md = document.querySelector('#userApply-mdlayout')['u.MDLayout'];
			md.dGo('userApply_new');
		},
		//点击删除按钮
		delClick: function() {
			var rows = viewModel.listData.getSelectedRows();
			if(null==rows){
				u.messageDialog({msg:"请选择单据！",title:"提示", btnText:"OK"});
				return;
			}else if(rows.length>1){
				u.messageDialog({msg:"只能选择一条单据！",title:"提示", btnText:"OK"});
				return;
			}
			//操作当前行
			var row = viewModel.listData.getCurrentRow();
			var p = row.getSimpleData();
			if(p.vstatus=='20291002'){
				u.messageDialog({msg:"用户申请审核中，无法操作！",title:"提示", btnText:"OK"});
				return;
			}
			if(p.vstatus=='20291004'){
				u.messageDialog({msg:"用户申请已通过审核，无法操作！",title:"提示", btnText:"OK"});
				return;
			}
			//确认删除对话框
			u.confirmDialog({
				title: "确认",
				msg: "是否删除此用户申请？",
				onOk: function () {
					//如有页面级逻辑在这里补充
					//todo...
					
					//调用后台删除方法
					$.ajax({
						url: window.cturl+'/bd/userapply/deleteUserApply',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(row.getSimpleData()),
						success: function(data) {
							//删除成功后将页面选中行移除
							viewModel.listData.removeRow(row);
							//成功后页面提示
							u.messageDialog({msg:"删除成功！",title:"提示", btnText:"OK"});
							//也可重新调用初始化查询方法，
							//getInitData();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			});
		},
		//点击提交按钮
		submitClick: function() {
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();
			if(null==rows){
				u.messageDialog({msg:"请选择单据！",title:"提示", btnText:"OK"});
				return;
			}else if(rows.length>1){
				u.messageDialog({msg:"只能选择一条单据！",title:"提示", btnText:"OK"});
				return;
			}
			//操作当前行
			var row = viewModel.listData.getCurrentRow();
			var p = row.getSimpleData();
			if(p.vstatus=='20291002'){
				u.messageDialog({msg:"用户申请审核中，无法操作！",title:"提示", btnText:"OK"});
				return;
			}
			if(p.vstatus=='20291004'){
				u.messageDialog({msg:"用户申请已通过审核，无法操作！",title:"提示", btnText:"OK"});
				return;
			}
			//确认提交对话框
			u.confirmDialog({
				title: "确认",
				msg: "是否提交此用户申请？",
				onOk: function () {
					//如有页面级逻辑在这里补充
					//todo...
					
					//调用后台删除方法
					$.ajax({
						url: window.cturl+'/bd/userapply/submitUserApply',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(row.getSimpleData()),
						success: function(data) {
							//成功后页面提示
							u.messageDialog({msg:"提交成功！",title:"提示", btnText:"OK"});
							//也可重新调用初始化查询方法，
							getInitData();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			});
		},
		changeClick: function(){
			
		},
		/**
		 * 卡片界面方法
		 */
		//点击返回按钮
		onBackClick: function() {
			viewModel.cardData.clear();
			var md = document.querySelector('#userApply-mdlayout')['u.MDLayout'];
			md.dGo('userApply_list');
		},
		//点击保存按钮
		onSaveClick: function() {
			//表单校验
			var validate = getvalidate(app,"#userApply_new");
			if(validate==false){
				return
			};
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			//获取用户岗位列表
			var mrows = viewModel.posListData.getSimpleData();
			main.positions = mrows;
			$.ajax({
				type: 'post',
				url: window.cturl+'/bd/userapply/saveUserApply',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(main),
				success: function(data) {
					//提示框
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					//返回列表界面
					var md = document.querySelector('#userApply-mdlayout')['u.MDLayout'];
					md.dGo('userApply_list');
					//重新执行页面加载方法
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
		roleaddClick:function(){
			viewModel.posSearchData.createEmptyRow();
			var md = document.querySelector('#userApply-mdlayout')['u.MDLayout'];
			md.dGo('userApply_poslist');
			viewModel.posSearchClick();
		},
		posSearchClick: function() {
			var rows = viewModel.posSearchData.getSelectedRows()
			var param = rows[0].getSimpleData();
			param.psize = pagesize;
			$.ajax({
				type: 'post',
				url: window.cturl+"/bd/userapply/queryPositionUser",
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function(data) {
					viewModel.roleData.pageSize(pagesize);//每页显示多少条
					viewModel.roleData.setSimpleData(data.content);
					viewModel.roleData.totalPages(data.totalPages);
					viewModel.roleData.totalRow(data.totalElements); 
					viewModel.roleData.setAllRowsUnSelect();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//岗位选择页码改变
		posPageChangeFunc: function(pageIndex) {
			var rows = viewModel.posSearchData.getSelectedRows()
			var param = rows[0].getSimpleData();
			param.psize = pagesize;
			param.pindex = pageIndex;
			$.ajax({
				type: 'post',
				url: window.cturl+"/bd/userapply/queryPositionUser",
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function(data) {
					viewModel.roleData.setSimpleData(data.content);
					viewModel.roleData.totalPages(data.totalPages); //共多少页
					viewModel.roleData.totalRow(data.totalElements); 
					viewModel.roleData.setAllRowsUnSelect();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//岗位选择每页显示条数改变
		posSizeChangeFunc: function(size, pageIndex) {
			var rows = viewModel.posSearchData.getSelectedRows();
			var param = rows[0].getSimpleData();
			param.psize = size;
			param.pindex = pageIndex;
			$.ajax({
				type: 'post',
				url: window.cturl+"/bd/userapply/queryPositionUser",
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function(data) {
					viewModel.roleData.pageSize(size);//每页显示多少条
					viewModel.roleData.setSimpleData(data.content);
					viewModel.roleData.totalPages(data.totalPages);
					viewModel.roleData.totalRow(data.totalElements); 
					viewModel.roleData.setRowUnFocus();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//岗位确认选择
		confirmClick: function() {
			var positionRow = viewModel.roleData.getSelectedRows();
			var userRow = viewModel.cardData.getSelectedRows();
			if(positionRow.length<=0){
				u.messageDialog({msg:"请选择要关联的岗位",title:"提示", btnText:"OK"});
				return;
			}
			
			var main = [];
			for(var i=0;i<positionRow.length;i++){
				main.push({	  "pk_userapply":userRow[0].getSimpleData().pk_userapply,
				        	  "pk_position":positionRow[i].getSimpleData().id,
				        	  "position_code":positionRow[i].getSimpleData().position_code,
				        	  "position_name":positionRow[i].getSimpleData().position_name
				          });
			}
//			viewModel.posListData.setSimpleData(main);
			viewModel.posListData.addSimpleData(main);
			var md = document.querySelector('#userApply-mdlayout')['u.MDLayout'];
			md.dGo('userApply_new');
		},
		//删除岗位
		delPosClick: function(){
			var rows = viewModel.posListData.getSelectedRows();
			if(rows.length<=0){
				u.messageDialog({msg:"请选择要删除的岗位",title:"提示", btnText:"OK"});
				return;
			}
			//确认删除对话框
			u.confirmDialog({
				title: "确认",
				msg: "是否删除选择的岗位？",
				onOk: function () {
					//如有页面级逻辑在这里补充
					//todo...
					for(var i=0;i<rows.length;i++){
						var pk = rows[i].getSimpleData().pk_userapplypos;
						var row = rows[i];
						if(null==pk||pk==""){
							viewModel.posListData.removeRow(row);
						}else{
							viewModel.delPosByPk(row);
							viewModel.posListData.removeRow(row);
						}
					}
				}
			});
		},
		delPosByPk:function(row){
			$.ajax({
				url: window.cturl+'/bd/userapply/delPosition',
				type: 'POST',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(row.getSimpleData()),
				success: function(data) {
					
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		posCancelClick:function(){
			var md = document.querySelector('#userApply-mdlayout')['u.MDLayout'];
			md.dBack();
		}
	}

	viewModel = u.extend(basicDatas,events,enums)

	var getInitData = function() {
		viewModel.searchData.createEmptyRow();
		viewModel.searchClick();
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
			ref();
			viewModel.listData.setEnable(false);
		}
	}
});