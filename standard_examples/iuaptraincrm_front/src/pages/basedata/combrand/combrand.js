define(['iReferComp', 'refComp', 'error', 'text!./combrand.html', 'uuigrid', "utilrow"], function(iReferComp, refComp, errors, html) {
	'use strict';

	var app, viewModel, basicDatas, events, selectValue, pagesize = 10;
	var refid;
	var dom;

	ko.bindingHandlers.sdfCreater = {
		init: function(element, valueAccessor) {
			var currentValue = valueAccessor();
			// console.log('init: ' + currentValue);
			$(element).text('');
		},
		update: function(element, valueAccessor) {
			var currentValue = valueAccessor();
			// console.log('update: ' + currentValue);
			$(element).text('*' + currentValue + '*');
		}
	};

	basicDatas = {
		comboData: enumerate(1005),

		searchData: new u.DataTable({
			meta: {
				vcbrandcode: {
					type: 'string'
				}, // 品牌编码 
				vcbrandname: {
					type: 'string'
				}, // 品牌名称 
				vstatus: {
					type: 'string'
				} // 状态 
			}
		}),
		listData: new u.DataTable(),
		cardData: new u.DataTable({
			meta: {
				pk_combrand: {
					type: 'string'
				}, // 品牌主键
				vcbrandname: {type: 'string', required: true, nullMsg: '不能为空！'}, // 品牌名称
				vfactory: {type: 'string',maxLength:300},//车型编码
			}
		}),
		//新增
		addFunShow: ko.observable(false),
		//修改
		editFunShow: ko.observable(false),
		//启用
		startFunShow: ko.observable(false),
		//停用
		stopFunShow: ko.observable(false)
	};

	events = {
		formattedC: function(creator) {
			return "#" + creator + "#";
		},

		queryMain: function() {
			var rows = viewModel.searchData.getSelectedRows();
			var param = rows[0].getSimpleData();
			param.psize = viewModel.listData.pageSize();
			param.pindex = viewModel.listData.pageIndex();
			// console.log(param);
			$.ajax({
				type: 'post',
				url: window.cturl + "/baseData/combrand/querypage",
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
			events.queryMain();
		},
		// 页大小改变事件
		sizeChangeFunc: function(size, pageIndex) {
			viewModel.listData.pageSize(size);
			viewModel.listData.pageIndex(0);
			viewModel.searchClick();
		},
		//	页数跳转事件
		pageChangeFunc: function(pageIndex) {
			viewModel.listData.pageIndex(pageIndex);
			viewModel.searchClick();
		},
		afterAdd: function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},

		//	新增按钮点击事件
		addClick: function addClick() {
			var md = document.querySelector('#combrand-mdlayout')['u.MDLayout'];
			viewModel.cardData.clear();

			md.dGo('combrand_cardpage');
			var r = viewModel.cardData.createEmptyRow();
		},
		// 返回按钮点击事件
		backClick: function backClick() {
			viewModel.cardData.clear();
			var md = document.querySelector('#combrand-mdlayout')['u.MDLayout'];
			md.dGo('combrand_listpage');
		},
		//  重置按钮点击事件
		resetClick: function resetClick() {
			viewModel.cardData.clear();
		},
		// 保存按钮点击事件
		saveClick: function saveClick() {
			// 获取当前编辑行
			var body = viewModel.cardData.getCurrentRow().getSimpleData();
			var validate = getvalidate(app, "#combrand_cardpage");
			if (validate == false) {
				return;
			}
			onLoading();
			// 请求后台保存当前数据
			$.ajax({
				type: 'post',
				url: window.cturl + '/baseData/combrand/save',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(body),
				success: function success(data) {
					onCloseLoading();
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
			// var delRow = viewModel.listData.getCurrentRow();
			// 请求后台删除该行
			if (rows == null || rows.length == 0) {
				u.messageDialog({
					msg: "请选择要启用的品牌",
					title: "提示",
					btnText: "OK"
				});
				return;
			} else {
				// 校验是否已启用
				if (rows[0].getSimpleData().vstatus == '10051001') {
					u.messageDialog({
						msg: "已启用",
						title: "提示",
						btnText: "OK"
					});
					return;
				}
				$.ajax({
					type: 'post',
					url: window.cturl + '/baseData/combrand/start',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(rows[0].getSimpleData()),
					success: function success(data) {
						u.messageDialog({
							msg: data.msg,
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
			// var delRow = viewModel.listData.getCurrentRow();
			// 请求后台删除该行
			if (rows == null || rows.length == 0) {
				u.messageDialog({
					msg: "请选择要启用的品牌",
					title: "提示",
					btnText: "OK"
				});
				return;
			} else {
				// 校验是否已停用
				if (rows[0].getSimpleData().vstatus == '10051002') {
					u.messageDialog({
						msg: "已停用",
						title: "提示",
						btnText: "OK"
					});
					return;
				}
				$.ajax({
					type: 'post',
					url: window.cturl + '/baseData/combrand/stop',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(rows[0].getSimpleData()),
					success: function success(data) {
						// console.log(data);
						u.messageDialog({
							msg: data.msg,
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
		// 	修改按钮点击事件
		updateClick: function() {
			viewModel.cardData.setEnable(true);
			// viewModel.listData.setRowSelect();
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();

			if (rows != null && rows.length == 1) {
				var mainData = rows[0].data;
				viewModel.cardData.clear();
				var r = viewModel.cardData.createEmptyRow();

				var md = document.querySelector('#combrand-mdlayout')['u.MDLayout'];
				md.dGo('combrand_cardpage');

				r.setSimpleData(mainData);
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
		// 输入后，自动查询品牌名称是否重复
		OnBlurEvent: function OnBlurEvent() {
			//console.log(vm.cardData.getCurrentRow().vbrandname);
			$.get(window.cturl + '/baseData/combrand/checkBrandName', {
				vbrandname: vm.cardData.getCurrentRow().vbrandname
			}, function(data, status) {
				if (data.result)
					alert('REPEAT BRANDNAME');
			});
		}
	};

	viewModel = u.extend(basicDatas, events);

	var trClickListener = function() {
		$('#brandDataGrid').on('click', 'tr', function(event) {
			// alert('slfjl');
			selectValue = $(this).children().eq(0).text();
		});
	};


	// 初始化列表数据 或 刷新列表数据 - 品牌信息
	var getInitData = function getInitData() {
		$.ajax({
			type: 'get',
			url: window.cturl+"/security/authBtn/auth?funcCode=F020301",
			success: function(data) {
				var funList = data;
				if(null != funList){
					for(var i=0;i<funList.length;i++){
						var funTemp = funList[i];
						if("新增"==funTemp){
							viewModel.addFunShow(true);
							viewModel.editFunShow(true);
						}/*else if("修改"==funTemp){
							viewModel.editFunShow(true);
						}*/else if("启用"==funTemp){
							viewModel.startFunShow(true);
						}else if("停用"==funTemp){
							viewModel.stopFunShow(true);
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

		// 初始化搜索栏
		viewModel.searchData.createEmptyRow();
		viewModel.listData.pageSize(10);
		viewModel.listData.pageIndex(0);
		events.searchClick();
		//viewModel.listData.setRowUnFocus();
		//viewModel.listData.setAllRowsUnSelect();
		// viewModel.listData.setEnable(true);
	};

	return {
		init: function init(content,tabid) {
			content.innerHTML = html;
			window.vm = viewModel;
			app = u.createApp({
				el: '#'+tabid,
				model: viewModel
			});
			selectValue = -1;
			getInitData();
		}
	};
});