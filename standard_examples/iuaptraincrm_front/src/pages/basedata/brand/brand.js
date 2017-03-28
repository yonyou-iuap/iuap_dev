define(['error', 'text!./brand.html', 'uuigrid', "utilrow"], function(errors, html) {
	'use strict';
	var app, viewModel, basicDatas, enums, events;
	var defaultPageSize = 10;
	var funcCode = 'F020201';


	enums = {
		statusEnums: enumerate(1005) // 状态码： 启用/停用
	};

	basicDatas = {
		//新增
		addFunShow: ko.observable(false),
		//修改
		updateFunShow: ko.observable(false),
		//启用
		startFunShow: ko.observable(false),
		//停用
		stopFunShow: ko.observable(false),


		searchData: new u.DataTable({
			meta: {
				vbrandcode: {
					type: 'string'
				}, // 品牌编码 
				vbrandname: {
					type: 'string'
				}, // 品牌名称 
				vstatus: {
					type: 'string'
				} // 状态 
			}
		}),
		listData: new u.DataTable({
			pk_brand: {
				type: 'string'
			}, // 品牌主键
			vbrandcode: {
				type: 'string'
			}, // 品牌编码
			vbrandname: {
				type: 'string'
			}, // 品牌名称
			vstatus: {
				type: 'string'
			} // 状态 
			// creationtime:   { type: 'string' },		 // 创建时间
			// creator: 		{ type: 'string' }		 // 创建人
		}),
		cardData: new u.DataTable({
			meta: {
				pk_brand: {
					type: 'string'
				}, // 品牌主键
				vbrandcode: {
					type: 'string',
					required: true
				}, // 品牌编码
				vbrandname: {
					type: 'string',
					required: true
				}, // 品牌名称
				// vstatus:		{ type: 'string' }		 // 状态 
				// creationtime:   { type: 'string' },		 // 创建时间
				// creator: 		{ type: 'string' }		 // 创建人
			}
		})


	};

	events = {

		queryMain: function() {
			var rows = viewModel.searchData.getSelectedRows();
			var param = rows[0].getSimpleData();
			param.psize = viewModel.listData.pageSize();
			param.pindex = viewModel.listData.pageIndex();
			$.ajax({
				type: 'post',
				url: window.cturl + "/baseData/brand/querypage",
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function(data) {
					// viewModel.listData.pageSize(pagesize); //每页显示多少条
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


		// 查询按钮点击事件
		searchClick: function searchClick(model, event) {
			viewModel.listData.pageIndex(0);
			events.queryMain();
		},
		// 页大小改变事件
		sizeChangeFunc: function(size, pageIndex) {
			viewModel.listData.pageSize(size);
			viewModel.listData.pageIndex(0);
			viewModel.queryMain();
		},
		//	页数跳转事件
		pageChangeFunc: function(pageIndex) {
			viewModel.listData.pageIndex(pageIndex);
			viewModel.queryMain();
		},
		afterAdd: function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},

		//	新增按钮点击事件
		addClick: function addClick() {
			var md = document.querySelector('#brand-mdlayout')['u.MDLayout'];
			viewModel.cardData.clear();
			$('#brand_cardpage-title').text('新增');
			md.dGo('brand_cardpage');
			var r = viewModel.cardData.createEmptyRow();
		},
		// 返回按钮点击事件
		backClick: function backClick() {
			viewModel.cardData.clear();
			var md = document.querySelector('#brand-mdlayout')['u.MDLayout'];
			md.dGo('brand_listpage');
		},
		//  重置按钮点击事件
		resetClick: function resetClick() {
			viewModel.cardData.clear();
		},
		// 保存按钮点击事件
		saveClick: function saveClick() {
			onLoading();
			// 获取当前编辑行
			var body = viewModel.cardData.getCurrentRow().getSimpleData();
			// 校验
			var validate = getvalidate(app, "#brand_cardpage");
			if (validate == false) {
				onCloseLoading();
				return;
			}
			// 请求后台保存当前数据
			$.ajax({
				type: 'post',
				url: window.cturl + '/baseData/brand/save',
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
					msg: "请选择要启用的品牌",
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
					url: window.cturl + '/baseData/brand/start',
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
					msg: "请选择要启用的品牌",
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
					url: window.cturl + '/baseData/brand/stop',
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
								msg: '品牌 ' + data.nstop + ' 下还有启用的车辆类别， 无法停用！',
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
		// 	修改按钮点击事件
		updateClick: function() {
			viewModel.cardData.setEnable(true);
			// viewModel.listData.setRowSelect();
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();

			if (rows != null && rows.length == 1) {
				var mainData = rows[0].data;
				if (comToName(mainData.vstatus.value) == '停用') {
					u.messageDialog({
						msg: "已停用品牌不能进行修改，请启用后再修改！",
						title: "提示",
						btnText: "OK"
					});
				} else {

					viewModel.cardData.clear();
					var r = viewModel.cardData.createEmptyRow();
					$('#brand_cardpage-title').text('修改');
					var md = document.querySelector('#brand-mdlayout')['u.MDLayout'];
					md.dGo('brand_cardpage');

					r.setSimpleData(mainData);
				}
			} else if (null != rows && rows.length > 1) {
				u.messageDialog({
					msg: "每次只能选择一个品牌进行修改，请重新选择！",
					title: "提示",
					btnText: "OK"
				});
			} else {
				u.messageDialog({
					msg: "请选择一个品牌，再进行修改！",
					title: "提示",
					btnText: "OK"
				});
			}


		},

	};

	// viewModel = u.extend(enums, basicDatas, events);
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

	// 初始化列表数据 或 刷新列表数据 - 品牌信息
	var getInitData = function getInitData() {
		// 初始化按钮权限
		getInitBtn();

		viewModel.listData.clear();
		viewModel.cardData.clear();
		viewModel.searchData.clear();

		// 初始化搜索栏
		viewModel.searchData.createEmptyRow();
		viewModel.listData.pageSize(defaultPageSize);

		events.searchClick();
	};

	return {
		init: function init(content, tabid) {

			//getInitEnums();

			content.innerHTML = html;
			window.vm = viewModel;

			app = u.createApp({
				el: '#' + tabid,
				model: viewModel
			});
			getInitData();
		}
	};
});