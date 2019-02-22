<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 04/02/2019
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>High</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/css/bootstrap.min.css"
          rel="stylesheet"/>

</head>
<body>

<style type="text/css">
    body {padding-top: 70px;}
    h1 {padding-top: 50px;}
</style>

<div class="container">

    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top ">
        <a class="navbar-brand"  href="${pageContext.request.contextPath}">HOME</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav mr-auto">

                <li class="nav-item">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/redirect?anchor=lesSpots" >Les spots</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/redirect?anchor=lesTopos">Les topos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/redirect?anchor=Contribuez">Contribuez</a>
                </li>
            </ul>

            <ul class="navbar-nav" >
                <c:if test="${empty user}">
                    <li class="nav-item">
                        <a class="nav-item nav-link"
                           href="${pageContext.request.contextPath}/escalade/login?afterLogin=index">Login</a>
                    </li>
                </c:if>

                <c:if test="${not empty user}">
                    <li class="nav-item">
                        <p>Bienvenue ${user.nickname}</p>
                    </li>
                    <li class="nav-item">
                        <a class="nav-item nav-link" href="">Mon compte</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/logout">Se déconnecter</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>

    <h1>New Guidebooks</h1>


</div>


<script src="//code.jquery.com/jquery-1.12.4.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.autocomplete.min.js"></script>


<script>
    $(function() {
        $( "#autocomplete" ).autocomplete({
            minLength: 2,
            source: '${pageContext.request.contextPath}/escalade/get_location_API_Rest' ,
        });
    });

</script>


</body>
</html>
