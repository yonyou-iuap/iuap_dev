define(['text!pages/dict/dicttype.html', 'pages/dict/dictmeta', 'css!pages/dict/dicttype', 'uuigrid','config/sys_const'], function (html) {

    var init = function (element, params) {
        var viewModel = {

            //元数据持久化方式  /sysDictTypeMeta
            //listurl : '/sysDictTypeMeta/list',
            //addurl :  '/sysDictTypeMeta/createRow',
            //saveurl : '/sysDictTypeMeta/save',
            //delurl  :  '/sysDictTypeMeta/del',

            //mybatis持久化方式
            listurl: '/sysDictType/list',
            addurl: '/sysDictType/createRow',
            saveurl: '/sysDictType/save',
            delurl: '/sysDictType/del',
            yesOrNo: [{name: "是", value: "Y"}, {name: "否", value: "N"}],
            dictTypeDa: new u.DataTable(dictmeta),
            event: {
                initDictTypeList: function () {
                    var nowPageIndex = viewModel.dictTypeDa.pageIndex();
                    if (viewModel.dictTypeDa.hasPage(nowPageIndex)) {
                        viewModel.dictTypeDa.setCurrentPage(nowPageIndex)
                    } else {
                        var queryData = {};
                        $(".form-search").find(".input_search").each(function () {
                            queryData[this.name] = removeSpace(this.value) ;
                        });
                        viewModel.dictTypeDa.addParams(queryData);
                        app.serverEvent().addDataTable("dictTypeDa").fire({
                            url: ctx + viewModel.listurl,
                            success: function (data) {

                            }
                        })
                    }
                },
                pageChange: function (index) {
                    viewModel.dictTypeDa.pageIndex(index);
                    viewModel.event.initDictTypeList();
                },
                sizeChange: function (size) {
                    viewModel.dictTypeDa.clear();
                    viewModel.dictTypeDa.pageSize(size);
                    viewModel.event.initDictTypeList();
                },
                search: function () {
                    viewModel.dictTypeDa.clear();
                    var queryData = {};
                    $(".form-search").find(".input_search").each(function () {
                        queryData[this.name] = removeSpace(this.value);
                    });
                    viewModel.dictTypeDa.addParams(queryData);
                    app.serverEvent().addDataTable("dictTypeDa").fire({
                        url: ctx + viewModel.listurl,
                        success: function (data) {

                        }
                    })
                },
                cleanSearch: function () {
                    $(element).find('.form-search').find('input').val('');
                },
                addClick: function () {
                    app.serverEvent().addDataTable("dictTypeDa").fire({
                        url: ctx + viewModel.addurl,
                    })
                },
                getRowData: function (rows) {  //rows 表示行数据对象
                    var rowsdata = [];
                    for (var i = 0; i < rows.length; i++) {
                        var d = rows[i].getSimpleData();
                        rowsdata.push(d);
                    }
                    return rowsdata;
                },
                saveClick: function () {
                    //以下compsValidate是验证输入格式。开发调试，暂时不用
                    if (!app.compsValidate($(element).find('#list')[0])) {
                        return;
                    }

                    app.serverEvent().addDataTable("dictTypeDa", 'change').fire({
                        url: ctx + viewModel.saveurl,
                        success: function (data) {
                            u.showMessage({msg: '操作完成'})
                        }
                    })
                },

                delRow: function (data, index) {
                    if (typeof(data) == 'number') {
                        viewModel.dictTypeDa.setRowSelect(index);
                    }
                    u.confirmDialog({
                        msg: '<div class="pull-left col-padding" >' +
                        '<i class="fa fa-exclamation-circle margin-r-5 fa-3x orange" style="vertical-align:middle"></i>确认删除这些数据吗？</div>',
                        title: '警告',
                        onOk: function () {
                            app.serverEvent().addDataTable("dictTypeDa", 'allSelect').fire({
                                url: ctx + viewModel.delurl,
                                success: function (data) {
                                    /* u.showMessage({msg: '操作完成'})*/
                                }
                            })
                        }
                    });
                },
                showValue: function (obj) {
                    var showValue = '';
                    if (obj.value === 'Y') {
                        showValue = '是';
                    } else {
                        showValue = '否';
                    }
                    obj.element.innerHTML = showValue;
                }
            }
        }		//end viewModel

        $(element).html(html);
        var app = u.createApp({
            el: element,  // el: '#content',
            model: viewModel
        });
        viewModel.event.initDictTypeList();
        $('.search-enter').keydown(function (e) {
            if (e.keyCode == 13) {
                $('#user-action-search').trigger('click');
				u.stopEvent(e);
            }
        });
    }

    return {
        'template': html,
        init: init
    }
});//end define
