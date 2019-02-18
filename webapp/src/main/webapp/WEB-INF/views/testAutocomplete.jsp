<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 08/02/2019
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang = "en">
<head>
    <meta charset = "utf-8">
    <title>jQuery UI Autocomplete functionality</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.2.1/css/bootstrap.min.css"
          rel="stylesheet"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <!-- Javascript -->
    <script>
        $(function() {
            // var availableTutorials  =  [
            //     "Azae",
            //     "ActionScript",
            //     "Bootstrap",
            //     "C",
            //     "C++",
            // ];
            $( "#automplete-1" ).autocomplete({
                source: '${pageContext. request. contextPath}/escalade/get_location_list'});
        });
    </script>
</head>

<body>
<!-- HTML -->
<div class = "ui-widget">
    <p>Type "a" or "s"</p>
    <label for = "automplete-1">Tags: </label>
    <input id = "automplete-1">
</div>
</body>
</html>