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
	<div class="container mt-60">
		<%@ include file="../jspf/header.jspf"%>
		<c:if test="${not empty error}">
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
							<fmt:message key="msg${error}" />
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
		<div class="lines">
			<div class="row">
				<div class="col-lg-3 col-lg-offset-1">
					<img src="/images/400x400.jpg" height="300" />
				</div>
				<div class="col-lg-offset-1 col-lg-6 bgcolor propertyWrapper">
					<p class="fs-25">
						<fmt:message key="profile.name" />
						: <em><c:out value="${user.fullName}" /></em>
					</p>
					<p class="fs-25">
						<fmt:message key="profile.login" />
						: <em><c:out value="${user.login}" /></em>
					</p>
					<p class="fs-25">
						<fmt:message key="profile.mail" />
						: <em><c:out value="${user.mail}" /></em>
					</p>
				</div>
			</div>
		</div>
		<div class="lines">
			<div class="row bgcolor" style="border-radius: 40px; padding: 10px; padding-left: 30px;">
				<form class="" method="post" action="http://localhost:8080/profile?command=pchange">
				<input type="hidden" name="uid" value="${uid}">
					<div class="col-lg-4">
						<h2>
							<fmt:message key="profile.chPass" />
							:
						</h2>
						<div class="form-group">
							<label for="password" class="cols-sm-2 control-label"><fmt:message key="profile.currentPass" /></label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
										name="currentPass" id="currentPass" placeholder="<fmt:message key="input.password" />" required />
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="cols-sm-2 control-label"><fmt:message key="profile.newPass" /></label>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
											name="newPass" id="newPass" placeholder="<fmt:message key="input.setpassword" />" required />
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="cols-sm-2 control-label"><fmt:message key="profile.repPass" /></label>
									<div class="cols-sm-10">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input type="password" class="form-control"
												name="confirmPass" id="confirmPass" placeholder="<fmt:message key="input.password" />" required />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<button type="submit" id="button" class="btn btn-primary btn-lg btn-block login-button">
							<fmt:message key="save" />
						</button>
					</div>
				</form>
			</div>
		</div>
		<div class="bottomLines">
			<c:if test="${not sessionScope.role.equals('Admin')}">
				<div class="row col-lg-12 bgcolor tableWrapper">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#panel1"><fmt:message key="button.actual" /></a></li>
						<li><a data-toggle="tab" href="#panel2"><fmt:message key="button.current" /></a></li>
						<li><a data-toggle="tab" href="#panel3"><fmt:message key="button.archive" /></a></li>
					</ul>
					<div class="tab-content">
						<div id="panel1" class="tab-pane fade in active" style="margin-top: 20px;">
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
									<c:forEach var="course" items="${user.actualCourseList}">
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
						<div id="panel2" class="tab-pane" style="margin-top: 20px;">
							<table id="example2" class="display" style="width: 100%">
								<thead>
									<tr>
										<th><fmt:message key="table.title" /></th>
										<th><fmt:message key="table.theme" /></th>
										<th><fmt:message key="table.duration" /></th>
										<th><fmt:message key="table.reg" /></th>
										<th><fmt:message key="table.max" /></th>
										<th><fmt:message key="table.teacher" /></th>
										<th><fmt:message key="table.journal" /></th>
										<th><fmt:message key="table.more" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="course" items="${user.currentCourseList}">
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
											<td><a role="button" class="btn btn-default" href="/journal/${course.courseId}"><fmt:message key="table.journal" /></a></td>
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
										<th><fmt:message key="table.journal" /></th>
										<th><fmt:message key="table.more" /></th>
									</tr>
								</tfoot>
							</table>
						</div>
						<div id="panel3" class="tab-pane" style="margin-top: 20px;">
							<table id="example3" class="display" style="width: 100%">
								<thead>
									<tr>
										<th><fmt:message key="table.title" /></th>
										<th><fmt:message key="table.theme" /></th>
										<th><fmt:message key="table.duration" /></th>
										<th><fmt:message key="table.reg" /></th>
										<th><fmt:message key="table.max" /></th>
										<th><fmt:message key="table.teacher" /></th>
										<th><fmt:message key="table.journal" /></th>
										<th><fmt:message key="table.more" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="course" items="${user.archiveCourseList}">
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
											<td><a role="button" class="btn btn-default" href="/journal/${course.courseId}"><fmt:message key="table.journal" /></a></td>
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
										<th><fmt:message key="table.journal" /></th>
										<th><fmt:message key="table.more" /></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>
