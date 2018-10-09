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
		<div class="course-button-holder col-lg-2 bgcolor">
			<a role="button" class="btn btn-default cb-all-center" href="/courses?show=all">Все</a><br> <a role="button"
				class="btn btn-default cb-all-center" href="/courses?show=actual">Актуальные</a><br> <a role="button" class="btn btn-default cb-all-center"
				href="/courses?show=current">Текущие</a><br> <a role="button" class="btn btn-default cb-all-center" href="/courses?show=archive">Архив</a>
		</div>
		<div class="bottomLines mt-60">
			<div class="row">
				<div class="col-lg-9 col-lg-offset-3 bgcolor tableWrapper">
					<table id="example" class="display" style="width: 100%">
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
							<c:forEach var="course" items="${resultList}">
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
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>