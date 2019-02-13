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
                    value="${ guidebook.surnameAuthor }"/></p> <br/>
            <br/>
        </c:forEach>
    </ol>
</div>


</body>
</html>
