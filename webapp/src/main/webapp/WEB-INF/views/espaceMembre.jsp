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


<h1>ESPACE MEMBRE</h1>

    <h2>Coordonnées</h2>
<ul>
    <li>Prénom: ${user.firstName} (non public)</li>
    <li>Nom: ${user.surname} (non public)</li>
    <li>Pseudo: ${user.nickname} </li>
    <li>Email: ${user.email}</li>
    <li>Télephone: ${user.phone}</li>
    <li>Membre depuis: ${user.dateMembership}</li>
</ul>

<c:if test="${message!='password2different' && message!='ok'}">
<a href="${pageContext.request.contextPath}/escalade/login/resetPassword"> Changer le mot de passe</a>
</c:if>

<c:if test="${message=='password2different'}">
    <p style="color: red">Erreur de saisie, les mots de passe ne sont pas identiques!</p>
</c:if>

<c:if test="${message=='ok'}">
    <p style="color: green">Le mot de passe a bien été changé. </p>
</c:if>


<c:if test="${action=='resetPassword'}">


    <br/>

    <form method="post" action="${pageContext.request.contextPath}/escalade/login/resetPassword">

        <div class="form-group" >
            <label for="password1">Choisissez un mot de passe</label>
            <input type="password" name="password1" class="form-control" id="password1" placeholder="monadresse@exemple.fr">
        </div>

        <div class="form-group">
            <label for="password2">Saisissez à nouveau le mot de passe</label>
            <input type="password" name="password2" class="form-control" id="password2">
        </div>

        <button type="submit" class="btn btn-primary">Changer le mot de passe</button>
    </form>

</c:if>

    <h2>Liste des topos que je propose au prêt: </h2>

    <div>
        <ol>
            <c:forEach items="${guidebooksForLoan }" var="guidebook">
                <li><strong>Titre: <c:out value="${ guidebook.name }"/> </strong></li>
                <p> Nr. ISBN 13: <c:out value="${ guidebook.isbn13 }"/></p>
                <p> Éditeur: <c:out value="${ guidebook.publisher }"/></p>
                <p> Langue: <c:out value="${ guidebook.language}"/></p>
                <p> Summary: <c:out value="${ guidebook.summary }"/></p>
                <p> Auteur: <c:out value="${ guidebook.firstnameAuthor }"/> <c:out
                        value="${ guidebook.surnameAuthor }"/></p>
            </c:forEach>
        </ol>
    </div>











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