<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/tld/rmapTagLibrary.tld" %>
<c:set var="pageTitle" value="RMap Agent Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/headstart.inc" %>
<%@include file="/includes/js/nodesedges.js" %> 
</head>       
<body onload="drawgraph();">
<%@include file="/includes/bodystart.inc" %> 

<c:set var="agentUri" value="${AGENT.getUri().toString()}"/>      
<article class="twelve columns main-content">
	<h1>RMap Agent Summary</h1>
	<h2>URI: <a href="<c:url value='/agents/${my:httpEncode(agentUri)}'/>">${agentUri}</a></h2>

	<img src="<c:url value='/includes/images/graphlegend.png'/>" class="graphlegend" />
	<div id="visualWrapperSmall">
		<div id="mynetwork" class="cysmall"></div>
		<div id="loadbar" class="loadbarSmall">
			<div class="loadbarOuterBorder">
				<div id="loadbarText">0%</div>
				<div id="loadbarBorder">
					<div id="loadbarBar"></div>
				</div>
			</div>
		</div>
	</div>

	<a href="<c:url value='/agents/${my:httpEncode(agentUri)}?visualize=1'/>">View larger visualization</a> | 
	<div id="toggleLiterals" class="toggle" onclick="toggle('LITERAL');">Hide literals</div> | 
	<div id="toggleTypes" class="toggle" onclick="toggle('TYPE');">Hide types</div>
	<br/><br/>
	
	
	<h2>Agent details</h2>
	<div class="CSSTableGenerator">
		<table>
			<tr>
				<td colspan="2">Agent Details</td>
			</tr>		
					
		<c:if test="${AGENT.getName().length()>0}">
			<tr>
				<td>Name</td>
				<td>${AGENT.getName()}</td>
			</tr>
		</c:if>	
		<tr>
			<td>ID Provider</td>
			<td><a href="<c:url value='/resources/${my:httpEncode(AGENT.getIdProvider())}'/>">${AGENT.getIdProvider()}</a></td>
		</tr>	
		<tr>
			<td>User Authentication ID</td>
			<td><a href="<c:url value='/resources/${my:httpEncode(AGENT.getAuthId())}'/>">${AGENT.getAuthId()}</a></td>
		</tr>	
		
		</table>
	</div>

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
				<c:forEach var="discoId" items="${discos}" begin="0" end="49">
					<tr>
						<td>
							<a href="<c:url value='/discos/${my:httpEncodeUri(discoId)}'/>">${discoId}</a>
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
			<c:forEach var="eventId" items="${AGENT.getEvents()}" begin="0" end="19">
				<li><a href="<c:url value='/events/${my:httpEncodeUri(eventId)}'/>">${eventId}</a></li>
			</c:forEach>
		</ul>
	</div>
</aside>
<!-- End Right Sidebar -->


<%@include file="/includes/footer.inc" %>