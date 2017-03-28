define(['iReferComp', 'refComp', 'error', 'text!./vehicleClassify.html', 'uuigrid', "utilrow"], function(iReferComp, refComp, errors, html) {
	'use strict';

	// 车辆分类信息基础数据
	var app, viewModel, basicDatas, enums, events, defaultPageSize = 10;
	var refid, dom;
	var funcCode = 'F020202';

	function ref() {

		var pk = '';
		$('.vehicleClassify_brand').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode: "brand",
				selectedVals: pk,
				isMultiSelectedEnabled: false
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
		$('.vehicleClassify_corp').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode: "corpref",
				selectedVals: pk,
				isMultiSelectedEnabled: false
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
		var $input = dom.find('input');
		$input.val(name);
	}


	enums = {
		statusEnums: enumerate(1005) // 状态码： 启用/停用
	};

	basicDatas = {
		//新增
		addFunShow : ko.observable(false),
		//修改
		updateFunShow : ko.observable(false),
		//启用
		startFunShow : ko.observable(false),
		//停用
		stopFunShow : ko.observable(false),

		searchData: new u.DataTable({
			meta: {
				vclasscode: {
					type: 'string',
					maxLength: 50
				}, // 类别编码
				vclassname: {
					type: 'string',
					maxLength: 100
				}, // 类别名称
				pk_brand: {
					type: 'string'
				}, // 所属品牌
				pk_org: {
					type: 'string'
				}, // 所属事业部
				vstatus: {
					type: 'string'
				} // 状态 
			}
		}),
		listData: new u.DataTable({
			meta: {
				// pk_vehicleclassify: {	type: 'string'	},	// 车辆类别主键
				vclasscode: {
					type: 'string'
				}, // 类别编码
				vclassname: {
					type: 'string'
				}, // 类别名称
				vbrandname: {
					type: 'string'
				},
				pk_brand: {
					type: 'string'
				}, // 所属品牌
				unitname: {
					type: 'string'
				},
				pk_org: {
					type: 'string'
				}, // 所属组织
				vstatus: {
					type: 'string'
				}, // 状态 
				// creationtime: 		{	type: 'string'  },	// 创建时间
				// creator: 			{	type: 'string'  }	// 创建人
			}
		}),
		cardData: new u.DataTable({
			meta: {
				// pk_vehicleclassify: {	type: 'string'	},	// 车辆类别主键
				vclasscode: {
					type: 'string',
					required: true,
					maxLength: 50
				}, // 类别编码
				vclassname: {
					type: 'string',
					required: true,
					maxLength: 100
				}, // 类别名称
				pk_brand: {
					type: 'string',
					required: true
				}, // 所属品牌
				pk_org: {
					type: 'string',
					required: true
				}, // 所属组织
			}
		})
	};

	events = {
		//	查询车辆分类方法
		queryMain: function() {
			var rows = viewModel.searchData.getSelectedRows();
			var param = rows[0].getSimpleData(); // param: 	查询参数 & 分页参数
			param.psize = viewModel.listData.pageSize();
			param.pindex = viewModel.listData.pageIndex();
			// console.log(param);

			$.ajax({
				type: 'post',
				url: window.cturl + '/baseData/vehicleClassify/querypageExt',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function(data) {
					// viewModel.listData.pageSize(pagesize);//每页显示多少条
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
			});
		},

		// queryTest: function() {
			
		// 	$.ajax({
		// 		type: 'post',
		// 		url: window.cturl + '/baseData/vehicleClassify/test1',
		// 		contentType: "application/json ; charset=utf-8",
		// 		success: function(data) {
		// 			// viewModel.listData.pageSize(pagesize);//每页显示多少条
		// 			console.log(data);
		// 		},
		// 		error: function(XMLHttpRequest, textStatus, errorThrown) {
		// 			errors.error(XMLHttpRequest);
		// 		}
		// 	});
		// },
		// 查询按钮点击事件
		searchClick: function searchClick(model, event) {
			viewModel.listData.pageIndex(0);
			// // 清零状态时， 重置value
			// if(document.getElementById("vehicleClassify_vstatus").getAttribute("title").trim() == ""){
			// 	viewModel.searchData.setValue("vstatus",null);
			// }

			events.queryMain();
		},
		sizeChangeFunc: function(size, pageIndex) {
			viewModel.listData.pageSize(size);
			viewModel.listData.pageIndex(0);
			viewModel.queryMain();
		},
		pageChangeFunc: function(pageIndex) {
			viewModel.listData.pageIndex(pageIndex);
			viewModel.queryMain();
		},
		afterAdd: function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},
		// 新增按钮点击事件
		addClick: function addClick() {
			var md = document.querySelector('#vehicleClassify-mdlayout')['u.MDLayout'];
			viewModel.cardData.clear();
			$('#vehicleClassify_cardpage-title').text('新增');
			md.dGo('vehicleClassify_cardpage');
			var r = viewModel.cardData.createEmptyRow();
		},
		// 返回按钮点击事件
		backClick: function backClick() {
			viewModel.cardData.clear();
			var md = document.querySelector('#vehicleClassify-mdlayout')['u.MDLayout'];
			md.dGo('vehicleClassify_listpage');
		},
		// 重置按钮点击事件
		resetClick: function resetClick() {
			viewModel.cardData.clear();
		},
		// 保存按钮点击事件
		saveClick: function saveClick() {
			onLoading();
			// 校验
			var validate = getvalidate(app, "#vehicleClassify_cardpage");
			if (validate == false) {
				onCloseLoading();
				return;
			}

			// 获取当前编辑行
			var body = viewModel.cardData.getCurrentRow().getSimpleData();

			// 请求后台保存当前数据
			$.ajax({
				type: 'post',
				url: window.cturl + '/baseData/vehicleClassify/save',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(body),
				success: function success(data) {
					onCloseLoading();
					// console.log(data);
					u.messageDialog({
						msg: "保存成功",
						title: "提示",
						btnText: "OK"
					});
					getInitData();
					events.backClick();
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					onCloseLoading();
					errors.error(XMLHttpRequest);
				}
			});
		},
		// 启用按钮点击事件
		startClick: function() {

			// 获取应删除当前行
			var rows = viewModel.listData.getSelectedRows();
			var postArray = [];
			// 请求后台删除该行
			if (rows == null || rows.length == 0) {
				u.messageDialog({
					msg: "请选择要启用的车辆分类",
					title: "提示",
					btnText: "OK"
				});
				return;
			} else {
				// 过滤已经启用的数据
				for (var i = 0; i < rows.length; i++) {
					if (rows[i].getSimpleData().vstatus == '10051002') {
						postArray.push(rows[i].getSimpleData());
					}
				}
				$.ajax({
					type: 'post',
					url: window.cturl + '/baseData/vehicleClassify/start',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(postArray),
					success: function success(data) {
						// console.log(data);
						u.messageDialog({
							msg: "启用成功",
							title: "提示",
							btnText: "OK"
						});
						getInitData();
					},
					error: function error(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				});
			}
		},

		// 停用按钮点击事件
		stopClick: function() {

			// 获取应删除当前行
			var rows = viewModel.listData.getSelectedRows();
			var postArray = [];
			// 请求后台删除该行
			if (rows == null || rows.length == 0) {
				u.messageDialog({
					msg: "请选择要停用的车辆分类",
					title: "提示",
					btnText: "OK"
				});
				return;
			} else {
				// 过滤已经停用的数据
				for (var i = 0; i < rows.length; i++) {
					if (rows[i].getSimpleData().vstatus == '10051001') {
						postArray.push(rows[i].getSimpleData());
					}
				}
				$.ajax({
					type: 'post',
					url: window.cturl + '/baseData/vehicleClassify/stop',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(postArray),
					success: function success(data) {
						// console.log(data);
						if (data.result) {
							u.messageDialog({
								msg: "停用成功",
								title: "提示",
								btnText: "OK"
							});
						} else {
							u.messageDialog({
								msg: '车辆类别 ' + data.nstop + ' 下还有启用的车系， 无法停用！',
								title: "提示",
								btnText: "OK"
							});
						}
						getInitData();
					},
					error: function error(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				});
			}
		},
		// 删除按钮点击事件
		deleteClick: function deleteClick(id) {
			// 设置点击删除该行为当前行
			viewModel.listData.setRowSelect(id);
			// 获取应删除当前行
			var delRow = viewModel.listData.getCurrentRow();
			// 请求后台删除该行
			$.ajax({
				type: 'post',
				url: window.cturl + '/baseData/vehicleClassify/delete',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(delRow.getSimpleData()),
				success: function success(data) {
					// console.log(data);
					u.messageDialog({
						msg: "删除成功",
						title: "提示",
						btnText: "OK"
					});
					viewModel.listData.removeRow(delRow);
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		//所属事业部编辑前事件
		filerData: function() {
			var row = viewModel.cardData.getCurrentRow();
			if (row != null) {
				var pk = "10101002";
				iReferComp.setFilterPks("pk", pk,
					'#vehicleClassify_pk_org');
			}
		},
		filterData: function() {
			var pk = "10101002"; // 组织类型： 事业部
			iReferComp.setFilterPks("pk", pk,
				'#vehicleClassify_org');
		},
		// 修改按钮点击事件
		updateClick: function updateClick() {
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();

			if (rows != null && rows.length == 1) {
				var mainData = rows[0].data;
				if (comToName(mainData.vstatus.value) == '停用') {
					u.messageDialog({
						msg: "已停用的车辆类别不能进行修改，请启用后再修改！",
						title: "提示",
						btnText: "OK"
					});
				} else {

					viewModel.cardData.clear();
					var r = viewModel.cardData.createEmptyRow();
					$('#vehicleClassify_cardpage-title').text('修改');
					var md = document.querySelector('#vehicleClassify-mdlayout')['u.MDLayout'];
					md.dGo('vehicleClassify_cardpage');

					r.setSimpleData(mainData);
				}
			} else if (null != rows && rows.length > 1) {
				u.messageDialog({
					msg: "每次只能选择一个车辆类别进行修改，请重新选择！",
					title: "提示",
					btnText: "OK"
				});
			} else {
				u.messageDialog({
					msg: "请选择一个车辆类别，再进行修改！",
					title: "提示",
					btnText: "OK"
				});
			}
		}
	};

	// viewModel = u.extend(basicDatas, enums, events);
  viewModel = u.extend(basicDatas, events, enums);
  
	var getInitBtn = function() {
		//按钮权限
		$.ajax({
			type: 'get',
			url: window.cturl + "/security/authBtn/auth?funcCode=" + funcCode,
			success: function(data) {
				var funList = data;
				if (null != funList) {
					for (var i = 0; i < funList.length; i++) {
						var funTemp = funList[i];
						if ("新增" == funTemp) {
							viewModel.addFunShow(true);
						} else if ("修改" == funTemp) {
							viewModel.updateFunShow(true);
						} else if ("启用" == funTemp) {
							viewModel.startFunShow(true);
						} else if ("停用" == funTemp) {
							viewModel.stopFunShow(true);
						}
					}
				}

			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		});
	}

	// 初始化列表数据 或 刷新列表数据 - 车辆分类信息
	var getInitData = function getInitData() {
		getInitBtn();
		viewModel.listData.clear();
		viewModel.cardData.clear();
		viewModel.searchData.clear();

		// 初始化搜索栏
		viewModel.searchData.createEmptyRow();
		viewModel.listData.pageSize(defaultPageSize);
		// 加载车辆类别信息
		events.searchClick();
	};

	return {
		init: function init(content, tabid) {

			content.innerHTML = html;
			window.vm = viewModel;

			app = u.createApp({
				el: '#' + tabid,
				model: viewModel
			});
			getInitData();
			ref();
		}
	};
});