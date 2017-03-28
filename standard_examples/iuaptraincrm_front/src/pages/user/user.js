define(['iReferComp','refComp','error','text!./user.html', 'css!./productmgr.css','uuigrid','utilrow'], function(iReferComp,refComp,errors,html) {
	'use strict';
	
	var refid;
	var dom;
	 function userref() {
		 var pk='';

		 $('#user_user').each(function(i,val){
		     	var $that=$(this);
		     	dom = $that;
				var options = {
						refCode:"psn",
						selectedVals:pk,
						isMultiSelectedEnabled:false
				};
				refComp.initRefComp($that,options);
				refid ='#refContainer' + $that.attr('id');
		 	});
		 $('#user_corp').each(function(i,val){
	     	var $that=$(this);
	     	dom = $that;
			var options = {
					refCode:"corp",
					selectedVals:pk,
					isMultiSelectedEnabled:false
			};
			refComp.initRefComp($that,options);
			refid ='#refContainer' + $that.attr('id');
	 	});
		 $('#userinfo_deptref').each(function(i,val){
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
		 var $input=dom.find('input');
		 $input.val(name);
     }
	 var msg = {}; 
		var str = JSON.stringify(msg); 
		top.postMessage(str, "*");
		var _CONST = {
			STATUS_ADD: 'add',
			STATUS_EDIT: 'edit',
			STATUS_POSITION: 'position'
		},
		app,
		viewModel,
		treeSetting,
		treeSetting2,
		basicDatas,
		computes,
		oper,
		events,
		lsroleid,
		lsrolecode,
		lsrolename,
		funcid,
		pagesize=10,// 记录当前页面每页显示多少条
		lsrolecode;
	basicDatas = {
		listData: new u.DataTable({
			meta: {
				id: {
					type: 'string'
				},
				name: {
					type: 'string'
				},
				loginName: {
					type: 'string'
				},
				psntel: {
					type: 'string'
				},
				pkCorp: {
					type: 'string'
				},
				pkDept: {
					type: 'string'
				},
				pkPsndoc: {
					type: 'string'
				},
				psnseal: {
					type: 'string'
				},
				pkUsertype: {
					type: 'string'
				},
				pk_propt: {
					type: 'string'
				},
				email: {
					type: 'string'
				},
				password: {
					type: 'string'
				},lockedT: {
					type: 'string'
				},
				locked: {
					type: 'string'
				}
			}
		}),
		cardData: new u.DataTable({
			meta: {
				id: {
					type: 'string'
				},
				name: {
					type: 'string',required: true,maxLength: 100
				},
				loginName: {
					type: 'string',required: true,maxLength: 50
				},
				psntel: {
					type: 'string',required: true
				},
				pkCorp: {
					type: 'string',required: true
				},
				pkDept: {
					type: 'string',required: true
				},
				pkPsndoc: {
					type: 'string',required: true
				},
				psnseal: {
					type: 'string'
				},
				pkUsertype: {
					type: 'string',required: true
				},
				pk_propt: {
					type: 'string',required: true
				},
				email: {
					type: 'string',required: true
				},
				password: {
					type: 'string'
				},
				lockedT: {
					type: 'string'
				},				
				locked: {
					type: 'string'
				}
			}
		}),
		searchData: new u.DataTable({
			meta: {
				name: {
					type: 'string'
				},
				loginName: {
					type: 'string'
				},
				pkCorp: {
					type: 'string'
				}
			}
		}),
		roleData: new u.DataTable({
			meta: {
				id: {
					type: 'string'
				},
				position_name: {
					type: 'string'
				},
				position_code: {
					type: 'string'
				},
				pk_org: {
					type: 'string'
				},
				pk_dept: {
					type: 'string'
				},
				position_status: {
					type: 'string'
				},
				remark: {
					type: 'string'
				},
				creator: {
					type: 'string'
				},
				creationtime: {
					type: 'string'
				},
				modifier: {
					type: 'string'
				},
				modifiedtime: {
					type: 'string'
				},
				dr: {
					type: 'string'
				},
				ts: {
					type: 'string'
				}
			}
		}),
		// 岗位
		rolelistData: new u.DataTable({
			meta: {
				id: {
					type: 'string'
				},
				position_name: {
					type: 'string'
				},
				position_code: {
					type: 'string'
				},
				pk_org: {
					type: 'string'
				},
				pk_dept: {
					type: 'string'
				},
				position_status: {
					type: 'string'
				},
				remark: {
					type: 'string'
				},
				creator: {
					type: 'string'
				},
				creationtime: {
					type: 'string'
				},
				modifier: {
					type: 'string'
				},
				modifiedtime: {
					type: 'string'
				},
				dr: {
					type: 'string'
				},
				ts: {
					type: 'string'
				}
			}
		}),
		formStatus: ko.observable(_CONST.STATUS_ADD),
		gridStatus : ko.observable("read")
	};

	computes = {
		refFormInputValue: function(field) {
			return ko.pureComputed({
				read: function() {
					if (viewModel.formStatus() == _CONST.STATUS_ADD) {
						var fr = this.getFocusRow()
						return fr != null ? fr.ref(field) : '';
					} else if (viewModel.formStatus() == _CONST.STATUS_EDIT) {
						var srs = this.getSelectedRows()
						return srs.length > 0 ? srs[0].ref(field) : ''
					}
				}
			})
		}
	}

	events = {
			pageChangeFunc: function(pageIndex) {
				$.ajax({
					type: 'get',
					url:window.cturl+"/system/user/querypage?page.size="+pagesize+"&page="+(pageIndex+1)+"",
					success: function(data) {
						var total = data.totalElements;// 共多少条
						var data =data['content'];
						iReferComp.setSimpleData(data, 'user_grid3',false);
						viewModel.listData.setAllRowsUnSelect();
						var pages = CalculatePageCount(pagesize,total);// 计算共多少页
						viewModel.listData.totalPages(pages); // 共多少页
						viewModel.listData.totalRow(total); 
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
			},
			sizeChangeFunc: function(size, pageIndex) {
				pagesize=size;
				getInitData();
			},
		addClick: function() {
			var md = document.querySelector('#user-mdlayout')['u.MDLayout']
			viewModel.cardData.clear();
			viewModel.rolelistData.clear();
			 document.getElementById('user_rolediv').style.display='none';
			md.dGo('user_addPage')
			viewModel.formStatus(_CONST.STATUS_ADD)
			viewModel.gridStatus("edit");
			oper="add";
			var r = viewModel.cardData.createEmptyRow()
		},
		editClick: function() {
			if (viewModel.listData.getSelectedRows().length != 1) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds:"2"
				});
				return
			}
			oper="update";
			viewModel.gridStatus("edit");
//			 document.getElementById('user_rolediv').style.display='block';
			var md = document.querySelector('#user-mdlayout')['u.MDLayout']
			md.dGo('user_addPage')
			viewModel.formStatus(_CONST.STATUS_EDIT)
			var r = viewModel.listData.getSelectedRows()[0];
			iReferComp.setFormData('#user_addPage', r.getData().data,"user_grid3");
//			var listdat = viewModel.listData.getCurrentRow();
//			lsroleid = listdat.getSimpleData().id;
//			lsrolecode = listdat.getSimpleData().loginName;
//			lsrolename = listdat.getSimpleData().name;
//			$.ajax({
//				type: 'get',
//				url:window.cturl+'/security/ext_user_role/queryuser/'+lsroleid,
//				success: function(data) {
//					viewModel.rolelistData.setSimpleData(data);
//					viewModel.rolelistData.setAllRowsUnSelect();
//				}
//			})
		},
		delClick: function() {
			if (viewModel.listData.getSelectedRows().length ==0) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds:"2"
				});
				return
			}
			var dats = [];
			var rows = viewModel.listData.getSelectedRows()
			for(var i=0;i<rows.length;i++){
				dats.push(rows[i].getSimpleData());
			}
			u.confirmDialog({
				title : "确认",
				msg : "请确认是否删除用户信息？",
				onOk : function() {
					$.ajax({
						type: 'post',
						url: window.cturl+'/system/user/removelist',
						dataType: 'json',
						contentType: "application/json ; charset=utf-8",
						data: JSON.stringify(dats),
						success: function(data) {
							if(data.flag==1){
								u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
							getInitData();
						}else{
							u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
						}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							alert(textStatus);
						}
					})
				}
			});
		},
		backClick: function() {
			viewModel.cardData.removeRows;
			var	md = document.querySelector('#user-mdlayout')['u.MDLayout']
			viewModel.gridStatus("read");
			getInitData();
			md.dGo('user_list');
			var yid = viewModel.listData.getCurrentRow().rowId;
			viewModel.listData.setRowSelect(yid);
		},
		getdeptref:function(){
			var row = viewModel.cardData.getCurrentRow();
			if(row!=null){
				var pkCorp = row.getValue("pkCorp");
				if(null != pkCorp && pkCorp.length>0){
					iReferComp.setFilterPks("pkCorp", pkCorp, '#user');
				}
			}
//			var row = viewModel.listData.getSelectedRows();
//					iReferComp.setFilterPks("loginName", "12", '#psn');
//						if(row.length==1){
//							var id=row[0].getSimpleData().id;
//							if(id!=null&&id!=""){
//								iReferComp.setFilterPks("id", id, '#psn');
//							}else{
//								iReferComp.setFilterPks("id", null, '#psn');
//							}
//						}
		},
		saveClick: function() {
//			var result = app.compsValidateMultiParam({element:document.querySelector("#userdiv"), showMsg: true});
			//if (result.passed == false){
			var vlidate = getvalidate(app, "#user_addPage");
			if (vlidate == true) {
				var dat = viewModel.cardData.getSelectedRows()[0];
				var listdat = viewModel.listData.getSelectedRows()[0];
				$.ajax({
					type: 'post',
					url: window.cturl+'/system/user/save',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(dat.getSimpleData()),
					success: function(data) {
						if(data.flag==1){
							if(oper=="add"){
								u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
								dat.setValue("id",data.msg);
							}else{
								u.messageDialog({msg:"修改成功",title:"提示", btnText:"OK"});
							}
							viewModel.cardData.removeRows;
							viewModel.gridStatus("read");
							
							var md = document.querySelector('#user-mdlayout')['u.MDLayout']
							md.dGo('user_list')
							viewModel.searchClick();
						}else{
							u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus);
					}
				})
			}
		},
		
		afterAdd:function(element, index, data){
            if (element.nodeType === 1) {
                u.compMgr.updateComp(element);
            }
       },
       searchClick: function() {
			var row = viewModel.searchData.getSelectedRows()[0];
			var datas = {};
			datas["page.size"] = pagesize;
			if(row!=null){
				datas["params"] = row.getSimpleData();			
			}else{
				datas["params"] = null;
			}
			
			$.ajax({
				type: 'post',
				url:window.cturl+"/system/user/queryArea",
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(datas),
				success: function(data) {
					var total = data.totalElements;// 共多少条
					iReferComp.setSimpleData(data.content, 'user_grid3',{unSelect:true});
					viewModel.listData.pageSize(pagesize);// 每页显示多少条
					var pages = CalculatePageCount(pagesize,total);// 计算共多少页
					viewModel.listData.totalPages(pages); // 共多少页
					viewModel.listData.totalRow(total);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				}
			})
		},
       
		lockClick: function() {
			if (viewModel.listData.getSelectedRows().length ==0) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds:"2"
				});
				return
			}
			var dats = [];
			var rows = viewModel.listData.getSelectedRows()
			for(var i=0;i<rows.length;i++){
				dats.push(rows[i].getSimpleData());
			}
			$.ajax({
				type: 'post',
				url: window.cturl+'/system/user/lockedstoplist',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(dats),
				success: function(data) {
					if(data.flag==1){
								u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
							getInitData()
						}else{
							u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
						}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				}
			})
		},rowDblClick: function(row, e) {
			viewModel.formStatus(_CONST.STATUS_EDIT);
			viewModel.gridStatus("read");
			viewModel.listData.setRowSelect(row.rowIndex);
			var md = document.querySelector('#user-mdlayout')['u.MDLayout']
			md.dGo('user_addPage')
				var r = viewModel.listData.getSelectedRows()[0];
			iReferComp.setFormData('#user_addPage', r.getData().data,"user_grid3");
		},
		openClick: function() {
			if (viewModel.listData.getSelectedRows().length ==0) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds:"2"
				});
				return
			}
			var dats = [];
			var rows = viewModel.listData.getSelectedRows()
			for(var i=0;i<rows.length;i++){
				dats.push(rows[i].getSimpleData());
			}
			$.ajax({
				type: 'post',
				url: window.cturl+'/system/user/psnsealstoptlist',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(dats),
				success: function(data) {
					if(data.flag==1){
						u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					getInitData()
				}else{
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
				}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				}
			})
			
		},
		//关联岗位
		roleaddClick: function assignClick(e,index,colIndex) {
		   var row = viewModel.rolelistData.getSelectedRows();
			 document.getElementById('user_light').style.display='block';
			 document.getElementById('user_fade').style.display='block';
			 var pk_attr = viewModel.listData.getCurrentRow().getSimpleData().id;
		   $.ajax({
				type: 'get',
				url:window.cturl+'/security/extposition/queryAddForUser/'+pk_attr,
				success: function(data) {
					viewModel.roleData.setSimpleData(data);
					viewModel.roleData.setAllRowsUnSelect();
				}
			})
		},
		//关联岗位确认按钮
	   okClick: function okClick(e,index,colIndex) {
		   document.getElementById('user_light').style.display='none';
			document.getElementById('user_fade').style.display='none';
			var positionRow = viewModel.roleData.getSelectedRows();
			var userRow = viewModel.cardData.getSelectedRows();
			if(positionRow.length<=0){
				u.messageDialog({msg:"请选择关联的角色",title:"提示", btnText:"OK"});
				
				return;
			}
//			document.getElementById('lightnum').style.display='none';
//				document.getElementById('fadenum').style.display='none';
//			document.getElementById('saverole').style.display='inline-block';
//			document.getElementById('faderole').style.display='inline-block';
			
			//billLiu
			
			var main = [];
			
			for(var i=0;i<positionRow.length;i++){
				main.push({	  "user_id":userRow[0].getSimpleData().id,
				        	  "user_code":userRow[0].getSimpleData().loginName,
				        	  "position_id":positionRow[i].getSimpleData().id,
				        	  "position_code":positionRow[i].getSimpleData().position_code
				          });
			}
//			var sIndices = [];
//			var rows = viewModel.rolelistData.getAllRows();
//			for(var i=0;i<rows.length;i++){
//				var id = rows[i].getSimpleData().ieop_id;
//					if(null == id){
//						sIndices.push(rows[i]);
//					}
//			}
//			viewModel.rolelistData.setRowsSelect(sIndices);
			
			$.ajax({
				type: 'post',
				url: window.cturl+'/security/extposition/savePositionForUser',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(main),
				success: function(data) {
					//提示框
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					var listdat = viewModel.listData.getCurrentRow();
					lsroleid = listdat.getSimpleData().id;
					/**
					 * 查询用户岗位信息
					 */
					$.ajax({
						type: 'get',
						url:window.cturl+'/security/extposition/queryuser/'+lsroleid,
						success: function(data) {
							viewModel.rolelistData.setSimpleData(data);
							viewModel.rolelistData.setAllRowsUnSelect();

						}
					})
					
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			})
		},
		//关联岗位，取消按钮
		canceluserClick: function cancelClick(e,index,colIndex) {
			   document.getElementById('user_light').style.display='none';
				document.getElementById('user_fade').style.display='none';

		},
		saveuserClick : function cancelClick(e,index,colIndex) {
			var sIndices = [];
			var rows = viewModel.rolelistData.getAllRows();
			for(var i=0;i<rows.length;i++){
				var id = rows[i].getSimpleData().ieop_id;
					if(null == id){
						sIndices.push(rows[i].getSimpleData());
					}
			}
				$.ajax({
					type: 'POST',
					url: window.cturl+'/security/ext_user_role/createBatch',
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: JSON.stringify(sIndices),
					success: function(data) {
							u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
							   document.getElementById('lightnum').style.display='inline-block';
								document.getElementById('fadenum').style.display='inline-block';
							document.getElementById('saverole').style.display='none';
							document.getElementById('faderole').style.display='none';
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						errors.error(XMLHttpRequest);
					}
				})
		},
		cancelroleClick : function() {
			var row = viewModel.rolelistData.getSelectedRows();
			   document.getElementById('lightnum').style.display='inline-block';
				document.getElementById('fadenum').style.display='inline-block';
			document.getElementById('saverole').style.display='none';
			document.getElementById('faderole').style.display='none';
			viewModel.rolelistData.removeRows(viewModel.rolelistData.getSelectedRows());
		},
		//关联岗位【删除】
		deluserClick: function() {
			var userRow = viewModel.cardData.getSelectedRows();
			var rows = viewModel.rolelistData.getSelectedRows();
			if (viewModel.rolelistData.getSelectedRows().length ==0) {
				u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
				return
			}
			var sIndices = [];
			for(var i=0;i<rows.length;i++){
				sIndices.push({	  "user_id":userRow[0].getSimpleData().id,
		        	  "user_code":userRow[0].getSimpleData().loginName,
		        	  "position_id":rows[i].getSimpleData().id,
		        	  "position_code":rows[i].getSimpleData().position_code
		          });
			}
			
			
			u.confirmDialog({
				title: "确认",
				msg: "请确认是否删除选中的数据？",
				onOk: function () {
					$.ajax({
						url: window.cturl+'/security/extposition/deletePositionForUser',
						type: 'post',
						dataType: 'json',
						data: JSON.stringify(sIndices),
						contentType: "application/json ; charset=utf-8",
						success: function(data) {
							u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
							
							var listdat = viewModel.listData.getCurrentRow();
							lsroleid = listdat.getSimpleData().id;
							/**
							 * 查询用户岗位信息
							 */
							$.ajax({
								type: 'get',
								url:window.cturl+'/security/extposition/queryuser/'+lsroleid,
								success: function(data) {
									viewModel.rolelistData.setSimpleData(data);
									viewModel.rolelistData.setAllRowsUnSelect();

								}
							})
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							errors.error(XMLHttpRequest);
						}
					})
				}
			});
		},
		//编辑岗位
		editClickPosition: function() {
			if (viewModel.listData.getSelectedRows().length != 1) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds:"2"
				});
				return
			}
			oper="update";
			document.getElementById('user_rolediv').style.display='block';
			var md = document.querySelector('#user-mdlayout')['u.MDLayout']
			md.dGo('user_addPage')
			viewModel.formStatus(_CONST.STATUS_POSITION)
			viewModel.gridStatus("position");
			var r = viewModel.listData.getSelectedRows()[0];
			iReferComp.setFormData('#user_addPage', r.getData().data,"user_grid3");
			var listdat = viewModel.listData.getCurrentRow();
			lsroleid = listdat.getSimpleData().id;
//			lsrolecode = listdat.getSimpleData().loginName;
//			lsrolename = listdat.getSimpleData().name;

			/**
			 * 查询用户岗位信息
			 */
			$.ajax({
				type: 'get',
				url:window.cturl+'/security/extposition/queryuser/'+lsroleid,
				success: function(data) {
					viewModel.rolelistData.setSimpleData(data);
					viewModel.rolelistData.setAllRowsUnSelect();

				}
			})
		},canlClick: function() {
			viewModel.cardData.removeRows;
			var	md = document.querySelector('#user-mdlayout')['u.MDLayout']
			viewModel.gridStatus("read");
			getInitData();
			md.dGo('user_list');
//			var yid = viewModel.listData.getCurrentRow().rowId;
//			viewModel.listData.setRowSelect(yid);
		},
		pwClick: function() {
			if (viewModel.listData.getSelectedRows().length ==0) {
				u.messageDialog({
					msg : "请选择一行操作数据",
					title : "提示",
					btnText : "OK",
					showSeconds:"2"
				});
				return
			}
			var dats = [];
			var rows = viewModel.listData.getSelectedRows()
			for(var i=0;i<rows.length;i++){
				dats.push(rows[i].getSimpleData());
			}
			$.ajax({
				type: 'post',
				url: window.cturl+'/system/user/updatepasswordlist',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(dats),
				success: function(data) {
					if(data.flag==1){
						u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					getInitData()
				}else{
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
				}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				}
			})
			
		},
		//部门前事件,组织部门过滤
		filerDept: function() {
			var row = viewModel.cardData.getCurrentRow();
			if(row!=null){
				var pkCorp = row.getValue("pkCorp");
				if(null != pkCorp && pkCorp.length>0){
					iReferComp.setFilterPks("pk_corp", pkCorp, '#userinfo_deptref');
				}else{
					iReferComp.setFilterPks("pkCorp", "1", '#userinfo_deptref');
					u.messageDialog({ msg: "请先选择组织", title: "提示", btnText: "OK" });
					return
				}
			}
		}
	}

	viewModel = u.extend({}, basicDatas, computes, events)
	
	//组织调整后，需要清空部门信息
	viewModel.cardData.on('pkCorp.valuechange',function(obj){
		var row = viewModel.cardData.getCurrentRow();
		if(row!=null){
			row.setValue("pkDept","");
			row.setValue("pkPsndoc","");
		}
	});
	var getInitData = function() {
		
		$.ajax({
			type: 'get',
			url:window.cturl+"/system/user/querypage?page.size="+pagesize+"",
			success: function(data) {
				
				var total = data.totalElements;// 共多少条
				var data =data['content'];
				iReferComp.setSimpleData(data, 'user_grid3',{unSelect:true});
				// viewModel.listData.setSimpleData(data);
				viewModel.listData.setAllRowsUnSelect();
				viewModel.listData.pageSize(pagesize);// 每页显示多少条
				var pages = CalculatePageCount(pagesize,total);// 计算共多少页
				viewModel.listData.totalPages(pages); // 共多少页
				viewModel.listData.totalRow(total); 
// viewModel.searchData.createEmptyRow();
				viewModel.searchData.createEmptyRow();
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				errors.error(XMLHttpRequest);
			}
		})
	};

	return {
		refComp,
		init: function(content,tabid) {
			content.innerHTML = html;
			window.vm = viewModel;

			app = u.createApp({
				el: '#'+tabid,
				model: viewModel
			})
			
			getInitData();
			var contentMinHeight = $('#content').css('min-height');
		    contentMinHeight = contentMinHeight.substr(0,contentMinHeight.length-2);
		    var tabsMinHeight = $('#content .nav-tabs').css('height');
		    tabsMinHeight = tabsMinHeight.substr(0,tabsMinHeight.length-2);
		    var tabContentHeight = (contentMinHeight-tabsMinHeight)+"px";
			$('#user-mdlayout').css('min-height',tabContentHeight);
			userref();
			/*viewModel.cardData.on('pkPsndoc.valueChange', function(obj){
				$.ajax({
					type:'post',
					url:window.cturl+"/system/reimbursement/getRecord?id="+obj.newValue,
					datatype:'json',
					success:function(data){
		               debugger;
		               viewModel.cardData.setValue("pkCorp",data.pk_corp);
		               iReferComp.setFormData('#user_addPage',viewModel.cardData.getCurrentRow().getSimpleData());
					}
				});
			})*/
			
		}
	}


});