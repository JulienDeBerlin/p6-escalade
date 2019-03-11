<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 04/02/2019
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>High</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/css/bootstrap.min.css"
          rel="stylesheet"/>
    <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
          rel="stylesheet">

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
                    <a class="nav-item nav-link"
                       href="${pageContext.request.contextPath}/escalade/redirect?anchor=lesSpots">Les spots</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link"
                       href="${pageContext.request.contextPath}/escalade/redirect?anchor=lesTopos">Les topos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link"
                       href="${pageContext.request.contextPath}/escalade/redirect?anchor=Contribuez">Contribuez</a>
                </li>

                <c:if test="${user.email=='superadmin@admin.fr'}">
                    <li class="nav-item">
                        <a class="nav-item nav-link"
                           href="${pageContext.request.contextPath}/escalade/redirect?anchor=Moderation">Moderation</a>
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


    <h1>Emprunter un topo</h1>

    <p> Titre du topo: ${selectedGuidebook.name}
        <br/>Auteur: ${selectedGuidebook.firstnameAuthor} ${selectedGuidebook.surnameAuthor}</p>

    <form method="post" action="${pageContext.request.contextPath}/escalade/memberArea/librairy/loan/checkDates">

        <h3>Entrez ci-dessous les dates souhaitées de la réservation</h3>

        <c:if test="${message=='dateWrong'}">
            <p style="color: red"> Attention, la date de fin ne peut pas être antérieure à la date de début!</p>
        </c:if>


        <div class="form-group">
            <label for="date_from">Date début</label>
            <input type="date" name="date_from" class="form-control" id="date_from" min="" required>
        </div>
        <div class="form-group">
            <label for="date_until">Date fin</label>
            <input type="date" name="date_until" class="form-control" id="date_until" required>
        </div>

        <button type="submit" class="btn btn-primary">Valider la nouvelle réservation</button>

    </form>

    <c:if test="${privateGuidebooks!=null}">
        <h3>Pour la période indiquée, ce topo est disponible auprès des membres suivants:</h3>
    </c:if>

    <c:if test="${empty privateGuidebooks && privateGuidebooks!=null}">
        <p style="color: red"> Le topo n'est malheureusement pas disponible sur la période indiquée. </p>
    </c:if>

    <c:forEach items="${ privateGuidebooks }" var="ownerGuidebook">
        <p> ${ownerGuidebook.member.firstName} ${ownerGuidebook.member.surname}
            <br/>Contact:${ownerGuidebook.member.email} / Tel: ${ownerGuidebook.member.phone} </p>
    </c:forEach>


</div>


<script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<script>
    $(function () {
        $("#locationSpotsForGuidebook").autocomplete({
            minLength: 2,
            source: '${pageContext.request.contextPath}/escalade/get_location_list',
        });
    });

</script>


</body>
</html>
