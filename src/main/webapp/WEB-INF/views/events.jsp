<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="RMap Event Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/header.inc" %>

<h1>RMap Event Summary</h1>
<h2>About: <em>${EVENT_URI}</em></h2>

<h3>Event initiated by <!-- ${EVENT_AGENT} --> (not working right now) </h3>
<div class="CSSTableGenerator">
<table>
	<tr>
		<td colspan="2">Event details</td>
	</tr>
	<tr>
		<td>Start time</td><td>${EVENT_STARTTIME.toString()}</td>
	</tr>
	<tr>
		<td>End time</td><td>${EVENT_ENDTIME.toString()}</td>
	</tr>
	<tr>
		<td>Event target type</td><td>${EVENT_TARGETTYPE}</td>
	</tr>
	<tr>
		<td>Event type</td><td>${EVENT_TYPE}</td>
	</tr>		
</table>

<c:if test="${EVENT_DESCRIPTION.toString().length()>0}">
	<em>${EVENT_DESCRIPTION.toString()}</em>
</c:if>
<br/>
<h2>Affected Resources</h2>
	<table>
		<tr>
			<td>Affected Resource</td>
			<td>Action</td>
		</tr>
		<c:forEach var="affected_resource" items="${EVENT_RESAFFECTED}">
			<tr>
				<td><a href="resources?uri=${affected_resource.getKey()}">${affected_resource.getKey()}</a></td>
				<td>${affected_resource.getValue()}</td>
			</tr>
		</c:forEach>
	</table>
</div>
	
	
<br/>

<%@include file="/includes/footer.inc" %>