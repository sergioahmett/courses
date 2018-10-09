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
			<div id="myModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title">Ошибка</h4>
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
				<div class="col-lg-8 col-lg-offset-2 bgcolo">
					<a role="button" class="btn btn-default cb-all-center" href="/admin/users">Пользователи</a><br> <a role="button"
						class="btn btn-default cb-all-center active" href="/admin/teachers">Учителя</a><br> <a role="button" class="btn btn-default cb-all-center"
						href="/admin/courses">Курсы</a><br> <a role="button" class="btn btn-default cb-all-center" href="/admin/theams">Темы</a>
				</div>
			</div>
		</div>
		<div class="bottomLines">
			<div class="row">
				<div class="col-lg-12 bgcolor tableWrapper">
					<table id="example" class="display" style="width: 100%">
						<thead>
							<tr>
								<th>ID</th>
								<th>Логин</th>
								<th>Почта</th>
								<th>Время рег.</th>
								<th>Заблокирован</th>
								<th>Блокировать</th>
								<th>Вернуть в студенты</th>
								<th>Изменить описание</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${userList}">
								<tr>
									<td><c:out value="${user.id}" /></td>
									<td><c:out value="${user.login}" /></td>
									<td><c:out value="${user.mail}" /></td>
									<td>11-08-96</td>
									<td><c:out value="${user.name}" /> <c:out value="${user.surname}" /></td>
									<c:if test="${user.block}">
										<td><form method="post" action="/admin/teachers/${user.id}?command=unblock">
												<button type="submit" class="btn btn-success">Разблокировать</button>
											</form></td>
									</c:if>
									<c:if test="${not user.block}">
										<td><form method="post" action="/admin/teachers/${user.id}?command=block">
												<button type="submit" class="btn btn-danger">Заблокировать</button>
											</form></td>

									</c:if>
									<td><form method="post" action="/admin/teachers/${user.id}?command=setStudent">
											<button type="submit" class="btn btn-default">Вернуть в студенты</button>
										</form></td>
									<td><button class="btn btn-default" type="button" data-toggle="modal" data-target="#descChange">Изменить описание</button>
										<div id="descChange" class="modal fade">
											<div class="modal-dialog">
												<div class="modal-content">
													<form method="post" action="/admin/teachers/${user.id}?command=changeDescription">
														<div class="modal-header">
															<button class="close" type="button" data-dismiss="modal">×</button>
															<h4 class="modal-title">Введите новое описание</h4>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<label for="exampleFormControlTextarea1">Введите новое описание учителя</label> <textarea class="form-control" id="description"
																	name="description" rows="10"></textarea>
															</div>
														</div>
														<div class="modal-footer">
															<button type="submit" class="btn btn-default">Изменить описание</button>
															<button class="btn btn-default" type="button" data-dismiss="modal">Закрыть</button>
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
								<th>ID</th>
								<th>Логин</th>
								<th>Почта</th>
								<th>Зарегистрирован</th>
								<th>Заблокирован</th>
								<th>Блокировать</th>
								<th>Вернуть в студенты</th>
								<th>Изменить описание</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>