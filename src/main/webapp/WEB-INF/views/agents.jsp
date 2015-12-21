<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="RMap Agent Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>
<%@include file="/includes/js/nodesedges.js" %> 
</head>       
<body onload="drawgraph();">
<%@include file="/includes/bodystart.inc" %>       
<article class="twelve columns main-content">
	<h1>RMap Agent Summary</h1>
	<h2>About: <a href="agents?uri=${AGENT.getUri()}">${AGENT.getUri()}</a></h2>
		
	<c:if test="${AGENT.getCreator().length()>0}">
		<h3><em>Created by <a href="resources?uri=${AGENT.getCreator()}">${AGENT.getCreator()}</a></em></h3>
	</c:if>
	<img src="includes/images/graphlegend.png" class="graphlegend" />
	<div id="wrapper">
		<div id="mynetwork" class="cysmall"></div>
		<div id="loadbar">
			<div class="outerBorder">
				<div id="loadbarText">0%</div>
				<div id="loadbarBorder">
					<div id="loadbarBar"></div>
				</div>
			</div>
		</div>
	</div>
	<a href="agents?uri=${AGENT.getUri()}&visualize=1">View larger visualization</a> | 
	<div id="toggleLiterals" class="toggle" onclick="toggle('LITERAL');">Hide literals</div> | 
	<div id="toggleTypes" class="toggle" onclick="toggle('TYPE');">Hide types</div>
	<br/><br/>
	
	<h2>Agent represented</h2>
	<p><a href="resources?uri=${AGENT.getAgentRepresented()}">${AGENT.getAgentRepresented()}</a></p>
	
	<h2>Additional Properties</h2>
	
	<c:forEach var="resource_descrip" items="${AGENT.getResourceDescriptions()}">
		<c:set var="properties" value="${resource_descrip.getPropertyValues()}"/>
		<c:set var="resource_types" value="${resource_descrip.getResourceTypes()}"/>
			
		<c:if test="${properties.size()>0||resource_types.size()>0}">
			<h3>
				About: <em><a href="${resource_descrip.getResourceName()}">${resource_descrip.getResourceName()}</a></em>
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
			<c:if test="${properties.size()>0}">
				<div class="CSSTableGenerator">
					<table>
						<tr>
							<td>Property</td>
							<td>Value</td>
						</tr>
		
						<c:forEach var="property" items="${properties}">	
							<tr>
								<td><a href="${property.getValue().getPredicateLink()}">${property.getValue().getPredicateDisplay()}</a></td>
								<td>
									<c:set var="objectLink" value="${property.getValue().getObjectLink()}"/>
									<c:if test="${objectLink.length()>0}">
										<a href="${objectLink}">
									</c:if>
									${property.getValue().getObjectDisplay()}
									<c:if test="${objectLink.length()>0}">
										</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>	
					</table>
				</div>
			</c:if>
			
			<c:if test="${properties.size()==0}">
				<div class="CSSTableGenerator">
					<table>
						<tr>
							<td>Property</td>
							<td>Value</td>
						</tr>
						<tr><td colspan="2"><em>No additional properties for this RMap:Agent.</em></td></tr>
					</table>
				</div>
			</c:if>
		</c:if>
	</c:forEach>
	<br/>
	<br/>
</article>

<!-- End main Content -->
	    
<aside class="four columns right-sidebar">
     
	<div class="sidebar-widget">
		<div class="status${AGENT.getStatus()}"><h1>${AGENT.getStatus()}</h1></div>
		<h2>Agent Events</h2>
		<c:if test="${AGENT.getNumEvents()>20}">
			<em>(Displaying <strong>20</strong> out of <strong>${AGENT.getNumEvents()})</strong></em><br/><br/>
		</c:if>
		<ul>
			<c:forEach var="event" items="${AGENT.getEvents()}" begin="1" end="20">
				<li><a href="events?uri=${event.toString()}">${event.toString()}</a></li>
			</c:forEach>
		</ul>
	</div>
</aside>
<!-- End Right Sidebar -->


<%@include file="/includes/footer.inc" %>