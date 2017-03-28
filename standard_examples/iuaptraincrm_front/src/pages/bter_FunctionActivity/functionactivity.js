define(
		[ 'error','text!./functionactivity.html', 'css!./productmgr.css', 'uuitree',
				'uuigrid','utilrow' ],
		function(errors,html) {
			'use strict';
			var app, viewModel, basicDatas, events, treeSetting, oper;

			treeSetting = {
					view:{
						showLine:false,
			            autoCancelSelected: true,
			            selectedMulti:false
			        },
			        check: {
			            chkboxType:{ "Y" : "ps", "N" : "ps" }
			        },
				callback : {
					onClick : function(e, id, node) {
						viewModel.setCurrentRow(node.id);// 设置选中行
						var isleaf = viewModel.treeData.getCurrentRow().getSimpleData().isleaf;
						if("Y" == isleaf){
							viewModel.gridStatus("init");
						}else if("N" == isleaf){
							viewModel.gridStatus("read");
						}
						
						$.ajax({
									type : 'get',
									url : window.cturl+'/security/extfuncactivity/page?search_EQ_funcID='
											+ node.id,
									dataType : 'json',
									contentType : "application/json ; charset=utf-8",
									success : function(data) {
										var data = data['content'];
										viewModel.activityChildData.setSimpleData(data);
										viewModel.activityChildData.setAllRowsUnSelect();
										viewModel.activityChildData.setRowUnFocus();
									},
									error: function(XMLHttpRequest, textStatus, errorThrown) {
										errors.error(XMLHttpRequest);
									}
								})
					}
				}
			}
			basicDatas = {
				treeSetting : treeSetting,
				treeData : new u.DataTable({
					meta : {
						id : {
							type : 'string'
						},
						parentId : {
							type : 'string'
						},
						isleaf : {
							type : 'string'
						},
						funcName : {
							type : 'string'
						}
					}
				}),
				activityData : new u.DataTable({
					meta : {
						funcCode : {
							type : 'string'
						},
						funcName : {
							type : 'string'
						},
						id : {
							type : 'string'
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
//						funcCode : {
//							type : 'string'
//						},
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
				comItems : [ {
					"value" : "Y",
					"name" : "启用"
				}, {
					"value" : "N",
					"name" : "停用"
				} ],
				gridStatus : ko.observable("read")
			};
			events = {
				setCurrentRow : function(id) {// 设置选中行
					var allrow = viewModel.activityData.getAllRows();
					if (allrow && id) {
						for ( var i in allrow) {
							var row = allrow[i];
							if (row instanceof u.Row)
								if (row.getValue('id') == id) {
									viewModel.activityData.setRowSelect(row);
								}
						}
					}

				},
				addClick : function() {
					var row = viewModel.activityData.getCurrentRow();
					if (row != null) {
						if (row.getSimpleData().id.length == undefined) {
							u.messageDialog({
								msg : "请先选择功能节点",
								title : "提示",
								btnText : "OK"
							});
							return

						}
					} else {
						u.messageDialog({
							msg : "请先选择功能节点",
							title : "提示",
							btnText : "OK"
						});
						return

					}
					viewModel.gridStatus("edit");
					oper = 'add';
					document.getElementById("funcaction_zhezhao").style.display ="block";//树遮照
					// 增加时，付默认值
					var funcCode = viewModel.activityData.getCurrentRow()
							.getSimpleData().funcCode;
					var pid = viewModel.activityData.getCurrentRow()
							.getSimpleData().id;
					var r = viewModel.activityChildData.createEmptyRow();
					viewModel.activityChildData.setRowFocus(r);
//					viewModel.activityChildData.setValue("funcCode", funcCode);// 用于显示
					viewModel.activityChildData.setValue("funcID", pid);// 用于保存
					viewModel.activityChildData.setValue("isactive", "Y");// 默认启用
				},
				editClick : function() {
					if (viewModel.activityChildData.getSelectedRows().length != 1) {
						u.messageDialog({
							msg : "请选择一行操作数据",
							title : "提示",
							btnText : "OK"
						});
						return

					}
					viewModel.aa = viewModel.activityChildData.getCurrentRow().rowId;// getSimpleData().id;
					viewModel.activityChildData.setEnable(true);// 设置可编辑
					document.getElementById("funcaction_zhezhao").sfuncaction_zhezhaosplay ="block";//树遮照
					viewModel.gridStatus("edit");
					oper = 'edit';
				},
				// 停用按钮
				tyClick : function() {
					var row = viewModel.activityChildData.getCurrentRow();
					if (row == null) {
						u.messageDialog({
							msg : "请选择要停用的数据",
							title : "提示",
							btnText : "OK"
						});
						return

					}
					var seal = row.getSimpleData().isactive;
					if(seal=='N'){
						u.messageDialog({
							msg : "启用状态不可用",
							title : "提示",
							btnText : "OK"
						});
					}else{
						viewModel.activityChildData.setValue("isactive", "N");
						$.ajax({
							type : 'POST',
							url : window.cturl+'/security/extfuncactivity/update',
							dataType : 'json',
							contentType : "application/json ; charset=utf-8",
							data : JSON.stringify(row.getSimpleData()),
							success : function(data) {
								u.messageDialog({
									msg : "停用成功",
									title : "提示",
									btnText : "OK"
								});
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
				},
				// 启用按钮
				qyClick : function() {
					var row = viewModel.activityChildData.getCurrentRow();
					if (row == null) {
						u.messageDialog({
							msg : "请选择要起用的数据",
							title : "提示",
							btnText : "OK"
						});
						return

					} 
					var seal = row.getSimpleData().isactive;
					if(seal=='Y'){
						u.messageDialog({
							msg : "启用状态不可用",
							title : "提示",
							btnText : "OK"
						});
					}else{
						viewModel.activityChildData.setValue("isactive", "Y");
						$.ajax({
							type : 'POST',
							url : window.cturl+'/security/extfuncactivity/update',
							dataType : 'json',
							contentType : "application/json ; charset=utf-8",
							data : JSON.stringify(row.getSimpleData()),
							success : function(data) {
								u.messageDialog({
									msg : "启用成功",
									title : "提示",
									btnText : "OK"
								});
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
				},
				delClick : function() {
					var row = viewModel.activityChildData.getCurrentRow();
					if (row == null) {
						u.messageDialog({
							msg : "请选择要删除的数据",
							title : "提示",
							btnText : "OK"
						});
						return

					}
					u.confirmDialog({
								title : "确认",
								msg : "请确认是否删除选中的按钮？",
								onOk : function() {
									$.ajax({
												url : window.cturl+'/security/extfuncactivity/delete/'
														+ row.getSimpleData().id,
												type : 'DELETE',
												dataType : 'json',
												contentType : "application/json ; charset=utf-8",
												success : function(data) {
													viewModel.activityChildData
															.removeRows(viewModel.activityChildData
																	.getSelectedRows());
													u.messageDialog({
														msg : "删除成功",
														title : "提示",
														btnText : "OK"
													});
												},
												error: function(XMLHttpRequest, textStatus, errorThrown) {
													errors.error(XMLHttpRequest);
												}
											})
								}
							});
				},
				cancelClick : function() {
					viewModel.gridStatus("read")
					if (oper == 'add') {
						oper = 'int';
						viewModel.activityChildData
								.removeRows(viewModel.activityChildData
										.getSelectedRows());
					} else if (oper == 'edit') {
						oper = 'int';
					}
					document.getElementById("funcaction_zhezhao").style.display ="none";//树遮照
				},
				saveClick : function(row, e) {
					var result = app.compsValidateMultiParam({element:$('#gridid')[0]});//  element:document.querySelector("#gridid")
					if (result.passed == false){
					 u.messageDialog({msg:result.notPassedArr[0].Msg,title:"操作提示",btnText:"确定"});
					}else{
					var dat = viewModel.activityChildData.getCurrentRow();
					// 新增保存
					if (oper == 'add') {
						viewModel.gridStatus("read")
						$.ajax({
									type : 'POST',
									url : window.cturl+'/security/extfuncactivity/create',
									dataType : 'json',
									contentType : "application/json ; charset=utf-8",
									data : JSON.stringify(dat.getSimpleData()),
									success : function(data) {
										if (data.msg == undefined) {
											oper = 'int';
											viewModel.activityChildData
													.setEnable(false);
											u.messageDialog({
												msg : "保存成功",
												title : "提示1",
												btnText : "OK"
											});
										} else if (data.msg.length > 0) {
											u.messageDialog({
												msg : data.msg,
												title : "提示",
												btnText : "OK"
											});
										}
									},
									error: function(XMLHttpRequest, textStatus, errorThrown) {
										errors.error(XMLHttpRequest);
									}
								})
					} else if (oper == 'edit') { // 编辑保存
						viewModel.gridStatus("read")
						$.ajax({
							type : 'POST',
							url : window.cturl+'/security/extfuncactivity/update',
							dataType : 'json',
							contentType : "application/json ; charset=utf-8",
							data : JSON.stringify(dat.getSimpleData()),
							success : function(data) {
								viewModel.activityChildData.setEnable(false);
								u.messageDialog({
									msg : "修改成功",
									title : "提示",
									btnText : "OK"
								});
								oper = 'int';
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
					document.getElementById("funcaction_zhezhao").style.display ="none";//树遮照
				}
				},
				//树搜索事件
				searchclick:function(){
					//alert($("#searchtxt").val());
				},
				//点击行事件
				rowClick : function() {
					var status = viewModel.activityChildData.getCurrentRow().status;
					var id = viewModel.activityChildData.getCurrentRow().rowId;
					if (oper == 'add' && status == 'new') {
						viewModel.activityChildData.setEnable(true);
					} else if (oper == 'edit' && viewModel.aa == id) {
						viewModel.activityChildData.setEnable(true);
					} else {
						viewModel.activityChildData.setEnable(false);
					}
				}
			}

			viewModel = u.extend(basicDatas, events)

			var getInitData = function() {
				viewModel.gridStatus("read");
				$.ajax({
					type : 'get',
					url : window.cturl+'/security/extfunctiondefine/page?page.size=1000',
					success : function(data) {
						var data = data['content'];
						viewModel.treeData.setSimpleData(data);
						viewModel.activityData.setSimpleData(data);
						viewModel.activityData.setAllRowsUnSelect();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			}

			return {
				init : function(content,tabid) {
					content.innerHTML = html;
					window.vm = viewModel;

					app = u.createApp({
						el : '#'+tabid,
						model : viewModel
					})
					getInitData();
					$("#searchtxt").click(function(){
						$(this).val("");
					})
					$("#searchtxt").blur(function(){
						if($(this).val() == "" || $(this).val() == "请输入关键词")
						$(this).val("请输入关键词");
					})

				}
			}

		});