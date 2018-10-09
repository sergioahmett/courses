<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
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
	<div class="container">
		<div class="lines mt-60">
			<div class="row">
				<h1 class="col-lg-4 col-lg-offset-4 bgcolor" style="border-radius: 20px">
					<c:if test="${teachers.size() > 1}">Наши преподователи</c:if>
					<c:if test="${teachers.size() == 1}">Наш преподователь</c:if>
				</h1>
			</div>
		</div>
		<c:forEach var="teacher" items="${teachers}">
			<div class="lines">
				<div class="row">
					<div class="col-lg-2 col-lg-offset-1">
						<img src="/images/400x400.jpg" height="300" />
					</div>
					<div class="col-lg-offset-2 col-lg-6 bgcolor propertyWrapper">
						<p class="fs-25">
							<em><c:out value="${teacher.fullName}" /></em>
						</p>
						<p class="fs-25">
							<em><c:out value="${teacher.description}" /></em>
						</p>
					</div>
				</div>
			</div>
			<div class="bottomLines">
				<div class="row">
					<button class="btn btn-default btn-lg col-lg-2 col-lg-offset-5" type="button" data-toggle="modal" data-target="#modal-${teacher.id}">Показать
						курсы</button>
				</div>
			</div>
			<div id="modal-${teacher.id}" class="modal fade">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title">Список курсов учителя</h4>
						</div>
						<div class="modal-body">
							<div class="lines">
								<div class="row">
									<div class="col-lg-10  col-lg-offset-1 modal-table-wrapper">
										<table id="teacher-${teacher.id}" class="display" style="width: 100%">
											<thead>
												<tr>
													<th>Название</th>
													<th>Тема</th>
													<th>Продолжительность</th>
													<th>Зарегистрированно</th>
													<th>Max студентов</th>
													<th>Учитель</th>
													<th>Подробнее</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="course" items="${teacher.courseList}">
													<tr>
														<td><c:out value="${course.courseTitle}" /></td>
														<td><c:out value="${course.theamTitle}" default="Тема не задана" /></td>
														<td><c:out value="${course.duration}" /></td>
														<td><c:out value="${course.regStudent}" /></td>
														<td><c:out value="${course.maxStudent}" /></td>
														<td><c:out value="${course.teacherFullName}" default="Учитель не назначен" /></td>
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
													<th>Подробнее</th>
												</tr>
											</tfoot>
										</table>
										<script type="text/javascript">
											$('#teacher-${teacher.id}')
													.DataTable();
										</script>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" type="button" data-dismiss="modal">Закрыть</button>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<%@ include file="WEB-INF/jspf/footer.jspf"%>
</body>
</html>
