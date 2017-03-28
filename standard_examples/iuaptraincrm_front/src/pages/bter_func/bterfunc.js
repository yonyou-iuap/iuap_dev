define([ 'error', 'text!./bterfunc.html', 'css!./productmgr.css','uuitree','utilrow' ],
		function(errors, html) {
			'use strict';
			var app, viewModel, basicDatas, events, treeSetting, computes, oper;
			treeSetting = {
				"check" : {
					"enable" : false
				},
				view : {
					showLine : false,
					selectedMulti : false
				},
				callback : {
					onClick : function(e, id, node) {
// $("#imgcodeLog").attr("class","");
						var rowId = node.id;
						viewModel.setCurrentRow(rowId);
						var row = viewModel.treeData.getCurrentRow();
						viewModel.listData.removeAllRows();
						var r = viewModel.listData.createEmptyRow();
						viewModel.listData.setValue("id",
								row.getSimpleData().id);
						viewModel.listData.setValue("funcCode", row
								.getSimpleData().funcCode);// 用于保存
						viewModel.listData.setValue("funcName", row
								.getSimpleData().funcName);
						viewModel.listData.setValue("parentId", row
								.getSimpleData().parentId);
						viewModel.listData.setValue("funcUrl", row
								.getSimpleData().funcUrl);
						viewModel.listData.setValue("menuUrl", row
								.getSimpleData().menuUrl);
						viewModel.listData.setValue("iscontrol", row
								.getSimpleData().iscontrol);
						viewModel.listData.setValue("funcType", row
								.getSimpleData().funcType);
						viewModel.listData.setValue("isactive", row
								.getSimpleData().isactive);
						viewModel.listData.setValue("isleaf", row
								.getSimpleData().isleaf);
						viewModel.listData.setValue("enableAction", row
								.getSimpleData().enableAction);
						viewModel.listData.setValue("createDate", row
								.getSimpleData().createDate);
// viewModel.listData.setValue("digest", row
// .getSimpleData().digest);
// viewModel.listData.setValue("imgcode", row
// .getSimpleData().imgcode);
						viewModel.listData.setRowFocus(r)
					}
				}
			}

			basicDatas = {
				treeSetting : treeSetting,
				treeData : new u.DataTable({// 树 表
					meta : {
						id : {
							type : 'string'
						},
						funcCode : {
							type : 'string'
						},
						funcName : {
							type : 'string'
						},
						funcUrl : {
							type : 'string'
						},
						menuUrl : {
							type : 'string'
						},
						iscontrol : {
							type : 'string'
						},
						funcType : {
							type : 'string'
						},
						isactive : {
							type : 'string'
						},
						isleaf : {
							type : 'string'
						},
						parentId : {
							type : 'string'
						},
						enableAction : {
							type : 'string'
						},
// digest : {
// type : 'string'
// },
						createDate : {
							type : 'string'
						},
// imgcode: {
// type : 'string'
// }
					}
				}),
				listData : new u.DataTable({// 实体表
					meta : {
						id : {
							type : 'string'
						},
						funcCode : {
							type : 'string',
							required : true
						},
						funcName : {
							type : 'string',
							required : true
						},
						funcUrl : {
							type : 'string'
						},
						menuUrl : {
							type : 'string'
						},
						iscontrol : {
							type : 'string'
						},
						funcType : {
							type : 'string'
						},
						isactive : {
							type : 'string'
						},
						isleaf : {
							type : 'string'
						},
						parentId : {
							type : 'string'
						},
						enableAction : {
							type : 'string'
						},
						createDate : {
							type : 'string'
						},
// imgcode: {
// type : 'string'
// }
					}
				}),
				enable : [ {
					"value" : 'Y',
					"name" : '启用'
				}, {
					"value" : 'N',
					"name" : '停用'
				} ],
				comboLog : [ 
				    {"value" : 'icon-settings',"name" : '齿轮'}, 
				    {"value" : 'icon-bulb',"name" : '灯泡'} ,
				    {"value" : 'icon-briefcase',"name" : '工具包'} ,
				    {"value" : 'icon-wallet',"name" : '文件篮'} ,
				    {"value" : 'icon-bar-chart',"name" : '柱状图'} ,
				    {"value" : 'icon-pointer',"name" : '定位'} ,
				    {"value" : 'icon-diamond',"name" : '砖石'} ,
				    {"value" : 'icon-puzzle',"name" : '主键'} ,
				    {"value" : 'icon-layers',"name" : '多层'} ,
				    {"value" : 'icon-feed',"name" : '广播'} ,
				    {"value" : 'icon-paper-plane',"name" : '飞机'} ,
				    {"value" : ' icon-wrench',"name" : '扳手'} ,
				    {"value" : 'icon-basket',"name" : '购物车'} ,
				    {"value" : 'icon-user',"name" : '用户'} ,
				    {"value" : 'icon-social-dribbble',"name" : '足球'} ,
				    {"value" : 'icon-folder',"name" : '文件夹'} ,
				],
				gridStatus : ko.observable("read")
			};
			computes = {
				refFormInputValue : function(field) {
					return ko.pureComputed({
						read : function() {
							if (viewModel.gridStatus() == 'add') {
								var fr = this.getFocusRow()
								return fr != null ? fr.ref(field) : '';
							} else if (viewModel.gridStatus() == 'edit') {
								var srs = this.getSelectedRows()
								return srs.length > 0 ? srs[0].ref(field) : ''
							}
						},
						owner : viewModel.listData
					})
				}
			};
			// 按钮事件
			events = {
				// 新增
				addClick : function() {
					var row = viewModel.listData.getCurrentRow();
					if (row == null) {
						row = viewModel.listData.createEmptyRow();
					}
					var isleaf = viewModel.listData.getCurrentRow().getSimpleData().isleaf;
					if (isleaf == "N") {
						u.messageDialog({
							msg : "该节点不是虚节点，无法增加下级节点",
							title : "提示",
							btnText : "OK"
						});
						return;
					}
					viewModel.gridStatus("edit");
					oper = 'add';
					var id = viewModel.listData.getCurrentRow().getSimpleData().id;
					if (id == null || "" == id) {
						id = 0;
					}
					document.getElementById("func_zhezhao").style.display = "block";// 树遮照
					$(app.getComp('iscontrol').comp.element).find(
							'.u-checkbox-input').attr('disabled', false);// 业务活动权限
					$(app.getComp('isleaf').comp.element).find(
							'.u-checkbox-input').attr('disabled', false);// 功能分类
					$(app.getComp('funcType').comp.element).find(
							'.u-checkbox-input').attr('disabled', false);// 功能性质
					$(app.getComp('isactive').comp.element).find(
							'.u-checkbox-input').attr('disabled', false);// 是否启用功能
					viewModel.listData.removeAllRows();
					var r = viewModel.listData.createEmptyRow();
					viewModel.listData.setValue("parentId", id);// 用于显示
					viewModel.listData.setValue("enableAction", "Y");
					viewModel.listData.setRowFocus(r);
					// 清空图标
// $("#imgcodeLog").attr("class","");
				},
				// 编辑
				editClick : function() {
					debugger;
					var row = viewModel.listData.getCurrentRow();
					if (row != null) {
						viewModel.gridStatus("edit");
						oper = 'edit';
						var row = viewModel.listData.getCurrentRow();
						document.getElementById("func_zhezhao").style.display = "block";// 树遮照
						$(app.getComp('iscontrol').comp.element).find(
							'.u-checkbox-input').attr('disabled', false);// 业务活动权限
						$(app.getComp('isleaf').comp.element).find(
								'.u-checkbox-input').attr('disabled', false);// 功能分类
						$(app.getComp('funcType').comp.element).find(
								'.u-checkbox-input').attr('disabled', false);// 功能性质
						$(app.getComp('isactive').comp.element).find(
								'.u-checkbox-input').attr('disabled', false);// 是否启用功能
					} else {
						u.messageDialog({
							msg : "请选择要修改的功能节点",
							title : "提示",
							btnText : "OK"
						});
						return
					}
				},
				// 删除
				delClick : function() {
					var row = viewModel.listData.getSelectedRows();
					if (row.length != 1) {
						u.messageDialog({
							msg : "请选择要删除的数据",
							title : "提示",
							btnText : "OK"
						});
						return
					}
					u.confirmDialog({
								title : "确认",
								msg : "请确认是否删除选中的功能节点？",
								onOk : function() {
									$.ajax({
												url : window.cturl+'/security/extfunctiondefine/delete/'+ row[0].getSimpleData().id,
												type : 'DELETE',
												dataType : 'json',
												contentType : "application/json ; charset=utf-8",
												success : function(data) {
													if (data.flag==1) {
														viewModel.listData.removeRows(viewModel.listData.getSelectedRows());
														u.messageDialog({
															msg : "删除成功",
															title : "提示",
															btnText : "OK"
														});
														getInitData();
													} else {
														u.messageDialog({
															msg : data.msg,
															title : "提示",
															btnText : "OK"
														});
													}
												},
												error : function(XMLHttpRequest,textStatus, errorThrown) {
													errors.error(XMLHttpRequest);
												}
											})
								}
							});
				},

				// 启用按钮
				qyClick : function() {
					debugger;
					var row = viewModel.listData.getCurrentRow();
					if (row == null) {
						u.messageDialog({
							msg : "请选择要起用的数据",
							title : "提示",
							btnText : "OK"
						});
						return

					} else {
						var enableAction = viewModel.listData.getCurrentRow().getSimpleData().enableAction;
						if (enableAction == "Y") {
							u.messageDialog({
								msg : "按钮权限已启动，不需要重新启动！",
								title : "提示",
								btnText : "OK"
							});
							return

						}
						row.setValue("enableAction", "Y");
					}
					$.ajax({
						type : 'POST',
						url : window.cturl+'/security/extfunctiondefine/create',
						dataType : 'json',
						contentType : "application/json ; charset=utf-8",
						data : JSON.stringify(row.getSimpleData()),
						success : function(data) {
							viewModel.listData.setRowUnFocus(row);
							u.messageDialog({
								msg : "启用成功",
								title : "提示",
								btnText : "OK"
							});
							getInitData();
						}
					})
				},
				// 停用按钮
				tyClick : function() {
					var row = viewModel.listData.getCurrentRow();
					if (row == null) {
						u.messageDialog({
							msg : "请选择要停用的数据",
							title : "提示",
							btnText : "OK"
						});
						return

					} else {
						var enableAction = viewModel.listData.getCurrentRow()
								.getSimpleData().enableAction;
						if (enableAction == "N") {
							u.messageDialog({
								msg : "按钮权限已停用，不需要重新停用！",
								title : "提示",
								btnText : "OK"
							});
							return
						}
						row.setValue("enableAction", "N");
					}
					$.ajax({
						type : 'POST',
						url : window.cturl+'/security/extfunctiondefine/create',
						dataType : 'json',
						contentType : "application/json ; charset=utf-8",
						data : JSON.stringify(row.getSimpleData()),
						success : function(data) {
							viewModel.listData.setRowUnFocus(row);
							u.messageDialog({
								msg : "停用成功",
								title : "提示",
								btnText : "OK"
							});
							getInitData();
						}
					})
				},
				// 图标设置确定
// okiconClick : function() {
// var parm = [], a, b, html;
// a = $('#iconset-cont .iconbox .img.active').attr('src')
// .replace(/[^0-9]/ig, '');
// b = $.trim($('#iconset-cont .iconbox .img.active').attr(
// 'style').replace('background-color:', '').replace(
// ';', ''));
// parm.push(a, b);
// viewModel.listData.setValue("imgcode", a + "," + b);
// $('#djtb').attr({
// 'src' : 'static/images/account_icon_' + a + '.png',
// 'style' : 'background-color:' + b + ';'
// });
// },
				// 取消
				cancelClick : function() {
					viewModel.gridStatus("read")
					var row = viewModel.listData.getSelectedRows();
					$(app.getComp('iscontrol').comp.element).find(
							'.u-checkbox-input').attr('disabled', true);// 业务活动权限
					$(app.getComp('isleaf').comp.element).find(
							'.u-checkbox-input').attr('disabled', true);// 功能分类
					$(app.getComp('funcType').comp.element).find(
							'.u-checkbox-input').attr('disabled', true);// 功能性质
					$(app.getComp('isactive').comp.element).find(
							'.u-checkbox-input').attr('disabled', true);// 是否启用功能
					document.getElementById("func_zhezhao").style.display = "none";// 树遮照
				},
				// 保存
				saveClick : function(row, e) {
// var result = app.compsValidateMultiParam({
// element : document.querySelector("#input1"),
// showMsg : true
// });
// if (result.passed == false) {
					var vlidate = getvalidate(app, "#corpformref");
					if(vlidate==false){
// u.messageDialog({
// msg : "请检查必输项!",
// title : "操作提示",
// btnText : "确定"
// });
					} else {
						var dat = viewModel.listData.getCurrentRow();
						var iscontrol = viewModel.listData.getCurrentRow()
								.getSimpleData().iscontrol;
						var funcType = viewModel.listData.getCurrentRow()
								.getSimpleData().funcType;
						var isleaf = viewModel.listData.getCurrentRow()
								.getSimpleData().isleaf;
						var isactive = viewModel.listData.getCurrentRow()
								.getSimpleData().isactive;
						if (iscontrol == null || "" == iscontrol) {
							viewModel.listData.setValue("iscontrol", "N");
						}
						if (funcType == null || "" == funcType) {
							viewModel.listData.setValue("funcType", "N");
						}
						if (isleaf == null || "" == isleaf) {
							viewModel.listData.setValue("isleaf", "N");
						}
						if (isactive == null || "" == isactive) {
							viewModel.listData.setValue("isactive", "N");
						}
						// 新增保存
						if (oper === 'add') {
							$.ajax({
										type : 'POST',
										url : window.cturl+'/security/extfunctiondefine/create',
										dataType : 'json',
										contentType : "application/json ; charset=utf-8",
										data : JSON.stringify(dat.getSimpleData()),
										success : function(data) {
											if (data.flag==1) {
												viewModel.gridStatus("read")
												viewModel.listData.setRowUnFocus(row);
												u.messageDialog({
													msg : "保存成功",
													title : "提示",
													btnText : "OK"
												});
												document.getElementById("func_zhezhao").style.display = "none";// 树遮照
												getInitData();
											} else {
												u.messageDialog({
													msg : data.msg,
													title : "提示",
													btnText : "OK"
												});
											}
										},
										error : function(XMLHttpRequest,
												textStatus, errorThrown) {
											errors.error(XMLHttpRequest);
										}
									})
						} else if (oper === 'edit') { // 编辑保存
							viewModel.gridStatus("read")
							debugger;
							if (iscontrol != "Y") {
								viewModel.listData.setValue("iscontrol", "N");
							}
							if (funcType != "Y") {
								viewModel.listData.setValue("funcType", "N");
							}
							if (isleaf != "Y") {
								viewModel.listData.setValue("isleaf", "N");
							}
							if (isactive != "Y") {
								viewModel.listData.setValue("isactive", "N");
							}
							$.ajax({
										type : 'POST',
										url : window.cturl+'/security/extfunctiondefine/create',
										dataType : 'json',
										contentType : "application/json ; charset=utf-8",
										data : JSON.stringify(dat.getSimpleData()),
										success : function(data) {
											if (data.flag==1) {
												viewModel.listData.setRowUnFocus(row);
												u.messageDialog({
													msg : "修改成功",
													title : "提示",
													btnText : "OK"
												});
												document.getElementById("func_zhezhao").style.display = "none";// 树遮照
												getInitData();
											} else {
												u.messageDialog({
													msg : data.msg,
													title : "提示",
													btnText : "OK"
												});
											}
										},
										error : function(XMLHttpRequest,textStatus, errorThrown) {
											errors.error(XMLHttpRequest);
										}
									})
						}
					}
				},
				setCurrentRow : function(id) {// 设置选中行
					var allrow = viewModel.treeData.getAllRows();
					if (allrow && id) {
						for ( var i in allrow) {
							var row = allrow[i];
							if (row instanceof u.Row)
								if (row.getValue('id') == id) {
									viewModel.treeData.setRowSelect(row);
								}
						}
					}

				}
			}

			viewModel = u.extend(basicDatas, computes, events)
			
			// 组织调整后，需要清空部门信息
// viewModel.listData.on('imgcode.valuechange',function(obj){
// var row = viewModel.listData.getCurrentRow();
// if(row!=null){
// var imgcodeVal = row.getValue("imgcode");
// $("#imgcodeLog").attr("class",imgcodeVal);
// }
// });

			// 初始化树
			var getInitData = function() {
				viewModel.gridStatus("read")
				$.ajax({
					type : 'get',
					url : window.cturl+'/security/extfunctiondefine/list',
					success : function(data) {
						// var data = data['content'];
						viewModel.treeData.setSimpleData(data)
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
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

					$(app.getComp('iscontrol').comp.element).find(
							'.u-checkbox-input').attr('disabled', true);// 业务活动权限
					$(app.getComp('isleaf').comp.element).find(
							'.u-checkbox-input').attr('disabled', true);// 功能分类
					$(app.getComp('funcType').comp.element).find(
							'.u-checkbox-input').attr('disabled', true);// 功能性质
					$(app.getComp('isactive').comp.element).find(
							'.u-checkbox-input').attr('disabled', true);// 是否启用功能
					
					getInitData();

					(function() {// 图标定制
						$('#djtb').on('click', function() {
							$('#iconset-cont').show();
						});
						$('#iconset-cont .closebtn').on('click', function() {
							$('#iconset-cont').hide();
						});
						$('#iconset-cont .iconbox .img').on('click',function() {
											var _color = $(
													'#iconset-cont .colorbox').hasClass('active') ? $(
													'#iconset-cont .colorbox.active').css('background-color'): '#ccc';
											$('#iconset-cont .iconbox .img').removeClass('active').css('background-color','#ccc');
											$(this).addClass('active').css('background-color', _color);
										});
						$('#iconset-cont .colorbox').on('click',function() {
											$('#iconset-cont .colorbox').removeClass('active');
											$(this).addClass('active');
											$('#iconset-cont .iconbox .img.active')
													&& $('#iconset-cont .iconbox .img.active')
															.css('background-color',
																	$(this).css('background-color'));
										});
					})();

				}
			}

		});