<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.email.system.MessageModel" %>
<%@ page import="java.util.ArrayList" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link type="text/css" href="${ pageContext.request.contextPath }/css/bootstrap.css" rel="stylesheet"/>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Inbox</title>
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
                String error = (String) request.getAttribute("error");
                ArrayList<MessageModel> list = MessageModel.getReceivedMessages(userName);
                pageContext.setAttribute("messageList", list);
                pageContext.setAttribute("error", error);
                int numberOfMessages = MessageModel.numberOfReceivedMessages(userName);
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
                <hr>
                <div class="container">
                    <div class="col-md-12">
                        <h4>
                            Welcome <strong><%= userName %></strong>. Number of messages : <%= numberOfMessages%>
                        </h4>
                        <table class="table table-hover">
                            <tr>
                                <th>#</th>
                                <th>From</th>
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
                                        <c:out value="${message.fromAddress}"/>
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

            </div>
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
            <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.js"></script>
        </div>
    </body>
</html>