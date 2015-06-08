<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="RMap DiSCO Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/header.inc" %>
<%@include file="/includes/js/nodesedges.js" %>        
                        
        
<article class="twelve columns main-content">
	<h1>RMap DiSCO Summary</h1>
	<h2>About: <em>${DISCO_URI}</em></h2>
		
	<c:if test="${DISCO_CREATOR.toString().length()>0}">
		<h3>Created by ${DISCO_CREATOR.toString()}</h3>
	</c:if>
	
	<c:if test="${DISCO_DESCRIPTION.toString().length()>0}">
		<em>${DISCO_DESCRIPTION.toString()}</em>
	</c:if>
	<div id="cy"></div>
	<a href="discos?uri=${DISCO_URI}&visualize=1">View larger visualization</a>
	<br/><br/>
	<h3>Aggregated Resources</h3>
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
			<h3>About: <em>${resource_descrip.getResourceName()}</em></h3>
			<c:if test="${resource_types.size()>0}">
				<h4>
					A resource of type
					<c:if test="${resource_types.size()>1}">
						s
					</c:if>
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
						<tr><td colspan="2"><em>No additional statements about this resource.</em></td></tr>
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
		<h1>&nbsp;</h1>
		<h2>Related Event Links</h2>
		<c:forEach var="event" items="${DISCO_EVENTS}">
			<p><a href="events?uri=${event.toString()}">${event.toString()}</a></p>
		</c:forEach>
	</div>
     
</aside>
<!-- End Right Sidebar -->


<%@include file="/includes/footer.inc" %>