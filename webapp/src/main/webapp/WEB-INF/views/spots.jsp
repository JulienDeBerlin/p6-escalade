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


<ul>
    <c:forEach items="${ listSpots }" var="spot">
        <li><c:out value="${ spot.id }" />
            <c:out value="${ spot.nameSpot } " />
            <c:out value="${ spot.nameArea }" />
            <c:out value="${ spot.locationId }" />
        </li>
    </c:forEach>
</ul>




</body>
</html>
