define(['iReferComp','refComp','error','text!./countrysubsidy.html','uuigrid','utilrow','ajaxfileupload','ossupload','interfaceFile','interfaceFileImpl'], function(iReferComp,refComp,errors,html) {
	'use strict';
	var refid;
	var dom;
	
	var ctxRoot = window.cturl//ctx || '';
	var ctrlBathPath = ctxRoot+'/subsidy/country';
	var editoradd = '';
	var app, viewModel, basicDatas, events, oper,enums,pageSize=10,comValueNameMap;
	
	basicDatas = {
		//查询用
		searchData: new u.DataTable({
			meta:{
				search_vbillno: {type: 'string'}, //国补单号
				search_vdeclareno:{type: 'string'},//企业申报文号
				search_vdeclarecity: {type: 'string'},//申报城市
				search_vvin: {type: 'string'},//车辆识别代码
				search_dentrydate_start: {type: 'string'},//申报日期开始日期
				search_dentrydate_end: {type: 'string'},//申报日期结束日期
				search_vvehicleseries: {type: 'string'},//车系
				search_vrundept: {type: 'string'},//车辆运营单位
				search_doverduedate_start: {type: 'string'},//超期回款日期
				search_doverduedate_end: {type: 'string'},//超期回款日期
				search_vstatus: {type: 'string'}//国补申报状态
			}
		}),
		//列表界面
		listData: new u.DataTable({
			meta: {
				pk_countrysubsidy: {type: 'string'},//国补主键
				vbillno: {type: 'string'},//国补单号
				countrysubsidyno: {type: 'string'},//国补申报单号
				vdeclareno:{type: 'string'},//企业申报文号
				dentrydate: {type: 'string'},//录入日期
				vdeclaredept: {type: 'string'},//申报部门
				vdeclarelegal:{type: 'string'},//申报法人
				vgovernmentappno: {type: 'string'},//政府批文文号
				vdeclarecity: {type: 'string'},//申报城市
				visdatacomplete: {type: 'string'},//资料是否齐全
				visdataapprove:{type: 'string'},//资料是否审核
				pk_org: {type: 'string'},//组织
				vstatus: {type: 'string'},//状态
				creator:{type: 'string'},//创建人
				creationtime: {type: 'string'},//创建事假
				modifier: {type: 'string'},//修改人
				modifiedtime: {type: 'string'},//修改时间
				ts:{type: 'string'}//时间戳
			}
		}),
		//卡片界面(新增，修改)
		cardData: new u.DataTable({
			meta: {
				pk_countrysubsidy: {type: 'string'},//国补主键
				vbillno: {type: 'string'},//国补单号
				countrysubsidyno: {type: 'string',required: true,nullMsg: '内容不能为空!',maxLength:30},//国补申报单号
				vdeclareno:{type: 'string',required: true,nullMsg: '内容不能为空!',maxLength:30},//企业申报文号
				dentrydate: {type: 'datetime',required: true,nullMsg: '内容不能为空!',maxLength:19},//录入日期
				vdeclaredept: {type: 'string',required: true,nullMsg: '内容不能为空!',maxLength:30},//申报部门
				vdeclarelegal:{type: 'string',required: true,nullMsg: '内容不能为空!',maxLength:30},//申报法人
				vgovernmentappno: {type: 'string',required: true,nullMsg: '内容不能为空!',maxLength:30},//政府批文文号
				vdeclarecity: {type: 'string',required: true,nullMsg: '内容不能为空!'},//申报城市
				visdatacomplete: {type: 'string'},//资料是否齐全
				visdataapprove:{type: 'string'},//资料是否审核
				pk_org: {type: 'string'},//组织
				vstatus: {type: 'string'},//状态
				creator:{type: 'string'},//创建人
				creationtime: {type: 'string'},//创建时间
				modifier: {type: 'string'},//修改人
				modifiedtime: {type: 'string'},//修改时间
				ts:{type: 'string'},//时间戳
				countrysubsidyItems:{type: 'string'},//车辆子表
				countrysubsidyFundback:{type: 'string'}//回款子表
			}
		}),
		//子表车辆信息子表
		gridVehicleData: new u.DataTable({
			meta: {
				pk_countrysubsidy_items: {type: 'string'},//车辆子表主键
				pk_countrysubsidy: {type: 'string'},//国补申报主键
				viscommercial: {type: 'string'},//是否商用车
				vbuydomain: {type: 'string'},//购车领域
				vbuycity: {type: 'string'},//购车城市
				vrundept: {type: 'string'},//车辆运营单位
				vvehiclekind: {type: 'string'},//车辆种类
				vvehiclepurpose: {type: 'string'},//车辆用途
				vvehiclemodel: {type: 'string'},//车辆型号
				vvin: {type: 'string'},//车辆识别代码VIN
				vlicense: {type: 'string'},//车辆牌照
				nsubsidystandard: {type: 'float'},//申请补贴标准
				ntotalback: {type: 'float'},//累计回款金额
				nnotback: {type: 'float'},//未回款金额
				npurchaseprice: {type: 'float'},//购买价格
				vinvoiceno: {type: 'string'},//发票号
				ninvoiceyear: {type: 'integer'},//发票年
				ninvoicemonth: {type: 'integer'},//发票月
				ninvoiceday: {type: 'integer'},//发票日
				ndlicenseyear: {type: 'integer'},//行驶证年
				ndlicensemonth: {type: 'integer'},//行驶证月
				ndlicenseday: {type: 'integer'},//行驶证日
				dmustbackdate: {type: 'string'},//应回款日期
				ddeclaredate: {type: 'string'},//申报时间
				vapprovepoint: {type: 'string'},//审批节点
				vnotdeclarestatus: {type: 'string'},//未申报状态
				vnotdeclarecomments: {type: 'string'},//未申报说明
				doverduedate: {type: 'string'},//超期回款日期
				vdeclarestatus: {type: 'string'},//申报状态
				creator: {type: 'string'},//创建人
				creationtime: {type: 'string'},//创建时间
				modifier: {type: 'string'},//修改人
				modifiedtime: {type: 'string'},//修改时间
				ts: {type: 'string'}//时间戳
			}
		}),
		//车辆回款信息子表
		gridFundbackData: new u.DataTable({
			meta: {
				pk_countrysubsidy_fundback: {type: 'string'},//回款子表主键
				pk_countrysubsidy: {type: 'string'},//国补申报主键
				vvin: {type: 'string'},//VIN码
				vvehicleseries: {type: 'string'},//车系
				vsaledept: {type: 'string'},//所属销售部
				nsubsidystandard: {type: 'string'},//申请补贴标准
				dmustbackdate: {type: 'string'},//应回款日期
				nfactback: {type: 'string'},//实际回款金额
				dfactbackdate: {type: 'string'},//实际回款日期
				creator: {type: 'string'},//创建人
				creationtime: {type: 'string'},//创建时间
				modifier: {type: 'string'},//修改人
				modifiedtime: {type: 'string'},//修改时间
				ts: {type: 'string'}//时间戳
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
		//查看界面
		viewData: new u.DataTable({
			meta: {
				pk_countrysubsidy: {type: 'string'},//国补主键
				vbillno: {type: 'string'},//国补单号
				countrysubsidyno: {type: 'string'},//国补申报单号
				vdeclareno:{type: 'string'},//企业申报文号
				dentrydate: {type: 'string'},//录入日期
				vdeclaredept: {type: 'string'},//申报部门
				vdeclarelegal:{type: 'string'},//申报法人
				vgovernmentappno: {type: 'string'},//政府批文文号
				vdeclarecity: {type: 'string'},//申报城市
				visdatacomplete: {type: 'string'},//资料是否齐全
				visdataapprove:{type: 'string'},//资料是否审核
				pk_org: {type: 'string'},//组织
				vstatus: {type: 'string'},//状态
				creator:{type: 'string'},//创建人
				creationtime: {type: 'string'},//创建事假
				modifier: {type: 'string'},//修改人
				modifiedtime: {type: 'string'},//修改时间
				ts:{type: 'string'}//时间戳
			}
		}),
		//附件内容
		importData: new u.DataTable({
			meta : {
				filePath: {type: 'string'}//文件路径
			}
		}),
		//查询vin
		searchItems: new u.DataTable({
			meta:{
				vvin: {type: 'string'}   
			}
		}),
		//查询vin
		searchFundback: new u.DataTable({
			meta:{
				vvin: {type: 'string'}   
			}
		}),
		//导入标记
		importFlag:"",
		//新增
		addFunShow : ko.observable(false),
		//修改
		editFunShow : ko.observable(false),
		//删除
		delFunShow : ko.observable(false),
		//完结
		finishFunShow : ko.observable(false),
		//撤销完结
		unFinishFunShow : ko.observable(false)
		
			
	};
	
	comValueNameMap={};
	
	enums = {
		//国补状态	
//		vpscomboData: enumerate(3010),
//			[{"value": "30101001","name": "已保存"}, 
//		            {"value": "30101002","name": "已完结"}],
		//是否枚举	
		yesORno: enumerate(1001),
//			[{"value": "10011001","name": "是"}, 
//		            {"value": "10011002","name": "否"}],
		//国补申报状态	
		vsubsidyStatus: enumerate(3001)
//			[
//		        {"value": "30011001","name": "已保存"}, 
//	            {"value": "30011002","name": "已结案"}]
				};


	events = {
		//点击查询按钮
		searchClick: function(){
			var queryData = {};
			var search_vbillno = viewModel.searchData.getSimpleData()[0];
			queryData["search_vbillno"] = viewModel.searchData.getSimpleData()[0].search_vbillno;
			queryData["search_vdeclareno"] = viewModel.searchData.getSimpleData()[0].search_vdeclareno;
			queryData["search_vdeclarecity"] = viewModel.searchData.getSimpleData()[0].search_vdeclarecity;
			queryData["search_vvin"] = viewModel.searchData.getSimpleData()[0].search_vvin;
			queryData["search_dentrydate_start"] = viewModel.searchData.getSimpleData()[0].search_dentrydate_start;
			queryData["search_dentrydate_end"] = viewModel.searchData.getSimpleData()[0].search_dentrydate_end;
			queryData["search_vvehicleseries"] = viewModel.searchData.getSimpleData()[0].search_vvehicleseries;
			queryData["search_vrundept"] = viewModel.searchData.getSimpleData()[0].search_vrundept;
			queryData["search_doverduedate_start"] = viewModel.searchData.getSimpleData()[0].search_doverduedate_start;
			queryData["search_doverduedate_end"] = viewModel.searchData.getSimpleData()[0].search_doverduedate_end;
			queryData["search_vstatus"] = viewModel.searchData.getSimpleData()[0].search_vstatus;
			queryData["page"] = viewModel.listData.pageIndex();

			viewModel.listData.pageSize(pageSize);
	        queryData["page.size"] = viewModel.listData.pageSize();
	        
	        
	        
//	        $.getJSON("/pages/subsidy/countrysubsidy.json",function(data){
//				viewModel.listData.pageSize(pagesize);//每页显示多少条
//				viewModel.listData.setSimpleData(data.content);
//				viewModel.listData.totalPages(data.totalPages);
//				viewModel.listData.setRowUnFocus();
//			});
//	        $.ajax({
//				type: 'get',
//				url: ctrlBathPath+"/mainFun",
//				//data: queryData,
//				success: function(data) {
//					var funList = data.funList;
//					if(null != funList){
//						for(var i=0;i<funList.length;i++){
//							var funTemp = funList[i];
//							if("新增"==funTemp){
//								viewModel.addFunShow(true);
//							}else if("修改"==funTemp){
//								viewModel.editFunShow(true);
//							}else if("删除"==funTemp){
//								viewModel.delFunShow(true);
//							}else if("完结"==funTemp){
//								viewModel.finishFunShow(true);
//							}else if("撤销完结"==funTemp){
//								viewModel.unFinishFunShow(true);
//							}
//						}
//					}
//					
//				},
//				error: function(XMLHttpRequest, textStatus, errorThrown) {
//					errors.error(XMLHttpRequest);
//				}
//			})
	        
	        
	        var queryFunData = {};
	        $.ajax({
				type: 'get',
				url: ctxRoot+"/security/authBtn/auth?funcCode=F070101",
				success: function(data) {
					var funList = data;
					if(null != funList){
						for(var i=0;i<funList.length;i++){
							var funTemp = funList[i];
							if("新增"==funTemp){
								viewModel.addFunShow(true);
							}else if("修改"==funTemp){
								viewModel.editFunShow(true);
							}else if("删除"==funTemp){
								viewModel.delFunShow(true);
							}else if("完结"==funTemp){
								viewModel.finishFunShow(true);
							}else if("撤销完结"==funTemp){
								viewModel.unFinishFunShow(true);
							}
						}
					}
					
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
	        
			$.ajax({
				type: 'get',
				url: ctrlBathPath+"/main",
				data: queryData,
				success: function(data) {
					viewModel.listData.pageSize(data.size);//每页显示多少条
					viewModel.listData.setSimpleData(data.content);
					viewModel.listData.totalPages(data.totalPages);
					viewModel.listData.totalRow(data.totalElements); 
					viewModel.listData.setRowUnFocus();
					
					/**
			         * 控制按钮权限
			         */
//					var funList = data.funList;
//					if(null != funList){
//						for(var i=0;i<funList.)
//					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//单据主表信息导出
		onDownload:function(){
			var queryData = {};
			var search_vbillno = viewModel.searchData.getSimpleData()[0];
			queryData["search_vbillno"] = viewModel.searchData.getSimpleData()[0].search_vbillno;
			queryData["search_vdeclareno"] = viewModel.searchData.getSimpleData()[0].search_vdeclareno;
			queryData["search_vdeclarecity"] = viewModel.searchData.getSimpleData()[0].search_vdeclarecity;
			queryData["search_vvin"] = viewModel.searchData.getSimpleData()[0].search_vvin;
			queryData["search_dentrydate_start"] = viewModel.searchData.getSimpleData()[0].search_dentrydate_start;
			queryData["search_dentrydate_end"] = viewModel.searchData.getSimpleData()[0].search_dentrydate_end;
			queryData["search_vvehicleseries"] = viewModel.searchData.getSimpleData()[0].search_vvehicleseries;
			queryData["search_vrundept"] = viewModel.searchData.getSimpleData()[0].search_vrundept;
			queryData["search_doverduedate_start"] = viewModel.searchData.getSimpleData()[0].search_doverduedate_start;
			queryData["search_doverduedate_end"] = viewModel.searchData.getSimpleData()[0].search_doverduedate_end;
			queryData["search_vstatus"] = viewModel.searchData.getSimpleData()[0].search_vstatus;
			
			var form = $("<form>");   //定义一个form表单
            form.attr('style', 'display:none');   //在form表单中添加查询参数
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', ctrlBathPath+"/countrySublidDownload");

            
            $('#countrysubsidy-mdlayout').append(form);  //将表单放置在web中 
            /**
             * 查询条件
             */
            var input1 = $('<input>');
            input1.attr('type', 'hidden');
            input1.attr('name', 'search_vbillno');
            input1.attr('value', viewModel.searchData.getSimpleData()[0].search_vbillno);
            form.append(input1);  
            
            var input2 = $('<input>');
            input2.attr('type', 'hidden');
            input2.attr('name', 'search_vdeclareno');
            input2.attr('value', viewModel.searchData.getSimpleData()[0].search_vdeclareno);
            form.append(input2);  
            
            var input3 = $('<input>');
            input3.attr('type', 'hidden');
            input3.attr('name', 'search_vdeclarecity');
            input3.attr('value', viewModel.searchData.getSimpleData()[0].search_vdeclarecity);
            form.append(input3);  
            
            var input4 = $('<input>');
            input4.attr('type', 'hidden');
            input4.attr('name', 'search_vvin');
            input4.attr('value', viewModel.searchData.getSimpleData()[0].search_vvin);
            form.append(input4);  
            
            var input5 = $('<input>');
            input5.attr('type', 'hidden');
            input5.attr('name', 'search_dentrydate_start');
            input5.attr('value', viewModel.searchData.getSimpleData()[0].search_dentrydate_start);
            form.append(input5); 
            
            var input6 = $('<input>');
            input6.attr('type', 'hidden');
            input6.attr('name', 'search_dentrydate_end');
            input6.attr('value', viewModel.searchData.getSimpleData()[0].search_dentrydate_end);
            form.append(input6); 
            
            var input7 = $('<input>');
            input7.attr('type', 'hidden');
            input7.attr('name', 'search_vvehicleseries');
            input7.attr('value', viewModel.searchData.getSimpleData()[0].search_vvehicleseries);
            form.append(input7); 
            
            var input8 = $('<input>');
            input8.attr('type', 'hidden');
            input8.attr('name', 'search_vrundept');
            input8.attr('value', viewModel.searchData.getSimpleData()[0].search_vrundept);
            form.append(input8); 
            
            var input9 = $('<input>');
            input9.attr('type', 'hidden');
            input9.attr('name', 'search_doverduedate_start');
            input9.attr('value', viewModel.searchData.getSimpleData()[0].search_doverduedate_start);
            form.append(input9);
            
            var input10 = $('<input>');
            input10.attr('type', 'hidden');
            input10.attr('name', 'search_doverduedate_end');
            input10.attr('value', viewModel.searchData.getSimpleData()[0].search_doverduedate_end);
            form.append(input10);
            
            var input11 = $('<input>');
            input11.attr('type', 'hidden');
            input11.attr('name', 'search_vstatus');
            input11.attr('value', viewModel.searchData.getSimpleData()[0].search_vstatus);
            form.append(input11);
            
            
            
            form.submit();
		
		},
		//点击新增按钮
		addClick: function() {
			viewModel.cardData.setEnable(true);
			viewModel.cardData.clear();
//			var r = viewModel.cardData.createEmptyRow();
			$.ajax({
				type: 'get',
				url: ctrlBathPath+"/infoDefault",
				success: function(data) {
//					viewModel.cardData.setSimpleData(data);
//					viewModel.cardData.setValue("pk_org",data.pkCorp);
//					viewModel.cardData.setValue("vdeclaredept",data.pkDept);
//					viewModel.cardData.setValue("vdeclarelegal",data.pkUser);
//					viewModel.cardData.setValue("pk_countrysubsidy",data.pk_countrysubsidy);
//					viewModel.cardData.setSimpleData(r);
					viewModel.cardData.setSimpleData([
					{"pk_org":data.pkCorp,"vdeclaredept":data.pkDept,"vdeclarelegal":data.pkUser,"pk_countrysubsidy":data.pk_countrysubsidy}
					]);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
			
			viewModel.gridVehicleData.clear();
			viewModel.gridFundbackData.clear();
			var md = document.querySelector('#countrysubsidy-mdlayout')['u.MDLayout'];
			md.dGo('countrysubsidy_new');
		},
		//点击修改按钮
		editClick: function() {
			viewModel.cardData.setEnable(true);
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();
			if(null !=rows && rows.length == 1){
				var errorMsg = "";
				var vbillnoTmp = rows[0].getSimpleData().vbillno;
				var vstatusTmp = rows[0].getSimpleData().vstatus;
				if("30011001" != vstatusTmp){
					errorMsg = errorMsg+"单据："+vbillnoTmp+"已经完结，不能修改"
				}
				if(errorMsg.length>0){
					u.messageDialog({msg:errorMsg,title:"提示", btnText:"OK"});
					return;
				}
				var mainData = rows[0].data;
//				viewModel.cardData.setSimpleData(mainData);
				/**
				 * 设置子表数据
				 */
				var queryData = {};
				var pk = mainData.pk_countrysubsidy;
				queryData["pk"]=pk.value;
				queryData["itempage"] = 0;
		        queryData["itempage.size"] = 10;
		        queryData["fundpage"] = 0;
		        queryData["fundpage.size"] = 10;
		        
		        viewModel.searchItems.setSimpleData([{}]);
		        viewModel.searchFundback.setSimpleData([{}]);
		        
				$.ajax({
					type: 'get',
					url: ctrlBathPath+"/editItemQuery",
					data: queryData,
					success: function(data) {
						viewModel.cardData.setSimpleData(data.mainVO);
//						iReferComp.setFormData('#countrysubsidy_edit',data.mainVO, null);
						
//						viewModel.gridVehicleData.setSimpleData(data.itemList,{unSelect:true});
//						viewModel.gridFundbackData.setSimpleData(data.fundbackList,{unSelect:true});
						viewModel.gridVehicleData.pageSize(data.itemList.size);//每页显示多少条
						viewModel.gridVehicleData.setSimpleData(data.itemList.content);
						viewModel.gridVehicleData.totalPages(data.itemList.totalPages);
						viewModel.gridVehicleData.totalRow(data.itemList.totalElements); 
						viewModel.gridVehicleData.setRowUnFocus();
						
						viewModel.gridFundbackData.pageSize(data.fundbackList.size);//每页显示多少条
						viewModel.gridFundbackData.setSimpleData(data.fundbackList.content);
						viewModel.gridFundbackData.totalPages(data.fundbackList.totalPages);
						viewModel.gridFundbackData.totalRow(data.fundbackList.totalElements); 
						viewModel.gridFundbackData.setRowUnFocus();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
				viewModel.fileQuery();
				
				var md = document.querySelector('#countrysubsidy-mdlayout')['u.MDLayout'];
				md.dGo('countrysubsidy_edit');
			}else if(null !=rows && rows.length > 1){
				u.messageDialog({msg:"每次只能选择一条单据进行修改，请重新选择！",title:"提示", btnText:"OK"});
			}else{
				u.messageDialog({msg:"请选择一条单据，再进行修改！",title:"提示", btnText:"OK"});
			}
		},
		//点击查看按钮
		showClick: function(id){
			//将id行设置为选中行
			viewModel.listData.setRowSelect(id);
			//获取选中行
			var row = viewModel.listData.getCurrentRow();
			viewModel.cardData.setSimpleData(row.getSimpleData());
			
			/**
			 * 设置子表数据
			 */
			var queryData = {};
			var pk = row.getValue("pk_countrysubsidy");
			queryData["pk"]=pk;
			
			viewModel.searchItems.setSimpleData([{}]);
	        viewModel.searchFundback.setSimpleData([{}]);
			
			$.ajax({
				type: 'get',
				url: ctrlBathPath+"/viewItemQuery",
				data: queryData,
				success: function(data) {
					viewModel.gridVehicleData.pageSize(data.itemList.size);//每页显示多少条
					viewModel.gridVehicleData.setSimpleData(data.itemList.content);
					viewModel.gridVehicleData.totalPages(data.itemList.totalPages);
					viewModel.gridVehicleData.totalRow(data.itemList.totalElements); 
					viewModel.gridVehicleData.setRowUnFocus();
					
					viewModel.gridFundbackData.pageSize(data.fundbackList.size);//每页显示多少条
					viewModel.gridFundbackData.setSimpleData(data.fundbackList.content);
					viewModel.gridFundbackData.totalPages(data.fundbackList.totalPages);
					viewModel.gridFundbackData.totalRow(data.fundbackList.totalElements); 
					viewModel.gridFundbackData.setRowUnFocus();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
			viewModel.fileQuery();
			var md = document.querySelector('#countrysubsidy-mdlayout')['u.MDLayout'];
			md.dGo('countrysubsidy_view');
		},
		//完结
		finishClick : function(){
			var dats = [];
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();
			if(rows==null){
				u.messageDialog({msg:"请选择要完结的数据",title:"提示", btnText:"OK"});
				return
			}
			var errorMsg = "";
			for(var i=0;i<rows.length;i++){
				var vbillnoTmp = rows[i].getSimpleData().vbillno;
				var vstatusTmp = rows[i].getSimpleData().vstatus;
				if("30011001" != vstatusTmp){
					errorMsg = errorMsg+"单据："+vbillnoTmp+"已经完结，不能完结"
				}
				dats.push(rows[i].getSimpleData());
			}
			if(errorMsg.length>0){
				u.messageDialog({msg:errorMsg,title:"提示", btnText:"OK"});
				return;
			}
			//确认删除对话框
			u.confirmDialog({
				title: "确认",
				msg: "是否完结选中的数据？",
				onOk: function () {
					$.ajax({
						url:  ctrlBathPath+'/finish',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(dats),
						success: function(data) {
							//成功后页面提示
							u.messageDialog({msg:"完结成功！",title:"提示", btnText:"OK"});
							//也可重新调用初始化查询方法，
							getInitData();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			});
		},
		//撤销完结
		unFinishClick : function(){
			var dats = [];
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();
			if(rows==null){
				u.messageDialog({msg:"请选择要撤销完结的数据",title:"提示", btnText:"OK"});
				return
			}
			var errorMsg = "";
			for(var i=0;i<rows.length;i++){
				var vbillnoTmp = rows[i].getSimpleData().vbillno;
				var vstatusTmp = rows[i].getSimpleData().vstatus;
				if("30011002" != vstatusTmp){
					errorMsg = errorMsg+"单据："+vbillnoTmp+"状态为已保存，不能撤销完结"
				}
				dats.push(rows[i].getSimpleData());
			}
			if(errorMsg.length>0){
				u.messageDialog({msg:errorMsg,title:"提示", btnText:"OK"});
				return;
			}
			//确认删除对话框
			u.confirmDialog({
				title: "确认",
				msg: "是否撤销完结选中的数据？",
				onOk: function () {
					$.ajax({
						url:  ctrlBathPath+'/unfinish',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(dats),
						success: function(data) {
							//成功后页面提示
							u.messageDialog({msg:"撤销完结成功！",title:"提示", btnText:"OK"});
							//也可重新调用初始化查询方法，
							getInitData();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			});
		},
		//明细界面：车辆明细分页查询
		itemVehiclItmeQueryClick: function(){
			//获取选中行
			var row = viewModel.cardData.getCurrentRow();
			var searchItemsRow = viewModel.searchItems.getCurrentRow();
			/**
			 * 设置子表数据
			 */
			var queryData = {};
			var pk = row.getValue("pk_countrysubsidy");
			var vvin = searchItemsRow.getValue("vvin");
			queryData["pk"]=pk;
			queryData["vvin"]=vvin;
			queryData["itempage"] = viewModel.gridVehicleData.pageIndex();
	        queryData["itempage.size"] = viewModel.gridVehicleData.pageSize();
			$.ajax({
				type: 'get',
				url: ctrlBathPath+"/viewItemPageQuery",
				data: queryData,
				success: function(data) {
					viewModel.gridVehicleData.pageSize(data.itemList.size);//每页显示多少条
					viewModel.gridVehicleData.setSimpleData(data.itemList.content);
					viewModel.gridVehicleData.totalPages(data.itemList.totalPages);
					viewModel.gridVehicleData.totalRow(data.itemList.totalElements); 
					viewModel.gridVehicleData.setRowUnFocus();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		//明细界面：车辆回款分页查询
		itemVehicleFundbackQueryClick: function(){
			//获取选中行
			var row = viewModel.cardData.getCurrentRow();
			var searchFundRow = viewModel.searchFundback.getCurrentRow();
			/**
			 * 设置子表数据
			 */
			var queryData = {};
			var pk = row.getValue("pk_countrysubsidy");
			var vvin = searchFundRow.getValue("vvin");
			queryData["pk"]=pk;
			queryData["vvin"]=vvin;
			queryData["fundpage"] = viewModel.gridFundbackData.pageIndex();
	        queryData["fundpage.size"] = viewModel.gridFundbackData.pageSize();
			$.ajax({
				type: 'get',
				url: ctrlBathPath+"/viewFundbackPageQuery",
				data: queryData,
				success: function(data) {
					viewModel.gridFundbackData.pageSize(data.fundbackList.size);//每页显示多少条
					viewModel.gridFundbackData.setSimpleData(data.fundbackList.content);
					viewModel.gridFundbackData.totalPages(data.fundbackList.totalPages);
					viewModel.gridFundbackData.totalRow(data.fundbackList.totalElements); 
					viewModel.gridFundbackData.setRowUnFocus();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		//点击删除按钮
		delClick: function(id) {
			var dats = [];
			//获取选中行
			var rows = viewModel.listData.getSelectedRows();
			if(rows==null){
				u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
				return
			}
			var errorMsg = "";
			for(var i=0;i<rows.length;i++){
				var vbillnoTmp = rows[i].getSimpleData().vbillno;
				var vstatusTmp = rows[i].getSimpleData().vstatus;
				if("30011001" != vstatusTmp){
					errorMsg = errorMsg+"单据："+vbillnoTmp+"已经完结，不能删除"
				}
				dats.push(rows[i].getSimpleData());
			}
			if(errorMsg.length>0){
				u.messageDialog({msg:errorMsg,title:"提示", btnText:"OK"});
				return;
			}
			//确认删除对话框
			u.confirmDialog({
				title: "确认",
				msg: "是否删除选中的数据？",
				onOk: function () {
					$.ajax({
						url:  ctrlBathPath+'/delete',
						type: 'POST',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(dats),
						success: function(data) {
							//删除成功后将页面选中行移除
							viewModel.listData.removeRow(viewModel.listData.getSelectedRows());
							//成功后页面提示
							u.messageDialog({msg:"删除成功！",title:"提示", btnText:"OK"});
							//也可重新调用初始化查询方法，
							getInitData();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			});
		},
		//点击返回按钮
		onBackClick: function() {
			viewModel.cardData.clear();
			var md = document.querySelector('#countrysubsidy-mdlayout')['u.MDLayout'];
			md.dBack();

		},
		//点击保存按钮
		onSaveClick: function() {
			//表单校验
			var validate = getvalidate(app,"#countrysubsidy_new");
			if(validate==false){
				return
			};
			//点击保存时将页面表单设置为不可编辑
			viewModel.cardData.setEnable(false);
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			//获取子表车辆信息
			main.countrysubsidyItems=viewModel.gridVehicleData.getSimpleData();
			main.countrysubsidyFundback=viewModel.gridFundbackData.getSimpleData();
			onLoading();
			$.ajax({
				type: 'post',
				url: ctrlBathPath+'/save',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(main),
				success: function(data) {
					//提示框
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					//返回列表界面
					var md = document.querySelector('#countrysubsidy-mdlayout')['u.MDLayout'];
					md.dBack();
					//重新执行页面加载方法
					getInitData();
					onCloseLoading();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
					getInitData();
					onCloseLoading();
				}
			})
		},
		//点击修改按钮
		onUpdateClick: function() {
			//表单校验
//			var validate = getvalidate(app,"#countrysubsidy_edit");
//			if(validate==false){
//				return
//			};
			//点击保存时将页面表单设置为不可编辑
			viewModel.cardData.setEnable(false);
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			//获取子表车辆信息
			main.countrysubsidyItems=viewModel.gridVehicleData.getSimpleData();
			main.countrysubsidyFundback=viewModel.gridFundbackData.getSimpleData();
			onLoading();
			$.ajax({
				type: 'post',
				url: ctrlBathPath+'/update',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(main),
				success: function(data) {
					//提示框
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					//返回列表界面
					var md = document.querySelector('#countrysubsidy-mdlayout')['u.MDLayout'];
					md.dBack();
					//重新执行页面加载方法
					getInitData();
					onCloseLoading();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
					getInitData();
					onCloseLoading();
				}
			})
		},
		afterAdd: function(element, index, data) {
			if (element.nodeType === 1) {
				u.compMgr.updateComp(element);
			}
		},
		//列表页码改变
		pageChangeFunc: function(pageIndex) {
			viewModel.listData.pageIndex(pageIndex);
			viewModel.searchClick();
		},
		//列表每页显示条数改变
		sizeChangeFunc: function(size, pageIndex) {
			pageSize = size;
			viewModel.listData.pageIndex(0);
			viewModel.searchClick();
		},
		//明细界面-车辆明细页码改变
		pageChangeItemFunc: function(pageIndex) {
			viewModel.gridVehicleData.pageIndex(pageIndex);
			viewModel.itemVehiclItmeQueryClick();
		},
		//明细界面-车辆明细每页显示条数改变
		sizeChangeItemFunc: function(size, pageIndex) {
			viewModel.gridVehicleData.pageSize(size);
			viewModel.gridVehicleData.pageIndex(0);
			viewModel.itemVehiclItmeQueryClick();
		},
		//明细界面-回款明细页码改变
		pageChangeFundFunc: function(pageIndex) {
			viewModel.gridFundbackData.pageIndex(pageIndex);
			viewModel.itemVehicleFundbackQueryClick();
		},
		//明细界面-回款明细每页显示条数改变
		sizeChangeFundFunc: function(size, pageIndex) {
			viewModel.gridFundbackData.pageSize(size);
			viewModel.gridFundbackData.pageIndex(0);
			viewModel.itemVehicleFundbackQueryClick();
		},
		comToName : function(field){
			var value = field;
			var name = comValueNameMap[value];
			return name;
		},
		//车辆信息增行
		onVehiAddClick : function(){
			viewModel.gridVehicleData.setEnable(true);
			var r = viewModel.gridVehicleData.createEmptyRow();
			viewModel.gridVehicleData.setRowFocus(r);
		},
		//车辆信息删行
		onVehiDelClick : function(){
			var dats = [];
			var vinStr = '';
			var row = viewModel.gridVehicleData.getSelectedRows();
			if(row==null || row.length==0){
				u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
				return
			}
				for(var i=0;i<row.length;i++){
					var pkItem = row[i].getValue("pk_countrysubsidy_items");
					if(pkItem==null){ 
						viewModel.gridVehicleData.removeRow(row[i]);
					}else{
						var ntotalback = row[i].getValue("ntotalback");
						var vin = row[i].getValue("vvin");
						if(null != ntotalback && ntotalback>0){
							if(vinStr.length==0){
								vinStr = vin;
							}else{
								vinStr = vinStr+","+vin;
							}
						}else{
							dats.push(row[i].getSimpleData());
						}
					}
				}
//
//				u.confirmDialog({
//			
//				title: "确认",
//				msg: "请确认是否删除选中的数据？",
//				onOk: function () {		
					$.ajax({
						type: 'post',
						url: ctrlBathPath+"/delItemWithPk",
						data: JSON.stringify(dats),
						contentType: "application/json ; charset=utf-8",
						success: function(data) {
							var msg = "删除成功！";
							if(vinStr.length>0){
								msg = msg+"VIN:"+vinStr+"车辆已经回款不能删除！如需删除，请先红冲回款金额！";
							}
					
							viewModel.gridVehicleData.removeRows(viewModel.gridVehicleData.getSelectedRows());;
							viewModel.itemVehiclItmeQueryClick();
							u.messageDialog({msg:msg,title:"提示", btnText:"OK"});
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
//				}
//			});
//				}
//			}
		},
		//回款增行
		onFundAddClick: function(){
			viewModel.gridFundbackData.setEnable(true);
			var r = viewModel.gridFundbackData.createEmptyRow();
			viewModel.gridFundbackData.setRowFocus(r);
		},
		//回款信息删行
		onFundDelClick : function(){
			var row = viewModel.gridFundbackData.getCurrentRow();
			if(row.getValue("pk_countrysubsidy_fundback")==null){
				viewModel.gridFundbackData.removeRows(viewModel.gridFundbackData.getSelectedRows());
			}else{
				u.messageDialog({msg:"已经增加的回款不能删除！如需抵扣，请录入负回款进行红冲！",title:"提示", btnText:"OK"});
//				u.confirmDialog({
//					title: "确认",
//					msg: "请确认是否删除选中的数据？",
//					onOk: function () {
//						$.ajax({
//							url: window.cturl+'/marcket/city/deleteTraffic',
//							type: 'DELETE',
//							dataType: 'json',
//							data: JSON.stringify(dats),
//							contentType: "application/json ; charset=utf-8",
//							success: function(data) {
//								viewModel.listData.removeRows(viewModel.listChildData.getSelectedRows());
//								u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
//							},
//							error: function(XMLHttpRequest, textStatus, errorThrown) {
//								errors.error(XMLHttpRequest);
//							}
//						})
//					}
//				});
			}
		}
		,
//		//申报车辆导入
		onUploadFile : function(){
			viewModel.importFlag = "itemImport";
			window.countrysubsid_md = u.dialog({id:'countrysubsid_testDialg',content:"#countrysubsid_dialog_content",hasCloseMenu:true});
			$('.sub-list1-new').css('display','inline-block');
		} ,
		//申报车辆回款导入
		onFundbackUploadFile : function(){
			viewModel.importFlag = "funcBackImport";
			window.countrysubsid_md = u.dialog({id:'countrysubsid_testDialg2',content:"#countrysubsid_dialog_content",hasCloseMenu:true});
			$('.sub-list1-new').css('display','inline-block');
		} ,
		//关闭上传附件界面
		onCloseFileWindow : function(){
			countrysubsid_md.close();
		},
		onUploadExcel : function(){
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_countrysubsidy;
			
			if(null==pk || pk.length==0){
				var msgInfo = "";
				if(viewModel.importFlag=="itemImport"){
					msgInfo = "考虑到国补申报车辆信息比较多，请先保存国补申报单，再导入申报车辆信息！";
				}else if (viewModel.importFlag=="funcBackImport"){
					msgInfo = "考虑到国补申报车辆信息比较多，请先保存国补申报单，再导入申报车辆回款信息！";
				}
				u.messageDialog({msg:msgInfo,title:"提示", btnText:"OK"});
				return false;
			}
			var urlInfo = "";
			if(viewModel.importFlag=="itemImport"){
				urlInfo="/vehicelUpload";
			}else if (viewModel.importFlag=="funcBackImport"){
				urlInfo="/fundbackUpload";
			}
			
			$.ajaxFileUpload({
				url: ctrlBathPath+urlInfo,
				secureuri:false,
				fileElementId:'countrysubsid_fileName',
				dataType: 'json',
				data:{pk_countrysubsidy:pk},
				success: function(data) {
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					if(viewModel.importFlag=="itemImport"){
						viewModel.itemVehiclItmeQueryClick();
					}else if (viewModel.importFlag=="funcBackImport"){
						viewModel.itemVehicleFundbackQueryClick();
						viewModel.itemVehiclItmeQueryClick();
					}
					viewModel.close();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		//导出
		onDownloadExcel : function(){
			var dats = [];
			var pks = ""
			var row = viewModel.gridVehicleData.getSelectedRows();
			if(row==null || row.length==0){
				u.messageDialog({msg:"请选择要导出的数据",title:"提示", btnText:"OK"});
				return
			}
			for(var i=0;i<row.length;i++){
				var pkItem = row[i].getValue("pk_countrysubsidy_items");
				dats.push(row[i].getSimpleData());
				if(pks.length==0){
					pks = pkItem;
				}else{
					pks = pks+","+pkItem;
				}
			}
//			var form = $("#vechileDownload1");
//			form.action=ctrlBathPath+"/vehicelDownload";
//			$("#pkVehicleIds").val(pks);
//			form.submit();
			
			
			 var form = $("<form>");   //定义一个form表单
            form.attr('style', 'display:none');   //在form表单中添加查询参数
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', ctrlBathPath+"/vehicelDownload");

            var input1 = $('<input>');
            input1.attr('type', 'hidden');
            input1.attr('name', 'pkVehicleIds');
            input1.attr('value', pks);
            $('#countrysubsidy-mdlayout').append(form);  //将表单放置在web中 
            form.append(input1);   //将查询参数控件提交到表单上
            form.submit();
//			$.ajax({
//				type: 'post',
//				url: ctrlBathPath+"/vehicelDownload",
//				data: JSON.stringify(dats),
//				contentType: "application/json ; charset=utf-8",
//				success: function(data) {
//					u.messageDialog({msg:"导出成功",title:"提示", btnText:"OK"});
//				},
//				error: function(XMLHttpRequest, textStatus, errorThrown) {
//					errors.error(XMLHttpRequest);
//				}
//			})
		},
		//车辆导入模板下载
		onTempletVehicleExcel : function(){
		    var form = $("<form>");   //定义一个form表单
            form.attr('style', 'display:none');   //在form表单中添加查询参数
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', ctrlBathPath+"/vehicelDownload");

            var input1 = $('<input>');
            input1.attr('type', 'hidden');
            input1.attr('name', 'pkVehicleIds');
            input1.attr('value', "");
            $('#countrysubsidy-mdlayout').append(form);  //将表单放置在web中 
            form.append(input1);   //将查询参数控件提交到表单上
            form.submit();
		},
		//车辆回款导入模板下载
		onTempletFundbackExcel : function(){
		    var form = $("<form>");   //定义一个form表单
            form.attr('style', 'display:none');   //在form表单中添加查询参数
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', ctrlBathPath+"/fundbackDownload");
            $('#countrysubsidy-mdlayout').append(form);  //将表单放置在web中 
            form.submit();
		},
		//打开附件上传界面
		onOpenUploadWin : function(){
			window.countrysubsid_md = u.dialog({id:'countrysubsid_testDialg3',content:"#countrysubsid_dialog_uploadfile",hasCloseMenu:true});
			$('.sub-list1-new').css('display','inline-block');
		},
		//上传附件
		onFileUpload : function(){
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_countrysubsidy;
			var par = { 
					 fileElementId: "countrysubsidybatch_id",  //【必填】文件上传空间的id属性  <input type="file" id="id_file" name="file" />,可以修改，主要看你使用的 id是什么 
					 filepath: pk,   //【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
					 groupname: "COUNTRYSUBSIDY",//【必填】分組名称,未来会提供树节点
					 permission: "read", //【选填】 read是可读=公有     private=私有     当这个参数不传的时候会默认private
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
			 if(null == data){
				 u.messageDialog({msg:"上传文件不能超过1M，请优化后再上传！",title:"提示", btnText:"OK"});
			 }else{
				 if(1 == data.status){//上传成功状态
					 viewModel.fileData.addSimpleData(data.data);
					 u.messageDialog({msg:"上传成功！",title:"提示", btnText:"OK"});
				 }else{//error 或者加載js錯誤
					 u.messageDialog({msg:"上传失败！"+data.message,title:"提示", btnText:"OK"});
				 }
			 }
		 },
		 fileQuery : function(){
			//获取表单
			var row = viewModel.cardData.getCurrentRow();
			if(null==row){
				row = viewModel.listData.getSelectedRows()[0];
			}
			//获取表单数据
			var main = row.getSimpleData();
			var pk = main.pk_countrysubsidy;
			 var par = { 
				     //建议一定要有条件否则会返回所有值
					 filepath: pk, //【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
					 groupname: "COUNTRYSUBSIDY",//【选填】[分組名称,未来会提供树节点]
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
				 form.attr('action', window.ctxfilemng+"file/download?permission=read&id="+pk);
				 $('#countrysubsidy-mdlayout').append(form);  //将表单放置在web中 
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
				 if(!url.startsWith("http://")){
					 url = "http://"+url; 
				 }
				 parent.open(url);
			 }
//			 var attarchId = row[0].getValue("id");
//			 var par = 
//		     {
//	    	   ids:attarchId,//【必填】表的id 支持批量
//	    	   thumbnail :  "100w", //【选填】缩略图
//	    	   cross_url : window.ctxfilemng  ////【选填】跨iuap-saas-fileservice-base 时候必填
//			 }
//			 var f = new interface_file();
//			 f.filesystem_download(par,viewModel.fileViewCallBack);
		 }
//		 , 
//		 //附件查看回调
//		 fileViewCallBack : function(data){
//			 if(1 == data.status){//上传成功状态
//				 viewModel.fileQuery();
//			 }else{
//				 u.messageDialog({msg:"删除失败"+data.message,title:"提示", btnText:"OK"});
//			 }
//		 }
		//获取枚举
	}

	viewModel = u.extend(basicDatas,events,enums,comValueNameMap)
	
//	function enumerate(type){
//		var retData = [];
//		$.ajax({
//			type: 'get',
//			url: window.cturl+"/bd/enums/queryByVtype/"+type,
//			contentType: "application/json ; charset=utf-8",
//			async:false,
//			success: function(data) {
//				retData = data;
//				if(null != data){
//					for(var i=0;i<data.length;i++){
//						window.syc_fixcode_map[data[i].value]=data[i].name;
//					}
//				}
//			},
//			error: function(XMLHttpRequest, textStatus, errorThrown) {
//				errors.error(XMLHttpRequest);
//			}
//		});
//		return retData;
//	}

	var getInitData = function() {
		viewModel.searchData.createEmptyRow();
		viewModel.searchClick();
		enumerate("2013");
		enumerate("2014");
		
//		//是否
//		comValueNameMap["10011001"]="是";
//		comValueNameMap["10011002"]="否";
//		//单据状态
//		comValueNameMap["30011001"]="已保存";
//		comValueNameMap["30011002"]="已完结";
//		//申报状态
//		comValueNameMap["20131001"]="已申报";
//		comValueNameMap["20131002"]="未申报";
//		//未申报状态
//		comValueNameMap["20141001"]="未销售给终端";
//		comValueNameMap["20141002"]="已销售未上牌";
//		comValueNameMap["20141003"]="已上牌无资料";
//		comValueNameMap["20141004"]="已上牌有资料";
//		comValueNameMap["20141005"]="未交车";
//		var cancelButton = document.body.querySelector(".sub-list1-cancel");
//		u.on(cancelButton,'click', function(){//取消
//			 var status = viewModel.importData.getCurrentRow().status;
//			if(status == 'new'){
//				var row = viewModel.importData.getCurrentRow();
//				viewModel.importData.removeRows(viewModel.importData.getSelectedRows());
//			}
//			else if(window.md.oldRow){
//				reSetData(viewModel.importData.getFocusRow(), window.md.oldRow);
//				window.md.oldRow = null;
//			}
//			md.close();
//		});
	}
	var refid;
	var dom;
	function ref() {
		 var pk='';

		 $('.deptref').each(function(i,val){
		     	var $that=$(this);
		     	dom = $that;
				var options = {
						refCode:"dept",
						selectedVals:pk,
						isMultiSelectedEnabled:false
				};
				refComp.initRefComp($that,options);
				refid ='#refContainer' + $that.attr('id');
		 	});
		 $('.vdeclarelegalref').each(function(i,val){
		     	var $that=$(this);
		     	dom = $that;
				var options = {
						refCode:"user",
						selectedVals:pk,
						isMultiSelectedEnabled:false
				};
				refComp.initRefComp($that,options);
				refid ='#refContainer' + $that.attr('id');
		 	});
		 $('#countrysubsidy_city_edit').each(function(i,val){
		     	var $that=$(this);
		     	dom = $that;
				var options = {
						refCode:"city",
						selectedVals:pk,
						isMultiSelectedEnabled:false
				};
				refComp.initRefComp($that,options);
				refid ='#refContainer' + $that.attr('id');
		 	});
		 $('#countrysubsidy_city_add').each(function(i,val){
		     	var $that=$(this);
		     	dom = $that;
				var options = {
						refCode:"city",
						selectedVals:pk,
						isMultiSelectedEnabled:false
				};
				refComp.initRefComp($that,options);
				refid ='#refContainer' + $that.attr('id');
		 	});
		 var $input=dom.find('input');
		 $input.val(name);
    }

	return {
		init: function(content,tabid) {
			content.innerHTML = html;
			window.vm = viewModel;

			app = u.createApp({
				el: '#'+tabid,
				model: viewModel
			});
			getInitData();
			ref();
			viewModel.listData.setEnable(false);
		}
	}
});