<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
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
	<div class="container-fluix mt-60">
		<div class="lines">
			<div class="row col-lg-10 col-lg-offset-1 bgcolor tableWrapper">
				<table id="example" class="display" style="width: 100%">
					<thead>
						<tr>
							<th>ФИО</th>
							<c:forEach var="title" items="${journal.titleList}">
								<th>title</th>
							</c:forEach>
							<th>Финальная оценка</th>
							<th>Установить оценку</th>
							<th>Установить финальную оценку</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entry" items="${journal.usersMap}">
							<tr>
								<c:forEach var="entity" items="${entry.value}">
									<td><c:out value="${entity}" /></td>
								</c:forEach>
								<td><c:out value="${entity.finalRaiting.get(entity.key)}" /></td>
								<td><a role="button" class="btn btn-default cb-all-center" href="/theams/${theam.theam.id}">добавить 1</a></td>
								<td><a role="button" class="btn btn-default cb-all-center" href="/theams/${theam.theam.id}">добавить 2</a></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th>ФИО</th>
							<c:forEach var="title" items="${journal.titleList}">
								<th>title</th>
							</c:forEach>
							<th>Финальная оценка</th>
							<th>Установить оценку</th>
							<th>Установить финальную оценку</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div class="bottomLines">
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
					<a role="button" class="btn btn-default cb-all-center" href="/theams/${theam.theam.id}">добавить день</a>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="WEB-INF/jspf/footer.jspf"%>
</body>
</html>