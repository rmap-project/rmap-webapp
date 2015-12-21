<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Welcome | RMap Project"/>
<c:set var="currPage" value="welcome"/>
<%@include file="/includes/headstart.inc" %>  
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 
<h1>Welcome to the RMap Project</h1>
<h2>What can I do here?</h2>
<h3>Create API keys</h3>
<p>By signing in you can <a href="<c:url value='/user/keys' />">generate API keys</a> for readonly access to the RMap API right away.  
In order to write to the RMap database, a System Agent is required.</p>
<h3>Create your RMap System Agent</h3>
<p>Creating an RMap System Agent gives you write access to RMap through the API.  
This involves pushing some information about your identity into RMap so that it can be associated with data that you create.  
You can start this process on the <a href="<c:url value='/user/settings' />">Settings page</a>.</p>
<h3>View API activity</h3>
<p>You can <a href="<c:url value='/user/key/reports' />">generate activity reports</a> showing what objects have been created or updated using your API keys.</p>
<br/>
<br/>

<%@include file="/includes/footer.inc" %>
