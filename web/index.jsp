<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Input</title>

    <link type="text/css" href="${ pageContext.request.contextPath }/css/bootstrap.css" rel="stylesheet"/>


</head>
<body style="width : 75%;margin: auto;">
<div class="header">
    <h1>Tiny Email System</h1>
</div>
<div class="container">

    <h1>Register</h1>
    <form role="form" action="/register" method="post">
        <div class="form-group"	>
            <label for="userName">Username</label>
            <input class="form-control" id="userName" placeholder="Enter your name" type="text" name="username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input class="form-control" id="password" placeholder="Enter your passowrd" type="password" name="password">

        </div>
        <button type="submit" class="btn btn-primary ">Submit</button>
    </form>
</div>
<%
    String error = (String)request.getAttribute("error");
    pageContext.setAttribute("error",error);
%>

<c:set var="error" value="${error}"/>
<c:choose>
    <c:when test="${error != null}">
        <div class="bg-warning">
            <%= error%>
        </div>
    </c:when>
    <c:when test="${error == null }">
        <div>
                <%--Empty div--%>
        </div>
    </c:when>
</c:choose>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.js"></script>

</body>
</html>