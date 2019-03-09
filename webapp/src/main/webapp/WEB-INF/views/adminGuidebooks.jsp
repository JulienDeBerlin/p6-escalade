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

    <h1>Modifier ou supprimer un topo </h1>
    <br/>

    <h2>Étape 1: Saisie du code ISBN13</h2>

    <form method="get" action="${pageContext.request.contextPath}/escalade/admin/guidebooks/isbn">

        <div class="form-group">
            <label for="isbn13">Saisissez le numéro ISBN13 du topo</label>
            <input type="text" name="isbn13" class="form-control" id="isbn13" aria-describedby="helpIsbn"
                   pattern="\d{13}" required>
            <small id="helpIsbn" class="form-text text-muted">Combinaison de 13 chiffres, sans tirets</small>
            <small></small>
        </div>

        <button type="submit" class="btn btn-primary">Valider</button>
    </form>


    <c:if test="${message == 'guidebookDeleted' }">
        <p style="color: green"> Le topo a bien été supprimé.</p>
    </c:if>


    <c:if test="${message == 'notFound' }">
        <p style="color: red"> Ce numéro ISBN ne correspond à aucun topo référencé.</p>
    </c:if>


    <c:if test="${ selectedGuidebook != null }">

        <h2>Étape 2: référencement du topo</h2>

        <h4>Modification des champs: </h4>

        <form method="post" action="${pageContext.request.contextPath}/escalade/admin/guidebooks/update">

            <div class="form-group">
                <label for="isbn"> Numéro ISBN13 du topo</label>
                <input type="number" name="isbn13" class="form-control" id="isbn" value="${selectedGuidebook.isbn13}"
                       readonly>
            </div>

            <div class="form-group">
                <label for="name">Titre de l'ouvrage</label>
                <input type="text" name="name" class="form-control" id="name" value="${selectedGuidebook.name}"
                       required>
            </div>

            <div class="form-group">
                <label for="firstnameAuthor">Prénom de l'auteur</label>
                <input type="text" name="firstnameAuthor" class="form-control" id="firstnameAuthor"
                       value="${selectedGuidebook.firstnameAuthor}" required>
            </div>

            <div class="form-group">
                <label for="surnameAuthor">Nom de l'auteur</label>
                <input type="text" name="surnameAuthor" class="form-control" id="surnameAuthor"
                       value="${selectedGuidebook.surnameAuthor}" required>
            </div>

            <div class="form-group">
                <label for="yearPublication">Année de publication</label>
                <input type="number" name="yearPublication" class="form-control" id="yearPublication"
                       value="${selectedGuidebook.yearPublication}" required>
            </div>

            <div class="form-group">
                <label for="publisher">Éditeur</label>
                <input type="text" name="publisher" class="form-control" id="publisher"
                       value="${selectedGuidebook.publisher}" required>
            </div>

            <div class="form-group">
                <label for="language">Langue</label>
                <input type="text" name="language" class="form-control" id="language"
                       value="${selectedGuidebook.language}" required>
            </div>

            <div>
                <label for="summary">Présentation de l'ouvrage:</label>
                <textarea class="form-control" rows="5" id="summary" name="summary" maxlength="1000"
                          required>${selectedGuidebook.summary}</textarea>
            </div>

            <button type="submit" class="btn btn-primary">Modifier</button>
        </form>

        <c:if test="${message == 'guidebookUpdated' }">
            <p style="color: green"> Le topo a bien été modifié.</p>

        </c:if>


        <h4>Suppression du topo: </h4>

        <form method="post" action="${pageContext.request.contextPath}/escalade/admin/guidebooks/delete?isbn13=${selectedGuidebook.isbn13}">

            <div class="form-check">
                <input type="checkbox" name="deleteGuidebook" id="deleteGuidebook"
                       class="form-check-input">
                <label class="form-check-label" for="deleteGuidebook" style="color: red">
                    Supprimer le topo. Attention, cette action est irréversible!
                </label>
            </div>

            <button type="submit" class="btn btn-danger">Supprimer</button>


        </form>


        <br/>

        <h2 id="etape3">Étape 3: Valider les sites couverts par le topo </h2>

        <script>
            $("html, body").animate({scrollTop: $('#etape3').offset().top}, 1000);
        </script>


        <h4>Ci-dessous la liste des sites actuellement associés au topo:</h4>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Nom du site</th>
                <th scope="col">Secteur</th>
                <th scope="col">Commune</th>
                <th scope="col">Département</th>
                <th scope="col">Supprimer</th>
            </tr>
            </thead>


            <tbody>
            <c:forEach items="${ selectedGuidebook.spots}" var="spot">
                <tr>
                    <th>${ spot.nameSpot  }</th>
                    <th>${ spot.nameArea  }</th>
                    <th>${ spot.location.cityName } </th>
                    <th>${ spot.location.departementName} (${spot.location.departementId})</th>
                    <td><a href="${pageContext.request.contextPath}/escalade/admin/guidebooks/deleteLinkGuidebookSpot?isbn13=${selectedGuidebook.isbn13}&spotId=${spot.id}">
                        <img src="${pageContext.request.contextPath}/resources/img/delete.png" alt="delete"/> </a></td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <c:if test="${message == 'spotDeleted' }">
            <p style="color: green"> Le spot a bien été supprimé.</p>

        </c:if>


        <h4>Compléter la liste</h4>

        <p> Pour cela vous devez d'abord vous assurer que les sites que vous souhaitez associer au topo sont référencés
            dans la base de
            données et si ce n'est pas le cas les ajouter. <a
                    href=${pageContext.request.contextPath}/escalade/addcontent/spot
                    target="_blank">Cela ce passe ici.</a>


        <p>Ensuite il suffit de saisir une localité et de sélectionner les sites à associer parmi la liste des
            propositions. </p>

        <c:if test="${alert=='notFound'}">
            <p style="color: red"> Attention, la localisation doit être choisie parmi la liste des propositions
            </p>
        </c:if>

        <form method="get" action="${pageContext.request.contextPath}/escalade/spotsForGuidebook">

            <div class="form-group">
                <label for="locationSpotsForGuidebook">Entrer la localité des sites à associer au topo</label>
                <input type="text" name="locationSpotsForGuidebook" class="form-control" id="locationSpotsForGuidebook"
                       placeholder="Région, département, commune">
            </div>

            <button type="submit" class="btn btn-primary">Valider</button>
        </form>


        <c:if test="${alert=='noSpot'}">
            <p style="color: red">La recherche n'a donné aucun résultat</p>
        </c:if>

        <c:if test="${alert=='ok'}">

            <form method="post" action="${pageContext.request.contextPath}/escalade/spotsForGuidebook">

                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Sélection</th>
                        <th scope="col">Nom du site</th>
                        <th scope="col">Secteur</th>
                        <th scope="col">Commune</th>
                        <th scope="col">Département</th>
                    </tr>
                    </thead>


                    <tbody>
                    <c:forEach items="${ listMatchingLocations}" var="location">

                        <c:if test="${empty location.spots}" var="noSpots" scope="session"/>

                        <c:forEach items="${ location.spots }" var="spot">
                            <tr>
                                <th scope="col">
                                    <div class="checkbox">
                                        <input type="checkbox" name="selectedSpots" value="${spot.id}">
                                    </div>
                                </th>
                                <th>${ spot.nameSpot  }</th>
                                <th>${ spot.nameArea  }</th>
                                <th>${ location.cityName } </th>
                                <th>${ location.departementName} (${location.departementId})</th>
                            </tr>
                        </c:forEach>
                    </c:forEach>

                    </tbody>
                </table>
                <button type="submit" class="btn btn-primary">Associer les sites sélectionnés au topo</button>
            </form>
        </c:if>

    </c:if>

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
