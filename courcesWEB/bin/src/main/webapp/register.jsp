<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
	<div class="container mt-60">
		<%@ include file="WEB-INF/jspf/header.jspf"%>
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
			<form class="" method="post" action="http://localhost:8080/auth/register">
				<div class="form-group">
					<label for="name" class="cols-sm-2 control-label">Имя</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span> <input type="text" class="form-control" name="name"
								id="name" placeholder="Введите имя" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="surname" class="cols-sm-2 control-label">Фамилия</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span> <input type="text" class="form-control" name="surname"
								id="surname" placeholder="Введите фамилию" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="email" class="cols-sm-2 control-label">Ваш имейл</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span> <input type="email" class="form-control"
								name="email" id="email" placeholder="Введите имейл" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="username" class="cols-sm-2 control-label">Логин</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span> <input type="text" class="form-control"
								name="username" id="username" placeholder="Введите ваш логин" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="password" class="cols-sm-2 control-label">Пароль</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
								name="password" id="password" placeholder="Введите пароль" required />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="confirm" class="cols-sm-2 control-label">Подтвердите пароль</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
								name="confirm" id="confirm" placeholder="Подтвердите пароль" required />
						</div>
					</div>
				</div>

				<div class="form-group ">
					<button type="submit" id="button" class="btn btn-primary btn-lg btn-block login-button">Зарегистрироваться</button>
				</div>

			</form>
		</div>
	</div>
	<%@ include file="WEB-INF/jspf/footer.jspf"%>
</body>
</html>