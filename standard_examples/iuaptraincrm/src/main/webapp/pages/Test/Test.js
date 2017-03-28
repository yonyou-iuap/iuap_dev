define(['text!pages/Test/Test.html','pages/Test/meta','css!pages/Test/Test.css', 'uuitree', 'uuigrid', 'config/sys_const'], function (template) {
   
  //开始初始页面基础数据
    var init =  function (element, params) {
        var viewModel = {
            draw: 1,//页数(第几页)
            pageSize: 5,
            searchURL: ctx + '/Test/list',
            addURL: ctx + "/Test/add",
            updateURL: ctx + "/Test/update",
            delURL: ctx + "/Test/delBatch",
            formStatus: _CONST.FORM_STATUS_ADD, 
            TestDa: new u.DataTable(metaDt),
            TestFormDa: new u.DataTable(metaDt),

           
            TestBDa: new u.DataTable(metaTestB),
            TestBFormDa: new u.DataTable(metaTestB),

			refCurrtypeDa: new u.DataTable({
				meta: {
                    'pk_currtype': {},
                    'name': {},
                    'parentid': {}
                }
            }),
            

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
                        sortField: "ts",
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
                                        viewModel.event.clearDa(viewModel.TestDa);
                                        viewModel.event.clearDa(viewModel.TestBDa);
                                        viewModel.TestDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
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
                    $('#child_list_pagination').hide(); //隐藏子表的分页层
                },

                addClick: function () {
                    viewModel.formStatus = _CONST.FORM_STATUS_ADD;
                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();
                    viewModel.event.clearDa(viewModel.TestFormDa);
                    viewModel.TestFormDa.createEmptyRow();
                    viewModel.TestFormDa.setRowSelect(0);
                    viewModel.event.clearDa(viewModel.TestBFormDa);
                    //设置业务操作逻辑
                    var row = viewModel.TestFormDa.getCurrentRow();
                    //显示操作卡片
                    viewModel.md.dGo('addPage');
                },
                editClick: function () {
                    viewModel.formStatus = _CONST.FORM_STATUS_EDIT;
                    var selectArray = viewModel.TestDa.selectedIndices();
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
                    viewModel.TestDa.setRowSelect(selectArray);
                    viewModel.TestFormDa.clear();
                    viewModel.TestBFormDa.clear();
                    viewModel.TestFormDa.setSimpleData(viewModel.TestDa.getSimpleData({type: 'select'}));
                    viewModel.TestBFormDa.setSimpleData(viewModel.TestBDa.getSimpleData(), {unSelect: true});

                    //显示操作卡片
                    viewModel.md.dGo('addPage');
                },

                saveClick: function () {
                    // compsValidate是验证输入格式。
                    if (! app.compsValidate($(element).find('#user-form')[0])) {
                        return;
                    }
                   
                    var user = viewModel.TestFormDa.getSimpleData();
                    var userJob = viewModel.TestBFormDa.getSimpleData();
                    var jsondata =user[0];
                    jsondata.id_testb = userJob;
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
                    var selectArray = viewModel.TestDa.selectedIndices();
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
                        '<i class="fa fa-exclamation-circle margin-r-5 fa-3x orange" style="vertical-align:middle"></i>确认删除这些数据数据吗？</div>',
                        title: '警告',
                        onOk: function () {
                            viewModel.event.delConfirm();
                        }
                    });
                },
                /**确认删除*/
                delConfirm: function () {
                    var jsonDel = viewModel.TestDa.getSimpleData({type: 'select'});
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
                        viewModel.TestDa.setRowFocus(parseInt(ri));
                        viewModel.TestDa.setRowSelect(parseInt(ri));
                    }
                    viewModel.TestFormDa.setSimpleData(viewModel.TestDa.getSimpleData({type: 'select'}));
                    var userId = viewModel.TestFormDa.getValue("id");
                    if (userId == null || userId == "") {
                        viewModel.TestBDa.removeAllRows();
                        viewModel.TestBDa.clear();
                    } else {
                        viewModel.event.getUserJobList();
                    }
                },
                selectUserJob: function (row, e) {
                    var ri = e.target.parentNode.getAttribute('rowindex')
                    if (ri != null) {
                        viewModel.TestBDa.setRowFocus(parseInt(ri));
                        viewModel.TestBDa.setRowSelect(parseInt(ri));
                    }
                },
                //选择参照（树）
                selectCurrtype_Test: function () {
                    var treeSet = this.treeSetting;
                    var title = '请选择参照值';
                    var url = ctx + '/Test/Currtype/listall';
                    viewModel.event.showCurrtypeTreeDiv_Test(null, url, title, this.treeSetting);
                },
                
                /**
                 *  sendjosn 发送的数据
                 *  ajaxurl 请求的地址
                 *  title 弹窗标题
                 *  treeset 树控件的配置obj
                 */
                showCurrtypeTreeDiv_Test: function (sendjson, ajaxurl, treetitle, treeset) {
                    $.ajax({
                        type: "GET",
                        url: ajaxurl,
                        contentType: 'application/json;charset=utf-8',
                        dataType: 'json',
                        success: function (res) {
                            if (res) {
                                $(element).find('#tree-title').html(treetitle);
                                viewModel.refCurrtypeDa.setSimpleData(res.detailMsg.data);
                                $("#currtypeForTestTree")[0]['u-meta'].tree.expandAll(true);
                                window.md = u.dialog({
                                    id: 'commonShowDialog',
                                    content: '#tree-currtypeForTest',
                                    hasCloseMenu: true
                                });
                                viewModel.event.bindClickButton($('#confirm_select_CurrtypeToTest'), null, viewModel.event.confirmSelectCurrtypeToTest);
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
                
                /**选中某一个参照到主表 */
                confirmSelectCurrtypeToTest: function () {
                    var zTree = $("#currtypeForTestTree")[0]['u-meta'].tree;
                    var selectNode = zTree.getSelectedNodes();
                    if (selectNode) {
                        var id = zTree.getSelectedNodes()[0].id;
                        var node = viewModel.refCurrtypeDa.getRowByField('pk_currtype', id).getSimpleData();
                        viewModel.TestFormDa.setValue('currtype', node.pk_currtype)
                        viewModel.TestFormDa.setValue('currtype_name', node.name);
                    }
                    viewModel.event.mdClose();
                },               
               

                /**
                 * 树弹窗公共方法中取消按钮
                 */

                mdClose: function () {
                    md.close();
                },                

                /**绑定弹出层 树的按钮 */
                bindClickButton: function (ele, data, functionevent) { //对某一个按钮进行  点击事假绑定 ele:被绑定的元素，  data：需要传递的数据，functionevent：绑定的方法
                    $(ele).unbind('click'); //取消之前的绑定
                    $(ele).bind('click', data, functionevent); //重新绑定
                },

                                      

                /**子表列表 */
                getUserJobList: function () {
                    var userId = viewModel.TestFormDa.getValue("id");
                    var jsonData = {
                        pageIndex: 0,
                        pageSize: viewModel.pageSize,
                        sortField: "ts",
                        sortDirection: "asc"
                    };
                    jsonData['search_fk_id_testb'] = userId;
                    $.ajax({
                        type: 'GET',
                        url: ctx + '/TestB/list',
                        datatype: 'json',
                        data: jsonData,
                        contentType: 'application/json;charset=utf-8',
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    if (res.detailMsg.data) {
                                        viewModel.TestBDa.removeAllRows();
                                        viewModel.TestBDa.clear();
                                        viewModel.TestBDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                        
                                        viewModel.TestBFormDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
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
                    viewModel.TestBFormDa.createEmptyRow();
                },
                delUserJob: function () {
                    var userJobs = viewModel.TestBFormDa.getSimpleData({type: 'select'})
                    if (userJobs.length < 1) {
                        u.messageDialog({
                            msg: "请选择要删除的行!",
                            title: "提示",
                            btnText: "OK"
                        });
                    }

                    if (confirm("确定要删除吗?")) {
                        var jsonDel = viewModel.TestBFormDa.getSimpleData({type: 'focus'});
                        var index = viewModel.TestBFormDa.getFocusIndex();
                        if (jsonDel[0].id == null) {
                            viewModel.TestBFormDa.removeRows(index);
                            return;
                        }
                        $.ajax({
                            type: "post",
                            url: ctx + "/TestB/del",
                            contentType: 'application/json;charset=utf-8',
                            data: JSON.stringify(jsonDel[0]),
                            success: function (res) {
                                if (res) {
                                    if (res.success == 'success') {
                                       /* u.showMessage({
                                            msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>删除成功",
                                            position: "center"
                                        })*/
                                        viewModel.TestBFormDa.removeRows(index);
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
                
 


            } // end  event

        };
        //end viewModel


        $(element).html(template);
        var app = u.createApp({
            el: '#content',
            model: viewModel
        });
        viewModel.md = $(element).find('#user-mdlayout')[0]['u.MDLayout'];
        var paginationDiv = $(element).find('#pagination')[0];
        viewModel.comps = new u.pagination({el: paginationDiv, jumppage: true});
        
        viewModel.child_list_pcomp = new u.pagination({el: $(element).find('#child_list_pagination')[0], jumppage: true});
        viewModel.child_card_pcomp = new u.pagination({el: $(element).find('#child_card_pagination')[0], jumppage: true});
        viewModel.childdraw=1 ;

        viewModel.event.initUerList();
        viewModel.event.pageChange();
        viewModel.event.sizeChange();

    }  //end init

    return {
        'model': init.viewModel,
        'template': template,
        init: function (params, arg) {
            init(params, arg);
            /*回车搜索*/
            $('.search-enter').keydown(function (e) {
                if (e.keyCode == 13) {
                    $('#user-action-search').trigger('click');

                }
            });
        }
    }
});
//end define
