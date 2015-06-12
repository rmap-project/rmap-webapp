<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="RMap Resource Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/header.inc" %>
<%@include file="/includes/js/nodesedges.js" %>

<h1>Resource Summary</h1>
<h2>About: <a href="resources?uri=${RESOURCE_URI}">${RESOURCE_URI}</a></h2>

<c:set var="resource_descrip" value="${RESOURCE_DESCRIP}"/>
<c:set var="properties" value="${resource_descrip.getPropertyValues()}"/>
<c:set var="resource_types" value="${resource_descrip.getResourceTypes()}"/>

<c:if test="${resource_types.size()>0}">
	<h3>
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
	</h3>
</c:if>
<c:if test="${properties.size()>0 || resource_types.size()>0}">
	<div id="cy" class="cysmall"></div>
	<a href="resources?uri=${RESOURCE_URI}&visualize=1">View larger visualization</a> | 
	<div id="toggle" class="literaltoggle" onclick="toggleLiterals();">Hide literals</div>
</c:if>
<br/>
	<div class="CSSTableGenerator">
		<table>
			<tr>
				<td>Resource ID</td>
				<td>Property</td>
				<td>Value</td>
			</tr>
			<c:if test="${properties.size()==0}">
				<tr>
					<td colspan="3">No assertions found for this resource</td>
				</tr>
			</c:if>
			
			<c:if test="${properties.size()>0}">
				
				<c:forEach var="property" items="${properties}">	
					<tr>
						<td>
							<c:set var="subjectLink" value="${property.getValue().getSubjectLink()}"/>
							<c:if test="${subjectLink.length()>0}">
								<a href="${subjectLink}">
							</c:if>
							${property.getValue().getSubjectDisplay()}
							<c:if test="${subjectLink.length()>0}">
								</a>
							</c:if>
						</td>
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
		
		
	</table>
</div>
<br/><br/>

<%@include file="/includes/footer.inc" %>