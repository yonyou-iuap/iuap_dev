
/*
 * rowb 增加修改行
 * delrow 删除行
 * btrow  定义的数组
 * */
function addbtrow(rowb, delrow,btrow){
	var 
	_CONST = {
			STATUS_UNCHANGED:0,
			STATUS_UPDATED:1,
			STATUS_NEW:2,
			STATUS_DELETED:3
	}
	for(var i=0;i<rowb.length;i++){
		if(rowb[i].status=='upd'){
			rowb[i].setValue("status", _CONST.STATUS_UPDATED);
		}else if(rowb[i].status=='new'){
			rowb[i].setValue("status", _CONST.STATUS_NEW);
		}
		btrow.push(rowb[i].getSimpleData());
	}
	if(delrow!=undefined){//删除汇总
		for(var i=0;i<delrow.length;i++){
			delrow[i].setValue("status", _CONST.STATUS_DELETED);
			btrow.push(delrow[i].getSimpleData());
		}
	}
	return btrow;
}

function CalculatePageCount(_pagesize, _recordcount) {//计算总页数
	        var NumberRegex = new RegExp(/^\d+$/);
	        var PageSize,RecordCount,PageCount;
	        if (_pagesize != null && NumberRegex.test(_pagesize)) PageSize = parseInt(_pagesize);
	        if (_recordcount != null && NumberRegex.test(_recordcount)) RecordCount = parseInt(_recordcount);
	        else RecordCount = 0;
	        if (RecordCount % PageSize == 0) {//计算总也页数
	            PageCount = parseInt(RecordCount / PageSize);
	        }
	        else {
	            PageCount = parseInt(RecordCount / PageSize) + 1;
	        }
	return PageCount;
	    }
//获取当前时间
function getnowdata(obj){//obj=1,返回当前日期  obj=2,返回当前时间
	var myDate = new Date();
	var year=myDate.getFullYear();
	var month=myDate.getMonth()+1; 
	var day=myDate.getDate(); 
	var hour=myDate.getHours();
	var minute=myDate.getMinutes();
	var second=myDate.getSeconds();
	var nowdate = year+"-"+month+"-"+day;
	var nowdatetime=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second; 
	if(obj==1){
		return nowdate;
	}else{
		return nowdatetime;
	}
}
//表头保存检验数据
function getvalidate(obj1,obj){//obj为 校验数据div的主键
	var result = obj1.compsValidateMultiParam({element:document.querySelector(obj), showMsg: true});
	if (result.passed == false){
		var msgv="";	
		for(var i=0;i<result.notPassedArr.length;i++){
//			var field = result.notPassedArr[i].comp.element.parentElement.previousElementSibling.childNodes[0];
			var msg1 = result.notPassedArr[i].Msg;
			var type = result.notPassedArr[i].comp.type;
			if(type=='grid'){
				var name = result.notPassedArr[i].comp.gridOptions.name;
				msgv += "表格【"+name+"】:"+msg1+"<br>";
			}else{
				var name = result.notPassedArr[i].comp.options.name;
				msgv += "字段【"+name+"】:"+msg1+"<br>";
			}
		}	
		u.messageDialog({msg:msgv,title:"操作提示",btnText:"确定"});
		return false;
	}else{
		return true;
	}
	
}

/**
 * 角色关联功能，选中事件
 * 
 * @param obj
 */
function selected(obj){
	if(!window.selectArray){
		if(window.flag && window.flag == 1){//防止最后一次调用后循环调用
			window.flag = 2;
			return;
		}
		window.unselectArray = null;
		window.unflag = 2;
		
		window.selectArray = new Array();
		var tempD = new Array();
		var o = obj['rowObj'];
		var rows = obj.gridObj.dataSourceObj.rows;
		while(true){//找父亲，把自己push到队列
			if(o['level'] == 0) break;
			var parentKeyValue = o['parentKeyValue'];
			for(var i in rows){
				if(rows[i].keyValue == o['parentKeyValue']){
					o = rows[i];
					window.selectArray.unshift(i);
					break;
				}
			}
		}
		
		o = obj;
		var children = o['rowObj']['childRowIndex'];
		if(children.length > 0){
			while(true){//遍历被选中的所有孩子节点
		    	if(children){
		    		for(var i = 0; i < children.length; i++){
		    			window.selectArray.unshift(children[i]);
		    			tempD.unshift(children[i]);
		    		}
		    		o = obj.gridObj.dataSourceObj.rows[tempD.pop()];
		    		children = o['childRowIndex'];
	    		}
		    	if(!tempD || tempD.length == 0)break;
    		}	
		}
	}	
var length = window.selectArray.length;
if(window.selectArray && length > 0){
	//var index = window.selectArray.pop();
	var index;
	for(var i = 0; i < length; i++ ){
		index = window.selectArray.pop();//若该行被选中，则直接出队列
		o = obj.gridObj.dataSourceObj.rows[index];
		if(!o['checked']){
			break;
		}
	}
	if(window.selectArray.length == 0){
		window.selectArray = null;
		window.flag = 1;//
	}
	if(index)//选中index
		obj['gridObj'].setRowSelect(index);
}
else{
	window.selectArray = null;
	window.flag = 2;
}
}
function unselected(obj){
	debugger;
if(!window.unselectArray){
	if(window.unflag && window.unflag == 1){//防止最后一次调用后循环调用
		window.unflag = 2;
		return;
	}
	window.selectArray = null;
	window.flag = 2;
	
	window.unselectArray = new Array();
	var tempD = new Array();
	var o = obj['rowObj'];
	var rows = obj.gridObj.dataSourceObj.rows;
	while(true){//找父亲，把自己push到队列
		if(o['level'] == 0) break;
		var parentKeyValue = o['parentKeyValue'];
		for(var i in rows){
			if(rows[i].keyValue == o['parentKeyValue']){
				var count = 0;
				o = rows[i];
				var children = o['childRowIndex'];
				for(var j = 0; j < children.length; j++){//判断每一个父亲有几个孩子被选中
	    			if(obj.gridObj.dataSourceObj.rows[children[j]].checked)
	    				count++;
	    		}
					if((count > 0 && window.unselectArray.length == 0) || (count > 1 && window.unselectArray.length > 0)){//count大于1表示该节点被选中的孩子不止一个
					break;
				}
				window.unselectArray.unshift(i);
			}
		}
			if((count > 0 && window.unselectArray.length == 0) || (count > 1 && window.unselectArray.length > 0)){//count大于1表示该节点被选中的孩子不止一个
			break;
		}
	}
	
	o = obj;
	var children = o['rowObj']['childRowIndex'];
	if(children.length > 0){
		while(true){//遍历被选中的所有孩子节点
	    	if(children){
	    		for(var i = 0; i < children.length; i++){
	    			window.unselectArray.unshift(children[i]);
	    			tempD.unshift(children[i]);
	    		}
	    		o = obj.gridObj.dataSourceObj.rows[tempD.pop()];
	    		children = o['childRowIndex'];
    		}
	    	if(!tempD || tempD.length == 0)break;
		}	
	}
}	
var length = window.unselectArray.length;
if(window.unselectArray && length > 0){
	//var index = window.unselectArray.pop();
	var index;
	for(var i = 0; i < length; i++ ){
		index = window.unselectArray.pop();//若该行被选中，则直接出队列
		o = obj.gridObj.dataSourceObj.rows[index];
		if(o['checked']){
			break;
		}
	}
	if(window.unselectArray.length == 0){
		window.unselectArray = null;
		window.unflag = 1;//
	}
	if(index)//选中index
		obj['gridObj'].setRowUnselect(index);
}
else{
	window.unselectArray = null;
	window.unflag = 2;
}
}
