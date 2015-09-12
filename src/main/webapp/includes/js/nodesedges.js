<script>

var nodes, edges, network;

function drawgraph(){
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
	        stabilize: true,
	        stabilizationIterations: 3500,
	        zoomExtentOnStabilize: true,
	        navigation: true,
	        keyboard: true,
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
	              color: "#0000FF" // blue
	            },
	            TEXT: {
	              shape: 'dot',
	              color: "#4F4FCD" // rmap purple
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
		nodes.forEach(function (node) {
		  if (node.id==params.nodes && node.group!='LITERAL'){
			location.href="resources?uri=" + node.title;
		    }
		});
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
}
 
function toggleLiterals()
	{
	var toggleButton = document.getElementById('toggleLiterals');
	var toggleText = toggleButton.innerHTML;
				
	if (toggleText=="Hide literals") 
		{		
		nodes.forEach(function(node) {
		    if (node.group == 'LITERAL')	{
				nodes.remove({id: node.id}); 		    	
		    };
		});
		edges.forEach(function(edge) {
		    if (edge.targetgroup == 'LITERAL')	{
		    	edges.remove({id: edge.id}); 		    	
		    };
		});
		toggleButton.innerHTML="Show literals";
		}
	else 
		{
		drawgraph();
		toggleButton.innerHTML="Hide literals";
		}   
	}


function toggle(type)
	{
	var toggleTypeBtn = document.getElementById('toggleTypes');
	var toggleTypeText = toggleTypeBtn.innerHTML;
	
	var toggleLiteralBtn = document.getElementById('toggleLiterals');
	var toggleLiteralText = toggleLiteralBtn.innerHTML;
				
	if ((type=="TYPE" && toggleTypeText=="Hide types") || (type=="LITERAL" && toggleLiteralText=="Hide literals")) 
		{		
		nodes.forEach(function(node) {
		    if (node.group == type)	{
				nodes.remove({id: node.id}); 		    	
		    };
		});
		edges.forEach(function(edge) {
		    if (edge.targetgroup == type)	{
		    	edges.remove({id: edge.id}); 		    	
		    };
		});
		if (type=="LITERAL") {toggleLiteralBtn.innerHTML="Reset graph";}
		if (type=="TYPE") {toggleTypeBtn.innerHTML="Reset graph";}
		}
	else 
		{
		document.getElementById('loadbar').style.opacity = 100;
		setTimeout(function () {document.getElementById('loadbar').style.display = 'inline';}, 320);
		drawgraph();
		toggleTypeBtn.innerHTML="Hide types";
		toggleLiteralBtn.innerHTML="Hide literals";
		}   
	}


</script>