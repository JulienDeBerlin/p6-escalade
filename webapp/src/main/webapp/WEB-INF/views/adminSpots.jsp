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

    <h1>Modifier/supprimer un site ou une voie</h1>
    <br/>

    <h2>Étape 1: choix de la localisation</h2>


    <form method="post" action="${pageContext.request.contextPath}/escalade/admin/spots/locationInput">

        <div class="form-group">
            <label for="locationInput">Indiquez la commune dans laquelle se trouve le site à supprimer/modifier</label>
            <input type="text" name="locationInput" class="form-control" id="locationInput"
                   placeholder="Chamonix-Mont-Blanc..." required>
            <input type="hidden" name="step" value="step1">
        </div>

        <button type="submit" class="btn btn-primary">Valider la localisation</button>
    </form>

    <br/>

    <c:if test="${step == 'step2' || step == 'step3'}">
        <h2>Étape 2: choix d'un site</h2>

        <div>
            <strong>Localisation:</strong><br/>
            <c:out value="${locationInput}"/>
        </div>

        <br/>

        <form>
            <div class="form-row">

                <div class="col">
                    <input type="text" class="form-control" value="nom du site" readonly disabled>
                </div>
                <div class="col">
                    <input type="text" class="form-control" value="secteur" readonly disabled>
                </div>
                <div class="col">
                    <button type="submit" class="btn btn-primary" disabled>Modifier</button>
                </div>

                <div class="col">
                    <button type="submit" class="btn btn-success" formaction="" disabled>Les voies</button>
                </div>

                <div class="col">
                    <button type="submit" class="btn btn-danger" formaction="" disabled>Supprimer</button>
                </div>

            </div>

        </form>

        <c:forEach items="${selectedLocations }" var="location">
            <c:forEach items="${location.spots }" var="spot">
                <form method="post" action="${pageContext.request.contextPath}/escalade/admin/spots/update">
                    <div class="form-row">
                        <div class="col">
                            <input type="text" class="form-control" id="nameSpot" name="nameSpot"
                                   value="${spot.nameSpot}">
                            <input type="hidden" class="form-control" id="spotId" name="spotId" value="${spot.id}">
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" id="nameArea" name="nameArea"
                                   value="${spot.nameArea}">
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-primary">Modifier</button>
                        </div>

                        <div class="col">
                            <button type="submit" class="btn btn-success"
                                    formaction="${pageContext.request.contextPath}/escalade/admin/spots/accessRoute">Les
                                voies
                            </button>
                        </div>

                        <div class="col">
                            <button type="submit" class="btn btn-danger"
                                    formaction="${pageContext.request.contextPath}/escalade/admin/spots/delete">
                                Supprimer
                            </button>
                        </div>

                            <%--<div class="col">--%>
                            <%--<a href="${pageContext.request.contextPath}/escalade/memberArea/librairy/bookings/delete?bookingId=${booking.id}">--%>
                            <%--<img src="${pageContext.request.contextPath}/resources/img/delete.png" alt="delete"/>--%>
                            <%--</a>--%>
                            <%--</div>--%>

                    </div>
                </form>

            </c:forEach>
        </c:forEach>

    </c:if>

    <br/>


    <c:if test="${step =='step3'}">

        <h2>Étape 3: choix d'une voie</h2>

        <div>
            <strong>Site:</strong><br/>
            <c:out value="${selectedSpot.nameSpot} (secteur ${selectedSpot.nameArea})"/>
        </div>

        <br/>

        <form>
            <div class="form-row">

                <div class="col">
                    <input type="text" class="form-control" value="nom de la voie" readonly disabled>
                </div>
                <div class="col">
                    <input type="text" class="form-control" value="nb pitchs" readonly disabled>
                </div>
                <div class="col">
                    <input type="text" class="form-control" value="index pitch" readonly disabled>
                </div>
                <div class="col">
                    <input type="text" class="form-control" value="cotation" readonly disabled>
                </div>
                <div class="col">
                    <input type="text" class="form-control" value="voie équipée?" readonly disabled>
                </div>

                <div class="col">
                    <button type="submit" class="btn btn-primary" disabled>Modifier</button>
                </div>

                <div class="col">
                    <button type="submit" class="btn btn-danger" formaction="" disabled>Supprimer</button>
                </div>

            </div>

        </form>


        <c:forEach items="${selectedSpot.routes }" var="route">

            <form method="post" action="${pageContext.request.contextPath}/escalade/admin/routes/update">
                <div class="form-row">

                    <div class="col">
                        <input type="text" class="form-control" value="${route.name}" name="name">
                        <input type="hidden" class="form-control" value="${route.id}" name="id">

                    </div>
                    <div class="col">
                        <input type="text" class="form-control" value="${route.nbPitch}" name="nbPitch">
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" value="${route.indexPitch}" name="indexPitch">
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" value="${route.rating}" name="rating">
                    </div>
                    <div class="col">
                        <input type="checkbox" class="form-control"
                               <c:if test="${route.bolted==true}">checked</c:if> name="bolted">
                    </div>

                    <div class="col">
                        <button type="submit" class="btn btn-primary">Modifier</button>
                    </div>

                    <div class="col">
                        <button type="submit" class="btn btn-danger"
                                formaction="${pageContext.request.contextPath}/escalade/admin/routes/delete">Supprimer
                        </button>
                    </div>

                </div>
            </form>

        </c:forEach>

    </c:if>


</div>


<script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>

    $(function () {
        $("#locationInput").autocomplete({
            minLength: 2,
            source: '${pageContext.request.contextPath}/escalade/autocomplete/citiesForUpdateSpots'
        });
    });

</script>


</body>
</html>