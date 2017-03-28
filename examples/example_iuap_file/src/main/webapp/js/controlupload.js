function init(){

	//提交
	//获取oss签名参数
    var ret = get_signature()
	//装配发送到oss文件上传url   
    var formData = new FormData(); 
    var file=$("#file").prop('files');
   
    var filepath=$("#file").val();
    var arr=filepath.split('\\');
    var filename=arr[arr.length-1];//===================获得本地文件名（需要你提供）

    if (ret == true)
    {
    	//装配oss签名参数
    	formData.append("name",filename);
    	formData.append("key", key);
    	formData.append("policy", policyBase64);
    	formData.append("OSSAccessKeyId", accessid);
    	formData.append("success_action_status", '200');
    	formData.append("callback", callbackbody);
    	formData.append("signature", signature);
    	formData.append("file",document.getElementById('file').files[0])//===================获得文件数据（需要你提供）
    }
	//发送文件上传请求   
	$.ajax({  
        url: host,  
        type: 'POST',  
        data: formData,  
        async: false,  
        contentType: false, //必须
    	processData: false, //必须
        success: function (returndata) {  
            alert(returndata);  
        },  
        error: function (returndata) {  
            alert(returndata);  
        }  
    });  
}

//发送到应用服务器 获取oss签名参数
function send_request(){
	var obj
	$.ajax({
		type : 'GET',
		async : false,
		url :'http://localhost/example_iuap_file/osscontroller/getsign?bucketname='+document.getElementById('bucketname').value,//==========租户id（需要你提供）
		success : function(data){
		obj=$.parseJSON(data);
		} 
	});
	return obj;
}

//获取到签名参数后将参数填入变量待用
function get_signature()
{
    //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
    expire = 0
    now = timestamp = Date.parse(new Date()) / 1000; 
    console.log('get_signature ...');
    console.log('expire:' + expire.toString());
    console.log('now:', + now.toString())
    if (expire < now + 3)
    {
        console.log('get new sign')
        var obj = send_request(obj)
        policyBase64 = obj['policy']
        accessid = obj['accessid']
        signature = obj['signature']
        expire = parseInt(obj['expire'])
        callbackbody = obj['callback'] 
        host =obj['host']
        key = obj['perfix']+'${filename}'
        return true;
    }
    return false;
};

$("#submit").attr("onclick","init();");