define(['iReferComp','refComp','error','text!./position.html','css!./productmgr.css', 'uuigrid','utilrow'], function(iReferComp,refComp,errors,html) {
	'use strict';
	
	var refid;
	var dom;
	
	 function ref() {
		 var pk='';
     }
	 var msg = {}; 
		var str = JSON.stringify(msg); 
		top.postMessage(str, "*");
		var _CONST = {
			STATUS_ADD: 'add',
			STATUS_EDIT: 'edit'
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
		lsrolecode,
		comValueNameMap,
		enums;
	basicDatas = {
		listData: new u.DataTable({
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
				ts: {
					type: 'string'
				}
			}
		}),
		cardData: new u.DataTable({
			meta: {
				id: {
					type: 'string'
				},
				position_name: {
					type: 'string' ,required: true,nullMsg: '内容不能为空!',maxLength:30
				},
				position_code: {
					type: 'string' ,required: true,nullMsg: '内容不能为空!',maxLength:30
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
				ts: {
					type: 'string'
				}
			}
		}),
		searchData: new u.DataTable({
			meta: {
				position_code: {
					type: 'string'
				},
				position_name: {
					type: 'string'
				}
			}
		}),
		roleData: new u.DataTable({
			meta: {
				id: {
					type: 'string'
				},
				role_name: {
					type: 'string'
				},
				role_code: {
					type: 'string'
				},
				role_type: {
					type: 'string'
				},
				isactive: {
					type: 'string'
				},
				create_date: {
					type: 'date'
				},
//				is_user: {
//					type: 'string'
//				}
			}
		}),
		// 角色
		rolelistData: new u.DataTable({
			meta: {
				id: {
					type: 'string'
				},
				role_name: {
					type: 'string'
				},
				role_code: {
					type: 'string'
				},
				role_type: {
					type: 'string'
				},
				isactive: {
					type: 'string'
				},
				create_date: {
					type: 'date'
				},
//				is_user: {
//					type: 'string'
//				}
			}
		}),

		formStatus: ko.observable(_CONST.STATUS_ADD),
		gridStatus : ko.observable("read")
	};

	comValueNameMap={};
	
	enums = {
		//状态	
		orderStatus: [
	        {"value": "10051001","name": "启用"}, 
            {"value": "10051002","name": "停用"}]
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
		 //查询
	     searchClick: function() {
				var queryData = {};
				queryData["search_position_code"] = viewModel.searchData.getSimpleData()[0].position_code;
				queryData["search_position_name"] = viewModel.searchData.getSimpleData()[0].position_name;
				queryData["page"] = viewModel.listData.pageIndex();
				viewModel.listData.pageSize(pagesize);
		        queryData["page.size"] = viewModel.listData.pageSize();
		        
				$.ajax({
					type: 'get',
					url:window.cturl+"/security/extposition/main",
					dataType: 'json',
					contentType: "application/json ; charset=utf-8",
					data: queryData,
					success: function(data) {
						viewModel.listData.pageSize(data.size);//每页显示多少条
						viewModel.listData.setSimpleData(data.content);
						viewModel.listData.totalPages(data.totalPages);
						viewModel.listData.totalRow(data.totalElements); 
						viewModel.listData.setRowUnFocus();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus);
					}
				})
			},
		pageChangeFunc: function(pageIndex) {
			viewModel.listData.pageIndex(pageIndex);
			viewModel.searchClick();
		},
		sizeChangeFunc: function(size, pageIndex) {
			pageSize = size;
			viewModel.listData.pageIndex(0);
			viewModel.searchClick();
		},
		addClick: function() {
			var md = document.querySelector('#position-mdlayout')['u.MDLayout']
			viewModel.cardData.clear();
			viewModel.rolelistData.clear();
			document.getElementById('rolediv').style.display='none';
			md.dGo('position_addPage')
			viewModel.formStatus(_CONST.STATUS_ADD)
			viewModel.gridStatus("edit");
			oper="add";
			var r = viewModel.cardData.createEmptyRow()
			r.setValue("position_status","10051001");
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
			// document.getElementById('rolediv').style.display='block';
			var md = document.querySelector('#position-mdlayout')['u.MDLayout']
			md.dGo('position_addPage')
			viewModel.formStatus(_CONST.STATUS_EDIT)
			var r = viewModel.listData.getSelectedRows()[0];
//			iReferComp.setFormData('#addPage', r.getData().data,"grid3");
			viewModel.cardData.setSimpleData(r.getSimpleData());
		},
		backClick: function() {
			viewModel.cardData.removeRows;
			var	md = document.querySelector('#position-mdlayout')['u.MDLayout']
			viewModel.gridStatus("read");
			getInitData();
			md.dGo('position_list');
			var yid = viewModel.listData.getCurrentRow().rowId;
			viewModel.listData.setRowSelect(yid);
		},
		editClickrole: function() {
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
			document.getElementById('rolediv').style.display='block';
			var md = document.querySelector('#position-mdlayout')['u.MDLayout']
			md.dGo('position_addPage')
			viewModel.formStatus(_CONST.STATUS_EDIT)
			var r = viewModel.listData.getSelectedRows()[0];
			viewModel.cardData.setSimpleData(r.getSimpleData());
			var positionId = viewModel.cardData.getCurrentRow().getSimpleData().id;

			$.ajax({
				type: 'get',
				url:window.cturl+'/security/extposition/query/'+positionId,
				success: function(data) {
					viewModel.rolelistData.setSimpleData(data);
					viewModel.rolelistData.setAllRowsUnSelect();

				}
			})
		},
		saveClick: function() {
			//表单校验
			var validate = getvalidate(app,"#position_addPage");
			if(validate==false){
				return
			};
			var dat = viewModel.cardData.getSelectedRows()[0];
			if(oper=="add"){
				var editUrl='save';
			}else{
				var editUrl='update';
			}
			$.ajax({
				type: 'post',
				url: window.cturl+'/security/extposition/'+editUrl,
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(dat.getSimpleData()),
				success: function(data) {
					if(data.flag=='success'){
						if(oper=="add"){
							u.messageDialog({msg:"保存成功",title:"提示", btnText:"OK"});
						}else{
							u.messageDialog({msg:"修改成功",title:"提示", btnText:"OK"});
						}

						viewModel.gridStatus("read");
						getInitData();
						var md = document.querySelector('#position-mdlayout')['u.MDLayout']
						md.dGo('position_list');
						var yid = viewModel.listData.getCurrentRow().rowId;
						viewModel.listData.setRowSelect(yid);
					}else{
						u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				}
			})
		},
		
		afterAdd:function(element, index, data){
            if (element.nodeType === 1) {
                u.compMgr.updateComp(element);
            }
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
				url: window.cturl+'/security/extposition/openPosition',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(dats),
				success: function(data) {
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					getInitData()
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				}
			})
			
		},
		//关联角色
		roleaddClick: function assignClick(e,index,colIndex) {
			var row = viewModel.rolelistData.getSelectedRows();
			document.getElementById('position_light').style.display='block';
			document.getElementById('position_fade').style.display='block';
			var positionId = viewModel.cardData.getCurrentRow().getSimpleData().id;
			$.ajax({
				type: 'get',
				url:window.cturl+'/security/extposition/queryAddRole/'+positionId,
				success: function(data) {
					viewModel.roleData.setSimpleData(data);
					viewModel.roleData.setAllRowsUnSelect();
				}
			})
		},
		//关联角色【确认】
	   okClick: function okClick(e,index,colIndex) {
		   document.getElementById('position_light').style.display='none';
			document.getElementById('position_fade').style.display='none';
			var rowrole = viewModel.roleData.getSelectedRows();
//			var rowuser = viewModel.listData.getSelectedRows();
			if(rowrole.length<=0){
				u.messageDialog({msg:"请选择关联的角色",title:"提示", btnText:"OK"});
				
				return;
			}
			
			var roleList = [];
			for(var i=0;i<rowrole.length;i++){
				roleList.push(rowrole[i].getSimpleData());
			}
			//保存角色
			var main = viewModel.cardData.getCurrentRow().getSimpleData();
			main.roleItems= roleList;
			
			$.ajax({
				type: 'post',
				url: window.cturl+'/security/extposition/saveRole',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(main),
				success: function(data) {
					//提示框
					u.messageDialog({msg:data.msg,title:"提示", btnText:"OK"});
					var positionId = viewModel.cardData.getCurrentRow().getSimpleData().id;
					$.ajax({
						type: 'get',
						url:window.cturl+'/security/extposition/query/'+positionId,
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
		//关联角色【取消】
		canceluserClick: function cancelClick(e,index,colIndex) {
			document.getElementById('position_light').style.display='none';
			document.getElementById('position_fade').style.display='none';
		},
		//删除角色
		deluserClick: function() {
			var rows = viewModel.rolelistData.getSelectedRows();
			if (viewModel.rolelistData.getSelectedRows().length ==0) {
				u.messageDialog({msg:"请选择要删除的数据",title:"提示", btnText:"OK"});
				return
			}
			var roleList = [];
			for(var i=0;i<rows.length;i++){
				roleList.push(rows[i].getSimpleData());
			}
			//保存角色
			var main = viewModel.cardData.getCurrentRow().getSimpleData();
			main.roleItems= roleList;
			u.confirmDialog({
				title: "确认",
				msg: "请确认是否删除选中的数据？",
				onOk: function () {
					$.ajax({
						url: window.cturl+'/security/extposition/deleteRole',
						type: 'post',
						dataType: 'json',
						data: JSON.stringify(main),
						contentType: "application/json ; charset=utf-8",
						success: function(data) {
							u.messageDialog({msg:"删除成功",title:"提示", btnText:"OK"});
							var positionId = viewModel.cardData.getCurrentRow().getSimpleData().id;
							$.ajax({
								type: 'get',
								url:window.cturl+'/security/extposition/query/'+positionId,
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
		
		canlClick: function() {
			viewModel.cardData.removeRows;
			var	md = document.querySelector('#position-mdlayout')['u.MDLayout']
			viewModel.gridStatus("read");
			getInitData();
			md.dGo('position_list');
		}
	}

	viewModel = u.extend({}, basicDatas, computes, events,enums,comValueNameMap)
	
	var getInitData = function() {
		viewModel.searchData.createEmptyRow();
		viewModel.searchClick();
		//状态
		comValueNameMap["10051001"]="启用";
		comValueNameMap["10051002"]="停用";
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
		}
	}


});