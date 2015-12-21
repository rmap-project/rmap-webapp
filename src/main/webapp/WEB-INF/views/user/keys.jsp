<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<c:set var="pageTitle" value="Manage Keys | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/headstart.inc" %>  
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 

<h1>Manage API keys</h1>

<c:if test="${not user.hasRMapAgent}">
<p class="notice">
	IMPORTANT NOTE: Currently the keys you generate will provide read-only access to the RMap triplestore.  
	To create content in RMap, an RMap System Agent must be created for your account.  
	This will be associated with any content you create in RMap. 
	To trigger the creation of a System Agent, visit the <a href="settings">settings</a> page.
</p>
</c:if>
<p style="text-align:right;">
<a href="<c:url value='/user/key/new' />">Create new key</a>
</p>
<c:if test="${empty apiKeyList}">
<fieldset style="text-align:center;">
	<br/>No keys found.<br/><br/>
</fieldset>
</c:if>

<c:if test="${!empty apiKeyList}">
 <div class="CSSTableGenerator">	
 	<table>
	 	<tbody>
		    <tr>
		        <td>Key ID</td>
		        <td>Label</td>
		        <td>Status</td>
		        <td>Start date</td>
		        <td>End date</td>
		        <td>&nbsp;</td>
		    </tr>
		    <c:forEach items="${apiKeyList}" var="key">
		        <tr>
		            <td style="text-align:center;">${key.apiKeyId}</td>
		            <td>${key.label}</td>
		            <td>${key.keyStatus}</td>
		            <td>${key.startDate}</td>
		            <td>${key.endDate}</td>
		            <td style="text-align:center;"><a href="<c:url value='/user/key/download?keyid=${key.apiKeyId}'/>" target="_blank" >download</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		            <a href="<c:url value='/user/key/edit?keyid=${key.apiKeyId}' />" >edit</a></td>
		        </tr>
		    </c:forEach>
	    </tbody>
	</table>
</div>
</c:if>
<br/>

<br/>
<br/>

<%@include file="/includes/footer.inc" %>