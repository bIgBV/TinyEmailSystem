package com.email.system;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by bIgB on 10/7/2014.
 *
 * This servlet handles showing a particular message when provided an id.
 */
@WebServlet("/messages")
public class DisplayMessage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryParameter = request.getParameter("id");
        if(queryParameter != null) {
            int messageId = Integer.parseInt(queryParameter);
            try {

                // Getting the required properties by calling getMessageById() method.
                MessageModel model = MessageModel.getMessageById(messageId);
                if(model.getMessageTime() != null) {
                    String msgFrom = model.getFromAddress();
                    String msgSub = model.getMessageSubject();
                    String msgCnt = model.getMessageContent();
                    Timestamp msgTime = model.getMessageTime();

                    // Setting various request attributes to be used by the jsp
                    request.setAttribute("msgFrom", msgFrom);
                    request.setAttribute("msgSub", msgSub);
                    request.setAttribute("msgCnt", msgCnt);
                    request.setAttribute("msgTime", msgTime);

                    // Forwarding the request to the jsp.
                    getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
                }
                else{
                    String error = "There was an error.";
                    request.setAttribute("error", error);
                    getServletContext().getRequestDispatcher("/inbox.jsp").forward(request, response);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
