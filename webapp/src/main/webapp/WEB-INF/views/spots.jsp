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

        
        <c:forEach items="${ resultLocations }" var="location" varStatus = "status">

            <c:if test="${status.first}">
                <p> <strong> <c:out value="${ location.region}" /> </strong></p>
            </c:if>

            <c:set var="message" value="Salut les zéros !"/>

            <p> <c:out value="${ location.departementName }" /> (<c:out value="${ location.departementId }" />) </p>
            <p> <c:out value="${ location.zipCode }" />   <c:out value="${ location.cityName }" /> </p>

                <c:forEach items="${ location.spots }" var="spot">
                <p>  <c:out value="${ spot.nameSpot } " /> <c:out value="${ spot.nameArea } " />
                    <a href="${pageContext.request.contextPath}/escalade/topos?spotId=${spot.id}">Afficher les topos correspondants</a></p>


                        <ul>
                        <c:forEach items="${spot.routes }" var="route">
                        <li>Nom de la voie: <c:out value="${ route.name }" /> Cotation: <c:out value="${ route.rating }" /> Voie équipée: <c:out value="${ route.bolted }" /></li>
                        </c:forEach>
                        </ul>
                 </c:forEach>
            <br/>
        </c:forEach>

    </ul>
</div>



<%--<ul>--%>
    <%--<c:forEach items="${ listSpots }" var="spot">--%>
        <%--<li>--%>
        <%--<c:out value="${ spot.nameSpot } " />--%>
        <%--<c:out value="${ spot.nameArea }" />--%>

        <%--<c:forEach items="${ spot.routes }" var="route">--%>
            <%--<li>--%>
                <%--<p>Nom de la voie: <c:out value="${ route.name }" /> </p>--%>
                <%--<p>Cotation: <c:out value="${ route.rating }" /> </p>--%>
            <%--</li>--%>
        <%--</c:forEach>--%>

        <%--</li>--%>
    <%--</c:forEach>--%>
<%--</ul>--%>












</body>
</html>
