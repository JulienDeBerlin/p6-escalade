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
        <a class="navbar-brand" href="#">MENU</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-item nav-link active" href="#leProjet">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="#lesSpots" >Les spots</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="#lesTopos">Les topos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="#Contribuez">Contribuez</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link"
                       href="${pageContext.request.contextPath}/escalade/login?loginFrom=home" >Login</a>
                </li>
            </ul>
        </div>
    </nav>
    <h1>New Guidebooks</h1>
    <jsp:include page="../../resources/JspFragments/scriptsJS.jsp"></jsp:include>

</body>
</html>
