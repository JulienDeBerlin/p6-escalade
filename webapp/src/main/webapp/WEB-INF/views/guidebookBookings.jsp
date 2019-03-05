<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 14/02/2019
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><html>

<head>
    <meta charset="UTF-8" />
    <title>High</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/css/bootstrap.min.css"
          rel="stylesheet"/>
</head>

<body>

<style type="text/css">
    body {padding-top: 70px;}
    h1 {padding-top: 50px;}
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
                <%--<li class="nav-item">--%>
                <%--<a class="nav-item nav-link active" href="#leProjet">Home <span class="sr-only">(current)</span></a>--%>
                <%--</li>--%>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/redirect?anchor=lesSpots" >Les spots</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/redirect?anchor=lesTopos">Les topos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/redirect?anchor=Contribuez">Contribuez</a>
                </li>
            </ul>

            <ul class="navbar-nav" >
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
                        <a class="nav-item nav-link active" >Mon compte</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/logout">Se déconnecter</a>
                    </li>
                </c:if>
            </ul>


        </div>
    </nav>

<h1>Gestion des réservations</h1>
<p> Titre du topo: ${selectedGuidebook.name}
    <br/>Auteur: ${selectedGuidebook.firstnameAuthor} ${selectedGuidebook.surnameAuthor}</p>


<h3>Liste des réservations actuelles</h3>

<table class="table">
    <thead>
    <tr>
        <th scope="col">Nom de l'emprunteur</th>
        <th scope="col">Du</th>
        <th scope="col">Au</th>
        <th scope="col">Email</th>
        <th scope="col">Téléphone</th>
        <th scope="col">Modifier</th>
        <th scope="col">Supprimer</th>

    </tr>
    </thead>

    <tbody>
    <c:forEach items="${privateGuidebook.bookings }" var="booking">
        <tr>
            <td>${ booking.bookedBy}</td>
            <td>${ booking.dateFrom}</td>
            <td>${ booking.dateUntil} </td>
            <td>${ booking.email} </td>
            <td>${ booking.phone} </td>
            <td><a href=""> <img src="${pageContext.request.contextPath}/resources/img/modify.png"
                                                                                                                                     alt="réservation"/> </a></td>
            <td><a href=""> <img src="${pageContext.request.contextPath}/resources/img/delete.png" alt="delete"/>
            </a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<h3>Ajouter une nouvelle réservation</h3>

<form method="post" action="${pageContext.request.contextPath}/escalade/memberArea/librairy/bookings">

    <div class="form-group">
        <label for="booked_by">Emprunteur</label>
        <input type="text" name="booked_by" class="form-control" id="booked_by" required>
    </div>
    <div class="form-group">
        <label for="date_from">Date début</label>
        <input type="date" name="date_from" class="form-control" id="date_from" required>
    </div>
    <div class="form-group">
        <label for="date_until">Date fin</label>
        <input type="date" name="date_until" class="form-control" id="date_until" required>
    </div>
    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" name="email" class="form-control" id="email">
    </div>
    <div class="form-group">
        <label for="phone">Phone</label>
        <input type="tel" name="phone" class="form-control" id="phone">
    </div>

    <button type="submit" class="btn btn-primary">Valider la nouvelle réservation</button>

</form>

</div>

<script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"> </script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/js/bootstrap.min.js"> </script>

<script>
    $('.navbar-nav>li>a').on('click', function(){
        $('.navbar-collapse').collapse('hide');
    });
</script>

</body>

</html>
