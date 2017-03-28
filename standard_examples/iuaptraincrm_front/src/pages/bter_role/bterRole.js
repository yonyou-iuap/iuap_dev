define(['error','text!./bterRole.html','dtree','css!./productmgr.css','uuigrid','uuitree','utilrow'], function (errors,html,dtree) {
	'use strict';

	var _CONST = {
			STATUS_ADD: 'add',
			STATUS_INIT: 'init',
			STATUS_EDIT: 'edit'
	},
	app,
	viewModel,
	treeSetting,
	treeSetting2,
	basicDatas,
	computes,
	oper,
	events,
	lsroleid,
	funcid,
	pagesize=10,//记录当前页面每页显示多少条
	lsrolecode;
	treeSetting = {
			view:{
				showLine:false,
	            autoCancelSelected: true,
	            selectedMulti:true
	        },
			"check" : {
				"enable" : false
			},
			callback : {
				onClick : function(e, id, node) {
					viewModel.setCurrentRow(node.id);// 设置选中行
					var listdat = viewModel.listData.getCurrentRow();
					var dat = viewModel.functionData.getCurrentRow();
					funcid = dat.getSimpleData().id;
					var enableaction = dat.getSimpleData().enableAction;
					if("true" == enableaction){
						//加载当前功能节点下按钮
						$.ajax({
							type : 'get',
							url : window.cturl+'/security/extfuncactivity/page?search_EQ_funcID='
								+ node.id+"&search_EQ_isactive=Y",
								dataType : 'json',
								contentType : "application/json ; charset=utf-8",
								success : function(data) {
									var data1 = data['content'];
									viewModel.activityChildData.setSimpleData(data1);
									viewModel.activityChildData.setAllRowsUnSelect();
									//加载当前功能节点下已分配权限按钮
									$.ajax({
										type : 'get',
										url : window.cturl+"/security/extrolefunctiondefine/pagePermAction?roleId="+ lsroleid+"&funcId="+funcid+"",
										dataType : 'json',
										contentType : "application/json ; charset=utf-8",
										success : function(data2) {
											var sIndices = [];
											var rows = viewModel.activityChildData.getAllRows();
											for(var i=0;i<rows.length;i++){
												var id = rows[i].getSimpleData().id;
												for(var ii=0;ii<data2.length;ii++) {
													var dataid = data2[ii];
													if(dataid == id){
														sIndices.push(rows[i]);
													}
												}
											}
//											lsroleid = listdat.getSimpleData().id;
//											lsrolecode = listdat.getSimpleData().roleCode;
											viewModel.activityChildData.setRowsSelect(sIndices);
										},
										error: function(XMLHttpRequest, textStatus, errorThrown) {
											errors.error(XMLHttpRequest);
										}
									})
								},
								error: function(XMLHttpRequest, textStatus, errorThrown) {
									errors.error(XMLHttpRequest);
								}
						})
					}else{
						u.messageDialog({
							msg : "该功能节点未开启按钮权限",
							title : "提示",
							btnText : "OK"
						});

					}
				}
			}
	},
		
	basicDatas = {
			treeSetting : treeSetting,
			//功能注册
			functionData: new u.DataTable({
				meta: {
					funcCode: {
						type: 'string'
					},
					funcName: {
						type: 'string'
					},
					parentId: {
						type: 'string'
					},
					enableAction: {
						type: 'string'
					},
					id: {
						type: 'string'
					}
				}
			}),
			activityChildData : new u.DataTable({
				meta : {
					activityName : {
						type : 'string'
					},
					activityCode : {
						type : 'string'
					},
//					funcCode : {
//						type : 'string'
//					},
					funcID : {
						type : 'string'
					},
					isactive : {
						type : 'string'
					},
					id : {
						type : 'string'
					}
				}
			}),
			//角色
			listData: new u.DataTable({
				meta: {
					role_code: {
						type: 'string',required: true
					},
					id: {
						type: 'string'
					},
					role_name: {
						type: 'string',required: true
					},
					dataRole: {
						type: 'string',required: true
					},
					ts:{type: 'string'},
					dr:{type: 'int'}
				}
			}),
			//角色查询
			searchData: new u.DataTable({
				meta: {
					roleCode: {
						type: 'string'
					},
					roleName: {
						type: 'string'
					}
				}
			}),
			userData: new u.DataTable({
				meta: {
					id: {
						type: 'string'
					},
					position_name: {
						type: 'string'
					},
					position_code: {
						type: 'string'
					},
					pk_org: {
						type: 'string'
					},
					pk_dept: {
						type: 'string'
					},
					position_status: {
						type: 'string'
					},
					remark: {
						type: 'string'
					},
					creator: {
						type: 'string'
					},
					creationtime: {
						type: 'string'
					},
					modifier: {
						type: 'string'
					},
					modifiedtime: {
						type: 'string'
					},
					dr: {
						type: 'string'
					},
					ts: {
						type: 'string'
					}
				}
			}),
			//岗位关联人员
			userRoleData: new u.DataTable({
				meta: {
					id: {
						type: 'string'
					},
					position_name: {
						type: 'string'
					},
					position_code: {
						type: 'string'
					},
					pk_org: {
						type: 'string'
					},
					pk_dept: {
						type: 'string'
					},
					position_status: {
						type: 'string'
					},
					remark: {
						type: 'string'
					},
					creator: {
						type: 'string'
					},
					creationtime: {
						type: 'string'
					},
					modifier: {
						type: 'string'
					},
					modifiedtime: {
						type: 'string'
					},
					dr: {
						type: 'string'
					},
					ts: {
						type: 'string'
					}
				}
			}),
			//角色关联功能/按钮
			functionRoleData: new u.DataTable({
				meta: {
					permissionCode: {
						type: 'string'
					},
					permissionId: {
						type: 'string'
					},
					permissionType: {
						type: 'string'
					},
					roleId: {
						type: 'string'
					},
					roleCode: {
						type: 'string'
					}
				}
			}),
			formStatus: ko.observable(_CONST.STATUS_INIT),
			gridStatus : ko.observable("init")

	};

	computes = {
			refFormInputValue: function(field) {
				return ko.pureComputed({
					read: function read() {

						if (viewModel.formStatus() == _CONST.STATUS_ADD) {
							var fr = this.getFocusRow();
							return fr != null ? fr.ref(field) : '';
						} else if (viewModel.formStatus() == _CONST.STATUS_EDIT) {
							var srs = this.getSelectedRows();
							return srs.length > 0 ? srs[0].ref(field) : '';
						}
					},
					owner: viewModel.listData
				});
			},
			dataRole:enumerate(1006)
	};

	events = {
			//角色关联功能，选中or取消选中事件
			xzmenth : function(obj){
				selected(obj);
			},
			qxmenth : function(obj){
				unselected(obj);
			},
			setCurrentRow : function(id) {// 设置选中行
				var allrow = viewModel.functionData.getAllRows();
				if (allrow && id) {
					for ( var i in allrow) {
						var row = allrow[i];
						if (row instanceof u.Row)
							if (row.getValue('id') == id) {
								viewModel.functionData.setRowSelect(row);
							}
					}
				}

			},
			/**
			 * 分页
			 * 
			 */
			pageChangeFunc: function(pageIndex) {
				$.ajax({
					type: 'get',
					url:window.cturl+"/security/extroledefine/page?page.size="+pagesize+"&page="+(pageIndex+1)+"",
					success: function(data) {
						var total = data.totalElements;//共多少条
						var data =data['content'];
						viewModel.listData.setSimpleData(data);
						viewModel.listData.setAllRowsUnSelect();
						var pages = CalculatePageCount(pagesize,total);//计算共多少页
						viewModel.listData.totalPages(pages); //共多少页
						viewModel.listData.totalRow(total); 
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			sizeChangeFunc: function(size, pageIndex) {
				pagesize=size;
				getInitData();
			},
			/**
			 * 角色事件
			 * 
			 * 
			 * 
			 */
			// 角色新增
			addClick: function() {
				//设置按钮状态
				document.getElementById("role_userdiv").style.display ="none";
//				document.getElementById("addPage").style.display ="block";
				viewModel.formStatus(_CONST.STATUS_ADD);
				viewModel.gridStatus("init");
				var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
				md.dGo('role_addPage');
				viewModel.formStatus(_CONST.STATUS_ADD);
				viewModel.userRoleData.removeAllRows();
				var r = viewModel.listData.createEmptyRow();
				viewModel.listData.setRowFocus(r);
//				viewModel.userRoleData.setSimpleData(null);
			},
			// 角色编辑
			editClick: function() {
				//设置按钮状态
				if (viewModel.listData.getSelectedRows().length != 1) {
					u.messageDialog({
						msg : "请选择一行操作数据",
						title : "提示",
						btnText : "OK",
						showSeconds:"2"
					});
					return
				}
				viewModel.userRoleData.clear();
				viewModel.formStatus(_CONST.STATUS_EDIT);
				viewModel.gridStatus("init");
				var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
				md.dGo('role_addPage');
//				document.getElementById("addPage").style.display ="block";
				document.getElementById("role_userdiv").style.display ="none";
				var r = viewModel.listData.getSelectedRows()[0];
				r.originData = r.getSimpleData();
			},
			//角色查询分页
			searchClick: function() {
				var row = viewModel.searchData.getCurrentRow();
				var datas = {};
				datas["page.size"] = pagesize;
				if(row!=null){
					datas["params"] = row.getSimpleData();			
				}else{
					datas["params"] = null;
				}
				$.ajax({
					type: 'post',
					url:window.cturl+"/security/extroledefine/queryArea",
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(datas),
					success: function(data) {
//						var total = data.totalElements;//共多少条
//						viewModel.listData.setSimpleData(data.content);
//						viewModel.listData.pageSize(pagesize);//每页显示多少条
//						var pages = CalculatePageCount(pagesize,total);//计算共多少页
//						viewModel.listData.totalPages(pages); //共多少页
//						viewModel.listData.totalRow(total);
						
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
			// 角色删除
			delClick: function() {
				var dats = [];
				var row = viewModel.listData.getSelectedRows();
				if(row==null){
					u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
					return
				}
				for(var i=0;i<row.length;i++){
					dats.push(row[i].getSimpleData());
				}
				u.confirmDialog({
					title: "确认",
					msg: "请确认是否删除选中的数据？",
					onOk: function () {
						$.ajax({
							url: window.cturl+'/security/extroledefine/delete',
							type: 'DELETE',
							dataType: 'json',
							data: JSON.stringify(dats),
							contentType: "application/json ; charset=utf-8",
							success: function(data) {
								viewModel.listData.removeRows(viewModel.listData.getSelectedRows());
								u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
				});
			},
			// 角色保存
			saveClick: function() {
//				var result = app.compsValidateMultiParam({element:document.querySelector("#role_addPage"), showMsg: true});
				var vlidate = getvalidate(app, "#role_addPage");
				if (vlidate == false){
//				 u.messageDialog({msg:"请检查必输项!",title:"操作提示",btnText:"确定"});
				}else{
				 //校验通过操作
				 var dat = viewModel.listData.getCurrentRow();
				// 新增保存
				if (viewModel.formStatus() == _CONST.STATUS_ADD){ 
					$.ajax({
						type: 'POST',
						url: window.cturl+'/security/extroledefine/create',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(dat.getSimpleData()),
						success: function(data) {
							if(data.flag==1){
								u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
								viewModel.listData.getCurrentRow().setSimpleData(dat.getSimpleData());
								var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
								md.dBack();
								getInitData();
							}else{
								u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}else if(viewModel.formStatus() == _CONST.STATUS_EDIT){ // 编辑保存
					$.ajax({
						type: 'POST',
						url: window.cturl+'/security/extroledefine/update',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(dat.getSimpleData()),
						success: function(data) {
							if(data.flag==1){
								u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
								var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
								md.dBack();
								getInitData();
							}else{
								u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
//				document.getElementById("addPage").style.display ="none";
			}
			},

			/**
			 * 关联用户事件
			 * 
			 * 
			 * 
			 */
			pageChangeFund: function(pageIndex) {
				  var roleId = row[0].getSimpleData().id;
				   $.ajax({
						type: 'get',
						url:window.cturl+"/security/extposition/queryAddForRole?id="+roleId+"&pageSize="+pagesize+"&pageIndex="+pageIndex,
						success: function(data) {
							viewModel.userData.pageSize(data.size);//每页显示多少条
							viewModel.userData.setSimpleData(data.content);
							viewModel.userData.totalPages(data.totalPages);
							viewModel.userData.totalRow(data.totalElements); 
							viewModel.userData.setRowUnFocus();
						}
					})
			},
			sizeChangeFund: function(size, pageIndex) {
				pagesize=size;
				var roleId = row[0].getSimpleData().id;
			   $.ajax({
					type: 'get',
					url:window.cturl+"/security/extposition/queryAddForRole?id="+roleId+"&pageSize="+pagesize+"&pageIndex="+0,
					success: function(data) {
						viewModel.userData.pageSize(data.size);//每页显示多少条
						viewModel.userData.setSimpleData(data.content);
						viewModel.userData.totalPages(data.totalPages);
						viewModel.userData.totalRow(data.totalElements); 
						viewModel.userData.setRowUnFocus();
					}
				})
			},
			//关联岗位信息
			assuserClick: function assignClick(e,index,colIndex) {
				   var row = viewModel.listData.getSelectedRows();
				   document.getElementById('role_light').style.display='block';
				   document.getElementById('role_fade').style.display='block';
				   var roleId = row[0].getSimpleData().id;
				   $.ajax({
						type: 'get',
						url:window.cturl+"/security/extposition/queryAddForRole?id="+roleId+"&pageSize="+pagesize+"&pageIndex="+0,
						success: function(data) {
//							var total = data.totalElements;//共多少条
//							var data =data['content'];
//							viewModel.userData.setSimpleData(data);
//							viewModel.userData.pageSize(pagesize);//每页显示多少条
//							var pages = CalculatePageCount(pagesize,total);//计算共多少页
//							viewModel.userData.totalPages(pages); //共多少页
//							viewModel.userData.totalRow(total); 
//							viewModel.userData.setAllRowsUnSelect();
							
							viewModel.userData.pageSize(data.size);//每页显示多少条
							viewModel.userData.setSimpleData(data.content);
							viewModel.userData.totalPages(data.totalPages);
							viewModel.userData.totalRow(data.totalElements); 
							viewModel.userData.setRowUnFocus();
						}
					})
			},
			canceluserClick: function cancelClick(e,index,colIndex) {
				document.getElementById('role_light').style.display='none';
				document.getElementById('role_fade').style.display='none';
			},
			//分配岗位【确认】
			okClick: function okClick(e,index,colIndex) {
				document.getElementById('role_light').style.display='none';
				document.getElementById('role_fade').style.display='none';
				var rowuser = viewModel.userData.getSelectedRows();
				if(rowuser.length<=0){
					u.messageDialog({msg:"请选择关联的用户",title:"提示", btnText:"OK"});
					return;
				}
				oper="adduser";
				viewModel.gridStatus("edit");
				var roleRow = viewModel.listData.getSelectedRows();
				var sIndices = [];
				for(var i=0;i<rowuser.length;i++){
					sIndices.push(
                        {   "role_id":roleRow[0].getSimpleData().id,
                        	"role_code":roleRow[0].getSimpleData().role_code,
                        	"position_id":rowuser[i].getSimpleData().id,
                        	"position_code":rowuser[i].getSimpleData().position_code
                        });
				}
				
				$.ajax({
					type: 'post',
					url: window.cturl+'/security/extposition/savePositionForRole',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(sIndices),
					success: function(data) {
						//提示框
						u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
						
						//刷新岗位信息
						var dat = viewModel.listData.getSelectedRows()[0];
						$.ajax({
							type: 'get',
							url:window.cturl+'/security/extposition/queryForRole/'+dat.getSimpleData().id,
							success: function(data) {
								viewModel.userRoleData.setSimpleData(data);
								viewModel.userRoleData.setAllRowsUnSelect();
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
						
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			// 关联岗位【删除】
			deluserClick: function() {
				var rows = viewModel.userRoleData.getSelectedRows();
				var roleRow = viewModel.listData.getSelectedRows();
				if(rows.length<1){
					u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
					return
				}
				var sIndices = [];
//				var rows = viewModel.userRoleData.getAllRows();
				for(var i=0;i<rows.length;i++){
					sIndices.push(
	                        {   "role_id":roleRow[0].getSimpleData().id,
	                        	"role_code":roleRow[0].getSimpleData().role_code,
	                        	"position_id":rows[i].getSimpleData().id,
	                        	"position_code":rows[i].getSimpleData().position_code
	                        });
				}
				u.confirmDialog({
					title: "确认",
					msg: "请确认是否删除选中的数据？",
					onOk: function () {
						$.ajax({
							url: window.cturl+'/security/extposition/deletePositionForRole',
							type: 'post',
							dataType: 'json',
							data: JSON.stringify(sIndices),
							contentType: "application/json ; charset=utf-8",
							success: function(data) {
								u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
								
								//刷新岗位信息
								var dat = viewModel.listData.getSelectedRows()[0];
								$.ajax({
									type: 'get',
									url:window.cturl+'/security/extposition/queryForRole/'+dat.getSimpleData().id,
									success: function(data) {
										viewModel.userRoleData.setSimpleData(data);
										viewModel.userRoleData.setAllRowsUnSelect();
									},
									error: function(XMLHttpRequest, textStatus, errorThrown) {
										errors.error(XMLHttpRequest);
									}
								})
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
				});
			},
			//关联用户取消
			cancelClick : function() {
				viewModel.gridStatus("read");
				if (viewModel.formStatus() == _CONST.STATUS_ADD) {
					viewModel.formStatus("init");
					viewModel.userRoleData
					.removeRows(viewModel.userRoleData
							.getSelectedRows());
				} else if (viewModel.formStatus() == _CONST.STATUS_EDIT) {
					viewModel.formStatus("init");
				}
				var row = viewModel.userRoleData.getSelectedRows();
				if (oper == 'adduser') {
					oper = 'int';
					viewModel.userRoleData.removeRows(viewModel.userRoleData.getSelectedRows());
				} 
			},
			// 关联用户保存
			saveuserClick: function() {
				viewModel.gridStatus("read");
				var sIndices = [];
				var rows = viewModel.userRoleData.getAllRows();
				for(var i=0;i<rows.length;i++){
					var id = rows[i].getSimpleData().id;
						if(null == id){
							sIndices.push(rows[i].getSimpleData());
						}
				}
				// 新增保存
				if (oper=="adduser"){ 
					$.ajax({
						type: 'POST',
						url: window.cturl+'/security/ext_user_role/createBatch',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(sIndices),
						success: function(data) {
								u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}else if(oper=="edituser"){ // 编辑保存
					$.ajax({
						type: 'POST',
						url: window.cturl+'/security/ext_user_role/update',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(dat.getSimpleData()),
						success: function(data) {
							u.messageDialog({msg:"修改成功",title:"提示", btnText:"OK"});
							var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
							md.dBack();
							getInitData();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			},
			/**
			 * 关联功能事件
			 * 
			 * 
			 * 
			 */
			// 关联功能新增
			addfuncClick: function() {
				viewModel.functionRoleData.getChangedRows();
				viewModel.gridStatus("edit");
				oper="adduser";
				var r = viewModel.functionRoleData.createEmptyRow();
				viewModel.functionRoleData.setRowFocus(r);
			},
			// 关联功能编辑
			editfuncClick: function() {
				viewModel.gridStatus("edit");
				oper="edituser";
				viewModel.functionRoleData.setEnable(true);// 设置可编辑
				viewModel.aa = viewModel.functionRoleData.getCurrentRow().rowId;
				if(viewModel.functionRoleData.getAllRows().length==1){
					viewModel.functionRoleData.setRowUnFocus(viewModel.functionRoleData.getCurrentRow());
				}

			},
			// 关联功能删除
			delfuncClick: function() {
				var row = viewModel.functionRoleData.getCurrentRow();
				if(row==null){
					u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
					return
				}
				u.confirmDialog({
					title: "确认",
					msg: "请确认是否删除选中的数据？",
					onOk: function () {
						$.ajax({
							url: window.cturl+'/security/extrolefunctiondefine/delete/'+row.getSimpleData().id,
							type: 'DELETE',
							dataType: 'json',
							contentType: "application/json ; charset=utf-8",
							success: function(data) {
								viewModel.functionRoleData.removeRows(viewModel.functionRoleData.getSelectedRows());
								u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
				});

			},
			//功能分配 选中行事件
			funcrowClick : function() {
				var status = viewModel.functionRoleData.getCurrentRow().status;
				//			viewModel.activityChildData.getCurrentRow().setValue(status,"upd");
				var id = viewModel.functionRoleData.getCurrentRow().rowId;//getSimpleData().id;
				if (viewModel.formStatus() == _CONST.STATUS_INIT && status == 'new') {
					viewModel.functionRoleData.setEnable(true);
				} else if (viewModel.gridStatus() =='edit'&& viewModel.aa == id) {
					viewModel.functionRoleData.setEnable(true);
				} else {
					viewModel.functionRoleData.setEnable(false);
				}
			},
			// 关联功能保存   //批量保存bill
			funcsaveClick: function() {
				viewModel.gridStatus("read");
//				var dats = [];
//				var dat = viewModel.functionData.getSelectedRows();
				var dat = viewModel.listData.getCurrentRow();
				var ids = "";
				var checkArr = $("#menuDiv input:checkbox")
				for(var j=0;j<checkArr.length;j++){
					if(checkArr[j].checked){
						ids = ids + "," + checkArr[j].value;
					}
				}
//				.each(function(obj){
//					if(obj.checked=="checked" || obj.checked==true){
//						ids = ids + "," + obj.value;
//					}
//				});
				if(ids.length<1){
					u.messageDialog({msg:"未分配任何功能",title:"提示", btnText:"OK"});
					return;
				}
				var json={
						"datas":ids,
						"roleid": dat.getSimpleData().id,
						"rolecode":dat.getSimpleData().role_code
				};
				// 新增保存
				$.ajax({
					type: 'POST',
					url: window.cturl+'/security/extrolefunctiondefine/createRoleFunc',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(json),
					success: function(data) {
						if(data.msg==undefined){
							u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
						}else if(data.msg.length>0){
							u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
//							viewModel.functionRoleData.removeRows(viewModel.functionRoleData.getSelectedRows());
							var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
							md.dBack();
//							document.getElementById("showPage").style.display ="none";
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			/**
			 * 关联按钮功能事件
			 * 
			 * 
			 * 
			 */
			// 关联按钮新增
			btnaddClick: function() {
				viewModel.functionRoleData.getChangedRows();
				viewModel.gridStatus("edit");
				oper="adduser";
				var r = viewModel.functionRoleData.createEmptyRow();
				viewModel.functionRoleData.setRowFocus(r);
			},
			// 关联按钮编辑
			btneditClick: function() {
				if (viewModel.functionRoleData.getSelectedRows().length < 1) {
					u.messageDialog({
						msg : "请选择一行操作数据",
						title : "提示",
						btnText : "OK"
					});
					return
				}
				viewModel.gridStatus("edit");
				oper="edituser";
				viewModel.functionRoleData.setEnable(true);// 设置可编辑
				viewModel.cc = viewModel.functionRoleData.getCurrentRow().rowId;
				if(viewModel.functionRoleData.getAllRows().length==1){
					viewModel.functionRoleData.setRowUnFocus(viewModel.functionRoleData.getCurrentRow());
				}

			},
			//关联按钮取消
			btncancelClick : function() {
				viewModel.gridStatus("read");
				if (viewModel.formStatus() == _CONST.STATUS_ADD) {
					viewModel.formStatus("init");
					viewModel.functionRoleData
					.removeRows(viewModel.functionRoleData
							.getSelectedRows());
				} else if (viewModel.formStatus() == _CONST.STATUS_EDIT) {
					viewModel.formStatus("init");
				}
				var row = viewModel.functionRoleData.getSelectedRows();
				if (oper == 'adduser') {
					oper = 'int';
					viewModel.functionRoleData
					.removeRows(viewModel.functionRoleData
							.getSelectedRows());
				} 
			},
			// 关联按钮删除
			btndelClick: function() {
				var row = viewModel.functionRoleData.getCurrentRow();
				if(row==null){
					u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
					return
				}
				u.confirmDialog({
					title: "确认",
					msg: "请确认是否删除选中的数据？",
					onOk: function () {
						$.ajax({
							url: window.cturl+'/security/extrolefunctiondefine/delete/'+row.getSimpleData().id,
							type: 'DELETE',
							dataType: 'json',
							contentType: "application/json ; charset=utf-8",
							success: function(data) {
								viewModel.functionRoleData.removeRows(viewModel.functionRoleData.getSelectedRows());
								u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
				});

			},
			// 按钮权限保存   
			btnsaveClick: function() {
//				if (viewModel.activityChildData.getSelectedRows().length < 1) {
//					u.messageDialog({
//						msg : "请选择一行操作数据",
//						title : "提示",
//						btnText : "OK"
//					});
//					return
//				}
				viewModel.gridStatus("read");
				var dats = [];
				var dat = viewModel.activityChildData.getSelectedRows();
				for(var i=0;i<dat.length;i++){
					dats.push(dat[i].getSimpleData());
				}
				var json={
						"datas":dats,
						"roleid":lsroleid,
						"rolecode":lsrolecode,
						"funcId":funcid
				};
				// 新增保存
				$.ajax({
					type: 'POST',
					url: window.cturl+'/security/extrolefunctiondefine/createBatchBtn',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(json),
					success: function(data) {
						if(data.msg==undefined){
							u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
						}else if(data.msg.length>0){
							u.messageDialog({msg:"分配成功！",title:"提示", btnText:"OK"});
//							viewModel.functionRoleData.removeRows(viewModel.functionRoleData.getSelectedRows());
//							var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
//							md.dBack();
//							getInitData();
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})

			},

			/**
			 * 角色按钮事件
			 * 
			 * 
			 * 
			 */
			// 返回按钮
			backClick: function() {
				var status = viewModel.formStatus();
				viewModel.formStatus(_CONST.STATUS_INIT);
				viewModel.gridStatus("init");
				if (status == _CONST.STATUS_ADD) {
					viewModel.listData.removeRow(viewModel.listData.rows().length - 1);
				} else if (status == _CONST.STATUS_EDIT) {
					var r = viewModel.listData.getSelectedRows()[0];
					r.setSimpleData(r.originData);
				}
				var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
				md.dBack();
//				var yid = viewModel.listData.getCurrentRow().rowId;
				getInitData();
//				viewModel.listData.setRowSelect(yid);
			},

			//双击进入卡片
			dbClick: function(row, e) {
				viewModel.formStatus(_CONST.STATUS_INIT);
				viewModel.gridStatus("read");
				lsroleid = row.rowObj.value.id;
				lsrolecode = row.rowObj.value.role_code;
				$.ajax({
					type: 'get',
					url:window.cturl+'/security/ext_user_role/query/'+lsroleid,
					success: function(data) {
						viewModel.listData.setRowSelect(row.rowIndex);
						var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
						md.dGo('role_addPage');
						document.getElementById("role_userdiv").style.display ="block";
						var r = viewModel.listData.getSelectedRows()[0];
						r.originData = r.getSimpleData();
						viewModel.userRoleData.setSimpleData(data);
						viewModel.userRoleData.setAllRowsUnSelect();
//						document.getElementById("addPage").style.display ="block";
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			//关联岗位
			assignuserClick: function(row, e) {
				viewModel.formStatus(_CONST.STATUS_INIT);
				viewModel.gridStatus("read");
				if (viewModel.listData.getSelectedRows().length != 1) {
					u.messageDialog({
						msg : "请选择一行操作数据",
						title : "提示",
						btnText : "OK"
					});
					return
				}
				var dat = viewModel.listData.getSelectedRows()[0];
				$.ajax({
					type: 'get',
					url:window.cturl+'/security/extposition/queryForRole/'+dat.getSimpleData().id,
					success: function(data) {
						var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
						md.dGo('role_addPage');
						document.getElementById("role_userdiv").style.display ="block";
						var r = viewModel.listData.getSelectedRows()[0];
						r.originData = r.getSimpleData();
						lsroleid = dat.getSimpleData().id;
						lsrolecode = dat.getSimpleData().role_code;
						
						viewModel.userRoleData.setSimpleData(data);
						viewModel.userRoleData.setAllRowsUnSelect();
//						document.getElementById("addPage").style.display ="block";
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			// 分配功能按钮bill
			assignclick: function(row, e) {
				if (viewModel.listData.getSelectedRows().length != 1) {
					u.messageDialog({
						msg : "请选择一行操作数据",
						title : "提示",
						btnText : "OK"
					});
					return
				}
				var dat = viewModel.listData.getCurrentRow();
//				document.getElementById("showPage").style.display ="block";
				$.ajax({
					type: 'get',
					url:window.cturl+"/security/extfunctiondefine/page?search_EQ_isactive=Y",//?search_EQ_permissionType='action'
					success: function(data) {
						var data1 =data['content'];
//						viewModel.functionData.setSimpleData(data1);
//						viewModel.functionData.setAllRowsUnSelect();
						
						var bydroletree = window.dtree = new dTree('bydroletree');
						
//						var funRows = viewModel.functionData.getAllRows();
						
						for(var i=0;i<data1.length;i++){
							var id = data1[i].id;
							var paretId = data1[i].parentId;
							var funcName = data1[i].funcName;
							bydroletree.add(id,paretId,funcName);
						}
						$("#menuDiv").html(bydroletree.toString());
						window.bydroletree=bydroletree;
						
						$.ajax({
							type: 'GET',
							url:window.cturl+'/security/extrolefunctiondefine/pagePermFunctionDefine/'+dat.getSimpleData().id,
							success: function(data2) {
								for(var ii=0;ii<data2.length;ii++) {
									var dataid = data2[ii].id;
									$("input:checkbox[value='"+dataid+"']").attr("checked",true);
								}
//								var sIndices = [];
//								var rows = viewModel.functionData.getAllRows();
//								for(var i=0;i<rows.length;i++){
//									var id = rows[i].getSimpleData().id;
//									for(var ii=0;ii<data2.length;ii++) {
//										var dataid = data2[ii].id;
//										if(dataid == id){
//											sIndices.push(rows[i]);
//										}
//									}
//								}
//								lsroleid = dat.getSimpleData().id;
//								lsrolecode = dat.getSimpleData().roleCode;
//								viewModel.functionData.setRowsSelect(sIndices);
//								viewModel.listData.setValue("id",lsroleid);
//								viewModel.listData.setValue("roleName",dat.getSimpleData().roleName);
//								viewModel.listData.setValue("roleCode",lsrolecode);
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						});
					}
				});
				var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
				md.dGo('role_showPage');
			},
			//分配功能全部展开
			openAllClick : function(){
				window.bydroletree.openAll();
			},
			//分配功能全部收缩
			closeAllClick: function(){
				window.bydroletree.closeAll();
			},
			// 分配按钮权限
			assignbtnclick: function(row, e) {
				if (viewModel.listData.getSelectedRows().length != 1) {
					u.messageDialog({
						msg : "请选择一行操作数据",
						title : "提示",
						btnText : "OK"
					});
					return
				}
				viewModel.activityChildData.clear();
				var dat = viewModel.listData.getCurrentRow();
				$.ajax({
					type: 'get',
					url:window.cturl+'/security/extrolefunctiondefine/pagePermFunctionDefine/'+dat.getSimpleData().id,
					success: function(data) {
						viewModel.functionData.setSimpleData(data);
						lsroleid = dat.getSimpleData().id;
						lsrolecode = dat.getSimpleData().role_code;
						viewModel.gridStatus("read");
						viewModel.listData.clear();
						viewModel.listData.setSimpleData(data);
//						document.getElementById("showbtnPage").style.display ="block";
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				});
				var md = document.querySelector('#role-mdlayout')['u.MDLayout'];
				md.dGo('role_showbtnPage');
			},
			afterAdd: function(element, index, data) {
				if (element.nodeType === 1) {
					u.compMgr.updateComp(element);
				}
			}

	};
	
	function enumerate(type){
		var retData = [];
		$.ajax({
			type: 'get',
			url: window.cturl+"/bd/enums/queryByVtype/"+type,
			contentType: "application/json ; charset=utf-8",
			async:false,
			success: function(data) {
				retData = data;
//				if(null != data){
//					for(var i=0;i<data.length;i++){
//						comValueNameMap[data[i].value]=data[i].name;
//					}
//				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		});
		return retData;
	}

	viewModel = u.extend({}, basicDatas, computes, events);

	var getInitData = function() {
		viewModel.searchData.createEmptyRow();
		viewModel.searchClick();
//		$.ajax({
//			type: 'get',
//			url:window.cturl+"/security/extroledefine/page?page.size="+pagesize+"",
//			success: function(data) {
//				var total = data.totalElements;//共多少条
//				viewModel.listData.setSimpleData(data.content);
//				viewModel.listData.setAllRowsUnSelect();
//				viewModel.listData.pageSize(pagesize);//每页显示多少条
//				var pages = CalculatePageCount(pagesize,total);//计算共多少页
//				viewModel.listData.totalPages(pages); //共多少页
//				viewModel.listData.totalRow(total); 
//				viewModel.searchData.createEmptyRow();  
//			},
//			error: function(XMLHttpRequest, textStatus, errorThrown) {
//				errors.error(XMLHttpRequest);
//			}
//		})
	};

	return {
		init: function(content,tabid) {
			content.innerHTML = html;
			window.vm = viewModel;

			app = u.createApp({
				el: '#'+tabid,
				model: viewModel
			});

			getInitData();
		}
	};
});