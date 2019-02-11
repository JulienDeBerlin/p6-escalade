<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 04/02/2019
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>High</title>
</head>
<body>
<p>VOICI LA LISTE DE TOUS LES SPOTS</p>


<div>
    <c:forEach items="${ resultLocations }" var="location">
        <li>
            <c:out value="${ location.region } " />
            <c:out value="${ location.departementId }" />
            <c:out value="${ location.departementName }" />
            <c:out value="${ location.cityName }" />
            <c:out value="${ location.zipCode }" />

        <c:forEach items="${ resultLocation.spots }" var="spot">
            <c:out value="${ spot.nameSpot } " />
            <c:out value="${ spot.nameArea } " />

            <c:forEach items="${resultLocation.spots.routes }" var="route">
             <p>Nom de la voie: <c:out value="${ route.name }" /> </p>
             <p>Cotation: <c:out value="${ route.rating }" /> </p>
            </c:forEach>

        </li>
    </c:forEach>
</div>




</body>
</html>
