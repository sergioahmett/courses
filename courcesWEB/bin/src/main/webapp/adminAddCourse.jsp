<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
	<div class="container">
		<%@ include file="WEB-INF/jspf/header.jspf"%>

		<div class="bottomLines mt-60">
			<div class="row">
				<div class="col-lg-10 col-lg-offset-1 bgcolor tableWrapper">
					<form action="/admin/courses/add?command=${not empty course?'change&courseId=':'create'}${not empty course?course.id:''}" method="post">
						<div class="form-group">
							<label>Название курса</label> <textarea class="form-control" id="title" name="courseName" rows="3"><c:out value="${course.title}" /></textarea>
						</div>
						<div class="form-group">
							<label>Выберите тему</label> <select class="form-control" id="theam" name="theam">
								<option value="-1">Тема не выбрана</option>
								<c:forEach var="theam" items="${theamsList}">
									<c:if test="${course.theme eq theam.id}">
										<option selected="selected" value="${theam.id}"><c:out value="${theam.title}" /></option>
									</c:if>
									<c:if test="${course.theme != theam.id}">
										<option value="${theam.id}"><c:out value="${theam.title}" /></option>
									</c:if>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label>Описание курса</label> <textarea class="form-control" id="description" name="description" rows="10"><c:out
									value="${course.description}" /></textarea>
						</div>
						<div class="form-group">
							<label>Длительность в днях</label> <input type="number" class="form-control" id="duration" name="duration"
								value="<c:out value="${course.duration}"/>"></input>
						</div>
						<div class="form-group">
							<label>Максимум студентов</label> <input type="number" class="form-control" id="maxStudent" name="maxStudent"
								value="<c:out value="${course.maxStudentCount}"/>"></input>
						</div>

						<div class="form-group">
							<label>Выберите учителя</label> <select class="form-control" id="teacher" name="teacher">
								<option value="-1">Нет учителя</option>
								<c:forEach var="teacher" items="${teachersList}">
									<c:if test="${course.teacher eq teacher.id}">
										<option selected="selected" value="${teacher.id}"><c:out value="${teacher.name}" /> <c:out value="${teacher.surname}" /></option>
									</c:if>
									<c:if test="${course.teacher != teacher.id}">
										<option value="${teacher.id}"><c:out value="${teacher.name}" /> <c:out value="${teacher.surname}" /></option>
									</c:if>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label>Дата начала</label> <input type="date" class="form-control" id="startDate" name="startDate" value="${course.getDate()}">
						</div>
						<c:if test="${not empty course}">
							<div class="form-group">
								<button type="submit" class="btn btn-primary">Изменить курс</button>
							</div>
						</c:if>
						<c:if test="${empty course}">
							<div class="form-group">
								<button type="submit" class="btn btn-primary">Добавить курс</button>
							</div>
						</c:if>
					</form>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>