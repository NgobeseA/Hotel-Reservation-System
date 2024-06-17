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
import co.za.andile.luxuryleisurehotel.room.model.Room;
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        switch(request.getParameter("submit")){
            case "reserveRoom":
                Date checkInDate, checkOutDate;
                LocalDateTime check_in=null, check_out=null;
                Instant instant;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    checkInDate =formatter.parse(request.getParameter("checkIn"));
                    instant = checkInDate.toInstant();
                    check_in = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    check_in = check_in.with(LocalTime.of(14, 0,0));
                    checkOutDate = formatter.parse(request.getParameter("checkOut"));
                    check_out = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    check_out = check_out.with(LocalTime.of(12, 0, 0));
                } catch (ParseException ex) {
                    Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                HttpSession session = request.getSession(false);
                List<Room> rooms = (List<Room>) session.getAttribute("rooms");
                Optional <Room> matchingRoom = rooms.stream().
                        filter(s -> s.getId() == Integer.parseInt(request.getParameter("roomIds")))
                        .findFirst();
                
                if(!reservationService.makeReservation(new Reservation(
                        (User) session.getAttribute("user"),
                        matchingRoom.get(),
                        check_in,
                        check_out,
                        Status.PENDING
                ))){
                    request.setAttribute("message", "failed to make reservation");
                }
                request.getRequestDispatcher("home.jsp").forward(request, response);
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
