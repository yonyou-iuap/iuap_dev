'use strict';
define(['iReferComp', 'refComp', 'error', 'text!./competbrand.html', 'uuigrid', 'utilrow','ajaxfileupload','ossupload','interfaceFile','interfaceFileImpl'], function(iReferComp, refComp, errors, html) {
	'use strict';
	var refid;
	var dom;
	//参照
	function ref() {
		//车型
		var pk = '';
		$('#competbrand_byd_model').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode: "model",
				selectedVals: pk,
				isMultiSelectedEnabled: false
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
		//城市
		$('#competbrand_search_city').each(function(i, val) {
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
		
		$('#competbrand_search_combrand').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode: "combrand",
				selectedVals: pk,
				isMultiSelectedEnabled: false
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
		$('#competbrand_edit_combrand').each(function(i, val) {
			var $that = $(this);
			dom = $that;
			var options = {
				refCode: "combrand",
				selectedVals: pk,
				isMultiSelectedEnabled: false
			};
			refComp.initRefComp($that, options);
			refid = '#refContainer' + $that.attr('id');
		});
//		//电量
//		$('#byd_battery').each(function(i, val) {
//			var $that = $(this);
//			dom = $that;
//			var options = {
//				refCode: "battery",
//				selectedVals: pk,
//				isMultiSelectedEnabled: false
//			};
//			refComp.initRefComp($that, options);
//			refid = '#refContainer' + $that.attr('id');
//		});
	}
	var app, viewModel, dialogEvents, pagesize = 10;
	var basicDatas = {
			searchData: new u.DataTable({
				meta: {
					vlengthMin: { type: 'string'}, //车长
					vlengthMax: { type: 'string'},//车长
					vcompetbrand: { type: 'string'},//品牌
					vmodelname: { type: 'string'},//车型名
					vcity: { type: 'string'},//城市pk
					vstatus: { type: 'string'},//状态
					isShipped: { type: 'string'}//是否投放市场
				}
			}),
			//附件信息
			fileData: new u.DataTable({
				meta: {
					id: {type: 'string'},//主键
					filepath: {type: 'string'},//文件名称
					filesize: {type: 'string'},//文件大小
					filename: {type: 'string'},//文件名称
					uploadtime: {type: 'string'},//上传时间
					groupname: {type: 'string'},//
					url: {type: 'string'}//URL
				}
			}),
			//竞品信息
			listData: new u.DataTable({
				meta:{
					vcompetbrand:{type:"string"},
					vmodelname:{type:"string"},
					vnoticenum:{type:"string"},
					vlength:{type:"string"},
					vwidth:{type:"string"},
					ts:{type:"string"},
					ncurbweight:{type:"string"},
					nmaxweight:{type:"string"},
					npasenger:{type:"string"},
					vmotorpower:{type:"string"},
					vbatteryclass:{type:"string"},
					vstatus:{type:"string"}
				}
			}),
			compareListData: new u.DataTable(),
			//对比车型卡片
			compareCardData: new u.DataTable({
				meta:{
					pk_competbrand: {type: 'string'},//竞品品牌外键
					pk_model: {type: 'string', required: true},//车型主键
					nvpower: {type: 'string', required: true}//电池电量
				}
			}),
			cardData: new u.DataTable({
				meta: {
					pk_competbrand:{type:'string'},//主键
					vcompetmodel: {type: 'string'},//车型编码，自动生成
					vmodelname: {type: 'string', required: true,maxLength:100},//车型名称
					vcompetbrand: {type: 'string', required: true},//品牌
					vnoticenum: {type: 'string', maxLength:500},//公告批次
					vheight: {type: 'string', required: true,regExp:/^[0-9]+$/,errorMsg:"必须填写数字",maxLength:11}, //长（mm）
					vwidth: {type: 'string', required: true,regExp:/^[0-9]+$/,errorMsg:"必须填写数字",maxLength:11}, //宽（mm）
					vlength: {type: 'string', required: true,regExp:/^[0-9]+$/,errorMsg:"必须填写数字",maxLength:11},//高（mm）
					ncurbweight: {type: 'string', required: true,regExp:/^\d+\.?\d+$/,errorMsg:"必须填写数字（允许小数）",maxLength:13},//整备质量（kg）
					npasenger: {type: 'string', required: true,maxLength:11 }, //额定载客
					vmotorpower: {type: 'string', required: true, maxLength: 120 }, //电机额定/最大功率（kW）
					vbatteryclass: {type: 'string', required: true, maxLength: 120 }, //电池型式
					nmaxweight: {type: 'string', required: true,regExp:/^\d+\.?\d+$/,errorMsg:"必须填写数字（允许小数）",maxLength:13}//最大质量（kg）
				}
			}),
			dialogListData: new u.DataTable({
				meta:{
					batpower:{type:'string'}
				}
			}),
			//状态下拉框
			statusEnum: [{name: '已保存', value: '20211001'}, {name: '已关闭', value: '20211004'}],
			// statusEnum: enumerate(2021),
			//是否投放下拉框
			shipEnum: enumerate(1001),
			//新增
			addFunShow : ko.observable(false),
			//修改
			editFunShow : ko.observable(false),
			//导入
			importFunShow : ko.observable(false),
			//关闭
			closeFunShow : ko.observable(false)
		},
		events = {
			/*================================
			=            主表信息处理方法            				    =
			================================*/
			//查询主表信息的封装方法
		queryMainData: function() {
			var rows = viewModel.searchData.getSelectedRows();
			var queryData = rows[0].getSimpleData();
			queryData["pageIndex"] = viewModel.listData.pageIndex();
			queryData["pageSize"] = viewModel.listData.pageSize();
			$.ajax({
				type: 'POST',
				url: window.cturl + '/bd/competBrand/querypageExt',
				data: queryData,
				success: function success(data) {
					viewModel.listData.setSimpleData(data.content);
					viewModel.listData.totalPages(data.totalPages);
					viewModel.listData.totalRow(data.totalElements);
					viewModel.listData.setAllRowsUnSelect();
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		searchClick: function() {
			viewModel.listData.pageIndex(0);
			viewModel.queryMainData();
		},
		addClick: function addClick() {
			viewModel.cardData.setEnable(true);
			$('.attachment').css('visibility','hidden');//隐藏附件栏
			var r = viewModel.cardData.createEmptyRow();
			var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
			md.dGo('competbrand_new');
		},
		backClick: function backClick() {
			viewModel.cardData.clear();
			viewModel.compareListData.clear();
			viewModel.listData.setAllRowsUnSelect();
			var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
			md.dGo('competbrand_list');
		},
		saveClick: function saveClick() {
			var validate = getvalidate(app, "#competbrand_new"),vcompetmodel,vmodelname;
			if (validate == false) {
				return;
			}
			var row = viewModel.cardData.getCurrentRow();
			vcompetmodel=row.getValue('vcompetmodel');
			vmodelname=row.getValue('vmodelname');
			//过滤掉车型编码和车型名称相同的情况
			if(vcompetmodel==vmodelname){
				u.messageDialog({
					msg: "车型编码和车型名称不能相同！",
					title: "提示",
					btnText: "OK"
				});
				return;
			}
			onLoading();
			// viewModel.cardData.setEnable(false);
			viewModel.compareListData.setEnable(false);
			$.ajax({
				type: 'post',
				url: window.cturl + '/bd/competBrand/save',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(row.getSimpleData()),
				success: function success(data) {
					viewModel.saveCompare(data.msg);//保存对比数据
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					onCloseLoading();
					errors.error(XMLHttpRequest);
					/*viewModel.queryMainData();
					var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
					md.dGo('competbrand_list');*/
				}
			});
		},
		editClick: function editClick() {
			viewModel.cardData.setEnable(true);
			$('.attachment').css('visibility','visible');//显示附件栏
			var rows = viewModel.listData.getSelectedRows(),
				row, pk, md,status;
			//如果没有row被选中
			if (rows.length != 1) {
				u.messageDialog({
					msg: "请选择（仅）一行要修改的数据",
					title: "提示",
					btnText: "OK"
				});
				return;
			}
			row = rows[0];
			status=row.getValue('vstatus');//获取选择行竞品信息的状态
			if(status.trim()=="20211004"){
				u.messageDialog({
					msg: "已关闭状态不执行修改",
					title: "提示",
					btnText: "OK"
				});
				return;
			}
			pk = row.getValue('pk_competbrand'); //主表ID
			viewModel.cardData.setSimpleData(row.getSimpleData());
			viewModel.queryCompareData(pk);
			viewModel.fileQuery();//查询附件
			md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
			md.dGo('competbrand_new');
		},
		deleteClick: function deleteClick() {
			//获取所有要删除的行
			var rows = viewModel.listData.getSelectedRows(),
				delArr;
			if (rows.length < 1) {
				u.messageDialog({
					msg: "请选择要删除的数据",
					title: "提示",
					btnText: "OK"
				});
				return;
			}
			delArr = []; //保存要删除的行数据
			for (var i = rows.length - 1; i >= 0; i--) {
				delArr.push(rows[i].getSimpleData());
			}
			u.confirmDialog({
				title: "确认",
				msg: "请确认是否删除选中的数据？",
				onOk: function onOk() {
					$.ajax({
						url: window.cturl + '/bd/competBrand/remove',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(delArr),
						success: function success(data) {
							viewModel.listData.removeRows(rows);
							u.messageDialog({
								msg: "删除成功",
								title: "提示",
								btnText: "OK"
							});
							viewModel.queryMainData();
						},
						error: function error(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					});
				}
			});
		},
		publishClick: function publishClick() {
			var rows = viewModel.listData.getSelectedRows(),
				pubArr;
			if (rows.length < 1) {
				u.messageDialog({
					msg: "请选择要发布的数据",
					title: "提示",
					btnText: "OK"
				});
				return;
			}
			pubArr = []; //保存要发布的行数据
			for (var i = rows.length - 1; i >= 0; i--) {
				pubArr.push(rows[i].getSimpleData());
			}
			u.confirmDialog({
				title: "确认",
				msg: "请确认是否发布选中的数据？",
				onOk: function onOk() {
					$.ajax({
						url: window.cturl + '/bd/competBrand/publish',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(pubArr),
						success: function success(data) {
							u.messageDialog({
								msg: "发布成功",
								title: "提示",
								btnText: "OK"
							});
							viewModel.queryMainData();
						},
						error: function error(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
							viewModel.queryMainData();
						}
					});
				}
			});
		},
		revokeClick: function revokeClick() {
			var rows = viewModel.listData.getSelectedRows(),
				revArr;
			if (rows.length < 1) {
				u.messageDialog({
					msg: "请选择要撤销的数据",
					title: "提示",
					btnText: "OK"
				});
				return;
			}
			revArr = []; //保存要撤销的行数据
			for (var i = rows.length - 1; i >= 0; i--) {
				revArr.push(rows[i].getSimpleData());
			}
			u.confirmDialog({
				title: "确认",
				msg: "请确认是否撤销选中的数据？",
				onOk: function onOk() {
					$.ajax({
						url: window.cturl + '/bd/competBrand/revoke',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(revArr),
						success: function success(data) {
							u.messageDialog({
								msg: "撤销成功",
								title: "提示",
								btnText: "OK"
							});
							viewModel.queryMainData();
						},
						error: function error(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
							viewModel.queryMainData();
						}
					});
				}
			});
		},
		closeClick: function closeClick() {
			var rows = viewModel.listData.getSelectedRows(),
				cloArr;
			if (rows.length < 1) {
				u.messageDialog({
					msg: "请选择要关闭的数据",
					title: "提示",
					btnText: "OK"
				});
				return;
			}
			cloArr = []; //保存要关闭的行数据
			for (var i = rows.length - 1; i >= 0; i--) {
				cloArr.push(rows[i].getSimpleData());
			}
			u.confirmDialog({
				title: "确认",
				msg: "请确认是否关闭选中的数据？",
				onOk: function onOk() {
					$.ajax({
						url: window.cturl + '/bd/competBrand/close',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(cloArr),
						success: function success(data) {
							u.messageDialog({
								msg: "关闭成功",
								title: "提示",
								btnText: "OK"
							});
							viewModel.queryMainData();
						},
						error: function error(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
							viewModel.queryMainData();
						}
					});
				}
			});
		},
		//获取导入模板
		getTemplate:function(){
		   	var form = $("<form>");   //定义一个form表单
	            form.attr('style', 'display:none');   //在form表单中添加查询参数
	            form.attr('target', '');
	            form.attr('method', 'post');
	            form.attr('action', window.cturl+"/bd/competBrand/getTemplate");
	            $('#competbrand-mdlayout').append(form);  //将表单放置在web中 
	            form.submit();
		},
		//导入
		importData:function(){
			window.md = u.dialog({id:'competbrand_testDialg2',content:"#competbrand_dialog_content",hasCloseMenu:true});
			$('.sub-list1-new').css('display','inline-block');
		},
		//实际导入方法
		onUploadExcel : function(){
			//获取表单
			$.ajaxFileUpload({
				url: window.cturl + '/bd/competBrand/importData',
				secureuri:false,
				fileElementId:'competbrand_fileName',
				dataType: 'json',
				success: function(data) {
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					viewModel.queryMainData();//重查主表数据
					viewModel.close();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		showClick:function (id) {
			//将id行设置为选中行
			viewModel.listData.setRowSelect(id);
			//获取选中行
			var row = viewModel.listData.getCurrentRow(),pk;
			viewModel.cardData.setSimpleData(row.getSimpleData());
			pk=row.getValue('pk_competbrand');
			$.ajax({
				type: 'POST',
				contentType: "application/json ; charset=utf-8",
				url: window.cturl + '/bd/competBrand/getView',
				data: JSON.stringify(row.getSimpleData()),
				success: function success(data) {
					viewModel.cardData.setSimpleData(data);
					viewModel.compareListData.setEnable(false);
					viewModel.queryCompareData(pk);//查询对比车型信息
					viewModel.fileQuery();
					var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
					md.dGo('competbrand_view');
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		/*==============================
		=           			对比车型处理方法			    =
		==============================*/
		//查询对比车型信息
		queryCompareData:function(pk){
			var param={};
			param['pk_competbrand']=pk;
            param['psize'] = pagesize;
            $.ajax({
                type: 'POST',
                url: window.cturl + '/bd/competCompare/querypage',
                contentType: 'application/json;charset=utf-8',
                data:JSON.stringify(param),
                success: function success(data) {
                    data = data['content'];
					viewModel.compareListData.setSimpleData(data);
					viewModel.compareListData.setRowUnFocus();
					viewModel.compareListData.setEnable(false);
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		//新增对比车型关联关系
		addCompare: function() {
			viewModel.compareCardData.clear();
			viewModel.compareCardData.setEnable(true);
			var r = viewModel.compareCardData.createEmptyRow();
			var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
			md.dGo('brand_choose');
		},
		//保存对比车型关联关系
		saveCompare: function(pk) {
			viewModel.compareListData.setEnable(false);
			var rows = viewModel.compareListData.getAllRows(), //获取所有列的数据
				saveArr; //真正需要执行保存的行
			saveArr = [];
			for (var i = rows.length - 1; i >= 0; i--) {
				if(rows[i].getValue("pk_competbrand") == null){
					rows[i].setValue("pk_competbrand", pk); //设置每一行的pk_competbrand值为主表返回值
					saveArr.push(rows[i].getSimpleData());
				}
			}
			//没有新增行则不走后台
			if(saveArr.length>0){
				$.ajax({
					type: 'POST',
					url: window.cturl + '/bd/competCompare/save',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(saveArr), //将数组序列化然后提交
					success: function success(data) {
						onCloseLoading();
						//提示框
						u.messageDialog({
							msg: data.msg,
							title: "提示",
							btnText: "OK"
						});
						viewModel.cardData.clear(); //提交修改后清空卡片数据
						//返回列表界面
						var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
						md.dGo('competbrand_list');
						//重新执行页面加载方法
						viewModel.queryMainData();
					},
					error: function error(XMLHttpRequest, textStatus, errorThrown) {
						onCloseLoading();
						errors.error(XMLHttpRequest);
					}
				});
			}else {
				onCloseLoading();
				u.messageDialog({
					msg: "保存成功",
					title: "提示",
					btnText: "OK"
				});
				viewModel.cardData.clear(); //提交修改后清空卡片数据
				//返回列表界面
				var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
				md.dGo('competbrand_list');
				//重新执行页面加载方法
				viewModel.queryMainData();
			}
		},
		//删除对比车型关联关系
		deleteCompare: function() {
			var rows = viewModel.compareListData.getSelectedRows(),
			delObj={},pk_model=[],pk_competbrand=viewModel.cardData.getCurrentRow().getValue('pk_competbrand');
			if (rows.length < 1) {
				u.messageDialog({
					msg: "请选择要删除的数据",
					title: "提示",
					btnText: "OK"
				});
				return;
			}
			delObj.pk_competbrand=pk_competbrand;//保存竞品车型主键
			u.confirmDialog({
				title: "确认",
				msg: "是否删除选中的数据？",
				onOk: function onOk() {
					for (var i = rows.length - 1; i >= 0; i--) {
						//如果是后台带来的数据
						if (rows[i].getValue("pk_competbrand")) {
							pk_model.push(rows[i].getValue('pk_model'));//将车型的主键放入
						}
					}
					if(pk_model.length>0){
						onLoading();
						delObj.pk_model="'"+pk_model.join("','")+"'";//前台拼接
						$.ajax({
							url: window.cturl + '/bd/competCompare/remove',
							type: 'POST',
							dataType: 'json',
							contentType: "application/json ; charset=utf-8",
							data: JSON.stringify(delObj),
							success: function success(content) {
								onCloseLoading();
								viewModel.compareListData.removeRows(rows);
								u.messageDialog({
									msg: "删除成功",
									title: "提示",
									btnText: "OK"
								});
							},
							error: function error(XMLHttpRequest, textStatus, errorThrown) {
								onCloseLoading();
								errors.error(XMLHttpRequest);
							}
						});
					}else {
						viewModel.compareListData.removeRows(rows);
						u.messageDialog({
							msg: "删除成功",
							title: "提示",
							btnText: "OK"
						});
					}
				}
			});
		},
		//车型界面确认
		bydConfirmClick: function(){
			var validate = getvalidate(app, "#brand_choose");
			if (validate == false) {
				return;
			}
			var data = viewModel.compareCardData.getCurrentRow().getSimpleData();
			var newRow=viewModel.compareListData.createEmptyRow();
			newRow.setSimpleData(data);
			var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
			md.dGo('competbrand_new');
		},
		//车型界面返回
		bydBackClick: function(){
			var md = document.querySelector('#competbrand-mdlayout')['u.MDLayout'];
			md.dGo('competbrand_new');
		},
		modelChange:function(obj){
			//如果是电量的改变，不作处理
            if (obj.field != "pk_model") return;
			var param = {};
			param.pkmodel = obj.newValue;
			$.ajax({
				type: 'POST',
				url: window.cturl + '/bd/competBrand/getModelDetail',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param), 
				success: function success(data) {
					viewModel.compareCardData.clear(); 
					viewModel.compareCardData.setSimpleData(data.tmmodel);
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		/*============================
		=            		分页方法及辅助方法 	           =
		============================*/
		//切换页码方法
		pageChangeFunc: function(index) {
			viewModel.listData.pageIndex(index);
			viewModel.queryMainData();
		},
		//每页数据量切换方法
		sizeChangeFunc: function(size) {
			viewModel.listData.pageSize(size);
			viewModel.listData.pageIndex(0);
			viewModel.queryMainData();
		},
       /* comparePageChangeFunc: function(index) {
            var row = viewModel.cardData.getCurrentRow(), pk = row.getValue('pk_competbrand');
            viewModel.compareListData.pageIndex(index);
            viewModel.queryCompareData(pk);
        },
        //每页数据量切换方法
        compareSizeChangeFunc: function(size) {
            var row = viewModel.cardData.getCurrentRow(), pk = row.getValue('pk_competbrand');
            viewModel.compareListData.pageSize(size);
            viewModel.compareListData.pageIndex(0);
            viewModel.queryCompareData(pk);
        },*/
		afterAdd: function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},
		/*============================
		=           			 附件方法			          =
		============================*/
		//关闭上传附件界面
		onCloseFileWindow : function(){
			md.close();
		},
		//打开附件上传界面
		onOpenUploadWin : function(){
			window.md = u.dialog({id:'competbrand_testDialg3',content:"#competbrand_dialog_uploadfile",hasCloseMenu:true});
			$('.sub-list1-new').css('display','inline-block');
		},
		//上传附件
		onFileUpload : function(){
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_competbrand;
			var par = { 
					 fileElementId: "competbrand_uploadbatch_id",  //【必填】文件上传空间的id属性  <input type="file" id="id_file" name="file" />,可以修改，主要看你使用的 id是什么 
					 filepath: pk,   //【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
					 groupname: "COMPETBRAND",//【必填】分組名称,未来会提供树节点
					 permission: "private", //【选填】 read是可读=公有     private=私有     当这个参数不传的时候会默认private
					 url: true,          //【选填】是否返回附件的连接地址，并且会存储到数据库
					 //thumbnail :  "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
					 cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
					 }
			 var f = new interface_file();
			 f.filesystem_upload(par,viewModel.fileUploadCallback);	  
			 onLoading();
		},
		//上传文件回传信息
		fileUploadCallback : function(data){
			 onCloseLoading();
			 if(1 == data.status){//上传成功状态
				 viewModel.fileData.addSimpleData(data.data);
				 u.messageDialog({msg:"上传成功！",title:"提示", btnText:"OK"});
			 }else{//error 或者加載js錯誤
				 u.messageDialog({msg:"上传失败！"+data.message,title:"提示", btnText:"OK"});
			 }
		 },
		 fileQuery : function(){
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_competbrand;
			 var par = { 
				     //建议一定要有条件否则会返回所有值
					 filepath: pk, //【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
					 groupname: "COMPETBRAND",//【选填】[分組名称,未来会提供树节点]
					 cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
				}
			 var f = new interface_file();
			 f.filesystem_query(par,viewModel.fileQueryCallBack);	 
		 },
		 fileQueryCallBack : function(data){
			 if(1 == data.status){//上传成功状态
				 viewModel.fileData.setSimpleData(data.data);
			 }else{
				 //没有查询到数据，可以不用提醒	
				 if("没有查询到相关数据" != data.message){
					 u.messageDialog({msg:"查询失败"+data.message,title:"提示", btnText:"OK"});
				 }else{
					 viewModel.fileData.removeAllRows();
				 }
			 }
		 },
		 //附件删除
		 fileDelete : function(){
			 var row = viewModel.fileData.getSelectedRows();
			 if(row==null || row.length==0){
				 u.messageDialog({msg:"请选择要删除的附件",title:"提示", btnText:"OK"});
				 return
			 }else if(row.length>1){
				 u.messageDialog({msg:"每次只能删除一个附件",title:"提示", btnText:"OK"});
				 return
			 }
			 for(var i=0;i<row.length;i++){
				 var pk = row[i].getValue("id");
				 var par = {
		        	 id:pk,//【必填】表的id
		        	 cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
				 }
				 var f = new interface_file();
				 f.filesystem_delete(par,viewModel.fileDeleteCallBack);
			 }
		 },
		 //附件删除回调
		 fileDeleteCallBack : function(data){
			 if(1 == data.status){//上传成功状态
				 viewModel.fileQuery();
			 }else{
				 u.messageDialog({msg:"删除失败"+data.message,title:"提示", btnText:"OK"});
			 }
		 },
		 //下载
		 fileDownload : function(){
			 var row = viewModel.fileData.getSelectedRows();
			 if(row==null || row.length==0){
				 u.messageDialog({msg:"请选择要下载的附件",title:"提示", btnText:"OK"});
				 return
			 }else if(row.length>1){
				 u.messageDialog({msg:"每次只能下载一个附件",title:"提示", btnText:"OK"});
				 return
			 }
			 for(var i=0;i<row.length;i++){
				 var pk = row[i].getValue("id");
				 var form = $("<form>");   //定义一个form表单
				 form.attr('style', 'display:none');   //在form表单中添加查询参数
				 form.attr('target', '');
				 form.attr('enctype', 'multipart/form-data');
				 form.attr('method', 'post');
				 form.attr('action', window.ctxfilemng+"file/download?permission=private&id="+pk);
				 $('#competbrand-mdlayout').append(form);  //将表单放置在web中 
				 form.submit();
			 }
		 },
		 //查看
		 fileView : function(){
			 var row = viewModel.fileData.getSelectedRows();
			 if(row==null || row.length==0){
				 u.messageDialog({msg:"请选择要下载的附件",title:"提示", btnText:"OK"});
				 return
			 }else if(row.length>1){
				 u.messageDialog({msg:"每次只能查看一个附件",title:"提示", btnText:"OK"});
				 return
			 }
			 for(var i=0;i<row.length;i++){
				 var url = row[i].getValue("url");
				 parent.open("http://"+url);
			 }
		 }
	};
	dialogEvents={
		//模态框事件-----
		//打开模态框
		dialogClick:function(){
			var arry = new Array();
			var datas = new Array();
			var nvbatterypower = viewModel.compareCardData.getSimpleData()[0].nvbatterypower;
			if(null!=nvbatterypower){
				arry = nvbatterypower.split('/');
				for (var i=0;i<arry.length;i++){
					datas[i] = new Object();
					datas[i].batpower = arry[i]+"";
				}
			}
			window.md = u.dialog({id:'competbrand_powerDialg',content:"#competbrand_battery_dialog_content",hasCloseMenu:true});
			viewModel.dialogListData.clear();
			viewModel.dialogListData.setSimpleData(datas);
		},
		//保存-模态框
		dialog_saveClick:function(){
			var dialogData = viewModel.dialogListData.getCurrentRow().getSimpleData();
			var cardRow = viewModel.compareCardData.getCurrentRow();
			cardRow.setValue("nvpower",dialogData.batpower);
			window.md.close();
		}
	};
	
	viewModel = u.extend(basicDatas, events,dialogEvents);
    var initPageSize = function () {

    };
	var getInitData = function getInitData() {
		$.ajax({
			type: 'get',
			url: window.cturl+"/security/authBtn/auth?funcCode=F020302",
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
						}*/else if("导入"==funTemp){
							viewModel.importFunShow(true);
						}else if("关闭"==funTemp){
							viewModel.closeFunShow(true);
						}
					}
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		});
		ref();
		enumerate(2021);
		viewModel.listData.pageSize(10);
		viewModel.searchData.createEmptyRow(); //创建查询空行
		viewModel.queryMainData();
		viewModel.compareCardData.on("valuechange",events.modelChange);
	};
	return {
		init: function init(content,tabid) {
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