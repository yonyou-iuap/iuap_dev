define(['text!pages/mainPage/main.html', 'css!pages/mainPage/main','uui'], function (html) {
    var init = function (element) {
        $(element).html(html);
    }
    return {
        'template': html,
         init: init
    }
});

