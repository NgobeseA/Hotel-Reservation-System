/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.payments.controller;

import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.invoice.Invoice;
import co.za.andile.luxuryleisurehotel.payments.dao.PaymentDaoImpl;
import co.za.andile.luxuryleisurehotel.payments.model.Payment;
import co.za.andile.luxuryleisurehotel.payments.service.PaymentService;
import co.za.andile.luxuryleisurehotel.payments.service.PaymentServiceImpl;
import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDaoImpl;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "PaymentController", urlPatterns = {"/PaymentController"})
public class PaymentController extends HttpServlet {
    private PaymentService paymentService = new PaymentServiceImpl(new ReservationDaoImpl(new DBConnection().connect()));
    private final PaymentService paymentDaoService = new PaymentServiceImpl(new PaymentDaoImpl(new DBConnection().connect()));
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        switch(request.getParameter("submit")){
            case "getPaymentPage":
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                Reservation reservation = paymentService.getReservation(
                        Integer.parseInt(request.getParameter("reservationId")),
                        user.getId());
                if(reservation == null)request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
                double total_amount = paymentService.calcTotalAmount(reservation);
                
                session.setAttribute("total_amount", total_amount);
                session.setAttribute("reservation", reservation);
                request.getRequestDispatcher("payment.jsp").forward(request, response);
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
        switch(request.getParameter("submit")){
            case "pay now":
                /*
                Controller to make payment after the user has reserve a room
                first getting a reservation information and other all required info
                */
                String message;
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                Reservation reservation = paymentService.getReservation(
                        Integer.parseInt(request.getParameter("reservationId")),
                        user.getId());
                if(paymentDaoService.makePayment(new Payment(
                        reservation,
                        request.getParameter("accHolder"),
                        request.getParameter("cardNumber"),
                        Double.parseDouble(request.getParameter("amount"))
                ))){
                    Payment payment = paymentDaoService.getPayment(reservation);
                    if(paymentService.changeStatusReservation(reservation.getBookingId())){
                        
                        new Invoice().generateInvoice(reservation, payment);
                        message = "Successfully made the payment, Thank you for choosing us!";
                        request.setAttribute("paymentMessage", message);
                        request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
                    }
                }
                break;
            case "getPaymentPage":
                doGet(request, response);
                break;
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
