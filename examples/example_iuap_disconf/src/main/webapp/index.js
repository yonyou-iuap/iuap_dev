require(['knockout', 'director','polyfill','html5shiv'], function(ko) {
	$.ajaxSetup({
		cache:false
	});
	
    window.addRouter = function(path, func) {
        var pos = path.indexOf('/:');
        var truePath = path;
        if (pos != -1)
            truePath = path.substring(0, pos);
        func = func || function() {
            var params = arguments;
            initPage('pages/' + truePath, params);
        }
        var tmparray = truePath.split("/");
        if (tmparray[1] in router.routes && tmparray[2] in router.routes[tmparray[1]] && tmparray[3] in router.routes[tmparray[1]][tmparray[2]]) {
            return;
        } else {
            router.on(path, func);
        }
    }

    window.router = Router();
    window.ko=ko;
    ctx="/iuap-disconf-example";

    $(function() {
        $('#menu').find("a[href*='#']").each(function() {
            var path = this.hash.replace('#', '');
            addRouter(path);
        });
		window.router.init();
        if (window.location.href.indexOf("#") < 0) {
            window.router.setRoute($('#menu').find("a[href*='#']")[0].hash.replace('#', ''));
        } 
	    $('.foldingpad').click(function() {
		    if($(this).hasClass('rotate')){
		      _unfold();//展开
		    }else{
		      _shrink();
		    }
		 });
		
		// left nav shrink 收缩
		  function _shrink(){
		    $('.nav-li').addClass('live-hover');
		    $('.foldingpad').addClass('rotate');
		    // $('.nav-item-list').css('left','-180px');
		    $('.page-container').css('margin-left','55px');
		    $('.foldingpad').css('left','65px')
		    $('.page-sidebar').css('margin-left','-200px');
		    $('.page-small-sidebar').css('margin-left','0px');
		    $('.global-notice').css('left','90px')
		    // setCookie('menu','2');
		  }
		  // left nav unfold 展开
		  function _unfold(){
		    $('.nav-li').removeClass('live-hover');
		    $('.foldingpad').removeClass('rotate');
		    // $('.nav-item-list').css('left','75px');
		    $('.page-container').css('margin-left','200px');
		    $('.page-sidebar').css('margin-left','0px');
		    $('.page-small-sidebar').css('margin-left','-55px');
		    $('.global-notice').css('left','235px')
		    $('.foldingpad').css('left','210px')
		  }
		
    })

    function initPage(p, id) {
        var module = p;
        var content = document.getElementById("content");
        require([module], function(module) {
            ko.cleanNode(content);
            content.innerHTML = "";
            module.init(content);
        })
    }
})