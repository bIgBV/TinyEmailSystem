<%--
  Created by IntelliJ IDEA.
  User: home
  Date: 9/19/2014
  Time: 11:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link type="text/css" href="${ pageContext.request.contextPath }/css/bootstrap.css" rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title></title>
</head>
<body >
    <%
        String userName = (String)session.getAttribute("userId");
    %>
    <div class="container">
        <div class="row">
            <div class="navbar-inverse navbar-fixed-top" role="navigation">
                <div class="container">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="inbox.jsp">Tiny Email System</a>
                    </div>
                    <ul class="nav navbar-nav navbar-right">
                        <li id="item1"><a href="inbox.jsp">Inbox</a></li>
                        <li id="item2"><a href="compose.jsp">Compose email</a></li>
                        <li id="item3"><a href="sent.jsp">Sent email</a></li>
                        <li id="item5"><a href="logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <hr>
        <div class="container">
            <div class="col-md-12">
                <h4>Welcome <strong><%= userName %></strong></h4>
                <form role="form" action="messageSender" method="post">
                    <div class="form-group">
                        <label for="toAddress"><h4>To:</h4></label>
                        <input type="text" class="form-control" id="toAddress" placeholder="To address" name="toAddress">
                    </div>
                    <div class="form-group">
                        <label for="subjectField"><h4>Subject:</h4></label>
                        <input type="text" class="form-control" id="subjectField" placeholder="Subject" name="subjectField">
                    </div>
                    <div class="form-group">
                        <label for="emailContent"><h4>Content:</h4></label>
                        <textarea class="form-control" id="emailContent"  name="emailContent" rows="20"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>

                </form>
            </div>


        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.js"></script>
    </div>
</body>
</html>
