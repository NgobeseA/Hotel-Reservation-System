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
import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDaoImpl;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.reservations.service.ReservationService;
import co.za.andile.luxuryleisurehotel.reservations.service.ReservationServiceImpl;
import co.za.andile.luxuryleisurehotel.room.dao.RoomDaoImpl;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.users.dao.UserDaoImpl;
import co.za.andile.luxuryleisurehotel.users.model.User;
import co.za.andile.luxuryleisurehotel.users.service.UserService;
import co.za.andile.luxuryleisurehotel.users.service.UserServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author T440
 */
@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {
    private final AdminService adminService = new AdminServiceImpl(new AdminDaoImpl(new DBConnection().connect()));
   private final AdminService roomAdminService = new AdminServiceImpl(new RoomDaoImpl(new DBConnection().connect()));
   private final ReservationService reservationService = new ReservationServiceImpl(new ReservationDaoImpl(new DBConnection().connect()));
   private final UserService userService = new UserServiceImpl(new UserDaoImpl(new DBConnection().connect())); 
   
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        switch(request.getParameter("submit")){
            case "getDashboard":
                HttpSession session = request.getSession(false);
                List<Room> rooms = roomAdminService.getAllRooms();
                List<Reservation> bookedRooms = reservationService.filteredReservation();
                session.setAttribute("Allrooms", rooms);
                session.setAttribute("reservedRooms", bookedRooms);
                request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
        }
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
                        isAdmin
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
