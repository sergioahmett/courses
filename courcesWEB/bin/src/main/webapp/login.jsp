<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
	<%@ include file="WEB-INF/jspf/header.jspf"%>
	<div class="container mt-60">
		<c:if test="${not empty error}">
			<script type="text/javascript">
				PopUpShow();
			</script>
			<div id="myModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title">Ошибка регистрации</h4>
						</div>
						<div class="modal-body">
							<c:out value="${error}" />
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" type="button" data-dismiss="modal">Закрыть</button>
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
								id="log" placeholder="Введите имейл или логин" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
								name="password" id="password" placeholder="Введите пароль" required />
						</div>
					</div>
				</div>

				<div class="form-group ">
					<button type="submit" id="button" class="btn btn-primary btn-lg btn-block login-button">Войти</button>
				</div>

				<div class="form-group ">
					<a type="submit" target="_blank" id="button" href="http://localhost:8080/auth/register" class="btn btn-primary btn-lg btn-block login-button">Зарегистрироваться</a>
				</div>

			</form>
		</div>
	</div>
	<%@ include file="WEB-INF/jspf/footer.jspf"%>
</body>
</html>