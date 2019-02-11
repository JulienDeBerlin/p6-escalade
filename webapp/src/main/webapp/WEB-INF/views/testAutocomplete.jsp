<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 08/02/2019
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang = "en">
<head>
    <meta charset = "utf-8">
    <title>jQuery UI Autocomplete functionality</title>
    <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
          rel = "stylesheet">
    <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

    <!-- Javascript -->
    <script>
        $(function() {
            var availableTutorials  =  [
                "Azae",
                "ActionScript",
                "Bootstrap",
                "C",
                "C++",
            ];
            $( "#automplete-1" ).autocomplete({
                source: availableTutorials
            });
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