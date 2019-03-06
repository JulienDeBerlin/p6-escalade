<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 12/02/2019
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <meta charset="UTF-8"/>
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
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/redirect?anchor=lesSpots">Les spots</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link active" >Les topos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/redirect?anchor=Contribuez">Contribuez</a>
                </li>
            </ul>

            <ul class="navbar-nav" >
                <c:if test="${empty user}">
                    <li class="nav-item">
                        <a class="nav-item nav-link">Login</a>
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


    <p>VOICI LA LISTE DES TOPOS CORRESPONDANT À LA RECHERCHE "${locationInputForTopo}"</p>

    <div>
        <ol>
            <c:forEach items="${guidebookListWithoutDuplicates }" var="guidebook">
                <li><strong>Titre: <c:out value="${ guidebook.name }"/> </strong></li>
                <p> Nr. ISBN 13: <c:out value="${ guidebook.isbn13 }"/></p>
                <p> Éditeur: <c:out value="${ guidebook.publisher }"/></p>
                <p> Langue: <c:out value="${ guidebook.language}"/></p>
                <p> Summary: <c:out value="${ guidebook.summary }"/></p>
                <p> Auteur: <c:out value="${ guidebook.firstnameAuthor }"/> <c:out
                        value="${ guidebook.surnameAuthor }"/></p>
                <a href="${pageContext.request.contextPath}/escalade/spots?guidebookId=${guidebook.id}">Afficher les
                sites correspondants</a> <br/>
                <a href="${pageContext.request.contextPath}/escalade/memberArea/librairy/loan?guidebookId=${guidebook.id}">Emprunter ce topo</a>
                <br/>
            </c:forEach>
        </ol>
    </div>

</div>

    <jsp:include page="../../resources/JspFragments/scriptsJS.jsp"></jsp:include>


</body>
</html>
