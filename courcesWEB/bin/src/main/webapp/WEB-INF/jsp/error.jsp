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
			<p class="home-title"><fmt:message key="error.oops"/></p>
			<c:if test="${empty code}">
				<p class="home-title-descr"><fmt:message key="error.404"/></p>
			</c:if>
			<c:if test="${not empty code}">
				<p class="home-title-descr"><fmt:message key="msg${code}"/></p>
			</c:if>
		</div>
	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>