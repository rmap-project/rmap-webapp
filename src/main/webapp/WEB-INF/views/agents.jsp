<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="RMap Agent Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>
<%@include file="/includes/js/nodesedges.js" %> 
</head>       
<body onload="drawgraph();">
<%@include file="/includes/bodystart.inc" %>       
<article class="twelve columns main-content">
	<h1>RMap Agent Summary</h1>
	<h2>About: <a href="agents?uri=${AGENT.getUri()}">${AGENT.getUri()}</a></h2>
		
	<c:if test="${AGENT.getName().length()>0}">
		<h2>Name: <em>${AGENT.getName()}</em></h2>
	</c:if>
	<img src="includes/images/graphlegend.png" class="graphlegend" />
	<div id="wrapper">
		<div id="mynetwork" class="cysmall"></div>
		<div id="loadbar">
			<div class="outerBorder">
				<div id="loadbarText">0%</div>
				<div id="loadbarBorder">
					<div id="loadbarBar"></div>
				</div>
			</div>
		</div>
	</div>
	<a href="agents?uri=${AGENT.getUri()}&visualize=1">View larger visualization</a> | 
	<div id="toggleLiterals" class="toggle" onclick="toggle('LITERAL');">Hide literals</div> | 
	<div id="toggleTypes" class="toggle" onclick="toggle('TYPE');">Hide types</div>
	<br/><br/>
	
	<h2>ID Provider</h2>
	<p><a href="resources?uri=${AGENT.getIdProvider()}">${AGENT.getIdProvider()}</a></p>
	
	<h2>User Authentication ID</h2>
	<p><a href="resources?uri=${AGENT.getAuthId()}">${AGENT.getAuthId()}</a></p>
	
	
	<c:set var="discos" value="${AGENT.getDiscos()}"/>
	<c:set var="numdiscos" value="${AGENT.getNumDiscos()}"/>
	<c:if test="${numdiscos>50}">
		<h2>DiSCOs Created (Displaying 50 out of ${numdiscos})</h2>
	</c:if>
	<c:if test="${numdiscos<=50}">
		<h2>DiSCOs Created (${numdiscos})</h2>
	</c:if>
				
	<div class="CSSTableGenerator">
		<table>
			<tr>
				<td>DiSCO URI</td>
			</tr>		
			
			<c:if test="${numdiscos>0}">			
				<c:forEach var="disco" items="${discos}" begin="0" end="49">
					<tr>
						<td>
							<a href="discos?uri=${disco.toString()}">${disco.toString()}</a>
						</td>
					</tr>
				</c:forEach>	
			</c:if>
			<c:if test="${numdiscos==0}">
				<tr><td><em>No DiSCOs created by this RMap:Agent.</em></td></tr>
			</c:if>
		</table>
	</div>
	<br/>
	<br/>
</article>

<!-- End main Content -->
	    
<aside class="four columns right-sidebar">
     
	<div class="sidebar-widget">
		<div class="status${AGENT.getStatus()}"><h1>${AGENT.getStatus()}</h1></div>
		<h2>Agent Events</h2>
		<c:if test="${AGENT.getNumEvents()>20}">
			<em>(Displaying <strong>20</strong> out of <strong>${AGENT.getNumEvents()})</strong></em><br/><br/>
		</c:if>
		<ul>
			<c:forEach var="event" items="${AGENT.getEvents()}" begin="0" end="19">
				<li><a href="events?uri=${event.toString()}">${event.toString()}</a></li>
			</c:forEach>
		</ul>
	</div>
</aside>
<!-- End Right Sidebar -->


<%@include file="/includes/footer.inc" %>