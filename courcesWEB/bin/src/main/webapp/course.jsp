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
				<div class="col-lg-3 col-lg-offset-1">
					<img src="/images/400x400.jpg" height="300" />
				</div>
				<div class="col-lg-offset-1 col-lg-6 bgcolor propertyWrapper">
					<p class="fs-25">
						Название: <em><c:out value="${resultCourse.courseTitle}" /></em>
					</p>
					<p class="fs-25">
						Тема: <em><a href="/theams/${resultCourse.theamId}"><c:out value="${resultCourse.theamTitle}" default="Тема не задана" /></a></em>
					</p>
					<p class="fs-25">
						Дата начала: <em><c:out value="${resultCourse.getDate()}" /></em>
					</p>
					<p class="fs-25">
						Свободных мест: <em><c:out value="${resultCourse.maxStudent - resultCourse.regStudent}" /></em>
					</p>
					<p class="fs-25">
						Преподователь:
						<c:if test="${not empty resultCourse.teacherFullName}">
							<em><a href="/teachers/${resultCourse.teacheId}"><c:out value="${resultCourse.teacherFullName}" default="Учитель не назначен" /></a></em>
						</c:if>
						<c:if test="${empty resultCourse.teacherFullName}">
							<em>Учитель не назначен</em>
						</c:if>
					</p>
				</div>
			</div>
		</div>
		<div class="lines">
			<div class="row">
				<div class="col-lg-10 col-lg-offset-1 bgcolor propertyWrapper">
					<p style="font-size: 20px;">
						<c:out value="${resultCourse.description}" />
					</p>
				</div>
			</div>
		</div>
		<div class="bottomLines">
			<c:if test="${sessionScope.role == 'Student'}">
				<c:if test="${resultCourse.isActual()}">
					<div class="row">
						<div class="col-lg-4 col-lg-offset-4">
							<c:if test="${not sessionScope.courseList.contains(resultCourse)}">
								<form method="post" action="/courses/${resultCourse.courseId}?command=register">
									<button type="submit" class="btn btn-success cb-all-center course-button" name="id" value="${resultCourse.courseId}">Зарегистрироваться</button>
								</form>
							</c:if>
							<c:if test="${sessionScope.courseList.contains(resultCourse)}">
								<form method="post" action="/courses/${resultCourse.courseId}?command=unregister">
									<button type="submit" class="btn btn-warning cb-all-center course-button" name="id" value="${resultCourse.courseId}">Отменить
										регистрацию</button>
								</form>
							</c:if>
						</div>
					</div>
				</c:if>
			</c:if>
		</div>
	</div>
	<%@ include file="WEB-INF/jspf/footer.jspf"%>
</body>
</html>
