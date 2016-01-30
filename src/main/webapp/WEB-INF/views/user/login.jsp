<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="pageTitle" value="Login | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/headstart.inc" %>      
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 
<h1>Login</h1>
<br/>
<fieldset>
<ul>
<li><a href="<c:url value='/user/login/google'/>">Login with Google</a></li>
<li><a href="<c:url value='/user/login/twitter'/>">Login with Twitter</a></li>
<li><a href="<c:url value='/user/login/orcid'/>">Login with ORCiD</a></li>
</ul>
</fieldset>
<br/>
<br/>

<%@include file="/includes/footer.inc" %>
