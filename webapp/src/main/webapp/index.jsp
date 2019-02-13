<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 31/01/2019
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <meta charset="UTF-8" />
    <title>High</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/css/bootstrap.min.css"
          rel="stylesheet"/>


    <style type="text/css">
        .jumbotron-fluid {
            background-image: url("<c:url value="/resources/img/hauteMontage.jpg" />");
        }

        body
        { padding-top: 70px; }

        h1
        {
            padding-top: 50px;
        }

    </style>
</head>

<body>


<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top ">
        <a class="navbar-brand" href="#">MENU</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                <a class="nav-item nav-link active" href="#leProjet">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                <a class="nav-item nav-link" href="#lesSpots" >Les spots</a>
                </li>
                <li class="nav-item">
                <a class="nav-item nav-link" href="#lesTopos">Les topos</a>
                </li>
                <li class="nav-item">
                <a class="nav-item nav-link" href="#Contribuez">Contribuez</a>
                </li>
                <li class="nav-item">
                <a class="nav-item nav-link" href="#" >Login</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <p class="display-4">HIGH</p>
            <p class="lead">La plateforme collaborative pour les passionnés d'escalade</p>
        </div>
    </div>



    <h1 id="leProjet">LE PROJET</h1>



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


    <h1 id="lesSpots">RECHERCHER DES SITES</h1>

    <form method="post" action="${pageContext.request.contextPath}/escalade/spots">

        <div class="form-group">
            <label for="locationInput">Localisation</label>
            <input type="text" name="locationInput" class="form-control" id="locationInput" placeholder="Région, département, commune, site...">
        </div>

        <div class="form-group">
            <label for="ratingMin">Cotation minimum</label>
            <select class="form-control" id="ratingMin" name="ratingMin">
                <option selected>1</option>
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

        <div class="form-group">
            <label for="ratingMax">Cotation maximum</label>
            <select class="form-control" id="ratingMax" name="ratingMax">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
                <option selected>9</option>
            </select>
        </div>


        <div class="form-check">
            <input type="checkbox" name= "onlySpotsWithBoltedRoutes" id="onlySpotsWithBoltedRoutes" class="form-check-input" >
            <label class="form-check-label" for="onlySpotsWithBoltedRoutes">
                Afficher uniquement les voies équipées
            </label>
        </div>


        <button type="submit" class="btn btn-primary">Submit</button>

    </form>

    <h1 id="lesTopos">RECHERCHER DES TOPOS</h1>

    <form method="post" action="${pageContext.request.contextPath}/escalade/topos">

        <div class="form-group">
            <label for="locationInputForTopo">Localisation</label>
            <input type="text" name="locationInputForTopo" class="form-control" id="locationInputForTopo" placeholder="Région, département, commune, site...">
        </div>

        <div class="form-check">
            <input type="checkbox" name= "loanRequired" id="loanRequired" class="form-check-input" >
            <label class="form-check-label" for="loanRequired">
                Afficher uniquement les topos proposés au prêt par nos membres pour nos membres
            </label>
        </div>


        <button type="submit" class="btn btn-primary">Submit</button>

    </form>


    <h1 id="Contribuez">CONTRIBUEZ!</h1>

    <div>
       <a href="">Référencez un spot ou une voie</a>
    </div>

    <div>
        <a href="">Référencez un topo</a>
    </div>

    <div>
        <a href="">Proposez un topo au prêt</a>
    </div>

    <br/>
    <br/>


    <a href="${pageContext.request.contextPath}/escalade/test/autocomplete">TESTER L'AUTOCOMPLETION</a>

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
