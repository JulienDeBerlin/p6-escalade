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
    <meta charset="utf-8" />
    <title>High</title>
</head>
<body>
<p>VOICI LA LISTE DES SITES CORRESPONDANT À LA RECHERCHE</p>


<div>
    <ul>


        <c:forEach items="${ resultLocations }" var="location" varStatus="status">

            <c:if test="${status.first}">
            <p><strong> <c:out value="${ location.region}"/> </strong></p>
            </c:if>

        <p><c:out value="${ location.departementName }"/> (<c:out value="${ location.departementId }"/>) </p>
        <p><c:out value="${ location.zipCode }"/> <c:out value="${ location.cityName }"/></p>

            <c:forEach items="${ location.spots }" var="spot">
            <p><c:out value="${ spot.nameSpot } "/> <c:out value="${ spot.nameArea } "/>
                <a href="${pageContext.request.contextPath}/escalade/topos?spotId=${spot.id}">Afficher les topos
                correspondants</a></p>

            <c:forEach items="${spot.routes }" var="route">
                <ul>
                <li>Nom de la voie: <c:out value="${ route.name }"/> Cotation: <c:out value="${ route.rating }"/> Voie
                    équipée: <c:out value="${ route.bolted }"/></li>
                </ul>
            </c:forEach>


            <c:forEach items="${ spot.comments }" var="comment">
                 <p> Commentaire de <c:out value="${comment.member.nickname}"/> posté le <c:out value="${comment.date}"/></p>
                <c:out value="${comment.comment}"/>
            </c:forEach>

        </c:forEach>
        <br/>
        </c:forEach>

    </ul>
</div>


</body>
</html>
