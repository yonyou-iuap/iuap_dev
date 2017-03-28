define(
		[ 'iReferComp', 'refComp', 'error', 'text!./btercorps.html',
				'css!./productmgr.css', 'uuitree', 'utilrow', 'require' ],
		function(iReferComp, refComp, errors, html) {
			'use strict';
			if (window.addEventListener) { // all browsers except IE before
				// version 9
				window.removeEventListener('message', refValue, false);
				window.addEventListener('message', refValue, false);
			} else if (window.attachEvent) { // IE before version 9
				window.detachEvent("onmessage", refValue);
				window.attachEvent("onmessage", refValue);
			} else {
				alert("浏览器不支持");
			}
			var refid;
			var dom;

			function refValue(event) {
				var para = JSON.parse(event.data);

				// 参照code
				var refCode = para.refCode;
				var selected = para.data;
				var pk = '';

				if (selected && selected.length > 0) {
					pk = $.map(selected, function(val, index) {
						return val.refpk
					}).join(',');
					var name = $.map(selected, function(val, index) {
						return val.refname
					}).join(',');
					var code = $.map(selected, function(val, index) {
						return val.refcode
					}).join(',');
				}

				if (window.addEventListener) { // all browsers except IE before
					// version 9
					window.removeEventListener('message', refValue, false);
				} else if (window.attachEvent) { // IE before version 9
					window.detachEvent("onmessage", refValue);
				}

				$('#person').each(function(i, val) {
					var $that = $(this);
					dom = $that;
					var options = {
						refCode : "user",
						selectedVals : "pk",
						isMultiSelectedEnabled : false
					};
					refComp.initRefComp($that, options);
					refid = '#refContainer' + $that.attr('id');
				});
				$('#corps').each(function(i, val) {
					var $that = $(this);
					dom = $that;
					var options = {
						refCode : "corp",
						selectedVals : "pk",
						isMultiSelectedEnabled : false
					};
					refComp.initRefComp($that, options);
					refid = '#refContainer' + $that.attr('id');
				});
				//省份
				$('#def2').each(function(i, val) {
					var $that = $(this);
					dom = $that;
					var options = {
						refCode : "province",
						selectedVals : "pk",
						isMultiSelectedEnabled : false
					};
					refComp.initRefComp($that, options);
					refid = '#refContainer' + $that.attr('id');
				});
				//城市
				$('#def3').each(function(i, val) {
					var $that = $(this);
					dom = $that;
					var options = {
						refCode : "city",
						selectedVals : "pk",
						isMultiSelectedEnabled : false
					};
					refComp.initRefComp($that, options);
					refid = '#refContainer' + $that.attr('id');
				});
				
				var $input = dom.find('input');
				$input.val(name);

			}

			var msg = {};
			msg.data = [];

			var str = JSON.stringify(msg);
			top.postMessage(str, "*");
			var app, viewModel, basicDatas, events, treeSetting, computes, oper;
			treeSetting = {
				view : {
					showLine : false,
					autoCancelSelected : false,
					selectedMulti : true
				},
				check : {
					chkboxType : {
						"Y" : "ps",
						"N" : "ps"
					}
				},
				callback : {
					onClick : function(e, id, node) {// 点击树事件
						$.ajax({
									type : 'get',
									url : window.cturl+'/bd/corp/queryPageByCorp?pk_corp='+ node.id,
									dataType : 'json',
									contentType : "application/json ; charset=utf-8",
									success : function(data) {
										var data = data['content'];
										iReferComp.setFormData('#corpformref',data);
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										errors.error(XMLHttpRequest);
									}
								})
					},
					// 初始化，根据不同状态，显示不同颜色
					onNodeCreated : function(obj, obj1, obj2) {
						var datas = viewModel.treeData.getData();
						if (!datas){
							return;
						}
						for ( var i in datas) {
							var data = viewModel.treeData.getData()[i].data;
							if (obj2.id == data['pk_corp'].value) {
								if (data['isseal'].value == '3') {
									$('#' + obj2.tId + '_span').css('color','#333333');
								} else if (data['isseal'].value == '2') {
									$('#' + obj2.tId + '_span').css('color','grey');
								}
								break;
							}
						}

					}
				}

			}
			basicDatas = {
				treeSetting : treeSetting,
				treeData : new u.DataTable({// 树 表
					meta : {
						pk_corp : {
							type : 'string'
						},
						unitname : {
							type : 'string'
						},
						isseal : {
							type : 'string'
						},
						fathercorp : {
							type : 'string'
						}
					}
				}),
				combodata : [ { // 组织类型
					"name" : '公司',
					"value" : '10101001'}
				, {
					"name" : '事业部',
					"value" : '10101002'} 
				, {
					"name" : '直营店',
					"value" : '10101003'} 
				, {
					"name" : '维修站',
					"value" : '10101004'} 
				],
				issealdata : [ 
//				               { // 组织状态
//					"value" : '3',
//					"name" : '初始态'
//				}, 
				{
					"value" : '1',
					"name" : '启用'
				}, {
					"value" : '2',
					"name" : '停用'
				} ],
				entity1 : new u.DataTable({
					meta : {
						'corptype' : {},
						'name' : {}
					}
				}),
				listData : new u.DataTable({// 实体表
					meta : {
						unitcode : {type : 'string',required : true},//组织代码
						unitname : {type : 'string',required : true},//组织名称
						fathercorp : {type : 'string'},//上级组织
						corptype : {type : 'string'},//组织类型
						pk_user : {type : 'string'},//组织负责人
						isseal : {type : 'int'},//组织状态
						creationtime : {type : 'Date'},
						creator : {type : 'string'},
						modifiedtime : {type : 'Date'},
						modifier : {type : 'string'},
						memo : {type : 'string'},//备注
						def1 : {type : 'string'},//
						def2 : {type : 'string'},//省份
						def3 : {type : 'string'},//城市
						pk_corp : {type : 'string'},//组织主键
						ts : {type : 'string'}//时间戳
					}
				}),
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
					// 操作前，徐先选择树
					if (viewModel.listData.getSelectedRows().length != 1) {
						u.messageDialog({
							msg : "请先选择上级组织",
							title : "提示",
							btnText : "OK"
						});
						return
					}
					document.getElementById("corp_zhezhao").style.display = "block";// 树遮照
					viewModel.gridStatus("edit");
					oper = 'add';
					var unitname = viewModel.listData.getCurrentRow().getSimpleData().unitname;
					var pk_corp = viewModel.listData.getCurrentRow().getSimpleData().pk_corp;
					viewModel.listData.removeAllRows();
					var r = viewModel.listData.createEmptyRow();
					viewModel.listData.setValue("fathercorp", pk_corp);// 父节点用于保存
					viewModel.listData.setValue("isseal", '1');// 组织状态：默认启用
//					viewModel.listData.setValue("corptype", '2');// 用于显示
					viewModel.listData.setValue("pk_user", null);
					viewModel.listData.setRowFocus(r);
					iReferComp.setFormData('#corpformref', new Array(
									viewModel.listData.getCurrentRow().getSimpleData()));
				},
				// 编辑
				editClick : function() {
					if (viewModel.listData.getSelectedRows().length != 1) {
						u.messageDialog({
							msg : "请选择一行操作数据",
							title : "提示",
							btnText : "OK"
						});
						return
					}
					document.getElementById("corp_zhezhao").style.display = "block";// 树遮照
					viewModel.gridStatus("edit");
					oper = 'edit';
					var row = viewModel.listData.getCurrentRow();
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
								msg : "请确认是否删除选中的组织？",
								onOk : function() {
									$.ajax({
												url : window.cturl+'/bd/corp/delete?pk_corp='+ row[0].getSimpleData().pk_corp,
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
														viewModel.listData.clear();
														var rowId = $('#corp_ztree1')[0]['u-meta'].getRowIdByIdValue(row[0].getSimpleData().pk_corp);
														viewModel.treeData.removeRowByRowId(rowId);
													} else {
														u.messageDialog({
															msg : data.msg,
															title : "提示",
															btnText : "OK"
														});
													}
												},
												error : function(
														XMLHttpRequest,
														textStatus, errorThrown) {
													errors.error(XMLHttpRequest);
												}
											})
								}
							});
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
					}
					var seal = row.getSimpleData().isseal;
					if (seal == '2') {
						u.messageDialog({
							msg : "停用状态不可用",
							title : "提示",
							btnText : "OK"
						});
					} else {
						$.ajax({
							url : window.cturl+'/bd/corp/stop?pk_corp='+ row.getSimpleData().pk_corp,
							type : 'POST',
							dataType : 'json',
							contentType : "application/json ; charset=utf-8",
							success : function(data) {
								u.messageDialog({
									msg : "停用成功",
									title : "提示",
									btnText : "OK"
								});
								viewModel.listData.setValue("isseal", '2');
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
				},
				// 启用按钮
				qyClick : function() {
					var row = viewModel.listData.getCurrentRow();
					if (row == null) {
						u.messageDialog({
							msg : "请选择要起用的数据",
							title : "提示",
							btnText : "OK"
						});
						return
					}
					var seal = row.getSimpleData().isseal;
					if (seal == '1') {
						u.messageDialog({
							msg : "启用状态不可用",
							title : "提示",
							btnText : "OK"
						});
					} else {
						viewModel.gridStatus("qystatus");
						document.getElementById("corp_zhezhao").style.display = "block";// 树遮照
					}
				},
				qystaClick : function() {
					var row = viewModel.listData.getCurrentRow();
//					if (row.getSimpleData().def1 == null) {
//						u.messageDialog({
//							msg : "财务期间不能为空",
//							title : "提示",
//							btnText : "OK"
//						});
//						return
//					}
					$.ajax({
								url : window.cturl+"/bd/corp/start?pk_corp="+ row.getSimpleData().pk_corp
										+ "&def1=" + row.getSimpleData().def1+ "",
								type : 'POST',
								dataType : 'json',
								contentType : "application/json ; charset=utf-8",
								success : function(data) {
									u.messageDialog({
										msg : "启用成功",
										title : "提示",
										btnText : "OK"
									});
									viewModel.listData.setValue("isseal", '1');
									viewModel.gridStatus("read");
//									document.getElementById("corp_zhezhao").style.display = "none";// 树遮照
								},
								error : function(XMLHttpRequest, textStatus,errorThrown) {
									errors.error(XMLHttpRequest);
								}
							})
				},
				// 树搜索事件
				searchclick : function() {
					alert($("#searchtxt").val());
				},
				// 负责人编辑前事件
				UserRefClick : function() {
					var row = viewModel.listData.getSelectedRows();
					if (row.length == 1) {
						var pkCorp = row[0].getSimpleData().pk_corp;
						if (pkCorp != null && pkCorp != "") {
							iReferComp.setFilterPks("pkCorp", pkCorp, '#person');
						} else {
							iReferComp.setFilterPks("pkCorp", "1", '#person');
						}
					}
				},
				// 取消
				cancelClick : function() {
					viewModel.gridStatus("read")
					if (oper == 'add') {
						oper = 'int';
						viewModel.listData.removeRows(viewModel.listData.getSelectedRows());
					} else if (oper == 'edit') {
						oper = 'int';
					}
					document.getElementById("corp_zhezhao").style.display = "none";// 树遮照
				},
				// 保存
				saveClick : function(row, e) {
					var vlidate = getvalidate(app, "#input1");
					if (vlidate == true) {
						var dat = viewModel.listData.getCurrentRow();
						// 新增保存
						if (oper == 'add') {
							$.ajax({
										type : 'POST',
										url : window.cturl+'/bd/corp/save',
										dataType : 'json',
										contentType : "application/json ; charset=utf-8",
										data : JSON.stringify(dat.getSimpleData()),
										success : function(data) {
											if (data.flag==1) {
												viewModel.gridStatus("read")
												u.messageDialog({
													msg : "保存成功",
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
										error : function(XMLHttpRequest,
												textStatus, errorThrown) {
											errors.error(XMLHttpRequest);
										}
									})
						} else if (oper == 'edit') { // 编辑保存
							var fatherOrg = dat.getSimpleData().fathercorp;
							var pkOrg = dat.getSimpleData().pk_corp;
							if(null != fatherOrg && fatherOrg==pkOrg){
								u.messageDialog({
									msg : "不能选择本组织作为自己的上级组织", 
									title : "提示",
									btnText : "OK"
								});
							}else{
								viewModel.gridStatus("read")
								$.ajax({
									type : 'POST',
									url : window.cturl+'/bd/corp/update',
									dataType : 'json',
									contentType : "application/json ; charset=utf-8",
									data : JSON.stringify(dat.getSimpleData()),
									success : function(data) {
										viewModel.listData.setRowUnFocus(row);
										u.messageDialog({
											msg : "修改成功",
											title : "提示",
											btnText : "OK"
										});
										getInitData();
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										errors.error(XMLHttpRequest);
									}
								})
							}
						}
						document.getElementById("corp_zhezhao").style.display = "none";// 树遮照
					}
				},
				//城市编辑前事件
				filerCity: function() {
					var row = viewModel.listData.getCurrentRow();
					if(row!=null){
						var pkprovince = row.getValue("def2");
						if(null != pkprovince && pkprovince.length>0){
							iReferComp.setFilterPks("province", pkprovince, '#def3');
						}
					}
				}
			}
			
			

			viewModel = u.extend(basicDatas, computes, events)

			//给省份绑定值改变时间，省份调整后清空城市
			viewModel.listData.on('def2.valuechange',function(obj){
				var row = viewModel.listData.getCurrentRow();
				if(row!=null){
					row.setValue("def3","");
				}
			});
			
			// 初始化树
			var getInitData = function() {
				viewModel.gridStatus("read")
				$.ajax({
					type : 'get',
					url : window.cturl+'/bd/corp/queryTree',
					success : function(data) {
						var data = data.data['list'];
						//$('#ztree1').addClass('red');
						viewModel.treeData.setSimpleData(data)
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				});
				var contentMinHeight = $('#content').css('min-height');
			    contentMinHeight = contentMinHeight.substr(0,contentMinHeight.length-2);
			    var tabsMinHeight = $('#content .nav-tabs').css('height');
			    tabsMinHeight = tabsMinHeight.substr(0,tabsMinHeight.length-2);
			    var tabContentHeight = (contentMinHeight-tabsMinHeight)+"px";
				$('#btercorp_list .u-mdlayout-page-section').css('min-height',tabContentHeight);
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

					$("#searchtxt").click(function() {
						$(this).val("");
					})
					$("#searchtxt").blur(function() {
						if ($(this).val() == "" || $(this).val() == "请输入关键词")
							$(this).val("请输入关键词");
					})
					
					//绑定省份值改变事件
					u.on(document.getElementById("def2refid"), 'valuechange', function(){
					    viewModel.privinceChange();
					});
					u.on(document.getElementById("def2refidButton"), 'valuechange', function(){
					    viewModel.privinceChange();
					});
				}
			}

		});