function send_request()
{
    var xmlhttp = null;
    if (window.XMLHttpRequest)
    {
        xmlhttp=new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
  
    if (xmlhttp!=null)
    {
        Url = 'http://localhost/example_iuap_file/ossget/url?bucketname='+document.getElementById('bucketname').value+ '&filename='+document.getElementById('filename').value
        xmlhttp.open( "GET", Url, false );
        xmlhttp.send( null );
        return xmlhttp.responseText
    }
    else
    {
        alert("Your browser does not support XMLHTTP.");
    }
};

document.getElementById('getfileurl').onclick = function() {
	body=send_request();
	document.getElementById('ossfile').innerHTML += '<div>'+ body +'</div>';
};