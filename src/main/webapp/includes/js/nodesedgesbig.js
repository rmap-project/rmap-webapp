<script>
	$(function(){ // on dom ready

		$('#cybig').cytoscape({
			  style: cytoscape.stylesheet()
			    .selector('node')
			      .css({
	                'background-color': '#4F4FCD',
	                'width': 'mapData(weight, 20, 80, 20, 60)',
	                'height': 'mapData(weight, 20, 80, 20, 60)',
			        'content': 'data(name)',
			        'text-valign': 'top',
			        'text-halign': 'center',
			        'color': '#000',                
	                'font-size':'18px',
	                'text-max-width':'200',
	                'text-wrap':'wrap'
			      })
			    .selector('edge')
			      .css({
	                'line-color':'#B3B3D2',
			        'target-arrow-shape': 'triangle',
	                'target-arrow-color': '#B3B3D2',
			        'content' : 'data(label)',
	                'width': 4,
	                'edge-text-rotation': 'autorotate',
	                'font-size':'18px'
			      })
	          		  
	          
	          .selector(':selected')
			      .css({
			        'background-color': 'black',
			        'line-color': 'black',
			        'target-arrow-color': 'black',
			        'source-arrow-color': 'black'
			      })
			    .selector('.faded')
			      .css({
			        'opacity': 0.25,
			        'text-opacity': 0
			      }),

		  elements: {
		    nodes: [
			  <c:forEach var="node" items="${OBJECT_NODES}">
		     	 { data: { id: '${node.getId()}', name: '${node.getName()}', weight:${node.getWeight()}} },
		      </c:forEach>
		    ],
		    edges: [
			  <c:forEach var="edge" items="${OBJECT_EDGES}">
				{ data: { source: '${edge.getSource()}', target: '${edge.getTarget()}', label:'${edge.getLabel()}'} },
			  </c:forEach>
		    ]
		  },
		  		    
		    
		  layout: {
		    name: 'circle',
		    directed: true,
            padding:30,
			fit: true,
			boundingBox: undefined,
			avoidOverlap: true,
			animate: true,
			animationDuration: 500
		  },
		  
		  // on graph initial layout done (could be async depending on layout...)
		  ready: function(){
		    window.cybig = this;
		    
		    // giddy up...
		    
		    cybig.elements().unselectify();
		    
		    cybig.on('tap', 'node', function(e){
		      var node = e.cyTarget; 
		      var neighborhood = node.neighborhood().add(node);
		      
		      cybig.elements().addClass('faded');
		      neighborhood.removeClass('faded');
		    });
		    
		    cybig.on('tap', function(e){
		      if( e.cyTarget === cybig ){
		        cybig.elements().removeClass('faded');
		      }
		    });
		  }
		});

		}); // on dom ready

</script>