<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 31/01/2019
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title>High</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/css/bootstrap.min.css"
          rel="stylesheet"/>


    <style type="text/css">
        .jumbotron-fluid {
            background-image: url("<c:url value="/resources/img/hauteMontage.jpg" />");
        }
    </style>
</head>

<body>


<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">MENU</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" href="#">Home <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="#">Les spots</a>
                <a class="nav-item nav-link" href="#">Les topos</a>
                <a class="nav-item nav-link" href="#">Contribuez</a>
                <a class="nav-item nav-link" href="#">Login</a>
            </div>
        </div>
    </nav>

    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">HIGH</h1>
            <p class="lead">La plateforme collaborative pour les passionnés d'escalade</p>
        </div>
    </div>

    <p>LE PROJET</p>
    <div class="row">
        <section id="block1" class="col-lg-6">
            <p>Damit Ihr indess erkennt, woher dieser ganze Irrthum gekommen ist,
                und weshalb man die Lust anklagt und den Schmerz lobet, so will ich
                Euch Alles eröffnen und auseinander setzen, was jener Begründer der Wahrheit
        </section>

        <section id="block2" class="col-lg-6">
            <p>Damit Ihr indess erkennt, woher dieser ganze Irrthum gekommen ist,
                und weshalb man die Lust anklagt und den Schmerz lobet, so will ich
        </section>

    </div>

    <p>RECHERCHER UN SITE</p>

    <form method="post" action="${pageContext.request.contextPath}/escalade/spots">

        <div class="form-group">
            <label for="locationInput">Localisation</label>
            <input type="text" name="locationInput" class="form-control" id="locationInput" placeholder="Région, département, commune, site...">
        </div>

        <div class="form-group">
            <label for="level">Cotation minimum</label>
            <select class="form-control" id="level" name="rating">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
                <option>9</option>
            </select>
        </div>

        <div class="form-check">
            <input type="checkbox" name= "onlySpotsWithBoltedRoutes" id="onlySpotsWithBoltedRoutes" class="form-check-input" >
            <label class="form-check-label" for="onlySpotsWithBoltedRoutes">
                Uniquement les sites avec des voies équipées
            </label>
        </div>


        <button type="submit" class="btn btn-primary">Submit</button>

    </form>

    <a href="${pageContext.request.contextPath}/escalade/test/autocomplete">TESTER L'AUTOCOMPLETION</a>

</div>


<script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"> </script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/js/bootstrap.min.js"> </script>

</body>

</html>
