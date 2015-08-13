<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Visualization | RMap DiSCO Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>
<%@include file="/includes/js/nodesedges.js" %>        
</head>
<body onload="drawgraph();">
<%@include file="/includes/bodystart.inc" %>  

<h1>RMap DiSCO Summary</h1>
<h2>About: <a href="discos?uri=${DISCO_URI}">${DISCO_URI}</a></h2>
<a href="resources?uri=${DISCO_URI}">Return to summary</a> | 
<div id="toggleLiterals" class="toggle" onclick="toggle('LITERAL');">Hide literals</div> | 
<div id="toggleTypes" class="toggle" onclick="toggle('TYPE');">Hide types</div>
<br/>
<img src="includes/images/graphlegend.png" class="graphlegend" />
<div id="mynetwork" class="cybig"></div>
<br/><br/>
<%@include file="/includes/footer.inc" %>