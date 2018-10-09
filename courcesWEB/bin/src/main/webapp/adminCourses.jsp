<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
	<div class="container-fluid">
		<%@ include file="WEB-INF/jspf/header.jspf"%>
		<c:if test="${not empty msg}">
			<div id="myModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title">Ошибка</h4>
						</div>
						<div class="modal-body">
							<c:out value="${msg}" />
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" type="button" data-dismiss="modal">Закрыть</button>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<div class="lines mt-60">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 bgcolo">
					<a role="button" class="btn btn-default cb-all-center" href="/admin/users">Пользователи</a><br> <a role="button"
						class="btn btn-default cb-all-center" href="/admin/teachers">Учителя</a><br> <a role="button" class="btn btn-default cb-all-center  active"
						href="/admin/courses">Курсы</a><br> <a role="button" class="btn btn-default cb-all-center" href="/admin/theams">Темы</a>
				</div>
			</div>
		</div>
		<div class="lines">
			<div class="row">
				<a href="/admin/courses/add?command=create" class="btn btn-default col-lg-10 col-lg-offset-1" type="button">Добавить курс</a>
			</div>
		</div>
		<div class="bottomLines">
			<div class="row">
				<div class="col-lg-12 bgcolor tableWrapper">
					<table id="example" class="display" style="width: 100%">
						<thead>
							<tr>
								<th>Название</th>
								<th>Тема</th>
								<th>Продолжительность</th>
								<th>Зарегистрированно</th>
								<th>Max студентов</th>
								<th>Учитель</th>
								<th>Изменить</th>
								<th>Удалить</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="course" items="${resultList}">
								<tr>

									<td><c:out value="${course.courseTitle}" /></td>
									<td><c:out value="${course.theamTitle}" default="Тема не задана" /></td>
									<td><c:out value="${course.duration}" /></td>
									<td><c:out value="${course.regStudent}" /></td>
									<td><c:out value="${course.maxStudent}" /></td>
									<td><c:out value="${course.teacherFullName}" default="Учитель не назначен" /></td>
									<td><a role="button" class="btn btn-default" href="/admin/courses/add?command=change&courseId=${course.courseId}">Изменить</a></td>
									<td><form method="post" action="/admin/courses/add?command=delete&courseId=${course.courseId}">
											<button role="button" type="submit" class="btn btn-default">Удалить</button>
										</form></td>
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
								<th>Изменить</th>
								<th>Удалить</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>