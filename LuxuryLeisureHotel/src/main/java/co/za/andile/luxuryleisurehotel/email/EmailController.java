/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.email;

import co.za.andile.luxuryleisurehotel.users.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
//import org.simplejavamail.api.email.Email;
//import org.simplejavamail.email.EmailBuilder;
//import org.simplejavamail.api.mailer.Mailer;
//import org.simplejavamail.mailer.MailerBuilder;
//import org.simplejavamail.api.mailer.config.TransportStrategy;


@WebServlet(name = "EmailController", urlPatterns = {"/EmailController"})

public class EmailController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get form parameters
        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        try {
            // Build the email
            Email email = EmailBuilder.startingBlank()
                    .from("Luxury Leisure Hotel (LLH)", "andilek.ngobese@outlook.com")
                    .to(to)
                    .withSubject(subject)
                    .withPlainText(message)
                    .buildEmail();

            // Configure the mailer
            Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.gmail.com", 587, "andilekngobese@gmail.com", "isni ridl rhrw fcxs")
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .buildMailer();

            // Send the email
            mailer.sendMail(email);
            System.out.println("Email sent successfully to " + to);

            // Provide feedback to the user
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Email sent successfully to " + to);

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Failed to send email: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
