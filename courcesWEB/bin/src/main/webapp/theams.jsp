<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
	<%@ include file="WEB-INF/jspf/header.jspf"%>
	<c:if test="${not empty error}">
		<div id="myModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal">×</button>
						<h4 class="modal-title">Ошибка</h4>
					</div>
					<div class="modal-body">
						<c:out value="${error}" />
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" type="button" data-dismiss="modal">Закрыть</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<div class="container-fluix mt-60">
		<div class="bottomLines">
			<div class="row col-lg-10 col-lg-offset-1 bgcolor tableWrapper">
				<table id="example" class="display" style="width: 100%">
					<thead>
						<tr>
							<th>Название</th>
							<th>Описание</th>
							<th>Количество курсов</th>
							<th>Подробнее</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="theam" items="${resultList}">
							<tr>
								<td><c:out value="${theam.theam.title}" /></td>
								<td><c:out value="${theam.theam.description}" /></td>
								<td><c:out value="${theam.courseList.size()}" /></td>
								<td><a role="button" class="btn btn-default cb-all-center" href="/theams/${theam.theam.id}">Подробнее</a></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th>Название</th>
							<th>Описание</th>
							<th>Количество курсов</th>
							<th>Подробнее</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="WEB-INF/jspf/footer.jspf"%>
</body>
</html>