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


</body>
</html>
