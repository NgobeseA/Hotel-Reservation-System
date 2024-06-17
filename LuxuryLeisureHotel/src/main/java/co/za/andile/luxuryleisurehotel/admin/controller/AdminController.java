/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.admin.controller;

import co.za.andile.luxuryleisurehotel.admin.dao.AdminDaoImpl;
import co.za.andile.luxuryleisurehotel.admin.service.AdminService;
import co.za.andile.luxuryleisurehotel.admin.service.AdminServiceImpl;
import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.users.model.User;
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
@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {
    private final AdminService adminService = new AdminServiceImpl(new AdminDaoImpl(new DBConnection().connect()));
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        switch(request.getParameter("submit")){
            case "addUser":
                boolean isAdmin = false;
                String checkbox = request.getParameter("admin");
                if(checkbox != null){
                    System.out.println("something");
                    isAdmin = true;
                }
                if(adminService.createUser(new User(
                        request.getParameter("name"),
                        request.getParameter("surname"),
                        request.getParameter("email"),
                        request.getParameter("contact"),
                        request.getParameter("address"),
                        isAdmin,
                        false
                 ),request.getParameter("password"))){
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else{
                    request.setAttribute("message", "failed to add user");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
                break;
        }
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
