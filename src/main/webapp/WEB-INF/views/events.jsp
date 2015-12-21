<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="RMap Event Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>       
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 

<h1>RMap Event Summary</h1>
<h2>About: <a href="events?uri=${EVENT.getUri()}">${EVENT.getUri()}</a></h2>

<h3><em>Event initiated by <a href="resources?uri=${EVENT.getAssociatedAgent()}">${EVENT.getAssociatedAgent()}</a></em></h3>
<div class="CSSTableGenerator">
<table>
	<tr>
		<td colspan="2">Event details</td>
	</tr>
	<tr>
		<td>Start time</td><td>${EVENT.getStartTime().toString()}</td>
	</tr>
	<tr>
		<td>End time</td><td>${EVENT.getEndTime().toString()}</td>
	</tr>
	<tr>
		<td>Event target type</td><td>${EVENT.getTargetType()}</td>
	</tr>
	<tr>
		<td>Event type</td><td>${EVENT.getType()}</td>
	</tr>		
</table>

<c:if test="${EVENT.getDescription().length()>0}">
	<em>${EVENT.getDescription()}</em>
</c:if>
<br/>
<h2>Affected Resources</h2>
	<table>
		<tr>
			<td>Affected Resource</td>
			<td>Action</td>
		</tr>
		<c:forEach var="affected_resource" items="${EVENT.getResourcesAffected()}">
			<tr>
				<td><a href="resources?uri=${affected_resource.getKey()}&objpage=1">${affected_resource.getKey()}</a></td>
				<td>${affected_resource.getValue()}</td>
			</tr>
		</c:forEach>
		<c:if test="${EVENT.getResourcesAffected().size()==0}">
			<tr>
				<td colspan="2">No Resources were affected by this RMap Event</td>
			</tr>
		</c:if>
	</table>
</div>
	
	
<br/>

<%@include file="/includes/footer.inc" %>