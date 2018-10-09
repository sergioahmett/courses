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
		<c:if test="${not empty msg}">
			<script type="text/javascript">
				PopUpShow();
			</script>
			<div id="myModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title">
								<fmt:message key="modal.msg" />
							</h4>
						</div>
						<div class="modal-body">
							<fmt:message key="msg${msg}" />
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" type="button" data-dismiss="modal">
								<fmt:message key="modal.close" />
							</button>
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
						<fmt:message key="table.title" />
						: <em><c:out value="${resultCourse.courseTitle}" /></em>
					</p>
					<p class="fs-25">
						<fmt:message key="table.theme" />
						:<em><c:if test="${resultCourse.theamId == 0}">
								<fmt:message key="notheme" />
							</c:if> <c:if test="${resultCourse.theamId != 0}">
								<a href="/theams/${resultCourse.theamId}"><c:out value="${resultCourse.theamTitle}" /></a>
							</c:if> </em>
					</p>
					<p class="fs-25">
						<fmt:message key="startDate" />
						: <em><c:out value="${resultCourse.getDate()}" /></em>
					</p>
					<p class="fs-25">
						<fmt:message key="free" />
						: <em><c:out value="${resultCourse.maxStudent - resultCourse.regStudent}" /></em>
					</p>
					<p class="fs-25">
						<fmt:message key="table.teacher" />:
						<c:if test="${not empty resultCourse.teacherFullName}">
							<em><a href="/teachers/${resultCourse.teacheId}"><c:out value="${resultCourse.teacherFullName}"/></a></em>
						</c:if>
						<c:if test="${empty resultCourse.teacherFullName}">
							<em><fmt:message key="noTeacher" /></em>
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
			<c:if test="${sessionScope.role eq 'Student'}">
				<c:if test="${resultCourse.isActual()}">
					<div class="row">
						<div class="col-lg-4 col-lg-offset-4">
							<c:if test="${not sessionScope.courseList.contains(resultCourse)}">
								<form method="post" action="/courses/${resultCourse.courseId}?command=register">
									<button type="submit" class="btn btn-success cb-all-center course-button" name="id" value="${resultCourse.courseId}">
										<fmt:message key="button.register" />
									</button>
								</form>
							</c:if>
							<c:if test="${sessionScope.courseList.contains(resultCourse)}">
								<form method="post" action="/courses/${resultCourse.courseId}?command=unregister">
									<button type="submit" class="btn btn-warning cb-all-center course-button" name="id" value="${resultCourse.courseId}">
										<fmt:message key="button.unregister" />
									</button>
								</form>
							</c:if>
						</div>
					</div>
				</c:if>
			</c:if>
		</div>
	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>
