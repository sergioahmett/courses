<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IT-COURSES - лучшие курсы</title>
<%@ include file="WEB-INF/jspf/head.jspf"%>
</head>
<body class="body">
	<%@ include file="WEB-INF/jspf/header.jspf"%>
	<div class="container">
		<img src="http://localhost:8080/images/test_logo.png" class="logoclass">
		<div class="home-title-holder">
			<p class="home-title">Компьютерная школа в Харькове</p>
			<p class="home-title-descr">
				Обучающие программы по программированию и <br>интернет-технологиям для людей любого возраста<br>и с любой базой знаний.
			</p>
		</div>
		<div class="hb-all-center home-button-holder">
			<a role="button" class="btn btn-default mr-5 " href="/courses">Записаться на курс</a> <a role="button" class="btn btn-default mr-5" href="/about">Узнать
				больше</a>
		</div>
	</div>
	<%@ include file="WEB-INF/jspf/footer.jspf"%>
</body>
</html>