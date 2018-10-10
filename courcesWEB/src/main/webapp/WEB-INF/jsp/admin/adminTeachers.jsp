<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource" />
<html>
<head>
<title><fmt:message key="title"/></title>
<%@ include file="../../jspf/head.jspf"%>
</head>
<body class="body">
	<div class="container-fluid">
		<%@ include file="../../jspf/header.jspf"%>
		<c:if test="${not empty msg}">
			<div id="myModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title"><fmt:message key="modal.msg"/></h4>
						</div>
						<div class="modal-body">
							<fmt:message key="msg${msg}"/>
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message key="modal.close"/></button>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<div class="lines mt-60">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 bgcolo">
					<a role="button" class="btn btn-default cb-all-center" href="/admin/users"><fmt:message key="button.users"/></a><br> <a role="button"
						class="btn btn-default cb-all-center active" href="/admin/teachers"><fmt:message key="button.teachers"/></a><br> <a role="button" class="btn btn-default cb-all-center"
						href="/admin/courses"><fmt:message key="button.courses"/></a><br> <a role="button" class="btn btn-default cb-all-center" href="/admin/themes"><fmt:message key="button.themes"/></a>
				</div>
			</div>
		</div>
		<div class="bottomLines">
			<div class="row">
				<div class="col-lg-12 bgcolor tableWrapper">
					<table id="example" class="display" style="width: 100%">
						<thead>
							<tr>
								<th><fmt:message key="table.id"/></th>
								<th><fmt:message key="table.login"/></th>
								<th><fmt:message key="table.mail"/></th>
								<th><fmt:message key="table.fullName"/></th>
								<th><fmt:message key="table.blocking"/></th>
								<th><fmt:message key="table.toStudent"/></th>
								<th><fmt:message key="table.chDescription"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${userList}">
								<tr>
									<td><c:out value="${user.id}" /></td>
									<td><c:out value="${user.login}" /></td>
									<td><c:out value="${user.mail}" /></td>
									<td><c:out value="${user.name}" /> <c:out value="${user.surname}" /></td>
									<c:if test="${user.block}">
										<td><form method="post" action="/admin/teachers/${user.id}?command=unblock">
												<button type="submit" class="btn btn-success"><fmt:message key="table.unblock"/></button>
											</form></td>
									</c:if>
									<c:if test="${not user.block}">
										<td><form method="post" action="/admin/teachers/${user.id}?command=block">
												<button type="submit" class="btn btn-danger"><fmt:message key="table.block"/></button>
											</form></td>

									</c:if>
									<td><form method="post" action="/admin/teachers/${user.id}?command=setStudent">
											<button type="submit" class="btn btn-default"><fmt:message key="table.toStudent"/></button>
										</form></td>
									<td><button class="btn btn-default" type="button" data-toggle="modal" data-target="#descChange"><fmt:message key="table.chDescription"/></button>
										<div id="descChange" class="modal fade">
											<div class="modal-dialog">
												<div class="modal-content">
													<form method="post" action="/admin/teachers/${user.id}?command=changeDescription">
														<div class="modal-header">
															<button class="close" type="button" data-dismiss="modal">×</button>
															<h4 class="modal-title"><fmt:message key="table.addNewDescription"/></h4>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<label for="exampleFormControlTextarea1"><fmt:message key="table.addNewDescription"/></label> <textarea class="form-control" id="description"
																	name="description" rows="10"></textarea>
															</div>
														</div>
														<div class="modal-footer">
															<button type="submit" class="btn btn-default"><fmt:message key="table.chDescription"/></button>
															<button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message key="modal.close"/></button>
														</div>
													</form>
												</div>
											</div>
										</div></td>

								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th><fmt:message key="table.id"/></th>
								<th><fmt:message key="table.login"/></th>
								<th><fmt:message key="table.mail"/></th>
								<th><fmt:message key="table.fullName"/></th>
								<th><fmt:message key="table.blocking"/></th>
								<th><fmt:message key="table.toStudent"/></th>
								<th><fmt:message key="table.chDescription"/></th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="../../jspf/footer.jspf"%>
	</div>
</body>
</html>