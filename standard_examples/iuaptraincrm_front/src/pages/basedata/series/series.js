define(
		[ 'iReferComp', 'refComp', 'error', 'text!./series.html', 'uuigrid',
				'utilrow', 'ajaxfileupload', 'ossupload', 'interfaceFile',
				'interfaceFileImpl' ],
		function(iReferComp, refComp, errors, html) {
			'use strict';
			var refid;
			var dom;
			var editoradd = '';
			var app, viewModel, basicDatas, events, fileEvents, fileInEvents, fileThumbEvents, oper, enums, pagesize = 10, pageIndex = 0;
			// 初始化参照
			function ref() {
				var pk = '';
				// 所属类别-查询条件
				$('#series_vehicleclassifylist').each(function(i, val) {
					var $that = $(this);
					dom = $that;
					var options = {
						refCode : "vehicleclassifyref",
						selectedVals : pk,
						isMultiSelectedEnabled : false
					};
					refComp.initRefComp($that, options);
					refid = '#refContainer' + $that.attr('id');
				});
				// 所属类别-查询条件（新增修改）
				$('#series_vehicleclassifycar').each(function(i, val) {// 新增修改参照
					var $that = $(this);
					dom = $that;
					var options = {
						refCode : "vehicleclassifyref",
						selectedVals : pk,
						isMultiSelectedEnabled : false
					};
					refComp.initRefComp($that, options);
					refid = '#refContainer' + $that.attr('id');
				});
				// 所属品牌-查询条件
				$('#series_brandlist').each(function(i, val) {
					var $that = $(this);
					dom = $that;
					var options = {
						refCode : "brand",
						selectedVals : pk,
						isMultiSelectedEnabled : false
					};
					refComp.initRefComp($that, options);
					refid = '#refContainer' + $that.attr('id');
				});
			}

			// 初始化状态枚举
			/*
			 * function initenums() { $.ajax({ type : 'get', url : window.cturl +
			 * "/bd/enums/queryByVtype/" + 1005, contentType : "application/json ;
			 * charset=utf-8", success : function(data) {
			 * $('#vstatus')[0]['u.Combo'].setComboData(data); }, error :
			 * function(XMLHttpRequest, textStatus, errorThrown) {
			 * errors.error(XMLHttpRequest); } }) }
			 */

			basicDatas = {
				// 查询用
				searchData : new u.DataTable({
					meta : {
						vseriescode : {// 车系编码
							type : 'string'
						},
						vseriesname : {// 车系名称
							type : 'string'
						},
						pk_vehicleclassify : {// 所属类别
							type : 'string'
						},
						pk_brand : {// 所属品牌
							type : 'string'
						},
						vstatus : {// 状态
							type : 'string'
						}
					}
				}),
				// 列表界面
				listData : new u.DataTable({
					meta : {
						pk_series : {// 车系主键
							type : 'string'
						},
						vseriescode : {// 车系编码
							type : 'string',
						},
						vseriesname : {// 车系名称
							type : 'string',
						},
						vcomments : {// 车系描述
							type : 'string',
						},
						vehiclename : {// 所属类别
							type : 'string'
						},
						brandname : {// 所属品牌
							type : 'string'
						},
						vstatus : {// 状态
							type : 'string'
						}
					}
				}),
				// 卡片界面(新增，修改)
				cardData : new u.DataTable({
					meta : {
						pk_series : {// 车系主键
							type : 'string'
						},
						vseriescode : {// 车系编码
							type : 'string',
							required : true,
							nullMsg : '车系编码不能为空！',
							maxLength : 50,
							lengthMsg : '车系编码长度不能大于50！'
						},
						vseriesname : {// 车系名称
							type : 'string',
							required : true,
							nullMsg : '车系名称不能为空！',
							maxLength : 100,
							lengthMsg : '车系名称长度不能大于100！'
						},
						vcomments : {// 车系描述
							type : 'string',
							maxLength : 500,
							lengthMsg : '车系描述长度不能大于500！'
						},
						pk_vehicleclassify : {// 所属类别
							type : 'string',
							required : true,
							nullMsg : '所属类别不能为空！',
							maxLength : 100
						}
					}
				}),
				// 附件信息(车型外观图)
				fileData : new u.DataTable({
					meta : {
						id : {
							type : 'string'
						},// 主键
						filepath : {
							type : 'string'
						},// 文件名称
						filesize : {
							type : 'string'
						},// 文件大小
						filename : {
							type : 'string'
						},// 文件名称
						uploadtime : {
							type : 'string'
						},// 上传时间
						groupname : {
							type : 'string'
						},//
						url : {
							type : 'string'
						}
					// URL
					}
				}),
				// 附件信息(车型内饰图)
				fileInData : new u.DataTable({
					meta : {
						id : {
							type : 'string'
						},// 主键
						filepath : {
							type : 'string'
						},// 文件名称
						filesize : {
							type : 'string'
						},// 文件大小
						filename : {
							type : 'string'
						},// 文件名称
						uploadtime : {
							type : 'string'
						},// 上传时间
						groupname : {
							type : 'string'
						},//
						url : {
							type : 'string'
						}
					// URL
					}
				}),
				// 附件信息(缩略图)
				fileThumbData : new u.DataTable({
					meta : {
						id : {
							type : 'string'
						},// 主键
						filepath : {
							type : 'string'
						},// 文件名称
						filesize : {
							type : 'string'
						},// 文件大小
						filename : {
							type : 'string'
						},// 文件名称
						uploadtime : {
							type : 'string'
						},// 上传时间
						groupname : {
							type : 'string'
						},//
						url : {
							type : 'string'
						}
					// URL
					}
				}),
				//gridStatus: ko.observable("view"),
				//新增
				addFunShow : ko.observable(false),
				//修改
				editFunShow : ko.observable(false),
				//启用
				startFunShow : ko.observable(false),
				//停用
				stopFunShow : ko.observable(false),
				//导出
				exportFunShow : ko.observable(false),
			};
			enums = {
				// 状态
				vstatusData : enumerate(1005)
			};

			events = {
				queryMain : function() {
					// 获取选中行
					var rows = viewModel.searchData.getSelectedRows();
					// 获取查询数据
					var param = rows[0].getSimpleData();
					param.psize = viewModel.listData.pageSize();
					param.pindex = viewModel.listData.pageIndex();
					$.ajax({
						type : 'post',
						url : window.cturl + "/db/series/querypage1",
						contentType : "application/json ; charset=utf-8",
						data : JSON.stringify(param),// 将json对象数据转化为字符串
						success : function(data) {
							// viewModel.listData.pageSize(pagesize);// 每页显示多少条
							viewModel.listData.setSimpleData(data.content, {
								unSelect : true
							});
							viewModel.listData.totalPages(data.totalPages);
							viewModel.listData.totalRow(data.totalElements);
							viewModel.listData.setRowUnFocus();
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				},
				// 查询按钮点击事件
				searchClick : function searchClick(model, event) {
					viewModel.listData.pageIndex(0);
					events.queryMain();
				},
				// 页大小改变事件
				sizeChangeFunc : function(size, pageIndex) {
					viewModel.listData.pageSize(size);
					viewModel.listData.pageIndex(0);
					viewModel.queryMain();
				},
				// 页数跳转事件
				pageChangeFunc : function(pageIndex) {
					viewModel.listData.pageIndex(pageIndex);
					viewModel.queryMain();
				},
				// 查看
				showClick : function(id) {
					viewModel.listData.setRowSelect(id);
					var row = viewModel.listData.getCurrentRow();
					if (row == null) {
						u.messageDialog({
							msg : "请选择一条要查看的数据",
							title : "提示",
							btnText : "OK"
						});
						return

					}
					viewModel.cardData.setEnable(false);
					viewModel.cardData.setSimpleData(row.getSimpleData());
					// 查询上传的文件
					viewModel.fileQuery();
					viewModel.fileQuery2();
					viewModel.fileQuery3();
					var series_md = document.querySelector('#series-mdlayout')['u.MDLayout'];
					series_md.dGo('series_view');
				},
				// 点击新增按钮
				addClick : function() {
					// 设置为可编辑
					viewModel.cardData.setEnable(true);
					// 清除卡片界面数据
					viewModel.cardData.clear();
					// 创建空行
					var r = viewModel.cardData.createEmptyRow();
					var series_md = document.querySelector('#series-mdlayout')['u.MDLayout'];
					series_md.dGo('series_new');
				},
				// 点击修改按钮
				editClick : function(id) {
					// 设置为可编辑
					viewModel.cardData.setEnable(true);
					// 获取选中行
					var rows = viewModel.listData.getSelectedRows();
					if (null != rows && rows.length == 1) {// 判断当选中行数为一的时候
						var mainData = rows[0].data;
						viewModel.cardData.setSimpleData(mainData);
						viewModel.fileQuery();
						viewModel.fileQuery2();
						viewModel.fileQuery3();
						var series_md = document
								.querySelector('#series-mdlayout')['u.MDLayout'];
						series_md.dGo('series_new');
					} else if (null != rows && rows.length > 1) {// 判断当选中行数大于一的时候
						u.messageDialog({
							msg : "每次只能选择一条单据进行修改，请重新选择！",
							title : "提示",
							btnText : "OK"
						});
					} else {
						u.messageDialog({// 判断当选中行数大于等于一的时候
							msg : "请选择一条单据，再进行修改！",
							title : "提示",
							btnText : "OK"
						});
					}

				},
				// 启用按钮点击事件
				startClick : function() {
					// 获取当前行
					var rows = viewModel.listData.getSelectedRows();
					if (rows == null || rows.length == 0) {// 判断当选中行数为0的时候
						u.messageDialog({
							msg : "请选择要启用的车系",
							title : "提示",
							btnText : "OK"
						});
						return;
					} else if (null != rows && rows.length > 1) {// 判断当选中行数大于一的时候
						u.messageDialog({
							msg : "每次只能选择一条车系进行状态改变，请重新选择！",
							title : "提示",
							btnText : "OK"
						});
						return;
					} else {
						// 校验是否已启用
						if (rows[0].getSimpleData().vstatus == '10051001') {
							u.messageDialog({
								msg : "已启用",
								title : "提示",
								btnText : "OK"
							});
							return;
						}
						$.ajax({
							url : window.cturl + '/db/series/start',
							type : 'post',
							dataType : 'json',
							contentType : "application/json ; charset=utf-8",
							data : JSON.stringify(rows[0].getSimpleData()),
							success : function success(data) {
								u.messageDialog({
									msg : "启用成功",
									title : "提示",
									btnText : "OK"
								});
								getInitData();
							},
							error : function error(XMLHttpRequest, textStatus,
									errorThrown) {
								errors.error(XMLHttpRequest);
							}
						});
					}
				},
				// 停用按钮点击事件
				stopClick : function() {
					// 获取应删除当前行
					var rows = viewModel.listData.getSelectedRows();
					// 请求后台删除该行
					if (rows == null || rows.length == 0) {
						u.messageDialog({
							msg : "请选择要启用的品牌",
							title : "提示",
							btnText : "OK"
						});
						return;
					} else if (null != rows && rows.length > 1) {// 判断当选中行数大于一的时候
						u.messageDialog({
							msg : "每次只能选择一条车系进行状态改变，请重新选择！",
							title : "提示",
							btnText : "OK"
						});
						return;
					} else {
						// 校验是否已停用
						if (rows[0].getSimpleData().vstatus == '10051002') {
							u.messageDialog({
								msg : "已停用",
								title : "提示",
								btnText : "OK"
							});
							return;
						}
						$.ajax({
							url : window.cturl + '/db/series/stop',
							type : 'post',
							dataType : 'json',
							contentType : "application/json ; charset=utf-8",
							data : JSON.stringify(rows[0].getSimpleData()),
							success : function success(data) {
								u.messageDialog({
									msg : "停用成功",
									title : "提示",
									btnText : "OK"
								});
								getInitData();
							},
							error : function error(XMLHttpRequest, textStatus,
									errorThrown) {
								errors.error(XMLHttpRequest);
							}
						});
					}
				},
				// 车系信息导出
				exportClick : function() {
					var ctrlBathPath = 'iuaptraincrm/db/series';
					var dats = [];
					var pks = "";
					var row = viewModel.listData.getSelectedRows();
					var form = $("<form>"); // 定义一个form表单
					form.attr('style', 'display:none'); // 在form表单中添加查询参数
					form.attr('target', '');
					form.attr('method', 'post');
					form.attr('action', ctrlBathPath + "/seriesDownload");
					var input1 = $('<input>');
					input1.attr('type', 'hidden');
					input1.attr('name', 'pkSeriesIds');
					input1.attr('value', pks);
					$('#series-mdlayout').append(form); // 将表单放置在web中
					form.append(input1); // 将查询参数控件提交到表单上
					form.submit();
				},
				// 点击返回按钮
				onBackClick : function() {
					viewModel.cardData.clear();
					var series_md = document.querySelector('#series-mdlayout')['u.MDLayout'];
					series_md.dBack();
				},

				// 点击保存按钮
				onSaveClick : function() {
					// 判断是否必填
					var validate = getvalidate(app, "#series_new");
					if (validate == false) {
						return;
					}
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					onLoading();
					// 调用后台保存方法
					$
							.ajax({
								url : window.cturl + '/db/series/save',
								type : 'POST',
								dataType : 'json',
								contentType : "application/json ; charset=utf-8",
								data : JSON.stringify(row.getSimpleData()),
								success : function(data) {
									// 成功后将页面选中行移除
									viewModel.listData.removeRow(row);
									onCloseLoading();
									// 成功后页面提示
									u.messageDialog({
										msg : "保存成功！",
										title : "提示",
										btnText : "OK"
									});
									// 返回列表界面
									var md = document
											.querySelector('#series-mdlayout')['u.MDLayout'];
									// 返回
									md.dBack();
									// 重新执行页面加载方法，先清除查询数据
									viewModel.searchData.clear();
									getInitData(10, 0);
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									onCloseLoading();
									errors.error(XMLHttpRequest);
								}
							})
				},
				afterAdd : function(element, index, data) {
					if (element.nodeType === 1) {
						u.compMgr.updateComp(element);
					}
				},
				// 当车辆类别输入框值改变
				modelChange : function(obj) {
					if (obj.field != "pk_vehicleclassify")
						return;
					$.ajax({
						type : 'GET',
						url : window.cturl + "/db/series/getIntRefData?pks="
								+ "'" + obj.newValue + "'",
						dataType : 'json',
						contentType : "application/json ; charset=utf-8",
						success : function success(data) {
							if (data.length > 0) {
								viewModel.cardData.setValue("brandname",
										data[0].brand);
							} else {
								viewModel.cardData.setValue("brandname", "");
							}
						},
						error : function error(XMLHttpRequest, textStatus,
								errorThrown) {
							errors.error(XMLHttpRequest);
						}
					});
				}
			/*
			 * // 页码改变 pageChangeFunc : function(pageIndex) { var pagesize =
			 * viewModel.listData.pageSize(); var rows =
			 * viewModel.searchData.getSelectedRows() var param =
			 * rows[0].getSimpleData(); param.pagesize = pagesize;
			 * param.pageIndex = pageIndex; $.ajax({ type : 'post', url :
			 * window.cturl + "/db/series/querypage1", contentType :
			 * "application/json ; charset=utf-8", data : JSON.stringify(param),
			 * success : function(data) {
			 * viewModel.listData.setSimpleData(data.content);
			 * viewModel.listData.totalPages(data.totalPages); // 共多少页
			 * viewModel.listData.totalRow(data.totalElements);
			 * viewModel.listData.setAllRowsUnSelect(); }, error :
			 * function(XMLHttpRequest, textStatus, errorThrown) {
			 * errors.error(XMLHttpRequest); } }) },
			 */
			/*
			 * // 每页显示条数改变 sizeChangeFunc : function(page) { var flag =
			 * u.isArray(page); if (flag) { getInitData(page[0], page[1]); }
			 * else { var rows = viewModel.searchData.getSelectedRows() var
			 * param = rows[0].getSimpleData(); param.pagesize = page;
			 * param.pageIndex = pageIndex; $ .ajax({ type : 'post', url :
			 * window.cturl + "/db/series/querypage1", contentType :
			 * "application/json ; charset=utf-8", data : JSON.stringify(param),
			 * success : function(data) {
			 * viewModel.listData.pageSize(pagesize);// 每页显示多少条
			 * viewModel.listData .setSimpleData(data.content);
			 * viewModel.listData .totalPages(data.totalPages);
			 * viewModel.listData .totalRow(data.totalElements);
			 * viewModel.listData.setRowUnFocus(); }, error :
			 * function(XMLHttpRequest, textStatus, errorThrown) {
			 * errors.error(XMLHttpRequest); } }) } }
			 */

			}

			fileEvents = {
				// 打开附件上传界面
				onOpenUploadWin : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					if (pk == null || pk == "") {
						u.messageDialog({
							msg : "请先保存车系！",
							title : "提示",
							btnText : "OK"
						});
						return;
					}
					window.series_md = u.dialog({
						id : 'series_testDialg3',
						content : "#series_dialog_uploadfile",
						hasCloseMenu : true
					});
					$('.sub-list1-new').css('display', 'inline-block');
				},
				// 上传附件
				onFileUpload : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					var par = {
						fileElementId : "series_uploadbatch_id", // 【必填】文件上传空间的id属性
						// <input
						// type="file"
						// id="id_file"
						// name="file"
						// />,可以修改，主要看你使用的
						// id是什么
						filepath : pk, // 【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
						groupname : "tmseries-out",// 【必填】分組名称,未来会提供树节点
						permission : "read", // 【选填】 read是可读=公有 private=私有
						// 当这个参数不传的时候会默认private
						url : true, // 【选填】是否返回附件的连接地址，并且会存储到数据库
						// thumbnail :
						// "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
						cross_url : window.ctxfilemng
					// 【选填】跨iuap-saas-fileservice-base 时候必填
					}
					var f = new interface_file();
					f.filesystem_upload(par, viewModel.fileUploadCallback);
					onLoading();
				},
				// 上传文件回传信息
				fileUploadCallback : function(data) {
					onCloseLoading();
					if (1 == data.status) {// 上传成功状态
						viewModel.fileData.addSimpleData(data.data);
						u.messageDialog({
							msg : "上传成功！",
							title : "提示",
							btnText : "OK"
						});
					} else {// error 或者加載js錯誤
						u.messageDialog({
							msg : "上传失败！" + data.message,
							title : "提示",
							btnText : "OK"
						});
					}
				},
				fileQuery : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					var par = {
						// 建议一定要有条件否则会返回所有值
						filepath : pk, // 【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
						groupname : "tmseries-out",// 【选填】[分組名称,未来会提供树节点]
						cross_url : window.ctxfilemng
					// 【选填】跨iuap-saas-fileservice-base 时候必填
					}
					var f = new interface_file();
					f.filesystem_query(par, viewModel.fileQueryCallBack);
				},
				fileQueryCallBack : function(data) {
					if (1 == data.status) {// 上传成功状态
						viewModel.fileData.setSimpleData(data.data);
					} else {
						// 没有查询到数据，可以不用提醒
						if ("没有查询到相关数据" != data.message) {
							u.messageDialog({
								msg : "查询失败" + data.message,
								title : "提示",
								btnText : "OK"
							});
						} else {
							viewModel.fileData.removeAllRows();
						}
					}
				},
				// 附件删除
				fileDelete : function() {
					var row = viewModel.fileData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要删除的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能删除一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var pk = row[i].getValue("id");
						var par = {
							id : pk,// 【必填】表的id
							cross_url : window.ctxfilemng
						// 【选填】跨iuap-saas-fileservice-base 时候必填
						}
						var f = new interface_file();
						f.filesystem_delete(par, viewModel.fileDeleteCallBack);
					}
				},
				// 关闭上传附件界面
				onCloseFileWindow : function() {
					series_md.close();
				},
				// 附件删除回调
				fileDeleteCallBack : function(data) {
					if (1 == data.status) {// 上传成功状态
						viewModel.fileQuery();
					} else {
						u.messageDialog({
							msg : "删除失败" + data.message,
							title : "提示",
							btnText : "OK"
						});
					}
				},
				// 下载
				fileDownload : function() {
					var row = viewModel.fileData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要下载的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能下载一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var pk = row[i].getValue("id");
						var form = $("<form>"); // 定义一个form表单
						form.attr('style', 'display:none'); // 在form表单中添加查询参数
						form.attr('target', '');
						form.attr('enctype', 'multipart/form-data');
						form.attr('method', 'post');
						form.attr('action', window.ctxfilemng
								+ "file/download?permission=read&id=" + pk);
						$('#series-mdlayout').append(form); // 将表单放置在web中
						form.submit();
					}
				},
				// 查看
				fileView : function() {
					var row = viewModel.fileData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要下载的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能查看一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var url = row[i].getValue("url");
						parent.open("http://" + url);
					}
				}
			}

			fileInEvents = {
				// 打开附件上传界面
				onOpenUploadWin2 : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					if (pk == null || pk == "") {
						u.messageDialog({
							msg : "请先保存车系！",
							title : "提示",
							btnText : "OK"
						});
						return;
					}
					window.series_md = u.dialog({
						id : 'series_testDialg3',
						content : "#series_dialog_uploadfile2",
						hasCloseMenu : true
					});
					$('.sub-list1-new').css('display', 'inline-block');
				},
				// 上传附件
				onFileUpload2 : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					var par = {
						fileElementId : "series_uploadbatch_id2", // 【必填】文件上传空间的id属性
						// <input
						// type="file"
						// id="id_file"
						// name="file"
						// />,可以修改，主要看你使用的
						// id是什么
						filepath : pk, // 【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
						groupname : "tmseries-in",// 【必填】分組名称,未来会提供树节点
						permission : "read", // 【选填】 read是可读=公有 private=私有
						// 当这个参数不传的时候会默认private
						url : true, // 【选填】是否返回附件的连接地址，并且会存储到数据库
						// thumbnail :
						// "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
						cross_url : window.ctxfilemng
					// 【选填】跨iuap-saas-fileservice-base 时候必填
					}
					var f = new interface_file();
					f.filesystem_upload(par, viewModel.fileUploadCallback2);
					onLoading();
				},
				// 上传文件回传信息
				fileUploadCallback2 : function(data) {
					onCloseLoading();
					if (1 == data.status) {// 上传成功状态
						viewModel.fileInData.addSimpleData(data.data);
						u.messageDialog({
							msg : "上传成功！",
							title : "提示",
							btnText : "OK"
						});
					} else {// error 或者加載js錯誤
						u.messageDialog({
							msg : "上传失败！" + data.message,
							title : "提示",
							btnText : "OK"
						});
					}
				},
				fileQuery2 : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					var par = {
						// 建议一定要有条件否则会返回所有值
						filepath : pk, // 【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
						groupname : "tmseries-in",// 【选填】[分組名称,未来会提供树节点]
						cross_url : window.ctxfilemng
					// 【选填】跨iuap-saas-fileservice-base 时候必填
					}
					var f = new interface_file();
					f.filesystem_query(par, viewModel.fileQueryCallBack2);
				},
				fileQueryCallBack2 : function(data) {
					if (1 == data.status) {// 上传成功状态
						viewModel.fileInData.setSimpleData(data.data);
					} else {
						// 没有查询到数据，可以不用提醒
						if ("没有查询到相关数据" != data.message) {
							u.messageDialog({
								msg : "查询失败" + data.message,
								title : "提示",
								btnText : "OK"
							});
						} else {
							viewModel.fileInData.removeAllRows();
						}
					}
				},
				// 关闭上传附件界面
				onCloseFileWindow : function() {
					series_md.close();
				},
				// 附件删除
				fileDelete2 : function() {
					var row = viewModel.fileInData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要删除的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能删除一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var pk = row[i].getValue("id");
						var par = {
							id : pk,// 【必填】表的id
							cross_url : window.ctxfilemng
						// 【选填】跨iuap-saas-fileservice-base 时候必填
						}
						var f = new interface_file();
						f.filesystem_delete(par, viewModel.fileDeleteCallBack2);
					}
				},
				// 附件删除回调
				fileDeleteCallBack2 : function(data) {
					if (1 == data.status) {// 上传成功状态
						viewModel.fileQuery2();
					} else {
						u.messageDialog({
							msg : "删除失败" + data.message,
							title : "提示",
							btnText : "OK"
						});
					}
				},
				// 下载
				fileDownload2 : function() {
					var row = viewModel.fileInData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要下载的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能下载一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var pk = row[i].getValue("id");
						var form = $("<form>"); // 定义一个form表单
						form.attr('style', 'display:none'); // 在form表单中添加查询参数
						form.attr('target', '');
						form.attr('enctype', 'multipart/form-data');
						form.attr('method', 'post');
						form.attr('action', window.ctxfilemng
								+ "file/download?permission=read&id=" + pk);
						$('#series-mdlayout').append(form); // 将表单放置在web中
						form.submit();
					}
				},
				// 查看
				fileView2 : function() {
					var row = viewModel.fileInData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要下载的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能查看一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var url = row[i].getValue("url");
						parent.open("http://" + url);
					}
				}
			}

			fileThumbEvents = {
				// 打开附件上传界面
				onOpenUploadWin3 : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					if (pk == null || pk == "") {
						u.messageDialog({
							msg : "请先保存车系！",
							title : "提示",
							btnText : "OK"
						});
						return;
					}
					window.series_md = u.dialog({
						id : 'series_testDialg3',
						content : "#series_dialog_uploadfile3",
						hasCloseMenu : true
					});
					$('.sub-list1-new').css('display', 'inline-block');
				},
				// 上传附件
				onFileUpload3 : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					var par = {
						fileElementId : "series_uploadbatch_id3", // 【必填】文件上传空间的id属性
						// <input
						// type="file"
						// id="id_file"
						// name="file"
						// />,可以修改，主要看你使用的
						// id是什么
						filepath : pk, // 【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
						groupname : "tmseries-thumb",// 【必填】分組名称,未来会提供树节点
						permission : "read", // 【选填】 read是可读=公有 private=私有
						// 当这个参数不传的时候会默认private
						url : true, // 【选填】是否返回附件的连接地址，并且会存储到数据库
						// thumbnail :
						// "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
						cross_url : window.ctxfilemng
					// 【选填】跨iuap-saas-fileservice-base 时候必填
					}
					var f = new interface_file();
					f.filesystem_upload(par, viewModel.fileUploadCallback3);
					onLoading();
				},
				// 上传文件回传信息
				fileUploadCallback3 : function(data) {
					onCloseLoading();
					if (1 == data.status) {// 上传成功状态
						viewModel.fileThumbData.addSimpleData(data.data);
						u.messageDialog({
							msg : "上传成功！",
							title : "提示",
							btnText : "OK"
						});
					} else {// error 或者加載js錯誤
						u.messageDialog({
							msg : "上传失败！" + data.message,
							title : "提示",
							btnText : "OK"
						});
					}
				},
				fileQuery3 : function() {
					// 获取表单
					var row = viewModel.cardData.getCurrentRow();
					// 获取表单数据
					var main = row.getSimpleData();
					var pk = main.pk_series;
					var par = {
						// 建议一定要有条件否则会返回所有值
						filepath : pk, // 【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
						groupname : "tmseries-thumb",// 【选填】[分組名称,未来会提供树节点]
						cross_url : window.ctxfilemng
					// 【选填】跨iuap-saas-fileservice-base 时候必填
					}
					var f = new interface_file();
					f.filesystem_query(par, viewModel.fileQueryCallBack3);
				},
				fileQueryCallBack3 : function(data) {
					if (1 == data.status) {// 上传成功状态
						viewModel.fileThumbData.setSimpleData(data.data);
					} else {
						// 没有查询到数据，可以不用提醒
						if ("没有查询到相关数据" != data.message) {
							u.messageDialog({
								msg : "查询失败" + data.message,
								title : "提示",
								btnText : "OK"
							});
						} else {
							viewModel.fileThumbData.removeAllRows();
						}
					}
				},
				// 关闭上传附件界面
				onCloseFileWindow : function() {
					series_md.close();
				},
				// 附件删除
				fileDelete3 : function() {
					var row = viewModel.fileThumbData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要删除的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能删除一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var pk = row[i].getValue("id");
						var par = {
							id : pk,// 【必填】表的id
							cross_url : window.ctxfilemng
						// 【选填】跨iuap-saas-fileservice-base 时候必填
						}
						var f = new interface_file();
						f.filesystem_delete(par, viewModel.fileDeleteCallBack3);
					}
				},
				// 附件删除回调
				fileDeleteCallBack3 : function(data) {
					if (1 == data.status) {// 上传成功状态
						viewModel.fileQuery3();
					} else {
						u.messageDialog({
							msg : "删除失败" + data.message,
							title : "提示",
							btnText : "OK"
						});
					}
				},
				// 下载
				fileDownload3 : function() {
					var row = viewModel.fileThumbData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要下载的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能下载一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var pk = row[i].getValue("id");
						var form = $("<form>"); // 定义一个form表单
						form.attr('style', 'display:none'); // 在form表单中添加查询参数
						form.attr('target', '');
						form.attr('enctype', 'multipart/form-data');
						form.attr('method', 'post');
						form.attr('action', window.ctxfilemng
								+ "file/download?permission=read&id=" + pk);
						$('#series-mdlayout').append(form); // 将表单放置在web中
						form.submit();
					}
				},
				// 查看
				fileView3 : function() {
					var row = viewModel.fileThumbData.getSelectedRows();
					if (row == null || row.length == 0) {
						u.messageDialog({
							msg : "请选择要下载的附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					} else if (row.length > 1) {
						u.messageDialog({
							msg : "每次只能查看一个附件",
							title : "提示",
							btnText : "OK"
						});
						return

						

												

						

					}
					for (var i = 0; i < row.length; i++) {
						var url = row[i].getValue("url");
						parent.open("http://" + url);
					}
				}
			}

			viewModel = u.extend(basicDatas, events, fileEvents, fileInEvents,
					fileThumbEvents, enums)
			// 初始化页面
			var getInitData = function(pagesize, pageIndex) {
				//按钮权限
				var queryFunData = {};
		        $.ajax({
					type: 'get',
					url: window.cturl+"/security/authBtn/auth?funcCode=F020203",
					success: function(data) {
						var funList = data;
						if(null != funList){
							for(var i=0;i<funList.length;i++){
								var funTemp = funList[i];
								if("新增"==funTemp){
									viewModel.addFunShow(true);
								}else if("修改"==funTemp){
									viewModel.editFunShow(true);
								}else if("启用"==funTemp){
									viewModel.startFunShow(true);
								}else if("导出"==funTemp){
									viewModel.exportFunShow(true);
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
				viewModel.searchData.createEmptyRow();// 创建一个空行
				viewModel.searchClick();// 执行查询
				// initenums();// 初始化枚举
				viewModel.cardData.on("valuechange", events.modelChange);// 值改变初始化
			}

			return {
				init : function(content, tabid) {
					content.innerHTML = html;
					window.vm = viewModel;
					app = u.createApp({
						el : '#' + tabid,
						model : viewModel
					})
					getInitData(pagesize, pageIndex);// 初始化页面
					ref();// 执行初始化参照方法
					viewModel.listData.setEnable(false);// 设置为不可编辑
				}
			}
		});