<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource" />
<html>
<head>
<title><fmt:message key="title" /></title>
<%@ include file="../jspf/head.jspf"%>
</head>
<body class="body">
	<div class="container-fluix mt-60">
		<%@ include file="../jspf/header.jspf"%>
		<div class="lines">
			<div class="row">
				<div class="col-lg-3 col-lg-offset-1">
					<img src="/images/400x400.jpg" height="300" />
				</div>
				<div class="col-lg-offset-1 col-lg-6 bgcolor propertyWrapper">
					<p class="fs-25">
						<fmt:message key="table.title" />
						: <em><c:out value="${theme.theme.title}" /></em>
					</p>
					<p class="fs-25">
						<fmt:message key="table.description" />
						: <em><c:out value="${theme.theme.description}" /></em>
					</p>
				</div>
			</div>
		</div>
		<div class="bottomLines">
			<div class="row col-lg-10 col-lg-offset-1 bgcolor tableWrapper">
				<table id="example" class="display" style="width: 100%">
					<thead>
						<tr>
							<th><fmt:message key="table.title" /></th>
							<th><fmt:message key="table.theme" /></th>
							<th><fmt:message key="table.duration" /></th>
							<th><fmt:message key="table.reg" /></th>
							<th><fmt:message key="table.max" /></th>
							<th><fmt:message key="table.teacher" /></th>
							<th><fmt:message key="table.more" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="course" items="${theme.courseList}">
							<tr>
								<td><c:out value="${course.courseTitle}" /></td>

									<td><c:if test="${empty course.themeTitle}">
											<fmt:message key="notheme" />
										</c:if> <c:if test="${not empty course.themeTitle}">
											<c:out value="${course.themeTitle}" />
										</c:if></td>
									<td><c:out value="${course.duration}" /></td>
									<td><c:out value="${course.regStudent}" /></td>
									<td><c:out value="${course.maxStudent}" /></td>
									<td><c:if test="${empty course.teacherFullName}">
											<fmt:message key="noTeacher" />
										</c:if> <c:if test="${not empty course.teacherFullName}">
											<c:out value="${course.teacherFullName}" />
										</c:if></td>
									<td><a role="button" class="btn btn-default" href="/courses/${course.courseId}"><fmt:message key="table.more" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th><fmt:message key="table.title" /></th>
							<th><fmt:message key="table.theme" /></th>
							<th><fmt:message key="table.duration" /></th>
							<th><fmt:message key="table.reg" /></th>
							<th><fmt:message key="table.max" /></th>
							<th><fmt:message key="table.teacher" /></th>
							<th><fmt:message key="table.more" /></th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>
