define(['text!pages/user/user.html', 'pages/user/usermeta','css!pages/user/user', 'uuitree', 'uuigrid', 'config/sys_const'], 
		function (template,metaModel) {

    //开始初始页面基础数据
    var init = function (element, params) {
        var viewModel = {
            draw: 1,//页数(第几页)
            pageSize: 5,
            searchURL: ctx + '/sysUser/list',
            addURL: ctx + "/sysUser/add",
            updateURL: ctx + "/sysUser/update",
            delURL: ctx + "/sysUser/delBatch",
            formStatus: _CONST.FORM_STATUS_ADD, //
            userDa: new u.DataTable(userMetaDt),
            userFormDa: new u.DataTable(userMetaDt),
//            dictSexs: [{name: "男", value: "0"}, {name: "女", value: "1"}],
            dictSexs :[] , //从后台拉取数据
            dictUserLevels: [{name: "运营用户", value: "1"}, {name: "金融用户", value: "2"}, {name: "商户用户", value: "3"}],
            dictUserStatus: [{name: "正常", value: "1"}, {name: "冻结", value: "2"}],
            dictCertificates: [{name: "身份证", value: "1"}, {name: "护照", value: "2"}, {name: "军官证", value: "3"}],
            userJobDa: new u.DataTable(userJobMetaDt),
            userJobFormDa: new u.DataTable(userJobMetaDt),
            sysOrgDa: new u.DataTable({
                meta: {
                    'orgid': {},
                    'orgname': {},
                    'parentid': {}
                }
            }),
            sysDeptDa: new u.DataTable({
            	meta: {
            		'pk_dept': {},
            		'deptname': {},
            		'parentid': {}
            	}
            }),
            //启用状态
            isActiveItems: YesOrNo,
            //默认状态
            isDefaultItems: YesOrNo,
            /**树默认设置 */
            treeSetting: {
                view: {
                    showLine: false,
                    selectedMulti: false
                },
                callback: {
                    onClick: function (e, id, node) {
                        var rightInfo = node.name + '被选中';
                        /*u.showMessage({msg: rightInfo, position: "topright",darkType:"dark",width:"420px"})*/
                    }
                }
            },
             
            event: {
                /**20161205修改最外层框架按钮组的显示与隐藏 */
                userListBtn: function () {  //显示user_list_button_2
                    $('#user_list_button_2').parent('.u-mdlayout-btn').removeClass('hide');
                    $('.form-search').removeClass('hide');
                    $('#user_card_button').parent('.u-mdlayout-btn').addClass('hide');

                },
                userCardBtn: function () {   //显示user_card_button
                    $('#user_list_button_2').parent('.u-mdlayout-btn').addClass('hide');
                    $('.form-search').addClass('hide');
                    $('#user_card_button').parent('.u-mdlayout-btn').removeClass('hide');
                },
                /**判断对象的值是否为空 */
                isEmpty: function (obj) {
                    if (obj.value == undefined || obj.value == '' || obj.value.length == 0) {
                        return true;
                    } else {
                        return false;
                    }
                },
                /**清除 datatable的数据  */
                clearDa: function (da) {
                    da.removeAllRows();
                    da.clear();
                },

                //加载初始列表
                initUerList: function () {
                    var jsonData = {
                        pageIndex: viewModel.draw - 1,
                        pageSize: viewModel.pageSize,
                        sortField: "create_time",
                        sortDirection: "desc"
                    };
                    $(element).find(".input_search").each(function () {
                        if (!viewModel.event.isEmpty(this)) {
                            jsonData['search_' + $(this).attr('name')] = removeSpace(this.value);
                        }
                    });

                    $.ajax({
                        type: 'get',
                        url: viewModel.searchURL,
                        datatype: 'json',
                        data: jsonData,
                        contentType: 'application/json;charset=utf-8',
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    if (res.detailMsg.data) {
                                        var totleCount = res.detailMsg.data.totalElements;
                                        var totlePage = res.detailMsg.data.totalPages;
                                        viewModel.comps.update({
                                            totalPages: totlePage,
                                            pageSize: viewModel.pageSize,
                                            currentPage: viewModel.draw,
                                            totalCount: totleCount
                                        });
                                        viewModel.event.clearDa(viewModel.userDa);
                                        viewModel.event.clearDa(viewModel.userJobDa);
                                        $('#child_list_pagination').hide();//子表分页层隐藏
                                        viewModel.userDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                        if(totleCount == 0){
                                        	$('#user-no-data-tip').show().removeClass('hide');
                                        }else{
                                        	$('#user-no-data-tip').hide();
                                        }
                                    } 
                                } else {
                                    var msg = "";
                                    for (var key in res.detailMsg) {
                                        msg += res.detailMsg[key] + "<br/>";
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                            }
                        } 
                    });
                    //end ajax
                },

                pageChange: function () {
                    viewModel.comps.on('pageChange', function (pageIndex) {
                        viewModel.draw = pageIndex + 1;
                        viewModel.event.initUerList();
                    });
                    viewModel.child_list_pcomp.on('pageChange', function (pageIndex) {
                    	viewModel.childdraw = pageIndex + 1;
                    	viewModel.event.getUserJobList();
                    });
                    viewModel.child_card_pcomp.on('pageChange', function (pageIndex) {
                    	viewModel.childdraw = pageIndex + 1;
                    	viewModel.event.getUserJobList();
                    });
                },
                //end pageChange
                sizeChange: function () {
                    viewModel.comps.on('sizeChange', function (arg) {
                        //数据库分页
                        viewModel.pageSize = parseInt(arg);
                        viewModel.draw = 1;
                        viewModel.event.initUerList();
                    });
                    viewModel.child_list_pcomp.on('sizeChange', function (arg) {
                    	//数据库分页
                    	viewModel.pageSize = parseInt(arg);
                    	viewModel.childdraw = 1;
                    	viewModel.event.getUserJobList();
                    });
                    viewModel.child_card_pcomp.on('sizeChange', function (arg) {
                    	viewModel.pageSize = parseInt(arg);
                    	viewModel.childdraw = 1;
                    	viewModel.event.getUserJobList();
                    });
                },
                //end sizeChange

                search: function () {
                    viewModel.draw = 1;
                    viewModel.event.initUerList();
                },
                cleanSearch: function () {
                    $(element).find('.form-search').find('input').val('');
                },
                //以下用于check checkbox
                afterAdd: function (element, index, data) {
                    if (element.nodeType === 1) {
                        u.compMgr.updateComp(element);
                    }
                },
                goBack: function () {
                    //只显示新增编辑删除按钮
                    viewModel.event.userListBtn();
                    viewModel.md.dBack();
                    viewModel.event.initUerList();
                    $('#userjob-no-data-tip').show();
                },

                addClick: function () {
                    viewModel.formStatus = _CONST.FORM_STATUS_ADD;
                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();
                    viewModel.event.clearDa(viewModel.userFormDa);
                    viewModel.userFormDa.createEmptyRow();
                    viewModel.userFormDa.setRowSelect(0);
                    viewModel.event.clearDa(viewModel.userJobFormDa);
                    //设置业务操作逻辑
                    var row = viewModel.userFormDa.getCurrentRow();
                    row.setValue('sexid', '1');   //设置默认值
                    row.setValue('levelid', '1');
                    row.setValue('statusid', '1');
                    row.setValue('certificateid', '1');
                    //显示操作卡片
                    $('#usercode').removeAttr("readonly");
                    $('#child_card_pagination').hide(); //子表分页层隐藏
                    viewModel.md.dGo('addPage');
                },
                editClick: function () {
                    viewModel.formStatus = _CONST.FORM_STATUS_EDIT;
                    var selectArray = viewModel.userDa.selectedIndices();
                    if (selectArray.length < 1) {
                        u.messageDialog({
                            msg: "请选择要编辑的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    if (selectArray.length > 1) {
                        u.messageDialog({
                            msg: "一次只能编辑一条记录，请选择要编辑的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();
                    //获取选取行数据
                    viewModel.userDa.setRowSelect(selectArray);
                    viewModel.userFormDa.clear();
                    viewModel.userJobFormDa.clear();
                    //绑定所选值
                    viewModel.userFormDa.setSimpleData(viewModel.userDa.getSimpleData({type: 'select'}));
                    viewModel.userJobFormDa.setSimpleData(viewModel.userJobDa.getSimpleData(), {unSelect: true});
                    $('#usercode').attr("readonly","readonly");
                    //显示操作卡片
                    viewModel.md.dGo('addPage');
                },

                saveClick: function () {
                    // compsValidate是验证输入格式。
                    if (! app.compsValidate($(element).find('#user-form')[0])) {
                        return;
                    }
                   
                    var user = viewModel.userFormDa.getSimpleData();
                    var userJob = viewModel.userJobFormDa.getSimpleData();
                    var jsondata =user[0];
                    jsondata.idSysUserJob = userJob;
                    var sendurl = viewModel.addURL;
                    if (viewModel.formStatus === _CONST.FORM_STATUS_EDIT) {
                        sendurl = viewModel.updateURL;
                    }
                    $.ajax({
                        type: "post",
                        url: sendurl,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(jsondata),//将对象序列化成JSON字符串
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    u.showMessage({
                                        msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>操作成功",
                                        position: "bottom"
                                    });
                                    viewModel.event.goBack();
                                } else {
                                    var msg = "";
                                    if (res.success == 'fail_global') {
                                        msg = res.message;
                                    } else {
                                        for (var key in res.detailMsg) {
                                            msg += res.detailMsg[key] + "<br/>";
                                        }
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '没有返回数据', title: '操作提示', btnText: '确定'});
                            }
                        } 
                    });
                },
                /**删除选中行*/
                delRow: function () {
                    var selectArray = viewModel.userDa.selectedIndices();
                    if (selectArray.length < 1) {
                        u.messageDialog({
                            msg: "请选择要删除的行!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    u.confirmDialog({
                        msg: '<div class="pull-left col-padding" >' +
                        '<i class="fa fa-exclamation-circle margin-r-5 fa-3x orange" style="vertical-align:middle"></i>确认删除这些数据吗？</div>',
                        title: '警告',
                        onOk: function () {
                            viewModel.event.delConfirm();
                        }
                    });
                },
                /**确认删除*/
                delConfirm: function () {
                    var jsonDel = viewModel.userDa.getSimpleData({type: 'select'});
                    $.ajax({
                        type: "post",
                        url: viewModel.delURL,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(jsonDel),
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    /*u.showMessage({
                                        msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>删除成功",
                                        position: "center"
                                    })*/
                                    viewModel.event.initUerList();
                                } else {
                                    u.messageDialog({msg: res.message, title: '操作提示', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }
                        
                    });
                },
                rowClick: function (row, e) {
                    var ri = e.target.parentNode.getAttribute('rowindex')
                    if (ri != null) {
                        viewModel.userDa.setRowFocus(parseInt(ri));
                        viewModel.userDa.setRowSelect(parseInt(ri));
                    }
                    viewModel.userFormDa.setSimpleData(viewModel.userDa.getSimpleData({type: 'select'}));
                    var userId = viewModel.userFormDa.getValue("id");
                    if (userId == null || userId == "") {
                        viewModel.userJobDa.removeAllRows();
                        viewModel.userJobDa.clear();
                    } else {
                        viewModel.event.getUserJobList();
                    }
                },
                selectUserJob: function (row, e) {
                    var ri = e.target.parentNode.getAttribute('rowindex')
                    if (ri != null) {
                        viewModel.userJobDa.setRowFocus(parseInt(ri));
                        viewModel.userJobDa.setRowSelect(parseInt(ri));
                    }
                },
                //选择所属组织（树）
                selectOrg: function () {
                    var treeSet = this.treeSetting;
                    var title = '请选择所属组织';
                    var url = ctx + '/sysUser/sysOrg/listall';
                    viewModel.event.showTreeDiv(null, url, title, this.treeSetting);
                },

                /**
                 * 关闭弹出框
                 */
                mdClose: function () {
                    md.close();
                },
                /**
                 * 弹出组织的树形结构弹出框，并绑定 确定 ，取消  关闭 等按钮 。
                 *  sendjosn 发送的数据
                 *  ajaxurl 请求的地址
                 *  title 弹窗标题
                 *  treeset 树控件的配置obj
                 */
                showTreeDiv: function (sendjson, ajaxurl, treetitle, treeset) {
                    $.ajax({
                        type: "GET",
                        url: ajaxurl,
                        contentType: 'application/json;charset=utf-8',
                        dataType: 'json',
                        success: function (res) {
                            if (res) {
                                $(element).find('#tree-title').html(treetitle);
                                viewModel.sysOrgDa.setSimpleData(res.detailMsg.data);
                                $("#commonTree")[0]['u-meta'].tree.expandAll(true);
                                window.md = u.dialog({
                                    id: 'commonShowDialog',
                                    content: '#tree-modal',
                                    hasCloseMenu: true
                                });
                                viewModel.event.bindClickButton($('#confirm_select'), null, viewModel.event.confirmSelectOrg);
                            } else {
                                u.showMessage({
                                    msg: '<i class="fa fa-times-circle margin-r-5"></i>' + res.message,
                                    position: "bottom",
                                    msgType: "error"
                                });
                            }
                        } 
                    });
                },

                /**绑定弹出层 树的按钮 */
                bindClickButton: function (ele, data, functionevent) { //对某一个按钮进行  点击事假绑定 ele:被绑定的元素，  data：需要传递的数据，functionevent：绑定的方法
                    $(ele).unbind('click'); //取消之前的绑定
                    $(ele).bind('click', data, functionevent); //重新绑定
                },

                /**选中某一个组织到用户 */
                confirmSelectOrg: function () {
                    var zTree = $("#commonTree")[0]['u-meta'].tree;
                    var selectNode = zTree.getSelectedNodes();
                    if (selectNode) {
                        var id = zTree.getSelectedNodes()[0].orgid;
                        var node = viewModel.sysOrgDa.getRowByField('orgid', id).getSimpleData();
                        viewModel.userFormDa.setValue('orgid', node.orgid)
                        viewModel.userFormDa.setValue('orgname', node.orgname);
                    }
                    viewModel.event.mdClose();
                },
                /**选中某一个组织到到任职表 */
                confirmSelectOrgToJob: function () {
                    var zTree = $("#commonTree")[0]['u-meta'].tree;
                    var selectNode = zTree.getSelectedNodes();
                    if (selectNode) {
                        var id = zTree.getSelectedNodes()[0].orgid;
                        var node = viewModel.sysOrgDa.getRowByField('orgid', id).getSimpleData();
                        viewModel.userJobFormDa.setValue('orgid', node.orgid);
                        viewModel.userJobFormDa.setValue('orgname', node.orgname);
                    }
                    viewModel.event.mdClose();
                },
                /**选中某一个部门到任职表 */
                confirmSelectDeptToJob: function () {
//                    var zTree = $.fn.zTree.getZTreeObj("deptTree");
                    var zTree = $("#deptTree")[0]['u-meta'].tree;
                    var nodes = zTree.getSelectedNodes();
                    if (nodes != null) {
                        var node = nodes[0];
                        viewModel.userJobFormDa.setValue('deptid', node.pk_dept);
                        viewModel.userJobFormDa.setValue('deptname', node.deptname);
                    }

                    viewModel.event.mdClose();
                },
 

                /**用户任职列表 */
                getUserJobList: function () {
                    var userId = viewModel.userFormDa.getValue("id");
                    var jsonData = {
                        pageIndex: viewModel.childdraw -1,
                        pageSize: viewModel.pageSize,
                        sortField: "create_time",
                        sortDirection: "asc"
                    };
                    jsonData['search_userid'] = userId;
                    $.ajax({
                        type: 'GET',
                        url: ctx + '/sysUserJob/list',
                        datatype: 'json',
                        data: jsonData,
                        contentType: 'application/json;charset=utf-8',
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    if (res.detailMsg.data) {
                                        viewModel.userJobDa.removeAllRows();
                                        viewModel.userJobDa.clear();
                                        viewModel.userJobDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});

                                        viewModel.userJobFormDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                        var totleCount = res.detailMsg.data.totalElements;
                                        var totlePage = res.detailMsg.data.totalPages;
                                        viewModel.child_list_pcomp.update({ //列表页子表的分页信息
                                            totalPages: totlePage,
                                            pageSize: viewModel.pageSize,
                                            currentPage: viewModel.childdraw,
                                            totalCount: totleCount
                                        });
                                        viewModel.child_card_pcomp.update({ //卡片页子表的分页信息
                                        	totalPages: totlePage,
                                        	pageSize: viewModel.pageSize,
                                        	currentPage: viewModel.childdraw,
                                        	totalCount: totleCount
                                        });
                                        if(totleCount > viewModel.pageSize ){//根据总条数，来判断是否显示子表的分页层
                                        	$('#child_card_pagination').show();
                                        	$('#child_list_pagination').show();
                                        }else{
                                        	$('#child_card_pagination').hide();
                                        	$('#child_list_pagination').hide();
                                        }
                                        
                                        if(totleCount == 0){  //表格的无数据 提示
                                        	$('#userjob-no-data-tip').show();
                                        }else{$('#userjob-no-data-tip').hide();}
                                        
                                    }
                                } else {
                                    var msg = "";
                                    for (var key in res.message) {
                                        msg += res.message[key] + "<br/>";
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                            }
                        } 
                    });
                },
                //
                addUserJob: function () {
                    viewModel.userJobFormDa.createEmptyRow();
                    if (viewModel.userJobFormDa.getSimpleData().length == 1) {
                        viewModel.userJobFormDa.setValue('isdefault', "Y");
                    }
                },
                delUserJob: function () {
                    var userJobs = viewModel.userJobFormDa.getSimpleData({type: 'select'})
                    if (userJobs.length < 1) {
                        u.messageDialog({
                            msg: "请选择要删除的行!",
                            title: "提示",
                            btnText: "OK"
                        });
                    }                
                    u.confirmDialog({
                    	  title:"提示",
                          msg:"确定要删除吗?" ,
                          onOk: function () {
                        	  confirmUJF();
                          },
                          onCancel: function () {                     
                   }});                
                  /* if (confirm("确定要删除吗?"))*/
                    function confirmUJF(){
                        var jsonDel = viewModel.userJobFormDa.getSimpleData({type: 'focus'});
                        var index = viewModel.userJobFormDa.getFocusIndex();
                        if (jsonDel[0].id == null) {
                            viewModel.userJobFormDa.removeRows(index);
                            return;
                        }
                        $.ajax({
                            type: "post",
                            url: ctx + "/sysUserJob/del",
                            contentType: 'application/json;charset=utf-8',
                            data: JSON.stringify(jsonDel[0]),
                            success: function (res) {
                                if (res) {
                                    if (res.success == 'success') {
                                       /* u.showMessage({
                                            msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>删除成功",
                                            position: "center"
                                        })*/
//                                        viewModel.userJobFormDa.removeRows(index);
                                    	if(viewModel.userJobFormDa.getAllRows().length === 1){
                                    		viewModel.childdraw = 1;
                                    	}
                                        viewModel.event.getUserJobList();
                                    } else {
                                        u.messageDialog({msg: res.message, title: '操作提示', btnText: '确定'});
                                    }
                                } else {
                                    u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                                }
                            } 
                        });
                    }
                },
                 
                /** 用户任职选择所属的组织*/
                addUserJob_selectOrg: function (obj) {
                    var gridObj = obj.gridObj;
                    var viewModel = gridObj.viewModel;
                    var field = obj.field;
                    var ele = obj.element;
                    var dataTableId = gridObj.dataTable.id;
                    var objValue = obj.value;
                    var disableStr = '';
                    if(u.isIPAD){
                        disableStr = ' disabled ';
                    }
                    //组织row
                    var innerStr = '<div class="u-input-group u-has-feedback">'
                        + '<input type="text" class="u-form-control" id="" ' + disableStr + ' >'
                        + '<span class="u-form-control-feedback fa fa-list-ul" id="addUserJob_selectOrg"></span>'
                        + '</div>';
                    var innerDom = u.makeDOM(innerStr);
                    ele.innerHTML = '';
                    ele.appendChild(innerDom);
 
                    //赋原值
                    ele.querySelector('input').value = objValue;
                    //用户任职,选择所属组织
                    u.on(ele.querySelector('#addUserJob_selectOrg'), 'click', function () {
                        $.ajax({
                            type: "GET",
                            url: ctx + '/sysUser/sysOrg/listall',
                            contentType: 'application/json;charset=utf-8',
                            dataType: 'json',
                            success: function (res) {
                                if (res) {
                                    $(element).find('#tree-title').html("请选择组织");
                                    viewModel.sysOrgDa.setSimpleData(res.detailMsg.data);
                                    $("#commonTree")[0]['u-meta'].tree.expandAll(true);
                                    window.md = u.dialog({
                                        id: 'commonShowDialog',
                                        content: '#tree-modal',
                                        hasCloseMenu: true
                                    });
                                    viewModel.event.bindClickButton($('#confirm_select'), null, viewModel.event.confirmSelectOrgToJob);
                                } else {
                                    u.showMessage({msg: '无数据', position: "bottom", msgType: "error"});
                                }
                            }
                        });
                    });
                },
                /** 用户任职选择所属的部门*/
                addUserJob_selectDept: function (obj) {
                    var gridObj = obj.gridObj;
                    var viewModel = gridObj.viewModel;
                    var field = obj.field;
                    var ele = obj.element;
                    var dataTableId = gridObj.dataTable.id;
                    var objValue = obj.value;
                    var disableStr = '';
                    if(u.isIPAD){
                        disableStr = ' disabled ';
                    }
 
                    var innerStr = '<div class="u-input-group u-has-feedback">'
                        + '<input type="text" class="u-form-control"' + disableStr + '>'
                        + '<span class="u-form-control-feedback fa fa-list-ul" id="addUserJob_selectDept"></span>'
                        + '</div>';
                    var innerDom = u.makeDOM(innerStr);
                    ele.innerHTML = '';
                    ele.appendChild(innerDom);
                    //赋原值
                    ele.querySelector('input').value = objValue;
 
                    u.on(ele.querySelector('#addUserJob_selectDept'), 'click', function () {
                        viewModel.event.showDeptDiv();
                    });
                },
 

                /**性别渲染 */
                changeSex: function (sexid) {
                    var v = sexid();
                    for( var i= 0 ;i< viewModel.dictSexs.length;i++ ){
                    	if(v == viewModel.dictSexs[i].value ){
                    		return viewModel.dictSexs[i].name ;
                    	} 
                    }
                },
                
                showDeptDiv: function () {
                    $.ajax({
                        type: "GET",
                        url: ctx + '/sysUserJob/dept/listall',
                        contentType: 'application/json;charset=utf-8',
                        dataType: 'json',
                        success: function (res) {
                            if (res) {
                                $(element).find('#dept-tree-title').html("请选择部门");
                                viewModel.sysDeptDa.setSimpleData(res.detailMsg.data);
                                $("#deptTree")[0]['u-meta'].tree.expandAll(true);
                                window.md = u.dialog({
                                    id: 'commonShowDialog',
                                    content: '#dept-tree-modal',
                                    hasCloseMenu: true
                                });
                                viewModel.event.bindClickButton($('#confirm_select_dept'), null, viewModel.event.confirmSelectDeptToJob);
                            } else {
                                u.showMessage({msg: '无数据', position: "bottom", msgType: "error"});
                            }
                        } 
                    });
                },
                /** 获取下拉框的枚举值*/
                loadEnum:function(){
                	 $.ajax({
                         type: "GET",
                         async : false,
                         url: ctx + '/sysUser/loadEnum',
                         contentType: 'application/json;charset=utf-8',
                         dataType: 'json',
                         success: function (res) {
                             if (res) {
                            	 viewModel.dictSexs = res.detailMsg.sex ;
                             } else {
                            	 console.error('未获取到枚举值，请检查。') ;
                             }
                         } 
                     });
                }

            } // end  event

        };
        //end viewModel

        viewModel.userFormDa.on("certificateID.valueChange", function (event) {
            var comboId = event.newValue;
            var datas = $(element).find('#comboCertificate')[0]['u.Combo'].comboDatas;
            for (var i = 0; i < datas.length; i++) {
                if (comboId == datas[i].value) {
                    viewModel.userFormDa.setValue('certificatename', datas[i].name);
                    break;
                }
            }
        });

        viewModel.userFormDa.on("levelId.valueChange", function (event) {
            var comboId = event.newValue;
            var datas = $(element).find('#comboLevel')[0]['u.Combo'].comboDatas;
            for (var i = 0; i < datas.length; i++) {
                if (comboId == datas[i].value) {
                    viewModel.userFormDa.setValue('levelname', datas[i].name);
                    break;
                }
            }
        });

        viewModel.userFormDa.on("statusId.valueChange", function (event) {
            var comboId = event.newValue;
            var datas = $(element).find('#comboStatus')[0]['u.Combo'].comboDatas;
            for (var i = 0; i < datas.length; i++) {
                if (comboId == datas[i].value) {
                    viewModel.userFormDa.setValue('statusname', datas[i].name);
                    break;
                }
            }
        });


        $(element).html(template);
        viewModel.event.loadEnum();// 去后台加载枚举值，ajax需设置为同步
        var app = u.createApp({
            el: element,   //    '#content'
            model: viewModel
        });
        viewModel.md = $(element).find('#user-mdlayout')[0]['u.MDLayout'];
        var paginationDiv = $(element).find('#pagination')[0];
        viewModel.comps = new u.pagination({el: paginationDiv, jumppage: true});

        viewModel.child_list_pcomp = new u.pagination({el: $(element).find('#child_list_pagination')[0], jumppage: true});//列表页面子表的分页层
        viewModel.child_card_pcomp = new u.pagination({el: $(element).find('#child_card_pagination')[0], jumppage: true});//卡片页面子表的分页层
        //$('#child_list_pagination').hide();
        viewModel.childdraw=1 ; 
        
        viewModel.event.initUerList();
        viewModel.event.pageChange();
        viewModel.event.sizeChange();
        /*兼容ie日期中后退按钮bug*/
        if(u.isIE){
	        $('#birthdate').keydown(function(e){
	    		e.preventDefault();
	    	})
	    	$('#registerdate').keydown(function(e){
	    		e.preventDefault();
	    	})
        }

    }  //end init

    return {
        'model': init.viewModel,
        'template': template,
        'init': function (element, arg) {
            init(element, arg);
            /*回车搜索*/
            $('.search-enter').keydown(function (e) {
                if (e.keyCode == 13) {
                    $('#user-action-search').trigger('click');
                    u.stopEvent(e);
                }
            });
        }
    }
});
//end define
