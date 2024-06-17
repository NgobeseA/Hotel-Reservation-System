/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.controller;

import co.za.andile.luxuryleisurehotel.BDconnection.Connect;
import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDaoImpl;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.room.dao.RoomDaoImpl;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.service.RoomService;
import co.za.andile.luxuryleisurehotel.room.service.RoomServiceImpl;
import co.za.andile.luxuryleisurehotel.users.dao.UserDaoImpl;
import co.za.andile.luxuryleisurehotel.users.emailservice.EmailServiceImpl;
import co.za.andile.luxuryleisurehotel.users.encryption.UserEncryptServiceImpl;
import co.za.andile.luxuryleisurehotel.users.model.User;
import co.za.andile.luxuryleisurehotel.users.service.UserService;
import co.za.andile.luxuryleisurehotel.users.service.UserServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    private final RoomService roomService = new RoomServiceImpl(new RoomDaoImpl(new DBConnection().connect()));
    private final UserService userService = new UserServiceImpl(
            new UserDaoImpl(new DBConnection().connect()),
            new EmailServiceImpl(),
            new UserEncryptServiceImpl()
    );
    private final UserService userServiceReservation = new UserServiceImpl(new ReservationDaoImpl(new DBConnection().connect()));
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        switch(request.getParameter("submit")){
            case "getSignUpPage":
               request.getRequestDispatcher("register.jsp").forward(request, response);
               break;
            case "getLoginPage":
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;
            case "getDashboard":
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                System.out.println("User id from session: "+ user.getId());
                List<Reservation> reservations = userServiceReservation.getUserReservation(user.getId());
                session.setAttribute("reservations", reservations);
                request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
                break;
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        switch(request.getParameter("submit")){
            case "register":
                 boolean result = userService.createUser(
                        request.getParameter("name"),
                        request.getParameter("surname"),
                        request.getParameter("email"),
                        request.getParameter("contact"),
                        request.getParameter("address"),
                        request.getParameter("password"), false, false);
                 
                if(result){
                    //request.setAttribute("message", message);
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                }else{
                    request.setAttribute("message", "failed to register");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
                break;
            case "login":
                User user = userService.login(
                        request.getParameter("email"), 
                        request.getParameter("password"));
                System.out.println("User controller " + user.isAdmin());
                if (user != null) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    List<Room> rooms = roomService.getAllAvailableRooms();
                    session.setAttribute("rooms", rooms);
                    System.out.println("About to go down");
                    rooms.forEach(System.out::println);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
                    if (dispatcher == null) {
                        System.out.println("RequestDispatcher for 'home.jsp' is null.");
                    } else {
                        dispatcher.forward(request, response);
                    }
                } else {
                    request.setAttribute("message", "Incorrect details");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
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
