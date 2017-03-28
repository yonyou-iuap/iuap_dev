require.config({
	baseUrl: "/",
	paths: {
		text: "../vendor/requirejs/text",
		css: "../vendor/requirejs/css",
		jquery: "../vendor/jquery/jquery-1.11.2.min",
		ajaxfileupload: "../vendor/jquery/ajaxfileupload",
		bootstrap: '../vendor/bootstrap-3.3.5/js/bootstrap',
		uui: "../vendor/uui/js/u.min",
		director:"../vendor/director/director",
		taffydb:"../vendor/taffydb/taffy",
		webuploader:"../vendor/uploader/webuploader",
		'uuitree':'../vendor/uui/js/u-tree.min',
		'uuigrid':'../vendor/uui/js/u-grid',
		'print':'../vendor/print/PrintModel',
		'orderdetail':'../vendor/print/orderdetail',
		'underscore':'../vendor/print/underscore',
		'error':'../pages/tools/error',
		'utilrow':'../pages/tools/util',
		'refComp': "/uitemplate_web/static/js/uiref/refComp",
		'ossupload': "/iuap-saas-filesystem-service/resources/js/ossupload",
		'interfaceFile': "/iuap-saas-filesystem-service/resources/js/interface.file",
		'interfaceFileImpl': "/iuap-saas-filesystem-service/resources/js/interface.file.impl",
		'iReferComp':"../pages/tools/iReferComp",
        'cookie':"../vendor/jQuery-cookie/jquery.cookie",
        'bdtpl':"../vendor/baiduTemp/baiduTemplate",
        dtree:"../vendor/dtree/dtree"
	},
	shim: {
		'bootstrap': {
			deps: ["jquery"]
		},
		'uuigrid':{
			deps: ["jquery","css!../vendor/uui/css/grid.css"]
		},
		'uuitree':{
			deps: ["css!../vendor/uui/css/tree.css"]
		},
		'jquery.file.upload':{
			deps: ["jquery","jquery.ui.widget","jquery.iframe.transport","css!../vendor/trd/juqery-file-upload/9.9.2/css/jquery.fileupload.css"]
		},
		uploader:{
			deps: ["jquery", "css!../vendor/trd/uploader/css/webuploader.css"]
		},
        swiper:{
            deps: ["jquery", "css!../vendor/trd/swiper/css/idangerous.swiper.css"]
        },
        refComp:{
        	deps: ["css!/uitemplate_web/static/css/ref/ref.css","css!/uitemplate_web/static/css/ref/jquery.scrollbar.css","css!/uitemplate_web/static/trd/bootstrap-table/src/bootstrap-table.css"]
        },
        print:{
        	deps: ["css!../vendor/print/print.css"]
        },
        ajaxfileupload:{
        	deps: ["jquery"]
        },
        dtree:{
			deps: ["css!../vendor/dtree/dtree.css"]
		},
		'bdtpl':{
			exports:'baidu'
		},
		interfaceFileImpl:{
			deps:["interfaceFile"]
		}
	}
});