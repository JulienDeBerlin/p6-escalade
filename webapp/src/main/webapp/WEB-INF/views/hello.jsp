<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019-01-29
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Hello</title>
    <link href="/webjars/bootstrap/4.2.1/css/bootstrap.min.css" rel="stylesheet"/>

</head>
<body>
<p>Hello guys</p>
<%--<img src="${pageContext.request.contextPath}/resources/img/pictureHome.jpg" alt="montagnes">--%>
<%--<img src="/resources/img/pictureHome.jpg" alt="montagnes"/>--%>
<img src="<c:url value="/resources/img/hauteMontage.jpg" />" alt="montagnes"/>


<script src="/webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</body>
</html>
