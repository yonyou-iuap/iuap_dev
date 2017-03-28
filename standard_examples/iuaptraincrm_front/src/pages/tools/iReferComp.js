'use strict';

define(['refComp'], function (refComp) {
	u.NcRefComps = u.BaseAdapter.extend({
		mixins: [u.ValueMixin, u.EnableMixin, u.RequiredMixin, u.ValidateMixin],
		init: function init() {
			var self = this;
			this.element = this.element.nodeName === 'INPUT' ? this.element : this.element.querySelector('input');
			if (!this.element) {
				throw new Error('not found INPUT element, u-meta:' + JSON.stringify(this.options));
			};
			this.validType = this.options['validType'] || 'string';
			this.minLength = this.getOption('minLength');
			this.maxLength = this.getOption('maxLength');
			u.on(this.element, 'keydown', function (e) {
				var keyCode = e.keyCode;
				if (e.keyCode == 13) {
					// 回车
					this.blur();
				}
			});

			u.on(this.element, 'blur', function (e) {
				if (self.enable) {
					if (!self.doValidate() && self._needClean()) {
						if (self.required && (self.element.value === null || self.element.value === undefined || self.element.value === '')) {
							// 因必输项清空导致检验没通过的情况
							self.setValue('');
						} else {
							self.element.value = self.getShowValue();
						}
					} else self.setValue(self.element.value);
				}
			});
		},
		modelValueChange: function modelValueChange(val) {
			if (val == '' || !val) {
				$(this.element).val('');
			} else {
				this.setShowValues(val);
			}
		},
		setValue: function setValue(value) {},
		/* istranslate为true就需要请求服务器翻译*/
		setShowValues: function setShowValues(value) {
			var refpk = $(this.element).attr('data-pk'),
			    refname = $(this.element).attr('data-name'),
			    istranslate = false,
			    refcode = null;
			if (value === refpk) {
				//参照弹出框选中参照时设值
				$(this.element).val(refname);
				$(this.element).attr('title', refname);
				istranslate = true;
			}
			if (!istranslate) {
				//查找缓存
				var meta = $(this.element).parent().attr('u-meta'),
				    refcode = JSON.parse(meta)['refCode'],
				    refCodes = window.iref[refcode],
				    refPk = '',
				    refname = '',
				    allref = {};
				if (value && value != '' && refCodes) {
					var pks = value.split(',');
					var length = pks.length;
					for (var i = 0; i < length; i++) {
						if (refCodes[pks[i]]) {
							refname += refCodes[pks[i]];
						}
						refPk += "'" + pks[i] + "',";
					}
				} else if (value && value != '') {
					var pks = value.split(',');
					var length = pks.length;
					for (var i = 0; i < length; i++) {
						refPk += "'" + pks[i] + "',";
					}
				}

				if (!istranslate) {
					refPk = refPk.substr(0, refPk.length - 1);
					allref[refcode] = refPk;
					this.setShowValueFormSer(allref, refcode);
				} else {
					refname = refname.substr(0, refname.length - 1);
					$(this.element).val(refname);
					$(this.element).attr('title', refname);
				}
			}
		},
		setShowValueFormSer: function setShowValueFormSer(refpk, refcode) {
			var that = this;
			$.ajax({
				type: "get",
				url: '/uitemplate_web/iref_ctr_ex/initRefData/',
				data: refpk,
				contentType: 'application/json',
				dataType: "json",
				success: function success(Data) {
					if (Data) {
						//把请求的数据放入缓存，以便多次使用
						setRefToCache(Data, null);
						var refArr = Data[refcode];
						var refname = '';
						for (var i in refArr) {
							refname += refArr[i] + ',';
						}
						refname = refname.substr(0, refname.length - 1);
						$(that.element).val(refname);
						$(this.element).attr('title', refname);
					}
				}
			});
		}
	});

	u.compMgr.addDataAdapter({
		adapter: u.NcRefComps,
		name: 'uiRefer'
	});
	/*参照联动
 
  * filed 关联字段
 
  * pks 关联字段pk
 
  * id 联动参照对应的id
 
  */
	var setFilterPks = function setFilterPks(filed, pks, id) {
		var filterPksVal = null;
		var refparam = {};
		var ele = $(id)[0];
		if (!ele) return;
		var srefparam = $(ele).attr('data-refparam');
		if (srefparam) {
			refparam = JSON.parse($(ele).attr('data-refparam'));
			var pkValue = refparam.pk_val;
		}
		var value = filed + '=' + pks;
		if (!pkValue || !srefparam) {
			//当联动参数没值时
			if (pks || pks != '') {
				//如果为空或不存在则删除该联动条件
				filterPksVal = new Array(value);
			}
		} else {
			filterPksVal = pkValue;
			var length = filterPksVal.length;
			for (var i = 0; i < length; i++) {
				var index = filterPksVal[i].indexOf(filed);
				if (index != -1) {
					//当该联动条件存在时进行的操作
					if (!pks || pks == '') {
						//如果为空或不存在则删除该联动条件
						filterPksVal.splice(i, 1);
					} else {
						//修改该联动条件
						filterPksVal[i] = value;
					}
					break;
				}
			}
			if (i >= length) {
				//表示filterPksVal里不存在
				filterPksVal.push(value);
			}
		}
		if (filterPksVal) refparam.pk_val = filterPksVal;
		$(ele).attr('data-refparam', JSON.stringify(refparam));
	};
	/*
 
  ele:一个被赋值的数据集作用范围
 
  data:data
 
  gridid:表格id（可选，当girdid）
 
  * */
	var setFormData = function setFormData(ele, data, gridid, callfn) {
		if (Object.prototype.toString.call(data) != '[object Array]') {
			data = new Array(data);
		}
		if (!gridid) {
			var ds = null;
			var allref = {};
			$(ele).find('.ref').each(function () {
				var refpk = '';
				var meta = eval('([' + $(this).attr('u-meta') + '])')[0];
				if (meta && meta['refCode']) {
					if (!ds) {
						var contentId = $(this).attr('data-contentId');
						ds = $('#' + contentId).data('uui.refer').dataTable;
					}
					var fieldpk = data[0][meta['field']];
					if (fieldpk && fieldpk != '') {
						var arrtemp = fieldpk.split(',');
						var length = arrtemp.length;
						for (var i = 0; i < length; i++) {
							refpk += "'" + arrtemp[i] + "',";
						}
						refpk = refpk.substr(0, refpk.length - 1);
						allref[meta['refCode']] = allref[meta['refCode']] ? allref[meta['refCode']] + ',' + refpk : refpk;
					}
				}
			});
			if (!$.isEmptyObject(allref)) {
				getFormRefNameByPks(allref, ele, data, callfn);
			} else {
				ds.setSimpleData(data);
			}
		} else {
			setGridToForm(ele, data, gridid);
		}
	};
	function getFormRefNameByPks(allref, ele, data, callfn) {
		//根据给的refcode和pk请求服务器，返回pk,name
		$.ajax({
			type: "get",
			url: '/uitemplate_web/iref_ctr_ex/initRefData/',
			data: allref,
			contentType: 'application/json',
			dataType: "json",
			success: function success(Data) {
				if (Data) {
					//把请求的数据放入缓存，以便多次使用
					setRefToCache(Data, null);
					var ds = null;
					$(ele).find('.ref').each(function () {
						var meta = eval('([' + $(this).attr('u-meta') + '])')[0];
						if (meta && meta['refCode']) {
							if (!ds) {
								var contentId = $(this).attr('data-contentId');
								ds = $('#' + contentId).data('uui.refer').dataTable;
								ds.setSimpleData(data);
							}
							var field = ds.getValue(meta['field']);
							var refCodes = window.iref[meta['refCode']];
							var refname = '';
							if (field && field != '') {
								var pks = field.split(',');
								var length = pks.length;
								for (var i = 0; i < length; i++) {
									refname += refCodes[pks[i]] ? refCodes[pks[i]] + ',' : '';
								}
							}
							refname = refname.substr(0, refname.length - 1);
							$(this).find('input').val(refname);
						}
					});
					if (Object.prototype.toString.call(callfn) === '[object Function]') callfn.call(ds, data);
				}
			}
		});
	}
	/*
 
  把表格数据转换到表单页面进行编辑时调用
 
  ele:表单页面一个数据集对应区域
 
  gridid：表格id
 
  * */
	function setGridToForm(ele, data, gridid) {
		var ds = null;
		var grid = $('#' + gridid).parent()[0]['u-meta'].grid;
		$(ele).find('.ref').each(function () {
			var meta = eval('([' + $(this).attr('u-meta') + '])')[0];
			if (meta && meta['refCode']) {
				if (!ds) {
					var contentId = $(this).attr('data-contentId');
					ds = $('#' + contentId).data('uui.refer').dataTable;
					ds.clear();
					ds.setSimpleData(data);
				}

				var refname = $('#' + gridid + ' .uuigrid-content-sel-row').find('.' + meta['field']).attr('data-refname');
				var refpk = data[meta['field']];
				//ds.setValue(meta['field'], refpk);
				$(this).find('input').val(refname);
			}
		});
	}
	/*
 
  给带参照的表格设置数据，通过该方法来设置数据可以大量减少ajax请求
 
  data：数据
 
  gridid：表格id
 
  option:setSimpleData的第二个参数
 
  * */
	var setSimpData = function setSimpData(data, gridid, option, afterClick) {
		var flag = false;
		var grid = $('#' + gridid).parent()[0]['u-meta'].grid;
		var refpks = getRefPkByDataTable(grid, data);
		if (!$.isEmptyObject(refpks)) {
			//如果缓存中有一个字段没有，则单独请求该字段
			getRefNameByPk(refpks, grid, data, true, option, afterClick);
		} else {
			grid.dataTable.setSimpleData(data, option);
		}
	};

	function getRefPkByDataTable(gird, datas) {
		var columns = gird.gridCompColumnArr;
		var allref = {};
		if (!datas || datas.length <= 0) {
			return;
		}
		var length = columns.length;
		for (var col = 0; col < length; col++) {
			//把数据集中所有的参照对应的pk提前出来
			var refcode = columns[col].options['refCode'];
			if (refcode) {
				var refPk = '';
				var refpkStack = {}; //去掉重复pk，减少请求时流量
				var i = 0;
				var field = columns[col].options['field'];
				if (!window.iref) window.iref = {};
				var refData = window.iref[refcode];
				for (var data in datas) {
					var pk = datas[data][field];
					if (i++ < datas.length) {
						if (pk && !refpkStack[pk] && (!refData || !refData[pk])) refPk += "'" + pk + "',";
					} else refPk = refPk.substr(0, refPk.length - 1);
					refpkStack[pk] = pk;
				}
				if (refPk != '') allref[refcode] = allref[refcode] ? allref[refcode] + ',' + refPk : refPk;
			}
		}
		return allref;
	}
	/*
 
  根据pk在服务器请求参照
 
  pk,name
 
  走渲染函数是flag是false，只要setData才为ture
 
  * */
	function getRefNameByPk(data, obj, list, flag, option, afterClick) {
		//根据给的refcode和pk请求服务器，返回pk,name
		$.ajax({
			type: "get",
			url: '/uitemplate_web/iref_ctr_ex/initRefData/',
			data: data,
			contentType: 'application/json',
			dataType: "json",
			success: function success(Data) {
				if (Data) {
					//把请求的数据放入缓存，以便多次使用
					setRefToCache(Data, obj);
					if (flag) {
						var pageSize = obj.dataTable.pageSize(); //每页显示多少条
						var totalPages = obj.dataTable.totalPages(); //共多少页
						var totalRow = obj.dataTable.totalRow();

						obj.dataTable.setSimpleData(list, option);
						obj.dataTable.pageSize(pageSize); //每页显示多少条
						obj.dataTable.totalPages(totalPages); //共多少页
						obj.dataTable.totalRow(totalRow);
					} else {
						//处理通过渲染函数调用的
						//if(!$.isEmptyObject(Data[obj.gridCompColumn.options.refCode]))//当渲染函数调用该请求后台时，防止该pk无对应值时循环请求
						var refCode = obj.gridCompColumn.options['refCode'];
						var refs = window.iref[refCode];
						var displayName = '',
						    refpks = null;
						if (!$.isEmptyObject(refs) && !$.isEmptyObject(data)) {
							refpks = data[refCode].replace(/'/g, "").split(',');
							for (var i = 0; i < refpks.length; i++) {
								if (!$.isEmptyObject(refpks[i]) && !$.isEmptyObject(refs[refpks[i]])) {
									displayName += refs[refpks[i]];
								}
							}
						}
						if (displayName == '') {
							displayName = obj.value;
						}
						$(obj.element).attr('data-refname', displayName); //参照名字备份
						$(obj.element).html(displayName);
						$(obj.element).parent().attr('title', displayName);
						var field = obj.gridCompColumn.options.field;
						var refid = field + obj.row.value['$_#_@_id'];
						//表单形式编辑是给输入框初始化值
						if (obj.gridObj.options['editType'] == "form") {
							$('#' + refid).find('input').val(displayName);
						}
						//obj.gridObj.options.editable = true;
						//obj.gridObj.setRenderType(obj.gridCompColumn.options.field, ReferRender);
					}
					if (afterClick && Object.prototype.toString.call(afterClick) === '[object Function]') afterClick.call(obj.dataTable, list);
				}
			}
		});
	}
	function setRefToCache(data, obj) {
		if (!data) return;
		for (var r in data) {
			if (data.hasOwnProperty(r)) {
				if (!window.iref) window.iref = {};
				//window.iref[r] = data[r];
				if (window.iref[r]) {
					//refcode上已经存在别的pk，则继续添加
					window.iref[r] = $.extend({}, window.iref[r], data[r]);
				} else {
					window.iref[r] = data[r];
				}
			}
		}
	}
	/**
 
  * grid控件 edittype
 
  * @param {Object} options
 
  * options:{gridObj,element,value,field,rowObj}
 
  */
	var ReferEditType = function ReferEditType(obj) {
		if (!window.refDiv) {
			window.refDiv = {};
		}
		var afterClick = null,
		    afterEType = null;
		var field = obj.field + obj.rowObj['$_#_@_id'];
		if (!window.refDiv[field]) {
			var refCode = '';
			var gridCompColumnArr = obj.gridObj.gridCompColumnArr;
			for (var i = 0; i < gridCompColumnArr.length; i++) {
				//
				if (obj.gridObj.gridCompColumnArr[i].options.field == obj.field) {
					var options = obj.gridObj.gridCompColumnArr[i].options;
					refCode = options.refCode;
					if (options.refOptions && options.afterEType) {
						afterEType = obj.gridObj.viewModel[options.afterEType];
					}

					if (options.refOptions && options.refOptions.afterClick) {
						afterClick = obj.gridObj.viewModel[options.refOptions.afterClick];
					}

					break;
				}
			}
			
			var rvalue = $(obj.element).parent().attr('data-refname');
			if (typeof rvalue == 'undefined') {
				rvalue = '';
			}
			var html = '<div class="input-group date form_date col-md-3" id="' + field + '" u-meta=\'{"type":"uiRefer", "data":"' + obj.gridObj.dataTable.id + '", "field":"' + obj.field + '"}\' style="width:100%;  position: absolute;">' + '<input id="testRefMuGridTree" class="u-input  grid-ref" type="text" value="' + rvalue + '" style="color:rgb(51, 51, 51);  padding-left: 0.833333333em;height: 100%;display: inline-block;line-height: 100%;padding: 0px;background-color: rgb(255, 255, 255);">' + '<span class="u-form-control-feedback fa fa-search refer" style="line-height: inherit;"></span>' + '</div>';
			var innerDom = u.makeDOM(html);
			$(obj.element).html(innerDom);

			//zhanghy7
			var rowId = $(obj.element).parentsUntil("tr").parent().index();

			var options = {
				refCode: refCode,
				selectedVals: obj.value
			};
			var refOptions = obj.gridObj.gridCompColumnArr[i].options.refOptions;
			if (refOptions) options = $.extend({}, options, refOptions);
			$('#refContainer' + field).remove();
			window.refComp.initRefComp($('#' + field), options,rowId);
		}
		$(obj.element).css('margin', 0);

		var refObj = $('#refContainer' + field).data('uui.refer');
		if(!refObj.dataTable){
			refObj.dataTable = obj.gridObj.dataTable;
		}

		if (window.refDiv[field]) {
			$(obj.element).html(window.refDiv[field]);
			refObj.bindFirstEvent();
			afterClick = window.refDiv[field].afterClick;
			afterEType = window.refDiv[field].afterEType;
		}

		var refDom = $(obj.element).find('.form_date');
		refDom.afterClick = afterClick;
		refDom.afterEType = afterEType;
		window.refDiv[field] = refDom;

		//表单形式编辑是给输入框初始化值
		if (obj.gridObj.options['editType'] == "form") {
			var showValue = $('.uuigrid-content-focus-row').find('.' + obj.field).attr('data-refname');
			if (!showValue) {
				showValue = '';
			}
			$(obj.element).find('input').val(showValue);
		}
		if (afterEType && Object.prototype.toString.call(afterEType) === '[object Function]') afterEType.call(obj, field);
		if (afterClick && Object.prototype.toString.call(afterClick) === '[object Function]') afterClick.call(obj, field);
	};

	/**
 
  *grid 控件 render 
 
  */
	var ReferRender = function ReferRender(obj) {
		var html = "";
		var displayName = obj.value;
		var allref = {};
		var refPk = '';
		var name = '';
		var refCode = obj.gridCompColumn.options.refCode;
		var field = obj.gridCompColumn.options.field;
		var datatable = obj.gridObj.dataTable;
		var refname = datatable.meta[field].refname;
		var refid = field + obj.row.value['$_#_@_id'];
		var active = false;
		var oldValue = $(obj.element).attr('data-value'); //
		if (oldValue == obj.value) {
			//行点击时触发的事件
			displayName = $(obj.element).attr('data-refname');
			html = "<span>" + displayName + "</span>";
			var innerDom = u.makeDOM(html);
			obj.element.appendChild(innerDom);
			return;
		}

		$('.active').each(function () {
			//表格失去焦点触发该函数时，判断是否有参照框弹出
			if ($(this)[0].id.indexOf('refContainer') != -1) active = true;
		});
		if (!$('#refContainer' + refid).hasClass('active') && active) {
			//表格中某一行失去焦点时会验证，会调用渲染函数，防止不必要的ajax请求
			displayName = $(obj.element).attr('data-refname');
			html = "<span>" + displayName + "</span>";
			var innerDom = u.makeDOM(html);
			obj.element.appendChild(innerDom);
			return;
		}
		if ($('#refContainer' + refid).hasClass('active') && !refname) {
			////当点击参照时，参照弹出会让表格失去焦点会触发该方法
			return;
		}

		if (refCode && obj.value != '' && !refname) {
			//判断是否为参照
			if (!window.iref) window.iref = {};
			var refData = window.iref[refCode];
			var values = obj.value.split(',');
			if (refData) {
				for (var i = 0; i < values.length; i++) {
					if (!refData[values[i]]) {
						refPk += "'" + values[i] + "',";
					} else {
						name += refData[values[i]] + ',';
					}
				}

				if (refPk == '') {
					//缓存中存在该参照直接渲染
					displayName = name.substr(0, name.length - 1);
				} else {
					//该参照对应的pk不存在，到服务器查询
					refPk = refPk.substr(0, refPk.length - 1);
					allref[refCode] = refPk;
					getRefNameByPk(allref, obj);
				}
			} else {
				for (var i = 0; i < values.length; i++) {
					refPk += "'" + values[i] + "',";
				}
				refPk = refPk.substr(0, refPk.length - 1);
				allref[refCode] = refPk;
				getRefNameByPk(allref, obj);
			}
		}
		if (refname) {
			//参照点击确认时赋值
			displayName = refname;
			delete datatable.meta[field].refname;
		}
		if (!$(obj.element).hasClass(field)) {
			$(obj.element).addClass(field);
		}
		$(obj.element).attr('data-value', obj.value); //
		$(obj.element).attr('data-refname', displayName); //参照名字备份
		html = "<span>" + displayName + "</span>";
		var innerDom = u.makeDOM(html);
		obj.element.appendChild(innerDom);
		$(obj.element).parent().attr('title', displayName);
		//表单形式编辑是给输入框初始化值
		if (obj.gridObj.options['editType'] == "form") {
			$('#' + refid).find('input').val(displayName).attr('title', displayName);
		}
		//obj.gridObj.options.editable = true;
	};

	window.iref = {};
	window.refDiv = {};
	window.refComp = refComp;
	window.ReferRender = ReferRender;
	window.ReferEditType = ReferEditType;
	return {
		setFilterPks: setFilterPks,
		setSimpleData: setSimpData,
		setFormData: setFormData
	};
});