<!-- Primary Page Layout
================================================== -->


<c:if test="${user!=null}">
	<c:set var="uid" value="${user.getUserId()}"/>
</c:if>
<c:if test="${user==null||uid==null}">
	<c:set var="uid" value="0"/>
</c:if>
<c:if test="${user.getName()==null||user.getName().length()==0}">
	<c:set var="uname" value=""/>
</c:if>
<c:if test="${user.getName()!=null&&user.getName().length()>0}">
	<c:set var="uname" value="${user.getName()}"/>
</c:if>


<header id="header" class="site-header" role="banner">
	<div id="header-inner" class="container sixteen columns over">
	<hgroup class="one-third column alpha">
		<h1 id="site-title" class="site-title">
			<a href="<c:url value='/home'/>" id="logo"><img src="<c:url value='/includes/images/rmap_logo_small.png'/>" alt="RMap logo" height="80" width="160" /></a>
		</h1>
	</hgroup>
	<nav id="main-nav" class="two thirds column omega">
		<ul id="main-nav-menu" class="nav-menu">
			<li id="menu-item-1">
				<a href="<c:url value='/home'/>">Home</a>
			</li>
			<li id="menu-item-2">
				<a href="<c:url value='/search'/>">Search</a>
			</li>
			<li id="menu-item-3">
				<a href="<c:url value='/contact'/>">Contact</a>
			</li>
			
			<c:if test="${uid>0}">
			<li id="menu-item-4">
				<a href="<c:url value='/user/welcome'/>" alt="${uname}"><div id="truncate">${uname}</div></a>
				<ul class="dropdown-menu">
					<li><a href="<c:url value='/user/keys'/>">Manage API keys</a></li>
					<li><a href="<c:url value='/user/reports'/>">View API activity</a></li>
					<li><a href="<c:url value='/user/settings'/>">Settings</a></li>
					<li><a href="<c:url value='/user/logout'/>">Logout</a></li>
				</ul>
			</li>
			</c:if>
			
			<c:if test="${uid==0&&uname.length()>0}">
			<li id="menu-item-4">
				<a href="<c:url value='/user/settings'/>" alt="${uname}"><div id="truncate">${uname}</div></a>
				<ul class="dropdown-menu">
					<li><a href="<c:url value='/user/logout'/>">Logout</a></li>
				</ul>
			</li>			
			</c:if>
			
			<c:if test="${uid==0&&uname.length()==0}">
			<li id="menu-item-4">
				<a href="<c:url value='/user/login'/>">Login</a>
				<ul class="dropdown-menu">
					<li><a href="<c:url value='/user/login/google'/>">Log in with Google</a></li>
					<li><a href="<c:url value='/user/login/twitter'/>">Log in with Twitter</a></li>
					<li><a href="<c:url value='/user/login/orcid'/>">Log in with ORCID</a></li>
				</ul>
			</li>			
			</c:if>
		</ul>
	</nav>
</div>
</header>

<div class="container">


