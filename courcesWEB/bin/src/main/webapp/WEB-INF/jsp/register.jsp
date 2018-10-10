<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource" />
<html>
<head>
<title><fmt:message key="title" /></title>
<%@ include file="../jspf/head.jspf"%>
</head>
<body class="body">
	<div class="container mt-60">
		<%@ include file="../jspf/header.jspf"%>
		<c:if test="${not empty error}">
			<div id="myModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title"><fmt:message key="modal.msg" /></h4>
						</div>
						<div class="modal-body">
							<fmt:message key="msg${error}"/>
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message key="modal.close" /></button>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row main-form">
			<form class="" method="post" action="http://localhost:8080/auth/register">
				<div class="form-group">
					<label for="name" class="cols-sm-2 control-label"><fmt:message key="input.name" /></label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span> <input type="text" class="form-control" name="name"
								id="name" placeholder="<fmt:message key="input.setname" />" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="surname" class="cols-sm-2 control-label"><fmt:message key="input.surname" /></label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span> <input type="text" class="form-control" name="surname"
								id="surname" placeholder="<fmt:message key="input.setsurname" />" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="email" class="cols-sm-2 control-label"><fmt:message key="input.mail" /></label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span> <input type="email" class="form-control"
								name="email" id="email" placeholder="<fmt:message key="input.setmail" />" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="username" class="cols-sm-2 control-label"><fmt:message key="input.login" /></label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span> <input type="text" class="form-control"
								name="username" id="username" placeholder="<fmt:message key="input.setlogin" />" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="password" class="cols-sm-2 control-label"><fmt:message key="input.password" /></label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
								name="password" id="password" placeholder="<fmt:message key="input.setpassword" />" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="confirm" class="cols-sm-2 control-label"><fmt:message key="profile.repPass" /></label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
								name="confirm" id="confirm" placeholder="<fmt:message key="profile.repPass" />" required />
						</div>
					</div>
				</div>

				<div class="form-group ">
					<button type="submit" id="button" class="btn btn-primary btn-lg btn-block login-button"><fmt:message key="button.register" /></button>
				</div>

			</form>
		</div>
	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>