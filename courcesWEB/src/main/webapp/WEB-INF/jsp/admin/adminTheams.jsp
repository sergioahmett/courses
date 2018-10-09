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
						href="/admin/courses"><fmt:message key="button.courses"/></a><br> <a role="button" class="btn btn-default cb-all-center" href="/admin/theams"><fmt:message key="button.themes"/></a>
				</div>
			</div>
		</div>
		<div class="lines">
			<div class="row">
				<button class="btn btn-default col-lg-8 col-lg-offset-2" type="button" data-toggle="modal" data-target="#addTheam">Добавить тему</button>
			</div>
			<div id="addTheam" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="/admin/theams?command=create">
							<div class="modal-header">
								<button class="close" type="button" data-dismiss="modal">×</button>
								<h4 class="modal-title"><fmt:message key="add.theme.data"/></h4>
							</div>
							<div class="modal-body">
								<div class="form-group">
									<label for="exampleFormControlTextarea1"><fmt:message key="table.title"/></label> <textarea class="form-control" id="title" name="title" rows="2"></textarea>
								</div>
								<div class="form-group">
									<label for="exampleFormControlTextarea1"><fmt:message key="table.description"/></label> <textarea class="form-control" id="description" name="description" rows="6"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default"><fmt:message key="save"/></button>
								<button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message key="modal.close"/></button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="bottomLines">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 bgcolor tableWrapper">
					<table id="example" class="display" style="width: 100%">
						<thead>
							<tr>
								<th><fmt:message key="table.title"/></th>
								<th><fmt:message key="table.description"/></th>
								<th><fmt:message key="table.change"/></th>
								<th><fmt:message key="table.dell"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="theam" items="${theamsList}">
								<tr>

									<td><c:out value="${theam.title}" /></td>
									<td><c:out value="${theam.description}" /></td>
									<td><button class="btn btn-default" type="button" data-toggle="modal" data-target="#changeTheam${theam.id}"><fmt:message key="table.change"/></button></td>
									<td><form method="post" action="/admin/theams?command=delete&theamId=${theam.id}">
											<button role="button" type="submit" class="btn btn-default"><fmt:message key="table.dell"/></button>
										</form>
										<div id="changeTheam${theam.id}" class="modal fade">
											<div class="modal-dialog">
												<div class="modal-content">
													<form method="post" action="/admin/theams?command=change&theamId=${theam.id}">
														<div class="modal-header">
															<button class="close" type="button" data-dismiss="modal">×</button>
															<h4 class="modal-title"><fmt:message key="table.addData"/></h4>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<label for="exampleFormControlTextarea1"><fmt:message key="table.title"/></label> 
																<textarea class="form-control" id="title" name="title" rows="2"></textarea>
															</div>
															<div class="form-group">
																<label for="exampleFormControlTextarea1"><fmt:message key="table.description"/></label> 
																<textarea class="form-control" id="description" name="description"
																	rows="6"></textarea>
															</div>
														</div>
														<div class="modal-footer">
															<button type="submit" class="btn btn-default"><fmt:message key="save"/></button>
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
								<th><fmt:message key="table.title"/></th>
								<th><fmt:message key="table.description"/></th>
								<th><fmt:message key="table.change"/></th>
								<th><fmt:message key="table.dell"/></th>
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