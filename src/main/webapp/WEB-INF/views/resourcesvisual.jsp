<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Visualization | RMap Resource Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>
<%@include file="/includes/js/nodesedges.js" %>        
</head>
<body onload="drawgraph();">
<%@include file="/includes/bodystart.inc" %>  

<h1>Resource Summary</h1>
<h2>About: <a href="resources?uri=${RESOURCE_URI}">${RESOURCE_URI}</a></h2>

<a href="resources?uri=${RESOURCE_URI}">Return to summary</a> | 
<div id="toggleLiterals" class="toggle" onclick="toggle('LITERAL');">Hide literals</div> | 
<div id="toggleTypes" class="toggle" onclick="toggle('TYPE');">Hide types</div>
<br/>
<img src="includes/images/graphlegend.png" class="graphlegend" />
<div id="mynetwork" class="cybig">
</div>
<br/><br/>

<%@include file="/includes/footer.inc" %>