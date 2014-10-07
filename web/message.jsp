<%@ page import="java.sql.Timestamp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link type="text/css" href="${ pageContext.request.contextPath }/css/bootstrap.css" rel="stylesheet"/>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Message</title>
    </head>
    <body>
        <div class="container">
            <%
                String userName = (String) session.getAttribute("userId");
                String messageFrom = (String) request.getAttribute("msgFrom");
                String messageSub = (String) request.getAttribute("msgSub");
                String messageContent = (String) request.getAttribute("msgCnt");
                Timestamp messageTime = (Timestamp) request.getAttribute("msgTime");
            %>
            <div class="container">
                <div class="row">
                    <div class="navbar-inverse navbar-fixed-top" role="navigation">
                        <div class="container">
                            <div class="navbar-header">
                                <a class="navbar-brand" href="#">Tiny Email System</a>
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
            </div>
            <hr>
            <div class="container">
                <div class="col-md-12">
                    <h4>Welcome <strong><%= userName %></strong></h4>
                    <div class="col-md-10">
                        <div>
                            <h3>Message from <strong><%= messageFrom %></strong></h3>
                        </div>
                        <div>
                            <h3>Subject</h3>
                            <blockquote>
                                <%= messageSub %>
                            </blockquote>
                        </div>
                        <div>
                            <h3>Message</h3>
                            <blockquote>
                                <p>
                                    <%= messageContent %>
                                </p>
                            </blockquote>
                        </div>
                        <div>
                            <h3>Time</h3>
                            <small>
                                <%= messageTime%>
                            </small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.js"></script>
    </body>
</html>