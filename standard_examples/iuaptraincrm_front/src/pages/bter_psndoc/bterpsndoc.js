define(['iReferComp', 'refComp', 'error', 'text!./bterpsndoc.html', 'css!./productmgr.css', 'uuigrid', 'utilrow'], function(iReferComp, refComp, errors, html) {
	'use strict';

	if (window.addEventListener) {// all browsers except IE before version 9
		window.removeEventListener('message', refValue, false);
		window.addEventListener('message', refValue, false);
	} else if (window.attachEvent) {// IE before version 9
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

		if (window.addEventListener) {// all browsers except IE before version 9
			window.removeEventListener('message', refValue, false);
		} else if (window.attachEvent) {// IE before version 9
			window.detachEvent("onmessage", refValue);
		}

		$('.dept').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode : "dept",
				selectedVals : pk
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
		$('.corp').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode : "corp",
				selectedVals : pk
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
		var $input = dom.find('input');
		$input.val(name);

	}

	var msg = {};
	msg.data = [
	//		{"memo":"备注","refcode":"BJ","refname":"北京","refpk":"780aca16-e1a3-11e5-aa70-0242ac11001d","selected":true},
	//		{"memo":"备注","refcode":"TJ","refname":"天津","refpk":"65c2c424-e1a3-11e5-aa70-0242ac11001d","selected":true}
	];

	var str = JSON.stringify(msg);
	top.postMessage(str, "*");

	//
	//
	//	var _CONST = {
	//			STATUS_ADD: 'add',
	//			STATUS_EDIT: 'edit'
	//		},
	var app, viewModel, basicDatas, computes, oper, events, pagesize = 10;
	//记录当前页面每页显示多少条

	basicDatas = {
		/**
		 * 人员信息
		 */
		listData : new u.DataTable({
			meta : {
				pk_psndoc : {
					type : 'string'
				},
				pk_dept : {
					type : 'string',
					required : true,
					refCode : 'dept'
				},
				pk_corp : {
					type : 'string',
					required : true,
					refCode : 'corp'
				},
				psncode : {
					type : 'string',
					required : true,
					maxLength: 30
				},
				psnname : {
					type : 'string',
					required : true,
					maxLength: 50
				},
				psnid : {
					type : 'string',
					required : true,
					maxLength: 30
				},
				psnseal : {
					type : 'string'
				},
				def9 : {
					type : 'string'

				},
				psntel : {
					type : 'string'
				},
				email : {
					type : 'string'
				},
				number : {
					type : 'string',
					required : true,
					maxLength: 30
				}
			}
		}),
		/**
		 * 调动信息
		 */
		listDatanum : new u.DataTable({
			meta : {
				pk_psndoc : {
					type : 'string'
				},
				psncode : {
					type : 'string',
					required : true
				},
				psnname : {
					type : 'string',
					required : true
				},
				oldDept : {
					type : 'string'
				},
				oldCorp : {
					type : 'string'
				},
				newDept : {
					type : 'string',
					required : true
				},
				newCorp : {
					type : 'string',
					required : true
				}
			}
		}),
		/**
		 * 任职信息
		 */
		listDataPsnduty : new u.DataTable({
			meta : {
				pk_psnduty : {
					type : 'string'
				},
				pk_psndoc : {
					type : 'string'
				},
				dutystate : {
					type : 'int'
				},
				pkCorp : {
					type : 'string',
					refCode : 'corp'
				},
				pkDept : {
					type : 'string',
					refCode : 'dept'
				},
				//调动时间
				def1 : {
					type : 'string',
					refCode : 'dept'
				}
			}
		}),
		/**
		 * 查询区域
		 */
		searchData : new u.DataTable({
			meta : {
				pkDept : {
					type : 'string'
				},
				psncode : {
					type : 'int'
				},
				pkCorp : {
					type : 'string'
				},
				psnname : {
					type : 'string'
				}
			}
		}),
		/**
		 * 页面状态：read浏览态，edit编辑态;
		 */
		gridStatus : ko.observable("read")
	};

	events = {

		//-------------人员信息列表---------------
		/**
		 * 人员信息查询区域
		 */
		searchClick : function() {
			var row = viewModel.searchData.getSelectedRows()[0];
			var datas = {};
			datas["page.size"] = pagesize;
			if (row != null) {
				datas["params"] = row.getSimpleData();
			} else {
				datas["params"] = null;
			}

			$.ajax({
				type : 'post',
				url : window.cturl + "/bd/psn/queryArea",
				dataType : 'json',
				contentType : "application/json ; charset=utf-8",
				data : JSON.stringify(datas),
				success : function(data) {
					var total = data.totalElements;
					//共多少条
					//viewModel.listData.setSimpleData(data.content);
					iReferComp.setSimpleData(data.content, 'bterpsndoc_grid3', {
						unSelect : true
					});

					viewModel.listData.pageSize(pagesize);
					//每页显示多少条
					var pages = CalculatePageCount(pagesize, total);
					//计算共多少页
					viewModel.listData.totalPages(pages);
					//共多少页
					viewModel.listData.totalRow(total);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		/**
		 * 人员档案新增操作
		 */
		addClick : function() {
			//			viewModel.listData.removeAllRows();
			var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
			md.dGo('psndoc_update')
			viewModel.gridStatus("edit");
			viewModel.listData.clear();

			document.getElementById("psndoc_none2").style.display = "none";

			viewModel.listDatanum.clear();
			viewModel.listDataPsnduty.clear();
			var r = viewModel.listData.createEmptyRow()
			viewModel.listData.setRowFocus(r)
			r.originData = r.getSimpleData();

		},
		/**
		 * 人员档案修改操作
		 */
		editClick : function() {
			if (viewModel.listData.getSelectedRows().length != 1) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds : "2"
				});
				return
			}

			var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
			md.dGo('psndoc_update')
			viewModel.gridStatus("edit")
			document.getElementById("psndoc_none2").style.display = "block";

			var r = viewModel.listData.getSelectedRows()[0];
			iReferComp.setFormData('#psndoc_update', r.getData().data, "bterpsndoc_grid3");
			viewModel.listDataPsnduty.setEnable(false);

			var row = viewModel.listData.getCurrentRow();
			$.ajax({
				type : 'get',
				url : window.cturl + '/bd/psnduty/page?pk_psndoc=' + row.getSimpleData().pk_psndoc,
				dataType : 'json',
				contentType : "application/json ; charset=utf-8",
				success : function(data) {
					var data1 = data['content'];
					//					viewModel.listDataPsnduty.setSimpleData(data1);
					iReferComp.setSimpleData(data1, 'bterpsndoc_gridpsndutyid', false);

					viewModel.listDataPsnduty.setAllRowsUnSelect();

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		/**
		 * 人员信息删除操作
		 */
		delClick : function() {
			if (viewModel.listData.getSelectedRows().length == 0) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds : "2"
				});
				return
			}
			var dats = [];
			var rows = viewModel.listData.getSelectedRows()
			for (var i = 0; i < rows.length; i++) {
				dats.push(rows[i].getSimpleData());
			}
			u.confirmDialog({
				title : "确认",
				msg : "请确认是否删除人员信息？",
				onOk : function() {
					$.ajax({
						type : 'delete',
						url : window.cturl + '/bd/psn/deletelist',
						dataType : 'json',
						contentType : "application/json ; charset=utf-8",
						data : JSON.stringify(dats),
						success : function(data) {
							if (data.flag==1) {
								u.messageDialog({
									msg : "删除成功",
									title : "提示",
									btnText : "OK"
								});

								var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
								md.dGo('psndoc_list')
								getInitData()
							} else {
								u.messageDialog({
									msg : data.msg,
									title : "提示",
									btnText : "OK"
								});
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})

				}
			});
			//			var row = viewModel.listData.getCurrentRow();
		},
		/**
		 * 人员信息解封操作
		 */
		startClick : function() {
			var row = viewModel.listData.getCurrentRow();
			if (viewModel.listData.getSelectedRows().length == 0) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds : "2"
				});
				return
			}
			var dats = [];
			var rows = viewModel.listData.getSelectedRows()
			for (var i = 0; i < rows.length; i++) {
				dats.push(rows[i].getSimpleData());
			}
			$.ajax({
				type : 'post',
				url : window.cturl + '/bd/psn/unSealedPsn',
				dataType : 'json',
				contentType : "application/json ; charset=utf-8",
				data : JSON.stringify(dats),
				success : function(data) {
					if (data.flag==1) {
						u.messageDialog({
							msg : "解封成功",
							title : "提示",
							btnText : "OK"
						});

						var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
						getInitData();
					} else {
						u.messageDialog({
							msg : data.msg,
							title : "提示",
							btnText : "OK"
						});
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		/**
		 * 人员信息封存操作
		 */
		stopClick : function() {
			var row = viewModel.listData.getCurrentRow();
			var dats = [];
			var rows = viewModel.listData.getSelectedRows()
			for (var i = 0; i < rows.length; i++) {
				dats.push(rows[i].getSimpleData());
			}
			//			var psnseal= row.getSimpleData().psnseal;
			if (viewModel.listData.getSelectedRows().length == 0) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds : "2"
				});
				return
			}
			//			if(psnseal==1){
			//				u.messageDialog({msg:"数据已封存",title:"提示", btnText:"OK"});
			//
			//				return false;
			//
			//			}

			$.ajax({
				type : 'post',
				url : window.cturl + '/bd/psn/sealedPsn',
				dataType : 'json',
				contentType : "application/json ; charset=utf-8",
				data : JSON.stringify(dats),
				success : function(data) {

					if (data.flag==1) {
						u.messageDialog({
							msg : "封存成功",
							title : "提示",
							btnText : "OK"
						});

						var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
						getInitData();
					} else {
						u.messageDialog({
							msg : data.msg,
							title : "提示",
							btnText : "OK"
						});
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		/**
		 * 人员信息调动操作
		 */
		addClickma : function() {
			//			viewModel.gridStatus("edit")
			//			var row = viewModel.listData.getCurrentRow();
			if (viewModel.listData.getSelectedRows().length != 1) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds : "2"
				});
				return
			}
			var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout'];
			viewModel.listDatanum.clear();
			viewModel.listDataPsnduty.clear();
			viewModel.gridStatus("read");
			var r = viewModel.listData.getSelectedRows()[0];
			iReferComp.setFormData('#psndoc_card', r.getData().data, "bterpsndoc_grid3");
//			var r = viewModel.listDatanum.createEmptyRow();
//			viewModel.listDatanum.setRowFocus(r);
			var row = viewModel.listData.getCurrentRow();
//			var rownum = viewModel.listDatanum.getCurrentRow();
			var pk_dept = row.getSimpleData().pk_dept
			var pk_corp = row.getSimpleData().pk_corp
			var pk_psndoc = row.getSimpleData().pk_psndoc
			var psncode = row.getSimpleData().psncode
			var psnname = row.getSimpleData().psnname
			var psnseal = row.getSimpleData().psnseal
			if (psnseal == "1") {
				u.messageDialog({
					msg : "封存人员不能调动",
					title : "提示",
					btnText : "OK"
				});
				return
			}

			viewModel.listDatanum.setValue("oldDept", pk_dept);
			viewModel.listDatanum.setValue("oldCorp", pk_corp);
			viewModel.listDatanum.setValue("pk_psndoc", pk_psndoc);
			viewModel.listDatanum.setValue("psncode", psncode);
			viewModel.listDatanum.setValue("psnname", psnname);

			md.dGo('psndoc_card');
			//viewModel.listData.setRowFocus(row)
		},
		/**
		 * 人员信息双击操作
		 * @param {Object} row
		 * @param {Object} e
		 */
		rowDblClick : function(row, e) {
			//			viewModel.formStatus(_CONST.STATUS_EDIT);
			viewModel.gridStatus("read");
			viewModel.listData.setRowSelect(row.rowIndex);
			document.getElementById("psndoc_none2").style.display = "block";
			var row = viewModel.listData.getCurrentRow();
			$.ajax({
				type : 'get',
				url : window.cturl + '/bd/psnduty/page?pk_psndoc=' + row.getSimpleData().pk_psndoc,
				dataType : 'json',
				contentType : "application/json ; charset=utf-8",
				success : function(data) {
					var data1 = data['content'];
					//					viewModel.listDataPsnduty.setSimpleData(data1);
					iReferComp.setSimpleData(data1, 'bterpsndoc_gridpsndutyid');

					viewModel.listDataPsnduty.setAllRowsUnSelect();

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
			var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
			md.dGo('psndoc_update')
			var r = viewModel.listData.getSelectedRows()[0];
			iReferComp.setFormData('#psndoc_update', r.getData().data, "bterpsndoc_grid3");
		},
		/**
		 * 人员信息 页码变动操作
		 */
		pageChangeFunc : function(pageIndex) {
			$.ajax({
				type : 'get',
				url : window.cturl + "/bd/psn/page?page.size=" + pagesize + "&page=" + (pageIndex + 1) + "",
				success : function(data) {
					var total = data.totalElements;
					//共多少条
					var data = data['content'];
					//						viewModel.listData.setSimpleData(data);
					iReferComp.setSimpleData(data, 'bterpsndoc_grid3', false);

					viewModel.listData.setAllRowsUnSelect();

					//						viewModel.listData.pageSize(2);//每页显示多少条
					var pages = CalculatePageCount(pagesize, total);
					//计算共多少页
					viewModel.listData.totalPages(pages);
					//共多少页
					viewModel.listData.totalRow(total);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		/**
		 * 人员信息 页码变动操作
		 */
		sizeChangeFunc : function(size, pageIndex) {
			pagesize = size;
			getInitData();
		},

		//-------------人员信息卡片---------------
		/**
		 * 人员档案保存操作
		 */
		saveClick : function(row, e) {
			var validate = getvalidate(app, "#psndoc_update");
			var row = viewModel.listData.getSelectedRows()[0];
			var result = app.compsValidateMultiParam({
				element : document.querySelector("#psndoc_update"),
				showMsg : true
			});
			if (result.passed == false) {
//				u.messageDialog({
//					msg : "请检查必输项!",
//					title : "操作提示",
//					btnText : "确定"
//				});
			} else {
				$.ajax({
					type : 'post',
					url : window.cturl + '/bd/psn/save',
					dataType : 'json',
					contentType : "application/json ; charset=utf-8",
					data : JSON.stringify(row.getSimpleData()),
					success : function(data) {

						if (data.flag==1) {
							debugger;
							u.messageDialog({
								msg : data.msg,
								title : "提示",
								btnText : "OK"
							});
							viewModel.listData.removeRows
							viewModel.gridStatus("read");
							document.getElementById("psndoc_none2").style.display = "block";
							row.setValue("pk_psndoc", data.pk);
//							row.setValue("ts", data.ts);
							viewModel.listDatanum.clear();
							viewModel.listDataPsnduty.clear();
							//							var	md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
							//							md.dGo('psndoc_list')
							//							getInitData()
						} else {
							u.messageDialog({
								msg : data.msg,
								title : "提示",
								btnText : "OK"
							});

						}

					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})

			}
		},
		/**
		 * 人员档案返回操作
		 */
		backClick : function() {
			var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
			viewModel.gridStatus("read");
			md.dGo('psndoc_list');
			//var yid = viewModel.listData.getCurrentRow().rowId;
			getInitData();
			//viewModel.listData.setRowSelect(yid);
		},
		/**
		 * 人员信息卡片界面取消操作
		 */
		canlClick : function() {
			var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
			viewModel.gridStatus("read");
			md.dGo('psndoc_list');
			var yid = viewModel.listData.getCurrentRow().rowId;
			getInitData();
		},

		//-------------调动信息---------------

		/**
		 * 人员调动操作
		 */
		saveClicknum : function(row, e) {
			var validate = getvalidate(app, "#psndoc_card");
			var result = app.compsValidateMultiParam({
				element : document.querySelector("#psndoc_card"),
				showMsg : true
			});
			if (result.passed == false) {
//				u.messageDialog({
//					msg : "请检查必输项!",
//					title : "操作提示",
//					btnText : "确定"
//				});
			} else {
				var row = viewModel.listDatanum.getSelectedRows()[0];
				var newDept = row.getValue("newDept");
				var oldDept = row.getValue("oldDept");
				if(newDept==oldDept){
					u.messageDialog({
						msg : "新部门与原部门一致，不需要调整，请确认！",
						title : "提示",
						btnText : "OK"
					});
				}else{
					$.ajax({
						type : 'post',
						url : window.cturl + '/bd/psn/savenum',
						dataType : 'json',
						contentType : "application/json ; charset=utf-8",
						data : JSON.stringify(row.getSimpleData()),
						success : function(data) {
							
							if (data.flag==1) {
								u.messageDialog({
									msg : data.msg,
									title : "提示",
									btnText : "OK"
								});
								var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
								md.dGo('psndoc_list')
								getInitData()
							} else {
								u.messageDialog({
									msg : data.msg,
									title : "提示",
									btnText : "OK"
								});
							}
							
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			}

		},
		/**
		 * 取消调动人员操作
		 */
		cancelClickd : function() {
			//			var	md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
			//				md.dGo('psndoc_update')
			//			viewModel.gridStatus("edit")
			//			var r = viewModel.listData.getSelectedRows()[0];
			//			iReferComp.setFormData('#update', r.getData().data,"grid3");
			//			document.getElementById("psndoc_none2").style.display ="block";
			//			debugger;
			//			var row = viewModel.listData.getCurrentRow();
			//			$.ajax({
			//				type: 'get',
			//				url: window.cturl+'/bd/Psnduty/page?pk_psndoc='+row.getSimpleData().pk_psndoc,
			//				dataType : 'json',
			//				contentType: "application/json ; charset=utf-8",
			//				success: function(data) {
			//					debugger;
			//					var data1 = data['content'];
			//					viewModel.listDataPsnduty.setSimpleData(data1);
			//				}
			//			})
			//			viewModel.listDataPsnduty.setEnable(false);// 设置可编辑
			var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
			viewModel.gridStatus("read");
			md.dGo('psndoc_list');
			var yid = viewModel.listData.getCurrentRow().rowId;
			getInitData();
		},

		//-------------任职信息---------------

		/**
		 * 任职信息新增
		 */
		addClickpage : function() {
			oper = 'addnum';
			//			viewModel.gridStatus("pade")
			viewModel.aa = viewModel.listData.getCurrentRow().pk_class;
			viewModel.listDataPsnduty.setEnable(true);
			var r = viewModel.listDataPsnduty.createEmptyRow()
			viewModel.listDataPsnduty.setRowFocus(r)
			r.originData = r.getSimpleData();
			document.getElementById("addren").style.display = "none";
			document.getElementById("updateren").style.display = "none";
			document.getElementById("delren").style.display = "none";
			document.getElementById("saveren").style.display = "inline-block";
			document.getElementById("clnren").style.display = "inline-block"
		},
		/**
		 * 任职信息修改
		 */
		editClickpage : function() {
			var row = viewModel.listDataPsnduty.getCurrentRow();
			if (viewModel.listDataPsnduty.getSelectedRows().length != 1) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds : "2"
				});
				return
			}
			oper = 'updatenum';
			viewModel.bb = viewModel.listDataPsnduty.getCurrentRow().rowId;

			//			viewModel.gridStatus("edit");
			//			viewModel.gridStatus("pade");
			row.originData = row.getSimpleData();
			viewModel.listDataPsnduty.setEnable(true);
			document.getElementById("addren").style.display = "none";
			document.getElementById("updateren").style.display = "none";
			document.getElementById("delren").style.display = "none";
			document.getElementById("saveren").style.display = "inline-block";
			document.getElementById("clnren").style.display = "inline-block";

		},
		/**
		 * 任职信息删除
		 */
		delClickpony : function() {
			var row = viewModel.listDataPsnduty.getCurrentRow();
			if (viewModel.listDataPsnduty.getSelectedRows().length == 0) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds : "2"
				});
				return
			}
			u.confirmDialog({
				title : "确认",
				msg : "请确认是否删除任职信息？",
				onOk : function() {
					$.ajax({
						url : window.cturl + '/bd/psnduty/delete/?pk_psnduty=' + row.getSimpleData().pk_psnduty,
						type : 'delete',
						dataType : 'json',
						contentType : "application/json ; charset=utf-8",
						success : function(data) {
							viewModel.listDataPsnduty.removeRows(viewModel.listDataPsnduty.getSelectedRows());
							u.messageDialog({
								msg : "删除成功",
								title : "提示",
								btnText : "OK"
							});
							// getInitData();
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			});
		},
		/**
		 * 任职信息保存操作
		 * @param {Object} row
		 * @param {Object} e
		 */
		saveClickshift : function(row, e) {
			var result = app.compsValidateMultiParam({
				element : $('#psndoc_agrid')[0]
			});
			if (result.passed == false) {
				u.messageDialog({
					msg : "请检查必输项!",
					title : "操作提示",
					btnText : "确定"
				});
			} else {
				var row = viewModel.listData.getCurrentRow();
				var rowPsnduty = viewModel.listDataPsnduty.getCurrentRow();
				var pk_psndoc = row.getSimpleData().pk_psndoc;
				viewModel.listDataPsnduty.setValue("pk_psndoc", pk_psndoc);
				var Psnduty = rowPsnduty.getSimpleData().pk_psndoc;
				$.ajax({
					type : 'post',
					url : window.cturl + '/bd/psnduty/savePsnduty',
					dataType : 'json',
					contentType : "application/json ; charset=utf-8",
					data : JSON.stringify(rowPsnduty.getSimpleData()),
					success : function(data) {
						if (data.flag==1) {
							u.messageDialog({
								msg : data.msg,
								title : "提示",
								btnText : "OK"
							});

							var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
							md.dGo('psndoc_update')
							viewModel.gridStatus("read")
							var r = viewModel.listData.getSelectedRows()[0];
							iReferComp.setFormData('#psndoc_update', r.getData().data, "bterpsndoc_grid3");

							document.getElementById("addren").style.display = "inline-block";
							document.getElementById("updateren").style.display = "inline-block";
							document.getElementById("delren").style.display = "inline-block";
							document.getElementById("saveren").style.display = "none";
							document.getElementById("clnren").style.display = "none"
							viewModel.listDataPsnduty.setEnable(false);
							// 设置可编辑

						} else {
							u.messageDialog({
								msg : data.msg,
								title : "提示",
								btnText : "OK"
							});
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})

			}
		},
		/**
		 * 取消任职信息操作
		 */
		cancelClicknum : function() {
			var md = document.querySelector('#psndoc-mdlayout')['u.MDLayout']
			//			viewModel.gridStatus("edit");
			if (oper == 'addnum') {
				viewModel.listDataPsnduty.removeRows(viewModel.listDataPsnduty.getSelectedRows());
				viewModel.listDataPsnduty.setEnable(true);

			} else if (oper == 'updatenum') {
				oper = 'int';
				viewModel.listDataPsnduty.setEnable(true);

			}
			document.getElementById("addren").style.display = "inline-block";
			document.getElementById("updateren").style.display = "inline-block";
			document.getElementById("delren").style.display = "inline-block";
			document.getElementById("saveren").style.display = "none";
			document.getElementById("clnren").style.display = "none"
		},

		afterAdd : function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},
		pageClick : function() {
			window.history.go(0);
		},
		filerData : function(obj) {
			var row = viewModel.listDataPsnduty.getSelectedRows();
			if (row.length == 1) {
				var pkCorp = row[0].getSimpleData().pkCorp;
				iReferComp.setFilterPks("pk_corp", pkCorp, '#' + obj);
			}
			return true;
		},
		//部门前事件
		filerDept: function() {
			var row = viewModel.listData.getCurrentRow();
			if(row!=null){
				var pkCorp = row.getValue("pk_corp");
				if(null != pkCorp && pkCorp.length>0){
					iReferComp.setFilterPks("pk_corp", pkCorp, '#pk_deptrefid');
				}else{
					iReferComp.setFilterPks("pk_corp", "1", '#pk_deptrefid');
					u.messageDialog({ msg: "请先选择组织", title: "提示", btnText: "OK" });
					return
				}
			}
		},
		//查询条件部门前事件
		filerSearchDept: function() {
			var row = viewModel.searchData.getCurrentRow();
			if(row!=null){
				var pkCorp = row.getValue("pkCorp");
				if(null != pkCorp && pkCorp.length>0){
					iReferComp.setFilterPks("pk_corp", pkCorp, '#search_pk_dept');
				}else{
					iReferComp.setFilterPks("pk_corp", "1", '#search_pk_dept');
					u.messageDialog({ msg: "请先选择组织", title: "提示", btnText: "OK" });
					return
				}
			}
		},
		//人员调动，部门前事件
		filerChangeDept: function() {
			var row = viewModel.listDatanum.getCurrentRow();
			if(row!=null){
				var pkCorp = row.getValue("newCorp");
				if(null != pkCorp && pkCorp.length>0){
					iReferComp.setFilterPks("pk_corp", pkCorp, '#newDeptRefId');
				}else{
					iReferComp.setFilterPks("pk_corp", "1", '#newDeptRefId');
					u.messageDialog({ msg: "请先选择组织", title: "提示", btnText: "OK" });
					return
				}
			}
		}
	}

	viewModel = u.extend(basicDatas, events)
	//组织调整后，需要清空部门信息
	viewModel.listData.on('pk_corp.valuechange',function(obj){
		var row = viewModel.listData.getCurrentRow();
		if(row!=null){
			row.setValue("pk_dept","");
		}
	});
	
	//查询条件：组织调整后，需要清空部门信息
	viewModel.searchData.on('pkCorp.valuechange',function(obj){
		var row = viewModel.searchData.getCurrentRow();
		if(row!=null){
			row.setValue("pkDept","");
		}
	});
	
	//人员调动：组织调整后，需要清空部门信息
	viewModel.listDatanum.on('newCorp.valuechange',function(obj){
		var row = viewModel.listDatanum.getCurrentRow();
		if(row!=null){
			row.setValue("newDept","");
		}
	});
	
	var getInitData = function() {

		$.ajax({
			type : 'get',
			url : window.cturl + "/bd/psn/page?page.size=" + pagesize + "",
			success : function(data) {

				var total = data.totalElements;
				//共多少条
				var data = data['content'];
				iReferComp.setSimpleData(data, 'bterpsndoc_grid3', {
					unSelect : true
				});
				//viewModel.listData.setSimpleData(data);
				viewModel.listData.setAllRowsUnSelect();
				viewModel.listData.pageSize(pagesize);
				//每页显示多少条
				var pages = CalculatePageCount(pagesize, total);
				//计算共多少页
				viewModel.listData.totalPages(pages);
				//共多少页
				viewModel.listData.totalRow(total);
				viewModel.searchData.createEmptyRow();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		})
	};
	return {
		init : function(content,tabid) {
			content.innerHTML = html;
			window.vm = viewModel;
			app = u.createApp({
				el : '#'+tabid,
				model : viewModel
			})
			getInitData();
			viewModel.listDataPsnduty.setEnable(false);
//			viewModel.listData.on('pk_dept.valueChange', function(obj) {
//				$.ajax({
//					type : 'get',
//					url : window.cturl + "/bd/dept/psndept?pk_dept=" + obj.newValue,
//					datatype : 'json',
//					success : function(data) {
//						debugger;
//						viewModel.listData.setValue("pk_corp", data.pk_corp);
//						iReferComp.setFormData('#update', viewModel.listData.getCurrentRow().getSimpleData());
//					},
//					error : function(XMLHttpRequest, textStatus, errorThrown) {
//						errors.error(XMLHttpRequest);
//					}
//				});
//			})
//			viewModel.listDatanum.on('newDept.valueChange', function(obj) {
//				$.ajax({
//					type : 'get',
//					url : window.cturl + "/bd/dept/psndept?pk_dept=" + obj.newValue,
//					datatype : 'json',
//					success : function(data) {
//						debugger;
//						viewModel.listDatanum.setValue("newCorp", data.pk_corp);
//						iReferComp.setFormData('#card', viewModel.listDatanum.getCurrentRow().getSimpleData());
//					},
//					error : function(XMLHttpRequest, textStatus, errorThrown) {
//						errors.error(XMLHttpRequest);
//					}
//				});
//			})
		}
	}
});
