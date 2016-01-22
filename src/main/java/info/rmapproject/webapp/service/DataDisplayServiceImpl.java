package info.rmapproject.webapp.service;

import info.rmapproject.core.exception.RMapObjectNotFoundException;
import info.rmapproject.core.model.RMapLiteral;
import info.rmapproject.core.model.RMapResource;
import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.model.RMapUri;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.core.model.agent.RMapAgent;
import info.rmapproject.core.model.disco.RMapDiSCO;
import info.rmapproject.core.model.event.RMapEvent;
import info.rmapproject.core.model.event.RMapEventCreation;
import info.rmapproject.core.model.event.RMapEventDeletion;
import info.rmapproject.core.model.event.RMapEventDerivation;
import info.rmapproject.core.model.event.RMapEventInactivation;
import info.rmapproject.core.model.event.RMapEventTombstone;
import info.rmapproject.core.model.event.RMapEventType;
import info.rmapproject.core.model.event.RMapEventUpdate;
import info.rmapproject.core.model.event.RMapEventUpdateWithReplace;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.webapp.domain.Graph;
import info.rmapproject.webapp.domain.NodeType;
import info.rmapproject.webapp.domain.ResourceDescription;
import info.rmapproject.webapp.domain.TripleDisplayFormat;
import info.rmapproject.webapp.service.dto.AgentDTO;
import info.rmapproject.webapp.service.dto.AgentDTOImpl;
import info.rmapproject.webapp.service.dto.DiSCODTO;
import info.rmapproject.webapp.service.dto.DiSCODTOImpl;
import info.rmapproject.webapp.service.dto.EventDTO;
import info.rmapproject.webapp.service.dto.EventDTOImpl;
import info.rmapproject.webapp.service.dto.ResourceDTO;
import info.rmapproject.webapp.service.dto.ResourceDTOImpl;
import info.rmapproject.webapp.utils.WebappUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openrdf.model.vocabulary.RDF;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author khanson
 *
 */

@Service("dataDisplayService")
@Transactional
public class DataDisplayServiceImpl implements DataDisplayService {

//private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	//private static final Logger logger = LoggerFactory.getLogger(DiSCOServiceImpl.class);
	
	@Override
	public DiSCODTO getDiSCODTO(String sDiscoUri) throws Exception {
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		
		DiSCODTOImpl discoDTO = new DiSCODTOImpl();
		
		sDiscoUri = URLDecoder.decode(sDiscoUri, "UTF-8");
		URI discoUri = new URI(sDiscoUri);	
		discoDTO.setUri(discoUri);
		
		RMapDiSCO disco = rmapService.readDiSCO(discoUri);
    	List <URI> aggregatedResources = disco.getAggregratedResources(); 	
    	
		discoDTO.setDescription(disco.getDescription());
		discoDTO.setCreator(disco.getCreator());
		
		discoDTO.setAgentVersions(rmapService.getDiSCOAllAgentVersions(discoUri));
		discoDTO.setAllVersions(rmapService.getDiSCOAllVersions(discoUri));
		
		discoDTO.setStatus(rmapService.getDiSCOStatus(discoUri));
		discoDTO.setEvents(rmapService.getDiSCOEvents(discoUri));
    	discoDTO.setAggregatedResources(aggregatedResources);
    	
	    List <RMapTriple> triples = disco.getRelatedStatements();    
	    
	    Graph graph = createDiSCOGraph(discoUri,  discoDTO.getDescription(),
										discoDTO.getCreator(), aggregatedResources, triples);	  
	    discoDTO.setGraph(graph);
	    
	    List<ResourceDescription> resourceDescriptions = getResourceDescriptions(aggregatedResources, triples);
	    discoDTO.setResourceDescriptions(resourceDescriptions);

	    rmapService.closeConnection();
		return discoDTO;
	}


	@Override
	public AgentDTO getAgentDTO(String sAgentUri) throws Exception {
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		
		AgentDTOImpl agentDTO = new AgentDTOImpl();
		
		sAgentUri = URLDecoder.decode(sAgentUri, "UTF-8");
		URI agentUri = new URI(sAgentUri);	
		agentDTO.setUri(agentUri);
		
		RMapAgent agent = rmapService.readAgent(agentUri);
		agentDTO.setName(agent.getName());		
		agentDTO.setStatus(rmapService.getAgentStatus(agentUri));
		agentDTO.setEvents(rmapService.getAgentEvents(agentUri));
		agentDTO.setIdProvider(agent.getIdProvider().getStringValue());
		agentDTO.setAuthId(agent.getAuthId().getStringValue());
		agentDTO.setDiscos(rmapService.getAgentDiSCOs(agentUri, RMapStatus.ACTIVE));
		
	    Graph graph = createAgentGraph(agentUri,  agentDTO.getName(),  agentDTO.getIdProvider(), agentDTO.getAuthId());	  
	    agentDTO.setGraph(graph);
	    	  	    	    
	    rmapService.closeConnection();
		
		return agentDTO;
	}

	public EventDTO getEventDTO(String sEventUri) throws Exception {
	
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		
		EventDTOImpl eventDTO = new EventDTOImpl();

		sEventUri = URLDecoder.decode(sEventUri, "UTF-8");
		URI eventUri = new URI(sEventUri);
		eventDTO.setUri(eventUri);
		
		RMapEvent event = rmapService.readEvent(eventUri);
		eventDTO.setDescription(event.getDescription());
		eventDTO.setAssociatedAgent(event.getAssociatedAgent());
		eventDTO.setTargetType(event.getEventTargetType());
		eventDTO.setStartTime(event.getStartTime());
		eventDTO.setEndTime(event.getEndTime());

		RMapEventType eventType = event.getEventType();
		eventDTO.setType(eventType);
		
	    Map<String, String> resourcesAffected = getEventResourcesAffected(event, eventType);
	    eventDTO.setResourcesAffected(resourcesAffected);  
	    rmapService.closeConnection();
	
	    return eventDTO;
	}
	
	public ResourceDTO getResourceDTO(String sResourceUri) throws Exception{
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		ResourceDTOImpl resourceDTO = new ResourceDTOImpl();

		sResourceUri = URLDecoder.decode(sResourceUri, "UTF-8");
		URI resourceUri = new URI(sResourceUri);
		resourceDTO.setUri(resourceUri);

		List<RMapTriple> rmapTriples = rmapService.getResourceRelatedTriples(resourceUri, RMapStatus.ACTIVE);
    	
		//if there are no triples, don't load an empty screen, show a not found error.
    	if (rmapTriples.size()==0)	{
    		throw new RMapObjectNotFoundException();
    	}

    	//start new resource description
    	ResourceDescription resourceDescription = new ResourceDescription(sResourceUri);	    
    	    	
    	for (RMapTriple triple : rmapTriples) {    		
    		TripleDisplayFormat tripleDF = new TripleDisplayFormat(triple);
    		
    		if (tripleDF.getPredicateDisplay().contains("rdf:type") 
    				&& triple.getSubject().toString().equals(sResourceUri))	{
    			resourceDescription.addResourceType(tripleDF);
    		}
    		else {
    			resourceDescription.addPropertyValue(tripleDF);		
    		}
    	}
    	    		
	    resourceDTO.setResourceDescription(resourceDescription);

	    List <URI> relatedDiSCOs = rmapService.getResourceRelatedDiSCOs(resourceUri, RMapStatus.ACTIVE);
	    resourceDTO.setRelatedDiSCOs(relatedDiSCOs);
	    
	    //used to create visual graph

	    Graph graph = new Graph();
		graph = addTriplesToGraph(graph, rmapTriples);  
	    resourceDTO.setGraph(graph);
	    	    
	    rmapService.closeConnection();
		return resourceDTO;
	}
	
	@SuppressWarnings("unused")
	private List<ResourceDescription> getResourceDescriptions(URI keyResource, List<RMapTriple> triples) {
		List<URI> keyResources = new ArrayList<URI>();
		keyResources.add(keyResource);
		return getResourceDescriptions(keyResources, triples);		
	}

	private List<ResourceDescription> getResourceDescriptions(List<URI> keyResources, List<RMapTriple> triples) {
		
	    List<ResourceDescription> resourceDescriptions = new ArrayList<ResourceDescription>();

	    //first extract unique list of resources mentioned in subject	
	    Set<String> resourcesDescribed = new LinkedHashSet<String>();
	    for (URI aggregate : keyResources) {
	    	resourcesDescribed.add(aggregate.toString());
	    }
	    for (RMapTriple stmt:triples) {
	    	resourcesDescribed.add(stmt.getSubject().toString());
	    }
	    
	    //now sort statements into blocks by resource
	    for (String resource : resourcesDescribed) {
	    	
	    	Map<String,TripleDisplayFormat> types = new TreeMap<String,TripleDisplayFormat>();	    	
	    	Map<String,TripleDisplayFormat> properties = new TreeMap<String,TripleDisplayFormat>(); 
	    	
	    	for (RMapTriple stmt : triples) {
	    		RMapResource subject = stmt.getSubject();
	    		
	    		if (subject.toString().equals(resource)) {
	    			TripleDisplayFormat tripleDF = new TripleDisplayFormat(stmt);
	    			String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
	    			
	    			if (tripleDF.getPredicateDisplay().contains("rdf:type"))	{
	    				types.put(listKey, tripleDF);	
	    			}
	    			else {
	    				properties.put(listKey, tripleDF);				
	    			}
	    		}
	    	}
	    		    	
	    	resourceDescriptions.add(new ResourceDescription(resource, types, properties));	    	
	    }

	return resourceDescriptions;
	}
	
	
	private Graph createDiSCOGraph(URI discoUri, 
			String discoDescription,
			String discoCreator,
			List<URI> aggregatedResources,
			List<RMapTriple> triples) throws Exception {
		Graph graph = new Graph();
		String sDiscoUri = discoUri.toString();

		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		
		graph.addEdge(sDiscoUri,"rmap:DiSCO","rdf:type", NodeType.DISCO, NodeType.TYPE);
		
		if (discoDescription.length()>0) {
		graph.addEdge(sDiscoUri, discoDescription,"dcterms:description", NodeType.DISCO, NodeType.LITERAL);
		}
		if (discoCreator.length()>0) {
		graph.addEdge(sDiscoUri, discoCreator,"dcterms:creator", NodeType.DISCO, NodeType.AGENT);
		}
		
		for (URI aggregate : aggregatedResources) {
			Set <URI> rdfTypes = rmapService.getResourceRdfTypes(aggregate, discoUri);
			NodeType targetNodeType = NodeType.getNodeType(rdfTypes);
			graph.addEdge(sDiscoUri, aggregate.toString(),"ore:aggregates", NodeType.DISCO, targetNodeType);
		}

		graph = addTriplesToGraph(graph, triples, discoUri);
		
		rmapService.closeConnection();
		return graph;
		}	
	
	private Graph createAgentGraph(URI agentUri, 
			String agentName,
			String idProvider,
			String authId) throws Exception {

		Graph graph = new Graph();
		String sAgentUri = agentUri.toString();
		
		graph.addEdge(sAgentUri,"rmap:Agent","rdf:type", NodeType.AGENT, NodeType.TYPE);
		graph.addEdge(sAgentUri, agentName,"foaf:name", NodeType.AGENT, NodeType.LITERAL);
		graph.addEdge(sAgentUri, idProvider,"rmap:identityProvider", NodeType.AGENT, NodeType.AGENT);
		graph.addEdge(sAgentUri, authId,"rmap:userAuthId", NodeType.AGENT, NodeType.UNDEFINED);
					
		return graph;
		}	
	
	private Graph addTriplesToGraph(Graph graph, List<RMapTriple> triples) throws Exception{
		return addTriplesToGraph(graph, triples, null);
	}
			
	private Graph addTriplesToGraph(Graph graph, List<RMapTriple> triples, URI contextUri) throws Exception{
		URI lastSubject = null;
		NodeType lastSourceNodeType = null;
		for (RMapTriple triple : triples) {
			URI subject = new URI(triple.getSubject().toString());
			URI predicate = new URI(triple.getPredicate().toString());
			RMapValue object = triple.getObject();
			Set <URI> sourceRdfTypes = new HashSet<URI>();
			Set <URI> targetRdfTypes = new HashSet<URI>();
			
			NodeType sourceNodeType = null;
			NodeType targetNodeType = null;

			//don't call database again if the subject has not changed
			if (lastSubject!=null && lastSubject.equals(subject)){
				sourceNodeType = lastSourceNodeType;
			}
			
			if (object instanceof RMapLiteral) {
				targetNodeType = NodeType.LITERAL;
			}
			if (predicate.toString().equals(RDF.TYPE.toString())) {
				targetNodeType = NodeType.TYPE;
			}
						
			if (sourceNodeType==null){
				sourceRdfTypes = getResourceRDFTypes(subject, contextUri);
				sourceNodeType = NodeType.getNodeType(sourceRdfTypes);
			}
			if (object instanceof RMapUri && targetNodeType==null){
				targetRdfTypes = getResourceRDFTypes(new URI(object.toString()), contextUri);		
				targetNodeType = NodeType.getNodeType(targetRdfTypes);			
			}
						
			
			graph.addEdge(subject.toString(), object.toString(), predicate.toString(), sourceNodeType, targetNodeType);
			lastSubject = subject;
			lastSourceNodeType = sourceNodeType;
		}
		return graph;
	}
	
	
	private Set<URI> getResourceRDFTypes(URI resourceUri, URI contextUri) throws Exception{
		if (contextUri==null){
			return getResourceRDFTypes(resourceUri);
		}
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		Set<URI> rdfTypes = rmapService.getResourceRdfTypes(resourceUri, contextUri);
		rmapService.closeConnection();
		return rdfTypes;
	}
	
	
	private Set<URI> getResourceRDFTypes(URI resource) throws Exception{
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		Set<URI> rdfTypes = new HashSet<URI>();
		
		Map <URI, Set<URI>> types = rmapService.getResourceRdfTypesAllContexts(resource, RMapStatus.ACTIVE);
		
		if (types!=null){
			for (Map.Entry<URI, Set<URI>> type : types.entrySet()){
				Set<URI> contexttypes = type.getValue();
				for (URI contexttype : contexttypes) {
					if (contexttype!=null) {
						rdfTypes.add(contexttype);
					}
				}
			}
		}		
		rmapService.closeConnection();
		return rdfTypes;
	}
	
	

	private Map<String, String> getEventResourcesAffected(RMapEvent event, RMapEventType eventType) throws Exception {

	    Map<String, String> resourcesAffected = new HashMap<String, String>();
	
	    if (eventType == RMapEventType.CREATION){
	    	RMapEventCreation creationEvent = (RMapEventCreation) event;
	    	List<RMapUri> uris = creationEvent.getCreatedObjectIds();
	    	for (RMapUri uri : uris){
	    		String type = WebappUtils.getRMapType(new URI(uri.toString()));
	    		resourcesAffected.put(uri.toString(), "Created " + type);
	    	}
	    }
	    else if (eventType == RMapEventType.DELETION)	{
	    	RMapEventDeletion deletionEvent = (RMapEventDeletion) event;
	    	List<RMapUri> uris = deletionEvent.getDeletedObjectIds();
	    	for (RMapUri uri : uris){
	    		String type = WebappUtils.getRMapType(new URI(uri.toString()));
	    		resourcesAffected.put(uri.toString(), "Deleted " + type);
	    	}
	    }
	    else if (eventType == RMapEventType.TOMBSTONE)	{
	    	RMapEventTombstone tombstoneEvent = (RMapEventTombstone) event;
	    	RMapUri uri = tombstoneEvent.getTombstonedResourceId();
			String type = WebappUtils.getRMapType(new URI(uri.toString()));
			resourcesAffected.put(uri.toString(), "Tombstoned " + type);
	    }
	    else if (eventType == RMapEventType.DERIVATION)	{
	    	RMapEventDerivation derivationEvent = (RMapEventDerivation) event;
	    	List<RMapUri> createdUris = derivationEvent.getCreatedObjectIds();
	    	for (RMapUri uri : createdUris){
	    		String type = WebappUtils.getRMapType(new URI(uri.toString()));
	    		resourcesAffected.put(uri.toString(), "Created " + type);
	    	}
	    	RMapUri derivedUri = derivationEvent.getDerivedObjectId();
			String type = WebappUtils.getRMapType(new URI(derivedUri.toString()));
			resourcesAffected.put(derivedUri.toString(), "Derived " + type);	    	
	    }
	    else if (eventType == RMapEventType.UPDATE)	{
	    	RMapEventUpdate updateEvent = (RMapEventUpdate) event;	   
	    	List<RMapUri> createdUris = updateEvent.getCreatedObjectIds();
	    	for (RMapUri uri : createdUris){
	    		String type = WebappUtils.getRMapType(new URI(uri.toString()));
	    		resourcesAffected.put(uri.toString(), "Created " + type);
	    	}
	    	RMapUri derivedUri = updateEvent.getDerivedObjectId();
			String type = WebappUtils.getRMapType(new URI(derivedUri.toString()));
			resourcesAffected.put(derivedUri.toString(), "Derived " + type);	
			
	    	RMapUri inactivatedUri = updateEvent.getInactivatedObjectId();
	    	type = WebappUtils.getRMapType(new URI(inactivatedUri.toString()));
			resourcesAffected.put(inactivatedUri.toString(), "Inactivated " + type);	  	    			    	
	    }
	    else if (eventType == RMapEventType.INACTIVATION)	{
	    	RMapEventInactivation inactivateEvent = (RMapEventInactivation) event;	    
	    	RMapUri uri = inactivateEvent.getInactivatedObjectId();
			String type = WebappUtils.getRMapType(new URI(uri.toString()));
			resourcesAffected.put(uri.toString(), "Inactivated " + type);	  
	    }
	    else if (eventType == RMapEventType.REPLACE)	{
	    	RMapEventUpdateWithReplace replaceEvent = (RMapEventUpdateWithReplace) event;	    
	    	RMapUri uri = replaceEvent.getUpdatedObjectId();
			String type = WebappUtils.getRMapType(new URI(uri.toString()));
			resourcesAffected.put(uri.toString(), "Replaced " + type);	  
	    }
	    
	    return resourcesAffected;
	}
	
	
		
}
