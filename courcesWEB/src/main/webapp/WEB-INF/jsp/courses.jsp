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
	<div class="container-fluid">
		<%@ include file="../jspf/header.jspf"%>
		<div class="course-button-holder col-lg-2 bgcolor">
			<a role="button" class="btn btn-default cb-all-center" href="/courses?show=all"><fmt:message key="button.all" /></a><br> <a role="button"
				class="btn btn-default cb-all-center" href="/courses?show=actual"><fmt:message key="button.actual" /></a><br> <a role="button"
				class="btn btn-default cb-all-center" href="/courses?show=current"><fmt:message key="button.current" /></a><br> <a role="button"
				class="btn btn-default cb-all-center" href="/courses?show=archive"><fmt:message key="button.archive" /></a>
		</div>
		<div class="bottomLines mt-60">
			<div class="row">
				<div class="col-lg-9 col-lg-offset-3 bgcolor tableWrapper">
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
							<c:forEach var="course" items="${resultList}">
								<tr>
									<td><c:out value="${course.courseTitle}" /></td>

									<td><c:if test="${empty course.theamTitle}">
											<fmt:message key="notheme" />
										</c:if> <c:if test="${not empty course.theamTitle}">
											<c:out value="${course.theamTitle}" />
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
	</div>
</body>
</html>