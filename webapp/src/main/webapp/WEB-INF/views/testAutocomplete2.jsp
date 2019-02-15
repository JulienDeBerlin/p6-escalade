<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 08/02/2019
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>jQuery Bootstrap 3/4 Typeahead Plugin Demo</title>
    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <style>
        body { background-color:#fafafa; font-family:'Roboto';}
        .container { margin:150px auto; max-width:600px;}
    </style>
</head>

<body>



<div id="jquery-script-menu">
    <div class="jquery-script-center">
        <ul>
            <li><a href="https://www.jqueryscript.net/form/jQuery-Bootstrap-4-Typeahead-Plugin.html">Download This Plugin</a></li>
            <li><a href="https://www.jqueryscript.net/">Back To jQueryScript.Net</a></li>
        </ul>
        <div class="jquery-script-ads"><script type="text/javascript"><!--
        google_ad_client = "ca-pub-2783044520727903";
        /* jQuery_demo */
        google_ad_slot = "2780937993";
        google_ad_width = 728;
        google_ad_height = 90;
        //-->
        </script>
            <script type="text/javascript"
                    src="https://pagead2.googlesyndication.com/pagead/show_ads.js">
            </script>
        </div>
        <div class="jquery-script-clear"></div>
    </div>
</div>
<div class="container">
    <h1>jQuery Bootstrap 3/4 Typeahead Plugin Demo</h1>
    <p>Bienvenue ${user.nickname}!</p>
    <input class="typeahead form-control">
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
<script src="<c:url value="/resources/lib/autocomplete-plugin/bootstrap3-typeahead.js"/>"> </script>


<script>
    var $input = $(".typeahead");
    $input.typeahead({
        source: [
            {id: "someId1", name: "jQueryScript.Net XXX"},
            {id: "someId2", name: "Angularz"},
            {id: "someId3", name: "React Components"},
            {id: "someId4", name: "Vue.js Components"},
            {id: "someId5", name: "Native JavaScript"},
            {id: "someId6", name: "jQuery Plugins"},
            {id: "someId7", name: "Vanilla JavaScript"},
            {id: "someId8", name: "Banana"},
            {id: "someId9", name: "Angulara "},
        ],
        autoSelect: true
    });
    $input.change(function() {
        var current = $input.typeahead("getActive");
        if (current) {
            // Some item from your model is active!
            if (current.name == $input.val()) {
                // This means the exact match is found. Use toLowerCase() if you want case insensitive match.
            } else {
                // This means it is only a partial match, you can either add a new item
                // or take the active if you don't want new items
            }
        } else {
            // Nothing is active so it is a new value (or maybe empty value)
        }
    });
</script>
<script type="text/javascript">

    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-36251023-1']);
    _gaq.push(['_setDomainName', 'jqueryscript.net']);
    _gaq.push(['_trackPageview']);

    (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })();
</script>

<div class="container">
    <h1>Tentative d'auto-complétion avec liste importée</h1>
    <input class="typeahead2 form-control">
</div>


<script>

$.get("dataAutocomplete.json", function(data){
$(".typeahead2").typeahead({
source:url("<c:url value="/resources/js/dataAutocomplete.json" />")
});
},'json');

// $(".typeahead2").typeahead({
//
//     source: ["abbb", "edede", "eded"],
// })
</script>

</body>
</html>


