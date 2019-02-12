<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 12/02/2019
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>High</title>
</head>
<body>

<p>VOICI LA LISTE DES TOPOS CORRESPONDANT À LA RECHERCHE</p>

<%--<div>--%>
    <%--<ul>--%>
        <%--<c:forEach items="${ resultLocations }" var="location">--%>
            <%--<p> <mark> ${ location.region} </mark> </p>--%>

            <%--<c:forEach items="${ location.spots }" var="spot">--%>
                <%--<ul>--%>
                    <%--<c:forEach items="${spot.guidebooks }" var="guidebook">--%>
                        <%--<li> Nr. ISBN 13: <c:out value="${ guidebook.isbn13 }" /> </li>--%>
                        <%--<li> Titre: <c:out value="${ guidebook.name }" /> </li>--%>
                        <%--<li> Éditeur: <c:out value="${ guidebook.publisher }" /> </li>--%>
                        <%--<li> Langue: <c:out value="${ guidebook.language}" /> </li>--%>
                        <%--<li> Summary: <c:out value="${ guidebook.summary }" /> </li>--%>
                        <%--<li> Auteur: <c:out value="${ guidebook.firstnameAuthor }" /> <c:out value="${ guidebook.surnameAuthor }" /></li>--%>
                        <%--<br/>--%>
                        <%--<br/>--%>
                    <%--</c:forEach>--%>

                <%--</ul>--%>
            <%--</c:forEach>--%>

        <%--</c:forEach>--%>

    <%--</ul>--%>
<%--</div>--%>

<div>
    <ul>
        <c:forEach items="${ resultLocations }" var="location">
            <p> <mark> ${ location.region} </mark> </p>

            <c:forEach items="${ location.spots }" var="spot">
                <ul>
                    <c:forEach items="${spot.guidebooks }" var="guidebook">
                        <li> Nr. ISBN 13: <c:out value="${ guidebook.isbn13 }" /> </li>
                        <li> Titre: <c:out value="${ guidebook.name }" /> </li>
                        <li> Éditeur: <c:out value="${ guidebook.publisher }" /> </li>
                        <li> Langue: <c:out value="${ guidebook.language}" /> </li>
                        <li> Summary: <c:out value="${ guidebook.summary }" /> </li>
                        <li> Auteur: <c:out value="${ guidebook.firstnameAuthor }" /> <c:out value="${ guidebook.surnameAuthor }" /></li>
                        <br/>
                        <br/>
                    </c:forEach>

                </ul>
            </c:forEach>

        </c:forEach>

    </ul>
</div>




</body>
</html>
