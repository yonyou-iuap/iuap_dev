document.getElementById('getfilelist').onclick = function() {
	 $(".imagetable tr:not(:first)").remove();
	$.ajax({
		type : 'GET',
		dataType : 'json',
		async : false,
		url :'http://localhost/example_iuap_file/ossget/filelist?bucketname='+document.getElementById('bucketname').value,
		success : function(data){
			$.each( data,function(index,item){ 
		        console.log(item);
		        var row ="<tr><td>"+index+"</td><td>"+item +"</td></tr>";
		       $(row).insertAfter( $(".imagetable tr:last"));
		    });  
		} 
	});
};