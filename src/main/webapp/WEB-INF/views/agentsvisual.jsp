<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Visualization | RMap Agent Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>
<%@include file="/includes/js/nodesedges.js" %>    
</head>       
<body onload="drawgraph();">
<%@include file="/includes/bodystart.inc" %>  

<h1>RMap Agent Visualization</h1>
<h2>About: <a href="agents?uri=${AGENT.getUri()}">${AGENT.getUri()}</a></h2>
<a href="agents?uri=${AGENT.getUri()}">Return to summary</a> | 
<div id="toggleLiterals" class="toggle" onclick="toggle('LITERAL');">Hide literals</div> | 
<div id="toggleTypes" class="toggle" onclick="toggle('TYPE');">Hide types</div>
<br/>
<img src="includes/images/graphlegend.png" class="graphlegend" />
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
<br/><br/>

<%@include file="/includes/footer.inc" %>