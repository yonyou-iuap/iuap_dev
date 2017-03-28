'use strict';

window.addRouter = function(path, func) {
	var pos = path.indexOf('/:');
	var truePath = path;
	if (pos != -1)
		truePath = path.substring(0, pos);
	func = func || function() {
		var params = arguments;
		initPage('pages/' + truePath, params);
	};
	var tmparray = truePath.split("/");
	if (tmparray[1] in router.routes
			&& tmparray[2] in router.routes[tmparray[1]]
			&& tmparray[3] in router.routes[tmparray[1]][tmparray[2]]) {
		return;
	} else {
		router.on(path, func);
	}
};

window.router = Router();
window.ko = ko;
window.ctx = '/uitemplate_web';
window.ctxfilemng = 'iuap-saas-filesystem-service/';
window.syc_fixcode_map = {};
var first_page_id = null;
var initMenuTree = function initMenuTree() {
	$('.list-group li > a').on(
			'click',
			function(e) {
				if ($(this).children('.title').length > 0
						&& !$(this).children('.title').is(':visible')) {
					$('#show_side').click();
				}
				if ($(this).next().hasClass('sub-menu') === false) {
					return;
				}
				var parent = $(this).parent().parent();
				parent.children('li.open').children('a').children('.arrow')
						.removeClass('open').removeClass(
								'glyphicon-chevron-down').addClass(
								'glyphicon-chevron-left');
				parent.children('li.open').children('a').children('.arrow')
						.removeClass('active');
				parent.children('li.open').children('.sub-menu').slideUp(200);
				parent.children('li').removeClass('open');
				// parent.children('li').removeClass('active');

				var sub = $(this).next();
				if (sub.is(":visible")) {
					$('.arrow', $(this)).removeClass("open").removeClass(
							'glyphicon-chevron-down').addClass(
							'glyphicon-chevron-left');
					$(this).parent().removeClass("active");
					sub.slideUp(200);
				} else {
					$('.arrow', $(this)).addClass("open").removeClass(
							'glyphicon-chevron-left').addClass(
							'glyphicon-chevron-down');
					$(this).parent().addClass("open");
					sub.slideDown(200);
				}

				e.preventDefault();
			});
};

//var errors ;
$(function() {
	
//	require(["pages/tools/error"],function(module){
//		errors = module;
//	});
	
	// $.getJSON("/pages/subsidy/menu.json", function (data) {
	// initFuncTree(data);
	// ready();
	// });

	$("#userNameId").html($.cookie("userCNName"));
	$
			.ajax({
				type : 'GET',
				url : window.cturl + "/security/extfunctiondefine/rootmenu",
				data : '',
				dataType : 'json',
				success : function(data) {
					initFuncTree(data);
					/**
					 */
					// ready();
					// modify by billLiu 多页签
					// $('#menu').find("a[href*='#']").each(function () {
					// var path = this.hash.replace('#', '');
					// addRouter(path);
					// });
					// 增加控制台路由
					// addRouter("dashboard/dashboard");
					window.router.init();

					$("body").css("overflow-y", "hidden");

					$(".nav-tabs")
							.contextmenu(
									{
										target : '#context-menu',
										onItem : function(context, e) {
											var operAction = $(e.target).text();
											if ("关闭当前" == operAction) {
												var curTabId = $("li.active")
														.attr('id');
												if (null != curTabId
														&& first_page_id != curTabId) {
													curTabId = curTabId
															.substr(4);
													closeTab(curTabId);
												}
											} else if ("关闭其他" == operAction) {
												var liTabArr = $(".nav-tabs > li")
												for (var i = 0; i < liTabArr.length; i++) {
													var tabId = liTabArr[i].id;
													var curTabId = $(
															"li.active").attr(
															'id');
													if (null != tabId
															&& null != curTabId
															&& tabId != curTabId
															&& first_page_id != tabId) {
														tabId = tabId.substr(4);
														closeTab(tabId);
													}
												}
											} else if ("全部关闭" == operAction) {
												var liTabArr = $(".nav-tabs > li")
												for (var i = 0; i < liTabArr.length; i++) {
													var tabId = liTabArr[i].id;
													if (null != tabId
															&& first_page_id != tabId) {
														tabId = tabId.substr(4);
														closeTab(tabId);
													}
												}
											}
										}
									});

					// window.location="#dashboard/dashboard";
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});

	// if (window.location.href.indexOf("#") < 0) {
	// window.router.setRoute($('#menu').find("a[href*='#']")[0].hash.replace('#',
	// ''));
	// }
});

function modifyPassword() {
	window.md = u.dialog({
		id : 'modifypasswordDialg',
		content : "#dialog_modifypassword",
		hasCloseMenu : true
	});
}

function onClosePasswordWindow() {
	window.md.close();
}

function commitPsw() {
	var oldpwd = $("#oldpwd").val();
	var newpwd = $("#newpwd").val();
	var newpwd2 = $("#newpwd2").val();
	var errorMsg = "";
	if (null == oldpwd || oldpwd.length == 0) {
		errorMsg = errorMsg + "请输入原密码；"
	}
	if (null == newpwd || newpwd.length == 0) {
		errorMsg = errorMsg + "请输入新密码；"
	}
	if (null == newpwd2 || newpwd2.length == 0) {
		errorMsg = errorMsg + "请输入新密码确认；"
	}
	if (null != newpwd && null != newpwd2 && newpwd != newpwd2) {
		errorMsg = errorMsg + "新密码与新密码确认不一致，请重新输入；"
	}
	var pwdObj = {};
	pwdObj["oldpwd"] = oldpwd;
	pwdObj["newpwd"] = newpwd;
	$.ajax({
		type : 'post',
		url : window.cturl + "/system/user/modifypwd",
		data : pwdObj,
		dataType : 'json',
		success : function(data) {
			u.messageDialog({
				msg : data.msg,
				title : "提示",
				btnText : "OK"
			});
			if ("true" == data.flag) {
				onClosePasswordWindow();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			errors.error(XMLHttpRequest);
		}
	});
}

/*******************************************************************************
 * 递归生成树形菜单节点
 ******************************************************************************/
function createTreeNode(_data, _element, parentimg) {

	for (var i = 0, len = _data.length; i < len; i++) {
		var _dTemp = _data[i];
		var _isHasChildren = _dTemp.hasOwnProperty('children')
				&& _dTemp['children'].length > 0;
		var $content = $('#content')
		var $navItem = $('<li class="nav-item">');
		var $navLink = $('<a href="javascript:void(0);" class="nav-link nav-toggle">');
		/**
		 * 菜单的图标 来源3类： 1 自行设置的图标 2 父节点的图标 3 默认图标 1 级别最高
		 */
		var thismsg = "icon-home";
		if (null != _dTemp["imgcode"] && _dTemp["imgcode"].length > 0) {
			thismsg = _dTemp["imgcode"];
		} else if (null != parentimg && parentimg.length > 0) {
			thismsg = parentimg;
		}
		var $icon = $('<i class="' + thismsg + '">');

		var $title = $('<span class="title">');
		var $selected = $('<span class="selected">');
		var $arrow = $('<span class="arrow">');
		var $subMenu = $('<ul class="sub-menu">');
		$title.text(_dTemp['funcName']);
		$navLink.append($icon);
		$navLink.append($title);
		$navItem.append($navLink);

		if (!_isHasChildren) {
			// 多页签
			// $navLink.prop('href','#'+_dTemp['menuUrl']);
			$navLink.prop('href', "javascript:menuClick({id:'" + _dTemp['id']
					+ "',title: '" + _dTemp['funcName']
					+ "',close: true,url: '" + _dTemp['menuUrl'] + "'});");
			// 路由设置
			if (null != _dTemp['menuUrl'] && _dTemp['menuUrl'].length > 0) {
				addRouter(_dTemp['menuUrl']);
			}
		} else {
			$navLink.append($arrow);
			createTreeNode(_dTemp['children'], $subMenu, thismsg);
			$navItem.append($subMenu);
		}
		// 首页无需加载
		if ("dashboard/dashboard" != _dTemp['menuUrl']) {
			_element.append($navItem);
		} else {
			first_page_id = 'tab_tab_' + _dTemp['id'];
			menuClick({
				id : _dTemp['id'],
				title : _dTemp['funcName'],
				close : false,
				url : _dTemp['menuUrl']
			})
			// $("body").css("overflow-y","auto");
		}

	}

}

var initFuncTree = function initFuncTree(menuData) {
	var $elementWrap = $('#menu');
	if (menuData.parentId == -1) {
		var rootMenuArray = menuData.children;

		// for (var i = 0; i < rootMenuArray.length; i++) {
		// var menu = rootMenuArray[i];
		// var liObj = $('<li class="list-group-item firest">');
		// var aObj = $('<a href="javascript:void(0)" class="parent
		// list-group-item title">' + menu.funcName + "</a>");
		// var ulObj = $('<ul class="children slidhid ">');
		// if (menu.children.length > 0) {
		// for (var j = 0; j < menu.children.length; j++) {
		// var subMenuObj = menu.children[j];
		// var subLiObj = $('<li class="list-group-item"><a
		// class="list-group-item" href="#' + subMenuObj.menuUrl + '">' +
		// subMenuObj.funcName + "</a> </li>");
		// $(ulObj).append(subLiObj);
		// }
		// }
		// $(liObj).append(aObj).append(ulObj);
		// $(".list-group").append(liObj);
		// }
		createTreeNode(rootMenuArray, $elementWrap, null);

	}
	// initMenuTree();

	// $elementWrap.find("a[href*='#']").each(function () {
	// var path = this.hash.replace('#', '');
	// if (typeof path != "undefined" && path != "" && path != "null") {
	// addRouter(path);
	// }
	// });
};
function destroyGridForCurPage(id){
	$('#' + id)[0].querySelectorAll('[u-meta]').forEach(function (ele) {
		if (ele['u-meta']) {
			var comp = ele['u-meta'];
			if (comp && comp.type == 'grid'){
				comp.grid.destroySelf();
			}
		}
	});
}

function menuClick(options) {
	var tabsCount = $(".nav.nav-tabs > li").length;
	var tabid = "tab_" + options.id;
	var checkTab = $("#tab_" + tabid + " [role]");
	// 检查是否已经打开
	if (null != checkTab && checkTab.length > 0) {
		// 移除class
		var selTabId = $("li.active").attr('id').substr(4)
		$("#tab_" + selTabId).removeClass('active');
		$("#" + selTabId).removeClass('active');
		// 增加class
		$("#tab_" + tabid).addClass('active');
		$("#" + tabid).addClass('active');

	} else if (tabsCount >= 7) {
		u.messageDialog({
			msg : "最多只能打开六个功能菜单，请关闭后再打开新菜单！",
			title : "提示",
			btnText : "OK"
		});
	}
	// 未打开，进行初始化
	else {
		addTabs(options);
		var truePath = options.url;
		var p = 'pages/' + truePath;
		var content = document.getElementById(tabid);
		var module = p;
		requirejs.undef(module);
		require([ module ], function(module) {
			// ko.cleanNode(content);
			module.init(content, tabid);
			// up1201
			var contentMinHeight = $('#content').css('min-height');
			contentMinHeight = contentMinHeight.substr(0,
					contentMinHeight.length - 2);
			var tabsMinHeight = $('#content .nav-tabs').css('height');
			tabsMinHeight = tabsMinHeight.substr(0, tabsMinHeight.length - 2);
			var tabContentHeight = (contentMinHeight - tabsMinHeight) + "px";
			$('#content .u-mdlayout .u-mdlayout-detail').css('min-height',
					tabContentHeight);

			$('#content .tab-content').css('min-height', tabContentHeight);
		});
		
		 $("#tab_"+tabid).on("click", "[tabclose]", function (e) {
			 id = $(this).attr("tabclose");
			 destroyGridForCurPage(id);
			 closeTab(id);
		 });
		 
	}
}

function initPage(p) {
	var module = p;
	requirejs.undef(module);
	var content = document.getElementById("content");
	require([ module ], function(module) {
		ko.cleanNode(content);
		module.init(content);
		// up1201
		$('#content .u-mdlayout').css('min-height',
				$('#content').css('min-height'));
	});
}

// $(document).ready(function(){
// $('.list-group .parent').click(function(){
// var oThat = $(this).next(),
// falg = oThat.hasClass('slidshow');
// //oThat.parents('.list-group').find('.children').removeClass('slidshow').addClass('slidhid');
// if(falg){
// $(this).removeClass('active');
// $(oThat).slideUp().addClass('slidhid').removeClass('slidshow');
// }
// else{
// $(this).addClass('active');
// $(oThat).slideDown().addClass('slidshow').removeClass('slidhid');
// }
// })
//    	
// })

// 加载条
function onLoading() {
	var centerContent = '<i class="fa fa-cloud u-loader-centerContent"></i>';
	var opt1 = {
		hasback : true,
		hasDesc : true,// 是否含有加载内容描述
		centerContent : centerContent
	};
	u.showLoader(opt1);
}

// 关闭加载条
function onCloseLoading() {
	u.hideLoader();
}
// //获取枚举
// function enumerate(type){
// var retData = [];
// $.ajax({
// type: 'get',
// url: window.cturl+"/bd/enums/queryByVtype/"+type,
// contentType: "application/json ; charset=utf-8",
// success: function(data) {
// if(null != data){
// retData = data;
// }
// },
// error: function(XMLHttpRequest, textStatus, errorThrown) {
// errors.error(XMLHttpRequest);
// }
// });
// return retData;
// }

// 枚举值-名映射
// function comToName(field, enumObj) {
// var name = "";
// enumObj.map(function (item) {
// if (item.value == field) {
// name = item.name;
// }
// });
// return name;
// }
function comToName(field) {
	var value = field;
	var name = syc_fixcode_map[value];
	return name;
}

function enumerate(type) {
	var retData = [];
	$.ajax({
		type : 'get',
		url : window.cturl + "/bd/enums/queryByVtype/" + type,
		contentType : "application/json ; charset=utf-8",
		async : false,
		success : function(data) {
			retData = data;
			if (null != data) {
				for (var i = 0; i < data.length; i++) {
					window.syc_fixcode_map[data[i].value] = data[i].name;
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			errors.error(XMLHttpRequest);
		}
	});
	return retData;
}

function ready() {
	$('.list-group .parent').click(function() {
		var oThat = $(this).next(), falg = oThat.hasClass('slidshow');
		// oThat.parents('.list-group').find('.children').removeClass('slidshow').addClass('slidhid');
		if (falg) {
			$(this).removeClass('active');
			$(oThat).slideUp().addClass('slidhid').removeClass('slidshow');
		} else {
			$(this).addClass('active');
			$(oThat).slideDown().addClass('slidshow').removeClass('slidhid');
		}
	});
}