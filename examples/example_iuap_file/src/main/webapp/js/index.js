
$("#upload").attr("onclick","upload();");

$("#geturl").attr("onclick","geturl();");

$("#controlupload").attr("onclick","controlupload();");

$("#filelist").attr("onclick","filelist();");

$("#simpleupload").attr("onclick","simpleupload();");

$("#nopostfix").attr("onclick","nopostfix();");


function upload(){
	$("div.content").load("pages/upload/upload.html");
}

function geturl(){
	$("div.content").load("pages/geturl/getUrl.html");
}

function controlupload(){
	$("div.content").load("pages/controlupload/controlupload.html");
}

function filelist(){
	$("div.content").load("pages/filelist/getFileList.html");
}

function simpleupload(){
	$("div.content").load("pages/simpleupload/simple.html");
}

function nopostfix(){
	$("div.content").load("pages/nopostfix/nopostfix.html");
}