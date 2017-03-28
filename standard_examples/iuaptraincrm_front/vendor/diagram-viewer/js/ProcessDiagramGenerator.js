 /**
 * Class to generate an image based the diagram interchange information in a
 * BPMN 2.0 process.
 *
 * @author (Javascript) Dmitry Farafonov
 */
 
var ProcessDiagramGenerator = {	
	options: {},
	
	processDiagramCanvas: [],
	
	activityDrawInstructions:{},
	
	processDiagrams: {},
	
	diagramBreadCrumbs: null,
	
	init: function(){
		// start event
		this.activityDrawInstructions["startEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawNoneStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// start timer event
		this.activityDrawInstructions["startTimerEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawTimerStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, activityImpl.getProperty("name"));
		};
		
		// start event
		this.activityDrawInstructions["messageStartEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawMessageStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, activityImpl.getProperty("name"));
		};
		
		// start signal event
		this.activityDrawInstructions["startSignalEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawSignalStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, activityImpl.getProperty("name"));
		};
		
		// start multiple event
		this.activityDrawInstructions["startMultipleEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawMultipleStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, activityImpl.getProperty("name"));
		};
		
		// signal catch
		this.activityDrawInstructions["intermediateSignalCatch"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawCatchingSignalEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, null);
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
			//if (label)
			//  processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// message catch
		this.activityDrawInstructions["intermediateMessageCatch"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawCatchingMessageEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, null);
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
       // processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// multiple catch
		this.activityDrawInstructions["intermediateMultipleCatch"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawCatchingMultipleEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, null);
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
       // processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		
		
		// signal throw
		this.activityDrawInstructions["intermediateSignalThrow"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawThrowingSignalEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), activityImpl.getProperty("name"));
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
        //processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// message throw
		this.activityDrawInstructions["intermediateMessageThrow"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawThrowingMessageEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), activityImpl.getProperty("name"));
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
       // processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		// conpensate throw
		this.activityDrawInstructions["intermediateCompensateThrow"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawThrowingCompensateEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), activityImpl.getProperty("name"));
			
			var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      if (label)
        processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// multiple throw
		this.activityDrawInstructions["intermediateMultipleThrow"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawThrowingMultipleEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), activityImpl.getProperty("name"));
			
			var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      if (label)
        processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// none throw
		this.activityDrawInstructions["intermediateThrowEvent"] = function() {
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawThrowingNoneEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), activityImpl.getProperty("name"));
			
			var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      if (label)
        processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// end event
		this.activityDrawInstructions["endEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawNoneEndEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// error end event
		this.activityDrawInstructions["errorEndEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawErrorEndEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), null);
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
        //processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// message end event
		this.activityDrawInstructions["messageEndEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawMessageEndEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), null);
			
			var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      if (label)
        processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// signal end event
		this.activityDrawInstructions["signalEndEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawSignalEndEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), null);
			
			var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      if (label)
        processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// multiple end event
		this.activityDrawInstructions["multipleEndEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawMultipleEndEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), null);
			
			var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      if (label)
        processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// terminate end event
		this.activityDrawInstructions["terminateEndEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawTerminateEndEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
			
			var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      if (label)
        processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// error start event
		this.activityDrawInstructions["errorStartEvent"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawErrorStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), activityImpl.getProperty("name"));
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
       // processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// task
		this.activityDrawInstructions["task"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			// TODO: 
			//console.error("task is not implemented yet");
			/*
			var activityImpl = this;
			processDiagramCanvas.drawTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), thickBorder);
			*/
		};
		
		
		// user task
		this.activityDrawInstructions["userTask"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawUserTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// approveuser task
		this.activityDrawInstructions["approveUserTask"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawApproveUserTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// script task
		this.activityDrawInstructions["scriptTask"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawScriptTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// service task
		this.activityDrawInstructions["serviceTask"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawServiceTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};

		// receive task
		this.activityDrawInstructions["receiveTask"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawReceiveTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// send task
		this.activityDrawInstructions["sendTask"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawSendTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};

		// manual task
		this.activityDrawInstructions["manualTask"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
		
			processDiagramCanvas.drawManualTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};

		// businessRuleTask task
		this.activityDrawInstructions["businessRuleTask"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawBusinessRuleTask(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};

		// exclusive gateway
		this.activityDrawInstructions["exclusiveGateway"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawExclusiveGateway(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// inclusive gateway
		this.activityDrawInstructions["inclusiveGateway"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawInclusiveGateway(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// parallel gateway
		this.activityDrawInstructions["parallelGateway"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawParallelGateway(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// eventBasedGateway
		this.activityDrawInstructions["eventBasedGateway"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			processDiagramCanvas.drawEventBasedGateway(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		// Boundary timer
		this.activityDrawInstructions["boundaryTimer"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawCatchingTimerEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, null);
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
      //  processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// Boundary catch error
		this.activityDrawInstructions["boundaryError"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawCatchingErrorEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, null);
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
       // processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};
		
		// Boundary signal event
		this.activityDrawInstructions["boundarySignal"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = activityImpl.getProperty("isInterrupting");
			processDiagramCanvas.drawCatchingSignalEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, null);
			
			//var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
      //if (label)
        //processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
		};

    // Boundary message event
    this.activityDrawInstructions["boundaryMessage"] = function(){
        var activityImpl = this.activity;
        var processDiagramCanvas = this.processDiagramCanvas;
        processDiagramCanvas.setConextObject(activityImpl);

        var isInterrupting = activityImpl.getProperty("isInterrupting");
        processDiagramCanvas.drawCatchingMessageEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, null);

        //var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
        //if (label)
         //   processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
    };
 // Boundary compensation event
    this.activityDrawInstructions["compensationBoundaryCatch"] = function(){
        var activityImpl = this.activity;
        var processDiagramCanvas = this.processDiagramCanvas;
        processDiagramCanvas.setConextObject(activityImpl);

        var isInterrupting = activityImpl.getProperty("isInterrupting");
        processDiagramCanvas.drawCatchingCompensateEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, null);

        //var label = ProcessDiagramGenerator.getActivitiLabel(activityImpl);
        //if (label)
        //    processDiagramCanvas.drawLabel(label.text, label.x, label.y, label.width, label.height);
    };
		
		// timer catch event
		this.activityDrawInstructions["intermediateTimer"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isInterrupting = null;
			processDiagramCanvas.drawCatchingTimerEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(), isInterrupting, activityImpl.getProperty("name"));
		};
		
		// subprocess
		this.activityDrawInstructions["subProcess"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			// TODO: 
			
			processDiagramCanvas.setConextObject(activityImpl);
			
			var isExpanded = activityImpl.getProperty("isExpanded");
			var isTriggeredByEvent = activityImpl.getProperty("triggeredByEvent");
			if(isTriggeredByEvent == undefined) {
			  isTriggeredByEvent = true;
			}
			// TODO: check why isTriggeredByEvent = true when undefined
			isTriggeredByEvent = false;
			
			if (isExpanded != undefined && isExpanded == false) {
			  processDiagramCanvas.drawCollapsedSubProcess(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(),
					  activityImpl.getWidth(), activityImpl.getHeight(), isTriggeredByEvent);
			} else {
			  processDiagramCanvas.drawExpandedSubProcess(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(),
					  activityImpl.getWidth(), activityImpl.getHeight(), isTriggeredByEvent);
			}
			
			//console.error("subProcess is not implemented yet");
		};
		
		// call activity
		this.activityDrawInstructions["callActivity"] = function(){
			var activityImpl = this.activity;
			var processDiagramCanvas = this.processDiagramCanvas;
			processDiagramCanvas.setConextObject(activityImpl);
			processDiagramCanvas.drawCollapsedCallActivity(activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
		};
		
		$(document).ready(function(){
		  // Protect right click on SVG elements (and on canvas too)
		  document.body.oncontextmenu = function(event) {
		    if (window.event.srcElement.tagName == "shape" || window.event.srcElement.tagName == "DIV" && window.event.srcElement.parentElement.className == "diagram") {

		      // IE DIAGRAM CANVAS OR SHAPE DETECTED!
		      return false;
		    }
		    return (!Object.isSVGElement(window.event.srcElement));
		  };
		});
	},
	
	 getActivitiLabel:function(activityImpl){
	   /*
	     TODO: Label object should be in activityImpl and looks like:
	     {
	       x: 250,
	       y: 250,
	       width: 80,
	       height: 30
	     }
	     And then:
	     if (!activityImpl.label)
	       return null;
	     var label = activityImpl.label;
	     label.text = activityImpl.name;
	     return label;
	   */

	   // But now default label for all events is:
	   return {
	     text: activityImpl.getProperty("name"),
	     x: activityImpl.getX() + .5 + activityImpl.getWidth()/2,
	     y: activityImpl.getY() + .5 + activityImpl.getHeight() + ICON_PADDING,
	     width: 100,
	     height: 0
	   };
	},
		
	generateDiagram: function(processDefinitionDiagramLayout){
		// Init canvas
		var processDefinitionId = processDefinitionDiagramLayout.processDefinition.id;
		//console.log("Init canvas ", processDefinitionId);
		
		if (this.getProcessDiagram(processDefinitionId) != undefined) {
			// TODO: may be reset canvas if exists.. Or just show
			//console.log("ProcessDiagram '" + processDefinitionId + "' is already generated. Just show it.");
			return;
		}
		var processDiagram = this.initProcessDiagramCanvas(processDefinitionDiagramLayout);
		var processDiagramCanvas = processDiagram.diagramCanvas;
		
		// Draw pool shape, if process is participant in collaboration
		
		if(processDefinitionDiagramLayout.participantProcess != undefined) {
		  //console.log("Draw pool shape");
		  var pProc = processDefinitionDiagramLayout.participantProcess;
		  processDiagramCanvas.drawPoolOrLane(pProc.x, pProc.y, pProc.width, pProc.height, pProc.name);
		}
		
		var laneSets = processDefinitionDiagramLayout.laneSets;
		var activities = processDefinitionDiagramLayout.activities;
		var sequenceFlows = processDefinitionDiagramLayout.sequenceFlows;
		
		
		pb1.set('value', 0);
		var cnt = 0;
		if (laneSets) 
			for(var i in laneSets) {
				cnt += laneSets[i].lanes.length;
			}
		if (activities) 
			cnt += activities.length;
		if (sequenceFlows) 
			cnt += sequenceFlows.length;
		var step = (cnt>0)? 100/cnt : 0;
		var progress = 0;
		//console.log("progress bar step: ", step);
		
		var task1 = new $.AsyncQueue();
		
			// Draw lanes
			
			task1.add(function (task1) {
				if (!laneSets) laneSets = [];
				//console.log("> draw lane sets, count:", laneSets.length)
			});
			
			for(var i in laneSets) {
				var laneSet = laneSets[i];
				//laneSet.id, laneSet.name
				
				task1.add(laneSet.lanes,function (task1, lane) {
					progress += step;
					pb1.set('value', parseInt(progress));
					
					//console.log("--> laneId: " + lane.name + ", name: " + lane.name);
					
					processDiagramCanvas.drawPoolOrLane(lane.x, lane.y, lane.width, lane.height, lane.name);
				});
			}
			
			// Draw activities
			
			task1.add(function (task1) {
				if (!activities) activities = [];
				//console.log("> draw activities, count:", activities.length)
			});
			
			var activitiesLength = activities.length;
			task1.add(activities,function (task1, activityJson) {
				var activity = new ActivityImpl(activityJson);
				activitiesLength --;
				progress += step;
				pb1.set('value', parseInt(progress));
				//console.log(activitiesLength, "--> activityId: " + activity.getId() + ", name: " + activity.getProperty("name"));
				ProcessDiagramGenerator.drawActivity(processDiagramCanvas, activity);
			});
			
			// Draw sequence-flows
			
			task1.add(function (task1) {
				if (!sequenceFlows) sequenceFlows = [];
				//console.log("> draw sequence flows, count:", sequenceFlows.length)
			});
			
			var flowsLength = sequenceFlows.length;
			task1.add(sequenceFlows,function (task1, flow) {
				var waypoints = [];
				for(var j in flow.xPointArray) {
					waypoints[j] = {x: flow.xPointArray[j], y: flow.yPointArray[j]};
				}
				var isDefault = flow.isDefault;
				var isConditional = flow.isConditional;
				var isHighLighted = flow.isHighLighted;
				
				// TODO: add source and destination for sequence flows in REST
				// parse for test
					var f = flow.flow;
					var matches = f.match(/\((.*)\)--.*-->\((.*)\)/);
					var sourceActivityId, destinationActivityId;
					if (matches != null) {
						sourceActivityId = matches[1];
						destinationActivityId = matches[2];
					}
					flow.sourceActivityId = sourceActivityId;
					flow.destinationActivityId = destinationActivityId;
				//
				flowsLength--;
				progress += step;
				pb1.set('value', parseInt(progress));
				
				//console.log(flowsLength, "--> flow: " + flow.flow);
				
				processDiagramCanvas.setConextObject(flow);
				if(flow.type)
					processDiagramCanvas.drawAssociation(waypoints, true);
				else
					processDiagramCanvas.drawSequenceflow(waypoints, isConditional, isDefault, isHighLighted);
			});
			
			task1.onComplete(function(){
				if (progress<100)
					pb1.set('value', 100);
				//console.log("COMPLETE!!!");
					
				//console.timeEnd('generateDiagram');
			});
			
			task1.run();
	},
	
	getProcessDiagram: function (processDefinitionId) {
		return this.processDiagrams[processDefinitionId];
	},
	
	initProcessDiagramCanvas: function (processDefinitionDiagramLayout) {
		var minX = 0;
		var maxX = 0;
		var minY = 0;
		var maxY = 0;
		
		if(processDefinitionDiagramLayout.participantProcess != undefined) {
		  var pProc = processDefinitionDiagramLayout.participantProcess;
		  
		  minX = pProc.x;
		  maxX = pProc.x + pProc.width;
		  minY = pProc.y;
		  maxY = pProc.y + pProc.height;
		}

		var activities = processDefinitionDiagramLayout.activities;
		for(var i in activities) {
			var activityJson = activities[i];
			var activity = new ActivityImpl(activityJson);
			
			// width
			if (activity.getX() + activity.getWidth() > maxX) {
				maxX = activity.getX() + activity.getWidth();
			}
			if (activity.getX() < minX) {
				minX = activity.getX();
			}
			// height
			if (activity.getY() + activity.getHeight() > maxY) {
				maxY = activity.getY() + activity.getHeight();
			}
			if (activity.getY() < minY) {
				minY = activity.getY();
			}
		}
		
		var sequenceFlows = processDefinitionDiagramLayout.sequenceFlows;
		for(var i in sequenceFlows) {
			var flow = sequenceFlows[i];
			var waypoints = [];
			for(var j in flow.xPointArray) {
				waypoints[j] = {x: flow.xPointArray[j], y: flow.yPointArray[j]};
				
				// width
				if (waypoints[j].x > maxX) {
					maxX = waypoints[j].x;
				}
				if (waypoints[j].x < minX) {
					minX = waypoints[j].x;
				}
				// height
				if (waypoints[j].y > maxY) {
					maxY = waypoints[j].y;
				}
				if (waypoints[j].y < minY) {
					minY = waypoints[j].y;
				}
			}
		}
		
		var laneSets = processDefinitionDiagramLayout.laneSets;
		for(var i in laneSets) {
			var laneSet = laneSets[i];
			//laneSet.id, laneSet.name
			
			for(var j in laneSet.lanes) {
				var lane = laneSet.lanes[j];
				// width
				if (lane.x + lane.width > maxX) {
				  maxX = lane.x + lane.width;
				}
				if (lane.x < minX) {
				  minX = lane.x;
				}
				// height
				if (lane.y + lane.height > maxY) {
				  maxY = lane.y + lane.height;
				}
				if (lane.y < minY) {
				  minY = lane.y;
				}
			}
		}
	
		var diagramCanvas = new ProcessDiagramCanvas();
		if (diagramCanvas) {
			
			// create div in diagramHolder
			var diagramHolder = document.getElementById(this.options.diagramHolderId);
			if (!diagramHolder)
				throw {msg: "Diagram holder not found", error: "diagramHolderNotFound"};
			var div = document.createElement("DIV");
			div.id = processDefinitionDiagramLayout.processDefinition.id;
			div.className = "diagram";
			diagramHolder.appendChild(div);
			
			diagramCanvas.init(maxX + 20, maxY + 20, processDefinitionDiagramLayout.processDefinition.id);
			this.processDiagrams[processDefinitionDiagramLayout.processDefinition.id] = {
				processDefinitionDiagramLayout: processDefinitionDiagramLayout,
				diagramCanvas: diagramCanvas
			};
		}
		return this.getProcessDiagram(processDefinitionDiagramLayout.processDefinition.id);
		//return new DefaultProcessDiagramCanvas(maxX + 10, maxY + 10, minX, minY);
	},
	
	drawActivity: function(processDiagramCanvas, activity, highLightedActivities) {
		var type = activity.getProperty("type");
		var drawInstruction = this.activityDrawInstructions[type];
		if (drawInstruction != null) {	
			drawInstruction.apply({processDiagramCanvas:processDiagramCanvas, activity:activity});
		} else {
			//console.error("no drawInstruction for " + type + ": ", activity);
		}
		
		// Actually draw the markers
		if (activity.getProperty("multiInstance") != undefined || activity.getProperty("collapsed") != undefined) {
			//console.log(activity.getProperty("name"), activity.properties);
			var multiInstanceSequential = (activity.getProperty("multiInstance") == "sequential");
			var multiInstanceParallel = (activity.getProperty("multiInstance") == "parallel");
			var collapsed = activity.getProperty("collapsed");
				processDiagramCanvas.drawActivityMarkers(activity.getX(), activity.getY(), activity.getWidth(), activity.getHeight(), 
					multiInstanceSequential, multiInstanceParallel, collapsed);
		}
		if (activity.getProperty("isForCompensation") != undefined) {
				processDiagramCanvas.drawForCompensationMarker(activity.getX(), activity.getY(), activity.getWidth(), activity.getHeight());
		}
		/*
		processDiagramCanvas.drawActivityMarkers(activity.getX(), activity.getY(), activity.getWidth(), activity.getHeight(), multiInstanceSequential,
              multiInstanceParallel, collapsed);
		*/

		// TODO: Draw highlighted activities if they are present
		
	},
	
	setHighLights: function(highLights){
		if (highLights.processDefinitionId == undefined) {
			//console.error("Process instance " + highLights.processInstanceId + " doesn't exist");
			return;
		}
		
		var processDiagram = this.getProcessDiagram(highLights.processDefinitionId);
		if (processDiagram == undefined) {
			//console.error("Process diagram " + highLights.processDefinitionId + " not found");
			return;
		}
		
		var processDiagramCanvas = processDiagram.diagramCanvas;
		
		// TODO: remove highLightes from all activities before set new highLight
		for (var i in highLights.activities) {
			var activityId = highLights.activities[i];
			processDiagramCanvas.highLightActivity(activityId);
		}
		
		for (var i in highLights.currentactivities) {
			var activityId = highLights.currentactivities[i];
			processDiagramCanvas.highLightCurrentActivity(activityId);
		}
		
		// TODO: remove highLightes from all flows before set new highLight
		for (var i in highLights.flows) {
			var flowId = highLights.flows[i];
			var object = processDiagramCanvas.g.getById(flowId);
			var flow = object.data("contextObject");
			flow.isHighLighted = true;
			processDiagramCanvas.highLightFlow(flowId);
		}
	},
	
	drawHighLights: function(processInstanceId) {
		// Load highLights for the processInstanceId
		/*
		var url = Lang.sub(this.options.processInstanceHighLightsUrl, {processInstanceId: processInstanceId});
		$.ajax({
			url: url,
			type: 'GET',
			dataType: 'json',
			cache: false,
			async: true,
		}).done(function(data) {
			var highLights = data;
			if (!highLights) {
				console.log("highLights not found");
				return;
			}
			
			console.log("highLights[" + highLights.processDefinitionId + "][" + processInstanceId + "]: ", highLights);
			
			ProcessDiagramGenerator.setHighLights(highLights);
		}).fail(function(jqXHR, textStatus){
			console.log('Get HighLights['+processDefinitionId+'] failure: ', textStatus, jqXHR);
		});
		*/
		ActivitiRest.getHighLights(processInstanceId, this._drawHighLights);
	},
	_drawHighLights: function() {
		var highLights = this.highLights;
		ProcessDiagramGenerator.setHighLights(highLights);
	},
	
	// Load processDefinition
	
	drawDiagram: function(processDefinitionId,processInstanceId) {
		// Hide all diagrams
		var diagrams = $("#" + this.options.diagramHolderId + " div.diagram");
		diagrams.addClass("hidden");
	
	
		// If processDefinitionId doesn't contain ":" then it's a "processDefinitionKey", not an id.
		// Get process definition by key
		if (processDefinitionId.indexOf(":") < 0) {
			ActivitiRest.getProcessDefinitionByKey(processDefinitionId, this._drawDiagram);
		} else {
			this._drawDiagram.apply({processDefinitionId: processDefinitionId,processInstanceId: processInstanceId});
		}
	},
	_drawDiagram: function() {
		var processDefinitionId = this.processDefinitionId;
		var processInstanceId = this.processInstanceId;
		
		ProcessDiagramGenerator.addBreadCrumbsItem(processDefinitionId);
		
		
		// Check if processDefinition is already loaded and rendered
		
		
		var processDiagram = ProcessDiagramGenerator.getProcessDiagram(processDefinitionId,processInstanceId);

		if (processDiagram != undefined && processDiagram != null) {
			//console.log("Process diagram " + processDefinitionId + " is already loaded");
			//return;
			
			var diagram = document.getElementById(processDefinitionId);
			$(diagram).removeClass("hidden");
			
			// Regenerate image
			var processDefinitionDiagramLayout = processDiagram.processDefinitionDiagramLayout;
			ProcessDiagramGenerator.generateDiagram(processDefinitionDiagramLayout);
			
			return;
		}

		//console.time('loadDiagram');
		
		// Load processDefinition
		
		ActivitiRest.getProcessDefinition(processDefinitionId,processInstanceId, ProcessDiagramGenerator._generateDiagram);
	},
	_generateDiagram: function() {
		var processDefinitionDiagramLayout = this.processDefinitionDiagramLayout;
		
		//console.log("process-definition-diagram-layout["+processDefinitionDiagramLayout.processDefinition.id+"]: ", processDefinitionDiagramLayout);
		
		//console.timeEnd('loadDiagram');
		//console.time('generateDiagram');
		
		pb1.set('value', 0);
		ProcessDiagramGenerator.generateDiagram(processDefinitionDiagramLayout);
	},
	
	getProcessDefinitionByKey: function(processDefinitionKey) {
		var url = Lang.sub(this.options.processDefinitionByKeyUrl, {processDefinitionKey: processDefinitionKey});
		
		var processDefinition;
		$.ajax({
			url: url,
			type: 'POST',
			dataType: 'json',
			cache: false,
			async: false
		}).done(function(data) { 
			//console.log("ajax returned data");
			//console.log("ajax returned data:", data);
			processDefinition = data;
			if (!processDefinition) {
				//console.error("Process definition '" + processDefinitionKey + "' not found");
			}
		}).fail(function(jqXHR, textStatus){
			//console.error('Get diagram layout['+processDefinitionKey+'] failure: ', textStatus, jqXHR);
		});
		
		if (processDefinition) {
			//console.log("Get process definition by key '" + processDefinitionKey + "': ", processDefinition.id);
			return processDefinition;
		} else {
			return null;
		}
	},
	
	addBreadCrumbsItem: function(processDefinitionId){
		var TPL_UL_CONTAINER = '<ul></ul>',
			TPL_LI_CONTAINER = '<li id="{id}", processDefinitionId="{processDefinitionId}"><span>{name}</span></li>';
		
		if (!this.diagramBreadCrumbs)
			this.diagramBreadCrumbs = $("#" + this.options.diagramBreadCrumbsId);
		if (!this.diagramBreadCrumbs) return;
		
		
		var ul = this.diagramBreadCrumbs.find("ul");
		//console.log("ul: ", ul);
		if (ul.size() == 0) {
			ul = $(TPL_UL_CONTAINER);
			this.diagramBreadCrumbs.append(ul);
			
		}
		var liListOld = ul.find("li");
		//console.warn("liListOld", liListOld);
		
		// TODO: if there is any items after current then remove that before adding new item (m.b. it is a duplicate)
		var currentBreadCrumbsItemId = this.currentBreadCrumbsItemId;
			found = false;
		liListOld.each(
			function(index, item) {
				//console.warn("item:", $(this));
				if (!found && currentBreadCrumbsItemId == $(this).attr("id")) {
					found = true;
					return;
				}
				if (found) {
					//console.warn("remove ", $(this).attr("id"));
					$(this).remove();
				}
			}
		);
		
		var liListNew = ul.find("li");
		
		//console.log("liListNew size: ", liListNew.size());
		var values = {
			id: 'breadCrumbsItem_' + liListNew.size(),
			processDefinitionId: processDefinitionId,
			name: processDefinitionId
		};
		
		
		var tpl = Lang.sub(TPL_LI_CONTAINER, values);
		//console.log("tpl: ", tpl);
		ul.append(tpl);
		
		var li = ul.find("#" + values.id);
		//console.warn("li:", li);
		$('#' + values.id).on('click', this._breadCrumbsItemClick);
		
		ul.find("li").removeClass("selected");
		li.attr("num", liListNew.size());
		li.addClass("selected");
		this.currentBreadCrumbsItemId = li.attr("id");
	},
	_breadCrumbsItemClick: function(){
		var li = $(this),
			id = li.attr("id"),
			processDefinitionId = li.attr("processDefinitionId");
		//console.warn("_breadCrumbsItemClick: ", id, ", processDefinitionId: ", processDefinitionId);
		
		var ul = ProcessDiagramGenerator.diagramBreadCrumbs.one("ul");
		ul.find("li").removeClass("selected");
		li.addClass("selected");
		ProcessDiagramGenerator.currentBreadCrumbsItemId = li.attr("id");
		
		// Hide all diagrams
		var diagrams = $("#"+ProcessDiagramGenerator.options.diagramHolderId+" div.diagram");
		diagrams.addClass("hidden");
		
		var processDiagram = ProcessDiagramGenerator.getProcessDiagram(processDefinitionId);
		
		var diagram = document.getElementById(processDefinitionId);
		if (!diagram) return;
		$(diagram).removeClass("hidden");
		
		// Regenerate image
		var processDefinitionDiagramLayout = processDiagram.processDefinitionDiagramLayout;
		ProcessDiagramGenerator.generateDiagram(processDefinitionDiagramLayout);
	},
	
	showFlowInfo: function(flow){
/*		var diagramInfo = $("#" + this.options.diagramInfoId);
		if (!diagramInfo) return;
		if (flow.type=="association") return;
		var values = {
			flow: flow.flow,
			isDefault: (flow.isDefault)? "是":"否",
			isConditional: (flow.isConditional)? "true":"",
			isHighLighted: (flow.isHighLighted)? "true":"",
			sourceActivityId: flow.sourceActivityId,
			sourceActivityName: flow.sourseName,
			destinationActivityId: flow.destinationActivityId,
			destinationActivityName: flow.destinationName
		};
		var width=150;
		var height=100;
		var sumX=0;
		var minY=10000;
		var maxX=0
		var minX=10000;
		for(var i=0;i<flow.xPointArray.length;i++)
		{
			sumX=sumX+flow.xPointArray[i];
			if(maxX<flow.xPointArray[i])
				maxX=flow.xPointArray[i];
			if(minX>flow.xPointArray[i])
				minX=flow.xPointArray[i];
		}
		if(maxX-minX>width)
			var left=sumX/flow.xPointArray.length-((maxX-minX)-width)/2;
		else
			var left=sumX/flow.xPointArray.length-width/2;
		for(var i=0;i<flow.yPointArray.length;i++)
		{
			if(minY>flow.yPointArray[i])
				minY=flow.yPointArray[i];
		}
		var maxHeight=$("#diagramHolder").height()-height;
		var top=-document.getElementById("diagramHolder").scrollHeight+minY-height;;
		var TPL_FLOW_INFO = '<div style="padding:10px;position: absolute;top:'+top+'px; left:'+left+'px; border:1px solid; width: '+width+'px; height: '+height+'px;background-color: #FFFFFF">' 
				+ '<font color="#333"><div>起点: {sourceActivityName}</div>'
				+ '<div>终点: {destinationActivityName}</div>'
				+ '<div>是否默认: {isDefault}</div>';
		var name=flow.name;
		if(name!=null)
			TPL_FLOW_INFO=TPL_FLOW_INFO+ '<div>条件: '+name+'</div></font>';
		else
			TPL_FLOW_INFO=TPL_FLOW_INFO+ '</font>';
		var tpl = Lang.sub(TPL_FLOW_INFO, values);
		//console.log("info: ", tpl);
		diagramInfo.html(tpl);*/
	},
	
	showActivityInfo: function(activity){
		var diagramInfo = $("#" + this.options.diagramInfoId);
		if (!diagramInfo) return;
		var values = {
			activityId: activity.getId(),
			name: activity.getProperty("name"),
			type: activity.getProperty("type")
			
		};
		var zhType;
		if(activity.getProperty("type")=="userTask") zhType="用户任务";
		else if(activity.getProperty("type")=="startEvent") zhType="启动";
		else if(activity.getProperty("type")=="startTimerEvent") zhType="定时启动";
		else if(activity.getProperty("type")=="errorStartEvent") zhType="错误启动";
		else if(activity.getProperty("type")=="approveUserTask") zhType="审批任务";
		else if(activity.getProperty("type")=="scriptTask") zhType="脚本任务";
		else if(activity.getProperty("type")=="serviceTask") zhType="服务任务";
		else if(activity.getProperty("type")=="mailTask") zhType="邮件任务";
		else if(activity.getProperty("type")=="manualTask") zhType="手工任务";
		else if(activity.getProperty("type")=="receiveTask") zhType="接收任务";
		else if(activity.getProperty("type")=="sendTask") zhType="邮件任务";
		else if(activity.getProperty("type")=="businessRuleTask") zhType="业务规则任务";
		else if(activity.getProperty("type")=="eventSubProcess") zhType="事件子流程";
		else if(activity.getProperty("type")=="subProcess") zhType="子流程";
		else if(activity.getProperty("type")=="callActivity") zhType="调用活动";
		else if(activity.getProperty("type")=="parallelGateway") zhType="并行网关";
		else if(activity.getProperty("type")=="exclusiveGateway") zhType="独占网关";
		else if(activity.getProperty("type")=="inclusiveGateway") zhType="包含网关";
		else if(activity.getProperty("type")=="eventBasedGateway") zhType="事件网关";
		else if(activity.getProperty("type")=="boundaryTimer") zhType="定时边界事件";
		else if(activity.getProperty("type")=="boundaryError") zhType="错误边界事件";
		else if(activity.getProperty("type")=="boundarySignal") zhType="信号边界事件";
		else if(activity.getProperty("type")=="boundaryMessage") zhType="消息边界事件";
		else if(activity.getProperty("type")=="compensationBoundaryCatch") zhType="补偿边界事件";
		else if(activity.getProperty("type")=="Compensate") zhType="补偿";
		else if(activity.getProperty("type")=="Timer") zhType="定时";
		else if(activity.getProperty("type")=="Error") zhType="错误";
		else if(activity.getProperty("type")=="Signal") zhType="信号";
		else if(activity.getProperty("type")=="Message") zhType="消息";
		else if(activity.getProperty("type")=="intermediateTimer") zhType="定时中间事件（捕获）";
		else if(activity.getProperty("type")=="intermediateSignalCatch") zhType="信号中间事件（捕获）";
		else if(activity.getProperty("type")=="intermediateMessageCatch") zhType="消息中间事件（捕获）";
		else if(activity.getProperty("type")=="intermediateThrowEvent") zhType="中间事件（抛掷）";
		else if(activity.getProperty("type")=="intermediateSignalThrow") zhType="信号中间事件（抛掷）";
		else if(activity.getProperty("type")=="intermediateCompensateThrow") zhType="补偿中间事件（抛掷）";
		else if(activity.getProperty("type")=="intermediateMessageThrow") zhType="消息中间事件（抛掷）";
		else if(activity.getProperty("type")=="endEvent") zhType="结束";
		else if(activity.getProperty("type")=="errorEndEvent") zhType="错误结束";
		else if(activity.getProperty("type")=="terminateEndEvent") zhType="终止结束";
		var actState=null;
		var state=activity.getProperty("state");
		var tasknum=activity.getProperty("taskNum");
		if(state=="complete")
			actState="完成";
		else if(state=="run")
			actState="处理中";
		var width=230;
		var height=60;
		var left=activity.x-30;
		var showMessage="";
		if(actState!=null&&actState!=undefined)
			showMessage='<div>状态: '+actState+'</div>';
		if(activity.getProperty("name")!=null&&activity.getProperty("name").length>19)
			height=height+(Math.ceil(activity.getProperty("name").length/19)-1)*14;
		for(var i=0;i<tasknum;i++)
		{
			var taskAssignment=activity.getProperty("assignment"+i);
			var taskcreateTime=activity.getProperty("createTime"+i);
			var taskendTime=activity.getProperty("endTime"+i);
			var taskdurationInMillis=activity.getProperty("durationInMillis"+i);
			var hour=Math.floor(taskdurationInMillis/1000/60/60);
			var minute=Math.floor((taskdurationInMillis-hour*1000*60*60)/1000/60);
			var second=(taskdurationInMillis-hour*1000*60*60-minute*1000*60)/1000;
			var time=hour+"小时"+minute+"分"+second+"秒";
			if(taskAssignment!=null)
			{
				showMessage=showMessage+ '<div><div>任务执行人:' +taskAssignment;
				height=height+14;
			}
			if(taskcreateTime!=null)
			{
				showMessage=showMessage+ '<div><div>任务创建时间: '+taskcreateTime;
				height=height+14;
			}
			if(taskendTime!=null)
			{
				showMessage=showMessage+ '<div><div>任务完成时间: '+taskendTime;
				height=height+14;
			}
			if(taskdurationInMillis!=null)
			{
				showMessage=showMessage+'<div><div>耗时: '+time+'</div>';
				height=height+14;
			}
			showMessage=showMessage+'<div></div>';
			if(taskAssignment!=null&&taskAssignment.length>19)
				height=height+(Math.ceil(taskAssignment.length/19)-1)*14;
		}
		var maxHeight=$("#diagramHolder").height()-height;
		var maxY=-document.getElementById("diagramHolder").scrollHeight+activity.y+activity.height;
		var TPL_ACTIVITY_INFO = '' 
				+ '<div id="showActMessage" style="padding:10px;position: absolute; top:'+maxY+'px; left:'+left+'px; border:1px solid; width: '+width+'px; height: '+height+'px;background-color: #FFFFFF"><font color="#333"> <div>活动ID: {activityId}</div>'
				+ '<div>活动名称: {name}</div>'
				+ '<div>类型:' +zhType+'</div>'+showMessage+'</font></div>';

		var TPL_CALLACTIVITY_INFO = ''
				+ '<div style="padding:10px;position: absolute;top:'+maxY+'px; left:'+left+'px; border:1px solid; width: '+width+'px; height: '+height+'px;background-color: #FFFFFF"> <div>折叠: {collapsed}</div>'
				+ '<div><b>流程定义ID</b>: {processDefinitonKey}</div></div>';
		
		
		var template = TPL_ACTIVITY_INFO;
		if (activity.getProperty("type") == "callActivity") {
			values.collapsed = activity.getProperty("collapsed");
			if(values.collapsed)
				values.collapsed="是";
			else
				values.collapsed="否";
			values.processDefinitonKey = activity.getProperty("processDefinitonKey");
			template += TPL_CALLACTIVITY_INFO;
		} else if (activity.getProperty("type") == "callActivity") {
		
		}
				
		var tpl = Lang.sub(template, values);
		//console.log("info: ", tpl);
		diagramInfo.html(tpl);
	},
	
	hideInfo: function(){
	  var diagramInfo = $("#" + this.options.diagramInfoId);
	  if (!diagramInfo) return;
	  diagramInfo.html("");
	},
	
	vvoid: function(){}
};

var Lang = {
	SUBREGEX: /\{\s*([^\|\}]+?)\s*(?:\|([^\}]*))?\s*\}/g,
	UNDEFINED: 'undefined',
	isUndefined: function(o) {
		return typeof o === Lang.UNDEFINED;
	},
	sub: function(s, o) {
		return ((s.replace) ? s.replace(Lang.SUBREGEX, function(match, key) {
			return (!Lang.isUndefined(o[key])) ? o[key] : match;
		}) : s);
	}
};

if (Lang.isUndefined(console)) {
    console = { log: function() {}, warn: function() {}, error: function() {}};
}
ProcessDiagramGenerator.init();