define(['iReferComp','refComp','error','text!./bterdept.html', 'css!./productmgr.css','uuitree', 'uuigrid','require','utilrow' ],function(iReferComp,refComp,errors,html) {
			'use strict';
	 if (window.addEventListener) {  // all browsers except IE before version 9
		 window.removeEventListener('message',refValue,false);
		 window.addEventListener('message',refValue,false);
	 }else if (window.attachEvent) {   // IE before version 9
		 window.detachEvent("onmessage", refValue);
		 window.attachEvent("onmessage", refValue);
	 }else{
		 alert("浏览器不支持");
	 }
	 var refid;
	 var dom;
			
	 function refValue( event ) {
		 var para = JSON.parse(event.data);
		 
		 // 参照code
		 var refCode = para.refCode;
		 var selected = para.data;
		 var pk='';
	
		 if (selected  && selected.length > 0) {
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
				
		if (window.addEventListener) {  // all browsers except IE before version 9
			window.removeEventListener('message',refValue,false);
	    }else if (window.attachEvent) {   // IE before version 9
	    	window.detachEvent("onmessage", refValue);
	    }
				
		$('#dept_pk_user').each(function(i,val){
		     	var $that=$(this);
		     	dom = $that;
				var options = {
						refCode:"user",
						selectedVals:"pk",
						isMultiSelectedEnabled:false,
				};
				refComp.initRefComp($that,options);
				refid ='#refContainer' + $that.attr('id');
		 	});
		$('#dept_pkLeader').each(function(i,val){
	     	var $that=$(this);
	     	dom = $that;
			var options = {
					refCode:"user",
					selectedVals:"pk",
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
	 	});
		$('#dept_pk_corpref').each(function(i,val){
	     	var $that=$(this);
	     	dom = $that;
			var options = {
					refCode:"corp",
					selectedVals:"pk",
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
	 	});
	 	$('#dept_pkFathedept').each(function(i,val){
	     	var $that=$(this);
	     	dom = $that;
			var options = { 
					refCode:"dept",
					selectedVals:"pk",
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
	 	});
		 
		 var $input=dom.find('input');
		 $input.val(name);
				
     }
	 

	 var msg = {}; 
		msg.data = [ 
//		{"memo":"备注","refcode":"BJ","refname":"北京","refpk":"780aca16-e1a3-11e5-aa70-0242ac11001d","selected":true}, 
//		{"memo":"备注","refcode":"TJ","refname":"天津","refpk":"65c2c424-e1a3-11e5-aa70-0242ac11001d","selected":true} 
		]; 

		var str = JSON.stringify(msg); 
		top.postMessage(str, "*");
	 
	
	
	
	var _CONST = {
			STATUS_ADD: 'add',
			STATUS_EDIT: 'edit'
		},
		app, viewModel, basicDatas, computes, events, treeSetting,computes,pagesize=10,oper;

	treeSetting = {
			"check": {
				"enable": false
			},
			view:{
	            showLine:false,
	            selectedMulti:false
	        },
			callback: {
				onClick: function(e, id, node) {//点击树事件
//					var corpname=$('#bucorpname').val();
//					alert(corpname)
//					viewModel.listData.setValue("corpname",corpname);//用于保存
					
					$.ajax({
						type: 'get',
						url: window.cturl+'/bd/dept/queryPageByDept?pk_dept='+node.id,
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						success: function(data) {
							
							var data = data['content'];
							iReferComp.setFormData('#user_deptref', data);
							debugger;
//							viewModel.listData.setSimpleData(data)

						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			}

	}
	
	basicDatas = {
			treeSetting : treeSetting,
			treeData: new u.DataTable({//树 表
				meta: {
					pk_dept: {
						type: 'string'
					},
					deptname:{
						type: 'string'
					},
					pkFathedept: {
						type: 'string'
					}
				}
			}),
			treecorpData : new u.DataTable({// 树 表
				meta : {
					pk_corp : {
						type : 'string'
					},
					unitcode : {
						type : 'string'
					},
					unitname : {
						type : 'string'
					}
				}
			}),
			listData: new u.DataTable({//实体表
				meta: {
					pk_dept: {
						type: 'string'
					},
					deptcode: {
						type: 'string',required: true,maxLength: 50
					},
					deptname: {
						type: 'string',required: true,maxLength: 100
					},
					pk_user: {
						type: 'string'
					},
					pkLeader: {
						type: 'string'
					},
					pk_corp: {
						type: 'string'
					},
					canceled: {
						type: 'int'
					},
					pkFathedept: {
						type: 'string'
					},
					fathedept: {
						type: 'string'
					},
					isbudept: {
						type: 'string'
					},
					iscbzx: {
						type: 'string'
					},
					cbzx: {
						type: 'string'
					},
					//品牌ID
					def1: {
						type: 'string'
					},
					//品牌名称
					def2: {
						type: 'string'
					}
				}
			}),
			brandData : new u.DataTable({//品牌实体表
				meta: {
					pk_brand: {type: 'string'},
					vbrandcode: {type: 'string'},
					vbrandname: {type: 'string'}
				}
			}),
			listdeptData: new u.DataTable({//实体表
				meta: {
					pk_dept: {
						type: 'string'
					},
					pk_corp: {
						type: 'string',required: true
					},
					corpname: {
						type: 'string',required: true
					}
				}
			}),
			enable : [ {
				"value" : "1",
				"name" : '启用'
			}, {
				"value" : "2",
				"name" : '停用'
			} ],
			gridStatus: ko.observable("read")
	};

	computes = {
			refFormInputValue: function(field) {
				return ko.pureComputed({
					read: function() {

						if (viewModel.gridStatus() == 'add') {
							var fr = this.getFocusRow()
							return fr != null ? fr.ref(field) : '';
						} else if (viewModel.gridStatus() == 'edit') {
							var srs = this.getSelectedRows()
							return srs.length > 0 ? srs[0].ref(field) : ''
						}


					},
					owner: viewModel.listData
				})
			}
	};
	//按钮事件
	events = {
			//新增
			addClick: function() {
				var pk_corp=$('#user_bucorpcode').val();
				if(pk_corp==""){
					u.messageDialog({
						msg : "请先选择所属公司",
						title : "提示",
						btnText : "OK"
					});
					return
				}
				var row = viewModel.listData.getCurrentRow();
				if(row==null){
					row = viewModel.listData.createEmptyRow();
				}
				viewModel.gridStatus("edit");
				$("#testRefMuGridTree3").attr('disabled', true);
				document.getElementById("dept_zhezhao").style.display ="block";//树遮照
//				$(app.getComp('iscbzx').comp.element).find('.u-checkbox-input').attr('disabled', false);
//				$(app.getComp('isbudept').comp.element).find('.u-checkbox-input').attr('disabled', false);
				oper = 'add';
				var deptname = viewModel.listData.getCurrentRow().getSimpleData().deptname;
				var pk_dept = viewModel.listData.getCurrentRow().getSimpleData().pk_dept;
				var pk_corp=$('#user_bucorpcode').val();
				viewModel.listData.removeAllRows();
				var r = viewModel.listData.createEmptyRow();
				viewModel.listData.setValue("canceled","1");
				viewModel.listData.setValue("pkFathedept",pk_dept);//用于保存
				viewModel.listData.setValue("pk_corp",pk_corp);//用于保存
				iReferComp.setFormData('#user_deptref',new Array(viewModel.listData.getCurrentRow().getSimpleData()) );//viewModel.listData.getSimpleData()
				viewModel.listData.setRowFocus(r)
			},
			//编辑
			editClick: function() {
				var row = viewModel.listData.getSelectedRows();
				if (row.length != 1) {
					u.messageDialog({
						msg : "请选择要修改的数据",
						title : "提示",
						btnText : "OK"
					});
					return
				}
				viewModel.gridStatus("edit");
				var pkFathedept=viewModel.listData.getCurrentRow().getSimpleData().pkFathedept;
				if(pkFathedept==null||pkFathedept==""){
					$("#testRefMuGridTree3").attr('disabled', true);
				}
//				var iscbzx=viewModel.listData.getCurrentRow().getSimpleData().iscbzx;
//				if(iscbzx=="Y"){
//					$("#testRefMuGridTree4").attr('disabled', true);
//				}
				oper = 'edit';
				var row = viewModel.listData.getCurrentRow();
//				$(app.getComp('iscbzx').comp.element).find('.u-checkbox-input').attr('disabled', false);
//				$(app.getComp('isbudept').comp.element).find('.u-checkbox-input').attr('disabled', false);
				document.getElementById("dept_zhezhao").style.display ="block";//树遮照 
			},
			//删除
			delClick: function() {
				
				var row = viewModel.listData.getSelectedRows();
				if (row.length != 1) {
					u.messageDialog({
						msg : "请选择要删除的数据",
						title : "提示",
						btnText : "OK"
					});
					return
				}
				var pk_corp = viewModel.listData.getCurrentRow().getSimpleData().pk_corp;
				u.confirmDialog({
					title: "确认",
					msg: "请确认是否删除选中的部门档案？",
					onOk: function () {
						$.ajax({
							url : window.cturl+'/bd/dept/delete?pk_dept='+ row[0].getSimpleData().pk_dept,
							type : 'DELETE',
							dataType : 'json',
							contentType : "application/json ; charset=utf-8",
							success: function(data) {
								if(data.flag==1){
									
									viewModel.listData.removeRows(viewModel.listData.getSelectedRows());
									u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
									getInitData(pk_corp);
								}else{
									u.messageDialog({
										msg : data.msg,
										title : "提示",
										btnText : "OK"
									});
								}
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
				});
			},
			//停用按钮
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
					var canceled = viewModel.listData.getCurrentRow().getSimpleData().canceled;
					if (canceled == "2") {
						u.messageDialog({
							msg : "部门状态已停用，不需要重新停用！",
							title : "提示",
							btnText : "OK"
						});
						return
					}
					row.setValue("canceled", "2");
				}
				var pk_corp = viewModel.listData.getCurrentRow().getSimpleData().pk_corp;
				$.ajax({
					type : 'POST',
					url: window.cturl+'/bd/dept/update',
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
						getInitData(pk_corp);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			//启用按钮
			qyClick : function() {
				
				var row = viewModel.listData.getCurrentRow();
				if (row == null) {
					u.messageDialog({
						msg : "请选择要启用的数据",
						title : "提示",
						btnText : "OK"
					});
					return
				} else {
					var canceled = viewModel.listData.getCurrentRow().getSimpleData().canceled;
					if (canceled == "1") {
						u.messageDialog({
							msg : "部门状态已启用，不需要重新启用！",
							title : "提示",
							btnText : "OK"
						});
						return
					}
					row.setValue("canceled", "1");
				}
				var pk_corp = viewModel.listData.getCurrentRow().getSimpleData().pk_corp;
				$.ajax({
					type : 'POST',
					url: window.cturl+'/bd/dept/update',
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
						getInitData(pk_corp);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			//取消
			cancelClick: function() {
				
				viewModel.gridStatus("read");
//				var row = viewModel.listData.getSelectedRows();
//				viewModel.listData.removeRows(viewModel.listData.getSelectedRows());
//				viewModel.listData.setSimpleData(viewModel.listData.getRow(0));
//				$(app.getComp('iscbzx').comp.element).find('.u-checkbox-input').attr('disabled', true);
//				$(app.getComp('isbudept').comp.element).find('.u-checkbox-input').attr('disabled', true);
				document.getElementById("dept_zhezhao").style.display ="none";//树遮照
			},
			//保存
			saveClick: function(row, e) {
//				var result = app.compsValidateMultiParam({element:document.querySelector("#input1"), showMsg: true});
				var vlidate = getvalidate(app, "#input1");
				if (vlidate == false){
//					u.messageDialog({msg:"请检查必输项!",title:"操作提示",btnText:"确定"});
				}else{
//					var iscbzx = viewModel.listData.getCurrentRow().getSimpleData().iscbzx;
//					var isbudept = viewModel.listData.getCurrentRow().getSimpleData().isbudept;
//					if(iscbzx!="Y"){
//						viewModel.listData.setValue("iscbzx","N");
//					}
//					if(isbudept!="Y"){
//						viewModel.listData.setValue("isbudept","N");
//					}	
					
					var dat = viewModel.listData.getCurrentRow();
					var pk_corp = viewModel.listData.getCurrentRow().getSimpleData().pk_corp;
					//新增保存
					if (oper === 'add'){ 
						$.ajax({
							type: 'POST',
							url: window.cturl+'/bd/dept/save?pk_corp='+ pk_corp,
							dataType: 'json',
							contentType: "application/json ; charset=utf-8",
							data: JSON.stringify(dat.getSimpleData()),
							success: function(data) {
								if(data.flag==1){
									debugger;
									viewModel.listData.setRowUnFocus(row);
									u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
									getInitData(pk_corp);
									viewModel.gridStatus("read");
									document.getElementById("dept_ishasdept").style.display ="none";//树遮照
								}else{
									u.messageDialog({
										msg : data.msg,
										title : "提示",
										btnText : "OK"
									});
	//								viewModel.treeData.clear()
								}
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}else if(oper === 'edit'){ //编辑保存
						$.ajax({
							type: 'POST',
							url: window.cturl+'/bd/dept/update?pk_corp='+ pk_corp,
							dataType: 'json',
							contentType: "application/json ; charset=utf-8",
							data: JSON.stringify(dat.getSimpleData()),
							success: function(data) {
								if(data.flag==1){
									debugger;
									viewModel.listData.setRowUnFocus(row);
									u.messageDialog({msg:"修改成功",title:"提示", btnText:"OK"});
									getInitData(pk_corp);
									viewModel.gridStatus("read")
								}else{
									u.messageDialog({
										msg : data.msg,
										title : "提示",
										btnText : "OK"
									});
								}
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								errors.error(XMLHttpRequest);
							}
						})
					}
					document.getElementById("dept_zhezhao").style.display ="none";//树遮照
				}
			},
			roleaddClick: function(e,index,colIndex) {
				 var row = viewModel.listdeptData.getSelectedRows();
				 viewModel.treecorpData.clear();
				 document.getElementById('dept_light').style.display='block';
				 document.getElementById('dept_fade').style.display='block';
//			   $.ajax({
//					type: 'get',
//					url : window.cturl+'/bter/corp/queryPages',
////					url:window.cturl+'/security/extroledefine/page',
//					success: function(data) {
//						viewModel.treecorpData.setSimpleData(data['content']);
//						viewModel.treecorpData.setRowUnFocus();
//					}
//				})
				 $.ajax({
						type: 'get',
						url:window.cturl+"/bd/dept/corppage?page.size="+pagesize+"",
						success: function(data) {
							viewModel.treecorpData.pageSize(data.size);//每页显示多少条
							viewModel.treecorpData.setSimpleData(data.content);
							viewModel.treecorpData.totalPages(data.totalPages);
							viewModel.treecorpData.totalRow(data.totalElements); 
//							viewModel.treecorpData.setRowUnFocus();
							viewModel.treecorpData.setAllRowsUnSelect();
							
//							var total = data.totalElements;//共多少条
//							viewModel.treecorpData.setSimpleData(data['content']);
//							viewModel.treecorpData.setRowUnFocus();
//							viewModel.treecorpData.pageSize(pagesize);//每页显示多少条
//							var pages = CalculatePageCount(pagesize,total);//计算共多少页
//							viewModel.treecorpData.totalPages(pages); //共多少页
//							viewModel.treecorpData.totalRow(total); 
//							viewModel.searchData.createEmptyRow();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
			  },
			  
			  pageChangeFunc: function(pageIndex) {
					
					$.ajax({
						type: 'get',
						url:window.cturl+"/bd/dept/corppage?page.size="+pagesize+"&page="+(pageIndex+1)+"",
						success: function(data) {
							
							var total = data.totalElements;//共多少条
							var data =data['content'];
							viewModel.treecorpData.setSimpleData(data);
							viewModel.treecorpData.setAllRowsUnSelect();
							
//							viewModel.listData.pageSize(2);//每页显示多少条
							var pages = CalculatePageCount(pagesize,total);//计算共多少页
							viewModel.treecorpData.totalPages(pages); //共多少页
							viewModel.treecorpData.totalRow(total); 
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				},
				sizeChangeFunc: function(size, pageIndex) {
					pagesize=size;
					$.ajax({
						type: 'get',
						url:window.cturl+"/bd/dept/corppage?page.size="+pagesize+"",
						success: function(data) {
							var total = data.totalElements;//共多少条
							viewModel.treecorpData.setSimpleData(data['content']);
							viewModel.treecorpData.setRowUnFocus();
							viewModel.treecorpData.pageSize(pagesize);//每页显示多少条
							var pages = CalculatePageCount(pagesize,total);//计算共多少页
							viewModel.treecorpData.totalPages(pages); //共多少页
							viewModel.treecorpData.totalRow(total); 
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				},
			  
			  
			  okClick: function okClick(e,index,colIndex) {
					var rowrole = viewModel.treecorpData.getSelectedRows();
					if(rowrole.length!=1){
						u.messageDialog({msg:"请选择一个所属组织",title:"提示", btnText:"OK"});
						return;
					}
					document.getElementById('dept_light').style.display='none';
					document.getElementById('dept_fade').style.display='none';
					var pk_corp=$('#user_bucorpcode').val();
					if (pk_corp!=""){
						viewModel.listdeptData.clear()
					}
					for(i=0;i<rowrole.length;i++){
						viewModel.listdeptData.addSimpleData([
						          {"pk_corp":rowrole[i].getSimpleData().pk_corp,"corpname":rowrole[i].getSimpleData().unitname}
						          								])
					}
					var sIndices = [];
					var rows = viewModel.listdeptData.getSelectedRows();
					for(var i=0;i<rows.length;i++){
						var id = rows[i].getSimpleData().id;
							if(null == id){
								sIndices.push(rows[i]);
							}
					}
					viewModel.listdeptData.setRowsSelect(sIndices);
			},
			//关闭组织选择界面
			canceluserClick: function cancelClick(e,index,colIndex) {
				document.getElementById('dept_light').style.display='none';
				document.getElementById('dept_fade').style.display='none';
			},
			//品牌选择
			brandAddClick: function() {
				document.getElementById('dept_brand').style.display='block';
				document.getElementById('dept_fade').style.display='block';
				var param = {};
				param["psize"] = 100;
				param["pindex"] = 0;
				$.ajax({
					type: 'post',
					url:window.cturl+"/baseData/brand/querypage",
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(param),
					success: function(data) {
						viewModel.brandData.setSimpleData(data['content']);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			//品牌选择确认
			onBrandClick: function okClick(e,index,colIndex) {
				document.getElementById('dept_brand').style.display='none';
				document.getElementById('dept_fade').style.display='none';
				var brandRows = viewModel.brandData.getSelectedRows();
				if(brandRows.length==0){
					u.messageDialog({msg:"请选择品牌",title:"提示", btnText:"OK"});
					return;
				}
				var pkBrandStr = "";
				var brandCodeStr = "";
				for(var i=0;i<brandRows.length;i++){
					var pkBrandTmp = brandRows[i].getSimpleData().pk_brand;
					var brandCodeTmp = brandRows[i].getSimpleData().vbrandcode;
					if(i==0){
						pkBrandStr = pkBrandTmp;
						brandCodeStr = brandCodeTmp;
					}else{
						pkBrandStr = pkBrandStr+","+pkBrandTmp;
						brandCodeStr = brandCodeStr+","+brandCodeTmp;
					}
				}
				viewModel.listData.getCurrentRow().setValue("def1",pkBrandStr);
				viewModel.listData.getCurrentRow().setValue("def2",brandCodeStr);
			},
			//品牌选择取消
			cancelBrandClick: function cancelClick(e,index,colIndex) {
				document.getElementById('dept_brand').style.display='none';
				document.getElementById('dept_fade').style.display='none';
			},
			afterAdd: function(element, index, data) {
				if (element.nodeType === 1) {
					u.compMgr.updateComp(element);
				}
			},
			corpClick:function corpClick() {
				var pk_corp=$('#user_bucorpcode').val();
				viewModel.gridStatus("read")
				
				$.ajax({
					type: 'get',
					url: window.cturl+'/bd/dept/queryTree?pk_corp='+ pk_corp,
					success: function(data) {
						if(data.flag==1){
							
							var data = data.data['list'];
							viewModel.treeData.setSimpleData(data)
						}else{
							u.messageDialog({
								msg : data.msg,
								title : "提示",
								btnText : "OK"
							});
							viewModel.treeData.clear()
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
				
			},
			getdeptref:function(){
				var row = viewModel.listData.getSelectedRows();
				if(row.length==1){
					var pk_corp=row[0].getSimpleData().pk_corp;
					var deptname=row[0].getSimpleData().deptname;
//					if(pk_corp!=null&&pk_corp!=""&&deptname!=null&&deptname!=""){
						iReferComp.setFilterPks("pk_corp", pk_corp, '#dept1');
						iReferComp.setFilterPks("deptname", deptname, '#dept1');
//					}else{
//						iReferComp.setFilterPks("pk_corp", null, '#dept1');
//						iReferComp.setFilterPks("pk_corp", null, '#dept1');
//					}
				}
			},
			getcbzxref:function(){
				var row = viewModel.listData.getSelectedRows();
				if(row.length==1){
					var pk_corp=row[0].getSimpleData().pk_corp;
						iReferComp.setFilterPks("pk_corp", pk_corp, '#cbzx1');
//						iReferComp.setFilterPks("deptname", deptname, '#cbzx1');
				}
			},
			searchclick:function(){
				if($("#searchtxt").val()=="请输入关键词"){
					u.messageDialog({msg:$("#searchtxt").val(),title:"提示", btnText:"OK"});
				}else{
					var deptname = $("#searchtxt").val();
//					viewModel.treeData.clear();
					$.ajax({
						type: 'get',
						url: window.cturl+'/bd/dept/search?deptname='+ deptname,
						dataType : 'json',
						contentType : "application/json ; charset=utf-8",
						success: function(data) {
							if(data.flag==1){
								var data = data.data['list'];
								document.getElementById("dept_ishasdept").style.display ="none";//树遮照
								viewModel.treeData.setSimpleData(data)
							}else{
								u.messageDialog({
									msg : data.msg,
									title : "提示",
									btnText : "OK"
								});
								document.getElementById("dept_ishasdept").style.display ="block";//树遮照
								viewModel.treeData.clear()
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			}
	}


	viewModel = u.extend(basicDatas,computes, events)


	//初始化树
	var getInitData = function(pk_corp) {
		viewModel.gridStatus("read")
//		var pk_corp = viewModel.listData.getCurrentRow().getSimpleData().pk_corp;
//		alert(pk_corp);
		
		$.ajax({
			type: 'get',
			url: window.cturl+'/bd/dept/queryTree?pk_corp='+ pk_corp,
			success: function(data) {
				if(data.flag==1){
					
					var data = data.data['list'];
					viewModel.treeData.setSimpleData(data)
//					$(app.getComp('iscbzx').comp.element).find('.u-checkbox-input').attr('disabled', true);
//					$(app.getComp('isbudept').comp.element).find('.u-checkbox-input').attr('disabled', true);
				}else{
					u.messageDialog({
						msg : data.msg,
						title : "提示",
						btnText : "OK"
					});
					viewModel.treeData.clear()
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		});
		
	}
	
	viewModel.listdeptData.on('pk_corp.valuechange',function(obj){
		var pk_corp=$('#user_bucorpcode').val();
		viewModel.gridStatus("read")
		
		$.ajax({
			type: 'get',
			url: window.cturl+'/bd/dept/queryTree?pk_corp='+ pk_corp,
			success: function(data) {
				if(data.flag==1){
					
					var data = data.data['list'];
					document.getElementById("dept_ishasdept").style.display ="none";//树遮照
					viewModel.treeData.setSimpleData(data)
				}else{
//					u.messageDialog({
//						msg : data.msg,
//						title : "提示",
//						btnText : "OK"
//					});
					document.getElementById("dept_ishasdept").style.display ="block";//树遮照
					viewModel.treeData.clear()
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		})
	});
	
//	viewModel.listData.on('iscbzx.valuechange',function(obj){
//		var iscbzx = viewModel.listData.getCurrentRow().getSimpleData().iscbzx;
//		if(iscbzx=='Y'){
//			$("#testRefMuGridTree4").attr('disabled', true);
//			viewModel.listData.setValue("cbzx",null);
//		}else{
//			$("#testRefMuGridTree4").attr('disabled', false);
//		}
//	});
	
	return {
		init: function(content,tabid) {
			
			content.innerHTML = html;
			window.vm = viewModel;

			app = u.createApp({
				el: '#'+tabid,
				model: viewModel
			})

//			$(app.getComp('iscbzx').comp.element).find('.u-checkbox-input').attr('disabled', true);
//			$(app.getComp('isbudept').comp.element).find('.u-checkbox-input').attr('disabled', true);
//			getInitData();
			
//			viewModel.listdeptData.on('pk_corp.valueChange', function(obj){
//				iReferComp.setFilterPks(obj.field, obj.newValue, '#user_bucorpcode');
//				
//			})

			$("#listclear").click(function(){
				viewModel.listData.clear()
			})
			
			$("#searchtxt").click(function(){
				$(this).val("");
			})
			$("#searchtxt").blur(function(){
				if($(this).val() == "" || $(this).val() == "请输入关键词")
				 $(this).val("请输入关键词");
			});
			$('#bucorpname').attr('title') == '' && $('.ztree').css('border-top','none') ;
			var contentMinHeight = $('#content').css('min-height');
		    contentMinHeight = contentMinHeight.substr(0,contentMinHeight.length-2);
		    var tabsMinHeight = $('#content .nav-tabs').css('height');
		    tabsMinHeight = tabsMinHeight.substr(0,tabsMinHeight.length-2);
		    var tabContentHeight = (contentMinHeight-tabsMinHeight)+"px";
			$('#dept_list .u-mdlayout-page-section').css('min-height',tabContentHeight);
		}
	}


});