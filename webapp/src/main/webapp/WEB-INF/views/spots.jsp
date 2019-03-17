<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 04/02/2019
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>High</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/css/bootstrap.min.css"
          rel="stylesheet"/>
</head>
<body>

<style type="text/css">
    body {
        padding-top: 70px;
    }

    h1 {
        padding-top: 50px;
    }
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

                <li class="nav-item">
                    <a class="nav-item nav-link active">Les spots</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link"
                       href="${pageContext.request.contextPath}/#lesTopos">Les topos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link"
                       href="${pageContext.request.contextPath}/#Contribuez">Contribuez</a>
                </li>
                <c:if test="${user.email=='superadmin@admin.fr'}">
                    <li class="nav-item">
                        <a class="nav-item nav-link"
                           href="${pageContext.request.contextPath}/#Moderation">Moderation</a>
                    </li>
                </c:if>
            </ul>

            <ul class="navbar-nav">
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
                        <a class="nav-item nav-link"
                           href="${pageContext.request.contextPath}/escalade/login/espaceMembre">Espace Membre</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/logout">Se
                            déconnecter</a>
                    </li>
                </c:if>
            </ul>


        </div>
    </nav>

    <p><strong> LISTE DES SITES CORRESPONDANT À LA RECHERCHE <br/>
        ${locationInput}</strong></p>


    <c:forEach items="${ resultLocations }" var="location">

        <c:forEach items="${ location.spots }" var="spot" varStatus="status">

            <c:if test="${ not empty spot.routes}">

                <c:if test="${status.first}">

                    <div style="background: lightgray;margin-top: 2em;margin-bottom: -2em ">

                        <c:out value="${ location.departementName }"/> </br>
                        <c:out value="${ location.zipCode }"/> <c:out value="${ location.cityName }"/>
                    </div>

                    <br/>
                </c:if>


                <div style=" margin-top: 2em ">
                    <c:if test="${ not empty spot.guidebooks}">
                        <a href="${pageContext.request.contextPath}/escalade/topos?spotId=${spot.id}">
                            <img src="${pageContext.request.contextPath}/resources/img/bookshelf.png"
                                 title="Afficher les topos correspondants"></a>
                    </c:if>

                    <a href="${pageContext.request.contextPath}/escalade/toNewComment?idSpotToBeCommented=${spot.id}"
                       title="Ajouter un commentaire">
                        <img src="${pageContext.request.contextPath}/resources/img/chat.png"></a>

                    <span> <strong> <c:out value="site: ${spot.nameSpot}"/>
                    <c:if test="${ spot.nameArea != null}"><c:out value="/ secteur: ${spot.nameArea}"/>
                    </c:if></strong> </span>
                </div>


                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Nom de la voie</th>
                        <th scope="col">Cotation</th>
                        <th scope="col">Longueur</th>
                        <th scope="col">Voie équipée</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${spot.routes}" var="route">
                        <tr>
                            <td>${route.name}</td>
                            <td>${route.rating}</td>
                            <td>${route.indexPitch}/${route.nbPitch} </td>
                            <td><c:if test="${route.bolted==true}">oui</c:if>
                                <c:if test="${route.bolted==false}">non</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>


                <div>
                    <c:if test="${ idSpotToBeCommented == spot.id}">
                        <form method="post" action="${pageContext.request.contextPath}/escalade/addComment">
                            <div class="form-group">
                                <label for="comment">Commentaire:</label>
                                <textarea class="form-control" rows="5" id="comment" name="comment"
                                          autofocus></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Valider le commentaire</button>
                        </form>
                    </c:if>

                    <c:forEach items="${ spot.comments }" var="comment">

                        <div>
                            <c:if test="${user.email=='superadmin@admin.fr'}">
                                <a class="nav-item nav-link"
                                   href="${pageContext.request.contextPath}/escalade/admin/deleteComment?commentId=${comment.id}">
                                    <img style="float: left"
                                         src="${pageContext.request.contextPath}/resources/img/delete.png"
                                         alt="delete" title="Supprimer le commentaire"/></a>
                            </c:if>

                            <small class="text-muted"><c:out value=" ${comment.member.nickname}"/>,
                                <javatime:format value="${comment.date}" pattern="dd/MM/uuuu"/></small>

                            <p class="font-italic">
                                <c:out value="\"${comment.comment}\""/>
                            </p>
                        </div>

                    </c:forEach>
                </div>

            </c:if>

        </c:forEach>

    </c:forEach>


</div>

<jsp:include page="../../resources/JspFragments/scriptsJS.jsp"></jsp:include>

</body>
</html>
