package com.email.system;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
/**
 * Created by bIgB on 9/26/2014.
 */
public class LoginHandler extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("username").equals("") || request.getParameter("password").equals("")){
            String msg = "Both username and password fields must be filled";
            request.setAttribute("error", msg);
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
        else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                String passwordHash = UserModel.hashPassword(password);
                String hash = UserModel.getUserHash(username);
                if(hash != "") {
                    if (passwordHash.equals(hash)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("userId", username);
                        getServletContext().getRequestDispatcher("/inbox.jsp").forward(request, response);
                    } else {
                        String error = "Username or password incorrect";
                        request.setAttribute("error", error);
                        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                    }
                }else {
                    String error = "Username or password incorrect";
                    request.setAttribute("error", error);
                    getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
