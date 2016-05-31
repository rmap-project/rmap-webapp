<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/rmapTagLibrary.tld" %>
<c:set var="pageTitle" value="RMap DiSCO Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>
<%@include file="/includes/js/nodesedges.js" %>        
</head>
<body onload="drawgraph();">
<%@include file="/includes/bodystart.inc" %>        
        
<article class="twelve columns main-content">

	<h1>RMap DiSCO Summary</h1>
	<h2>URI: <a href="<c:url value='/discos/${my:httpEncodeUri(DISCO.getUri())}'/>">${DISCO.getUri()}</a></h2>
		
	<c:if test="${DISCO.getCreator().length()>0}">
		<h3><em>Created by <a href="<c:url value='/resources/${my:httpEncode(DISCO.getCreator())}'/>">${DISCO.getCreator()}</a></em></h3>
	</c:if>
	<c:if test="${DISCO.getProvGeneratedBy().length()>0}">
		<h3><em>Generated by <a href="<c:url value='/resources/${my:httpEncode(DISCO.getProvGeneratedBy())}'/>">${DISCO.getProvGeneratedBy()}</a></em></h3>
	</c:if>
	<c:if test="${DISCO.getDescription().length()>0}">
		<p>${DISCO.getDescription()}</p>
	</c:if>
	
	<a href="<c:url value='/discos/${my:httpEncodeUri(DISCO.getUri())}/visual'/>">View larger visualization</a>&nbsp;|&nbsp;
	<a href="<c:url value='/resources/${my:httpEncodeUri(DISCO.getUri())}?resview=1'/>">View DiSCO as Resource</a><br/>
	<%@include file="/includes/standardViewGraph.inc" %>

	<br/><br/>
	<h2>Aggregated Resources</h2>
	<ul>
	<c:forEach var="agg_resource" items="${DISCO.getAggregatedResources()}">
		<li><a href="<c:url value='/resources/${my:httpEncodeUri(agg_resource)}'/>">${agg_resource}</a></li>
	</c:forEach>
	</ul>
	<br/>
	
	<h2>Additional Statements</h2>
	
	<c:forEach var="resource_descrip" items="${DISCO.getResourceDescriptions()}">
		<c:set var="properties" value="${resource_descrip.getPropertyValues()}"/>
		<c:set var="resource_types" value="${resource_descrip.getResourceTypes()}"/>
			
		<c:if test="${properties.size()>0||resource_types.size()>0}">
			<h3>
				About: <em><a href="<c:url value='/resources/${my:httpEncode(resource_descrip.getResourceName())}'/>">${resource_descrip.getResourceName()}</a></em>
			</h3>
			<c:if test="${resource_types.size()>0}">
				<h4>
					A resource of type<c:if test="${resource_types.size()>1}">s</c:if>
					:&nbsp;
					<em>
						<c:forEach var="resource_type" items="${resource_types}">
							<a href="${resource_type.getValue().getObjectLink()}">
								${resource_type.getValue().getObjectDisplay()}
							</a>;&nbsp;
						</c:forEach>
					</em>
				</h4>
			</c:if>
			<div class="CSSTableGenerator">		
			<table>
				<tr>
					<td>Property</td>
					<td>Value</td>
				</tr>
				<c:if test="${properties.size()>0}">
						<c:forEach var="property" items="${properties}">	
							<tr>
								<td><a href="${property.getValue().getPredicateLink()}">${property.getValue().getPredicateDisplay()}</a></td>
								<td>
									<c:set var="objectLink" value="${property.getValue().getObjectLink()}"/>
									<c:if test="${objectLink.length()>0}">
										<a href="<c:url value='${objectLink}'/>">
									</c:if>
									${property.getValue().getObjectDisplay()}
									<c:if test="${objectLink.length()>0}">
										</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>	
				</c:if>
			
				<c:if test="${properties.size()==0}">
					<tr><td colspan="2"><em>No additional properties for this Resource.</em></td></tr>
				</c:if>
				</table>
			</div>
		</c:if>
	</c:forEach>
	<br/>
	<br/>
</article>

<!-- End main Content -->
	    
<aside class="four columns right-sidebar">
     
	<div class="sidebar-widget">
		<div class="status${DISCO.getStatus().toString()}"><h1>${DISCO.getStatus().toString()}</h1></div>
		<h2>Related Events</h2>
		<ul>
			<c:forEach var="event" items="${DISCO.getEvents()}">
				<li><a href="<c:url value='/events/${my:httpEncodeUri(event)}'/>">${event.toString()}</a></li>
			</c:forEach>
		</ul>
		
		<c:set var="agentVersions" value="${DISCO.getAgentVersions()}"/>
		<h2>Other DiSCO Versions</h2>
		<h3>Same agent</h3>
		<c:if test="${agentVersions!=null && agentVersions.size()>0}">
			<ul>
			<c:forEach var="version" items="${agentVersions}">
				<li><a href="<c:url value='/discos/${my:httpEncodeUri(version)}'/>">${version.toString()}</a></li>
			</c:forEach>
			</ul>
		</c:if>
		<c:if test="${agentVersions==null || agentVersions.size()==0}">
			<p><em>None found</em></p>
		</c:if>

		<c:set var="otherAgentVersions" value="${DISCO.getOtherAgentVersions()}"/>
		<h3>Other agents</h3>
		<c:if test="${otherAgentVersions!=null && otherAgentVersions.size()>0}">
			<ul>
			<c:forEach var="version" items="${otherAgentVersions}">
				<li><a href="<c:url value='/discos/${my:httpEncodeUri(version)}'/>">${version.toString()}</a></li>
			</c:forEach>
			</ul>
		</c:if>
		<c:if test="${otherAgentVersions==null || otherAgentVersions.size()==0}">
			<p><em>None found</em></p>
		</c:if>
	</div>
</aside>
<!-- End Right Sidebar -->


<%@include file="/includes/footer.inc" %>