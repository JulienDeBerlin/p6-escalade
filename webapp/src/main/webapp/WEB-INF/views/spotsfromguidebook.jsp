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
        <a class="navbar-brand" href="${pageContext.request.contextPath}">HOME</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav mr-auto">
                <%--<li class="nav-item active">--%>
                <%--<a class="nav-item nav-link active" href="#leProjet">Home <span class="sr-only">(current)</span></a>--%>
                <%--</li>--%>
                <li class="nav-item">
                    <a class="nav-item nav-link active" >Les spots</a>
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
                        <a class="nav-item nav-link" href="">Espace Membre</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/logout">Se déconnecter</a>
                    </li>
                </c:if>
            </ul>


        </div>
    </nav>
<p>VOICI LA LISTE DES SITES CORRESPONDANT AU TOPO ${selectedGuidebook.name}</p>

<div>
    <ul>
        <c:forEach items="${ selectedGuidebook.spots }" var="spot" varStatus="status">

            <p><strong> <c:out value="${ spot.location.region}"/> </strong></p>
            <p><c:out value="${ spot.location.departementName }"/> (<c:out
                    value="${ spot.location.departementId }"/>) </p>
            <p><c:out value="${ spot.location.zipCode }"/> <c:out value="${ spot.location.cityName }"/></p>
            <p><c:out value="${ spot.nameSpot } "/> <c:out value="${ spot.nameArea } "/>
                <a href="${pageContext.request.contextPath}/escalade/topos?spotId=${spot.id}">Afficher les topos
                    correspondants</a></p>
            <ul>
                <c:forEach items="${spot.routes }" var="route">
                    <li>Nom de la voie: <c:out value="${ route.name }"/> Cotation: <c:out value="${ route.rating }"/>
                        Voie équipée: <c:out value="${ route.bolted }"/></li>
                </c:forEach>
            </ul>


        <c:forEach items="${ spot.comments }" var="comment">
            <p> Commentaire de <c:out value="${comment.member.nickname}"/> posté le <c:out value="${comment.date}"/></p>
            <c:out value="${comment.comment}"/>
        </c:forEach>

        </c:forEach>
        <br/>
    </ul>
</div>

</div>
<jsp:include page="../../resources/JspFragments/scriptsJS.jsp"></jsp:include>

</body>
</html>
