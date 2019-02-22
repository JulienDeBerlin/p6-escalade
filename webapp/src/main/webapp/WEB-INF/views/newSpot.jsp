<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 04/02/2019
  Time: 16:39
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
    <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
          rel = "stylesheet">

</head>
<body>

<style type="text/css">
    body {padding-top: 70px;}
    h1 {padding-top: 50px;}
</style>

<div class="container">

    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top ">
        <a class="navbar-brand"  href="${pageContext.request.contextPath}">HOME</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav mr-auto">

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
                        <a class="nav-item nav-link" href="">Mon compte</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/logout">Se déconnecter</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>

<h1>Ajouter un site ou une voie à la base de donnée</h1>
    <br/>

    <h2>Étape 1: choix de la localisation</h2>


    <form method="post" action="${pageContext.request.contextPath}/escalade/addcontent/spot">

        <div class="form-group">
            <label for="cityNameInput">Indiquez la commune dans laquelle se trouve le site</label>
            <input type="text" name="cityNameInput" class="form-control" id="cityNameInput"
                   placeholder="Chamonix-Mont-Blanc...">

            <input type="text" name="region" class="form-control" id="region" readonly>
            <input type="text" name="departementName" class="form-control" id="departementName" readonly>
            <input type="text" name="departementId" class="form-control" id="departementId" readonly>
            <input type="text" name="codePostal" class="form-control" id="codePostal" >

        </div>

        <button type="submit" class="btn btn-primary">Valider la localisation</button>
    </form>




    <div>
        <p>Vous avez sélectionné: </p>
        <ul>
            <li>${selectedLocation.cityName}</li>
            <li>${selectedLocation.region}</li>
            <li>${selectedLocation.departementName}</li>
            <li>${selectedLocation.departementId}</li>
        </ul>
    </div>


</div>


<script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"> </script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/js/bootstrap.min.js"> </script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>


    $(function () {

        $("#cityNameInput").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "https://geo.api.gouv.fr/communes?nom=" + request.term + "&fields=codesPostaux,departement,region",
                    dataType: "json",

                    data: {
                    },

                    success: function (data) {
                        response($.map(data, function (item) {
                            return {
                                label: (item.nom + ' (' + item.departement.nom + ')'),
                                value: item.nom,
                                departementName: item.departement.nom,
                                departementId: item.departement.code,
                                region: item.region.nom,
                                codesPostal: item.codesPostaux[0]
                            }

                        }));
                    },
                });
            },


            minLength: 3,
            focus: function (event, ui) {
                $("#cityNameInput").val(ui.item.value);
                return false;
            },

            select: function (event, ui) {
                $("#cityNameInput").val(ui.item.value);
                $("#region").val(ui.item.region);
                $("#departementName").val(ui.item.departementName);
                $("#departementId").val(ui.item.departementId);
                $("#codePostal").val(ui.item.codesPostal);

                return false;
            }

        })
    });

</script>


</body>
</html>
