<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="pageTitle" value="Settings | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/headstart.inc" %>  
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 

<h1>Settings</h1>

<br/>
<form:form method="POST" modelAttribute="user">
	<fieldset>
	<legend>New user details</legend>
	<form:label path="name">Name *</form:label> 
	<form:errors path="name" cssClass="validationErrors"/>
	<form:input path="name" style="width:500px;"/>
		
	<form:label path="email">Email *</form:label> 
	<form:errors path="email" cssClass="validationErrors"/>
	<form:input path="email" style="width:500px;"/>
	<br/><br/>		
	</fieldset>
	<div id="formButtons">
		<a href="/user/settingscancel">Cancel</a>&nbsp;&nbsp;
		<input type="submit" value="Save"/>
	</div>	
</form:form>
<br/>
<br/>

<%@include file="/includes/footer.inc" %>