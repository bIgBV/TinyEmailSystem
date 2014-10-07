<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Output</title>

    <link type="text/css" href="${ pageContext.request.contextPath }/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <h1>Your Account details are :</h1>
    <%
        String username = (String) request.getAttribute("username");
        String password = (String) request.getAttribute("password");

    %>
</div>

<div class="container">
    First Name : <%= username %>
</div>
<div class="container">
    Password hash : <%= password %>
    <hr>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.js"></script>
</body>
</html>