<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
	<div class="container-fluix mt-60">
		<%@ include file="WEB-INF/jspf/header.jspf"%>
		<div class="lines">
			<div class="row">
				<div class="col-lg-3 col-lg-offset-1">
					<img src="/images/400x400.jpg" height="300" />
				</div>
				<div class="col-lg-offset-1 col-lg-6 bgcolor propertyWrapper">
					<p class="fs-25">
						Название: <em><c:out value="${theam.theam.title}" /></em>
					</p>
					<p class="fs-25">
						Описание: <em><c:out value="${theam.theam.description}" /></em>
					</p>
				</div>
			</div>
		</div>
		<div class="bottomLines">
			<div class="row col-lg-10 col-lg-offset-1 bgcolor tableWrapper">
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
						<c:forEach var="course" items="${theam.courseList}">
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
</body>
</html>
