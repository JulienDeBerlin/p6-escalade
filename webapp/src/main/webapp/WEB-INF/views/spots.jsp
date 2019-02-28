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
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/login/espaceMembre">Mon compte</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/logout">Se déconnecter</a>
                    </li>
                </c:if>
            </ul>


        </div>
    </nav>

<p>VOICI LA LISTE DES SITES CORRESPONDANT À LA RECHERCHE</p>


    <div>
    <ul>


        <c:forEach items="${ resultLocations }" var="location" varStatus="status">
            <div>
            <c:if test="${status.first}">
            <p> <strong> <c:out value="${ location.region}"/> </strong> </p>
            </c:if>

                <c:out value="${ location.departementName }"/> (<c:out value="${ location.departementId }"/>) </br>
                <c:out value="${ location.zipCode }"/> <c:out value="${ location.cityName }"/>
            </div>

            <p>
            <br/>
            <c:forEach items="${ location.spots }" var="spot">
            <c:out value="${ spot.nameSpot } "/> <c:out value="${ spot.nameArea } "/>
                <a href="${pageContext.request.contextPath}/escalade/topos?spotId=${spot.id}">Afficher les topos
                correspondants</a>
            </p>

                <div>
                    <c:forEach items="${spot.routes }" var="route">
                        <ul>
                            <li>Nom de la voie: <c:out value="${ route.name }"/>
                                Cotation: <c:out value="${ route.rating }"/>
                                Voie équipée: <c:out value="${ route.bolted }"/>
                            </li>
                        </ul>
                    </c:forEach>
                </div>

                <div>
                    <a href="${pageContext.request.contextPath}/escalade/toNewComment?IdSpotToBeCommented=${spot.id}"> Ajouter un commentaire</a>

                    <c:if test="${ selectedSpot.id==spot.id}">
                        <form method="post" action="${pageContext.request.contextPath}/escalade/addComment">
                            <div class="form-group">
                                <label for="comment">Commentaire:</label>
                                <textarea class="form-control" rows="5" id="comment" name="comment" autofocus></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Valider le commentaire</button>
                        </form>
                    </c:if>


                    <c:forEach items="${ spot.comments }" var="comment">
                        <br/>
                        <p>Commentaire de <c:out value="${comment.member.nickname}"/> posté le <c:out
                                value="${comment.date}"/> </p>
                          <p>  <c:out value="${comment.comment}"/> </p>
                    </c:forEach>
                </div>

            </c:forEach>
        <br/>
        </c:forEach>

    </ul>
</div>
</div>
<jsp:include page="../../resources/JspFragments/scriptsJS.jsp"></jsp:include>

</body>
</html>
