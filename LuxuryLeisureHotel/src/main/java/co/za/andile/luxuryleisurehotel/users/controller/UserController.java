/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.controller;

import co.za.andile.luxuryleisurehotel.users.dao.UserDaoImpl;
import co.za.andile.luxuryleisurehotel.users.service.UserService;
import co.za.andile.luxuryleisurehotel.users.service.UserServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author T440
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    private UserService userService = new UserServiceImpl(new UserDaoImpl());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        switch(request.getParameter("submit")){
            case "getSignUpPage":
                String message = userService.createUser(
                        request.getParameter("name"),
                        request.getParameter("surname"),
                        request.getParameter("email"),
                        request.getParameter("contact"),
                        request.getParameter("address"),
                        request.getParameter("password"), false, false);
                if(message.startsWith("Successfully")){
                    //request.setAttribute("message", message);
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                }else{
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
