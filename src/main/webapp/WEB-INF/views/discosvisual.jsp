<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/rmapTagLibrary.tld" %>
<c:set var="pageTitle" value="Visualization | RMap DiSCO | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>
<%@include file="/includes/js/nodesedges.js" %>        
</head>
<body onload="drawgraph();">
<div class="largecontainer">
	<div style="float:left; padding-top:10px; width:200px;">
		<a href="<c:url value='/home'/>" id="logo">
		<img src="<c:url value='/includes/images/rmap_logo_small.png'/>" alt="RMap logo" height="80" width="160" />
		</a>
	</div>
	<div style="padding-top:15px;">
		<h1>RMap DiSCO</h1>
		<h2>${DISCO.getUri()}</h2>
	</div>
	<a href="<c:url value='/discos/${my:httpEncodeUri(DISCO.getUri())}'/>">Return to summary</a> | 
	<div id="toggleLiterals" class="toggle" onclick="toggle('LITERAL');">Hide literals</div> | 
	<div id="toggleTypes" class="toggle" onclick="toggle('TYPE');">Hide types</div>
	<br/>
	<img src="<c:url value='/includes/images/graphlegend.png'/>" class="graphlegend" />
	<div id="visualWrapperBig">
	    <div id="mynetwork" class="cybig"></div>
	    <div id="loadbar" class="loadbarBig">
	        <div class="loadbarOuterBorder">
	            <div id="loadbarText">0%</div>
	            <div id="loadbarBorder">
	                <div id="loadbarBar"></div>
	            </div>
	        </div>
	    </div>
	</div>
</div>

</body>