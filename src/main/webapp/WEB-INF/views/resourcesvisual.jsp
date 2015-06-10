<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Visualization | RMap Resource Summary | RMap Project"/>
<c:set var="currPage" value="search"/>
<%@include file="/includes/header.inc" %>
<%@include file="/includes/js/nodesedgesbig.js" %>        

<h1>Resource Summary</h1>
<h2>About: <a href="resources?uri=${RESOURCE_URI}">${RESOURCE_URI}</a></h2>
<a href="resources?uri=${RESOURCE_URI}">Return to summary</a>
<br/>

<div id="cybig"></div>


<%@include file="/includes/footer.inc" %>