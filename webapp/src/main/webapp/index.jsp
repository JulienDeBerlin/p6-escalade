<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 31/01/2019
  Time: 15:25
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
    .jumbotron-fluid {
        background-image: url("<c:url value="/resources/img/hauteMontage.jpg" />");
    }

    body {
        padding-top: 70px;
    }

    h1 {
        padding-top: 50px;
    }
</style>

<p id="home"></p>

<div class="container">

    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top ">
        <a class="navbar-brand" href=#>HOME</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-item nav-link" href="#lesSpots">Les spots</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="#lesTopos">Les topos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="#Contribuez">Contribuez</a>
                </li>

                <c:if test="${user.email=='superadmin@admin.fr'}">
                    <li class="nav-item">
                        <a class="nav-item nav-link" href="#Moderation">Moderation</a>
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

    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <p class="display-4">HIGH</p>
            <p class="lead">La plateforme collaborative pour les passionnés d'escalade</p>
        </div>
    </div>

    <c:if test="${message == 'memberAccountDeleted'}">
        <p style="color: green">Le compte a bien été supprimé </p>
    </c:if>



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


    <c:if test="${alert=='notFound'}">
        <p style="color: red"> Attention, la localisation doit être choisie parmi la liste des propositions
        </p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/escalade/spots">

        <div class="form-group">
            <label for="locationInput">Localisation</label>
            <input type="text" name="locationInput" class="form-control" id="locationInput"
                   aria-describedby="inputLocationHelp" required
                   placeholder="Région, département, commune">
            <small id="inputLocationHelp" class="form-text text-muted">Les lieux disponibles s'affichent automatiquement
                lors de la saisie du texte. Choisissez parmi les propositions.
            </small>

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
            <input type="checkbox" name="onlySpotsWithBoltedRoutes" id="onlySpotsWithBoltedRoutes"
                   class="form-check-input">
            <label class="form-check-label" for="onlySpotsWithBoltedRoutes">
                Afficher uniquement les voies équipées
            </label>
        </div>
        <br/>
        <button type="submit" class="btn btn-primary">Chercher</button>

    </form>

    <h1 id="lesTopos">RECHERCHER DES TOPOS</h1>

    <c:if test="${alertTopo=='notFound'}">
        <p style="color: red"> Attention, la localisation doit être choisie parmi la liste des propositions
        </p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/escalade/topos">

        <div class="form-group">
            <label for="locationInputForTopo">Localisation</label>
            <input type="text" name="locationInputForTopo" class="form-control" id="locationInputForTopo"
                   placeholder="Région, département, commune" aria-describedby="inputLocationForTopoHelp">
            <small id="inputLocationForTopoHelp" class="form-text text-muted">Les lieux pour lesquels des topos sont
                répertoriés s'affichent automatiquement lors de la saisie du texte. Choisissez parmi les propositions.
            </small>
        </div>

        <div class="form-check">
            <input type="checkbox" name="loanRequired" id="loanRequired" class="form-check-input">
            <label class="form-check-label" for="loanRequired">
                Afficher uniquement les topos proposés au prêt par nos membres pour nos membres
            </label>
        </div>
        <br/>
        <button type="submit" class="btn btn-primary">Chercher</button>

    </form>


    <h1 id="Contribuez">CONTRIBUEZ!</h1>

    <div>
        <a href="${pageContext.request.contextPath}/escalade/addcontent/spot/checkCityInput">Référencez un spot ou une
            voie</a>
    </div>

    <div>
        <a href="${pageContext.request.contextPath}/escalade/addcontent/guidebook">Référencez un topo</a>
    </div>

    <div>
        <a href="${pageContext.request.contextPath}/escalade/memberArea/librairy">Proposez un topo au prêt</a>
    </div>


    <c:if test="${user.email=='superadmin@admin.fr'}">


        <h1 id="Moderation">MODERATION</h1>
        <div>
            <a href="${pageContext.request.contextPath}/escalade/admin/guidebooks">Modifier/supprimer un topo</a>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/escalade/admin/spots">Modifier/supprimer un site ou une voie</a>
        </div>
    </c:if>
    <br/>
    <br/>
    <br/>
    <br/>

</div>


<script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<script>
    $('.navbar-nav>li>a').on('click', function () {
        $('.navbar-collapse').collapse('hide');
    });
</script>

<script>
    $(function () {
        $("#locationInput, #locationInputForTopo").autocomplete({
            minLength: 2,
            source: '${pageContext.request.contextPath}/escalade/get_location_list',

            change: function (event, ui) {
                if (!ui.item) {
                    this.value = '';
                }
            }

        });
    });


</script>


</body>

</html>
