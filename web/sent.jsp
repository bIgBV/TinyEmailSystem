<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.email.system.MessageModel" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link type="text/css" href="${ pageContext.request.contextPath }/css/bootstrap.css" rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Sent</title>
        <script type="application/javascript">
            document.ready(function($) {
                $(".click").click(function() {
                    window.document.location = $(this).attr("href");
                });
            });
        </script>
</head>
<body>
    <div class="container">
        <%
            //TODO: find out why the first message is not being displayed.
            String userName = (String) session.getAttribute("userId");
            ArrayList<MessageModel> list = MessageModel.getSentMessages(userName);
            pageContext.setAttribute("messageList", list);

            int numberOfMessages = MessageModel.numberOfSentMessages(userName);
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
                <hr>
            </div>
            <div class="container">
                <div class="col-md-12">
                    <h4>
                        Welcome <strong><%= userName %></strong>. Number of sent messages : <%= numberOfMessages %>
                    </h4>
                    <table class="table table-hover">
                        <tr>
                            <th>#</th>
                            <th>To</th>
                            <th>Subject</th>
                            <th>Time</th>
                        </tr>
                        <!-- This forEach loop displays the various properties of the MessageModel object. -->
                        <c:forEach items="${messageList}" var="message" varStatus="status">
                            <tr onclick="window.document.location='messages?id=${message.messageId}'">
                                <td>
                                    <c:out value="${status.index + 1}"/>
                                </td>
                                <td>
                                    <c:out value="${message.toAddress}"/>
                                </td>
                                <td>
                                    <c:out value="${message.messageSubject}"/>
                                </td>
                                <td>
                                    <c:out value="${message.messageTime}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.js"></script>
    </div>
</body>
</html>