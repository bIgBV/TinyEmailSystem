package com.email.system;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * This servlet registers a user.
 */

@WebServlet("/register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("username") == null || request.getParameter("password") == null
                || request.getParameter("username").equals("") || request.getParameter("password").equals("")){
            String msg = "Both username and password fields must be filled";
            request.setAttribute("error", msg);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        else{
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                String hash = UserModel.getUserHash(username);
                if(hash != null) {
                    UserModel model = new UserModel(username, password);
                    // Registering the user
                    model.registerUser(username, password);

                    // Setting session variable to track the user through the website
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", username);
                    ArrayList<MessageModel> msglist = MessageModel.getReceivedMessages(username);
                    request.setAttribute("msgList", msglist);
                }else
                {
                    String error = "username already exists";
                    request.setAttribute("error", error);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            getServletContext().getRequestDispatcher("/inbox.jsp").forward(request, response);
        }
    }

}
