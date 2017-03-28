'use strict';

define(['error', 'text!./dashboard.html', 'uuigrid','utilrow'], function (errors, html) {
	'use strict';

	var app, viewModel, basicDatas, events, oper;

	basicDatas = {
		cardData: new u.DataTable({
			meta: {
				pocounts: {type: 'int'},//po订单数
				vehiclecounts: {type: 'int'},//车辆数
				customercounts:{type: 'int'},//客户数
				procounts:{type: 'int'},//立项申请数
				proauditcounts:{type: 'int'},//待审核立项申请
				transcountsa:{type: 'int'},//待执行发运申请
				transcountsb:{type: 'int'},//待接车发运申请
				transcountsc:{type: 'int'},//待交付发运申请
				newtracks:{type: 'int'},//新增线索
				newcustomers:{type: 'int'},//新增客户
				newcharges:{type: 'int'}//新增充电站
				
			}
		}),
		alertListData: new u.DataTable({
			meta: {
				pk_alert:{type:'string'},
				vtype:{type:'string'},
				vorderno:{type:'string'},
				vcontent:{type:'string'},
				vlinkaddress:{type:'string'},
				vcreator:{type:'string'},
				pk_org:{type:'string'},
				pk_dept:{type:'string'},
				ts:{type:'string'},
				dr:{type:'int'}
			}
		})
	};

	events = {
		getDashBoard:function (){
			var param={};
			$.ajax({
				type: 'post',
				url: window.cturl + '/dashboard/querydb',
				dataType: 'json',
				contentType: "application/json ; charset=utf-8",
				data: JSON.stringify(param),
				success: function success(data) {
					viewModel.cardData.setSimpleData(data);
					viewModel.alertListData.setSimpleData(data.alertlist);
				},
				error: function error(XMLHttpRequest, textStatus, errorThrown) {
					errors.error(XMLHttpRequest);
				}
			});
		},
		showClick:function(id){
//			将id行设置为选中行
			viewModel.alertListData.setRowSelect(id);
//			获取选中行
			var row = viewModel.alertListData.getCurrentRow();
			
		}
	},
	viewModel = u.extend(basicDatas, events);

	var getInitData = function () {
		viewModel.getDashBoard();
	};

	return {
		init: function(content,tabId) {
			content.innerHTML = html;
			window.vm = viewModel;

			app = u.createApp({
				el: '#'+tabId,
				model: viewModel
			});
//			viewModel.getDashBoard();
			getInitData();
			
			var contentMinHeight = $('#content').css('min-height');
		    contentMinHeight = contentMinHeight.substr(0,contentMinHeight.length-2);
		    var tabsMinHeight = $('#content .nav-tabs').css('height');
		    tabsMinHeight = tabsMinHeight.substr(0,tabsMinHeight.length-2);
		    var tabContentHeight = (contentMinHeight-tabsMinHeight)+"px";
			$('#'+tabId+' .u-mdlayout .u-mdlayout-detail').css('min-height',tabContentHeight);
		}
	}
});