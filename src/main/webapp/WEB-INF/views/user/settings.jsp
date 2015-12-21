<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<c:set var="pageTitle" value="Settings | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/headstart.inc" %>  
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 

<h1>Settings</h1>

<br/>
<script>
$(document).ready(function () {
    $("#adduri").click(function () {
        //Create a new select box
        $("#agentUriSet").append($("#userAgentUri:first").clone(true).attr({id: "userAgentUri"}));
		$("#agentUriSet").find("input:last").val("");
		$("#userAgentUri #remuri").css("visibility","visible");
		$("#userAgentUri:first #remuri").css("visibility","hidden");
    });
	$(document).on('click',"#remuri", function () {
		//remove an input box
		$(this).parent().remove();
    });
});
</script>

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
	<div id="agentUriSet" class="agentUriSet">
	<c:set var="buttonvisibility" value="hidden"/>
	<form:label path="userAgentUris[0]">Your URIs e.g. ORCID, ISNI, VIAF, or LOC (examples)</form:label> 
	<c:forEach items="${user.userAgentUris}" varStatus="i">
		<div id="userAgentUri" class="userAgentUri">
			<form:errors path="userAgentUris[${i.index}].uri" cssClass="validationErrors"/>
			<form:input path="userAgentUris[${i.index}].uri" style="width:500px;display:inline-block;"/>
			<a id="remuri" class="remFieldBtn" style="visibility:${buttonvisibility};">&nbsp;x</a>
		</div>
		<c:set var="buttonvisibility" value="visible"/>
	</c:forEach>		
	</div> 
	<a id="adduri" class="addFieldBtn">&nbsp;+ Add URI</a>
	</fieldset>
	<div id="formButtons">
		<a href="signupcancel">Cancel</a>&nbsp;&nbsp;
		<input type="submit" value="Save"/>
	</div>	
</form:form>
<br/>
<br/>

<%@include file="/includes/footer.inc" %>