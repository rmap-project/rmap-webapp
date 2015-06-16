<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="pageTitle" value="Home | RMap Project"/>
<c:set var="currPage" value="home"/>
<%@include file="/includes/header.inc" %>
<h1>Search RMap</h1>

<br/>
<form:form commandName="search" style="width:100%;display:inline; align:center;">
	<!--<form:errors path="search" cssClass="validationErrors"/>-->
	<form:label path="search">Enter URI</form:label> 
	<!--PROD --><form:input path="search" style="width:320px;" value="ark:/22573/rmd1s30hr"/>
	<!--TEST<form:input path="search" style="width:320px;" value="ark:/22573/rmd1s1kg2"/>-->
	<input type="submit" value="GO"/>
</form:form>
<br/>
<!--PROD-->
Other examples: <br/>
<a href="discos?uri=ark:/22573/rmdccm69">ark:/22573/rmdccm69</a><br/>
<a href="discos?uri=ark:/22573/rmdccm69">ark:/22573/rmd1s32cq</a><br/>
<a href="discos?uri=ark:/22573/rmdccm69">ark:/22573/rmd1s2mgx</a><br/> 
<a href="resources?uri=http://dx.doi.org/10.1109/InPar.2012.6339604">http://dx.doi.org/10.1109/InPar.2012.6339604</a>

<!--TEST
Another example: <a href="discos?uri=ark%3A%2F22573%2Frmd1s21t4">ark:/22573/rmd1s21t4</a>
-->
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<%@include file="/includes/footer.inc" %>
