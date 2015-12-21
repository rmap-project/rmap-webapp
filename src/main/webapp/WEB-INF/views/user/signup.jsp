<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<c:set var="pageTitle" value="Sign Up Form | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/headstart.inc" %>  
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 

<h1>Sign Up</h1>

<br/>
<script>
$(document).ready(function () {
    $("#addtype").click(function () {
        //Create a new select box
        $("#agentTypeSet").append($("#userAgentType:first").clone(true).attr({id: "userAgentType"}));	
		$("#userAgentType #remtype").css("visibility","visible");	
		$("#userAgentType:first #remtype").css("visibility","hidden");
    });
    $("#adduri").click(function () {
        //Create a new select box
        $("#agentUriSet").append($("#userAgentUri:first").clone(true).attr({id: "userAgentUri"}));
		$("#agentUriSet").find("input:last").val("");
		$("#userAgentUri #remuri").css("visibility","visible");
		$("#userAgentUri:first #remuri").css("visibility","hidden");
    });
	$(document).on('click',"#remtype", function () {
		//remove an input box
		$(this).parent().remove();
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
	<!-- 
	<div id="agentTypeSet" class="agentTypeSet">
		<form:label path="userAgentTypes">Agent Type</form:label> 
		<div id="userAgentType" class="userAgentType">
			<form:errors path="userAgentTypes" cssClass="validationErrors"/>-->
			<!--<form:input path="userAgentTypes" style="width:500px;display:inline-block;"/>-->
			<!--<form:select path="userAgentTypes" style="width:507px;display:inline-block;" multiple="false">
				<form:option value="" label="-----Select-----"/>
				<form:options items="${agentTypes}" itemValue="uri" itemLabel="label"/>
			</form:select>
			<a id="remtype" class="remFieldBtn" style="visibility:hidden;">&nbsp;x</a>
		</div>
	</div> 
	-->
	<!--<a id="addtype" class="addFieldBtn">&nbsp;+ Add type</a>
	<br/><br/>		
	<div id="agentUriSet" class="agentUriSet">
		<form:label path="userAgentUris">Your URIs e.g. ORCID, ISNI, VIAF, or LOC (examples)</form:label> 
		<c:if test="${not empty userAgentUris}">
			<c:forEach items="${userAgentUris}" var="userAgentUri" varStatus="loop">
				<div id="userAgentUri" class="userAgentUri">
					<form:errors path="userAgentUris[${loop.index}]" cssClass="validationErrors"/>
					<form:input path="userAgentUris[${loop.index}].uri" style="width:500px;display:inline-block;"/>
					<a id="remuri" class="remFieldBtn" style="visibility:hidden;">&nbsp;x</a>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${empty userAgentUris}">
			<div id="userAgentUri" class="userAgentUri">
				<form:errors path="userAgentUris" cssClass="validationErrors"/>
				<form:input path="userAgentUris" style="width:500px;display:inline-block;"/>
				<a id="remuri" class="remFieldBtn" style="visibility:hidden;">&nbsp;x</a>
			</div>
		</c:if>
	</div>
	<a id="adduri" class="addFieldBtn">&nbsp;+ Add URI</a> -->
	</fieldset>
	<div id="formButtons">
		<a href="<c:url value='/user/signupcancel' />">Cancel</a>&nbsp;&nbsp;
		<input type="submit" value="Save"/>
	</div>	
</form:form>



<br/>
<br/>

<%@include file="/includes/footer.inc" %>