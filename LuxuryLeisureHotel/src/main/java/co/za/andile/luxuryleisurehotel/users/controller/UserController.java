/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.controller;

import co.za.andile.luxuryleisurehotel.BDconnection.Connect;
import co.za.andile.luxuryleisurehotel.exceptions.DuplicateUserException;
import co.za.andile.luxuryleisurehotel.exceptions.InvalidDataException;
//import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDaoImpl;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.reservations.service.ReservationService;
import co.za.andile.luxuryleisurehotel.reservations.service.ReservationServiceImpl;
import co.za.andile.luxuryleisurehotel.room.dao.RoomDaoImpl;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomtype.dao.RoomTypeDaoImpl;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final RoomService roomService = new RoomServiceImpl(new RoomDaoImpl(new Connect().connectToDB()));
    private final UserService userService = new UserServiceImpl(
            new UserDaoImpl(new Connect().connectToDB()),
            new EmailServiceImpl(),
            new UserEncryptServiceImpl()
    );
    private final ReservationService reservationService = new ReservationServiceImpl(new ReservationDaoImpl(new Connect().connectToDB()));
    private final RoomService roomTService = new RoomServiceImpl(new RoomTypeDaoImpl(new Connect().connectToDB()));

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        switch (request.getParameter("submit")) {
            case "getSignUpPage":
                request.getRequestDispatcher("register.jsp").forward(request, response);
                break;
            case "getLoginPage":
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;
            
            case "logout":
                HttpSession session = request.getSession(false);

                session.removeAttribute("user");
                request.getRequestDispatcher("home?submit=home").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        switch (request.getParameter("submit")) {
            case "register":
                boolean isAdmin = request.getParameter("admin") != null;
                boolean result;
                String message = null;
                try {
                    result = userService.createUser(
                            request.getParameter("name"),
                            request.getParameter("surname"),
                            request.getParameter("email"),
                            request.getParameter("contact"),
                            request.getParameter("password"), isAdmin);

                    if (result)
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                } catch (DuplicateUserException ex) {
                    message = "Email already taken";
                } catch (InvalidDataException ex) {
                    message= "please fill all fields";
                }
                    request.setAttribute("RegisterMessage", message);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
               
                break;
            case "login":
                User user = userService.login(
                        request.getParameter("email"),
                        request.getParameter("password"));
                System.out.println("User controller " + user.getId());
                if (user.getName() != null) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    List<RoomType> rooms = roomTService.getRoomTypes();
                    if (rooms != null) {
                        session.setAttribute("roomtypes", rooms);
                    }
                    request.getRequestDispatcher("home.jsp").forward(request, response);

                } else {
                    request.setAttribute("message", "Incorrect details");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.
        switch (req.getParameter("submit")) {
            case "update":
                HttpSession session = req.getSession(false);
                if (userService.updateUserProfile(new User(
                        req.getParameter("name"),
                        req.getParameter("surname"),
                        req.getParameter("email"),
                        req.getParameter("contact"),
                        req.getParameter("address"),
                        false
                ))) {

                    session.setAttribute("updateUserMessage", "profile updated");

                } else {
                    session.setAttribute("updateUserMessage", "failed to update your profile");
                }
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
