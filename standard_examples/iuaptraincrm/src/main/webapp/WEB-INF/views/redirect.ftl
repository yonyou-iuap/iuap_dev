<script>
	window.setTimeout(function(){
	    var flag = "${flag}";
	    if(flag=="success"){
	    	window.location.href="/";
	    }else{
	    	window.location.href="/login.html";
	    }
	},500);
</script>