<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="RMap Resource Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>    
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 

<article class="twelve columns main-content">
	<h1>RMap Statement Summary</h1>
<!-- 
No support for statements at the moment.
<h2>About: <a href="stmts?uri=${STMT_URI}">${STMT_URI}</a></h2>
	
	<div class="CSSTableGenerator">
		<table>
			<tr>
				<td>Property</td>
				<td>Value</td>
			</tr>
			<tr>
				<td>rdf:subject</td>
				<td>${STMT_SUBJ}</td>
			</tr>
			<tr>
				<td>rdf:predicate</td>
				<td>${STMT_PRED}</td>
			</tr>
			<tr>
				<td>rdf:object</td>
				<td>${STMT_OBJ}</td>
			</tr>
		</table>
	</div>
	
	<br/><br/>
</article>
 -->	
<!-- End main Content -->
<!--  
<aside class="four columns right-sidebar">
     
	<div class="sidebar-widget">
		<div class="status${STMT_STATUS.toString()}"><h1>${STMT_STATUS.toString()}</h1></div>
		<h2>Related Events</h2>
		<ul>
		<c:forEach var="event" items="${STMT_EVENTS}">
			<li><a href="events?uri=${event.toString()}">${event.toString()}</a></li>
		</c:forEach>
		</ul>
	</div>
</aside>
-->  
<!-- End Right Sidebar -->
	
<br/>

<%@include file="/includes/footer.inc" %>