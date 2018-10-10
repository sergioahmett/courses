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
	<%@ include file="../jspf/header.jspf"%>
	<c:if test="${not empty msg}">
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
	<div class="container-fluix mt-60">
		<div class="lines">
			<div class="row col-lg-10 col-lg-offset-1 bgcolor tableWrapper">
				<table id="example" class="display" style="width: 100%">
					<thead>
						<tr>
							<th><fmt:message key="journal.name" /></th>
							<c:forEach var="title" items="${journal.titleList}">
								<th>${title}</th>
							</c:forEach>
							<th><fmt:message key="journal.finalrating" /></th>
							<c:if test="${sessionScope.role eq 'Teacher'}">
								<th><fmt:message key="journal.setrating" /></th>
								<th><fmt:message key="journal.setfinalrating" /></th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entry" items="${journal.usersMap}">
							<tr>
								<c:forEach var="entity" items="${entry.value}">
									<td><c:out value="${entity}" /></td>
								</c:forEach>
								<td><c:out value="${journal.finalRating[entry.key]}" /></td>
								<c:if test="${sessionScope.role eq 'Teacher'}">
									<td><button class="btn btn-default cb-all-center" type="button" data-toggle="modal" data-target="#addRaiting${entry.key}">
											<fmt:message key="journal.setrating" />
										</button>
										<div id="addRaiting${entry.key}" class="modal fade">
											<div class="modal-dialog">
												<div class="modal-content">
													<form method="post" action="/journal/${courseId}?command=addRaiting&userId=${entry.key}">
														<div class="modal-header">
															<button class="close" type="button" data-dismiss="modal">×</button>
															<h4 class="modal-title"></h4>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<div class="form-group">
																	<label><fmt:message key="journal.enterrating" /></label> <input class="form-control" type="number" id="raiting" name="raiting"></input>
																</div>
																<div class="form-group">
																	<label><fmt:message key="journal.selectlessonday" /></label> <select class="form-control" id="day" name="day">
																		<c:forEach var="title" items="${journal.titleList}">
																			<option value="${title}">${title}</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
														<div class="modal-footer">
															<button type="submit" class="btn btn-default">
																<fmt:message key="journal.add" />
															</button>
															<button class="btn btn-default" type="button" data-dismiss="modal">
																<fmt:message key="modal.close" />
															</button>
														</div>
													</form>
												</div>
											</div>
										</div></td>
									<td><button class="btn btn-default cb-all-center" type="button" data-toggle="modal" data-target="#addFinal${entry.key}">
											<fmt:message key="journal.setfinalrating" />
										</button>
										<div id="addFinal${entry.key}" class="modal fade">
											<div class="modal-dialog">
												<div class="modal-content">
													<form method="post" action="/journal/${courseId}?command=addFinal&userId=${entry.key}">
														<div class="modal-header">
															<button class="close" type="button" data-dismiss="modal">×</button>
															<h4 class="modal-title"></h4>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<div class="form-group">
																	<label><fmt:message key="journal.enterrating" /></label> <input class="form-control" type="number" id="final" name="final"></input>
																</div>
															</div>
														</div>
														<div class="modal-footer">
															<button type="submit" class="btn btn-default">
																<fmt:message key="journal.add" />
															</button>
															<button class="btn btn-default" type="button" data-dismiss="modal">
																<fmt:message key="modal.close" />
															</button>
														</div>
													</form>
												</div>
											</div>
										</div></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th><fmt:message key="journal.name" /></th>
							<c:forEach var="title" items="${journal.titleList}">
								<th>${title}</th>
							</c:forEach>
							<th><fmt:message key="journal.finalrating" /></th>
							<c:if test="${sessionScope.role eq 'Teacher'}">
								<th><fmt:message key="journal.setrating" /></th>
								<th><fmt:message key="journal.setfinalrating" /></th>
							</c:if>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div class="bottomLines">
			<c:if test="${sessionScope.role eq 'Teacher'}">
				<div class="row">
					<div class="col-lg-6 col-lg-offset-3 ">
						<button class="btn btn-default cb-all-center" type="button" data-toggle="modal" data-target="#addDay">
							<fmt:message key="journal.addDay" />
						</button>
						<div id="addDay" class="modal fade">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="post" action="/journal/${courseId}?command=addDay">
										<div class="modal-header">
											<button class="close" type="button" data-dismiss="modal">×</button>
											<h4 class="modal-title"></h4>
										</div>
										<div class="modal-body">
											<div class="form-group">
												<div class="form-group">
													<label><fmt:message key="journal.addDate" /></label> <input type="date" class="form-control" id="day" name="day" />
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-default"><fmt:message key="journal.add" /></button>
											<button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message key="modal.close" /></button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>