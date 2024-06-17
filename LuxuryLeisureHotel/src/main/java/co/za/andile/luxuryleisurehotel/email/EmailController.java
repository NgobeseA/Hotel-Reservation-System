/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.email;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author T440
 */
@WebServlet(name = "EmailController", urlPatterns = {"/EmailController"})
public class EmailController extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   
    @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String messageText = request.getParameter("message");

        final String user = "andilekngobese@gmail.com"; // change accordingly
        final String password = "isniridlrhrwfcxs";  // change accordingly

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", "465"); // Use 465 for SSL
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "false"); // Enable TLS
props.put("mail.smtp.ssl.enable", "true"); // Enable SSL
props.put("mail.smtp.ssl.protocols", "TLSv1.2");
props.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        // Enable debug mode for detailed logs (optional)
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            response.getWriter().println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().println("Failed to send email: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
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
