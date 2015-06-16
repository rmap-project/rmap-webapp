<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="RMap DiSCO Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/header.inc" %>
<%@include file="/includes/js/nodesedges.js" %>        
                        
        
<article class="twelve columns main-content">

	<h1>RMap DiSCO Summary</h1>
	<h2>About: <a href="discos?uri=${DISCO_URI}">${DISCO_URI}</a></h2>
		
	<c:if test="${DISCO_CREATOR.length()>0}">
		<h3><em>Created by <a href="resources?uri=${DISCO_CREATOR.toString()}">${DISCO_CREATOR.toString()}</a></em></h3>
	</c:if>
	<c:if test="${DISCO_DESCRIPTION.length()>0}">
		<p>${DISCO_DESCRIPTION.toString()}</p>
	</c:if>

	<div id="cy" class="cysmall"></div>
	<a href="discos?uri=${DISCO_URI}&visualize=1">View larger visualization</a> | 
	<div id="toggle" class="literaltoggle" onclick="toggleLiterals();">Hide literals</div>
	<br/><br/>
	<h2>Aggregated Resources</h2>
	<ul>
	<c:forEach var="agg_resource" items="${RESOURCE_LIST}">
		<li><a href="resources?uri=${agg_resource}">${agg_resource}</a></li>
	</c:forEach>
	</ul>
	<br/>
	
	<h2>Additional Statements</h2>
	
	<c:forEach var="resource_descrip" items="${DISCO_RESOURCE_DESCRIP}">
		<c:set var="properties" value="${resource_descrip.getPropertyValues()}"/>
		<c:set var="resource_types" value="${resource_descrip.getResourceTypes()}"/>
			
		<c:if test="${properties.size()>0||resource_types.size()>0}">
			<h3>
				About: <em><a href="resources?uri=${resource_descrip.getResourceName()}">${resource_descrip.getResourceName()}</a></em>
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
										<a href="${objectLink}">
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
		<div class="status${DISCO_STATUS.toString()}"><h1>${DISCO_STATUS.toString()}</h1></div>
		<h2>Related Events</h2>
		<ul>
			<c:forEach var="event" items="${DISCO_EVENTS}">
				<li><a href="events?uri=${event.toString()}">${event.toString()}</a></li>
			</c:forEach>
		</ul>

		<h2>Other DiSCO Versions</h2>
		<h3>Same agent</h3>
		<c:if test="${DISCO_AGENTVERSIONS!=null && DISCO_AGENTVERSIONS.size()>0}">
			<c:forEach var="version" items="${DISCO_AGENTVERSIONS}">
				<p><a href="discos?uri=${version.toString()}">${version.toString()}</a></p>
			</c:forEach>
		</c:if>
		<c:if test="${DISCO_AGENTVERSIONS==null || DISCO_AGENTVERSIONS.size()==0}">
			<p><em>None found</em></p>
		</c:if>

		<h3>Other agents</h3>
		<c:if test="${DISCO_OTHERVERSIONS!=null && DISCO_OTHERVERSIONS.size()>0}">
			<c:forEach var="version" items="${DISCO_OTHERVERSIONS}">
				<p><a href="discos?uri=${version.toString()}">${version.toString()}</a></p>
			</c:forEach>
		</c:if>
		<c:if test="${DISCO_OTHERVERSIONS==null || DISCO_OTHERVERSIONS.size()==0}">
			<p><em>None found</em></p>
		</c:if>
	</div>
</aside>
<!-- End Right Sidebar -->


<%@include file="/includes/footer.inc" %>