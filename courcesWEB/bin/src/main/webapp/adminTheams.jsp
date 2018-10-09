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
						class="btn btn-default cb-all-center" href="/admin/teachers">Учителя</a><br> <a role="button" class="btn btn-default cb-all-center "
						href="/admin/courses">Курсы</a><br> <a role="button" class="btn btn-default cb-all-center active" href="/admin/theams">Темы</a>
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
								<h4 class="modal-title">Введите данные темы</h4>
							</div>
							<div class="modal-body">
								<div class="form-group">
									<label for="exampleFormControlTextarea1">Введите название</label> <textarea class="form-control" id="title" name="title" rows="2"></textarea>
								</div>
								<div class="form-group">
									<label for="exampleFormControlTextarea1">Введите описание</label> <textarea class="form-control" id="description" name="description" rows="6"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default">Сохранить изменения</button>
								<button class="btn btn-default" type="button" data-dismiss="modal">Закрыть</button>
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
								<th>Название</th>
								<th>Описание</th>
								<th>Изменить</th>
								<th>Удалить</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="theam" items="${theamsList}">
								<tr>

									<td><c:out value="${theam.title}" /></td>
									<td><c:out value="${theam.description}" /></td>
									<td><button class="btn btn-default" type="button" data-toggle="modal" data-target="#changeTheam${theam.id}">Изменить</button></td>
									<td><form method="post" action="/admin/theams?command=delete&theamId=${theam.id}">
											<button role="button" type="submit" class="btn btn-default">Удалить</button>
										</form>
										<div id="changeTheam${theam.id}" class="modal fade">
											<div class="modal-dialog">
												<div class="modal-content">
													<form method="post" action="/admin/theams?command=change&theamId=${theam.id}">
														<div class="modal-header">
															<button class="close" type="button" data-dismiss="modal">×</button>
															<h4 class="modal-title">Введите измененные данные</h4>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<label for="exampleFormControlTextarea1">Введите название</label> <textarea class="form-control" id="title" name="title" rows="2"></textarea>
															</div>
															<div class="form-group">
																<label for="exampleFormControlTextarea1">Введите описание</label> <textarea class="form-control" id="description" name="description"
																	rows="6"></textarea>
															</div>
														</div>
														<div class="modal-footer">
															<button type="submit" class="btn btn-default">Сохранить изменения</button>
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
								<th>Название</th>
								<th>Описание</th>
								<th>Изменить</th>
								<th>Удалить</th>
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