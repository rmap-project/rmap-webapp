<script>
	$(function(){ // on dom ready

		$('#cy').cytoscape({
		  style: cytoscape.stylesheet()
		    .selector('node')
		      .css({
                'background-color': '#6272A3',
                'width': 'mapData(weight, 40, 80, 20, 60)',
                'height': 'mapData(weight, 40, 80, 20, 60)',
		        'content': 'data(name)',
		        'text-valign': 'center',
		        'color': 'black'
		      })
		    .selector('edge')
		      .css({
                'line-color':'#B1C1F2',
		        'target-arrow-shape': 'triangle',
                'target-arrow-color': '#B1C1F2',
		        'content' : 'data(label)',
                'width': 3,
                'edge-text-rotation': 'autorotate',
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
		    window.cy = this;
		    
		    // giddy up...
		    
		    cy.elements().unselectify();
		    
		    cy.on('tap', 'node', function(e){
		      var node = e.cyTarget; 
		      var neighborhood = node.neighborhood().add(node);
		      
		      cy.elements().addClass('faded');
		      neighborhood.removeClass('faded');
		    });
		    
		    cy.on('tap', function(e){
		      if( e.cyTarget === cy ){
		        cy.elements().removeClass('faded');
		      }
		    });
		  }
		});

		}); // on dom ready

</script>