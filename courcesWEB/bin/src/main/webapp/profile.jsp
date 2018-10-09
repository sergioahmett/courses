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
							<h4 class="modal-title">Ошибка</h4>
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
		<div class="lines">
			<div class="row">
				<div class="col-lg-3 col-lg-offset-1">
					<img src="/images/400x400.jpg" height="300" />
				</div>
				<div class="col-lg-offset-1 col-lg-6 bgcolor propertyWrapper">
					<p class="fs-25">
						ФИО: <em><c:out value="${user.fullName}" /></em>
					</p>
					<p class="fs-25">
						ЛОГИН: <em><c:out value="${user.login}" /></em>
					</p>
					<p class="fs-25">
						ИМЕЙЛ: <em><c:out value="${user.mail}" /></em>
					</p>
				</div>
			</div>
		</div>
		<div class="lines">
			<div class="row">
				<div class="col-lg-12 bgcolor" style="border-radius: 40px; padding: 10px; padding-left: 30px;">
					<h2>Привязать соцсеть:</h2>
					<c:if test="${empty user.social.facebookId}">
						<a href="#"><i class="fab fa-facebook fa-3x	mr-5"></i></a>
					</c:if>
					<c:if test="${empty user.social.twitterId}">
						<a href="#"><i class="fab fa-twitter fa-3x	mr-5"></i></a>
					</c:if>
					<c:if test="${empty user.social.googleId}">
						<a href="#"><i class="fab fa-google fa-3x	mr-5"></i></a>
					</c:if>
				</div>
			</div>
		</div>
		<div class="lines">
			<div class="row bgcolor" style="border-radius: 40px; padding: 10px; padding-left: 30px;">
				<form class="" method="post" action="http://localhost:8080/profile?command=pchange">
					<div class="col-lg-4">
						<h2>Изменить пароль:</h2>
						<div class="form-group">
							<label for="password" class="cols-sm-2 control-label">Текущий пароль</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
										name="currentPass" id="currentPass" placeholder="Введите пароль" required />
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="cols-sm-2 control-label">Новый пароль</label>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
											name="newPass" id="newPass" placeholder="Введите пароль" required />
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="cols-sm-2 control-label">Повторите новы пароль</label>
									<div class="cols-sm-10">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
												name="confirmPass" id="confirmPass" placeholder="Введите пароль" required />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<button type="submit" id="button" class="btn btn-primary btn-lg btn-block login-button">Сохранить</button>
					</div>
				</form>
			</div>
		</div>
		<div class="bottomLines">
			<div class="row col-lg-12 bgcolor tableWrapper">
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#panel1">Актуальные</a></li>
					<li><a data-toggle="tab" href="#panel2">Текущие</a></li>
					<li><a data-toggle="tab" href="#panel3">Архив</a></li>
				</ul>
				<div class="tab-content">
					<div id="panel1" class="tab-pane fade in active" style="margin-top: 20px;">
						<table id="example" class="display" style="width: 100%">
							<thead>
								<tr>
									<th>Название</th>
									<th>Тема</th>
									<th>Продолжительность</th>
									<th>Зарегистрированно</th>
									<th>Max студентов</th>
									<th>Учитель</th>
									<th>Журнал</th>
									<th>Подробнее</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="course" items="${user.actualCourseList}">
									<tr>
										<td><c:out value="${course.courseTitle}" /></td>
										<td><c:out value="${course.theamTitle}" default="Тема не задана" /></td>
										<td><c:out value="${course.duration}" /></td>
										<td><c:out value="${course.regStudent}" /></td>
										<td><c:out value="${course.maxStudent}" /></td>
										<td><c:out value="${course.teacherFullName}" default="Учитель не назначен" /></td>
										<td><a role="button" class="btn btn-default" href="/courses/${course.courseId}">Журнал</a></td>
										<td><a role="button" class="btn btn-default" href="/courses/${course.courseId}">Подробнее</a></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>Название</th>
									<th>Тема</th>
									<th>Продолжительность</th>
									<th>Зарегистрированно</th>
									<th>Max студентов</th>
									<th>Учитель</th>
									<th>Журнал</th>
									<th>Подробнее</th>
								</tr>
							</tfoot>
						</table>
					</div>
					<div id="panel2" class="tab-pane" style="margin-top: 20px;">
						<table id="example2" class="display" style="width: 100%">
							<thead>
								<tr>
									<th>Название</th>
									<th>Тема</th>
									<th>Продолжительность</th>
									<th>Зарегистрированно</th>
									<th>Max студентов</th>
									<th>Учитель</th>
									<th>Журнал</th>
									<th>Подробнее</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="course" items="${user.currentCourseList}">
									<tr>
										<td><c:out value="${course.courseTitle}" /></td>
										<td><c:out value="${course.theamTitle}" default="Тема не задана" /></td>
										<td><c:out value="${course.duration}" /></td>
										<td><c:out value="${course.regStudent}" /></td>
										<td><c:out value="${course.maxStudent}" /></td>
										<td><c:out value="${course.teacherFullName}" default="Учитель не назначен" /></td>
										<td><a role="button" class="btn btn-default" href="/courses/${course.courseId}">Журнал</a></td>
										<td><a role="button" class="btn btn-default" href="/courses/${course.courseId}">Подробнее</a></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>Название</th>
									<th>Тема</th>
									<th>Продолжительность</th>
									<th>Зарегистрированно</th>
									<th>Max студентов</th>
									<th>Учитель</th>
									<th>Журнал</th>
									<th>Подробнее</th>
								</tr>
							</tfoot>
						</table>
					</div>
					<div id="panel3" class="tab-pane" style="margin-top: 20px;">
						<table id="example3" class="display" style="width: 100%">
							<thead>
								<tr>
									<th>Название</th>
									<th>Тема</th>
									<th>Продолжительность</th>
									<th>Зарегистрированно</th>
									<th>Max студентов</th>
									<th>Учитель</th>
									<th>Журнал</th>
									<th>Подробнее</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="course" items="${user.archiveCourseList}">
									<tr>
										<td><c:out value="${course.courseTitle}" /></td>
										<td><c:out value="${course.theamTitle}" default="Тема не задана" /></td>
										<td><c:out value="${course.duration}" /></td>
										<td><c:out value="${course.regStudent}" /></td>
										<td><c:out value="${course.maxStudent}" /></td>
										<td><c:out value="${course.teacherFullName}" default="Учитель не назначен" /></td>
										<td><a role="button" class="btn btn-default" href="/courses/${course.courseId}">Журнал</a></td>
										<td><a role="button" class="btn btn-default" href="/courses/${course.courseId}">Подробнее</a></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>Название</th>
									<th>Тема</th>
									<th>Продолжительность</th>
									<th>Зарегистрированно</th>
									<th>Max студентов</th>
									<th>Учитель</th>
									<th>Журнал</th>
									<th>Подробнее</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="WEB-INF/jspf/footer.jspf"%>
</body>
</html>
