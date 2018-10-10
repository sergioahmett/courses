<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource" />
<html>
<head>
<title><fmt:message key="title"/></title>
<%@ include file="../jspf/head.jspf"%>
</head>
<body class="body">
	<%@ include file="../jspf/header.jspf"%>
	<div class="container">
		<img src="http://localhost:8080/images/test_logo.png" class="logoclass">
		<div class="home-title-holder">
			<p class="home-title"><fmt:message key="index.title"/></p>
			<p class="home-title-descr">
				<fmt:message key="index.desc"/>
			</p>
		</div>
		<div class="hb-all-center home-button-holder">
			<a role="button" class="btn btn-default mr-5 " href="/courses"><fmt:message key="index.regToCourse"/></a>
		</div>
	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>