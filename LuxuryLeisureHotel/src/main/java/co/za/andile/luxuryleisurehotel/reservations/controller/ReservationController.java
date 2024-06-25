/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.controller;

import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDaoImpl;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.reservations.model.Status;
import co.za.andile.luxuryleisurehotel.reservations.service.ReservationService;
import co.za.andile.luxuryleisurehotel.reservations.service.ReservationServiceImpl;
import co.za.andile.luxuryleisurehotel.room.dao.RoomDaoImpl;
import co.za.andile.luxuryleisurehotel.room.exception.NoRoomAvailableException;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.service.RoomServiceImpl;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
@WebServlet(name = "ReservationController", urlPatterns = {"/ReservationController"})
public class ReservationController extends HttpServlet {

    private final ReservationService reservationService = new ReservationServiceImpl(new ReservationDaoImpl(new DBConnection().connect()));
    private final ReservationService reservationServiceR = new ReservationServiceImpl(new RoomServiceImpl(new RoomDaoImpl(new DBConnection().connect())));

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
        switch (request.getParameter("submit")) {
            case "getBookingPage":
                request.getRequestDispatcher("booking.jsp").forward(request, response);
                break;
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
        switch (request.getParameter("submit")) {
            case "reserveRoom":
                handleReserveRoom(request, response);
            break;

            case "cancelReservation":
                handleCancelReservation(request, response);
            break;
            case "service":
                handleServiceForUser(request, response);
                break;
            case "checkIn":
                handleSigningInGuest(request, response);
                break;
            case "checkOut":
                handleSignOutGuest(request, response);
                break;
        }

    }

    private LocalDateTime convertToDateTime(String dateStr, int hr) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateStr);
        Instant instant = date.toInstant();
        LocalDateTime toTimeDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return toTimeDate.with(LocalTime.of(hr, 0, 0));
    }

    private void handleReserveRoom(HttpServletRequest request, HttpServletResponse response){
        try{
                LocalDateTime check_in , check_out ;
                String message = null;
                Room matchingRoom ;
                HttpSession session = request.getSession(false);
                try{
                    check_in = convertToDateTime(request.getParameter("checkIn"), 14);
                    check_out = convertToDateTime(request.getParameter("checkOut"), 12);

                    matchingRoom = reservationServiceR.getRoomFromRoomType(
                            Integer.parseInt(request.getParameter("roomtypeId")),
                            check_in,
                            check_out);


                    if (reservationService.makeReservation(new Reservation(
                            (User) session.getAttribute("user"),
                            matchingRoom,
                            check_in,
                            check_out,
                            Status.PENDING,
                            request.getParameter("mealtype"),
                            request.getParameter("dietary")
                    ))) {
                        //if (reservationServiceR.editRoomStatus(matchingRoom.getId())) {
                        request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
                        //}
                    } else {
                        message = "failed to register";
                    }
                } catch (ParseException ex) {
                    message = "Please contact the admin, internal server error";
                } catch (NoRoomAvailableException ex) {
                    message = ex.getMessage();
                }
                request.setAttribute("message", message);
                request.getRequestDispatcher("booking.jsp").forward(request, response);

            } catch (ServletException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void handleCancelReservation(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(reservationService.cancelReservation(
                Integer.parseInt(request.getParameter("reservationId")))){

            try {
                List<Reservation> bookedRooms = reservationService.filteredReservation();

                session.setAttribute("reservedRooms", bookedRooms);
                request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void handleServiceForUser(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        List<Reservation> reservations = reservationService.getUserReservation(request.getParameter("email"));
        
        List<Reservation> rightReservations = reservations.stream()
                .filter(r -> r.getStatus() != Status.CANCELLED || r.getStatus() != Status.CHECKED_OUT)
                .collect(Collectors.toList());
        List<Reservation> wrongReservations = reservations.stream()
                .filter(s -> s.getStatus() == Status.CANCELLED || s.getStatus() == Status.CHECKED_OUT)
                .collect(Collectors.toList());
        request.setAttribute("rightReservations", rightReservations);
        request.setAttribute("wrongReservations", wrongReservations);
        
        try {
            request.getRequestDispatcher("guestService.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleSigningInGuest(HttpServletRequest request, HttpServletResponse response){
        String message;
        if(reservationService.checkInReservation(
                Integer.parseInt(request.getParameter("reservationId")))){
            message ="Guest successfully signed in";
            request.setAttribute("serviceMessage", message);
            
        }else {
            request.setAttribute("serviceMessage", "failed to checkIn");
        }
        
        try {
            request.getRequestDispatcher("guestService.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleSignOutGuest(HttpServletRequest request, HttpServletResponse response){
        String message;
        if(reservationService.checkOutReservation(
                Integer.parseInt(request.getParameter("reservationId")))){
            message ="Guest successfully signed Out";
            request.setAttribute("serviceMessage", message);
            
        }else {
            request.setAttribute("serviceMessage", "failed to checkIn");
        }
        
        try {
            request.getRequestDispatcher("guestService.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
