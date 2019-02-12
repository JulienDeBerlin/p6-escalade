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
<p>VOICI LA LISTE DES SITES CORRESPONDANT À LA RECHERCHE</p>


<div>
    <ul>

        <c:forEach items="${ resultLocations }" var="location">
        <p> <mark> ${ location.region} </mark> </p>

            <p> <c:out value="${ location.departementName }" /> (<c:out value="${ location.departementId }" />) </p>
            <p> <c:out value="${ location.zipCode }" />   <c:out value="${ location.cityName }" /> </p>

                <c:forEach items="${ location.spots }" var="spot">
                <p>  <c:out value="${ spot.nameSpot } " /> <c:out value="${ spot.nameArea } " /></p>

                        <ul>
                        <c:forEach items="${spot.routes }" var="route">
                        <li>Nom de la voie: <c:out value="${ route.name }" /> Cotation: <c:out value="${ route.rating }" /> Voie équipée: <c:out value="${ route.bolted }" /></li>
                        </c:forEach>
                        </ul>
                 </c:forEach>

            <br/>
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
