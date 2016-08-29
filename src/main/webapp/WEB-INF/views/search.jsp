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
<c:if test="${notice!=null}">
	<p class="notice">
		${notice}
	</p>
</c:if>

<c:if test="${search.getSearch()==null||search.getSearch().length()==0}">
	<c:set var="searchVal" value="http://dx.doi.org/10.1109/InPar.2012.6339604"/>
</c:if>
<c:if test="${search.getSearch()!=null&&search.getSearch().length()>0}">
	<c:set var="searchVal" value="${search.getSearch()}"/>
</c:if>
<form:form commandName="search" style="width:100%;display:inline; align:center;">
	<form:label path="search">Enter a URI *</form:label> 
	<form:input path="search" style="width:320px;" value="${searchVal}"/>
	<input type="submit" value="GO"/>
</form:form>
<br/>
<br/>
<p style="font-style: italic;">* A URI is a Uniform Resource Identifier. URIs typically contain a colon to separate the prefix from an identifier. 
Examples of valid URIs include URLs, ARK IDs, DOIs (all formats), and ORCID IDs.</p>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<%@include file="/includes/footer.inc" %>
