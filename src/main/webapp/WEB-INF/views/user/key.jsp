<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="pageTitle" value="API Key Form | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/headstart.inc" %>  

<link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script> 
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 

<script>
$(document).ready(function() {
	$("#startDate").datepicker();
	});
	
$(document).ready(function() {
	$("#endDate").datepicker();
	});
</script>

<c:if test="${targetPage=='keyedit'}">
	<h1>Edit API key (${apiKey.apiKeyId})</h1>
</c:if>
<c:if test="${targetPage=='keynew'}">
	<h1>New API key</h1>
</c:if>

<br/>

<form:form method="POST" modelAttribute="apiKey">
	<fieldset>
		<form:hidden path="apiKeyId"/>
		
		<legend>Key details</legend>
		<form:label path="label">Key label *</form:label> 
		<form:errors path="label" cssClass="validationErrors"/>
		<form:input path="label" />
			
		<form:label path="note">Key description</form:label> 
		<form:errors path="note" cssClass="validationErrors"/>
		<form:textarea path="note" rows="4"/>
		
		<form:label path="keyStatus">Status</form:label> 
		<form:errors path="keyStatus" cssClass="validationErrors"/>
		<form:select path="keyStatus" multiple="false">
			<form:option value="" label="-----Select-----"/>
			<form:options values="${keyStatuses}" items="${keyStatuses}"/>
		</form:select>			
		
		<form:label path="startDate">Start date (leave blank for no fixed start date)</form:label> 
		<form:errors path="startDate" cssClass="validationErrors"/>
		<form:input path="startDate" cssClass="dateInput" readonly="true"/>
		
		<form:label path="endDate">End date (leave blank for no fixed end date)</form:label> 
		<form:errors path="endDate" cssClass="validationErrors"/>
		<form:input path="endDate" cssClass="dateInput" readonly="true"/>
	</fieldset>
	<div id="formButtons">
		<a href="<c:url value='/user/keys' />">Cancel</a>&nbsp;&nbsp;
		<input type="submit" value="Save"/>
	</div>	
</form:form>
<br/>
<br/>

<%@include file="/includes/footer.inc" %>