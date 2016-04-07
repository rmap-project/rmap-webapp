<script>

var nodes, edges, network;

function drawgraph(){
	                         
   <c:choose>
     <c:when test="${NEWDISCO}">
 		nodes = new vis.DataSet([]);
 		edges = new vis.DataSet([]);
     </c:when>
     <c:otherwise>
 		 nodes = new vis.DataSet([
     	 <c:forEach var="node" items="${OBJECT_NODES}" varStatus="loop">
		 {id: ${node.getId()}, title: '${node.getName()}', label: '${node.getShortname()}', value:${node.getWeight()}, group:'${node.getType().toString()}'}<c:if test="${!loop.last}">,</c:if>
		 </c:forEach>
		 ]);
 		 edges = new vis.DataSet([
		 <c:forEach var="edge" items="${OBJECT_EDGES}" varStatus="loop">
		 {from: ${edge.getSource()}, to: ${edge.getTarget()}, label:'${edge.getLabel()}', arrows:'to', targetgroup:'${edge.getTargetNodeType().toString()}'}<c:if test="${!loop.last}">,</c:if>
		 </c:forEach>
		 ]);
     </c:otherwise>
   </c:choose> 
	
	var container = document.getElementById('mynetwork');
	var data = {
			nodes: nodes,
			edges: edges
	};
	var options = {
			autoResize:true,
			nodes: {
				shape: 'dot',
				font: {strokeWidth: 2, strokeColor : '#ffffff'}
			},
			edges: {
				width: 0.15,
				color: {inherit: 'from'},
				smooth: {
					type: 'dynamic'
				},
				font: {align: 'middle', strokeWidth: 2, strokeColor : '#ffffff'}
			},
			physics: {
				barnesHut: {
					gravitationalConstant: -9000,
					centralGravity: 0.6,
					springLength: 80,
					springConstant: 0.03,
					damping: 0.5,
					avoidOverlap: 0.4
				},
				maxVelocity: 500,
				minVelocity: 0.2
			},
	        smoothCurves: {dynamic:false},
        manipulation: {
          addNode: function (data, callback) {
            // filling in the popup DOM elements
			 //{id: 8, title: 'ark:/22573/rmd18mdd27', label: 'ark:/22573/rmd18mdd27', value:30, group:'AGENT'},
            document.getElementById('operation').innerHTML = "Add Node";
            document.getElementById('node-id').value = data.id;	
            document.getElementById('node-label').value = "http://doi.org/10.12334/kasjf92k";
			//document.getElementById('node-label').value = data.title;	
			document.getElementById('node-value').value = 30;
			document.getElementById('node-group').value = "UNDEFINED";
            document.getElementById('saveButton').onclick = saveData.bind(this, data, callback);
            document.getElementById('cancelButton').onclick = clearPopUp.bind();
            document.getElementById('network-popUp').style.display = 'block';
          },		  
          editNode: function (data, callback) {
            // filling in the popup DOM elements
            document.getElementById('operation').innerHTML = "Edit Node";
            document.getElementById('node-id').value = data.id;
            document.getElementById('node-label').value = data.title;			
			//document.getElementById('node-title').value = data.title;
			document.getElementById('node-value').value = data.value;
			document.getElementById('node-group').value = data.group;
            document.getElementById('saveButton').onclick = saveData.bind(this, data, callback);
            document.getElementById('cancelButton').onclick = cancelEdit.bind(this,callback);
            document.getElementById('network-popUp').style.display = 'block';
          },
          addEdge: function (data, callback) {
            document.getElementById('operation2').innerHTML = "Add Edge";
            document.getElementById('edge-label').value = "dcterms:relation";	
			document.getElementById('edge-from').value = data.from;
			document.getElementById('edge-to').value = data.to;
            document.getElementById('edgeSaveButton').onclick = saveEdgeData.bind(this, data, callback);
            document.getElementById('edgeCancelButton').onclick = cancelEdit.bind(this,callback);
            document.getElementById('edge-popUp').style.display = 'block';		
          },
          editEdge: function (data, callback) {
			var label = "";
			edges.forEach(function(edge) {
				if (edge.id == data.id)	{
					label = edge.label;
				};
			});
            document.getElementById('operation2').innerHTML = "Edit Edge";
            document.getElementById('edge-label').value = label;	
			document.getElementById('edge-from').value = data.from;
			document.getElementById('edge-to').value = data.to;
            document.getElementById('edgeSaveButton').onclick = saveEdgeData.bind(this, data, callback);
            document.getElementById('edgeCancelButton').onclick = cancelEdit.bind(this,callback);
            document.getElementById('edge-popUp').style.display = 'block';		
          }
        },
	        stabilize: true,
	        stabilizationIterations: 3500,
	        zoomExtentOnStabilize: true,
	        navigation: true,
			interaction: {
				  keyboard: true
				},
		        groups: {
		            LITERAL: {
		              shape: 'dot',
		              color: '#C0C0C0' // grey
		            },
		            DISCO: {
		              shape: 'square',
		              color: "#91CC00" // rmap green
		            },
		            DATASET: {
		              shape: 'dot',
		              color: "#C392E9" // pinkish purple
		            },
		            TEXT: {
		              shape: 'dot',
		              color: "#4F4FCD" // rmap purple
		            },
		            PHYSICALOBJECT: {
		              shape: 'dot',
		              color: "#996600" // brown
		            },
		            CODE: {
			              shape: 'dot',
			              color: "#FF9900" // orange
			            },
		            AGENT: {
		            	shape: 'dot',
		            	color: '#C5000B' // red
		            },
		            TYPE: {
		            	shape: 'dot',
		            	color: '#FFFF00' // yellow
		            },
		            UNDEFINED: {
			              shape: 'dot',
			              color: "#87CEFA" // light blue
			            }
		          }
	};

	network = new vis.Network(container, data, options);
	network.on("click", function (params) {
		if (document.getElementsByClassName('vis-manipulation')[0].style.display!='block') {
			nodes.forEach(function (node) {
			  if (node.id==params.nodes && node.group!='LITERAL' && node.group!='TYPE'){
				location.href="<c:url value='/resources/'/>" + encodeURIComponent(node.title);
				}
			});
		}
	  });
	  
	network.on("stabilizationProgress", function(params) {
		var maxWidth = 200;
		var minWidth = 20;
		var widthFactor = params.iterations/params.total;
		var width = Math.max(minWidth,maxWidth * widthFactor); 

		document.getElementById('loadbarBar').style.width = width + 'px'; 
		document.getElementById('loadbarText').innerHTML = Math.round(widthFactor*100) + '%';
	});
	
	network.once("stabilizationIterationsDone", function() {
		document.getElementById('loadbarText').innerHTML = '100%';
		document.getElementById('loadbarBar').style.width = '200px';
		document.getElementById('loadbar').style.opacity = 0;
		// really clean the dom element
		setTimeout(function () {document.getElementById('loadbar').style.display = 'none';}, 320);
	});
	
    clearPopUp();
}

    function clearPopUp() {
      document.getElementById('saveButton').onclick = null;
      document.getElementById('cancelButton').onclick = null;
      document.getElementById('network-popUp').style.display = 'none';
      document.getElementById('edge-popUp').style.display = 'none';
    }

    function cancelEdit(callback) {
      clearPopUp();
      callback(null);
    }
    
    function generateLabel(title){
    	var MAX_LABEL_LENGTH=21;
    	var newLabel = title;
    	if (newLabel.length>MAX_LABEL_LENGTH) {
    		newLabel = newLabel.substring(0,MAX_LABEL_LENGTH-3) + "...";
    	}
    	return newLabel;
    }
    
    
    function saveData(data,callback) {
		data.id = document.getElementById('node-id').value;
		data.title = document.getElementById('node-label').value;
		data.label = generateLabel(data.title);
		data.value = document.getElementById('node-value').value;
		data.group = document.getElementById('node-group').value;
		clearPopUp();
		callback(data);
        //add type automagically
		var newNodeId = data.id + "1";
		var label = "";
		if (data.group=="CODE"){
    	    label = "dcmitype:Software";
			}
		if (data.group=="DATASET"){	
    	    label = "dcmitype:Dataset";	
		}
		if (data.group=="PHYSICALOBJECT"){	
    	    label = "dcmitype:PhysicalObject";	
		}
		if (data.group=="TEXT"){		
    	    label = "dcmitype:Text";	
		}
		if (data.group=="AGENT"){			
    	    label = "foaf:Agent";	
		}
		if (label.length>0) {
    	    nodes.add({id: newNodeId,title: label, label: label, value:30, group:'TYPE'});
    	    edges.add({from:data.id, to:newNodeId, arrows:'to', targetgroup:'TYPE', label:'rdf:type'});
			}
    }
	
	function saveEdgeData(data,callback) {
		data.from = document.getElementById('edge-from').value;
		data.to = document.getElementById('edge-to').value;
		data.label = document.getElementById('edge-label').value;
		data.arrows = "to";
		if (data.from == data.to) {
		  var r = confirm("Do you want to connect the node to itself?");
		  if (r == true) {
			callback(data);		
		  }
		}
		else {			  
			callback(data);
		}
     clearPopUp();
	}

function toggle(type)
	{	
	var toggleTypeBtn = document.getElementById('toggleTypes');
	var toggleTypeText = toggleTypeBtn.innerHTML;
	
	var toggleLiteralBtn = document.getElementById('toggleLiterals');
	var toggleLiteralText = toggleLiteralBtn.innerHTML;
				
	if ((type=="TYPE" && toggleTypeText=="Hide types") 
			|| (type=="LITERAL" && toggleLiteralText=="Hide literals")) 
		{		
		removeNodeType(type);
		if (type=="LITERAL") {toggleLiteralBtn.innerHTML="Show literals";}
		if (type=="TYPE") {toggleTypeBtn.innerHTML="Show types";}
		}
	else 
		{
		addNodeType(type);
		if (type=="LITERAL") {toggleLiteralBtn.innerHTML="Hide literals";}
		if (type=="TYPE") {toggleTypeBtn.innerHTML="Hide types";}
	}   
}
//store and edges and nodes that have been removed
var removedNodes= new vis.DataSet([]);
var removedEdges = new vis.DataSet([]);

function removeNodeType(type){
	nodes.forEach(function(node) {
		if (node.group == type)	{
			nodes.remove({id: node.id});
			removedNodes.add(node);
		};
	});
	edges.forEach(function(edge) {
		if (edge.targetgroup == type)	{
			edges.remove({id: edge.id}); 	
			removedEdges.add(edge);	    	
		};
	});
}

function addNodeType(type){
	removedNodes.forEach(function(node) {
		if (node.group == type)	{
			nodes.add({id: node.id,title: node.title, label: node.label, value:node.value, group:node.group}); 		
			removedNodes.remove({id: node.id});
			
		};
	});
	removedEdges.forEach(function(edge) {
		if (edge.targetgroup == type)	{
			edges.add({from: edge.from, to: edge.to, label:edge.label, arrows:edge.arrows, targetgroup:edge.targetgroup}); 		   
			removedEdges.remove({id: edge.id}); 	
		};
	});
}


</script> 