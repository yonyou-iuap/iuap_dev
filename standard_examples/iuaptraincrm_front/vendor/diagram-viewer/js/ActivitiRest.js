var ActivitiRest = {
	options: {},
	getProcessDefinitionByKey: function(processDefinitionKey, callback) {
		var url =""; //Lang.sub(this.options.processDefinitionByKeyUrl, {processDefinitionKey: processDefinitionKey});
		
		$.ajax({
			url: url,
			dataType: 'jsonp',
			cache: false,
			async: true,
			success: function(data, textStatus) {
				if(navigator.userAgent.indexOf("Firefox")>0)
	              {
	                  if(data instanceof Object && data.activities!=undefined)
	                     var data1=data;
	                  else
	                     var data1=eval('('+arguments[2].responseText+')');
	              }
	               else{
	               if(data instanceof Object && data.activities!=undefined)
	                     var data1=data;
	                  else
	                     var data1=eval('('+data+')');
	               }
	              var processDefinition = data1;
				if (!processDefinition) {
					console.error("Process definition '" + processDefinitionKey + "' not found");
				} else {
				  callback.apply({processDefinitionId: processDefinition.id});
				}
			}
		}).done(function(data, textStatus) {
			console.log("ajax done");
		}).fail(function(jqXHR, textStatus, error){
			return;
			//console.error('Get diagram layout['+processDefinitionKey+'] failure: ', textStatus, 'error: ', error, jqXHR);
		});
	},
	
	getProcessDefinition: function(processDefinitionId, processInstanceId, callback) {
		var path = (window.location+'').split('/'); 
      	var basePath = path[0]+'//'+path[2] ;
      	var uri=basePath+window.cturl+"/process/processInstancediagram";
      	 var obj = {};
      	 obj.processDefinitionId=processDefinitionId;
      	 obj.processInstanceId=processInstanceId;
      	var json = eval(obj);
		$.ajax({
			url: uri,
//			dataType: 'jsonp',
			 contentType:'text/json',
			cache: false,
			data: json,
			async: true,
			success: function(data, textStatus) {
				if(navigator.userAgent.indexOf("Firefox")>0)
	              {
	                  if(data instanceof Object && data.activities!=undefined)
	                     var data1=data;
	                  else
	                     var data1=eval('('+arguments[2].responseText+')');
	              }
	               else{
	               if(data instanceof Object && data.activities!=undefined)
	                     var data1=data;
	                  else
	                     var data1=eval('('+data+')');
	               }
	              var processDefinitionDiagramLayout = data1;
				if (!processDefinitionDiagramLayout) {
					console.error("Process definition diagram layout '" + processDefinitionId + "' not found");
					return;
				} else {
					callback.apply({processDefinitionDiagramLayout: processDefinitionDiagramLayout});
				}
			}
		}).done(function(data, textStatus) {
			console.log("ajax done");
		}).fail(function(jqXHR, textStatus, error){
			//console.log('Get diagram layout['+processDefinitionId+'] failure: ', textStatus, jqXHR);
			return;
		});
	},
	
	getHighLights: function(processInstanceId, callback) {
		var path = (window.location+'').split('/'); 
      	var basePath = path[0]+'//'+path[2];
      	var uri=basePath+window.cturl+"/process/highlightsprocessInstance";
      	 var obj = {};
      	 obj.processInstanceId=processInstanceId;
      	var json = eval(obj);
		$.ajax({
			url: uri,
//			dataType: 'jsonp',
			cache: false,
			 contentType:'text/json',
			data: json,
			async: true,
			success: function(data, textStatus) {
				console.log("ajax returned data");
				if(navigator.userAgent.indexOf("Firefox")>0)
	              {
	                  if(data instanceof Object && data.activities!=undefined)
	                     var data1=data;
	                  else
	                     var data1=eval('('+arguments[2].responseText+')');
	              }
	               else{
	               if(data instanceof Object && data.activities!=undefined)
	                     var data1=data;
	                  else
	                     var data1=eval('('+data+')');
	               }
	              var highLights = data1;
				if (!highLights) {
					console.log("highLights not found");
					return;
				} else {
					callback.apply({highLights: highLights});
				}
			}
		}).done(function(data, textStatus) {
			console.log("ajax done");
		}).fail(function(jqXHR, textStatus, error){
		  //console.log('Get HighLights['+processInstanceId+'] failure: ', textStatus, jqXHR);
			return;
		});
	}
};