<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="pageTitle" value="Home | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/headstart.inc" %>     
</head>
<body>
<%@include file="/includes/bodystart.inc" %> 
<h1>Search RMap</h1>

<br/>
<form:form commandName="search" style="width:100%;display:inline; align:center;">
	<!--<form:errors path="search" cssClass="validationErrors"/>-->
	<form:label path="search">Enter URI</form:label> 
	<form:input path="search" style="width:320px;" value="http://dx.doi.org/10.1109/InPar.2012.6339604"/>
	<input type="submit" value="GO"/>
</form:form>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<%@include file="/includes/footer.inc" %>
