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
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/login/espaceMembre">Espace Membre</a>
                </li>
                <li class="nav-item">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/escalade/logout">Se
                        déconnecter</a>
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
        <label for="cityNameInput">Indiquez la commune dans laquelle se trouve le site à ajouter ou compléter</label>
        <input type="text" name="cityNameInput" class="form-control" id="cityNameInput"
               placeholder="Chamonix-Mont-Blanc...">

        <input type="hidden" name="region" class="form-control" id="region">
        <input type="hidden" name="departementName" class="form-control" id="departementName">
        <input type="hidden" name="departementId" class="form-control" id="departementId">

        <label for="codePostal">Code postal</label>
        <input type="text" name="codePostal" class="form-control" id="codePostal">

    </div>

    <button type="submit" class="btn btn-primary">Valider la localisation</button>
</form>

<br/>

<c:if test="${step == 'step2' || step == 'step3'}">
    <h2>Étape 2</h2>

        <div>
            <strong>Localisation:</strong><br/>
            <c:out value="${ selectedLocation.region}"/>
            <c:out value="${ selectedLocation.departementName }"/> (<c:out value="${ selectedLocation.departementId }"/>) </br>
            <c:out value="${ selectedLocation.zipCode }"/> <c:out value="${ selectedLocation.cityName }"/>
        </div>

        <br/>

        <h4>Référencer un nouveau site/secteur</h4>

        <form method="post" action="${pageContext.request.contextPath}/escalade/addcontent/newSpot">
            <div class="form-group">

                <label for="nameSpot">Nom du site</label>
                <input type="text" name="nameSpot" class="form-control" id="nameSpot">

                <label for="nameArea">Nom du secteur (facultatif)</label>
                <input type="text" name="nameArea" class="form-control" id="nameArea">

                <label for="comment">Commentaire:</label>
                <textarea class="form-control" rows="5" id="comment" name="comment"></textarea>

            </div>
            <button type="submit" class="btn btn-primary">Ajouter un site/secteur</button>
        </form>

        <c:if test="${not empty selectedLocation.spots}">
        <h4>et/ou ajouter une voie à un site déjà référencé</h4>
        <form method="post" action="${pageContext.request.contextPath}/escalade/addcontent/formNewRoute">
            <div class="form-group">

                <label for="selectedSpot">Choisissez un site/secteur</label>
                <select name="selectedSpot" class="form-control" id="selectedSpot">
                    <c:forEach items="${selectedLocation.spots}" var="spot">
                        <option value=${spot.id}>
                            Site: ${spot.nameSpot} / secteur: ${spot.nameArea}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Ajouter une voie à ce site/secteur</button>
        </form>
        </c:if>

    </c:if>

    <br/>


    <c:if test="${step == 'step3'}">

        <h2>Étape 3: ajouter une route</h2>

        <h4></h4>

        <mark><strong>Site: </strong> <c:out value="${ selectedSpot.nameSpot } "/> <strong>secteur:
        </strong><c:out value="${ selectedSpot.nameArea } "/></mark>
        </p>

        <c:if test="${not empty selectedSpot.routes }">
        <p>Voie(s) déjà répertoriée(s):</p>
        </c:if>
        <div>
            <c:forEach items="${selectedSpot.routes }" var="route">
                <ul>
                    <li>Nom de la voie: <c:out value="${ route.name }"/>
                        Cotation: <c:out value="${ route.rating }"/>
                        Voie équipée: <c:out value="${ route.bolted }"/>
                    </li>
                </ul>
            </c:forEach>
        </div>
<br/>

        <c:if test="${message == 'ok'}">
            <p style="color: green"> La route a bien été ajoutée!</p> <br/>
        </c:if>
        <p>Ajouter une nouvelle route ou longueur pour ce site/secteur:</p>



        <form method="post" action="${pageContext.request.contextPath}/escalade/addcontent/newRoute">

            <div class="form-group">
                <label for="newRoute">Nom de la voie:</label>
                <input type="text" name="name" class="form-control" id="newRoute">

                <label for="newRoute">Nombre de longueurs: </label>
                <input type="number" name="nbPitch" class="form-control" id="nbPitch">

                <label for="indexPitch">Si plusieurs longueurs, indiquez la position de la longueur actuellement
                    référencée:</label>
                <input type="number" name="indexPitch" class="form-control" id="indexPitch">

                <label for="rating">Cotation:</label>
                <input type="text" name="rating" class="form-control" id="rating">

                <%--<label for="isBolted">Voie équipée?:</label>--%>
                <%--<select name="isBolted" class="form-control" id="isBolted">--%>
                    <%--<option value="oui" selected>oui</option>--%>
                    <%--<option value="non">non</option>--%>
                <%--</select>--%>

                <div class="form-check">
                    <input type="checkbox" name="isBolted" id="isBolted" class="form-check-input">
                    <label class="form-check-label" for="isBolted">
                        Voie équipée?:
                    </label>
                </div>

            </div>

            <button type="submit" class="btn btn-primary">Ajouter une voie à ce site/secteur</button>
        </form>


    </c:if>


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
                        data: {},

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
