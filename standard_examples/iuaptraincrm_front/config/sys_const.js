define( function() {
	
	//系统常量，被系统各个页面使用。
	window._CONST={
			//卡片(form)操作类型（增加-ADD,编辑-EDIT,查看-VIEW,列表-LIST,删除-DELETE）
			FORM_STATUS_ADD: 'ADD',
			FORM_STATUS_EDIT: 'EDIT',
			FORM_STATUS_VIEW: 'VIEW',
			FORM_STATUS_LIST: 'LIST',//返回列表界面
			FORM_STATUS_DEL: 'DELETE',//删除
	};	
	 
	//定义字典所需的meta对象
	window.dictMeta = {
		meta:{
			value:{type:'string'},
			name:{type:'string'}
		}
	};
	 
	//定义通过是否的字典属性
	window.YesOrNo = [{
        "value": "N",
        "name": "否"
      }, {
        "value": "Y",
        "name": "是"
      }];
	
	/**
	 * 去除前后空格
	 */
	removeSpace = function(newStr){
		newStr = newStr.replace(/(^\s*)|(\s*$)/g, "");
		return newStr;
	}
	
	/**
	 * 去除所有空格（前后及中间）
	 */
	removeAllSpace = function(newStr){
		newStr = newStr.replace(/\s+/g, "");
		return newStr;
	}
 
});
