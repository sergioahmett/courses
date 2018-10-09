<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource" />

<html>
<head>
<title><fmt:message key="title" /></title>
<%@ include file="../jspf/head.jspf"%>
</head>
<body class="body">
	<%@ include file="../jspf/header.jspf"%>
	<div class="container mt-60">
		<c:if test="${not empty error}">
			<div id="myModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title">
								<fmt:message key="modal.msg" />
							</h4>
						</div>
						<div class="modal-body">
							<fmt:message key="msg${error}" />
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" type="button" data-dismiss="modal">
								<fmt:message key="modal.close" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row main-form">
			<form class="" method="post" action="http://localhost:8080/auth/login">

				<div class="form-group">
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span> <input type="text" class="form-control" name="log"
								id="log" placeholder="<fmt:message key="input.mailOrLogin" />" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
								name="password" id="password" placeholder="<fmt:message key="input.password" />" required />
						</div>
					</div>
				</div>

				<div class="form-group ">
					<button type="submit" id="button" class="btn btn-primary btn-lg btn-block login-button">
						<fmt:message key="header.login" />
					</button>
				</div>

				<div class="form-group ">
					<a type="submit" target="_blank" id="button" href="http://localhost:8080/auth/register" class="btn btn-primary btn-lg btn-block login-button"><fmt:message
							key="header.register" /></a>
				</div>

			</form>
		</div>
	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>