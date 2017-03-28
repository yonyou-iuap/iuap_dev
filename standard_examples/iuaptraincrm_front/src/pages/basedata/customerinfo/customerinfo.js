define(['iReferComp', 'refComp', 'error', 'text!./customerinfo.html', 'uuigrid', "utilrow", 'ajaxfileupload', 'ossupload', 'interfaceFile', 'interfaceFileImpl'], function(iReferComp, refComp, errors, html) {
	'use strict';

	var ctxRoot = 'iuaptraincrm'; //ctx || 'iuaptraincrm';
	var ctrlBathPath = ctxRoot + '/bd/customerinfo';
	var funcCode = 'F020401';
	var app, viewModel, basicDatas, enums, events, defaultPageSize = 10;
	var refid, dom;
	var saveOrUpdate = '/update'; // 默认设置页面为新增状态

	// 参照函数， 内容： 省份，城市信息
	function ref() {
		var pk = '';
		$('.customerinfo-city').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode: "city",
				selectedVals: pk,
				isMultiSelectedEnabled: false
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
		$('.customerinfo-province').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode: "province",
				selectedVals: pk,
				isMultiSelectedEnabled: false
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
		
		var $input = dom.find('input');
		$input.val(name);
	}


	basicDatas = {

		gridStatus: ko.observable("view"), // 客户联系人表格状态监视变量

		//修改
		updateFunShow : ko.observable(false),
		//合并
		mergeFunShow : ko.observable(false),
		//启用
		startFunShow : ko.observable(false),
		//停用
		stopFunShow : ko.observable(false),

		 
		// 搜索框数据  ： 客户信息列表查询
		searchData: new u.DataTable({
			meta: {
				vcustomerno: {
					type: 'string'
				}, // 客户编码
				vcustomername: {
					type: 'string'
				}, // 客户名称
				pk_province: {
					type: 'string'
				}, // 省份代码
				pk_city: {
					type: 'string'
				}, // 城市代码
				vstatus: {
					type: 'string'
				} // 状态
			}
		}),
		// 客户信息列表数据 
		listData: new u.DataTable({
			// vbillno: 		{ type: 'string' },      // 单据编号
			vcustomerno: {
				type: 'string'
			}, // 客户编码
			vcustomername: {
				type: 'string'
			}, // 客户名称
			pk_province: {
				type: 'string'
			}, // 省份代码
			provinceName: {
				type: 'string'
			}, // 省份名称
			pk_city: {
				type: 'string'
			}, // 城市代码
			cityName: {
				type: 'string'
			}, // 城市名称
			vaddress: {
				type: 'string'
			}, // 地址
			vzipcode: {
				type: 'string'
			}, // 邮编
			vtaxnumber: {
				type: 'string'
			}, // 纳税人识别号
			vbizlicense: {
				type: 'string'
			}, //营业执照地址
			vbizphone: {
				type: 'string'
			}, // 营业执照固定电话
			vaccountbank: {
				type: 'string'
			}, // 开户银行
			vaccountno: {
				type: 'string'
			}, // 开户行账号
			vcusorgcode: {
				type: 'string'
			}, // 组织机构代码
			vcompanysize: {
				type: 'string'
			}, // 企业规模
			vbuyinterest: {
				type: 'string'
			}, // 购买意向强烈程度
			vsubpower: {
				type: 'string'
			}, // 当地补贴政策力度
			vstatus: {
				type: 'string'
			}, // 状态
			ts: {
				type: 'string'
			} //时间戳
			// pk_customerinfo 主键
			// vbizphone	营业执照固定电话
			//
		}),
		mergeData: new u.DataTable({
			// vbillno: 		{ type: 'string' },      // 单据编号
			vcustomerno: {
				type: 'string'
			}, // 客户编码
			vcustomername: {
				type: 'string'
			}, // 客户名称
			pk_province: {
				type: 'string'
			}, // 省份代码
			provinceName: {
				type: 'string'
			}, // 省份名称
			pk_city: {
				type: 'string'
			}, // 城市代码
			cityName: {
				type: 'string'
			}, // 城市名称
			vaddress: {
				type: 'string'
			}, // 地址
			vzipcode: {
				type: 'string'
			}, // 邮编
			vtaxnumber: {
				type: 'string'
			}, // 纳税人识别号
			vbizlicense: {
				type: 'string'
			}, //营业执照地址
			vbizphone: {
				type: 'string'
			}, // 营业执照固定电话
			vaccountbank: {
				type: 'string'
			}, // 开户银行
			vaccountno: {
				type: 'string'
			}, // 开户行账号
			vcusorgcode: {
				type: 'string'
			}, // 组织机构代码
			vcompanysize: {
				type: 'string'
			}, // 企业规模
			vbuyinterest: {
				type: 'string'
			}, // 购买意向强烈程度
			vsubpower: {
				type: 'string'
			}, // 当地补贴政策力度
			vstatus: {
				type: 'string'
			}, // 状态
			ts: {
				type: 'string'
			} //时间戳
			// pk_customerinfo 主键
			// vbizphone	营业执照固定电话
			//
		}),
		// 新增或修改卡片页面 - 客户信息主表数据
		cardData: new u.DataTable({
			meta: {
				vcustomerno: {
					type: 'string'
				}, // 客户编码
				vcustomername: {
					type: 'string',
					required: true,
					maxLength: 100
				}, // 客户名称
				pk_province: {
					type: 'string',
					required: true
				}, // 省份
				provinceName: {
					type: 'string'
				},
				pk_city: {
					type: 'string',
					required: true
				}, // 城市
				cityName: {
					type: 'string'
				},
				// 城市
				vaddress: {
					type: 'string',
					maxLength: 200
				}, // 地址
				vzipcode: {
					type: 'string',
					maxLength: 30
				}, // 邮编
				vtaxnumber: {
					type: 'string',
					maxLength: 100
				}, // 纳税人识别号
				vbizlicense: {
					type: 'string',
					maxLength: 200
				}, //营业执照地址
				vbizphone: {
					type: 'string',
					maxLength: 100
				}, // 营业执照固定电话
				vaccountbank: {
					type: 'string',
					maxLength: 50
				}, // 开户银行
				vaccountno: {
					type: 'string',
					maxLength: 50
				}, // 开户行账号
				vcusorgcode: {
					type: 'string',
					maxLength: 50
				}, // 组织机构代码
				vcompanysize: {
					type: 'string',
					maxLength: 200
				}, // 企业规模
				vbuyinterest: {
					type: 'string',
					maxLength: 200
				}, // 购买意向强烈程度
				vsubpower: {
					type: 'string',
					maxLength: 200
				}, // 当地补贴政策力度
				vstatus: {
					type: 'string'
				}, // 状态
				ts: {
					type: 'string'
				} // 时间戳
			}
			// vdeclaredept	申报部门
			// pk_org 组织
		}),

		// 车辆信息明细子表 -列表数据
		subVehicleData: new u.DataTable({
			meta: {
				pk_customervehicle: {
					type: 'string'
				}, // 主键
				pk_customerinfo: {
					type: 'string'
				}, // 客户信息主键
				vvin: {
					type: 'string'
				}, // 车辆识别码VIN
				pk_model: {
					type: 'string'
				}, // 车型
				vmodelname: {
					type: 'string'
				}, // 车型（名称）
				vseriesname: {
					type: 'string'
				}, // 车系
				vvehiclelicense: {
					type: 'string'
				}, // 车牌号
				vcertificate: {
					type: 'string'
				}, // 合格证号
				vinvoiceno: {
					type: 'string'
				}, // 发票号
				dofflinedate: {
					type: 'string'
				}, // 下线日期
				ddeliverydate: {
					type: 'string'
				}, // 交付日期
				dinvoicedate: {
					type: 'string'
				}, // 开票日期
				dreceivedate: {
					type: 'string'
				}, // 验收日期
				dlicensedate: {
					type: 'string'
				}, // 上牌日期
				visoperation: {
					type: 'string'
				}, // 是否试运营车
				doperstartdate: {
					type: 'string'
				}, // 试运营开始日期
				doperenddate: {
					type: 'string'
				}, // 试运营结束日期
				vlocation: {
					type: 'string'
				}, // 车辆所在地
				vcarstatus: {
					type: 'string'
				}, // 车辆状态
				dwarrantydate: {
					type: 'string'
				}, // 质保期
				nwarratymile: {
					type: 'string'
				} // 质保里程
				// 备注
				//pk_customerinfo_items			主键
				//pk_customerinfo		客户信息主键
			}
		}),
		//  联系人明细  - 列表数据
		subContactorData: new u.DataTable({
			meta: {
				pk_customercontactor: {
					type: 'string'
				}, // 客户联系人
				pk_customerinfo: {
					type: 'string',
				}, // *客户信息主键
				vname: {
					type: 'string',
					required: true,
					maxLength: 50
				}, //20 *姓名
				vphone: {
					type: 'integer',
					maxLength: 50
				}, //17 电话
				vduty: {
					type: 'string',
					maxLength: 50
				} //11 职位
				//pk_advance_items			主键
				//pk_customerinfo		客户信息主键
			}
		}),

		// 附件信息
		fileData: new u.DataTable({
			meta: {
				id: {
					type: 'string'
				}, //主键
				filepath: {
					type: 'string'
				}, //文件名称
				filesize: {
					type: 'string'
				}, //文件大小
				filename: {
					type: 'string'
				}, //文件名称
				uploadtime: {
					type: 'string'
				}, //上传时间
				groupname: {
					type: 'string'
				}, //
				url: {
					type: 'string'
				} //URL
			}
		})
		
	};

	enums = {
		 statusEnums:enumerate(1005),
		 yesOrNo:enumerate(1001)
	};


	// 事件处理函数
	events = {
		// 客户信息查询按钮函数
		queryMain: function() {
			var rows = viewModel.searchData.getSelectedRows();
			var param = rows[0].getSimpleData();
			param.psize = viewModel.listData.pageSize();
			param.pindex = viewModel.listData.pageIndex();
			// console.log(param);
			$.ajax({
				type: 'POST',
				url: ctrlBathPath + "/queryPage",
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
			});
		},


		// 查询按钮点击事件
		searchClick: function(model, event) {
			viewModel.listData.pageIndex(0);
			events.queryMain();
		},
		// 客户信息列表页面 - 页大小改变事件
		sizeChangeFunc: function(size, pageIndex) {
			viewModel.listData.pageSize(size);
			viewModel.listData.pageIndex(0);
			viewModel.queryMain();
		},

		// 客户信息明细列表页面 - 页大小改变事件
		itemVehicleSizeChangeFunc: function(size, pageIndex) {
			viewModel.subVehicleData.pageSize(size);
			viewModel.subVehicleData.pageIndex(0);
			viewModel.itemVehicleQueryClick();
		},


		//	客户信息列表页面 - 页数跳转事件
		pageChangeFunc: function(pageIndex) {
			viewModel.listData.pageIndex(pageIndex);
			viewModel.queryMain();
		},

		itemContactorSizeChangeFunc: function(size, pageIndex) {
			viewModel.subContactorData.pageSize(size);
			viewModel.subContactorData.pageIndex(0);
			viewModel.itemContactorQueryClick();
		},
		itemContactorPageChangeFunc: function(pageIndex) {
			viewModel.subContactorData.pageIndex(pageIndex);
			viewModel.itemContactorQueryClick();
		},
		//	客户信息列表页面 - 页数跳转事件
		itemVehiclePageChangeFunc: function(pageIndex) {
			viewModel.subVehicleData.pageIndex(pageIndex);
			viewModel.itemVehicleQueryClick();
		},
		// checkbox事件
		afterAdd: function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},

		//	客户信息列表页面 - 新增按钮点击事件
		addClick: function addClick() {
			var md = document.querySelector('#customerinfo-mdlayout')['u.MDLayout'];
			viewModel.cardData.clear();
			viewModel.subVehicleData.clear();

			md.dGo('customerinfo_cardpage');
			var r = viewModel.cardData.createEmptyRow();
		},
		// 客户信息明细页面 - 返回按钮点击事件
		backClick: function backClick() {
			viewModel.cardData.clear();
			// viewModel.fileData.clear();
			var md = document.querySelector('#customerinfo-mdlayout')['u.MDLayout'];
			md.dGo('customerinfo_listpage');
		},
		//  重置按钮点击事件
		resetClick: function resetClick() {
			viewModel.cardData.clear();
		},
		//点击保存按钮
		onSaveClick: function() {
			onLoading();
			//表单校验
			var validate = getvalidate(app, "#customerinfo_cardpage");
			if (validate == false) {
				onCloseLoading();
				return
			};
			//点击保存时将页面表单设置为不可编辑
			viewModel.cardData.setEnable(false);
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			//获取车型列表
			var mrows = viewModel.subContactorData.getSimpleData();
			main.items = mrows;
			$.ajax({
				type: 'post',
				url: ctrlBathPath + '/saveCustomerinfo',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(main),
				success: function(data) {
					onCloseLoading();
					//提示框
					u.messageDialog({
						msg: data.msg,
						title: "提示",
						btnText: "OK"
					});
					//返回列表界面
					events.backClick();
					//重新执行页面加载方法
					getInitData();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					onCloseLoading();
					errors.error(XMLHttpRequest);
					events.backClick();
					getInitData();
				}
			})
		},
		showClick: function(id) {
			//将id行设置为选中行
			viewModel.listData.setRowSelect(id);
			//获取选中行
			var row = viewModel.listData.getCurrentRow();
			viewModel.cardData.setSimpleData(row.getSimpleData());
			// 获取子表（车辆信息、联系人信息）数据
			viewModel.itemVehicleQueryClick();
			viewModel.itemContactorQueryClick();
			viewModel.fileQuery();
			var md = document.querySelector('#customerinfo-mdlayout')['u.MDLayout'];
			md.dGo('customerinfo_viewpage');
		},

		// 联系人新增行按钮点击事件
		addrow: function() {
			viewModel.subContactorData.setEnable(true);
			viewModel.subContactorData.insertRow(0, null);
			viewModel.subContactorData.setRowSelect(0);
			
			// var r = viewModel.subContactorData.createEmptyRow();
			// viewModel.subContactorData.setRowFocus(r);
		},

		startOrStopClickBase: function(options, msg) {
			// 获取应删除当前行
			var rows = viewModel.listData.getSelectedRows();
			var postArray = [];

			// 请求后台删除该行
			if (rows == null || rows.length == 0) {
				u.messageDialog({
					msg: "请选择要" + msg + "的客户",
					title: "提示",
					btnText: "OK"
				});
				return;
			} else {
				// 过滤已经停用的数据
				for (var i = 0; i < rows.length; i++) {
					if (comToName(rows[i].getSimpleData().vstatus) != msg) {
						postArray.push(rows[i].getSimpleData());
					}
				}

				$.ajax({
					type: 'post',
					url: ctrlBathPath + options,
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(postArray),
					success: function success(data) {
						
						u.messageDialog({
							msg: msg + "成功",
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
		mergeClick: function() {
			// 获取应删除当前行
			var rows = viewModel.listData.getSelectedRows();
			var mergeArray = [];
			if (rows == null || rows.length == 0) {
				u.messageDialog({
					msg: "请选择要合并的客户",
					title: "提示",
					btnText: "OK"
				});
				return;
			} else if(rows.length < 2) {
				u.messageDialog({
					msg: "请合并2个以上客户",
					title: "提示",
					btnText: "OK"
				});
				return;
			} 
			else {
				for(var i = 0; i < rows.length; i++) {
					mergeArray.push(rows[i].getSimpleData());
				}

				viewModel.mergeData.setSimpleData(mergeArray , {
						unSelect: true
					});

				var md = document.querySelector('#customerinfo-mdlayout')['u.MDLayout'];
				md.dGo('customerinfo_mergepage');
			}
		},
		onMergeClick: function() {
			var mainVo = {};
			var nonSelect = [];
			var rows = viewModel.mergeData.getSelectedRows();

			if(rows == null || rows.length == 0) {
				u.messageDialog({
					msg: "请选择要保留的客户",
					title: "提示",
					btnText: "OK"
				});
			} else {
				// TODO 单选实现
				var AllRows = viewModel.mergeData.getAllRows();
				for(var j = 0; j < AllRows.length; j++) {
					if(AllRows[j] != rows[0]) {
						nonSelect.push(AllRows[j].getSimpleData());
					}
				}
				mainVo.main = rows[0].getSimpleData();
				mainVo.sub = nonSelect;

				$.ajax({
					type: 'post',
					url: ctrlBathPath + '/merge',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(mainVo),
					success: function success(data) {
						
						u.messageDialog({
							msg: data.msg,
							title: "提示",
							btnText: "OK"
						});
						viewModel.backClick();
						events.searchClick();
					},
					error: function error(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				});
			}
		},
		startClick: function() {
			events.startOrStopClickBase('/start', '启用');
		},
		stopClick: function() {
			events.startOrStopClickBase('/stop', '停用');
		},
		// 城市信息随省份联动
		searchFilerData: function() {
			var row = viewModel.cardData.getCurrentRow();
			if (row != null) {
				var pkprovince = row.getValue("pk_province");

				if (pkprovince != null && pkprovince != "") {
					iReferComp.setFilterPks("province", pkprovince, '#customerinfo_city2');
				} else {
					iReferComp.setFilterPks("province", "1", '#customerinfo_city2');
				}
			}
		},
		// 城市信息随省份联动
		searchFilerCity: function() {
			var row = viewModel.searchData.getCurrentRow();
			if (row != null) {
				var pkprovince = row.getValue("pk_province");

				if (pkprovince != null && pkprovince != "") {
					iReferComp.setFilterPks("province", pkprovince, '#customerinfo-city1');
				} else {
					iReferComp.setFilterPks("province", "1", '#customerinfo-city1');
				}
			}
		},

		// 客户信息修改页面 - 删除子表按钮点击事件（批量）
		deleteItemsClick: function() {
			var dats = [];
			// 获取应删除当前行
			var rows = viewModel.subContactorData.getSelectedRows();
			// 请求后台删除该行
			if (rows == null || rows.length == 0) {
				u.messageDialog({
					msg: "请选择要删除的联系人信息",
					title: "提示",
					btnText: "OK"
				});
				return;
			} else {
				// 数据没有在后台保存时， 直接删除
				if (!rows[0].getValue('pk_customerinfo')) {
					u.confirmDialog({
						title: "确认",
						msg: "新增联系人未保存，确认删除？",
						onOk: function() {
							viewModel.subContactorData.removeRow(rows[0]);
							viewModel.pageCheckFunc(viewModel.subContactorData, viewModel.itemContactorQueryClick);
								
						},

					});

					return;
				}

				// 数据有在后台保存时， 请求后台删除
				u.confirmDialog({
					title: "确认",
					msg: "确认删除？",
					onOk: function() {

						for (var i = 0; i < rows.length; i++) {
							if (rows[i].getSimpleData().pk_customerinfo) {
								dats.push(rows[i].getSimpleData());
							}
						}
						$.ajax({
							type: 'post',
							url: ctrlBathPath + '/deleteContactors',
							dataType: 'json',
							contentType: "application/json ; charset=utf-8",
							data: JSON.stringify(dats),
							success: function success(data) {
								u.messageDialog({
									msg: data.msg,
									title: "提示",
									btnText: "OK"
								});
								viewModel.subContactorData.removeRows(rows);
								viewModel.pageCheckFunc(viewModel.subContactorData, viewModel.itemContactorQueryClick);
								
							},
							error: function error(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						});

					},

				});


			}
		},
		// 检查当页是否没有数据, 无则返回上一页数据
		pageCheckFunc: function(tableData, queryFunc) {
				var currentPageRows = tableData.getAllRows();
				if(currentPageRows.length == 0) {
					var currentPageIndex = tableData.pageIndex();
					if(currentPageIndex > 0) {
						tableData.pageIndex( currentPageIndex - 1);
					}
					queryFunc();
				}
		},

		// 	修改按钮点击事件
		updateClick: function() {
			viewModel.cardData.setEnable(true);

			//获取选中行
			var rows = viewModel.listData.getSelectedRows();

			if (rows != null && rows.length == 1) {
				var mainData = rows[0].data;

				if (comToName(mainData.vstatus.value) == '停用') {
					u.messageDialog({
						msg: "已停用客户不能进行修改，请启用后再修改！",
						title: "提示",
						btnText: "OK"
					});
				} else {
					viewModel.cardData.clear();
					var r = viewModel.cardData.createEmptyRow();
					r.setSimpleData(mainData);
					var md = document.querySelector('#customerinfo-mdlayout')['u.MDLayout'];
					md.dGo('customerinfo_cardpage');

					viewModel.itemVehicleQueryClick();
					viewModel.itemContactorQueryClick();
					viewModel.fileQuery();
				}
			} else if (null != rows && rows.length > 1) {
				u.messageDialog({
					msg: "每次只能选择一个客户信息进行修改，请重新选择！",
					title: "提示",
					btnText: "OK"
				});
			} else {
				u.messageDialog({
					msg: "请选择一个客户信息，再进行修改！",
					title: "提示",
					btnText: "OK"
				});
			}

		},
		// 车辆信息子表查询事件
		itemVehicleQueryClick: function() {
			var row = viewModel.cardData.getCurrentRow();
			// 设置子表外键 - 客户信息
			var queryData = {};
			var pk = row.getValue("pk_customerinfo");
			queryData["pk"] = pk;
			queryData["itempage"] = viewModel.subVehicleData.pageIndex();
			queryData["itempage.size"] = viewModel.subVehicleData.pageSize();
			$.ajax({
				type: 'get',
				url: ctrlBathPath + "/queryVehicle",
				data: queryData,
				success: function(data) {

					viewModel.subVehicleData.setSimpleData(data.itemList.content, {
						unSelect: true
					});
					viewModel.subVehicleData.totalPages(data.itemList.totalPages);
					viewModel.subVehicleData.totalRow(data.itemList.totalElements);
					viewModel.subVehicleData.setRowUnFocus();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},

		// 联系人信息子表查询事件
		itemContactorQueryClick: function() {
			var row = viewModel.cardData.getCurrentRow();
			// 设置子表外键 - 客户信息
			var queryData = {};
			var pk = row.getValue("pk_customerinfo");
			queryData["pk"] = pk;
			queryData["itempage"] = viewModel.subContactorData.pageIndex();
			queryData["itempage.size"] = viewModel.subContactorData.pageSize();
			$.ajax({
				type: 'get',
				url: ctrlBathPath + "/queryContact",
				data: queryData,
				success: function(data) {

					viewModel.subContactorData.setSimpleData(data.itemList.content, {
						unSelect: true
					});
					viewModel.subContactorData.totalPages(data.itemList.totalPages);
					viewModel.subContactorData.totalRow(data.itemList.totalElements);
					viewModel.subContactorData.setRowUnFocus();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},

		//打开附件上传界面
		onOpenUploadWin: function() {
			window.customerinfo_file_md = u.dialog({
				id: 'customerinfo_dialog_upload',
				content: "#customerinfo_dialog_uploadfile",
				hasCloseMenu: true
			});
			$('.sub-list1-new').css('display', 'inline-block');
		},
		// 关闭上传附件界面
		onCloseFileWindow: function() {
			customerinfo_file_md.close();
		},

		//上传附件
		onFileUpload: function() {
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_customerinfo;
			var par = {
				fileElementId: "customerinfo_uploadbatch_id", //【必填】文件上传空间的id属性  <input type="file" id="id_file" name="file" />,可以修改，主要看你使用的 id是什么 
				filepath: pk, //【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
				groupname: "CUSTOMERINFO", //【必填】分組名称,未来会提供树节点
				permission: "private", //【选填】 read是可读=公有     private=私有     当这个参数不传的时候会默认private
				url: true, //【选填】是否返回附件的连接地址，并且会存储到数据库
				//thumbnail :  "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
				cross_url: window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
			}
			var f = new interface_file();
			f.filesystem_upload(par, viewModel.fileUploadCallback);
			onLoading();
		},
		//上传文件回传信息
		fileUploadCallback: function(data) {
			onCloseLoading();
			if (1 == data.status) { //上传成功状态
				viewModel.fileData.addSimpleData(data.data);
				customerinfo_file_md.hide();
				u.messageDialog({
					msg: "上传成功！",
					title: "提示",
					btnText: "OK"
				});

			} else { //error 或者加載js錯誤
				u.messageDialog({
					msg: "上传失败！" + data.message,
					title: "提示",
					btnText: "OK"
				});
			}
			// viewModel.files(viewModel.fileData.getAllDatas().length == 0);
		},
		fileQuery: function() {
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			if (null == row) {
				row = viewModel.listData.getSelectedRows()[0];
			}
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_customerinfo;
			var par = {
				//建议一定要有条件否则会返回所有值
				filepath: pk, //【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
				groupname: "CUSTOMERINFO", //【选填】[分組名称,未来会提供树节点]
				cross_url: window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
			}
			var f = new interface_file();
			f.filesystem_query(par, viewModel.fileQueryCallBack);
		},
		fileQueryCallBack: function(data) {
			if (1 == data.status) { //上传成功状态
				viewModel.fileData.setSimpleData(data.data, {
						unSelect: true
					});
			} else {
				//没有查询到数据，可以不用提醒	
				if ("没有查询到相关数据" != data.message) {
					u.messageDialog({
						msg: "查询失败" + data.message,
						title: "提示",
						btnText: "OK"
					});
				} else {
					viewModel.fileData.clear();
				}
			}
			
		},
		//附件删除
		fileDelete: function() {
			var row = viewModel.fileData.getSelectedRows();
			if (row == null || row.length == 0) {
				u.messageDialog({
					msg: "请选择要删除的附件",
					title: "提示",
					btnText: "OK"
				});
				return
			} else if (row.length > 1) {
				u.messageDialog({
					msg: "每次只能删除一个附件",
					title: "提示",
					btnText: "OK"
				});
				return
			}
			for (var i = 0; i < row.length; i++) {
				var pk = row[i].getValue("id");
				var par = {
					id: pk, //【必填】表的id
					cross_url: window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
				}
				var f = new interface_file();
				f.filesystem_delete(par, viewModel.fileDeleteCallBack);
			}
		},
		//附件删除回调
		fileDeleteCallBack: function(data) {
			if (1 == data.status) { //上传成功状态
				viewModel.fileQuery();
			} else {
				u.messageDialog({
					msg: "删除失败" + data.message,
					title: "提示",
					btnText: "OK"
				});
			}
			// viewModel.files(viewModel.fileData.getAllDatas().length == 0);
		},
		// 下载附件
		fileDownload: function() {
			var row = viewModel.fileData.getSelectedRows();
			if (row == null || row.length == 0) {
				u.messageDialog({
					msg: "请选择要下载的附件",
					title: "提示",
					btnText: "OK"
				});
				return
			} else if (row.length > 1) {
				u.messageDialog({
					msg: "每次只能下载一个附件",
					title: "提示",
					btnText: "OK"
				});
				return
			}
			for (var i = 0; i < row.length; i++) {
				var pk = row[i].getValue("id");
				var form = $("<form>"); //定义一个form表单
				form.attr('style', 'display:none'); //在form表单中添加查询参数
				form.attr('target', '');
				form.attr('enctype', 'multipart/form-data');
				form.attr('method', 'post');
				form.attr('action', window.ctxfilemng + "file/download?permission=private&id=" + pk);
				$('#customerinfo-mdlayout').append(form); //将表单放置在web中 
				form.submit();
			}
		},
		// 查看附件
		fileView: function() {
			var row = viewModel.fileData.getSelectedRows();
			if (row == null || row.length == 0) {
				u.messageDialog({
					msg: "请选择要查看的附件",
					title: "提示",
					btnText: "OK"
				});
				return
			} else if (row.length > 1) {
				u.messageDialog({
					msg: "每次只能查看一个附件",
					title: "提示",
					btnText: "OK"
				});
				return
			}
			for (var i = 0; i < row.length; i++) {
				var url = row[i].getValue("url");
				parent.open("http://" + url);
			}
		}

	};


	//viewModel = u.extend(enums, basicDatas, events);
	viewModel = u.extend(basicDatas, events, enums);

	// 初始化列表数据 或 刷新列表数据 - 客户信息信息
	var getInitData = function getInitData() {
		getInitBtn();
		viewModel.listData.clear();
		viewModel.cardData.clear();
		viewModel.searchData.clear();

		// 初始化搜索栏
		viewModel.searchData.createEmptyRow();

		// 初始化页签数据： 页大小默认为10， 页码默认为0
		viewModel.listData.pageSize(defaultPageSize);
		viewModel.listData.pageIndex(0);
		viewModel.subVehicleData.pageSize(defaultPageSize);
		viewModel.subVehicleData.pageIndex(0);
		viewModel.subContactorData.pageSize(defaultPageSize);
		viewModel.subContactorData.pageIndex(0);

		events.searchClick();

	};

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
						if ("修改" == funTemp) {
							viewModel.updateFunShow(true);
						} else if ("合并" == funTemp) {
							viewModel.mergeFunShow(true);
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

			ref();
		}
	};
});