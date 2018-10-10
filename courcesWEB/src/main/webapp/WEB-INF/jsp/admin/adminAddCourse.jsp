<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource"/>
<html>
<head>
<title><fmt:message key="title"/></title>
<%@ include file="../../jspf/head.jspf"%>
</head>
<body class="body">
	<div class="container">
		<%@ include file="../../jspf/header.jspf"%>

		<div class="bottomLines mt-60">
			<div class="row">
				<div class="col-lg-10 col-lg-offset-1 bgcolor tableWrapper">
					<form action="/admin/courses/add?command=${not empty course?'change&courseId=':'create'}${not empty course?course.id:''}" method="post">
						<div class="form-group">
							<label><fmt:message key="course.name"/></label> <textarea class="form-control" id="title" name="courseName" rows="3"><c:out value="${course.title}" /></textarea>
						</div>
						<div class="form-group">
							<label><fmt:message key="select.theme"/></label> <select class="form-control" id="theme" name="theme">
								<option value="-1"><fmt:message key="theme.notSelected"/></option>
								<c:forEach var="theme" items="${themesList}">
									<c:if test="${course.theme eq theme.id}">
										<option selected="selected" value="${theme.id}"><c:out value="${theme.title}" /></option>
									</c:if>
									<c:if test="${course.theme != theme.id}">
										<option value="${theme.id}"><c:out value="${theme.title}" /></option>
									</c:if>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label><fmt:message key="course.description"/></label> <textarea class="form-control" id="description" name="description" rows="10"><c:out
									value="${course.description}" /></textarea>
						</div>
						<div class="form-group">
							<label><fmt:message key="course.durationInDay"/></label> <input type="number" class="form-control" id="duration" name="duration"
								value="<c:out value="${course.duration}"/>"></input>
						</div>
						<div class="form-group">
							<label><fmt:message key="course.maxtudent"/></label> <input type="number" class="form-control" id="maxStudent" name="maxStudent"
								value="<c:out value="${course.maxStudentCount}"/>"></input>
						</div>

						<div class="form-group">
							<label><fmt:message key="select.teacher"/></label> <select class="form-control" id="teacher" name="teacher">
								<option value="-1"><fmt:message key="noTeacher"/></option>
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
							<label><fmt:message key="startDate"/></label> <input type="date" class="form-control" id="startDate" name="startDate" value="${course.getDate()}" required="required">
						</div>
						<c:if test="${not empty course}">
							<div class="form-group">
								<button type="submit" class="btn btn-primary"><fmt:message key="changeCourse"/></button>
							</div>
						</c:if>
						<c:if test="${empty course}">
							<div class="form-group">
								<button type="submit" class="btn btn-primary"><fmt:message key="addCourse"/></button>
							</div>
						</c:if>
					</form>
				</div>
			</div>
		</div>
		<%@ include file="../../jspf/footer.jspf"%>
	</div>
</body>
</html>