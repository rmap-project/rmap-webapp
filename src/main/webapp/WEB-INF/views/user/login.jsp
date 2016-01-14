<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="pageTitle" value="Login | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/headstart.inc" %>      
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 
<h1>Sign in</h1>
<br/>
<form:form method="POST" modelAttribute="user">
	<fieldset>
	<legend>Enter a valid User ID</legend>
	<form:label path="userId">User ID *</form:label> 
	<form:errors path="userId" cssClass="validationErrors"/>
	<form:input path="userId" style="width:500px;"/>
	</fieldset>
	<div id="formButtons">
		<a href="<c:url value='/user/logincancel' />">Cancel</a>&nbsp;&nbsp;
		<input type="submit" value="Login"/>
	</div>	
</form:form>



<br/>
<br/>

<%@include file="/includes/footer.inc" %>
