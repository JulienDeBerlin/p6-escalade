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
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.autocomplete.min.js"></script>



    <!-- Javascript -->
    <script>
        $(function() {

            <%--$( '#automplete-1' ).autocomplete({--%>
            <%--serviceUrl:'${pageContext.request.contextPath}/escalade/get_location_list',--%>
            <%--dataType: jsonp,--%>
            <%--});--%>

            var countries =
                [
                    { value: 'Rhône-Alpes', data: { category: 'Region(s):' } },
                    { value: 'Bourgogne', data: { category: 'Region(s):' } },
                    { value: 'Occitanie', data: { category: 'Region(s):' } },
                    { value: 'Bretagne', data: { category: 'Region(s):' } },
                    { value: 'Savoie', data: { category: 'Département(s):' } },
                    { value: 'Corse', data: { category: 'Département(s):' } },
                    { value: 'Ain', data: { category: 'Département(s):' } },
                    { value: 'Corsicaca', data: { category: 'Commune(s):' } }
                ]

            $('#autocomplete').autocomplete({
                lookup: countries,
                groupBy: 'category',
                });
        });

    </script>

    <style>

    .autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
    .autocomplete-suggestion { padding: 2px 5px; white-space: nowrap; overflow: hidden; }
    .autocomplete-selected { background: #F0F0F0; }
    .autocomplete-suggestions strong { font-weight: normal; color: #3399FF; }
    .autocomplete-group { padding: 2px 5px; }
    .autocomplete-group strong { display: block; border-bottom: 1px solid #000;}
    </style>

</head>

<body>
<!-- HTML -->
<%--<div class = "autocomplete-suggestions">--%>
    <p>Type "a" or "s"</p>
    <label for = "autocomplete">Tags: </label>
    <input id = "autocomplete">
    <%--<div class="autocomplete-group"><strong>...</strong></div>--%>
    <%--<div class="autocomplete-suggestion autocomplete-selected">aaaaa</div>--%>
    <%--<div class="autocomplete-suggestion">bbbbb</div>--%>
    <%--<div class="autocomplete-suggestion">cccc</div>--%>
</div>







</body>
</html>