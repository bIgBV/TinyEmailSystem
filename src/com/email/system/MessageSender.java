package com.email.system;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.sql.Timestamp;

/**
 * Created by bIgB on 9/19/2014.
 *
 * This servlet handles sending messages by saving them to the database.
 */

@WebServlet("/messageSender")
public class MessageSender extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Getting various parameters required for the query.
        String toAddress = request.getParameter("toAddress");
        String msgSubject = request.getParameter("subjectField");
        String msgContent = request.getParameter("emailContent");


        System.out.println("Sending to :" + toAddress);

        // Getting the username of the user stored in the session.
        HttpSession session = request.getSession();
        String fromAddress = (String) session.getAttribute("userId");

        if(toAddress == "" || msgContent == "" || msgSubject ==  ""){
            String msg = "All fields must be filled";
            request.setAttribute("error", msg);
            getServletContext().getRequestDispatcher("/compose.jsp").forward(request, response);
            return;

        }else {
            Calendar cal = Calendar.getInstance();
            Timestamp msgTime = new Timestamp(cal.getTimeInMillis());
            MessageModel msgModel = new MessageModel(toAddress,fromAddress, msgSubject, msgContent, msgTime);

            msgModel.sendMessage(msgModel);
            System.out.println("Sent message..");
            System.out.println("------------------------------------");

            response.sendRedirect("inbox.jsp");
        }
    }
}
