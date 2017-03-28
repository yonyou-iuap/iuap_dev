define(['text!pages/addressBook/contacts.html','css!pages/addressBook/contacts','pages/addressBook/contactsmeta','uuitree','uuigrid'],function(html) {
	var init = function(element){
		var treelistUrl = ctx+'/instit/list';
		var treedelUrl = ctx+'/instit/del/';
		var treesaveUrl = ctx+'/instit/save';
		
		var tablelistUrl = ctx+'/telbook/list';
		var tabledelUrl = ctx+'/telbook/del/';
		var tablesaveUrl = ctx+'/telbook/save';
	
		var viewModel = {
				app:{},
				/* 数据模型 */
				draw:1,
				totlePage:0,
				pageSize:5,
				totleCount:0,

				/* 树数据 */
				institdata : new u.DataTable(metaInstit),
				
				/* 编辑框树数据 */
				institdatanew : new u.DataTable(metaInstit),
				
				/* 电话本数据 */
				telbookdata : new u.DataTable(metaTelbook),
				
				/* 电话本数据 */
				telbookdatanew : new u.DataTable(metaTelbook),
				
				/* 树设置 */
				treeSetting : {
					view : {
						showLine : false,
						selectedMulti : false
					},
					callback : {
						onClick : function(e, id, node) {
							treeid=[];
							viewModel.event.getAllChildren(node,treeid);
						   var pid = node.pId;
						   viewModel.event.loadTelbook(treeid);
						}
					}
				
				},
				event:{
					
	                //清除datatable数据
	                clearDt: function (dt) {
	                	dt.removeAllRows();
	                	dt.clear();
	                },
					
					/* 获得树节点的所有子节点 */
					getAllChildren:function(node,childrenlist){
						childrenlist.push(node.id)
						if(node.children){
							var i;
							for(i=0;i<node.children.length;i++){
								viewModel.event.getAllChildren(node.children[i],childrenlist);
							}
						}
					},

					loadTelbook:function(instit){
						var jsonData={
								pageIndex:viewModel.draw-1,
								pageSize:viewModel.pageSize,
								sortField:"peocode",
								sortDirection:"asc"
							};
						/*右表的上面详细信息显示*/
						var infoDiv = document.getElementById('infoPanel');
						var dtVal = viewModel.institdata.getValue('institname');
						infoDiv.innerHTML = dtVal;
						//end
						$(element).find("#search").each(function(){
							if(this.value == undefined || this.value =='' || this.value.length ==0 ){
								//不执行操作
							}else{
								jsonData['search_searchParam'] =  this.value.replace(/(^\s*)|(\s*$)/g, "");  //去掉空格
							}
						});
						if(instit){
							if(instit!=''||instit.length!=0){
								jsonData['search_institid'] = instit.join();
							}
						}
						$.ajax({
							type : 'get',
							url : tablelistUrl,
							dataType : 'json',
							data:jsonData,
							contentType: 'application/json;charset=utf-8',
							success : function(res) {
								if(res){
									if( res.success =='success'){
										if(!res.detailMsg.data){
											viewModel.totleCount=0;
											viewModel.totlePage=1;
											viewModel.event.comps.update({totalPages:viewModel.totlePage,pageSize:viewModel.pageSize,currentPage:viewModel.draw,totalCount:viewModel.totleCount});
											viewModel.telbookdata.removeAllRows();
											viewModel.telbookdata.clear();
										}else{
											viewModel.totleCount=res.detailMsg.data.totalElements;
											viewModel.totlePage=res.detailMsg.data.totalPages;
											viewModel.event.comps.update({totalPages:viewModel.totlePage,pageSize:viewModel.pageSize,currentPage:viewModel.draw,totalCount:viewModel.totleCount});
											viewModel.telbookdata.removeAllRows();
											viewModel.telbookdata.clear();
											viewModel.telbookdata.setSimpleData(res.detailMsg.data.content,{unSelect:true});
										}
									}else{
										var msg = "";
										if(res.success == 'fail_global'){
											msg = res.message;
										}else{
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key] + "<br/>";
											}
										}
										u.messageDialog({msg:msg,title:'请求错误',btnText:'确定'});									
									}
								}else{
									u.messageDialog({msg:'后台返回数据格式有误，请联系管理员',title:'数据错误',btnText:'确定'});
								}

							},
							error:function(er){
								u.messageDialog({msg:'请求超时',title:'请求错误',btnText:'确定'});
							}
						})
					},
					loadTree:function(){
						$.ajax({
							type : 'get',
							url : treelistUrl,
							dataType : 'json',
							success : function(res) {
								if(res){
									if( res.success =='success'){
										if(res.detailMsg.data){
											viewModel.institdata.removeAllRows();
											viewModel.institdata.clear();
											viewModel.institdata.setSimpleData(res.detailMsg.data,{unSelect:true});
	                                        $("#tree2")[0]['u-meta'].tree.expandAll(true);
	/*                                        for(var i=0;i<$("#tree2")[0]['u-meta'].tree.getNodeByTId('tree2_1').children.length;i++){
	                                            $("#tree2")[0]['u-meta'].tree.expandNode($("#tree2")[0]['u-meta'].tree.getNodeByTId('tree2_1').children [i],false,false,false)
	                                        }*/
										}
									}else{
										var msg = "";
										if(res.success == 'fail_global'){
											msg = res.message;
										}else{
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key] + "<br/>";
											}
										}
										u.messageDialog({msg:msg,title:'请求错误',btnText:'确定'});									
									}
								}else{
									u.messageDialog({msg:'后台返回数据格式有误，请联系管理员',title:'数据错误',btnText:'确定'});
								}

							},
							error:function(er){
								u.messageDialog({msg:'请求超时',title:'请求错误',btnText:'确定'});
							}
						})
					},
					//组装list
					genDataList:function(data){
						var datalist = [];
						datalist.push(data);
						return datalist ;
					},
					//新增和更新组织树
					saveTree:function(data){
						var list=viewModel.event.genDataList(data);
						$.ajax({
							type : 'post',
							url : treesaveUrl,
							dataType : 'json',
							contentType : "application/json",
							data : JSON.stringify(list),
							success : function(res) {
								if(res){
									if( res.success =='success'){
										u.messageDialog({msg: '保存成功！', btnText: '确定'});
										viewModel.event.loadTree();
										md.close();
									}else{
										var msg = "";
										if(res.success == 'fail_global'){
											msg = res.message;
										}else{
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key] + "<br/>";
											}
										}
										u.messageDialog({msg:msg,title:'操作提示',btnText:'确定'});
									}
								}else{
									u.messageDialog({msg:'没有返回数据',title:'操作提示',btnText:'确定'});
								}
							}
						})
						
						},
						//删除组织树
						deleteTree: function(data) {
				        	var datalist = viewModel.event.genDataList(data);
				            var json = JSON.stringify(datalist);
				            $.ajax({
				                url: treedelUrl,
				                data: json,
				                dataType: 'json',
				                type: 'post',
				                contentType: 'application/json',
				                success: function (res) {
				                	if(res){
				                	       if (res.success == 'success') {
				                	    	   viewModel.institdata.removeRow(viewModel.institdata.getSelectedIndexs());
				                	    	   u.messageDialog({msg:'删除成功',title:'操作提示',btnText:'确定'});
				    	                    } else {
												var msg = "";
											   if(res.success == 'fail_global'){
												   msg = res.message;
												   if(msg=='Data had been referenced,remove is forbidden!'){
													   msg='该部门含有子部门不能删除';   
												   }
											   }else{
												   for (var key in res.detailMsg) {
													   msg += res.detailMsg[key] + "<br/>";
												   }
											   }
											   u.messageDialog({msg:msg,title:'操作提示',btnText:'确定'});
				    	                    }
				                	}else{
										u.messageDialog({msg:'无返回数据',title:'操作提示',btnText:'确定'});
									}
				             
				                },
								error:function(er){
									u.messageDialog({msg:'操作请求失败，'+er,title:'操作提示',btnText:'确定'});
								}
				            });
				        },
				        //更新和保存人员
				        saveMan:function(data){
							var list=viewModel.event.genDataList(data);
							$.ajax({
								type : 'post',
								url : tablesaveUrl,
								dataType : 'json',
								contentType : "application/json",
								data : JSON.stringify(list),
								success : function(res) {
									if(res){
										if( res.success =='success'){
											u.messageDialog({msg: '保存成功！', btnText: '确定'});
											viewModel.event.loadTelbook(treeid);
											md.close();
										}else{
											var msg = "";
											if(res.success == 'fail_global'){
												msg = res.message;
											}else{
												for (var key in res.detailMsg) {
													msg += res.detailMsg[key] + "<br/>";
												}
											}
											u.messageDialog({msg:msg,title:'操作提示',btnText:'确定'});
										}
									}else{
										u.messageDialog({msg:'没有返回数据',title:'操作提示',btnText:'确定'});
									}
								}
							});
				        },
				        //删除人员
				        delMan:function(data){
							var list=viewModel.event.genDataList(data);
							$.ajax({
								type : 'post',
								url : tabledelUrl,
								dataType : 'json',
								contentType : "application/json",
								data : JSON.stringify(list),
								success : function(res) {
									if( res.success =='success'){
										u.messageDialog({msg: '删除成功！', btnText: '确定'});
										 //md.close();
									}else{
										u.messageDialog({msg: '删除失败！', btnText: '确定'});
									}
								}
							})
				        },
				    //分页相关
					pageChange:function(){
						viewModel.event.comps.on('pageChange', function (pageIndex) {
							viewModel.draw = pageIndex + 1;
							viewModel.event.loadTelbook(treeid);
						});
					},
					sizeChange:function(){
						viewModel.event.comps.on('sizeChange', function (arg) {
							viewModel.pageSize = parseInt(arg);
							viewModel.draw = 1;
							viewModel.event.loadTelbook(treeid);
						});
					},
					
					//页面初始化
					pageInit : function() {
						treeid=[];
						
						viewModel.app = u.createApp({
							el :element /* Document.body */,
							model : viewModel
						})

						//分页初始化	
						var paginationDiv = $(element).find('#pagination')[0];
						this.comps=new u.pagination({el:paginationDiv,jumppage:true});
						this.comps.update({pageSize: 5 });  //默认每页显示5条数据
						this.loadTree();
						viewModel.event.pageChange();
						viewModel.event.sizeChange();	
						
	                    //回车搜索
	                    $('.input_enter').keydown(function(e){
	                        if(e.keyCode==13){
	                        	viewModel.event.searchClick()
	                        	u.stopEvent(e);
	                        }
	                    });
					
					},
					addinstitClick:function(){
                        $('#institcode').removeAttr("readonly");
						$('#dialog_content_instit').find('.u-msg-title').html("<h4>新增机构</h4>");
						viewModel.event.clearDt(viewModel.institdatanew);
						var row = viewModel.institdata.getSelectedRows()[0];
						
						if(row){
							var parentId = row.getValue('institid');
							var parentName = row.getValue('institname');
						}
						
						var newr = viewModel.institdatanew.createEmptyRow();
			            viewModel.institdatanew.setRowSelect(newr);
			            
			            if(row){
			            	var newrow = viewModel.institdatanew.getSelectedRows()[0];
			            	newrow.setValue('parentid',parentId);
			            	newrow.setValue('parentname',parentName);
			            }

			            window.md = u.dialog({id:'add_depart',content:"#dialog_content_instit",hasCloseMenu:true});
					},
					editinstitClick:function(){
						$('#dialog_content_instit').find('.u-msg-title').html("<h4>编辑机构</h4>");
						viewModel.event.clearDt(viewModel.institdatanew);
						var row = viewModel.institdata.getSelectedRows()[0];
						if(row){
							if(row.data.parentid.value){
								row.setValue('parentname',$("#tree2")[0]['u-meta'].tree.getNodeByParam('id',row.getValue('parentid')).name);
							}

							viewModel.institdatanew.setSimpleData(viewModel.institdata.getSimpleData({type: 'select'}));
							$('#institcode').attr("readonly","readonly");
							window.md = u.dialog({id:'edit_depart',content:"#dialog_content_instit",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择要编辑的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					delinstitClick:function(){
						var row = viewModel.institdata.getSelectedRows()[0]
						if(row){
							var data = viewModel.institdata.getSelectedRows()[0].getSimpleData()
							u.confirmDialog({
					            msg: "是否删除"+data.institname+"?",
					            title: "删除确认",
					            onOk: function () {
					                viewModel.event.deleteTree(data);
					            },
					            onCancel: function () {
					            }
							});
						}else{
							u.messageDialog({msg:'请选择要删除的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					saveinstitClick:function(){
						var data = viewModel.institdatanew.getSelectedRows()[0].getSimpleData();
	                    if (!viewModel.app.compsValidate($('#dialog_content_instit')[0])) {
	                        return;
	                    }
                        viewModel.event.saveTree(data);
					},
					cancelinstitClick:function(){
							md.close();
					},
					addManClick:function(){
						$('#dialog_content_man').find('.u-msg-title').html("<h4>新增人员</h4>");
						viewModel.event.clearDt(viewModel.telbookdatanew);
						var row = viewModel.institdata.getSelectedRows()[0];
						if(row){
							var institId = row.getValue('institid');
							var instit = row.getValue('institname');
							var newr = viewModel.telbookdatanew.createEmptyRow();
							viewModel.telbookdatanew.setRowSelect(newr);
							var newrow = viewModel.telbookdatanew.getSelectedRows()[0];
							newrow.setValue('institid',institId);
							newrow.setValue('institname',instit);
							$('#add_peocode').removeAttr("readonly");
							window.md = u.dialog({id:'add_man',content:"#dialog_content_man",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择部门！',title:'操作提示',btnText:'确定'});
						}
					},
					editManClick:function(){
						$('#dialog_content_man').find('.u-msg-title').html("<h4>编辑人员</h4>");
						viewModel.event.clearDt(viewModel.telbookdatanew);
						var row = viewModel.telbookdata.getSelectedRows()[0]
						if(row){
							viewModel.telbookdatanew.setSimpleData(viewModel.telbookdata.getSimpleData({type: 'select'}));
                            $('#add_peocode').attr("readonly","readonly");
							window.md = u.dialog({id:'edit_man',content:"#dialog_content_man",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择要编辑的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					delManClick:function(){
						var row = viewModel.telbookdata.getSelectedRows()[0];
						if(row){
							var data = viewModel.telbookdata.getSelectedRows()[0].getSimpleData()
							u.confirmDialog({
					            msg: "是否删除"+data.peoname+"?",
					            title: "删除确认",
					            onOk: function () {
					                viewModel.event.delMan(data);
					                viewModel.telbookdata.removeRow(viewModel.telbookdata.getSelectedIndexs());
					            },
					            onCancel: function () {
					            }
							});
						}else{
							u.messageDialog({msg:'请选择要删除的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					saveManClick:function(){
						var data = viewModel.telbookdatanew.getSelectedRows()[0].getSimpleData();
	                    if (!viewModel.app.compsValidate($('#add-form')[0])) {
	                        return;
	                    }
                        viewModel.event.saveMan(data);
					},
					cancelManClick:function(){
							md.close();
					},
					searchClick:function(){
						viewModel.draw = 1; 
						viewModel.event.loadTelbook(treeid);
					}
				}
			};
		$(element).html(html) ;
		viewModel.event.pageInit();
		if(u.isIE8){
			$('.ie8-bg').css('display','block');
		}
	}
	
	return {
		'template': html,
        init:init
	}
});
